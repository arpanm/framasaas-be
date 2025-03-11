package com.framasaas.be.repository;

import com.framasaas.be.domain.FranchiseAllocationRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseAllocationRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseAllocationRuleRepository extends JpaRepository<FranchiseAllocationRule, Long> {}
