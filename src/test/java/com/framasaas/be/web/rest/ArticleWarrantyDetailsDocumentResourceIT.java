package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.ArticleWarrantyDetailsDocumentAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.ArticleWarrantyDetailsDocument;
import com.framasaas.be.repository.ArticleWarrantyDetailsDocumentRepository;
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
 * Integration tests for the {@link ArticleWarrantyDetailsDocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticleWarrantyDetailsDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VALIDATED = false;
    private static final Boolean UPDATED_IS_VALIDATED = true;

    private static final String DEFAULT_VALIDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALIDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALIDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/article-warranty-details-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArticleWarrantyDetailsDocumentRepository articleWarrantyDetailsDocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleWarrantyDetailsDocumentMockMvc;

    private ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument;

    private ArticleWarrantyDetailsDocument insertedArticleWarrantyDetailsDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleWarrantyDetailsDocument createEntity() {
        return new ArticleWarrantyDetailsDocument()
            .documentPath(DEFAULT_DOCUMENT_PATH)
            .isValidated(DEFAULT_IS_VALIDATED)
            .validatedBy(DEFAULT_VALIDATED_BY)
            .validatedTime(DEFAULT_VALIDATED_TIME)
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
    public static ArticleWarrantyDetailsDocument createUpdatedEntity() {
        return new ArticleWarrantyDetailsDocument()
            .documentPath(UPDATED_DOCUMENT_PATH)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        articleWarrantyDetailsDocument = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedArticleWarrantyDetailsDocument != null) {
            articleWarrantyDetailsDocumentRepository.delete(insertedArticleWarrantyDetailsDocument);
            insertedArticleWarrantyDetailsDocument = null;
        }
    }

    @Test
    @Transactional
    void createArticleWarrantyDetailsDocument() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ArticleWarrantyDetailsDocument
        var returnedArticleWarrantyDetailsDocument = om.readValue(
            restArticleWarrantyDetailsDocumentMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ArticleWarrantyDetailsDocument.class
        );

        // Validate the ArticleWarrantyDetailsDocument in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertArticleWarrantyDetailsDocumentUpdatableFieldsEquals(
            returnedArticleWarrantyDetailsDocument,
            getPersistedArticleWarrantyDetailsDocument(returnedArticleWarrantyDetailsDocument)
        );

        insertedArticleWarrantyDetailsDocument = returnedArticleWarrantyDetailsDocument;
    }

    @Test
    @Transactional
    void createArticleWarrantyDetailsDocumentWithExistingId() throws Exception {
        // Create the ArticleWarrantyDetailsDocument with an existing ID
        articleWarrantyDetailsDocument.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetailsDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentPathIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetailsDocument.setDocumentPath(null);

        // Create the ArticleWarrantyDetailsDocument, which fails.

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsValidatedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetailsDocument.setIsValidated(null);

        // Create the ArticleWarrantyDetailsDocument, which fails.

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetailsDocument.setValidatedBy(null);

        // Create the ArticleWarrantyDetailsDocument, which fails.

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetailsDocument.setValidatedTime(null);

        // Create the ArticleWarrantyDetailsDocument, which fails.

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetailsDocument.setCreateddBy(null);

        // Create the ArticleWarrantyDetailsDocument, which fails.

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetailsDocument.setCreatedTime(null);

        // Create the ArticleWarrantyDetailsDocument, which fails.

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetailsDocument.setUpdatedBy(null);

        // Create the ArticleWarrantyDetailsDocument, which fails.

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetailsDocument.setUpdatedTime(null);

        // Create the ArticleWarrantyDetailsDocument, which fails.

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetailsDocuments() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetailsDocument = articleWarrantyDetailsDocumentRepository.saveAndFlush(articleWarrantyDetailsDocument);

        // Get all the articleWarrantyDetailsDocumentList
        restArticleWarrantyDetailsDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleWarrantyDetailsDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentPath").value(hasItem(DEFAULT_DOCUMENT_PATH)))
            .andExpect(jsonPath("$.[*].isValidated").value(hasItem(DEFAULT_IS_VALIDATED)))
            .andExpect(jsonPath("$.[*].validatedBy").value(hasItem(DEFAULT_VALIDATED_BY)))
            .andExpect(jsonPath("$.[*].validatedTime").value(hasItem(DEFAULT_VALIDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getArticleWarrantyDetailsDocument() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetailsDocument = articleWarrantyDetailsDocumentRepository.saveAndFlush(articleWarrantyDetailsDocument);

        // Get the articleWarrantyDetailsDocument
        restArticleWarrantyDetailsDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, articleWarrantyDetailsDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleWarrantyDetailsDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentPath").value(DEFAULT_DOCUMENT_PATH))
            .andExpect(jsonPath("$.isValidated").value(DEFAULT_IS_VALIDATED))
            .andExpect(jsonPath("$.validatedBy").value(DEFAULT_VALIDATED_BY))
            .andExpect(jsonPath("$.validatedTime").value(DEFAULT_VALIDATED_TIME.toString()))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingArticleWarrantyDetailsDocument() throws Exception {
        // Get the articleWarrantyDetailsDocument
        restArticleWarrantyDetailsDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticleWarrantyDetailsDocument() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetailsDocument = articleWarrantyDetailsDocumentRepository.saveAndFlush(articleWarrantyDetailsDocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the articleWarrantyDetailsDocument
        ArticleWarrantyDetailsDocument updatedArticleWarrantyDetailsDocument = articleWarrantyDetailsDocumentRepository
            .findById(articleWarrantyDetailsDocument.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedArticleWarrantyDetailsDocument are not directly saved in db
        em.detach(updatedArticleWarrantyDetailsDocument);
        updatedArticleWarrantyDetailsDocument
            .documentPath(UPDATED_DOCUMENT_PATH)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArticleWarrantyDetailsDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedArticleWarrantyDetailsDocument))
            )
            .andExpect(status().isOk());

        // Validate the ArticleWarrantyDetailsDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArticleWarrantyDetailsDocumentToMatchAllProperties(updatedArticleWarrantyDetailsDocument);
    }

    @Test
    @Transactional
    void putNonExistingArticleWarrantyDetailsDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetailsDocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleWarrantyDetailsDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetailsDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticleWarrantyDetailsDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetailsDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetailsDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticleWarrantyDetailsDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetailsDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleWarrantyDetailsDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleWarrantyDetailsDocumentWithPatch() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetailsDocument = articleWarrantyDetailsDocumentRepository.saveAndFlush(articleWarrantyDetailsDocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the articleWarrantyDetailsDocument using partial update
        ArticleWarrantyDetailsDocument partialUpdatedArticleWarrantyDetailsDocument = new ArticleWarrantyDetailsDocument();
        partialUpdatedArticleWarrantyDetailsDocument.setId(articleWarrantyDetailsDocument.getId());

        partialUpdatedArticleWarrantyDetailsDocument
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME);

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleWarrantyDetailsDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArticleWarrantyDetailsDocument))
            )
            .andExpect(status().isOk());

        // Validate the ArticleWarrantyDetailsDocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArticleWarrantyDetailsDocumentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedArticleWarrantyDetailsDocument, articleWarrantyDetailsDocument),
            getPersistedArticleWarrantyDetailsDocument(articleWarrantyDetailsDocument)
        );
    }

    @Test
    @Transactional
    void fullUpdateArticleWarrantyDetailsDocumentWithPatch() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetailsDocument = articleWarrantyDetailsDocumentRepository.saveAndFlush(articleWarrantyDetailsDocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the articleWarrantyDetailsDocument using partial update
        ArticleWarrantyDetailsDocument partialUpdatedArticleWarrantyDetailsDocument = new ArticleWarrantyDetailsDocument();
        partialUpdatedArticleWarrantyDetailsDocument.setId(articleWarrantyDetailsDocument.getId());

        partialUpdatedArticleWarrantyDetailsDocument
            .documentPath(UPDATED_DOCUMENT_PATH)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleWarrantyDetailsDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArticleWarrantyDetailsDocument))
            )
            .andExpect(status().isOk());

        // Validate the ArticleWarrantyDetailsDocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArticleWarrantyDetailsDocumentUpdatableFieldsEquals(
            partialUpdatedArticleWarrantyDetailsDocument,
            getPersistedArticleWarrantyDetailsDocument(partialUpdatedArticleWarrantyDetailsDocument)
        );
    }

    @Test
    @Transactional
    void patchNonExistingArticleWarrantyDetailsDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetailsDocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleWarrantyDetailsDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetailsDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticleWarrantyDetailsDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetailsDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetailsDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticleWarrantyDetailsDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetailsDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleWarrantyDetailsDocument))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleWarrantyDetailsDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticleWarrantyDetailsDocument() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetailsDocument = articleWarrantyDetailsDocumentRepository.saveAndFlush(articleWarrantyDetailsDocument);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the articleWarrantyDetailsDocument
        restArticleWarrantyDetailsDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, articleWarrantyDetailsDocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return articleWarrantyDetailsDocumentRepository.count();
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

    protected ArticleWarrantyDetailsDocument getPersistedArticleWarrantyDetailsDocument(
        ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument
    ) {
        return articleWarrantyDetailsDocumentRepository.findById(articleWarrantyDetailsDocument.getId()).orElseThrow();
    }

    protected void assertPersistedArticleWarrantyDetailsDocumentToMatchAllProperties(
        ArticleWarrantyDetailsDocument expectedArticleWarrantyDetailsDocument
    ) {
        assertArticleWarrantyDetailsDocumentAllPropertiesEquals(
            expectedArticleWarrantyDetailsDocument,
            getPersistedArticleWarrantyDetailsDocument(expectedArticleWarrantyDetailsDocument)
        );
    }

    protected void assertPersistedArticleWarrantyDetailsDocumentToMatchUpdatableProperties(
        ArticleWarrantyDetailsDocument expectedArticleWarrantyDetailsDocument
    ) {
        assertArticleWarrantyDetailsDocumentAllUpdatablePropertiesEquals(
            expectedArticleWarrantyDetailsDocument,
            getPersistedArticleWarrantyDetailsDocument(expectedArticleWarrantyDetailsDocument)
        );
    }
}
