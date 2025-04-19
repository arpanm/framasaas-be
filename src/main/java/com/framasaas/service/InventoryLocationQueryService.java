package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.InventoryLocation;
import com.framasaas.repository.InventoryLocationRepository;
import com.framasaas.service.criteria.InventoryLocationCriteria;
import com.framasaas.service.dto.InventoryLocationDTO;
import com.framasaas.service.mapper.InventoryLocationMapper;
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
 * Service for executing complex queries for {@link InventoryLocation} entities in the database.
 * The main input is a {@link InventoryLocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link InventoryLocationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryLocationQueryService extends QueryService<InventoryLocation> {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryLocationQueryService.class);

    private final InventoryLocationRepository inventoryLocationRepository;

    private final InventoryLocationMapper inventoryLocationMapper;

    public InventoryLocationQueryService(
        InventoryLocationRepository inventoryLocationRepository,
        InventoryLocationMapper inventoryLocationMapper
    ) {
        this.inventoryLocationRepository = inventoryLocationRepository;
        this.inventoryLocationMapper = inventoryLocationMapper;
    }

    /**
     * Return a {@link Page} of {@link InventoryLocationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryLocationDTO> findByCriteria(InventoryLocationCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventoryLocation> specification = createSpecification(criteria);
        return inventoryLocationRepository.findAll(specification, page).map(inventoryLocationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryLocationCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<InventoryLocation> specification = createSpecification(criteria);
        return inventoryLocationRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryLocationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InventoryLocation> createSpecification(InventoryLocationCriteria criteria) {
        Specification<InventoryLocation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), InventoryLocation_.id),
                buildStringSpecification(criteria.getName(), InventoryLocation_.name),
                buildSpecification(criteria.getLocationType(), InventoryLocation_.locationType),
                buildSpecification(criteria.getIsActive(), InventoryLocation_.isActive),
                buildStringSpecification(criteria.getCreateddBy(), InventoryLocation_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), InventoryLocation_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), InventoryLocation_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), InventoryLocation_.updatedTime),
                buildSpecification(criteria.getInventoryId(), root ->
                    root.join(InventoryLocation_.inventories, JoinType.LEFT).get(Inventory_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(InventoryLocation_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                )
            );
        }
        return specification;
    }
}
