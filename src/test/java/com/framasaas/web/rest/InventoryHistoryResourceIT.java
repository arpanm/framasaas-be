package com.framasaas.web.rest;

import static com.framasaas.domain.InventoryHistoryAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.InventoryHistory;
import com.framasaas.domain.enumeration.InventoryChangeReason;
import com.framasaas.repository.InventoryHistoryRepository;
import com.framasaas.service.dto.InventoryHistoryDTO;
import com.framasaas.service.mapper.InventoryHistoryMapper;
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
 * Integration tests for the {@link InventoryHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InventoryHistoryResourceIT {

    private static final Long DEFAULT_INVENTORY_VALUE = 1L;
    private static final Long UPDATED_INVENTORY_VALUE = 2L;
    private static final Long SMALLER_INVENTORY_VALUE = 1L - 1L;

    private static final InventoryChangeReason DEFAULT_REASON = InventoryChangeReason.GRIN;
    private static final InventoryChangeReason UPDATED_REASON = InventoryChangeReason.RETURNINIT;

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/inventory-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InventoryHistoryRepository inventoryHistoryRepository;

    @Autowired
    private InventoryHistoryMapper inventoryHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryHistoryMockMvc;

    private InventoryHistory inventoryHistory;

    private InventoryHistory insertedInventoryHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryHistory createEntity() {
        return new InventoryHistory()
            .inventoryValue(DEFAULT_INVENTORY_VALUE)
            .reason(DEFAULT_REASON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryHistory createUpdatedEntity() {
        return new InventoryHistory()
            .inventoryValue(UPDATED_INVENTORY_VALUE)
            .reason(UPDATED_REASON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        inventoryHistory = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedInventoryHistory != null) {
            inventoryHistoryRepository.delete(insertedInventoryHistory);
            insertedInventoryHistory = null;
        }
    }

    @Test
    @Transactional
    void createInventoryHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InventoryHistory
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);
        var returnedInventoryHistoryDTO = om.readValue(
            restInventoryHistoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryHistoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InventoryHistoryDTO.class
        );

        // Validate the InventoryHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInventoryHistory = inventoryHistoryMapper.toEntity(returnedInventoryHistoryDTO);
        assertInventoryHistoryUpdatableFieldsEquals(returnedInventoryHistory, getPersistedInventoryHistory(returnedInventoryHistory));

        insertedInventoryHistory = returnedInventoryHistory;
    }

    @Test
    @Transactional
    void createInventoryHistoryWithExistingId() throws Exception {
        // Create the InventoryHistory with an existing ID
        inventoryHistory.setId(1L);
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInventoryValueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryHistory.setInventoryValue(null);

        // Create the InventoryHistory, which fails.
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        restInventoryHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReasonIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryHistory.setReason(null);

        // Create the InventoryHistory, which fails.
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        restInventoryHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryHistory.setUpdatedBy(null);

        // Create the InventoryHistory, which fails.
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        restInventoryHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryHistory.setUpdatedTime(null);

        // Create the InventoryHistory, which fails.
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        restInventoryHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInventoryHistories() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList
        restInventoryHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inventoryValue").value(hasItem(DEFAULT_INVENTORY_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getInventoryHistory() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get the inventoryHistory
        restInventoryHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, inventoryHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryHistory.getId().intValue()))
            .andExpect(jsonPath("$.inventoryValue").value(DEFAULT_INVENTORY_VALUE.intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getInventoryHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        Long id = inventoryHistory.getId();

        defaultInventoryHistoryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultInventoryHistoryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultInventoryHistoryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByInventoryValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where inventoryValue equals to
        defaultInventoryHistoryFiltering(
            "inventoryValue.equals=" + DEFAULT_INVENTORY_VALUE,
            "inventoryValue.equals=" + UPDATED_INVENTORY_VALUE
        );
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByInventoryValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where inventoryValue in
        defaultInventoryHistoryFiltering(
            "inventoryValue.in=" + DEFAULT_INVENTORY_VALUE + "," + UPDATED_INVENTORY_VALUE,
            "inventoryValue.in=" + UPDATED_INVENTORY_VALUE
        );
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByInventoryValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where inventoryValue is not null
        defaultInventoryHistoryFiltering("inventoryValue.specified=true", "inventoryValue.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByInventoryValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where inventoryValue is greater than or equal to
        defaultInventoryHistoryFiltering(
            "inventoryValue.greaterThanOrEqual=" + DEFAULT_INVENTORY_VALUE,
            "inventoryValue.greaterThanOrEqual=" + UPDATED_INVENTORY_VALUE
        );
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByInventoryValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where inventoryValue is less than or equal to
        defaultInventoryHistoryFiltering(
            "inventoryValue.lessThanOrEqual=" + DEFAULT_INVENTORY_VALUE,
            "inventoryValue.lessThanOrEqual=" + SMALLER_INVENTORY_VALUE
        );
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByInventoryValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where inventoryValue is less than
        defaultInventoryHistoryFiltering(
            "inventoryValue.lessThan=" + UPDATED_INVENTORY_VALUE,
            "inventoryValue.lessThan=" + DEFAULT_INVENTORY_VALUE
        );
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByInventoryValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where inventoryValue is greater than
        defaultInventoryHistoryFiltering(
            "inventoryValue.greaterThan=" + SMALLER_INVENTORY_VALUE,
            "inventoryValue.greaterThan=" + DEFAULT_INVENTORY_VALUE
        );
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where reason equals to
        defaultInventoryHistoryFiltering("reason.equals=" + DEFAULT_REASON, "reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where reason in
        defaultInventoryHistoryFiltering("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON, "reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where reason is not null
        defaultInventoryHistoryFiltering("reason.specified=true", "reason.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where updatedBy equals to
        defaultInventoryHistoryFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where updatedBy in
        defaultInventoryHistoryFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where updatedBy is not null
        defaultInventoryHistoryFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where updatedBy contains
        defaultInventoryHistoryFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where updatedBy does not contain
        defaultInventoryHistoryFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where updatedTime equals to
        defaultInventoryHistoryFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where updatedTime in
        defaultInventoryHistoryFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllInventoryHistoriesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        // Get all the inventoryHistoryList where updatedTime is not null
        defaultInventoryHistoryFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    private void defaultInventoryHistoryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultInventoryHistoryShouldBeFound(shouldBeFound);
        defaultInventoryHistoryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryHistoryShouldBeFound(String filter) throws Exception {
        restInventoryHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inventoryValue").value(hasItem(DEFAULT_INVENTORY_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restInventoryHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryHistoryShouldNotBeFound(String filter) throws Exception {
        restInventoryHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInventoryHistory() throws Exception {
        // Get the inventoryHistory
        restInventoryHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInventoryHistory() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventoryHistory
        InventoryHistory updatedInventoryHistory = inventoryHistoryRepository.findById(inventoryHistory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInventoryHistory are not directly saved in db
        em.detach(updatedInventoryHistory);
        updatedInventoryHistory
            .inventoryValue(UPDATED_INVENTORY_VALUE)
            .reason(UPDATED_REASON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(updatedInventoryHistory);

        restInventoryHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the InventoryHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInventoryHistoryToMatchAllProperties(updatedInventoryHistory);
    }

    @Test
    @Transactional
    void putNonExistingInventoryHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryHistory.setId(longCount.incrementAndGet());

        // Create the InventoryHistory
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInventoryHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryHistory.setId(longCount.incrementAndGet());

        // Create the InventoryHistory
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInventoryHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryHistory.setId(longCount.incrementAndGet());

        // Create the InventoryHistory
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InventoryHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInventoryHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventoryHistory using partial update
        InventoryHistory partialUpdatedInventoryHistory = new InventoryHistory();
        partialUpdatedInventoryHistory.setId(inventoryHistory.getId());

        partialUpdatedInventoryHistory.reason(UPDATED_REASON).updatedBy(UPDATED_UPDATED_BY).updatedTime(UPDATED_UPDATED_TIME);

        restInventoryHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventoryHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventoryHistory))
            )
            .andExpect(status().isOk());

        // Validate the InventoryHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventoryHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInventoryHistory, inventoryHistory),
            getPersistedInventoryHistory(inventoryHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateInventoryHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventoryHistory using partial update
        InventoryHistory partialUpdatedInventoryHistory = new InventoryHistory();
        partialUpdatedInventoryHistory.setId(inventoryHistory.getId());

        partialUpdatedInventoryHistory
            .inventoryValue(UPDATED_INVENTORY_VALUE)
            .reason(UPDATED_REASON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restInventoryHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventoryHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventoryHistory))
            )
            .andExpect(status().isOk());

        // Validate the InventoryHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventoryHistoryUpdatableFieldsEquals(
            partialUpdatedInventoryHistory,
            getPersistedInventoryHistory(partialUpdatedInventoryHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInventoryHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryHistory.setId(longCount.incrementAndGet());

        // Create the InventoryHistory
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventoryHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventoryHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInventoryHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryHistory.setId(longCount.incrementAndGet());

        // Create the InventoryHistory
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventoryHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInventoryHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryHistory.setId(longCount.incrementAndGet());

        // Create the InventoryHistory
        InventoryHistoryDTO inventoryHistoryDTO = inventoryHistoryMapper.toDto(inventoryHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryHistoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inventoryHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InventoryHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInventoryHistory() throws Exception {
        // Initialize the database
        insertedInventoryHistory = inventoryHistoryRepository.saveAndFlush(inventoryHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inventoryHistory
        restInventoryHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventoryHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inventoryHistoryRepository.count();
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

    protected InventoryHistory getPersistedInventoryHistory(InventoryHistory inventoryHistory) {
        return inventoryHistoryRepository.findById(inventoryHistory.getId()).orElseThrow();
    }

    protected void assertPersistedInventoryHistoryToMatchAllProperties(InventoryHistory expectedInventoryHistory) {
        assertInventoryHistoryAllPropertiesEquals(expectedInventoryHistory, getPersistedInventoryHistory(expectedInventoryHistory));
    }

    protected void assertPersistedInventoryHistoryToMatchUpdatableProperties(InventoryHistory expectedInventoryHistory) {
        assertInventoryHistoryAllUpdatablePropertiesEquals(
            expectedInventoryHistory,
            getPersistedInventoryHistory(expectedInventoryHistory)
        );
    }
}
