package com.framasaas.repository;

import com.framasaas.domain.ServiceOrderMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceOrderMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrderMasterRepository
    extends JpaRepository<ServiceOrderMaster, Long>, JpaSpecificationExecutor<ServiceOrderMaster> {}
