package com.framasaas.be.service.impl;

import com.framasaas.be.domain.ServiceOrder;
import com.framasaas.be.repository.ServiceOrderRepository;
import com.framasaas.be.service.ServiceOrderService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.ServiceOrder}.
 */
@Service
@Transactional
public class ServiceOrderServiceImpl implements ServiceOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderServiceImpl.class);

    private final ServiceOrderRepository serviceOrderRepository;

    public ServiceOrderServiceImpl(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Override
    public ServiceOrder save(ServiceOrder serviceOrder) {
        LOG.debug("Request to save ServiceOrder : {}", serviceOrder);
        return serviceOrderRepository.save(serviceOrder);
    }

    @Override
    public ServiceOrder update(ServiceOrder serviceOrder) {
        LOG.debug("Request to update ServiceOrder : {}", serviceOrder);
        return serviceOrderRepository.save(serviceOrder);
    }

    @Override
    public Optional<ServiceOrder> partialUpdate(ServiceOrder serviceOrder) {
        LOG.debug("Request to partially update ServiceOrder : {}", serviceOrder);

        return serviceOrderRepository
            .findById(serviceOrder.getId())
            .map(existingServiceOrder -> {
                if (serviceOrder.getDescription() != null) {
                    existingServiceOrder.setDescription(serviceOrder.getDescription());
                }
                if (serviceOrder.getOrderType() != null) {
                    existingServiceOrder.setOrderType(serviceOrder.getOrderType());
                }
                if (serviceOrder.getOrderStatus() != null) {
                    existingServiceOrder.setOrderStatus(serviceOrder.getOrderStatus());
                }
                if (serviceOrder.getInWarranty() != null) {
                    existingServiceOrder.setInWarranty(serviceOrder.getInWarranty());
                }
                if (serviceOrder.getIsFree() != null) {
                    existingServiceOrder.setIsFree(serviceOrder.getIsFree());
                }
                if (serviceOrder.getIsSpareNeeded() != null) {
                    existingServiceOrder.setIsSpareNeeded(serviceOrder.getIsSpareNeeded());
                }
                if (serviceOrder.getConfirmedTime() != null) {
                    existingServiceOrder.setConfirmedTime(serviceOrder.getConfirmedTime());
                }
                if (serviceOrder.getClosedTime() != null) {
                    existingServiceOrder.setClosedTime(serviceOrder.getClosedTime());
                }
                if (serviceOrder.getServiceCharge() != null) {
                    existingServiceOrder.setServiceCharge(serviceOrder.getServiceCharge());
                }
                if (serviceOrder.getTax() != null) {
                    existingServiceOrder.setTax(serviceOrder.getTax());
                }
                if (serviceOrder.getTotalSpareCharges() != null) {
                    existingServiceOrder.setTotalSpareCharges(serviceOrder.getTotalSpareCharges());
                }
                if (serviceOrder.getTotalSpareTax() != null) {
                    existingServiceOrder.setTotalSpareTax(serviceOrder.getTotalSpareTax());
                }
                if (serviceOrder.getTotalCharges() != null) {
                    existingServiceOrder.setTotalCharges(serviceOrder.getTotalCharges());
                }
                if (serviceOrder.getTotalPaymentDone() != null) {
                    existingServiceOrder.setTotalPaymentDone(serviceOrder.getTotalPaymentDone());
                }
                if (serviceOrder.getCustomerInvoicePath() != null) {
                    existingServiceOrder.setCustomerInvoicePath(serviceOrder.getCustomerInvoicePath());
                }
                if (serviceOrder.getNps() != null) {
                    existingServiceOrder.setNps(serviceOrder.getNps());
                }
                if (serviceOrder.getPriority() != null) {
                    existingServiceOrder.setPriority(serviceOrder.getPriority());
                }
                if (serviceOrder.getIsActive() != null) {
                    existingServiceOrder.setIsActive(serviceOrder.getIsActive());
                }
                if (serviceOrder.getCreateddBy() != null) {
                    existingServiceOrder.setCreateddBy(serviceOrder.getCreateddBy());
                }
                if (serviceOrder.getCreatedTime() != null) {
                    existingServiceOrder.setCreatedTime(serviceOrder.getCreatedTime());
                }
                if (serviceOrder.getUpdatedBy() != null) {
                    existingServiceOrder.setUpdatedBy(serviceOrder.getUpdatedBy());
                }
                if (serviceOrder.getUpdatedTime() != null) {
                    existingServiceOrder.setUpdatedTime(serviceOrder.getUpdatedTime());
                }

                return existingServiceOrder;
            })
            .map(serviceOrderRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOrder> findAll(Pageable pageable) {
        LOG.debug("Request to get all ServiceOrders");
        return serviceOrderRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrder> findOne(Long id) {
        LOG.debug("Request to get ServiceOrder : {}", id);
        return serviceOrderRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrder : {}", id);
        serviceOrderRepository.deleteById(id);
    }
}
