package com.framasaas.be.service;

import com.framasaas.be.domain.Franchise;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.Franchise}.
 */
public interface FranchiseService {
    /**
     * Save a franchise.
     *
     * @param franchise the entity to save.
     * @return the persisted entity.
     */
    Franchise save(Franchise franchise);

    /**
     * Updates a franchise.
     *
     * @param franchise the entity to update.
     * @return the persisted entity.
     */
    Franchise update(Franchise franchise);

    /**
     * Partially updates a franchise.
     *
     * @param franchise the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Franchise> partialUpdate(Franchise franchise);

    /**
     * Get all the franchises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Franchise> findAll(Pageable pageable);

    /**
     * Get the "id" franchise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Franchise> findOne(Long id);

    /**
     * Delete the "id" franchise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
