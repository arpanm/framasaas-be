package com.framasaas.repository;

import com.framasaas.domain.Franchise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Franchise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Long>, JpaSpecificationExecutor<Franchise> {}
