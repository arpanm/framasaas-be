package com.framasaas.be.service.impl;

import com.framasaas.be.domain.ServiceOrderFieldAgentAssignment;
import com.framasaas.be.repository.ServiceOrderFieldAgentAssignmentRepository;
import com.framasaas.be.service.ServiceOrderFieldAgentAssignmentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.ServiceOrderFieldAgentAssignment}.
 */
@Service
@Transactional
public class ServiceOrderFieldAgentAssignmentServiceImpl implements ServiceOrderFieldAgentAssignmentService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderFieldAgentAssignmentServiceImpl.class);

    private final ServiceOrderFieldAgentAssignmentRepository serviceOrderFieldAgentAssignmentRepository;

    public ServiceOrderFieldAgentAssignmentServiceImpl(
        ServiceOrderFieldAgentAssignmentRepository serviceOrderFieldAgentAssignmentRepository
    ) {
        this.serviceOrderFieldAgentAssignmentRepository = serviceOrderFieldAgentAssignmentRepository;
    }

    @Override
    public ServiceOrderFieldAgentAssignment save(ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment) {
        LOG.debug("Request to save ServiceOrderFieldAgentAssignment : {}", serviceOrderFieldAgentAssignment);
        return serviceOrderFieldAgentAssignmentRepository.save(serviceOrderFieldAgentAssignment);
    }

    @Override
    public ServiceOrderFieldAgentAssignment update(ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment) {
        LOG.debug("Request to update ServiceOrderFieldAgentAssignment : {}", serviceOrderFieldAgentAssignment);
        return serviceOrderFieldAgentAssignmentRepository.save(serviceOrderFieldAgentAssignment);
    }

    @Override
    public Optional<ServiceOrderFieldAgentAssignment> partialUpdate(ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment) {
        LOG.debug("Request to partially update ServiceOrderFieldAgentAssignment : {}", serviceOrderFieldAgentAssignment);

        return serviceOrderFieldAgentAssignmentRepository
            .findById(serviceOrderFieldAgentAssignment.getId())
            .map(existingServiceOrderFieldAgentAssignment -> {
                if (serviceOrderFieldAgentAssignment.getServiceOrderAssignmentStatus() != null) {
                    existingServiceOrderFieldAgentAssignment.setServiceOrderAssignmentStatus(
                        serviceOrderFieldAgentAssignment.getServiceOrderAssignmentStatus()
                    );
                }
                if (serviceOrderFieldAgentAssignment.getNps() != null) {
                    existingServiceOrderFieldAgentAssignment.setNps(serviceOrderFieldAgentAssignment.getNps());
                }
                if (serviceOrderFieldAgentAssignment.getIsActive() != null) {
                    existingServiceOrderFieldAgentAssignment.setIsActive(serviceOrderFieldAgentAssignment.getIsActive());
                }
                if (serviceOrderFieldAgentAssignment.getAssignedTime() != null) {
                    existingServiceOrderFieldAgentAssignment.setAssignedTime(serviceOrderFieldAgentAssignment.getAssignedTime());
                }
                if (serviceOrderFieldAgentAssignment.getMovedBackTime() != null) {
                    existingServiceOrderFieldAgentAssignment.setMovedBackTime(serviceOrderFieldAgentAssignment.getMovedBackTime());
                }
                if (serviceOrderFieldAgentAssignment.getVisitTime() != null) {
                    existingServiceOrderFieldAgentAssignment.setVisitTime(serviceOrderFieldAgentAssignment.getVisitTime());
                }
                if (serviceOrderFieldAgentAssignment.getSpareOrderTime() != null) {
                    existingServiceOrderFieldAgentAssignment.setSpareOrderTime(serviceOrderFieldAgentAssignment.getSpareOrderTime());
                }
                if (serviceOrderFieldAgentAssignment.getSpareDeliveryTime() != null) {
                    existingServiceOrderFieldAgentAssignment.setSpareDeliveryTime(serviceOrderFieldAgentAssignment.getSpareDeliveryTime());
                }
                if (serviceOrderFieldAgentAssignment.getCompletedTime() != null) {
                    existingServiceOrderFieldAgentAssignment.setCompletedTime(serviceOrderFieldAgentAssignment.getCompletedTime());
                }
                if (serviceOrderFieldAgentAssignment.getCompletionOTP() != null) {
                    existingServiceOrderFieldAgentAssignment.setCompletionOTP(serviceOrderFieldAgentAssignment.getCompletionOTP());
                }
                if (serviceOrderFieldAgentAssignment.getCancellationOTP() != null) {
                    existingServiceOrderFieldAgentAssignment.setCancellationOTP(serviceOrderFieldAgentAssignment.getCancellationOTP());
                }
                if (serviceOrderFieldAgentAssignment.getCreateddBy() != null) {
                    existingServiceOrderFieldAgentAssignment.setCreateddBy(serviceOrderFieldAgentAssignment.getCreateddBy());
                }
                if (serviceOrderFieldAgentAssignment.getCreatedTime() != null) {
                    existingServiceOrderFieldAgentAssignment.setCreatedTime(serviceOrderFieldAgentAssignment.getCreatedTime());
                }
                if (serviceOrderFieldAgentAssignment.getUpdatedBy() != null) {
                    existingServiceOrderFieldAgentAssignment.setUpdatedBy(serviceOrderFieldAgentAssignment.getUpdatedBy());
                }
                if (serviceOrderFieldAgentAssignment.getUpdatedTime() != null) {
                    existingServiceOrderFieldAgentAssignment.setUpdatedTime(serviceOrderFieldAgentAssignment.getUpdatedTime());
                }

                return existingServiceOrderFieldAgentAssignment;
            })
            .map(serviceOrderFieldAgentAssignmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOrderFieldAgentAssignment> findAll(Pageable pageable) {
        LOG.debug("Request to get all ServiceOrderFieldAgentAssignments");
        return serviceOrderFieldAgentAssignmentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderFieldAgentAssignment> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderFieldAgentAssignment : {}", id);
        return serviceOrderFieldAgentAssignmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderFieldAgentAssignment : {}", id);
        serviceOrderFieldAgentAssignmentRepository.deleteById(id);
    }
}
