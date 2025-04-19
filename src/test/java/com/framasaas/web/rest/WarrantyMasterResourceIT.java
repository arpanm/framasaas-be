package com.framasaas.web.rest;

import static com.framasaas.domain.WarrantyMasterAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Product;
import com.framasaas.domain.WarrantyMaster;
import com.framasaas.domain.enumeration.WarrantyType;
import com.framasaas.repository.WarrantyMasterRepository;
import com.framasaas.service.WarrantyMasterService;
import com.framasaas.service.dto.WarrantyMasterDTO;
import com.framasaas.service.mapper.WarrantyMasterMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WarrantyMasterResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WarrantyMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_WARRANTY_MASTER_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_WARRANTY_MASTER_ID = "BBBBBBBBBB";

    private static final WarrantyType DEFAULT_WARRANTY_TYPE = WarrantyType.BRANDFREEWARRANTY;
    private static final WarrantyType UPDATED_WARRANTY_TYPE = WarrantyType.EXTENDEDPAIDWARRANTY;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    private static final Long DEFAULT_PERIOD_IN_MONTHS = 1L;
    private static final Long UPDATED_PERIOD_IN_MONTHS = 2L;
    private static final Long SMALLER_PERIOD_IN_MONTHS = 1L - 1L;

    private static final Float DEFAULT_TAX_RATE = 1F;
    private static final Float UPDATED_TAX_RATE = 2F;
    private static final Float SMALLER_TAX_RATE = 1F - 1F;

    private static final String DEFAULT_COVERAGE = "AAAAAAAAAA";
    private static final String UPDATED_COVERAGE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_EXCLUSION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/warranty-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WarrantyMasterRepository warrantyMasterRepository;

    @Mock
    private WarrantyMasterRepository warrantyMasterRepositoryMock;

    @Autowired
    private WarrantyMasterMapper warrantyMasterMapper;

    @Mock
    private WarrantyMasterService warrantyMasterServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWarrantyMasterMockMvc;

    private WarrantyMaster warrantyMaster;

    private WarrantyMaster insertedWarrantyMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarrantyMaster createEntity() {
        return new WarrantyMaster()
            .name(DEFAULT_NAME)
            .vendorWarrantyMasterId(DEFAULT_VENDOR_WARRANTY_MASTER_ID)
            .warrantyType(DEFAULT_WARRANTY_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .tax(DEFAULT_TAX)
            .franchiseCommission(DEFAULT_FRANCHISE_COMMISSION)
            .franchiseTax(DEFAULT_FRANCHISE_TAX)
            .periodInMonths(DEFAULT_PERIOD_IN_MONTHS)
            .taxRate(DEFAULT_TAX_RATE)
            .coverage(DEFAULT_COVERAGE)
            .exclusion(DEFAULT_EXCLUSION)
            .isActive(DEFAULT_IS_ACTIVE)
            .createddBy(DEFAULT_CREATEDD_BY)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarrantyMaster createUpdatedEntity() {
        return new WarrantyMaster()
            .name(UPDATED_NAME)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .franchiseTax(UPDATED_FRANCHISE_TAX)
            .periodInMonths(UPDATED_PERIOD_IN_MONTHS)
            .taxRate(UPDATED_TAX_RATE)
            .coverage(UPDATED_COVERAGE)
            .exclusion(UPDATED_EXCLUSION)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        warrantyMaster = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedWarrantyMaster != null) {
            warrantyMasterRepository.delete(insertedWarrantyMaster);
            insertedWarrantyMaster = null;
        }
    }

    @Test
    @Transactional
    void createWarrantyMaster() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WarrantyMaster
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);
        var returnedWarrantyMasterDTO = om.readValue(
            restWarrantyMasterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WarrantyMasterDTO.class
        );

        // Validate the WarrantyMaster in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWarrantyMaster = warrantyMasterMapper.toEntity(returnedWarrantyMasterDTO);
        assertWarrantyMasterUpdatableFieldsEquals(returnedWarrantyMaster, getPersistedWarrantyMaster(returnedWarrantyMaster));

        insertedWarrantyMaster = returnedWarrantyMaster;
    }

    @Test
    @Transactional
    void createWarrantyMasterWithExistingId() throws Exception {
        // Create the WarrantyMaster with an existing ID
        warrantyMaster.setId(1L);
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setName(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVendorWarrantyMasterIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setVendorWarrantyMasterId(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWarrantyTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setWarrantyType(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setPrice(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setTax(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFranchiseCommissionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setFranchiseCommission(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFranchiseTaxIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setFranchiseTax(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPeriodInMonthsIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setPeriodInMonths(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxRateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setTaxRate(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setCreateddBy(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setCreatedTime(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setUpdatedBy(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setUpdatedTime(null);

        // Create the WarrantyMaster, which fails.
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWarrantyMasters() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList
        restWarrantyMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warrantyMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vendorWarrantyMasterId").value(hasItem(DEFAULT_VENDOR_WARRANTY_MASTER_ID)))
            .andExpect(jsonPath("$.[*].warrantyType").value(hasItem(DEFAULT_WARRANTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommission").value(hasItem(DEFAULT_FRANCHISE_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseTax").value(hasItem(DEFAULT_FRANCHISE_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].periodInMonths").value(hasItem(DEFAULT_PERIOD_IN_MONTHS.intValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].coverage").value(hasItem(DEFAULT_COVERAGE)))
            .andExpect(jsonPath("$.[*].exclusion").value(hasItem(DEFAULT_EXCLUSION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWarrantyMastersWithEagerRelationshipsIsEnabled() throws Exception {
        when(warrantyMasterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWarrantyMasterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(warrantyMasterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWarrantyMastersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(warrantyMasterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWarrantyMasterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(warrantyMasterRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getWarrantyMaster() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get the warrantyMaster
        restWarrantyMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, warrantyMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(warrantyMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.vendorWarrantyMasterId").value(DEFAULT_VENDOR_WARRANTY_MASTER_ID))
            .andExpect(jsonPath("$.warrantyType").value(DEFAULT_WARRANTY_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.franchiseCommission").value(DEFAULT_FRANCHISE_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.franchiseTax").value(DEFAULT_FRANCHISE_TAX.doubleValue()))
            .andExpect(jsonPath("$.periodInMonths").value(DEFAULT_PERIOD_IN_MONTHS.intValue()))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.doubleValue()))
            .andExpect(jsonPath("$.coverage").value(DEFAULT_COVERAGE))
            .andExpect(jsonPath("$.exclusion").value(DEFAULT_EXCLUSION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getWarrantyMastersByIdFiltering() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        Long id = warrantyMaster.getId();

        defaultWarrantyMasterFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultWarrantyMasterFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultWarrantyMasterFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where name equals to
        defaultWarrantyMasterFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where name in
        defaultWarrantyMasterFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where name is not null
        defaultWarrantyMasterFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where name contains
        defaultWarrantyMasterFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where name does not contain
        defaultWarrantyMasterFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByVendorWarrantyMasterIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where vendorWarrantyMasterId equals to
        defaultWarrantyMasterFiltering(
            "vendorWarrantyMasterId.equals=" + DEFAULT_VENDOR_WARRANTY_MASTER_ID,
            "vendorWarrantyMasterId.equals=" + UPDATED_VENDOR_WARRANTY_MASTER_ID
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByVendorWarrantyMasterIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where vendorWarrantyMasterId in
        defaultWarrantyMasterFiltering(
            "vendorWarrantyMasterId.in=" + DEFAULT_VENDOR_WARRANTY_MASTER_ID + "," + UPDATED_VENDOR_WARRANTY_MASTER_ID,
            "vendorWarrantyMasterId.in=" + UPDATED_VENDOR_WARRANTY_MASTER_ID
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByVendorWarrantyMasterIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where vendorWarrantyMasterId is not null
        defaultWarrantyMasterFiltering("vendorWarrantyMasterId.specified=true", "vendorWarrantyMasterId.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByVendorWarrantyMasterIdContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where vendorWarrantyMasterId contains
        defaultWarrantyMasterFiltering(
            "vendorWarrantyMasterId.contains=" + DEFAULT_VENDOR_WARRANTY_MASTER_ID,
            "vendorWarrantyMasterId.contains=" + UPDATED_VENDOR_WARRANTY_MASTER_ID
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByVendorWarrantyMasterIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where vendorWarrantyMasterId does not contain
        defaultWarrantyMasterFiltering(
            "vendorWarrantyMasterId.doesNotContain=" + UPDATED_VENDOR_WARRANTY_MASTER_ID,
            "vendorWarrantyMasterId.doesNotContain=" + DEFAULT_VENDOR_WARRANTY_MASTER_ID
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByWarrantyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where warrantyType equals to
        defaultWarrantyMasterFiltering("warrantyType.equals=" + DEFAULT_WARRANTY_TYPE, "warrantyType.equals=" + UPDATED_WARRANTY_TYPE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByWarrantyTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where warrantyType in
        defaultWarrantyMasterFiltering(
            "warrantyType.in=" + DEFAULT_WARRANTY_TYPE + "," + UPDATED_WARRANTY_TYPE,
            "warrantyType.in=" + UPDATED_WARRANTY_TYPE
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByWarrantyTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where warrantyType is not null
        defaultWarrantyMasterFiltering("warrantyType.specified=true", "warrantyType.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where description equals to
        defaultWarrantyMasterFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where description in
        defaultWarrantyMasterFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where description is not null
        defaultWarrantyMasterFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where description contains
        defaultWarrantyMasterFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where description does not contain
        defaultWarrantyMasterFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where price equals to
        defaultWarrantyMasterFiltering("price.equals=" + DEFAULT_PRICE, "price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where price in
        defaultWarrantyMasterFiltering("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE, "price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where price is not null
        defaultWarrantyMasterFiltering("price.specified=true", "price.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where price is greater than or equal to
        defaultWarrantyMasterFiltering("price.greaterThanOrEqual=" + DEFAULT_PRICE, "price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where price is less than or equal to
        defaultWarrantyMasterFiltering("price.lessThanOrEqual=" + DEFAULT_PRICE, "price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where price is less than
        defaultWarrantyMasterFiltering("price.lessThan=" + UPDATED_PRICE, "price.lessThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where price is greater than
        defaultWarrantyMasterFiltering("price.greaterThan=" + SMALLER_PRICE, "price.greaterThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where tax equals to
        defaultWarrantyMasterFiltering("tax.equals=" + DEFAULT_TAX, "tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where tax in
        defaultWarrantyMasterFiltering("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX, "tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where tax is not null
        defaultWarrantyMasterFiltering("tax.specified=true", "tax.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where tax is greater than or equal to
        defaultWarrantyMasterFiltering("tax.greaterThanOrEqual=" + DEFAULT_TAX, "tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where tax is less than or equal to
        defaultWarrantyMasterFiltering("tax.lessThanOrEqual=" + DEFAULT_TAX, "tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where tax is less than
        defaultWarrantyMasterFiltering("tax.lessThan=" + UPDATED_TAX, "tax.lessThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where tax is greater than
        defaultWarrantyMasterFiltering("tax.greaterThan=" + SMALLER_TAX, "tax.greaterThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseCommission equals to
        defaultWarrantyMasterFiltering(
            "franchiseCommission.equals=" + DEFAULT_FRANCHISE_COMMISSION,
            "franchiseCommission.equals=" + UPDATED_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseCommission in
        defaultWarrantyMasterFiltering(
            "franchiseCommission.in=" + DEFAULT_FRANCHISE_COMMISSION + "," + UPDATED_FRANCHISE_COMMISSION,
            "franchiseCommission.in=" + UPDATED_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseCommission is not null
        defaultWarrantyMasterFiltering("franchiseCommission.specified=true", "franchiseCommission.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseCommissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseCommission is greater than or equal to
        defaultWarrantyMasterFiltering(
            "franchiseCommission.greaterThanOrEqual=" + DEFAULT_FRANCHISE_COMMISSION,
            "franchiseCommission.greaterThanOrEqual=" + UPDATED_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseCommissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseCommission is less than or equal to
        defaultWarrantyMasterFiltering(
            "franchiseCommission.lessThanOrEqual=" + DEFAULT_FRANCHISE_COMMISSION,
            "franchiseCommission.lessThanOrEqual=" + SMALLER_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseCommissionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseCommission is less than
        defaultWarrantyMasterFiltering(
            "franchiseCommission.lessThan=" + UPDATED_FRANCHISE_COMMISSION,
            "franchiseCommission.lessThan=" + DEFAULT_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseCommissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseCommission is greater than
        defaultWarrantyMasterFiltering(
            "franchiseCommission.greaterThan=" + SMALLER_FRANCHISE_COMMISSION,
            "franchiseCommission.greaterThan=" + DEFAULT_FRANCHISE_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseTax equals to
        defaultWarrantyMasterFiltering("franchiseTax.equals=" + DEFAULT_FRANCHISE_TAX, "franchiseTax.equals=" + UPDATED_FRANCHISE_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseTax in
        defaultWarrantyMasterFiltering(
            "franchiseTax.in=" + DEFAULT_FRANCHISE_TAX + "," + UPDATED_FRANCHISE_TAX,
            "franchiseTax.in=" + UPDATED_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseTax is not null
        defaultWarrantyMasterFiltering("franchiseTax.specified=true", "franchiseTax.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseTax is greater than or equal to
        defaultWarrantyMasterFiltering(
            "franchiseTax.greaterThanOrEqual=" + DEFAULT_FRANCHISE_TAX,
            "franchiseTax.greaterThanOrEqual=" + UPDATED_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseTax is less than or equal to
        defaultWarrantyMasterFiltering(
            "franchiseTax.lessThanOrEqual=" + DEFAULT_FRANCHISE_TAX,
            "franchiseTax.lessThanOrEqual=" + SMALLER_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseTax is less than
        defaultWarrantyMasterFiltering("franchiseTax.lessThan=" + UPDATED_FRANCHISE_TAX, "franchiseTax.lessThan=" + DEFAULT_FRANCHISE_TAX);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByFranchiseTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where franchiseTax is greater than
        defaultWarrantyMasterFiltering(
            "franchiseTax.greaterThan=" + SMALLER_FRANCHISE_TAX,
            "franchiseTax.greaterThan=" + DEFAULT_FRANCHISE_TAX
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPeriodInMonthsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where periodInMonths equals to
        defaultWarrantyMasterFiltering(
            "periodInMonths.equals=" + DEFAULT_PERIOD_IN_MONTHS,
            "periodInMonths.equals=" + UPDATED_PERIOD_IN_MONTHS
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPeriodInMonthsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where periodInMonths in
        defaultWarrantyMasterFiltering(
            "periodInMonths.in=" + DEFAULT_PERIOD_IN_MONTHS + "," + UPDATED_PERIOD_IN_MONTHS,
            "periodInMonths.in=" + UPDATED_PERIOD_IN_MONTHS
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPeriodInMonthsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where periodInMonths is not null
        defaultWarrantyMasterFiltering("periodInMonths.specified=true", "periodInMonths.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPeriodInMonthsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where periodInMonths is greater than or equal to
        defaultWarrantyMasterFiltering(
            "periodInMonths.greaterThanOrEqual=" + DEFAULT_PERIOD_IN_MONTHS,
            "periodInMonths.greaterThanOrEqual=" + UPDATED_PERIOD_IN_MONTHS
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPeriodInMonthsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where periodInMonths is less than or equal to
        defaultWarrantyMasterFiltering(
            "periodInMonths.lessThanOrEqual=" + DEFAULT_PERIOD_IN_MONTHS,
            "periodInMonths.lessThanOrEqual=" + SMALLER_PERIOD_IN_MONTHS
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPeriodInMonthsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where periodInMonths is less than
        defaultWarrantyMasterFiltering(
            "periodInMonths.lessThan=" + UPDATED_PERIOD_IN_MONTHS,
            "periodInMonths.lessThan=" + DEFAULT_PERIOD_IN_MONTHS
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByPeriodInMonthsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where periodInMonths is greater than
        defaultWarrantyMasterFiltering(
            "periodInMonths.greaterThan=" + SMALLER_PERIOD_IN_MONTHS,
            "periodInMonths.greaterThan=" + DEFAULT_PERIOD_IN_MONTHS
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxRateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where taxRate equals to
        defaultWarrantyMasterFiltering("taxRate.equals=" + DEFAULT_TAX_RATE, "taxRate.equals=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxRateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where taxRate in
        defaultWarrantyMasterFiltering("taxRate.in=" + DEFAULT_TAX_RATE + "," + UPDATED_TAX_RATE, "taxRate.in=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where taxRate is not null
        defaultWarrantyMasterFiltering("taxRate.specified=true", "taxRate.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where taxRate is greater than or equal to
        defaultWarrantyMasterFiltering("taxRate.greaterThanOrEqual=" + DEFAULT_TAX_RATE, "taxRate.greaterThanOrEqual=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where taxRate is less than or equal to
        defaultWarrantyMasterFiltering("taxRate.lessThanOrEqual=" + DEFAULT_TAX_RATE, "taxRate.lessThanOrEqual=" + SMALLER_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxRateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where taxRate is less than
        defaultWarrantyMasterFiltering("taxRate.lessThan=" + UPDATED_TAX_RATE, "taxRate.lessThan=" + DEFAULT_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByTaxRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where taxRate is greater than
        defaultWarrantyMasterFiltering("taxRate.greaterThan=" + SMALLER_TAX_RATE, "taxRate.greaterThan=" + DEFAULT_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCoverageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where coverage equals to
        defaultWarrantyMasterFiltering("coverage.equals=" + DEFAULT_COVERAGE, "coverage.equals=" + UPDATED_COVERAGE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCoverageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where coverage in
        defaultWarrantyMasterFiltering("coverage.in=" + DEFAULT_COVERAGE + "," + UPDATED_COVERAGE, "coverage.in=" + UPDATED_COVERAGE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCoverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where coverage is not null
        defaultWarrantyMasterFiltering("coverage.specified=true", "coverage.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCoverageContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where coverage contains
        defaultWarrantyMasterFiltering("coverage.contains=" + DEFAULT_COVERAGE, "coverage.contains=" + UPDATED_COVERAGE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCoverageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where coverage does not contain
        defaultWarrantyMasterFiltering("coverage.doesNotContain=" + UPDATED_COVERAGE, "coverage.doesNotContain=" + DEFAULT_COVERAGE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByExclusionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where exclusion equals to
        defaultWarrantyMasterFiltering("exclusion.equals=" + DEFAULT_EXCLUSION, "exclusion.equals=" + UPDATED_EXCLUSION);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByExclusionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where exclusion in
        defaultWarrantyMasterFiltering("exclusion.in=" + DEFAULT_EXCLUSION + "," + UPDATED_EXCLUSION, "exclusion.in=" + UPDATED_EXCLUSION);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByExclusionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where exclusion is not null
        defaultWarrantyMasterFiltering("exclusion.specified=true", "exclusion.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByExclusionContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where exclusion contains
        defaultWarrantyMasterFiltering("exclusion.contains=" + DEFAULT_EXCLUSION, "exclusion.contains=" + UPDATED_EXCLUSION);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByExclusionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where exclusion does not contain
        defaultWarrantyMasterFiltering("exclusion.doesNotContain=" + UPDATED_EXCLUSION, "exclusion.doesNotContain=" + DEFAULT_EXCLUSION);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where isActive equals to
        defaultWarrantyMasterFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where isActive in
        defaultWarrantyMasterFiltering("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE, "isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where isActive is not null
        defaultWarrantyMasterFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where createddBy equals to
        defaultWarrantyMasterFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where createddBy in
        defaultWarrantyMasterFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where createddBy is not null
        defaultWarrantyMasterFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where createddBy contains
        defaultWarrantyMasterFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where createddBy does not contain
        defaultWarrantyMasterFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where createdTime equals to
        defaultWarrantyMasterFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where createdTime in
        defaultWarrantyMasterFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where createdTime is not null
        defaultWarrantyMasterFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where updatedBy equals to
        defaultWarrantyMasterFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where updatedBy in
        defaultWarrantyMasterFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where updatedBy is not null
        defaultWarrantyMasterFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where updatedBy contains
        defaultWarrantyMasterFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where updatedBy does not contain
        defaultWarrantyMasterFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where updatedTime equals to
        defaultWarrantyMasterFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where updatedTime in
        defaultWarrantyMasterFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList where updatedTime is not null
        defaultWarrantyMasterFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByCoveredSpareIsEqualToSomething() throws Exception {
        Product coveredSpare;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            warrantyMasterRepository.saveAndFlush(warrantyMaster);
            coveredSpare = ProductResourceIT.createEntity();
        } else {
            coveredSpare = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(coveredSpare);
        em.flush();
        warrantyMaster.addCoveredSpare(coveredSpare);
        warrantyMasterRepository.saveAndFlush(warrantyMaster);
        Long coveredSpareId = coveredSpare.getId();
        // Get all the warrantyMasterList where coveredSpare equals to coveredSpareId
        defaultWarrantyMasterShouldBeFound("coveredSpareId.equals=" + coveredSpareId);

        // Get all the warrantyMasterList where coveredSpare equals to (coveredSpareId + 1)
        defaultWarrantyMasterShouldNotBeFound("coveredSpareId.equals=" + (coveredSpareId + 1));
    }

    @Test
    @Transactional
    void getAllWarrantyMastersByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            warrantyMasterRepository.saveAndFlush(warrantyMaster);
            product = ProductResourceIT.createEntity();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        warrantyMaster.setProduct(product);
        warrantyMasterRepository.saveAndFlush(warrantyMaster);
        Long productId = product.getId();
        // Get all the warrantyMasterList where product equals to productId
        defaultWarrantyMasterShouldBeFound("productId.equals=" + productId);

        // Get all the warrantyMasterList where product equals to (productId + 1)
        defaultWarrantyMasterShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    private void defaultWarrantyMasterFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultWarrantyMasterShouldBeFound(shouldBeFound);
        defaultWarrantyMasterShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWarrantyMasterShouldBeFound(String filter) throws Exception {
        restWarrantyMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warrantyMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vendorWarrantyMasterId").value(hasItem(DEFAULT_VENDOR_WARRANTY_MASTER_ID)))
            .andExpect(jsonPath("$.[*].warrantyType").value(hasItem(DEFAULT_WARRANTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommission").value(hasItem(DEFAULT_FRANCHISE_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseTax").value(hasItem(DEFAULT_FRANCHISE_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].periodInMonths").value(hasItem(DEFAULT_PERIOD_IN_MONTHS.intValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].coverage").value(hasItem(DEFAULT_COVERAGE)))
            .andExpect(jsonPath("$.[*].exclusion").value(hasItem(DEFAULT_EXCLUSION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restWarrantyMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWarrantyMasterShouldNotBeFound(String filter) throws Exception {
        restWarrantyMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWarrantyMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWarrantyMaster() throws Exception {
        // Get the warrantyMaster
        restWarrantyMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWarrantyMaster() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warrantyMaster
        WarrantyMaster updatedWarrantyMaster = warrantyMasterRepository.findById(warrantyMaster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWarrantyMaster are not directly saved in db
        em.detach(updatedWarrantyMaster);
        updatedWarrantyMaster
            .name(UPDATED_NAME)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .franchiseTax(UPDATED_FRANCHISE_TAX)
            .periodInMonths(UPDATED_PERIOD_IN_MONTHS)
            .taxRate(UPDATED_TAX_RATE)
            .coverage(UPDATED_COVERAGE)
            .exclusion(UPDATED_EXCLUSION)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(updatedWarrantyMaster);

        restWarrantyMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warrantyMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warrantyMasterDTO))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWarrantyMasterToMatchAllProperties(updatedWarrantyMaster);
    }

    @Test
    @Transactional
    void putNonExistingWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // Create the WarrantyMaster
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warrantyMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warrantyMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // Create the WarrantyMaster
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warrantyMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // Create the WarrantyMaster
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWarrantyMasterWithPatch() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warrantyMaster using partial update
        WarrantyMaster partialUpdatedWarrantyMaster = new WarrantyMaster();
        partialUpdatedWarrantyMaster.setId(warrantyMaster.getId());

        partialUpdatedWarrantyMaster
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .coverage(UPDATED_COVERAGE)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY);

        restWarrantyMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarrantyMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWarrantyMaster))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWarrantyMasterUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWarrantyMaster, warrantyMaster),
            getPersistedWarrantyMaster(warrantyMaster)
        );
    }

    @Test
    @Transactional
    void fullUpdateWarrantyMasterWithPatch() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warrantyMaster using partial update
        WarrantyMaster partialUpdatedWarrantyMaster = new WarrantyMaster();
        partialUpdatedWarrantyMaster.setId(warrantyMaster.getId());

        partialUpdatedWarrantyMaster
            .name(UPDATED_NAME)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .franchiseCommission(UPDATED_FRANCHISE_COMMISSION)
            .franchiseTax(UPDATED_FRANCHISE_TAX)
            .periodInMonths(UPDATED_PERIOD_IN_MONTHS)
            .taxRate(UPDATED_TAX_RATE)
            .coverage(UPDATED_COVERAGE)
            .exclusion(UPDATED_EXCLUSION)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restWarrantyMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarrantyMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWarrantyMaster))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWarrantyMasterUpdatableFieldsEquals(partialUpdatedWarrantyMaster, getPersistedWarrantyMaster(partialUpdatedWarrantyMaster));
    }

    @Test
    @Transactional
    void patchNonExistingWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // Create the WarrantyMaster
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, warrantyMasterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warrantyMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // Create the WarrantyMaster
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warrantyMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // Create the WarrantyMaster
        WarrantyMasterDTO warrantyMasterDTO = warrantyMasterMapper.toDto(warrantyMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(warrantyMasterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWarrantyMaster() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the warrantyMaster
        restWarrantyMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, warrantyMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return warrantyMasterRepository.count();
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

    protected WarrantyMaster getPersistedWarrantyMaster(WarrantyMaster warrantyMaster) {
        return warrantyMasterRepository.findById(warrantyMaster.getId()).orElseThrow();
    }

    protected void assertPersistedWarrantyMasterToMatchAllProperties(WarrantyMaster expectedWarrantyMaster) {
        assertWarrantyMasterAllPropertiesEquals(expectedWarrantyMaster, getPersistedWarrantyMaster(expectedWarrantyMaster));
    }

    protected void assertPersistedWarrantyMasterToMatchUpdatableProperties(WarrantyMaster expectedWarrantyMaster) {
        assertWarrantyMasterAllUpdatablePropertiesEquals(expectedWarrantyMaster, getPersistedWarrantyMaster(expectedWarrantyMaster));
    }
}
