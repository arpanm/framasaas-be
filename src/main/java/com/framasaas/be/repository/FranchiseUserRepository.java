package com.framasaas.be.repository;

import com.framasaas.be.domain.FranchiseUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FranchiseUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FranchiseUserRepository extends JpaRepository<FranchiseUser, Long> {}
