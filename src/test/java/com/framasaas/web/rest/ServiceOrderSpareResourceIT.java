package com.framasaas.web.rest;

import static com.framasaas.domain.ServiceOrderSpareAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Product;
import com.framasaas.domain.ServiceOrder;
import com.framasaas.domain.ServiceOrderSpare;
import com.framasaas.domain.enumeration.InventoryLocationType;
import com.framasaas.domain.enumeration.ServiceOrderSpareStatus;
import com.framasaas.repository.ServiceOrderSpareRepository;
import com.framasaas.service.dto.ServiceOrderSpareDTO;
import com.framasaas.service.mapper.ServiceOrderSpareMapper;
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
 * Integration tests for the {@link ServiceOrderSpareResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceOrderSpareResourceIT {

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;
    private static final Float SMALLER_PRICE = 1F - 1F;

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;
    private static final Float SMALLER_TAX = 1F - 1F;

    private static final Float DEFAULT_TOTAL_CHARGE = 1F;
    private static final Float UPDATED_TOTAL_CHARGE = 2F;
    private static final Float SMALLER_TOTAL_CHARGE = 1F - 1F;

    private static final Float DEFAULT_FRANCHISE_COMMISION = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISION = 2F;
    private static final Float SMALLER_FRANCHISE_COMMISION = 1F - 1F;

    private static final Float DEFAULT_FRANCHISE_COMMISION_TAX = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISION_TAX = 2F;
    private static final Float SMALLER_FRANCHISE_COMMISION_TAX = 1F - 1F;

    private static final InventoryLocationType DEFAULT_ORDERED_FROM = InventoryLocationType.ENGINEER;
    private static final InventoryLocationType UPDATED_ORDERED_FROM = InventoryLocationType.FRANCHISE;

    private static final ServiceOrderSpareStatus DEFAULT_SPARE_STATUS = ServiceOrderSpareStatus.ADDED;
    private static final ServiceOrderSpareStatus UPDATED_SPARE_STATUS = ServiceOrderSpareStatus.PAYMENTINIT;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/service-order-spares";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceOrderSpareRepository serviceOrderSpareRepository;

    @Autowired
    private ServiceOrderSpareMapper serviceOrderSpareMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOrderSpareMockMvc;

    private ServiceOrderSpare serviceOrderSpare;

    private ServiceOrderSpare insertedServiceOrderSpare;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrderSpare createEntity() {
        return new ServiceOrderSpare()
            .price(DEFAULT_PRICE)
            .tax(DEFAULT_TAX)
            .totalCharge(DEFAULT_TOTAL_CHARGE)
            .franchiseCommision(DEFAULT_FRANCHISE_COMMISION)
            .franchiseCommisionTax(DEFAULT_FRANCHISE_COMMISION_TAX)
            .orderedFrom(DEFAULT_ORDERED_FROM)
            .spareStatus(DEFAULT_SPARE_STATUS)
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
    public static ServiceOrderSpare createUpdatedEntity() {
        return new ServiceOrderSpare()
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .totalCharge(UPDATED_TOTAL_CHARGE)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseCommisionTax(UPDATED_FRANCHISE_COMMISION_TAX)
            .orderedFrom(UPDATED_ORDERED_FROM)
            .spareStatus(UPDATED_SPARE_STATUS)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        serviceOrderSpare = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedServiceOrderSpare != null) {
            serviceOrderSpareRepository.delete(insertedServiceOrderSpare);
            insertedServiceOrderSpare = null;
        }
    }

    @Test
    @Transactional
    void createServiceOrderSpare() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceOrderSpare
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);
        var returnedServiceOrderSpareDTO = om.readValue(
            restServiceOrderSpareMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpareDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderSpareDTO.class
        );

        // Validate the ServiceOrderSpare in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServiceOrderSpare = serviceOrderSpareMapper.toEntity(returnedServiceOrderSpareDTO);
        assertServiceOrderSpareUpdatableFieldsEquals(returnedServiceOrderSpare, getPersistedServiceOrderSpare(returnedServiceOrderSpare));

        insertedServiceOrderSpare = returnedServiceOrderSpare;
    }

    @Test
    @Transactional
    void createServiceOrderSpareWithExistingId() throws Exception {
        // Create the ServiceOrderSpare with an existing ID
        serviceOrderSpare.setId(1L);
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderSpareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderSpare in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderSpare.setCreateddBy(null);

        // Create the ServiceOrderSpare, which fails.
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        restServiceOrderSpareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpareDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderSpare.setCreatedTime(null);

        // Create the ServiceOrderSpare, which fails.
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        restServiceOrderSpareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpareDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderSpare.setUpdatedBy(null);

        // Create the ServiceOrderSpare, which fails.
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        restServiceOrderSpareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpareDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderSpare.setUpdatedTime(null);

        // Create the ServiceOrderSpare, which fails.
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        restServiceOrderSpareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpareDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceOrderSpares() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList
        restServiceOrderSpareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderSpare.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCharge").value(hasItem(DEFAULT_TOTAL_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommision").value(hasItem(DEFAULT_FRANCHISE_COMMISION.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommisionTax").value(hasItem(DEFAULT_FRANCHISE_COMMISION_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].orderedFrom").value(hasItem(DEFAULT_ORDERED_FROM.toString())))
            .andExpect(jsonPath("$.[*].spareStatus").value(hasItem(DEFAULT_SPARE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getServiceOrderSpare() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get the serviceOrderSpare
        restServiceOrderSpareMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceOrderSpare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrderSpare.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.totalCharge").value(DEFAULT_TOTAL_CHARGE.doubleValue()))
            .andExpect(jsonPath("$.franchiseCommision").value(DEFAULT_FRANCHISE_COMMISION.doubleValue()))
            .andExpect(jsonPath("$.franchiseCommisionTax").value(DEFAULT_FRANCHISE_COMMISION_TAX.doubleValue()))
            .andExpect(jsonPath("$.orderedFrom").value(DEFAULT_ORDERED_FROM.toString()))
            .andExpect(jsonPath("$.spareStatus").value(DEFAULT_SPARE_STATUS.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getServiceOrderSparesByIdFiltering() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        Long id = serviceOrderSpare.getId();

        defaultServiceOrderSpareFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultServiceOrderSpareFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultServiceOrderSpareFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where price equals to
        defaultServiceOrderSpareFiltering("price.equals=" + DEFAULT_PRICE, "price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where price in
        defaultServiceOrderSpareFiltering("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE, "price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where price is not null
        defaultServiceOrderSpareFiltering("price.specified=true", "price.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where price is greater than or equal to
        defaultServiceOrderSpareFiltering("price.greaterThanOrEqual=" + DEFAULT_PRICE, "price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where price is less than or equal to
        defaultServiceOrderSpareFiltering("price.lessThanOrEqual=" + DEFAULT_PRICE, "price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where price is less than
        defaultServiceOrderSpareFiltering("price.lessThan=" + UPDATED_PRICE, "price.lessThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where price is greater than
        defaultServiceOrderSpareFiltering("price.greaterThan=" + SMALLER_PRICE, "price.greaterThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where tax equals to
        defaultServiceOrderSpareFiltering("tax.equals=" + DEFAULT_TAX, "tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where tax in
        defaultServiceOrderSpareFiltering("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX, "tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where tax is not null
        defaultServiceOrderSpareFiltering("tax.specified=true", "tax.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where tax is greater than or equal to
        defaultServiceOrderSpareFiltering("tax.greaterThanOrEqual=" + DEFAULT_TAX, "tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where tax is less than or equal to
        defaultServiceOrderSpareFiltering("tax.lessThanOrEqual=" + DEFAULT_TAX, "tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where tax is less than
        defaultServiceOrderSpareFiltering("tax.lessThan=" + UPDATED_TAX, "tax.lessThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where tax is greater than
        defaultServiceOrderSpareFiltering("tax.greaterThan=" + SMALLER_TAX, "tax.greaterThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTotalChargeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where totalCharge equals to
        defaultServiceOrderSpareFiltering("totalCharge.equals=" + DEFAULT_TOTAL_CHARGE, "totalCharge.equals=" + UPDATED_TOTAL_CHARGE);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTotalChargeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where totalCharge in
        defaultServiceOrderSpareFiltering(
            "totalCharge.in=" + DEFAULT_TOTAL_CHARGE + "," + UPDATED_TOTAL_CHARGE,
            "totalCharge.in=" + UPDATED_TOTAL_CHARGE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTotalChargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where totalCharge is not null
        defaultServiceOrderSpareFiltering("totalCharge.specified=true", "totalCharge.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTotalChargeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where totalCharge is greater than or equal to
        defaultServiceOrderSpareFiltering(
            "totalCharge.greaterThanOrEqual=" + DEFAULT_TOTAL_CHARGE,
            "totalCharge.greaterThanOrEqual=" + UPDATED_TOTAL_CHARGE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTotalChargeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where totalCharge is less than or equal to
        defaultServiceOrderSpareFiltering(
            "totalCharge.lessThanOrEqual=" + DEFAULT_TOTAL_CHARGE,
            "totalCharge.lessThanOrEqual=" + SMALLER_TOTAL_CHARGE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTotalChargeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where totalCharge is less than
        defaultServiceOrderSpareFiltering("totalCharge.lessThan=" + UPDATED_TOTAL_CHARGE, "totalCharge.lessThan=" + DEFAULT_TOTAL_CHARGE);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByTotalChargeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where totalCharge is greater than
        defaultServiceOrderSpareFiltering(
            "totalCharge.greaterThan=" + SMALLER_TOTAL_CHARGE,
            "totalCharge.greaterThan=" + DEFAULT_TOTAL_CHARGE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommision equals to
        defaultServiceOrderSpareFiltering(
            "franchiseCommision.equals=" + DEFAULT_FRANCHISE_COMMISION,
            "franchiseCommision.equals=" + UPDATED_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommision in
        defaultServiceOrderSpareFiltering(
            "franchiseCommision.in=" + DEFAULT_FRANCHISE_COMMISION + "," + UPDATED_FRANCHISE_COMMISION,
            "franchiseCommision.in=" + UPDATED_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommision is not null
        defaultServiceOrderSpareFiltering("franchiseCommision.specified=true", "franchiseCommision.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommision is greater than or equal to
        defaultServiceOrderSpareFiltering(
            "franchiseCommision.greaterThanOrEqual=" + DEFAULT_FRANCHISE_COMMISION,
            "franchiseCommision.greaterThanOrEqual=" + UPDATED_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommision is less than or equal to
        defaultServiceOrderSpareFiltering(
            "franchiseCommision.lessThanOrEqual=" + DEFAULT_FRANCHISE_COMMISION,
            "franchiseCommision.lessThanOrEqual=" + SMALLER_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommision is less than
        defaultServiceOrderSpareFiltering(
            "franchiseCommision.lessThan=" + UPDATED_FRANCHISE_COMMISION,
            "franchiseCommision.lessThan=" + DEFAULT_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommision is greater than
        defaultServiceOrderSpareFiltering(
            "franchiseCommision.greaterThan=" + SMALLER_FRANCHISE_COMMISION,
            "franchiseCommision.greaterThan=" + DEFAULT_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommisionTax equals to
        defaultServiceOrderSpareFiltering(
            "franchiseCommisionTax.equals=" + DEFAULT_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.equals=" + UPDATED_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommisionTax in
        defaultServiceOrderSpareFiltering(
            "franchiseCommisionTax.in=" + DEFAULT_FRANCHISE_COMMISION_TAX + "," + UPDATED_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.in=" + UPDATED_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommisionTax is not null
        defaultServiceOrderSpareFiltering("franchiseCommisionTax.specified=true", "franchiseCommisionTax.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommisionTax is greater than or equal to
        defaultServiceOrderSpareFiltering(
            "franchiseCommisionTax.greaterThanOrEqual=" + DEFAULT_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.greaterThanOrEqual=" + UPDATED_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommisionTax is less than or equal to
        defaultServiceOrderSpareFiltering(
            "franchiseCommisionTax.lessThanOrEqual=" + DEFAULT_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.lessThanOrEqual=" + SMALLER_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommisionTax is less than
        defaultServiceOrderSpareFiltering(
            "franchiseCommisionTax.lessThan=" + UPDATED_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.lessThan=" + DEFAULT_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByFranchiseCommisionTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where franchiseCommisionTax is greater than
        defaultServiceOrderSpareFiltering(
            "franchiseCommisionTax.greaterThan=" + SMALLER_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.greaterThan=" + DEFAULT_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByOrderedFromIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where orderedFrom equals to
        defaultServiceOrderSpareFiltering("orderedFrom.equals=" + DEFAULT_ORDERED_FROM, "orderedFrom.equals=" + UPDATED_ORDERED_FROM);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByOrderedFromIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where orderedFrom in
        defaultServiceOrderSpareFiltering(
            "orderedFrom.in=" + DEFAULT_ORDERED_FROM + "," + UPDATED_ORDERED_FROM,
            "orderedFrom.in=" + UPDATED_ORDERED_FROM
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByOrderedFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where orderedFrom is not null
        defaultServiceOrderSpareFiltering("orderedFrom.specified=true", "orderedFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesBySpareStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where spareStatus equals to
        defaultServiceOrderSpareFiltering("spareStatus.equals=" + DEFAULT_SPARE_STATUS, "spareStatus.equals=" + UPDATED_SPARE_STATUS);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesBySpareStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where spareStatus in
        defaultServiceOrderSpareFiltering(
            "spareStatus.in=" + DEFAULT_SPARE_STATUS + "," + UPDATED_SPARE_STATUS,
            "spareStatus.in=" + UPDATED_SPARE_STATUS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesBySpareStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where spareStatus is not null
        defaultServiceOrderSpareFiltering("spareStatus.specified=true", "spareStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where createddBy equals to
        defaultServiceOrderSpareFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where createddBy in
        defaultServiceOrderSpareFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where createddBy is not null
        defaultServiceOrderSpareFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where createddBy contains
        defaultServiceOrderSpareFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where createddBy does not contain
        defaultServiceOrderSpareFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where createdTime equals to
        defaultServiceOrderSpareFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where createdTime in
        defaultServiceOrderSpareFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where createdTime is not null
        defaultServiceOrderSpareFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where updatedBy equals to
        defaultServiceOrderSpareFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where updatedBy in
        defaultServiceOrderSpareFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where updatedBy is not null
        defaultServiceOrderSpareFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where updatedBy contains
        defaultServiceOrderSpareFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where updatedBy does not contain
        defaultServiceOrderSpareFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where updatedTime equals to
        defaultServiceOrderSpareFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where updatedTime in
        defaultServiceOrderSpareFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        // Get all the serviceOrderSpareList where updatedTime is not null
        defaultServiceOrderSpareFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByServiceOrderIsEqualToSomething() throws Exception {
        ServiceOrder serviceOrder;
        if (TestUtil.findAll(em, ServiceOrder.class).isEmpty()) {
            serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);
            serviceOrder = ServiceOrderResourceIT.createEntity();
        } else {
            serviceOrder = TestUtil.findAll(em, ServiceOrder.class).get(0);
        }
        em.persist(serviceOrder);
        em.flush();
        serviceOrderSpare.setServiceOrder(serviceOrder);
        serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);
        Long serviceOrderId = serviceOrder.getId();
        // Get all the serviceOrderSpareList where serviceOrder equals to serviceOrderId
        defaultServiceOrderSpareShouldBeFound("serviceOrderId.equals=" + serviceOrderId);

        // Get all the serviceOrderSpareList where serviceOrder equals to (serviceOrderId + 1)
        defaultServiceOrderSpareShouldNotBeFound("serviceOrderId.equals=" + (serviceOrderId + 1));
    }

    @Test
    @Transactional
    void getAllServiceOrderSparesByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);
            product = ProductResourceIT.createEntity();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        serviceOrderSpare.setProduct(product);
        serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);
        Long productId = product.getId();
        // Get all the serviceOrderSpareList where product equals to productId
        defaultServiceOrderSpareShouldBeFound("productId.equals=" + productId);

        // Get all the serviceOrderSpareList where product equals to (productId + 1)
        defaultServiceOrderSpareShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    private void defaultServiceOrderSpareFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultServiceOrderSpareShouldBeFound(shouldBeFound);
        defaultServiceOrderSpareShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceOrderSpareShouldBeFound(String filter) throws Exception {
        restServiceOrderSpareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderSpare.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCharge").value(hasItem(DEFAULT_TOTAL_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommision").value(hasItem(DEFAULT_FRANCHISE_COMMISION.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommisionTax").value(hasItem(DEFAULT_FRANCHISE_COMMISION_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].orderedFrom").value(hasItem(DEFAULT_ORDERED_FROM.toString())))
            .andExpect(jsonPath("$.[*].spareStatus").value(hasItem(DEFAULT_SPARE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restServiceOrderSpareMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceOrderSpareShouldNotBeFound(String filter) throws Exception {
        restServiceOrderSpareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceOrderSpareMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServiceOrderSpare() throws Exception {
        // Get the serviceOrderSpare
        restServiceOrderSpareMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceOrderSpare() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderSpare
        ServiceOrderSpare updatedServiceOrderSpare = serviceOrderSpareRepository.findById(serviceOrderSpare.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServiceOrderSpare are not directly saved in db
        em.detach(updatedServiceOrderSpare);
        updatedServiceOrderSpare
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .totalCharge(UPDATED_TOTAL_CHARGE)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseCommisionTax(UPDATED_FRANCHISE_COMMISION_TAX)
            .orderedFrom(UPDATED_ORDERED_FROM)
            .spareStatus(UPDATED_SPARE_STATUS)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(updatedServiceOrderSpare);

        restServiceOrderSpareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderSpareDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderSpareDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderSpare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceOrderSpareToMatchAllProperties(updatedServiceOrderSpare);
    }

    @Test
    @Transactional
    void putNonExistingServiceOrderSpare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderSpare.setId(longCount.incrementAndGet());

        // Create the ServiceOrderSpare
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderSpareDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderSpareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderSpare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceOrderSpare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderSpare.setId(longCount.incrementAndGet());

        // Create the ServiceOrderSpare
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderSpareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderSpare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceOrderSpare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderSpare.setId(longCount.incrementAndGet());

        // Create the ServiceOrderSpare
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpareDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderSpare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceOrderSpareWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderSpare using partial update
        ServiceOrderSpare partialUpdatedServiceOrderSpare = new ServiceOrderSpare();
        partialUpdatedServiceOrderSpare.setId(serviceOrderSpare.getId());

        partialUpdatedServiceOrderSpare
            .price(UPDATED_PRICE)
            .totalCharge(UPDATED_TOTAL_CHARGE)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseCommisionTax(UPDATED_FRANCHISE_COMMISION_TAX)
            .spareStatus(UPDATED_SPARE_STATUS)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderSpareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderSpare.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderSpare))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderSpare in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderSpareUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceOrderSpare, serviceOrderSpare),
            getPersistedServiceOrderSpare(serviceOrderSpare)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceOrderSpareWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderSpare using partial update
        ServiceOrderSpare partialUpdatedServiceOrderSpare = new ServiceOrderSpare();
        partialUpdatedServiceOrderSpare.setId(serviceOrderSpare.getId());

        partialUpdatedServiceOrderSpare
            .price(UPDATED_PRICE)
            .tax(UPDATED_TAX)
            .totalCharge(UPDATED_TOTAL_CHARGE)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseCommisionTax(UPDATED_FRANCHISE_COMMISION_TAX)
            .orderedFrom(UPDATED_ORDERED_FROM)
            .spareStatus(UPDATED_SPARE_STATUS)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderSpareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderSpare.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderSpare))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderSpare in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderSpareUpdatableFieldsEquals(
            partialUpdatedServiceOrderSpare,
            getPersistedServiceOrderSpare(partialUpdatedServiceOrderSpare)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServiceOrderSpare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderSpare.setId(longCount.incrementAndGet());

        // Create the ServiceOrderSpare
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderSpareDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderSpareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderSpare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceOrderSpare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderSpare.setId(longCount.incrementAndGet());

        // Create the ServiceOrderSpare
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderSpareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderSpare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceOrderSpare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderSpare.setId(longCount.incrementAndGet());

        // Create the ServiceOrderSpare
        ServiceOrderSpareDTO serviceOrderSpareDTO = serviceOrderSpareMapper.toDto(serviceOrderSpare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceOrderSpareDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderSpare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceOrderSpare() throws Exception {
        // Initialize the database
        insertedServiceOrderSpare = serviceOrderSpareRepository.saveAndFlush(serviceOrderSpare);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceOrderSpare
        restServiceOrderSpareMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceOrderSpare.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceOrderSpareRepository.count();
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

    protected ServiceOrderSpare getPersistedServiceOrderSpare(ServiceOrderSpare serviceOrderSpare) {
        return serviceOrderSpareRepository.findById(serviceOrderSpare.getId()).orElseThrow();
    }

    protected void assertPersistedServiceOrderSpareToMatchAllProperties(ServiceOrderSpare expectedServiceOrderSpare) {
        assertServiceOrderSpareAllPropertiesEquals(expectedServiceOrderSpare, getPersistedServiceOrderSpare(expectedServiceOrderSpare));
    }

    protected void assertPersistedServiceOrderSpareToMatchUpdatableProperties(ServiceOrderSpare expectedServiceOrderSpare) {
        assertServiceOrderSpareAllUpdatablePropertiesEquals(
            expectedServiceOrderSpare,
            getPersistedServiceOrderSpare(expectedServiceOrderSpare)
        );
    }
}
