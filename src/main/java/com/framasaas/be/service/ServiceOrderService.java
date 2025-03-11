package com.framasaas.be.service;

import com.framasaas.be.domain.ServiceOrder;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.ServiceOrder}.
 */
public interface ServiceOrderService {
    /**
     * Save a serviceOrder.
     *
     * @param serviceOrder the entity to save.
     * @return the persisted entity.
     */
    ServiceOrder save(ServiceOrder serviceOrder);

    /**
     * Updates a serviceOrder.
     *
     * @param serviceOrder the entity to update.
     * @return the persisted entity.
     */
    ServiceOrder update(ServiceOrder serviceOrder);

    /**
     * Partially updates a serviceOrder.
     *
     * @param serviceOrder the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrder> partialUpdate(ServiceOrder serviceOrder);

    /**
     * Get all the serviceOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOrder> findAll(Pageable pageable);

    /**
     * Get the "id" serviceOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrder> findOne(Long id);

    /**
     * Delete the "id" serviceOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
