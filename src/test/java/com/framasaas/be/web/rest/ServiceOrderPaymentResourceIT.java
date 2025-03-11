package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.ServiceOrderPaymentAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.ServiceOrderPayment;
import com.framasaas.be.domain.enumeration.ModeOfPayment;
import com.framasaas.be.domain.enumeration.PaymentStatus;
import com.framasaas.be.repository.ServiceOrderPaymentRepository;
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
 * Integration tests for the {@link ServiceOrderPaymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceOrderPaymentResourceIT {

    private static final String DEFAULT_PAYMENT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_LINK = "BBBBBBBBBB";

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.INITIATED;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.SUCCESS;

    private static final ModeOfPayment DEFAULT_MOP = ModeOfPayment.CASH;
    private static final ModeOfPayment UPDATED_MOP = ModeOfPayment.ONLINE;

    private static final String DEFAULT_PG_TXN_ID = "AAAAAAAAAA";
    private static final String UPDATED_PG_TXN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PG_TXN_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_PG_TXN_RESPONSE = "BBBBBBBBBB";

    private static final Instant DEFAULT_PG_TXN_RESPONSE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PG_TXN_RESPONSE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PAYMENT_INITIATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_INITIATED_BY = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/service-order-payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceOrderPaymentRepository serviceOrderPaymentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOrderPaymentMockMvc;

    private ServiceOrderPayment serviceOrderPayment;

    private ServiceOrderPayment insertedServiceOrderPayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrderPayment createEntity() {
        return new ServiceOrderPayment()
            .paymentLink(DEFAULT_PAYMENT_LINK)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .mop(DEFAULT_MOP)
            .pgTxnId(DEFAULT_PG_TXN_ID)
            .pgTxnResponse(DEFAULT_PG_TXN_RESPONSE)
            .pgTxnResponseTime(DEFAULT_PG_TXN_RESPONSE_TIME)
            .paymentInitiatedBy(DEFAULT_PAYMENT_INITIATED_BY)
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
    public static ServiceOrderPayment createUpdatedEntity() {
        return new ServiceOrderPayment()
            .paymentLink(UPDATED_PAYMENT_LINK)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .mop(UPDATED_MOP)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgTxnResponse(UPDATED_PG_TXN_RESPONSE)
            .pgTxnResponseTime(UPDATED_PG_TXN_RESPONSE_TIME)
            .paymentInitiatedBy(UPDATED_PAYMENT_INITIATED_BY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        serviceOrderPayment = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedServiceOrderPayment != null) {
            serviceOrderPaymentRepository.delete(insertedServiceOrderPayment);
            insertedServiceOrderPayment = null;
        }
    }

    @Test
    @Transactional
    void createServiceOrderPayment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceOrderPayment
        var returnedServiceOrderPayment = om.readValue(
            restServiceOrderPaymentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPayment)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderPayment.class
        );

        // Validate the ServiceOrderPayment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServiceOrderPaymentUpdatableFieldsEquals(
            returnedServiceOrderPayment,
            getPersistedServiceOrderPayment(returnedServiceOrderPayment)
        );

        insertedServiceOrderPayment = returnedServiceOrderPayment;
    }

    @Test
    @Transactional
    void createServiceOrderPaymentWithExistingId() throws Exception {
        // Create the ServiceOrderPayment with an existing ID
        serviceOrderPayment.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPayment)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderPayment.setCreateddBy(null);

        // Create the ServiceOrderPayment, which fails.

        restServiceOrderPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPayment)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderPayment.setCreatedTime(null);

        // Create the ServiceOrderPayment, which fails.

        restServiceOrderPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPayment)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderPayment.setUpdatedBy(null);

        // Create the ServiceOrderPayment, which fails.

        restServiceOrderPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPayment)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderPayment.setUpdatedTime(null);

        // Create the ServiceOrderPayment, which fails.

        restServiceOrderPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPayment)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceOrderPayments() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList
        restServiceOrderPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentLink").value(hasItem(DEFAULT_PAYMENT_LINK)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].mop").value(hasItem(DEFAULT_MOP.toString())))
            .andExpect(jsonPath("$.[*].pgTxnId").value(hasItem(DEFAULT_PG_TXN_ID)))
            .andExpect(jsonPath("$.[*].pgTxnResponse").value(hasItem(DEFAULT_PG_TXN_RESPONSE)))
            .andExpect(jsonPath("$.[*].pgTxnResponseTime").value(hasItem(DEFAULT_PG_TXN_RESPONSE_TIME.toString())))
            .andExpect(jsonPath("$.[*].paymentInitiatedBy").value(hasItem(DEFAULT_PAYMENT_INITIATED_BY)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getServiceOrderPayment() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get the serviceOrderPayment
        restServiceOrderPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceOrderPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrderPayment.getId().intValue()))
            .andExpect(jsonPath("$.paymentLink").value(DEFAULT_PAYMENT_LINK))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.mop").value(DEFAULT_MOP.toString()))
            .andExpect(jsonPath("$.pgTxnId").value(DEFAULT_PG_TXN_ID))
            .andExpect(jsonPath("$.pgTxnResponse").value(DEFAULT_PG_TXN_RESPONSE))
            .andExpect(jsonPath("$.pgTxnResponseTime").value(DEFAULT_PG_TXN_RESPONSE_TIME.toString()))
            .andExpect(jsonPath("$.paymentInitiatedBy").value(DEFAULT_PAYMENT_INITIATED_BY))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingServiceOrderPayment() throws Exception {
        // Get the serviceOrderPayment
        restServiceOrderPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceOrderPayment() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderPayment
        ServiceOrderPayment updatedServiceOrderPayment = serviceOrderPaymentRepository.findById(serviceOrderPayment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServiceOrderPayment are not directly saved in db
        em.detach(updatedServiceOrderPayment);
        updatedServiceOrderPayment
            .paymentLink(UPDATED_PAYMENT_LINK)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .mop(UPDATED_MOP)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgTxnResponse(UPDATED_PG_TXN_RESPONSE)
            .pgTxnResponseTime(UPDATED_PG_TXN_RESPONSE_TIME)
            .paymentInitiatedBy(UPDATED_PAYMENT_INITIATED_BY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServiceOrderPayment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServiceOrderPayment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceOrderPaymentToMatchAllProperties(updatedServiceOrderPayment);
    }

    @Test
    @Transactional
    void putNonExistingServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderPayment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderPayment))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderPayment))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPayment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceOrderPaymentWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderPayment using partial update
        ServiceOrderPayment partialUpdatedServiceOrderPayment = new ServiceOrderPayment();
        partialUpdatedServiceOrderPayment.setId(serviceOrderPayment.getId());

        partialUpdatedServiceOrderPayment
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .isActive(UPDATED_IS_ACTIVE)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderPayment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderPayment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderPaymentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceOrderPayment, serviceOrderPayment),
            getPersistedServiceOrderPayment(serviceOrderPayment)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceOrderPaymentWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderPayment using partial update
        ServiceOrderPayment partialUpdatedServiceOrderPayment = new ServiceOrderPayment();
        partialUpdatedServiceOrderPayment.setId(serviceOrderPayment.getId());

        partialUpdatedServiceOrderPayment
            .paymentLink(UPDATED_PAYMENT_LINK)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .mop(UPDATED_MOP)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgTxnResponse(UPDATED_PG_TXN_RESPONSE)
            .pgTxnResponseTime(UPDATED_PG_TXN_RESPONSE_TIME)
            .paymentInitiatedBy(UPDATED_PAYMENT_INITIATED_BY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderPayment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderPayment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderPaymentUpdatableFieldsEquals(
            partialUpdatedServiceOrderPayment,
            getPersistedServiceOrderPayment(partialUpdatedServiceOrderPayment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderPayment))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderPayment))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceOrderPayment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceOrderPayment() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceOrderPayment
        restServiceOrderPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceOrderPayment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceOrderPaymentRepository.count();
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

    protected ServiceOrderPayment getPersistedServiceOrderPayment(ServiceOrderPayment serviceOrderPayment) {
        return serviceOrderPaymentRepository.findById(serviceOrderPayment.getId()).orElseThrow();
    }

    protected void assertPersistedServiceOrderPaymentToMatchAllProperties(ServiceOrderPayment expectedServiceOrderPayment) {
        assertServiceOrderPaymentAllPropertiesEquals(
            expectedServiceOrderPayment,
            getPersistedServiceOrderPayment(expectedServiceOrderPayment)
        );
    }

    protected void assertPersistedServiceOrderPaymentToMatchUpdatableProperties(ServiceOrderPayment expectedServiceOrderPayment) {
        assertServiceOrderPaymentAllUpdatablePropertiesEquals(
            expectedServiceOrderPayment,
            getPersistedServiceOrderPayment(expectedServiceOrderPayment)
        );
    }
}
