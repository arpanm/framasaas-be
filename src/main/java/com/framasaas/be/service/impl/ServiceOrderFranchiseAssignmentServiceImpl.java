package com.framasaas.be.service.impl;

import com.framasaas.be.domain.ServiceOrderFranchiseAssignment;
import com.framasaas.be.repository.ServiceOrderFranchiseAssignmentRepository;
import com.framasaas.be.service.ServiceOrderFranchiseAssignmentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.ServiceOrderFranchiseAssignment}.
 */
@Service
@Transactional
public class ServiceOrderFranchiseAssignmentServiceImpl implements ServiceOrderFranchiseAssignmentService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderFranchiseAssignmentServiceImpl.class);

    private final ServiceOrderFranchiseAssignmentRepository serviceOrderFranchiseAssignmentRepository;

    public ServiceOrderFranchiseAssignmentServiceImpl(ServiceOrderFranchiseAssignmentRepository serviceOrderFranchiseAssignmentRepository) {
        this.serviceOrderFranchiseAssignmentRepository = serviceOrderFranchiseAssignmentRepository;
    }

    @Override
    public ServiceOrderFranchiseAssignment save(ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment) {
        LOG.debug("Request to save ServiceOrderFranchiseAssignment : {}", serviceOrderFranchiseAssignment);
        return serviceOrderFranchiseAssignmentRepository.save(serviceOrderFranchiseAssignment);
    }

    @Override
    public ServiceOrderFranchiseAssignment update(ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment) {
        LOG.debug("Request to update ServiceOrderFranchiseAssignment : {}", serviceOrderFranchiseAssignment);
        return serviceOrderFranchiseAssignmentRepository.save(serviceOrderFranchiseAssignment);
    }

    @Override
    public Optional<ServiceOrderFranchiseAssignment> partialUpdate(ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment) {
        LOG.debug("Request to partially update ServiceOrderFranchiseAssignment : {}", serviceOrderFranchiseAssignment);

        return serviceOrderFranchiseAssignmentRepository
            .findById(serviceOrderFranchiseAssignment.getId())
            .map(existingServiceOrderFranchiseAssignment -> {
                if (serviceOrderFranchiseAssignment.getServiceOrderAssignmentStatus() != null) {
                    existingServiceOrderFranchiseAssignment.setServiceOrderAssignmentStatus(
                        serviceOrderFranchiseAssignment.getServiceOrderAssignmentStatus()
                    );
                }
                if (serviceOrderFranchiseAssignment.getNps() != null) {
                    existingServiceOrderFranchiseAssignment.setNps(serviceOrderFranchiseAssignment.getNps());
                }
                if (serviceOrderFranchiseAssignment.getIsActive() != null) {
                    existingServiceOrderFranchiseAssignment.setIsActive(serviceOrderFranchiseAssignment.getIsActive());
                }
                if (serviceOrderFranchiseAssignment.getAssignedTime() != null) {
                    existingServiceOrderFranchiseAssignment.setAssignedTime(serviceOrderFranchiseAssignment.getAssignedTime());
                }
                if (serviceOrderFranchiseAssignment.getMovedBackTime() != null) {
                    existingServiceOrderFranchiseAssignment.setMovedBackTime(serviceOrderFranchiseAssignment.getMovedBackTime());
                }
                if (serviceOrderFranchiseAssignment.getVisitTime() != null) {
                    existingServiceOrderFranchiseAssignment.setVisitTime(serviceOrderFranchiseAssignment.getVisitTime());
                }
                if (serviceOrderFranchiseAssignment.getSpareOrderTime() != null) {
                    existingServiceOrderFranchiseAssignment.setSpareOrderTime(serviceOrderFranchiseAssignment.getSpareOrderTime());
                }
                if (serviceOrderFranchiseAssignment.getSpareDeliveryTime() != null) {
                    existingServiceOrderFranchiseAssignment.setSpareDeliveryTime(serviceOrderFranchiseAssignment.getSpareDeliveryTime());
                }
                if (serviceOrderFranchiseAssignment.getCompletedTime() != null) {
                    existingServiceOrderFranchiseAssignment.setCompletedTime(serviceOrderFranchiseAssignment.getCompletedTime());
                }
                if (serviceOrderFranchiseAssignment.getFranchiseCommision() != null) {
                    existingServiceOrderFranchiseAssignment.setFranchiseCommision(serviceOrderFranchiseAssignment.getFranchiseCommision());
                }
                if (serviceOrderFranchiseAssignment.getFranchiseCommisionTax() != null) {
                    existingServiceOrderFranchiseAssignment.setFranchiseCommisionTax(
                        serviceOrderFranchiseAssignment.getFranchiseCommisionTax()
                    );
                }
                if (serviceOrderFranchiseAssignment.getFranchiseInvoicePath() != null) {
                    existingServiceOrderFranchiseAssignment.setFranchiseInvoicePath(
                        serviceOrderFranchiseAssignment.getFranchiseInvoicePath()
                    );
                }
                if (serviceOrderFranchiseAssignment.getCreateddBy() != null) {
                    existingServiceOrderFranchiseAssignment.setCreateddBy(serviceOrderFranchiseAssignment.getCreateddBy());
                }
                if (serviceOrderFranchiseAssignment.getCreatedTime() != null) {
                    existingServiceOrderFranchiseAssignment.setCreatedTime(serviceOrderFranchiseAssignment.getCreatedTime());
                }
                if (serviceOrderFranchiseAssignment.getUpdatedBy() != null) {
                    existingServiceOrderFranchiseAssignment.setUpdatedBy(serviceOrderFranchiseAssignment.getUpdatedBy());
                }
                if (serviceOrderFranchiseAssignment.getUpdatedTime() != null) {
                    existingServiceOrderFranchiseAssignment.setUpdatedTime(serviceOrderFranchiseAssignment.getUpdatedTime());
                }

                return existingServiceOrderFranchiseAssignment;
            })
            .map(serviceOrderFranchiseAssignmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOrderFranchiseAssignment> findAll(Pageable pageable) {
        LOG.debug("Request to get all ServiceOrderFranchiseAssignments");
        return serviceOrderFranchiseAssignmentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderFranchiseAssignment> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderFranchiseAssignment : {}", id);
        return serviceOrderFranchiseAssignmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderFranchiseAssignment : {}", id);
        serviceOrderFranchiseAssignmentRepository.deleteById(id);
    }
}
