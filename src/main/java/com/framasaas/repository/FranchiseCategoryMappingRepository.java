package com.framasaas.repository;

import com.framasaas.domain.FranchiseCategoryMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseCategoryMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseCategoryMappingRepository extends JpaRepository<FranchiseCategoryMapping, Long> {}
