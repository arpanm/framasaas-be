package com.framasaas.repository;

import com.framasaas.domain.ServiceOrderFieldAgentAssignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceOrderFieldAgentAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrderFieldAgentAssignmentRepository
    extends JpaRepository<ServiceOrderFieldAgentAssignment, Long>, JpaSpecificationExecutor<ServiceOrderFieldAgentAssignment> {}
