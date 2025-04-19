package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.ServiceOrderSpare;
import com.framasaas.repository.ServiceOrderSpareRepository;
import com.framasaas.service.criteria.ServiceOrderSpareCriteria;
import com.framasaas.service.dto.ServiceOrderSpareDTO;
import com.framasaas.service.mapper.ServiceOrderSpareMapper;
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
 * Service for executing complex queries for {@link ServiceOrderSpare} entities in the database.
 * The main input is a {@link ServiceOrderSpareCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ServiceOrderSpareDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceOrderSpareQueryService extends QueryService<ServiceOrderSpare> {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderSpareQueryService.class);

    private final ServiceOrderSpareRepository serviceOrderSpareRepository;

    private final ServiceOrderSpareMapper serviceOrderSpareMapper;

    public ServiceOrderSpareQueryService(
        ServiceOrderSpareRepository serviceOrderSpareRepository,
        ServiceOrderSpareMapper serviceOrderSpareMapper
    ) {
        this.serviceOrderSpareRepository = serviceOrderSpareRepository;
        this.serviceOrderSpareMapper = serviceOrderSpareMapper;
    }

    /**
     * Return a {@link Page} of {@link ServiceOrderSpareDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceOrderSpareDTO> findByCriteria(ServiceOrderSpareCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceOrderSpare> specification = createSpecification(criteria);
        return serviceOrderSpareRepository.findAll(specification, page).map(serviceOrderSpareMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceOrderSpareCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ServiceOrderSpare> specification = createSpecification(criteria);
        return serviceOrderSpareRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceOrderSpareCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceOrderSpare> createSpecification(ServiceOrderSpareCriteria criteria) {
        Specification<ServiceOrderSpare> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), ServiceOrderSpare_.id),
                buildRangeSpecification(criteria.getPrice(), ServiceOrderSpare_.price),
                buildRangeSpecification(criteria.getTax(), ServiceOrderSpare_.tax),
                buildRangeSpecification(criteria.getTotalCharge(), ServiceOrderSpare_.totalCharge),
                buildRangeSpecification(criteria.getFranchiseCommision(), ServiceOrderSpare_.franchiseCommision),
                buildRangeSpecification(criteria.getFranchiseCommisionTax(), ServiceOrderSpare_.franchiseCommisionTax),
                buildSpecification(criteria.getOrderedFrom(), ServiceOrderSpare_.orderedFrom),
                buildSpecification(criteria.getSpareStatus(), ServiceOrderSpare_.spareStatus),
                buildStringSpecification(criteria.getCreateddBy(), ServiceOrderSpare_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), ServiceOrderSpare_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), ServiceOrderSpare_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), ServiceOrderSpare_.updatedTime),
                buildSpecification(criteria.getServiceOrderId(), root ->
                    root.join(ServiceOrderSpare_.serviceOrder, JoinType.LEFT).get(ServiceOrder_.id)
                ),
                buildSpecification(criteria.getProductId(), root -> root.join(ServiceOrderSpare_.product, JoinType.LEFT).get(Product_.id))
            );
        }
        return specification;
    }
}
