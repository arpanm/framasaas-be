package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.ArticleWarrantyDetails;
import com.framasaas.repository.ArticleWarrantyDetailsRepository;
import com.framasaas.service.criteria.ArticleWarrantyDetailsCriteria;
import com.framasaas.service.dto.ArticleWarrantyDetailsDTO;
import com.framasaas.service.mapper.ArticleWarrantyDetailsMapper;
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
 * Service for executing complex queries for {@link ArticleWarrantyDetails} entities in the database.
 * The main input is a {@link ArticleWarrantyDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ArticleWarrantyDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ArticleWarrantyDetailsQueryService extends QueryService<ArticleWarrantyDetails> {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleWarrantyDetailsQueryService.class);

    private final ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository;

    private final ArticleWarrantyDetailsMapper articleWarrantyDetailsMapper;

    public ArticleWarrantyDetailsQueryService(
        ArticleWarrantyDetailsRepository articleWarrantyDetailsRepository,
        ArticleWarrantyDetailsMapper articleWarrantyDetailsMapper
    ) {
        this.articleWarrantyDetailsRepository = articleWarrantyDetailsRepository;
        this.articleWarrantyDetailsMapper = articleWarrantyDetailsMapper;
    }

    /**
     * Return a {@link Page} of {@link ArticleWarrantyDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticleWarrantyDetailsDTO> findByCriteria(ArticleWarrantyDetailsCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ArticleWarrantyDetails> specification = createSpecification(criteria);
        return articleWarrantyDetailsRepository.findAll(specification, page).map(articleWarrantyDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ArticleWarrantyDetailsCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ArticleWarrantyDetails> specification = createSpecification(criteria);
        return articleWarrantyDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link ArticleWarrantyDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ArticleWarrantyDetails> createSpecification(ArticleWarrantyDetailsCriteria criteria) {
        Specification<ArticleWarrantyDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), ArticleWarrantyDetails_.id),
                buildSpecification(criteria.getWarrantyType(), ArticleWarrantyDetails_.warrantyType),
                buildStringSpecification(criteria.getVendorArticleWarrantyId(), ArticleWarrantyDetails_.vendorArticleWarrantyId),
                buildStringSpecification(criteria.getVendorWarrantyMasterId(), ArticleWarrantyDetails_.vendorWarrantyMasterId),
                buildRangeSpecification(criteria.getStartDate(), ArticleWarrantyDetails_.startDate),
                buildRangeSpecification(criteria.getEndDate(), ArticleWarrantyDetails_.endDate),
                buildSpecification(criteria.getSoldBy(), ArticleWarrantyDetails_.soldBy),
                buildStringSpecification(criteria.getSoldByUser(), ArticleWarrantyDetails_.soldByUser),
                buildRangeSpecification(criteria.getSoldDate(), ArticleWarrantyDetails_.soldDate),
                buildSpecification(criteria.getIsValidated(), ArticleWarrantyDetails_.isValidated),
                buildStringSpecification(criteria.getValidatedBy(), ArticleWarrantyDetails_.validatedBy),
                buildRangeSpecification(criteria.getValidatedTime(), ArticleWarrantyDetails_.validatedTime),
                buildSpecification(criteria.getIsActive(), ArticleWarrantyDetails_.isActive),
                buildStringSpecification(criteria.getCreateddBy(), ArticleWarrantyDetails_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), ArticleWarrantyDetails_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), ArticleWarrantyDetails_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), ArticleWarrantyDetails_.updatedTime),
                buildSpecification(criteria.getSupportingDocumentId(), root ->
                    root.join(ArticleWarrantyDetails_.supportingDocuments, JoinType.LEFT).get(SupportingDocument_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(ArticleWarrantyDetails_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getArticleId(), root ->
                    root.join(ArticleWarrantyDetails_.article, JoinType.LEFT).get(Article_.id)
                ),
                buildSpecification(criteria.getWarrantyMasterId(), root ->
                    root.join(ArticleWarrantyDetails_.warrantyMaster, JoinType.LEFT).get(WarrantyMaster_.id)
                )
            );
        }
        return specification;
    }
}
