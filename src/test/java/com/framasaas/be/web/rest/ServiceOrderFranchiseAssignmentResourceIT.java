package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.ServiceOrderFranchiseAssignmentAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.ServiceOrderFranchiseAssignment;
import com.framasaas.be.domain.enumeration.ServiceOrderAssignmentStatus;
import com.framasaas.be.repository.ServiceOrderFranchiseAssignmentRepository;
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

    private static final Long DEFAULT_CANCELLATION_OTP = 1L;
    private static final Long UPDATED_CANCELLATION_OTP = 2L;

    private static final Float DEFAULT_FRANCHISE_COMMISION = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISION = 2F;

    private static final Float DEFAULT_FRANCHISE_COMMISION_TAX = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISION_TAX = 2F;

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
            .completionOTP(DEFAULT_COMPLETION_OTP)
            .cancellationOTP(DEFAULT_CANCELLATION_OTP)
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
            .completionOTP(UPDATED_COMPLETION_OTP)
            .cancellationOTP(UPDATED_CANCELLATION_OTP)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseCommisionTax(UPDATED_FRANCHISE_COMMISION_TAX)
            .franchiseInvoicePath(UPDATED_FRANCHISE_INVOICE_PATH)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        serviceOrderFranchiseAssignment = createEntity();
    }

    @AfterEach
    public void cleanup() {
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
        var returnedServiceOrderFranchiseAssignment = om.readValue(
            restServiceOrderFranchiseAssignmentMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderFranchiseAssignment.class
        );

        // Validate the ServiceOrderFranchiseAssignment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
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

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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
            .andExpect(jsonPath("$.[*].completionOTP").value(hasItem(DEFAULT_COMPLETION_OTP.intValue())))
            .andExpect(jsonPath("$.[*].cancellationOTP").value(hasItem(DEFAULT_CANCELLATION_OTP.intValue())))
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
            .andExpect(jsonPath("$.completionOTP").value(DEFAULT_COMPLETION_OTP.intValue()))
            .andExpect(jsonPath("$.cancellationOTP").value(DEFAULT_CANCELLATION_OTP.intValue()))
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
            .completionOTP(UPDATED_COMPLETION_OTP)
            .cancellationOTP(UPDATED_CANCELLATION_OTP)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseCommisionTax(UPDATED_FRANCHISE_COMMISION_TAX)
            .franchiseInvoicePath(UPDATED_FRANCHISE_INVOICE_PATH)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServiceOrderFranchiseAssignment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServiceOrderFranchiseAssignment))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderFranchiseAssignment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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
            .nps(UPDATED_NPS)
            .assignedTime(UPDATED_ASSIGNED_TIME)
            .spareDeliveryTime(UPDATED_SPARE_DELIVERY_TIME)
            .completedTime(UPDATED_COMPLETED_TIME)
            .completionOTP(UPDATED_COMPLETION_OTP)
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
            .completionOTP(UPDATED_COMPLETION_OTP)
            .cancellationOTP(UPDATED_CANCELLATION_OTP)
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderFranchiseAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFranchiseAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFranchiseAssignment))
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
