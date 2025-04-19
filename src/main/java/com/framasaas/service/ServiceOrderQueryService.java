package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.ServiceOrder;
import com.framasaas.repository.ServiceOrderRepository;
import com.framasaas.service.criteria.ServiceOrderCriteria;
import com.framasaas.service.dto.ServiceOrderDTO;
import com.framasaas.service.mapper.ServiceOrderMapper;
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
 * Service for executing complex queries for {@link ServiceOrder} entities in the database.
 * The main input is a {@link ServiceOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ServiceOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceOrderQueryService extends QueryService<ServiceOrder> {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderQueryService.class);

    private final ServiceOrderRepository serviceOrderRepository;

    private final ServiceOrderMapper serviceOrderMapper;

    public ServiceOrderQueryService(ServiceOrderRepository serviceOrderRepository, ServiceOrderMapper serviceOrderMapper) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceOrderMapper = serviceOrderMapper;
    }

    /**
     * Return a {@link Page} of {@link ServiceOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceOrderDTO> findByCriteria(ServiceOrderCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceOrder> specification = createSpecification(criteria);
        return serviceOrderRepository.findAll(specification, page).map(serviceOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceOrderCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ServiceOrder> specification = createSpecification(criteria);
        return serviceOrderRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceOrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceOrder> createSpecification(ServiceOrderCriteria criteria) {
        Specification<ServiceOrder> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), ServiceOrder_.id),
                buildStringSpecification(criteria.getDescription(), ServiceOrder_.description),
                buildSpecification(criteria.getOrderType(), ServiceOrder_.orderType),
                buildSpecification(criteria.getOrderStatus(), ServiceOrder_.orderStatus),
                buildSpecification(criteria.getInWarranty(), ServiceOrder_.inWarranty),
                buildSpecification(criteria.getIsFree(), ServiceOrder_.isFree),
                buildSpecification(criteria.getIsSpareNeeded(), ServiceOrder_.isSpareNeeded),
                buildRangeSpecification(criteria.getConfirmedTime(), ServiceOrder_.confirmedTime),
                buildRangeSpecification(criteria.getClosedTime(), ServiceOrder_.closedTime),
                buildRangeSpecification(criteria.getServiceCharge(), ServiceOrder_.serviceCharge),
                buildRangeSpecification(criteria.getTax(), ServiceOrder_.tax),
                buildRangeSpecification(criteria.getTotalSpareCharges(), ServiceOrder_.totalSpareCharges),
                buildRangeSpecification(criteria.getTotalSpareTax(), ServiceOrder_.totalSpareTax),
                buildRangeSpecification(criteria.getTotalCharges(), ServiceOrder_.totalCharges),
                buildRangeSpecification(criteria.getTotalPaymentDone(), ServiceOrder_.totalPaymentDone),
                buildStringSpecification(criteria.getCustomerInvoicePath(), ServiceOrder_.customerInvoicePath),
                buildRangeSpecification(criteria.getNps(), ServiceOrder_.nps),
                buildRangeSpecification(criteria.getPriority(), ServiceOrder_.priority),
                buildSpecification(criteria.getIsActive(), ServiceOrder_.isActive),
                buildStringSpecification(criteria.getCreateddBy(), ServiceOrder_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), ServiceOrder_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), ServiceOrder_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), ServiceOrder_.updatedTime),
                buildSpecification(criteria.getSupportingDocumentId(), root ->
                    root.join(ServiceOrder_.supportingDocuments, JoinType.LEFT).get(SupportingDocument_.id)
                ),
                buildSpecification(criteria.getServiceOrderFranchiseAssignmentId(), root ->
                    root.join(ServiceOrder_.serviceOrderFranchiseAssignments, JoinType.LEFT).get(ServiceOrderFranchiseAssignment_.id)
                ),
                buildSpecification(criteria.getServiceOrderSpareId(), root ->
                    root.join(ServiceOrder_.serviceOrderSpares, JoinType.LEFT).get(ServiceOrderSpare_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(ServiceOrder_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getCustomerId(), root -> root.join(ServiceOrder_.customer, JoinType.LEFT).get(Customer_.id)),
                buildSpecification(criteria.getServiceMasterId(), root ->
                    root.join(ServiceOrder_.serviceMaster, JoinType.LEFT).get(ServiceOrderMaster_.id)
                ),
                buildSpecification(criteria.getArticleId(), root -> root.join(ServiceOrder_.article, JoinType.LEFT).get(Article_.id)),
                buildSpecification(criteria.getAddressId(), root -> root.join(ServiceOrder_.address, JoinType.LEFT).get(Address_.id))
            );
        }
        return specification;
    }
}
