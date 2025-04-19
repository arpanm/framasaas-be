package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.FranchisePerformanceHistory;
import com.framasaas.repository.FranchisePerformanceHistoryRepository;
import com.framasaas.service.criteria.FranchisePerformanceHistoryCriteria;
import com.framasaas.service.dto.FranchisePerformanceHistoryDTO;
import com.framasaas.service.mapper.FranchisePerformanceHistoryMapper;
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
 * Service for executing complex queries for {@link FranchisePerformanceHistory} entities in the database.
 * The main input is a {@link FranchisePerformanceHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FranchisePerformanceHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FranchisePerformanceHistoryQueryService extends QueryService<FranchisePerformanceHistory> {

    private static final Logger LOG = LoggerFactory.getLogger(FranchisePerformanceHistoryQueryService.class);

    private final FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository;

    private final FranchisePerformanceHistoryMapper franchisePerformanceHistoryMapper;

    public FranchisePerformanceHistoryQueryService(
        FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository,
        FranchisePerformanceHistoryMapper franchisePerformanceHistoryMapper
    ) {
        this.franchisePerformanceHistoryRepository = franchisePerformanceHistoryRepository;
        this.franchisePerformanceHistoryMapper = franchisePerformanceHistoryMapper;
    }

    /**
     * Return a {@link Page} of {@link FranchisePerformanceHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FranchisePerformanceHistoryDTO> findByCriteria(FranchisePerformanceHistoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FranchisePerformanceHistory> specification = createSpecification(criteria);
        return franchisePerformanceHistoryRepository.findAll(specification, page).map(franchisePerformanceHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FranchisePerformanceHistoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<FranchisePerformanceHistory> specification = createSpecification(criteria);
        return franchisePerformanceHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link FranchisePerformanceHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FranchisePerformanceHistory> createSpecification(FranchisePerformanceHistoryCriteria criteria) {
        Specification<FranchisePerformanceHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), FranchisePerformanceHistory_.id),
                buildRangeSpecification(criteria.getPerformanceScore(), FranchisePerformanceHistory_.performanceScore),
                buildSpecification(criteria.getPerformanceTag(), FranchisePerformanceHistory_.performanceTag),
                buildStringSpecification(criteria.getUpdatedBy(), FranchisePerformanceHistory_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), FranchisePerformanceHistory_.updatedTime),
                buildStringSpecification(criteria.getCreateddBy(), FranchisePerformanceHistory_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), FranchisePerformanceHistory_.createdTime),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(FranchisePerformanceHistory_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getFranchiseId(), root ->
                    root.join(FranchisePerformanceHistory_.franchise, JoinType.LEFT).get(Franchise_.id)
                )
            );
        }
        return specification;
    }
}
