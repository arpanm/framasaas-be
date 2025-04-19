package com.framasaas.service.impl;

import com.framasaas.domain.ServiceOrderPayment;
import com.framasaas.repository.ServiceOrderPaymentRepository;
import com.framasaas.service.ServiceOrderPaymentService;
import com.framasaas.service.dto.ServiceOrderPaymentDTO;
import com.framasaas.service.mapper.ServiceOrderPaymentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.ServiceOrderPayment}.
 */
@Service
@Transactional
public class ServiceOrderPaymentServiceImpl implements ServiceOrderPaymentService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderPaymentServiceImpl.class);

    private final ServiceOrderPaymentRepository serviceOrderPaymentRepository;

    private final ServiceOrderPaymentMapper serviceOrderPaymentMapper;

    public ServiceOrderPaymentServiceImpl(
        ServiceOrderPaymentRepository serviceOrderPaymentRepository,
        ServiceOrderPaymentMapper serviceOrderPaymentMapper
    ) {
        this.serviceOrderPaymentRepository = serviceOrderPaymentRepository;
        this.serviceOrderPaymentMapper = serviceOrderPaymentMapper;
    }

    @Override
    public ServiceOrderPaymentDTO save(ServiceOrderPaymentDTO serviceOrderPaymentDTO) {
        LOG.debug("Request to save ServiceOrderPayment : {}", serviceOrderPaymentDTO);
        ServiceOrderPayment serviceOrderPayment = serviceOrderPaymentMapper.toEntity(serviceOrderPaymentDTO);
        serviceOrderPayment = serviceOrderPaymentRepository.save(serviceOrderPayment);
        return serviceOrderPaymentMapper.toDto(serviceOrderPayment);
    }

    @Override
    public ServiceOrderPaymentDTO update(ServiceOrderPaymentDTO serviceOrderPaymentDTO) {
        LOG.debug("Request to update ServiceOrderPayment : {}", serviceOrderPaymentDTO);
        ServiceOrderPayment serviceOrderPayment = serviceOrderPaymentMapper.toEntity(serviceOrderPaymentDTO);
        serviceOrderPayment = serviceOrderPaymentRepository.save(serviceOrderPayment);
        return serviceOrderPaymentMapper.toDto(serviceOrderPayment);
    }

    @Override
    public Optional<ServiceOrderPaymentDTO> partialUpdate(ServiceOrderPaymentDTO serviceOrderPaymentDTO) {
        LOG.debug("Request to partially update ServiceOrderPayment : {}", serviceOrderPaymentDTO);

        return serviceOrderPaymentRepository
            .findById(serviceOrderPaymentDTO.getId())
            .map(existingServiceOrderPayment -> {
                serviceOrderPaymentMapper.partialUpdate(existingServiceOrderPayment, serviceOrderPaymentDTO);

                return existingServiceOrderPayment;
            })
            .map(serviceOrderPaymentRepository::save)
            .map(serviceOrderPaymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderPaymentDTO> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderPayment : {}", id);
        return serviceOrderPaymentRepository.findById(id).map(serviceOrderPaymentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderPayment : {}", id);
        serviceOrderPaymentRepository.deleteById(id);
    }
}
