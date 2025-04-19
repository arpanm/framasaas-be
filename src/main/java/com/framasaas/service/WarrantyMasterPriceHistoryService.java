package com.framasaas.service;

import com.framasaas.service.dto.WarrantyMasterPriceHistoryDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.WarrantyMasterPriceHistory}.
 */
public interface WarrantyMasterPriceHistoryService {
    /**
     * Save a warrantyMasterPriceHistory.
     *
     * @param warrantyMasterPriceHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    WarrantyMasterPriceHistoryDTO save(WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO);

    /**
     * Updates a warrantyMasterPriceHistory.
     *
     * @param warrantyMasterPriceHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    WarrantyMasterPriceHistoryDTO update(WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO);

    /**
     * Partially updates a warrantyMasterPriceHistory.
     *
     * @param warrantyMasterPriceHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WarrantyMasterPriceHistoryDTO> partialUpdate(WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO);

    /**
     * Get the "id" warrantyMasterPriceHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WarrantyMasterPriceHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" warrantyMasterPriceHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
