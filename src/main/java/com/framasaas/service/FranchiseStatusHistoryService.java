package com.framasaas.service;

import com.framasaas.service.dto.FranchiseStatusHistoryDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.FranchiseStatusHistory}.
 */
public interface FranchiseStatusHistoryService {
    /**
     * Save a franchiseStatusHistory.
     *
     * @param franchiseStatusHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    FranchiseStatusHistoryDTO save(FranchiseStatusHistoryDTO franchiseStatusHistoryDTO);

    /**
     * Updates a franchiseStatusHistory.
     *
     * @param franchiseStatusHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    FranchiseStatusHistoryDTO update(FranchiseStatusHistoryDTO franchiseStatusHistoryDTO);

    /**
     * Partially updates a franchiseStatusHistory.
     *
     * @param franchiseStatusHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseStatusHistoryDTO> partialUpdate(FranchiseStatusHistoryDTO franchiseStatusHistoryDTO);

    /**
     * Get the "id" franchiseStatusHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseStatusHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" franchiseStatusHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
