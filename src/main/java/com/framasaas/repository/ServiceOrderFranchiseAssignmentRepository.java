package com.framasaas.repository;

import com.framasaas.domain.ServiceOrderFranchiseAssignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceOrderFranchiseAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrderFranchiseAssignmentRepository
    extends JpaRepository<ServiceOrderFranchiseAssignment, Long>, JpaSpecificationExecutor<ServiceOrderFranchiseAssignment> {}
