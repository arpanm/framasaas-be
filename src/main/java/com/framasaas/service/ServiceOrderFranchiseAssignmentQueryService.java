package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.ServiceOrderFranchiseAssignment;
import com.framasaas.repository.ServiceOrderFranchiseAssignmentRepository;
import com.framasaas.service.criteria.ServiceOrderFranchiseAssignmentCriteria;
import com.framasaas.service.dto.ServiceOrderFranchiseAssignmentDTO;
import com.framasaas.service.mapper.ServiceOrderFranchiseAssignmentMapper;
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
 * Service for executing complex queries for {@link ServiceOrderFranchiseAssignment} entities in the database.
 * The main input is a {@link ServiceOrderFranchiseAssignmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ServiceOrderFranchiseAssignmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceOrderFranchiseAssignmentQueryService extends QueryService<ServiceOrderFranchiseAssignment> {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderFranchiseAssignmentQueryService.class);

    private final ServiceOrderFranchiseAssignmentRepository serviceOrderFranchiseAssignmentRepository;

    private final ServiceOrderFranchiseAssignmentMapper serviceOrderFranchiseAssignmentMapper;

    public ServiceOrderFranchiseAssignmentQueryService(
        ServiceOrderFranchiseAssignmentRepository serviceOrderFranchiseAssignmentRepository,
        ServiceOrderFranchiseAssignmentMapper serviceOrderFranchiseAssignmentMapper
    ) {
        this.serviceOrderFranchiseAssignmentRepository = serviceOrderFranchiseAssignmentRepository;
        this.serviceOrderFranchiseAssignmentMapper = serviceOrderFranchiseAssignmentMapper;
    }

    /**
     * Return a {@link Page} of {@link ServiceOrderFranchiseAssignmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceOrderFranchiseAssignmentDTO> findByCriteria(ServiceOrderFranchiseAssignmentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceOrderFranchiseAssignment> specification = createSpecification(criteria);
        return serviceOrderFranchiseAssignmentRepository.findAll(specification, page).map(serviceOrderFranchiseAssignmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceOrderFranchiseAssignmentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ServiceOrderFranchiseAssignment> specification = createSpecification(criteria);
        return serviceOrderFranchiseAssignmentRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceOrderFranchiseAssignmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceOrderFranchiseAssignment> createSpecification(ServiceOrderFranchiseAssignmentCriteria criteria) {
        Specification<ServiceOrderFranchiseAssignment> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), ServiceOrderFranchiseAssignment_.id),
                buildSpecification(
                    criteria.getServiceOrderAssignmentStatus(),
                    ServiceOrderFranchiseAssignment_.serviceOrderAssignmentStatus
                ),
                buildRangeSpecification(criteria.getNps(), ServiceOrderFranchiseAssignment_.nps),
                buildSpecification(criteria.getIsActive(), ServiceOrderFranchiseAssignment_.isActive),
                buildRangeSpecification(criteria.getAssignedTime(), ServiceOrderFranchiseAssignment_.assignedTime),
                buildRangeSpecification(criteria.getMovedBackTime(), ServiceOrderFranchiseAssignment_.movedBackTime),
                buildRangeSpecification(criteria.getVisitTime(), ServiceOrderFranchiseAssignment_.visitTime),
                buildRangeSpecification(criteria.getSpareOrderTime(), ServiceOrderFranchiseAssignment_.spareOrderTime),
                buildRangeSpecification(criteria.getSpareDeliveryTime(), ServiceOrderFranchiseAssignment_.spareDeliveryTime),
                buildRangeSpecification(criteria.getCompletedTime(), ServiceOrderFranchiseAssignment_.completedTime),
                buildRangeSpecification(criteria.getFranchiseCommision(), ServiceOrderFranchiseAssignment_.franchiseCommision),
                buildRangeSpecification(criteria.getFranchiseCommisionTax(), ServiceOrderFranchiseAssignment_.franchiseCommisionTax),
                buildStringSpecification(criteria.getFranchiseInvoicePath(), ServiceOrderFranchiseAssignment_.franchiseInvoicePath),
                buildStringSpecification(criteria.getCreateddBy(), ServiceOrderFranchiseAssignment_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), ServiceOrderFranchiseAssignment_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), ServiceOrderFranchiseAssignment_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), ServiceOrderFranchiseAssignment_.updatedTime),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(ServiceOrderFranchiseAssignment_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getServiceOrderId(), root ->
                    root.join(ServiceOrderFranchiseAssignment_.serviceOrder, JoinType.LEFT).get(ServiceOrder_.id)
                ),
                buildSpecification(criteria.getFranchiseId(), root ->
                    root.join(ServiceOrderFranchiseAssignment_.franchise, JoinType.LEFT).get(Franchise_.id)
                )
            );
        }
        return specification;
    }
}
