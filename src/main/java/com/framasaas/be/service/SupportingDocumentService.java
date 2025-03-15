package com.framasaas.be.service;

import com.framasaas.be.domain.SupportingDocument;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.SupportingDocument}.
 */
public interface SupportingDocumentService {
    /**
     * Save a supportingDocument.
     *
     * @param supportingDocument the entity to save.
     * @return the persisted entity.
     */
    SupportingDocument save(SupportingDocument supportingDocument);

    /**
     * Updates a supportingDocument.
     *
     * @param supportingDocument the entity to update.
     * @return the persisted entity.
     */
    SupportingDocument update(SupportingDocument supportingDocument);

    /**
     * Partially updates a supportingDocument.
     *
     * @param supportingDocument the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SupportingDocument> partialUpdate(SupportingDocument supportingDocument);

    /**
     * Get all the supportingDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupportingDocument> findAll(Pageable pageable);

    /**
     * Get the "id" supportingDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupportingDocument> findOne(Long id);

    /**
     * Delete the "id" supportingDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
