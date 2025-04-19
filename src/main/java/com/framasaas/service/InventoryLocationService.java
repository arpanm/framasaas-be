package com.framasaas.service;

import com.framasaas.service.dto.InventoryLocationDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.InventoryLocation}.
 */
public interface InventoryLocationService {
    /**
     * Save a inventoryLocation.
     *
     * @param inventoryLocationDTO the entity to save.
     * @return the persisted entity.
     */
    InventoryLocationDTO save(InventoryLocationDTO inventoryLocationDTO);

    /**
     * Updates a inventoryLocation.
     *
     * @param inventoryLocationDTO the entity to update.
     * @return the persisted entity.
     */
    InventoryLocationDTO update(InventoryLocationDTO inventoryLocationDTO);

    /**
     * Partially updates a inventoryLocation.
     *
     * @param inventoryLocationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InventoryLocationDTO> partialUpdate(InventoryLocationDTO inventoryLocationDTO);

    /**
     * Get the "id" inventoryLocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InventoryLocationDTO> findOne(Long id);

    /**
     * Delete the "id" inventoryLocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
