package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.FranchiseStatusHistory;
import com.framasaas.repository.FranchiseStatusHistoryRepository;
import com.framasaas.service.criteria.FranchiseStatusHistoryCriteria;
import com.framasaas.service.dto.FranchiseStatusHistoryDTO;
import com.framasaas.service.mapper.FranchiseStatusHistoryMapper;
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
 * Service for executing complex queries for {@link FranchiseStatusHistory} entities in the database.
 * The main input is a {@link FranchiseStatusHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FranchiseStatusHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FranchiseStatusHistoryQueryService extends QueryService<FranchiseStatusHistory> {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseStatusHistoryQueryService.class);

    private final FranchiseStatusHistoryRepository franchiseStatusHistoryRepository;

    private final FranchiseStatusHistoryMapper franchiseStatusHistoryMapper;

    public FranchiseStatusHistoryQueryService(
        FranchiseStatusHistoryRepository franchiseStatusHistoryRepository,
        FranchiseStatusHistoryMapper franchiseStatusHistoryMapper
    ) {
        this.franchiseStatusHistoryRepository = franchiseStatusHistoryRepository;
        this.franchiseStatusHistoryMapper = franchiseStatusHistoryMapper;
    }

    /**
     * Return a {@link Page} of {@link FranchiseStatusHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FranchiseStatusHistoryDTO> findByCriteria(FranchiseStatusHistoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FranchiseStatusHistory> specification = createSpecification(criteria);
        return franchiseStatusHistoryRepository.findAll(specification, page).map(franchiseStatusHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FranchiseStatusHistoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<FranchiseStatusHistory> specification = createSpecification(criteria);
        return franchiseStatusHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link FranchiseStatusHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FranchiseStatusHistory> createSpecification(FranchiseStatusHistoryCriteria criteria) {
        Specification<FranchiseStatusHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), FranchiseStatusHistory_.id),
                buildSpecification(criteria.getFranchiseSatus(), FranchiseStatusHistory_.franchiseSatus),
                buildStringSpecification(criteria.getUpdatedBy(), FranchiseStatusHistory_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), FranchiseStatusHistory_.updatedTime),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(FranchiseStatusHistory_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getFranchiseId(), root ->
                    root.join(FranchiseStatusHistory_.franchise, JoinType.LEFT).get(Franchise_.id)
                )
            );
        }
        return specification;
    }
}
