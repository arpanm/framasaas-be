package com.framasaas.be.repository;

import com.framasaas.be.domain.FranchiseCategoryMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseCategoryMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseCategoryMappingRepository extends JpaRepository<FranchiseCategoryMapping, Long> {}
