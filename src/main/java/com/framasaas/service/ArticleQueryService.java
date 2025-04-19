package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.Article;
import com.framasaas.repository.ArticleRepository;
import com.framasaas.service.criteria.ArticleCriteria;
import com.framasaas.service.dto.ArticleDTO;
import com.framasaas.service.mapper.ArticleMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Article} entities in the database.
 * The main input is a {@link ArticleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ArticleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ArticleQueryService extends QueryService<Article> {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleQueryService.class);

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public ArticleQueryService(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    /**
     * Return a {@link Page} of {@link ArticleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticleDTO> findByCriteria(ArticleCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Article> specification = createSpecification(criteria);
        return articleRepository.findAll(specification, page).map(articleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ArticleCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Article> specification = createSpecification(criteria);
        return articleRepository.count(specification);
    }

    /**
     * Function to convert {@link ArticleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Article> createSpecification(ArticleCriteria criteria) {
        Specification<Article> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Article_.id),
                buildStringSpecification(criteria.getSerialNo(), Article_.serialNo),
                buildStringSpecification(criteria.getVendorArticleId(), Article_.vendorArticleId),
                buildRangeSpecification(criteria.getPurchaseDate(), Article_.purchaseDate),
                buildRangeSpecification(criteria.getPuchasePrice(), Article_.puchasePrice),
                buildStringSpecification(criteria.getPurchaseStore(), Article_.purchaseStore),
                buildStringSpecification(criteria.getInvoicePath(), Article_.invoicePath),
                buildSpecification(criteria.getIsValidated(), Article_.isValidated),
                buildStringSpecification(criteria.getValidatedBy(), Article_.validatedBy),
                buildRangeSpecification(criteria.getValidatedTime(), Article_.validatedTime),
                buildStringSpecification(criteria.getCreateddBy(), Article_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), Article_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), Article_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), Article_.updatedTime),
                buildSpecification(criteria.getSupportingDocumentId(), root ->
                    root.join(Article_.supportingDocuments, JoinType.LEFT).get(SupportingDocument_.id)
                ),
                buildSpecification(criteria.getArticleWarrantyDetailsId(), root ->
                    root.join(Article_.articleWarrantyDetails, JoinType.LEFT).get(ArticleWarrantyDetails_.id)
                ),
                buildSpecification(criteria.getServiceOrderId(), root ->
                    root.join(Article_.serviceOrders, JoinType.LEFT).get(ServiceOrder_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(Article_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getProductId(), root -> root.join(Article_.product, JoinType.LEFT).get(Product_.id)),
                buildSpecification(criteria.getCustomerId(), root -> root.join(Article_.customer, JoinType.LEFT).get(Customer_.id))
            );
        }
        return specification;
    }
}
