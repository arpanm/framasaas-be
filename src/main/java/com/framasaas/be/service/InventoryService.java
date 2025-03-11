package com.framasaas.be.service;

import com.framasaas.be.domain.Inventory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.Inventory}.
 */
public interface InventoryService {
    /**
     * Save a inventory.
     *
     * @param inventory the entity to save.
     * @return the persisted entity.
     */
    Inventory save(Inventory inventory);

    /**
     * Updates a inventory.
     *
     * @param inventory the entity to update.
     * @return the persisted entity.
     */
    Inventory update(Inventory inventory);

    /**
     * Partially updates a inventory.
     *
     * @param inventory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Inventory> partialUpdate(Inventory inventory);

    /**
     * Get all the inventories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Inventory> findAll(Pageable pageable);

    /**
     * Get the "id" inventory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Inventory> findOne(Long id);

    /**
     * Delete the "id" inventory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
