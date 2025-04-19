package com.framasaas.service;

import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.FranchiseAllocationRule}.
 */
public interface FranchiseAllocationRuleService {
    /**
     * Save a franchiseAllocationRule.
     *
     * @param franchiseAllocationRuleDTO the entity to save.
     * @return the persisted entity.
     */
    FranchiseAllocationRuleDTO save(FranchiseAllocationRuleDTO franchiseAllocationRuleDTO);

    /**
     * Updates a franchiseAllocationRule.
     *
     * @param franchiseAllocationRuleDTO the entity to update.
     * @return the persisted entity.
     */
    FranchiseAllocationRuleDTO update(FranchiseAllocationRuleDTO franchiseAllocationRuleDTO);

    /**
     * Partially updates a franchiseAllocationRule.
     *
     * @param franchiseAllocationRuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseAllocationRuleDTO> partialUpdate(FranchiseAllocationRuleDTO franchiseAllocationRuleDTO);

    /**
     * Get the "id" franchiseAllocationRule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseAllocationRuleDTO> findOne(Long id);

    /**
     * Delete the "id" franchiseAllocationRule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
