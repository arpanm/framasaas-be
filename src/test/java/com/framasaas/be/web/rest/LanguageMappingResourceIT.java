package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.LanguageMappingAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.LanguageMapping;
import com.framasaas.be.domain.enumeration.Language;
import com.framasaas.be.repository.LanguageMappingRepository;
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
 * Integration tests for the {@link LanguageMappingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LanguageMappingResourceIT {

    private static final Language DEFAULT_LANG = Language.English;
    private static final Language UPDATED_LANG = Language.Hindi;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/language-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LanguageMappingRepository languageMappingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLanguageMappingMockMvc;

    private LanguageMapping languageMapping;

    private LanguageMapping insertedLanguageMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LanguageMapping createEntity() {
        return new LanguageMapping()
            .lang(DEFAULT_LANG)
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
    public static LanguageMapping createUpdatedEntity() {
        return new LanguageMapping()
            .lang(UPDATED_LANG)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        languageMapping = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedLanguageMapping != null) {
            languageMappingRepository.delete(insertedLanguageMapping);
            insertedLanguageMapping = null;
        }
    }

    @Test
    @Transactional
    void createLanguageMapping() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LanguageMapping
        var returnedLanguageMapping = om.readValue(
            restLanguageMappingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(languageMapping)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LanguageMapping.class
        );

        // Validate the LanguageMapping in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLanguageMappingUpdatableFieldsEquals(returnedLanguageMapping, getPersistedLanguageMapping(returnedLanguageMapping));

        insertedLanguageMapping = returnedLanguageMapping;
    }

    @Test
    @Transactional
    void createLanguageMappingWithExistingId() throws Exception {
        // Create the LanguageMapping with an existing ID
        languageMapping.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLanguageMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(languageMapping)))
            .andExpect(status().isBadRequest());

        // Validate the LanguageMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLangIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        languageMapping.setLang(null);

        // Create the LanguageMapping, which fails.

        restLanguageMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(languageMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        languageMapping.setCreateddBy(null);

        // Create the LanguageMapping, which fails.

        restLanguageMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(languageMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        languageMapping.setCreatedTime(null);

        // Create the LanguageMapping, which fails.

        restLanguageMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(languageMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        languageMapping.setUpdatedBy(null);

        // Create the LanguageMapping, which fails.

        restLanguageMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(languageMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        languageMapping.setUpdatedTime(null);

        // Create the LanguageMapping, which fails.

        restLanguageMappingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(languageMapping)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLanguageMappings() throws Exception {
        // Initialize the database
        insertedLanguageMapping = languageMappingRepository.saveAndFlush(languageMapping);

        // Get all the languageMappingList
        restLanguageMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(languageMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getLanguageMapping() throws Exception {
        // Initialize the database
        insertedLanguageMapping = languageMappingRepository.saveAndFlush(languageMapping);

        // Get the languageMapping
        restLanguageMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, languageMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(languageMapping.getId().intValue()))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLanguageMapping() throws Exception {
        // Get the languageMapping
        restLanguageMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLanguageMapping() throws Exception {
        // Initialize the database
        insertedLanguageMapping = languageMappingRepository.saveAndFlush(languageMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the languageMapping
        LanguageMapping updatedLanguageMapping = languageMappingRepository.findById(languageMapping.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLanguageMapping are not directly saved in db
        em.detach(updatedLanguageMapping);
        updatedLanguageMapping
            .lang(UPDATED_LANG)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restLanguageMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLanguageMapping.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedLanguageMapping))
            )
            .andExpect(status().isOk());

        // Validate the LanguageMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLanguageMappingToMatchAllProperties(updatedLanguageMapping);
    }

    @Test
    @Transactional
    void putNonExistingLanguageMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        languageMapping.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLanguageMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, languageMapping.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(languageMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the LanguageMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLanguageMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        languageMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLanguageMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(languageMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the LanguageMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLanguageMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        languageMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLanguageMappingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(languageMapping)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LanguageMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLanguageMappingWithPatch() throws Exception {
        // Initialize the database
        insertedLanguageMapping = languageMappingRepository.saveAndFlush(languageMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the languageMapping using partial update
        LanguageMapping partialUpdatedLanguageMapping = new LanguageMapping();
        partialUpdatedLanguageMapping.setId(languageMapping.getId());

        partialUpdatedLanguageMapping.lang(UPDATED_LANG).createddBy(UPDATED_CREATEDD_BY);

        restLanguageMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLanguageMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLanguageMapping))
            )
            .andExpect(status().isOk());

        // Validate the LanguageMapping in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLanguageMappingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLanguageMapping, languageMapping),
            getPersistedLanguageMapping(languageMapping)
        );
    }

    @Test
    @Transactional
    void fullUpdateLanguageMappingWithPatch() throws Exception {
        // Initialize the database
        insertedLanguageMapping = languageMappingRepository.saveAndFlush(languageMapping);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the languageMapping using partial update
        LanguageMapping partialUpdatedLanguageMapping = new LanguageMapping();
        partialUpdatedLanguageMapping.setId(languageMapping.getId());

        partialUpdatedLanguageMapping
            .lang(UPDATED_LANG)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restLanguageMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLanguageMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLanguageMapping))
            )
            .andExpect(status().isOk());

        // Validate the LanguageMapping in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLanguageMappingUpdatableFieldsEquals(
            partialUpdatedLanguageMapping,
            getPersistedLanguageMapping(partialUpdatedLanguageMapping)
        );
    }

    @Test
    @Transactional
    void patchNonExistingLanguageMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        languageMapping.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLanguageMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, languageMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(languageMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the LanguageMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLanguageMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        languageMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLanguageMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(languageMapping))
            )
            .andExpect(status().isBadRequest());

        // Validate the LanguageMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLanguageMapping() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        languageMapping.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLanguageMappingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(languageMapping)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LanguageMapping in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLanguageMapping() throws Exception {
        // Initialize the database
        insertedLanguageMapping = languageMappingRepository.saveAndFlush(languageMapping);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the languageMapping
        restLanguageMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, languageMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return languageMappingRepository.count();
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

    protected LanguageMapping getPersistedLanguageMapping(LanguageMapping languageMapping) {
        return languageMappingRepository.findById(languageMapping.getId()).orElseThrow();
    }

    protected void assertPersistedLanguageMappingToMatchAllProperties(LanguageMapping expectedLanguageMapping) {
        assertLanguageMappingAllPropertiesEquals(expectedLanguageMapping, getPersistedLanguageMapping(expectedLanguageMapping));
    }

    protected void assertPersistedLanguageMappingToMatchUpdatableProperties(LanguageMapping expectedLanguageMapping) {
        assertLanguageMappingAllUpdatablePropertiesEquals(expectedLanguageMapping, getPersistedLanguageMapping(expectedLanguageMapping));
    }
}
