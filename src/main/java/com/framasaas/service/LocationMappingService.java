package com.framasaas.service;

import com.framasaas.service.dto.LocationMappingDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.LocationMapping}.
 */
public interface LocationMappingService {
    /**
     * Save a locationMapping.
     *
     * @param locationMappingDTO the entity to save.
     * @return the persisted entity.
     */
    LocationMappingDTO save(LocationMappingDTO locationMappingDTO);

    /**
     * Updates a locationMapping.
     *
     * @param locationMappingDTO the entity to update.
     * @return the persisted entity.
     */
    LocationMappingDTO update(LocationMappingDTO locationMappingDTO);

    /**
     * Partially updates a locationMapping.
     *
     * @param locationMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocationMappingDTO> partialUpdate(LocationMappingDTO locationMappingDTO);

    /**
     * Get the "id" locationMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocationMappingDTO> findOne(Long id);

    /**
     * Delete the "id" locationMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
