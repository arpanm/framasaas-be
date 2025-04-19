package com.framasaas.web.rest;

import static com.framasaas.domain.WarrantyMasterPriceHistoryAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.WarrantyMaster;
import com.framasaas.domain.WarrantyMasterPriceHistory;
import com.framasaas.repository.WarrantyMasterPriceHistoryRepository;
import com.framasaas.service.dto.WarrantyMasterPriceHistoryDTO;
import com.framasaas.service.mapper.WarrantyMasterPriceHistoryMapper;
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
    private WarrantyMasterPriceHistoryMapper warrantyMasterPriceHistoryMapper;

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
        return new WarrantyMasterPriceHistory()
            .price(DEFAULT_PRICE)
            .tax(DEFAULT_TAX)
            .franchiseCommission(DEFAULT_FRANCHISE_COMMISSION)
            .franchiseTax(DEFAULT_FRANCHISE_TAX)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarrantyMasterPriceHistory createUpdatedEntity() {
        return new WarrantyMasterPriceHistory()
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .franchiseTax(UPDATED_FRANCHISE_TAX)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        warrantyMasterPriceHistory = createEntity();
    }

    @AfterEach
    void cleanup() {
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
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);
        var returnedWarrantyMasterPriceHistoryDTO = om.readValue(
            restWarrantyMasterPriceHistoryMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WarrantyMasterPriceHistoryDTO.class
        );

        // Validate the WarrantyMasterPriceHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryMapper.toEntity(returnedWarrantyMasterPriceHistoryDTO);
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
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMasterPriceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMasterPriceHistory.setUpdatedBy(null);

        // Create the WarrantyMasterPriceHistory, which fails.
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);

        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
            )
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
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);

        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
            )
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
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommission").value(hasItem(DEFAULT_FRANCHISE_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseTax").value(hasItem(DEFAULT_FRANCHISE_TAX.doubleValue())))
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
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.franchiseCommission").value(DEFAULT_FRANCHISE_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.franchiseTax").value(DEFAULT_FRANCHISE_TAX.doubleValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getWarrantyMasterPriceHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        Long id = warrantyMasterPriceHistory.getId();

        defaultWarrantyMasterPriceHistoryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultWarrantyMasterPriceHistoryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultWarrantyMasterPriceHistoryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where price equals to
        defaultWarrantyMasterPriceHistoryFiltering("price.equals=" + DEFAULT_PRICE, "price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where price in
        defaultWarrantyMasterPriceHistoryFiltering("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE, "price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where price is not null
        defaultWarrantyMasterPriceHistoryFiltering("price.specified=true", "price.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where price is greater than or equal to
        defaultWarrantyMasterPriceHistoryFiltering(
            "price.greaterThanOrEqual=" + DEFAULT_PRICE,
            "price.greaterThanOrEqual=" + UPDATED_PRICE
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where price is less than or equal to
        defaultWarrantyMasterPriceHistoryFiltering("price.lessThanOrEqual=" + DEFAULT_PRICE, "price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where price is less than
        defaultWarrantyMasterPriceHistoryFiltering("price.lessThan=" + UPDATED_PRICE, "price.lessThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where price is greater than
        defaultWarrantyMasterPriceHistoryFiltering("price.greaterThan=" + SMALLER_PRICE, "price.greaterThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where tax equals to
        defaultWarrantyMasterPriceHistoryFiltering("tax.equals=" + DEFAULT_TAX, "tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where tax in
        defaultWarrantyMasterPriceHistoryFiltering("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX, "tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where tax is not null
        defaultWarrantyMasterPriceHistoryFiltering("tax.specified=true", "tax.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where tax is greater than or equal to
        defaultWarrantyMasterPriceHistoryFiltering("tax.greaterThanOrEqual=" + DEFAULT_TAX, "tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where tax is less than or equal to
        defaultWarrantyMasterPriceHistoryFiltering("tax.lessThanOrEqual=" + DEFAULT_TAX, "tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where tax is less than
        defaultWarrantyMasterPriceHistoryFiltering("tax.lessThan=" + UPDATED_TAX, "tax.lessThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where tax is greater than
        defaultWarrantyMasterPriceHistoryFiltering("tax.greaterThan=" + SMALLER_TAX, "tax.greaterThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseCommission equals to
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseCommission.equals=" + DEFAULT_FRANCHISE_COMMISSION,
            "franchiseCommission.equals=" + UPDATED_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseCommission in
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseCommission.in=" + DEFAULT_FRANCHISE_COMMISSION + "," + UPDATED_FRANCHISE_COMMISSION,
            "franchiseCommission.in=" + UPDATED_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseCommission is not null
        defaultWarrantyMasterPriceHistoryFiltering("franchiseCommission.specified=true", "franchiseCommission.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseCommissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseCommission is greater than or equal to
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseCommission.greaterThanOrEqual=" + DEFAULT_FRANCHISE_COMMISSION,
            "franchiseCommission.greaterThanOrEqual=" + UPDATED_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseCommissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseCommission is less than or equal to
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseCommission.lessThanOrEqual=" + DEFAULT_FRANCHISE_COMMISSION,
            "franchiseCommission.lessThanOrEqual=" + SMALLER_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseCommissionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseCommission is less than
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseCommission.lessThan=" + UPDATED_FRANCHISE_COMMISSION,
            "franchiseCommission.lessThan=" + DEFAULT_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseCommissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseCommission is greater than
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseCommission.greaterThan=" + SMALLER_FRANCHISE_COMMISSION,
            "franchiseCommission.greaterThan=" + DEFAULT_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseTax equals to
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseTax.equals=" + DEFAULT_FRANCHISE_TAX,
            "franchiseTax.equals=" + UPDATED_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseTax in
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseTax.in=" + DEFAULT_FRANCHISE_TAX + "," + UPDATED_FRANCHISE_TAX,
            "franchiseTax.in=" + UPDATED_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseTax is not null
        defaultWarrantyMasterPriceHistoryFiltering("franchiseTax.specified=true", "franchiseTax.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseTax is greater than or equal to
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseTax.greaterThanOrEqual=" + DEFAULT_FRANCHISE_TAX,
            "franchiseTax.greaterThanOrEqual=" + UPDATED_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseTax is less than or equal to
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseTax.lessThanOrEqual=" + DEFAULT_FRANCHISE_TAX,
            "franchiseTax.lessThanOrEqual=" + SMALLER_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseTax is less than
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseTax.lessThan=" + UPDATED_FRANCHISE_TAX,
            "franchiseTax.lessThan=" + DEFAULT_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByFranchiseTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where franchiseTax is greater than
        defaultWarrantyMasterPriceHistoryFiltering(
            "franchiseTax.greaterThan=" + SMALLER_FRANCHISE_TAX,
            "franchiseTax.greaterThan=" + DEFAULT_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where updatedBy equals to
        defaultWarrantyMasterPriceHistoryFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where updatedBy in
        defaultWarrantyMasterPriceHistoryFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where updatedBy is not null
        defaultWarrantyMasterPriceHistoryFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where updatedBy contains
        defaultWarrantyMasterPriceHistoryFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where updatedBy does not contain
        defaultWarrantyMasterPriceHistoryFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where updatedTime equals to
        defaultWarrantyMasterPriceHistoryFiltering(
            "updatedTime.equals=" + DEFAULT_UPDATED_TIME,
            "updatedTime.equals=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where updatedTime in
        defaultWarrantyMasterPriceHistoryFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);

        // Get all the warrantyMasterPriceHistoryList where updatedTime is not null
        defaultWarrantyMasterPriceHistoryFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMasterPriceHistoriesByWarrantyMasterIsEqualToSomething() throws Exception {
        WarrantyMaster warrantyMaster;
        if (TestUtil.findAll(em, WarrantyMaster.class).isEmpty()) {
            warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);
            warrantyMaster = WarrantyMasterResourceIT.createEntity();
        } else {
            warrantyMaster = TestUtil.findAll(em, WarrantyMaster.class).get(0);
        }
        em.persist(warrantyMaster);
        em.flush();
        warrantyMasterPriceHistory.setWarrantyMaster(warrantyMaster);
        warrantyMasterPriceHistoryRepository.saveAndFlush(warrantyMasterPriceHistory);
        Long warrantyMasterId = warrantyMaster.getId();
        // Get all the warrantyMasterPriceHistoryList where warrantyMaster equals to warrantyMasterId
        defaultWarrantyMasterPriceHistoryShouldBeFound("warrantyMasterId.equals=" + warrantyMasterId);

        // Get all the warrantyMasterPriceHistoryList where warrantyMaster equals to (warrantyMasterId + 1)
        defaultWarrantyMasterPriceHistoryShouldNotBeFound("warrantyMasterId.equals=" + (warrantyMasterId + 1));
    }

    private void defaultWarrantyMasterPriceHistoryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultWarrantyMasterPriceHistoryShouldBeFound(shouldBeFound);
        defaultWarrantyMasterPriceHistoryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWarrantyMasterPriceHistoryShouldBeFound(String filter) throws Exception {
        restWarrantyMasterPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warrantyMasterPriceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommission").value(hasItem(DEFAULT_FRANCHISE_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseTax").value(hasItem(DEFAULT_FRANCHISE_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restWarrantyMasterPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWarrantyMasterPriceHistoryShouldNotBeFound(String filter) throws Exception {
        restWarrantyMasterPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWarrantyMasterPriceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        updatedWarrantyMasterPriceHistory
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .franchiseTax(UPDATED_FRANCHISE_TAX)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(
            updatedWarrantyMasterPriceHistory
        );

        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warrantyMasterPriceHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
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

        // Create the WarrantyMasterPriceHistory
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warrantyMasterPriceHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
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

        // Create the WarrantyMasterPriceHistory
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
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

        // Create the WarrantyMasterPriceHistory
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
            )
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

        partialUpdatedWarrantyMasterPriceHistory
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .franchiseTax(UPDATED_FRANCHISE_TAX)
            .updatedBy(UPDATED_UPDATED_BY);

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

        partialUpdatedWarrantyMasterPriceHistory
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .franchiseTax(UPDATED_FRANCHISE_TAX)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

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

        // Create the WarrantyMasterPriceHistory
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, warrantyMasterPriceHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
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

        // Create the WarrantyMasterPriceHistory
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
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

        // Create the WarrantyMasterPriceHistory
        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterPriceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warrantyMasterPriceHistoryDTO))
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
