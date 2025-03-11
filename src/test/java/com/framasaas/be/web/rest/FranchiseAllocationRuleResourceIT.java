package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FranchiseAllocationRuleAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FranchiseAllocationRule;
import com.framasaas.be.domain.enumeration.FranchiseAllocationRuleJoinType;
import com.framasaas.be.domain.enumeration.FranchiseAllocationRuleType;
import com.framasaas.be.repository.FranchiseAllocationRuleRepository;
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
 * Integration tests for the {@link FranchiseAllocationRuleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseAllocationRuleResourceIT {

    private static final FranchiseAllocationRuleType DEFAULT_RULE_TYPE = FranchiseAllocationRuleType.BRANDRULE;
    private static final FranchiseAllocationRuleType UPDATED_RULE_TYPE = FranchiseAllocationRuleType.CATEGORYRULE;

    private static final FranchiseAllocationRuleJoinType DEFAULT_JOIN_TYPE = FranchiseAllocationRuleJoinType.ANDJOIN;
    private static final FranchiseAllocationRuleJoinType UPDATED_JOIN_TYPE = FranchiseAllocationRuleJoinType.ORJOIN;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchise-allocation-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseAllocationRuleRepository franchiseAllocationRuleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseAllocationRuleMockMvc;

    private FranchiseAllocationRule franchiseAllocationRule;

    private FranchiseAllocationRule insertedFranchiseAllocationRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseAllocationRule createEntity() {
        return new FranchiseAllocationRule()
            .ruleType(DEFAULT_RULE_TYPE)
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
    public static FranchiseAllocationRule createUpdatedEntity() {
        return new FranchiseAllocationRule()
            .ruleType(UPDATED_RULE_TYPE)
            .joinType(UPDATED_JOIN_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        franchiseAllocationRule = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFranchiseAllocationRule != null) {
            franchiseAllocationRuleRepository.delete(insertedFranchiseAllocationRule);
            insertedFranchiseAllocationRule = null;
        }
    }

    @Test
    @Transactional
    void createFranchiseAllocationRule() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchiseAllocationRule
        var returnedFranchiseAllocationRule = om.readValue(
            restFranchiseAllocationRuleMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRule))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseAllocationRule.class
        );

        // Validate the FranchiseAllocationRule in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFranchiseAllocationRuleUpdatableFieldsEquals(
            returnedFranchiseAllocationRule,
            getPersistedFranchiseAllocationRule(returnedFranchiseAllocationRule)
        );

        insertedFranchiseAllocationRule = returnedFranchiseAllocationRule;
    }

    @Test
    @Transactional
    void createFranchiseAllocationRuleWithExistingId() throws Exception {
        // Create the FranchiseAllocationRule with an existing ID
        franchiseAllocationRule.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRule)))
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRule in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRuleTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRule.setRuleType(null);

        // Create the FranchiseAllocationRule, which fails.

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJoinTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRule.setJoinType(null);

        // Create the FranchiseAllocationRule, which fails.

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRule.setCreateddBy(null);

        // Create the FranchiseAllocationRule, which fails.

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRule.setCreatedTime(null);

        // Create the FranchiseAllocationRule, which fails.

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRule.setUpdatedBy(null);

        // Create the FranchiseAllocationRule, which fails.

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRule.setUpdatedTime(null);

        // Create the FranchiseAllocationRule, which fails.

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRule)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRules() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList
        restFranchiseAllocationRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseAllocationRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleType").value(hasItem(DEFAULT_RULE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].joinType").value(hasItem(DEFAULT_JOIN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchiseAllocationRule() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get the franchiseAllocationRule
        restFranchiseAllocationRuleMockMvc
            .perform(get(ENTITY_API_URL_ID, franchiseAllocationRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchiseAllocationRule.getId().intValue()))
            .andExpect(jsonPath("$.ruleType").value(DEFAULT_RULE_TYPE.toString()))
            .andExpect(jsonPath("$.joinType").value(DEFAULT_JOIN_TYPE.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFranchiseAllocationRule() throws Exception {
        // Get the franchiseAllocationRule
        restFranchiseAllocationRuleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchiseAllocationRule() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseAllocationRule
        FranchiseAllocationRule updatedFranchiseAllocationRule = franchiseAllocationRuleRepository
            .findById(franchiseAllocationRule.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFranchiseAllocationRule are not directly saved in db
        em.detach(updatedFranchiseAllocationRule);
        updatedFranchiseAllocationRule
            .ruleType(UPDATED_RULE_TYPE)
            .joinType(UPDATED_JOIN_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseAllocationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFranchiseAllocationRule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFranchiseAllocationRule))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseAllocationRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseAllocationRuleToMatchAllProperties(updatedFranchiseAllocationRule);
    }

    @Test
    @Transactional
    void putNonExistingFranchiseAllocationRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRule.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseAllocationRule.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseAllocationRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchiseAllocationRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseAllocationRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchiseAllocationRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRule)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseAllocationRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseAllocationRuleWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseAllocationRule using partial update
        FranchiseAllocationRule partialUpdatedFranchiseAllocationRule = new FranchiseAllocationRule();
        partialUpdatedFranchiseAllocationRule.setId(franchiseAllocationRule.getId());

        partialUpdatedFranchiseAllocationRule
            .ruleType(UPDATED_RULE_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseAllocationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseAllocationRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseAllocationRule))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseAllocationRule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseAllocationRuleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchiseAllocationRule, franchiseAllocationRule),
            getPersistedFranchiseAllocationRule(franchiseAllocationRule)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseAllocationRuleWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseAllocationRule using partial update
        FranchiseAllocationRule partialUpdatedFranchiseAllocationRule = new FranchiseAllocationRule();
        partialUpdatedFranchiseAllocationRule.setId(franchiseAllocationRule.getId());

        partialUpdatedFranchiseAllocationRule
            .ruleType(UPDATED_RULE_TYPE)
            .joinType(UPDATED_JOIN_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseAllocationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseAllocationRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseAllocationRule))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseAllocationRule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseAllocationRuleUpdatableFieldsEquals(
            partialUpdatedFranchiseAllocationRule,
            getPersistedFranchiseAllocationRule(partialUpdatedFranchiseAllocationRule)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFranchiseAllocationRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRule.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseAllocationRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseAllocationRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchiseAllocationRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseAllocationRule))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchiseAllocationRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRule.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseAllocationRule))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseAllocationRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchiseAllocationRule() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchiseAllocationRule
        restFranchiseAllocationRuleMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchiseAllocationRule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseAllocationRuleRepository.count();
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

    protected FranchiseAllocationRule getPersistedFranchiseAllocationRule(FranchiseAllocationRule franchiseAllocationRule) {
        return franchiseAllocationRuleRepository.findById(franchiseAllocationRule.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseAllocationRuleToMatchAllProperties(FranchiseAllocationRule expectedFranchiseAllocationRule) {
        assertFranchiseAllocationRuleAllPropertiesEquals(
            expectedFranchiseAllocationRule,
            getPersistedFranchiseAllocationRule(expectedFranchiseAllocationRule)
        );
    }

    protected void assertPersistedFranchiseAllocationRuleToMatchUpdatableProperties(
        FranchiseAllocationRule expectedFranchiseAllocationRule
    ) {
        assertFranchiseAllocationRuleAllUpdatablePropertiesEquals(
            expectedFranchiseAllocationRule,
            getPersistedFranchiseAllocationRule(expectedFranchiseAllocationRule)
        );
    }
}
