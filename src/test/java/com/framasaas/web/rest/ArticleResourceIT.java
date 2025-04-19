package com.framasaas.web.rest;

import static com.framasaas.domain.ArticleAsserts.*;
import static com.framasaas.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.IntegrationTest;
import com.framasaas.domain.Article;
import com.framasaas.domain.Customer;
import com.framasaas.domain.Product;
import com.framasaas.repository.ArticleRepository;
import com.framasaas.service.dto.ArticleDTO;
import com.framasaas.service.mapper.ArticleMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ArticleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticleResourceIT {

    private static final String DEFAULT_SERIAL_NO = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_ARTICLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_ARTICLE_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PURCHASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_PURCHASE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Float DEFAULT_PUCHASE_PRICE = 1F;
    private static final Float UPDATED_PUCHASE_PRICE = 2F;
    private static final Float SMALLER_PUCHASE_PRICE = 1F - 1F;

    private static final String DEFAULT_PURCHASE_STORE = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_STORE = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_PATH = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleMockMvc;

    private Article article;

    private Article insertedArticle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createEntity() {
        return new Article()
            .serialNo(DEFAULT_SERIAL_NO)
            .vendorArticleId(DEFAULT_VENDOR_ARTICLE_ID)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .puchasePrice(DEFAULT_PUCHASE_PRICE)
            .purchaseStore(DEFAULT_PURCHASE_STORE)
            .invoicePath(DEFAULT_INVOICE_PATH)
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
    public static Article createUpdatedEntity() {
        return new Article()
            .serialNo(UPDATED_SERIAL_NO)
            .vendorArticleId(UPDATED_VENDOR_ARTICLE_ID)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .puchasePrice(UPDATED_PUCHASE_PRICE)
            .purchaseStore(UPDATED_PURCHASE_STORE)
            .invoicePath(UPDATED_INVOICE_PATH)
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
        article = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedArticle != null) {
            articleRepository.delete(insertedArticle);
            insertedArticle = null;
        }
    }

    @Test
    @Transactional
    void createArticle() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);
        var returnedArticleDTO = om.readValue(
            restArticleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ArticleDTO.class
        );

        // Validate the Article in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedArticle = articleMapper.toEntity(returnedArticleDTO);
        assertArticleUpdatableFieldsEquals(returnedArticle, getPersistedArticle(returnedArticle));

        insertedArticle = returnedArticle;
    }

    @Test
    @Transactional
    void createArticleWithExistingId() throws Exception {
        // Create the Article with an existing ID
        article.setId(1L);
        ArticleDTO articleDTO = articleMapper.toDto(article);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        article.setCreateddBy(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        article.setCreatedTime(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        article.setUpdatedBy(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        article.setUpdatedTime(null);

        // Create the Article, which fails.
        ArticleDTO articleDTO = articleMapper.toDto(article);

        restArticleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArticles() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].vendorArticleId").value(hasItem(DEFAULT_VENDOR_ARTICLE_ID)))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].puchasePrice").value(hasItem(DEFAULT_PUCHASE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].purchaseStore").value(hasItem(DEFAULT_PURCHASE_STORE)))
            .andExpect(jsonPath("$.[*].invoicePath").value(hasItem(DEFAULT_INVOICE_PATH)))
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
    void getArticle() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc
            .perform(get(ENTITY_API_URL_ID, article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO))
            .andExpect(jsonPath("$.vendorArticleId").value(DEFAULT_VENDOR_ARTICLE_ID))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.puchasePrice").value(DEFAULT_PUCHASE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.purchaseStore").value(DEFAULT_PURCHASE_STORE))
            .andExpect(jsonPath("$.invoicePath").value(DEFAULT_INVOICE_PATH))
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
    void getArticlesByIdFiltering() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        Long id = article.getId();

        defaultArticleFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultArticleFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultArticleFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllArticlesBySerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where serialNo equals to
        defaultArticleFiltering("serialNo.equals=" + DEFAULT_SERIAL_NO, "serialNo.equals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllArticlesBySerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where serialNo in
        defaultArticleFiltering("serialNo.in=" + DEFAULT_SERIAL_NO + "," + UPDATED_SERIAL_NO, "serialNo.in=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllArticlesBySerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where serialNo is not null
        defaultArticleFiltering("serialNo.specified=true", "serialNo.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesBySerialNoContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where serialNo contains
        defaultArticleFiltering("serialNo.contains=" + DEFAULT_SERIAL_NO, "serialNo.contains=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllArticlesBySerialNoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where serialNo does not contain
        defaultArticleFiltering("serialNo.doesNotContain=" + UPDATED_SERIAL_NO, "serialNo.doesNotContain=" + DEFAULT_SERIAL_NO);
    }

    @Test
    @Transactional
    void getAllArticlesByVendorArticleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where vendorArticleId equals to
        defaultArticleFiltering(
            "vendorArticleId.equals=" + DEFAULT_VENDOR_ARTICLE_ID,
            "vendorArticleId.equals=" + UPDATED_VENDOR_ARTICLE_ID
        );
    }

    @Test
    @Transactional
    void getAllArticlesByVendorArticleIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where vendorArticleId in
        defaultArticleFiltering(
            "vendorArticleId.in=" + DEFAULT_VENDOR_ARTICLE_ID + "," + UPDATED_VENDOR_ARTICLE_ID,
            "vendorArticleId.in=" + UPDATED_VENDOR_ARTICLE_ID
        );
    }

    @Test
    @Transactional
    void getAllArticlesByVendorArticleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where vendorArticleId is not null
        defaultArticleFiltering("vendorArticleId.specified=true", "vendorArticleId.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByVendorArticleIdContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where vendorArticleId contains
        defaultArticleFiltering(
            "vendorArticleId.contains=" + DEFAULT_VENDOR_ARTICLE_ID,
            "vendorArticleId.contains=" + UPDATED_VENDOR_ARTICLE_ID
        );
    }

    @Test
    @Transactional
    void getAllArticlesByVendorArticleIdNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where vendorArticleId does not contain
        defaultArticleFiltering(
            "vendorArticleId.doesNotContain=" + UPDATED_VENDOR_ARTICLE_ID,
            "vendorArticleId.doesNotContain=" + DEFAULT_VENDOR_ARTICLE_ID
        );
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseDate equals to
        defaultArticleFiltering("purchaseDate.equals=" + DEFAULT_PURCHASE_DATE, "purchaseDate.equals=" + UPDATED_PURCHASE_DATE);
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseDate in
        defaultArticleFiltering(
            "purchaseDate.in=" + DEFAULT_PURCHASE_DATE + "," + UPDATED_PURCHASE_DATE,
            "purchaseDate.in=" + UPDATED_PURCHASE_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseDate is not null
        defaultArticleFiltering("purchaseDate.specified=true", "purchaseDate.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseDate is greater than or equal to
        defaultArticleFiltering(
            "purchaseDate.greaterThanOrEqual=" + DEFAULT_PURCHASE_DATE,
            "purchaseDate.greaterThanOrEqual=" + UPDATED_PURCHASE_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseDate is less than or equal to
        defaultArticleFiltering(
            "purchaseDate.lessThanOrEqual=" + DEFAULT_PURCHASE_DATE,
            "purchaseDate.lessThanOrEqual=" + SMALLER_PURCHASE_DATE
        );
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseDate is less than
        defaultArticleFiltering("purchaseDate.lessThan=" + UPDATED_PURCHASE_DATE, "purchaseDate.lessThan=" + DEFAULT_PURCHASE_DATE);
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseDate is greater than
        defaultArticleFiltering("purchaseDate.greaterThan=" + SMALLER_PURCHASE_DATE, "purchaseDate.greaterThan=" + DEFAULT_PURCHASE_DATE);
    }

    @Test
    @Transactional
    void getAllArticlesByPuchasePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where puchasePrice equals to
        defaultArticleFiltering("puchasePrice.equals=" + DEFAULT_PUCHASE_PRICE, "puchasePrice.equals=" + UPDATED_PUCHASE_PRICE);
    }

    @Test
    @Transactional
    void getAllArticlesByPuchasePriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where puchasePrice in
        defaultArticleFiltering(
            "puchasePrice.in=" + DEFAULT_PUCHASE_PRICE + "," + UPDATED_PUCHASE_PRICE,
            "puchasePrice.in=" + UPDATED_PUCHASE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllArticlesByPuchasePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where puchasePrice is not null
        defaultArticleFiltering("puchasePrice.specified=true", "puchasePrice.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByPuchasePriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where puchasePrice is greater than or equal to
        defaultArticleFiltering(
            "puchasePrice.greaterThanOrEqual=" + DEFAULT_PUCHASE_PRICE,
            "puchasePrice.greaterThanOrEqual=" + UPDATED_PUCHASE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllArticlesByPuchasePriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where puchasePrice is less than or equal to
        defaultArticleFiltering(
            "puchasePrice.lessThanOrEqual=" + DEFAULT_PUCHASE_PRICE,
            "puchasePrice.lessThanOrEqual=" + SMALLER_PUCHASE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllArticlesByPuchasePriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where puchasePrice is less than
        defaultArticleFiltering("puchasePrice.lessThan=" + UPDATED_PUCHASE_PRICE, "puchasePrice.lessThan=" + DEFAULT_PUCHASE_PRICE);
    }

    @Test
    @Transactional
    void getAllArticlesByPuchasePriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where puchasePrice is greater than
        defaultArticleFiltering("puchasePrice.greaterThan=" + SMALLER_PUCHASE_PRICE, "puchasePrice.greaterThan=" + DEFAULT_PUCHASE_PRICE);
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseStoreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseStore equals to
        defaultArticleFiltering("purchaseStore.equals=" + DEFAULT_PURCHASE_STORE, "purchaseStore.equals=" + UPDATED_PURCHASE_STORE);
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseStoreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseStore in
        defaultArticleFiltering(
            "purchaseStore.in=" + DEFAULT_PURCHASE_STORE + "," + UPDATED_PURCHASE_STORE,
            "purchaseStore.in=" + UPDATED_PURCHASE_STORE
        );
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseStoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseStore is not null
        defaultArticleFiltering("purchaseStore.specified=true", "purchaseStore.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseStoreContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseStore contains
        defaultArticleFiltering("purchaseStore.contains=" + DEFAULT_PURCHASE_STORE, "purchaseStore.contains=" + UPDATED_PURCHASE_STORE);
    }

    @Test
    @Transactional
    void getAllArticlesByPurchaseStoreNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where purchaseStore does not contain
        defaultArticleFiltering(
            "purchaseStore.doesNotContain=" + UPDATED_PURCHASE_STORE,
            "purchaseStore.doesNotContain=" + DEFAULT_PURCHASE_STORE
        );
    }

    @Test
    @Transactional
    void getAllArticlesByInvoicePathIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where invoicePath equals to
        defaultArticleFiltering("invoicePath.equals=" + DEFAULT_INVOICE_PATH, "invoicePath.equals=" + UPDATED_INVOICE_PATH);
    }

    @Test
    @Transactional
    void getAllArticlesByInvoicePathIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where invoicePath in
        defaultArticleFiltering(
            "invoicePath.in=" + DEFAULT_INVOICE_PATH + "," + UPDATED_INVOICE_PATH,
            "invoicePath.in=" + UPDATED_INVOICE_PATH
        );
    }

    @Test
    @Transactional
    void getAllArticlesByInvoicePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where invoicePath is not null
        defaultArticleFiltering("invoicePath.specified=true", "invoicePath.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByInvoicePathContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where invoicePath contains
        defaultArticleFiltering("invoicePath.contains=" + DEFAULT_INVOICE_PATH, "invoicePath.contains=" + UPDATED_INVOICE_PATH);
    }

    @Test
    @Transactional
    void getAllArticlesByInvoicePathNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where invoicePath does not contain
        defaultArticleFiltering("invoicePath.doesNotContain=" + UPDATED_INVOICE_PATH, "invoicePath.doesNotContain=" + DEFAULT_INVOICE_PATH);
    }

    @Test
    @Transactional
    void getAllArticlesByIsValidatedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where isValidated equals to
        defaultArticleFiltering("isValidated.equals=" + DEFAULT_IS_VALIDATED, "isValidated.equals=" + UPDATED_IS_VALIDATED);
    }

    @Test
    @Transactional
    void getAllArticlesByIsValidatedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where isValidated in
        defaultArticleFiltering(
            "isValidated.in=" + DEFAULT_IS_VALIDATED + "," + UPDATED_IS_VALIDATED,
            "isValidated.in=" + UPDATED_IS_VALIDATED
        );
    }

    @Test
    @Transactional
    void getAllArticlesByIsValidatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where isValidated is not null
        defaultArticleFiltering("isValidated.specified=true", "isValidated.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByValidatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where validatedBy equals to
        defaultArticleFiltering("validatedBy.equals=" + DEFAULT_VALIDATED_BY, "validatedBy.equals=" + UPDATED_VALIDATED_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByValidatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where validatedBy in
        defaultArticleFiltering(
            "validatedBy.in=" + DEFAULT_VALIDATED_BY + "," + UPDATED_VALIDATED_BY,
            "validatedBy.in=" + UPDATED_VALIDATED_BY
        );
    }

    @Test
    @Transactional
    void getAllArticlesByValidatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where validatedBy is not null
        defaultArticleFiltering("validatedBy.specified=true", "validatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByValidatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where validatedBy contains
        defaultArticleFiltering("validatedBy.contains=" + DEFAULT_VALIDATED_BY, "validatedBy.contains=" + UPDATED_VALIDATED_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByValidatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where validatedBy does not contain
        defaultArticleFiltering("validatedBy.doesNotContain=" + UPDATED_VALIDATED_BY, "validatedBy.doesNotContain=" + DEFAULT_VALIDATED_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByValidatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where validatedTime equals to
        defaultArticleFiltering("validatedTime.equals=" + DEFAULT_VALIDATED_TIME, "validatedTime.equals=" + UPDATED_VALIDATED_TIME);
    }

    @Test
    @Transactional
    void getAllArticlesByValidatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where validatedTime in
        defaultArticleFiltering(
            "validatedTime.in=" + DEFAULT_VALIDATED_TIME + "," + UPDATED_VALIDATED_TIME,
            "validatedTime.in=" + UPDATED_VALIDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllArticlesByValidatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where validatedTime is not null
        defaultArticleFiltering("validatedTime.specified=true", "validatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByCreateddByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where createddBy equals to
        defaultArticleFiltering("createddBy.equals=" + DEFAULT_CREATEDD_BY, "createddBy.equals=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByCreateddByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where createddBy in
        defaultArticleFiltering("createddBy.in=" + DEFAULT_CREATEDD_BY + "," + UPDATED_CREATEDD_BY, "createddBy.in=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByCreateddByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where createddBy is not null
        defaultArticleFiltering("createddBy.specified=true", "createddBy.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByCreateddByContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where createddBy contains
        defaultArticleFiltering("createddBy.contains=" + DEFAULT_CREATEDD_BY, "createddBy.contains=" + UPDATED_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByCreateddByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where createddBy does not contain
        defaultArticleFiltering("createddBy.doesNotContain=" + UPDATED_CREATEDD_BY, "createddBy.doesNotContain=" + DEFAULT_CREATEDD_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByCreatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where createdTime equals to
        defaultArticleFiltering("createdTime.equals=" + DEFAULT_CREATED_TIME, "createdTime.equals=" + UPDATED_CREATED_TIME);
    }

    @Test
    @Transactional
    void getAllArticlesByCreatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where createdTime in
        defaultArticleFiltering(
            "createdTime.in=" + DEFAULT_CREATED_TIME + "," + UPDATED_CREATED_TIME,
            "createdTime.in=" + UPDATED_CREATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllArticlesByCreatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where createdTime is not null
        defaultArticleFiltering("createdTime.specified=true", "createdTime.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where updatedBy equals to
        defaultArticleFiltering("updatedBy.equals=" + DEFAULT_UPDATED_BY, "updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where updatedBy in
        defaultArticleFiltering("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY, "updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where updatedBy is not null
        defaultArticleFiltering("updatedBy.specified=true", "updatedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where updatedBy contains
        defaultArticleFiltering("updatedBy.contains=" + DEFAULT_UPDATED_BY, "updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where updatedBy does not contain
        defaultArticleFiltering("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY, "updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void getAllArticlesByUpdatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where updatedTime equals to
        defaultArticleFiltering("updatedTime.equals=" + DEFAULT_UPDATED_TIME, "updatedTime.equals=" + UPDATED_UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllArticlesByUpdatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where updatedTime in
        defaultArticleFiltering(
            "updatedTime.in=" + DEFAULT_UPDATED_TIME + "," + UPDATED_UPDATED_TIME,
            "updatedTime.in=" + UPDATED_UPDATED_TIME
        );
    }

    @Test
    @Transactional
    void getAllArticlesByUpdatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        // Get all the articleList where updatedTime is not null
        defaultArticleFiltering("updatedTime.specified=true", "updatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllArticlesByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            articleRepository.saveAndFlush(article);
            product = ProductResourceIT.createEntity();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        article.setProduct(product);
        articleRepository.saveAndFlush(article);
        Long productId = product.getId();
        // Get all the articleList where product equals to productId
        defaultArticleShouldBeFound("productId.equals=" + productId);

        // Get all the articleList where product equals to (productId + 1)
        defaultArticleShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllArticlesByCustomerIsEqualToSomething() throws Exception {
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            articleRepository.saveAndFlush(article);
            customer = CustomerResourceIT.createEntity();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        em.persist(customer);
        em.flush();
        article.setCustomer(customer);
        articleRepository.saveAndFlush(article);
        Long customerId = customer.getId();
        // Get all the articleList where customer equals to customerId
        defaultArticleShouldBeFound("customerId.equals=" + customerId);

        // Get all the articleList where customer equals to (customerId + 1)
        defaultArticleShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    private void defaultArticleFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultArticleShouldBeFound(shouldBeFound);
        defaultArticleShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultArticleShouldBeFound(String filter) throws Exception {
        restArticleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].vendorArticleId").value(hasItem(DEFAULT_VENDOR_ARTICLE_ID)))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].puchasePrice").value(hasItem(DEFAULT_PUCHASE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].purchaseStore").value(hasItem(DEFAULT_PURCHASE_STORE)))
            .andExpect(jsonPath("$.[*].invoicePath").value(hasItem(DEFAULT_INVOICE_PATH)))
            .andExpect(jsonPath("$.[*].isValidated").value(hasItem(DEFAULT_IS_VALIDATED)))
            .andExpect(jsonPath("$.[*].validatedBy").value(hasItem(DEFAULT_VALIDATED_BY)))
            .andExpect(jsonPath("$.[*].validatedTime").value(hasItem(DEFAULT_VALIDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));

        // Check, that the count call also returns 1
        restArticleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultArticleShouldNotBeFound(String filter) throws Exception {
        restArticleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restArticleMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticle() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the article
        Article updatedArticle = articleRepository.findById(article.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArticle are not directly saved in db
        em.detach(updatedArticle);
        updatedArticle
            .serialNo(UPDATED_SERIAL_NO)
            .vendorArticleId(UPDATED_VENDOR_ARTICLE_ID)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .puchasePrice(UPDATED_PUCHASE_PRICE)
            .purchaseStore(UPDATED_PURCHASE_STORE)
            .invoicePath(UPDATED_INVOICE_PATH)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        ArticleDTO articleDTO = articleMapper.toDto(updatedArticle);

        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArticleToMatchAllProperties(updatedArticle);
    }

    @Test
    @Transactional
    void putNonExistingArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleWithPatch() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the article using partial update
        Article partialUpdatedArticle = new Article();
        partialUpdatedArticle.setId(article.getId());

        partialUpdatedArticle
            .puchasePrice(UPDATED_PUCHASE_PRICE)
            .purchaseStore(UPDATED_PURCHASE_STORE)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArticle))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArticleUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedArticle, article), getPersistedArticle(article));
    }

    @Test
    @Transactional
    void fullUpdateArticleWithPatch() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the article using partial update
        Article partialUpdatedArticle = new Article();
        partialUpdatedArticle.setId(article.getId());

        partialUpdatedArticle
            .serialNo(UPDATED_SERIAL_NO)
            .vendorArticleId(UPDATED_VENDOR_ARTICLE_ID)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .puchasePrice(UPDATED_PUCHASE_PRICE)
            .purchaseStore(UPDATED_PURCHASE_STORE)
            .invoicePath(UPDATED_INVOICE_PATH)
            .isValidated(UPDATED_IS_VALIDATED)
            .validatedBy(UPDATED_VALIDATED_BY)
            .validatedTime(UPDATED_VALIDATED_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticle.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArticle))
            )
            .andExpect(status().isOk());

        // Validate the Article in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArticleUpdatableFieldsEquals(partialUpdatedArticle, getPersistedArticle(partialUpdatedArticle));
    }

    @Test
    @Transactional
    void patchNonExistingArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticle() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        article.setId(longCount.incrementAndGet());

        // Create the Article
        ArticleDTO articleDTO = articleMapper.toDto(article);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(articleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Article in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticle() throws Exception {
        // Initialize the database
        insertedArticle = articleRepository.saveAndFlush(article);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the article
        restArticleMockMvc
            .perform(delete(ENTITY_API_URL_ID, article.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return articleRepository.count();
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

    protected Article getPersistedArticle(Article article) {
        return articleRepository.findById(article.getId()).orElseThrow();
    }

    protected void assertPersistedArticleToMatchAllProperties(Article expectedArticle) {
        assertArticleAllPropertiesEquals(expectedArticle, getPersistedArticle(expectedArticle));
    }

    protected void assertPersistedArticleToMatchUpdatableProperties(Article expectedArticle) {
        assertArticleAllUpdatablePropertiesEquals(expectedArticle, getPersistedArticle(expectedArticle));
    }
}
