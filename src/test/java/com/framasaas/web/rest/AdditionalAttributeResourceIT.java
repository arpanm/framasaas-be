package com.framasaas.web.rest;

import static com.framasaas.domain.AdditionalAttributeAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.AdditionalAttribute;
import com.framasaas.domain.Address;
import com.framasaas.domain.Article;
import com.framasaas.domain.ArticleWarrantyDetails;
import com.framasaas.domain.ArticleWarrantyDetailsDocument;
import com.framasaas.domain.Brand;
import com.framasaas.domain.Category;
import com.framasaas.domain.Customer;
import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FieldAgentSkillRuleSet;
import com.framasaas.domain.Franchise;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.domain.FranchiseAllocationRuleSet;
import com.framasaas.domain.FranchiseDocument;
import com.framasaas.domain.FranchisePerformanceHistory;
import com.framasaas.domain.FranchiseStatusHistory;
import com.framasaas.domain.FranchiseUser;
import com.framasaas.domain.Hsn;
import com.framasaas.domain.Inventory;
import com.framasaas.domain.InventoryLocation;
import com.framasaas.domain.LocationMapping;
import com.framasaas.domain.Product;
import com.framasaas.domain.ProductPriceHistory;
import com.framasaas.domain.ServiceOrder;
import com.framasaas.domain.ServiceOrderAssignment;
import com.framasaas.domain.ServiceOrderFieldAgentAssignment;
import com.framasaas.domain.ServiceOrderFranchiseAssignment;
import com.framasaas.domain.ServiceOrderPayment;
import com.framasaas.domain.SupportingDocument;
import com.framasaas.domain.WarrantyMaster;
import com.framasaas.domain.WarrantyMasterPriceHistory;
import com.framasaas.domain.enumeration.AttributeType;
import com.framasaas.repository.AdditionalAttributeRepository;
import com.framasaas.service.dto.AdditionalAttributeDTO;
import com.framasaas.service.mapper.AdditionalAttributeMapper;
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
 * Integration tests for the {@link AdditionalAttributeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdditionalAttributeResourceIT {

    private static final String DEFAULT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    private static final AttributeType DEFAULT_ATTRIBUTE_TYPE = AttributeType.ATTRSTRING;
    private static final AttributeType UPDATED_ATTRIBUTE_TYPE = AttributeType.ATTRNUMBER;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/additional-attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdditionalAttributeRepository additionalAttributeRepository;

    @Autowired
    private AdditionalAttributeMapper additionalAttributeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdditionalAttributeMockMvc;

    private AdditionalAttribute additionalAttribute;

    private AdditionalAttribute insertedAdditionalAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalAttribute createEntity() {
        return new AdditionalAttribute()
            .attributeName(DEFAULT_ATTRIBUTE_NAME)
            .attributeValue(DEFAULT_ATTRIBUTE_VALUE)
            .attributeType(DEFAULT_ATTRIBUTE_TYPE)
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
    public static AdditionalAttribute createUpdatedEntity() {
        return new AdditionalAttribute()
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .attributeType(UPDATED_ATTRIBUTE_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        additionalAttribute = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAdditionalAttribute != null) {
            additionalAttributeRepository.delete(insertedAdditionalAttribute);
            insertedAdditionalAttribute = null;
        }
    }

    @Test
    @Transactional
    void createAdditionalAttribute() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AdditionalAttribute
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);
        var returnedAdditionalAttributeDTO = om.readValue(
            restAdditionalAttributeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdditionalAttributeDTO.class
        );

        // Validate the AdditionalAttribute in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAdditionalAttribute = additionalAttributeMapper.toEntity(returnedAdditionalAttributeDTO);
        assertAdditionalAttributeUpdatableFieldsEquals(
            returnedAdditionalAttribute,
            getPersistedAdditionalAttribute(returnedAdditionalAttribute)
        );

        insertedAdditionalAttribute = returnedAdditionalAttribute;
    }

    @Test
    @Transactional
    void createAdditionalAttributeWithExistingId() throws Exception {
        // Create the AdditionalAttribute with an existing ID
        additionalAttribute.setId(1L);
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttributeNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setAttributeName(null);

        // Create the AdditionalAttribute, which fails.
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAttributeValueIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setAttributeValue(null);

        // Create the AdditionalAttribute, which fails.
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setCreateddBy(null);

        // Create the AdditionalAttribute, which fails.
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setCreatedTime(null);

        // Create the AdditionalAttribute, which fails.
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setUpdatedBy(null);

        // Create the AdditionalAttribute, which fails.
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttribute.setUpdatedTime(null);

        // Create the AdditionalAttribute, which fails.
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        restAdditionalAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributes() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList
        restAdditionalAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)))
            .andExpect(jsonPath("$.[*].attributeType").value(hasItem(DEFAULT_ATTRIBUTE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getAdditionalAttribute() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get the additionalAttribute
        restAdditionalAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, additionalAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(additionalAttribute.getId().intValue()))
            .andExpect(jsonPath("$.attributeName").value(DEFAULT_ATTRIBUTE_NAME))
            .andExpect(jsonPath("$.attributeValue").value(DEFAULT_ATTRIBUTE_VALUE))
            .andExpect(jsonPath("$.attributeType").value(DEFAULT_ATTRIBUTE_TYPE.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getAdditionalAttributesByIdFiltering() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        Long id = additionalAttribute.getId();

        defaultAdditionalAttributeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAdditionalAttributeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAdditionalAttributeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeName equals to
        defaultAdditionalAttributeFiltering(
            "attributeName.equals=" + DEFAULT_ATTRIBUTE_NAME,
            "attributeName.equals=" + UPDATED_ATTRIBUTE_NAME
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeName in
        defaultAdditionalAttributeFiltering(
            "attributeName.in=" + DEFAULT_ATTRIBUTE_NAME + "," + UPDATED_ATTRIBUTE_NAME,
            "attributeName.in=" + UPDATED_ATTRIBUTE_NAME
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeName is not null
        defaultAdditionalAttributeFiltering("attributeName.specified=true", "attributeName.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeNameContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeName contains
        defaultAdditionalAttributeFiltering(
            "attributeName.contains=" + DEFAULT_ATTRIBUTE_NAME,
            "attributeName.contains=" + UPDATED_ATTRIBUTE_NAME
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeName does not contain
        defaultAdditionalAttributeFiltering(
            "attributeName.doesNotContain=" + UPDATED_ATTRIBUTE_NAME,
            "attributeName.doesNotContain=" + DEFAULT_ATTRIBUTE_NAME
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeValue equals to
        defaultAdditionalAttributeFiltering(
            "attributeValue.equals=" + DEFAULT_ATTRIBUTE_VALUE,
            "attributeValue.equals=" + UPDATED_ATTRIBUTE_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeValue in
        defaultAdditionalAttributeFiltering(
            "attributeValue.in=" + DEFAULT_ATTRIBUTE_VALUE + "," + UPDATED_ATTRIBUTE_VALUE,
            "attributeValue.in=" + UPDATED_ATTRIBUTE_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeValue is not null
        defaultAdditionalAttributeFiltering("attributeValue.specified=true", "attributeValue.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeValueContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeValue contains
        defaultAdditionalAttributeFiltering(
            "attributeValue.contains=" + DEFAULT_ATTRIBUTE_VALUE,
            "attributeValue.contains=" + UPDATED_ATTRIBUTE_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeValueNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeValue does not contain
        defaultAdditionalAttributeFiltering(
            "attributeValue.doesNotContain=" + UPDATED_ATTRIBUTE_VALUE,
            "attributeValue.doesNotContain=" + DEFAULT_ATTRIBUTE_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeType equals to
        defaultAdditionalAttributeFiltering(
            "attributeType.equals=" + DEFAULT_ATTRIBUTE_TYPE,
            "attributeType.equals=" + UPDATED_ATTRIBUTE_TYPE
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeType in
        defaultAdditionalAttributeFiltering(
            "attributeType.in=" + DEFAULT_ATTRIBUTE_TYPE + "," + UPDATED_ATTRIBUTE_TYPE,
            "attributeType.in=" + UPDATED_ATTRIBUTE_TYPE
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAttributeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where attributeType is not null
        defaultAdditionalAttributeFiltering("attributeType.specified=true", "attributeType.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where createddBy equals to
        defaultAdditionalAttributeFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where createddBy in
        defaultAdditionalAttributeFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where createddBy is not null
        defaultAdditionalAttributeFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where createddBy contains
        defaultAdditionalAttributeFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where createddBy does not contain
        defaultAdditionalAttributeFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where createdTime equals to
        defaultAdditionalAttributeFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where createdTime in
        defaultAdditionalAttributeFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where createdTime is not null
        defaultAdditionalAttributeFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where updatedBy equals to
        defaultAdditionalAttributeFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where updatedBy in
        defaultAdditionalAttributeFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where updatedBy is not null
        defaultAdditionalAttributeFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where updatedBy contains
        defaultAdditionalAttributeFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where updatedBy does not contain
        defaultAdditionalAttributeFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where updatedTime equals to
        defaultAdditionalAttributeFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where updatedTime in
        defaultAdditionalAttributeFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        // Get all the additionalAttributeList where updatedTime is not null
        defaultAdditionalAttributeFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByFranchiseIsEqualToSomething() throws Exception {
        Franchise franchise;
        if (TestUtil.findAll(em, Franchise.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            franchise = FranchiseResourceIT.createEntity();
        } else {
            franchise = TestUtil.findAll(em, Franchise.class).get(0);
        }
        em.persist(franchise);
        em.flush();
        additionalAttribute.setFranchise(franchise);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long franchiseId = franchise.getId();
        // Get all the additionalAttributeList where franchise equals to franchiseId
        defaultAdditionalAttributeShouldBeFound("franchiseId.equals=" + franchiseId);

        // Get all the additionalAttributeList where franchise equals to (franchiseId + 1)
        defaultAdditionalAttributeShouldNotBeFound("franchiseId.equals=" + (franchiseId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByFranchiseStatusIsEqualToSomething() throws Exception {
        FranchiseStatusHistory franchiseStatus;
        if (TestUtil.findAll(em, FranchiseStatusHistory.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            franchiseStatus = FranchiseStatusHistoryResourceIT.createEntity();
        } else {
            franchiseStatus = TestUtil.findAll(em, FranchiseStatusHistory.class).get(0);
        }
        em.persist(franchiseStatus);
        em.flush();
        additionalAttribute.setFranchiseStatus(franchiseStatus);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long franchiseStatusId = franchiseStatus.getId();
        // Get all the additionalAttributeList where franchiseStatus equals to franchiseStatusId
        defaultAdditionalAttributeShouldBeFound("franchiseStatusId.equals=" + franchiseStatusId);

        // Get all the additionalAttributeList where franchiseStatus equals to (franchiseStatusId + 1)
        defaultAdditionalAttributeShouldNotBeFound("franchiseStatusId.equals=" + (franchiseStatusId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByFranchisePerformanceIsEqualToSomething() throws Exception {
        FranchisePerformanceHistory franchisePerformance;
        if (TestUtil.findAll(em, FranchisePerformanceHistory.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            franchisePerformance = FranchisePerformanceHistoryResourceIT.createEntity();
        } else {
            franchisePerformance = TestUtil.findAll(em, FranchisePerformanceHistory.class).get(0);
        }
        em.persist(franchisePerformance);
        em.flush();
        additionalAttribute.setFranchisePerformance(franchisePerformance);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long franchisePerformanceId = franchisePerformance.getId();
        // Get all the additionalAttributeList where franchisePerformance equals to franchisePerformanceId
        defaultAdditionalAttributeShouldBeFound("franchisePerformanceId.equals=" + franchisePerformanceId);

        // Get all the additionalAttributeList where franchisePerformance equals to (franchisePerformanceId + 1)
        defaultAdditionalAttributeShouldNotBeFound("franchisePerformanceId.equals=" + (franchisePerformanceId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByBrandIsEqualToSomething() throws Exception {
        Brand brand;
        if (TestUtil.findAll(em, Brand.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            brand = BrandResourceIT.createEntity();
        } else {
            brand = TestUtil.findAll(em, Brand.class).get(0);
        }
        em.persist(brand);
        em.flush();
        additionalAttribute.setBrand(brand);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long brandId = brand.getId();
        // Get all the additionalAttributeList where brand equals to brandId
        defaultAdditionalAttributeShouldBeFound("brandId.equals=" + brandId);

        // Get all the additionalAttributeList where brand equals to (brandId + 1)
        defaultAdditionalAttributeShouldNotBeFound("brandId.equals=" + (brandId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByCategoryIsEqualToSomething() throws Exception {
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            category = CategoryResourceIT.createEntity();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        additionalAttribute.setCategory(category);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long categoryId = category.getId();
        // Get all the additionalAttributeList where category equals to categoryId
        defaultAdditionalAttributeShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the additionalAttributeList where category equals to (categoryId + 1)
        defaultAdditionalAttributeShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByAddressIsEqualToSomething() throws Exception {
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            address = AddressResourceIT.createEntity();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        additionalAttribute.setAddress(address);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long addressId = address.getId();
        // Get all the additionalAttributeList where address equals to addressId
        defaultAdditionalAttributeShouldBeFound("addressId.equals=" + addressId);

        // Get all the additionalAttributeList where address equals to (addressId + 1)
        defaultAdditionalAttributeShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByLocationIsEqualToSomething() throws Exception {
        LocationMapping location;
        if (TestUtil.findAll(em, LocationMapping.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            location = LocationMappingResourceIT.createEntity();
        } else {
            location = TestUtil.findAll(em, LocationMapping.class).get(0);
        }
        em.persist(location);
        em.flush();
        additionalAttribute.setLocation(location);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long locationId = location.getId();
        // Get all the additionalAttributeList where location equals to locationId
        defaultAdditionalAttributeShouldBeFound("locationId.equals=" + locationId);

        // Get all the additionalAttributeList where location equals to (locationId + 1)
        defaultAdditionalAttributeShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByFranchiseUserIsEqualToSomething() throws Exception {
        FranchiseUser franchiseUser;
        if (TestUtil.findAll(em, FranchiseUser.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            franchiseUser = FranchiseUserResourceIT.createEntity();
        } else {
            franchiseUser = TestUtil.findAll(em, FranchiseUser.class).get(0);
        }
        em.persist(franchiseUser);
        em.flush();
        additionalAttribute.setFranchiseUser(franchiseUser);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long franchiseUserId = franchiseUser.getId();
        // Get all the additionalAttributeList where franchiseUser equals to franchiseUserId
        defaultAdditionalAttributeShouldBeFound("franchiseUserId.equals=" + franchiseUserId);

        // Get all the additionalAttributeList where franchiseUser equals to (franchiseUserId + 1)
        defaultAdditionalAttributeShouldNotBeFound("franchiseUserId.equals=" + (franchiseUserId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByCustomerIsEqualToSomething() throws Exception {
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            customer = CustomerResourceIT.createEntity();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        em.persist(customer);
        em.flush();
        additionalAttribute.setCustomer(customer);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long customerId = customer.getId();
        // Get all the additionalAttributeList where customer equals to customerId
        defaultAdditionalAttributeShouldBeFound("customerId.equals=" + customerId);

        // Get all the additionalAttributeList where customer equals to (customerId + 1)
        defaultAdditionalAttributeShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesBySupportDocumentIsEqualToSomething() throws Exception {
        SupportingDocument supportDocument;
        if (TestUtil.findAll(em, SupportingDocument.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            supportDocument = SupportingDocumentResourceIT.createEntity();
        } else {
            supportDocument = TestUtil.findAll(em, SupportingDocument.class).get(0);
        }
        em.persist(supportDocument);
        em.flush();
        additionalAttribute.setSupportDocument(supportDocument);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long supportDocumentId = supportDocument.getId();
        // Get all the additionalAttributeList where supportDocument equals to supportDocumentId
        defaultAdditionalAttributeShouldBeFound("supportDocumentId.equals=" + supportDocumentId);

        // Get all the additionalAttributeList where supportDocument equals to (supportDocumentId + 1)
        defaultAdditionalAttributeShouldNotBeFound("supportDocumentId.equals=" + (supportDocumentId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            product = ProductResourceIT.createEntity();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        additionalAttribute.setProduct(product);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long productId = product.getId();
        // Get all the additionalAttributeList where product equals to productId
        defaultAdditionalAttributeShouldBeFound("productId.equals=" + productId);

        // Get all the additionalAttributeList where product equals to (productId + 1)
        defaultAdditionalAttributeShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByHsnIsEqualToSomething() throws Exception {
        Hsn hsn;
        if (TestUtil.findAll(em, Hsn.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            hsn = HsnResourceIT.createEntity();
        } else {
            hsn = TestUtil.findAll(em, Hsn.class).get(0);
        }
        em.persist(hsn);
        em.flush();
        additionalAttribute.setHsn(hsn);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long hsnId = hsn.getId();
        // Get all the additionalAttributeList where hsn equals to hsnId
        defaultAdditionalAttributeShouldBeFound("hsnId.equals=" + hsnId);

        // Get all the additionalAttributeList where hsn equals to (hsnId + 1)
        defaultAdditionalAttributeShouldNotBeFound("hsnId.equals=" + (hsnId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByPriceHistoryIsEqualToSomething() throws Exception {
        ProductPriceHistory priceHistory;
        if (TestUtil.findAll(em, ProductPriceHistory.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            priceHistory = ProductPriceHistoryResourceIT.createEntity();
        } else {
            priceHistory = TestUtil.findAll(em, ProductPriceHistory.class).get(0);
        }
        em.persist(priceHistory);
        em.flush();
        additionalAttribute.setPriceHistory(priceHistory);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long priceHistoryId = priceHistory.getId();
        // Get all the additionalAttributeList where priceHistory equals to priceHistoryId
        defaultAdditionalAttributeShouldBeFound("priceHistoryId.equals=" + priceHistoryId);

        // Get all the additionalAttributeList where priceHistory equals to (priceHistoryId + 1)
        defaultAdditionalAttributeShouldNotBeFound("priceHistoryId.equals=" + (priceHistoryId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByWarrantyMasterIsEqualToSomething() throws Exception {
        WarrantyMaster warrantyMaster;
        if (TestUtil.findAll(em, WarrantyMaster.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            warrantyMaster = WarrantyMasterResourceIT.createEntity();
        } else {
            warrantyMaster = TestUtil.findAll(em, WarrantyMaster.class).get(0);
        }
        em.persist(warrantyMaster);
        em.flush();
        additionalAttribute.setWarrantyMaster(warrantyMaster);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long warrantyMasterId = warrantyMaster.getId();
        // Get all the additionalAttributeList where warrantyMaster equals to warrantyMasterId
        defaultAdditionalAttributeShouldBeFound("warrantyMasterId.equals=" + warrantyMasterId);

        // Get all the additionalAttributeList where warrantyMaster equals to (warrantyMasterId + 1)
        defaultAdditionalAttributeShouldNotBeFound("warrantyMasterId.equals=" + (warrantyMasterId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByWarrantyMasterPriceHistoryIsEqualToSomething() throws Exception {
        WarrantyMasterPriceHistory warrantyMasterPriceHistory;
        if (TestUtil.findAll(em, WarrantyMasterPriceHistory.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            warrantyMasterPriceHistory = WarrantyMasterPriceHistoryResourceIT.createEntity();
        } else {
            warrantyMasterPriceHistory = TestUtil.findAll(em, WarrantyMasterPriceHistory.class).get(0);
        }
        em.persist(warrantyMasterPriceHistory);
        em.flush();
        additionalAttribute.setWarrantyMasterPriceHistory(warrantyMasterPriceHistory);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long warrantyMasterPriceHistoryId = warrantyMasterPriceHistory.getId();
        // Get all the additionalAttributeList where warrantyMasterPriceHistory equals to warrantyMasterPriceHistoryId
        defaultAdditionalAttributeShouldBeFound("warrantyMasterPriceHistoryId.equals=" + warrantyMasterPriceHistoryId);

        // Get all the additionalAttributeList where warrantyMasterPriceHistory equals to (warrantyMasterPriceHistoryId + 1)
        defaultAdditionalAttributeShouldNotBeFound("warrantyMasterPriceHistoryId.equals=" + (warrantyMasterPriceHistoryId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByArticleIsEqualToSomething() throws Exception {
        Article article;
        if (TestUtil.findAll(em, Article.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            article = ArticleResourceIT.createEntity();
        } else {
            article = TestUtil.findAll(em, Article.class).get(0);
        }
        em.persist(article);
        em.flush();
        additionalAttribute.setArticle(article);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long articleId = article.getId();
        // Get all the additionalAttributeList where article equals to articleId
        defaultAdditionalAttributeShouldBeFound("articleId.equals=" + articleId);

        // Get all the additionalAttributeList where article equals to (articleId + 1)
        defaultAdditionalAttributeShouldNotBeFound("articleId.equals=" + (articleId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByArticleWarrantyIsEqualToSomething() throws Exception {
        ArticleWarrantyDetails articleWarranty;
        if (TestUtil.findAll(em, ArticleWarrantyDetails.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            articleWarranty = ArticleWarrantyDetailsResourceIT.createEntity();
        } else {
            articleWarranty = TestUtil.findAll(em, ArticleWarrantyDetails.class).get(0);
        }
        em.persist(articleWarranty);
        em.flush();
        additionalAttribute.setArticleWarranty(articleWarranty);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long articleWarrantyId = articleWarranty.getId();
        // Get all the additionalAttributeList where articleWarranty equals to articleWarrantyId
        defaultAdditionalAttributeShouldBeFound("articleWarrantyId.equals=" + articleWarrantyId);

        // Get all the additionalAttributeList where articleWarranty equals to (articleWarrantyId + 1)
        defaultAdditionalAttributeShouldNotBeFound("articleWarrantyId.equals=" + (articleWarrantyId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByServiceOrderIsEqualToSomething() throws Exception {
        ServiceOrder serviceOrder;
        if (TestUtil.findAll(em, ServiceOrder.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            serviceOrder = ServiceOrderResourceIT.createEntity();
        } else {
            serviceOrder = TestUtil.findAll(em, ServiceOrder.class).get(0);
        }
        em.persist(serviceOrder);
        em.flush();
        additionalAttribute.setServiceOrder(serviceOrder);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long serviceOrderId = serviceOrder.getId();
        // Get all the additionalAttributeList where serviceOrder equals to serviceOrderId
        defaultAdditionalAttributeShouldBeFound("serviceOrderId.equals=" + serviceOrderId);

        // Get all the additionalAttributeList where serviceOrder equals to (serviceOrderId + 1)
        defaultAdditionalAttributeShouldNotBeFound("serviceOrderId.equals=" + (serviceOrderId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByServiceOrderPaymentIsEqualToSomething() throws Exception {
        ServiceOrderPayment serviceOrderPayment;
        if (TestUtil.findAll(em, ServiceOrderPayment.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            serviceOrderPayment = ServiceOrderPaymentResourceIT.createEntity();
        } else {
            serviceOrderPayment = TestUtil.findAll(em, ServiceOrderPayment.class).get(0);
        }
        em.persist(serviceOrderPayment);
        em.flush();
        additionalAttribute.setServiceOrderPayment(serviceOrderPayment);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long serviceOrderPaymentId = serviceOrderPayment.getId();
        // Get all the additionalAttributeList where serviceOrderPayment equals to serviceOrderPaymentId
        defaultAdditionalAttributeShouldBeFound("serviceOrderPaymentId.equals=" + serviceOrderPaymentId);

        // Get all the additionalAttributeList where serviceOrderPayment equals to (serviceOrderPaymentId + 1)
        defaultAdditionalAttributeShouldNotBeFound("serviceOrderPaymentId.equals=" + (serviceOrderPaymentId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByServiceOrderFranchiseAssignmentIsEqualToSomething() throws Exception {
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment;
        if (TestUtil.findAll(em, ServiceOrderFranchiseAssignment.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            serviceOrderFranchiseAssignment = ServiceOrderFranchiseAssignmentResourceIT.createEntity();
        } else {
            serviceOrderFranchiseAssignment = TestUtil.findAll(em, ServiceOrderFranchiseAssignment.class).get(0);
        }
        em.persist(serviceOrderFranchiseAssignment);
        em.flush();
        additionalAttribute.setServiceOrderFranchiseAssignment(serviceOrderFranchiseAssignment);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long serviceOrderFranchiseAssignmentId = serviceOrderFranchiseAssignment.getId();
        // Get all the additionalAttributeList where serviceOrderFranchiseAssignment equals to serviceOrderFranchiseAssignmentId
        defaultAdditionalAttributeShouldBeFound("serviceOrderFranchiseAssignmentId.equals=" + serviceOrderFranchiseAssignmentId);

        // Get all the additionalAttributeList where serviceOrderFranchiseAssignment equals to (serviceOrderFranchiseAssignmentId + 1)
        defaultAdditionalAttributeShouldNotBeFound("serviceOrderFranchiseAssignmentId.equals=" + (serviceOrderFranchiseAssignmentId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByServiceOrderFieldAgentAssignmentIsEqualToSomething() throws Exception {
        ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment;
        if (TestUtil.findAll(em, ServiceOrderFieldAgentAssignment.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            serviceOrderFieldAgentAssignment = ServiceOrderFieldAgentAssignmentResourceIT.createEntity();
        } else {
            serviceOrderFieldAgentAssignment = TestUtil.findAll(em, ServiceOrderFieldAgentAssignment.class).get(0);
        }
        em.persist(serviceOrderFieldAgentAssignment);
        em.flush();
        additionalAttribute.setServiceOrderFieldAgentAssignment(serviceOrderFieldAgentAssignment);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long serviceOrderFieldAgentAssignmentId = serviceOrderFieldAgentAssignment.getId();
        // Get all the additionalAttributeList where serviceOrderFieldAgentAssignment equals to serviceOrderFieldAgentAssignmentId
        defaultAdditionalAttributeShouldBeFound("serviceOrderFieldAgentAssignmentId.equals=" + serviceOrderFieldAgentAssignmentId);

        // Get all the additionalAttributeList where serviceOrderFieldAgentAssignment equals to (serviceOrderFieldAgentAssignmentId + 1)
        defaultAdditionalAttributeShouldNotBeFound("serviceOrderFieldAgentAssignmentId.equals=" + (serviceOrderFieldAgentAssignmentId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByFranchiseAllocationRuleSetIsEqualToSomething() throws Exception {
        FranchiseAllocationRuleSet franchiseAllocationRuleSet;
        if (TestUtil.findAll(em, FranchiseAllocationRuleSet.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            franchiseAllocationRuleSet = FranchiseAllocationRuleSetResourceIT.createEntity();
        } else {
            franchiseAllocationRuleSet = TestUtil.findAll(em, FranchiseAllocationRuleSet.class).get(0);
        }
        em.persist(franchiseAllocationRuleSet);
        em.flush();
        additionalAttribute.setFranchiseAllocationRuleSet(franchiseAllocationRuleSet);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long franchiseAllocationRuleSetId = franchiseAllocationRuleSet.getId();
        // Get all the additionalAttributeList where franchiseAllocationRuleSet equals to franchiseAllocationRuleSetId
        defaultAdditionalAttributeShouldBeFound("franchiseAllocationRuleSetId.equals=" + franchiseAllocationRuleSetId);

        // Get all the additionalAttributeList where franchiseAllocationRuleSet equals to (franchiseAllocationRuleSetId + 1)
        defaultAdditionalAttributeShouldNotBeFound("franchiseAllocationRuleSetId.equals=" + (franchiseAllocationRuleSetId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByFranchiseAllocationRuleIsEqualToSomething() throws Exception {
        FranchiseAllocationRule franchiseAllocationRule;
        if (TestUtil.findAll(em, FranchiseAllocationRule.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            franchiseAllocationRule = FranchiseAllocationRuleResourceIT.createEntity();
        } else {
            franchiseAllocationRule = TestUtil.findAll(em, FranchiseAllocationRule.class).get(0);
        }
        em.persist(franchiseAllocationRule);
        em.flush();
        additionalAttribute.setFranchiseAllocationRule(franchiseAllocationRule);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long franchiseAllocationRuleId = franchiseAllocationRule.getId();
        // Get all the additionalAttributeList where franchiseAllocationRule equals to franchiseAllocationRuleId
        defaultAdditionalAttributeShouldBeFound("franchiseAllocationRuleId.equals=" + franchiseAllocationRuleId);

        // Get all the additionalAttributeList where franchiseAllocationRule equals to (franchiseAllocationRuleId + 1)
        defaultAdditionalAttributeShouldNotBeFound("franchiseAllocationRuleId.equals=" + (franchiseAllocationRuleId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByFieldAgentSkillRuleSetIsEqualToSomething() throws Exception {
        FieldAgentSkillRuleSet fieldAgentSkillRuleSet;
        if (TestUtil.findAll(em, FieldAgentSkillRuleSet.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            fieldAgentSkillRuleSet = FieldAgentSkillRuleSetResourceIT.createEntity();
        } else {
            fieldAgentSkillRuleSet = TestUtil.findAll(em, FieldAgentSkillRuleSet.class).get(0);
        }
        em.persist(fieldAgentSkillRuleSet);
        em.flush();
        additionalAttribute.setFieldAgentSkillRuleSet(fieldAgentSkillRuleSet);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long fieldAgentSkillRuleSetId = fieldAgentSkillRuleSet.getId();
        // Get all the additionalAttributeList where fieldAgentSkillRuleSet equals to fieldAgentSkillRuleSetId
        defaultAdditionalAttributeShouldBeFound("fieldAgentSkillRuleSetId.equals=" + fieldAgentSkillRuleSetId);

        // Get all the additionalAttributeList where fieldAgentSkillRuleSet equals to (fieldAgentSkillRuleSetId + 1)
        defaultAdditionalAttributeShouldNotBeFound("fieldAgentSkillRuleSetId.equals=" + (fieldAgentSkillRuleSetId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByFieldAgentSkillRuleIsEqualToSomething() throws Exception {
        FieldAgentSkillRule fieldAgentSkillRule;
        if (TestUtil.findAll(em, FieldAgentSkillRule.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            fieldAgentSkillRule = FieldAgentSkillRuleResourceIT.createEntity();
        } else {
            fieldAgentSkillRule = TestUtil.findAll(em, FieldAgentSkillRule.class).get(0);
        }
        em.persist(fieldAgentSkillRule);
        em.flush();
        additionalAttribute.setFieldAgentSkillRule(fieldAgentSkillRule);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long fieldAgentSkillRuleId = fieldAgentSkillRule.getId();
        // Get all the additionalAttributeList where fieldAgentSkillRule equals to fieldAgentSkillRuleId
        defaultAdditionalAttributeShouldBeFound("fieldAgentSkillRuleId.equals=" + fieldAgentSkillRuleId);

        // Get all the additionalAttributeList where fieldAgentSkillRule equals to (fieldAgentSkillRuleId + 1)
        defaultAdditionalAttributeShouldNotBeFound("fieldAgentSkillRuleId.equals=" + (fieldAgentSkillRuleId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByInventoryLocationIsEqualToSomething() throws Exception {
        InventoryLocation inventoryLocation;
        if (TestUtil.findAll(em, InventoryLocation.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            inventoryLocation = InventoryLocationResourceIT.createEntity();
        } else {
            inventoryLocation = TestUtil.findAll(em, InventoryLocation.class).get(0);
        }
        em.persist(inventoryLocation);
        em.flush();
        additionalAttribute.setInventoryLocation(inventoryLocation);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long inventoryLocationId = inventoryLocation.getId();
        // Get all the additionalAttributeList where inventoryLocation equals to inventoryLocationId
        defaultAdditionalAttributeShouldBeFound("inventoryLocationId.equals=" + inventoryLocationId);

        // Get all the additionalAttributeList where inventoryLocation equals to (inventoryLocationId + 1)
        defaultAdditionalAttributeShouldNotBeFound("inventoryLocationId.equals=" + (inventoryLocationId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByInventoryIsEqualToSomething() throws Exception {
        Inventory inventory;
        if (TestUtil.findAll(em, Inventory.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            inventory = InventoryResourceIT.createEntity();
        } else {
            inventory = TestUtil.findAll(em, Inventory.class).get(0);
        }
        em.persist(inventory);
        em.flush();
        additionalAttribute.setInventory(inventory);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long inventoryId = inventory.getId();
        // Get all the additionalAttributeList where inventory equals to inventoryId
        defaultAdditionalAttributeShouldBeFound("inventoryId.equals=" + inventoryId);

        // Get all the additionalAttributeList where inventory equals to (inventoryId + 1)
        defaultAdditionalAttributeShouldNotBeFound("inventoryId.equals=" + (inventoryId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByDocumentIsEqualToSomething() throws Exception {
        FranchiseDocument document;
        if (TestUtil.findAll(em, FranchiseDocument.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            document = FranchiseDocumentResourceIT.createEntity();
        } else {
            document = TestUtil.findAll(em, FranchiseDocument.class).get(0);
        }
        em.persist(document);
        em.flush();
        additionalAttribute.setDocument(document);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long documentId = document.getId();
        // Get all the additionalAttributeList where document equals to documentId
        defaultAdditionalAttributeShouldBeFound("documentId.equals=" + documentId);

        // Get all the additionalAttributeList where document equals to (documentId + 1)
        defaultAdditionalAttributeShouldNotBeFound("documentId.equals=" + (documentId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByArticleWarrantyDocumentIsEqualToSomething() throws Exception {
        ArticleWarrantyDetailsDocument articleWarrantyDocument;
        if (TestUtil.findAll(em, ArticleWarrantyDetailsDocument.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            articleWarrantyDocument = ArticleWarrantyDetailsDocumentResourceIT.createEntity();
        } else {
            articleWarrantyDocument = TestUtil.findAll(em, ArticleWarrantyDetailsDocument.class).get(0);
        }
        em.persist(articleWarrantyDocument);
        em.flush();
        additionalAttribute.setArticleWarrantyDocument(articleWarrantyDocument);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long articleWarrantyDocumentId = articleWarrantyDocument.getId();
        // Get all the additionalAttributeList where articleWarrantyDocument equals to articleWarrantyDocumentId
        defaultAdditionalAttributeShouldBeFound("articleWarrantyDocumentId.equals=" + articleWarrantyDocumentId);

        // Get all the additionalAttributeList where articleWarrantyDocument equals to (articleWarrantyDocumentId + 1)
        defaultAdditionalAttributeShouldNotBeFound("articleWarrantyDocumentId.equals=" + (articleWarrantyDocumentId + 1));
    }

    @Test
    @Transactional
    void getAllAdditionalAttributesByServiceOrderAssignmentIsEqualToSomething() throws Exception {
        ServiceOrderAssignment serviceOrderAssignment;
        if (TestUtil.findAll(em, ServiceOrderAssignment.class).isEmpty()) {
            additionalAttributeRepository.saveAndFlush(additionalAttribute);
            serviceOrderAssignment = ServiceOrderAssignmentResourceIT.createEntity();
        } else {
            serviceOrderAssignment = TestUtil.findAll(em, ServiceOrderAssignment.class).get(0);
        }
        em.persist(serviceOrderAssignment);
        em.flush();
        additionalAttribute.setServiceOrderAssignment(serviceOrderAssignment);
        additionalAttributeRepository.saveAndFlush(additionalAttribute);
        Long serviceOrderAssignmentId = serviceOrderAssignment.getId();
        // Get all the additionalAttributeList where serviceOrderAssignment equals to serviceOrderAssignmentId
        defaultAdditionalAttributeShouldBeFound("serviceOrderAssignmentId.equals=" + serviceOrderAssignmentId);

        // Get all the additionalAttributeList where serviceOrderAssignment equals to (serviceOrderAssignmentId + 1)
        defaultAdditionalAttributeShouldNotBeFound("serviceOrderAssignmentId.equals=" + (serviceOrderAssignmentId + 1));
    }

    private void defaultAdditionalAttributeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAdditionalAttributeShouldBeFound(shouldBeFound);
        defaultAdditionalAttributeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdditionalAttributeShouldBeFound(String filter) throws Exception {
        restAdditionalAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].attributeName").value(hasItem(DEFAULT_ATTRIBUTE_NAME)))
            .andExpect(jsonPath("$.[*].attributeValue").value(hasItem(DEFAULT_ATTRIBUTE_VALUE)))
            .andExpect(jsonPath("$.[*].attributeType").value(hasItem(DEFAULT_ATTRIBUTE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restAdditionalAttributeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdditionalAttributeShouldNotBeFound(String filter) throws Exception {
        restAdditionalAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdditionalAttributeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAdditionalAttribute() throws Exception {
        // Get the additionalAttribute
        restAdditionalAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdditionalAttribute() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttribute
        AdditionalAttribute updatedAdditionalAttribute = additionalAttributeRepository.findById(additionalAttribute.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdditionalAttribute are not directly saved in db
        em.detach(updatedAdditionalAttribute);
        updatedAdditionalAttribute
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .attributeType(UPDATED_ATTRIBUTE_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(updatedAdditionalAttribute);

        restAdditionalAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalAttributeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributeDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdditionalAttributeToMatchAllProperties(updatedAdditionalAttribute);
    }

    @Test
    @Transactional
    void putNonExistingAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // Create the AdditionalAttribute
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalAttributeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // Create the AdditionalAttribute
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // Create the AdditionalAttribute
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(additionalAttributeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdditionalAttributeWithPatch() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttribute using partial update
        AdditionalAttribute partialUpdatedAdditionalAttribute = new AdditionalAttribute();
        partialUpdatedAdditionalAttribute.setId(additionalAttribute.getId());

        partialUpdatedAdditionalAttribute
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeType(UPDATED_ATTRIBUTE_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME);

        restAdditionalAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdditionalAttribute))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttribute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdditionalAttributeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdditionalAttribute, additionalAttribute),
            getPersistedAdditionalAttribute(additionalAttribute)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdditionalAttributeWithPatch() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttribute using partial update
        AdditionalAttribute partialUpdatedAdditionalAttribute = new AdditionalAttribute();
        partialUpdatedAdditionalAttribute.setId(additionalAttribute.getId());

        partialUpdatedAdditionalAttribute
            .attributeName(UPDATED_ATTRIBUTE_NAME)
            .attributeValue(UPDATED_ATTRIBUTE_VALUE)
            .attributeType(UPDATED_ATTRIBUTE_TYPE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAdditionalAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdditionalAttribute))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttribute in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdditionalAttributeUpdatableFieldsEquals(
            partialUpdatedAdditionalAttribute,
            getPersistedAdditionalAttribute(partialUpdatedAdditionalAttribute)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // Create the AdditionalAttribute
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, additionalAttributeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(additionalAttributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // Create the AdditionalAttribute
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(additionalAttributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdditionalAttribute() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttribute.setId(longCount.incrementAndGet());

        // Create the AdditionalAttribute
        AdditionalAttributeDTO additionalAttributeDTO = additionalAttributeMapper.toDto(additionalAttribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(additionalAttributeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalAttribute in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdditionalAttribute() throws Exception {
        // Initialize the database
        insertedAdditionalAttribute = additionalAttributeRepository.saveAndFlush(additionalAttribute);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the additionalAttribute
        restAdditionalAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, additionalAttribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return additionalAttributeRepository.count();
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

    protected AdditionalAttribute getPersistedAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        return additionalAttributeRepository.findById(additionalAttribute.getId()).orElseThrow();
    }

    protected void assertPersistedAdditionalAttributeToMatchAllProperties(AdditionalAttribute expectedAdditionalAttribute) {
        assertAdditionalAttributeAllPropertiesEquals(
            expectedAdditionalAttribute,
            getPersistedAdditionalAttribute(expectedAdditionalAttribute)
        );
    }

    protected void assertPersistedAdditionalAttributeToMatchUpdatableProperties(AdditionalAttribute expectedAdditionalAttribute) {
        assertAdditionalAttributeAllUpdatablePropertiesEquals(
            expectedAdditionalAttribute,
            getPersistedAdditionalAttribute(expectedAdditionalAttribute)
        );
    }
}
