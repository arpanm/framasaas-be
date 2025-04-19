package com.framasaas.service;

import com.framasaas.service.dto.HsnDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.Hsn}.
 */
public interface HsnService {
    /**
     * Save a hsn.
     *
     * @param hsnDTO the entity to save.
     * @return the persisted entity.
     */
    HsnDTO save(HsnDTO hsnDTO);

    /**
     * Updates a hsn.
     *
     * @param hsnDTO the entity to update.
     * @return the persisted entity.
     */
    HsnDTO update(HsnDTO hsnDTO);

    /**
     * Partially updates a hsn.
     *
     * @param hsnDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HsnDTO> partialUpdate(HsnDTO hsnDTO);

    /**
     * Get the "id" hsn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HsnDTO> findOne(Long id);

    /**
     * Delete the "id" hsn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
