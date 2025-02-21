package com.framasaas.be.repository;

import com.framasaas.be.domain.FranchiseStatusHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseStatusHistoryRepository extends JpaRepository<FranchiseStatusHistory, Long> {}
