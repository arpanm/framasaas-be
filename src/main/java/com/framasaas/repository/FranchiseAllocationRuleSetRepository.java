package com.framasaas.repository;

import com.framasaas.domain.FranchiseAllocationRuleSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseAllocationRuleSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseAllocationRuleSetRepository
    extends JpaRepository<FranchiseAllocationRuleSet, Long>, JpaSpecificationExecutor<FranchiseAllocationRuleSet> {}
