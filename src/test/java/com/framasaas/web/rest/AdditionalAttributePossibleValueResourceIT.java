package com.framasaas.web.rest;

import static com.framasaas.domain.AdditionalAttributePossibleValueAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.AdditionalAttribute;
import com.framasaas.domain.AdditionalAttributePossibleValue;
import com.framasaas.repository.AdditionalAttributePossibleValueRepository;
import com.framasaas.service.dto.AdditionalAttributePossibleValueDTO;
import com.framasaas.service.mapper.AdditionalAttributePossibleValueMapper;
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
 * Integration tests for the {@link AdditionalAttributePossibleValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdditionalAttributePossibleValueResourceIT {

    private static final String DEFAULT_POSSIBLE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_POSSIBLE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/additional-attribute-possible-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository;

    @Autowired
    private AdditionalAttributePossibleValueMapper additionalAttributePossibleValueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdditionalAttributePossibleValueMockMvc;

    private AdditionalAttributePossibleValue additionalAttributePossibleValue;

    private AdditionalAttributePossibleValue insertedAdditionalAttributePossibleValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalAttributePossibleValue createEntity() {
        return new AdditionalAttributePossibleValue()
            .possibleValue(DEFAULT_POSSIBLE_VALUE)
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
    public static AdditionalAttributePossibleValue createUpdatedEntity() {
        return new AdditionalAttributePossibleValue()
            .possibleValue(UPDATED_POSSIBLE_VALUE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        additionalAttributePossibleValue = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAdditionalAttributePossibleValue != null) {
            additionalAttributePossibleValueRepository.delete(insertedAdditionalAttributePossibleValue);
            insertedAdditionalAttributePossibleValue = null;
        }
    }

    @Test
    @Transactional
    void createAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AdditionalAttributePossibleValue
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );
        var returnedAdditionalAttributePossibleValueDTO = om.readValue(
            restAdditionalAttributePossibleValueMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdditionalAttributePossibleValueDTO.class
        );

        // Validate the AdditionalAttributePossibleValue in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAdditionalAttributePossibleValue = additionalAttributePossibleValueMapper.toEntity(
            returnedAdditionalAttributePossibleValueDTO
        );
        assertAdditionalAttributePossibleValueUpdatableFieldsEquals(
            returnedAdditionalAttributePossibleValue,
            getPersistedAdditionalAttributePossibleValue(returnedAdditionalAttributePossibleValue)
        );

        insertedAdditionalAttributePossibleValue = returnedAdditionalAttributePossibleValue;
    }

    @Test
    @Transactional
    void createAdditionalAttributePossibleValueWithExistingId() throws Exception {
        // Create the AdditionalAttributePossibleValue with an existing ID
        additionalAttributePossibleValue.setId(1L);
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttributePossibleValue.setCreateddBy(null);

        // Create the AdditionalAttributePossibleValue, which fails.
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttributePossibleValue.setCreatedTime(null);

        // Create the AdditionalAttributePossibleValue, which fails.
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttributePossibleValue.setUpdatedBy(null);

        // Create the AdditionalAttributePossibleValue, which fails.
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        additionalAttributePossibleValue.setUpdatedTime(null);

        // Create the AdditionalAttributePossibleValue, which fails.
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValues() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList
        restAdditionalAttributePossibleValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalAttributePossibleValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].possibleValue").value(hasItem(DEFAULT_POSSIBLE_VALUE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getAdditionalAttributePossibleValue() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get the additionalAttributePossibleValue
        restAdditionalAttributePossibleValueMockMvc
            .perform(get(ENTITY_API_URL_ID, additionalAttributePossibleValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(additionalAttributePossibleValue.getId().intValue()))
            .andExpect(jsonPath("$.possibleValue").value(DEFAULT_POSSIBLE_VALUE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getAdditionalAttributePossibleValuesByIdFiltering() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        Long id = additionalAttributePossibleValue.getId();

        defaultAdditionalAttributePossibleValueFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAdditionalAttributePossibleValueFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAdditionalAttributePossibleValueFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByPossibleValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where possibleValue equals to
        defaultAdditionalAttributePossibleValueFiltering(
            "possibleValue.equals=" + DEFAULT_POSSIBLE_VALUE,
            "possibleValue.equals=" + UPDATED_POSSIBLE_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByPossibleValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where possibleValue in
        defaultAdditionalAttributePossibleValueFiltering(
            "possibleValue.in=" + DEFAULT_POSSIBLE_VALUE + "," + UPDATED_POSSIBLE_VALUE,
            "possibleValue.in=" + UPDATED_POSSIBLE_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByPossibleValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where possibleValue is not null
        defaultAdditionalAttributePossibleValueFiltering("possibleValue.specified=true", "possibleValue.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByPossibleValueContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where possibleValue contains
        defaultAdditionalAttributePossibleValueFiltering(
            "possibleValue.contains=" + DEFAULT_POSSIBLE_VALUE,
            "possibleValue.contains=" + UPDATED_POSSIBLE_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByPossibleValueNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where possibleValue does not contain
        defaultAdditionalAttributePossibleValueFiltering(
            "possibleValue.doesNotContain=" + UPDATED_POSSIBLE_VALUE,
            "possibleValue.doesNotContain=" + DEFAULT_POSSIBLE_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where createddBy equals to
        defaultAdditionalAttributePossibleValueFiltering(
            "createddBy.equals=" + DEFAULT_CREATEDD_BY,
            "createddBy.equals=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where createddBy in
        defaultAdditionalAttributePossibleValueFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where createddBy is not null
        defaultAdditionalAttributePossibleValueFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where createddBy contains
        defaultAdditionalAttributePossibleValueFiltering(
            "createddBy.contains=" + DEFAULT_CREATEDD_BY,
            "createddBy.contains=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where createddBy does not contain
        defaultAdditionalAttributePossibleValueFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where createdTime equals to
        defaultAdditionalAttributePossibleValueFiltering(
            "createdTime.equals=" + DEFAULT_CREATED_TIME,
            "createdTime.equals=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where createdTime in
        defaultAdditionalAttributePossibleValueFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where createdTime is not null
        defaultAdditionalAttributePossibleValueFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where updatedBy equals to
        defaultAdditionalAttributePossibleValueFiltering(
            "updatedBy.equals=" + DEFAULT_UPDATED_BY,
            "updatedBy.equals=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where updatedBy in
        defaultAdditionalAttributePossibleValueFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where updatedBy is not null
        defaultAdditionalAttributePossibleValueFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where updatedBy contains
        defaultAdditionalAttributePossibleValueFiltering(
            "updatedBy.contains=" + DEFAULT_UPDATED_BY,
            "updatedBy.contains=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where updatedBy does not contain
        defaultAdditionalAttributePossibleValueFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where updatedTime equals to
        defaultAdditionalAttributePossibleValueFiltering(
            "updatedTime.equals=" + DEFAULT_UPDATED_TIME,
            "updatedTime.equals=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where updatedTime in
        defaultAdditionalAttributePossibleValueFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        // Get all the additionalAttributePossibleValueList where updatedTime is not null
        defaultAdditionalAttributePossibleValueFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllAdditionalAttributePossibleValuesByAttributeIsEqualToSomething() throws Exception {
        AdditionalAttribute attribute;
        if (TestUtil.findAll(em, AdditionalAttribute.class).isEmpty()) {
            additionalAttributePossibleValueRepository.saveAndFlush(additionalAttributePossibleValue);
            attribute = AdditionalAttributeResourceIT.createEntity();
        } else {
            attribute = TestUtil.findAll(em, AdditionalAttribute.class).get(0);
        }
        em.persist(attribute);
        em.flush();
        additionalAttributePossibleValue.setAttribute(attribute);
        additionalAttributePossibleValueRepository.saveAndFlush(additionalAttributePossibleValue);
        Long attributeId = attribute.getId();
        // Get all the additionalAttributePossibleValueList where attribute equals to attributeId
        defaultAdditionalAttributePossibleValueShouldBeFound("attributeId.equals=" + attributeId);

        // Get all the additionalAttributePossibleValueList where attribute equals to (attributeId + 1)
        defaultAdditionalAttributePossibleValueShouldNotBeFound("attributeId.equals=" + (attributeId + 1));
    }

    private void defaultAdditionalAttributePossibleValueFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAdditionalAttributePossibleValueShouldBeFound(shouldBeFound);
        defaultAdditionalAttributePossibleValueShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdditionalAttributePossibleValueShouldBeFound(String filter) throws Exception {
        restAdditionalAttributePossibleValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalAttributePossibleValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].possibleValue").value(hasItem(DEFAULT_POSSIBLE_VALUE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restAdditionalAttributePossibleValueMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdditionalAttributePossibleValueShouldNotBeFound(String filter) throws Exception {
        restAdditionalAttributePossibleValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdditionalAttributePossibleValueMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAdditionalAttributePossibleValue() throws Exception {
        // Get the additionalAttributePossibleValue
        restAdditionalAttributePossibleValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdditionalAttributePossibleValue() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttributePossibleValue
        AdditionalAttributePossibleValue updatedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository
            .findById(additionalAttributePossibleValue.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAdditionalAttributePossibleValue are not directly saved in db
        em.detach(updatedAdditionalAttributePossibleValue);
        updatedAdditionalAttributePossibleValue
            .possibleValue(UPDATED_POSSIBLE_VALUE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            updatedAdditionalAttributePossibleValue
        );

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalAttributePossibleValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdditionalAttributePossibleValueToMatchAllProperties(updatedAdditionalAttributePossibleValue);
    }

    @Test
    @Transactional
    void putNonExistingAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // Create the AdditionalAttributePossibleValue
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalAttributePossibleValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // Create the AdditionalAttributePossibleValue
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // Create the AdditionalAttributePossibleValue
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdditionalAttributePossibleValueWithPatch() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttributePossibleValue using partial update
        AdditionalAttributePossibleValue partialUpdatedAdditionalAttributePossibleValue = new AdditionalAttributePossibleValue();
        partialUpdatedAdditionalAttributePossibleValue.setId(additionalAttributePossibleValue.getId());

        partialUpdatedAdditionalAttributePossibleValue
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY);

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalAttributePossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdditionalAttributePossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttributePossibleValue in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdditionalAttributePossibleValueUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdditionalAttributePossibleValue, additionalAttributePossibleValue),
            getPersistedAdditionalAttributePossibleValue(additionalAttributePossibleValue)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdditionalAttributePossibleValueWithPatch() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the additionalAttributePossibleValue using partial update
        AdditionalAttributePossibleValue partialUpdatedAdditionalAttributePossibleValue = new AdditionalAttributePossibleValue();
        partialUpdatedAdditionalAttributePossibleValue.setId(additionalAttributePossibleValue.getId());

        partialUpdatedAdditionalAttributePossibleValue
            .possibleValue(UPDATED_POSSIBLE_VALUE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAdditionalAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalAttributePossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdditionalAttributePossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalAttributePossibleValue in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdditionalAttributePossibleValueUpdatableFieldsEquals(
            partialUpdatedAdditionalAttributePossibleValue,
            getPersistedAdditionalAttributePossibleValue(partialUpdatedAdditionalAttributePossibleValue)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // Create the AdditionalAttributePossibleValue
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, additionalAttributePossibleValueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // Create the AdditionalAttributePossibleValue
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdditionalAttributePossibleValue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        additionalAttributePossibleValue.setId(longCount.incrementAndGet());

        // Create the AdditionalAttributePossibleValue
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = additionalAttributePossibleValueMapper.toDto(
            additionalAttributePossibleValue
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(additionalAttributePossibleValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalAttributePossibleValue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdditionalAttributePossibleValue() throws Exception {
        // Initialize the database
        insertedAdditionalAttributePossibleValue = additionalAttributePossibleValueRepository.saveAndFlush(
            additionalAttributePossibleValue
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the additionalAttributePossibleValue
        restAdditionalAttributePossibleValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, additionalAttributePossibleValue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return additionalAttributePossibleValueRepository.count();
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

    protected AdditionalAttributePossibleValue getPersistedAdditionalAttributePossibleValue(
        AdditionalAttributePossibleValue additionalAttributePossibleValue
    ) {
        return additionalAttributePossibleValueRepository.findById(additionalAttributePossibleValue.getId()).orElseThrow();
    }

    protected void assertPersistedAdditionalAttributePossibleValueToMatchAllProperties(
        AdditionalAttributePossibleValue expectedAdditionalAttributePossibleValue
    ) {
        assertAdditionalAttributePossibleValueAllPropertiesEquals(
            expectedAdditionalAttributePossibleValue,
            getPersistedAdditionalAttributePossibleValue(expectedAdditionalAttributePossibleValue)
        );
    }

    protected void assertPersistedAdditionalAttributePossibleValueToMatchUpdatableProperties(
        AdditionalAttributePossibleValue expectedAdditionalAttributePossibleValue
    ) {
        assertAdditionalAttributePossibleValueAllUpdatablePropertiesEquals(
            expectedAdditionalAttributePossibleValue,
            getPersistedAdditionalAttributePossibleValue(expectedAdditionalAttributePossibleValue)
        );
    }
}
