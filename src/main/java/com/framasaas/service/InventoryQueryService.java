package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.Inventory;
import com.framasaas.repository.InventoryRepository;
import com.framasaas.service.criteria.InventoryCriteria;
import com.framasaas.service.dto.InventoryDTO;
import com.framasaas.service.mapper.InventoryMapper;
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
 * Service for executing complex queries for {@link Inventory} entities in the database.
 * The main input is a {@link InventoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link InventoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryQueryService extends QueryService<Inventory> {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryQueryService.class);

    private final InventoryRepository inventoryRepository;

    private final InventoryMapper inventoryMapper;

    public InventoryQueryService(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * Return a {@link Page} of {@link InventoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryDTO> findByCriteria(InventoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Inventory> specification = createSpecification(criteria);
        return inventoryRepository.findAll(specification, page).map(inventoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Inventory> specification = createSpecification(criteria);
        return inventoryRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Inventory> createSpecification(InventoryCriteria criteria) {
        Specification<Inventory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Inventory_.id),
                buildRangeSpecification(criteria.getInventoryValue(), Inventory_.inventoryValue),
                buildSpecification(criteria.getIsSellable(), Inventory_.isSellable),
                buildStringSpecification(criteria.getCreateddBy(), Inventory_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), Inventory_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), Inventory_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), Inventory_.updatedTime),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(Inventory_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getProductId(), root -> root.join(Inventory_.product, JoinType.LEFT).get(Product_.id)),
                buildSpecification(criteria.getLocationId(), root ->
                    root.join(Inventory_.location, JoinType.LEFT).get(InventoryLocation_.id)
                )
            );
        }
        return specification;
    }
}
