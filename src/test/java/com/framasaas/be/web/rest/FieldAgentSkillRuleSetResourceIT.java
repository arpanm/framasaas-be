package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FieldAgentSkillRuleSetAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FieldAgentSkillRuleSet;
import com.framasaas.be.domain.enumeration.FieldAgentSkillRuleSetSortType;
import com.framasaas.be.repository.FieldAgentSkillRuleSetRepository;
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
 * Integration tests for the {@link FieldAgentSkillRuleSetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldAgentSkillRuleSetResourceIT {

    private static final FieldAgentSkillRuleSetSortType DEFAULT_SORT_TYPE = FieldAgentSkillRuleSetSortType.NPS;
    private static final FieldAgentSkillRuleSetSortType UPDATED_SORT_TYPE = FieldAgentSkillRuleSetSortType.ROUNDROBIN;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/field-agent-skill-rule-sets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FieldAgentSkillRuleSetRepository fieldAgentSkillRuleSetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldAgentSkillRuleSetMockMvc;

    private FieldAgentSkillRuleSet fieldAgentSkillRuleSet;

    private FieldAgentSkillRuleSet insertedFieldAgentSkillRuleSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldAgentSkillRuleSet createEntity() {
        return new FieldAgentSkillRuleSet()
            .sortType(DEFAULT_SORT_TYPE)
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
    public static FieldAgentSkillRuleSet createUpdatedEntity() {
        return new FieldAgentSkillRuleSet()
            .sortType(UPDATED_SORT_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        fieldAgentSkillRuleSet = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFieldAgentSkillRuleSet != null) {
            fieldAgentSkillRuleSetRepository.delete(insertedFieldAgentSkillRuleSet);
            insertedFieldAgentSkillRuleSet = null;
        }
    }

    @Test
    @Transactional
    void createFieldAgentSkillRuleSet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FieldAgentSkillRuleSet
        var returnedFieldAgentSkillRuleSet = om.readValue(
            restFieldAgentSkillRuleSetMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSet)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FieldAgentSkillRuleSet.class
        );

        // Validate the FieldAgentSkillRuleSet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFieldAgentSkillRuleSetUpdatableFieldsEquals(
            returnedFieldAgentSkillRuleSet,
            getPersistedFieldAgentSkillRuleSet(returnedFieldAgentSkillRuleSet)
        );

        insertedFieldAgentSkillRuleSet = returnedFieldAgentSkillRuleSet;
    }

    @Test
    @Transactional
    void createFieldAgentSkillRuleSetWithExistingId() throws Exception {
        // Create the FieldAgentSkillRuleSet with an existing ID
        fieldAgentSkillRuleSet.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldAgentSkillRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSet)))
            .andExpect(status().isBadRequest());

        // Validate the FieldAgentSkillRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldAgentSkillRuleSet.setCreateddBy(null);

        // Create the FieldAgentSkillRuleSet, which fails.

        restFieldAgentSkillRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldAgentSkillRuleSet.setCreatedTime(null);

        // Create the FieldAgentSkillRuleSet, which fails.

        restFieldAgentSkillRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldAgentSkillRuleSet.setUpdatedBy(null);

        // Create the FieldAgentSkillRuleSet, which fails.

        restFieldAgentSkillRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldAgentSkillRuleSet.setUpdatedTime(null);

        // Create the FieldAgentSkillRuleSet, which fails.

        restFieldAgentSkillRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSet)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSets() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList
        restFieldAgentSkillRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldAgentSkillRuleSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].sortType").value(hasItem(DEFAULT_SORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFieldAgentSkillRuleSet() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get the fieldAgentSkillRuleSet
        restFieldAgentSkillRuleSetMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldAgentSkillRuleSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldAgentSkillRuleSet.getId().intValue()))
            .andExpect(jsonPath("$.sortType").value(DEFAULT_SORT_TYPE.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFieldAgentSkillRuleSet() throws Exception {
        // Get the fieldAgentSkillRuleSet
        restFieldAgentSkillRuleSetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldAgentSkillRuleSet() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldAgentSkillRuleSet
        FieldAgentSkillRuleSet updatedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository
            .findById(fieldAgentSkillRuleSet.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFieldAgentSkillRuleSet are not directly saved in db
        em.detach(updatedFieldAgentSkillRuleSet);
        updatedFieldAgentSkillRuleSet
            .sortType(UPDATED_SORT_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFieldAgentSkillRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFieldAgentSkillRuleSet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFieldAgentSkillRuleSet))
            )
            .andExpect(status().isOk());

        // Validate the FieldAgentSkillRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFieldAgentSkillRuleSetToMatchAllProperties(updatedFieldAgentSkillRuleSet);
    }

    @Test
    @Transactional
    void putNonExistingFieldAgentSkillRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRuleSet.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldAgentSkillRuleSet.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAgentSkillRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldAgentSkillRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRuleSet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAgentSkillRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldAgentSkillRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRuleSet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSet)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldAgentSkillRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldAgentSkillRuleSetWithPatch() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldAgentSkillRuleSet using partial update
        FieldAgentSkillRuleSet partialUpdatedFieldAgentSkillRuleSet = new FieldAgentSkillRuleSet();
        partialUpdatedFieldAgentSkillRuleSet.setId(fieldAgentSkillRuleSet.getId());

        partialUpdatedFieldAgentSkillRuleSet.createddBy(UPDATED_CREATEDD_BY).updatedTime(UPDATED_UPDATED_TIME);

        restFieldAgentSkillRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldAgentSkillRuleSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFieldAgentSkillRuleSet))
            )
            .andExpect(status().isOk());

        // Validate the FieldAgentSkillRuleSet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFieldAgentSkillRuleSetUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFieldAgentSkillRuleSet, fieldAgentSkillRuleSet),
            getPersistedFieldAgentSkillRuleSet(fieldAgentSkillRuleSet)
        );
    }

    @Test
    @Transactional
    void fullUpdateFieldAgentSkillRuleSetWithPatch() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldAgentSkillRuleSet using partial update
        FieldAgentSkillRuleSet partialUpdatedFieldAgentSkillRuleSet = new FieldAgentSkillRuleSet();
        partialUpdatedFieldAgentSkillRuleSet.setId(fieldAgentSkillRuleSet.getId());

        partialUpdatedFieldAgentSkillRuleSet
            .sortType(UPDATED_SORT_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFieldAgentSkillRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldAgentSkillRuleSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFieldAgentSkillRuleSet))
            )
            .andExpect(status().isOk());

        // Validate the FieldAgentSkillRuleSet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFieldAgentSkillRuleSetUpdatableFieldsEquals(
            partialUpdatedFieldAgentSkillRuleSet,
            getPersistedFieldAgentSkillRuleSet(partialUpdatedFieldAgentSkillRuleSet)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFieldAgentSkillRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRuleSet.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldAgentSkillRuleSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAgentSkillRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldAgentSkillRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRuleSet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleSet))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAgentSkillRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldAgentSkillRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRuleSet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fieldAgentSkillRuleSet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldAgentSkillRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldAgentSkillRuleSet() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fieldAgentSkillRuleSet
        restFieldAgentSkillRuleSetMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldAgentSkillRuleSet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fieldAgentSkillRuleSetRepository.count();
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

    protected FieldAgentSkillRuleSet getPersistedFieldAgentSkillRuleSet(FieldAgentSkillRuleSet fieldAgentSkillRuleSet) {
        return fieldAgentSkillRuleSetRepository.findById(fieldAgentSkillRuleSet.getId()).orElseThrow();
    }

    protected void assertPersistedFieldAgentSkillRuleSetToMatchAllProperties(FieldAgentSkillRuleSet expectedFieldAgentSkillRuleSet) {
        assertFieldAgentSkillRuleSetAllPropertiesEquals(
            expectedFieldAgentSkillRuleSet,
            getPersistedFieldAgentSkillRuleSet(expectedFieldAgentSkillRuleSet)
        );
    }

    protected void assertPersistedFieldAgentSkillRuleSetToMatchUpdatableProperties(FieldAgentSkillRuleSet expectedFieldAgentSkillRuleSet) {
        assertFieldAgentSkillRuleSetAllUpdatablePropertiesEquals(
            expectedFieldAgentSkillRuleSet,
            getPersistedFieldAgentSkillRuleSet(expectedFieldAgentSkillRuleSet)
        );
    }
}
