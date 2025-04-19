package com.framasaas.service.impl;

import com.framasaas.domain.ServiceOrderFieldAgentAssignment;
import com.framasaas.repository.ServiceOrderFieldAgentAssignmentRepository;
import com.framasaas.service.ServiceOrderFieldAgentAssignmentService;
import com.framasaas.service.dto.ServiceOrderFieldAgentAssignmentDTO;
import com.framasaas.service.mapper.ServiceOrderFieldAgentAssignmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.ServiceOrderFieldAgentAssignment}.
 */
@Service
@Transactional
public class ServiceOrderFieldAgentAssignmentServiceImpl implements ServiceOrderFieldAgentAssignmentService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderFieldAgentAssignmentServiceImpl.class);

    private final ServiceOrderFieldAgentAssignmentRepository serviceOrderFieldAgentAssignmentRepository;

    private final ServiceOrderFieldAgentAssignmentMapper serviceOrderFieldAgentAssignmentMapper;

    public ServiceOrderFieldAgentAssignmentServiceImpl(
        ServiceOrderFieldAgentAssignmentRepository serviceOrderFieldAgentAssignmentRepository,
        ServiceOrderFieldAgentAssignmentMapper serviceOrderFieldAgentAssignmentMapper
    ) {
        this.serviceOrderFieldAgentAssignmentRepository = serviceOrderFieldAgentAssignmentRepository;
        this.serviceOrderFieldAgentAssignmentMapper = serviceOrderFieldAgentAssignmentMapper;
    }

    @Override
    public ServiceOrderFieldAgentAssignmentDTO save(ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO) {
        LOG.debug("Request to save ServiceOrderFieldAgentAssignment : {}", serviceOrderFieldAgentAssignmentDTO);
        ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentMapper.toEntity(
            serviceOrderFieldAgentAssignmentDTO
        );
        serviceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.save(serviceOrderFieldAgentAssignment);
        return serviceOrderFieldAgentAssignmentMapper.toDto(serviceOrderFieldAgentAssignment);
    }

    @Override
    public ServiceOrderFieldAgentAssignmentDTO update(ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO) {
        LOG.debug("Request to update ServiceOrderFieldAgentAssignment : {}", serviceOrderFieldAgentAssignmentDTO);
        ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentMapper.toEntity(
            serviceOrderFieldAgentAssignmentDTO
        );
        serviceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignmentRepository.save(serviceOrderFieldAgentAssignment);
        return serviceOrderFieldAgentAssignmentMapper.toDto(serviceOrderFieldAgentAssignment);
    }

    @Override
    public Optional<ServiceOrderFieldAgentAssignmentDTO> partialUpdate(
        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO
    ) {
        LOG.debug("Request to partially update ServiceOrderFieldAgentAssignment : {}", serviceOrderFieldAgentAssignmentDTO);

        return serviceOrderFieldAgentAssignmentRepository
            .findById(serviceOrderFieldAgentAssignmentDTO.getId())
            .map(existingServiceOrderFieldAgentAssignment -> {
                serviceOrderFieldAgentAssignmentMapper.partialUpdate(
                    existingServiceOrderFieldAgentAssignment,
                    serviceOrderFieldAgentAssignmentDTO
                );

                return existingServiceOrderFieldAgentAssignment;
            })
            .map(serviceOrderFieldAgentAssignmentRepository::save)
            .map(serviceOrderFieldAgentAssignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderFieldAgentAssignmentDTO> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderFieldAgentAssignment : {}", id);
        return serviceOrderFieldAgentAssignmentRepository.findById(id).map(serviceOrderFieldAgentAssignmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderFieldAgentAssignment : {}", id);
        serviceOrderFieldAgentAssignmentRepository.deleteById(id);
    }
}
