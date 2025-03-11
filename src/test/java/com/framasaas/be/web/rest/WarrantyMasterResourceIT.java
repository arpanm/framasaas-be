package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.WarrantyMasterAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.WarrantyMaster;
import com.framasaas.be.domain.enumeration.WarrantyType;
import com.framasaas.be.repository.WarrantyMasterRepository;
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
 * Integration tests for the {@link WarrantyMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WarrantyMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_WARRANTY_MASTER_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_WARRANTY_MASTER_ID = "BBBBBBBBBB";

    private static final WarrantyType DEFAULT_WARRANTY_TYPE = WarrantyType.BRANDFREEWARRANTY;
    private static final WarrantyType UPDATED_WARRANTY_TYPE = WarrantyType.EXTENDEDPAIDWARRANTY;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final Long DEFAULT_PERIOD_IN_MONTHS = 1L;
    private static final Long UPDATED_PERIOD_IN_MONTHS = 2L;

    private static final Float DEFAULT_TAX_RATE = 1F;
    private static final Float UPDATED_TAX_RATE = 2F;

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

    private static final String ENTITY_API_URL = "/api/warranty-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WarrantyMasterRepository warrantyMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWarrantyMasterMockMvc;

    private WarrantyMaster warrantyMaster;

    private WarrantyMaster insertedWarrantyMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarrantyMaster createEntity() {
        return new WarrantyMaster()
            .name(DEFAULT_NAME)
            .vendorWarrantyMasterId(DEFAULT_VENDOR_WARRANTY_MASTER_ID)
            .warrantyType(DEFAULT_WARRANTY_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .periodInMonths(DEFAULT_PERIOD_IN_MONTHS)
            .taxRate(DEFAULT_TAX_RATE)
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
    public static WarrantyMaster createUpdatedEntity() {
        return new WarrantyMaster()
            .name(UPDATED_NAME)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .periodInMonths(UPDATED_PERIOD_IN_MONTHS)
            .taxRate(UPDATED_TAX_RATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        warrantyMaster = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedWarrantyMaster != null) {
            warrantyMasterRepository.delete(insertedWarrantyMaster);
            insertedWarrantyMaster = null;
        }
    }

    @Test
    @Transactional
    void createWarrantyMaster() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WarrantyMaster
        var returnedWarrantyMaster = om.readValue(
            restWarrantyMasterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WarrantyMaster.class
        );

        // Validate the WarrantyMaster in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWarrantyMasterUpdatableFieldsEquals(returnedWarrantyMaster, getPersistedWarrantyMaster(returnedWarrantyMaster));

        insertedWarrantyMaster = returnedWarrantyMaster;
    }

    @Test
    @Transactional
    void createWarrantyMasterWithExistingId() throws Exception {
        // Create the WarrantyMaster with an existing ID
        warrantyMaster.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setName(null);

        // Create the WarrantyMaster, which fails.

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVendorWarrantyMasterIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setVendorWarrantyMasterId(null);

        // Create the WarrantyMaster, which fails.

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWarrantyTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setWarrantyType(null);

        // Create the WarrantyMaster, which fails.

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setPrice(null);

        // Create the WarrantyMaster, which fails.

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPeriodInMonthsIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setPeriodInMonths(null);

        // Create the WarrantyMaster, which fails.

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxRateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setTaxRate(null);

        // Create the WarrantyMaster, which fails.

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setCreateddBy(null);

        // Create the WarrantyMaster, which fails.

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setCreatedTime(null);

        // Create the WarrantyMaster, which fails.

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setUpdatedBy(null);

        // Create the WarrantyMaster, which fails.

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        warrantyMaster.setUpdatedTime(null);

        // Create the WarrantyMaster, which fails.

        restWarrantyMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWarrantyMasters() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get all the warrantyMasterList
        restWarrantyMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warrantyMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vendorWarrantyMasterId").value(hasItem(DEFAULT_VENDOR_WARRANTY_MASTER_ID)))
            .andExpect(jsonPath("$.[*].warrantyType").value(hasItem(DEFAULT_WARRANTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].periodInMonths").value(hasItem(DEFAULT_PERIOD_IN_MONTHS.intValue())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getWarrantyMaster() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        // Get the warrantyMaster
        restWarrantyMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, warrantyMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(warrantyMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.vendorWarrantyMasterId").value(DEFAULT_VENDOR_WARRANTY_MASTER_ID))
            .andExpect(jsonPath("$.warrantyType").value(DEFAULT_WARRANTY_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.periodInMonths").value(DEFAULT_PERIOD_IN_MONTHS.intValue()))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWarrantyMaster() throws Exception {
        // Get the warrantyMaster
        restWarrantyMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWarrantyMaster() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warrantyMaster
        WarrantyMaster updatedWarrantyMaster = warrantyMasterRepository.findById(warrantyMaster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWarrantyMaster are not directly saved in db
        em.detach(updatedWarrantyMaster);
        updatedWarrantyMaster
            .name(UPDATED_NAME)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .periodInMonths(UPDATED_PERIOD_IN_MONTHS)
            .taxRate(UPDATED_TAX_RATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restWarrantyMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWarrantyMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWarrantyMaster))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWarrantyMasterToMatchAllProperties(updatedWarrantyMaster);
    }

    @Test
    @Transactional
    void putNonExistingWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warrantyMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warrantyMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(warrantyMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWarrantyMasterWithPatch() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warrantyMaster using partial update
        WarrantyMaster partialUpdatedWarrantyMaster = new WarrantyMaster();
        partialUpdatedWarrantyMaster.setId(warrantyMaster.getId());

        partialUpdatedWarrantyMaster
            .price(UPDATED_PRICE)
            .periodInMonths(UPDATED_PERIOD_IN_MONTHS)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restWarrantyMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarrantyMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWarrantyMaster))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWarrantyMasterUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWarrantyMaster, warrantyMaster),
            getPersistedWarrantyMaster(warrantyMaster)
        );
    }

    @Test
    @Transactional
    void fullUpdateWarrantyMasterWithPatch() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the warrantyMaster using partial update
        WarrantyMaster partialUpdatedWarrantyMaster = new WarrantyMaster();
        partialUpdatedWarrantyMaster.setId(warrantyMaster.getId());

        partialUpdatedWarrantyMaster
            .name(UPDATED_NAME)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .periodInMonths(UPDATED_PERIOD_IN_MONTHS)
            .taxRate(UPDATED_TAX_RATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restWarrantyMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarrantyMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWarrantyMaster))
            )
            .andExpect(status().isOk());

        // Validate the WarrantyMaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWarrantyMasterUpdatableFieldsEquals(partialUpdatedWarrantyMaster, getPersistedWarrantyMaster(partialUpdatedWarrantyMaster));
    }

    @Test
    @Transactional
    void patchNonExistingWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, warrantyMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warrantyMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(warrantyMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWarrantyMaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        warrantyMaster.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarrantyMasterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(warrantyMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WarrantyMaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWarrantyMaster() throws Exception {
        // Initialize the database
        insertedWarrantyMaster = warrantyMasterRepository.saveAndFlush(warrantyMaster);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the warrantyMaster
        restWarrantyMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, warrantyMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return warrantyMasterRepository.count();
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

    protected WarrantyMaster getPersistedWarrantyMaster(WarrantyMaster warrantyMaster) {
        return warrantyMasterRepository.findById(warrantyMaster.getId()).orElseThrow();
    }

    protected void assertPersistedWarrantyMasterToMatchAllProperties(WarrantyMaster expectedWarrantyMaster) {
        assertWarrantyMasterAllPropertiesEquals(expectedWarrantyMaster, getPersistedWarrantyMaster(expectedWarrantyMaster));
    }

    protected void assertPersistedWarrantyMasterToMatchUpdatableProperties(WarrantyMaster expectedWarrantyMaster) {
        assertWarrantyMasterAllUpdatablePropertiesEquals(expectedWarrantyMaster, getPersistedWarrantyMaster(expectedWarrantyMaster));
    }
}
