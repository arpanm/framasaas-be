package com.framasaas.repository;

import com.framasaas.domain.FranchiseUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseUserRepository extends JpaRepository<FranchiseUser, Long>, JpaSpecificationExecutor<FranchiseUser> {}
