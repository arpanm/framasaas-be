package com.framasaas.be.service;

import com.framasaas.be.domain.FranchiseUserStatusHistory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.FranchiseUserStatusHistory}.
 */
public interface FranchiseUserStatusHistoryService {
    /**
     * Save a franchiseUserStatusHistory.
     *
     * @param franchiseUserStatusHistory the entity to save.
     * @return the persisted entity.
     */
    FranchiseUserStatusHistory save(FranchiseUserStatusHistory franchiseUserStatusHistory);

    /**
     * Updates a franchiseUserStatusHistory.
     *
     * @param franchiseUserStatusHistory the entity to update.
     * @return the persisted entity.
     */
    FranchiseUserStatusHistory update(FranchiseUserStatusHistory franchiseUserStatusHistory);

    /**
     * Partially updates a franchiseUserStatusHistory.
     *
     * @param franchiseUserStatusHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseUserStatusHistory> partialUpdate(FranchiseUserStatusHistory franchiseUserStatusHistory);

    /**
     * Get all the franchiseUserStatusHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FranchiseUserStatusHistory> findAll(Pageable pageable);

    /**
     * Get the "id" franchiseUserStatusHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseUserStatusHistory> findOne(Long id);

    /**
     * Delete the "id" franchiseUserStatusHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
