package com.framasaas.repository;

import com.framasaas.domain.ServiceOrderAssignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceOrderAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrderAssignmentRepository extends JpaRepository<ServiceOrderAssignment, Long> {}
