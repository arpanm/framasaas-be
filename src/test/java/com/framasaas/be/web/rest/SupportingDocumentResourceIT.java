package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.SupportingDocumentAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.SupportingDocument;
import com.framasaas.be.domain.enumeration.DocumentFormat;
import com.framasaas.be.domain.enumeration.DocumentType;
import com.framasaas.be.repository.SupportingDocumentRepository;
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
 * Integration tests for the {@link SupportingDocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SupportingDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final DocumentType DEFAULT_DOCUMENT_TYPE = DocumentType.AddressProof;
    private static final DocumentType UPDATED_DOCUMENT_TYPE = DocumentType.RegistrationProof;

    private static final DocumentFormat DEFAULT_DOCUMENT_FORMAT = DocumentFormat.PdfFormat;
    private static final DocumentFormat UPDATED_DOCUMENT_FORMAT = DocumentFormat.JpgFormat;

    private static final Long DEFAULT_DOCUMENT_SIZE = 1L;
    private static final Long UPDATED_DOCUMENT_SIZE = 2L;

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

    private static final String ENTITY_API_URL = "/api/supporting-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SupportingDocumentRepository supportingDocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupportingDocumentMockMvc;

    private SupportingDocument supportingDocument;

    private SupportingDocument insertedSupportingDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportingDocument createEntity() {
        return new SupportingDocument()
            .documentName(DEFAULT_DOCUMENT_NAME)
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .documentFormat(DEFAULT_DOCUMENT_FORMAT)
            .documentSize(DEFAULT_DOCUMENT_SIZE)
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
    public static SupportingDocument createUpdatedEntity() {
        return new SupportingDocument()
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .documentSize(UPDATED_DOCUMENT_SIZE)
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
        supportingDocument = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSupportingDocument != null) {
            supportingDocumentRepository.delete(insertedSupportingDocument);
            insertedSupportingDocument = null;
        }
    }

    @Test
    @Transactional
    void createSupportingDocument() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SupportingDocument
        var returnedSupportingDocument = om.readValue(
            restSupportingDocumentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SupportingDocument.class
        );

        // Validate the SupportingDocument in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSupportingDocumentUpdatableFieldsEquals(
            returnedSupportingDocument,
            getPersistedSupportingDocument(returnedSupportingDocument)
        );

        insertedSupportingDocument = returnedSupportingDocument;
    }

    @Test
    @Transactional
    void createSupportingDocumentWithExistingId() throws Exception {
        // Create the SupportingDocument with an existing ID
        supportingDocument.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        // Validate the SupportingDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setDocumentName(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setDocumentType(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentFormatIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setDocumentFormat(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentPathIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setDocumentPath(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsValidatedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setIsValidated(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setValidatedBy(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setValidatedTime(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setCreateddBy(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setCreatedTime(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setUpdatedBy(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supportingDocument.setUpdatedTime(null);

        // Create the SupportingDocument, which fails.

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSupportingDocuments() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList
        restSupportingDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportingDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentFormat").value(hasItem(DEFAULT_DOCUMENT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].documentSize").value(hasItem(DEFAULT_DOCUMENT_SIZE.intValue())))
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
    void getSupportingDocument() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get the supportingDocument
        restSupportingDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, supportingDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supportingDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.documentFormat").value(DEFAULT_DOCUMENT_FORMAT.toString()))
            .andExpect(jsonPath("$.documentSize").value(DEFAULT_DOCUMENT_SIZE.intValue()))
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
    void getNonExistingSupportingDocument() throws Exception {
        // Get the supportingDocument
        restSupportingDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSupportingDocument() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the supportingDocument
        SupportingDocument updatedSupportingDocument = supportingDocumentRepository.findById(supportingDocument.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSupportingDocument are not directly saved in db
        em.detach(updatedSupportingDocument);
        updatedSupportingDocument
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .documentSize(UPDATED_DOCUMENT_SIZE)
            .documentPath(UPDATED_DOCUMENT_PATH)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restSupportingDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSupportingDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSupportingDocument))
            )
            .andExpect(status().isOk());

        // Validate the SupportingDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSupportingDocumentToMatchAllProperties(updatedSupportingDocument);
    }

    @Test
    @Transactional
    void putNonExistingSupportingDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supportingDocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supportingDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supportingDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportingDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupportingDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supportingDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supportingDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportingDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupportingDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supportingDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupportingDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupportingDocumentWithPatch() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the supportingDocument using partial update
        SupportingDocument partialUpdatedSupportingDocument = new SupportingDocument();
        partialUpdatedSupportingDocument.setId(supportingDocument.getId());

        partialUpdatedSupportingDocument
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY);

        restSupportingDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupportingDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSupportingDocument))
            )
            .andExpect(status().isOk());

        // Validate the SupportingDocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSupportingDocumentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSupportingDocument, supportingDocument),
            getPersistedSupportingDocument(supportingDocument)
        );
    }

    @Test
    @Transactional
    void fullUpdateSupportingDocumentWithPatch() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the supportingDocument using partial update
        SupportingDocument partialUpdatedSupportingDocument = new SupportingDocument();
        partialUpdatedSupportingDocument.setId(supportingDocument.getId());

        partialUpdatedSupportingDocument
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .documentSize(UPDATED_DOCUMENT_SIZE)
            .documentPath(UPDATED_DOCUMENT_PATH)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restSupportingDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupportingDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSupportingDocument))
            )
            .andExpect(status().isOk());

        // Validate the SupportingDocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSupportingDocumentUpdatableFieldsEquals(
            partialUpdatedSupportingDocument,
            getPersistedSupportingDocument(partialUpdatedSupportingDocument)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSupportingDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supportingDocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supportingDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(supportingDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportingDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupportingDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supportingDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(supportingDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportingDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupportingDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supportingDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(supportingDocument)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupportingDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupportingDocument() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the supportingDocument
        restSupportingDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, supportingDocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return supportingDocumentRepository.count();
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

    protected SupportingDocument getPersistedSupportingDocument(SupportingDocument supportingDocument) {
        return supportingDocumentRepository.findById(supportingDocument.getId()).orElseThrow();
    }

    protected void assertPersistedSupportingDocumentToMatchAllProperties(SupportingDocument expectedSupportingDocument) {
        assertSupportingDocumentAllPropertiesEquals(expectedSupportingDocument, getPersistedSupportingDocument(expectedSupportingDocument));
    }

    protected void assertPersistedSupportingDocumentToMatchUpdatableProperties(SupportingDocument expectedSupportingDocument) {
        assertSupportingDocumentAllUpdatablePropertiesEquals(
            expectedSupportingDocument,
            getPersistedSupportingDocument(expectedSupportingDocument)
        );
    }
}
