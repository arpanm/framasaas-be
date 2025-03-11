package com.framasaas.be.service;

import com.framasaas.be.domain.WarrantyMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.WarrantyMaster}.
 */
public interface WarrantyMasterService {
    /**
     * Save a warrantyMaster.
     *
     * @param warrantyMaster the entity to save.
     * @return the persisted entity.
     */
    WarrantyMaster save(WarrantyMaster warrantyMaster);

    /**
     * Updates a warrantyMaster.
     *
     * @param warrantyMaster the entity to update.
     * @return the persisted entity.
     */
    WarrantyMaster update(WarrantyMaster warrantyMaster);

    /**
     * Partially updates a warrantyMaster.
     *
     * @param warrantyMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WarrantyMaster> partialUpdate(WarrantyMaster warrantyMaster);

    /**
     * Get all the warrantyMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WarrantyMaster> findAll(Pageable pageable);

    /**
     * Get the "id" warrantyMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WarrantyMaster> findOne(Long id);

    /**
     * Delete the "id" warrantyMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
