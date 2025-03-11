package com.framasaas.be.service.impl;

import com.framasaas.be.domain.ServiceOrderMaster;
import com.framasaas.be.repository.ServiceOrderMasterRepository;
import com.framasaas.be.service.ServiceOrderMasterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.ServiceOrderMaster}.
 */
@Service
@Transactional
public class ServiceOrderMasterServiceImpl implements ServiceOrderMasterService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderMasterServiceImpl.class);

    private final ServiceOrderMasterRepository serviceOrderMasterRepository;

    public ServiceOrderMasterServiceImpl(ServiceOrderMasterRepository serviceOrderMasterRepository) {
        this.serviceOrderMasterRepository = serviceOrderMasterRepository;
    }

    @Override
    public ServiceOrderMaster save(ServiceOrderMaster serviceOrderMaster) {
        LOG.debug("Request to save ServiceOrderMaster : {}", serviceOrderMaster);
        return serviceOrderMasterRepository.save(serviceOrderMaster);
    }

    @Override
    public ServiceOrderMaster update(ServiceOrderMaster serviceOrderMaster) {
        LOG.debug("Request to update ServiceOrderMaster : {}", serviceOrderMaster);
        return serviceOrderMasterRepository.save(serviceOrderMaster);
    }

    @Override
    public Optional<ServiceOrderMaster> partialUpdate(ServiceOrderMaster serviceOrderMaster) {
        LOG.debug("Request to partially update ServiceOrderMaster : {}", serviceOrderMaster);

        return serviceOrderMasterRepository
            .findById(serviceOrderMaster.getId())
            .map(existingServiceOrderMaster -> {
                if (serviceOrderMaster.getServiceOrderType() != null) {
                    existingServiceOrderMaster.setServiceOrderType(serviceOrderMaster.getServiceOrderType());
                }
                if (serviceOrderMaster.getSlaInHours() != null) {
                    existingServiceOrderMaster.setSlaInHours(serviceOrderMaster.getSlaInHours());
                }
                if (serviceOrderMaster.getCharge() != null) {
                    existingServiceOrderMaster.setCharge(serviceOrderMaster.getCharge());
                }
                if (serviceOrderMaster.getTax() != null) {
                    existingServiceOrderMaster.setTax(serviceOrderMaster.getTax());
                }
                if (serviceOrderMaster.getFranchiseCommissionWithinSla() != null) {
                    existingServiceOrderMaster.setFranchiseCommissionWithinSla(serviceOrderMaster.getFranchiseCommissionWithinSla());
                }
                if (serviceOrderMaster.getFranchiseTaxWithinSlaTax() != null) {
                    existingServiceOrderMaster.setFranchiseTaxWithinSlaTax(serviceOrderMaster.getFranchiseTaxWithinSlaTax());
                }
                if (serviceOrderMaster.getFranchiseCommissionOutsideSla() != null) {
                    existingServiceOrderMaster.setFranchiseCommissionOutsideSla(serviceOrderMaster.getFranchiseCommissionOutsideSla());
                }
                if (serviceOrderMaster.getFranchiseTaxOutsideSlaTax() != null) {
                    existingServiceOrderMaster.setFranchiseTaxOutsideSlaTax(serviceOrderMaster.getFranchiseTaxOutsideSlaTax());
                }
                if (serviceOrderMaster.getIsActive() != null) {
                    existingServiceOrderMaster.setIsActive(serviceOrderMaster.getIsActive());
                }
                if (serviceOrderMaster.getCreateddBy() != null) {
                    existingServiceOrderMaster.setCreateddBy(serviceOrderMaster.getCreateddBy());
                }
                if (serviceOrderMaster.getCreatedTime() != null) {
                    existingServiceOrderMaster.setCreatedTime(serviceOrderMaster.getCreatedTime());
                }
                if (serviceOrderMaster.getUpdatedBy() != null) {
                    existingServiceOrderMaster.setUpdatedBy(serviceOrderMaster.getUpdatedBy());
                }
                if (serviceOrderMaster.getUpdatedTime() != null) {
                    existingServiceOrderMaster.setUpdatedTime(serviceOrderMaster.getUpdatedTime());
                }

                return existingServiceOrderMaster;
            })
            .map(serviceOrderMasterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOrderMaster> findAll(Pageable pageable) {
        LOG.debug("Request to get all ServiceOrderMasters");
        return serviceOrderMasterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderMaster> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderMaster : {}", id);
        return serviceOrderMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderMaster : {}", id);
        serviceOrderMasterRepository.deleteById(id);
    }
}
