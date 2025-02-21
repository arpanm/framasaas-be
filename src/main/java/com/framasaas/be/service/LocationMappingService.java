package com.framasaas.be.service;

import com.framasaas.be.domain.LocationMapping;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.LocationMapping}.
 */
public interface LocationMappingService {
    /**
     * Save a locationMapping.
     *
     * @param locationMapping the entity to save.
     * @return the persisted entity.
     */
    LocationMapping save(LocationMapping locationMapping);

    /**
     * Updates a locationMapping.
     *
     * @param locationMapping the entity to update.
     * @return the persisted entity.
     */
    LocationMapping update(LocationMapping locationMapping);

    /**
     * Partially updates a locationMapping.
     *
     * @param locationMapping the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocationMapping> partialUpdate(LocationMapping locationMapping);

    /**
     * Get all the locationMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LocationMapping> findAll(Pageable pageable);

    /**
     * Get the "id" locationMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocationMapping> findOne(Long id);

    /**
     * Delete the "id" locationMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
