package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.ServiceOrderFieldAgentAssignmentAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.ServiceOrderFieldAgentAssignment;
import com.framasaas.be.domain.enumeration.ServiceOrderAssignmentStatus;
import com.framasaas.be.repository.ServiceOrderFieldAgentAssignmentRepository;
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
    public void initTest() {
        serviceOrderFieldAgentAssignment = createEntity();
    }

    @AfterEach
    public void cleanup() {
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
        var returnedServiceOrderFieldAgentAssignment = om.readValue(
            restServiceOrderFieldAgentAssignmentMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderFieldAgentAssignment.class
        );

        // Validate the ServiceOrderFieldAgentAssignment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
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

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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

        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServiceOrderFieldAgentAssignment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServiceOrderFieldAgentAssignment))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderFieldAgentAssignment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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
            .nps(UPDATED_NPS)
            .spareOrderTime(UPDATED_SPARE_ORDER_TIME)
            .spareDeliveryTime(UPDATED_SPARE_DELIVERY_TIME)
            .completionOTP(UPDATED_COMPLETION_OTP)
            .createdTime(UPDATED_CREATED_TIME)
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderFieldAgentAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderFieldAgentAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderFieldAgentAssignment))
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
