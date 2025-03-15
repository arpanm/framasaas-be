package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FranchiseCategoryMappingAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FranchiseCategoryMapping;
import com.framasaas.be.domain.enumeration.ServiceCategory;
import com.framasaas.be.repository.FranchiseCategoryMappingRepository;
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
 * Integration tests for the {@link FranchiseCategoryMappingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseCategoryMappingResourceIT {

    private static final ServiceCategory DEFAULT_SERVICE_CATEGORY = ServiceCategory.AC;
    private static final ServiceCategory UPDATED_SERVICE_CATEGORY = ServiceCategory.TV;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchise-category-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseCategoryMappingRepository franchiseCategoryMappingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseCategoryMappingMockMvc;

    private FranchiseCategoryMapping franchiseCategoryMapping;

    private FranchiseCategoryMapping insertedFranchiseCategoryMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseCategoryMapping createEntity() {
        return new FranchiseCategoryMapping()
            .serviceCategory(DEFAULT_SERVICE_CATEGORY)
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
    public static FranchiseCategoryMapping createUpdatedEntity() {
        return new FranchiseCategoryMapping()
            .serviceCategory(UPDATED_SERVICE_CATEGORY)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        franchiseCategoryMapping = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFranchiseCategoryMapping != null) {
            franchiseCategoryMappingRepository.delete(insertedFranchiseCategoryMapping);
            insertedFranchiseCategoryMapping = null;
        }
    }

    @Test
    @Transactional
    void createFranchiseCategoryMapping() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchiseCategoryMapping
        var returnedFranchiseCategoryMapping = om.readValue(
            restFranchiseCategoryMappingMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseCategoryMapping))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseCategoryMapping.class
        );

        // Validate the FranchiseCategoryMapping in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFranchiseCategoryMappingUpdatableFieldsEquals(
            returnedFranchiseCategoryMapping,
            getPersistedFranchiseCategoryMapping(returnedFranchiseCategoryMapping)
        );

        insertedFranchiseCategoryMapping = returnedFranchiseCategoryMapping;
    }

    @Test
    @Transactional
    void createFranchiseCategoryMappingWithExistingId() throws Exception {
        // Create the FranchiseCategoryMapping with an existing ID
        franchiseCategoryMapping.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseCategoryMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseCategoryMapping)))
            .andExpect(status().isBadRequest());

        // Validate the FranchiseCategoryMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkServiceCategoryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseCategoryMapping.setServiceCategory(null);

        // Create the FranchiseCategoryMapping, which fails.

        restFranchiseCategoryMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseCategoryMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseCategoryMapping.setCreateddBy(null);

        // Create the FranchiseCategoryMapping, which fails.

        restFranchiseCategoryMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseCategoryMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseCategoryMapping.setCreatedTime(null);

        // Create the FranchiseCategoryMapping, which fails.

        restFranchiseCategoryMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseCategoryMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseCategoryMapping.setUpdatedBy(null);

        // Create the FranchiseCategoryMapping, which fails.

        restFranchiseCategoryMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseCategoryMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseCategoryMapping.setUpdatedTime(null);

        // Create the FranchiseCategoryMapping, which fails.

        restFranchiseCategoryMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseCategoryMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchiseCategoryMappings() throws Exception {
        // Initialize the database
        insertedFranchiseCategoryMapping = franchiseCategoryMappingRepository.saveAndFlush(franchiseCategoryMapping);

        // Get all the franchiseCategoryMappingList
        restFranchiseCategoryMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseCategoryMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceCategory").value(hasItem(DEFAULT_SERVICE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchiseCategoryMapping() throws Exception {
        // Initialize the database
        insertedFranchiseCategoryMapping = franchiseCategoryMappingRepository.saveAndFlush(franchiseCategoryMapping);

        // Get the franchiseCategoryMapping
        restFranchiseCategoryMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, franchiseCategoryMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchiseCategoryMapping.getId().intValue()))
            .andExpect(jsonPath("$.serviceCategory").value(DEFAULT_SERVICE_CATEGORY.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFranchiseCategoryMapping() throws Exception {
        // Get the franchiseCategoryMapping
        restFranchiseCategoryMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchiseCategoryMapping() throws Exception {
        // Initialize the database
        insertedFranchiseCategoryMapping = franchiseCategoryMappingRepository.saveAndFlush(franchiseCategoryMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseCategoryMapping
        FranchiseCategoryMapping updatedFranchiseCategoryMapping = franchiseCategoryMappingRepository
            .findById(franchiseCategoryMapping.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFranchiseCategoryMapping are not directly saved in db
        em.detach(updatedFranchiseCategoryMapping);
        updatedFranchiseCategoryMapping
            .serviceCategory(UPDATED_SERVICE_CATEGORY)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseCategoryMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFranchiseCategoryMapping.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFranchiseCategoryMapping))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseCategoryMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseCategoryMappingToMatchAllProperties(updatedFranchiseCategoryMapping);
    }

    @Test
    @Transactional
    void putNonExistingFranchiseCategoryMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseCategoryMapping.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseCategoryMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseCategoryMapping.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseCategoryMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseCategoryMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchiseCategoryMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseCategoryMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseCategoryMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseCategoryMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseCategoryMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchiseCategoryMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseCategoryMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseCategoryMappingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseCategoryMapping)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseCategoryMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseCategoryMappingWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseCategoryMapping = franchiseCategoryMappingRepository.saveAndFlush(franchiseCategoryMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseCategoryMapping using partial update
        FranchiseCategoryMapping partialUpdatedFranchiseCategoryMapping = new FranchiseCategoryMapping();
        partialUpdatedFranchiseCategoryMapping.setId(franchiseCategoryMapping.getId());

        partialUpdatedFranchiseCategoryMapping.createdTime(UPDATED_CREATED_TIME).updatedBy(UPDATED_UPDATED_BY);

        restFranchiseCategoryMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseCategoryMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseCategoryMapping))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseCategoryMapping in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseCategoryMappingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchiseCategoryMapping, franchiseCategoryMapping),
            getPersistedFranchiseCategoryMapping(franchiseCategoryMapping)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseCategoryMappingWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseCategoryMapping = franchiseCategoryMappingRepository.saveAndFlush(franchiseCategoryMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseCategoryMapping using partial update
        FranchiseCategoryMapping partialUpdatedFranchiseCategoryMapping = new FranchiseCategoryMapping();
        partialUpdatedFranchiseCategoryMapping.setId(franchiseCategoryMapping.getId());

        partialUpdatedFranchiseCategoryMapping
            .serviceCategory(UPDATED_SERVICE_CATEGORY)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseCategoryMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseCategoryMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseCategoryMapping))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseCategoryMapping in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseCategoryMappingUpdatableFieldsEquals(
            partialUpdatedFranchiseCategoryMapping,
            getPersistedFranchiseCategoryMapping(partialUpdatedFranchiseCategoryMapping)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFranchiseCategoryMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseCategoryMapping.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseCategoryMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseCategoryMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseCategoryMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseCategoryMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchiseCategoryMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseCategoryMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseCategoryMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseCategoryMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseCategoryMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchiseCategoryMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseCategoryMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseCategoryMappingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseCategoryMapping))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseCategoryMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchiseCategoryMapping() throws Exception {
        // Initialize the database
        insertedFranchiseCategoryMapping = franchiseCategoryMappingRepository.saveAndFlush(franchiseCategoryMapping);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchiseCategoryMapping
        restFranchiseCategoryMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchiseCategoryMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseCategoryMappingRepository.count();
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

    protected FranchiseCategoryMapping getPersistedFranchiseCategoryMapping(FranchiseCategoryMapping franchiseCategoryMapping) {
        return franchiseCategoryMappingRepository.findById(franchiseCategoryMapping.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseCategoryMappingToMatchAllProperties(FranchiseCategoryMapping expectedFranchiseCategoryMapping) {
        assertFranchiseCategoryMappingAllPropertiesEquals(
            expectedFranchiseCategoryMapping,
            getPersistedFranchiseCategoryMapping(expectedFranchiseCategoryMapping)
        );
    }

    protected void assertPersistedFranchiseCategoryMappingToMatchUpdatableProperties(
        FranchiseCategoryMapping expectedFranchiseCategoryMapping
    ) {
        assertFranchiseCategoryMappingAllUpdatablePropertiesEquals(
            expectedFranchiseCategoryMapping,
            getPersistedFranchiseCategoryMapping(expectedFranchiseCategoryMapping)
        );
    }
}
