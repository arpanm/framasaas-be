package com.framasaas.web.rest;

import static com.framasaas.domain.ServiceOrderMasterAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Product;
import com.framasaas.domain.ServiceOrderMaster;
import com.framasaas.domain.enumeration.ServiceOrderType;
import com.framasaas.repository.ServiceOrderMasterRepository;
import com.framasaas.service.dto.ServiceOrderMasterDTO;
import com.framasaas.service.mapper.ServiceOrderMasterMapper;
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
 * Integration tests for the {@link ServiceOrderMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceOrderMasterResourceIT {

    private static final ServiceOrderType DEFAULT_SERVICE_ORDER_TYPE = ServiceOrderType.REPAIR;
    private static final ServiceOrderType UPDATED_SERVICE_ORDER_TYPE = ServiceOrderType.PERIODICMAINTENANCE;

    private static final Long DEFAULT_SLA_IN_HOURS = 1L;
    private static final Long UPDATED_SLA_IN_HOURS = 2L;
    private static final Long SMALLER_SLA_IN_HOURS = 1L - 1L;

    private static final Float DEFAULT_CHARGE = 1F;
    private static final Float UPDATED_CHARGE = 2F;
    private static final Float SMALLER_CHARGE = 1F - 1F;

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;
    private static final Float SMALLER_TAX = 1F - 1F;

    private static final Float DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA = 2F;
    private static final Float SMALLER_FRANCHISE_COMMISSION_WITHIN_SLA = 1F - 1F;

    private static final Float DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX = 1F;
    private static final Float UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX = 2F;
    private static final Float SMALLER_FRANCHISE_TAX_WITHIN_SLA_TAX = 1F - 1F;

    private static final Float DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISSION_OUTSIDE_SLA = 2F;
    private static final Float SMALLER_FRANCHISE_COMMISSION_OUTSIDE_SLA = 1F - 1F;

    private static final Float DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX = 1F;
    private static final Float UPDATED_FRANCHISE_TAX_OUTSIDE_SLA_TAX = 2F;
    private static final Float SMALLER_FRANCHISE_TAX_OUTSIDE_SLA_TAX = 1F - 1F;

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

    private static final String ENTITY_API_URL = "/api/service-order-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceOrderMasterRepository serviceOrderMasterRepository;

    @Autowired
    private ServiceOrderMasterMapper serviceOrderMasterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOrderMasterMockMvc;

    private ServiceOrderMaster serviceOrderMaster;

    private ServiceOrderMaster insertedServiceOrderMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrderMaster createEntity() {
        return new ServiceOrderMaster()
            .serviceOrderType(DEFAULT_SERVICE_ORDER_TYPE)
            .slaInHours(DEFAULT_SLA_IN_HOURS)
            .charge(DEFAULT_CHARGE)
            .tax(DEFAULT_TAX)
            .franchiseCommissionWithinSla(DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA)
            .franchiseTaxWithinSlaTax(DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX)
            .franchiseCommissionOutsideSla(DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA)
            .franchiseTaxOutsideSlaTax(DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX)
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
    public static ServiceOrderMaster createUpdatedEntity() {
        return new ServiceOrderMaster()
            .serviceOrderType(UPDATED_SERVICE_ORDER_TYPE)
            .slaInHours(UPDATED_SLA_IN_HOURS)
            .charge(UPDATED_CHARGE)
            .tax(UPDATED_TAX)
            .franchiseCommissionWithinSla(UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA)
            .franchiseTaxWithinSlaTax(UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX)
            .franchiseCommissionOutsideSla(UPDATED_FRANCHISE_COMMISSION_OUTSIDE_SLA)
            .franchiseTaxOutsideSlaTax(UPDATED_FRANCHISE_TAX_OUTSIDE_SLA_TAX)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        serviceOrderMaster = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedServiceOrderMaster != null) {
            serviceOrderMasterRepository.delete(insertedServiceOrderMaster);
            insertedServiceOrderMaster = null;
        }
    }

    @Test
    @Transactional
    void createServiceOrderMaster() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceOrderMaster
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);
        var returnedServiceOrderMasterDTO = om.readValue(
            restServiceOrderMasterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMasterDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderMasterDTO.class
        );

        // Validate the ServiceOrderMaster in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServiceOrderMaster = serviceOrderMasterMapper.toEntity(returnedServiceOrderMasterDTO);
        assertServiceOrderMasterUpdatableFieldsEquals(
            returnedServiceOrderMaster,
            getPersistedServiceOrderMaster(returnedServiceOrderMaster)
        );

        insertedServiceOrderMaster = returnedServiceOrderMaster;
    }

    @Test
    @Transactional
    void createServiceOrderMasterWithExistingId() throws Exception {
        // Create the ServiceOrderMaster with an existing ID
        serviceOrderMaster.setId(1L);
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderMaster.setCreateddBy(null);

        // Create the ServiceOrderMaster, which fails.
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        restServiceOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderMaster.setCreatedTime(null);

        // Create the ServiceOrderMaster, which fails.
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        restServiceOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderMaster.setUpdatedBy(null);

        // Create the ServiceOrderMaster, which fails.
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        restServiceOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderMaster.setUpdatedTime(null);

        // Create the ServiceOrderMaster, which fails.
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        restServiceOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMasterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceOrderMasters() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList
        restServiceOrderMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOrderType").value(hasItem(DEFAULT_SERVICE_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].slaInHours").value(hasItem(DEFAULT_SLA_IN_HOURS.intValue())))
            .andExpect(jsonPath("$.[*].charge").value(hasItem(DEFAULT_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommissionWithinSla").value(hasItem(DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseTaxWithinSlaTax").value(hasItem(DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX.doubleValue())))
            .andExpect(
                jsonPath("$.[*].franchiseCommissionOutsideSla").value(hasItem(DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].franchiseTaxOutsideSlaTax").value(hasItem(DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getServiceOrderMaster() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get the serviceOrderMaster
        restServiceOrderMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceOrderMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrderMaster.getId().intValue()))
            .andExpect(jsonPath("$.serviceOrderType").value(DEFAULT_SERVICE_ORDER_TYPE.toString()))
            .andExpect(jsonPath("$.slaInHours").value(DEFAULT_SLA_IN_HOURS.intValue()))
            .andExpect(jsonPath("$.charge").value(DEFAULT_CHARGE.doubleValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.franchiseCommissionWithinSla").value(DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA.doubleValue()))
            .andExpect(jsonPath("$.franchiseTaxWithinSlaTax").value(DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX.doubleValue()))
            .andExpect(jsonPath("$.franchiseCommissionOutsideSla").value(DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA.doubleValue()))
            .andExpect(jsonPath("$.franchiseTaxOutsideSlaTax").value(DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getServiceOrderMastersByIdFiltering() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        Long id = serviceOrderMaster.getId();

        defaultServiceOrderMasterFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultServiceOrderMasterFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultServiceOrderMasterFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByServiceOrderTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where serviceOrderType equals to
        defaultServiceOrderMasterFiltering(
            "serviceOrderType.equals=" + DEFAULT_SERVICE_ORDER_TYPE,
            "serviceOrderType.equals=" + UPDATED_SERVICE_ORDER_TYPE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByServiceOrderTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where serviceOrderType in
        defaultServiceOrderMasterFiltering(
            "serviceOrderType.in=" + DEFAULT_SERVICE_ORDER_TYPE + "," + UPDATED_SERVICE_ORDER_TYPE,
            "serviceOrderType.in=" + UPDATED_SERVICE_ORDER_TYPE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByServiceOrderTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where serviceOrderType is not null
        defaultServiceOrderMasterFiltering("serviceOrderType.specified=true", "serviceOrderType.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersBySlaInHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where slaInHours equals to
        defaultServiceOrderMasterFiltering("slaInHours.equals=" + DEFAULT_SLA_IN_HOURS, "slaInHours.equals=" + UPDATED_SLA_IN_HOURS);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersBySlaInHoursIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where slaInHours in
        defaultServiceOrderMasterFiltering(
            "slaInHours.in=" + DEFAULT_SLA_IN_HOURS + "," + UPDATED_SLA_IN_HOURS,
            "slaInHours.in=" + UPDATED_SLA_IN_HOURS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersBySlaInHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where slaInHours is not null
        defaultServiceOrderMasterFiltering("slaInHours.specified=true", "slaInHours.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersBySlaInHoursIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where slaInHours is greater than or equal to
        defaultServiceOrderMasterFiltering(
            "slaInHours.greaterThanOrEqual=" + DEFAULT_SLA_IN_HOURS,
            "slaInHours.greaterThanOrEqual=" + UPDATED_SLA_IN_HOURS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersBySlaInHoursIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where slaInHours is less than or equal to
        defaultServiceOrderMasterFiltering(
            "slaInHours.lessThanOrEqual=" + DEFAULT_SLA_IN_HOURS,
            "slaInHours.lessThanOrEqual=" + SMALLER_SLA_IN_HOURS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersBySlaInHoursIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where slaInHours is less than
        defaultServiceOrderMasterFiltering("slaInHours.lessThan=" + UPDATED_SLA_IN_HOURS, "slaInHours.lessThan=" + DEFAULT_SLA_IN_HOURS);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersBySlaInHoursIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where slaInHours is greater than
        defaultServiceOrderMasterFiltering(
            "slaInHours.greaterThan=" + SMALLER_SLA_IN_HOURS,
            "slaInHours.greaterThan=" + DEFAULT_SLA_IN_HOURS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByChargeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where charge equals to
        defaultServiceOrderMasterFiltering("charge.equals=" + DEFAULT_CHARGE, "charge.equals=" + UPDATED_CHARGE);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByChargeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where charge in
        defaultServiceOrderMasterFiltering("charge.in=" + DEFAULT_CHARGE + "," + UPDATED_CHARGE, "charge.in=" + UPDATED_CHARGE);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByChargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where charge is not null
        defaultServiceOrderMasterFiltering("charge.specified=true", "charge.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByChargeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where charge is greater than or equal to
        defaultServiceOrderMasterFiltering("charge.greaterThanOrEqual=" + DEFAULT_CHARGE, "charge.greaterThanOrEqual=" + UPDATED_CHARGE);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByChargeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where charge is less than or equal to
        defaultServiceOrderMasterFiltering("charge.lessThanOrEqual=" + DEFAULT_CHARGE, "charge.lessThanOrEqual=" + SMALLER_CHARGE);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByChargeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where charge is less than
        defaultServiceOrderMasterFiltering("charge.lessThan=" + UPDATED_CHARGE, "charge.lessThan=" + DEFAULT_CHARGE);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByChargeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where charge is greater than
        defaultServiceOrderMasterFiltering("charge.greaterThan=" + SMALLER_CHARGE, "charge.greaterThan=" + DEFAULT_CHARGE);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where tax equals to
        defaultServiceOrderMasterFiltering("tax.equals=" + DEFAULT_TAX, "tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where tax in
        defaultServiceOrderMasterFiltering("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX, "tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where tax is not null
        defaultServiceOrderMasterFiltering("tax.specified=true", "tax.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where tax is greater than or equal to
        defaultServiceOrderMasterFiltering("tax.greaterThanOrEqual=" + DEFAULT_TAX, "tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where tax is less than or equal to
        defaultServiceOrderMasterFiltering("tax.lessThanOrEqual=" + DEFAULT_TAX, "tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where tax is less than
        defaultServiceOrderMasterFiltering("tax.lessThan=" + UPDATED_TAX, "tax.lessThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where tax is greater than
        defaultServiceOrderMasterFiltering("tax.greaterThan=" + SMALLER_TAX, "tax.greaterThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionWithinSlaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionWithinSla equals to
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionWithinSla.equals=" + DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA,
            "franchiseCommissionWithinSla.equals=" + UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionWithinSlaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionWithinSla in
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionWithinSla.in=" + DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA + "," + UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA,
            "franchiseCommissionWithinSla.in=" + UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionWithinSlaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionWithinSla is not null
        defaultServiceOrderMasterFiltering("franchiseCommissionWithinSla.specified=true", "franchiseCommissionWithinSla.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionWithinSlaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionWithinSla is greater than or equal to
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionWithinSla.greaterThanOrEqual=" + DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA,
            "franchiseCommissionWithinSla.greaterThanOrEqual=" + UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionWithinSlaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionWithinSla is less than or equal to
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionWithinSla.lessThanOrEqual=" + DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA,
            "franchiseCommissionWithinSla.lessThanOrEqual=" + SMALLER_FRANCHISE_COMMISSION_WITHIN_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionWithinSlaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionWithinSla is less than
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionWithinSla.lessThan=" + UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA,
            "franchiseCommissionWithinSla.lessThan=" + DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionWithinSlaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionWithinSla is greater than
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionWithinSla.greaterThan=" + SMALLER_FRANCHISE_COMMISSION_WITHIN_SLA,
            "franchiseCommissionWithinSla.greaterThan=" + DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxWithinSlaTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxWithinSlaTax equals to
        defaultServiceOrderMasterFiltering(
            "franchiseTaxWithinSlaTax.equals=" + DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX,
            "franchiseTaxWithinSlaTax.equals=" + UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxWithinSlaTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxWithinSlaTax in
        defaultServiceOrderMasterFiltering(
            "franchiseTaxWithinSlaTax.in=" + DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX + "," + UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX,
            "franchiseTaxWithinSlaTax.in=" + UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxWithinSlaTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxWithinSlaTax is not null
        defaultServiceOrderMasterFiltering("franchiseTaxWithinSlaTax.specified=true", "franchiseTaxWithinSlaTax.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxWithinSlaTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxWithinSlaTax is greater than or equal to
        defaultServiceOrderMasterFiltering(
            "franchiseTaxWithinSlaTax.greaterThanOrEqual=" + DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX,
            "franchiseTaxWithinSlaTax.greaterThanOrEqual=" + UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxWithinSlaTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxWithinSlaTax is less than or equal to
        defaultServiceOrderMasterFiltering(
            "franchiseTaxWithinSlaTax.lessThanOrEqual=" + DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX,
            "franchiseTaxWithinSlaTax.lessThanOrEqual=" + SMALLER_FRANCHISE_TAX_WITHIN_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxWithinSlaTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxWithinSlaTax is less than
        defaultServiceOrderMasterFiltering(
            "franchiseTaxWithinSlaTax.lessThan=" + UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX,
            "franchiseTaxWithinSlaTax.lessThan=" + DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxWithinSlaTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxWithinSlaTax is greater than
        defaultServiceOrderMasterFiltering(
            "franchiseTaxWithinSlaTax.greaterThan=" + SMALLER_FRANCHISE_TAX_WITHIN_SLA_TAX,
            "franchiseTaxWithinSlaTax.greaterThan=" + DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionOutsideSlaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionOutsideSla equals to
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionOutsideSla.equals=" + DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA,
            "franchiseCommissionOutsideSla.equals=" + UPDATED_FRANCHISE_COMMISSION_OUTSIDE_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionOutsideSlaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionOutsideSla in
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionOutsideSla.in=" + DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA + "," + UPDATED_FRANCHISE_COMMISSION_OUTSIDE_SLA,
            "franchiseCommissionOutsideSla.in=" + UPDATED_FRANCHISE_COMMISSION_OUTSIDE_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionOutsideSlaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionOutsideSla is not null
        defaultServiceOrderMasterFiltering("franchiseCommissionOutsideSla.specified=true", "franchiseCommissionOutsideSla.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionOutsideSlaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionOutsideSla is greater than or equal to
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionOutsideSla.greaterThanOrEqual=" + DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA,
            "franchiseCommissionOutsideSla.greaterThanOrEqual=" + UPDATED_FRANCHISE_COMMISSION_OUTSIDE_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionOutsideSlaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionOutsideSla is less than or equal to
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionOutsideSla.lessThanOrEqual=" + DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA,
            "franchiseCommissionOutsideSla.lessThanOrEqual=" + SMALLER_FRANCHISE_COMMISSION_OUTSIDE_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionOutsideSlaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionOutsideSla is less than
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionOutsideSla.lessThan=" + UPDATED_FRANCHISE_COMMISSION_OUTSIDE_SLA,
            "franchiseCommissionOutsideSla.lessThan=" + DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseCommissionOutsideSlaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseCommissionOutsideSla is greater than
        defaultServiceOrderMasterFiltering(
            "franchiseCommissionOutsideSla.greaterThan=" + SMALLER_FRANCHISE_COMMISSION_OUTSIDE_SLA,
            "franchiseCommissionOutsideSla.greaterThan=" + DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxOutsideSlaTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxOutsideSlaTax equals to
        defaultServiceOrderMasterFiltering(
            "franchiseTaxOutsideSlaTax.equals=" + DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX,
            "franchiseTaxOutsideSlaTax.equals=" + UPDATED_FRANCHISE_TAX_OUTSIDE_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxOutsideSlaTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxOutsideSlaTax in
        defaultServiceOrderMasterFiltering(
            "franchiseTaxOutsideSlaTax.in=" + DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX + "," + UPDATED_FRANCHISE_TAX_OUTSIDE_SLA_TAX,
            "franchiseTaxOutsideSlaTax.in=" + UPDATED_FRANCHISE_TAX_OUTSIDE_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxOutsideSlaTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxOutsideSlaTax is not null
        defaultServiceOrderMasterFiltering("franchiseTaxOutsideSlaTax.specified=true", "franchiseTaxOutsideSlaTax.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxOutsideSlaTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxOutsideSlaTax is greater than or equal to
        defaultServiceOrderMasterFiltering(
            "franchiseTaxOutsideSlaTax.greaterThanOrEqual=" + DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX,
            "franchiseTaxOutsideSlaTax.greaterThanOrEqual=" + UPDATED_FRANCHISE_TAX_OUTSIDE_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxOutsideSlaTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxOutsideSlaTax is less than or equal to
        defaultServiceOrderMasterFiltering(
            "franchiseTaxOutsideSlaTax.lessThanOrEqual=" + DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX,
            "franchiseTaxOutsideSlaTax.lessThanOrEqual=" + SMALLER_FRANCHISE_TAX_OUTSIDE_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxOutsideSlaTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxOutsideSlaTax is less than
        defaultServiceOrderMasterFiltering(
            "franchiseTaxOutsideSlaTax.lessThan=" + UPDATED_FRANCHISE_TAX_OUTSIDE_SLA_TAX,
            "franchiseTaxOutsideSlaTax.lessThan=" + DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByFranchiseTaxOutsideSlaTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where franchiseTaxOutsideSlaTax is greater than
        defaultServiceOrderMasterFiltering(
            "franchiseTaxOutsideSlaTax.greaterThan=" + SMALLER_FRANCHISE_TAX_OUTSIDE_SLA_TAX,
            "franchiseTaxOutsideSlaTax.greaterThan=" + DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where isActive equals to
        defaultServiceOrderMasterFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where isActive in
        defaultServiceOrderMasterFiltering(
            "isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE,
            "isActive.in=" + UPDATED_IS_ACTIVE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where isActive is not null
        defaultServiceOrderMasterFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where createddBy equals to
        defaultServiceOrderMasterFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where createddBy in
        defaultServiceOrderMasterFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where createddBy is not null
        defaultServiceOrderMasterFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where createddBy contains
        defaultServiceOrderMasterFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where createddBy does not contain
        defaultServiceOrderMasterFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where createdTime equals to
        defaultServiceOrderMasterFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where createdTime in
        defaultServiceOrderMasterFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where createdTime is not null
        defaultServiceOrderMasterFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where updatedBy equals to
        defaultServiceOrderMasterFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where updatedBy in
        defaultServiceOrderMasterFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where updatedBy is not null
        defaultServiceOrderMasterFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where updatedBy contains
        defaultServiceOrderMasterFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where updatedBy does not contain
        defaultServiceOrderMasterFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where updatedTime equals to
        defaultServiceOrderMasterFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where updatedTime in
        defaultServiceOrderMasterFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        // Get all the serviceOrderMasterList where updatedTime is not null
        defaultServiceOrderMasterFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderMastersByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);
            product = ProductResourceIT.createEntity();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        serviceOrderMaster.setProduct(product);
        serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);
        Long productId = product.getId();
        // Get all the serviceOrderMasterList where product equals to productId
        defaultServiceOrderMasterShouldBeFound("productId.equals=" + productId);

        // Get all the serviceOrderMasterList where product equals to (productId + 1)
        defaultServiceOrderMasterShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    private void defaultServiceOrderMasterFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultServiceOrderMasterShouldBeFound(shouldBeFound);
        defaultServiceOrderMasterShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceOrderMasterShouldBeFound(String filter) throws Exception {
        restServiceOrderMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOrderType").value(hasItem(DEFAULT_SERVICE_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].slaInHours").value(hasItem(DEFAULT_SLA_IN_HOURS.intValue())))
            .andExpect(jsonPath("$.[*].charge").value(hasItem(DEFAULT_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommissionWithinSla").value(hasItem(DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseTaxWithinSlaTax").value(hasItem(DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX.doubleValue())))
            .andExpect(
                jsonPath("$.[*].franchiseCommissionOutsideSla").value(hasItem(DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].franchiseTaxOutsideSlaTax").value(hasItem(DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restServiceOrderMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceOrderMasterShouldNotBeFound(String filter) throws Exception {
        restServiceOrderMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceOrderMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServiceOrderMaster() throws Exception {
        // Get the serviceOrderMaster
        restServiceOrderMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceOrderMaster() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderMaster
        ServiceOrderMaster updatedServiceOrderMaster = serviceOrderMasterRepository.findById(serviceOrderMaster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServiceOrderMaster are not directly saved in db
        em.detach(updatedServiceOrderMaster);
        updatedServiceOrderMaster
            .serviceOrderType(UPDATED_SERVICE_ORDER_TYPE)
            .slaInHours(UPDATED_SLA_IN_HOURS)
            .charge(UPDATED_CHARGE)
            .tax(UPDATED_TAX)
            .franchiseCommissionWithinSla(UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA)
            .franchiseTaxWithinSlaTax(UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX)
            .franchiseCommissionOutsideSla(UPDATED_FRANCHISE_COMMISSION_OUTSIDE_SLA)
            .franchiseTaxOutsideSlaTax(UPDATED_FRANCHISE_TAX_OUTSIDE_SLA_TAX)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(updatedServiceOrderMaster);

        restServiceOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderMasterDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceOrderMasterToMatchAllProperties(updatedServiceOrderMaster);
    }

    @Test
    @Transactional
    void putNonExistingServiceOrderMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderMaster.setId(longCount.incrementAndGet());

        // Create the ServiceOrderMaster
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceOrderMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderMaster.setId(longCount.incrementAndGet());

        // Create the ServiceOrderMaster
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceOrderMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderMaster.setId(longCount.incrementAndGet());

        // Create the ServiceOrderMaster
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMasterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceOrderMasterWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderMaster using partial update
        ServiceOrderMaster partialUpdatedServiceOrderMaster = new ServiceOrderMaster();
        partialUpdatedServiceOrderMaster.setId(serviceOrderMaster.getId());

        partialUpdatedServiceOrderMaster
            .serviceOrderType(UPDATED_SERVICE_ORDER_TYPE)
            .slaInHours(UPDATED_SLA_IN_HOURS)
            .franchiseCommissionWithinSla(UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA)
            .franchiseTaxWithinSlaTax(UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX)
            .isActive(UPDATED_IS_ACTIVE)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderMaster))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderMasterUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceOrderMaster, serviceOrderMaster),
            getPersistedServiceOrderMaster(serviceOrderMaster)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceOrderMasterWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderMaster using partial update
        ServiceOrderMaster partialUpdatedServiceOrderMaster = new ServiceOrderMaster();
        partialUpdatedServiceOrderMaster.setId(serviceOrderMaster.getId());

        partialUpdatedServiceOrderMaster
            .serviceOrderType(UPDATED_SERVICE_ORDER_TYPE)
            .slaInHours(UPDATED_SLA_IN_HOURS)
            .charge(UPDATED_CHARGE)
            .tax(UPDATED_TAX)
            .franchiseCommissionWithinSla(UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA)
            .franchiseTaxWithinSlaTax(UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX)
            .franchiseCommissionOutsideSla(UPDATED_FRANCHISE_COMMISSION_OUTSIDE_SLA)
            .franchiseTaxOutsideSlaTax(UPDATED_FRANCHISE_TAX_OUTSIDE_SLA_TAX)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderMaster))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderMasterUpdatableFieldsEquals(
            partialUpdatedServiceOrderMaster,
            getPersistedServiceOrderMaster(partialUpdatedServiceOrderMaster)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServiceOrderMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderMaster.setId(longCount.incrementAndGet());

        // Create the ServiceOrderMaster
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderMasterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceOrderMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderMaster.setId(longCount.incrementAndGet());

        // Create the ServiceOrderMaster
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceOrderMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderMaster.setId(longCount.incrementAndGet());

        // Create the ServiceOrderMaster
        ServiceOrderMasterDTO serviceOrderMasterDTO = serviceOrderMasterMapper.toDto(serviceOrderMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceOrderMasterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceOrderMaster() throws Exception {
        // Initialize the database
        insertedServiceOrderMaster = serviceOrderMasterRepository.saveAndFlush(serviceOrderMaster);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceOrderMaster
        restServiceOrderMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceOrderMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceOrderMasterRepository.count();
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

    protected ServiceOrderMaster getPersistedServiceOrderMaster(ServiceOrderMaster serviceOrderMaster) {
        return serviceOrderMasterRepository.findById(serviceOrderMaster.getId()).orElseThrow();
    }

    protected void assertPersistedServiceOrderMasterToMatchAllProperties(ServiceOrderMaster expectedServiceOrderMaster) {
        assertServiceOrderMasterAllPropertiesEquals(expectedServiceOrderMaster, getPersistedServiceOrderMaster(expectedServiceOrderMaster));
    }

    protected void assertPersistedServiceOrderMasterToMatchUpdatableProperties(ServiceOrderMaster expectedServiceOrderMaster) {
        assertServiceOrderMasterAllUpdatablePropertiesEquals(
            expectedServiceOrderMaster,
            getPersistedServiceOrderMaster(expectedServiceOrderMaster)
        );
    }
}
