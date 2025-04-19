package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.InventoryHistory;
import com.framasaas.repository.InventoryHistoryRepository;
import com.framasaas.service.criteria.InventoryHistoryCriteria;
import com.framasaas.service.dto.InventoryHistoryDTO;
import com.framasaas.service.mapper.InventoryHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link InventoryHistory} entities in the database.
 * The main input is a {@link InventoryHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link InventoryHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryHistoryQueryService extends QueryService<InventoryHistory> {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryHistoryQueryService.class);

    private final InventoryHistoryRepository inventoryHistoryRepository;

    private final InventoryHistoryMapper inventoryHistoryMapper;

    public InventoryHistoryQueryService(
        InventoryHistoryRepository inventoryHistoryRepository,
        InventoryHistoryMapper inventoryHistoryMapper
    ) {
        this.inventoryHistoryRepository = inventoryHistoryRepository;
        this.inventoryHistoryMapper = inventoryHistoryMapper;
    }

    /**
     * Return a {@link Page} of {@link InventoryHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryHistoryDTO> findByCriteria(InventoryHistoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventoryHistory> specification = createSpecification(criteria);
        return inventoryHistoryRepository.findAll(specification, page).map(inventoryHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryHistoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<InventoryHistory> specification = createSpecification(criteria);
        return inventoryHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InventoryHistory> createSpecification(InventoryHistoryCriteria criteria) {
        Specification<InventoryHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), InventoryHistory_.id),
                buildRangeSpecification(criteria.getInventoryValue(), InventoryHistory_.inventoryValue),
                buildSpecification(criteria.getReason(), InventoryHistory_.reason),
                buildStringSpecification(criteria.getUpdatedBy(), InventoryHistory_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), InventoryHistory_.updatedTime)
            );
        }
        return specification;
    }
}
