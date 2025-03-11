package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.ServiceOrderSpareAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.ServiceOrderSpare;
import com.framasaas.be.domain.enumeration.ServiceOrderSpareStatus;
import com.framasaas.be.domain.enumeration.SpareOrderedFrom;
import com.framasaas.be.repository.ServiceOrderSpareRepository;
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

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;

    private static final Float DEFAULT_TOTAL_CHARGE = 1F;
    private static final Float UPDATED_TOTAL_CHARGE = 2F;

    private static final SpareOrderedFrom DEFAULT_ORDERED_FROM = SpareOrderedFrom.ENGINEER;
    private static final SpareOrderedFrom UPDATED_ORDERED_FROM = SpareOrderedFrom.FRANCHISE;

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
            .orderedFrom(UPDATED_ORDERED_FROM)
            .spareStatus(UPDATED_SPARE_STATUS)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        serviceOrderSpare = createEntity();
    }

    @AfterEach
    public void cleanup() {
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
        var returnedServiceOrderSpare = om.readValue(
            restServiceOrderSpareMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpare)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderSpare.class
        );

        // Validate the ServiceOrderSpare in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServiceOrderSpareUpdatableFieldsEquals(returnedServiceOrderSpare, getPersistedServiceOrderSpare(returnedServiceOrderSpare));

        insertedServiceOrderSpare = returnedServiceOrderSpare;
    }

    @Test
    @Transactional
    void createServiceOrderSpareWithExistingId() throws Exception {
        // Create the ServiceOrderSpare with an existing ID
        serviceOrderSpare.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderSpareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpare)))
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

        restServiceOrderSpareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpare)))
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

        restServiceOrderSpareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpare)))
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

        restServiceOrderSpareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpare)))
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

        restServiceOrderSpareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpare)))
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
            .andExpect(jsonPath("$.orderedFrom").value(DEFAULT_ORDERED_FROM.toString()))
            .andExpect(jsonPath("$.spareStatus").value(DEFAULT_SPARE_STATUS.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
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
            .orderedFrom(UPDATED_ORDERED_FROM)
            .spareStatus(UPDATED_SPARE_STATUS)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderSpareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServiceOrderSpare.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServiceOrderSpare))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderSpare.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderSpare))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderSpare))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderSpare)))
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
            .totalCharge(UPDATED_TOTAL_CHARGE)
            .orderedFrom(UPDATED_ORDERED_FROM)
            .createdTime(UPDATED_CREATED_TIME);

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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderSpare.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderSpare))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderSpare))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderSpareMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceOrderSpare)))
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
