package com.framasaas.service;

import com.framasaas.service.dto.SupportingDocumentDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.SupportingDocument}.
 */
public interface SupportingDocumentService {
    /**
     * Save a supportingDocument.
     *
     * @param supportingDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    SupportingDocumentDTO save(SupportingDocumentDTO supportingDocumentDTO);

    /**
     * Updates a supportingDocument.
     *
     * @param supportingDocumentDTO the entity to update.
     * @return the persisted entity.
     */
    SupportingDocumentDTO update(SupportingDocumentDTO supportingDocumentDTO);

    /**
     * Partially updates a supportingDocument.
     *
     * @param supportingDocumentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SupportingDocumentDTO> partialUpdate(SupportingDocumentDTO supportingDocumentDTO);

    /**
     * Get the "id" supportingDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupportingDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" supportingDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
