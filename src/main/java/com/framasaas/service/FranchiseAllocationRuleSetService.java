package com.framasaas.service;

import com.framasaas.service.dto.FranchiseAllocationRuleSetDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.FranchiseAllocationRuleSet}.
 */
public interface FranchiseAllocationRuleSetService {
    /**
     * Save a franchiseAllocationRuleSet.
     *
     * @param franchiseAllocationRuleSetDTO the entity to save.
     * @return the persisted entity.
     */
    FranchiseAllocationRuleSetDTO save(FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO);

    /**
     * Updates a franchiseAllocationRuleSet.
     *
     * @param franchiseAllocationRuleSetDTO the entity to update.
     * @return the persisted entity.
     */
    FranchiseAllocationRuleSetDTO update(FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO);

    /**
     * Partially updates a franchiseAllocationRuleSet.
     *
     * @param franchiseAllocationRuleSetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseAllocationRuleSetDTO> partialUpdate(FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO);

    /**
     * Get the "id" franchiseAllocationRuleSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseAllocationRuleSetDTO> findOne(Long id);

    /**
     * Delete the "id" franchiseAllocationRuleSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
