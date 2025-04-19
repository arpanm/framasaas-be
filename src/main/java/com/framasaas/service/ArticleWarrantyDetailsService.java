package com.framasaas.service;

import com.framasaas.service.dto.ArticleWarrantyDetailsDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.ArticleWarrantyDetails}.
 */
public interface ArticleWarrantyDetailsService {
    /**
     * Save a articleWarrantyDetails.
     *
     * @param articleWarrantyDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleWarrantyDetailsDTO save(ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO);

    /**
     * Updates a articleWarrantyDetails.
     *
     * @param articleWarrantyDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    ArticleWarrantyDetailsDTO update(ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO);

    /**
     * Partially updates a articleWarrantyDetails.
     *
     * @param articleWarrantyDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArticleWarrantyDetailsDTO> partialUpdate(ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO);

    /**
     * Get the "id" articleWarrantyDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleWarrantyDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" articleWarrantyDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
