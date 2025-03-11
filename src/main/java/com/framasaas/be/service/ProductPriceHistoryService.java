package com.framasaas.be.service;

import com.framasaas.be.domain.ProductPriceHistory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.ProductPriceHistory}.
 */
public interface ProductPriceHistoryService {
    /**
     * Save a productPriceHistory.
     *
     * @param productPriceHistory the entity to save.
     * @return the persisted entity.
     */
    ProductPriceHistory save(ProductPriceHistory productPriceHistory);

    /**
     * Updates a productPriceHistory.
     *
     * @param productPriceHistory the entity to update.
     * @return the persisted entity.
     */
    ProductPriceHistory update(ProductPriceHistory productPriceHistory);

    /**
     * Partially updates a productPriceHistory.
     *
     * @param productPriceHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductPriceHistory> partialUpdate(ProductPriceHistory productPriceHistory);

    /**
     * Get all the productPriceHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductPriceHistory> findAll(Pageable pageable);

    /**
     * Get the "id" productPriceHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductPriceHistory> findOne(Long id);

    /**
     * Delete the "id" productPriceHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
