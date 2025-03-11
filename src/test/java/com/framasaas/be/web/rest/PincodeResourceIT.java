package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.PincodeAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.Pincode;
import com.framasaas.be.repository.PincodeRepository;
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
 * Integration tests for the {@link PincodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PincodeResourceIT {

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/pincodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PincodeRepository pincodeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPincodeMockMvc;

    private Pincode pincode;

    private Pincode insertedPincode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pincode createEntity() {
        return new Pincode()
            .pincode(DEFAULT_PINCODE)
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
    public static Pincode createUpdatedEntity() {
        return new Pincode()
            .pincode(UPDATED_PINCODE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        pincode = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPincode != null) {
            pincodeRepository.delete(insertedPincode);
            insertedPincode = null;
        }
    }

    @Test
    @Transactional
    void createPincode() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pincode
        var returnedPincode = om.readValue(
            restPincodeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincode)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pincode.class
        );

        // Validate the Pincode in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPincodeUpdatableFieldsEquals(returnedPincode, getPersistedPincode(returnedPincode));

        insertedPincode = returnedPincode;
    }

    @Test
    @Transactional
    void createPincodeWithExistingId() throws Exception {
        // Create the Pincode with an existing ID
        pincode.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincode)))
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPincodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pincode.setPincode(null);

        // Create the Pincode, which fails.

        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pincode.setCreateddBy(null);

        // Create the Pincode, which fails.

        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pincode.setCreatedTime(null);

        // Create the Pincode, which fails.

        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pincode.setUpdatedBy(null);

        // Create the Pincode, which fails.

        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pincode.setUpdatedTime(null);

        // Create the Pincode, which fails.

        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPincodes() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList
        restPincodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincode.getId().intValue())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getPincode() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get the pincode
        restPincodeMockMvc
            .perform(get(ENTITY_API_URL_ID, pincode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pincode.getId().intValue()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPincode() throws Exception {
        // Get the pincode
        restPincodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPincode() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pincode
        Pincode updatedPincode = pincodeRepository.findById(pincode.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPincode are not directly saved in db
        em.detach(updatedPincode);
        updatedPincode
            .pincode(UPDATED_PINCODE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restPincodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPincode.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPincode))
            )
            .andExpect(status().isOk());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPincodeToMatchAllProperties(updatedPincode);
    }

    @Test
    @Transactional
    void putNonExistingPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(put(ENTITY_API_URL_ID, pincode.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincode)))
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pincode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePincodeWithPatch() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pincode using partial update
        Pincode partialUpdatedPincode = new Pincode();
        partialUpdatedPincode.setId(pincode.getId());

        partialUpdatedPincode.updatedBy(UPDATED_UPDATED_BY).updatedTime(UPDATED_UPDATED_TIME);

        restPincodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPincode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPincode))
            )
            .andExpect(status().isOk());

        // Validate the Pincode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPincodeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPincode, pincode), getPersistedPincode(pincode));
    }

    @Test
    @Transactional
    void fullUpdatePincodeWithPatch() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pincode using partial update
        Pincode partialUpdatedPincode = new Pincode();
        partialUpdatedPincode.setId(pincode.getId());

        partialUpdatedPincode
            .pincode(UPDATED_PINCODE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restPincodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPincode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPincode))
            )
            .andExpect(status().isOk());

        // Validate the Pincode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPincodeUpdatableFieldsEquals(partialUpdatedPincode, getPersistedPincode(partialUpdatedPincode));
    }

    @Test
    @Transactional
    void patchNonExistingPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pincode.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pincode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pincode))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pincode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePincode() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pincode
        restPincodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, pincode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pincodeRepository.count();
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

    protected Pincode getPersistedPincode(Pincode pincode) {
        return pincodeRepository.findById(pincode.getId()).orElseThrow();
    }

    protected void assertPersistedPincodeToMatchAllProperties(Pincode expectedPincode) {
        assertPincodeAllPropertiesEquals(expectedPincode, getPersistedPincode(expectedPincode));
    }

    protected void assertPersistedPincodeToMatchUpdatableProperties(Pincode expectedPincode) {
        assertPincodeAllUpdatablePropertiesEquals(expectedPincode, getPersistedPincode(expectedPincode));
    }
}
