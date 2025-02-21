package com.framasaas.be.service;

import com.framasaas.be.domain.FranchisePerformanceHistory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.FranchisePerformanceHistory}.
 */
public interface FranchisePerformanceHistoryService {
    /**
     * Save a franchisePerformanceHistory.
     *
     * @param franchisePerformanceHistory the entity to save.
     * @return the persisted entity.
     */
    FranchisePerformanceHistory save(FranchisePerformanceHistory franchisePerformanceHistory);

    /**
     * Updates a franchisePerformanceHistory.
     *
     * @param franchisePerformanceHistory the entity to update.
     * @return the persisted entity.
     */
    FranchisePerformanceHistory update(FranchisePerformanceHistory franchisePerformanceHistory);

    /**
     * Partially updates a franchisePerformanceHistory.
     *
     * @param franchisePerformanceHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchisePerformanceHistory> partialUpdate(FranchisePerformanceHistory franchisePerformanceHistory);

    /**
     * Get all the franchisePerformanceHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FranchisePerformanceHistory> findAll(Pageable pageable);

    /**
     * Get the "id" franchisePerformanceHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchisePerformanceHistory> findOne(Long id);

    /**
     * Delete the "id" franchisePerformanceHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
