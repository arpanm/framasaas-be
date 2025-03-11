package com.framasaas.be.service.impl;

import com.framasaas.be.domain.ServiceOrderSpare;
import com.framasaas.be.repository.ServiceOrderSpareRepository;
import com.framasaas.be.service.ServiceOrderSpareService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.ServiceOrderSpare}.
 */
@Service
@Transactional
public class ServiceOrderSpareServiceImpl implements ServiceOrderSpareService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderSpareServiceImpl.class);

    private final ServiceOrderSpareRepository serviceOrderSpareRepository;

    public ServiceOrderSpareServiceImpl(ServiceOrderSpareRepository serviceOrderSpareRepository) {
        this.serviceOrderSpareRepository = serviceOrderSpareRepository;
    }

    @Override
    public ServiceOrderSpare save(ServiceOrderSpare serviceOrderSpare) {
        LOG.debug("Request to save ServiceOrderSpare : {}", serviceOrderSpare);
        return serviceOrderSpareRepository.save(serviceOrderSpare);
    }

    @Override
    public ServiceOrderSpare update(ServiceOrderSpare serviceOrderSpare) {
        LOG.debug("Request to update ServiceOrderSpare : {}", serviceOrderSpare);
        return serviceOrderSpareRepository.save(serviceOrderSpare);
    }

    @Override
    public Optional<ServiceOrderSpare> partialUpdate(ServiceOrderSpare serviceOrderSpare) {
        LOG.debug("Request to partially update ServiceOrderSpare : {}", serviceOrderSpare);

        return serviceOrderSpareRepository
            .findById(serviceOrderSpare.getId())
            .map(existingServiceOrderSpare -> {
                if (serviceOrderSpare.getPrice() != null) {
                    existingServiceOrderSpare.setPrice(serviceOrderSpare.getPrice());
                }
                if (serviceOrderSpare.getTax() != null) {
                    existingServiceOrderSpare.setTax(serviceOrderSpare.getTax());
                }
                if (serviceOrderSpare.getTotalCharge() != null) {
                    existingServiceOrderSpare.setTotalCharge(serviceOrderSpare.getTotalCharge());
                }
                if (serviceOrderSpare.getOrderedFrom() != null) {
                    existingServiceOrderSpare.setOrderedFrom(serviceOrderSpare.getOrderedFrom());
                }
                if (serviceOrderSpare.getSpareStatus() != null) {
                    existingServiceOrderSpare.setSpareStatus(serviceOrderSpare.getSpareStatus());
                }
                if (serviceOrderSpare.getCreateddBy() != null) {
                    existingServiceOrderSpare.setCreateddBy(serviceOrderSpare.getCreateddBy());
                }
                if (serviceOrderSpare.getCreatedTime() != null) {
                    existingServiceOrderSpare.setCreatedTime(serviceOrderSpare.getCreatedTime());
                }
                if (serviceOrderSpare.getUpdatedBy() != null) {
                    existingServiceOrderSpare.setUpdatedBy(serviceOrderSpare.getUpdatedBy());
                }
                if (serviceOrderSpare.getUpdatedTime() != null) {
                    existingServiceOrderSpare.setUpdatedTime(serviceOrderSpare.getUpdatedTime());
                }

                return existingServiceOrderSpare;
            })
            .map(serviceOrderSpareRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOrderSpare> findAll(Pageable pageable) {
        LOG.debug("Request to get all ServiceOrderSpares");
        return serviceOrderSpareRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderSpare> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderSpare : {}", id);
        return serviceOrderSpareRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderSpare : {}", id);
        serviceOrderSpareRepository.deleteById(id);
    }
}
