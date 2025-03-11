package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.AdditionalAttributePossibleValueAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.AdditionalAttributePossibleValue;
import com.framasaas.be.repository.AdditionalAttributePossibleValueRepository;
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
 * Integration tests for the {@link AdditionalAttributePossibleValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdditionalAttributePossibleValueResourceIT {

    private static final String DEFAULT_POSSIBLE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_POSSIBLE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/additional-attribute-possible-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdditionalAttributePossibleValueMockMvc;

    private AdditionalAttributePossibleValue additionalAttributePossibleValue;

    private AdditionalAttributePossibleValue insertedAdditionalAttributePossibleValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalAttributePossibleValue createEntity() {
        return new AdditionalAttributePossibleValue()
            .possibleValue(DEFAULT_POSSIBLE_VALUE)
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
    public static AdditionalAttributePossibleValue createUpdatedEntity() {
        return new AdditionalAttributePossibleValue()
            .possibleValue(UPDATED_POSSIBLE_VALUE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        additionalAttributePossibleValue = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAdditionalAttributePossibleValue != null) {
            additionalAttributePossibleValueRepository.delete(insertedAdditionalAttributePossibleValue);
            insertedAdditionalAttributePossibleValue = null;
        }
    }

    @Test
    @Transactional
    void createAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AdditionalAttributePossibleValue
        var returnedAdditionalAttributePossibleValue = om.readValue(
            restAdditionalAttributePossibleValueMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(additionalAttributePossibleValue))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdditionalAttributePossibleValue.class
        );

        // Validate the AdditionalAttributePossibleValue in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAdditionalAttributePossibleValueUpdatableFieldsEquals(
            returnedAdditionalAttributePossibleValue,
            getPersistedAdditionalAttributePossibleValue(returnedAdditionalAttributePossibleValue)
        );

        insertedAdditionalAttributePossibleValue = returnedAdditionalAttributePossibleValue;
    }

    @Test
    @Transactional
    void createAdditionalAttributePossibleValueWithExistingId() throws Exception {
        // Create the AdditionalAttributePossibleValue with an existing ID
        additionalAttributePossibleValue.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttributePossibleValue.setCreateddBy(null);

        // Create the AdditionalAttributePossibleValue, which fails.

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttributePossibleValue.setCreatedTime(null);

        // Create the AdditionalAttributePossibleValue, which fails.

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttributePossibleValue.setUpdatedBy(null);

        // Create the AdditionalAttributePossibleValue, which fails.

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttributePossibleValue.setUpdatedTime(null);

        // Create the AdditionalAttributePossibleValue, which fails.

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValues() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList
        restAdditionalAttributePossibleValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalAttributePossibleValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].possibleValue").value(hasItem(DEFAULT_POSSIBLE_VALUE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getAdditionalAttributePossibleValue() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get the additionalAttributePossibleValue
        restAdditionalAttributePossibleValueMockMvc
            .perform(get(ENTITY_API_URL_ID, additionalAttributePossibleValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(additionalAttributePossibleValue.getId().intValue()))
            .andExpect(jsonPath("$.possibleValue").value(DEFAULT_POSSIBLE_VALUE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAdditionalAttributePossibleValue() throws Exception {
        // Get the additionalAttributePossibleValue
        restAdditionalAttributePossibleValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdditionalAttributePossibleValue() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttributePossibleValue
        AdditionalAttributePossibleValue updatedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository
            .findById(additionalAttributePossibleValue.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAdditionalAttributePossibleValue are not directly saved in db
        em.detach(updatedAdditionalAttributePossibleValue);
        updatedAdditionalAttributePossibleValue
            .possibleValue(UPDATED_POSSIBLE_VALUE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdditionalAttributePossibleValue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAdditionalAttributePossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdditionalAttributePossibleValueToMatchAllProperties(updatedAdditionalAttributePossibleValue);
    }

    @Test
    @Transactional
    void putNonExistingAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalAttributePossibleValue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdditionalAttributePossibleValueWithPatch() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttributePossibleValue using partial update
        AdditionalAttributePossibleValue partialUpdatedAdditionalAttributePossibleValue = new AdditionalAttributePossibleValue();
        partialUpdatedAdditionalAttributePossibleValue.setId(additionalAttributePossibleValue.getId());

        partialUpdatedAdditionalAttributePossibleValue
            .possibleValue(UPDATED_POSSIBLE_VALUE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalAttributePossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdditionalAttributePossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttributePossibleValue in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdditionalAttributePossibleValueUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdditionalAttributePossibleValue, additionalAttributePossibleValue),
            getPersistedAdditionalAttributePossibleValue(additionalAttributePossibleValue)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdditionalAttributePossibleValueWithPatch() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttributePossibleValue using partial update
        AdditionalAttributePossibleValue partialUpdatedAdditionalAttributePossibleValue = new AdditionalAttributePossibleValue();
        partialUpdatedAdditionalAttributePossibleValue.setId(additionalAttributePossibleValue.getId());

        partialUpdatedAdditionalAttributePossibleValue
            .possibleValue(UPDATED_POSSIBLE_VALUE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalAttributePossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdditionalAttributePossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttributePossibleValue in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdditionalAttributePossibleValueUpdatableFieldsEquals(
            partialUpdatedAdditionalAttributePossibleValue,
            getPersistedAdditionalAttributePossibleValue(partialUpdatedAdditionalAttributePossibleValue)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, additionalAttributePossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(additionalAttributePossibleValue))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdditionalAttributePossibleValue() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the additionalAttributePossibleValue
        restAdditionalAttributePossibleValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, additionalAttributePossibleValue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return additionalAttributePossibleValueRepository.count();
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

    protected AdditionalAttributePossibleValue getPersistedAdditionalAttributePossibleValue(
        AdditionalAttributePossibleValue additionalAttributePossibleValue
    ) {
        return additionalAttributePossibleValueRepository.findById(additionalAttributePossibleValue.getId()).orElseThrow();
    }

    protected void assertPersistedAdditionalAttributePossibleValueToMatchAllProperties(
        AdditionalAttributePossibleValue expectedAdditionalAttributePossibleValue
    ) {
        assertAdditionalAttributePossibleValueAllPropertiesEquals(
            expectedAdditionalAttributePossibleValue,
            getPersistedAdditionalAttributePossibleValue(expectedAdditionalAttributePossibleValue)
        );
    }

    protected void assertPersistedAdditionalAttributePossibleValueToMatchUpdatableProperties(
        AdditionalAttributePossibleValue expectedAdditionalAttributePossibleValue
    ) {
        assertAdditionalAttributePossibleValueAllUpdatablePropertiesEquals(
            expectedAdditionalAttributePossibleValue,
            getPersistedAdditionalAttributePossibleValue(expectedAdditionalAttributePossibleValue)
        );
    }
}
