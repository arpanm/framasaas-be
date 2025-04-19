package com.framasaas.web.rest;

import static com.framasaas.domain.ServiceOrderFranchiseAssignmentAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Franchise;
import com.framasaas.domain.ServiceOrder;
import com.framasaas.domain.ServiceOrderFranchiseAssignment;
import com.framasaas.domain.enumeration.ServiceOrderAssignmentStatus;
import com.framasaas.repository.ServiceOrderFranchiseAssignmentRepository;
import com.framasaas.service.dto.ServiceOrderFranchiseAssignmentDTO;
import com.framasaas.service.mapper.ServiceOrderFranchiseAssignmentMapper;
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
 * Integration tests for the {@link ServiceOrderFranchiseAssignmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceOrderFranchiseAssignmentResourceIT {

    private static final ServiceOrderAssignmentStatus DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS = ServiceOrderAssignmentStatus.ASSIGNED;
    private static final ServiceOrderAssignmentStatus UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS = ServiceOrderAssignmentStatus.MOVEDBACK;

    private static final Long DEFAULT_NPS = 1L;
    private static final Long UPDATED_NPS = 2L;
    private static final Long SMALLER_NPS = 1L - 1L;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Instant DEFAULT_ASSIGNED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ASSIGNED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MOVED_BACK_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MOVED_BACK_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VISIT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VISIT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SPARE_ORDER_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SPARE_ORDER_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SPARE_DELIVERY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SPARE_DELIVERY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_COMPLETED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPLETED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_FRANCHISE_COMMISION = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISION = 2F;
    private static final Float SMALLER_FRANCHISE_COMMISION = 1F - 1F;

    private static final Float DEFAULT_FRANCHISE_COMMISION_TAX = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISION_TAX = 2F;
    private static final Float SMALLER_FRANCHISE_COMMISION_TAX = 1F - 1F;

    private static final String DEFAULT_FRANCHISE_INVOICE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FRANCHISE_INVOICE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/service-order-franchise-assignments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceOrderFranchiseAssignmentRepository serviceOrderFranchiseAssignmentRepository;

    @Autowired
    private ServiceOrderFranchiseAssignmentMapper serviceOrderFranchiseAssignmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOrderFranchiseAssignmentMockMvc;

    private ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment;

    private ServiceOrderFranchiseAssignment insertedServiceOrderFranchiseAssignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrderFranchiseAssignment createEntity() {
        return new ServiceOrderFranchiseAssignment()
            .serviceOrderAssignmentStatus(DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .nps(DEFAULT_NPS)
            .isActive(DEFAULT_IS_ACTIVE)
            .assignedTime(DEFAULT_ASSIGNED_TIME)
            .movedBackTime(DEFAULT_MOVED_BACK_TIME)
            .visitTime(DEFAULT_VISIT_TIME)
            .spareOrderTime(DEFAULT_SPARE_ORDER_TIME)
            .spareDeliveryTime(DEFAULT_SPARE_DELIVERY_TIME)
            .completedTime(DEFAULT_COMPLETED_TIME)
            .franchiseCommision(DEFAULT_FRANCHISE_COMMISION)
            .franchiseCommisionTax(DEFAULT_FRANCHISE_COMMISION_TAX)
            .franchiseInvoicePath(DEFAULT_FRANCHISE_INVOICE_PATH)
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
    public static ServiceOrderFranchiseAssignment createUpdatedEntity() {
        return new ServiceOrderFranchiseAssignment()
            .serviceOrderAssignmentStatus(UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .nps(UPDATED_NPS)
            .isActive(UPDATED_IS_ACTIVE)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .movedBackTime(UPDATED_MOVED_BACK_TIME)
            .visitTime(UPDATED_VISIT_TIME)
            .spareOrderTime(UPDATED_SPARE_ORDER_TIME)
            .spareDeliveryTime(UPDATED_SPARE_DELIVERY_TIME)
            .completedTime(UPDATED_COMPLETED_TIME)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseCommisionTax(UPDATED_FRANCHISE_COMMISION_TAX)
            .franchiseInvoicePath(UPDATED_FRANCHISE_INVOICE_PATH)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        serviceOrderFranchiseAssignment = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedServiceOrderFranchiseAssignment != null) {
            serviceOrderFranchiseAssignmentRepository.delete(insertedServiceOrderFranchiseAssignment);
            insertedServiceOrderFranchiseAssignment = null;
        }
    }

    @Test
    @Transactional
    void createServiceOrderFranchiseAssignment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceOrderFranchiseAssignment
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );
        var returnedServiceOrderFranchiseAssignmentDTO = om.readValue(
            restServiceOrderFranchiseAssignmentMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderFranchiseAssignmentDTO.class
        );

        // Validate the ServiceOrderFranchiseAssignment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentMapper.toEntity(
            returnedServiceOrderFranchiseAssignmentDTO
        );
        assertServiceOrderFranchiseAssignmentUpdatableFieldsEquals(
            returnedServiceOrderFranchiseAssignment,
            getPersistedServiceOrderFranchiseAssignment(returnedServiceOrderFranchiseAssignment)
        );

        insertedServiceOrderFranchiseAssignment = returnedServiceOrderFranchiseAssignment;
    }

    @Test
    @Transactional
    void createServiceOrderFranchiseAssignmentWithExistingId() throws Exception {
        // Create the ServiceOrderFranchiseAssignment with an existing ID
        serviceOrderFranchiseAssignment.setId(1L);
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderFranchiseAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkServiceOrderAssignmentStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderFranchiseAssignment.setServiceOrderAssignmentStatus(null);

        // Create the ServiceOrderFranchiseAssignment, which fails.
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderFranchiseAssignment.setCreateddBy(null);

        // Create the ServiceOrderFranchiseAssignment, which fails.
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderFranchiseAssignment.setCreatedTime(null);

        // Create the ServiceOrderFranchiseAssignment, which fails.
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderFranchiseAssignment.setUpdatedBy(null);

        // Create the ServiceOrderFranchiseAssignment, which fails.
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderFranchiseAssignment.setUpdatedTime(null);

        // Create the ServiceOrderFranchiseAssignment, which fails.
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignments() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderFranchiseAssignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOrderAssignmentStatus").value(hasItem(DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].nps").value(hasItem(DEFAULT_NPS.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].assignedTime").value(hasItem(DEFAULT_ASSIGNED_TIME.toString())))
            .andExpect(jsonPath("$.[*].movedBackTime").value(hasItem(DEFAULT_MOVED_BACK_TIME.toString())))
            .andExpect(jsonPath("$.[*].visitTime").value(hasItem(DEFAULT_VISIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].spareOrderTime").value(hasItem(DEFAULT_SPARE_ORDER_TIME.toString())))
            .andExpect(jsonPath("$.[*].spareDeliveryTime").value(hasItem(DEFAULT_SPARE_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].completedTime").value(hasItem(DEFAULT_COMPLETED_TIME.toString())))
            .andExpect(jsonPath("$.[*].franchiseCommision").value(hasItem(DEFAULT_FRANCHISE_COMMISION.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommisionTax").value(hasItem(DEFAULT_FRANCHISE_COMMISION_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseInvoicePath").value(hasItem(DEFAULT_FRANCHISE_INVOICE_PATH)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getServiceOrderFranchiseAssignment() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get the serviceOrderFranchiseAssignment
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceOrderFranchiseAssignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrderFranchiseAssignment.getId().intValue()))
            .andExpect(jsonPath("$.serviceOrderAssignmentStatus").value(DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS.toString()))
            .andExpect(jsonPath("$.nps").value(DEFAULT_NPS.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.assignedTime").value(DEFAULT_ASSIGNED_TIME.toString()))
            .andExpect(jsonPath("$.movedBackTime").value(DEFAULT_MOVED_BACK_TIME.toString()))
            .andExpect(jsonPath("$.visitTime").value(DEFAULT_VISIT_TIME.toString()))
            .andExpect(jsonPath("$.spareOrderTime").value(DEFAULT_SPARE_ORDER_TIME.toString()))
            .andExpect(jsonPath("$.spareDeliveryTime").value(DEFAULT_SPARE_DELIVERY_TIME.toString()))
            .andExpect(jsonPath("$.completedTime").value(DEFAULT_COMPLETED_TIME.toString()))
            .andExpect(jsonPath("$.franchiseCommision").value(DEFAULT_FRANCHISE_COMMISION.doubleValue()))
            .andExpect(jsonPath("$.franchiseCommisionTax").value(DEFAULT_FRANCHISE_COMMISION_TAX.doubleValue()))
            .andExpect(jsonPath("$.franchiseInvoicePath").value(DEFAULT_FRANCHISE_INVOICE_PATH))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getServiceOrderFranchiseAssignmentsByIdFiltering() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        Long id = serviceOrderFranchiseAssignment.getId();

        defaultServiceOrderFranchiseAssignmentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultServiceOrderFranchiseAssignmentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultServiceOrderFranchiseAssignmentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByServiceOrderAssignmentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where serviceOrderAssignmentStatus equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "serviceOrderAssignmentStatus.equals=" + DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS,
            "serviceOrderAssignmentStatus.equals=" + UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByServiceOrderAssignmentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where serviceOrderAssignmentStatus in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "serviceOrderAssignmentStatus.in=" + DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS + "," + UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS,
            "serviceOrderAssignmentStatus.in=" + UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByServiceOrderAssignmentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where serviceOrderAssignmentStatus is not null
        defaultServiceOrderFranchiseAssignmentFiltering(
            "serviceOrderAssignmentStatus.specified=true",
            "serviceOrderAssignmentStatus.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByNpsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where nps equals to
        defaultServiceOrderFranchiseAssignmentFiltering("nps.equals=" + DEFAULT_NPS, "nps.equals=" + UPDATED_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByNpsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where nps in
        defaultServiceOrderFranchiseAssignmentFiltering("nps.in=" + DEFAULT_NPS + "," + UPDATED_NPS, "nps.in=" + UPDATED_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByNpsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where nps is not null
        defaultServiceOrderFranchiseAssignmentFiltering("nps.specified=true", "nps.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByNpsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where nps is greater than or equal to
        defaultServiceOrderFranchiseAssignmentFiltering("nps.greaterThanOrEqual=" + DEFAULT_NPS, "nps.greaterThanOrEqual=" + UPDATED_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByNpsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where nps is less than or equal to
        defaultServiceOrderFranchiseAssignmentFiltering("nps.lessThanOrEqual=" + DEFAULT_NPS, "nps.lessThanOrEqual=" + SMALLER_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByNpsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where nps is less than
        defaultServiceOrderFranchiseAssignmentFiltering("nps.lessThan=" + UPDATED_NPS, "nps.lessThan=" + DEFAULT_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByNpsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where nps is greater than
        defaultServiceOrderFranchiseAssignmentFiltering("nps.greaterThan=" + SMALLER_NPS, "nps.greaterThan=" + DEFAULT_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where isActive equals to
        defaultServiceOrderFranchiseAssignmentFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where isActive in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE,
            "isActive.in=" + UPDATED_IS_ACTIVE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where isActive is not null
        defaultServiceOrderFranchiseAssignmentFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByAssignedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where assignedTime equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "assignedTime.equals=" + DEFAULT_ASSIGNED_TIME,
            "assignedTime.equals=" + UPDATED_ASSIGNED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByAssignedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where assignedTime in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "assignedTime.in=" + DEFAULT_ASSIGNED_TIME + "," + UPDATED_ASSIGNED_TIME,
            "assignedTime.in=" + UPDATED_ASSIGNED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByAssignedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where assignedTime is not null
        defaultServiceOrderFranchiseAssignmentFiltering("assignedTime.specified=true", "assignedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByMovedBackTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where movedBackTime equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "movedBackTime.equals=" + DEFAULT_MOVED_BACK_TIME,
            "movedBackTime.equals=" + UPDATED_MOVED_BACK_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByMovedBackTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where movedBackTime in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "movedBackTime.in=" + DEFAULT_MOVED_BACK_TIME + "," + UPDATED_MOVED_BACK_TIME,
            "movedBackTime.in=" + UPDATED_MOVED_BACK_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByMovedBackTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where movedBackTime is not null
        defaultServiceOrderFranchiseAssignmentFiltering("movedBackTime.specified=true", "movedBackTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByVisitTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where visitTime equals to
        defaultServiceOrderFranchiseAssignmentFiltering("visitTime.equals=" + DEFAULT_VISIT_TIME, "visitTime.equals=" + UPDATED_VISIT_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByVisitTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where visitTime in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "visitTime.in=" + DEFAULT_VISIT_TIME + "," + UPDATED_VISIT_TIME,
            "visitTime.in=" + UPDATED_VISIT_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByVisitTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where visitTime is not null
        defaultServiceOrderFranchiseAssignmentFiltering("visitTime.specified=true", "visitTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsBySpareOrderTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where spareOrderTime equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "spareOrderTime.equals=" + DEFAULT_SPARE_ORDER_TIME,
            "spareOrderTime.equals=" + UPDATED_SPARE_ORDER_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsBySpareOrderTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where spareOrderTime in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "spareOrderTime.in=" + DEFAULT_SPARE_ORDER_TIME + "," + UPDATED_SPARE_ORDER_TIME,
            "spareOrderTime.in=" + UPDATED_SPARE_ORDER_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsBySpareOrderTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where spareOrderTime is not null
        defaultServiceOrderFranchiseAssignmentFiltering("spareOrderTime.specified=true", "spareOrderTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsBySpareDeliveryTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where spareDeliveryTime equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "spareDeliveryTime.equals=" + DEFAULT_SPARE_DELIVERY_TIME,
            "spareDeliveryTime.equals=" + UPDATED_SPARE_DELIVERY_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsBySpareDeliveryTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where spareDeliveryTime in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "spareDeliveryTime.in=" + DEFAULT_SPARE_DELIVERY_TIME + "," + UPDATED_SPARE_DELIVERY_TIME,
            "spareDeliveryTime.in=" + UPDATED_SPARE_DELIVERY_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsBySpareDeliveryTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where spareDeliveryTime is not null
        defaultServiceOrderFranchiseAssignmentFiltering("spareDeliveryTime.specified=true", "spareDeliveryTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCompletedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where completedTime equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "completedTime.equals=" + DEFAULT_COMPLETED_TIME,
            "completedTime.equals=" + UPDATED_COMPLETED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCompletedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where completedTime in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "completedTime.in=" + DEFAULT_COMPLETED_TIME + "," + UPDATED_COMPLETED_TIME,
            "completedTime.in=" + UPDATED_COMPLETED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCompletedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where completedTime is not null
        defaultServiceOrderFranchiseAssignmentFiltering("completedTime.specified=true", "completedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommision equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommision.equals=" + DEFAULT_FRANCHISE_COMMISION,
            "franchiseCommision.equals=" + UPDATED_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommision in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommision.in=" + DEFAULT_FRANCHISE_COMMISION + "," + UPDATED_FRANCHISE_COMMISION,
            "franchiseCommision.in=" + UPDATED_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommision is not null
        defaultServiceOrderFranchiseAssignmentFiltering("franchiseCommision.specified=true", "franchiseCommision.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommision is greater than or equal to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommision.greaterThanOrEqual=" + DEFAULT_FRANCHISE_COMMISION,
            "franchiseCommision.greaterThanOrEqual=" + UPDATED_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommision is less than or equal to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommision.lessThanOrEqual=" + DEFAULT_FRANCHISE_COMMISION,
            "franchiseCommision.lessThanOrEqual=" + SMALLER_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommision is less than
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommision.lessThan=" + UPDATED_FRANCHISE_COMMISION,
            "franchiseCommision.lessThan=" + DEFAULT_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommision is greater than
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommision.greaterThan=" + SMALLER_FRANCHISE_COMMISION,
            "franchiseCommision.greaterThan=" + DEFAULT_FRANCHISE_COMMISION
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommisionTax equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommisionTax.equals=" + DEFAULT_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.equals=" + UPDATED_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommisionTax in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommisionTax.in=" + DEFAULT_FRANCHISE_COMMISION_TAX + "," + UPDATED_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.in=" + UPDATED_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommisionTax is not null
        defaultServiceOrderFranchiseAssignmentFiltering("franchiseCommisionTax.specified=true", "franchiseCommisionTax.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommisionTax is greater than or equal to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommisionTax.greaterThanOrEqual=" + DEFAULT_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.greaterThanOrEqual=" + UPDATED_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommisionTax is less than or equal to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommisionTax.lessThanOrEqual=" + DEFAULT_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.lessThanOrEqual=" + SMALLER_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommisionTax is less than
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommisionTax.lessThan=" + UPDATED_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.lessThan=" + DEFAULT_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseCommisionTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseCommisionTax is greater than
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseCommisionTax.greaterThan=" + SMALLER_FRANCHISE_COMMISION_TAX,
            "franchiseCommisionTax.greaterThan=" + DEFAULT_FRANCHISE_COMMISION_TAX
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseInvoicePathIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseInvoicePath equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseInvoicePath.equals=" + DEFAULT_FRANCHISE_INVOICE_PATH,
            "franchiseInvoicePath.equals=" + UPDATED_FRANCHISE_INVOICE_PATH
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseInvoicePathIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseInvoicePath in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseInvoicePath.in=" + DEFAULT_FRANCHISE_INVOICE_PATH + "," + UPDATED_FRANCHISE_INVOICE_PATH,
            "franchiseInvoicePath.in=" + UPDATED_FRANCHISE_INVOICE_PATH
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseInvoicePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseInvoicePath is not null
        defaultServiceOrderFranchiseAssignmentFiltering("franchiseInvoicePath.specified=true", "franchiseInvoicePath.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseInvoicePathContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseInvoicePath contains
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseInvoicePath.contains=" + DEFAULT_FRANCHISE_INVOICE_PATH,
            "franchiseInvoicePath.contains=" + UPDATED_FRANCHISE_INVOICE_PATH
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseInvoicePathNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where franchiseInvoicePath does not contain
        defaultServiceOrderFranchiseAssignmentFiltering(
            "franchiseInvoicePath.doesNotContain=" + UPDATED_FRANCHISE_INVOICE_PATH,
            "franchiseInvoicePath.doesNotContain=" + DEFAULT_FRANCHISE_INVOICE_PATH
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where createddBy equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "createddBy.equals=" + DEFAULT_CREATEDD_BY,
            "createddBy.equals=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where createddBy in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where createddBy is not null
        defaultServiceOrderFranchiseAssignmentFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where createddBy contains
        defaultServiceOrderFranchiseAssignmentFiltering(
            "createddBy.contains=" + DEFAULT_CREATEDD_BY,
            "createddBy.contains=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where createddBy does not contain
        defaultServiceOrderFranchiseAssignmentFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where createdTime equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "createdTime.equals=" + DEFAULT_CREATED_TIME,
            "createdTime.equals=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where createdTime in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where createdTime is not null
        defaultServiceOrderFranchiseAssignmentFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where updatedBy equals to
        defaultServiceOrderFranchiseAssignmentFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where updatedBy in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where updatedBy is not null
        defaultServiceOrderFranchiseAssignmentFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where updatedBy contains
        defaultServiceOrderFranchiseAssignmentFiltering(
            "updatedBy.contains=" + DEFAULT_UPDATED_BY,
            "updatedBy.contains=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where updatedBy does not contain
        defaultServiceOrderFranchiseAssignmentFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where updatedTime equals to
        defaultServiceOrderFranchiseAssignmentFiltering(
            "updatedTime.equals=" + DEFAULT_UPDATED_TIME,
            "updatedTime.equals=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where updatedTime in
        defaultServiceOrderFranchiseAssignmentFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        // Get all the serviceOrderFranchiseAssignmentList where updatedTime is not null
        defaultServiceOrderFranchiseAssignmentFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByServiceOrderIsEqualToSomething() throws Exception {
        ServiceOrder serviceOrder;
        if (TestUtil.findAll(em, ServiceOrder.class).isEmpty()) {
            serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);
            serviceOrder = ServiceOrderResourceIT.createEntity();
        } else {
            serviceOrder = TestUtil.findAll(em, ServiceOrder.class).get(0);
        }
        em.persist(serviceOrder);
        em.flush();
        serviceOrderFranchiseAssignment.setServiceOrder(serviceOrder);
        serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);
        Long serviceOrderId = serviceOrder.getId();
        // Get all the serviceOrderFranchiseAssignmentList where serviceOrder equals to serviceOrderId
        defaultServiceOrderFranchiseAssignmentShouldBeFound("serviceOrderId.equals=" + serviceOrderId);

        // Get all the serviceOrderFranchiseAssignmentList where serviceOrder equals to (serviceOrderId + 1)
        defaultServiceOrderFranchiseAssignmentShouldNotBeFound("serviceOrderId.equals=" + (serviceOrderId + 1));
    }

    @Test
    @Transactional
    void getAllServiceOrderFranchiseAssignmentsByFranchiseIsEqualToSomething() throws Exception {
        Franchise franchise;
        if (TestUtil.findAll(em, Franchise.class).isEmpty()) {
            serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);
            franchise = FranchiseResourceIT.createEntity();
        } else {
            franchise = TestUtil.findAll(em, Franchise.class).get(0);
        }
        em.persist(franchise);
        em.flush();
        serviceOrderFranchiseAssignment.setFranchise(franchise);
        serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);
        Long franchiseId = franchise.getId();
        // Get all the serviceOrderFranchiseAssignmentList where franchise equals to franchiseId
        defaultServiceOrderFranchiseAssignmentShouldBeFound("franchiseId.equals=" + franchiseId);

        // Get all the serviceOrderFranchiseAssignmentList where franchise equals to (franchiseId + 1)
        defaultServiceOrderFranchiseAssignmentShouldNotBeFound("franchiseId.equals=" + (franchiseId + 1));
    }

    private void defaultServiceOrderFranchiseAssignmentFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultServiceOrderFranchiseAssignmentShouldBeFound(shouldBeFound);
        defaultServiceOrderFranchiseAssignmentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceOrderFranchiseAssignmentShouldBeFound(String filter) throws Exception {
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderFranchiseAssignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOrderAssignmentStatus").value(hasItem(DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].nps").value(hasItem(DEFAULT_NPS.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].assignedTime").value(hasItem(DEFAULT_ASSIGNED_TIME.toString())))
            .andExpect(jsonPath("$.[*].movedBackTime").value(hasItem(DEFAULT_MOVED_BACK_TIME.toString())))
            .andExpect(jsonPath("$.[*].visitTime").value(hasItem(DEFAULT_VISIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].spareOrderTime").value(hasItem(DEFAULT_SPARE_ORDER_TIME.toString())))
            .andExpect(jsonPath("$.[*].spareDeliveryTime").value(hasItem(DEFAULT_SPARE_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].completedTime").value(hasItem(DEFAULT_COMPLETED_TIME.toString())))
            .andExpect(jsonPath("$.[*].franchiseCommision").value(hasItem(DEFAULT_FRANCHISE_COMMISION.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseCommisionTax").value(hasItem(DEFAULT_FRANCHISE_COMMISION_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].franchiseInvoicePath").value(hasItem(DEFAULT_FRANCHISE_INVOICE_PATH)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceOrderFranchiseAssignmentShouldNotBeFound(String filter) throws Exception {
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServiceOrderFranchiseAssignment() throws Exception {
        // Get the serviceOrderFranchiseAssignment
        restServiceOrderFranchiseAssignmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceOrderFranchiseAssignment() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderFranchiseAssignment
        ServiceOrderFranchiseAssignment updatedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository
            .findById(serviceOrderFranchiseAssignment.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedServiceOrderFranchiseAssignment are not directly saved in db
        em.detach(updatedServiceOrderFranchiseAssignment);
        updatedServiceOrderFranchiseAssignment
            .serviceOrderAssignmentStatus(UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .nps(UPDATED_NPS)
            .isActive(UPDATED_IS_ACTIVE)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .movedBackTime(UPDATED_MOVED_BACK_TIME)
            .visitTime(UPDATED_VISIT_TIME)
            .spareOrderTime(UPDATED_SPARE_ORDER_TIME)
            .spareDeliveryTime(UPDATED_SPARE_DELIVERY_TIME)
            .completedTime(UPDATED_COMPLETED_TIME)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseCommisionTax(UPDATED_FRANCHISE_COMMISION_TAX)
            .franchiseInvoicePath(UPDATED_FRANCHISE_INVOICE_PATH)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            updatedServiceOrderFranchiseAssignment
        );

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderFranchiseAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderFranchiseAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceOrderFranchiseAssignmentToMatchAllProperties(updatedServiceOrderFranchiseAssignment);
    }

    @Test
    @Transactional
    void putNonExistingServiceOrderFranchiseAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFranchiseAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFranchiseAssignment
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderFranchiseAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderFranchiseAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceOrderFranchiseAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFranchiseAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFranchiseAssignment
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderFranchiseAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceOrderFranchiseAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFranchiseAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFranchiseAssignment
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderFranchiseAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceOrderFranchiseAssignmentWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderFranchiseAssignment using partial update
        ServiceOrderFranchiseAssignment partialUpdatedServiceOrderFranchiseAssignment = new ServiceOrderFranchiseAssignment();
        partialUpdatedServiceOrderFranchiseAssignment.setId(serviceOrderFranchiseAssignment.getId());

        partialUpdatedServiceOrderFranchiseAssignment
            .serviceOrderAssignmentStatus(UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .isActive(UPDATED_IS_ACTIVE)
            .movedBackTime(UPDATED_MOVED_BACK_TIME)
            .visitTime(UPDATED_VISIT_TIME)
            .spareOrderTime(UPDATED_SPARE_ORDER_TIME)
            .spareDeliveryTime(UPDATED_SPARE_DELIVERY_TIME)
            .completedTime(UPDATED_COMPLETED_TIME)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseInvoicePath(UPDATED_FRANCHISE_INVOICE_PATH)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderFranchiseAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderFranchiseAssignment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderFranchiseAssignment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderFranchiseAssignmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceOrderFranchiseAssignment, serviceOrderFranchiseAssignment),
            getPersistedServiceOrderFranchiseAssignment(serviceOrderFranchiseAssignment)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceOrderFranchiseAssignmentWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderFranchiseAssignment using partial update
        ServiceOrderFranchiseAssignment partialUpdatedServiceOrderFranchiseAssignment = new ServiceOrderFranchiseAssignment();
        partialUpdatedServiceOrderFranchiseAssignment.setId(serviceOrderFranchiseAssignment.getId());

        partialUpdatedServiceOrderFranchiseAssignment
            .serviceOrderAssignmentStatus(UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .nps(UPDATED_NPS)
            .isActive(UPDATED_IS_ACTIVE)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .movedBackTime(UPDATED_MOVED_BACK_TIME)
            .visitTime(UPDATED_VISIT_TIME)
            .spareOrderTime(UPDATED_SPARE_ORDER_TIME)
            .spareDeliveryTime(UPDATED_SPARE_DELIVERY_TIME)
            .completedTime(UPDATED_COMPLETED_TIME)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseCommisionTax(UPDATED_FRANCHISE_COMMISION_TAX)
            .franchiseInvoicePath(UPDATED_FRANCHISE_INVOICE_PATH)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderFranchiseAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderFranchiseAssignment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderFranchiseAssignment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderFranchiseAssignmentUpdatableFieldsEquals(
            partialUpdatedServiceOrderFranchiseAssignment,
            getPersistedServiceOrderFranchiseAssignment(partialUpdatedServiceOrderFranchiseAssignment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServiceOrderFranchiseAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFranchiseAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFranchiseAssignment
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderFranchiseAssignmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderFranchiseAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceOrderFranchiseAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFranchiseAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFranchiseAssignment
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderFranchiseAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceOrderFranchiseAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFranchiseAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFranchiseAssignment
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = serviceOrderFranchiseAssignmentMapper.toDto(
            serviceOrderFranchiseAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderFranchiseAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceOrderFranchiseAssignment() throws Exception {
        // Initialize the database
        insertedServiceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.saveAndFlush(serviceOrderFranchiseAssignment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceOrderFranchiseAssignment
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceOrderFranchiseAssignment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceOrderFranchiseAssignmentRepository.count();
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

    protected ServiceOrderFranchiseAssignment getPersistedServiceOrderFranchiseAssignment(
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment
    ) {
        return serviceOrderFranchiseAssignmentRepository.findById(serviceOrderFranchiseAssignment.getId()).orElseThrow();
    }

    protected void assertPersistedServiceOrderFranchiseAssignmentToMatchAllProperties(
        ServiceOrderFranchiseAssignment expectedServiceOrderFranchiseAssignment
    ) {
        assertServiceOrderFranchiseAssignmentAllPropertiesEquals(
            expectedServiceOrderFranchiseAssignment,
            getPersistedServiceOrderFranchiseAssignment(expectedServiceOrderFranchiseAssignment)
        );
    }

    protected void assertPersistedServiceOrderFranchiseAssignmentToMatchUpdatableProperties(
        ServiceOrderFranchiseAssignment expectedServiceOrderFranchiseAssignment
    ) {
        assertServiceOrderFranchiseAssignmentAllUpdatablePropertiesEquals(
            expectedServiceOrderFranchiseAssignment,
            getPersistedServiceOrderFranchiseAssignment(expectedServiceOrderFranchiseAssignment)
        );
    }
}
