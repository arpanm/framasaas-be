package com.framasaas.web.rest;

import static com.framasaas.domain.FranchiseUserAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.FieldAgentSkillRuleSet;
import com.framasaas.domain.Franchise;
import com.framasaas.domain.FranchiseUser;
import com.framasaas.domain.enumeration.FranchiseUserRole;
import com.framasaas.domain.enumeration.FranchiseUserStatus;
import com.framasaas.repository.FranchiseUserRepository;
import com.framasaas.service.dto.FranchiseUserDTO;
import com.framasaas.service.mapper.FranchiseUserMapper;
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
 * Integration tests for the {@link FranchiseUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseUserResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "Ky@(g.G0Mb9c";
    private static final String UPDATED_EMAIL = "ZHE>/@Ri.lw72SA";

    private static final Long DEFAULT_CONTACT = 1000000000L;
    private static final Long UPDATED_CONTACT = 1000000001L;
    private static final Long SMALLER_CONTACT = 1000000000L - 1L;

    private static final FranchiseUserStatus DEFAULT_USER_STATUS = FranchiseUserStatus.PendingApproval;
    private static final FranchiseUserStatus UPDATED_USER_STATUS = FranchiseUserStatus.Active;

    private static final FranchiseUserRole DEFAULT_USER_ROLE = FranchiseUserRole.FranchiseAdmin;
    private static final FranchiseUserRole UPDATED_USER_ROLE = FranchiseUserRole.FinanceUser;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/franchise-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseUserRepository franchiseUserRepository;

    @Autowired
    private FranchiseUserMapper franchiseUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseUserMockMvc;

    private FranchiseUser franchiseUser;

    private FranchiseUser insertedFranchiseUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseUser createEntity() {
        return new FranchiseUser()
            .userName(DEFAULT_USER_NAME)
            .email(DEFAULT_EMAIL)
            .contact(DEFAULT_CONTACT)
            .userStatus(DEFAULT_USER_STATUS)
            .userRole(DEFAULT_USER_ROLE)
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
    public static FranchiseUser createUpdatedEntity() {
        return new FranchiseUser()
            .userName(UPDATED_USER_NAME)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .userStatus(UPDATED_USER_STATUS)
            .userRole(UPDATED_USER_ROLE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        franchiseUser = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFranchiseUser != null) {
            franchiseUserRepository.delete(insertedFranchiseUser);
            insertedFranchiseUser = null;
        }
    }

    @Test
    @Transactional
    void createFranchiseUser() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchiseUser
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);
        var returnedFranchiseUserDTO = om.readValue(
            restFranchiseUserMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseUserDTO.class
        );

        // Validate the FranchiseUser in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFranchiseUser = franchiseUserMapper.toEntity(returnedFranchiseUserDTO);
        assertFranchiseUserUpdatableFieldsEquals(returnedFranchiseUser, getPersistedFranchiseUser(returnedFranchiseUser));

        insertedFranchiseUser = returnedFranchiseUser;
    }

    @Test
    @Transactional
    void createFranchiseUserWithExistingId() throws Exception {
        // Create the FranchiseUser with an existing ID
        franchiseUser.setId(1L);
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FranchiseUser in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseUser.setUserName(null);

        // Create the FranchiseUser, which fails.
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseUser.setEmail(null);

        // Create the FranchiseUser, which fails.
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseUser.setContact(null);

        // Create the FranchiseUser, which fails.
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseUser.setCreateddBy(null);

        // Create the FranchiseUser, which fails.
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseUser.setCreatedTime(null);

        // Create the FranchiseUser, which fails.
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseUser.setUpdatedBy(null);

        // Create the FranchiseUser, which fails.
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseUser.setUpdatedTime(null);

        // Create the FranchiseUser, which fails.
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchiseUsers() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList
        restFranchiseUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.intValue())))
            .andExpect(jsonPath("$.[*].userStatus").value(hasItem(DEFAULT_USER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].userRole").value(hasItem(DEFAULT_USER_ROLE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFranchiseUser() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get the franchiseUser
        restFranchiseUserMockMvc
            .perform(get(ENTITY_API_URL_ID, franchiseUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchiseUser.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.intValue()))
            .andExpect(jsonPath("$.userStatus").value(DEFAULT_USER_STATUS.toString()))
            .andExpect(jsonPath("$.userRole").value(DEFAULT_USER_ROLE.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getFranchiseUsersByIdFiltering() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        Long id = franchiseUser.getId();

        defaultFranchiseUserFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFranchiseUserFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFranchiseUserFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userName equals to
        defaultFranchiseUserFiltering("userName.equals=" + DEFAULT_USER_NAME, "userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userName in
        defaultFranchiseUserFiltering("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME, "userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userName is not null
        defaultFranchiseUserFiltering("userName.specified=true", "userName.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserNameContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userName contains
        defaultFranchiseUserFiltering("userName.contains=" + DEFAULT_USER_NAME, "userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userName does not contain
        defaultFranchiseUserFiltering("userName.doesNotContain=" + UPDATED_USER_NAME, "userName.doesNotContain=" + DEFAULT_USER_NAME);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where email equals to
        defaultFranchiseUserFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where email in
        defaultFranchiseUserFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where email is not null
        defaultFranchiseUserFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where email contains
        defaultFranchiseUserFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where email does not contain
        defaultFranchiseUserFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where contact equals to
        defaultFranchiseUserFiltering("contact.equals=" + DEFAULT_CONTACT, "contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByContactIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where contact in
        defaultFranchiseUserFiltering("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT, "contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where contact is not null
        defaultFranchiseUserFiltering("contact.specified=true", "contact.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByContactIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where contact is greater than or equal to
        defaultFranchiseUserFiltering(
            "contact.greaterThanOrEqual=" + DEFAULT_CONTACT,
            "contact.greaterThanOrEqual=" + (DEFAULT_CONTACT + 1)
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByContactIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where contact is less than or equal to
        defaultFranchiseUserFiltering("contact.lessThanOrEqual=" + DEFAULT_CONTACT, "contact.lessThanOrEqual=" + SMALLER_CONTACT);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByContactIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where contact is less than
        defaultFranchiseUserFiltering("contact.lessThan=" + (DEFAULT_CONTACT + 1), "contact.lessThan=" + DEFAULT_CONTACT);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByContactIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where contact is greater than
        defaultFranchiseUserFiltering("contact.greaterThan=" + SMALLER_CONTACT, "contact.greaterThan=" + DEFAULT_CONTACT);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userStatus equals to
        defaultFranchiseUserFiltering("userStatus.equals=" + DEFAULT_USER_STATUS, "userStatus.equals=" + UPDATED_USER_STATUS);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userStatus in
        defaultFranchiseUserFiltering(
            "userStatus.in=" + DEFAULT_USER_STATUS + "," + UPDATED_USER_STATUS,
            "userStatus.in=" + UPDATED_USER_STATUS
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userStatus is not null
        defaultFranchiseUserFiltering("userStatus.specified=true", "userStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userRole equals to
        defaultFranchiseUserFiltering("userRole.equals=" + DEFAULT_USER_ROLE, "userRole.equals=" + UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserRoleIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userRole in
        defaultFranchiseUserFiltering("userRole.in=" + DEFAULT_USER_ROLE + "," + UPDATED_USER_ROLE, "userRole.in=" + UPDATED_USER_ROLE);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUserRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where userRole is not null
        defaultFranchiseUserFiltering("userRole.specified=true", "userRole.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where createddBy equals to
        defaultFranchiseUserFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where createddBy in
        defaultFranchiseUserFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where createddBy is not null
        defaultFranchiseUserFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where createddBy contains
        defaultFranchiseUserFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where createddBy does not contain
        defaultFranchiseUserFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where createdTime equals to
        defaultFranchiseUserFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where createdTime in
        defaultFranchiseUserFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where createdTime is not null
        defaultFranchiseUserFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where updatedBy equals to
        defaultFranchiseUserFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where updatedBy in
        defaultFranchiseUserFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where updatedBy is not null
        defaultFranchiseUserFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where updatedBy contains
        defaultFranchiseUserFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where updatedBy does not contain
        defaultFranchiseUserFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where updatedTime equals to
        defaultFranchiseUserFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where updatedTime in
        defaultFranchiseUserFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        // Get all the franchiseUserList where updatedTime is not null
        defaultFranchiseUserFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllFranchiseUsersByFranchiseIsEqualToSomething() throws Exception {
        Franchise franchise;
        if (TestUtil.findAll(em, Franchise.class).isEmpty()) {
            franchiseUserRepository.saveAndFlush(franchiseUser);
            franchise = FranchiseResourceIT.createEntity();
        } else {
            franchise = TestUtil.findAll(em, Franchise.class).get(0);
        }
        em.persist(franchise);
        em.flush();
        franchiseUser.setFranchise(franchise);
        franchiseUserRepository.saveAndFlush(franchiseUser);
        Long franchiseId = franchise.getId();
        // Get all the franchiseUserList where franchise equals to franchiseId
        defaultFranchiseUserShouldBeFound("franchiseId.equals=" + franchiseId);

        // Get all the franchiseUserList where franchise equals to (franchiseId + 1)
        defaultFranchiseUserShouldNotBeFound("franchiseId.equals=" + (franchiseId + 1));
    }

    @Test
    @Transactional
    void getAllFranchiseUsersBySkillRuleSetIsEqualToSomething() throws Exception {
        FieldAgentSkillRuleSet skillRuleSet;
        if (TestUtil.findAll(em, FieldAgentSkillRuleSet.class).isEmpty()) {
            franchiseUserRepository.saveAndFlush(franchiseUser);
            skillRuleSet = FieldAgentSkillRuleSetResourceIT.createEntity();
        } else {
            skillRuleSet = TestUtil.findAll(em, FieldAgentSkillRuleSet.class).get(0);
        }
        em.persist(skillRuleSet);
        em.flush();
        franchiseUser.setSkillRuleSet(skillRuleSet);
        franchiseUserRepository.saveAndFlush(franchiseUser);
        Long skillRuleSetId = skillRuleSet.getId();
        // Get all the franchiseUserList where skillRuleSet equals to skillRuleSetId
        defaultFranchiseUserShouldBeFound("skillRuleSetId.equals=" + skillRuleSetId);

        // Get all the franchiseUserList where skillRuleSet equals to (skillRuleSetId + 1)
        defaultFranchiseUserShouldNotBeFound("skillRuleSetId.equals=" + (skillRuleSetId + 1));
    }

    private void defaultFranchiseUserFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFranchiseUserShouldBeFound(shouldBeFound);
        defaultFranchiseUserShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFranchiseUserShouldBeFound(String filter) throws Exception {
        restFranchiseUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.intValue())))
            .andExpect(jsonPath("$.[*].userStatus").value(hasItem(DEFAULT_USER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].userRole").value(hasItem(DEFAULT_USER_ROLE.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restFranchiseUserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFranchiseUserShouldNotBeFound(String filter) throws Exception {
        restFranchiseUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFranchiseUserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFranchiseUser() throws Exception {
        // Get the franchiseUser
        restFranchiseUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchiseUser() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseUser
        FranchiseUser updatedFranchiseUser = franchiseUserRepository.findById(franchiseUser.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFranchiseUser are not directly saved in db
        em.detach(updatedFranchiseUser);
        updatedFranchiseUser
            .userName(UPDATED_USER_NAME)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .userStatus(UPDATED_USER_STATUS)
            .userRole(UPDATED_USER_ROLE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(updatedFranchiseUser);

        restFranchiseUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseUserToMatchAllProperties(updatedFranchiseUser);
    }

    @Test
    @Transactional
    void putNonExistingFranchiseUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUser.setId(longCount.incrementAndGet());

        // Create the FranchiseUser
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchiseUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUser.setId(longCount.incrementAndGet());

        // Create the FranchiseUser
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchiseUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUser.setId(longCount.incrementAndGet());

        // Create the FranchiseUser
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseUserWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseUser using partial update
        FranchiseUser partialUpdatedFranchiseUser = new FranchiseUser();
        partialUpdatedFranchiseUser.setId(franchiseUser.getId());

        partialUpdatedFranchiseUser.email(UPDATED_EMAIL).contact(UPDATED_CONTACT).userRole(UPDATED_USER_ROLE).updatedBy(UPDATED_UPDATED_BY);

        restFranchiseUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseUser))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseUserUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchiseUser, franchiseUser),
            getPersistedFranchiseUser(franchiseUser)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseUserWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseUser using partial update
        FranchiseUser partialUpdatedFranchiseUser = new FranchiseUser();
        partialUpdatedFranchiseUser.setId(franchiseUser.getId());

        partialUpdatedFranchiseUser
            .userName(UPDATED_USER_NAME)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .userStatus(UPDATED_USER_STATUS)
            .userRole(UPDATED_USER_ROLE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseUser))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseUser in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseUserUpdatableFieldsEquals(partialUpdatedFranchiseUser, getPersistedFranchiseUser(partialUpdatedFranchiseUser));
    }

    @Test
    @Transactional
    void patchNonExistingFranchiseUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUser.setId(longCount.incrementAndGet());

        // Create the FranchiseUser
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchiseUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUser.setId(longCount.incrementAndGet());

        // Create the FranchiseUser
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchiseUser() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseUser.setId(longCount.incrementAndGet());

        // Create the FranchiseUser
        FranchiseUserDTO franchiseUserDTO = franchiseUserMapper.toDto(franchiseUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseUser in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchiseUser() throws Exception {
        // Initialize the database
        insertedFranchiseUser = franchiseUserRepository.saveAndFlush(franchiseUser);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchiseUser
        restFranchiseUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchiseUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseUserRepository.count();
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

    protected FranchiseUser getPersistedFranchiseUser(FranchiseUser franchiseUser) {
        return franchiseUserRepository.findById(franchiseUser.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseUserToMatchAllProperties(FranchiseUser expectedFranchiseUser) {
        assertFranchiseUserAllPropertiesEquals(expectedFranchiseUser, getPersistedFranchiseUser(expectedFranchiseUser));
    }

    protected void assertPersistedFranchiseUserToMatchUpdatableProperties(FranchiseUser expectedFranchiseUser) {
        assertFranchiseUserAllUpdatablePropertiesEquals(expectedFranchiseUser, getPersistedFranchiseUser(expectedFranchiseUser));
    }
}
