package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.FranchiseAllocationRuleSet;
import com.framasaas.repository.FranchiseAllocationRuleSetRepository;
import com.framasaas.service.criteria.FranchiseAllocationRuleSetCriteria;
import com.framasaas.service.dto.FranchiseAllocationRuleSetDTO;
import com.framasaas.service.mapper.FranchiseAllocationRuleSetMapper;
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
 * Service for executing complex queries for {@link FranchiseAllocationRuleSet} entities in the database.
 * The main input is a {@link FranchiseAllocationRuleSetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FranchiseAllocationRuleSetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FranchiseAllocationRuleSetQueryService extends QueryService<FranchiseAllocationRuleSet> {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseAllocationRuleSetQueryService.class);

    private final FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository;

    private final FranchiseAllocationRuleSetMapper franchiseAllocationRuleSetMapper;

    public FranchiseAllocationRuleSetQueryService(
        FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository,
        FranchiseAllocationRuleSetMapper franchiseAllocationRuleSetMapper
    ) {
        this.franchiseAllocationRuleSetRepository = franchiseAllocationRuleSetRepository;
        this.franchiseAllocationRuleSetMapper = franchiseAllocationRuleSetMapper;
    }

    /**
     * Return a {@link Page} of {@link FranchiseAllocationRuleSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FranchiseAllocationRuleSetDTO> findByCriteria(FranchiseAllocationRuleSetCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FranchiseAllocationRuleSet> specification = createSpecification(criteria);
        return franchiseAllocationRuleSetRepository.findAll(specification, page).map(franchiseAllocationRuleSetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FranchiseAllocationRuleSetCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<FranchiseAllocationRuleSet> specification = createSpecification(criteria);
        return franchiseAllocationRuleSetRepository.count(specification);
    }

    /**
     * Function to convert {@link FranchiseAllocationRuleSetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FranchiseAllocationRuleSet> createSpecification(FranchiseAllocationRuleSetCriteria criteria) {
        Specification<FranchiseAllocationRuleSet> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), FranchiseAllocationRuleSet_.id),
                buildStringSpecification(criteria.getName(), FranchiseAllocationRuleSet_.name),
                buildSpecification(criteria.getSortType(), FranchiseAllocationRuleSet_.sortType),
                buildRangeSpecification(criteria.getPriority(), FranchiseAllocationRuleSet_.priority),
                buildSpecification(criteria.getIsActive(), FranchiseAllocationRuleSet_.isActive),
                buildStringSpecification(criteria.getCreateddBy(), FranchiseAllocationRuleSet_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), FranchiseAllocationRuleSet_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), FranchiseAllocationRuleSet_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), FranchiseAllocationRuleSet_.updatedTime),
                buildSpecification(criteria.getFranchiseId(), root ->
                    root.join(FranchiseAllocationRuleSet_.franchises, JoinType.LEFT).get(Franchise_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(FranchiseAllocationRuleSet_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                )
            );
        }
        return specification;
    }
}
