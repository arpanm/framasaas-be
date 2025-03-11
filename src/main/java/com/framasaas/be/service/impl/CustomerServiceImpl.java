package com.framasaas.be.service.impl;

import com.framasaas.be.domain.Customer;
import com.framasaas.be.repository.CustomerRepository;
import com.framasaas.be.service.CustomerService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.Customer}.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        LOG.debug("Request to save Customer : {}", customer);
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        LOG.debug("Request to update Customer : {}", customer);
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> partialUpdate(Customer customer) {
        LOG.debug("Request to partially update Customer : {}", customer);

        return customerRepository
            .findById(customer.getId())
            .map(existingCustomer -> {
                if (customer.getEmail() != null) {
                    existingCustomer.setEmail(customer.getEmail());
                }
                if (customer.getContact() != null) {
                    existingCustomer.setContact(customer.getContact());
                }
                if (customer.getAlternameContact() != null) {
                    existingCustomer.setAlternameContact(customer.getAlternameContact());
                }
                if (customer.getLanguage() != null) {
                    existingCustomer.setLanguage(customer.getLanguage());
                }
                if (customer.getUserStatus() != null) {
                    existingCustomer.setUserStatus(customer.getUserStatus());
                }
                if (customer.getCreateddBy() != null) {
                    existingCustomer.setCreateddBy(customer.getCreateddBy());
                }
                if (customer.getCreatedTime() != null) {
                    existingCustomer.setCreatedTime(customer.getCreatedTime());
                }
                if (customer.getUpdatedBy() != null) {
                    existingCustomer.setUpdatedBy(customer.getUpdatedBy());
                }
                if (customer.getUpdatedTime() != null) {
                    existingCustomer.setUpdatedTime(customer.getUpdatedTime());
                }

                return existingCustomer;
            })
            .map(customerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        LOG.debug("Request to get all Customers");
        return customerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findOne(Long id) {
        LOG.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }
}
