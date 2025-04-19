package com.framasaas.service;

import com.framasaas.service.dto.FranchiseDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.Franchise}.
 */
public interface FranchiseService {
    /**
     * Save a franchise.
     *
     * @param franchiseDTO the entity to save.
     * @return the persisted entity.
     */
    FranchiseDTO save(FranchiseDTO franchiseDTO);

    /**
     * Updates a franchise.
     *
     * @param franchiseDTO the entity to update.
     * @return the persisted entity.
     */
    FranchiseDTO update(FranchiseDTO franchiseDTO);

    /**
     * Partially updates a franchise.
     *
     * @param franchiseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseDTO> partialUpdate(FranchiseDTO franchiseDTO);

    /**
     * Get the "id" franchise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseDTO> findOne(Long id);

    /**
     * Delete the "id" franchise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
