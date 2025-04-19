package com.framasaas.service.impl;

import com.framasaas.domain.ServiceOrder;
import com.framasaas.repository.ServiceOrderRepository;
import com.framasaas.service.ServiceOrderService;
import com.framasaas.service.dto.ServiceOrderDTO;
import com.framasaas.service.mapper.ServiceOrderMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.ServiceOrder}.
 */
@Service
@Transactional
public class ServiceOrderServiceImpl implements ServiceOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderServiceImpl.class);

    private final ServiceOrderRepository serviceOrderRepository;

    private final ServiceOrderMapper serviceOrderMapper;

    public ServiceOrderServiceImpl(ServiceOrderRepository serviceOrderRepository, ServiceOrderMapper serviceOrderMapper) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.serviceOrderMapper = serviceOrderMapper;
    }

    @Override
    public ServiceOrderDTO save(ServiceOrderDTO serviceOrderDTO) {
        LOG.debug("Request to save ServiceOrder : {}", serviceOrderDTO);
        ServiceOrder serviceOrder = serviceOrderMapper.toEntity(serviceOrderDTO);
        serviceOrder = serviceOrderRepository.save(serviceOrder);
        return serviceOrderMapper.toDto(serviceOrder);
    }

    @Override
    public ServiceOrderDTO update(ServiceOrderDTO serviceOrderDTO) {
        LOG.debug("Request to update ServiceOrder : {}", serviceOrderDTO);
        ServiceOrder serviceOrder = serviceOrderMapper.toEntity(serviceOrderDTO);
        serviceOrder = serviceOrderRepository.save(serviceOrder);
        return serviceOrderMapper.toDto(serviceOrder);
    }

    @Override
    public Optional<ServiceOrderDTO> partialUpdate(ServiceOrderDTO serviceOrderDTO) {
        LOG.debug("Request to partially update ServiceOrder : {}", serviceOrderDTO);

        return serviceOrderRepository
            .findById(serviceOrderDTO.getId())
            .map(existingServiceOrder -> {
                serviceOrderMapper.partialUpdate(existingServiceOrder, serviceOrderDTO);

                return existingServiceOrder;
            })
            .map(serviceOrderRepository::save)
            .map(serviceOrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderDTO> findOne(Long id) {
        LOG.debug("Request to get ServiceOrder : {}", id);
        return serviceOrderRepository.findById(id).map(serviceOrderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrder : {}", id);
        serviceOrderRepository.deleteById(id);
    }
}
