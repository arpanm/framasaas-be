package com.framasaas.service;

import com.framasaas.domain.FranchiseCategoryMapping;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.domain.FranchiseCategoryMapping}.
 */
public interface FranchiseCategoryMappingService {
    /**
     * Save a franchiseCategoryMapping.
     *
     * @param franchiseCategoryMapping the entity to save.
     * @return the persisted entity.
     */
    FranchiseCategoryMapping save(FranchiseCategoryMapping franchiseCategoryMapping);

    /**
     * Updates a franchiseCategoryMapping.
     *
     * @param franchiseCategoryMapping the entity to update.
     * @return the persisted entity.
     */
    FranchiseCategoryMapping update(FranchiseCategoryMapping franchiseCategoryMapping);

    /**
     * Partially updates a franchiseCategoryMapping.
     *
     * @param franchiseCategoryMapping the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseCategoryMapping> partialUpdate(FranchiseCategoryMapping franchiseCategoryMapping);

    /**
     * Get all the franchiseCategoryMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FranchiseCategoryMapping> findAll(Pageable pageable);

    /**
     * Get the "id" franchiseCategoryMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseCategoryMapping> findOne(Long id);

    /**
     * Delete the "id" franchiseCategoryMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
