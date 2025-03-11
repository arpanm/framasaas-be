package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.HsnAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.Hsn;
import com.framasaas.be.repository.HsnRepository;
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
 * Integration tests for the {@link HsnResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HsnResourceIT {

    private static final String DEFAULT_HSN_CD = "AAAAAAAAAA";
    private static final String UPDATED_HSN_CD = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/hsns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HsnRepository hsnRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHsnMockMvc;

    private Hsn hsn;

    private Hsn insertedHsn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hsn createEntity() {
        return new Hsn()
            .hsnCD(DEFAULT_HSN_CD)
            .description(DEFAULT_DESCRIPTION)
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
    public static Hsn createUpdatedEntity() {
        return new Hsn()
            .hsnCD(UPDATED_HSN_CD)
            .description(UPDATED_DESCRIPTION)
            .taxRate(UPDATED_TAX_RATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        hsn = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedHsn != null) {
            hsnRepository.delete(insertedHsn);
            insertedHsn = null;
        }
    }

    @Test
    @Transactional
    void createHsn() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hsn
        var returnedHsn = om.readValue(
            restHsnMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsn)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Hsn.class
        );

        // Validate the Hsn in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertHsnUpdatableFieldsEquals(returnedHsn, getPersistedHsn(returnedHsn));

        insertedHsn = returnedHsn;
    }

    @Test
    @Transactional
    void createHsnWithExistingId() throws Exception {
        // Create the Hsn with an existing ID
        hsn.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isBadRequest());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHsnCDIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setHsnCD(null);

        // Create the Hsn, which fails.

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxRateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setTaxRate(null);

        // Create the Hsn, which fails.

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setCreateddBy(null);

        // Create the Hsn, which fails.

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setCreatedTime(null);

        // Create the Hsn, which fails.

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setUpdatedBy(null);

        // Create the Hsn, which fails.

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setUpdatedTime(null);

        // Create the Hsn, which fails.

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHsns() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList
        restHsnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hsn.getId().intValue())))
            .andExpect(jsonPath("$.[*].hsnCD").value(hasItem(DEFAULT_HSN_CD)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getHsn() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get the hsn
        restHsnMockMvc
            .perform(get(ENTITY_API_URL_ID, hsn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hsn.getId().intValue()))
            .andExpect(jsonPath("$.hsnCD").value(DEFAULT_HSN_CD))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHsn() throws Exception {
        // Get the hsn
        restHsnMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHsn() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hsn
        Hsn updatedHsn = hsnRepository.findById(hsn.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHsn are not directly saved in db
        em.detach(updatedHsn);
        updatedHsn
            .hsnCD(UPDATED_HSN_CD)
            .description(UPDATED_DESCRIPTION)
            .taxRate(UPDATED_TAX_RATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restHsnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHsn.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(updatedHsn))
            )
            .andExpect(status().isOk());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHsnToMatchAllProperties(updatedHsn);
    }

    @Test
    @Transactional
    void putNonExistingHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(put(ENTITY_API_URL_ID, hsn.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isBadRequest());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hsn))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHsnWithPatch() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hsn using partial update
        Hsn partialUpdatedHsn = new Hsn();
        partialUpdatedHsn.setId(hsn.getId());

        partialUpdatedHsn
            .taxRate(UPDATED_TAX_RATE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restHsnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHsn.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHsn))
            )
            .andExpect(status().isOk());

        // Validate the Hsn in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHsnUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHsn, hsn), getPersistedHsn(hsn));
    }

    @Test
    @Transactional
    void fullUpdateHsnWithPatch() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hsn using partial update
        Hsn partialUpdatedHsn = new Hsn();
        partialUpdatedHsn.setId(hsn.getId());

        partialUpdatedHsn
            .hsnCD(UPDATED_HSN_CD)
            .description(UPDATED_DESCRIPTION)
            .taxRate(UPDATED_TAX_RATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restHsnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHsn.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHsn))
            )
            .andExpect(status().isOk());

        // Validate the Hsn in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHsnUpdatableFieldsEquals(partialUpdatedHsn, getPersistedHsn(partialUpdatedHsn));
    }

    @Test
    @Transactional
    void patchNonExistingHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(patch(ENTITY_API_URL_ID, hsn.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isBadRequest());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hsn))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hsn)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHsn() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hsn
        restHsnMockMvc.perform(delete(ENTITY_API_URL_ID, hsn.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hsnRepository.count();
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

    protected Hsn getPersistedHsn(Hsn hsn) {
        return hsnRepository.findById(hsn.getId()).orElseThrow();
    }

    protected void assertPersistedHsnToMatchAllProperties(Hsn expectedHsn) {
        assertHsnAllPropertiesEquals(expectedHsn, getPersistedHsn(expectedHsn));
    }

    protected void assertPersistedHsnToMatchUpdatableProperties(Hsn expectedHsn) {
        assertHsnAllUpdatablePropertiesEquals(expectedHsn, getPersistedHsn(expectedHsn));
    }
}
