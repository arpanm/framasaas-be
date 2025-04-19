package com.framasaas.service;

import com.framasaas.service.dto.ServiceOrderMasterDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.ServiceOrderMaster}.
 */
public interface ServiceOrderMasterService {
    /**
     * Save a serviceOrderMaster.
     *
     * @param serviceOrderMasterDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderMasterDTO save(ServiceOrderMasterDTO serviceOrderMasterDTO);

    /**
     * Updates a serviceOrderMaster.
     *
     * @param serviceOrderMasterDTO the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderMasterDTO update(ServiceOrderMasterDTO serviceOrderMasterDTO);

    /**
     * Partially updates a serviceOrderMaster.
     *
     * @param serviceOrderMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderMasterDTO> partialUpdate(ServiceOrderMasterDTO serviceOrderMasterDTO);

    /**
     * Get the "id" serviceOrderMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderMasterDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOrderMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
