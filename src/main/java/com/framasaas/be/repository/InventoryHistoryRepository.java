package com.framasaas.be.repository;

import com.framasaas.be.domain.InventoryHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InventoryHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryHistoryRepository extends JpaRepository<InventoryHistory, Long> {}
