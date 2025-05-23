package com.framasaas.repository;

import com.framasaas.domain.FranchisePerformanceHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchisePerformanceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchisePerformanceHistoryRepository
    extends JpaRepository<FranchisePerformanceHistory, Long>, JpaSpecificationExecutor<FranchisePerformanceHistory> {}
