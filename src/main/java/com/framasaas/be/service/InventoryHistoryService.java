package com.framasaas.be.service;

import com.framasaas.be.domain.InventoryHistory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.InventoryHistory}.
 */
public interface InventoryHistoryService {
    /**
     * Save a inventoryHistory.
     *
     * @param inventoryHistory the entity to save.
     * @return the persisted entity.
     */
    InventoryHistory save(InventoryHistory inventoryHistory);

    /**
     * Updates a inventoryHistory.
     *
     * @param inventoryHistory the entity to update.
     * @return the persisted entity.
     */
    InventoryHistory update(InventoryHistory inventoryHistory);

    /**
     * Partially updates a inventoryHistory.
     *
     * @param inventoryHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InventoryHistory> partialUpdate(InventoryHistory inventoryHistory);

    /**
     * Get all the inventoryHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InventoryHistory> findAll(Pageable pageable);

    /**
     * Get the "id" inventoryHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InventoryHistory> findOne(Long id);

    /**
     * Delete the "id" inventoryHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
