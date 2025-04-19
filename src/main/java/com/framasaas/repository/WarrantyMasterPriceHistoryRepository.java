package com.framasaas.repository;

import com.framasaas.domain.WarrantyMasterPriceHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WarrantyMasterPriceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WarrantyMasterPriceHistoryRepository
    extends JpaRepository<WarrantyMasterPriceHistory, Long>, JpaSpecificationExecutor<WarrantyMasterPriceHistory> {}
