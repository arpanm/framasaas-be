package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.FranchiseDocumentAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.FranchiseDocument;
import com.framasaas.be.domain.enumeration.DocumentFormat;
import com.framasaas.be.domain.enumeration.DocumentType;
import com.framasaas.be.repository.FranchiseDocumentRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link FranchiseDocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FranchiseDocumentResourceIT {

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

    private static final String ENTITY_API_URL = "/api/franchise-documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FranchiseDocumentRepository franchiseDocumentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFranchiseDocumentMockMvc;

    private FranchiseDocument franchiseDocument;

    private FranchiseDocument insertedFranchiseDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseDocument createEntity() {
        return new FranchiseDocument()
            .documentName(DEFAULT_DOCUMENT_NAME)
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .documentFormat(DEFAULT_DOCUMENT_FORMAT)
            .documentSize(DEFAULT_DOCUMENT_SIZE)
            .documentPath(DEFAULT_DOCUMENT_PATH);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FranchiseDocument createUpdatedEntity() {
        return new FranchiseDocument()
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .documentSize(UPDATED_DOCUMENT_SIZE)
            .documentPath(UPDATED_DOCUMENT_PATH);
    }

    @BeforeEach
    public void initTest() {
        franchiseDocument = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFranchiseDocument != null) {
            franchiseDocumentRepository.delete(insertedFranchiseDocument);
            insertedFranchiseDocument = null;
        }
    }

    @Test
    @Transactional
    void createFranchiseDocument() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FranchiseDocument
        var returnedFranchiseDocument = om.readValue(
            restFranchiseDocumentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDocument)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FranchiseDocument.class
        );

        // Validate the FranchiseDocument in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFranchiseDocumentUpdatableFieldsEquals(returnedFranchiseDocument, getPersistedFranchiseDocument(returnedFranchiseDocument));

        insertedFranchiseDocument = returnedFranchiseDocument;
    }

    @Test
    @Transactional
    void createFranchiseDocumentWithExistingId() throws Exception {
        // Create the FranchiseDocument with an existing ID
        franchiseDocument.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFranchiseDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDocument)))
            .andExpect(status().isBadRequest());

        // Validate the FranchiseDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseDocument.setDocumentName(null);

        // Create the FranchiseDocument, which fails.

        restFranchiseDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseDocument.setDocumentType(null);

        // Create the FranchiseDocument, which fails.

        restFranchiseDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentFormatIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseDocument.setDocumentFormat(null);

        // Create the FranchiseDocument, which fails.

        restFranchiseDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentPathIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        franchiseDocument.setDocumentPath(null);

        // Create the FranchiseDocument, which fails.

        restFranchiseDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDocument)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFranchiseDocuments() throws Exception {
        // Initialize the database
        insertedFranchiseDocument = franchiseDocumentRepository.saveAndFlush(franchiseDocument);

        // Get all the franchiseDocumentList
        restFranchiseDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(franchiseDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentFormat").value(hasItem(DEFAULT_DOCUMENT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].documentSize").value(hasItem(DEFAULT_DOCUMENT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].documentPath").value(hasItem(DEFAULT_DOCUMENT_PATH)));
    }

    @Test
    @Transactional
    void getFranchiseDocument() throws Exception {
        // Initialize the database
        insertedFranchiseDocument = franchiseDocumentRepository.saveAndFlush(franchiseDocument);

        // Get the franchiseDocument
        restFranchiseDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, franchiseDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(franchiseDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.documentFormat").value(DEFAULT_DOCUMENT_FORMAT.toString()))
            .andExpect(jsonPath("$.documentSize").value(DEFAULT_DOCUMENT_SIZE.intValue()))
            .andExpect(jsonPath("$.documentPath").value(DEFAULT_DOCUMENT_PATH));
    }

    @Test
    @Transactional
    void getNonExistingFranchiseDocument() throws Exception {
        // Get the franchiseDocument
        restFranchiseDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFranchiseDocument() throws Exception {
        // Initialize the database
        insertedFranchiseDocument = franchiseDocumentRepository.saveAndFlush(franchiseDocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseDocument
        FranchiseDocument updatedFranchiseDocument = franchiseDocumentRepository.findById(franchiseDocument.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFranchiseDocument are not directly saved in db
        em.detach(updatedFranchiseDocument);
        updatedFranchiseDocument
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .documentSize(UPDATED_DOCUMENT_SIZE)
            .documentPath(UPDATED_DOCUMENT_PATH);

        restFranchiseDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFranchiseDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFranchiseDocument))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFranchiseDocumentToMatchAllProperties(updatedFranchiseDocument);
    }

    @Test
    @Transactional
    void putNonExistingFranchiseDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseDocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, franchiseDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFranchiseDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(franchiseDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFranchiseDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseDocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(franchiseDocument)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFranchiseDocumentWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseDocument = franchiseDocumentRepository.saveAndFlush(franchiseDocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseDocument using partial update
        FranchiseDocument partialUpdatedFranchiseDocument = new FranchiseDocument();
        partialUpdatedFranchiseDocument.setId(franchiseDocument.getId());

        partialUpdatedFranchiseDocument
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .documentPath(UPDATED_DOCUMENT_PATH);

        restFranchiseDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseDocument))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseDocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseDocumentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFranchiseDocument, franchiseDocument),
            getPersistedFranchiseDocument(franchiseDocument)
        );
    }

    @Test
    @Transactional
    void fullUpdateFranchiseDocumentWithPatch() throws Exception {
        // Initialize the database
        insertedFranchiseDocument = franchiseDocumentRepository.saveAndFlush(franchiseDocument);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the franchiseDocument using partial update
        FranchiseDocument partialUpdatedFranchiseDocument = new FranchiseDocument();
        partialUpdatedFranchiseDocument.setId(franchiseDocument.getId());

        partialUpdatedFranchiseDocument
            .documentName(UPDATED_DOCUMENT_NAME)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .documentSize(UPDATED_DOCUMENT_SIZE)
            .documentPath(UPDATED_DOCUMENT_PATH);

        restFranchiseDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFranchiseDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFranchiseDocument))
            )
            .andExpect(status().isOk());

        // Validate the FranchiseDocument in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFranchiseDocumentUpdatableFieldsEquals(
            partialUpdatedFranchiseDocument,
            getPersistedFranchiseDocument(partialUpdatedFranchiseDocument)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFranchiseDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseDocument.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFranchiseDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, franchiseDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFranchiseDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(franchiseDocument))
            )
            .andExpect(status().isBadRequest());

        // Validate the FranchiseDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFranchiseDocument() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        franchiseDocument.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFranchiseDocumentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(franchiseDocument)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FranchiseDocument in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFranchiseDocument() throws Exception {
        // Initialize the database
        insertedFranchiseDocument = franchiseDocumentRepository.saveAndFlush(franchiseDocument);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the franchiseDocument
        restFranchiseDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, franchiseDocument.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return franchiseDocumentRepository.count();
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

    protected FranchiseDocument getPersistedFranchiseDocument(FranchiseDocument franchiseDocument) {
        return franchiseDocumentRepository.findById(franchiseDocument.getId()).orElseThrow();
    }

    protected void assertPersistedFranchiseDocumentToMatchAllProperties(FranchiseDocument expectedFranchiseDocument) {
        assertFranchiseDocumentAllPropertiesEquals(expectedFranchiseDocument, getPersistedFranchiseDocument(expectedFranchiseDocument));
    }

    protected void assertPersistedFranchiseDocumentToMatchUpdatableProperties(FranchiseDocument expectedFranchiseDocument) {
        assertFranchiseDocumentAllUpdatablePropertiesEquals(
            expectedFranchiseDocument,
            getPersistedFranchiseDocument(expectedFranchiseDocument)
        );
    }
}
