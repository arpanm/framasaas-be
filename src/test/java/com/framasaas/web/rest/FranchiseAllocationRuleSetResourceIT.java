package com.framasaas.web.rest;

import static com.framasaas.domain.FranchiseAllocationRuleSetAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.FranchiseAllocationRuleSet;
import com.framasaas.domain.enumeration.FranchiseAllocationRuleSetSortType;
import com.framasaas.repository.FranchiseAllocationRuleSetRepository;
import com.framasaas.service.dto.FranchiseAllocationRuleSetDTO;
import com.framasaas.service.mapper.FranchiseAllocationRuleSetMapper;
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
 * Integration tests for the {@link FranchiseAllocationRuleSetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseAllocationRuleSetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final FranchiseAllocationRuleSetSortType DEFAULT_SORT_TYPE = FranchiseAllocationRuleSetSortType.NPS;
    private static final FranchiseAllocationRuleSetSortType UPDATED_SORT_TYPE = FranchiseAllocationRuleSetSortType.ROUNDROBIN;

    private static final Long DEFAULT_PRIORITY = 1L;
    private static final Long UPDATED_PRIORITY = 2L;
    private static final Long SMALLER_PRIORITY = 1L - 1L;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchise-allocation-rule-sets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository;

    @Autowired
    private FranchiseAllocationRuleSetMapper franchiseAllocationRuleSetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseAllocationRuleSetMockMvc;

    private FranchiseAllocationRuleSet franchiseAllocationRuleSet;

    private FranchiseAllocationRuleSet insertedFranchiseAllocationRuleSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseAllocationRuleSet createEntity() {
        return new FranchiseAllocationRuleSet()
            .name(DEFAULT_NAME)
            .sortType(DEFAULT_SORT_TYPE)
            .priority(DEFAULT_PRIORITY)
            .isActive(DEFAULT_IS_ACTIVE)
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
    public static FranchiseAllocationRuleSet createUpdatedEntity() {
        return new FranchiseAllocationRuleSet()
            .name(UPDATED_NAME)
            .sortType(UPDATED_SORT_TYPE)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        franchiseAllocationRuleSet = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFranchiseAllocationRuleSet != null) {
            franchiseAllocationRuleSetRepository.delete(insertedFranchiseAllocationRuleSet);
            insertedFranchiseAllocationRuleSet = null;
        }
    }

    @Test
    @Transactional
    void createFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchiseAllocationRuleSet
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);
        var returnedFranchiseAllocationRuleSetDTO = om.readValue(
            restFranchiseAllocationRuleSetMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseAllocationRuleSetDTO.class
        );

        // Validate the FranchiseAllocationRuleSet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFranchiseAllocationRuleSet = franchiseAllocationRuleSetMapper.toEntity(returnedFranchiseAllocationRuleSetDTO);
        assertFranchiseAllocationRuleSetUpdatableFieldsEquals(
            returnedFranchiseAllocationRuleSet,
            getPersistedFranchiseAllocationRuleSet(returnedFranchiseAllocationRuleSet)
        );

        insertedFranchiseAllocationRuleSet = returnedFranchiseAllocationRuleSet;
    }

    @Test
    @Transactional
    void createFranchiseAllocationRuleSetWithExistingId() throws Exception {
        // Create the FranchiseAllocationRuleSet with an existing ID
        franchiseAllocationRuleSet.setId(1L);
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setName(null);

        // Create the FranchiseAllocationRuleSet, which fails.
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSortTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setSortType(null);

        // Create the FranchiseAllocationRuleSet, which fails.
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setIsActive(null);

        // Create the FranchiseAllocationRuleSet, which fails.
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setCreateddBy(null);

        // Create the FranchiseAllocationRuleSet, which fails.
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setCreatedTime(null);

        // Create the FranchiseAllocationRuleSet, which fails.
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setUpdatedBy(null);

        // Create the FranchiseAllocationRuleSet, which fails.
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseAllocationRuleSet.setUpdatedTime(null);

        // Create the FranchiseAllocationRuleSet, which fails.
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSets() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList
        restFranchiseAllocationRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseAllocationRuleSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sortType").value(hasItem(DEFAULT_SORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchiseAllocationRuleSet() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get the franchiseAllocationRuleSet
        restFranchiseAllocationRuleSetMockMvc
            .perform(get(ENTITY_API_URL_ID, franchiseAllocationRuleSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchiseAllocationRuleSet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sortType").value(DEFAULT_SORT_TYPE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getFranchiseAllocationRuleSetsByIdFiltering() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        Long id = franchiseAllocationRuleSet.getId();

        defaultFranchiseAllocationRuleSetFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFranchiseAllocationRuleSetFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFranchiseAllocationRuleSetFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where name equals to
        defaultFranchiseAllocationRuleSetFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where name in
        defaultFranchiseAllocationRuleSetFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where name is not null
        defaultFranchiseAllocationRuleSetFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where name contains
        defaultFranchiseAllocationRuleSetFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where name does not contain
        defaultFranchiseAllocationRuleSetFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsBySortTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where sortType equals to
        defaultFranchiseAllocationRuleSetFiltering("sortType.equals=" + DEFAULT_SORT_TYPE, "sortType.equals=" + UPDATED_SORT_TYPE);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsBySortTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where sortType in
        defaultFranchiseAllocationRuleSetFiltering(
            "sortType.in=" + DEFAULT_SORT_TYPE + "," + UPDATED_SORT_TYPE,
            "sortType.in=" + UPDATED_SORT_TYPE
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsBySortTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where sortType is not null
        defaultFranchiseAllocationRuleSetFiltering("sortType.specified=true", "sortType.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where priority equals to
        defaultFranchiseAllocationRuleSetFiltering("priority.equals=" + DEFAULT_PRIORITY, "priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where priority in
        defaultFranchiseAllocationRuleSetFiltering(
            "priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY,
            "priority.in=" + UPDATED_PRIORITY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where priority is not null
        defaultFranchiseAllocationRuleSetFiltering("priority.specified=true", "priority.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where priority is greater than or equal to
        defaultFranchiseAllocationRuleSetFiltering(
            "priority.greaterThanOrEqual=" + DEFAULT_PRIORITY,
            "priority.greaterThanOrEqual=" + UPDATED_PRIORITY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where priority is less than or equal to
        defaultFranchiseAllocationRuleSetFiltering(
            "priority.lessThanOrEqual=" + DEFAULT_PRIORITY,
            "priority.lessThanOrEqual=" + SMALLER_PRIORITY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where priority is less than
        defaultFranchiseAllocationRuleSetFiltering("priority.lessThan=" + UPDATED_PRIORITY, "priority.lessThan=" + DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where priority is greater than
        defaultFranchiseAllocationRuleSetFiltering("priority.greaterThan=" + SMALLER_PRIORITY, "priority.greaterThan=" + DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where isActive equals to
        defaultFranchiseAllocationRuleSetFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where isActive in
        defaultFranchiseAllocationRuleSetFiltering(
            "isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE,
            "isActive.in=" + UPDATED_IS_ACTIVE
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where isActive is not null
        defaultFranchiseAllocationRuleSetFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where createddBy equals to
        defaultFranchiseAllocationRuleSetFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where createddBy in
        defaultFranchiseAllocationRuleSetFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where createddBy is not null
        defaultFranchiseAllocationRuleSetFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where createddBy contains
        defaultFranchiseAllocationRuleSetFiltering(
            "createddBy.contains=" + DEFAULT_CREATEDD_BY,
            "createddBy.contains=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where createddBy does not contain
        defaultFranchiseAllocationRuleSetFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where createdTime equals to
        defaultFranchiseAllocationRuleSetFiltering(
            "createdTime.equals=" + DEFAULT_CREATED_TIME,
            "createdTime.equals=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where createdTime in
        defaultFranchiseAllocationRuleSetFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where createdTime is not null
        defaultFranchiseAllocationRuleSetFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where updatedBy equals to
        defaultFranchiseAllocationRuleSetFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where updatedBy in
        defaultFranchiseAllocationRuleSetFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where updatedBy is not null
        defaultFranchiseAllocationRuleSetFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where updatedBy contains
        defaultFranchiseAllocationRuleSetFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where updatedBy does not contain
        defaultFranchiseAllocationRuleSetFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where updatedTime equals to
        defaultFranchiseAllocationRuleSetFiltering(
            "updatedTime.equals=" + DEFAULT_UPDATED_TIME,
            "updatedTime.equals=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where updatedTime in
        defaultFranchiseAllocationRuleSetFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchiseAllocationRuleSetsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        // Get all the franchiseAllocationRuleSetList where updatedTime is not null
        defaultFranchiseAllocationRuleSetFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    private void defaultFranchiseAllocationRuleSetFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFranchiseAllocationRuleSetShouldBeFound(shouldBeFound);
        defaultFranchiseAllocationRuleSetShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFranchiseAllocationRuleSetShouldBeFound(String filter) throws Exception {
        restFranchiseAllocationRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseAllocationRuleSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sortType").value(hasItem(DEFAULT_SORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restFranchiseAllocationRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFranchiseAllocationRuleSetShouldNotBeFound(String filter) throws Exception {
        restFranchiseAllocationRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFranchiseAllocationRuleSetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFranchiseAllocationRuleSet() throws Exception {
        // Get the franchiseAllocationRuleSet
        restFranchiseAllocationRuleSetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchiseAllocationRuleSet() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseAllocationRuleSet
        FranchiseAllocationRuleSet updatedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository
            .findById(franchiseAllocationRuleSet.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFranchiseAllocationRuleSet are not directly saved in db
        em.detach(updatedFranchiseAllocationRuleSet);
        updatedFranchiseAllocationRuleSet
            .name(UPDATED_NAME)
            .sortType(UPDATED_SORT_TYPE)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(
            updatedFranchiseAllocationRuleSet
        );

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseAllocationRuleSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseAllocationRuleSetToMatchAllProperties(updatedFranchiseAllocationRuleSet);
    }

    @Test
    @Transactional
    void putNonExistingFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // Create the FranchiseAllocationRuleSet
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseAllocationRuleSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // Create the FranchiseAllocationRuleSet
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // Create the FranchiseAllocationRuleSet
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseAllocationRuleSetWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseAllocationRuleSet using partial update
        FranchiseAllocationRuleSet partialUpdatedFranchiseAllocationRuleSet = new FranchiseAllocationRuleSet();
        partialUpdatedFranchiseAllocationRuleSet.setId(franchiseAllocationRuleSet.getId());

        partialUpdatedFranchiseAllocationRuleSet
            .name(UPDATED_NAME)
            .sortType(UPDATED_SORT_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseAllocationRuleSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseAllocationRuleSet))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseAllocationRuleSet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseAllocationRuleSetUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchiseAllocationRuleSet, franchiseAllocationRuleSet),
            getPersistedFranchiseAllocationRuleSet(franchiseAllocationRuleSet)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseAllocationRuleSetWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseAllocationRuleSet using partial update
        FranchiseAllocationRuleSet partialUpdatedFranchiseAllocationRuleSet = new FranchiseAllocationRuleSet();
        partialUpdatedFranchiseAllocationRuleSet.setId(franchiseAllocationRuleSet.getId());

        partialUpdatedFranchiseAllocationRuleSet
            .name(UPDATED_NAME)
            .sortType(UPDATED_SORT_TYPE)
            .priority(UPDATED_PRIORITY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseAllocationRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseAllocationRuleSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseAllocationRuleSet))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseAllocationRuleSet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseAllocationRuleSetUpdatableFieldsEquals(
            partialUpdatedFranchiseAllocationRuleSet,
            getPersistedFranchiseAllocationRuleSet(partialUpdatedFranchiseAllocationRuleSet)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // Create the FranchiseAllocationRuleSet
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseAllocationRuleSetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // Create the FranchiseAllocationRuleSet
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchiseAllocationRuleSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseAllocationRuleSet.setId(longCount.incrementAndGet());

        // Create the FranchiseAllocationRuleSet
        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseAllocationRuleSetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseAllocationRuleSetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseAllocationRuleSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchiseAllocationRuleSet() throws Exception {
        // Initialize the database
        insertedFranchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.saveAndFlush(franchiseAllocationRuleSet);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchiseAllocationRuleSet
        restFranchiseAllocationRuleSetMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchiseAllocationRuleSet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseAllocationRuleSetRepository.count();
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

    protected FranchiseAllocationRuleSet getPersistedFranchiseAllocationRuleSet(FranchiseAllocationRuleSet franchiseAllocationRuleSet) {
        return franchiseAllocationRuleSetRepository.findById(franchiseAllocationRuleSet.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseAllocationRuleSetToMatchAllProperties(
        FranchiseAllocationRuleSet expectedFranchiseAllocationRuleSet
    ) {
        assertFranchiseAllocationRuleSetAllPropertiesEquals(
            expectedFranchiseAllocationRuleSet,
            getPersistedFranchiseAllocationRuleSet(expectedFranchiseAllocationRuleSet)
        );
    }

    protected void assertPersistedFranchiseAllocationRuleSetToMatchUpdatableProperties(
        FranchiseAllocationRuleSet expectedFranchiseAllocationRuleSet
    ) {
        assertFranchiseAllocationRuleSetAllUpdatablePropertiesEquals(
            expectedFranchiseAllocationRuleSet,
            getPersistedFranchiseAllocationRuleSet(expectedFranchiseAllocationRuleSet)
        );
    }
}
