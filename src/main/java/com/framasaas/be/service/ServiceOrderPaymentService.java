package com.framasaas.be.service;

import com.framasaas.be.domain.ServiceOrderPayment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.ServiceOrderPayment}.
 */
public interface ServiceOrderPaymentService {
    /**
     * Save a serviceOrderPayment.
     *
     * @param serviceOrderPayment the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderPayment save(ServiceOrderPayment serviceOrderPayment);

    /**
     * Updates a serviceOrderPayment.
     *
     * @param serviceOrderPayment the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderPayment update(ServiceOrderPayment serviceOrderPayment);

    /**
     * Partially updates a serviceOrderPayment.
     *
     * @param serviceOrderPayment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderPayment> partialUpdate(ServiceOrderPayment serviceOrderPayment);

    /**
     * Get all the serviceOrderPayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOrderPayment> findAll(Pageable pageable);

    /**
     * Get the "id" serviceOrderPayment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderPayment> findOne(Long id);

    /**
     * Delete the "id" serviceOrderPayment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
