package com.framasaas.be.service;

import com.framasaas.be.domain.FranchiseBrandMapping;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.FranchiseBrandMapping}.
 */
public interface FranchiseBrandMappingService {
    /**
     * Save a franchiseBrandMapping.
     *
     * @param franchiseBrandMapping the entity to save.
     * @return the persisted entity.
     */
    FranchiseBrandMapping save(FranchiseBrandMapping franchiseBrandMapping);

    /**
     * Updates a franchiseBrandMapping.
     *
     * @param franchiseBrandMapping the entity to update.
     * @return the persisted entity.
     */
    FranchiseBrandMapping update(FranchiseBrandMapping franchiseBrandMapping);

    /**
     * Partially updates a franchiseBrandMapping.
     *
     * @param franchiseBrandMapping the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FranchiseBrandMapping> partialUpdate(FranchiseBrandMapping franchiseBrandMapping);

    /**
     * Get all the franchiseBrandMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FranchiseBrandMapping> findAll(Pageable pageable);

    /**
     * Get the "id" franchiseBrandMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FranchiseBrandMapping> findOne(Long id);

    /**
     * Delete the "id" franchiseBrandMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
