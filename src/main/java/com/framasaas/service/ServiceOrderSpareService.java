package com.framasaas.service;

import com.framasaas.service.dto.ServiceOrderSpareDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.ServiceOrderSpare}.
 */
public interface ServiceOrderSpareService {
    /**
     * Save a serviceOrderSpare.
     *
     * @param serviceOrderSpareDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderSpareDTO save(ServiceOrderSpareDTO serviceOrderSpareDTO);

    /**
     * Updates a serviceOrderSpare.
     *
     * @param serviceOrderSpareDTO the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderSpareDTO update(ServiceOrderSpareDTO serviceOrderSpareDTO);

    /**
     * Partially updates a serviceOrderSpare.
     *
     * @param serviceOrderSpareDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderSpareDTO> partialUpdate(ServiceOrderSpareDTO serviceOrderSpareDTO);

    /**
     * Get the "id" serviceOrderSpare.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderSpareDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOrderSpare.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
