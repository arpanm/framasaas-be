package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.ServiceOrderPayment;
import com.framasaas.repository.ServiceOrderPaymentRepository;
import com.framasaas.service.criteria.ServiceOrderPaymentCriteria;
import com.framasaas.service.dto.ServiceOrderPaymentDTO;
import com.framasaas.service.mapper.ServiceOrderPaymentMapper;
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
 * Service for executing complex queries for {@link ServiceOrderPayment} entities in the database.
 * The main input is a {@link ServiceOrderPaymentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ServiceOrderPaymentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceOrderPaymentQueryService extends QueryService<ServiceOrderPayment> {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderPaymentQueryService.class);

    private final ServiceOrderPaymentRepository serviceOrderPaymentRepository;

    private final ServiceOrderPaymentMapper serviceOrderPaymentMapper;

    public ServiceOrderPaymentQueryService(
        ServiceOrderPaymentRepository serviceOrderPaymentRepository,
        ServiceOrderPaymentMapper serviceOrderPaymentMapper
    ) {
        this.serviceOrderPaymentRepository = serviceOrderPaymentRepository;
        this.serviceOrderPaymentMapper = serviceOrderPaymentMapper;
    }

    /**
     * Return a {@link Page} of {@link ServiceOrderPaymentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceOrderPaymentDTO> findByCriteria(ServiceOrderPaymentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceOrderPayment> specification = createSpecification(criteria);
        return serviceOrderPaymentRepository.findAll(specification, page).map(serviceOrderPaymentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceOrderPaymentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ServiceOrderPayment> specification = createSpecification(criteria);
        return serviceOrderPaymentRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceOrderPaymentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceOrderPayment> createSpecification(ServiceOrderPaymentCriteria criteria) {
        Specification<ServiceOrderPayment> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), ServiceOrderPayment_.id),
                buildStringSpecification(criteria.getPaymentLink(), ServiceOrderPayment_.paymentLink),
                buildSpecification(criteria.getPaymentStatus(), ServiceOrderPayment_.paymentStatus),
                buildSpecification(criteria.getMop(), ServiceOrderPayment_.mop),
                buildStringSpecification(criteria.getPgTxnId(), ServiceOrderPayment_.pgTxnId),
                buildStringSpecification(criteria.getPgTxnResponse(), ServiceOrderPayment_.pgTxnResponse),
                buildRangeSpecification(criteria.getPgTxnResponseTime(), ServiceOrderPayment_.pgTxnResponseTime),
                buildStringSpecification(criteria.getPaymentInitiatedBy(), ServiceOrderPayment_.paymentInitiatedBy),
                buildSpecification(criteria.getIsActive(), ServiceOrderPayment_.isActive),
                buildStringSpecification(criteria.getCreateddBy(), ServiceOrderPayment_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), ServiceOrderPayment_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), ServiceOrderPayment_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), ServiceOrderPayment_.updatedTime),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(ServiceOrderPayment_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                )
            );
        }
        return specification;
    }
}
