package com.framasaas.service;

import com.framasaas.service.dto.ServiceOrderPaymentDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.ServiceOrderPayment}.
 */
public interface ServiceOrderPaymentService {
    /**
     * Save a serviceOrderPayment.
     *
     * @param serviceOrderPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderPaymentDTO save(ServiceOrderPaymentDTO serviceOrderPaymentDTO);

    /**
     * Updates a serviceOrderPayment.
     *
     * @param serviceOrderPaymentDTO the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderPaymentDTO update(ServiceOrderPaymentDTO serviceOrderPaymentDTO);

    /**
     * Partially updates a serviceOrderPayment.
     *
     * @param serviceOrderPaymentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderPaymentDTO> partialUpdate(ServiceOrderPaymentDTO serviceOrderPaymentDTO);

    /**
     * Get the "id" serviceOrderPayment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderPaymentDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOrderPayment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
