package com.framasaas.web.rest;

import static com.framasaas.domain.ArticleWarrantyDetailsAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Article;
import com.framasaas.domain.ArticleWarrantyDetails;
import com.framasaas.domain.WarrantyMaster;
import com.framasaas.domain.enumeration.SoldBy;
import com.framasaas.domain.enumeration.WarrantyType;
import com.framasaas.repository.ArticleWarrantyDetailsRepository;
import com.framasaas.service.dto.ArticleWarrantyDetailsDTO;
import com.framasaas.service.mapper.ArticleWarrantyDetailsMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ArticleWarrantyDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticleWarrantyDetailsResourceIT {

    private static final WarrantyType DEFAULT_WARRANTY_TYPE = WarrantyType.BRANDFREEWARRANTY;
    private static final WarrantyType UPDATED_WARRANTY_TYPE = WarrantyType.EXTENDEDPAIDWARRANTY;

    private static final String DEFAULT_VENDOR_ARTICLE_WARRANTY_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_ARTICLE_WARRANTY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_WARRANTY_MASTER_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_WARRANTY_MASTER_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final SoldBy DEFAULT_SOLD_BY = SoldBy.FRANCHISE;
    private static final SoldBy UPDATED_SOLD_BY = SoldBy.ENGINEER;

    private static final String DEFAULT_SOLD_BY_USER = "AAAAAAAAAA";
    private static final String UPDATED_SOLD_BY_USER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SOLD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SOLD_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_SOLD_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_IS_VALIDATED = false;
    private static final Boolean UPDATED_IS_VALIDATED = true;

    private static final String DEFAULT_VALIDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALIDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALIDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/article-warranty-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository;

    @Autowired
    private ArticleWarrantyDetailsMapper articleWarrantyDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleWarrantyDetailsMockMvc;

    private ArticleWarrantyDetails articleWarrantyDetails;

    private ArticleWarrantyDetails insertedArticleWarrantyDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleWarrantyDetails createEntity() {
        return new ArticleWarrantyDetails()
            .warrantyType(DEFAULT_WARRANTY_TYPE)
            .vendorArticleWarrantyId(DEFAULT_VENDOR_ARTICLE_WARRANTY_ID)
            .vendorWarrantyMasterId(DEFAULT_VENDOR_WARRANTY_MASTER_ID)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .soldBy(DEFAULT_SOLD_BY)
            .soldByUser(DEFAULT_SOLD_BY_USER)
            .soldDate(DEFAULT_SOLD_DATE)
            .isValidated(DEFAULT_IS_VALIDATED)
            .validatedBy(DEFAULT_VALIDATED_BY)
            .validatedTime(DEFAULT_VALIDATED_TIME)
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
    public static ArticleWarrantyDetails createUpdatedEntity() {
        return new ArticleWarrantyDetails()
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .vendorArticleWarrantyId(UPDATED_VENDOR_ARTICLE_WARRANTY_ID)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .soldBy(UPDATED_SOLD_BY)
            .soldByUser(UPDATED_SOLD_BY_USER)
            .soldDate(UPDATED_SOLD_DATE)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        articleWarrantyDetails = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedArticleWarrantyDetails != null) {
            articleWarrantyDetailsRepository.delete(insertedArticleWarrantyDetails);
            insertedArticleWarrantyDetails = null;
        }
    }

    @Test
    @Transactional
    void createArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ArticleWarrantyDetails
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);
        var returnedArticleWarrantyDetailsDTO = om.readValue(
            restArticleWarrantyDetailsMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ArticleWarrantyDetailsDTO.class
        );

        // Validate the ArticleWarrantyDetails in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedArticleWarrantyDetails = articleWarrantyDetailsMapper.toEntity(returnedArticleWarrantyDetailsDTO);
        assertArticleWarrantyDetailsUpdatableFieldsEquals(
            returnedArticleWarrantyDetails,
            getPersistedArticleWarrantyDetails(returnedArticleWarrantyDetails)
        );

        insertedArticleWarrantyDetails = returnedArticleWarrantyDetails;
    }

    @Test
    @Transactional
    void createArticleWarrantyDetailsWithExistingId() throws Exception {
        // Create the ArticleWarrantyDetails with an existing ID
        articleWarrantyDetails.setId(1L);
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleWarrantyDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetails.setCreateddBy(null);

        // Create the ArticleWarrantyDetails, which fails.
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        restArticleWarrantyDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetails.setCreatedTime(null);

        // Create the ArticleWarrantyDetails, which fails.
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        restArticleWarrantyDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetails.setUpdatedBy(null);

        // Create the ArticleWarrantyDetails, which fails.
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        restArticleWarrantyDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetails.setUpdatedTime(null);

        // Create the ArticleWarrantyDetails, which fails.
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        restArticleWarrantyDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetails() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList
        restArticleWarrantyDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleWarrantyDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].warrantyType").value(hasItem(DEFAULT_WARRANTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vendorArticleWarrantyId").value(hasItem(DEFAULT_VENDOR_ARTICLE_WARRANTY_ID)))
            .andExpect(jsonPath("$.[*].vendorWarrantyMasterId").value(hasItem(DEFAULT_VENDOR_WARRANTY_MASTER_ID)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].soldBy").value(hasItem(DEFAULT_SOLD_BY.toString())))
            .andExpect(jsonPath("$.[*].soldByUser").value(hasItem(DEFAULT_SOLD_BY_USER)))
            .andExpect(jsonPath("$.[*].soldDate").value(hasItem(DEFAULT_SOLD_DATE.toString())))
            .andExpect(jsonPath("$.[*].isValidated").value(hasItem(DEFAULT_IS_VALIDATED)))
            .andExpect(jsonPath("$.[*].validatedBy").value(hasItem(DEFAULT_VALIDATED_BY)))
            .andExpect(jsonPath("$.[*].validatedTime").value(hasItem(DEFAULT_VALIDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getArticleWarrantyDetails() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get the articleWarrantyDetails
        restArticleWarrantyDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, articleWarrantyDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleWarrantyDetails.getId().intValue()))
            .andExpect(jsonPath("$.warrantyType").value(DEFAULT_WARRANTY_TYPE.toString()))
            .andExpect(jsonPath("$.vendorArticleWarrantyId").value(DEFAULT_VENDOR_ARTICLE_WARRANTY_ID))
            .andExpect(jsonPath("$.vendorWarrantyMasterId").value(DEFAULT_VENDOR_WARRANTY_MASTER_ID))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.soldBy").value(DEFAULT_SOLD_BY.toString()))
            .andExpect(jsonPath("$.soldByUser").value(DEFAULT_SOLD_BY_USER))
            .andExpect(jsonPath("$.soldDate").value(DEFAULT_SOLD_DATE.toString()))
            .andExpect(jsonPath("$.isValidated").value(DEFAULT_IS_VALIDATED))
            .andExpect(jsonPath("$.validatedBy").value(DEFAULT_VALIDATED_BY))
            .andExpect(jsonPath("$.validatedTime").value(DEFAULT_VALIDATED_TIME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getArticleWarrantyDetailsByIdFiltering() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        Long id = articleWarrantyDetails.getId();

        defaultArticleWarrantyDetailsFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultArticleWarrantyDetailsFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultArticleWarrantyDetailsFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByWarrantyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where warrantyType equals to
        defaultArticleWarrantyDetailsFiltering(
            "warrantyType.equals=" + DEFAULT_WARRANTY_TYPE,
            "warrantyType.equals=" + UPDATED_WARRANTY_TYPE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByWarrantyTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where warrantyType in
        defaultArticleWarrantyDetailsFiltering(
            "warrantyType.in=" + DEFAULT_WARRANTY_TYPE + "," + UPDATED_WARRANTY_TYPE,
            "warrantyType.in=" + UPDATED_WARRANTY_TYPE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByWarrantyTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where warrantyType is not null
        defaultArticleWarrantyDetailsFiltering("warrantyType.specified=true", "warrantyType.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByVendorArticleWarrantyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where vendorArticleWarrantyId equals to
        defaultArticleWarrantyDetailsFiltering(
            "vendorArticleWarrantyId.equals=" + DEFAULT_VENDOR_ARTICLE_WARRANTY_ID,
            "vendorArticleWarrantyId.equals=" + UPDATED_VENDOR_ARTICLE_WARRANTY_ID
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByVendorArticleWarrantyIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where vendorArticleWarrantyId in
        defaultArticleWarrantyDetailsFiltering(
            "vendorArticleWarrantyId.in=" + DEFAULT_VENDOR_ARTICLE_WARRANTY_ID + "," + UPDATED_VENDOR_ARTICLE_WARRANTY_ID,
            "vendorArticleWarrantyId.in=" + UPDATED_VENDOR_ARTICLE_WARRANTY_ID
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByVendorArticleWarrantyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where vendorArticleWarrantyId is not null
        defaultArticleWarrantyDetailsFiltering("vendorArticleWarrantyId.specified=true", "vendorArticleWarrantyId.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByVendorArticleWarrantyIdContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where vendorArticleWarrantyId contains
        defaultArticleWarrantyDetailsFiltering(
            "vendorArticleWarrantyId.contains=" + DEFAULT_VENDOR_ARTICLE_WARRANTY_ID,
            "vendorArticleWarrantyId.contains=" + UPDATED_VENDOR_ARTICLE_WARRANTY_ID
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByVendorArticleWarrantyIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where vendorArticleWarrantyId does not contain
        defaultArticleWarrantyDetailsFiltering(
            "vendorArticleWarrantyId.doesNotContain=" + UPDATED_VENDOR_ARTICLE_WARRANTY_ID,
            "vendorArticleWarrantyId.doesNotContain=" + DEFAULT_VENDOR_ARTICLE_WARRANTY_ID
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByVendorWarrantyMasterIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where vendorWarrantyMasterId equals to
        defaultArticleWarrantyDetailsFiltering(
            "vendorWarrantyMasterId.equals=" + DEFAULT_VENDOR_WARRANTY_MASTER_ID,
            "vendorWarrantyMasterId.equals=" + UPDATED_VENDOR_WARRANTY_MASTER_ID
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByVendorWarrantyMasterIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where vendorWarrantyMasterId in
        defaultArticleWarrantyDetailsFiltering(
            "vendorWarrantyMasterId.in=" + DEFAULT_VENDOR_WARRANTY_MASTER_ID + "," + UPDATED_VENDOR_WARRANTY_MASTER_ID,
            "vendorWarrantyMasterId.in=" + UPDATED_VENDOR_WARRANTY_MASTER_ID
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByVendorWarrantyMasterIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where vendorWarrantyMasterId is not null
        defaultArticleWarrantyDetailsFiltering("vendorWarrantyMasterId.specified=true", "vendorWarrantyMasterId.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByVendorWarrantyMasterIdContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where vendorWarrantyMasterId contains
        defaultArticleWarrantyDetailsFiltering(
            "vendorWarrantyMasterId.contains=" + DEFAULT_VENDOR_WARRANTY_MASTER_ID,
            "vendorWarrantyMasterId.contains=" + UPDATED_VENDOR_WARRANTY_MASTER_ID
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByVendorWarrantyMasterIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where vendorWarrantyMasterId does not contain
        defaultArticleWarrantyDetailsFiltering(
            "vendorWarrantyMasterId.doesNotContain=" + UPDATED_VENDOR_WARRANTY_MASTER_ID,
            "vendorWarrantyMasterId.doesNotContain=" + DEFAULT_VENDOR_WARRANTY_MASTER_ID
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where startDate equals to
        defaultArticleWarrantyDetailsFiltering("startDate.equals=" + DEFAULT_START_DATE, "startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where startDate in
        defaultArticleWarrantyDetailsFiltering(
            "startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE,
            "startDate.in=" + UPDATED_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where startDate is not null
        defaultArticleWarrantyDetailsFiltering("startDate.specified=true", "startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where startDate is greater than or equal to
        defaultArticleWarrantyDetailsFiltering(
            "startDate.greaterThanOrEqual=" + DEFAULT_START_DATE,
            "startDate.greaterThanOrEqual=" + UPDATED_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where startDate is less than or equal to
        defaultArticleWarrantyDetailsFiltering(
            "startDate.lessThanOrEqual=" + DEFAULT_START_DATE,
            "startDate.lessThanOrEqual=" + SMALLER_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where startDate is less than
        defaultArticleWarrantyDetailsFiltering("startDate.lessThan=" + UPDATED_START_DATE, "startDate.lessThan=" + DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where startDate is greater than
        defaultArticleWarrantyDetailsFiltering(
            "startDate.greaterThan=" + SMALLER_START_DATE,
            "startDate.greaterThan=" + DEFAULT_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where endDate equals to
        defaultArticleWarrantyDetailsFiltering("endDate.equals=" + DEFAULT_END_DATE, "endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where endDate in
        defaultArticleWarrantyDetailsFiltering("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE, "endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where endDate is not null
        defaultArticleWarrantyDetailsFiltering("endDate.specified=true", "endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where endDate is greater than or equal to
        defaultArticleWarrantyDetailsFiltering(
            "endDate.greaterThanOrEqual=" + DEFAULT_END_DATE,
            "endDate.greaterThanOrEqual=" + UPDATED_END_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where endDate is less than or equal to
        defaultArticleWarrantyDetailsFiltering(
            "endDate.lessThanOrEqual=" + DEFAULT_END_DATE,
            "endDate.lessThanOrEqual=" + SMALLER_END_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where endDate is less than
        defaultArticleWarrantyDetailsFiltering("endDate.lessThan=" + UPDATED_END_DATE, "endDate.lessThan=" + DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where endDate is greater than
        defaultArticleWarrantyDetailsFiltering("endDate.greaterThan=" + SMALLER_END_DATE, "endDate.greaterThan=" + DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldBy equals to
        defaultArticleWarrantyDetailsFiltering("soldBy.equals=" + DEFAULT_SOLD_BY, "soldBy.equals=" + UPDATED_SOLD_BY);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldBy in
        defaultArticleWarrantyDetailsFiltering("soldBy.in=" + DEFAULT_SOLD_BY + "," + UPDATED_SOLD_BY, "soldBy.in=" + UPDATED_SOLD_BY);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldBy is not null
        defaultArticleWarrantyDetailsFiltering("soldBy.specified=true", "soldBy.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldByUser equals to
        defaultArticleWarrantyDetailsFiltering("soldByUser.equals=" + DEFAULT_SOLD_BY_USER, "soldByUser.equals=" + UPDATED_SOLD_BY_USER);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldByUserIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldByUser in
        defaultArticleWarrantyDetailsFiltering(
            "soldByUser.in=" + DEFAULT_SOLD_BY_USER + "," + UPDATED_SOLD_BY_USER,
            "soldByUser.in=" + UPDATED_SOLD_BY_USER
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldByUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldByUser is not null
        defaultArticleWarrantyDetailsFiltering("soldByUser.specified=true", "soldByUser.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldByUserContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldByUser contains
        defaultArticleWarrantyDetailsFiltering(
            "soldByUser.contains=" + DEFAULT_SOLD_BY_USER,
            "soldByUser.contains=" + UPDATED_SOLD_BY_USER
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldByUserNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldByUser does not contain
        defaultArticleWarrantyDetailsFiltering(
            "soldByUser.doesNotContain=" + UPDATED_SOLD_BY_USER,
            "soldByUser.doesNotContain=" + DEFAULT_SOLD_BY_USER
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldDate equals to
        defaultArticleWarrantyDetailsFiltering("soldDate.equals=" + DEFAULT_SOLD_DATE, "soldDate.equals=" + UPDATED_SOLD_DATE);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldDate in
        defaultArticleWarrantyDetailsFiltering(
            "soldDate.in=" + DEFAULT_SOLD_DATE + "," + UPDATED_SOLD_DATE,
            "soldDate.in=" + UPDATED_SOLD_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldDate is not null
        defaultArticleWarrantyDetailsFiltering("soldDate.specified=true", "soldDate.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldDate is greater than or equal to
        defaultArticleWarrantyDetailsFiltering(
            "soldDate.greaterThanOrEqual=" + DEFAULT_SOLD_DATE,
            "soldDate.greaterThanOrEqual=" + UPDATED_SOLD_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldDate is less than or equal to
        defaultArticleWarrantyDetailsFiltering(
            "soldDate.lessThanOrEqual=" + DEFAULT_SOLD_DATE,
            "soldDate.lessThanOrEqual=" + SMALLER_SOLD_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldDate is less than
        defaultArticleWarrantyDetailsFiltering("soldDate.lessThan=" + UPDATED_SOLD_DATE, "soldDate.lessThan=" + DEFAULT_SOLD_DATE);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsBySoldDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where soldDate is greater than
        defaultArticleWarrantyDetailsFiltering("soldDate.greaterThan=" + SMALLER_SOLD_DATE, "soldDate.greaterThan=" + DEFAULT_SOLD_DATE);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByIsValidatedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where isValidated equals to
        defaultArticleWarrantyDetailsFiltering("isValidated.equals=" + DEFAULT_IS_VALIDATED, "isValidated.equals=" + UPDATED_IS_VALIDATED);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByIsValidatedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where isValidated in
        defaultArticleWarrantyDetailsFiltering(
            "isValidated.in=" + DEFAULT_IS_VALIDATED + "," + UPDATED_IS_VALIDATED,
            "isValidated.in=" + UPDATED_IS_VALIDATED
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByIsValidatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where isValidated is not null
        defaultArticleWarrantyDetailsFiltering("isValidated.specified=true", "isValidated.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByValidatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where validatedBy equals to
        defaultArticleWarrantyDetailsFiltering("validatedBy.equals=" + DEFAULT_VALIDATED_BY, "validatedBy.equals=" + UPDATED_VALIDATED_BY);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByValidatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where validatedBy in
        defaultArticleWarrantyDetailsFiltering(
            "validatedBy.in=" + DEFAULT_VALIDATED_BY + "," + UPDATED_VALIDATED_BY,
            "validatedBy.in=" + UPDATED_VALIDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByValidatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where validatedBy is not null
        defaultArticleWarrantyDetailsFiltering("validatedBy.specified=true", "validatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByValidatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where validatedBy contains
        defaultArticleWarrantyDetailsFiltering(
            "validatedBy.contains=" + DEFAULT_VALIDATED_BY,
            "validatedBy.contains=" + UPDATED_VALIDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByValidatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where validatedBy does not contain
        defaultArticleWarrantyDetailsFiltering(
            "validatedBy.doesNotContain=" + UPDATED_VALIDATED_BY,
            "validatedBy.doesNotContain=" + DEFAULT_VALIDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByValidatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where validatedTime equals to
        defaultArticleWarrantyDetailsFiltering(
            "validatedTime.equals=" + DEFAULT_VALIDATED_TIME,
            "validatedTime.equals=" + UPDATED_VALIDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByValidatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where validatedTime in
        defaultArticleWarrantyDetailsFiltering(
            "validatedTime.in=" + DEFAULT_VALIDATED_TIME + "," + UPDATED_VALIDATED_TIME,
            "validatedTime.in=" + UPDATED_VALIDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByValidatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where validatedTime is not null
        defaultArticleWarrantyDetailsFiltering("validatedTime.specified=true", "validatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where isActive equals to
        defaultArticleWarrantyDetailsFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where isActive in
        defaultArticleWarrantyDetailsFiltering(
            "isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE,
            "isActive.in=" + UPDATED_IS_ACTIVE
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where isActive is not null
        defaultArticleWarrantyDetailsFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where createddBy equals to
        defaultArticleWarrantyDetailsFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where createddBy in
        defaultArticleWarrantyDetailsFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where createddBy is not null
        defaultArticleWarrantyDetailsFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where createddBy contains
        defaultArticleWarrantyDetailsFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where createddBy does not contain
        defaultArticleWarrantyDetailsFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where createdTime equals to
        defaultArticleWarrantyDetailsFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where createdTime in
        defaultArticleWarrantyDetailsFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where createdTime is not null
        defaultArticleWarrantyDetailsFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where updatedBy equals to
        defaultArticleWarrantyDetailsFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where updatedBy in
        defaultArticleWarrantyDetailsFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where updatedBy is not null
        defaultArticleWarrantyDetailsFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where updatedBy contains
        defaultArticleWarrantyDetailsFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where updatedBy does not contain
        defaultArticleWarrantyDetailsFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where updatedTime equals to
        defaultArticleWarrantyDetailsFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where updatedTime in
        defaultArticleWarrantyDetailsFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList where updatedTime is not null
        defaultArticleWarrantyDetailsFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByArticleIsEqualToSomething() throws Exception {
        Article article;
        if (TestUtil.findAll(em, Article.class).isEmpty()) {
            articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);
            article = ArticleResourceIT.createEntity();
        } else {
            article = TestUtil.findAll(em, Article.class).get(0);
        }
        em.persist(article);
        em.flush();
        articleWarrantyDetails.setArticle(article);
        articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);
        Long articleId = article.getId();
        // Get all the articleWarrantyDetailsList where article equals to articleId
        defaultArticleWarrantyDetailsShouldBeFound("articleId.equals=" + articleId);

        // Get all the articleWarrantyDetailsList where article equals to (articleId + 1)
        defaultArticleWarrantyDetailsShouldNotBeFound("articleId.equals=" + (articleId + 1));
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsByWarrantyMasterIsEqualToSomething() throws Exception {
        WarrantyMaster warrantyMaster;
        if (TestUtil.findAll(em, WarrantyMaster.class).isEmpty()) {
            articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);
            warrantyMaster = WarrantyMasterResourceIT.createEntity();
        } else {
            warrantyMaster = TestUtil.findAll(em, WarrantyMaster.class).get(0);
        }
        em.persist(warrantyMaster);
        em.flush();
        articleWarrantyDetails.setWarrantyMaster(warrantyMaster);
        articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);
        Long warrantyMasterId = warrantyMaster.getId();
        // Get all the articleWarrantyDetailsList where warrantyMaster equals to warrantyMasterId
        defaultArticleWarrantyDetailsShouldBeFound("warrantyMasterId.equals=" + warrantyMasterId);

        // Get all the articleWarrantyDetailsList where warrantyMaster equals to (warrantyMasterId + 1)
        defaultArticleWarrantyDetailsShouldNotBeFound("warrantyMasterId.equals=" + (warrantyMasterId + 1));
    }

    private void defaultArticleWarrantyDetailsFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultArticleWarrantyDetailsShouldBeFound(shouldBeFound);
        defaultArticleWarrantyDetailsShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultArticleWarrantyDetailsShouldBeFound(String filter) throws Exception {
        restArticleWarrantyDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleWarrantyDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].warrantyType").value(hasItem(DEFAULT_WARRANTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vendorArticleWarrantyId").value(hasItem(DEFAULT_VENDOR_ARTICLE_WARRANTY_ID)))
            .andExpect(jsonPath("$.[*].vendorWarrantyMasterId").value(hasItem(DEFAULT_VENDOR_WARRANTY_MASTER_ID)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].soldBy").value(hasItem(DEFAULT_SOLD_BY.toString())))
            .andExpect(jsonPath("$.[*].soldByUser").value(hasItem(DEFAULT_SOLD_BY_USER)))
            .andExpect(jsonPath("$.[*].soldDate").value(hasItem(DEFAULT_SOLD_DATE.toString())))
            .andExpect(jsonPath("$.[*].isValidated").value(hasItem(DEFAULT_IS_VALIDATED)))
            .andExpect(jsonPath("$.[*].validatedBy").value(hasItem(DEFAULT_VALIDATED_BY)))
            .andExpect(jsonPath("$.[*].validatedTime").value(hasItem(DEFAULT_VALIDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restArticleWarrantyDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultArticleWarrantyDetailsShouldNotBeFound(String filter) throws Exception {
        restArticleWarrantyDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restArticleWarrantyDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingArticleWarrantyDetails() throws Exception {
        // Get the articleWarrantyDetails
        restArticleWarrantyDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticleWarrantyDetails() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the articleWarrantyDetails
        ArticleWarrantyDetails updatedArticleWarrantyDetails = articleWarrantyDetailsRepository
            .findById(articleWarrantyDetails.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedArticleWarrantyDetails are not directly saved in db
        em.detach(updatedArticleWarrantyDetails);
        updatedArticleWarrantyDetails
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .vendorArticleWarrantyId(UPDATED_VENDOR_ARTICLE_WARRANTY_ID)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .soldBy(UPDATED_SOLD_BY)
            .soldByUser(UPDATED_SOLD_BY_USER)
            .soldDate(UPDATED_SOLD_DATE)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(updatedArticleWarrantyDetails);

        restArticleWarrantyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleWarrantyDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleWarrantyDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArticleWarrantyDetailsToMatchAllProperties(updatedArticleWarrantyDetails);
    }

    @Test
    @Transactional
    void putNonExistingArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // Create the ArticleWarrantyDetails
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleWarrantyDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleWarrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // Create the ArticleWarrantyDetails
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleWarrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // Create the ArticleWarrantyDetails
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleWarrantyDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the articleWarrantyDetails using partial update
        ArticleWarrantyDetails partialUpdatedArticleWarrantyDetails = new ArticleWarrantyDetails();
        partialUpdatedArticleWarrantyDetails.setId(articleWarrantyDetails.getId());

        partialUpdatedArticleWarrantyDetails
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .soldByUser(UPDATED_SOLD_BY_USER)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restArticleWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleWarrantyDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArticleWarrantyDetails))
            )
            .andExpect(status().isOk());

        // Validate the ArticleWarrantyDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArticleWarrantyDetailsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedArticleWarrantyDetails, articleWarrantyDetails),
            getPersistedArticleWarrantyDetails(articleWarrantyDetails)
        );
    }

    @Test
    @Transactional
    void fullUpdateArticleWarrantyDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the articleWarrantyDetails using partial update
        ArticleWarrantyDetails partialUpdatedArticleWarrantyDetails = new ArticleWarrantyDetails();
        partialUpdatedArticleWarrantyDetails.setId(articleWarrantyDetails.getId());

        partialUpdatedArticleWarrantyDetails
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .vendorArticleWarrantyId(UPDATED_VENDOR_ARTICLE_WARRANTY_ID)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .soldBy(UPDATED_SOLD_BY)
            .soldByUser(UPDATED_SOLD_BY_USER)
            .soldDate(UPDATED_SOLD_DATE)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restArticleWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleWarrantyDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArticleWarrantyDetails))
            )
            .andExpect(status().isOk());

        // Validate the ArticleWarrantyDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArticleWarrantyDetailsUpdatableFieldsEquals(
            partialUpdatedArticleWarrantyDetails,
            getPersistedArticleWarrantyDetails(partialUpdatedArticleWarrantyDetails)
        );
    }

    @Test
    @Transactional
    void patchNonExistingArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // Create the ArticleWarrantyDetails
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleWarrantyDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleWarrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // Create the ArticleWarrantyDetails
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleWarrantyDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // Create the ArticleWarrantyDetails
        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = articleWarrantyDetailsMapper.toDto(articleWarrantyDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(articleWarrantyDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticleWarrantyDetails() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the articleWarrantyDetails
        restArticleWarrantyDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, articleWarrantyDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return articleWarrantyDetailsRepository.count();
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

    protected ArticleWarrantyDetails getPersistedArticleWarrantyDetails(ArticleWarrantyDetails articleWarrantyDetails) {
        return articleWarrantyDetailsRepository.findById(articleWarrantyDetails.getId()).orElseThrow();
    }

    protected void assertPersistedArticleWarrantyDetailsToMatchAllProperties(ArticleWarrantyDetails expectedArticleWarrantyDetails) {
        assertArticleWarrantyDetailsAllPropertiesEquals(
            expectedArticleWarrantyDetails,
            getPersistedArticleWarrantyDetails(expectedArticleWarrantyDetails)
        );
    }

    protected void assertPersistedArticleWarrantyDetailsToMatchUpdatableProperties(ArticleWarrantyDetails expectedArticleWarrantyDetails) {
        assertArticleWarrantyDetailsAllUpdatablePropertiesEquals(
            expectedArticleWarrantyDetails,
            getPersistedArticleWarrantyDetails(expectedArticleWarrantyDetails)
        );
    }
}
