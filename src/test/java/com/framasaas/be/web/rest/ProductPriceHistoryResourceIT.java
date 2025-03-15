package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.ProductPriceHistoryAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.ProductPriceHistory;
import com.framasaas.be.repository.ProductPriceHistoryRepository;
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
 * Integration tests for the {@link ProductPriceHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductPriceHistoryResourceIT {

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;

    private static final Float DEFAULT_FRANCHISE_COMMISSION = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISSION = 2F;

    private static final Float DEFAULT_FRANCHISE_TAX = 1F;
    private static final Float UPDATED_FRANCHISE_TAX = 2F;

    private static final String DEFAULT_UPDATE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/product-price-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductPriceHistoryRepository productPriceHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductPriceHistoryMockMvc;

    private ProductPriceHistory productPriceHistory;

    private ProductPriceHistory insertedProductPriceHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPriceHistory createEntity() {
        return new ProductPriceHistory()
            .price(DEFAULT_PRICE)
            .tax(DEFAULT_TAX)
            .franchiseCommission(DEFAULT_FRANCHISE_COMMISSION)
            .franchiseTax(DEFAULT_FRANCHISE_TAX)
            .updateDescription(DEFAULT_UPDATE_DESCRIPTION)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPriceHistory createUpdatedEntity() {
        return new ProductPriceHistory()
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .franchiseTax(UPDATED_FRANCHISE_TAX)
            .updateDescription(UPDATED_UPDATE_DESCRIPTION)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        productPriceHistory = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedProductPriceHistory != null) {
            productPriceHistoryRepository.delete(insertedProductPriceHistory);
            insertedProductPriceHistory = null;
        }
    }

    @Test
    @Transactional
    void createProductPriceHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ProductPriceHistory
        var returnedProductPriceHistory = om.readValue(
            restProductPriceHistoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productPriceHistory)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProductPriceHistory.class
        );

        // Validate the ProductPriceHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProductPriceHistoryUpdatableFieldsEquals(
            returnedProductPriceHistory,
            getPersistedProductPriceHistory(returnedProductPriceHistory)
        );

        insertedProductPriceHistory = returnedProductPriceHistory;
    }

    @Test
    @Transactional
    void createProductPriceHistoryWithExistingId() throws Exception {
        // Create the ProductPriceHistory with an existing ID
        productPriceHistory.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPriceHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productPriceHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        productPriceHistory.setUpdatedBy(null);

        // Create the ProductPriceHistory, which fails.

        restProductPriceHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productPriceHistory)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        productPriceHistory.setUpdatedTime(null);

        // Create the ProductPriceHistory, which fails.

        restProductPriceHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productPriceHistory)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductPriceHistories() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList
        restProductPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPriceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommission").value(hasItem(DEFAULT_FRANCHISE_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseTax").value(hasItem(DEFAULT_FRANCHISE_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].updateDescription").value(hasItem(DEFAULT_UPDATE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getProductPriceHistory() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get the productPriceHistory
        restProductPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, productPriceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productPriceHistory.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.franchiseCommission").value(DEFAULT_FRANCHISE_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.franchiseTax").value(DEFAULT_FRANCHISE_TAX.doubleValue()))
            .andExpect(jsonPath("$.updateDescription").value(DEFAULT_UPDATE_DESCRIPTION))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductPriceHistory() throws Exception {
        // Get the productPriceHistory
        restProductPriceHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductPriceHistory() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productPriceHistory
        ProductPriceHistory updatedProductPriceHistory = productPriceHistoryRepository.findById(productPriceHistory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProductPriceHistory are not directly saved in db
        em.detach(updatedProductPriceHistory);
        updatedProductPriceHistory
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .franchiseTax(UPDATED_FRANCHISE_TAX)
            .updateDescription(UPDATED_UPDATE_DESCRIPTION)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restProductPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductPriceHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProductPriceHistory))
            )
            .andExpect(status().isOk());

        // Validate the ProductPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProductPriceHistoryToMatchAllProperties(updatedProductPriceHistory);
    }

    @Test
    @Transactional
    void putNonExistingProductPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productPriceHistory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productPriceHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productPriceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productPriceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productPriceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productPriceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productPriceHistory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductPriceHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productPriceHistory using partial update
        ProductPriceHistory partialUpdatedProductPriceHistory = new ProductPriceHistory();
        partialUpdatedProductPriceHistory.setId(productPriceHistory.getId());

        partialUpdatedProductPriceHistory.tax(UPDATED_TAX).updatedBy(UPDATED_UPDATED_BY).updatedTime(UPDATED_UPDATED_TIME);

        restProductPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductPriceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductPriceHistory))
            )
            .andExpect(status().isOk());

        // Validate the ProductPriceHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductPriceHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProductPriceHistory, productPriceHistory),
            getPersistedProductPriceHistory(productPriceHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateProductPriceHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the productPriceHistory using partial update
        ProductPriceHistory partialUpdatedProductPriceHistory = new ProductPriceHistory();
        partialUpdatedProductPriceHistory.setId(productPriceHistory.getId());

        partialUpdatedProductPriceHistory
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .franchiseTax(UPDATED_FRANCHISE_TAX)
            .updateDescription(UPDATED_UPDATE_DESCRIPTION)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restProductPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductPriceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProductPriceHistory))
            )
            .andExpect(status().isOk());

        // Validate the ProductPriceHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProductPriceHistoryUpdatableFieldsEquals(
            partialUpdatedProductPriceHistory,
            getPersistedProductPriceHistory(partialUpdatedProductPriceHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingProductPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productPriceHistory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productPriceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productPriceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productPriceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productPriceHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductPriceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        productPriceHistory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(productPriceHistory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductPriceHistory() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the productPriceHistory
        restProductPriceHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, productPriceHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return productPriceHistoryRepository.count();
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

    protected ProductPriceHistory getPersistedProductPriceHistory(ProductPriceHistory productPriceHistory) {
        return productPriceHistoryRepository.findById(productPriceHistory.getId()).orElseThrow();
    }

    protected void assertPersistedProductPriceHistoryToMatchAllProperties(ProductPriceHistory expectedProductPriceHistory) {
        assertProductPriceHistoryAllPropertiesEquals(
            expectedProductPriceHistory,
            getPersistedProductPriceHistory(expectedProductPriceHistory)
        );
    }

    protected void assertPersistedProductPriceHistoryToMatchUpdatableProperties(ProductPriceHistory expectedProductPriceHistory) {
        assertProductPriceHistoryAllUpdatablePropertiesEquals(
            expectedProductPriceHistory,
            getPersistedProductPriceHistory(expectedProductPriceHistory)
        );
    }
}
