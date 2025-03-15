package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FranchisePerformanceHistoryAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FranchisePerformanceHistory;
import com.framasaas.be.domain.enumeration.PerformanceTag;
import com.framasaas.be.repository.FranchisePerformanceHistoryRepository;
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
 * Integration tests for the {@link FranchisePerformanceHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchisePerformanceHistoryResourceIT {

    private static final Float DEFAULT_PERFORMANCE_SCORE = 1F;
    private static final Float UPDATED_PERFORMANCE_SCORE = 2F;

    private static final PerformanceTag DEFAULT_PERFORMANCE_TAG = PerformanceTag.High;
    private static final PerformanceTag UPDATED_PERFORMANCE_TAG = PerformanceTag.Medium;

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchise-performance-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchisePerformanceHistoryMockMvc;

    private FranchisePerformanceHistory franchisePerformanceHistory;

    private FranchisePerformanceHistory insertedFranchisePerformanceHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchisePerformanceHistory createEntity() {
        return new FranchisePerformanceHistory()
            .performanceScore(DEFAULT_PERFORMANCE_SCORE)
            .performanceTag(DEFAULT_PERFORMANCE_TAG)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME)
            .createddBy(DEFAULT_CREATEDD_BY)
            .createdTime(DEFAULT_CREATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchisePerformanceHistory createUpdatedEntity() {
        return new FranchisePerformanceHistory()
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        franchisePerformanceHistory = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFranchisePerformanceHistory != null) {
            franchisePerformanceHistoryRepository.delete(insertedFranchisePerformanceHistory);
            insertedFranchisePerformanceHistory = null;
        }
    }

    @Test
    @Transactional
    void createFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchisePerformanceHistory
        var returnedFranchisePerformanceHistory = om.readValue(
            restFranchisePerformanceHistoryMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistory))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchisePerformanceHistory.class
        );

        // Validate the FranchisePerformanceHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFranchisePerformanceHistoryUpdatableFieldsEquals(
            returnedFranchisePerformanceHistory,
            getPersistedFranchisePerformanceHistory(returnedFranchisePerformanceHistory)
        );

        insertedFranchisePerformanceHistory = returnedFranchisePerformanceHistory;
    }

    @Test
    @Transactional
    void createFranchisePerformanceHistoryWithExistingId() throws Exception {
        // Create the FranchisePerformanceHistory with an existing ID
        franchisePerformanceHistory.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchisePerformanceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchisePerformanceHistory.setUpdatedBy(null);

        // Create the FranchisePerformanceHistory, which fails.

        restFranchisePerformanceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistory))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchisePerformanceHistory.setUpdatedTime(null);

        // Create the FranchisePerformanceHistory, which fails.

        restFranchisePerformanceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistory))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchisePerformanceHistory.setCreateddBy(null);

        // Create the FranchisePerformanceHistory, which fails.

        restFranchisePerformanceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistory))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchisePerformanceHistory.setCreatedTime(null);

        // Create the FranchisePerformanceHistory, which fails.

        restFranchisePerformanceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistory))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistories() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList
        restFranchisePerformanceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchisePerformanceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].performanceScore").value(hasItem(DEFAULT_PERFORMANCE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].performanceTag").value(hasItem(DEFAULT_PERFORMANCE_TAG.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchisePerformanceHistory() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get the franchisePerformanceHistory
        restFranchisePerformanceHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, franchisePerformanceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchisePerformanceHistory.getId().intValue()))
            .andExpect(jsonPath("$.performanceScore").value(DEFAULT_PERFORMANCE_SCORE.doubleValue()))
            .andExpect(jsonPath("$.performanceTag").value(DEFAULT_PERFORMANCE_TAG.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFranchisePerformanceHistory() throws Exception {
        // Get the franchisePerformanceHistory
        restFranchisePerformanceHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchisePerformanceHistory() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchisePerformanceHistory
        FranchisePerformanceHistory updatedFranchisePerformanceHistory = franchisePerformanceHistoryRepository
            .findById(franchisePerformanceHistory.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFranchisePerformanceHistory are not directly saved in db
        em.detach(updatedFranchisePerformanceHistory);
        updatedFranchisePerformanceHistory
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME);

        restFranchisePerformanceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFranchisePerformanceHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFranchisePerformanceHistory))
            )
            .andExpect(status().isOk());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchisePerformanceHistoryToMatchAllProperties(updatedFranchisePerformanceHistory);
    }

    @Test
    @Transactional
    void putNonExistingFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchisePerformanceHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchisePerformanceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchisePerformanceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchisePerformanceHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchisePerformanceHistory using partial update
        FranchisePerformanceHistory partialUpdatedFranchisePerformanceHistory = new FranchisePerformanceHistory();
        partialUpdatedFranchisePerformanceHistory.setId(franchisePerformanceHistory.getId());

        partialUpdatedFranchisePerformanceHistory.updatedTime(UPDATED_UPDATED_TIME).createddBy(UPDATED_CREATEDD_BY);

        restFranchisePerformanceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchisePerformanceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchisePerformanceHistory))
            )
            .andExpect(status().isOk());

        // Validate the FranchisePerformanceHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchisePerformanceHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchisePerformanceHistory, franchisePerformanceHistory),
            getPersistedFranchisePerformanceHistory(franchisePerformanceHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchisePerformanceHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchisePerformanceHistory using partial update
        FranchisePerformanceHistory partialUpdatedFranchisePerformanceHistory = new FranchisePerformanceHistory();
        partialUpdatedFranchisePerformanceHistory.setId(franchisePerformanceHistory.getId());

        partialUpdatedFranchisePerformanceHistory
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME);

        restFranchisePerformanceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchisePerformanceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchisePerformanceHistory))
            )
            .andExpect(status().isOk());

        // Validate the FranchisePerformanceHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchisePerformanceHistoryUpdatableFieldsEquals(
            partialUpdatedFranchisePerformanceHistory,
            getPersistedFranchisePerformanceHistory(partialUpdatedFranchisePerformanceHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchisePerformanceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchisePerformanceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchisePerformanceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchisePerformanceHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchisePerformanceHistory() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchisePerformanceHistory
        restFranchisePerformanceHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchisePerformanceHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchisePerformanceHistoryRepository.count();
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

    protected FranchisePerformanceHistory getPersistedFranchisePerformanceHistory(FranchisePerformanceHistory franchisePerformanceHistory) {
        return franchisePerformanceHistoryRepository.findById(franchisePerformanceHistory.getId()).orElseThrow();
    }

    protected void assertPersistedFranchisePerformanceHistoryToMatchAllProperties(
        FranchisePerformanceHistory expectedFranchisePerformanceHistory
    ) {
        assertFranchisePerformanceHistoryAllPropertiesEquals(
            expectedFranchisePerformanceHistory,
            getPersistedFranchisePerformanceHistory(expectedFranchisePerformanceHistory)
        );
    }

    protected void assertPersistedFranchisePerformanceHistoryToMatchUpdatableProperties(
        FranchisePerformanceHistory expectedFranchisePerformanceHistory
    ) {
        assertFranchisePerformanceHistoryAllUpdatablePropertiesEquals(
            expectedFranchisePerformanceHistory,
            getPersistedFranchisePerformanceHistory(expectedFranchisePerformanceHistory)
        );
    }
}
