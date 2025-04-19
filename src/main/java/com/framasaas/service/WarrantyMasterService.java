package com.framasaas.service;

import com.framasaas.service.dto.WarrantyMasterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.domain.WarrantyMaster}.
 */
public interface WarrantyMasterService {
    /**
     * Save a warrantyMaster.
     *
     * @param warrantyMasterDTO the entity to save.
     * @return the persisted entity.
     */
    WarrantyMasterDTO save(WarrantyMasterDTO warrantyMasterDTO);

    /**
     * Updates a warrantyMaster.
     *
     * @param warrantyMasterDTO the entity to update.
     * @return the persisted entity.
     */
    WarrantyMasterDTO update(WarrantyMasterDTO warrantyMasterDTO);

    /**
     * Partially updates a warrantyMaster.
     *
     * @param warrantyMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WarrantyMasterDTO> partialUpdate(WarrantyMasterDTO warrantyMasterDTO);

    /**
     * Get all the warrantyMasters with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WarrantyMasterDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" warrantyMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WarrantyMasterDTO> findOne(Long id);

    /**
     * Delete the "id" warrantyMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
