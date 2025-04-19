package com.framasaas.web.rest;

import static com.framasaas.domain.ServiceOrderAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Address;
import com.framasaas.domain.Article;
import com.framasaas.domain.Customer;
import com.framasaas.domain.ServiceOrder;
import com.framasaas.domain.ServiceOrderMaster;
import com.framasaas.domain.enumeration.ServiceOrderStatus;
import com.framasaas.domain.enumeration.ServiceOrderType;
import com.framasaas.repository.ServiceOrderRepository;
import com.framasaas.service.dto.ServiceOrderDTO;
import com.framasaas.service.mapper.ServiceOrderMapper;
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
 * Integration tests for the {@link ServiceOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceOrderResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ServiceOrderType DEFAULT_ORDER_TYPE = ServiceOrderType.REPAIR;
    private static final ServiceOrderType UPDATED_ORDER_TYPE = ServiceOrderType.PERIODICMAINTENANCE;

    private static final ServiceOrderStatus DEFAULT_ORDER_STATUS = ServiceOrderStatus.CREATED;
    private static final ServiceOrderStatus UPDATED_ORDER_STATUS = ServiceOrderStatus.ASSIGNED;

    private static final Boolean DEFAULT_IN_WARRANTY = false;
    private static final Boolean UPDATED_IN_WARRANTY = true;

    private static final Boolean DEFAULT_IS_FREE = false;
    private static final Boolean UPDATED_IS_FREE = true;

    private static final Boolean DEFAULT_IS_SPARE_NEEDED = false;
    private static final Boolean UPDATED_IS_SPARE_NEEDED = true;

    private static final Instant DEFAULT_CONFIRMED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONFIRMED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CLOSED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_SERVICE_CHARGE = 1F;
    private static final Float UPDATED_SERVICE_CHARGE = 2F;
    private static final Float SMALLER_SERVICE_CHARGE = 1F - 1F;

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;
    private static final Float SMALLER_TAX = 1F - 1F;

    private static final Float DEFAULT_TOTAL_SPARE_CHARGES = 1F;
    private static final Float UPDATED_TOTAL_SPARE_CHARGES = 2F;
    private static final Float SMALLER_TOTAL_SPARE_CHARGES = 1F - 1F;

    private static final Float DEFAULT_TOTAL_SPARE_TAX = 1F;
    private static final Float UPDATED_TOTAL_SPARE_TAX = 2F;
    private static final Float SMALLER_TOTAL_SPARE_TAX = 1F - 1F;

    private static final Float DEFAULT_TOTAL_CHARGES = 1F;
    private static final Float UPDATED_TOTAL_CHARGES = 2F;
    private static final Float SMALLER_TOTAL_CHARGES = 1F - 1F;

    private static final Float DEFAULT_TOTAL_PAYMENT_DONE = 1F;
    private static final Float UPDATED_TOTAL_PAYMENT_DONE = 2F;
    private static final Float SMALLER_TOTAL_PAYMENT_DONE = 1F - 1F;

    private static final String DEFAULT_CUSTOMER_INVOICE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_INVOICE_PATH = "BBBBBBBBBB";

    private static final Float DEFAULT_NPS = 1F;
    private static final Float UPDATED_NPS = 2F;
    private static final Float SMALLER_NPS = 1F - 1F;

    private static final Long DEFAULT_PRIORITY = 1L;
    private static final Long UPDATED_PRIORITY = 2L;
    private static final Long SMALLER_PRIORITY = 1L - 1L;

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

    private static final String ENTITY_API_URL = "/api/service-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private ServiceOrderMapper serviceOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOrderMockMvc;

    private ServiceOrder serviceOrder;

    private ServiceOrder insertedServiceOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrder createEntity() {
        return new ServiceOrder()
            .description(DEFAULT_DESCRIPTION)
            .orderType(DEFAULT_ORDER_TYPE)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .inWarranty(DEFAULT_IN_WARRANTY)
            .isFree(DEFAULT_IS_FREE)
            .isSpareNeeded(DEFAULT_IS_SPARE_NEEDED)
            .confirmedTime(DEFAULT_CONFIRMED_TIME)
            .closedTime(DEFAULT_CLOSED_TIME)
            .serviceCharge(DEFAULT_SERVICE_CHARGE)
            .tax(DEFAULT_TAX)
            .totalSpareCharges(DEFAULT_TOTAL_SPARE_CHARGES)
            .totalSpareTax(DEFAULT_TOTAL_SPARE_TAX)
            .totalCharges(DEFAULT_TOTAL_CHARGES)
            .totalPaymentDone(DEFAULT_TOTAL_PAYMENT_DONE)
            .customerInvoicePath(DEFAULT_CUSTOMER_INVOICE_PATH)
            .nps(DEFAULT_NPS)
            .priority(DEFAULT_PRIORITY)
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
    public static ServiceOrder createUpdatedEntity() {
        return new ServiceOrder()
            .description(UPDATED_DESCRIPTION)
            .orderType(UPDATED_ORDER_TYPE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .inWarranty(UPDATED_IN_WARRANTY)
            .isFree(UPDATED_IS_FREE)
            .isSpareNeeded(UPDATED_IS_SPARE_NEEDED)
            .confirmedTime(UPDATED_CONFIRMED_TIME)
            .closedTime(UPDATED_CLOSED_TIME)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .tax(UPDATED_TAX)
            .totalSpareCharges(UPDATED_TOTAL_SPARE_CHARGES)
            .totalSpareTax(UPDATED_TOTAL_SPARE_TAX)
            .totalCharges(UPDATED_TOTAL_CHARGES)
            .totalPaymentDone(UPDATED_TOTAL_PAYMENT_DONE)
            .customerInvoicePath(UPDATED_CUSTOMER_INVOICE_PATH)
            .nps(UPDATED_NPS)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        serviceOrder = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedServiceOrder != null) {
            serviceOrderRepository.delete(insertedServiceOrder);
            insertedServiceOrder = null;
        }
    }

    @Test
    @Transactional
    void createServiceOrder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);
        var returnedServiceOrderDTO = om.readValue(
            restServiceOrderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderDTO.class
        );

        // Validate the ServiceOrder in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServiceOrder = serviceOrderMapper.toEntity(returnedServiceOrderDTO);
        assertServiceOrderUpdatableFieldsEquals(returnedServiceOrder, getPersistedServiceOrder(returnedServiceOrder));

        insertedServiceOrder = returnedServiceOrder;
    }

    @Test
    @Transactional
    void createServiceOrderWithExistingId() throws Exception {
        // Create the ServiceOrder with an existing ID
        serviceOrder.setId(1L);
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrder.setCreateddBy(null);

        // Create the ServiceOrder, which fails.
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        restServiceOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrder.setCreatedTime(null);

        // Create the ServiceOrder, which fails.
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        restServiceOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrder.setUpdatedBy(null);

        // Create the ServiceOrder, which fails.
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        restServiceOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrder.setUpdatedTime(null);

        // Create the ServiceOrder, which fails.
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        restServiceOrderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceOrders() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList
        restServiceOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].inWarranty").value(hasItem(DEFAULT_IN_WARRANTY)))
            .andExpect(jsonPath("$.[*].isFree").value(hasItem(DEFAULT_IS_FREE)))
            .andExpect(jsonPath("$.[*].isSpareNeeded").value(hasItem(DEFAULT_IS_SPARE_NEEDED)))
            .andExpect(jsonPath("$.[*].confirmedTime").value(hasItem(DEFAULT_CONFIRMED_TIME.toString())))
            .andExpect(jsonPath("$.[*].closedTime").value(hasItem(DEFAULT_CLOSED_TIME.toString())))
            .andExpect(jsonPath("$.[*].serviceCharge").value(hasItem(DEFAULT_SERVICE_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalSpareCharges").value(hasItem(DEFAULT_TOTAL_SPARE_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].totalSpareTax").value(hasItem(DEFAULT_TOTAL_SPARE_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCharges").value(hasItem(DEFAULT_TOTAL_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPaymentDone").value(hasItem(DEFAULT_TOTAL_PAYMENT_DONE.doubleValue())))
            .andExpect(jsonPath("$.[*].customerInvoicePath").value(hasItem(DEFAULT_CUSTOMER_INVOICE_PATH)))
            .andExpect(jsonPath("$.[*].nps").value(hasItem(DEFAULT_NPS.doubleValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getServiceOrder() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get the serviceOrder
        restServiceOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrder.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.orderType").value(DEFAULT_ORDER_TYPE.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.inWarranty").value(DEFAULT_IN_WARRANTY))
            .andExpect(jsonPath("$.isFree").value(DEFAULT_IS_FREE))
            .andExpect(jsonPath("$.isSpareNeeded").value(DEFAULT_IS_SPARE_NEEDED))
            .andExpect(jsonPath("$.confirmedTime").value(DEFAULT_CONFIRMED_TIME.toString()))
            .andExpect(jsonPath("$.closedTime").value(DEFAULT_CLOSED_TIME.toString()))
            .andExpect(jsonPath("$.serviceCharge").value(DEFAULT_SERVICE_CHARGE.doubleValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.totalSpareCharges").value(DEFAULT_TOTAL_SPARE_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.totalSpareTax").value(DEFAULT_TOTAL_SPARE_TAX.doubleValue()))
            .andExpect(jsonPath("$.totalCharges").value(DEFAULT_TOTAL_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.totalPaymentDone").value(DEFAULT_TOTAL_PAYMENT_DONE.doubleValue()))
            .andExpect(jsonPath("$.customerInvoicePath").value(DEFAULT_CUSTOMER_INVOICE_PATH))
            .andExpect(jsonPath("$.nps").value(DEFAULT_NPS.doubleValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getServiceOrdersByIdFiltering() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        Long id = serviceOrder.getId();

        defaultServiceOrderFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultServiceOrderFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultServiceOrderFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where description equals to
        defaultServiceOrderFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where description in
        defaultServiceOrderFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where description is not null
        defaultServiceOrderFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where description contains
        defaultServiceOrderFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where description does not contain
        defaultServiceOrderFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByOrderTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where orderType equals to
        defaultServiceOrderFiltering("orderType.equals=" + DEFAULT_ORDER_TYPE, "orderType.equals=" + UPDATED_ORDER_TYPE);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByOrderTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where orderType in
        defaultServiceOrderFiltering("orderType.in=" + DEFAULT_ORDER_TYPE + "," + UPDATED_ORDER_TYPE, "orderType.in=" + UPDATED_ORDER_TYPE);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByOrderTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where orderType is not null
        defaultServiceOrderFiltering("orderType.specified=true", "orderType.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByOrderStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where orderStatus equals to
        defaultServiceOrderFiltering("orderStatus.equals=" + DEFAULT_ORDER_STATUS, "orderStatus.equals=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByOrderStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where orderStatus in
        defaultServiceOrderFiltering(
            "orderStatus.in=" + DEFAULT_ORDER_STATUS + "," + UPDATED_ORDER_STATUS,
            "orderStatus.in=" + UPDATED_ORDER_STATUS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByOrderStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where orderStatus is not null
        defaultServiceOrderFiltering("orderStatus.specified=true", "orderStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByInWarrantyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where inWarranty equals to
        defaultServiceOrderFiltering("inWarranty.equals=" + DEFAULT_IN_WARRANTY, "inWarranty.equals=" + UPDATED_IN_WARRANTY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByInWarrantyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where inWarranty in
        defaultServiceOrderFiltering(
            "inWarranty.in=" + DEFAULT_IN_WARRANTY + "," + UPDATED_IN_WARRANTY,
            "inWarranty.in=" + UPDATED_IN_WARRANTY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByInWarrantyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where inWarranty is not null
        defaultServiceOrderFiltering("inWarranty.specified=true", "inWarranty.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByIsFreeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where isFree equals to
        defaultServiceOrderFiltering("isFree.equals=" + DEFAULT_IS_FREE, "isFree.equals=" + UPDATED_IS_FREE);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByIsFreeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where isFree in
        defaultServiceOrderFiltering("isFree.in=" + DEFAULT_IS_FREE + "," + UPDATED_IS_FREE, "isFree.in=" + UPDATED_IS_FREE);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByIsFreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where isFree is not null
        defaultServiceOrderFiltering("isFree.specified=true", "isFree.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByIsSpareNeededIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where isSpareNeeded equals to
        defaultServiceOrderFiltering("isSpareNeeded.equals=" + DEFAULT_IS_SPARE_NEEDED, "isSpareNeeded.equals=" + UPDATED_IS_SPARE_NEEDED);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByIsSpareNeededIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where isSpareNeeded in
        defaultServiceOrderFiltering(
            "isSpareNeeded.in=" + DEFAULT_IS_SPARE_NEEDED + "," + UPDATED_IS_SPARE_NEEDED,
            "isSpareNeeded.in=" + UPDATED_IS_SPARE_NEEDED
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByIsSpareNeededIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where isSpareNeeded is not null
        defaultServiceOrderFiltering("isSpareNeeded.specified=true", "isSpareNeeded.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByConfirmedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where confirmedTime equals to
        defaultServiceOrderFiltering("confirmedTime.equals=" + DEFAULT_CONFIRMED_TIME, "confirmedTime.equals=" + UPDATED_CONFIRMED_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByConfirmedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where confirmedTime in
        defaultServiceOrderFiltering(
            "confirmedTime.in=" + DEFAULT_CONFIRMED_TIME + "," + UPDATED_CONFIRMED_TIME,
            "confirmedTime.in=" + UPDATED_CONFIRMED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByConfirmedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where confirmedTime is not null
        defaultServiceOrderFiltering("confirmedTime.specified=true", "confirmedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByClosedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where closedTime equals to
        defaultServiceOrderFiltering("closedTime.equals=" + DEFAULT_CLOSED_TIME, "closedTime.equals=" + UPDATED_CLOSED_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByClosedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where closedTime in
        defaultServiceOrderFiltering(
            "closedTime.in=" + DEFAULT_CLOSED_TIME + "," + UPDATED_CLOSED_TIME,
            "closedTime.in=" + UPDATED_CLOSED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByClosedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where closedTime is not null
        defaultServiceOrderFiltering("closedTime.specified=true", "closedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByServiceChargeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceCharge equals to
        defaultServiceOrderFiltering("serviceCharge.equals=" + DEFAULT_SERVICE_CHARGE, "serviceCharge.equals=" + UPDATED_SERVICE_CHARGE);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByServiceChargeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceCharge in
        defaultServiceOrderFiltering(
            "serviceCharge.in=" + DEFAULT_SERVICE_CHARGE + "," + UPDATED_SERVICE_CHARGE,
            "serviceCharge.in=" + UPDATED_SERVICE_CHARGE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByServiceChargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceCharge is not null
        defaultServiceOrderFiltering("serviceCharge.specified=true", "serviceCharge.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByServiceChargeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceCharge is greater than or equal to
        defaultServiceOrderFiltering(
            "serviceCharge.greaterThanOrEqual=" + DEFAULT_SERVICE_CHARGE,
            "serviceCharge.greaterThanOrEqual=" + UPDATED_SERVICE_CHARGE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByServiceChargeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceCharge is less than or equal to
        defaultServiceOrderFiltering(
            "serviceCharge.lessThanOrEqual=" + DEFAULT_SERVICE_CHARGE,
            "serviceCharge.lessThanOrEqual=" + SMALLER_SERVICE_CHARGE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByServiceChargeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceCharge is less than
        defaultServiceOrderFiltering(
            "serviceCharge.lessThan=" + UPDATED_SERVICE_CHARGE,
            "serviceCharge.lessThan=" + DEFAULT_SERVICE_CHARGE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByServiceChargeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where serviceCharge is greater than
        defaultServiceOrderFiltering(
            "serviceCharge.greaterThan=" + SMALLER_SERVICE_CHARGE,
            "serviceCharge.greaterThan=" + DEFAULT_SERVICE_CHARGE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tax equals to
        defaultServiceOrderFiltering("tax.equals=" + DEFAULT_TAX, "tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tax in
        defaultServiceOrderFiltering("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX, "tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tax is not null
        defaultServiceOrderFiltering("tax.specified=true", "tax.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tax is greater than or equal to
        defaultServiceOrderFiltering("tax.greaterThanOrEqual=" + DEFAULT_TAX, "tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tax is less than or equal to
        defaultServiceOrderFiltering("tax.lessThanOrEqual=" + DEFAULT_TAX, "tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tax is less than
        defaultServiceOrderFiltering("tax.lessThan=" + UPDATED_TAX, "tax.lessThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where tax is greater than
        defaultServiceOrderFiltering("tax.greaterThan=" + SMALLER_TAX, "tax.greaterThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareChargesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareCharges equals to
        defaultServiceOrderFiltering(
            "totalSpareCharges.equals=" + DEFAULT_TOTAL_SPARE_CHARGES,
            "totalSpareCharges.equals=" + UPDATED_TOTAL_SPARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareChargesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareCharges in
        defaultServiceOrderFiltering(
            "totalSpareCharges.in=" + DEFAULT_TOTAL_SPARE_CHARGES + "," + UPDATED_TOTAL_SPARE_CHARGES,
            "totalSpareCharges.in=" + UPDATED_TOTAL_SPARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareChargesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareCharges is not null
        defaultServiceOrderFiltering("totalSpareCharges.specified=true", "totalSpareCharges.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareChargesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareCharges is greater than or equal to
        defaultServiceOrderFiltering(
            "totalSpareCharges.greaterThanOrEqual=" + DEFAULT_TOTAL_SPARE_CHARGES,
            "totalSpareCharges.greaterThanOrEqual=" + UPDATED_TOTAL_SPARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareChargesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareCharges is less than or equal to
        defaultServiceOrderFiltering(
            "totalSpareCharges.lessThanOrEqual=" + DEFAULT_TOTAL_SPARE_CHARGES,
            "totalSpareCharges.lessThanOrEqual=" + SMALLER_TOTAL_SPARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareChargesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareCharges is less than
        defaultServiceOrderFiltering(
            "totalSpareCharges.lessThan=" + UPDATED_TOTAL_SPARE_CHARGES,
            "totalSpareCharges.lessThan=" + DEFAULT_TOTAL_SPARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareChargesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareCharges is greater than
        defaultServiceOrderFiltering(
            "totalSpareCharges.greaterThan=" + SMALLER_TOTAL_SPARE_CHARGES,
            "totalSpareCharges.greaterThan=" + DEFAULT_TOTAL_SPARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareTax equals to
        defaultServiceOrderFiltering("totalSpareTax.equals=" + DEFAULT_TOTAL_SPARE_TAX, "totalSpareTax.equals=" + UPDATED_TOTAL_SPARE_TAX);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareTax in
        defaultServiceOrderFiltering(
            "totalSpareTax.in=" + DEFAULT_TOTAL_SPARE_TAX + "," + UPDATED_TOTAL_SPARE_TAX,
            "totalSpareTax.in=" + UPDATED_TOTAL_SPARE_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareTax is not null
        defaultServiceOrderFiltering("totalSpareTax.specified=true", "totalSpareTax.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareTax is greater than or equal to
        defaultServiceOrderFiltering(
            "totalSpareTax.greaterThanOrEqual=" + DEFAULT_TOTAL_SPARE_TAX,
            "totalSpareTax.greaterThanOrEqual=" + UPDATED_TOTAL_SPARE_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareTax is less than or equal to
        defaultServiceOrderFiltering(
            "totalSpareTax.lessThanOrEqual=" + DEFAULT_TOTAL_SPARE_TAX,
            "totalSpareTax.lessThanOrEqual=" + SMALLER_TOTAL_SPARE_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareTax is less than
        defaultServiceOrderFiltering(
            "totalSpareTax.lessThan=" + UPDATED_TOTAL_SPARE_TAX,
            "totalSpareTax.lessThan=" + DEFAULT_TOTAL_SPARE_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalSpareTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalSpareTax is greater than
        defaultServiceOrderFiltering(
            "totalSpareTax.greaterThan=" + SMALLER_TOTAL_SPARE_TAX,
            "totalSpareTax.greaterThan=" + DEFAULT_TOTAL_SPARE_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalChargesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalCharges equals to
        defaultServiceOrderFiltering("totalCharges.equals=" + DEFAULT_TOTAL_CHARGES, "totalCharges.equals=" + UPDATED_TOTAL_CHARGES);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalChargesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalCharges in
        defaultServiceOrderFiltering(
            "totalCharges.in=" + DEFAULT_TOTAL_CHARGES + "," + UPDATED_TOTAL_CHARGES,
            "totalCharges.in=" + UPDATED_TOTAL_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalChargesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalCharges is not null
        defaultServiceOrderFiltering("totalCharges.specified=true", "totalCharges.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalChargesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalCharges is greater than or equal to
        defaultServiceOrderFiltering(
            "totalCharges.greaterThanOrEqual=" + DEFAULT_TOTAL_CHARGES,
            "totalCharges.greaterThanOrEqual=" + UPDATED_TOTAL_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalChargesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalCharges is less than or equal to
        defaultServiceOrderFiltering(
            "totalCharges.lessThanOrEqual=" + DEFAULT_TOTAL_CHARGES,
            "totalCharges.lessThanOrEqual=" + SMALLER_TOTAL_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalChargesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalCharges is less than
        defaultServiceOrderFiltering("totalCharges.lessThan=" + UPDATED_TOTAL_CHARGES, "totalCharges.lessThan=" + DEFAULT_TOTAL_CHARGES);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalChargesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalCharges is greater than
        defaultServiceOrderFiltering(
            "totalCharges.greaterThan=" + SMALLER_TOTAL_CHARGES,
            "totalCharges.greaterThan=" + DEFAULT_TOTAL_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalPaymentDoneIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalPaymentDone equals to
        defaultServiceOrderFiltering(
            "totalPaymentDone.equals=" + DEFAULT_TOTAL_PAYMENT_DONE,
            "totalPaymentDone.equals=" + UPDATED_TOTAL_PAYMENT_DONE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalPaymentDoneIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalPaymentDone in
        defaultServiceOrderFiltering(
            "totalPaymentDone.in=" + DEFAULT_TOTAL_PAYMENT_DONE + "," + UPDATED_TOTAL_PAYMENT_DONE,
            "totalPaymentDone.in=" + UPDATED_TOTAL_PAYMENT_DONE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalPaymentDoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalPaymentDone is not null
        defaultServiceOrderFiltering("totalPaymentDone.specified=true", "totalPaymentDone.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalPaymentDoneIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalPaymentDone is greater than or equal to
        defaultServiceOrderFiltering(
            "totalPaymentDone.greaterThanOrEqual=" + DEFAULT_TOTAL_PAYMENT_DONE,
            "totalPaymentDone.greaterThanOrEqual=" + UPDATED_TOTAL_PAYMENT_DONE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalPaymentDoneIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalPaymentDone is less than or equal to
        defaultServiceOrderFiltering(
            "totalPaymentDone.lessThanOrEqual=" + DEFAULT_TOTAL_PAYMENT_DONE,
            "totalPaymentDone.lessThanOrEqual=" + SMALLER_TOTAL_PAYMENT_DONE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalPaymentDoneIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalPaymentDone is less than
        defaultServiceOrderFiltering(
            "totalPaymentDone.lessThan=" + UPDATED_TOTAL_PAYMENT_DONE,
            "totalPaymentDone.lessThan=" + DEFAULT_TOTAL_PAYMENT_DONE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByTotalPaymentDoneIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where totalPaymentDone is greater than
        defaultServiceOrderFiltering(
            "totalPaymentDone.greaterThan=" + SMALLER_TOTAL_PAYMENT_DONE,
            "totalPaymentDone.greaterThan=" + DEFAULT_TOTAL_PAYMENT_DONE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCustomerInvoicePathIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where customerInvoicePath equals to
        defaultServiceOrderFiltering(
            "customerInvoicePath.equals=" + DEFAULT_CUSTOMER_INVOICE_PATH,
            "customerInvoicePath.equals=" + UPDATED_CUSTOMER_INVOICE_PATH
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCustomerInvoicePathIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where customerInvoicePath in
        defaultServiceOrderFiltering(
            "customerInvoicePath.in=" + DEFAULT_CUSTOMER_INVOICE_PATH + "," + UPDATED_CUSTOMER_INVOICE_PATH,
            "customerInvoicePath.in=" + UPDATED_CUSTOMER_INVOICE_PATH
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCustomerInvoicePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where customerInvoicePath is not null
        defaultServiceOrderFiltering("customerInvoicePath.specified=true", "customerInvoicePath.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCustomerInvoicePathContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where customerInvoicePath contains
        defaultServiceOrderFiltering(
            "customerInvoicePath.contains=" + DEFAULT_CUSTOMER_INVOICE_PATH,
            "customerInvoicePath.contains=" + UPDATED_CUSTOMER_INVOICE_PATH
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCustomerInvoicePathNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where customerInvoicePath does not contain
        defaultServiceOrderFiltering(
            "customerInvoicePath.doesNotContain=" + UPDATED_CUSTOMER_INVOICE_PATH,
            "customerInvoicePath.doesNotContain=" + DEFAULT_CUSTOMER_INVOICE_PATH
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByNpsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where nps equals to
        defaultServiceOrderFiltering("nps.equals=" + DEFAULT_NPS, "nps.equals=" + UPDATED_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByNpsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where nps in
        defaultServiceOrderFiltering("nps.in=" + DEFAULT_NPS + "," + UPDATED_NPS, "nps.in=" + UPDATED_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByNpsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where nps is not null
        defaultServiceOrderFiltering("nps.specified=true", "nps.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByNpsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where nps is greater than or equal to
        defaultServiceOrderFiltering("nps.greaterThanOrEqual=" + DEFAULT_NPS, "nps.greaterThanOrEqual=" + UPDATED_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByNpsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where nps is less than or equal to
        defaultServiceOrderFiltering("nps.lessThanOrEqual=" + DEFAULT_NPS, "nps.lessThanOrEqual=" + SMALLER_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByNpsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where nps is less than
        defaultServiceOrderFiltering("nps.lessThan=" + UPDATED_NPS, "nps.lessThan=" + DEFAULT_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByNpsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where nps is greater than
        defaultServiceOrderFiltering("nps.greaterThan=" + SMALLER_NPS, "nps.greaterThan=" + DEFAULT_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where priority equals to
        defaultServiceOrderFiltering("priority.equals=" + DEFAULT_PRIORITY, "priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where priority in
        defaultServiceOrderFiltering("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY, "priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where priority is not null
        defaultServiceOrderFiltering("priority.specified=true", "priority.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where priority is greater than or equal to
        defaultServiceOrderFiltering("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY, "priority.greaterThanOrEqual=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where priority is less than or equal to
        defaultServiceOrderFiltering("priority.lessThanOrEqual=" + DEFAULT_PRIORITY, "priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where priority is less than
        defaultServiceOrderFiltering("priority.lessThan=" + UPDATED_PRIORITY, "priority.lessThan=" + DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where priority is greater than
        defaultServiceOrderFiltering("priority.greaterThan=" + SMALLER_PRIORITY, "priority.greaterThan=" + DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where isActive equals to
        defaultServiceOrderFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where isActive in
        defaultServiceOrderFiltering("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE, "isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where isActive is not null
        defaultServiceOrderFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where createddBy equals to
        defaultServiceOrderFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where createddBy in
        defaultServiceOrderFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where createddBy is not null
        defaultServiceOrderFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where createddBy contains
        defaultServiceOrderFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where createddBy does not contain
        defaultServiceOrderFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where createdTime equals to
        defaultServiceOrderFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where createdTime in
        defaultServiceOrderFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where createdTime is not null
        defaultServiceOrderFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where updatedBy equals to
        defaultServiceOrderFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where updatedBy in
        defaultServiceOrderFiltering("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY, "updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where updatedBy is not null
        defaultServiceOrderFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where updatedBy contains
        defaultServiceOrderFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where updatedBy does not contain
        defaultServiceOrderFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where updatedTime equals to
        defaultServiceOrderFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrdersByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where updatedTime in
        defaultServiceOrderFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrdersByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        // Get all the serviceOrderList where updatedTime is not null
        defaultServiceOrderFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrdersByCustomerIsEqualToSomething() throws Exception {
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            serviceOrderRepository.saveAndFlush(serviceOrder);
            customer = CustomerResourceIT.createEntity();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        em.persist(customer);
        em.flush();
        serviceOrder.setCustomer(customer);
        serviceOrderRepository.saveAndFlush(serviceOrder);
        Long customerId = customer.getId();
        // Get all the serviceOrderList where customer equals to customerId
        defaultServiceOrderShouldBeFound("customerId.equals=" + customerId);

        // Get all the serviceOrderList where customer equals to (customerId + 1)
        defaultServiceOrderShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    @Test
    @Transactional
    void getAllServiceOrdersByServiceMasterIsEqualToSomething() throws Exception {
        ServiceOrderMaster serviceMaster;
        if (TestUtil.findAll(em, ServiceOrderMaster.class).isEmpty()) {
            serviceOrderRepository.saveAndFlush(serviceOrder);
            serviceMaster = ServiceOrderMasterResourceIT.createEntity();
        } else {
            serviceMaster = TestUtil.findAll(em, ServiceOrderMaster.class).get(0);
        }
        em.persist(serviceMaster);
        em.flush();
        serviceOrder.setServiceMaster(serviceMaster);
        serviceOrderRepository.saveAndFlush(serviceOrder);
        Long serviceMasterId = serviceMaster.getId();
        // Get all the serviceOrderList where serviceMaster equals to serviceMasterId
        defaultServiceOrderShouldBeFound("serviceMasterId.equals=" + serviceMasterId);

        // Get all the serviceOrderList where serviceMaster equals to (serviceMasterId + 1)
        defaultServiceOrderShouldNotBeFound("serviceMasterId.equals=" + (serviceMasterId + 1));
    }

    @Test
    @Transactional
    void getAllServiceOrdersByArticleIsEqualToSomething() throws Exception {
        Article article;
        if (TestUtil.findAll(em, Article.class).isEmpty()) {
            serviceOrderRepository.saveAndFlush(serviceOrder);
            article = ArticleResourceIT.createEntity();
        } else {
            article = TestUtil.findAll(em, Article.class).get(0);
        }
        em.persist(article);
        em.flush();
        serviceOrder.setArticle(article);
        serviceOrderRepository.saveAndFlush(serviceOrder);
        Long articleId = article.getId();
        // Get all the serviceOrderList where article equals to articleId
        defaultServiceOrderShouldBeFound("articleId.equals=" + articleId);

        // Get all the serviceOrderList where article equals to (articleId + 1)
        defaultServiceOrderShouldNotBeFound("articleId.equals=" + (articleId + 1));
    }

    @Test
    @Transactional
    void getAllServiceOrdersByAddressIsEqualToSomething() throws Exception {
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            serviceOrderRepository.saveAndFlush(serviceOrder);
            address = AddressResourceIT.createEntity();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        serviceOrder.setAddress(address);
        serviceOrderRepository.saveAndFlush(serviceOrder);
        Long addressId = address.getId();
        // Get all the serviceOrderList where address equals to addressId
        defaultServiceOrderShouldBeFound("addressId.equals=" + addressId);

        // Get all the serviceOrderList where address equals to (addressId + 1)
        defaultServiceOrderShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    private void defaultServiceOrderFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultServiceOrderShouldBeFound(shouldBeFound);
        defaultServiceOrderShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceOrderShouldBeFound(String filter) throws Exception {
        restServiceOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].inWarranty").value(hasItem(DEFAULT_IN_WARRANTY)))
            .andExpect(jsonPath("$.[*].isFree").value(hasItem(DEFAULT_IS_FREE)))
            .andExpect(jsonPath("$.[*].isSpareNeeded").value(hasItem(DEFAULT_IS_SPARE_NEEDED)))
            .andExpect(jsonPath("$.[*].confirmedTime").value(hasItem(DEFAULT_CONFIRMED_TIME.toString())))
            .andExpect(jsonPath("$.[*].closedTime").value(hasItem(DEFAULT_CLOSED_TIME.toString())))
            .andExpect(jsonPath("$.[*].serviceCharge").value(hasItem(DEFAULT_SERVICE_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalSpareCharges").value(hasItem(DEFAULT_TOTAL_SPARE_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].totalSpareTax").value(hasItem(DEFAULT_TOTAL_SPARE_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCharges").value(hasItem(DEFAULT_TOTAL_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPaymentDone").value(hasItem(DEFAULT_TOTAL_PAYMENT_DONE.doubleValue())))
            .andExpect(jsonPath("$.[*].customerInvoicePath").value(hasItem(DEFAULT_CUSTOMER_INVOICE_PATH)))
            .andExpect(jsonPath("$.[*].nps").value(hasItem(DEFAULT_NPS.doubleValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restServiceOrderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceOrderShouldNotBeFound(String filter) throws Exception {
        restServiceOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceOrderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServiceOrder() throws Exception {
        // Get the serviceOrder
        restServiceOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceOrder() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrder
        ServiceOrder updatedServiceOrder = serviceOrderRepository.findById(serviceOrder.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServiceOrder are not directly saved in db
        em.detach(updatedServiceOrder);
        updatedServiceOrder
            .description(UPDATED_DESCRIPTION)
            .orderType(UPDATED_ORDER_TYPE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .inWarranty(UPDATED_IN_WARRANTY)
            .isFree(UPDATED_IS_FREE)
            .isSpareNeeded(UPDATED_IS_SPARE_NEEDED)
            .confirmedTime(UPDATED_CONFIRMED_TIME)
            .closedTime(UPDATED_CLOSED_TIME)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .tax(UPDATED_TAX)
            .totalSpareCharges(UPDATED_TOTAL_SPARE_CHARGES)
            .totalSpareTax(UPDATED_TOTAL_SPARE_TAX)
            .totalCharges(UPDATED_TOTAL_CHARGES)
            .totalPaymentDone(UPDATED_TOTAL_PAYMENT_DONE)
            .customerInvoicePath(UPDATED_CUSTOMER_INVOICE_PATH)
            .nps(UPDATED_NPS)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(updatedServiceOrder);

        restServiceOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceOrderToMatchAllProperties(updatedServiceOrder);
    }

    @Test
    @Transactional
    void putNonExistingServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceOrderWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrder using partial update
        ServiceOrder partialUpdatedServiceOrder = new ServiceOrder();
        partialUpdatedServiceOrder.setId(serviceOrder.getId());

        partialUpdatedServiceOrder
            .orderStatus(UPDATED_ORDER_STATUS)
            .inWarranty(UPDATED_IN_WARRANTY)
            .isSpareNeeded(UPDATED_IS_SPARE_NEEDED)
            .closedTime(UPDATED_CLOSED_TIME)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .tax(UPDATED_TAX)
            .totalSpareTax(UPDATED_TOTAL_SPARE_TAX)
            .totalPaymentDone(UPDATED_TOTAL_PAYMENT_DONE)
            .nps(UPDATED_NPS)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY);

        restServiceOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrder))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceOrder, serviceOrder),
            getPersistedServiceOrder(serviceOrder)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceOrderWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrder using partial update
        ServiceOrder partialUpdatedServiceOrder = new ServiceOrder();
        partialUpdatedServiceOrder.setId(serviceOrder.getId());

        partialUpdatedServiceOrder
            .description(UPDATED_DESCRIPTION)
            .orderType(UPDATED_ORDER_TYPE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .inWarranty(UPDATED_IN_WARRANTY)
            .isFree(UPDATED_IS_FREE)
            .isSpareNeeded(UPDATED_IS_SPARE_NEEDED)
            .confirmedTime(UPDATED_CONFIRMED_TIME)
            .closedTime(UPDATED_CLOSED_TIME)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .tax(UPDATED_TAX)
            .totalSpareCharges(UPDATED_TOTAL_SPARE_CHARGES)
            .totalSpareTax(UPDATED_TOTAL_SPARE_TAX)
            .totalCharges(UPDATED_TOTAL_CHARGES)
            .totalPaymentDone(UPDATED_TOTAL_PAYMENT_DONE)
            .customerInvoicePath(UPDATED_CUSTOMER_INVOICE_PATH)
            .nps(UPDATED_NPS)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrder))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrder in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderUpdatableFieldsEquals(partialUpdatedServiceOrder, getPersistedServiceOrder(partialUpdatedServiceOrder));
    }

    @Test
    @Transactional
    void patchNonExistingServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrder.setId(longCount.incrementAndGet());

        // Create the ServiceOrder
        ServiceOrderDTO serviceOrderDTO = serviceOrderMapper.toDto(serviceOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceOrderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrder in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceOrder() throws Exception {
        // Initialize the database
        insertedServiceOrder = serviceOrderRepository.saveAndFlush(serviceOrder);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceOrder
        restServiceOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceOrderRepository.count();
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

    protected ServiceOrder getPersistedServiceOrder(ServiceOrder serviceOrder) {
        return serviceOrderRepository.findById(serviceOrder.getId()).orElseThrow();
    }

    protected void assertPersistedServiceOrderToMatchAllProperties(ServiceOrder expectedServiceOrder) {
        assertServiceOrderAllPropertiesEquals(expectedServiceOrder, getPersistedServiceOrder(expectedServiceOrder));
    }

    protected void assertPersistedServiceOrderToMatchUpdatableProperties(ServiceOrder expectedServiceOrder) {
        assertServiceOrderAllUpdatablePropertiesEquals(expectedServiceOrder, getPersistedServiceOrder(expectedServiceOrder));
    }
}
