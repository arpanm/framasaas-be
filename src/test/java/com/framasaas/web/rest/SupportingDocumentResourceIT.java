package com.framasaas.web.rest;

import static com.framasaas.domain.SupportingDocumentAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Article;
import com.framasaas.domain.ArticleWarrantyDetails;
import com.framasaas.domain.Franchise;
import com.framasaas.domain.ServiceOrder;
import com.framasaas.domain.SupportingDocument;
import com.framasaas.domain.enumeration.DocumentFormat;
import com.framasaas.domain.enumeration.DocumentType;
import com.framasaas.repository.SupportingDocumentRepository;
import com.framasaas.service.dto.SupportingDocumentDTO;
import com.framasaas.service.mapper.SupportingDocumentMapper;
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
    private static final Long SMALLER_DOCUMENT_SIZE = 1L - 1L;

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
    private SupportingDocumentMapper supportingDocumentMapper;

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
    void initTest() {
        supportingDocument = createEntity();
    }

    @AfterEach
    void cleanup() {
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);
        var returnedSupportingDocumentDTO = om.readValue(
            restSupportingDocumentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SupportingDocumentDTO.class
        );

        // Validate the SupportingDocument in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSupportingDocument = supportingDocumentMapper.toEntity(returnedSupportingDocumentDTO);
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        restSupportingDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
    void getSupportingDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        Long id = supportingDocument.getId();

        defaultSupportingDocumentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSupportingDocumentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSupportingDocumentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentName equals to
        defaultSupportingDocumentFiltering("documentName.equals=" + DEFAULT_DOCUMENT_NAME, "documentName.equals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentName in
        defaultSupportingDocumentFiltering(
            "documentName.in=" + DEFAULT_DOCUMENT_NAME + "," + UPDATED_DOCUMENT_NAME,
            "documentName.in=" + UPDATED_DOCUMENT_NAME
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentName is not null
        defaultSupportingDocumentFiltering("documentName.specified=true", "documentName.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentName contains
        defaultSupportingDocumentFiltering(
            "documentName.contains=" + DEFAULT_DOCUMENT_NAME,
            "documentName.contains=" + UPDATED_DOCUMENT_NAME
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentName does not contain
        defaultSupportingDocumentFiltering(
            "documentName.doesNotContain=" + UPDATED_DOCUMENT_NAME,
            "documentName.doesNotContain=" + DEFAULT_DOCUMENT_NAME
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentType equals to
        defaultSupportingDocumentFiltering("documentType.equals=" + DEFAULT_DOCUMENT_TYPE, "documentType.equals=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentType in
        defaultSupportingDocumentFiltering(
            "documentType.in=" + DEFAULT_DOCUMENT_TYPE + "," + UPDATED_DOCUMENT_TYPE,
            "documentType.in=" + UPDATED_DOCUMENT_TYPE
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentType is not null
        defaultSupportingDocumentFiltering("documentType.specified=true", "documentType.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentFormatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentFormat equals to
        defaultSupportingDocumentFiltering(
            "documentFormat.equals=" + DEFAULT_DOCUMENT_FORMAT,
            "documentFormat.equals=" + UPDATED_DOCUMENT_FORMAT
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentFormatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentFormat in
        defaultSupportingDocumentFiltering(
            "documentFormat.in=" + DEFAULT_DOCUMENT_FORMAT + "," + UPDATED_DOCUMENT_FORMAT,
            "documentFormat.in=" + UPDATED_DOCUMENT_FORMAT
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentFormatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentFormat is not null
        defaultSupportingDocumentFiltering("documentFormat.specified=true", "documentFormat.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentSize equals to
        defaultSupportingDocumentFiltering("documentSize.equals=" + DEFAULT_DOCUMENT_SIZE, "documentSize.equals=" + UPDATED_DOCUMENT_SIZE);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentSizeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentSize in
        defaultSupportingDocumentFiltering(
            "documentSize.in=" + DEFAULT_DOCUMENT_SIZE + "," + UPDATED_DOCUMENT_SIZE,
            "documentSize.in=" + UPDATED_DOCUMENT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentSize is not null
        defaultSupportingDocumentFiltering("documentSize.specified=true", "documentSize.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentSize is greater than or equal to
        defaultSupportingDocumentFiltering(
            "documentSize.greaterThanOrEqual=" + DEFAULT_DOCUMENT_SIZE,
            "documentSize.greaterThanOrEqual=" + UPDATED_DOCUMENT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentSizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentSize is less than or equal to
        defaultSupportingDocumentFiltering(
            "documentSize.lessThanOrEqual=" + DEFAULT_DOCUMENT_SIZE,
            "documentSize.lessThanOrEqual=" + SMALLER_DOCUMENT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentSize is less than
        defaultSupportingDocumentFiltering(
            "documentSize.lessThan=" + UPDATED_DOCUMENT_SIZE,
            "documentSize.lessThan=" + DEFAULT_DOCUMENT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentSizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentSize is greater than
        defaultSupportingDocumentFiltering(
            "documentSize.greaterThan=" + SMALLER_DOCUMENT_SIZE,
            "documentSize.greaterThan=" + DEFAULT_DOCUMENT_SIZE
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentPathIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentPath equals to
        defaultSupportingDocumentFiltering("documentPath.equals=" + DEFAULT_DOCUMENT_PATH, "documentPath.equals=" + UPDATED_DOCUMENT_PATH);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentPathIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentPath in
        defaultSupportingDocumentFiltering(
            "documentPath.in=" + DEFAULT_DOCUMENT_PATH + "," + UPDATED_DOCUMENT_PATH,
            "documentPath.in=" + UPDATED_DOCUMENT_PATH
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentPath is not null
        defaultSupportingDocumentFiltering("documentPath.specified=true", "documentPath.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentPathContainsSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentPath contains
        defaultSupportingDocumentFiltering(
            "documentPath.contains=" + DEFAULT_DOCUMENT_PATH,
            "documentPath.contains=" + UPDATED_DOCUMENT_PATH
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByDocumentPathNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where documentPath does not contain
        defaultSupportingDocumentFiltering(
            "documentPath.doesNotContain=" + UPDATED_DOCUMENT_PATH,
            "documentPath.doesNotContain=" + DEFAULT_DOCUMENT_PATH
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByIsValidatedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where isValidated equals to
        defaultSupportingDocumentFiltering("isValidated.equals=" + DEFAULT_IS_VALIDATED, "isValidated.equals=" + UPDATED_IS_VALIDATED);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByIsValidatedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where isValidated in
        defaultSupportingDocumentFiltering(
            "isValidated.in=" + DEFAULT_IS_VALIDATED + "," + UPDATED_IS_VALIDATED,
            "isValidated.in=" + UPDATED_IS_VALIDATED
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByIsValidatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where isValidated is not null
        defaultSupportingDocumentFiltering("isValidated.specified=true", "isValidated.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByValidatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where validatedBy equals to
        defaultSupportingDocumentFiltering("validatedBy.equals=" + DEFAULT_VALIDATED_BY, "validatedBy.equals=" + UPDATED_VALIDATED_BY);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByValidatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where validatedBy in
        defaultSupportingDocumentFiltering(
            "validatedBy.in=" + DEFAULT_VALIDATED_BY + "," + UPDATED_VALIDATED_BY,
            "validatedBy.in=" + UPDATED_VALIDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByValidatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where validatedBy is not null
        defaultSupportingDocumentFiltering("validatedBy.specified=true", "validatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByValidatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where validatedBy contains
        defaultSupportingDocumentFiltering("validatedBy.contains=" + DEFAULT_VALIDATED_BY, "validatedBy.contains=" + UPDATED_VALIDATED_BY);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByValidatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where validatedBy does not contain
        defaultSupportingDocumentFiltering(
            "validatedBy.doesNotContain=" + UPDATED_VALIDATED_BY,
            "validatedBy.doesNotContain=" + DEFAULT_VALIDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByValidatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where validatedTime equals to
        defaultSupportingDocumentFiltering(
            "validatedTime.equals=" + DEFAULT_VALIDATED_TIME,
            "validatedTime.equals=" + UPDATED_VALIDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByValidatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where validatedTime in
        defaultSupportingDocumentFiltering(
            "validatedTime.in=" + DEFAULT_VALIDATED_TIME + "," + UPDATED_VALIDATED_TIME,
            "validatedTime.in=" + UPDATED_VALIDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByValidatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where validatedTime is not null
        defaultSupportingDocumentFiltering("validatedTime.specified=true", "validatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where createddBy equals to
        defaultSupportingDocumentFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where createddBy in
        defaultSupportingDocumentFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where createddBy is not null
        defaultSupportingDocumentFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where createddBy contains
        defaultSupportingDocumentFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where createddBy does not contain
        defaultSupportingDocumentFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where createdTime equals to
        defaultSupportingDocumentFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where createdTime in
        defaultSupportingDocumentFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where createdTime is not null
        defaultSupportingDocumentFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where updatedBy equals to
        defaultSupportingDocumentFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where updatedBy in
        defaultSupportingDocumentFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where updatedBy is not null
        defaultSupportingDocumentFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where updatedBy contains
        defaultSupportingDocumentFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where updatedBy does not contain
        defaultSupportingDocumentFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where updatedTime equals to
        defaultSupportingDocumentFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where updatedTime in
        defaultSupportingDocumentFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSupportingDocument = supportingDocumentRepository.saveAndFlush(supportingDocument);

        // Get all the supportingDocumentList where updatedTime is not null
        defaultSupportingDocumentFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByFranchiseIsEqualToSomething() throws Exception {
        Franchise franchise;
        if (TestUtil.findAll(em, Franchise.class).isEmpty()) {
            supportingDocumentRepository.saveAndFlush(supportingDocument);
            franchise = FranchiseResourceIT.createEntity();
        } else {
            franchise = TestUtil.findAll(em, Franchise.class).get(0);
        }
        em.persist(franchise);
        em.flush();
        supportingDocument.setFranchise(franchise);
        supportingDocumentRepository.saveAndFlush(supportingDocument);
        Long franchiseId = franchise.getId();
        // Get all the supportingDocumentList where franchise equals to franchiseId
        defaultSupportingDocumentShouldBeFound("franchiseId.equals=" + franchiseId);

        // Get all the supportingDocumentList where franchise equals to (franchiseId + 1)
        defaultSupportingDocumentShouldNotBeFound("franchiseId.equals=" + (franchiseId + 1));
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByArticleIsEqualToSomething() throws Exception {
        Article article;
        if (TestUtil.findAll(em, Article.class).isEmpty()) {
            supportingDocumentRepository.saveAndFlush(supportingDocument);
            article = ArticleResourceIT.createEntity();
        } else {
            article = TestUtil.findAll(em, Article.class).get(0);
        }
        em.persist(article);
        em.flush();
        supportingDocument.setArticle(article);
        supportingDocumentRepository.saveAndFlush(supportingDocument);
        Long articleId = article.getId();
        // Get all the supportingDocumentList where article equals to articleId
        defaultSupportingDocumentShouldBeFound("articleId.equals=" + articleId);

        // Get all the supportingDocumentList where article equals to (articleId + 1)
        defaultSupportingDocumentShouldNotBeFound("articleId.equals=" + (articleId + 1));
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByArticleWarrantyIsEqualToSomething() throws Exception {
        ArticleWarrantyDetails articleWarranty;
        if (TestUtil.findAll(em, ArticleWarrantyDetails.class).isEmpty()) {
            supportingDocumentRepository.saveAndFlush(supportingDocument);
            articleWarranty = ArticleWarrantyDetailsResourceIT.createEntity();
        } else {
            articleWarranty = TestUtil.findAll(em, ArticleWarrantyDetails.class).get(0);
        }
        em.persist(articleWarranty);
        em.flush();
        supportingDocument.setArticleWarranty(articleWarranty);
        supportingDocumentRepository.saveAndFlush(supportingDocument);
        Long articleWarrantyId = articleWarranty.getId();
        // Get all the supportingDocumentList where articleWarranty equals to articleWarrantyId
        defaultSupportingDocumentShouldBeFound("articleWarrantyId.equals=" + articleWarrantyId);

        // Get all the supportingDocumentList where articleWarranty equals to (articleWarrantyId + 1)
        defaultSupportingDocumentShouldNotBeFound("articleWarrantyId.equals=" + (articleWarrantyId + 1));
    }

    @Test
    @Transactional
    void getAllSupportingDocumentsByServiceOrderIsEqualToSomething() throws Exception {
        ServiceOrder serviceOrder;
        if (TestUtil.findAll(em, ServiceOrder.class).isEmpty()) {
            supportingDocumentRepository.saveAndFlush(supportingDocument);
            serviceOrder = ServiceOrderResourceIT.createEntity();
        } else {
            serviceOrder = TestUtil.findAll(em, ServiceOrder.class).get(0);
        }
        em.persist(serviceOrder);
        em.flush();
        supportingDocument.setServiceOrder(serviceOrder);
        supportingDocumentRepository.saveAndFlush(supportingDocument);
        Long serviceOrderId = serviceOrder.getId();
        // Get all the supportingDocumentList where serviceOrder equals to serviceOrderId
        defaultSupportingDocumentShouldBeFound("serviceOrderId.equals=" + serviceOrderId);

        // Get all the supportingDocumentList where serviceOrder equals to (serviceOrderId + 1)
        defaultSupportingDocumentShouldNotBeFound("serviceOrderId.equals=" + (serviceOrderId + 1));
    }

    private void defaultSupportingDocumentFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSupportingDocumentShouldBeFound(shouldBeFound);
        defaultSupportingDocumentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupportingDocumentShouldBeFound(String filter) throws Exception {
        restSupportingDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restSupportingDocumentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupportingDocumentShouldNotBeFound(String filter) throws Exception {
        restSupportingDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupportingDocumentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(updatedSupportingDocument);

        restSupportingDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supportingDocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supportingDocumentDTO))
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

        // Create the SupportingDocument
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supportingDocumentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supportingDocumentDTO))
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

        // Create the SupportingDocument
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supportingDocumentDTO))
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

        // Create the SupportingDocument
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supportingDocumentDTO)))
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
            .documentFormat(UPDATED_DOCUMENT_FORMAT)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
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

        // Create the SupportingDocument
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supportingDocumentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(supportingDocumentDTO))
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

        // Create the SupportingDocument
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(supportingDocumentDTO))
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

        // Create the SupportingDocument
        SupportingDocumentDTO supportingDocumentDTO = supportingDocumentMapper.toDto(supportingDocument);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingDocumentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(supportingDocumentDTO)))
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
