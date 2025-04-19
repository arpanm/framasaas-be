package com.framasaas.web.rest;

import static com.framasaas.domain.FranchiseAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Address;
import com.framasaas.domain.Franchise;
import com.framasaas.domain.FranchiseAllocationRuleSet;
import com.framasaas.domain.enumeration.FranchiseStatus;
import com.framasaas.domain.enumeration.PerformanceTag;
import com.framasaas.repository.FranchiseRepository;
import com.framasaas.service.dto.FranchiseDTO;
import com.framasaas.service.mapper.FranchiseMapper;
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
 * Integration tests for the {@link FranchiseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseResourceIT {

    private static final String DEFAULT_FRANCHISE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FRANCHISE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "Kj=jk@`Y4j(.\\k";
    private static final String UPDATED_EMAIL = "K@kg[g.IJVi`";

    private static final Long DEFAULT_CONTACT = 1000000000L;
    private static final Long UPDATED_CONTACT = 1000000001L;
    private static final Long SMALLER_CONTACT = 1000000000L - 1L;

    private static final FranchiseStatus DEFAULT_FRANCHISE_STATUS = FranchiseStatus.PendingApproval;
    private static final FranchiseStatus UPDATED_FRANCHISE_STATUS = FranchiseStatus.Active;

    private static final String DEFAULT_GST_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GST_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_PERFORMANCE_SCORE = 1F;
    private static final Float UPDATED_PERFORMANCE_SCORE = 2F;
    private static final Float SMALLER_PERFORMANCE_SCORE = 1F - 1F;

    private static final PerformanceTag DEFAULT_PERFORMANCE_TAG = PerformanceTag.High;
    private static final PerformanceTag UPDATED_PERFORMANCE_TAG = PerformanceTag.Medium;

    private static final Long DEFAULT_DAILY_MAX_SERVICE_LIMIT = 1L;
    private static final Long UPDATED_DAILY_MAX_SERVICE_LIMIT = 2L;
    private static final Long SMALLER_DAILY_MAX_SERVICE_LIMIT = 1L - 1L;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private FranchiseMapper franchiseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseMockMvc;

    private Franchise franchise;

    private Franchise insertedFranchise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Franchise createEntity() {
        return new Franchise()
            .franchiseName(DEFAULT_FRANCHISE_NAME)
            .owner(DEFAULT_OWNER)
            .email(DEFAULT_EMAIL)
            .contact(DEFAULT_CONTACT)
            .franchiseStatus(DEFAULT_FRANCHISE_STATUS)
            .gstNumber(DEFAULT_GST_NUMBER)
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .performanceScore(DEFAULT_PERFORMANCE_SCORE)
            .performanceTag(DEFAULT_PERFORMANCE_TAG)
            .dailyMaxServiceLimit(DEFAULT_DAILY_MAX_SERVICE_LIMIT)
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
    public static Franchise createUpdatedEntity() {
        return new Franchise()
            .franchiseName(UPDATED_FRANCHISE_NAME)
            .owner(UPDATED_OWNER)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .franchiseStatus(UPDATED_FRANCHISE_STATUS)
            .gstNumber(UPDATED_GST_NUMBER)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .dailyMaxServiceLimit(UPDATED_DAILY_MAX_SERVICE_LIMIT)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        franchise = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFranchise != null) {
            franchiseRepository.delete(insertedFranchise);
            insertedFranchise = null;
        }
    }

    @Test
    @Transactional
    void createFranchise() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Franchise
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);
        var returnedFranchiseDTO = om.readValue(
            restFranchiseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseDTO.class
        );

        // Validate the Franchise in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFranchise = franchiseMapper.toEntity(returnedFranchiseDTO);
        assertFranchiseUpdatableFieldsEquals(returnedFranchise, getPersistedFranchise(returnedFranchise));

        insertedFranchise = returnedFranchise;
    }

    @Test
    @Transactional
    void createFranchiseWithExistingId() throws Exception {
        // Create the Franchise with an existing ID
        franchise.setId(1L);
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFranchiseNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setFranchiseName(null);

        // Create the Franchise, which fails.
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setEmail(null);

        // Create the Franchise, which fails.
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setContact(null);

        // Create the Franchise, which fails.
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setCreateddBy(null);

        // Create the Franchise, which fails.
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setCreatedTime(null);

        // Create the Franchise, which fails.
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setUpdatedBy(null);

        // Create the Franchise, which fails.
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchise.setUpdatedTime(null);

        // Create the Franchise, which fails.
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        restFranchiseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchises() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList
        restFranchiseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchise.getId().intValue())))
            .andExpect(jsonPath("$.[*].franchiseName").value(hasItem(DEFAULT_FRANCHISE_NAME)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.intValue())))
            .andExpect(jsonPath("$.[*].franchiseStatus").value(hasItem(DEFAULT_FRANCHISE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gstNumber").value(hasItem(DEFAULT_GST_NUMBER)))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].performanceScore").value(hasItem(DEFAULT_PERFORMANCE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].performanceTag").value(hasItem(DEFAULT_PERFORMANCE_TAG.toString())))
            .andExpect(jsonPath("$.[*].dailyMaxServiceLimit").value(hasItem(DEFAULT_DAILY_MAX_SERVICE_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchise() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get the franchise
        restFranchiseMockMvc
            .perform(get(ENTITY_API_URL_ID, franchise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchise.getId().intValue()))
            .andExpect(jsonPath("$.franchiseName").value(DEFAULT_FRANCHISE_NAME))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.intValue()))
            .andExpect(jsonPath("$.franchiseStatus").value(DEFAULT_FRANCHISE_STATUS.toString()))
            .andExpect(jsonPath("$.gstNumber").value(DEFAULT_GST_NUMBER))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.performanceScore").value(DEFAULT_PERFORMANCE_SCORE.doubleValue()))
            .andExpect(jsonPath("$.performanceTag").value(DEFAULT_PERFORMANCE_TAG.toString()))
            .andExpect(jsonPath("$.dailyMaxServiceLimit").value(DEFAULT_DAILY_MAX_SERVICE_LIMIT.intValue()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getFranchisesByIdFiltering() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        Long id = franchise.getId();

        defaultFranchiseFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFranchiseFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFranchiseFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFranchisesByFranchiseNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where franchiseName equals to
        defaultFranchiseFiltering("franchiseName.equals=" + DEFAULT_FRANCHISE_NAME, "franchiseName.equals=" + UPDATED_FRANCHISE_NAME);
    }

    @Test
    @Transactional
    void getAllFranchisesByFranchiseNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where franchiseName in
        defaultFranchiseFiltering(
            "franchiseName.in=" + DEFAULT_FRANCHISE_NAME + "," + UPDATED_FRANCHISE_NAME,
            "franchiseName.in=" + UPDATED_FRANCHISE_NAME
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByFranchiseNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where franchiseName is not null
        defaultFranchiseFiltering("franchiseName.specified=true", "franchiseName.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByFranchiseNameContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where franchiseName contains
        defaultFranchiseFiltering("franchiseName.contains=" + DEFAULT_FRANCHISE_NAME, "franchiseName.contains=" + UPDATED_FRANCHISE_NAME);
    }

    @Test
    @Transactional
    void getAllFranchisesByFranchiseNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where franchiseName does not contain
        defaultFranchiseFiltering(
            "franchiseName.doesNotContain=" + UPDATED_FRANCHISE_NAME,
            "franchiseName.doesNotContain=" + DEFAULT_FRANCHISE_NAME
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where owner equals to
        defaultFranchiseFiltering("owner.equals=" + DEFAULT_OWNER, "owner.equals=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    void getAllFranchisesByOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where owner in
        defaultFranchiseFiltering("owner.in=" + DEFAULT_OWNER + "," + UPDATED_OWNER, "owner.in=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    void getAllFranchisesByOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where owner is not null
        defaultFranchiseFiltering("owner.specified=true", "owner.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByOwnerContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where owner contains
        defaultFranchiseFiltering("owner.contains=" + DEFAULT_OWNER, "owner.contains=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    void getAllFranchisesByOwnerNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where owner does not contain
        defaultFranchiseFiltering("owner.doesNotContain=" + UPDATED_OWNER, "owner.doesNotContain=" + DEFAULT_OWNER);
    }

    @Test
    @Transactional
    void getAllFranchisesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where email equals to
        defaultFranchiseFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllFranchisesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where email in
        defaultFranchiseFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllFranchisesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where email is not null
        defaultFranchiseFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where email contains
        defaultFranchiseFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllFranchisesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where email does not contain
        defaultFranchiseFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllFranchisesByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where contact equals to
        defaultFranchiseFiltering("contact.equals=" + DEFAULT_CONTACT, "contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllFranchisesByContactIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where contact in
        defaultFranchiseFiltering("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT, "contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllFranchisesByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where contact is not null
        defaultFranchiseFiltering("contact.specified=true", "contact.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByContactIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where contact is greater than or equal to
        defaultFranchiseFiltering("contact.greaterThanOrEqual=" + DEFAULT_CONTACT, "contact.greaterThanOrEqual=" + (DEFAULT_CONTACT + 1));
    }

    @Test
    @Transactional
    void getAllFranchisesByContactIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where contact is less than or equal to
        defaultFranchiseFiltering("contact.lessThanOrEqual=" + DEFAULT_CONTACT, "contact.lessThanOrEqual=" + SMALLER_CONTACT);
    }

    @Test
    @Transactional
    void getAllFranchisesByContactIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where contact is less than
        defaultFranchiseFiltering("contact.lessThan=" + (DEFAULT_CONTACT + 1), "contact.lessThan=" + DEFAULT_CONTACT);
    }

    @Test
    @Transactional
    void getAllFranchisesByContactIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where contact is greater than
        defaultFranchiseFiltering("contact.greaterThan=" + SMALLER_CONTACT, "contact.greaterThan=" + DEFAULT_CONTACT);
    }

    @Test
    @Transactional
    void getAllFranchisesByFranchiseStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where franchiseStatus equals to
        defaultFranchiseFiltering(
            "franchiseStatus.equals=" + DEFAULT_FRANCHISE_STATUS,
            "franchiseStatus.equals=" + UPDATED_FRANCHISE_STATUS
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByFranchiseStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where franchiseStatus in
        defaultFranchiseFiltering(
            "franchiseStatus.in=" + DEFAULT_FRANCHISE_STATUS + "," + UPDATED_FRANCHISE_STATUS,
            "franchiseStatus.in=" + UPDATED_FRANCHISE_STATUS
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByFranchiseStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where franchiseStatus is not null
        defaultFranchiseFiltering("franchiseStatus.specified=true", "franchiseStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByGstNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where gstNumber equals to
        defaultFranchiseFiltering("gstNumber.equals=" + DEFAULT_GST_NUMBER, "gstNumber.equals=" + UPDATED_GST_NUMBER);
    }

    @Test
    @Transactional
    void getAllFranchisesByGstNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where gstNumber in
        defaultFranchiseFiltering("gstNumber.in=" + DEFAULT_GST_NUMBER + "," + UPDATED_GST_NUMBER, "gstNumber.in=" + UPDATED_GST_NUMBER);
    }

    @Test
    @Transactional
    void getAllFranchisesByGstNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where gstNumber is not null
        defaultFranchiseFiltering("gstNumber.specified=true", "gstNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByGstNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where gstNumber contains
        defaultFranchiseFiltering("gstNumber.contains=" + DEFAULT_GST_NUMBER, "gstNumber.contains=" + UPDATED_GST_NUMBER);
    }

    @Test
    @Transactional
    void getAllFranchisesByGstNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where gstNumber does not contain
        defaultFranchiseFiltering("gstNumber.doesNotContain=" + UPDATED_GST_NUMBER, "gstNumber.doesNotContain=" + DEFAULT_GST_NUMBER);
    }

    @Test
    @Transactional
    void getAllFranchisesByRegistrationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where registrationNumber equals to
        defaultFranchiseFiltering(
            "registrationNumber.equals=" + DEFAULT_REGISTRATION_NUMBER,
            "registrationNumber.equals=" + UPDATED_REGISTRATION_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByRegistrationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where registrationNumber in
        defaultFranchiseFiltering(
            "registrationNumber.in=" + DEFAULT_REGISTRATION_NUMBER + "," + UPDATED_REGISTRATION_NUMBER,
            "registrationNumber.in=" + UPDATED_REGISTRATION_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByRegistrationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where registrationNumber is not null
        defaultFranchiseFiltering("registrationNumber.specified=true", "registrationNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByRegistrationNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where registrationNumber contains
        defaultFranchiseFiltering(
            "registrationNumber.contains=" + DEFAULT_REGISTRATION_NUMBER,
            "registrationNumber.contains=" + UPDATED_REGISTRATION_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByRegistrationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where registrationNumber does not contain
        defaultFranchiseFiltering(
            "registrationNumber.doesNotContain=" + UPDATED_REGISTRATION_NUMBER,
            "registrationNumber.doesNotContain=" + DEFAULT_REGISTRATION_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByPerformanceScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where performanceScore equals to
        defaultFranchiseFiltering(
            "performanceScore.equals=" + DEFAULT_PERFORMANCE_SCORE,
            "performanceScore.equals=" + UPDATED_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByPerformanceScoreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where performanceScore in
        defaultFranchiseFiltering(
            "performanceScore.in=" + DEFAULT_PERFORMANCE_SCORE + "," + UPDATED_PERFORMANCE_SCORE,
            "performanceScore.in=" + UPDATED_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByPerformanceScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where performanceScore is not null
        defaultFranchiseFiltering("performanceScore.specified=true", "performanceScore.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByPerformanceScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where performanceScore is greater than or equal to
        defaultFranchiseFiltering(
            "performanceScore.greaterThanOrEqual=" + DEFAULT_PERFORMANCE_SCORE,
            "performanceScore.greaterThanOrEqual=" + UPDATED_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByPerformanceScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where performanceScore is less than or equal to
        defaultFranchiseFiltering(
            "performanceScore.lessThanOrEqual=" + DEFAULT_PERFORMANCE_SCORE,
            "performanceScore.lessThanOrEqual=" + SMALLER_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByPerformanceScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where performanceScore is less than
        defaultFranchiseFiltering(
            "performanceScore.lessThan=" + UPDATED_PERFORMANCE_SCORE,
            "performanceScore.lessThan=" + DEFAULT_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByPerformanceScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where performanceScore is greater than
        defaultFranchiseFiltering(
            "performanceScore.greaterThan=" + SMALLER_PERFORMANCE_SCORE,
            "performanceScore.greaterThan=" + DEFAULT_PERFORMANCE_SCORE
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByPerformanceTagIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where performanceTag equals to
        defaultFranchiseFiltering("performanceTag.equals=" + DEFAULT_PERFORMANCE_TAG, "performanceTag.equals=" + UPDATED_PERFORMANCE_TAG);
    }

    @Test
    @Transactional
    void getAllFranchisesByPerformanceTagIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where performanceTag in
        defaultFranchiseFiltering(
            "performanceTag.in=" + DEFAULT_PERFORMANCE_TAG + "," + UPDATED_PERFORMANCE_TAG,
            "performanceTag.in=" + UPDATED_PERFORMANCE_TAG
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByPerformanceTagIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where performanceTag is not null
        defaultFranchiseFiltering("performanceTag.specified=true", "performanceTag.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByDailyMaxServiceLimitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where dailyMaxServiceLimit equals to
        defaultFranchiseFiltering(
            "dailyMaxServiceLimit.equals=" + DEFAULT_DAILY_MAX_SERVICE_LIMIT,
            "dailyMaxServiceLimit.equals=" + UPDATED_DAILY_MAX_SERVICE_LIMIT
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByDailyMaxServiceLimitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where dailyMaxServiceLimit in
        defaultFranchiseFiltering(
            "dailyMaxServiceLimit.in=" + DEFAULT_DAILY_MAX_SERVICE_LIMIT + "," + UPDATED_DAILY_MAX_SERVICE_LIMIT,
            "dailyMaxServiceLimit.in=" + UPDATED_DAILY_MAX_SERVICE_LIMIT
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByDailyMaxServiceLimitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where dailyMaxServiceLimit is not null
        defaultFranchiseFiltering("dailyMaxServiceLimit.specified=true", "dailyMaxServiceLimit.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByDailyMaxServiceLimitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where dailyMaxServiceLimit is greater than or equal to
        defaultFranchiseFiltering(
            "dailyMaxServiceLimit.greaterThanOrEqual=" + DEFAULT_DAILY_MAX_SERVICE_LIMIT,
            "dailyMaxServiceLimit.greaterThanOrEqual=" + UPDATED_DAILY_MAX_SERVICE_LIMIT
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByDailyMaxServiceLimitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where dailyMaxServiceLimit is less than or equal to
        defaultFranchiseFiltering(
            "dailyMaxServiceLimit.lessThanOrEqual=" + DEFAULT_DAILY_MAX_SERVICE_LIMIT,
            "dailyMaxServiceLimit.lessThanOrEqual=" + SMALLER_DAILY_MAX_SERVICE_LIMIT
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByDailyMaxServiceLimitIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where dailyMaxServiceLimit is less than
        defaultFranchiseFiltering(
            "dailyMaxServiceLimit.lessThan=" + UPDATED_DAILY_MAX_SERVICE_LIMIT,
            "dailyMaxServiceLimit.lessThan=" + DEFAULT_DAILY_MAX_SERVICE_LIMIT
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByDailyMaxServiceLimitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where dailyMaxServiceLimit is greater than
        defaultFranchiseFiltering(
            "dailyMaxServiceLimit.greaterThan=" + SMALLER_DAILY_MAX_SERVICE_LIMIT,
            "dailyMaxServiceLimit.greaterThan=" + DEFAULT_DAILY_MAX_SERVICE_LIMIT
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where createddBy equals to
        defaultFranchiseFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFranchisesByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where createddBy in
        defaultFranchiseFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where createddBy is not null
        defaultFranchiseFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where createddBy contains
        defaultFranchiseFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFranchisesByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where createddBy does not contain
        defaultFranchiseFiltering("createddBy.doesNotContain=" + UPDATED_CREATEDD_BY, "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFranchisesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where createdTime equals to
        defaultFranchiseFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllFranchisesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where createdTime in
        defaultFranchiseFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where createdTime is not null
        defaultFranchiseFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where updatedBy equals to
        defaultFranchiseFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchisesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where updatedBy in
        defaultFranchiseFiltering("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY, "updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchisesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where updatedBy is not null
        defaultFranchiseFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where updatedBy contains
        defaultFranchiseFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchisesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where updatedBy does not contain
        defaultFranchiseFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchisesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where updatedTime equals to
        defaultFranchiseFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllFranchisesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where updatedTime in
        defaultFranchiseFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchisesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        // Get all the franchiseList where updatedTime is not null
        defaultFranchiseFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchisesByAddressIsEqualToSomething() throws Exception {
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            franchiseRepository.saveAndFlush(franchise);
            address = AddressResourceIT.createEntity();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        franchise.setAddress(address);
        franchiseRepository.saveAndFlush(franchise);
        Long addressId = address.getId();
        // Get all the franchiseList where address equals to addressId
        defaultFranchiseShouldBeFound("addressId.equals=" + addressId);

        // Get all the franchiseList where address equals to (addressId + 1)
        defaultFranchiseShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    @Test
    @Transactional
    void getAllFranchisesByRulesetIsEqualToSomething() throws Exception {
        FranchiseAllocationRuleSet ruleset;
        if (TestUtil.findAll(em, FranchiseAllocationRuleSet.class).isEmpty()) {
            franchiseRepository.saveAndFlush(franchise);
            ruleset = FranchiseAllocationRuleSetResourceIT.createEntity();
        } else {
            ruleset = TestUtil.findAll(em, FranchiseAllocationRuleSet.class).get(0);
        }
        em.persist(ruleset);
        em.flush();
        franchise.setRuleset(ruleset);
        franchiseRepository.saveAndFlush(franchise);
        Long rulesetId = ruleset.getId();
        // Get all the franchiseList where ruleset equals to rulesetId
        defaultFranchiseShouldBeFound("rulesetId.equals=" + rulesetId);

        // Get all the franchiseList where ruleset equals to (rulesetId + 1)
        defaultFranchiseShouldNotBeFound("rulesetId.equals=" + (rulesetId + 1));
    }

    private void defaultFranchiseFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFranchiseShouldBeFound(shouldBeFound);
        defaultFranchiseShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFranchiseShouldBeFound(String filter) throws Exception {
        restFranchiseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchise.getId().intValue())))
            .andExpect(jsonPath("$.[*].franchiseName").value(hasItem(DEFAULT_FRANCHISE_NAME)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.intValue())))
            .andExpect(jsonPath("$.[*].franchiseStatus").value(hasItem(DEFAULT_FRANCHISE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gstNumber").value(hasItem(DEFAULT_GST_NUMBER)))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].performanceScore").value(hasItem(DEFAULT_PERFORMANCE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].performanceTag").value(hasItem(DEFAULT_PERFORMANCE_TAG.toString())))
            .andExpect(jsonPath("$.[*].dailyMaxServiceLimit").value(hasItem(DEFAULT_DAILY_MAX_SERVICE_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restFranchiseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFranchiseShouldNotBeFound(String filter) throws Exception {
        restFranchiseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFranchiseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFranchise() throws Exception {
        // Get the franchise
        restFranchiseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchise() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchise
        Franchise updatedFranchise = franchiseRepository.findById(franchise.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFranchise are not directly saved in db
        em.detach(updatedFranchise);
        updatedFranchise
            .franchiseName(UPDATED_FRANCHISE_NAME)
            .owner(UPDATED_OWNER)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .franchiseStatus(UPDATED_FRANCHISE_STATUS)
            .gstNumber(UPDATED_GST_NUMBER)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .dailyMaxServiceLimit(UPDATED_DAILY_MAX_SERVICE_LIMIT)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(updatedFranchise);

        restFranchiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseToMatchAllProperties(updatedFranchise);
    }

    @Test
    @Transactional
    void putNonExistingFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // Create the Franchise
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // Create the Franchise
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // Create the Franchise
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseWithPatch() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchise using partial update
        Franchise partialUpdatedFranchise = new Franchise();
        partialUpdatedFranchise.setId(franchise.getId());

        partialUpdatedFranchise
            .franchiseName(UPDATED_FRANCHISE_NAME)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .dailyMaxServiceLimit(UPDATED_DAILY_MAX_SERVICE_LIMIT)
            .updatedBy(UPDATED_UPDATED_BY);

        restFranchiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchise.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchise))
            )
            .andExpect(status().isOk());

        // Validate the Franchise in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchise, franchise),
            getPersistedFranchise(franchise)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseWithPatch() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchise using partial update
        Franchise partialUpdatedFranchise = new Franchise();
        partialUpdatedFranchise.setId(franchise.getId());

        partialUpdatedFranchise
            .franchiseName(UPDATED_FRANCHISE_NAME)
            .owner(UPDATED_OWNER)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .franchiseStatus(UPDATED_FRANCHISE_STATUS)
            .gstNumber(UPDATED_GST_NUMBER)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .performanceScore(UPDATED_PERFORMANCE_SCORE)
            .performanceTag(UPDATED_PERFORMANCE_TAG)
            .dailyMaxServiceLimit(UPDATED_DAILY_MAX_SERVICE_LIMIT)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchise.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchise))
            )
            .andExpect(status().isOk());

        // Validate the Franchise in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseUpdatableFieldsEquals(partialUpdatedFranchise, getPersistedFranchise(partialUpdatedFranchise));
    }

    @Test
    @Transactional
    void patchNonExistingFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // Create the Franchise
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // Create the Franchise
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchise() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchise.setId(longCount.incrementAndGet());

        // Create the Franchise
        FranchiseDTO franchiseDTO = franchiseMapper.toDto(franchise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Franchise in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchise() throws Exception {
        // Initialize the database
        insertedFranchise = franchiseRepository.saveAndFlush(franchise);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchise
        restFranchiseMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseRepository.count();
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

    protected Franchise getPersistedFranchise(Franchise franchise) {
        return franchiseRepository.findById(franchise.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseToMatchAllProperties(Franchise expectedFranchise) {
        assertFranchiseAllPropertiesEquals(expectedFranchise, getPersistedFranchise(expectedFranchise));
    }

    protected void assertPersistedFranchiseToMatchUpdatableProperties(Franchise expectedFranchise) {
        assertFranchiseAllUpdatablePropertiesEquals(expectedFranchise, getPersistedFranchise(expectedFranchise));
    }
}
