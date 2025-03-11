package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.ServiceOrderMasterAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.ServiceOrderMaster;
import com.framasaas.be.domain.enumeration.ServiceOrderType;
import com.framasaas.be.repository.ServiceOrderMasterRepository;
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

    private static final Float DEFAULT_CHARGE = 1F;
    private static final Float UPDATED_CHARGE = 2F;

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;

    private static final Float DEFAULT_FRANCHISE_COMMISSION_WITHIN_SLA = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISSION_WITHIN_SLA = 2F;

    private static final Float DEFAULT_FRANCHISE_TAX_WITHIN_SLA_TAX = 1F;
    private static final Float UPDATED_FRANCHISE_TAX_WITHIN_SLA_TAX = 2F;

    private static final Float DEFAULT_FRANCHISE_COMMISSION_OUTSIDE_SLA = 1F;
    private static final Float UPDATED_FRANCHISE_COMMISSION_OUTSIDE_SLA = 2F;

    private static final Float DEFAULT_FRANCHISE_TAX_OUTSIDE_SLA_TAX = 1F;
    private static final Float UPDATED_FRANCHISE_TAX_OUTSIDE_SLA_TAX = 2F;

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
    public void initTest() {
        serviceOrderMaster = createEntity();
    }

    @AfterEach
    public void cleanup() {
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
        var returnedServiceOrderMaster = om.readValue(
            restServiceOrderMasterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMaster)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderMaster.class
        );

        // Validate the ServiceOrderMaster in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
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

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMaster)))
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

        restServiceOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMaster)))
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

        restServiceOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMaster)))
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

        restServiceOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMaster)))
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

        restServiceOrderMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMaster)))
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

        restServiceOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServiceOrderMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServiceOrderMaster))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderMaster))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderMaster))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderMaster)))
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
            .charge(UPDATED_CHARGE)
            .tax(UPDATED_TAX)
            .updatedBy(UPDATED_UPDATED_BY);

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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderMaster))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderMaster))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderMasterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceOrderMaster)))
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
