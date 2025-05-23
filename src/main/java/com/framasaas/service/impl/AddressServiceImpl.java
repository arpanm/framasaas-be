package com.framasaas.service.impl;

import com.framasaas.domain.Address;
import com.framasaas.repository.AddressRepository;
import com.framasaas.service.AddressService;
import com.framasaas.service.dto.AddressDTO;
import com.framasaas.service.mapper.AddressMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.Address}.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private static final Logger LOG = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        LOG.debug("Request to save Address : {}", addressDTO);
        Address address = addressMapper.toEntity(addressDTO);
        address = addressRepository.save(address);
        return addressMapper.toDto(address);
    }

    @Override
    public AddressDTO update(AddressDTO addressDTO) {
        LOG.debug("Request to update Address : {}", addressDTO);
        Address address = addressMapper.toEntity(addressDTO);
        address = addressRepository.save(address);
        return addressMapper.toDto(address);
    }

    @Override
    public Optional<AddressDTO> partialUpdate(AddressDTO addressDTO) {
        LOG.debug("Request to partially update Address : {}", addressDTO);

        return addressRepository
            .findById(addressDTO.getId())
            .map(existingAddress -> {
                addressMapper.partialUpdate(existingAddress, addressDTO);

                return existingAddress;
            })
            .map(addressRepository::save)
            .map(addressMapper::toDto);
    }

    /**
     *  Get all the addresses where Franchise is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AddressDTO> findAllWhereFranchiseIsNull() {
        LOG.debug("Request to get all addresses where Franchise is null");
        return StreamSupport.stream(addressRepository.findAll().spliterator(), false)
            .filter(address -> address.getFranchise() == null)
            .map(addressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDTO> findOne(Long id) {
        LOG.debug("Request to get Address : {}", id);
        return addressRepository.findById(id).map(addressMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Address : {}", id);
        addressRepository.deleteById(id);
    }
}
