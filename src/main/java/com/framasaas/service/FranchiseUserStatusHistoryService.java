package com.framasaas.service;

import com.framasaas.service.dto.FranchiseUserStatusHistoryDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.FranchiseUserStatusHistory}.
 */
public interface FranchiseUserStatusHistoryService {
    /**
     * Save a franchiseUserStatusHistory.
     *
     * @param franchiseUserStatusHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    FranchiseUserStatusHistoryDTO save(FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO);

    /**
     * Updates a franchiseUserStatusHistory.
     *
     * @param franchiseUserStatusHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    FranchiseUserStatusHistoryDTO update(FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO);

    /**
     * Partially updates a franchiseUserStatusHistory.
     *
     * @param franchiseUserStatusHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseUserStatusHistoryDTO> partialUpdate(FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO);

    /**
     * Get the "id" franchiseUserStatusHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseUserStatusHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" franchiseUserStatusHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
