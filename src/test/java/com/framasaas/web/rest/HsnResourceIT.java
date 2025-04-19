package com.framasaas.web.rest;

import static com.framasaas.domain.HsnAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Hsn;
import com.framasaas.repository.HsnRepository;
import com.framasaas.service.dto.HsnDTO;
import com.framasaas.service.mapper.HsnMapper;
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
 * Integration tests for the {@link HsnResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HsnResourceIT {

    private static final String DEFAULT_HSN_CD = "AAAAAAAAAA";
    private static final String UPDATED_HSN_CD = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_TAX_RATE = 1F;
    private static final Float UPDATED_TAX_RATE = 2F;
    private static final Float SMALLER_TAX_RATE = 1F - 1F;

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

    private static final String ENTITY_API_URL = "/api/hsns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HsnRepository hsnRepository;

    @Autowired
    private HsnMapper hsnMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHsnMockMvc;

    private Hsn hsn;

    private Hsn insertedHsn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hsn createEntity() {
        return new Hsn()
            .hsnCD(DEFAULT_HSN_CD)
            .description(DEFAULT_DESCRIPTION)
            .taxRate(DEFAULT_TAX_RATE)
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
    public static Hsn createUpdatedEntity() {
        return new Hsn()
            .hsnCD(UPDATED_HSN_CD)
            .description(UPDATED_DESCRIPTION)
            .taxRate(UPDATED_TAX_RATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        hsn = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedHsn != null) {
            hsnRepository.delete(insertedHsn);
            insertedHsn = null;
        }
    }

    @Test
    @Transactional
    void createHsn() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hsn
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);
        var returnedHsnDTO = om.readValue(
            restHsnMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HsnDTO.class
        );

        // Validate the Hsn in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHsn = hsnMapper.toEntity(returnedHsnDTO);
        assertHsnUpdatableFieldsEquals(returnedHsn, getPersistedHsn(returnedHsn));

        insertedHsn = returnedHsn;
    }

    @Test
    @Transactional
    void createHsnWithExistingId() throws Exception {
        // Create the Hsn with an existing ID
        hsn.setId(1L);
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHsnCDIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setHsnCD(null);

        // Create the Hsn, which fails.
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxRateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setTaxRate(null);

        // Create the Hsn, which fails.
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setCreateddBy(null);

        // Create the Hsn, which fails.
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setCreatedTime(null);

        // Create the Hsn, which fails.
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setUpdatedBy(null);

        // Create the Hsn, which fails.
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hsn.setUpdatedTime(null);

        // Create the Hsn, which fails.
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        restHsnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHsns() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList
        restHsnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hsn.getId().intValue())))
            .andExpect(jsonPath("$.[*].hsnCD").value(hasItem(DEFAULT_HSN_CD)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getHsn() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get the hsn
        restHsnMockMvc
            .perform(get(ENTITY_API_URL_ID, hsn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hsn.getId().intValue()))
            .andExpect(jsonPath("$.hsnCD").value(DEFAULT_HSN_CD))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getHsnsByIdFiltering() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        Long id = hsn.getId();

        defaultHsnFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultHsnFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultHsnFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHsnsByHsnCDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where hsnCD equals to
        defaultHsnFiltering("hsnCD.equals=" + DEFAULT_HSN_CD, "hsnCD.equals=" + UPDATED_HSN_CD);
    }

    @Test
    @Transactional
    void getAllHsnsByHsnCDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where hsnCD in
        defaultHsnFiltering("hsnCD.in=" + DEFAULT_HSN_CD + "," + UPDATED_HSN_CD, "hsnCD.in=" + UPDATED_HSN_CD);
    }

    @Test
    @Transactional
    void getAllHsnsByHsnCDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where hsnCD is not null
        defaultHsnFiltering("hsnCD.specified=true", "hsnCD.specified=false");
    }

    @Test
    @Transactional
    void getAllHsnsByHsnCDContainsSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where hsnCD contains
        defaultHsnFiltering("hsnCD.contains=" + DEFAULT_HSN_CD, "hsnCD.contains=" + UPDATED_HSN_CD);
    }

    @Test
    @Transactional
    void getAllHsnsByHsnCDNotContainsSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where hsnCD does not contain
        defaultHsnFiltering("hsnCD.doesNotContain=" + UPDATED_HSN_CD, "hsnCD.doesNotContain=" + DEFAULT_HSN_CD);
    }

    @Test
    @Transactional
    void getAllHsnsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where description equals to
        defaultHsnFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHsnsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where description in
        defaultHsnFiltering("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION, "description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHsnsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where description is not null
        defaultHsnFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllHsnsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where description contains
        defaultHsnFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHsnsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where description does not contain
        defaultHsnFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllHsnsByTaxRateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where taxRate equals to
        defaultHsnFiltering("taxRate.equals=" + DEFAULT_TAX_RATE, "taxRate.equals=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllHsnsByTaxRateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where taxRate in
        defaultHsnFiltering("taxRate.in=" + DEFAULT_TAX_RATE + "," + UPDATED_TAX_RATE, "taxRate.in=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllHsnsByTaxRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where taxRate is not null
        defaultHsnFiltering("taxRate.specified=true", "taxRate.specified=false");
    }

    @Test
    @Transactional
    void getAllHsnsByTaxRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where taxRate is greater than or equal to
        defaultHsnFiltering("taxRate.greaterThanOrEqual=" + DEFAULT_TAX_RATE, "taxRate.greaterThanOrEqual=" + UPDATED_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllHsnsByTaxRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where taxRate is less than or equal to
        defaultHsnFiltering("taxRate.lessThanOrEqual=" + DEFAULT_TAX_RATE, "taxRate.lessThanOrEqual=" + SMALLER_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllHsnsByTaxRateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where taxRate is less than
        defaultHsnFiltering("taxRate.lessThan=" + UPDATED_TAX_RATE, "taxRate.lessThan=" + DEFAULT_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllHsnsByTaxRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where taxRate is greater than
        defaultHsnFiltering("taxRate.greaterThan=" + SMALLER_TAX_RATE, "taxRate.greaterThan=" + DEFAULT_TAX_RATE);
    }

    @Test
    @Transactional
    void getAllHsnsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where isActive equals to
        defaultHsnFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllHsnsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where isActive in
        defaultHsnFiltering("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE, "isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllHsnsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where isActive is not null
        defaultHsnFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllHsnsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where createddBy equals to
        defaultHsnFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllHsnsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where createddBy in
        defaultHsnFiltering("createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY, "createddBy.in=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllHsnsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where createddBy is not null
        defaultHsnFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllHsnsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where createddBy contains
        defaultHsnFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllHsnsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where createddBy does not contain
        defaultHsnFiltering("createddBy.doesNotContain=" + UPDATED_CREATEDD_BY, "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllHsnsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where createdTime equals to
        defaultHsnFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllHsnsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where createdTime in
        defaultHsnFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllHsnsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where createdTime is not null
        defaultHsnFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllHsnsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where updatedBy equals to
        defaultHsnFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllHsnsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where updatedBy in
        defaultHsnFiltering("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY, "updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllHsnsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where updatedBy is not null
        defaultHsnFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllHsnsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where updatedBy contains
        defaultHsnFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllHsnsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where updatedBy does not contain
        defaultHsnFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllHsnsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where updatedTime equals to
        defaultHsnFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllHsnsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where updatedTime in
        defaultHsnFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllHsnsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        // Get all the hsnList where updatedTime is not null
        defaultHsnFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    private void defaultHsnFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultHsnShouldBeFound(shouldBeFound);
        defaultHsnShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHsnShouldBeFound(String filter) throws Exception {
        restHsnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hsn.getId().intValue())))
            .andExpect(jsonPath("$.[*].hsnCD").value(hasItem(DEFAULT_HSN_CD)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restHsnMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHsnShouldNotBeFound(String filter) throws Exception {
        restHsnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHsnMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHsn() throws Exception {
        // Get the hsn
        restHsnMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHsn() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hsn
        Hsn updatedHsn = hsnRepository.findById(hsn.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHsn are not directly saved in db
        em.detach(updatedHsn);
        updatedHsn
            .hsnCD(UPDATED_HSN_CD)
            .description(UPDATED_DESCRIPTION)
            .taxRate(UPDATED_TAX_RATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        HsnDTO hsnDTO = hsnMapper.toDto(updatedHsn);

        restHsnMockMvc
            .perform(put(ENTITY_API_URL_ID, hsnDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isOk());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHsnToMatchAllProperties(updatedHsn);
    }

    @Test
    @Transactional
    void putNonExistingHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // Create the Hsn
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(put(ENTITY_API_URL_ID, hsnDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // Create the Hsn
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hsnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // Create the Hsn
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHsnWithPatch() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hsn using partial update
        Hsn partialUpdatedHsn = new Hsn();
        partialUpdatedHsn.setId(hsn.getId());

        partialUpdatedHsn
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);

        restHsnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHsn.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHsn))
            )
            .andExpect(status().isOk());

        // Validate the Hsn in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHsnUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHsn, hsn), getPersistedHsn(hsn));
    }

    @Test
    @Transactional
    void fullUpdateHsnWithPatch() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hsn using partial update
        Hsn partialUpdatedHsn = new Hsn();
        partialUpdatedHsn.setId(hsn.getId());

        partialUpdatedHsn
            .hsnCD(UPDATED_HSN_CD)
            .description(UPDATED_DESCRIPTION)
            .taxRate(UPDATED_TAX_RATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restHsnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHsn.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHsn))
            )
            .andExpect(status().isOk());

        // Validate the Hsn in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHsnUpdatableFieldsEquals(partialUpdatedHsn, getPersistedHsn(partialUpdatedHsn));
    }

    @Test
    @Transactional
    void patchNonExistingHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // Create the Hsn
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hsnDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hsnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // Create the Hsn
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hsnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHsn() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hsn.setId(longCount.incrementAndGet());

        // Create the Hsn
        HsnDTO hsnDTO = hsnMapper.toDto(hsn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHsnMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hsnDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hsn in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHsn() throws Exception {
        // Initialize the database
        insertedHsn = hsnRepository.saveAndFlush(hsn);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hsn
        restHsnMockMvc.perform(delete(ENTITY_API_URL_ID, hsn.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hsnRepository.count();
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

    protected Hsn getPersistedHsn(Hsn hsn) {
        return hsnRepository.findById(hsn.getId()).orElseThrow();
    }

    protected void assertPersistedHsnToMatchAllProperties(Hsn expectedHsn) {
        assertHsnAllPropertiesEquals(expectedHsn, getPersistedHsn(expectedHsn));
    }

    protected void assertPersistedHsnToMatchUpdatableProperties(Hsn expectedHsn) {
        assertHsnAllUpdatablePropertiesEquals(expectedHsn, getPersistedHsn(expectedHsn));
    }
}
