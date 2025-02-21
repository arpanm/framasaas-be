package com.framasaas.be.service.impl;

import com.framasaas.be.domain.Address;
import com.framasaas.be.repository.AddressRepository;
import com.framasaas.be.service.AddressService;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.Address}.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private static final Logger LOG = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        LOG.debug("Request to save Address : {}", address);
        return addressRepository.save(address);
    }

    @Override
    public Address update(Address address) {
        LOG.debug("Request to update Address : {}", address);
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> partialUpdate(Address address) {
        LOG.debug("Request to partially update Address : {}", address);

        return addressRepository
            .findById(address.getId())
            .map(existingAddress -> {
                if (address.getAddress1() != null) {
                    existingAddress.setAddress1(address.getAddress1());
                }
                if (address.getAddress2() != null) {
                    existingAddress.setAddress2(address.getAddress2());
                }
                if (address.getCity() != null) {
                    existingAddress.setCity(address.getCity());
                }
                if (address.getArea() != null) {
                    existingAddress.setArea(address.getArea());
                }
                if (address.getDistrict() != null) {
                    existingAddress.setDistrict(address.getDistrict());
                }
                if (address.getPincode() != null) {
                    existingAddress.setPincode(address.getPincode());
                }
                if (address.getState() != null) {
                    existingAddress.setState(address.getState());
                }
                if (address.getCountry() != null) {
                    existingAddress.setCountry(address.getCountry());
                }
                if (address.getCreateddBy() != null) {
                    existingAddress.setCreateddBy(address.getCreateddBy());
                }
                if (address.getCreatedTime() != null) {
                    existingAddress.setCreatedTime(address.getCreatedTime());
                }
                if (address.getUpdatedBy() != null) {
                    existingAddress.setUpdatedBy(address.getUpdatedBy());
                }
                if (address.getUpdatedTime() != null) {
                    existingAddress.setUpdatedTime(address.getUpdatedTime());
                }

                return existingAddress;
            })
            .map(addressRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Address> findAll(Pageable pageable) {
        LOG.debug("Request to get all Addresses");
        return addressRepository.findAll(pageable);
    }

    /**
     *  Get all the addresses where Franchise is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Address> findAllWhereFranchiseIsNull() {
        LOG.debug("Request to get all addresses where Franchise is null");
        return StreamSupport.stream(addressRepository.findAll().spliterator(), false)
            .filter(address -> address.getFranchise() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> findOne(Long id) {
        LOG.debug("Request to get Address : {}", id);
        return addressRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Address : {}", id);
        addressRepository.deleteById(id);
    }
}
