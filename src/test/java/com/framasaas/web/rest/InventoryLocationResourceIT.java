package com.framasaas.web.rest;

import static com.framasaas.domain.InventoryLocationAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.InventoryLocation;
import com.framasaas.domain.enumeration.InventoryLocationType;
import com.framasaas.repository.InventoryLocationRepository;
import com.framasaas.service.dto.InventoryLocationDTO;
import com.framasaas.service.mapper.InventoryLocationMapper;
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
 * Integration tests for the {@link InventoryLocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InventoryLocationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final InventoryLocationType DEFAULT_LOCATION_TYPE = InventoryLocationType.ENGINEER;
    private static final InventoryLocationType UPDATED_LOCATION_TYPE = InventoryLocationType.FRANCHISE;

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

    private static final String ENTITY_API_URL = "/api/inventory-locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InventoryLocationRepository inventoryLocationRepository;

    @Autowired
    private InventoryLocationMapper inventoryLocationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryLocationMockMvc;

    private InventoryLocation inventoryLocation;

    private InventoryLocation insertedInventoryLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryLocation createEntity() {
        return new InventoryLocation()
            .name(DEFAULT_NAME)
            .locationType(DEFAULT_LOCATION_TYPE)
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
    public static InventoryLocation createUpdatedEntity() {
        return new InventoryLocation()
            .name(UPDATED_NAME)
            .locationType(UPDATED_LOCATION_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        inventoryLocation = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedInventoryLocation != null) {
            inventoryLocationRepository.delete(insertedInventoryLocation);
            insertedInventoryLocation = null;
        }
    }

    @Test
    @Transactional
    void createInventoryLocation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InventoryLocation
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);
        var returnedInventoryLocationDTO = om.readValue(
            restInventoryLocationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InventoryLocationDTO.class
        );

        // Validate the InventoryLocation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInventoryLocation = inventoryLocationMapper.toEntity(returnedInventoryLocationDTO);
        assertInventoryLocationUpdatableFieldsEquals(returnedInventoryLocation, getPersistedInventoryLocation(returnedInventoryLocation));

        insertedInventoryLocation = returnedInventoryLocation;
    }

    @Test
    @Transactional
    void createInventoryLocationWithExistingId() throws Exception {
        // Create the InventoryLocation with an existing ID
        inventoryLocation.setId(1L);
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryLocation.setCreateddBy(null);

        // Create the InventoryLocation, which fails.
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        restInventoryLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryLocation.setCreatedTime(null);

        // Create the InventoryLocation, which fails.
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        restInventoryLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryLocation.setUpdatedBy(null);

        // Create the InventoryLocation, which fails.
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        restInventoryLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventoryLocation.setUpdatedTime(null);

        // Create the InventoryLocation, which fails.
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        restInventoryLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInventoryLocations() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList
        restInventoryLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].locationType").value(hasItem(DEFAULT_LOCATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getInventoryLocation() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get the inventoryLocation
        restInventoryLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, inventoryLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryLocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.locationType").value(DEFAULT_LOCATION_TYPE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getInventoryLocationsByIdFiltering() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        Long id = inventoryLocation.getId();

        defaultInventoryLocationFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultInventoryLocationFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultInventoryLocationFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where name equals to
        defaultInventoryLocationFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where name in
        defaultInventoryLocationFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where name is not null
        defaultInventoryLocationFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where name contains
        defaultInventoryLocationFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where name does not contain
        defaultInventoryLocationFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByLocationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where locationType equals to
        defaultInventoryLocationFiltering("locationType.equals=" + DEFAULT_LOCATION_TYPE, "locationType.equals=" + UPDATED_LOCATION_TYPE);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByLocationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where locationType in
        defaultInventoryLocationFiltering(
            "locationType.in=" + DEFAULT_LOCATION_TYPE + "," + UPDATED_LOCATION_TYPE,
            "locationType.in=" + UPDATED_LOCATION_TYPE
        );
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByLocationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where locationType is not null
        defaultInventoryLocationFiltering("locationType.specified=true", "locationType.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where isActive equals to
        defaultInventoryLocationFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where isActive in
        defaultInventoryLocationFiltering("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE, "isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where isActive is not null
        defaultInventoryLocationFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where createddBy equals to
        defaultInventoryLocationFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where createddBy in
        defaultInventoryLocationFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where createddBy is not null
        defaultInventoryLocationFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where createddBy contains
        defaultInventoryLocationFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where createddBy does not contain
        defaultInventoryLocationFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where createdTime equals to
        defaultInventoryLocationFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where createdTime in
        defaultInventoryLocationFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where createdTime is not null
        defaultInventoryLocationFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where updatedBy equals to
        defaultInventoryLocationFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where updatedBy in
        defaultInventoryLocationFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where updatedBy is not null
        defaultInventoryLocationFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where updatedBy contains
        defaultInventoryLocationFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where updatedBy does not contain
        defaultInventoryLocationFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where updatedTime equals to
        defaultInventoryLocationFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where updatedTime in
        defaultInventoryLocationFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllInventoryLocationsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where updatedTime is not null
        defaultInventoryLocationFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    private void defaultInventoryLocationFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultInventoryLocationShouldBeFound(shouldBeFound);
        defaultInventoryLocationShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryLocationShouldBeFound(String filter) throws Exception {
        restInventoryLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].locationType").value(hasItem(DEFAULT_LOCATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restInventoryLocationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryLocationShouldNotBeFound(String filter) throws Exception {
        restInventoryLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryLocationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInventoryLocation() throws Exception {
        // Get the inventoryLocation
        restInventoryLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInventoryLocation() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventoryLocation
        InventoryLocation updatedInventoryLocation = inventoryLocationRepository.findById(inventoryLocation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInventoryLocation are not directly saved in db
        em.detach(updatedInventoryLocation);
        updatedInventoryLocation
            .name(UPDATED_NAME)
            .locationType(UPDATED_LOCATION_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(updatedInventoryLocation);

        restInventoryLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryLocationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryLocationDTO))
            )
            .andExpect(status().isOk());

        // Validate the InventoryLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInventoryLocationToMatchAllProperties(updatedInventoryLocation);
    }

    @Test
    @Transactional
    void putNonExistingInventoryLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryLocation.setId(longCount.incrementAndGet());

        // Create the InventoryLocation
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryLocationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInventoryLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryLocation.setId(longCount.incrementAndGet());

        // Create the InventoryLocation
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInventoryLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryLocation.setId(longCount.incrementAndGet());

        // Create the InventoryLocation
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InventoryLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInventoryLocationWithPatch() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventoryLocation using partial update
        InventoryLocation partialUpdatedInventoryLocation = new InventoryLocation();
        partialUpdatedInventoryLocation.setId(inventoryLocation.getId());

        restInventoryLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventoryLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventoryLocation))
            )
            .andExpect(status().isOk());

        // Validate the InventoryLocation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventoryLocationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInventoryLocation, inventoryLocation),
            getPersistedInventoryLocation(inventoryLocation)
        );
    }

    @Test
    @Transactional
    void fullUpdateInventoryLocationWithPatch() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventoryLocation using partial update
        InventoryLocation partialUpdatedInventoryLocation = new InventoryLocation();
        partialUpdatedInventoryLocation.setId(inventoryLocation.getId());

        partialUpdatedInventoryLocation
            .name(UPDATED_NAME)
            .locationType(UPDATED_LOCATION_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restInventoryLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventoryLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventoryLocation))
            )
            .andExpect(status().isOk());

        // Validate the InventoryLocation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventoryLocationUpdatableFieldsEquals(
            partialUpdatedInventoryLocation,
            getPersistedInventoryLocation(partialUpdatedInventoryLocation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInventoryLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryLocation.setId(longCount.incrementAndGet());

        // Create the InventoryLocation
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventoryLocationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventoryLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInventoryLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryLocation.setId(longCount.incrementAndGet());

        // Create the InventoryLocation
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventoryLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInventoryLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventoryLocation.setId(longCount.incrementAndGet());

        // Create the InventoryLocation
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inventoryLocationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InventoryLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInventoryLocation() throws Exception {
        // Initialize the database
        insertedInventoryLocation = inventoryLocationRepository.saveAndFlush(inventoryLocation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inventoryLocation
        restInventoryLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventoryLocation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inventoryLocationRepository.count();
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

    protected InventoryLocation getPersistedInventoryLocation(InventoryLocation inventoryLocation) {
        return inventoryLocationRepository.findById(inventoryLocation.getId()).orElseThrow();
    }

    protected void assertPersistedInventoryLocationToMatchAllProperties(InventoryLocation expectedInventoryLocation) {
        assertInventoryLocationAllPropertiesEquals(expectedInventoryLocation, getPersistedInventoryLocation(expectedInventoryLocation));
    }

    protected void assertPersistedInventoryLocationToMatchUpdatableProperties(InventoryLocation expectedInventoryLocation) {
        assertInventoryLocationAllUpdatablePropertiesEquals(
            expectedInventoryLocation,
            getPersistedInventoryLocation(expectedInventoryLocation)
        );
    }
}
