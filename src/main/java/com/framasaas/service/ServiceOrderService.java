package com.framasaas.service;

import com.framasaas.service.dto.ServiceOrderDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.ServiceOrder}.
 */
public interface ServiceOrderService {
    /**
     * Save a serviceOrder.
     *
     * @param serviceOrderDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderDTO save(ServiceOrderDTO serviceOrderDTO);

    /**
     * Updates a serviceOrder.
     *
     * @param serviceOrderDTO the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderDTO update(ServiceOrderDTO serviceOrderDTO);

    /**
     * Partially updates a serviceOrder.
     *
     * @param serviceOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderDTO> partialUpdate(ServiceOrderDTO serviceOrderDTO);

    /**
     * Get the "id" serviceOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
