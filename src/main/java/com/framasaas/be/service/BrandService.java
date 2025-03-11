package com.framasaas.be.service;

import com.framasaas.be.domain.Brand;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.Brand}.
 */
public interface BrandService {
    /**
     * Save a brand.
     *
     * @param brand the entity to save.
     * @return the persisted entity.
     */
    Brand save(Brand brand);

    /**
     * Updates a brand.
     *
     * @param brand the entity to update.
     * @return the persisted entity.
     */
    Brand update(Brand brand);

    /**
     * Partially updates a brand.
     *
     * @param brand the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Brand> partialUpdate(Brand brand);

    /**
     * Get all the brands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Brand> findAll(Pageable pageable);

    /**
     * Get the "id" brand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Brand> findOne(Long id);

    /**
     * Delete the "id" brand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
