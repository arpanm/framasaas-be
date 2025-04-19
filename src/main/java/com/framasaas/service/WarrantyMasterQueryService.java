package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.WarrantyMaster;
import com.framasaas.repository.WarrantyMasterRepository;
import com.framasaas.service.criteria.WarrantyMasterCriteria;
import com.framasaas.service.dto.WarrantyMasterDTO;
import com.framasaas.service.mapper.WarrantyMasterMapper;
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
 * Service for executing complex queries for {@link WarrantyMaster} entities in the database.
 * The main input is a {@link WarrantyMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link WarrantyMasterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WarrantyMasterQueryService extends QueryService<WarrantyMaster> {

    private static final Logger LOG = LoggerFactory.getLogger(WarrantyMasterQueryService.class);

    private final WarrantyMasterRepository warrantyMasterRepository;

    private final WarrantyMasterMapper warrantyMasterMapper;

    public WarrantyMasterQueryService(WarrantyMasterRepository warrantyMasterRepository, WarrantyMasterMapper warrantyMasterMapper) {
        this.warrantyMasterRepository = warrantyMasterRepository;
        this.warrantyMasterMapper = warrantyMasterMapper;
    }

    /**
     * Return a {@link Page} of {@link WarrantyMasterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WarrantyMasterDTO> findByCriteria(WarrantyMasterCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WarrantyMaster> specification = createSpecification(criteria);
        return warrantyMasterRepository
            .fetchBagRelationships(warrantyMasterRepository.findAll(specification, page))
            .map(warrantyMasterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WarrantyMasterCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<WarrantyMaster> specification = createSpecification(criteria);
        return warrantyMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link WarrantyMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WarrantyMaster> createSpecification(WarrantyMasterCriteria criteria) {
        Specification<WarrantyMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), WarrantyMaster_.id),
                buildStringSpecification(criteria.getName(), WarrantyMaster_.name),
                buildStringSpecification(criteria.getVendorWarrantyMasterId(), WarrantyMaster_.vendorWarrantyMasterId),
                buildSpecification(criteria.getWarrantyType(), WarrantyMaster_.warrantyType),
                buildStringSpecification(criteria.getDescription(), WarrantyMaster_.description),
                buildRangeSpecification(criteria.getPrice(), WarrantyMaster_.price),
                buildRangeSpecification(criteria.getTax(), WarrantyMaster_.tax),
                buildRangeSpecification(criteria.getFranchiseCommission(), WarrantyMaster_.franchiseCommission),
                buildRangeSpecification(criteria.getFranchiseTax(), WarrantyMaster_.franchiseTax),
                buildRangeSpecification(criteria.getPeriodInMonths(), WarrantyMaster_.periodInMonths),
                buildRangeSpecification(criteria.getTaxRate(), WarrantyMaster_.taxRate),
                buildStringSpecification(criteria.getCoverage(), WarrantyMaster_.coverage),
                buildStringSpecification(criteria.getExclusion(), WarrantyMaster_.exclusion),
                buildSpecification(criteria.getIsActive(), WarrantyMaster_.isActive),
                buildStringSpecification(criteria.getCreateddBy(), WarrantyMaster_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), WarrantyMaster_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), WarrantyMaster_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), WarrantyMaster_.updatedTime),
                buildSpecification(criteria.getWarrantyMasterPriceHistoryId(), root ->
                    root.join(WarrantyMaster_.warrantyMasterPriceHistories, JoinType.LEFT).get(WarrantyMasterPriceHistory_.id)
                ),
                buildSpecification(criteria.getArticleWarrantyDetailsId(), root ->
                    root.join(WarrantyMaster_.articleWarrantyDetails, JoinType.LEFT).get(ArticleWarrantyDetails_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(WarrantyMaster_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getCoveredSpareId(), root ->
                    root.join(WarrantyMaster_.coveredSpares, JoinType.LEFT).get(Product_.id)
                ),
                buildSpecification(criteria.getProductId(), root -> root.join(WarrantyMaster_.product, JoinType.LEFT).get(Product_.id))
            );
        }
        return specification;
    }
}
