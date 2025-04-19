package com.framasaas.repository;

import com.framasaas.domain.ServiceOrderSpare;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceOrderSpare entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrderSpareRepository extends JpaRepository<ServiceOrderSpare, Long>, JpaSpecificationExecutor<ServiceOrderSpare> {}
