package com.framasaas.service;

import com.framasaas.service.dto.InventoryDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.Inventory}.
 */
public interface InventoryService {
    /**
     * Save a inventory.
     *
     * @param inventoryDTO the entity to save.
     * @return the persisted entity.
     */
    InventoryDTO save(InventoryDTO inventoryDTO);

    /**
     * Updates a inventory.
     *
     * @param inventoryDTO the entity to update.
     * @return the persisted entity.
     */
    InventoryDTO update(InventoryDTO inventoryDTO);

    /**
     * Partially updates a inventory.
     *
     * @param inventoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InventoryDTO> partialUpdate(InventoryDTO inventoryDTO);

    /**
     * Get the "id" inventory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InventoryDTO> findOne(Long id);

    /**
     * Delete the "id" inventory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
