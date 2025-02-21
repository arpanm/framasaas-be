package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FranchiseUserAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FranchiseUser;
import com.framasaas.be.domain.enumeration.FranchiseUserStatus;
import com.framasaas.be.repository.FranchiseUserRepository;
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

    private static final String DEFAULT_EMAIL = "Mpo\".f@u}Y.gCV";
    private static final String UPDATED_EMAIL = "[jLHsh@!A.aDw~~";

    private static final Long DEFAULT_CONTACT = 1000000000L;
    private static final Long UPDATED_CONTACT = 1000000001L;

    private static final FranchiseUserStatus DEFAULT_USER_STATUS = FranchiseUserStatus.PendingApproval;
    private static final FranchiseUserStatus UPDATED_USER_STATUS = FranchiseUserStatus.Active;

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
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        franchiseUser = createEntity();
    }

    @AfterEach
    public void cleanup() {
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
        var returnedFranchiseUser = om.readValue(
            restFranchiseUserMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUser)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseUser.class
        );

        // Validate the FranchiseUser in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFranchiseUserUpdatableFieldsEquals(returnedFranchiseUser, getPersistedFranchiseUser(returnedFranchiseUser));

        insertedFranchiseUser = returnedFranchiseUser;
    }

    @Test
    @Transactional
    void createFranchiseUserWithExistingId() throws Exception {
        // Create the FranchiseUser with an existing ID
        franchiseUser.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUser)))
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

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUser)))
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

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUser)))
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

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUser)))
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

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUser)))
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

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUser)))
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

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUser)))
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

        restFranchiseUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUser)))
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
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
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
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFranchiseUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFranchiseUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFranchiseUser))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseUser))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseUser))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseUser)))
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

        partialUpdatedFranchiseUser.email(UPDATED_EMAIL).contact(UPDATED_CONTACT).createdTime(UPDATED_CREATED_TIME);

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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseUser))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseUser))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseUser)))
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
