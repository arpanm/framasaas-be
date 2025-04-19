package com.framasaas.web.rest;

import static com.framasaas.domain.BrandAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Brand;
import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.repository.BrandRepository;
import com.framasaas.service.dto.BrandDTO;
import com.framasaas.service.mapper.BrandMapper;
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
 * Integration tests for the {@link BrandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BrandResourceIT {

    private static final String DEFAULT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_PATH = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_BRAND_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_BRAND_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/brands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBrandMockMvc;

    private Brand brand;

    private Brand insertedBrand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brand createEntity() {
        return new Brand()
            .brandName(DEFAULT_BRAND_NAME)
            .logoPath(DEFAULT_LOGO_PATH)
            .vendorBrandId(DEFAULT_VENDOR_BRAND_ID)
            .description(DEFAULT_DESCRIPTION)
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
    public static Brand createUpdatedEntity() {
        return new Brand()
            .brandName(UPDATED_BRAND_NAME)
            .logoPath(UPDATED_LOGO_PATH)
            .vendorBrandId(UPDATED_VENDOR_BRAND_ID)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        brand = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedBrand != null) {
            brandRepository.delete(insertedBrand);
            insertedBrand = null;
        }
    }

    @Test
    @Transactional
    void createBrand() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);
        var returnedBrandDTO = om.readValue(
            restBrandMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BrandDTO.class
        );

        // Validate the Brand in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBrand = brandMapper.toEntity(returnedBrandDTO);
        assertBrandUpdatableFieldsEquals(returnedBrand, getPersistedBrand(returnedBrand));

        insertedBrand = returnedBrand;
    }

    @Test
    @Transactional
    void createBrandWithExistingId() throws Exception {
        // Create the Brand with an existing ID
        brand.setId(1L);
        BrandDTO brandDTO = brandMapper.toDto(brand);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBrandNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        brand.setBrandName(null);

        // Create the Brand, which fails.
        BrandDTO brandDTO = brandMapper.toDto(brand);

        restBrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVendorBrandIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        brand.setVendorBrandId(null);

        // Create the Brand, which fails.
        BrandDTO brandDTO = brandMapper.toDto(brand);

        restBrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        brand.setCreateddBy(null);

        // Create the Brand, which fails.
        BrandDTO brandDTO = brandMapper.toDto(brand);

        restBrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        brand.setCreatedTime(null);

        // Create the Brand, which fails.
        BrandDTO brandDTO = brandMapper.toDto(brand);

        restBrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        brand.setUpdatedBy(null);

        // Create the Brand, which fails.
        BrandDTO brandDTO = brandMapper.toDto(brand);

        restBrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        brand.setUpdatedTime(null);

        // Create the Brand, which fails.
        BrandDTO brandDTO = brandMapper.toDto(brand);

        restBrandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBrands() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].logoPath").value(hasItem(DEFAULT_LOGO_PATH)))
            .andExpect(jsonPath("$.[*].vendorBrandId").value(hasItem(DEFAULT_VENDOR_BRAND_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getBrand() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get the brand
        restBrandMockMvc
            .perform(get(ENTITY_API_URL_ID, brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(brand.getId().intValue()))
            .andExpect(jsonPath("$.brandName").value(DEFAULT_BRAND_NAME))
            .andExpect(jsonPath("$.logoPath").value(DEFAULT_LOGO_PATH))
            .andExpect(jsonPath("$.vendorBrandId").value(DEFAULT_VENDOR_BRAND_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getBrandsByIdFiltering() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        Long id = brand.getId();

        defaultBrandFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBrandFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBrandFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBrandsByBrandNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where brandName equals to
        defaultBrandFiltering("brandName.equals=" + DEFAULT_BRAND_NAME, "brandName.equals=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    void getAllBrandsByBrandNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where brandName in
        defaultBrandFiltering("brandName.in=" + DEFAULT_BRAND_NAME + "," + UPDATED_BRAND_NAME, "brandName.in=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    void getAllBrandsByBrandNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where brandName is not null
        defaultBrandFiltering("brandName.specified=true", "brandName.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByBrandNameContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where brandName contains
        defaultBrandFiltering("brandName.contains=" + DEFAULT_BRAND_NAME, "brandName.contains=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    void getAllBrandsByBrandNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where brandName does not contain
        defaultBrandFiltering("brandName.doesNotContain=" + UPDATED_BRAND_NAME, "brandName.doesNotContain=" + DEFAULT_BRAND_NAME);
    }

    @Test
    @Transactional
    void getAllBrandsByLogoPathIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where logoPath equals to
        defaultBrandFiltering("logoPath.equals=" + DEFAULT_LOGO_PATH, "logoPath.equals=" + UPDATED_LOGO_PATH);
    }

    @Test
    @Transactional
    void getAllBrandsByLogoPathIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where logoPath in
        defaultBrandFiltering("logoPath.in=" + DEFAULT_LOGO_PATH + "," + UPDATED_LOGO_PATH, "logoPath.in=" + UPDATED_LOGO_PATH);
    }

    @Test
    @Transactional
    void getAllBrandsByLogoPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where logoPath is not null
        defaultBrandFiltering("logoPath.specified=true", "logoPath.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByLogoPathContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where logoPath contains
        defaultBrandFiltering("logoPath.contains=" + DEFAULT_LOGO_PATH, "logoPath.contains=" + UPDATED_LOGO_PATH);
    }

    @Test
    @Transactional
    void getAllBrandsByLogoPathNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where logoPath does not contain
        defaultBrandFiltering("logoPath.doesNotContain=" + UPDATED_LOGO_PATH, "logoPath.doesNotContain=" + DEFAULT_LOGO_PATH);
    }

    @Test
    @Transactional
    void getAllBrandsByVendorBrandIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where vendorBrandId equals to
        defaultBrandFiltering("vendorBrandId.equals=" + DEFAULT_VENDOR_BRAND_ID, "vendorBrandId.equals=" + UPDATED_VENDOR_BRAND_ID);
    }

    @Test
    @Transactional
    void getAllBrandsByVendorBrandIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where vendorBrandId in
        defaultBrandFiltering(
            "vendorBrandId.in=" + DEFAULT_VENDOR_BRAND_ID + "," + UPDATED_VENDOR_BRAND_ID,
            "vendorBrandId.in=" + UPDATED_VENDOR_BRAND_ID
        );
    }

    @Test
    @Transactional
    void getAllBrandsByVendorBrandIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where vendorBrandId is not null
        defaultBrandFiltering("vendorBrandId.specified=true", "vendorBrandId.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByVendorBrandIdContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where vendorBrandId contains
        defaultBrandFiltering("vendorBrandId.contains=" + DEFAULT_VENDOR_BRAND_ID, "vendorBrandId.contains=" + UPDATED_VENDOR_BRAND_ID);
    }

    @Test
    @Transactional
    void getAllBrandsByVendorBrandIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where vendorBrandId does not contain
        defaultBrandFiltering(
            "vendorBrandId.doesNotContain=" + UPDATED_VENDOR_BRAND_ID,
            "vendorBrandId.doesNotContain=" + DEFAULT_VENDOR_BRAND_ID
        );
    }

    @Test
    @Transactional
    void getAllBrandsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where description equals to
        defaultBrandFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBrandsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where description in
        defaultBrandFiltering("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION, "description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBrandsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where description is not null
        defaultBrandFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where description contains
        defaultBrandFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBrandsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where description does not contain
        defaultBrandFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllBrandsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where isActive equals to
        defaultBrandFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllBrandsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where isActive in
        defaultBrandFiltering("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE, "isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllBrandsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where isActive is not null
        defaultBrandFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where createddBy equals to
        defaultBrandFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllBrandsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where createddBy in
        defaultBrandFiltering("createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY, "createddBy.in=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllBrandsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where createddBy is not null
        defaultBrandFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where createddBy contains
        defaultBrandFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllBrandsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where createddBy does not contain
        defaultBrandFiltering("createddBy.doesNotContain=" + UPDATED_CREATEDD_BY, "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllBrandsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where createdTime equals to
        defaultBrandFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllBrandsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where createdTime in
        defaultBrandFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllBrandsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where createdTime is not null
        defaultBrandFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where updatedBy equals to
        defaultBrandFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllBrandsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where updatedBy in
        defaultBrandFiltering("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY, "updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllBrandsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where updatedBy is not null
        defaultBrandFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where updatedBy contains
        defaultBrandFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllBrandsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where updatedBy does not contain
        defaultBrandFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllBrandsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where updatedTime equals to
        defaultBrandFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllBrandsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where updatedTime in
        defaultBrandFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllBrandsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        // Get all the brandList where updatedTime is not null
        defaultBrandFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllBrandsByFranchiseRuleIsEqualToSomething() throws Exception {
        FranchiseAllocationRule franchiseRule;
        if (TestUtil.findAll(em, FranchiseAllocationRule.class).isEmpty()) {
            brandRepository.saveAndFlush(brand);
            franchiseRule = FranchiseAllocationRuleResourceIT.createEntity();
        } else {
            franchiseRule = TestUtil.findAll(em, FranchiseAllocationRule.class).get(0);
        }
        em.persist(franchiseRule);
        em.flush();
        brand.setFranchiseRule(franchiseRule);
        brandRepository.saveAndFlush(brand);
        Long franchiseRuleId = franchiseRule.getId();
        // Get all the brandList where franchiseRule equals to franchiseRuleId
        defaultBrandShouldBeFound("franchiseRuleId.equals=" + franchiseRuleId);

        // Get all the brandList where franchiseRule equals to (franchiseRuleId + 1)
        defaultBrandShouldNotBeFound("franchiseRuleId.equals=" + (franchiseRuleId + 1));
    }

    @Test
    @Transactional
    void getAllBrandsByFieldAgentSkillRuleIsEqualToSomething() throws Exception {
        FieldAgentSkillRule fieldAgentSkillRule;
        if (TestUtil.findAll(em, FieldAgentSkillRule.class).isEmpty()) {
            brandRepository.saveAndFlush(brand);
            fieldAgentSkillRule = FieldAgentSkillRuleResourceIT.createEntity();
        } else {
            fieldAgentSkillRule = TestUtil.findAll(em, FieldAgentSkillRule.class).get(0);
        }
        em.persist(fieldAgentSkillRule);
        em.flush();
        brand.setFieldAgentSkillRule(fieldAgentSkillRule);
        brandRepository.saveAndFlush(brand);
        Long fieldAgentSkillRuleId = fieldAgentSkillRule.getId();
        // Get all the brandList where fieldAgentSkillRule equals to fieldAgentSkillRuleId
        defaultBrandShouldBeFound("fieldAgentSkillRuleId.equals=" + fieldAgentSkillRuleId);

        // Get all the brandList where fieldAgentSkillRule equals to (fieldAgentSkillRuleId + 1)
        defaultBrandShouldNotBeFound("fieldAgentSkillRuleId.equals=" + (fieldAgentSkillRuleId + 1));
    }

    private void defaultBrandFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBrandShouldBeFound(shouldBeFound);
        defaultBrandShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBrandShouldBeFound(String filter) throws Exception {
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].logoPath").value(hasItem(DEFAULT_LOGO_PATH)))
            .andExpect(jsonPath("$.[*].vendorBrandId").value(hasItem(DEFAULT_VENDOR_BRAND_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBrandShouldNotBeFound(String filter) throws Exception {
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBrandMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBrand() throws Exception {
        // Get the brand
        restBrandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBrand() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brand
        Brand updatedBrand = brandRepository.findById(brand.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBrand are not directly saved in db
        em.detach(updatedBrand);
        updatedBrand
            .brandName(UPDATED_BRAND_NAME)
            .logoPath(UPDATED_LOGO_PATH)
            .vendorBrandId(UPDATED_VENDOR_BRAND_ID)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        BrandDTO brandDTO = brandMapper.toDto(updatedBrand);

        restBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, brandDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO))
            )
            .andExpect(status().isOk());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBrandToMatchAllProperties(updatedBrand);
    }

    @Test
    @Transactional
    void putNonExistingBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, brandDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(brandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBrandWithPatch() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brand using partial update
        Brand partialUpdatedBrand = new Brand();
        partialUpdatedBrand.setId(brand.getId());

        partialUpdatedBrand
            .brandName(UPDATED_BRAND_NAME)
            .logoPath(UPDATED_LOGO_PATH)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBrand))
            )
            .andExpect(status().isOk());

        // Validate the Brand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBrandUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBrand, brand), getPersistedBrand(brand));
    }

    @Test
    @Transactional
    void fullUpdateBrandWithPatch() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the brand using partial update
        Brand partialUpdatedBrand = new Brand();
        partialUpdatedBrand.setId(brand.getId());

        partialUpdatedBrand
            .brandName(UPDATED_BRAND_NAME)
            .logoPath(UPDATED_LOGO_PATH)
            .vendorBrandId(UPDATED_VENDOR_BRAND_ID)
            .description(UPDATED_DESCRIPTION)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBrand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBrand))
            )
            .andExpect(status().isOk());

        // Validate the Brand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBrandUpdatableFieldsEquals(partialUpdatedBrand, getPersistedBrand(partialUpdatedBrand));
    }

    @Test
    @Transactional
    void patchNonExistingBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, brandDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(brandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(brandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBrand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        brand.setId(longCount.incrementAndGet());

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBrandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(brandDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Brand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBrand() throws Exception {
        // Initialize the database
        insertedBrand = brandRepository.saveAndFlush(brand);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the brand
        restBrandMockMvc
            .perform(delete(ENTITY_API_URL_ID, brand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return brandRepository.count();
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

    protected Brand getPersistedBrand(Brand brand) {
        return brandRepository.findById(brand.getId()).orElseThrow();
    }

    protected void assertPersistedBrandToMatchAllProperties(Brand expectedBrand) {
        assertBrandAllPropertiesEquals(expectedBrand, getPersistedBrand(expectedBrand));
    }

    protected void assertPersistedBrandToMatchUpdatableProperties(Brand expectedBrand) {
        assertBrandAllUpdatablePropertiesEquals(expectedBrand, getPersistedBrand(expectedBrand));
    }
}
