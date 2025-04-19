package com.framasaas.web.rest;

import static com.framasaas.domain.FranchiseUserStatusHistoryAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.FranchiseUser;
import com.framasaas.domain.FranchiseUserStatusHistory;
import com.framasaas.domain.enumeration.FranchiseUserStatus;
import com.framasaas.repository.FranchiseUserStatusHistoryRepository;
import com.framasaas.service.dto.FranchiseUserStatusHistoryDTO;
import com.framasaas.service.mapper.FranchiseUserStatusHistoryMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FranchiseUserStatusHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseUserStatusHistoryResourceIT {

    private static final FranchiseUserStatus DEFAULT_USER_SATUS = FranchiseUserStatus.PendingApproval;
    private static final FranchiseUserStatus UPDATED_USER_SATUS = FranchiseUserStatus.Active;

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchise-user-status-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseUserStatusHistoryRepository franchiseUserStatusHistoryRepository;

    @Autowired
    private FranchiseUserStatusHistoryMapper franchiseUserStatusHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseUserStatusHistoryMockMvc;

    private FranchiseUserStatusHistory franchiseUserStatusHistory;

    private FranchiseUserStatusHistory insertedFranchiseUserStatusHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseUserStatusHistory createEntity() {
        return new FranchiseUserStatusHistory()
            .userSatus(DEFAULT_USER_SATUS)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseUserStatusHistory createUpdatedEntity() {
        return new FranchiseUserStatusHistory()
            .userSatus(UPDATED_USER_SATUS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        franchiseUserStatusHistory = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFranchiseUserStatusHistory != null) {
            franchiseUserStatusHistoryRepository.delete(insertedFranchiseUserStatusHistory);
            insertedFranchiseUserStatusHistory = null;
        }
    }

    @Test
    @Transactional
    void createFranchiseUserStatusHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchiseUserStatusHistory
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);
        var returnedFranchiseUserStatusHistoryDTO = om.readValue(
            restFranchiseUserStatusHistoryMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseUserStatusHistoryDTO.class
        );

        // Validate the FranchiseUserStatusHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFranchiseUserStatusHistory = franchiseUserStatusHistoryMapper.toEntity(returnedFranchiseUserStatusHistoryDTO);
        assertFranchiseUserStatusHistoryUpdatableFieldsEquals(
            returnedFranchiseUserStatusHistory,
            getPersistedFranchiseUserStatusHistory(returnedFranchiseUserStatusHistory)
        );

        insertedFranchiseUserStatusHistory = returnedFranchiseUserStatusHistory;
    }

    @Test
    @Transactional
    void createFranchiseUserStatusHistoryWithExistingId() throws Exception {
        // Create the FranchiseUserStatusHistory with an existing ID
        franchiseUserStatusHistory.setId(1L);
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseUserStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserSatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseUserStatusHistory.setUserSatus(null);

        // Create the FranchiseUserStatusHistory, which fails.
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);

        restFranchiseUserStatusHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseUserStatusHistory.setUpdatedBy(null);

        // Create the FranchiseUserStatusHistory, which fails.
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);

        restFranchiseUserStatusHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseUserStatusHistory.setUpdatedTime(null);

        // Create the FranchiseUserStatusHistory, which fails.
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);

        restFranchiseUserStatusHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistories() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList
        restFranchiseUserStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseUserStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].userSatus").value(hasItem(DEFAULT_USER_SATUS.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchiseUserStatusHistory() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get the franchiseUserStatusHistory
        restFranchiseUserStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, franchiseUserStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchiseUserStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.userSatus").value(DEFAULT_USER_SATUS.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getFranchiseUserStatusHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        Long id = franchiseUserStatusHistory.getId();

        defaultFranchiseUserStatusHistoryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFranchiseUserStatusHistoryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFranchiseUserStatusHistoryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUserSatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where userSatus equals to
        defaultFranchiseUserStatusHistoryFiltering("userSatus.equals=" + DEFAULT_USER_SATUS, "userSatus.equals=" + UPDATED_USER_SATUS);
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUserSatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where userSatus in
        defaultFranchiseUserStatusHistoryFiltering(
            "userSatus.in=" + DEFAULT_USER_SATUS + "," + UPDATED_USER_SATUS,
            "userSatus.in=" + UPDATED_USER_SATUS
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUserSatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where userSatus is not null
        defaultFranchiseUserStatusHistoryFiltering("userSatus.specified=true", "userSatus.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where updatedBy equals to
        defaultFranchiseUserStatusHistoryFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where updatedBy in
        defaultFranchiseUserStatusHistoryFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where updatedBy is not null
        defaultFranchiseUserStatusHistoryFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where updatedBy contains
        defaultFranchiseUserStatusHistoryFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where updatedBy does not contain
        defaultFranchiseUserStatusHistoryFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where updatedTime equals to
        defaultFranchiseUserStatusHistoryFiltering(
            "updatedTime.equals=" + DEFAULT_UPDATED_TIME,
            "updatedTime.equals=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where updatedTime in
        defaultFranchiseUserStatusHistoryFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        // Get all the franchiseUserStatusHistoryList where updatedTime is not null
        defaultFranchiseUserStatusHistoryFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUserStatusHistoriesByFranchiseUserIsEqualToSomething() throws Exception {
        FranchiseUser franchiseUser;
        if (TestUtil.findAll(em, FranchiseUser.class).isEmpty()) {
            franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);
            franchiseUser = FranchiseUserResourceIT.createEntity();
        } else {
            franchiseUser = TestUtil.findAll(em, FranchiseUser.class).get(0);
        }
        em.persist(franchiseUser);
        em.flush();
        franchiseUserStatusHistory.setFranchiseUser(franchiseUser);
        franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);
        Long franchiseUserId = franchiseUser.getId();
        // Get all the franchiseUserStatusHistoryList where franchiseUser equals to franchiseUserId
        defaultFranchiseUserStatusHistoryShouldBeFound("franchiseUserId.equals=" + franchiseUserId);

        // Get all the franchiseUserStatusHistoryList where franchiseUser equals to (franchiseUserId + 1)
        defaultFranchiseUserStatusHistoryShouldNotBeFound("franchiseUserId.equals=" + (franchiseUserId + 1));
    }

    private void defaultFranchiseUserStatusHistoryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFranchiseUserStatusHistoryShouldBeFound(shouldBeFound);
        defaultFranchiseUserStatusHistoryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFranchiseUserStatusHistoryShouldBeFound(String filter) throws Exception {
        restFranchiseUserStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseUserStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].userSatus").value(hasItem(DEFAULT_USER_SATUS.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restFranchiseUserStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFranchiseUserStatusHistoryShouldNotBeFound(String filter) throws Exception {
        restFranchiseUserStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFranchiseUserStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFranchiseUserStatusHistory() throws Exception {
        // Get the franchiseUserStatusHistory
        restFranchiseUserStatusHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchiseUserStatusHistory() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseUserStatusHistory
        FranchiseUserStatusHistory updatedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository
            .findById(franchiseUserStatusHistory.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFranchiseUserStatusHistory are not directly saved in db
        em.detach(updatedFranchiseUserStatusHistory);
        updatedFranchiseUserStatusHistory.userSatus(UPDATED_USER_SATUS).updatedBy(UPDATED_UPDATED_BY).updatedTime(UPDATED_UPDATED_TIME);
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(
            updatedFranchiseUserStatusHistory
        );

        restFranchiseUserStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseUserStatusHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseUserStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseUserStatusHistoryToMatchAllProperties(updatedFranchiseUserStatusHistory);
    }

    @Test
    @Transactional
    void putNonExistingFranchiseUserStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUserStatusHistory.setId(longCount.incrementAndGet());

        // Create the FranchiseUserStatusHistory
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseUserStatusHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseUserStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchiseUserStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUserStatusHistory.setId(longCount.incrementAndGet());

        // Create the FranchiseUserStatusHistory
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseUserStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchiseUserStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUserStatusHistory.setId(longCount.incrementAndGet());

        // Create the FranchiseUserStatusHistory
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseUserStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseUserStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseUserStatusHistory using partial update
        FranchiseUserStatusHistory partialUpdatedFranchiseUserStatusHistory = new FranchiseUserStatusHistory();
        partialUpdatedFranchiseUserStatusHistory.setId(franchiseUserStatusHistory.getId());

        partialUpdatedFranchiseUserStatusHistory
            .userSatus(UPDATED_USER_SATUS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseUserStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseUserStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseUserStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseUserStatusHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseUserStatusHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchiseUserStatusHistory, franchiseUserStatusHistory),
            getPersistedFranchiseUserStatusHistory(franchiseUserStatusHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseUserStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseUserStatusHistory using partial update
        FranchiseUserStatusHistory partialUpdatedFranchiseUserStatusHistory = new FranchiseUserStatusHistory();
        partialUpdatedFranchiseUserStatusHistory.setId(franchiseUserStatusHistory.getId());

        partialUpdatedFranchiseUserStatusHistory
            .userSatus(UPDATED_USER_SATUS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseUserStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseUserStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseUserStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseUserStatusHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseUserStatusHistoryUpdatableFieldsEquals(
            partialUpdatedFranchiseUserStatusHistory,
            getPersistedFranchiseUserStatusHistory(partialUpdatedFranchiseUserStatusHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFranchiseUserStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUserStatusHistory.setId(longCount.incrementAndGet());

        // Create the FranchiseUserStatusHistory
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseUserStatusHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseUserStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchiseUserStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUserStatusHistory.setId(longCount.incrementAndGet());

        // Create the FranchiseUserStatusHistory
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseUserStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchiseUserStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUserStatusHistory.setId(longCount.incrementAndGet());

        // Create the FranchiseUserStatusHistory
        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseUserStatusHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseUserStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchiseUserStatusHistory() throws Exception {
        // Initialize the database
        insertedFranchiseUserStatusHistory = franchiseUserStatusHistoryRepository.saveAndFlush(franchiseUserStatusHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchiseUserStatusHistory
        restFranchiseUserStatusHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchiseUserStatusHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseUserStatusHistoryRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected FranchiseUserStatusHistory getPersistedFranchiseUserStatusHistory(FranchiseUserStatusHistory franchiseUserStatusHistory) {
        return franchiseUserStatusHistoryRepository.findById(franchiseUserStatusHistory.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseUserStatusHistoryToMatchAllProperties(
        FranchiseUserStatusHistory expectedFranchiseUserStatusHistory
    ) {
        assertFranchiseUserStatusHistoryAllPropertiesEquals(
            expectedFranchiseUserStatusHistory,
            getPersistedFranchiseUserStatusHistory(expectedFranchiseUserStatusHistory)
        );
    }

    protected void assertPersistedFranchiseUserStatusHistoryToMatchUpdatableProperties(
        FranchiseUserStatusHistory expectedFranchiseUserStatusHistory
    ) {
        assertFranchiseUserStatusHistoryAllUpdatablePropertiesEquals(
            expectedFranchiseUserStatusHistory,
            getPersistedFranchiseUserStatusHistory(expectedFranchiseUserStatusHistory)
        );
    }
}
