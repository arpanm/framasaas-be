package com.framasaas.service.impl;

import com.framasaas.domain.ServiceOrderAssignment;
import com.framasaas.repository.ServiceOrderAssignmentRepository;
import com.framasaas.service.ServiceOrderAssignmentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.ServiceOrderAssignment}.
 */
@Service
@Transactional
public class ServiceOrderAssignmentServiceImpl implements ServiceOrderAssignmentService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderAssignmentServiceImpl.class);

    private final ServiceOrderAssignmentRepository serviceOrderAssignmentRepository;

    public ServiceOrderAssignmentServiceImpl(ServiceOrderAssignmentRepository serviceOrderAssignmentRepository) {
        this.serviceOrderAssignmentRepository = serviceOrderAssignmentRepository;
    }

    @Override
    public ServiceOrderAssignment save(ServiceOrderAssignment serviceOrderAssignment) {
        LOG.debug("Request to save ServiceOrderAssignment : {}", serviceOrderAssignment);
        return serviceOrderAssignmentRepository.save(serviceOrderAssignment);
    }

    @Override
    public ServiceOrderAssignment update(ServiceOrderAssignment serviceOrderAssignment) {
        LOG.debug("Request to update ServiceOrderAssignment : {}", serviceOrderAssignment);
        return serviceOrderAssignmentRepository.save(serviceOrderAssignment);
    }

    @Override
    public Optional<ServiceOrderAssignment> partialUpdate(ServiceOrderAssignment serviceOrderAssignment) {
        LOG.debug("Request to partially update ServiceOrderAssignment : {}", serviceOrderAssignment);

        return serviceOrderAssignmentRepository
            .findById(serviceOrderAssignment.getId())
            .map(existingServiceOrderAssignment -> {
                if (serviceOrderAssignment.getServiceOrderAssignmentStatus() != null) {
                    existingServiceOrderAssignment.setServiceOrderAssignmentStatus(
                        serviceOrderAssignment.getServiceOrderAssignmentStatus()
                    );
                }
                if (serviceOrderAssignment.getNps() != null) {
                    existingServiceOrderAssignment.setNps(serviceOrderAssignment.getNps());
                }
                if (serviceOrderAssignment.getIsActive() != null) {
                    existingServiceOrderAssignment.setIsActive(serviceOrderAssignment.getIsActive());
                }
                if (serviceOrderAssignment.getAssignedTime() != null) {
                    existingServiceOrderAssignment.setAssignedTime(serviceOrderAssignment.getAssignedTime());
                }
                if (serviceOrderAssignment.getMovedBackTime() != null) {
                    existingServiceOrderAssignment.setMovedBackTime(serviceOrderAssignment.getMovedBackTime());
                }
                if (serviceOrderAssignment.getVisitTime() != null) {
                    existingServiceOrderAssignment.setVisitTime(serviceOrderAssignment.getVisitTime());
                }
                if (serviceOrderAssignment.getSpareOrderTime() != null) {
                    existingServiceOrderAssignment.setSpareOrderTime(serviceOrderAssignment.getSpareOrderTime());
                }
                if (serviceOrderAssignment.getSpareDeliveryTime() != null) {
                    existingServiceOrderAssignment.setSpareDeliveryTime(serviceOrderAssignment.getSpareDeliveryTime());
                }
                if (serviceOrderAssignment.getCompletedTime() != null) {
                    existingServiceOrderAssignment.setCompletedTime(serviceOrderAssignment.getCompletedTime());
                }
                if (serviceOrderAssignment.getCompletionOTP() != null) {
                    existingServiceOrderAssignment.setCompletionOTP(serviceOrderAssignment.getCompletionOTP());
                }
                if (serviceOrderAssignment.getCancellationOTP() != null) {
                    existingServiceOrderAssignment.setCancellationOTP(serviceOrderAssignment.getCancellationOTP());
                }
                if (serviceOrderAssignment.getFranchiseCommision() != null) {
                    existingServiceOrderAssignment.setFranchiseCommision(serviceOrderAssignment.getFranchiseCommision());
                }
                if (serviceOrderAssignment.getFranchiseCommisionTax() != null) {
                    existingServiceOrderAssignment.setFranchiseCommisionTax(serviceOrderAssignment.getFranchiseCommisionTax());
                }
                if (serviceOrderAssignment.getFranchiseInvoicePath() != null) {
                    existingServiceOrderAssignment.setFranchiseInvoicePath(serviceOrderAssignment.getFranchiseInvoicePath());
                }
                if (serviceOrderAssignment.getCreateddBy() != null) {
                    existingServiceOrderAssignment.setCreateddBy(serviceOrderAssignment.getCreateddBy());
                }
                if (serviceOrderAssignment.getCreatedTime() != null) {
                    existingServiceOrderAssignment.setCreatedTime(serviceOrderAssignment.getCreatedTime());
                }
                if (serviceOrderAssignment.getUpdatedBy() != null) {
                    existingServiceOrderAssignment.setUpdatedBy(serviceOrderAssignment.getUpdatedBy());
                }
                if (serviceOrderAssignment.getUpdatedTime() != null) {
                    existingServiceOrderAssignment.setUpdatedTime(serviceOrderAssignment.getUpdatedTime());
                }

                return existingServiceOrderAssignment;
            })
            .map(serviceOrderAssignmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOrderAssignment> findAll(Pageable pageable) {
        LOG.debug("Request to get all ServiceOrderAssignments");
        return serviceOrderAssignmentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderAssignment> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderAssignment : {}", id);
        return serviceOrderAssignmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderAssignment : {}", id);
        serviceOrderAssignmentRepository.deleteById(id);
    }
}
