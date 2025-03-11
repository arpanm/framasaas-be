package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.AdditionalAttributeAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.AdditionalAttribute;
import com.framasaas.be.domain.enumeration.AttributeType;
import com.framasaas.be.repository.AdditionalAttributeRepository;
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
 * Integration tests for the {@link AdditionalAttributeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdditionalAttributeResourceIT {

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final AttributeType DEFAULT_ATTRIBUTE_TYPE = AttributeType.ATTRSTRING;
    private static final AttributeType UPDATED_ATTRIBUTE_TYPE = AttributeType.ATTRNUMBER;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/additional-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdditionalAttributeRepository additionalAttributeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdditionalAttributeMockMvc;

    private AdditionalAttribute additionalAttribute;

    private AdditionalAttribute insertedAdditionalAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalAttribute createEntity() {
        return new AdditionalAttribute()
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE)
            .attributeType(DEFAULT_ATTRIBUTE_TYPE)
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
    public static AdditionalAttribute createUpdatedEntity() {
        return new AdditionalAttribute()
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .attributeType(UPDATED_ATTRIBUTE_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        additionalAttribute = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAdditionalAttribute != null) {
            additionalAttributeRepository.delete(insertedAdditionalAttribute);
            insertedAdditionalAttribute = null;
        }
    }

    @Test
    @Transactional
    void createAdditionalAttribute() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AdditionalAttribute
        var returnedAdditionalAttribute = om.readValue(
            restAdditionalAttributeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttribute)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdditionalAttribute.class
        );

        // Validate the AdditionalAttribute in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAdditionalAttributeUpdatableFieldsEquals(
            returnedAdditionalAttribute,
            getPersistedAdditionalAttribute(returnedAdditionalAttribute)
        );

        insertedAdditionalAttribute = returnedAdditionalAttribute;
    }

    @Test
    @Transactional
    void createAdditionalAttributeWithExistingId() throws Exception {
        // Create the AdditionalAttribute with an existing ID
        additionalAttribute.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttributeNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setAttributeName(null);

        // Create the AdditionalAttribute, which fails.

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttribute)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAttributeValueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setAttributeValue(null);

        // Create the AdditionalAttribute, which fails.

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttribute)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setCreateddBy(null);

        // Create the AdditionalAttribute, which fails.

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttribute)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setCreatedTime(null);

        // Create the AdditionalAttribute, which fails.

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttribute)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setUpdatedBy(null);

        // Create the AdditionalAttribute, which fails.

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttribute)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setUpdatedTime(null);

        // Create the AdditionalAttribute, which fails.

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttribute)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributes() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList
        restAdditionalAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)))
            .andExpect(jsonPath("$.[*].attributeType").value(hasItem(DEFAULT_ATTRIBUTE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getAdditionalAttribute() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get the additionalAttribute
        restAdditionalAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, additionalAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(additionalAttribute.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE))
            .andExpect(jsonPath("$.attributeType").value(DEFAULT_ATTRIBUTE_TYPE.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAdditionalAttribute() throws Exception {
        // Get the additionalAttribute
        restAdditionalAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdditionalAttribute() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttribute
        AdditionalAttribute updatedAdditionalAttribute = additionalAttributeRepository.findById(additionalAttribute.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdditionalAttribute are not directly saved in db
        em.detach(updatedAdditionalAttribute);
        updatedAdditionalAttribute
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .attributeType(UPDATED_ATTRIBUTE_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAdditionalAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdditionalAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAdditionalAttribute))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdditionalAttributeToMatchAllProperties(updatedAdditionalAttribute);
    }

    @Test
    @Transactional
    void putNonExistingAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalAttribute.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttribute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdditionalAttributeWithPatch() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttribute using partial update
        AdditionalAttribute partialUpdatedAdditionalAttribute = new AdditionalAttribute();
        partialUpdatedAdditionalAttribute.setId(additionalAttribute.getId());

        partialUpdatedAdditionalAttribute.attributeName(UPDATED_ATTRIBUTE_NAME).createdTime(UPDATED_CREATED_TIME);

        restAdditionalAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdditionalAttribute))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttribute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdditionalAttributeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdditionalAttribute, additionalAttribute),
            getPersistedAdditionalAttribute(additionalAttribute)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdditionalAttributeWithPatch() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttribute using partial update
        AdditionalAttribute partialUpdatedAdditionalAttribute = new AdditionalAttribute();
        partialUpdatedAdditionalAttribute.setId(additionalAttribute.getId());

        partialUpdatedAdditionalAttribute
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .attributeType(UPDATED_ATTRIBUTE_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAdditionalAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdditionalAttribute))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttribute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdditionalAttributeUpdatableFieldsEquals(
            partialUpdatedAdditionalAttribute,
            getPersistedAdditionalAttribute(partialUpdatedAdditionalAttribute)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, additionalAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(additionalAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(additionalAttribute))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(additionalAttribute)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdditionalAttribute() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the additionalAttribute
        restAdditionalAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, additionalAttribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return additionalAttributeRepository.count();
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

    protected AdditionalAttribute getPersistedAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        return additionalAttributeRepository.findById(additionalAttribute.getId()).orElseThrow();
    }

    protected void assertPersistedAdditionalAttributeToMatchAllProperties(AdditionalAttribute expectedAdditionalAttribute) {
        assertAdditionalAttributeAllPropertiesEquals(
            expectedAdditionalAttribute,
            getPersistedAdditionalAttribute(expectedAdditionalAttribute)
        );
    }

    protected void assertPersistedAdditionalAttributeToMatchUpdatableProperties(AdditionalAttribute expectedAdditionalAttribute) {
        assertAdditionalAttributeAllUpdatablePropertiesEquals(
            expectedAdditionalAttribute,
            getPersistedAdditionalAttribute(expectedAdditionalAttribute)
        );
    }
}
