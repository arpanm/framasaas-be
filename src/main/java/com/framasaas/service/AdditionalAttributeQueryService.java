package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.AdditionalAttribute;
import com.framasaas.repository.AdditionalAttributeRepository;
import com.framasaas.service.criteria.AdditionalAttributeCriteria;
import com.framasaas.service.dto.AdditionalAttributeDTO;
import com.framasaas.service.mapper.AdditionalAttributeMapper;
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
 * Service for executing complex queries for {@link AdditionalAttribute} entities in the database.
 * The main input is a {@link AdditionalAttributeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AdditionalAttributeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdditionalAttributeQueryService extends QueryService<AdditionalAttribute> {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalAttributeQueryService.class);

    private final AdditionalAttributeRepository additionalAttributeRepository;

    private final AdditionalAttributeMapper additionalAttributeMapper;

    public AdditionalAttributeQueryService(
        AdditionalAttributeRepository additionalAttributeRepository,
        AdditionalAttributeMapper additionalAttributeMapper
    ) {
        this.additionalAttributeRepository = additionalAttributeRepository;
        this.additionalAttributeMapper = additionalAttributeMapper;
    }

    /**
     * Return a {@link Page} of {@link AdditionalAttributeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdditionalAttributeDTO> findByCriteria(AdditionalAttributeCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdditionalAttribute> specification = createSpecification(criteria);
        return additionalAttributeRepository.findAll(specification, page).map(additionalAttributeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdditionalAttributeCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<AdditionalAttribute> specification = createSpecification(criteria);
        return additionalAttributeRepository.count(specification);
    }

    /**
     * Function to convert {@link AdditionalAttributeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdditionalAttribute> createSpecification(AdditionalAttributeCriteria criteria) {
        Specification<AdditionalAttribute> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), AdditionalAttribute_.id),
                buildStringSpecification(criteria.getAttributeName(), AdditionalAttribute_.attributeName),
                buildStringSpecification(criteria.getAttributeValue(), AdditionalAttribute_.attributeValue),
                buildSpecification(criteria.getAttributeType(), AdditionalAttribute_.attributeType),
                buildStringSpecification(criteria.getCreateddBy(), AdditionalAttribute_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), AdditionalAttribute_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), AdditionalAttribute_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), AdditionalAttribute_.updatedTime),
                buildSpecification(criteria.getAdditionalAttributePossibleValueId(), root ->
                    root
                        .join(AdditionalAttribute_.additionalAttributePossibleValues, JoinType.LEFT)
                        .get(AdditionalAttributePossibleValue_.id)
                ),
                buildSpecification(criteria.getFranchiseId(), root ->
                    root.join(AdditionalAttribute_.franchise, JoinType.LEFT).get(Franchise_.id)
                ),
                buildSpecification(criteria.getFranchiseStatusId(), root ->
                    root.join(AdditionalAttribute_.franchiseStatus, JoinType.LEFT).get(FranchiseStatusHistory_.id)
                ),
                buildSpecification(criteria.getFranchisePerformanceId(), root ->
                    root.join(AdditionalAttribute_.franchisePerformance, JoinType.LEFT).get(FranchisePerformanceHistory_.id)
                ),
                buildSpecification(criteria.getBrandId(), root -> root.join(AdditionalAttribute_.brand, JoinType.LEFT).get(Brand_.id)),
                buildSpecification(criteria.getCategoryId(), root ->
                    root.join(AdditionalAttribute_.category, JoinType.LEFT).get(Category_.id)
                ),
                buildSpecification(criteria.getAddressId(), root -> root.join(AdditionalAttribute_.address, JoinType.LEFT).get(Address_.id)
                ),
                buildSpecification(criteria.getLocationId(), root ->
                    root.join(AdditionalAttribute_.location, JoinType.LEFT).get(LocationMapping_.id)
                ),
                buildSpecification(criteria.getFranchiseUserId(), root ->
                    root.join(AdditionalAttribute_.franchiseUser, JoinType.LEFT).get(FranchiseUser_.id)
                ),
                buildSpecification(criteria.getCustomerId(), root ->
                    root.join(AdditionalAttribute_.customer, JoinType.LEFT).get(Customer_.id)
                ),
                buildSpecification(criteria.getSupportDocumentId(), root ->
                    root.join(AdditionalAttribute_.supportDocument, JoinType.LEFT).get(SupportingDocument_.id)
                ),
                buildSpecification(criteria.getProductId(), root -> root.join(AdditionalAttribute_.product, JoinType.LEFT).get(Product_.id)
                ),
                buildSpecification(criteria.getHsnId(), root -> root.join(AdditionalAttribute_.hsn, JoinType.LEFT).get(Hsn_.id)),
                buildSpecification(criteria.getPriceHistoryId(), root ->
                    root.join(AdditionalAttribute_.priceHistory, JoinType.LEFT).get(ProductPriceHistory_.id)
                ),
                buildSpecification(criteria.getWarrantyMasterId(), root ->
                    root.join(AdditionalAttribute_.warrantyMaster, JoinType.LEFT).get(WarrantyMaster_.id)
                ),
                buildSpecification(criteria.getWarrantyMasterPriceHistoryId(), root ->
                    root.join(AdditionalAttribute_.warrantyMasterPriceHistory, JoinType.LEFT).get(WarrantyMasterPriceHistory_.id)
                ),
                buildSpecification(criteria.getArticleId(), root -> root.join(AdditionalAttribute_.article, JoinType.LEFT).get(Article_.id)
                ),
                buildSpecification(criteria.getArticleWarrantyId(), root ->
                    root.join(AdditionalAttribute_.articleWarranty, JoinType.LEFT).get(ArticleWarrantyDetails_.id)
                ),
                buildSpecification(criteria.getServiceOrderId(), root ->
                    root.join(AdditionalAttribute_.serviceOrder, JoinType.LEFT).get(ServiceOrder_.id)
                ),
                buildSpecification(criteria.getServiceOrderPaymentId(), root ->
                    root.join(AdditionalAttribute_.serviceOrderPayment, JoinType.LEFT).get(ServiceOrderPayment_.id)
                ),
                buildSpecification(criteria.getServiceOrderFranchiseAssignmentId(), root ->
                    root.join(AdditionalAttribute_.serviceOrderFranchiseAssignment, JoinType.LEFT).get(ServiceOrderFranchiseAssignment_.id)
                ),
                buildSpecification(criteria.getServiceOrderFieldAgentAssignmentId(), root ->
                    root
                        .join(AdditionalAttribute_.serviceOrderFieldAgentAssignment, JoinType.LEFT)
                        .get(ServiceOrderFieldAgentAssignment_.id)
                ),
                buildSpecification(criteria.getFranchiseAllocationRuleSetId(), root ->
                    root.join(AdditionalAttribute_.franchiseAllocationRuleSet, JoinType.LEFT).get(FranchiseAllocationRuleSet_.id)
                ),
                buildSpecification(criteria.getFranchiseAllocationRuleId(), root ->
                    root.join(AdditionalAttribute_.franchiseAllocationRule, JoinType.LEFT).get(FranchiseAllocationRule_.id)
                ),
                buildSpecification(criteria.getFieldAgentSkillRuleSetId(), root ->
                    root.join(AdditionalAttribute_.fieldAgentSkillRuleSet, JoinType.LEFT).get(FieldAgentSkillRuleSet_.id)
                ),
                buildSpecification(criteria.getFieldAgentSkillRuleId(), root ->
                    root.join(AdditionalAttribute_.fieldAgentSkillRule, JoinType.LEFT).get(FieldAgentSkillRule_.id)
                ),
                buildSpecification(criteria.getInventoryLocationId(), root ->
                    root.join(AdditionalAttribute_.inventoryLocation, JoinType.LEFT).get(InventoryLocation_.id)
                ),
                buildSpecification(criteria.getInventoryId(), root ->
                    root.join(AdditionalAttribute_.inventory, JoinType.LEFT).get(Inventory_.id)
                ),
                buildSpecification(criteria.getDocumentId(), root ->
                    root.join(AdditionalAttribute_.document, JoinType.LEFT).get(FranchiseDocument_.id)
                ),
                buildSpecification(criteria.getArticleWarrantyDocumentId(), root ->
                    root.join(AdditionalAttribute_.articleWarrantyDocument, JoinType.LEFT).get(ArticleWarrantyDetailsDocument_.id)
                ),
                buildSpecification(criteria.getServiceOrderAssignmentId(), root ->
                    root.join(AdditionalAttribute_.serviceOrderAssignment, JoinType.LEFT).get(ServiceOrderAssignment_.id)
                )
            );
        }
        return specification;
    }
}
