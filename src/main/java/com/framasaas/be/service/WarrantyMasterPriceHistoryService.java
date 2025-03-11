package com.framasaas.be.service;

import com.framasaas.be.domain.WarrantyMasterPriceHistory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.WarrantyMasterPriceHistory}.
 */
public interface WarrantyMasterPriceHistoryService {
    /**
     * Save a warrantyMasterPriceHistory.
     *
     * @param warrantyMasterPriceHistory the entity to save.
     * @return the persisted entity.
     */
    WarrantyMasterPriceHistory save(WarrantyMasterPriceHistory warrantyMasterPriceHistory);

    /**
     * Updates a warrantyMasterPriceHistory.
     *
     * @param warrantyMasterPriceHistory the entity to update.
     * @return the persisted entity.
     */
    WarrantyMasterPriceHistory update(WarrantyMasterPriceHistory warrantyMasterPriceHistory);

    /**
     * Partially updates a warrantyMasterPriceHistory.
     *
     * @param warrantyMasterPriceHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WarrantyMasterPriceHistory> partialUpdate(WarrantyMasterPriceHistory warrantyMasterPriceHistory);

    /**
     * Get all the warrantyMasterPriceHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WarrantyMasterPriceHistory> findAll(Pageable pageable);

    /**
     * Get the "id" warrantyMasterPriceHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WarrantyMasterPriceHistory> findOne(Long id);

    /**
     * Delete the "id" warrantyMasterPriceHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
