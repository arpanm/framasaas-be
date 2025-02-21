package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FranchiseAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.Franchise;
import com.framasaas.be.domain.enumeration.FranchiseStatus;
import com.framasaas.be.domain.enumeration.PerformanceTag;
import com.framasaas.be.repository.FranchiseRepository;
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
 * Integration tests for the {@link FranchiseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseResourceIT {

    private static final String DEFAULT_FRANCHISE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FRANCHISE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "&@XId|&j.flg91,";
    private static final String UPDATED_EMAIL = "qY]@v.f#LA?";

    private static final Long DEFAULT_CONTACT = 1000000000L;
    private static final Long UPDATED_CONTACT = 1000000001L;

    private static final FranchiseStatus DEFAULT_FRANCHISE_STATUS = FranchiseStatus.PendingApproval;
    private static final FranchiseStatus UPDATED_FRANCHISE_STATUS = FranchiseStatus.Active;

    private static final String DEFAULT_GST_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GST_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_PERFORMANCE_SCORE = 1F;
    private static final Float UPDATED_PERFORMANCE_SCORE = 2F;

    private static final PerformanceTag DEFAULT_PERFORMANCE_TAG = PerformanceTag.High;
    private static final PerformanceTag UPDATED_PERFORMANCE_TAG = PerformanceTag.Medium;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseMockMvc;

    private Franchise franchise;

    private Franchise insertedFranchise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Franchise createEntity() {
        return new Franchise()
            .franchiseName(DEFAULT_FRANCHISE_NAME)
            .owner(DEFAULT_OWNER)
            .email(DEFAULT_EMAIL)
            .contact(DEFAULT_CONTACT)
            .franchiseStatus(DEFAULT_FRANCHISE_STATUS)
            .gstNumber(DEFAULT_GST_NUMBER)
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .performanceScore(DEFAULT_PERFORMANCE_SCORE)
            .performanceTag(DEFAULT_PERFORMANCE_TAG)
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
    public static Franchise createUpdatedEntity() {
        return new Franchise()
            .franchiseName(UPDATED_FRANCHISE_NAME)
            .owner(UPDATED_OWNER)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .franchiseStatus(UPDATED_FRANCHISE_STATUS)
            .gstNumber(UPDATED_GST_NUMBER)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        franchise = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFranchise != null) {
            franchiseRepository.delete(insertedFranchise);
            insertedFranchise = null;
        }
    }

    @Test
    @Transactional
    void createFranchise() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Franchise
        var returnedFranchise = om.readValue(
            restFranchiseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Franchise.class
        );

        // Validate the Franchise in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFranchiseUpdatableFieldsEquals(returnedFranchise, getPersistedFranchise(returnedFranchise));

        insertedFranchise = returnedFranchise;
    }

    @Test
    @Transactional
    void createFranchiseWithExistingId() throws Exception {
        // Create the Franchise with an existing ID
        franchise.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise)))
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFranchiseNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setFranchiseName(null);

        // Create the Franchise, which fails.

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setEmail(null);

        // Create the Franchise, which fails.

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setContact(null);

        // Create the Franchise, which fails.

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setCreateddBy(null);

        // Create the Franchise, which fails.

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setCreatedTime(null);

        // Create the Franchise, which fails.

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setUpdatedBy(null);

        // Create the Franchise, which fails.

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setUpdatedTime(null);

        // Create the Franchise, which fails.

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchises() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList
        restFranchiseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchise.getId().intValue())))
            .andExpect(jsonPath("$.[*].franchiseName").value(hasItem(DEFAULT_FRANCHISE_NAME)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.intValue())))
            .andExpect(jsonPath("$.[*].franchiseStatus").value(hasItem(DEFAULT_FRANCHISE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gstNumber").value(hasItem(DEFAULT_GST_NUMBER)))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].performanceScore").value(hasItem(DEFAULT_PERFORMANCE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].performanceTag").value(hasItem(DEFAULT_PERFORMANCE_TAG.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchise() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get the franchise
        restFranchiseMockMvc
            .perform(get(ENTITY_API_URL_ID, franchise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchise.getId().intValue()))
            .andExpect(jsonPath("$.franchiseName").value(DEFAULT_FRANCHISE_NAME))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.intValue()))
            .andExpect(jsonPath("$.franchiseStatus").value(DEFAULT_FRANCHISE_STATUS.toString()))
            .andExpect(jsonPath("$.gstNumber").value(DEFAULT_GST_NUMBER))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.performanceScore").value(DEFAULT_PERFORMANCE_SCORE.doubleValue()))
            .andExpect(jsonPath("$.performanceTag").value(DEFAULT_PERFORMANCE_TAG.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFranchise() throws Exception {
        // Get the franchise
        restFranchiseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchise() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchise
        Franchise updatedFranchise = franchiseRepository.findById(franchise.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFranchise are not directly saved in db
        em.detach(updatedFranchise);
        updatedFranchise
            .franchiseName(UPDATED_FRANCHISE_NAME)
            .owner(UPDATED_OWNER)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .franchiseStatus(UPDATED_FRANCHISE_STATUS)
            .gstNumber(UPDATED_GST_NUMBER)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFranchise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFranchise))
            )
            .andExpect(status().isOk());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseToMatchAllProperties(updatedFranchise);
    }

    @Test
    @Transactional
    void putNonExistingFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchise.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchise)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseWithPatch() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchise using partial update
        Franchise partialUpdatedFranchise = new Franchise();
        partialUpdatedFranchise.setId(franchise.getId());

        partialUpdatedFranchise
            .franchiseName(UPDATED_FRANCHISE_NAME)
            .email(UPDATED_EMAIL)
            .franchiseStatus(UPDATED_FRANCHISE_STATUS)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY);

        restFranchiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchise.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchise))
            )
            .andExpect(status().isOk());

        // Validate the Franchise in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchise, franchise),
            getPersistedFranchise(franchise)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseWithPatch() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchise using partial update
        Franchise partialUpdatedFranchise = new Franchise();
        partialUpdatedFranchise.setId(franchise.getId());

        partialUpdatedFranchise
            .franchiseName(UPDATED_FRANCHISE_NAME)
            .owner(UPDATED_OWNER)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .franchiseStatus(UPDATED_FRANCHISE_STATUS)
            .gstNumber(UPDATED_GST_NUMBER)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchise.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchise))
            )
            .andExpect(status().isOk());

        // Validate the Franchise in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseUpdatableFieldsEquals(partialUpdatedFranchise, getPersistedFranchise(partialUpdatedFranchise));
    }

    @Test
    @Transactional
    void patchNonExistingFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchise.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchise)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchise() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchise
        restFranchiseMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseRepository.count();
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

    protected Franchise getPersistedFranchise(Franchise franchise) {
        return franchiseRepository.findById(franchise.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseToMatchAllProperties(Franchise expectedFranchise) {
        assertFranchiseAllPropertiesEquals(expectedFranchise, getPersistedFranchise(expectedFranchise));
    }

    protected void assertPersistedFranchiseToMatchUpdatableProperties(Franchise expectedFranchise) {
        assertFranchiseAllUpdatablePropertiesEquals(expectedFranchise, getPersistedFranchise(expectedFranchise));
    }
}
