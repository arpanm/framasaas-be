package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FieldAgentSkillRuleAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FieldAgentSkillRule;
import com.framasaas.be.domain.enumeration.FieldAgentSkillRuleJoinType;
import com.framasaas.be.domain.enumeration.FieldAgentSkillRuleType;
import com.framasaas.be.repository.FieldAgentSkillRuleRepository;
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
 * Integration tests for the {@link FieldAgentSkillRuleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldAgentSkillRuleResourceIT {

    private static final FieldAgentSkillRuleType DEFAULT_SKILL_TYPE = FieldAgentSkillRuleType.BRANDSKILL;
    private static final FieldAgentSkillRuleType UPDATED_SKILL_TYPE = FieldAgentSkillRuleType.CATEGORYSKILL;

    private static final FieldAgentSkillRuleJoinType DEFAULT_JOIN_TYPE = FieldAgentSkillRuleJoinType.ANDJOIN;
    private static final FieldAgentSkillRuleJoinType UPDATED_JOIN_TYPE = FieldAgentSkillRuleJoinType.ORJOIN;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/field-agent-skill-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FieldAgentSkillRuleRepository fieldAgentSkillRuleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldAgentSkillRuleMockMvc;

    private FieldAgentSkillRule fieldAgentSkillRule;

    private FieldAgentSkillRule insertedFieldAgentSkillRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldAgentSkillRule createEntity() {
        return new FieldAgentSkillRule()
            .skillType(DEFAULT_SKILL_TYPE)
            .joinType(DEFAULT_JOIN_TYPE)
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
    public static FieldAgentSkillRule createUpdatedEntity() {
        return new FieldAgentSkillRule()
            .skillType(UPDATED_SKILL_TYPE)
            .joinType(UPDATED_JOIN_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        fieldAgentSkillRule = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFieldAgentSkillRule != null) {
            fieldAgentSkillRuleRepository.delete(insertedFieldAgentSkillRule);
            insertedFieldAgentSkillRule = null;
        }
    }

    @Test
    @Transactional
    void createFieldAgentSkillRule() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FieldAgentSkillRule
        var returnedFieldAgentSkillRule = om.readValue(
            restFieldAgentSkillRuleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRule)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FieldAgentSkillRule.class
        );

        // Validate the FieldAgentSkillRule in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFieldAgentSkillRuleUpdatableFieldsEquals(
            returnedFieldAgentSkillRule,
            getPersistedFieldAgentSkillRule(returnedFieldAgentSkillRule)
        );

        insertedFieldAgentSkillRule = returnedFieldAgentSkillRule;
    }

    @Test
    @Transactional
    void createFieldAgentSkillRuleWithExistingId() throws Exception {
        // Create the FieldAgentSkillRule with an existing ID
        fieldAgentSkillRule.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldAgentSkillRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRule)))
            .andExpect(status().isBadRequest());

        // Validate the FieldAgentSkillRule in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldAgentSkillRule.setCreateddBy(null);

        // Create the FieldAgentSkillRule, which fails.

        restFieldAgentSkillRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldAgentSkillRule.setCreatedTime(null);

        // Create the FieldAgentSkillRule, which fails.

        restFieldAgentSkillRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldAgentSkillRule.setUpdatedBy(null);

        // Create the FieldAgentSkillRule, which fails.

        restFieldAgentSkillRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldAgentSkillRule.setUpdatedTime(null);

        // Create the FieldAgentSkillRule, which fails.

        restFieldAgentSkillRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRules() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList
        restFieldAgentSkillRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldAgentSkillRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillType").value(hasItem(DEFAULT_SKILL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].joinType").value(hasItem(DEFAULT_JOIN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFieldAgentSkillRule() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get the fieldAgentSkillRule
        restFieldAgentSkillRuleMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldAgentSkillRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldAgentSkillRule.getId().intValue()))
            .andExpect(jsonPath("$.skillType").value(DEFAULT_SKILL_TYPE.toString()))
            .andExpect(jsonPath("$.joinType").value(DEFAULT_JOIN_TYPE.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFieldAgentSkillRule() throws Exception {
        // Get the fieldAgentSkillRule
        restFieldAgentSkillRuleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldAgentSkillRule() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldAgentSkillRule
        FieldAgentSkillRule updatedFieldAgentSkillRule = fieldAgentSkillRuleRepository.findById(fieldAgentSkillRule.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFieldAgentSkillRule are not directly saved in db
        em.detach(updatedFieldAgentSkillRule);
        updatedFieldAgentSkillRule
            .skillType(UPDATED_SKILL_TYPE)
            .joinType(UPDATED_JOIN_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFieldAgentSkillRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFieldAgentSkillRule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFieldAgentSkillRule))
            )
            .andExpect(status().isOk());

        // Validate the FieldAgentSkillRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFieldAgentSkillRuleToMatchAllProperties(updatedFieldAgentSkillRule);
    }

    @Test
    @Transactional
    void putNonExistingFieldAgentSkillRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRule.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldAgentSkillRule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldAgentSkillRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAgentSkillRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldAgentSkillRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldAgentSkillRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAgentSkillRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldAgentSkillRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRule)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldAgentSkillRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldAgentSkillRuleWithPatch() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldAgentSkillRule using partial update
        FieldAgentSkillRule partialUpdatedFieldAgentSkillRule = new FieldAgentSkillRule();
        partialUpdatedFieldAgentSkillRule.setId(fieldAgentSkillRule.getId());

        partialUpdatedFieldAgentSkillRule
            .joinType(UPDATED_JOIN_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY);

        restFieldAgentSkillRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldAgentSkillRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFieldAgentSkillRule))
            )
            .andExpect(status().isOk());

        // Validate the FieldAgentSkillRule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFieldAgentSkillRuleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFieldAgentSkillRule, fieldAgentSkillRule),
            getPersistedFieldAgentSkillRule(fieldAgentSkillRule)
        );
    }

    @Test
    @Transactional
    void fullUpdateFieldAgentSkillRuleWithPatch() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldAgentSkillRule using partial update
        FieldAgentSkillRule partialUpdatedFieldAgentSkillRule = new FieldAgentSkillRule();
        partialUpdatedFieldAgentSkillRule.setId(fieldAgentSkillRule.getId());

        partialUpdatedFieldAgentSkillRule
            .skillType(UPDATED_SKILL_TYPE)
            .joinType(UPDATED_JOIN_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFieldAgentSkillRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldAgentSkillRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFieldAgentSkillRule))
            )
            .andExpect(status().isOk());

        // Validate the FieldAgentSkillRule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFieldAgentSkillRuleUpdatableFieldsEquals(
            partialUpdatedFieldAgentSkillRule,
            getPersistedFieldAgentSkillRule(partialUpdatedFieldAgentSkillRule)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFieldAgentSkillRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRule.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldAgentSkillRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldAgentSkillRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAgentSkillRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldAgentSkillRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldAgentSkillRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldAgentSkillRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldAgentSkillRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldAgentSkillRule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fieldAgentSkillRule)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldAgentSkillRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldAgentSkillRule() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fieldAgentSkillRule
        restFieldAgentSkillRuleMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldAgentSkillRule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fieldAgentSkillRuleRepository.count();
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

    protected FieldAgentSkillRule getPersistedFieldAgentSkillRule(FieldAgentSkillRule fieldAgentSkillRule) {
        return fieldAgentSkillRuleRepository.findById(fieldAgentSkillRule.getId()).orElseThrow();
    }

    protected void assertPersistedFieldAgentSkillRuleToMatchAllProperties(FieldAgentSkillRule expectedFieldAgentSkillRule) {
        assertFieldAgentSkillRuleAllPropertiesEquals(
            expectedFieldAgentSkillRule,
            getPersistedFieldAgentSkillRule(expectedFieldAgentSkillRule)
        );
    }

    protected void assertPersistedFieldAgentSkillRuleToMatchUpdatableProperties(FieldAgentSkillRule expectedFieldAgentSkillRule) {
        assertFieldAgentSkillRuleAllUpdatablePropertiesEquals(
            expectedFieldAgentSkillRule,
            getPersistedFieldAgentSkillRule(expectedFieldAgentSkillRule)
        );
    }
}
