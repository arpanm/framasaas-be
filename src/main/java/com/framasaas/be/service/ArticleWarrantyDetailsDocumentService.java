package com.framasaas.be.service;

import com.framasaas.be.domain.ArticleWarrantyDetailsDocument;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.ArticleWarrantyDetailsDocument}.
 */
public interface ArticleWarrantyDetailsDocumentService {
    /**
     * Save a articleWarrantyDetailsDocument.
     *
     * @param articleWarrantyDetailsDocument the entity to save.
     * @return the persisted entity.
     */
    ArticleWarrantyDetailsDocument save(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument);

    /**
     * Updates a articleWarrantyDetailsDocument.
     *
     * @param articleWarrantyDetailsDocument the entity to update.
     * @return the persisted entity.
     */
    ArticleWarrantyDetailsDocument update(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument);

    /**
     * Partially updates a articleWarrantyDetailsDocument.
     *
     * @param articleWarrantyDetailsDocument the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArticleWarrantyDetailsDocument> partialUpdate(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument);

    /**
     * Get all the articleWarrantyDetailsDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleWarrantyDetailsDocument> findAll(Pageable pageable);

    /**
     * Get the "id" articleWarrantyDetailsDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleWarrantyDetailsDocument> findOne(Long id);

    /**
     * Delete the "id" articleWarrantyDetailsDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
