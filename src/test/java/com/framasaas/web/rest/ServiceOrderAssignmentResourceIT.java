package com.framasaas.web.rest;

import static com.framasaas.domain.ServiceOrderAssignmentAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.ServiceOrderAssignment;
import com.framasaas.domain.enumeration.ServiceOrderAssignmentStatus;
import com.framasaas.repository.ServiceOrderAssignmentRepository;
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
 * Integration tests for the {@link ServiceOrderAssignmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceOrderAssignmentResourceIT {

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

    private static final String ENTITY_API_URL = "/api/service-order-assignments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceOrderAssignmentRepository serviceOrderAssignmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOrderAssignmentMockMvc;

    private ServiceOrderAssignment serviceOrderAssignment;

    private ServiceOrderAssignment insertedServiceOrderAssignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrderAssignment createEntity() {
        return new ServiceOrderAssignment()
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
    public static ServiceOrderAssignment createUpdatedEntity() {
        return new ServiceOrderAssignment()
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
    void initTest() {
        serviceOrderAssignment = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedServiceOrderAssignment != null) {
            serviceOrderAssignmentRepository.delete(insertedServiceOrderAssignment);
            insertedServiceOrderAssignment = null;
        }
    }

    @Test
    @Transactional
    void createServiceOrderAssignment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceOrderAssignment
        var returnedServiceOrderAssignment = om.readValue(
            restServiceOrderAssignmentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderAssignment)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderAssignment.class
        );

        // Validate the ServiceOrderAssignment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServiceOrderAssignmentUpdatableFieldsEquals(
            returnedServiceOrderAssignment,
            getPersistedServiceOrderAssignment(returnedServiceOrderAssignment)
        );

        insertedServiceOrderAssignment = returnedServiceOrderAssignment;
    }

    @Test
    @Transactional
    void createServiceOrderAssignmentWithExistingId() throws Exception {
        // Create the ServiceOrderAssignment with an existing ID
        serviceOrderAssignment.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderAssignment)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkServiceOrderAssignmentStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderAssignment.setServiceOrderAssignmentStatus(null);

        // Create the ServiceOrderAssignment, which fails.

        restServiceOrderAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderAssignment)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderAssignment.setCreateddBy(null);

        // Create the ServiceOrderAssignment, which fails.

        restServiceOrderAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderAssignment)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderAssignment.setCreatedTime(null);

        // Create the ServiceOrderAssignment, which fails.

        restServiceOrderAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderAssignment)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderAssignment.setUpdatedBy(null);

        // Create the ServiceOrderAssignment, which fails.

        restServiceOrderAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderAssignment)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderAssignment.setUpdatedTime(null);

        // Create the ServiceOrderAssignment, which fails.

        restServiceOrderAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderAssignment)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceOrderAssignments() throws Exception {
        // Initialize the database
        insertedServiceOrderAssignment = serviceOrderAssignmentRepository.saveAndFlush(serviceOrderAssignment);

        // Get all the serviceOrderAssignmentList
        restServiceOrderAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderAssignment.getId().intValue())))
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
    void getServiceOrderAssignment() throws Exception {
        // Initialize the database
        insertedServiceOrderAssignment = serviceOrderAssignmentRepository.saveAndFlush(serviceOrderAssignment);

        // Get the serviceOrderAssignment
        restServiceOrderAssignmentMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceOrderAssignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrderAssignment.getId().intValue()))
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
    void getNonExistingServiceOrderAssignment() throws Exception {
        // Get the serviceOrderAssignment
        restServiceOrderAssignmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceOrderAssignment() throws Exception {
        // Initialize the database
        insertedServiceOrderAssignment = serviceOrderAssignmentRepository.saveAndFlush(serviceOrderAssignment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderAssignment
        ServiceOrderAssignment updatedServiceOrderAssignment = serviceOrderAssignmentRepository
            .findById(serviceOrderAssignment.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedServiceOrderAssignment are not directly saved in db
        em.detach(updatedServiceOrderAssignment);
        updatedServiceOrderAssignment
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

        restServiceOrderAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServiceOrderAssignment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServiceOrderAssignment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceOrderAssignmentToMatchAllProperties(updatedServiceOrderAssignment);
    }

    @Test
    @Transactional
    void putNonExistingServiceOrderAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderAssignment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderAssignment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderAssignment))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceOrderAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderAssignment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderAssignment))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceOrderAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderAssignment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderAssignmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderAssignment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceOrderAssignmentWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderAssignment = serviceOrderAssignmentRepository.saveAndFlush(serviceOrderAssignment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderAssignment using partial update
        ServiceOrderAssignment partialUpdatedServiceOrderAssignment = new ServiceOrderAssignment();
        partialUpdatedServiceOrderAssignment.setId(serviceOrderAssignment.getId());

        partialUpdatedServiceOrderAssignment
            .serviceOrderAssignmentStatus(UPDATED_SERVICE_ORDER_ASSIGNMENT_STATUS)
            .nps(UPDATED_NPS)
            .visitTime(UPDATED_VISIT_TIME)
            .spareOrderTime(UPDATED_SPARE_ORDER_TIME)
            .franchiseCommision(UPDATED_FRANCHISE_COMMISION)
            .franchiseCommisionTax(UPDATED_FRANCHISE_COMMISION_TAX)
            .franchiseInvoicePath(UPDATED_FRANCHISE_INVOICE_PATH)
            .createddBy(UPDATED_CREATEDD_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restServiceOrderAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderAssignment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderAssignment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderAssignmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceOrderAssignment, serviceOrderAssignment),
            getPersistedServiceOrderAssignment(serviceOrderAssignment)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceOrderAssignmentWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderAssignment = serviceOrderAssignmentRepository.saveAndFlush(serviceOrderAssignment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderAssignment using partial update
        ServiceOrderAssignment partialUpdatedServiceOrderAssignment = new ServiceOrderAssignment();
        partialUpdatedServiceOrderAssignment.setId(serviceOrderAssignment.getId());

        partialUpdatedServiceOrderAssignment
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

        restServiceOrderAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderAssignment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderAssignment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderAssignmentUpdatableFieldsEquals(
            partialUpdatedServiceOrderAssignment,
            getPersistedServiceOrderAssignment(partialUpdatedServiceOrderAssignment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServiceOrderAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderAssignment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderAssignment))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceOrderAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderAssignment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderAssignment))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceOrderAssignment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderAssignment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceOrderAssignment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderAssignment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceOrderAssignment() throws Exception {
        // Initialize the database
        insertedServiceOrderAssignment = serviceOrderAssignmentRepository.saveAndFlush(serviceOrderAssignment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceOrderAssignment
        restServiceOrderAssignmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceOrderAssignment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceOrderAssignmentRepository.count();
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

    protected ServiceOrderAssignment getPersistedServiceOrderAssignment(ServiceOrderAssignment serviceOrderAssignment) {
        return serviceOrderAssignmentRepository.findById(serviceOrderAssignment.getId()).orElseThrow();
    }

    protected void assertPersistedServiceOrderAssignmentToMatchAllProperties(ServiceOrderAssignment expectedServiceOrderAssignment) {
        assertServiceOrderAssignmentAllPropertiesEquals(
            expectedServiceOrderAssignment,
            getPersistedServiceOrderAssignment(expectedServiceOrderAssignment)
        );
    }

    protected void assertPersistedServiceOrderAssignmentToMatchUpdatableProperties(ServiceOrderAssignment expectedServiceOrderAssignment) {
        assertServiceOrderAssignmentAllUpdatablePropertiesEquals(
            expectedServiceOrderAssignment,
            getPersistedServiceOrderAssignment(expectedServiceOrderAssignment)
        );
    }
}
