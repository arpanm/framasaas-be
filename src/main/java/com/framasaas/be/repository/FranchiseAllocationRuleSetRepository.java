package com.framasaas.be.repository;

import com.framasaas.be.domain.FranchiseAllocationRuleSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseAllocationRuleSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseAllocationRuleSetRepository extends JpaRepository<FranchiseAllocationRuleSet, Long> {}
