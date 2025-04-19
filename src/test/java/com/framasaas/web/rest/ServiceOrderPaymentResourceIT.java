package com.framasaas.web.rest;

import static com.framasaas.domain.ServiceOrderPaymentAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.ServiceOrderPayment;
import com.framasaas.domain.enumeration.ModeOfPayment;
import com.framasaas.domain.enumeration.PaymentStatus;
import com.framasaas.repository.ServiceOrderPaymentRepository;
import com.framasaas.service.dto.ServiceOrderPaymentDTO;
import com.framasaas.service.mapper.ServiceOrderPaymentMapper;
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
 * Integration tests for the {@link ServiceOrderPaymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceOrderPaymentResourceIT {

    private static final String DEFAULT_PAYMENT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_LINK = "BBBBBBBBBB";

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.INITIATED;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.SUCCESS;

    private static final ModeOfPayment DEFAULT_MOP = ModeOfPayment.CASH;
    private static final ModeOfPayment UPDATED_MOP = ModeOfPayment.ONLINE;

    private static final String DEFAULT_PG_TXN_ID = "AAAAAAAAAA";
    private static final String UPDATED_PG_TXN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PG_TXN_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_PG_TXN_RESPONSE = "BBBBBBBBBB";

    private static final Instant DEFAULT_PG_TXN_RESPONSE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PG_TXN_RESPONSE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PAYMENT_INITIATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_INITIATED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/service-order-payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceOrderPaymentRepository serviceOrderPaymentRepository;

    @Autowired
    private ServiceOrderPaymentMapper serviceOrderPaymentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOrderPaymentMockMvc;

    private ServiceOrderPayment serviceOrderPayment;

    private ServiceOrderPayment insertedServiceOrderPayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrderPayment createEntity() {
        return new ServiceOrderPayment()
            .paymentLink(DEFAULT_PAYMENT_LINK)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .mop(DEFAULT_MOP)
            .pgTxnId(DEFAULT_PG_TXN_ID)
            .pgTxnResponse(DEFAULT_PG_TXN_RESPONSE)
            .pgTxnResponseTime(DEFAULT_PG_TXN_RESPONSE_TIME)
            .paymentInitiatedBy(DEFAULT_PAYMENT_INITIATED_BY)
            .isActive(DEFAULT_IS_ACTIVE)
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
    public static ServiceOrderPayment createUpdatedEntity() {
        return new ServiceOrderPayment()
            .paymentLink(UPDATED_PAYMENT_LINK)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .mop(UPDATED_MOP)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgTxnResponse(UPDATED_PG_TXN_RESPONSE)
            .pgTxnResponseTime(UPDATED_PG_TXN_RESPONSE_TIME)
            .paymentInitiatedBy(UPDATED_PAYMENT_INITIATED_BY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    void initTest() {
        serviceOrderPayment = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedServiceOrderPayment != null) {
            serviceOrderPaymentRepository.delete(insertedServiceOrderPayment);
            insertedServiceOrderPayment = null;
        }
    }

    @Test
    @Transactional
    void createServiceOrderPayment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServiceOrderPayment
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);
        var returnedServiceOrderPaymentDTO = om.readValue(
            restServiceOrderPaymentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPaymentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceOrderPaymentDTO.class
        );

        // Validate the ServiceOrderPayment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServiceOrderPayment = serviceOrderPaymentMapper.toEntity(returnedServiceOrderPaymentDTO);
        assertServiceOrderPaymentUpdatableFieldsEquals(
            returnedServiceOrderPayment,
            getPersistedServiceOrderPayment(returnedServiceOrderPayment)
        );

        insertedServiceOrderPayment = returnedServiceOrderPayment;
    }

    @Test
    @Transactional
    void createServiceOrderPaymentWithExistingId() throws Exception {
        // Create the ServiceOrderPayment with an existing ID
        serviceOrderPayment.setId(1L);
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrderPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderPayment.setCreateddBy(null);

        // Create the ServiceOrderPayment, which fails.
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        restServiceOrderPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPaymentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderPayment.setCreatedTime(null);

        // Create the ServiceOrderPayment, which fails.
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        restServiceOrderPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPaymentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderPayment.setUpdatedBy(null);

        // Create the ServiceOrderPayment, which fails.
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        restServiceOrderPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPaymentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        serviceOrderPayment.setUpdatedTime(null);

        // Create the ServiceOrderPayment, which fails.
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        restServiceOrderPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPaymentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServiceOrderPayments() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList
        restServiceOrderPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentLink").value(hasItem(DEFAULT_PAYMENT_LINK)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].mop").value(hasItem(DEFAULT_MOP.toString())))
            .andExpect(jsonPath("$.[*].pgTxnId").value(hasItem(DEFAULT_PG_TXN_ID)))
            .andExpect(jsonPath("$.[*].pgTxnResponse").value(hasItem(DEFAULT_PG_TXN_RESPONSE)))
            .andExpect(jsonPath("$.[*].pgTxnResponseTime").value(hasItem(DEFAULT_PG_TXN_RESPONSE_TIME.toString())))
            .andExpect(jsonPath("$.[*].paymentInitiatedBy").value(hasItem(DEFAULT_PAYMENT_INITIATED_BY)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getServiceOrderPayment() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get the serviceOrderPayment
        restServiceOrderPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, serviceOrderPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrderPayment.getId().intValue()))
            .andExpect(jsonPath("$.paymentLink").value(DEFAULT_PAYMENT_LINK))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.mop").value(DEFAULT_MOP.toString()))
            .andExpect(jsonPath("$.pgTxnId").value(DEFAULT_PG_TXN_ID))
            .andExpect(jsonPath("$.pgTxnResponse").value(DEFAULT_PG_TXN_RESPONSE))
            .andExpect(jsonPath("$.pgTxnResponseTime").value(DEFAULT_PG_TXN_RESPONSE_TIME.toString()))
            .andExpect(jsonPath("$.paymentInitiatedBy").value(DEFAULT_PAYMENT_INITIATED_BY))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getServiceOrderPaymentsByIdFiltering() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        Long id = serviceOrderPayment.getId();

        defaultServiceOrderPaymentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultServiceOrderPaymentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultServiceOrderPaymentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentLink equals to
        defaultServiceOrderPaymentFiltering("paymentLink.equals=" + DEFAULT_PAYMENT_LINK, "paymentLink.equals=" + UPDATED_PAYMENT_LINK);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentLinkIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentLink in
        defaultServiceOrderPaymentFiltering(
            "paymentLink.in=" + DEFAULT_PAYMENT_LINK + "," + UPDATED_PAYMENT_LINK,
            "paymentLink.in=" + UPDATED_PAYMENT_LINK
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentLink is not null
        defaultServiceOrderPaymentFiltering("paymentLink.specified=true", "paymentLink.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentLinkContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentLink contains
        defaultServiceOrderPaymentFiltering("paymentLink.contains=" + DEFAULT_PAYMENT_LINK, "paymentLink.contains=" + UPDATED_PAYMENT_LINK);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentLinkNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentLink does not contain
        defaultServiceOrderPaymentFiltering(
            "paymentLink.doesNotContain=" + UPDATED_PAYMENT_LINK,
            "paymentLink.doesNotContain=" + DEFAULT_PAYMENT_LINK
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentStatus equals to
        defaultServiceOrderPaymentFiltering(
            "paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS,
            "paymentStatus.equals=" + UPDATED_PAYMENT_STATUS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentStatus in
        defaultServiceOrderPaymentFiltering(
            "paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS,
            "paymentStatus.in=" + UPDATED_PAYMENT_STATUS
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentStatus is not null
        defaultServiceOrderPaymentFiltering("paymentStatus.specified=true", "paymentStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByMopIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where mop equals to
        defaultServiceOrderPaymentFiltering("mop.equals=" + DEFAULT_MOP, "mop.equals=" + UPDATED_MOP);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByMopIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where mop in
        defaultServiceOrderPaymentFiltering("mop.in=" + DEFAULT_MOP + "," + UPDATED_MOP, "mop.in=" + UPDATED_MOP);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByMopIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where mop is not null
        defaultServiceOrderPaymentFiltering("mop.specified=true", "mop.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnId equals to
        defaultServiceOrderPaymentFiltering("pgTxnId.equals=" + DEFAULT_PG_TXN_ID, "pgTxnId.equals=" + UPDATED_PG_TXN_ID);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnId in
        defaultServiceOrderPaymentFiltering("pgTxnId.in=" + DEFAULT_PG_TXN_ID + "," + UPDATED_PG_TXN_ID, "pgTxnId.in=" + UPDATED_PG_TXN_ID);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnId is not null
        defaultServiceOrderPaymentFiltering("pgTxnId.specified=true", "pgTxnId.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnIdContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnId contains
        defaultServiceOrderPaymentFiltering("pgTxnId.contains=" + DEFAULT_PG_TXN_ID, "pgTxnId.contains=" + UPDATED_PG_TXN_ID);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnId does not contain
        defaultServiceOrderPaymentFiltering("pgTxnId.doesNotContain=" + UPDATED_PG_TXN_ID, "pgTxnId.doesNotContain=" + DEFAULT_PG_TXN_ID);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnResponseIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnResponse equals to
        defaultServiceOrderPaymentFiltering(
            "pgTxnResponse.equals=" + DEFAULT_PG_TXN_RESPONSE,
            "pgTxnResponse.equals=" + UPDATED_PG_TXN_RESPONSE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnResponseIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnResponse in
        defaultServiceOrderPaymentFiltering(
            "pgTxnResponse.in=" + DEFAULT_PG_TXN_RESPONSE + "," + UPDATED_PG_TXN_RESPONSE,
            "pgTxnResponse.in=" + UPDATED_PG_TXN_RESPONSE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnResponseIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnResponse is not null
        defaultServiceOrderPaymentFiltering("pgTxnResponse.specified=true", "pgTxnResponse.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnResponseContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnResponse contains
        defaultServiceOrderPaymentFiltering(
            "pgTxnResponse.contains=" + DEFAULT_PG_TXN_RESPONSE,
            "pgTxnResponse.contains=" + UPDATED_PG_TXN_RESPONSE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnResponseNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnResponse does not contain
        defaultServiceOrderPaymentFiltering(
            "pgTxnResponse.doesNotContain=" + UPDATED_PG_TXN_RESPONSE,
            "pgTxnResponse.doesNotContain=" + DEFAULT_PG_TXN_RESPONSE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnResponseTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnResponseTime equals to
        defaultServiceOrderPaymentFiltering(
            "pgTxnResponseTime.equals=" + DEFAULT_PG_TXN_RESPONSE_TIME,
            "pgTxnResponseTime.equals=" + UPDATED_PG_TXN_RESPONSE_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnResponseTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnResponseTime in
        defaultServiceOrderPaymentFiltering(
            "pgTxnResponseTime.in=" + DEFAULT_PG_TXN_RESPONSE_TIME + "," + UPDATED_PG_TXN_RESPONSE_TIME,
            "pgTxnResponseTime.in=" + UPDATED_PG_TXN_RESPONSE_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPgTxnResponseTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where pgTxnResponseTime is not null
        defaultServiceOrderPaymentFiltering("pgTxnResponseTime.specified=true", "pgTxnResponseTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentInitiatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentInitiatedBy equals to
        defaultServiceOrderPaymentFiltering(
            "paymentInitiatedBy.equals=" + DEFAULT_PAYMENT_INITIATED_BY,
            "paymentInitiatedBy.equals=" + UPDATED_PAYMENT_INITIATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentInitiatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentInitiatedBy in
        defaultServiceOrderPaymentFiltering(
            "paymentInitiatedBy.in=" + DEFAULT_PAYMENT_INITIATED_BY + "," + UPDATED_PAYMENT_INITIATED_BY,
            "paymentInitiatedBy.in=" + UPDATED_PAYMENT_INITIATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentInitiatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentInitiatedBy is not null
        defaultServiceOrderPaymentFiltering("paymentInitiatedBy.specified=true", "paymentInitiatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentInitiatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentInitiatedBy contains
        defaultServiceOrderPaymentFiltering(
            "paymentInitiatedBy.contains=" + DEFAULT_PAYMENT_INITIATED_BY,
            "paymentInitiatedBy.contains=" + UPDATED_PAYMENT_INITIATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByPaymentInitiatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where paymentInitiatedBy does not contain
        defaultServiceOrderPaymentFiltering(
            "paymentInitiatedBy.doesNotContain=" + UPDATED_PAYMENT_INITIATED_BY,
            "paymentInitiatedBy.doesNotContain=" + DEFAULT_PAYMENT_INITIATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where isActive equals to
        defaultServiceOrderPaymentFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where isActive in
        defaultServiceOrderPaymentFiltering(
            "isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE,
            "isActive.in=" + UPDATED_IS_ACTIVE
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where isActive is not null
        defaultServiceOrderPaymentFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where createddBy equals to
        defaultServiceOrderPaymentFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where createddBy in
        defaultServiceOrderPaymentFiltering(
            "createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY,
            "createddBy.in=" + UPDATED_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where createddBy is not null
        defaultServiceOrderPaymentFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where createddBy contains
        defaultServiceOrderPaymentFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where createddBy does not contain
        defaultServiceOrderPaymentFiltering(
            "createddBy.doesNotContain=" + UPDATED_CREATEDD_BY,
            "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where createdTime equals to
        defaultServiceOrderPaymentFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where createdTime in
        defaultServiceOrderPaymentFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where createdTime is not null
        defaultServiceOrderPaymentFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where updatedBy equals to
        defaultServiceOrderPaymentFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where updatedBy in
        defaultServiceOrderPaymentFiltering(
            "updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY,
            "updatedBy.in=" + UPDATED_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where updatedBy is not null
        defaultServiceOrderPaymentFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where updatedBy contains
        defaultServiceOrderPaymentFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where updatedBy does not contain
        defaultServiceOrderPaymentFiltering(
            "updatedBy.doesNotContain=" + UPDATED_UPDATED_BY,
            "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where updatedTime equals to
        defaultServiceOrderPaymentFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where updatedTime in
        defaultServiceOrderPaymentFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllServiceOrderPaymentsByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        // Get all the serviceOrderPaymentList where updatedTime is not null
        defaultServiceOrderPaymentFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    private void defaultServiceOrderPaymentFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultServiceOrderPaymentShouldBeFound(shouldBeFound);
        defaultServiceOrderPaymentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceOrderPaymentShouldBeFound(String filter) throws Exception {
        restServiceOrderPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrderPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentLink").value(hasItem(DEFAULT_PAYMENT_LINK)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].mop").value(hasItem(DEFAULT_MOP.toString())))
            .andExpect(jsonPath("$.[*].pgTxnId").value(hasItem(DEFAULT_PG_TXN_ID)))
            .andExpect(jsonPath("$.[*].pgTxnResponse").value(hasItem(DEFAULT_PG_TXN_RESPONSE)))
            .andExpect(jsonPath("$.[*].pgTxnResponseTime").value(hasItem(DEFAULT_PG_TXN_RESPONSE_TIME.toString())))
            .andExpect(jsonPath("$.[*].paymentInitiatedBy").value(hasItem(DEFAULT_PAYMENT_INITIATED_BY)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restServiceOrderPaymentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceOrderPaymentShouldNotBeFound(String filter) throws Exception {
        restServiceOrderPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceOrderPaymentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServiceOrderPayment() throws Exception {
        // Get the serviceOrderPayment
        restServiceOrderPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServiceOrderPayment() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderPayment
        ServiceOrderPayment updatedServiceOrderPayment = serviceOrderPaymentRepository.findById(serviceOrderPayment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServiceOrderPayment are not directly saved in db
        em.detach(updatedServiceOrderPayment);
        updatedServiceOrderPayment
            .paymentLink(UPDATED_PAYMENT_LINK)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .mop(UPDATED_MOP)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgTxnResponse(UPDATED_PG_TXN_RESPONSE)
            .pgTxnResponseTime(UPDATED_PG_TXN_RESPONSE_TIME)
            .paymentInitiatedBy(UPDATED_PAYMENT_INITIATED_BY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(updatedServiceOrderPayment);

        restServiceOrderPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderPaymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderPaymentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceOrderPaymentToMatchAllProperties(updatedServiceOrderPayment);
    }

    @Test
    @Transactional
    void putNonExistingServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderPayment
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceOrderPaymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderPayment
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceOrderPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderPayment
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceOrderPaymentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceOrderPaymentWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderPayment using partial update
        ServiceOrderPayment partialUpdatedServiceOrderPayment = new ServiceOrderPayment();
        partialUpdatedServiceOrderPayment.setId(serviceOrderPayment.getId());

        partialUpdatedServiceOrderPayment
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgTxnResponseTime(UPDATED_PG_TXN_RESPONSE_TIME)
            .paymentInitiatedBy(UPDATED_PAYMENT_INITIATED_BY)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderPayment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderPayment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderPaymentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServiceOrderPayment, serviceOrderPayment),
            getPersistedServiceOrderPayment(serviceOrderPayment)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiceOrderPaymentWithPatch() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the serviceOrderPayment using partial update
        ServiceOrderPayment partialUpdatedServiceOrderPayment = new ServiceOrderPayment();
        partialUpdatedServiceOrderPayment.setId(serviceOrderPayment.getId());

        partialUpdatedServiceOrderPayment
            .paymentLink(UPDATED_PAYMENT_LINK)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .mop(UPDATED_MOP)
            .pgTxnId(UPDATED_PG_TXN_ID)
            .pgTxnResponse(UPDATED_PG_TXN_RESPONSE)
            .pgTxnResponseTime(UPDATED_PG_TXN_RESPONSE_TIME)
            .paymentInitiatedBy(UPDATED_PAYMENT_INITIATED_BY)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restServiceOrderPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServiceOrderPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServiceOrderPayment))
            )
            .andExpect(status().isOk());

        // Validate the ServiceOrderPayment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceOrderPaymentUpdatableFieldsEquals(
            partialUpdatedServiceOrderPayment,
            getPersistedServiceOrderPayment(partialUpdatedServiceOrderPayment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderPayment
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceOrderPaymentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderPayment
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceOrderPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServiceOrderPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        serviceOrderPayment.setId(longCount.incrementAndGet());

        // Create the ServiceOrderPayment
        ServiceOrderPaymentDTO serviceOrderPaymentDTO = serviceOrderPaymentMapper.toDto(serviceOrderPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceOrderPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceOrderPaymentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServiceOrderPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServiceOrderPayment() throws Exception {
        // Initialize the database
        insertedServiceOrderPayment = serviceOrderPaymentRepository.saveAndFlush(serviceOrderPayment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the serviceOrderPayment
        restServiceOrderPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, serviceOrderPayment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceOrderPaymentRepository.count();
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

    protected ServiceOrderPayment getPersistedServiceOrderPayment(ServiceOrderPayment serviceOrderPayment) {
        return serviceOrderPaymentRepository.findById(serviceOrderPayment.getId()).orElseThrow();
    }

    protected void assertPersistedServiceOrderPaymentToMatchAllProperties(ServiceOrderPayment expectedServiceOrderPayment) {
        assertServiceOrderPaymentAllPropertiesEquals(
            expectedServiceOrderPayment,
            getPersistedServiceOrderPayment(expectedServiceOrderPayment)
        );
    }

    protected void assertPersistedServiceOrderPaymentToMatchUpdatableProperties(ServiceOrderPayment expectedServiceOrderPayment) {
        assertServiceOrderPaymentAllUpdatablePropertiesEquals(
            expectedServiceOrderPayment,
            getPersistedServiceOrderPayment(expectedServiceOrderPayment)
        );
    }
}
