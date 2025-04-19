package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.FranchiseUserStatusHistory;
import com.framasaas.repository.FranchiseUserStatusHistoryRepository;
import com.framasaas.service.criteria.FranchiseUserStatusHistoryCriteria;
import com.framasaas.service.dto.FranchiseUserStatusHistoryDTO;
import com.framasaas.service.mapper.FranchiseUserStatusHistoryMapper;
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
 * Service for executing complex queries for {@link FranchiseUserStatusHistory} entities in the database.
 * The main input is a {@link FranchiseUserStatusHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FranchiseUserStatusHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FranchiseUserStatusHistoryQueryService extends QueryService<FranchiseUserStatusHistory> {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseUserStatusHistoryQueryService.class);

    private final FranchiseUserStatusHistoryRepository franchiseUserStatusHistoryRepository;

    private final FranchiseUserStatusHistoryMapper franchiseUserStatusHistoryMapper;

    public FranchiseUserStatusHistoryQueryService(
        FranchiseUserStatusHistoryRepository franchiseUserStatusHistoryRepository,
        FranchiseUserStatusHistoryMapper franchiseUserStatusHistoryMapper
    ) {
        this.franchiseUserStatusHistoryRepository = franchiseUserStatusHistoryRepository;
        this.franchiseUserStatusHistoryMapper = franchiseUserStatusHistoryMapper;
    }

    /**
     * Return a {@link Page} of {@link FranchiseUserStatusHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FranchiseUserStatusHistoryDTO> findByCriteria(FranchiseUserStatusHistoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FranchiseUserStatusHistory> specification = createSpecification(criteria);
        return franchiseUserStatusHistoryRepository.findAll(specification, page).map(franchiseUserStatusHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FranchiseUserStatusHistoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<FranchiseUserStatusHistory> specification = createSpecification(criteria);
        return franchiseUserStatusHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link FranchiseUserStatusHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FranchiseUserStatusHistory> createSpecification(FranchiseUserStatusHistoryCriteria criteria) {
        Specification<FranchiseUserStatusHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), FranchiseUserStatusHistory_.id),
                buildSpecification(criteria.getUserSatus(), FranchiseUserStatusHistory_.userSatus),
                buildStringSpecification(criteria.getUpdatedBy(), FranchiseUserStatusHistory_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), FranchiseUserStatusHistory_.updatedTime),
                buildSpecification(criteria.getFranchiseUserId(), root ->
                    root.join(FranchiseUserStatusHistory_.franchiseUser, JoinType.LEFT).get(FranchiseUser_.id)
                )
            );
        }
        return specification;
    }
}
