package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.repository.FranchiseAllocationRuleRepository;
import com.framasaas.service.criteria.FranchiseAllocationRuleCriteria;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import com.framasaas.service.mapper.FranchiseAllocationRuleMapper;
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
 * Service for executing complex queries for {@link FranchiseAllocationRule} entities in the database.
 * The main input is a {@link FranchiseAllocationRuleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FranchiseAllocationRuleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FranchiseAllocationRuleQueryService extends QueryService<FranchiseAllocationRule> {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseAllocationRuleQueryService.class);

    private final FranchiseAllocationRuleRepository franchiseAllocationRuleRepository;

    private final FranchiseAllocationRuleMapper franchiseAllocationRuleMapper;

    public FranchiseAllocationRuleQueryService(
        FranchiseAllocationRuleRepository franchiseAllocationRuleRepository,
        FranchiseAllocationRuleMapper franchiseAllocationRuleMapper
    ) {
        this.franchiseAllocationRuleRepository = franchiseAllocationRuleRepository;
        this.franchiseAllocationRuleMapper = franchiseAllocationRuleMapper;
    }

    /**
     * Return a {@link Page} of {@link FranchiseAllocationRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FranchiseAllocationRuleDTO> findByCriteria(FranchiseAllocationRuleCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FranchiseAllocationRule> specification = createSpecification(criteria);
        return franchiseAllocationRuleRepository.findAll(specification, page).map(franchiseAllocationRuleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FranchiseAllocationRuleCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<FranchiseAllocationRule> specification = createSpecification(criteria);
        return franchiseAllocationRuleRepository.count(specification);
    }

    /**
     * Function to convert {@link FranchiseAllocationRuleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FranchiseAllocationRule> createSpecification(FranchiseAllocationRuleCriteria criteria) {
        Specification<FranchiseAllocationRule> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), FranchiseAllocationRule_.id),
                buildSpecification(criteria.getRuleType(), FranchiseAllocationRule_.ruleType),
                buildSpecification(criteria.getJoinType(), FranchiseAllocationRule_.joinType),
                buildStringSpecification(criteria.getCreateddBy(), FranchiseAllocationRule_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), FranchiseAllocationRule_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), FranchiseAllocationRule_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), FranchiseAllocationRule_.updatedTime),
                buildSpecification(criteria.getBrandId(), root -> root.join(FranchiseAllocationRule_.brands, JoinType.LEFT).get(Brand_.id)),
                buildSpecification(criteria.getCategoryId(), root ->
                    root.join(FranchiseAllocationRule_.categories, JoinType.LEFT).get(Category_.id)
                ),
                buildSpecification(criteria.getPincodeId(), root ->
                    root.join(FranchiseAllocationRule_.pincodes, JoinType.LEFT).get(Pincode_.id)
                ),
                buildSpecification(criteria.getLocationMappingId(), root ->
                    root.join(FranchiseAllocationRule_.locationMappings, JoinType.LEFT).get(LocationMapping_.id)
                ),
                buildSpecification(criteria.getLanguageMappingId(), root ->
                    root.join(FranchiseAllocationRule_.languageMappings, JoinType.LEFT).get(LanguageMapping_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(FranchiseAllocationRule_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                )
            );
        }
        return specification;
    }
}
