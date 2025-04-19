package com.framasaas.service;

import com.framasaas.service.dto.FranchisePerformanceHistoryDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.FranchisePerformanceHistory}.
 */
public interface FranchisePerformanceHistoryService {
    /**
     * Save a franchisePerformanceHistory.
     *
     * @param franchisePerformanceHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    FranchisePerformanceHistoryDTO save(FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO);

    /**
     * Updates a franchisePerformanceHistory.
     *
     * @param franchisePerformanceHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    FranchisePerformanceHistoryDTO update(FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO);

    /**
     * Partially updates a franchisePerformanceHistory.
     *
     * @param franchisePerformanceHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchisePerformanceHistoryDTO> partialUpdate(FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO);

    /**
     * Get the "id" franchisePerformanceHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchisePerformanceHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" franchisePerformanceHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
