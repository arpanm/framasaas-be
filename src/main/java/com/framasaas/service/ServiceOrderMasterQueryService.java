package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.ServiceOrderMaster;
import com.framasaas.repository.ServiceOrderMasterRepository;
import com.framasaas.service.criteria.ServiceOrderMasterCriteria;
import com.framasaas.service.dto.ServiceOrderMasterDTO;
import com.framasaas.service.mapper.ServiceOrderMasterMapper;
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
 * Service for executing complex queries for {@link ServiceOrderMaster} entities in the database.
 * The main input is a {@link ServiceOrderMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ServiceOrderMasterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceOrderMasterQueryService extends QueryService<ServiceOrderMaster> {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderMasterQueryService.class);

    private final ServiceOrderMasterRepository serviceOrderMasterRepository;

    private final ServiceOrderMasterMapper serviceOrderMasterMapper;

    public ServiceOrderMasterQueryService(
        ServiceOrderMasterRepository serviceOrderMasterRepository,
        ServiceOrderMasterMapper serviceOrderMasterMapper
    ) {
        this.serviceOrderMasterRepository = serviceOrderMasterRepository;
        this.serviceOrderMasterMapper = serviceOrderMasterMapper;
    }

    /**
     * Return a {@link Page} of {@link ServiceOrderMasterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceOrderMasterDTO> findByCriteria(ServiceOrderMasterCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceOrderMaster> specification = createSpecification(criteria);
        return serviceOrderMasterRepository.findAll(specification, page).map(serviceOrderMasterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceOrderMasterCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ServiceOrderMaster> specification = createSpecification(criteria);
        return serviceOrderMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceOrderMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceOrderMaster> createSpecification(ServiceOrderMasterCriteria criteria) {
        Specification<ServiceOrderMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), ServiceOrderMaster_.id),
                buildSpecification(criteria.getServiceOrderType(), ServiceOrderMaster_.serviceOrderType),
                buildRangeSpecification(criteria.getSlaInHours(), ServiceOrderMaster_.slaInHours),
                buildRangeSpecification(criteria.getCharge(), ServiceOrderMaster_.charge),
                buildRangeSpecification(criteria.getTax(), ServiceOrderMaster_.tax),
                buildRangeSpecification(criteria.getFranchiseCommissionWithinSla(), ServiceOrderMaster_.franchiseCommissionWithinSla),
                buildRangeSpecification(criteria.getFranchiseTaxWithinSlaTax(), ServiceOrderMaster_.franchiseTaxWithinSlaTax),
                buildRangeSpecification(criteria.getFranchiseCommissionOutsideSla(), ServiceOrderMaster_.franchiseCommissionOutsideSla),
                buildRangeSpecification(criteria.getFranchiseTaxOutsideSlaTax(), ServiceOrderMaster_.franchiseTaxOutsideSlaTax),
                buildSpecification(criteria.getIsActive(), ServiceOrderMaster_.isActive),
                buildStringSpecification(criteria.getCreateddBy(), ServiceOrderMaster_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), ServiceOrderMaster_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), ServiceOrderMaster_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), ServiceOrderMaster_.updatedTime),
                buildSpecification(criteria.getServiceOrderId(), root ->
                    root.join(ServiceOrderMaster_.serviceOrders, JoinType.LEFT).get(ServiceOrder_.id)
                ),
                buildSpecification(criteria.getProductId(), root -> root.join(ServiceOrderMaster_.product, JoinType.LEFT).get(Product_.id))
            );
        }
        return specification;
    }
}
