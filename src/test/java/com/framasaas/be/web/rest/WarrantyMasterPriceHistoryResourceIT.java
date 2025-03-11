package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.WarrantyMasterPriceHistoryAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.WarrantyMasterPriceHistory;
import com.framasaas.be.repository.WarrantyMasterPriceHistoryRepository;
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
 * Integration tests for the {@link WarrantyMasterPriceHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WarrantyMasterPriceHistoryResourceIT {

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/warranty-master-price-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WarrantyMasterPriceHistoryRepository warrantyMasterPriceHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWarrantyMasterPriceHistoryMockMvc;

    private WarrantyMasterPriceHistory warrantyMasterPriceHistory;

    private WarrantyMasterPriceHistory insertedWarrantyMasterPriceHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarrantyMasterPriceHistory createEntity() {
        return new WarrantyMasterPriceHistory().price(DEFAULT_PRICE).updatedBy(DEFAULT_UPDATED_BY).updatedTime(DEFAULT_UPDATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarrantyMasterPriceHistory createUpdatedEntity() {
        return new WarrantyMasterPriceHistory().price(UPDATED_PRICE).updatedBy(UPDATED_UPDATED_BY).updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        warrantyMasterPriceHistory = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedWarrantyMasterPriceHistory != null) {
            warrantyMasterPriceHistoryRepository.delete(insertedWarrantyMasterPriceHistory);
            insertedWarrantyMasterPriceHistory = null;
        }
    }

    @Test
    @Transactional
    void createWarrantyMasterPriceHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WarrantyMasterPriceHistory
        var returnedWarrantyMasterPriceHistory = om.readValue(
            restWarrantyMasterPriceHistoryMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterPriceHistory))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WarrantyMasterPriceHistory.class
        );

        // Validate the WarrantyMasterPriceHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWarrantyMasterPriceHistoryUpdatableFieldsEquals(
            returnedWarrantyMasterPriceHistory,
            getPersistedWarrantyMasterPriceHistory(returnedWarrantyMasterPriceHistory)
        );

        insertedWarrantyMasterPriceHistory = returnedWarrantyMasterPriceHistory;
    }

    @Test
    @Transactional
    void createWarrantyMasterPriceHistoryWithExistingId() throws Exception {
        // Create the WarrantyMasterPriceHistory with an existing ID
        warrantyMasterPriceHistory.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarrantyMasterPriceHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterPriceHistory)))
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMasterPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMasterPriceHistory.setPrice(null);

        // Create the WarrantyMasterPriceHistory, which fails.

        restWarrantyMasterPriceHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterPriceHistory)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMasterPriceHistory.setUpdatedBy(null);

        // Create the WarrantyMasterPriceHistory, which fails.

        restWarrantyMasterPriceHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterPriceHistory)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMasterPriceHistory.setUpdatedTime(null);

        // Create the WarrantyMasterPriceHistory, which fails.

        restWarrantyMasterPriceHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterPriceHistory)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistories() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList
        restWarrantyMasterPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warrantyMasterPriceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getWarrantyMasterPriceHistory() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get the warrantyMasterPriceHistory
        restWarrantyMasterPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, warrantyMasterPriceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(warrantyMasterPriceHistory.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWarrantyMasterPriceHistory() throws Exception {
        // Get the warrantyMasterPriceHistory
        restWarrantyMasterPriceHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWarrantyMasterPriceHistory() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warrantyMasterPriceHistory
        WarrantyMasterPriceHistory updatedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository
            .findById(warrantyMasterPriceHistory.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedWarrantyMasterPriceHistory are not directly saved in db
        em.detach(updatedWarrantyMasterPriceHistory);
        updatedWarrantyMasterPriceHistory.price(UPDATED_PRICE).updatedBy(UPDATED_UPDATED_BY).updatedTime(UPDATED_UPDATED_TIME);

        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWarrantyMasterPriceHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWarrantyMasterPriceHistory))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyMasterPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWarrantyMasterPriceHistoryToMatchAllProperties(updatedWarrantyMasterPriceHistory);
    }

    @Test
    @Transactional
    void putNonExistingWarrantyMasterPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMasterPriceHistory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warrantyMasterPriceHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warrantyMasterPriceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMasterPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWarrantyMasterPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMasterPriceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warrantyMasterPriceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMasterPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWarrantyMasterPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMasterPriceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterPriceHistory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WarrantyMasterPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWarrantyMasterPriceHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warrantyMasterPriceHistory using partial update
        WarrantyMasterPriceHistory partialUpdatedWarrantyMasterPriceHistory = new WarrantyMasterPriceHistory();
        partialUpdatedWarrantyMasterPriceHistory.setId(warrantyMasterPriceHistory.getId());

        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarrantyMasterPriceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWarrantyMasterPriceHistory))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyMasterPriceHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWarrantyMasterPriceHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWarrantyMasterPriceHistory, warrantyMasterPriceHistory),
            getPersistedWarrantyMasterPriceHistory(warrantyMasterPriceHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateWarrantyMasterPriceHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warrantyMasterPriceHistory using partial update
        WarrantyMasterPriceHistory partialUpdatedWarrantyMasterPriceHistory = new WarrantyMasterPriceHistory();
        partialUpdatedWarrantyMasterPriceHistory.setId(warrantyMasterPriceHistory.getId());

        partialUpdatedWarrantyMasterPriceHistory.price(UPDATED_PRICE).updatedBy(UPDATED_UPDATED_BY).updatedTime(UPDATED_UPDATED_TIME);

        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarrantyMasterPriceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWarrantyMasterPriceHistory))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyMasterPriceHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWarrantyMasterPriceHistoryUpdatableFieldsEquals(
            partialUpdatedWarrantyMasterPriceHistory,
            getPersistedWarrantyMasterPriceHistory(partialUpdatedWarrantyMasterPriceHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWarrantyMasterPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMasterPriceHistory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, warrantyMasterPriceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warrantyMasterPriceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMasterPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWarrantyMasterPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMasterPriceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warrantyMasterPriceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMasterPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWarrantyMasterPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMasterPriceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(warrantyMasterPriceHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WarrantyMasterPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWarrantyMasterPriceHistory() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the warrantyMasterPriceHistory
        restWarrantyMasterPriceHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, warrantyMasterPriceHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return warrantyMasterPriceHistoryRepository.count();
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

    protected WarrantyMasterPriceHistory getPersistedWarrantyMasterPriceHistory(WarrantyMasterPriceHistory warrantyMasterPriceHistory) {
        return warrantyMasterPriceHistoryRepository.findById(warrantyMasterPriceHistory.getId()).orElseThrow();
    }

    protected void assertPersistedWarrantyMasterPriceHistoryToMatchAllProperties(
        WarrantyMasterPriceHistory expectedWarrantyMasterPriceHistory
    ) {
        assertWarrantyMasterPriceHistoryAllPropertiesEquals(
            expectedWarrantyMasterPriceHistory,
            getPersistedWarrantyMasterPriceHistory(expectedWarrantyMasterPriceHistory)
        );
    }

    protected void assertPersistedWarrantyMasterPriceHistoryToMatchUpdatableProperties(
        WarrantyMasterPriceHistory expectedWarrantyMasterPriceHistory
    ) {
        assertWarrantyMasterPriceHistoryAllUpdatablePropertiesEquals(
            expectedWarrantyMasterPriceHistory,
            getPersistedWarrantyMasterPriceHistory(expectedWarrantyMasterPriceHistory)
        );
    }
}
