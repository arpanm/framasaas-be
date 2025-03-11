package com.framasaas.be.repository;

import com.framasaas.be.domain.WarrantyMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WarrantyMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WarrantyMasterRepository extends JpaRepository<WarrantyMaster, Long> {}
