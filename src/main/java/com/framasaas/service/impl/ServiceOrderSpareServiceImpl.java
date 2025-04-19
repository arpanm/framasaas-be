package com.framasaas.service.impl;

import com.framasaas.domain.ServiceOrderSpare;
import com.framasaas.repository.ServiceOrderSpareRepository;
import com.framasaas.service.ServiceOrderSpareService;
import com.framasaas.service.dto.ServiceOrderSpareDTO;
import com.framasaas.service.mapper.ServiceOrderSpareMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.ServiceOrderSpare}.
 */
@Service
@Transactional
public class ServiceOrderSpareServiceImpl implements ServiceOrderSpareService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderSpareServiceImpl.class);

    private final ServiceOrderSpareRepository serviceOrderSpareRepository;

    private final ServiceOrderSpareMapper serviceOrderSpareMapper;

    public ServiceOrderSpareServiceImpl(
        ServiceOrderSpareRepository serviceOrderSpareRepository,
        ServiceOrderSpareMapper serviceOrderSpareMapper
    ) {
        this.serviceOrderSpareRepository = serviceOrderSpareRepository;
        this.serviceOrderSpareMapper = serviceOrderSpareMapper;
    }

    @Override
    public ServiceOrderSpareDTO save(ServiceOrderSpareDTO serviceOrderSpareDTO) {
        LOG.debug("Request to save ServiceOrderSpare : {}", serviceOrderSpareDTO);
        ServiceOrderSpare serviceOrderSpare = serviceOrderSpareMapper.toEntity(serviceOrderSpareDTO);
        serviceOrderSpare = serviceOrderSpareRepository.save(serviceOrderSpare);
        return serviceOrderSpareMapper.toDto(serviceOrderSpare);
    }

    @Override
    public ServiceOrderSpareDTO update(ServiceOrderSpareDTO serviceOrderSpareDTO) {
        LOG.debug("Request to update ServiceOrderSpare : {}", serviceOrderSpareDTO);
        ServiceOrderSpare serviceOrderSpare = serviceOrderSpareMapper.toEntity(serviceOrderSpareDTO);
        serviceOrderSpare = serviceOrderSpareRepository.save(serviceOrderSpare);
        return serviceOrderSpareMapper.toDto(serviceOrderSpare);
    }

    @Override
    public Optional<ServiceOrderSpareDTO> partialUpdate(ServiceOrderSpareDTO serviceOrderSpareDTO) {
        LOG.debug("Request to partially update ServiceOrderSpare : {}", serviceOrderSpareDTO);

        return serviceOrderSpareRepository
            .findById(serviceOrderSpareDTO.getId())
            .map(existingServiceOrderSpare -> {
                serviceOrderSpareMapper.partialUpdate(existingServiceOrderSpare, serviceOrderSpareDTO);

                return existingServiceOrderSpare;
            })
            .map(serviceOrderSpareRepository::save)
            .map(serviceOrderSpareMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderSpareDTO> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderSpare : {}", id);
        return serviceOrderSpareRepository.findById(id).map(serviceOrderSpareMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderSpare : {}", id);
        serviceOrderSpareRepository.deleteById(id);
    }
}
