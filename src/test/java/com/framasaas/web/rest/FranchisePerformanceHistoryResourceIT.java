package com.framasaas.web.rest;

import static com.framasaas.domain.FranchisePerformanceHistoryAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Franchise;
import com.framasaas.domain.FranchisePerformanceHistory;
import com.framasaas.domain.enumeration.PerformanceTag;
import com.framasaas.repository.FranchisePerformanceHistoryRepository;
import com.framasaas.service.dto.FranchisePerformanceHistoryDTO;
import com.framasaas.service.mapper.FranchisePerformanceHistoryMapper;
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
 * Integration tests for the {@link FranchisePerformanceHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchisePerformanceHistoryResourceIT {

    private static final Float DEFAULT_PERFORMANCE_SCORE = 1F;
    private static final Float UPDATED_PERFORMANCE_SCORE = 2F;
    private static final Float SMALLER_PERFORMANCE_SCORE = 1F - 1F;

    private static final PerformanceTag DEFAULT_PERFORMANCE_TAG = PerformanceTag.High;
    private static final PerformanceTag UPDATED_PERFORMANCE_TAG = PerformanceTag.Medium;

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchise-performance-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository;

    @Autowired
    private FranchisePerformanceHistoryMapper franchisePerformanceHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchisePerformanceHistoryMockMvc;

    private FranchisePerformanceHistory franchisePerformanceHistory;

    private FranchisePerformanceHistory insertedFranchisePerformanceHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchisePerformanceHistory createEntity() {
        return new FranchisePerformanceHistory()
            .performanceScore(DEFAULT_PERFORMANCE_SCORE)
            .performanceTag(DEFAULT_PERFORMANCE_TAG)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME)
            .createddBy(DEFAULT_CREATEDD_BY)
            .createdTime(DEFAULT_CREATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchisePerformanceHistory createUpdatedEntity() {
        return new FranchisePerformanceHistory()
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME);
    }

    @BeforeEach
    void initTest() {
        franchisePerformanceHistory = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFranchisePerformanceHistory != null) {
            franchisePerformanceHistoryRepository.delete(insertedFranchisePerformanceHistory);
            insertedFranchisePerformanceHistory = null;
        }
    }

    @Test
    @Transactional
    void createFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchisePerformanceHistory
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );
        var returnedFranchisePerformanceHistoryDTO = om.readValue(
            restFranchisePerformanceHistoryMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchisePerformanceHistoryDTO.class
        );

        // Validate the FranchisePerformanceHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFranchisePerformanceHistory = franchisePerformanceHistoryMapper.toEntity(returnedFranchisePerformanceHistoryDTO);
        assertFranchisePerformanceHistoryUpdatableFieldsEquals(
            returnedFranchisePerformanceHistory,
            getPersistedFranchisePerformanceHistory(returnedFranchisePerformanceHistory)
        );

        insertedFranchisePerformanceHistory = returnedFranchisePerformanceHistory;
    }

    @Test
    @Transactional
    void createFranchisePerformanceHistoryWithExistingId() throws Exception {
        // Create the FranchisePerformanceHistory with an existing ID
        franchisePerformanceHistory.setId(1L);
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchisePerformanceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchisePerformanceHistory.setUpdatedBy(null);

        // Create the FranchisePerformanceHistory, which fails.
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        restFranchisePerformanceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchisePerformanceHistory.setUpdatedTime(null);

        // Create the FranchisePerformanceHistory, which fails.
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        restFranchisePerformanceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchisePerformanceHistory.setCreateddBy(null);

        // Create the FranchisePerformanceHistory, which fails.
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        restFranchisePerformanceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchisePerformanceHistory.setCreatedTime(null);

        // Create the FranchisePerformanceHistory, which fails.
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        restFranchisePerformanceHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistories() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList
        restFranchisePerformanceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchisePerformanceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].performanceScore").value(hasItem(DEFAULT_PERFORMANCE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].performanceTag").value(hasItem(DEFAULT_PERFORMANCE_TAG.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchisePerformanceHistory() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get the franchisePerformanceHistory
        restFranchisePerformanceHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, franchisePerformanceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchisePerformanceHistory.getId().intValue()))
            .andExpect(jsonPath("$.performanceScore").value(DEFAULT_PERFORMANCE_SCORE.doubleValue()))
            .andExpect(jsonPath("$.performanceTag").value(DEFAULT_PERFORMANCE_TAG.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getFranchisePerformanceHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        Long id = franchisePerformanceHistory.getId();

        defaultFranchisePerformanceHistoryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFranchisePerformanceHistoryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFranchisePerformanceHistoryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByPerformanceScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where performanceScore equals to
        defaultFranchisePerformanceHistoryFiltering(
            "performanceScore.equals=" + DEFAULT_PERFORMANCE_SCORE,
            "performanceScore.equals=" + UPDATED_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByPerformanceScoreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where performanceScore in
        defaultFranchisePerformanceHistoryFiltering(
            "performanceScore.in=" + DEFAULT_PERFORMANCE_SCORE + "," + UPDATED_PERFORMANCE_SCORE,
            "performanceScore.in=" + UPDATED_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByPerformanceScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where performanceScore is not null
        defaultFranchisePerformanceHistoryFiltering("performanceScore.specified=true", "performanceScore.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByPerformanceScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where performanceScore is greater than or equal to
        defaultFranchisePerformanceHistoryFiltering(
            "performanceScore.greaterThanOrEqual=" + DEFAULT_PERFORMANCE_SCORE,
            "performanceScore.greaterThanOrEqual=" + UPDATED_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByPerformanceScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where performanceScore is less than or equal to
        defaultFranchisePerformanceHistoryFiltering(
            "performanceScore.lessThanOrEqual=" + DEFAULT_PERFORMANCE_SCORE,
            "performanceScore.lessThanOrEqual=" + SMALLER_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByPerformanceScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where performanceScore is less than
        defaultFranchisePerformanceHistoryFiltering(
            "performanceScore.lessThan=" + UPDATED_PERFORMANCE_SCORE,
            "performanceScore.lessThan=" + DEFAULT_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByPerformanceScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where performanceScore is greater than
        defaultFranchisePerformanceHistoryFiltering(
            "performanceScore.greaterThan=" + SMALLER_PERFORMANCE_SCORE,
            "performanceScore.greaterThan=" + DEFAULT_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByPerformanceTagIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where performanceTag equals to
        defaultFranchisePerformanceHistoryFiltering(
            "performanceTag.equals=" + DEFAULT_PERFORMANCE_TAG,
            "performanceTag.equals=" + UPDATED_PERFORMANCE_TAG
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByPerformanceTagIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where performanceTag in
        defaultFranchisePerformanceHistoryFiltering(
            "performanceTag.in=" + DEFAULT_PERFORMANCE_TAG + "," + UPDATED_PERFORMANCE_TAG,
            "performanceTag.in=" + UPDATED_PERFORMANCE_TAG
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByPerformanceTagIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where performanceTag is not null
        defaultFranchisePerformanceHistoryFiltering("performanceTag.specified=true", "performanceTag.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where updatedBy equals to
        defaultFranchisePerformanceHistoryFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where updatedBy in
        defaultFranchisePerformanceHistoryFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where updatedBy is not null
        defaultFranchisePerformanceHistoryFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where updatedBy contains
        defaultFranchisePerformanceHistoryFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where updatedBy does not contain
        defaultFranchisePerformanceHistoryFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where updatedTime equals to
        defaultFranchisePerformanceHistoryFiltering(
            "updatedTime.equals=" + DEFAULT_UPDATED_TIME,
            "updatedTime.equals=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where updatedTime in
        defaultFranchisePerformanceHistoryFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where updatedTime is not null
        defaultFranchisePerformanceHistoryFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where createddBy equals to
        defaultFranchisePerformanceHistoryFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where createddBy in
        defaultFranchisePerformanceHistoryFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where createddBy is not null
        defaultFranchisePerformanceHistoryFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where createddBy contains
        defaultFranchisePerformanceHistoryFiltering(
            "createddBy.contains=" + DEFAULT_CREATEDD_BY,
            "createddBy.contains=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where createddBy does not contain
        defaultFranchisePerformanceHistoryFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where createdTime equals to
        defaultFranchisePerformanceHistoryFiltering(
            "createdTime.equals=" + DEFAULT_CREATED_TIME,
            "createdTime.equals=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where createdTime in
        defaultFranchisePerformanceHistoryFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        // Get all the franchisePerformanceHistoryList where createdTime is not null
        defaultFranchisePerformanceHistoryFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisePerformanceHistoriesByFranchiseIsEqualToSomething() throws Exception {
        Franchise franchise;
        if (TestUtil.findAll(em, Franchise.class).isEmpty()) {
            franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);
            franchise = FranchiseResourceIT.createEntity();
        } else {
            franchise = TestUtil.findAll(em, Franchise.class).get(0);
        }
        em.persist(franchise);
        em.flush();
        franchisePerformanceHistory.setFranchise(franchise);
        franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);
        Long franchiseId = franchise.getId();
        // Get all the franchisePerformanceHistoryList where franchise equals to franchiseId
        defaultFranchisePerformanceHistoryShouldBeFound("franchiseId.equals=" + franchiseId);

        // Get all the franchisePerformanceHistoryList where franchise equals to (franchiseId + 1)
        defaultFranchisePerformanceHistoryShouldNotBeFound("franchiseId.equals=" + (franchiseId + 1));
    }

    private void defaultFranchisePerformanceHistoryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFranchisePerformanceHistoryShouldBeFound(shouldBeFound);
        defaultFranchisePerformanceHistoryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFranchisePerformanceHistoryShouldBeFound(String filter) throws Exception {
        restFranchisePerformanceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchisePerformanceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].performanceScore").value(hasItem(DEFAULT_PERFORMANCE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].performanceTag").value(hasItem(DEFAULT_PERFORMANCE_TAG.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())));

        // Check, that the count call also returns 1
        restFranchisePerformanceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFranchisePerformanceHistoryShouldNotBeFound(String filter) throws Exception {
        restFranchisePerformanceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFranchisePerformanceHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFranchisePerformanceHistory() throws Exception {
        // Get the franchisePerformanceHistory
        restFranchisePerformanceHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchisePerformanceHistory() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchisePerformanceHistory
        FranchisePerformanceHistory updatedFranchisePerformanceHistory = franchisePerformanceHistoryRepository
            .findById(franchisePerformanceHistory.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFranchisePerformanceHistory are not directly saved in db
        em.detach(updatedFranchisePerformanceHistory);
        updatedFranchisePerformanceHistory
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME);
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            updatedFranchisePerformanceHistory
        );

        restFranchisePerformanceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchisePerformanceHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchisePerformanceHistoryToMatchAllProperties(updatedFranchisePerformanceHistory);
    }

    @Test
    @Transactional
    void putNonExistingFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // Create the FranchisePerformanceHistory
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchisePerformanceHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // Create the FranchisePerformanceHistory
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // Create the FranchisePerformanceHistory
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchisePerformanceHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchisePerformanceHistory using partial update
        FranchisePerformanceHistory partialUpdatedFranchisePerformanceHistory = new FranchisePerformanceHistory();
        partialUpdatedFranchisePerformanceHistory.setId(franchisePerformanceHistory.getId());

        partialUpdatedFranchisePerformanceHistory
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .updatedTime(UPDATED_UPDATED_TIME)
            .createdTime(UPDATED_CREATED_TIME);

        restFranchisePerformanceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchisePerformanceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchisePerformanceHistory))
            )
            .andExpect(status().isOk());

        // Validate the FranchisePerformanceHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchisePerformanceHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchisePerformanceHistory, franchisePerformanceHistory),
            getPersistedFranchisePerformanceHistory(franchisePerformanceHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchisePerformanceHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchisePerformanceHistory using partial update
        FranchisePerformanceHistory partialUpdatedFranchisePerformanceHistory = new FranchisePerformanceHistory();
        partialUpdatedFranchisePerformanceHistory.setId(franchisePerformanceHistory.getId());

        partialUpdatedFranchisePerformanceHistory
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME);

        restFranchisePerformanceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchisePerformanceHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchisePerformanceHistory))
            )
            .andExpect(status().isOk());

        // Validate the FranchisePerformanceHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchisePerformanceHistoryUpdatableFieldsEquals(
            partialUpdatedFranchisePerformanceHistory,
            getPersistedFranchisePerformanceHistory(partialUpdatedFranchisePerformanceHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // Create the FranchisePerformanceHistory
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchisePerformanceHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // Create the FranchisePerformanceHistory
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchisePerformanceHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchisePerformanceHistory.setId(longCount.incrementAndGet());

        // Create the FranchisePerformanceHistory
        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = franchisePerformanceHistoryMapper.toDto(
            franchisePerformanceHistory
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchisePerformanceHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchisePerformanceHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchisePerformanceHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchisePerformanceHistory() throws Exception {
        // Initialize the database
        insertedFranchisePerformanceHistory = franchisePerformanceHistoryRepository.saveAndFlush(franchisePerformanceHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchisePerformanceHistory
        restFranchisePerformanceHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchisePerformanceHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchisePerformanceHistoryRepository.count();
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

    protected FranchisePerformanceHistory getPersistedFranchisePerformanceHistory(FranchisePerformanceHistory franchisePerformanceHistory) {
        return franchisePerformanceHistoryRepository.findById(franchisePerformanceHistory.getId()).orElseThrow();
    }

    protected void assertPersistedFranchisePerformanceHistoryToMatchAllProperties(
        FranchisePerformanceHistory expectedFranchisePerformanceHistory
    ) {
        assertFranchisePerformanceHistoryAllPropertiesEquals(
            expectedFranchisePerformanceHistory,
            getPersistedFranchisePerformanceHistory(expectedFranchisePerformanceHistory)
        );
    }

    protected void assertPersistedFranchisePerformanceHistoryToMatchUpdatableProperties(
        FranchisePerformanceHistory expectedFranchisePerformanceHistory
    ) {
        assertFranchisePerformanceHistoryAllUpdatablePropertiesEquals(
            expectedFranchisePerformanceHistory,
            getPersistedFranchisePerformanceHistory(expectedFranchisePerformanceHistory)
        );
    }
}
