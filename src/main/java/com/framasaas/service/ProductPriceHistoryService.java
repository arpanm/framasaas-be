package com.framasaas.service;

import com.framasaas.service.dto.ProductPriceHistoryDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.ProductPriceHistory}.
 */
public interface ProductPriceHistoryService {
    /**
     * Save a productPriceHistory.
     *
     * @param productPriceHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    ProductPriceHistoryDTO save(ProductPriceHistoryDTO productPriceHistoryDTO);

    /**
     * Updates a productPriceHistory.
     *
     * @param productPriceHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    ProductPriceHistoryDTO update(ProductPriceHistoryDTO productPriceHistoryDTO);

    /**
     * Partially updates a productPriceHistory.
     *
     * @param productPriceHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductPriceHistoryDTO> partialUpdate(ProductPriceHistoryDTO productPriceHistoryDTO);

    /**
     * Get the "id" productPriceHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductPriceHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" productPriceHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
