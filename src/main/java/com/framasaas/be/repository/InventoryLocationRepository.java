package com.framasaas.be.repository;

import com.framasaas.be.domain.InventoryLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InventoryLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryLocationRepository extends JpaRepository<InventoryLocation, Long> {}
