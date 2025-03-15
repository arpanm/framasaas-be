package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FranchiseUserStatusHistoryAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FranchiseUserStatusHistory;
import com.framasaas.be.domain.enumeration.FranchiseUserStatus;
import com.framasaas.be.repository.FranchiseUserStatusHistoryRepository;
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
    public void initTest() {
        franchiseUserStatusHistory = createEntity();
    }

    @AfterEach
    public void cleanup() {
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
        var returnedFranchiseUserStatusHistory = om.readValue(
            restFranchiseUserStatusHistoryMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistory))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseUserStatusHistory.class
        );

        // Validate the FranchiseUserStatusHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
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

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseUserStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistory)))
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

        restFranchiseUserStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistory)))
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

        restFranchiseUserStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistory)))
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

        restFranchiseUserStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistory)))
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

        restFranchiseUserStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFranchiseUserStatusHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFranchiseUserStatusHistory))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseUserStatusHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseUserStatusHistory))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseUserStatusHistory))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserStatusHistory)))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseUserStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseUserStatusHistory))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseUserStatusHistory))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseUserStatusHistory))
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
