package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.LocationMappingAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.LocationMapping;
import com.framasaas.be.repository.LocationMappingRepository;
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
    public void initTest() {
        locationMapping = createEntity();
    }

    @AfterEach
    public void cleanup() {
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
        var returnedLocationMapping = om.readValue(
            restLocationMappingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMapping)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LocationMapping.class
        );

        // Validate the LocationMapping in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLocationMappingUpdatableFieldsEquals(returnedLocationMapping, getPersistedLocationMapping(returnedLocationMapping));

        insertedLocationMapping = returnedLocationMapping;
    }

    @Test
    @Transactional
    void createLocationMappingWithExistingId() throws Exception {
        // Create the LocationMapping with an existing ID
        locationMapping.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMapping)))
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

        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMapping)))
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

        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMapping)))
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

        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMapping)))
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

        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMapping)))
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

        restLocationMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMapping)))
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

        restLocationMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLocationMapping.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLocationMapping))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationMapping.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationMapping))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(locationMapping))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(locationMapping)))
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

        partialUpdatedLocationMapping.updatedBy(UPDATED_UPDATED_BY);

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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationMapping))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(locationMapping))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMappingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(locationMapping)))
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
