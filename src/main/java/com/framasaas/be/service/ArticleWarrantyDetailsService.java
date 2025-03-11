package com.framasaas.be.service;

import com.framasaas.be.domain.ArticleWarrantyDetails;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.ArticleWarrantyDetails}.
 */
public interface ArticleWarrantyDetailsService {
    /**
     * Save a articleWarrantyDetails.
     *
     * @param articleWarrantyDetails the entity to save.
     * @return the persisted entity.
     */
    ArticleWarrantyDetails save(ArticleWarrantyDetails articleWarrantyDetails);

    /**
     * Updates a articleWarrantyDetails.
     *
     * @param articleWarrantyDetails the entity to update.
     * @return the persisted entity.
     */
    ArticleWarrantyDetails update(ArticleWarrantyDetails articleWarrantyDetails);

    /**
     * Partially updates a articleWarrantyDetails.
     *
     * @param articleWarrantyDetails the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArticleWarrantyDetails> partialUpdate(ArticleWarrantyDetails articleWarrantyDetails);

    /**
     * Get all the articleWarrantyDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleWarrantyDetails> findAll(Pageable pageable);

    /**
     * Get the "id" articleWarrantyDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleWarrantyDetails> findOne(Long id);

    /**
     * Delete the "id" articleWarrantyDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
