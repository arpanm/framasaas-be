package com.framasaas.repository;

import com.framasaas.domain.ProductPriceHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductPriceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductPriceHistoryRepository
    extends JpaRepository<ProductPriceHistory, Long>, JpaSpecificationExecutor<ProductPriceHistory> {}
