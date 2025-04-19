package com.framasaas.service;

import com.framasaas.domain.FranchiseDocument;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.domain.FranchiseDocument}.
 */
public interface FranchiseDocumentService {
    /**
     * Save a franchiseDocument.
     *
     * @param franchiseDocument the entity to save.
     * @return the persisted entity.
     */
    FranchiseDocument save(FranchiseDocument franchiseDocument);

    /**
     * Updates a franchiseDocument.
     *
     * @param franchiseDocument the entity to update.
     * @return the persisted entity.
     */
    FranchiseDocument update(FranchiseDocument franchiseDocument);

    /**
     * Partially updates a franchiseDocument.
     *
     * @param franchiseDocument the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseDocument> partialUpdate(FranchiseDocument franchiseDocument);

    /**
     * Get all the franchiseDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FranchiseDocument> findAll(Pageable pageable);

    /**
     * Get the "id" franchiseDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseDocument> findOne(Long id);

    /**
     * Delete the "id" franchiseDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
