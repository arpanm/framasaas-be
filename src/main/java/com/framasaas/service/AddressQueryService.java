package com.framasaas.service;

import com.framasaas.domain.*; // for static metamodels
import com.framasaas.domain.Address;
import com.framasaas.repository.AddressRepository;
import com.framasaas.service.criteria.AddressCriteria;
import com.framasaas.service.dto.AddressDTO;
import com.framasaas.service.mapper.AddressMapper;
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
 * Service for executing complex queries for {@link Address} entities in the database.
 * The main input is a {@link AddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AddressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressQueryService extends QueryService<Address> {

    private static final Logger LOG = LoggerFactory.getLogger(AddressQueryService.class);

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressQueryService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    /**
     * Return a {@link Page} of {@link AddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AddressDTO> findByCriteria(AddressCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Address> specification = createSpecification(criteria);
        return addressRepository.findAll(specification, page).map(addressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AddressCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Address> specification = createSpecification(criteria);
        return addressRepository.count(specification);
    }

    /**
     * Function to convert {@link AddressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Address> createSpecification(AddressCriteria criteria) {
        Specification<Address> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Address_.id),
                buildStringSpecification(criteria.getAddress1(), Address_.address1),
                buildStringSpecification(criteria.getAddress2(), Address_.address2),
                buildStringSpecification(criteria.getCity(), Address_.city),
                buildStringSpecification(criteria.getArea(), Address_.area),
                buildStringSpecification(criteria.getDistrict(), Address_.district),
                buildRangeSpecification(criteria.getPincode(), Address_.pincode),
                buildStringSpecification(criteria.getState(), Address_.state),
                buildStringSpecification(criteria.getCountry(), Address_.country),
                buildStringSpecification(criteria.getCreateddBy(), Address_.createddBy),
                buildRangeSpecification(criteria.getCreatedTime(), Address_.createdTime),
                buildStringSpecification(criteria.getUpdatedBy(), Address_.updatedBy),
                buildRangeSpecification(criteria.getUpdatedTime(), Address_.updatedTime),
                buildSpecification(criteria.getServiceOrderId(), root ->
                    root.join(Address_.serviceOrders, JoinType.LEFT).get(ServiceOrder_.id)
                ),
                buildSpecification(criteria.getAdditionalAttributeId(), root ->
                    root.join(Address_.additionalAttributes, JoinType.LEFT).get(AdditionalAttribute_.id)
                ),
                buildSpecification(criteria.getLocationId(), root -> root.join(Address_.location, JoinType.LEFT).get(LocationMapping_.id)),
                buildSpecification(criteria.getFranchiseId(), root -> root.join(Address_.franchise, JoinType.LEFT).get(Franchise_.id)),
                buildSpecification(criteria.getCustomerId(), root -> root.join(Address_.customer, JoinType.LEFT).get(Customer_.id))
            );
        }
        return specification;
    }
}
