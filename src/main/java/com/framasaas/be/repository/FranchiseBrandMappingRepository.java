package com.framasaas.be.repository;

import com.framasaas.be.domain.FranchiseBrandMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseBrandMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseBrandMappingRepository extends JpaRepository<FranchiseBrandMapping, Long> {}
