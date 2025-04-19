package com.framasaas.service.impl;

import com.framasaas.domain.ServiceOrderMaster;
import com.framasaas.repository.ServiceOrderMasterRepository;
import com.framasaas.service.ServiceOrderMasterService;
import com.framasaas.service.dto.ServiceOrderMasterDTO;
import com.framasaas.service.mapper.ServiceOrderMasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.ServiceOrderMaster}.
 */
@Service
@Transactional
public class ServiceOrderMasterServiceImpl implements ServiceOrderMasterService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderMasterServiceImpl.class);

    private final ServiceOrderMasterRepository serviceOrderMasterRepository;

    private final ServiceOrderMasterMapper serviceOrderMasterMapper;

    public ServiceOrderMasterServiceImpl(
        ServiceOrderMasterRepository serviceOrderMasterRepository,
        ServiceOrderMasterMapper serviceOrderMasterMapper
    ) {
        this.serviceOrderMasterRepository = serviceOrderMasterRepository;
        this.serviceOrderMasterMapper = serviceOrderMasterMapper;
    }

    @Override
    public ServiceOrderMasterDTO save(ServiceOrderMasterDTO serviceOrderMasterDTO) {
        LOG.debug("Request to save ServiceOrderMaster : {}", serviceOrderMasterDTO);
        ServiceOrderMaster serviceOrderMaster = serviceOrderMasterMapper.toEntity(serviceOrderMasterDTO);
        serviceOrderMaster = serviceOrderMasterRepository.save(serviceOrderMaster);
        return serviceOrderMasterMapper.toDto(serviceOrderMaster);
    }

    @Override
    public ServiceOrderMasterDTO update(ServiceOrderMasterDTO serviceOrderMasterDTO) {
        LOG.debug("Request to update ServiceOrderMaster : {}", serviceOrderMasterDTO);
        ServiceOrderMaster serviceOrderMaster = serviceOrderMasterMapper.toEntity(serviceOrderMasterDTO);
        serviceOrderMaster = serviceOrderMasterRepository.save(serviceOrderMaster);
        return serviceOrderMasterMapper.toDto(serviceOrderMaster);
    }

    @Override
    public Optional<ServiceOrderMasterDTO> partialUpdate(ServiceOrderMasterDTO serviceOrderMasterDTO) {
        LOG.debug("Request to partially update ServiceOrderMaster : {}", serviceOrderMasterDTO);

        return serviceOrderMasterRepository
            .findById(serviceOrderMasterDTO.getId())
            .map(existingServiceOrderMaster -> {
                serviceOrderMasterMapper.partialUpdate(existingServiceOrderMaster, serviceOrderMasterDTO);

                return existingServiceOrderMaster;
            })
            .map(serviceOrderMasterRepository::save)
            .map(serviceOrderMasterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderMasterDTO> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderMaster : {}", id);
        return serviceOrderMasterRepository.findById(id).map(serviceOrderMasterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderMaster : {}", id);
        serviceOrderMasterRepository.deleteById(id);
    }
}
