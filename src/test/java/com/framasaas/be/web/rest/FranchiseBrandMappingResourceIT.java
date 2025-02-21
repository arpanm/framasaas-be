package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FranchiseBrandMappingAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FranchiseBrandMapping;
import com.framasaas.be.domain.enumeration.Brand;
import com.framasaas.be.repository.FranchiseBrandMappingRepository;
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
 * Integration tests for the {@link FranchiseBrandMappingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseBrandMappingResourceIT {

    private static final Brand DEFAULT_BRAND = Brand.Samsung;
    private static final Brand UPDATED_BRAND = Brand.LG;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchise-brand-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseBrandMappingRepository franchiseBrandMappingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseBrandMappingMockMvc;

    private FranchiseBrandMapping franchiseBrandMapping;

    private FranchiseBrandMapping insertedFranchiseBrandMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseBrandMapping createEntity() {
        return new FranchiseBrandMapping()
            .brand(DEFAULT_BRAND)
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
    public static FranchiseBrandMapping createUpdatedEntity() {
        return new FranchiseBrandMapping()
            .brand(UPDATED_BRAND)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        franchiseBrandMapping = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFranchiseBrandMapping != null) {
            franchiseBrandMappingRepository.delete(insertedFranchiseBrandMapping);
            insertedFranchiseBrandMapping = null;
        }
    }

    @Test
    @Transactional
    void createFranchiseBrandMapping() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchiseBrandMapping
        var returnedFranchiseBrandMapping = om.readValue(
            restFranchiseBrandMappingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseBrandMapping)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseBrandMapping.class
        );

        // Validate the FranchiseBrandMapping in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFranchiseBrandMappingUpdatableFieldsEquals(
            returnedFranchiseBrandMapping,
            getPersistedFranchiseBrandMapping(returnedFranchiseBrandMapping)
        );

        insertedFranchiseBrandMapping = returnedFranchiseBrandMapping;
    }

    @Test
    @Transactional
    void createFranchiseBrandMappingWithExistingId() throws Exception {
        // Create the FranchiseBrandMapping with an existing ID
        franchiseBrandMapping.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseBrandMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseBrandMapping)))
            .andExpect(status().isBadRequest());

        // Validate the FranchiseBrandMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBrandIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseBrandMapping.setBrand(null);

        // Create the FranchiseBrandMapping, which fails.

        restFranchiseBrandMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseBrandMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseBrandMapping.setCreateddBy(null);

        // Create the FranchiseBrandMapping, which fails.

        restFranchiseBrandMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseBrandMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseBrandMapping.setCreatedTime(null);

        // Create the FranchiseBrandMapping, which fails.

        restFranchiseBrandMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseBrandMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseBrandMapping.setUpdatedBy(null);

        // Create the FranchiseBrandMapping, which fails.

        restFranchiseBrandMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseBrandMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseBrandMapping.setUpdatedTime(null);

        // Create the FranchiseBrandMapping, which fails.

        restFranchiseBrandMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseBrandMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchiseBrandMappings() throws Exception {
        // Initialize the database
        insertedFranchiseBrandMapping = franchiseBrandMappingRepository.saveAndFlush(franchiseBrandMapping);

        // Get all the franchiseBrandMappingList
        restFranchiseBrandMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseBrandMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchiseBrandMapping() throws Exception {
        // Initialize the database
        insertedFranchiseBrandMapping = franchiseBrandMappingRepository.saveAndFlush(franchiseBrandMapping);

        // Get the franchiseBrandMapping
        restFranchiseBrandMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, franchiseBrandMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchiseBrandMapping.getId().intValue()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFranchiseBrandMapping() throws Exception {
        // Get the franchiseBrandMapping
        restFranchiseBrandMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchiseBrandMapping() throws Exception {
        // Initialize the database
        insertedFranchiseBrandMapping = franchiseBrandMappingRepository.saveAndFlush(franchiseBrandMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseBrandMapping
        FranchiseBrandMapping updatedFranchiseBrandMapping = franchiseBrandMappingRepository
            .findById(franchiseBrandMapping.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFranchiseBrandMapping are not directly saved in db
        em.detach(updatedFranchiseBrandMapping);
        updatedFranchiseBrandMapping
            .brand(UPDATED_BRAND)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseBrandMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFranchiseBrandMapping.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFranchiseBrandMapping))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseBrandMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseBrandMappingToMatchAllProperties(updatedFranchiseBrandMapping);
    }

    @Test
    @Transactional
    void putNonExistingFranchiseBrandMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseBrandMapping.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseBrandMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseBrandMapping.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseBrandMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseBrandMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchiseBrandMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseBrandMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseBrandMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseBrandMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseBrandMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchiseBrandMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseBrandMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseBrandMappingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseBrandMapping)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseBrandMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseBrandMappingWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseBrandMapping = franchiseBrandMappingRepository.saveAndFlush(franchiseBrandMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseBrandMapping using partial update
        FranchiseBrandMapping partialUpdatedFranchiseBrandMapping = new FranchiseBrandMapping();
        partialUpdatedFranchiseBrandMapping.setId(franchiseBrandMapping.getId());

        partialUpdatedFranchiseBrandMapping.createddBy(UPDATED_CREATEDD_BY);

        restFranchiseBrandMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseBrandMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseBrandMapping))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseBrandMapping in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseBrandMappingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchiseBrandMapping, franchiseBrandMapping),
            getPersistedFranchiseBrandMapping(franchiseBrandMapping)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseBrandMappingWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseBrandMapping = franchiseBrandMappingRepository.saveAndFlush(franchiseBrandMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseBrandMapping using partial update
        FranchiseBrandMapping partialUpdatedFranchiseBrandMapping = new FranchiseBrandMapping();
        partialUpdatedFranchiseBrandMapping.setId(franchiseBrandMapping.getId());

        partialUpdatedFranchiseBrandMapping
            .brand(UPDATED_BRAND)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseBrandMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseBrandMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseBrandMapping))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseBrandMapping in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseBrandMappingUpdatableFieldsEquals(
            partialUpdatedFranchiseBrandMapping,
            getPersistedFranchiseBrandMapping(partialUpdatedFranchiseBrandMapping)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFranchiseBrandMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseBrandMapping.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseBrandMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseBrandMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseBrandMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseBrandMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchiseBrandMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseBrandMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseBrandMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseBrandMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseBrandMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchiseBrandMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseBrandMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseBrandMappingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseBrandMapping)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseBrandMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchiseBrandMapping() throws Exception {
        // Initialize the database
        insertedFranchiseBrandMapping = franchiseBrandMappingRepository.saveAndFlush(franchiseBrandMapping);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchiseBrandMapping
        restFranchiseBrandMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchiseBrandMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseBrandMappingRepository.count();
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

    protected FranchiseBrandMapping getPersistedFranchiseBrandMapping(FranchiseBrandMapping franchiseBrandMapping) {
        return franchiseBrandMappingRepository.findById(franchiseBrandMapping.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseBrandMappingToMatchAllProperties(FranchiseBrandMapping expectedFranchiseBrandMapping) {
        assertFranchiseBrandMappingAllPropertiesEquals(
            expectedFranchiseBrandMapping,
            getPersistedFranchiseBrandMapping(expectedFranchiseBrandMapping)
        );
    }

    protected void assertPersistedFranchiseBrandMappingToMatchUpdatableProperties(FranchiseBrandMapping expectedFranchiseBrandMapping) {
        assertFranchiseBrandMappingAllUpdatablePropertiesEquals(
            expectedFranchiseBrandMapping,
            getPersistedFranchiseBrandMapping(expectedFranchiseBrandMapping)
        );
    }
}
