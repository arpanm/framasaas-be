package com.framasaas.web.rest;

import static com.framasaas.domain.ProductPriceHistoryAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Product;
import com.framasaas.domain.ProductPriceHistory;
import com.framasaas.repository.ProductPriceHistoryRepository;
import com.framasaas.service.dto.ProductPriceHistoryDTO;
import com.framasaas.service.mapper.ProductPriceHistoryMapper;
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
    private static final Float SMALLER_PRICE = 1F - 1F;

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;
    private static final Float SMALLER_TAX = 1F - 1F;

    private static final Float DEFAULT_FRANCHISE_COMMISSION = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISSION = 2F;
    private static final Float SMALLER_FRANCHISE_COMMISSION = 1F - 1F;

    private static final Float DEFAULT_FRANCHISE_TAX = 1F;
    private static final Float UPDATED_FRANCHISE_TAX = 2F;
    private static final Float SMALLER_FRANCHISE_TAX = 1F - 1F;

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
    private ProductPriceHistoryMapper productPriceHistoryMapper;

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
    void initTest() {
        productPriceHistory = createEntity();
    }

    @AfterEach
    void cleanup() {
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
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(productPriceHistory);
        var returnedProductPriceHistoryDTO = om.readValue(
            restProductPriceHistoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productPriceHistoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProductPriceHistoryDTO.class
        );

        // Validate the ProductPriceHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProductPriceHistory = productPriceHistoryMapper.toEntity(returnedProductPriceHistoryDTO);
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
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(productPriceHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPriceHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productPriceHistoryDTO)))
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
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(productPriceHistory);

        restProductPriceHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productPriceHistoryDTO)))
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
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(productPriceHistory);

        restProductPriceHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productPriceHistoryDTO)))
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
    void getProductPriceHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        Long id = productPriceHistory.getId();

        defaultProductPriceHistoryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultProductPriceHistoryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultProductPriceHistoryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where price equals to
        defaultProductPriceHistoryFiltering("price.equals=" + DEFAULT_PRICE, "price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where price in
        defaultProductPriceHistoryFiltering("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE, "price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where price is not null
        defaultProductPriceHistoryFiltering("price.specified=true", "price.specified=false");
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where price is greater than or equal to
        defaultProductPriceHistoryFiltering("price.greaterThanOrEqual=" + DEFAULT_PRICE, "price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where price is less than or equal to
        defaultProductPriceHistoryFiltering("price.lessThanOrEqual=" + DEFAULT_PRICE, "price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where price is less than
        defaultProductPriceHistoryFiltering("price.lessThan=" + UPDATED_PRICE, "price.lessThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where price is greater than
        defaultProductPriceHistoryFiltering("price.greaterThan=" + SMALLER_PRICE, "price.greaterThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where tax equals to
        defaultProductPriceHistoryFiltering("tax.equals=" + DEFAULT_TAX, "tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where tax in
        defaultProductPriceHistoryFiltering("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX, "tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where tax is not null
        defaultProductPriceHistoryFiltering("tax.specified=true", "tax.specified=false");
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where tax is greater than or equal to
        defaultProductPriceHistoryFiltering("tax.greaterThanOrEqual=" + DEFAULT_TAX, "tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where tax is less than or equal to
        defaultProductPriceHistoryFiltering("tax.lessThanOrEqual=" + DEFAULT_TAX, "tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where tax is less than
        defaultProductPriceHistoryFiltering("tax.lessThan=" + UPDATED_TAX, "tax.lessThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where tax is greater than
        defaultProductPriceHistoryFiltering("tax.greaterThan=" + SMALLER_TAX, "tax.greaterThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseCommission equals to
        defaultProductPriceHistoryFiltering(
            "franchiseCommission.equals=" + DEFAULT_FRANCHISE_COMMISSION,
            "franchiseCommission.equals=" + UPDATED_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseCommission in
        defaultProductPriceHistoryFiltering(
            "franchiseCommission.in=" + DEFAULT_FRANCHISE_COMMISSION + "," + UPDATED_FRANCHISE_COMMISSION,
            "franchiseCommission.in=" + UPDATED_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseCommission is not null
        defaultProductPriceHistoryFiltering("franchiseCommission.specified=true", "franchiseCommission.specified=false");
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseCommissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseCommission is greater than or equal to
        defaultProductPriceHistoryFiltering(
            "franchiseCommission.greaterThanOrEqual=" + DEFAULT_FRANCHISE_COMMISSION,
            "franchiseCommission.greaterThanOrEqual=" + UPDATED_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseCommissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseCommission is less than or equal to
        defaultProductPriceHistoryFiltering(
            "franchiseCommission.lessThanOrEqual=" + DEFAULT_FRANCHISE_COMMISSION,
            "franchiseCommission.lessThanOrEqual=" + SMALLER_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseCommissionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseCommission is less than
        defaultProductPriceHistoryFiltering(
            "franchiseCommission.lessThan=" + UPDATED_FRANCHISE_COMMISSION,
            "franchiseCommission.lessThan=" + DEFAULT_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseCommissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseCommission is greater than
        defaultProductPriceHistoryFiltering(
            "franchiseCommission.greaterThan=" + SMALLER_FRANCHISE_COMMISSION,
            "franchiseCommission.greaterThan=" + DEFAULT_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseTax equals to
        defaultProductPriceHistoryFiltering("franchiseTax.equals=" + DEFAULT_FRANCHISE_TAX, "franchiseTax.equals=" + UPDATED_FRANCHISE_TAX);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseTax in
        defaultProductPriceHistoryFiltering(
            "franchiseTax.in=" + DEFAULT_FRANCHISE_TAX + "," + UPDATED_FRANCHISE_TAX,
            "franchiseTax.in=" + UPDATED_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseTax is not null
        defaultProductPriceHistoryFiltering("franchiseTax.specified=true", "franchiseTax.specified=false");
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseTax is greater than or equal to
        defaultProductPriceHistoryFiltering(
            "franchiseTax.greaterThanOrEqual=" + DEFAULT_FRANCHISE_TAX,
            "franchiseTax.greaterThanOrEqual=" + UPDATED_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseTax is less than or equal to
        defaultProductPriceHistoryFiltering(
            "franchiseTax.lessThanOrEqual=" + DEFAULT_FRANCHISE_TAX,
            "franchiseTax.lessThanOrEqual=" + SMALLER_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseTax is less than
        defaultProductPriceHistoryFiltering(
            "franchiseTax.lessThan=" + UPDATED_FRANCHISE_TAX,
            "franchiseTax.lessThan=" + DEFAULT_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByFranchiseTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where franchiseTax is greater than
        defaultProductPriceHistoryFiltering(
            "franchiseTax.greaterThan=" + SMALLER_FRANCHISE_TAX,
            "franchiseTax.greaterThan=" + DEFAULT_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdateDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updateDescription equals to
        defaultProductPriceHistoryFiltering(
            "updateDescription.equals=" + DEFAULT_UPDATE_DESCRIPTION,
            "updateDescription.equals=" + UPDATED_UPDATE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdateDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updateDescription in
        defaultProductPriceHistoryFiltering(
            "updateDescription.in=" + DEFAULT_UPDATE_DESCRIPTION + "," + UPDATED_UPDATE_DESCRIPTION,
            "updateDescription.in=" + UPDATED_UPDATE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdateDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updateDescription is not null
        defaultProductPriceHistoryFiltering("updateDescription.specified=true", "updateDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdateDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updateDescription contains
        defaultProductPriceHistoryFiltering(
            "updateDescription.contains=" + DEFAULT_UPDATE_DESCRIPTION,
            "updateDescription.contains=" + UPDATED_UPDATE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdateDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updateDescription does not contain
        defaultProductPriceHistoryFiltering(
            "updateDescription.doesNotContain=" + UPDATED_UPDATE_DESCRIPTION,
            "updateDescription.doesNotContain=" + DEFAULT_UPDATE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updatedBy equals to
        defaultProductPriceHistoryFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updatedBy in
        defaultProductPriceHistoryFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updatedBy is not null
        defaultProductPriceHistoryFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updatedBy contains
        defaultProductPriceHistoryFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updatedBy does not contain
        defaultProductPriceHistoryFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updatedTime equals to
        defaultProductPriceHistoryFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updatedTime in
        defaultProductPriceHistoryFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProductPriceHistory = productPriceHistoryRepository.saveAndFlush(productPriceHistory);

        // Get all the productPriceHistoryList where updatedTime is not null
        defaultProductPriceHistoryFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllProductPriceHistoriesByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            productPriceHistoryRepository.saveAndFlush(productPriceHistory);
            product = ProductResourceIT.createEntity();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        productPriceHistory.setProduct(product);
        productPriceHistoryRepository.saveAndFlush(productPriceHistory);
        Long productId = product.getId();
        // Get all the productPriceHistoryList where product equals to productId
        defaultProductPriceHistoryShouldBeFound("productId.equals=" + productId);

        // Get all the productPriceHistoryList where product equals to (productId + 1)
        defaultProductPriceHistoryShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    private void defaultProductPriceHistoryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProductPriceHistoryShouldBeFound(shouldBeFound);
        defaultProductPriceHistoryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductPriceHistoryShouldBeFound(String filter) throws Exception {
        restProductPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restProductPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductPriceHistoryShouldNotBeFound(String filter) throws Exception {
        restProductPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(updatedProductPriceHistory);

        restProductPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productPriceHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productPriceHistoryDTO))
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

        // Create the ProductPriceHistory
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(productPriceHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productPriceHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productPriceHistoryDTO))
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

        // Create the ProductPriceHistory
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(productPriceHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(productPriceHistoryDTO))
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

        // Create the ProductPriceHistory
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(productPriceHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(productPriceHistoryDTO)))
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

        // Create the ProductPriceHistory
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(productPriceHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productPriceHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productPriceHistoryDTO))
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

        // Create the ProductPriceHistory
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(productPriceHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(productPriceHistoryDTO))
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

        // Create the ProductPriceHistory
        ProductPriceHistoryDTO productPriceHistoryDTO = productPriceHistoryMapper.toDto(productPriceHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(productPriceHistoryDTO))
            )
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
