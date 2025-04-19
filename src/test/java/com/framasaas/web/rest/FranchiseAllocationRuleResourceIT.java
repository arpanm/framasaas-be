package com.framasaas.web.rest;

import static com.framasaas.domain.FranchiseAllocationRuleAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.domain.enumeration.FranchiseAllocationRuleJoinType;
import com.framasaas.domain.enumeration.FranchiseAllocationRuleType;
import com.framasaas.repository.FranchiseAllocationRuleRepository;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import com.framasaas.service.mapper.FranchiseAllocationRuleMapper;
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
    private FranchiseAllocationRuleMapper franchiseAllocationRuleMapper;

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
    void initTest() {
        franchiseAllocationRule = createEntity();
    }

    @AfterEach
    void cleanup() {
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
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);
        var returnedFranchiseAllocationRuleDTO = om.readValue(
            restFranchiseAllocationRuleMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseAllocationRuleDTO.class
        );

        // Validate the FranchiseAllocationRule in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFranchiseAllocationRule = franchiseAllocationRuleMapper.toEntity(returnedFranchiseAllocationRuleDTO);
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
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleDTO)))
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
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleDTO)))
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
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleDTO)))
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
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleDTO)))
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
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleDTO)))
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
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleDTO)))
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
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        restFranchiseAllocationRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleDTO)))
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
    void getFranchiseAllocationRulesByIdFiltering() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        Long id = franchiseAllocationRule.getId();

        defaultFranchiseAllocationRuleFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFranchiseAllocationRuleFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFranchiseAllocationRuleFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByRuleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where ruleType equals to
        defaultFranchiseAllocationRuleFiltering("ruleType.equals=" + DEFAULT_RULE_TYPE, "ruleType.equals=" + UPDATED_RULE_TYPE);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByRuleTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where ruleType in
        defaultFranchiseAllocationRuleFiltering(
            "ruleType.in=" + DEFAULT_RULE_TYPE + "," + UPDATED_RULE_TYPE,
            "ruleType.in=" + UPDATED_RULE_TYPE
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByRuleTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where ruleType is not null
        defaultFranchiseAllocationRuleFiltering("ruleType.specified=true", "ruleType.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByJoinTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where joinType equals to
        defaultFranchiseAllocationRuleFiltering("joinType.equals=" + DEFAULT_JOIN_TYPE, "joinType.equals=" + UPDATED_JOIN_TYPE);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByJoinTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where joinType in
        defaultFranchiseAllocationRuleFiltering(
            "joinType.in=" + DEFAULT_JOIN_TYPE + "," + UPDATED_JOIN_TYPE,
            "joinType.in=" + UPDATED_JOIN_TYPE
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByJoinTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where joinType is not null
        defaultFranchiseAllocationRuleFiltering("joinType.specified=true", "joinType.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where createddBy equals to
        defaultFranchiseAllocationRuleFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where createddBy in
        defaultFranchiseAllocationRuleFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where createddBy is not null
        defaultFranchiseAllocationRuleFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where createddBy contains
        defaultFranchiseAllocationRuleFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where createddBy does not contain
        defaultFranchiseAllocationRuleFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where createdTime equals to
        defaultFranchiseAllocationRuleFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where createdTime in
        defaultFranchiseAllocationRuleFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where createdTime is not null
        defaultFranchiseAllocationRuleFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where updatedBy equals to
        defaultFranchiseAllocationRuleFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where updatedBy in
        defaultFranchiseAllocationRuleFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where updatedBy is not null
        defaultFranchiseAllocationRuleFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where updatedBy contains
        defaultFranchiseAllocationRuleFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where updatedBy does not contain
        defaultFranchiseAllocationRuleFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where updatedTime equals to
        defaultFranchiseAllocationRuleFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where updatedTime in
        defaultFranchiseAllocationRuleFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRulesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRule = franchiseAllocationRuleRepository.saveAndFlush(franchiseAllocationRule);

        // Get all the franchiseAllocationRuleList where updatedTime is not null
        defaultFranchiseAllocationRuleFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    private void defaultFranchiseAllocationRuleFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFranchiseAllocationRuleShouldBeFound(shouldBeFound);
        defaultFranchiseAllocationRuleShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFranchiseAllocationRuleShouldBeFound(String filter) throws Exception {
        restFranchiseAllocationRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseAllocationRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleType").value(hasItem(DEFAULT_RULE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].joinType").value(hasItem(DEFAULT_JOIN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restFranchiseAllocationRuleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFranchiseAllocationRuleShouldNotBeFound(String filter) throws Exception {
        restFranchiseAllocationRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFranchiseAllocationRuleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(updatedFranchiseAllocationRule);

        restFranchiseAllocationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseAllocationRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseAllocationRuleDTO))
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

        // Create the FranchiseAllocationRule
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseAllocationRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseAllocationRuleDTO))
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

        // Create the FranchiseAllocationRule
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseAllocationRuleDTO))
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

        // Create the FranchiseAllocationRule
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleDTO)))
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
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY);

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

        // Create the FranchiseAllocationRule
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseAllocationRuleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseAllocationRuleDTO))
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

        // Create the FranchiseAllocationRule
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseAllocationRuleDTO))
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

        // Create the FranchiseAllocationRule
        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseAllocationRuleDTO))
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
