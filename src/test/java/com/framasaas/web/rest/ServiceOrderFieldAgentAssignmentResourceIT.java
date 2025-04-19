package com.framasaas.web.rest;

import static com.framasaas.domain.ServiceOrderFieldAgentAssignmentAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.ServiceOrderFieldAgentAssignment;
import com.framasaas.domain.enumeration.ServiceOrderAssignmentStatus;
import com.framasaas.repository.ServiceOrderFieldAgentAssignmentRepository;
import com.framasaas.service.dto.ServiceOrderFieldAgentAssignmentDTO;
import com.framasaas.service.mapper.ServiceOrderFieldAgentAssignmentMapper;
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
 * Integration tests for the {@link ServiceOrderFieldAgentAssignmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceOrderFieldAgentAssignmentResourceIT {

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

    private static final Long DEFAULT_COMPLETION_OTP = 1L;
    private static final Long UPDATED_COMPLETION_OTP = 2L;
    private static final Long SMALLER_COMPLETION_OTP = 1L - 1L;

    private static final Long DEFAULT_CANCELLATION_OTP = 1L;
    private static final Long UPDATED_CANCELLATION_OTP = 2L;
    private static final Long SMALLER_CANCELLATION_OTP = 1L - 1L;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/service-order-field-agent-assignments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceOrderFieldAgentAssignmentRepository serviceOrderFieldAgentAssignmentRepository;

    @Autowired
    private ServiceOrderFieldAgentAssignmentMapper serviceOrderFieldAgentAssignmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOrderFieldAgentAssignmentMockMvc;

    private ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment;

    private ServiceOrderFieldAgentAssignment insertedServiceOrderFieldAgentAssignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrderFieldAgentAssignment createEntity() {
        return new ServiceOrderFieldAgentAssignment()
            .serviceOrderAssignmentStatus(DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .nps(DEFAULT_NPS)
            .isActive(DEFAULT_IS_ACTIVE)
            .assignedTime(DEFAULT_ASSIGNED_TIME)
            .movedBackTime(DEFAULT_MOVED_BACK_TIME)
            .visitTime(DEFAULT_VISIT_TIME)
            .spareOrderTime(DEFAULT_SPARE_ORDER_TIME)
            .spareDeliveryTime(DEFAULT_SPARE_DELIVERY_TIME)
            .completedTime(DEFAULT_COMPLETED_TIME)
            .completionOTP(DEFAULT_COMPLETION_OTP)
            .cancellationOTP(DEFAULT_CANCELLATION_OTP)
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
    public static ServiceOrderFieldAgentAssignment createUpdatedEntity() {
        return new ServiceOrderFieldAgentAssignment()
            .serviceOrderAssignmentStatus(UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .nps(UPDATED_NPS)
            .isActive(UPDATED_IS_ACTIVE)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .movedBackTime(UPDATED_MOVED_BACK_TIME)
            .visitTime(UPDATED_VISIT_TIME)
            .spareOrderTime(UPDATED_SPARE_ORDER_TIME)
            .spareDeliveryTime(UPDATED_SPARE_DELIVERY_TIME)
            .completedTime(UPDATED_COMPLETED_TIME)
            .completionOTP(UPDATED_COMPLETION_OTP)
            .cancellationOTP(UPDATED_CANCELLATION_OTP)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        serviceOrderFieldAgentAssignment = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedServiceOrderFieldAgentAssignment != null) {
            serviceOrderFieldAgentAssignmentRepository.delete(insertedServiceOrderFieldAgentAssignment);
            insertedServiceOrderFieldAgentAssignment = null;
        }
    }

    @Test
    @Transactional
    void createServiceOrderFieldAgentAssignment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceOrderFieldAgentAssignment
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );
        var returnedServiceOrderFieldAgentAssignmentDTO = om.readValue(
            restServiceOrderFieldAgentAssignmentMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderFieldAgentAssignmentDTO.class
        );

        // Validate the ServiceOrderFieldAgentAssignment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentMapper.toEntity(
            returnedServiceOrderFieldAgentAssignmentDTO
        );
        assertServiceOrderFieldAgentAssignmentUpdatableFieldsEquals(
            returnedServiceOrderFieldAgentAssignment,
            getPersistedServiceOrderFieldAgentAssignment(returnedServiceOrderFieldAgentAssignment)
        );

        insertedServiceOrderFieldAgentAssignment = returnedServiceOrderFieldAgentAssignment;
    }

    @Test
    @Transactional
    void createServiceOrderFieldAgentAssignmentWithExistingId() throws Exception {
        // Create the ServiceOrderFieldAgentAssignment with an existing ID
        serviceOrderFieldAgentAssignment.setId(1L);
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderFieldAgentAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkServiceOrderAssignmentStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderFieldAgentAssignment.setServiceOrderAssignmentStatus(null);

        // Create the ServiceOrderFieldAgentAssignment, which fails.
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderFieldAgentAssignment.setCreateddBy(null);

        // Create the ServiceOrderFieldAgentAssignment, which fails.
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderFieldAgentAssignment.setCreatedTime(null);

        // Create the ServiceOrderFieldAgentAssignment, which fails.
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderFieldAgentAssignment.setUpdatedBy(null);

        // Create the ServiceOrderFieldAgentAssignment, which fails.
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderFieldAgentAssignment.setUpdatedTime(null);

        // Create the ServiceOrderFieldAgentAssignment, which fails.
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignments() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderFieldAgentAssignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOrderAssignmentStatus").value(hasItem(DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].nps").value(hasItem(DEFAULT_NPS.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].assignedTime").value(hasItem(DEFAULT_ASSIGNED_TIME.toString())))
            .andExpect(jsonPath("$.[*].movedBackTime").value(hasItem(DEFAULT_MOVED_BACK_TIME.toString())))
            .andExpect(jsonPath("$.[*].visitTime").value(hasItem(DEFAULT_VISIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].spareOrderTime").value(hasItem(DEFAULT_SPARE_ORDER_TIME.toString())))
            .andExpect(jsonPath("$.[*].spareDeliveryTime").value(hasItem(DEFAULT_SPARE_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].completedTime").value(hasItem(DEFAULT_COMPLETED_TIME.toString())))
            .andExpect(jsonPath("$.[*].completionOTP").value(hasItem(DEFAULT_COMPLETION_OTP.intValue())))
            .andExpect(jsonPath("$.[*].cancellationOTP").value(hasItem(DEFAULT_CANCELLATION_OTP.intValue())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getServiceOrderFieldAgentAssignment() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get the serviceOrderFieldAgentAssignment
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceOrderFieldAgentAssignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrderFieldAgentAssignment.getId().intValue()))
            .andExpect(jsonPath("$.serviceOrderAssignmentStatus").value(DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS.toString()))
            .andExpect(jsonPath("$.nps").value(DEFAULT_NPS.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.assignedTime").value(DEFAULT_ASSIGNED_TIME.toString()))
            .andExpect(jsonPath("$.movedBackTime").value(DEFAULT_MOVED_BACK_TIME.toString()))
            .andExpect(jsonPath("$.visitTime").value(DEFAULT_VISIT_TIME.toString()))
            .andExpect(jsonPath("$.spareOrderTime").value(DEFAULT_SPARE_ORDER_TIME.toString()))
            .andExpect(jsonPath("$.spareDeliveryTime").value(DEFAULT_SPARE_DELIVERY_TIME.toString()))
            .andExpect(jsonPath("$.completedTime").value(DEFAULT_COMPLETED_TIME.toString()))
            .andExpect(jsonPath("$.completionOTP").value(DEFAULT_COMPLETION_OTP.intValue()))
            .andExpect(jsonPath("$.cancellationOTP").value(DEFAULT_CANCELLATION_OTP.intValue()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getServiceOrderFieldAgentAssignmentsByIdFiltering() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        Long id = serviceOrderFieldAgentAssignment.getId();

        defaultServiceOrderFieldAgentAssignmentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultServiceOrderFieldAgentAssignmentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultServiceOrderFieldAgentAssignmentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByServiceOrderAssignmentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where serviceOrderAssignmentStatus equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "serviceOrderAssignmentStatus.equals=" + DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS,
            "serviceOrderAssignmentStatus.equals=" + UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByServiceOrderAssignmentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where serviceOrderAssignmentStatus in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "serviceOrderAssignmentStatus.in=" + DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS + "," + UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS,
            "serviceOrderAssignmentStatus.in=" + UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByServiceOrderAssignmentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where serviceOrderAssignmentStatus is not null
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "serviceOrderAssignmentStatus.specified=true",
            "serviceOrderAssignmentStatus.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByNpsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where nps equals to
        defaultServiceOrderFieldAgentAssignmentFiltering("nps.equals=" + DEFAULT_NPS, "nps.equals=" + UPDATED_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByNpsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where nps in
        defaultServiceOrderFieldAgentAssignmentFiltering("nps.in=" + DEFAULT_NPS + "," + UPDATED_NPS, "nps.in=" + UPDATED_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByNpsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where nps is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("nps.specified=true", "nps.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByNpsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where nps is greater than or equal to
        defaultServiceOrderFieldAgentAssignmentFiltering("nps.greaterThanOrEqual=" + DEFAULT_NPS, "nps.greaterThanOrEqual=" + UPDATED_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByNpsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where nps is less than or equal to
        defaultServiceOrderFieldAgentAssignmentFiltering("nps.lessThanOrEqual=" + DEFAULT_NPS, "nps.lessThanOrEqual=" + SMALLER_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByNpsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where nps is less than
        defaultServiceOrderFieldAgentAssignmentFiltering("nps.lessThan=" + UPDATED_NPS, "nps.lessThan=" + DEFAULT_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByNpsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where nps is greater than
        defaultServiceOrderFieldAgentAssignmentFiltering("nps.greaterThan=" + SMALLER_NPS, "nps.greaterThan=" + DEFAULT_NPS);
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where isActive equals to
        defaultServiceOrderFieldAgentAssignmentFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where isActive in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE,
            "isActive.in=" + UPDATED_IS_ACTIVE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where isActive is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByAssignedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where assignedTime equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "assignedTime.equals=" + DEFAULT_ASSIGNED_TIME,
            "assignedTime.equals=" + UPDATED_ASSIGNED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByAssignedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where assignedTime in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "assignedTime.in=" + DEFAULT_ASSIGNED_TIME + "," + UPDATED_ASSIGNED_TIME,
            "assignedTime.in=" + UPDATED_ASSIGNED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByAssignedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where assignedTime is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("assignedTime.specified=true", "assignedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByMovedBackTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where movedBackTime equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "movedBackTime.equals=" + DEFAULT_MOVED_BACK_TIME,
            "movedBackTime.equals=" + UPDATED_MOVED_BACK_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByMovedBackTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where movedBackTime in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "movedBackTime.in=" + DEFAULT_MOVED_BACK_TIME + "," + UPDATED_MOVED_BACK_TIME,
            "movedBackTime.in=" + UPDATED_MOVED_BACK_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByMovedBackTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where movedBackTime is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("movedBackTime.specified=true", "movedBackTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByVisitTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where visitTime equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "visitTime.equals=" + DEFAULT_VISIT_TIME,
            "visitTime.equals=" + UPDATED_VISIT_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByVisitTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where visitTime in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "visitTime.in=" + DEFAULT_VISIT_TIME + "," + UPDATED_VISIT_TIME,
            "visitTime.in=" + UPDATED_VISIT_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByVisitTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where visitTime is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("visitTime.specified=true", "visitTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsBySpareOrderTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where spareOrderTime equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "spareOrderTime.equals=" + DEFAULT_SPARE_ORDER_TIME,
            "spareOrderTime.equals=" + UPDATED_SPARE_ORDER_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsBySpareOrderTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where spareOrderTime in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "spareOrderTime.in=" + DEFAULT_SPARE_ORDER_TIME + "," + UPDATED_SPARE_ORDER_TIME,
            "spareOrderTime.in=" + UPDATED_SPARE_ORDER_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsBySpareOrderTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where spareOrderTime is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("spareOrderTime.specified=true", "spareOrderTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsBySpareDeliveryTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where spareDeliveryTime equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "spareDeliveryTime.equals=" + DEFAULT_SPARE_DELIVERY_TIME,
            "spareDeliveryTime.equals=" + UPDATED_SPARE_DELIVERY_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsBySpareDeliveryTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where spareDeliveryTime in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "spareDeliveryTime.in=" + DEFAULT_SPARE_DELIVERY_TIME + "," + UPDATED_SPARE_DELIVERY_TIME,
            "spareDeliveryTime.in=" + UPDATED_SPARE_DELIVERY_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsBySpareDeliveryTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where spareDeliveryTime is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("spareDeliveryTime.specified=true", "spareDeliveryTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCompletedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where completedTime equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "completedTime.equals=" + DEFAULT_COMPLETED_TIME,
            "completedTime.equals=" + UPDATED_COMPLETED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCompletedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where completedTime in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "completedTime.in=" + DEFAULT_COMPLETED_TIME + "," + UPDATED_COMPLETED_TIME,
            "completedTime.in=" + UPDATED_COMPLETED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCompletedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where completedTime is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("completedTime.specified=true", "completedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCompletionOTPIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where completionOTP equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "completionOTP.equals=" + DEFAULT_COMPLETION_OTP,
            "completionOTP.equals=" + UPDATED_COMPLETION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCompletionOTPIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where completionOTP in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "completionOTP.in=" + DEFAULT_COMPLETION_OTP + "," + UPDATED_COMPLETION_OTP,
            "completionOTP.in=" + UPDATED_COMPLETION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCompletionOTPIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where completionOTP is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("completionOTP.specified=true", "completionOTP.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCompletionOTPIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where completionOTP is greater than or equal to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "completionOTP.greaterThanOrEqual=" + DEFAULT_COMPLETION_OTP,
            "completionOTP.greaterThanOrEqual=" + UPDATED_COMPLETION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCompletionOTPIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where completionOTP is less than or equal to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "completionOTP.lessThanOrEqual=" + DEFAULT_COMPLETION_OTP,
            "completionOTP.lessThanOrEqual=" + SMALLER_COMPLETION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCompletionOTPIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where completionOTP is less than
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "completionOTP.lessThan=" + UPDATED_COMPLETION_OTP,
            "completionOTP.lessThan=" + DEFAULT_COMPLETION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCompletionOTPIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where completionOTP is greater than
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "completionOTP.greaterThan=" + SMALLER_COMPLETION_OTP,
            "completionOTP.greaterThan=" + DEFAULT_COMPLETION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCancellationOTPIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where cancellationOTP equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "cancellationOTP.equals=" + DEFAULT_CANCELLATION_OTP,
            "cancellationOTP.equals=" + UPDATED_CANCELLATION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCancellationOTPIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where cancellationOTP in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "cancellationOTP.in=" + DEFAULT_CANCELLATION_OTP + "," + UPDATED_CANCELLATION_OTP,
            "cancellationOTP.in=" + UPDATED_CANCELLATION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCancellationOTPIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where cancellationOTP is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("cancellationOTP.specified=true", "cancellationOTP.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCancellationOTPIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where cancellationOTP is greater than or equal to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "cancellationOTP.greaterThanOrEqual=" + DEFAULT_CANCELLATION_OTP,
            "cancellationOTP.greaterThanOrEqual=" + UPDATED_CANCELLATION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCancellationOTPIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where cancellationOTP is less than or equal to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "cancellationOTP.lessThanOrEqual=" + DEFAULT_CANCELLATION_OTP,
            "cancellationOTP.lessThanOrEqual=" + SMALLER_CANCELLATION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCancellationOTPIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where cancellationOTP is less than
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "cancellationOTP.lessThan=" + UPDATED_CANCELLATION_OTP,
            "cancellationOTP.lessThan=" + DEFAULT_CANCELLATION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCancellationOTPIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where cancellationOTP is greater than
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "cancellationOTP.greaterThan=" + SMALLER_CANCELLATION_OTP,
            "cancellationOTP.greaterThan=" + DEFAULT_CANCELLATION_OTP
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where createddBy equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "createddBy.equals=" + DEFAULT_CREATEDD_BY,
            "createddBy.equals=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where createddBy in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where createddBy is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where createddBy contains
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "createddBy.contains=" + DEFAULT_CREATEDD_BY,
            "createddBy.contains=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where createddBy does not contain
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where createdTime equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "createdTime.equals=" + DEFAULT_CREATED_TIME,
            "createdTime.equals=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where createdTime in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where createdTime is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where updatedBy equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "updatedBy.equals=" + DEFAULT_UPDATED_BY,
            "updatedBy.equals=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where updatedBy in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where updatedBy is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where updatedBy contains
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "updatedBy.contains=" + DEFAULT_UPDATED_BY,
            "updatedBy.contains=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where updatedBy does not contain
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where updatedTime equals to
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "updatedTime.equals=" + DEFAULT_UPDATED_TIME,
            "updatedTime.equals=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where updatedTime in
        defaultServiceOrderFieldAgentAssignmentFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderFieldAgentAssignmentsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        // Get all the serviceOrderFieldAgentAssignmentList where updatedTime is not null
        defaultServiceOrderFieldAgentAssignmentFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    private void defaultServiceOrderFieldAgentAssignmentFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultServiceOrderFieldAgentAssignmentShouldBeFound(shouldBeFound);
        defaultServiceOrderFieldAgentAssignmentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceOrderFieldAgentAssignmentShouldBeFound(String filter) throws Exception {
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderFieldAgentAssignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOrderAssignmentStatus").value(hasItem(DEFAULT_SERVICE_ORDER_ASSIGNMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].nps").value(hasItem(DEFAULT_NPS.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].assignedTime").value(hasItem(DEFAULT_ASSIGNED_TIME.toString())))
            .andExpect(jsonPath("$.[*].movedBackTime").value(hasItem(DEFAULT_MOVED_BACK_TIME.toString())))
            .andExpect(jsonPath("$.[*].visitTime").value(hasItem(DEFAULT_VISIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].spareOrderTime").value(hasItem(DEFAULT_SPARE_ORDER_TIME.toString())))
            .andExpect(jsonPath("$.[*].spareDeliveryTime").value(hasItem(DEFAULT_SPARE_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].completedTime").value(hasItem(DEFAULT_COMPLETED_TIME.toString())))
            .andExpect(jsonPath("$.[*].completionOTP").value(hasItem(DEFAULT_COMPLETION_OTP.intValue())))
            .andExpect(jsonPath("$.[*].cancellationOTP").value(hasItem(DEFAULT_CANCELLATION_OTP.intValue())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceOrderFieldAgentAssignmentShouldNotBeFound(String filter) throws Exception {
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServiceOrderFieldAgentAssignment() throws Exception {
        // Get the serviceOrderFieldAgentAssignment
        restServiceOrderFieldAgentAssignmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceOrderFieldAgentAssignment() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderFieldAgentAssignment
        ServiceOrderFieldAgentAssignment updatedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository
            .findById(serviceOrderFieldAgentAssignment.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedServiceOrderFieldAgentAssignment are not directly saved in db
        em.detach(updatedServiceOrderFieldAgentAssignment);
        updatedServiceOrderFieldAgentAssignment
            .serviceOrderAssignmentStatus(UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .nps(UPDATED_NPS)
            .isActive(UPDATED_IS_ACTIVE)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .movedBackTime(UPDATED_MOVED_BACK_TIME)
            .visitTime(UPDATED_VISIT_TIME)
            .spareOrderTime(UPDATED_SPARE_ORDER_TIME)
            .spareDeliveryTime(UPDATED_SPARE_DELIVERY_TIME)
            .completedTime(UPDATED_COMPLETED_TIME)
            .completionOTP(UPDATED_COMPLETION_OTP)
            .cancellationOTP(UPDATED_CANCELLATION_OTP)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            updatedServiceOrderFieldAgentAssignment
        );

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderFieldAgentAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderFieldAgentAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceOrderFieldAgentAssignmentToMatchAllProperties(updatedServiceOrderFieldAgentAssignment);
    }

    @Test
    @Transactional
    void putNonExistingServiceOrderFieldAgentAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFieldAgentAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFieldAgentAssignment
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderFieldAgentAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderFieldAgentAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceOrderFieldAgentAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFieldAgentAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFieldAgentAssignment
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderFieldAgentAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceOrderFieldAgentAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFieldAgentAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFieldAgentAssignment
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderFieldAgentAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceOrderFieldAgentAssignmentWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderFieldAgentAssignment using partial update
        ServiceOrderFieldAgentAssignment partialUpdatedServiceOrderFieldAgentAssignment = new ServiceOrderFieldAgentAssignment();
        partialUpdatedServiceOrderFieldAgentAssignment.setId(serviceOrderFieldAgentAssignment.getId());

        partialUpdatedServiceOrderFieldAgentAssignment
            .serviceOrderAssignmentStatus(UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .nps(UPDATED_NPS)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .movedBackTime(UPDATED_MOVED_BACK_TIME)
            .spareOrderTime(UPDATED_SPARE_ORDER_TIME)
            .completedTime(UPDATED_COMPLETED_TIME)
            .completionOTP(UPDATED_COMPLETION_OTP)
            .createddBy(UPDATED_CREATEDD_BY);

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderFieldAgentAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderFieldAgentAssignment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderFieldAgentAssignment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderFieldAgentAssignmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceOrderFieldAgentAssignment, serviceOrderFieldAgentAssignment),
            getPersistedServiceOrderFieldAgentAssignment(serviceOrderFieldAgentAssignment)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceOrderFieldAgentAssignmentWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderFieldAgentAssignment using partial update
        ServiceOrderFieldAgentAssignment partialUpdatedServiceOrderFieldAgentAssignment = new ServiceOrderFieldAgentAssignment();
        partialUpdatedServiceOrderFieldAgentAssignment.setId(serviceOrderFieldAgentAssignment.getId());

        partialUpdatedServiceOrderFieldAgentAssignment
            .serviceOrderAssignmentStatus(UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .nps(UPDATED_NPS)
            .isActive(UPDATED_IS_ACTIVE)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .movedBackTime(UPDATED_MOVED_BACK_TIME)
            .visitTime(UPDATED_VISIT_TIME)
            .spareOrderTime(UPDATED_SPARE_ORDER_TIME)
            .spareDeliveryTime(UPDATED_SPARE_DELIVERY_TIME)
            .completedTime(UPDATED_COMPLETED_TIME)
            .completionOTP(UPDATED_COMPLETION_OTP)
            .cancellationOTP(UPDATED_CANCELLATION_OTP)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderFieldAgentAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderFieldAgentAssignment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderFieldAgentAssignment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderFieldAgentAssignmentUpdatableFieldsEquals(
            partialUpdatedServiceOrderFieldAgentAssignment,
            getPersistedServiceOrderFieldAgentAssignment(partialUpdatedServiceOrderFieldAgentAssignment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServiceOrderFieldAgentAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFieldAgentAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFieldAgentAssignment
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderFieldAgentAssignmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderFieldAgentAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceOrderFieldAgentAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFieldAgentAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFieldAgentAssignment
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderFieldAgentAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceOrderFieldAgentAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderFieldAgentAssignment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderFieldAgentAssignment
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = serviceOrderFieldAgentAssignmentMapper.toDto(
            serviceOrderFieldAgentAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderFieldAgentAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceOrderFieldAgentAssignment() throws Exception {
        // Initialize the database
        insertedServiceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.saveAndFlush(
            serviceOrderFieldAgentAssignment
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceOrderFieldAgentAssignment
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceOrderFieldAgentAssignment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceOrderFieldAgentAssignmentRepository.count();
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

    protected ServiceOrderFieldAgentAssignment getPersistedServiceOrderFieldAgentAssignment(
        ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment
    ) {
        return serviceOrderFieldAgentAssignmentRepository.findById(serviceOrderFieldAgentAssignment.getId()).orElseThrow();
    }

    protected void assertPersistedServiceOrderFieldAgentAssignmentToMatchAllProperties(
        ServiceOrderFieldAgentAssignment expectedServiceOrderFieldAgentAssignment
    ) {
        assertServiceOrderFieldAgentAssignmentAllPropertiesEquals(
            expectedServiceOrderFieldAgentAssignment,
            getPersistedServiceOrderFieldAgentAssignment(expectedServiceOrderFieldAgentAssignment)
        );
    }

    protected void assertPersistedServiceOrderFieldAgentAssignmentToMatchUpdatableProperties(
        ServiceOrderFieldAgentAssignment expectedServiceOrderFieldAgentAssignment
    ) {
        assertServiceOrderFieldAgentAssignmentAllUpdatablePropertiesEquals(
            expectedServiceOrderFieldAgentAssignment,
            getPersistedServiceOrderFieldAgentAssignment(expectedServiceOrderFieldAgentAssignment)
        );
    }
}
