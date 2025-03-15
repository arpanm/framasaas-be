package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.InventoryLocationAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.InventoryLocation;
import com.framasaas.be.domain.enumeration.InventoryLocationType;
import com.framasaas.be.repository.InventoryLocationRepository;
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
    public void initTest() {
        inventoryLocation = createEntity();
    }

    @AfterEach
    public void cleanup() {
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
        var returnedInventoryLocation = om.readValue(
            restInventoryLocationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocation)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InventoryLocation.class
        );

        // Validate the InventoryLocation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInventoryLocationUpdatableFieldsEquals(returnedInventoryLocation, getPersistedInventoryLocation(returnedInventoryLocation));

        insertedInventoryLocation = returnedInventoryLocation;
    }

    @Test
    @Transactional
    void createInventoryLocationWithExistingId() throws Exception {
        // Create the InventoryLocation with an existing ID
        inventoryLocation.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocation)))
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

        restInventoryLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocation)))
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

        restInventoryLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocation)))
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

        restInventoryLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocation)))
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

        restInventoryLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocation)))
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

        restInventoryLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInventoryLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInventoryLocation))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryLocation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryLocation))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventoryLocation))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventoryLocation)))
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

        partialUpdatedInventoryLocation.updatedBy(UPDATED_UPDATED_BY);

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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventoryLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventoryLocation))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventoryLocation))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inventoryLocation)))
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
