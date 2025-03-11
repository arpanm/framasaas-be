package com.framasaas.be.service;

import com.framasaas.be.domain.FranchiseAllocationRuleSet;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.FranchiseAllocationRuleSet}.
 */
public interface FranchiseAllocationRuleSetService {
    /**
     * Save a franchiseAllocationRuleSet.
     *
     * @param franchiseAllocationRuleSet the entity to save.
     * @return the persisted entity.
     */
    FranchiseAllocationRuleSet save(FranchiseAllocationRuleSet franchiseAllocationRuleSet);

    /**
     * Updates a franchiseAllocationRuleSet.
     *
     * @param franchiseAllocationRuleSet the entity to update.
     * @return the persisted entity.
     */
    FranchiseAllocationRuleSet update(FranchiseAllocationRuleSet franchiseAllocationRuleSet);

    /**
     * Partially updates a franchiseAllocationRuleSet.
     *
     * @param franchiseAllocationRuleSet the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseAllocationRuleSet> partialUpdate(FranchiseAllocationRuleSet franchiseAllocationRuleSet);

    /**
     * Get all the franchiseAllocationRuleSets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FranchiseAllocationRuleSet> findAll(Pageable pageable);

    /**
     * Get the "id" franchiseAllocationRuleSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseAllocationRuleSet> findOne(Long id);

    /**
     * Delete the "id" franchiseAllocationRuleSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
