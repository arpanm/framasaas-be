package com.framasaas.service;

import com.framasaas.service.dto.InventoryHistoryDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.InventoryHistory}.
 */
public interface InventoryHistoryService {
    /**
     * Save a inventoryHistory.
     *
     * @param inventoryHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    InventoryHistoryDTO save(InventoryHistoryDTO inventoryHistoryDTO);

    /**
     * Updates a inventoryHistory.
     *
     * @param inventoryHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    InventoryHistoryDTO update(InventoryHistoryDTO inventoryHistoryDTO);

    /**
     * Partially updates a inventoryHistory.
     *
     * @param inventoryHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InventoryHistoryDTO> partialUpdate(InventoryHistoryDTO inventoryHistoryDTO);

    /**
     * Get the "id" inventoryHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InventoryHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" inventoryHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
