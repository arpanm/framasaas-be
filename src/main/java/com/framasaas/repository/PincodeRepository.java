package com.framasaas.repository;

import com.framasaas.domain.Pincode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pincode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PincodeRepository extends JpaRepository<Pincode, Long>, JpaSpecificationExecutor<Pincode> {}
