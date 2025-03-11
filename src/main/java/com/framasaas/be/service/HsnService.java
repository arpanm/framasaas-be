package com.framasaas.be.service;

import com.framasaas.be.domain.Hsn;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.Hsn}.
 */
public interface HsnService {
    /**
     * Save a hsn.
     *
     * @param hsn the entity to save.
     * @return the persisted entity.
     */
    Hsn save(Hsn hsn);

    /**
     * Updates a hsn.
     *
     * @param hsn the entity to update.
     * @return the persisted entity.
     */
    Hsn update(Hsn hsn);

    /**
     * Partially updates a hsn.
     *
     * @param hsn the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Hsn> partialUpdate(Hsn hsn);

    /**
     * Get all the hsns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Hsn> findAll(Pageable pageable);

    /**
     * Get the "id" hsn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Hsn> findOne(Long id);

    /**
     * Delete the "id" hsn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
