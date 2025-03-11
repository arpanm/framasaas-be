package com.framasaas.be.service;

import com.framasaas.be.domain.FranchiseAllocationRule;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.FranchiseAllocationRule}.
 */
public interface FranchiseAllocationRuleService {
    /**
     * Save a franchiseAllocationRule.
     *
     * @param franchiseAllocationRule the entity to save.
     * @return the persisted entity.
     */
    FranchiseAllocationRule save(FranchiseAllocationRule franchiseAllocationRule);

    /**
     * Updates a franchiseAllocationRule.
     *
     * @param franchiseAllocationRule the entity to update.
     * @return the persisted entity.
     */
    FranchiseAllocationRule update(FranchiseAllocationRule franchiseAllocationRule);

    /**
     * Partially updates a franchiseAllocationRule.
     *
     * @param franchiseAllocationRule the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseAllocationRule> partialUpdate(FranchiseAllocationRule franchiseAllocationRule);

    /**
     * Get all the franchiseAllocationRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FranchiseAllocationRule> findAll(Pageable pageable);

    /**
     * Get the "id" franchiseAllocationRule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseAllocationRule> findOne(Long id);

    /**
     * Delete the "id" franchiseAllocationRule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
