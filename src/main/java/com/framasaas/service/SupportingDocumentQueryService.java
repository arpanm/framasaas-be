package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.SupportingDocument;
import com.framasaas.repository.SupportingDocumentRepository;
import com.framasaas.service.criteria.SupportingDocumentCriteria;
import com.framasaas.service.dto.SupportingDocumentDTO;
import com.framasaas.service.mapper.SupportingDocumentMapper;
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
 * Service for executing complex queries for {@link SupportingDocument} entities in the database.
 * The main input is a {@link SupportingDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SupportingDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupportingDocumentQueryService extends QueryService<SupportingDocument> {

    private static final Logger LOG = LoggerFactory.getLogger(SupportingDocumentQueryService.class);

    private final SupportingDocumentRepository supportingDocumentRepository;

    private final SupportingDocumentMapper supportingDocumentMapper;

    public SupportingDocumentQueryService(
        SupportingDocumentRepository supportingDocumentRepository,
        SupportingDocumentMapper supportingDocumentMapper
    ) {
        this.supportingDocumentRepository = supportingDocumentRepository;
        this.supportingDocumentMapper = supportingDocumentMapper;
    }

    /**
     * Return a {@link Page} of {@link SupportingDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupportingDocumentDTO> findByCriteria(SupportingDocumentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupportingDocument> specification = createSpecification(criteria);
        return supportingDocumentRepository.findAll(specification, page).map(supportingDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupportingDocumentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SupportingDocument> specification = createSpecification(criteria);
        return supportingDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link SupportingDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupportingDocument> createSpecification(SupportingDocumentCriteria criteria) {
        Specification<SupportingDocument> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), SupportingDocument_.id),
                buildStringSpecification(criteria.getDocumentName(), SupportingDocument_.documentName),
                buildSpecification(criteria.getDocumentType(), SupportingDocument_.documentType),
                buildSpecification(criteria.getDocumentFormat(), SupportingDocument_.documentFormat),
                buildRangeSpecification(criteria.getDocumentSize(), SupportingDocument_.documentSize),
                buildStringSpecification(criteria.getDocumentPath(), SupportingDocument_.documentPath),
                buildSpecification(criteria.getIsValidated(), SupportingDocument_.isValidated),
                buildStringSpecification(criteria.getValidatedBy(), SupportingDocument_.validatedBy),
                buildRangeSpecification(criteria.getValidatedTime(), SupportingDocument_.validatedTime),
                buildStringSpecification(criteria.getCreateddBy(), SupportingDocument_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), SupportingDocument_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), SupportingDocument_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), SupportingDocument_.updatedTime),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(SupportingDocument_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getFranchiseId(), root ->
                    root.join(SupportingDocument_.franchise, JoinType.LEFT).get(Franchise_.id)
                ),
                buildSpecification(criteria.getArticleId(), root -> root.join(SupportingDocument_.article, JoinType.LEFT).get(Article_.id)),
                buildSpecification(criteria.getArticleWarrantyId(), root ->
                    root.join(SupportingDocument_.articleWarranty, JoinType.LEFT).get(ArticleWarrantyDetails_.id)
                ),
                buildSpecification(criteria.getServiceOrderId(), root ->
                    root.join(SupportingDocument_.serviceOrder, JoinType.LEFT).get(ServiceOrder_.id)
                )
            );
        }
        return specification;
    }
}
