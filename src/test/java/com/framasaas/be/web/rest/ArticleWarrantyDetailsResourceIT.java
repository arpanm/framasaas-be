package com.framasaas.be.web.rest;

import static com.framasaas.be.domain.ArticleWarrantyDetailsAsserts.*;
import static com.framasaas.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framasaas.be.IntegrationTest;
import com.framasaas.be.domain.ArticleWarrantyDetails;
import com.framasaas.be.domain.enumeration.WarrantyType;
import com.framasaas.be.repository.ArticleWarrantyDetailsRepository;
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
 * Integration tests for the {@link ArticleWarrantyDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticleWarrantyDetailsResourceIT {

    private static final WarrantyType DEFAULT_WARRANTY_TYPE = WarrantyType.BRANDFREEWARRANTY;
    private static final WarrantyType UPDATED_WARRANTY_TYPE = WarrantyType.EXTENDEDPAIDWARRANTY;

    private static final String DEFAULT_VENDOR_ARTICLE_WARRANTY_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_ARTICLE_WARRANTY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_WARRANTY_MASTER_ID = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_WARRANTY_MASTER_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/article-warranty-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleWarrantyDetailsMockMvc;

    private ArticleWarrantyDetails articleWarrantyDetails;

    private ArticleWarrantyDetails insertedArticleWarrantyDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleWarrantyDetails createEntity() {
        return new ArticleWarrantyDetails()
            .warrantyType(DEFAULT_WARRANTY_TYPE)
            .vendorArticleWarrantyId(DEFAULT_VENDOR_ARTICLE_WARRANTY_ID)
            .vendorWarrantyMasterId(DEFAULT_VENDOR_WARRANTY_MASTER_ID)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
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
    public static ArticleWarrantyDetails createUpdatedEntity() {
        return new ArticleWarrantyDetails()
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .vendorArticleWarrantyId(UPDATED_VENDOR_ARTICLE_WARRANTY_ID)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        articleWarrantyDetails = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedArticleWarrantyDetails != null) {
            articleWarrantyDetailsRepository.delete(insertedArticleWarrantyDetails);
            insertedArticleWarrantyDetails = null;
        }
    }

    @Test
    @Transactional
    void createArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ArticleWarrantyDetails
        var returnedArticleWarrantyDetails = om.readValue(
            restArticleWarrantyDetailsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetails)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ArticleWarrantyDetails.class
        );

        // Validate the ArticleWarrantyDetails in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertArticleWarrantyDetailsUpdatableFieldsEquals(
            returnedArticleWarrantyDetails,
            getPersistedArticleWarrantyDetails(returnedArticleWarrantyDetails)
        );

        insertedArticleWarrantyDetails = returnedArticleWarrantyDetails;
    }

    @Test
    @Transactional
    void createArticleWarrantyDetailsWithExistingId() throws Exception {
        // Create the ArticleWarrantyDetails with an existing ID
        articleWarrantyDetails.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleWarrantyDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetails.setCreateddBy(null);

        // Create the ArticleWarrantyDetails, which fails.

        restArticleWarrantyDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetails.setCreatedTime(null);

        // Create the ArticleWarrantyDetails, which fails.

        restArticleWarrantyDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetails.setUpdatedBy(null);

        // Create the ArticleWarrantyDetails, which fails.

        restArticleWarrantyDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        articleWarrantyDetails.setUpdatedTime(null);

        // Create the ArticleWarrantyDetails, which fails.

        restArticleWarrantyDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetails)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArticleWarrantyDetails() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get all the articleWarrantyDetailsList
        restArticleWarrantyDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleWarrantyDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].warrantyType").value(hasItem(DEFAULT_WARRANTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vendorArticleWarrantyId").value(hasItem(DEFAULT_VENDOR_ARTICLE_WARRANTY_ID)))
            .andExpect(jsonPath("$.[*].vendorWarrantyMasterId").value(hasItem(DEFAULT_VENDOR_WARRANTY_MASTER_ID)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getArticleWarrantyDetails() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        // Get the articleWarrantyDetails
        restArticleWarrantyDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, articleWarrantyDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleWarrantyDetails.getId().intValue()))
            .andExpect(jsonPath("$.warrantyType").value(DEFAULT_WARRANTY_TYPE.toString()))
            .andExpect(jsonPath("$.vendorArticleWarrantyId").value(DEFAULT_VENDOR_ARTICLE_WARRANTY_ID))
            .andExpect(jsonPath("$.vendorWarrantyMasterId").value(DEFAULT_VENDOR_WARRANTY_MASTER_ID))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingArticleWarrantyDetails() throws Exception {
        // Get the articleWarrantyDetails
        restArticleWarrantyDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticleWarrantyDetails() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the articleWarrantyDetails
        ArticleWarrantyDetails updatedArticleWarrantyDetails = articleWarrantyDetailsRepository
            .findById(articleWarrantyDetails.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedArticleWarrantyDetails are not directly saved in db
        em.detach(updatedArticleWarrantyDetails);
        updatedArticleWarrantyDetails
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .vendorArticleWarrantyId(UPDATED_VENDOR_ARTICLE_WARRANTY_ID)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restArticleWarrantyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArticleWarrantyDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedArticleWarrantyDetails))
            )
            .andExpect(status().isOk());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArticleWarrantyDetailsToMatchAllProperties(updatedArticleWarrantyDetails);
    }

    @Test
    @Transactional
    void putNonExistingArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articleWarrantyDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleWarrantyDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(articleWarrantyDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(articleWarrantyDetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticleWarrantyDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the articleWarrantyDetails using partial update
        ArticleWarrantyDetails partialUpdatedArticleWarrantyDetails = new ArticleWarrantyDetails();
        partialUpdatedArticleWarrantyDetails.setId(articleWarrantyDetails.getId());

        partialUpdatedArticleWarrantyDetails
            .vendorArticleWarrantyId(UPDATED_VENDOR_ARTICLE_WARRANTY_ID)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restArticleWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleWarrantyDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArticleWarrantyDetails))
            )
            .andExpect(status().isOk());

        // Validate the ArticleWarrantyDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArticleWarrantyDetailsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedArticleWarrantyDetails, articleWarrantyDetails),
            getPersistedArticleWarrantyDetails(articleWarrantyDetails)
        );
    }

    @Test
    @Transactional
    void fullUpdateArticleWarrantyDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the articleWarrantyDetails using partial update
        ArticleWarrantyDetails partialUpdatedArticleWarrantyDetails = new ArticleWarrantyDetails();
        partialUpdatedArticleWarrantyDetails.setId(articleWarrantyDetails.getId());

        partialUpdatedArticleWarrantyDetails
            .warrantyType(UPDATED_WARRANTY_TYPE)
            .vendorArticleWarrantyId(UPDATED_VENDOR_ARTICLE_WARRANTY_ID)
            .vendorWarrantyMasterId(UPDATED_VENDOR_WARRANTY_MASTER_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restArticleWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticleWarrantyDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArticleWarrantyDetails))
            )
            .andExpect(status().isOk());

        // Validate the ArticleWarrantyDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArticleWarrantyDetailsUpdatableFieldsEquals(
            partialUpdatedArticleWarrantyDetails,
            getPersistedArticleWarrantyDetails(partialUpdatedArticleWarrantyDetails)
        );
    }

    @Test
    @Transactional
    void patchNonExistingArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articleWarrantyDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleWarrantyDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(articleWarrantyDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticleWarrantyDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        articleWarrantyDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticleWarrantyDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(articleWarrantyDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArticleWarrantyDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticleWarrantyDetails() throws Exception {
        // Initialize the database
        insertedArticleWarrantyDetails = articleWarrantyDetailsRepository.saveAndFlush(articleWarrantyDetails);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the articleWarrantyDetails
        restArticleWarrantyDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, articleWarrantyDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return articleWarrantyDetailsRepository.count();
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

    protected ArticleWarrantyDetails getPersistedArticleWarrantyDetails(ArticleWarrantyDetails articleWarrantyDetails) {
        return articleWarrantyDetailsRepository.findById(articleWarrantyDetails.getId()).orElseThrow();
    }

    protected void assertPersistedArticleWarrantyDetailsToMatchAllProperties(ArticleWarrantyDetails expectedArticleWarrantyDetails) {
        assertArticleWarrantyDetailsAllPropertiesEquals(
            expectedArticleWarrantyDetails,
            getPersistedArticleWarrantyDetails(expectedArticleWarrantyDetails)
        );
    }

    protected void assertPersistedArticleWarrantyDetailsToMatchUpdatableProperties(ArticleWarrantyDetails expectedArticleWarrantyDetails) {
        assertArticleWarrantyDetailsAllUpdatablePropertiesEquals(
            expectedArticleWarrantyDetails,
            getPersistedArticleWarrantyDetails(expectedArticleWarrantyDetails)
        );
    }
}
