package com.framasaas.be.service.impl;

import com.framasaas.be.domain.ServiceOrderPayment;
import com.framasaas.be.repository.ServiceOrderPaymentRepository;
import com.framasaas.be.service.ServiceOrderPaymentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.ServiceOrderPayment}.
 */
@Service
@Transactional
public class ServiceOrderPaymentServiceImpl implements ServiceOrderPaymentService {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceOrderPaymentServiceImpl.class);

    private final ServiceOrderPaymentRepository serviceOrderPaymentRepository;

    public ServiceOrderPaymentServiceImpl(ServiceOrderPaymentRepository serviceOrderPaymentRepository) {
        this.serviceOrderPaymentRepository = serviceOrderPaymentRepository;
    }

    @Override
    public ServiceOrderPayment save(ServiceOrderPayment serviceOrderPayment) {
        LOG.debug("Request to save ServiceOrderPayment : {}", serviceOrderPayment);
        return serviceOrderPaymentRepository.save(serviceOrderPayment);
    }

    @Override
    public ServiceOrderPayment update(ServiceOrderPayment serviceOrderPayment) {
        LOG.debug("Request to update ServiceOrderPayment : {}", serviceOrderPayment);
        return serviceOrderPaymentRepository.save(serviceOrderPayment);
    }

    @Override
    public Optional<ServiceOrderPayment> partialUpdate(ServiceOrderPayment serviceOrderPayment) {
        LOG.debug("Request to partially update ServiceOrderPayment : {}", serviceOrderPayment);

        return serviceOrderPaymentRepository
            .findById(serviceOrderPayment.getId())
            .map(existingServiceOrderPayment -> {
                if (serviceOrderPayment.getPaymentLink() != null) {
                    existingServiceOrderPayment.setPaymentLink(serviceOrderPayment.getPaymentLink());
                }
                if (serviceOrderPayment.getPaymentStatus() != null) {
                    existingServiceOrderPayment.setPaymentStatus(serviceOrderPayment.getPaymentStatus());
                }
                if (serviceOrderPayment.getMop() != null) {
                    existingServiceOrderPayment.setMop(serviceOrderPayment.getMop());
                }
                if (serviceOrderPayment.getPgTxnId() != null) {
                    existingServiceOrderPayment.setPgTxnId(serviceOrderPayment.getPgTxnId());
                }
                if (serviceOrderPayment.getPgTxnResponse() != null) {
                    existingServiceOrderPayment.setPgTxnResponse(serviceOrderPayment.getPgTxnResponse());
                }
                if (serviceOrderPayment.getPgTxnResponseTime() != null) {
                    existingServiceOrderPayment.setPgTxnResponseTime(serviceOrderPayment.getPgTxnResponseTime());
                }
                if (serviceOrderPayment.getPaymentInitiatedBy() != null) {
                    existingServiceOrderPayment.setPaymentInitiatedBy(serviceOrderPayment.getPaymentInitiatedBy());
                }
                if (serviceOrderPayment.getIsActive() != null) {
                    existingServiceOrderPayment.setIsActive(serviceOrderPayment.getIsActive());
                }
                if (serviceOrderPayment.getCreateddBy() != null) {
                    existingServiceOrderPayment.setCreateddBy(serviceOrderPayment.getCreateddBy());
                }
                if (serviceOrderPayment.getCreatedTime() != null) {
                    existingServiceOrderPayment.setCreatedTime(serviceOrderPayment.getCreatedTime());
                }
                if (serviceOrderPayment.getUpdatedBy() != null) {
                    existingServiceOrderPayment.setUpdatedBy(serviceOrderPayment.getUpdatedBy());
                }
                if (serviceOrderPayment.getUpdatedTime() != null) {
                    existingServiceOrderPayment.setUpdatedTime(serviceOrderPayment.getUpdatedTime());
                }

                return existingServiceOrderPayment;
            })
            .map(serviceOrderPaymentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOrderPayment> findAll(Pageable pageable) {
        LOG.debug("Request to get all ServiceOrderPayments");
        return serviceOrderPaymentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOrderPayment> findOne(Long id) {
        LOG.debug("Request to get ServiceOrderPayment : {}", id);
        return serviceOrderPaymentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ServiceOrderPayment : {}", id);
        serviceOrderPaymentRepository.deleteById(id);
    }
}
