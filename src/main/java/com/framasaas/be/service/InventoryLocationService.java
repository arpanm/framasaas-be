package com.framasaas.be.service;

import com.framasaas.be.domain.InventoryLocation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.InventoryLocation}.
 */
public interface InventoryLocationService {
    /**
     * Save a inventoryLocation.
     *
     * @param inventoryLocation the entity to save.
     * @return the persisted entity.
     */
    InventoryLocation save(InventoryLocation inventoryLocation);

    /**
     * Updates a inventoryLocation.
     *
     * @param inventoryLocation the entity to update.
     * @return the persisted entity.
     */
    InventoryLocation update(InventoryLocation inventoryLocation);

    /**
     * Partially updates a inventoryLocation.
     *
     * @param inventoryLocation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InventoryLocation> partialUpdate(InventoryLocation inventoryLocation);

    /**
     * Get all the inventoryLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InventoryLocation> findAll(Pageable pageable);

    /**
     * Get the "id" inventoryLocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InventoryLocation> findOne(Long id);

    /**
     * Delete the "id" inventoryLocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
