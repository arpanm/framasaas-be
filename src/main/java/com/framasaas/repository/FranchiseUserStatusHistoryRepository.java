package com.framasaas.repository;

import com.framasaas.domain.FranchiseUserStatusHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseUserStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseUserStatusHistoryRepository
    extends JpaRepository<FranchiseUserStatusHistory, Long>, JpaSpecificationExecutor<FranchiseUserStatusHistory> {}
