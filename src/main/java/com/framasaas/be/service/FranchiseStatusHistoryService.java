package com.framasaas.be.service;

import com.framasaas.be.domain.FranchiseStatusHistory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.FranchiseStatusHistory}.
 */
public interface FranchiseStatusHistoryService {
    /**
     * Save a franchiseStatusHistory.
     *
     * @param franchiseStatusHistory the entity to save.
     * @return the persisted entity.
     */
    FranchiseStatusHistory save(FranchiseStatusHistory franchiseStatusHistory);

    /**
     * Updates a franchiseStatusHistory.
     *
     * @param franchiseStatusHistory the entity to update.
     * @return the persisted entity.
     */
    FranchiseStatusHistory update(FranchiseStatusHistory franchiseStatusHistory);

    /**
     * Partially updates a franchiseStatusHistory.
     *
     * @param franchiseStatusHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseStatusHistory> partialUpdate(FranchiseStatusHistory franchiseStatusHistory);

    /**
     * Get all the franchiseStatusHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FranchiseStatusHistory> findAll(Pageable pageable);

    /**
     * Get the "id" franchiseStatusHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseStatusHistory> findOne(Long id);

    /**
     * Delete the "id" franchiseStatusHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
