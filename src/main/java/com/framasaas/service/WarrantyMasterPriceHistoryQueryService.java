package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.WarrantyMasterPriceHistory;
import com.framasaas.repository.WarrantyMasterPriceHistoryRepository;
import com.framasaas.service.criteria.WarrantyMasterPriceHistoryCriteria;
import com.framasaas.service.dto.WarrantyMasterPriceHistoryDTO;
import com.framasaas.service.mapper.WarrantyMasterPriceHistoryMapper;
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
 * Service for executing complex queries for {@link WarrantyMasterPriceHistory} entities in the database.
 * The main input is a {@link WarrantyMasterPriceHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link WarrantyMasterPriceHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WarrantyMasterPriceHistoryQueryService extends QueryService<WarrantyMasterPriceHistory> {

    private static final Logger LOG = LoggerFactory.getLogger(WarrantyMasterPriceHistoryQueryService.class);

    private final WarrantyMasterPriceHistoryRepository warrantyMasterPriceHistoryRepository;

    private final WarrantyMasterPriceHistoryMapper warrantyMasterPriceHistoryMapper;

    public WarrantyMasterPriceHistoryQueryService(
        WarrantyMasterPriceHistoryRepository warrantyMasterPriceHistoryRepository,
        WarrantyMasterPriceHistoryMapper warrantyMasterPriceHistoryMapper
    ) {
        this.warrantyMasterPriceHistoryRepository = warrantyMasterPriceHistoryRepository;
        this.warrantyMasterPriceHistoryMapper = warrantyMasterPriceHistoryMapper;
    }

    /**
     * Return a {@link Page} of {@link WarrantyMasterPriceHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WarrantyMasterPriceHistoryDTO> findByCriteria(WarrantyMasterPriceHistoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WarrantyMasterPriceHistory> specification = createSpecification(criteria);
        return warrantyMasterPriceHistoryRepository.findAll(specification, page).map(warrantyMasterPriceHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WarrantyMasterPriceHistoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<WarrantyMasterPriceHistory> specification = createSpecification(criteria);
        return warrantyMasterPriceHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link WarrantyMasterPriceHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WarrantyMasterPriceHistory> createSpecification(WarrantyMasterPriceHistoryCriteria criteria) {
        Specification<WarrantyMasterPriceHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), WarrantyMasterPriceHistory_.id),
                buildRangeSpecification(criteria.getPrice(), WarrantyMasterPriceHistory_.price),
                buildRangeSpecification(criteria.getTax(), WarrantyMasterPriceHistory_.tax),
                buildRangeSpecification(criteria.getFranchiseCommission(), WarrantyMasterPriceHistory_.franchiseCommission),
                buildRangeSpecification(criteria.getFranchiseTax(), WarrantyMasterPriceHistory_.franchiseTax),
                buildStringSpecification(criteria.getUpdatedBy(), WarrantyMasterPriceHistory_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), WarrantyMasterPriceHistory_.updatedTime),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(WarrantyMasterPriceHistory_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getWarrantyMasterId(), root ->
                    root.join(WarrantyMasterPriceHistory_.warrantyMaster, JoinType.LEFT).get(WarrantyMaster_.id)
                )
            );
        }
        return specification;
    }
}
