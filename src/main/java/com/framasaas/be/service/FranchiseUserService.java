package com.framasaas.be.service;

import com.framasaas.be.domain.FranchiseUser;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.FranchiseUser}.
 */
public interface FranchiseUserService {
    /**
     * Save a franchiseUser.
     *
     * @param franchiseUser the entity to save.
     * @return the persisted entity.
     */
    FranchiseUser save(FranchiseUser franchiseUser);

    /**
     * Updates a franchiseUser.
     *
     * @param franchiseUser the entity to update.
     * @return the persisted entity.
     */
    FranchiseUser update(FranchiseUser franchiseUser);

    /**
     * Partially updates a franchiseUser.
     *
     * @param franchiseUser the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseUser> partialUpdate(FranchiseUser franchiseUser);

    /**
     * Get all the franchiseUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FranchiseUser> findAll(Pageable pageable);

    /**
     * Get the "id" franchiseUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseUser> findOne(Long id);

    /**
     * Delete the "id" franchiseUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
