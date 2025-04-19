package com.framasaas.service;

import com.framasaas.service.dto.FranchiseUserDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.FranchiseUser}.
 */
public interface FranchiseUserService {
    /**
     * Save a franchiseUser.
     *
     * @param franchiseUserDTO the entity to save.
     * @return the persisted entity.
     */
    FranchiseUserDTO save(FranchiseUserDTO franchiseUserDTO);

    /**
     * Updates a franchiseUser.
     *
     * @param franchiseUserDTO the entity to update.
     * @return the persisted entity.
     */
    FranchiseUserDTO update(FranchiseUserDTO franchiseUserDTO);

    /**
     * Partially updates a franchiseUser.
     *
     * @param franchiseUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseUserDTO> partialUpdate(FranchiseUserDTO franchiseUserDTO);

    /**
     * Get the "id" franchiseUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseUserDTO> findOne(Long id);

    /**
     * Delete the "id" franchiseUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
