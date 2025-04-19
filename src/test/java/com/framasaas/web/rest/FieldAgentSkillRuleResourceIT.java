package com.framasaas.web.rest;

import static com.framasaas.domain.FieldAgentSkillRuleAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FieldAgentSkillRuleSet;
import com.framasaas.domain.enumeration.FieldAgentSkillRuleJoinType;
import com.framasaas.domain.enumeration.FieldAgentSkillRuleType;
import com.framasaas.repository.FieldAgentSkillRuleRepository;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import com.framasaas.service.mapper.FieldAgentSkillRuleMapper;
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
    private FieldAgentSkillRuleMapper fieldAgentSkillRuleMapper;

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
    void initTest() {
        fieldAgentSkillRule = createEntity();
    }

    @AfterEach
    void cleanup() {
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
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);
        var returnedFieldAgentSkillRuleDTO = om.readValue(
            restFieldAgentSkillRuleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FieldAgentSkillRuleDTO.class
        );

        // Validate the FieldAgentSkillRule in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFieldAgentSkillRule = fieldAgentSkillRuleMapper.toEntity(returnedFieldAgentSkillRuleDTO);
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
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldAgentSkillRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleDTO)))
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
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        restFieldAgentSkillRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleDTO)))
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
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        restFieldAgentSkillRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleDTO)))
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
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        restFieldAgentSkillRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleDTO)))
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
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        restFieldAgentSkillRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleDTO)))
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
    void getFieldAgentSkillRulesByIdFiltering() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        Long id = fieldAgentSkillRule.getId();

        defaultFieldAgentSkillRuleFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFieldAgentSkillRuleFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFieldAgentSkillRuleFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesBySkillTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where skillType equals to
        defaultFieldAgentSkillRuleFiltering("skillType.equals=" + DEFAULT_SKILL_TYPE, "skillType.equals=" + UPDATED_SKILL_TYPE);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesBySkillTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where skillType in
        defaultFieldAgentSkillRuleFiltering(
            "skillType.in=" + DEFAULT_SKILL_TYPE + "," + UPDATED_SKILL_TYPE,
            "skillType.in=" + UPDATED_SKILL_TYPE
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesBySkillTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where skillType is not null
        defaultFieldAgentSkillRuleFiltering("skillType.specified=true", "skillType.specified=false");
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByJoinTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where joinType equals to
        defaultFieldAgentSkillRuleFiltering("joinType.equals=" + DEFAULT_JOIN_TYPE, "joinType.equals=" + UPDATED_JOIN_TYPE);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByJoinTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where joinType in
        defaultFieldAgentSkillRuleFiltering(
            "joinType.in=" + DEFAULT_JOIN_TYPE + "," + UPDATED_JOIN_TYPE,
            "joinType.in=" + UPDATED_JOIN_TYPE
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByJoinTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where joinType is not null
        defaultFieldAgentSkillRuleFiltering("joinType.specified=true", "joinType.specified=false");
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where createddBy equals to
        defaultFieldAgentSkillRuleFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where createddBy in
        defaultFieldAgentSkillRuleFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where createddBy is not null
        defaultFieldAgentSkillRuleFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where createddBy contains
        defaultFieldAgentSkillRuleFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where createddBy does not contain
        defaultFieldAgentSkillRuleFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where createdTime equals to
        defaultFieldAgentSkillRuleFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where createdTime in
        defaultFieldAgentSkillRuleFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where createdTime is not null
        defaultFieldAgentSkillRuleFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where updatedBy equals to
        defaultFieldAgentSkillRuleFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where updatedBy in
        defaultFieldAgentSkillRuleFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where updatedBy is not null
        defaultFieldAgentSkillRuleFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where updatedBy contains
        defaultFieldAgentSkillRuleFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where updatedBy does not contain
        defaultFieldAgentSkillRuleFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where updatedTime equals to
        defaultFieldAgentSkillRuleFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where updatedTime in
        defaultFieldAgentSkillRuleFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRule = fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);

        // Get all the fieldAgentSkillRuleList where updatedTime is not null
        defaultFieldAgentSkillRuleFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRulesByFieldAgentSkillRuleSetIsEqualToSomething() throws Exception {
        FieldAgentSkillRuleSet fieldAgentSkillRuleSet;
        if (TestUtil.findAll(em, FieldAgentSkillRuleSet.class).isEmpty()) {
            fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);
            fieldAgentSkillRuleSet = FieldAgentSkillRuleSetResourceIT.createEntity();
        } else {
            fieldAgentSkillRuleSet = TestUtil.findAll(em, FieldAgentSkillRuleSet.class).get(0);
        }
        em.persist(fieldAgentSkillRuleSet);
        em.flush();
        fieldAgentSkillRule.setFieldAgentSkillRuleSet(fieldAgentSkillRuleSet);
        fieldAgentSkillRuleRepository.saveAndFlush(fieldAgentSkillRule);
        Long fieldAgentSkillRuleSetId = fieldAgentSkillRuleSet.getId();
        // Get all the fieldAgentSkillRuleList where fieldAgentSkillRuleSet equals to fieldAgentSkillRuleSetId
        defaultFieldAgentSkillRuleShouldBeFound("fieldAgentSkillRuleSetId.equals=" + fieldAgentSkillRuleSetId);

        // Get all the fieldAgentSkillRuleList where fieldAgentSkillRuleSet equals to (fieldAgentSkillRuleSetId + 1)
        defaultFieldAgentSkillRuleShouldNotBeFound("fieldAgentSkillRuleSetId.equals=" + (fieldAgentSkillRuleSetId + 1));
    }

    private void defaultFieldAgentSkillRuleFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFieldAgentSkillRuleShouldBeFound(shouldBeFound);
        defaultFieldAgentSkillRuleShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFieldAgentSkillRuleShouldBeFound(String filter) throws Exception {
        restFieldAgentSkillRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldAgentSkillRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillType").value(hasItem(DEFAULT_SKILL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].joinType").value(hasItem(DEFAULT_JOIN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restFieldAgentSkillRuleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFieldAgentSkillRuleShouldNotBeFound(String filter) throws Exception {
        restFieldAgentSkillRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFieldAgentSkillRuleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(updatedFieldAgentSkillRule);

        restFieldAgentSkillRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldAgentSkillRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleDTO))
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

        // Create the FieldAgentSkillRule
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldAgentSkillRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleDTO))
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

        // Create the FieldAgentSkillRule
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleDTO))
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

        // Create the FieldAgentSkillRule
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleDTO)))
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

        partialUpdatedFieldAgentSkillRule.skillType(UPDATED_SKILL_TYPE).joinType(UPDATED_JOIN_TYPE).updatedBy(UPDATED_UPDATED_BY);

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

        // Create the FieldAgentSkillRule
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldAgentSkillRuleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleDTO))
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

        // Create the FieldAgentSkillRule
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleDTO))
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

        // Create the FieldAgentSkillRule
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fieldAgentSkillRuleDTO))
            )
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
