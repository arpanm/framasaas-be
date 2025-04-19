package com.framasaas.web.rest;

import static com.framasaas.domain.PincodeAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.domain.Pincode;
import com.framasaas.repository.PincodeRepository;
import com.framasaas.service.dto.PincodeDTO;
import com.framasaas.service.mapper.PincodeMapper;
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
 * Integration tests for the {@link PincodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PincodeResourceIT {

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/pincodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PincodeRepository pincodeRepository;

    @Autowired
    private PincodeMapper pincodeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPincodeMockMvc;

    private Pincode pincode;

    private Pincode insertedPincode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pincode createEntity() {
        return new Pincode()
            .pincode(DEFAULT_PINCODE)
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
    public static Pincode createUpdatedEntity() {
        return new Pincode()
            .pincode(UPDATED_PINCODE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        pincode = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPincode != null) {
            pincodeRepository.delete(insertedPincode);
            insertedPincode = null;
        }
    }

    @Test
    @Transactional
    void createPincode() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pincode
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);
        var returnedPincodeDTO = om.readValue(
            restPincodeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincodeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PincodeDTO.class
        );

        // Validate the Pincode in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPincode = pincodeMapper.toEntity(returnedPincodeDTO);
        assertPincodeUpdatableFieldsEquals(returnedPincode, getPersistedPincode(returnedPincode));

        insertedPincode = returnedPincode;
    }

    @Test
    @Transactional
    void createPincodeWithExistingId() throws Exception {
        // Create the Pincode with an existing ID
        pincode.setId(1L);
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPincodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pincode.setPincode(null);

        // Create the Pincode, which fails.
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincodeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pincode.setCreateddBy(null);

        // Create the Pincode, which fails.
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincodeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pincode.setCreatedTime(null);

        // Create the Pincode, which fails.
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincodeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pincode.setUpdatedBy(null);

        // Create the Pincode, which fails.
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincodeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pincode.setUpdatedTime(null);

        // Create the Pincode, which fails.
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        restPincodeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincodeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPincodes() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList
        restPincodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincode.getId().intValue())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getPincode() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get the pincode
        restPincodeMockMvc
            .perform(get(ENTITY_API_URL_ID, pincode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pincode.getId().intValue()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getPincodesByIdFiltering() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        Long id = pincode.getId();

        defaultPincodeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPincodeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPincodeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPincodesByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where pincode equals to
        defaultPincodeFiltering("pincode.equals=" + DEFAULT_PINCODE, "pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllPincodesByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where pincode in
        defaultPincodeFiltering("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE, "pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllPincodesByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where pincode is not null
        defaultPincodeFiltering("pincode.specified=true", "pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllPincodesByPincodeContainsSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where pincode contains
        defaultPincodeFiltering("pincode.contains=" + DEFAULT_PINCODE, "pincode.contains=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllPincodesByPincodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where pincode does not contain
        defaultPincodeFiltering("pincode.doesNotContain=" + UPDATED_PINCODE, "pincode.doesNotContain=" + DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    void getAllPincodesByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where createddBy equals to
        defaultPincodeFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllPincodesByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where createddBy in
        defaultPincodeFiltering("createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY, "createddBy.in=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllPincodesByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where createddBy is not null
        defaultPincodeFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPincodesByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where createddBy contains
        defaultPincodeFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllPincodesByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where createddBy does not contain
        defaultPincodeFiltering("createddBy.doesNotContain=" + UPDATED_CREATEDD_BY, "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllPincodesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where createdTime equals to
        defaultPincodeFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllPincodesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where createdTime in
        defaultPincodeFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllPincodesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where createdTime is not null
        defaultPincodeFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllPincodesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where updatedBy equals to
        defaultPincodeFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllPincodesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where updatedBy in
        defaultPincodeFiltering("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY, "updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllPincodesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where updatedBy is not null
        defaultPincodeFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPincodesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where updatedBy contains
        defaultPincodeFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllPincodesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where updatedBy does not contain
        defaultPincodeFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllPincodesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where updatedTime equals to
        defaultPincodeFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllPincodesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where updatedTime in
        defaultPincodeFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllPincodesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        // Get all the pincodeList where updatedTime is not null
        defaultPincodeFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllPincodesByFranchiseRuleIsEqualToSomething() throws Exception {
        FranchiseAllocationRule franchiseRule;
        if (TestUtil.findAll(em, FranchiseAllocationRule.class).isEmpty()) {
            pincodeRepository.saveAndFlush(pincode);
            franchiseRule = FranchiseAllocationRuleResourceIT.createEntity();
        } else {
            franchiseRule = TestUtil.findAll(em, FranchiseAllocationRule.class).get(0);
        }
        em.persist(franchiseRule);
        em.flush();
        pincode.setFranchiseRule(franchiseRule);
        pincodeRepository.saveAndFlush(pincode);
        Long franchiseRuleId = franchiseRule.getId();
        // Get all the pincodeList where franchiseRule equals to franchiseRuleId
        defaultPincodeShouldBeFound("franchiseRuleId.equals=" + franchiseRuleId);

        // Get all the pincodeList where franchiseRule equals to (franchiseRuleId + 1)
        defaultPincodeShouldNotBeFound("franchiseRuleId.equals=" + (franchiseRuleId + 1));
    }

    @Test
    @Transactional
    void getAllPincodesByFieldAgentSkillRuleIsEqualToSomething() throws Exception {
        FieldAgentSkillRule fieldAgentSkillRule;
        if (TestUtil.findAll(em, FieldAgentSkillRule.class).isEmpty()) {
            pincodeRepository.saveAndFlush(pincode);
            fieldAgentSkillRule = FieldAgentSkillRuleResourceIT.createEntity();
        } else {
            fieldAgentSkillRule = TestUtil.findAll(em, FieldAgentSkillRule.class).get(0);
        }
        em.persist(fieldAgentSkillRule);
        em.flush();
        pincode.setFieldAgentSkillRule(fieldAgentSkillRule);
        pincodeRepository.saveAndFlush(pincode);
        Long fieldAgentSkillRuleId = fieldAgentSkillRule.getId();
        // Get all the pincodeList where fieldAgentSkillRule equals to fieldAgentSkillRuleId
        defaultPincodeShouldBeFound("fieldAgentSkillRuleId.equals=" + fieldAgentSkillRuleId);

        // Get all the pincodeList where fieldAgentSkillRule equals to (fieldAgentSkillRuleId + 1)
        defaultPincodeShouldNotBeFound("fieldAgentSkillRuleId.equals=" + (fieldAgentSkillRuleId + 1));
    }

    private void defaultPincodeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPincodeShouldBeFound(shouldBeFound);
        defaultPincodeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPincodeShouldBeFound(String filter) throws Exception {
        restPincodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pincode.getId().intValue())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restPincodeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPincodeShouldNotBeFound(String filter) throws Exception {
        restPincodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPincodeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPincode() throws Exception {
        // Get the pincode
        restPincodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPincode() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pincode
        Pincode updatedPincode = pincodeRepository.findById(pincode.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPincode are not directly saved in db
        em.detach(updatedPincode);
        updatedPincode
            .pincode(UPDATED_PINCODE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        PincodeDTO pincodeDTO = pincodeMapper.toDto(updatedPincode);

        restPincodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pincodeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincodeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPincodeToMatchAllProperties(updatedPincode);
    }

    @Test
    @Transactional
    void putNonExistingPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // Create the Pincode
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pincodeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // Create the Pincode
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pincodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // Create the Pincode
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pincodeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePincodeWithPatch() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pincode using partial update
        Pincode partialUpdatedPincode = new Pincode();
        partialUpdatedPincode.setId(pincode.getId());

        partialUpdatedPincode
            .pincode(UPDATED_PINCODE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);

        restPincodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPincode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPincode))
            )
            .andExpect(status().isOk());

        // Validate the Pincode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPincodeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPincode, pincode), getPersistedPincode(pincode));
    }

    @Test
    @Transactional
    void fullUpdatePincodeWithPatch() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pincode using partial update
        Pincode partialUpdatedPincode = new Pincode();
        partialUpdatedPincode.setId(pincode.getId());

        partialUpdatedPincode
            .pincode(UPDATED_PINCODE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restPincodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPincode.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPincode))
            )
            .andExpect(status().isOk());

        // Validate the Pincode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPincodeUpdatableFieldsEquals(partialUpdatedPincode, getPersistedPincode(partialUpdatedPincode));
    }

    @Test
    @Transactional
    void patchNonExistingPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // Create the Pincode
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pincodeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pincodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // Create the Pincode
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pincodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPincode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pincode.setId(longCount.incrementAndGet());

        // Create the Pincode
        PincodeDTO pincodeDTO = pincodeMapper.toDto(pincode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPincodeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pincodeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pincode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePincode() throws Exception {
        // Initialize the database
        insertedPincode = pincodeRepository.saveAndFlush(pincode);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pincode
        restPincodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, pincode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pincodeRepository.count();
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

    protected Pincode getPersistedPincode(Pincode pincode) {
        return pincodeRepository.findById(pincode.getId()).orElseThrow();
    }

    protected void assertPersistedPincodeToMatchAllProperties(Pincode expectedPincode) {
        assertPincodeAllPropertiesEquals(expectedPincode, getPersistedPincode(expectedPincode));
    }

    protected void assertPersistedPincodeToMatchUpdatableProperties(Pincode expectedPincode) {
        assertPincodeAllUpdatablePropertiesEquals(expectedPincode, getPersistedPincode(expectedPincode));
    }
}
