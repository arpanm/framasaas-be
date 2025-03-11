package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FranchiseStatusHistoryAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FranchiseStatusHistory;
import com.framasaas.be.domain.enumeration.FranchiseStatus;
import com.framasaas.be.repository.FranchiseStatusHistoryRepository;
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
 * Integration tests for the {@link FranchiseStatusHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseStatusHistoryResourceIT {

    private static final FranchiseStatus DEFAULT_FRANCHISE_SATUS = FranchiseStatus.PendingApproval;
    private static final FranchiseStatus UPDATED_FRANCHISE_SATUS = FranchiseStatus.Active;

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchise-status-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseStatusHistoryRepository franchiseStatusHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseStatusHistoryMockMvc;

    private FranchiseStatusHistory franchiseStatusHistory;

    private FranchiseStatusHistory insertedFranchiseStatusHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseStatusHistory createEntity() {
        return new FranchiseStatusHistory()
            .franchiseSatus(DEFAULT_FRANCHISE_SATUS)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseStatusHistory createUpdatedEntity() {
        return new FranchiseStatusHistory()
            .franchiseSatus(UPDATED_FRANCHISE_SATUS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        franchiseStatusHistory = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFranchiseStatusHistory != null) {
            franchiseStatusHistoryRepository.delete(insertedFranchiseStatusHistory);
            insertedFranchiseStatusHistory = null;
        }
    }

    @Test
    @Transactional
    void createFranchiseStatusHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchiseStatusHistory
        var returnedFranchiseStatusHistory = om.readValue(
            restFranchiseStatusHistoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseStatusHistory)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseStatusHistory.class
        );

        // Validate the FranchiseStatusHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFranchiseStatusHistoryUpdatableFieldsEquals(
            returnedFranchiseStatusHistory,
            getPersistedFranchiseStatusHistory(returnedFranchiseStatusHistory)
        );

        insertedFranchiseStatusHistory = returnedFranchiseStatusHistory;
    }

    @Test
    @Transactional
    void createFranchiseStatusHistoryWithExistingId() throws Exception {
        // Create the FranchiseStatusHistory with an existing ID
        franchiseStatusHistory.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseStatusHistory)))
            .andExpect(status().isBadRequest());

        // Validate the FranchiseStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFranchiseSatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseStatusHistory.setFranchiseSatus(null);

        // Create the FranchiseStatusHistory, which fails.

        restFranchiseStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseStatusHistory)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseStatusHistory.setUpdatedBy(null);

        // Create the FranchiseStatusHistory, which fails.

        restFranchiseStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseStatusHistory)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseStatusHistory.setUpdatedTime(null);

        // Create the FranchiseStatusHistory, which fails.

        restFranchiseStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseStatusHistory)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchiseStatusHistories() throws Exception {
        // Initialize the database
        insertedFranchiseStatusHistory = franchiseStatusHistoryRepository.saveAndFlush(franchiseStatusHistory);

        // Get all the franchiseStatusHistoryList
        restFranchiseStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].franchiseSatus").value(hasItem(DEFAULT_FRANCHISE_SATUS.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchiseStatusHistory() throws Exception {
        // Initialize the database
        insertedFranchiseStatusHistory = franchiseStatusHistoryRepository.saveAndFlush(franchiseStatusHistory);

        // Get the franchiseStatusHistory
        restFranchiseStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, franchiseStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchiseStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.franchiseSatus").value(DEFAULT_FRANCHISE_SATUS.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFranchiseStatusHistory() throws Exception {
        // Get the franchiseStatusHistory
        restFranchiseStatusHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchiseStatusHistory() throws Exception {
        // Initialize the database
        insertedFranchiseStatusHistory = franchiseStatusHistoryRepository.saveAndFlush(franchiseStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseStatusHistory
        FranchiseStatusHistory updatedFranchiseStatusHistory = franchiseStatusHistoryRepository
            .findById(franchiseStatusHistory.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFranchiseStatusHistory are not directly saved in db
        em.detach(updatedFranchiseStatusHistory);
        updatedFranchiseStatusHistory
            .franchiseSatus(UPDATED_FRANCHISE_SATUS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFranchiseStatusHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFranchiseStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseStatusHistoryToMatchAllProperties(updatedFranchiseStatusHistory);
    }

    @Test
    @Transactional
    void putNonExistingFranchiseStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseStatusHistory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseStatusHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseStatusHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchiseStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseStatusHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseStatusHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchiseStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseStatusHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseStatusHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseStatusHistory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseStatusHistory = franchiseStatusHistoryRepository.saveAndFlush(franchiseStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseStatusHistory using partial update
        FranchiseStatusHistory partialUpdatedFranchiseStatusHistory = new FranchiseStatusHistory();
        partialUpdatedFranchiseStatusHistory.setId(franchiseStatusHistory.getId());

        partialUpdatedFranchiseStatusHistory.updatedBy(UPDATED_UPDATED_BY);

        restFranchiseStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseStatusHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseStatusHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchiseStatusHistory, franchiseStatusHistory),
            getPersistedFranchiseStatusHistory(franchiseStatusHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseStatusHistory = franchiseStatusHistoryRepository.saveAndFlush(franchiseStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseStatusHistory using partial update
        FranchiseStatusHistory partialUpdatedFranchiseStatusHistory = new FranchiseStatusHistory();
        partialUpdatedFranchiseStatusHistory.setId(franchiseStatusHistory.getId());

        partialUpdatedFranchiseStatusHistory
            .franchiseSatus(UPDATED_FRANCHISE_SATUS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseStatusHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseStatusHistoryUpdatableFieldsEquals(
            partialUpdatedFranchiseStatusHistory,
            getPersistedFranchiseStatusHistory(partialUpdatedFranchiseStatusHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFranchiseStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseStatusHistory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseStatusHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchiseStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseStatusHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseStatusHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchiseStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseStatusHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseStatusHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchiseStatusHistory() throws Exception {
        // Initialize the database
        insertedFranchiseStatusHistory = franchiseStatusHistoryRepository.saveAndFlush(franchiseStatusHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchiseStatusHistory
        restFranchiseStatusHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchiseStatusHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseStatusHistoryRepository.count();
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

    protected FranchiseStatusHistory getPersistedFranchiseStatusHistory(FranchiseStatusHistory franchiseStatusHistory) {
        return franchiseStatusHistoryRepository.findById(franchiseStatusHistory.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseStatusHistoryToMatchAllProperties(FranchiseStatusHistory expectedFranchiseStatusHistory) {
        assertFranchiseStatusHistoryAllPropertiesEquals(
            expectedFranchiseStatusHistory,
            getPersistedFranchiseStatusHistory(expectedFranchiseStatusHistory)
        );
    }

    protected void assertPersistedFranchiseStatusHistoryToMatchUpdatableProperties(FranchiseStatusHistory expectedFranchiseStatusHistory) {
        assertFranchiseStatusHistoryAllUpdatablePropertiesEquals(
            expectedFranchiseStatusHistory,
            getPersistedFranchiseStatusHistory(expectedFranchiseStatusHistory)
        );
    }
}
