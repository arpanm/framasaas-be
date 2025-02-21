package com.framasaas.be.repository;

import com.framasaas.be.domain.FranchiseUserStatusHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseUserStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseUserStatusHistoryRepository extends JpaRepository<FranchiseUserStatusHistory, Long> {}
