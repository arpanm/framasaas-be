package com.framasaas.web.rest;

import static com.framasaas.domain.FieldAgentSkillRuleSetAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.FieldAgentSkillRuleSet;
import com.framasaas.domain.enumeration.FieldAgentSkillRuleSetSortType;
import com.framasaas.repository.FieldAgentSkillRuleSetRepository;
import com.framasaas.service.dto.FieldAgentSkillRuleSetDTO;
import com.framasaas.service.mapper.FieldAgentSkillRuleSetMapper;
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
    private FieldAgentSkillRuleSetMapper fieldAgentSkillRuleSetMapper;

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
    void initTest() {
        fieldAgentSkillRuleSet = createEntity();
    }

    @AfterEach
    void cleanup() {
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
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);
        var returnedFieldAgentSkillRuleSetDTO = om.readValue(
            restFieldAgentSkillRuleSetMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FieldAgentSkillRuleSetDTO.class
        );

        // Validate the FieldAgentSkillRuleSet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetMapper.toEntity(returnedFieldAgentSkillRuleSetDTO);
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
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldAgentSkillRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO)))
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
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        restFieldAgentSkillRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO)))
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
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        restFieldAgentSkillRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO)))
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
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        restFieldAgentSkillRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO)))
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
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        restFieldAgentSkillRuleSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO)))
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
    void getFieldAgentSkillRuleSetsByIdFiltering() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        Long id = fieldAgentSkillRuleSet.getId();

        defaultFieldAgentSkillRuleSetFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFieldAgentSkillRuleSetFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFieldAgentSkillRuleSetFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsBySortTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where sortType equals to
        defaultFieldAgentSkillRuleSetFiltering("sortType.equals=" + DEFAULT_SORT_TYPE, "sortType.equals=" + UPDATED_SORT_TYPE);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsBySortTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where sortType in
        defaultFieldAgentSkillRuleSetFiltering(
            "sortType.in=" + DEFAULT_SORT_TYPE + "," + UPDATED_SORT_TYPE,
            "sortType.in=" + UPDATED_SORT_TYPE
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsBySortTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where sortType is not null
        defaultFieldAgentSkillRuleSetFiltering("sortType.specified=true", "sortType.specified=false");
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where createddBy equals to
        defaultFieldAgentSkillRuleSetFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where createddBy in
        defaultFieldAgentSkillRuleSetFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where createddBy is not null
        defaultFieldAgentSkillRuleSetFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where createddBy contains
        defaultFieldAgentSkillRuleSetFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where createddBy does not contain
        defaultFieldAgentSkillRuleSetFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where createdTime equals to
        defaultFieldAgentSkillRuleSetFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where createdTime in
        defaultFieldAgentSkillRuleSetFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where createdTime is not null
        defaultFieldAgentSkillRuleSetFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where updatedBy equals to
        defaultFieldAgentSkillRuleSetFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where updatedBy in
        defaultFieldAgentSkillRuleSetFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where updatedBy is not null
        defaultFieldAgentSkillRuleSetFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where updatedBy contains
        defaultFieldAgentSkillRuleSetFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where updatedBy does not contain
        defaultFieldAgentSkillRuleSetFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where updatedTime equals to
        defaultFieldAgentSkillRuleSetFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where updatedTime in
        defaultFieldAgentSkillRuleSetFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFieldAgentSkillRuleSetsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.saveAndFlush(fieldAgentSkillRuleSet);

        // Get all the fieldAgentSkillRuleSetList where updatedTime is not null
        defaultFieldAgentSkillRuleSetFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    private void defaultFieldAgentSkillRuleSetFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFieldAgentSkillRuleSetShouldBeFound(shouldBeFound);
        defaultFieldAgentSkillRuleSetShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFieldAgentSkillRuleSetShouldBeFound(String filter) throws Exception {
        restFieldAgentSkillRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldAgentSkillRuleSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].sortType").value(hasItem(DEFAULT_SORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restFieldAgentSkillRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFieldAgentSkillRuleSetShouldNotBeFound(String filter) throws Exception {
        restFieldAgentSkillRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFieldAgentSkillRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(updatedFieldAgentSkillRuleSet);

        restFieldAgentSkillRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldAgentSkillRuleSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO))
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

        // Create the FieldAgentSkillRuleSet
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldAgentSkillRuleSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO))
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

        // Create the FieldAgentSkillRuleSet
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO))
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

        // Create the FieldAgentSkillRuleSet
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO)))
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

        // Create the FieldAgentSkillRuleSet
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldAgentSkillRuleSetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO))
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

        // Create the FieldAgentSkillRuleSet
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO))
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

        // Create the FieldAgentSkillRuleSet
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldAgentSkillRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fieldAgentSkillRuleSetDTO))
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
