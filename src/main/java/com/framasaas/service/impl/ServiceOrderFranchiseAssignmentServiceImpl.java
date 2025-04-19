package com.framasaas.service.impl;

import com.framasaas.domain.ServiceOrderFranchiseAssignment;
import com.framasaas.repository.ServiceOrderFranchiseAssignmentRepository;
import com.framasaas.service.ServiceOrderFranchiseAssignmentService;
import com.framasaas.service.dto.ServiceOrderFranchiseAssignmentDTO;
import com.framasaas.service.mapper.ServiceOrderFranchiseAssignmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.ServiceOrderFranchiseAssignment}.
 */
@Service
@Transactional
public class ServiceOrderFranchiseAssignmentServiceImpl implements ServiceOrderFranchiseAssignmentService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderFranchiseAssignmentServiceImpl.class);

    private final ServiceOrderFranchiseAssignmentRepository serviceOrderFranchiseAssignmentRepository;

    private final ServiceOrderFranchiseAssignmentMapper serviceOrderFranchiseAssignmentMapper;

    public ServiceOrderFranchiseAssignmentServiceImpl(
        ServiceOrderFranchiseAssignmentRepository serviceOrderFranchiseAssignmentRepository,
        ServiceOrderFranchiseAssignmentMapper serviceOrderFranchiseAssignmentMapper
    ) {
        this.serviceOrderFranchiseAssignmentRepository = serviceOrderFranchiseAssignmentRepository;
        this.serviceOrderFranchiseAssignmentMapper = serviceOrderFranchiseAssignmentMapper;
    }

    @Override
    public ServiceOrderFranchiseAssignmentDTO save(ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO) {
        LOG.debug("Request to save ServiceOrderFranchiseAssignment : {}", serviceOrderFranchiseAssignmentDTO);
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentMapper.toEntity(
            serviceOrderFranchiseAssignmentDTO
        );
        serviceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.save(serviceOrderFranchiseAssignment);
        return serviceOrderFranchiseAssignmentMapper.toDto(serviceOrderFranchiseAssignment);
    }

    @Override
    public ServiceOrderFranchiseAssignmentDTO update(ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO) {
        LOG.debug("Request to update ServiceOrderFranchiseAssignment : {}", serviceOrderFranchiseAssignmentDTO);
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentMapper.toEntity(
            serviceOrderFranchiseAssignmentDTO
        );
        serviceOrderFranchiseAssignment = serviceOrderFranchiseAssignmentRepository.save(serviceOrderFranchiseAssignment);
        return serviceOrderFranchiseAssignmentMapper.toDto(serviceOrderFranchiseAssignment);
    }

    @Override
    public Optional<ServiceOrderFranchiseAssignmentDTO> partialUpdate(
        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO
    ) {
        LOG.debug("Request to partially update ServiceOrderFranchiseAssignment : {}", serviceOrderFranchiseAssignmentDTO);

        return serviceOrderFranchiseAssignmentRepository
            .findById(serviceOrderFranchiseAssignmentDTO.getId())
            .map(existingServiceOrderFranchiseAssignment -> {
                serviceOrderFranchiseAssignmentMapper.partialUpdate(
                    existingServiceOrderFranchiseAssignment,
                    serviceOrderFranchiseAssignmentDTO
                );

                return existingServiceOrderFranchiseAssignment;
            })
            .map(serviceOrderFranchiseAssignmentRepository::save)
            .map(serviceOrderFranchiseAssignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderFranchiseAssignmentDTO> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderFranchiseAssignment : {}", id);
        return serviceOrderFranchiseAssignmentRepository.findById(id).map(serviceOrderFranchiseAssignmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderFranchiseAssignment : {}", id);
        serviceOrderFranchiseAssignmentRepository.deleteById(id);
    }
}
