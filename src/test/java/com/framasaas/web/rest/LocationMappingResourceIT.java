package com.framasaas.web.rest;

import static com.framasaas.domain.LocationMappingAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.domain.LocationMapping;
import com.framasaas.repository.LocationMappingRepository;
import com.framasaas.service.dto.LocationMappingDTO;
import com.framasaas.service.mapper.LocationMappingMapper;
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
 * Integration tests for the {@link LocationMappingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LocationMappingResourceIT {

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/location-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocationMappingRepository locationMappingRepository;

    @Autowired
    private LocationMappingMapper locationMappingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationMappingMockMvc;

    private LocationMapping locationMapping;

    private LocationMapping insertedLocationMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocationMapping createEntity() {
        return new LocationMapping()
            .locationName(DEFAULT_LOCATION_NAME)
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
    public static LocationMapping createUpdatedEntity() {
        return new LocationMapping()
            .locationName(UPDATED_LOCATION_NAME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        locationMapping = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedLocationMapping != null) {
            locationMappingRepository.delete(insertedLocationMapping);
            insertedLocationMapping = null;
        }
    }

    @Test
    @Transactional
    void createLocationMapping() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LocationMapping
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);
        var returnedLocationMappingDTO = om.readValue(
            restLocationMappingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMappingDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LocationMappingDTO.class
        );

        // Validate the LocationMapping in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLocationMapping = locationMappingMapper.toEntity(returnedLocationMappingDTO);
        assertLocationMappingUpdatableFieldsEquals(returnedLocationMapping, getPersistedLocationMapping(returnedLocationMapping));

        insertedLocationMapping = returnedLocationMapping;
    }

    @Test
    @Transactional
    void createLocationMappingWithExistingId() throws Exception {
        // Create the LocationMapping with an existing ID
        locationMapping.setId(1L);
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocationMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLocationNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        locationMapping.setLocationName(null);

        // Create the LocationMapping, which fails.
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMappingDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        locationMapping.setCreateddBy(null);

        // Create the LocationMapping, which fails.
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMappingDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        locationMapping.setCreatedTime(null);

        // Create the LocationMapping, which fails.
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMappingDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        locationMapping.setUpdatedBy(null);

        // Create the LocationMapping, which fails.
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMappingDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        locationMapping.setUpdatedTime(null);

        // Create the LocationMapping, which fails.
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMappingDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLocationMappings() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList
        restLocationMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getLocationMapping() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get the locationMapping
        restLocationMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, locationMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(locationMapping.getId().intValue()))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getLocationMappingsByIdFiltering() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        Long id = locationMapping.getId();

        defaultLocationMappingFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultLocationMappingFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultLocationMappingFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLocationMappingsByLocationNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where locationName equals to
        defaultLocationMappingFiltering("locationName.equals=" + DEFAULT_LOCATION_NAME, "locationName.equals=" + UPDATED_LOCATION_NAME);
    }

    @Test
    @Transactional
    void getAllLocationMappingsByLocationNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where locationName in
        defaultLocationMappingFiltering(
            "locationName.in=" + DEFAULT_LOCATION_NAME + "," + UPDATED_LOCATION_NAME,
            "locationName.in=" + UPDATED_LOCATION_NAME
        );
    }

    @Test
    @Transactional
    void getAllLocationMappingsByLocationNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where locationName is not null
        defaultLocationMappingFiltering("locationName.specified=true", "locationName.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationMappingsByLocationNameContainsSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where locationName contains
        defaultLocationMappingFiltering("locationName.contains=" + DEFAULT_LOCATION_NAME, "locationName.contains=" + UPDATED_LOCATION_NAME);
    }

    @Test
    @Transactional
    void getAllLocationMappingsByLocationNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where locationName does not contain
        defaultLocationMappingFiltering(
            "locationName.doesNotContain=" + UPDATED_LOCATION_NAME,
            "locationName.doesNotContain=" + DEFAULT_LOCATION_NAME
        );
    }

    @Test
    @Transactional
    void getAllLocationMappingsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where createddBy equals to
        defaultLocationMappingFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllLocationMappingsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where createddBy in
        defaultLocationMappingFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllLocationMappingsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where createddBy is not null
        defaultLocationMappingFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationMappingsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where createddBy contains
        defaultLocationMappingFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllLocationMappingsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where createddBy does not contain
        defaultLocationMappingFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllLocationMappingsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where createdTime equals to
        defaultLocationMappingFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllLocationMappingsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where createdTime in
        defaultLocationMappingFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllLocationMappingsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where createdTime is not null
        defaultLocationMappingFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationMappingsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where updatedBy equals to
        defaultLocationMappingFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationMappingsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where updatedBy in
        defaultLocationMappingFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllLocationMappingsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where updatedBy is not null
        defaultLocationMappingFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationMappingsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where updatedBy contains
        defaultLocationMappingFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationMappingsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where updatedBy does not contain
        defaultLocationMappingFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllLocationMappingsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where updatedTime equals to
        defaultLocationMappingFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllLocationMappingsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where updatedTime in
        defaultLocationMappingFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllLocationMappingsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        // Get all the locationMappingList where updatedTime is not null
        defaultLocationMappingFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllLocationMappingsByFranchiseRuleIsEqualToSomething() throws Exception {
        FranchiseAllocationRule franchiseRule;
        if (TestUtil.findAll(em, FranchiseAllocationRule.class).isEmpty()) {
            locationMappingRepository.saveAndFlush(locationMapping);
            franchiseRule = FranchiseAllocationRuleResourceIT.createEntity();
        } else {
            franchiseRule = TestUtil.findAll(em, FranchiseAllocationRule.class).get(0);
        }
        em.persist(franchiseRule);
        em.flush();
        locationMapping.setFranchiseRule(franchiseRule);
        locationMappingRepository.saveAndFlush(locationMapping);
        Long franchiseRuleId = franchiseRule.getId();
        // Get all the locationMappingList where franchiseRule equals to franchiseRuleId
        defaultLocationMappingShouldBeFound("franchiseRuleId.equals=" + franchiseRuleId);

        // Get all the locationMappingList where franchiseRule equals to (franchiseRuleId + 1)
        defaultLocationMappingShouldNotBeFound("franchiseRuleId.equals=" + (franchiseRuleId + 1));
    }

    @Test
    @Transactional
    void getAllLocationMappingsByFieldAgentSkillRuleIsEqualToSomething() throws Exception {
        FieldAgentSkillRule fieldAgentSkillRule;
        if (TestUtil.findAll(em, FieldAgentSkillRule.class).isEmpty()) {
            locationMappingRepository.saveAndFlush(locationMapping);
            fieldAgentSkillRule = FieldAgentSkillRuleResourceIT.createEntity();
        } else {
            fieldAgentSkillRule = TestUtil.findAll(em, FieldAgentSkillRule.class).get(0);
        }
        em.persist(fieldAgentSkillRule);
        em.flush();
        locationMapping.setFieldAgentSkillRule(fieldAgentSkillRule);
        locationMappingRepository.saveAndFlush(locationMapping);
        Long fieldAgentSkillRuleId = fieldAgentSkillRule.getId();
        // Get all the locationMappingList where fieldAgentSkillRule equals to fieldAgentSkillRuleId
        defaultLocationMappingShouldBeFound("fieldAgentSkillRuleId.equals=" + fieldAgentSkillRuleId);

        // Get all the locationMappingList where fieldAgentSkillRule equals to (fieldAgentSkillRuleId + 1)
        defaultLocationMappingShouldNotBeFound("fieldAgentSkillRuleId.equals=" + (fieldAgentSkillRuleId + 1));
    }

    private void defaultLocationMappingFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultLocationMappingShouldBeFound(shouldBeFound);
        defaultLocationMappingShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocationMappingShouldBeFound(String filter) throws Exception {
        restLocationMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locationMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restLocationMappingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocationMappingShouldNotBeFound(String filter) throws Exception {
        restLocationMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocationMappingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLocationMapping() throws Exception {
        // Get the locationMapping
        restLocationMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocationMapping() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locationMapping
        LocationMapping updatedLocationMapping = locationMappingRepository.findById(locationMapping.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLocationMapping are not directly saved in db
        em.detach(updatedLocationMapping);
        updatedLocationMapping
            .locationName(UPDATED_LOCATION_NAME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(updatedLocationMapping);

        restLocationMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationMappingDTO))
            )
            .andExpect(status().isOk());

        // Validate the LocationMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocationMappingToMatchAllProperties(updatedLocationMapping);
    }

    @Test
    @Transactional
    void putNonExistingLocationMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationMapping.setId(longCount.incrementAndGet());

        // Create the LocationMapping
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocationMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationMapping.setId(longCount.incrementAndGet());

        // Create the LocationMapping
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocationMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationMapping.setId(longCount.incrementAndGet());

        // Create the LocationMapping
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMappingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationMappingWithPatch() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locationMapping using partial update
        LocationMapping partialUpdatedLocationMapping = new LocationMapping();
        partialUpdatedLocationMapping.setId(locationMapping.getId());

        partialUpdatedLocationMapping.createdTime(UPDATED_CREATED_TIME);

        restLocationMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocationMapping))
            )
            .andExpect(status().isOk());

        // Validate the LocationMapping in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationMappingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLocationMapping, locationMapping),
            getPersistedLocationMapping(locationMapping)
        );
    }

    @Test
    @Transactional
    void fullUpdateLocationMappingWithPatch() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the locationMapping using partial update
        LocationMapping partialUpdatedLocationMapping = new LocationMapping();
        partialUpdatedLocationMapping.setId(locationMapping.getId());

        partialUpdatedLocationMapping
            .locationName(UPDATED_LOCATION_NAME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restLocationMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocationMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLocationMapping))
            )
            .andExpect(status().isOk());

        // Validate the LocationMapping in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationMappingUpdatableFieldsEquals(
            partialUpdatedLocationMapping,
            getPersistedLocationMapping(partialUpdatedLocationMapping)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLocationMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationMapping.setId(longCount.incrementAndGet());

        // Create the LocationMapping
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationMappingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocationMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationMapping.setId(longCount.incrementAndGet());

        // Create the LocationMapping
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LocationMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocationMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        locationMapping.setId(longCount.incrementAndGet());

        // Create the LocationMapping
        LocationMappingDTO locationMappingDTO = locationMappingMapper.toDto(locationMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(locationMappingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LocationMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocationMapping() throws Exception {
        // Initialize the database
        insertedLocationMapping = locationMappingRepository.saveAndFlush(locationMapping);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the locationMapping
        restLocationMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, locationMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locationMappingRepository.count();
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

    protected LocationMapping getPersistedLocationMapping(LocationMapping locationMapping) {
        return locationMappingRepository.findById(locationMapping.getId()).orElseThrow();
    }

    protected void assertPersistedLocationMappingToMatchAllProperties(LocationMapping expectedLocationMapping) {
        assertLocationMappingAllPropertiesEquals(expectedLocationMapping, getPersistedLocationMapping(expectedLocationMapping));
    }

    protected void assertPersistedLocationMappingToMatchUpdatableProperties(LocationMapping expectedLocationMapping) {
        assertLocationMappingAllUpdatablePropertiesEquals(expectedLocationMapping, getPersistedLocationMapping(expectedLocationMapping));
    }
}
