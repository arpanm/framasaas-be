package com.framasaas.be.service;

import com.framasaas.be.domain.Pincode;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.Pincode}.
 */
public interface PincodeService {
    /**
     * Save a pincode.
     *
     * @param pincode the entity to save.
     * @return the persisted entity.
     */
    Pincode save(Pincode pincode);

    /**
     * Updates a pincode.
     *
     * @param pincode the entity to update.
     * @return the persisted entity.
     */
    Pincode update(Pincode pincode);

    /**
     * Partially updates a pincode.
     *
     * @param pincode the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Pincode> partialUpdate(Pincode pincode);

    /**
     * Get all the pincodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pincode> findAll(Pageable pageable);

    /**
     * Get the "id" pincode.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pincode> findOne(Long id);

    /**
     * Delete the "id" pincode.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
