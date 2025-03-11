package com.framasaas.be.repository;

import com.framasaas.be.domain.ProductPriceHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductPriceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, Long> {}
