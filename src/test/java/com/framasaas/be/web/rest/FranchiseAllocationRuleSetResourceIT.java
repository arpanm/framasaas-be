package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FranchiseAllocationRuleSetAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FranchiseAllocationRuleSet;
import com.framasaas.be.domain.enumeration.FranchiseAllocationRuleSetSortType;
import com.framasaas.be.repository.FranchiseAllocationRuleSetRepository;
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
 * Integration tests for the {@link FranchiseAllocationRuleSetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseAllocationRuleSetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final FranchiseAllocationRuleSetSortType DEFAULT_SORT_TYPE = FranchiseAllocationRuleSetSortType.NPS;
    private static final FranchiseAllocationRuleSetSortType UPDATED_SORT_TYPE = FranchiseAllocationRuleSetSortType.ROUNDROBIN;

    private static final Long DEFAULT_PRIORITY = 1L;
    private static final Long UPDATED_PRIORITY = 2L;

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

    private static final String ENTITY_API_URL = "/api/franchise-allocation-rule-sets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseAllocationRuleSetMockMvc;

    private FranchiseAllocationRuleSet franchiseAllocationRuleSet;

    private FranchiseAllocationRuleSet insertedFranchiseAllocationRuleSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseAllocationRuleSet createEntity() {
        return new FranchiseAllocationRuleSet()
            .name(DEFAULT_NAME)
            .sortType(DEFAULT_SORT_TYPE)
            .priority(DEFAULT_PRIORITY)
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
    public static FranchiseAllocationRuleSet createUpdatedEntity() {
        return new FranchiseAllocationRuleSet()
            .name(UPDATED_NAME)
            .sortType(UPDATED_SORT_TYPE)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        franchiseAllocationRuleSet = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFranchiseAllocationRuleSet != null) {
            franchiseAllocationRuleSetRepository.delete(insertedFranchiseAllocationRuleSet);
            insertedFranchiseAllocationRuleSet = null;
        }
    }

    @Test
    @Transactional
    void createFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchiseAllocationRuleSet
        var returnedFranchiseAllocationRuleSet = om.readValue(
            restFranchiseAllocationRuleSetMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSet))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseAllocationRuleSet.class
        );

        // Validate the FranchiseAllocationRuleSet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFranchiseAllocationRuleSetUpdatableFieldsEquals(
            returnedFranchiseAllocationRuleSet,
            getPersistedFranchiseAllocationRuleSet(returnedFranchiseAllocationRuleSet)
        );

        insertedFranchiseAllocationRuleSet = returnedFranchiseAllocationRuleSet;
    }

    @Test
    @Transactional
    void createFranchiseAllocationRuleSetWithExistingId() throws Exception {
        // Create the FranchiseAllocationRuleSet with an existing ID
        franchiseAllocationRuleSet.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseAllocationRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSet)))
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setName(null);

        // Create the FranchiseAllocationRuleSet, which fails.

        restFranchiseAllocationRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSortTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setSortType(null);

        // Create the FranchiseAllocationRuleSet, which fails.

        restFranchiseAllocationRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setIsActive(null);

        // Create the FranchiseAllocationRuleSet, which fails.

        restFranchiseAllocationRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setCreateddBy(null);

        // Create the FranchiseAllocationRuleSet, which fails.

        restFranchiseAllocationRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setCreatedTime(null);

        // Create the FranchiseAllocationRuleSet, which fails.

        restFranchiseAllocationRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setUpdatedBy(null);

        // Create the FranchiseAllocationRuleSet, which fails.

        restFranchiseAllocationRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setUpdatedTime(null);

        // Create the FranchiseAllocationRuleSet, which fails.

        restFranchiseAllocationRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSets() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList
        restFranchiseAllocationRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseAllocationRuleSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sortType").value(hasItem(DEFAULT_SORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchiseAllocationRuleSet() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get the franchiseAllocationRuleSet
        restFranchiseAllocationRuleSetMockMvc
            .perform(get(ENTITY_API_URL_ID, franchiseAllocationRuleSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchiseAllocationRuleSet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sortType").value(DEFAULT_SORT_TYPE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFranchiseAllocationRuleSet() throws Exception {
        // Get the franchiseAllocationRuleSet
        restFranchiseAllocationRuleSetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchiseAllocationRuleSet() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseAllocationRuleSet
        FranchiseAllocationRuleSet updatedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository
            .findById(franchiseAllocationRuleSet.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFranchiseAllocationRuleSet are not directly saved in db
        em.detach(updatedFranchiseAllocationRuleSet);
        updatedFranchiseAllocationRuleSet
            .name(UPDATED_NAME)
            .sortType(UPDATED_SORT_TYPE)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFranchiseAllocationRuleSet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFranchiseAllocationRuleSet))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseAllocationRuleSetToMatchAllProperties(updatedFranchiseAllocationRuleSet);
    }

    @Test
    @Transactional
    void putNonExistingFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseAllocationRuleSet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseAllocationRuleSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseAllocationRuleSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseAllocationRuleSetWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseAllocationRuleSet using partial update
        FranchiseAllocationRuleSet partialUpdatedFranchiseAllocationRuleSet = new FranchiseAllocationRuleSet();
        partialUpdatedFranchiseAllocationRuleSet.setId(franchiseAllocationRuleSet.getId());

        partialUpdatedFranchiseAllocationRuleSet
            .sortType(UPDATED_SORT_TYPE)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseAllocationRuleSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseAllocationRuleSet))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseAllocationRuleSet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseAllocationRuleSetUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchiseAllocationRuleSet, franchiseAllocationRuleSet),
            getPersistedFranchiseAllocationRuleSet(franchiseAllocationRuleSet)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseAllocationRuleSetWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseAllocationRuleSet using partial update
        FranchiseAllocationRuleSet partialUpdatedFranchiseAllocationRuleSet = new FranchiseAllocationRuleSet();
        partialUpdatedFranchiseAllocationRuleSet.setId(franchiseAllocationRuleSet.getId());

        partialUpdatedFranchiseAllocationRuleSet
            .name(UPDATED_NAME)
            .sortType(UPDATED_SORT_TYPE)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseAllocationRuleSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseAllocationRuleSet))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseAllocationRuleSet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseAllocationRuleSetUpdatableFieldsEquals(
            partialUpdatedFranchiseAllocationRuleSet,
            getPersistedFranchiseAllocationRuleSet(partialUpdatedFranchiseAllocationRuleSet)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseAllocationRuleSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseAllocationRuleSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseAllocationRuleSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseAllocationRuleSet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchiseAllocationRuleSet() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchiseAllocationRuleSet
        restFranchiseAllocationRuleSetMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchiseAllocationRuleSet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseAllocationRuleSetRepository.count();
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

    protected FranchiseAllocationRuleSet getPersistedFranchiseAllocationRuleSet(FranchiseAllocationRuleSet franchiseAllocationRuleSet) {
        return franchiseAllocationRuleSetRepository.findById(franchiseAllocationRuleSet.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseAllocationRuleSetToMatchAllProperties(
        FranchiseAllocationRuleSet expectedFranchiseAllocationRuleSet
    ) {
        assertFranchiseAllocationRuleSetAllPropertiesEquals(
            expectedFranchiseAllocationRuleSet,
            getPersistedFranchiseAllocationRuleSet(expectedFranchiseAllocationRuleSet)
        );
    }

    protected void assertPersistedFranchiseAllocationRuleSetToMatchUpdatableProperties(
        FranchiseAllocationRuleSet expectedFranchiseAllocationRuleSet
    ) {
        assertFranchiseAllocationRuleSetAllUpdatablePropertiesEquals(
            expectedFranchiseAllocationRuleSet,
            getPersistedFranchiseAllocationRuleSet(expectedFranchiseAllocationRuleSet)
        );
    }
}
