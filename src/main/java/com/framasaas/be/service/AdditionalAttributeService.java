package com.framasaas.be.service;

import com.framasaas.be.domain.AdditionalAttribute;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.AdditionalAttribute}.
 */
public interface AdditionalAttributeService {
    /**
     * Save a additionalAttribute.
     *
     * @param additionalAttribute the entity to save.
     * @return the persisted entity.
     */
    AdditionalAttribute save(AdditionalAttribute additionalAttribute);

    /**
     * Updates a additionalAttribute.
     *
     * @param additionalAttribute the entity to update.
     * @return the persisted entity.
     */
    AdditionalAttribute update(AdditionalAttribute additionalAttribute);

    /**
     * Partially updates a additionalAttribute.
     *
     * @param additionalAttribute the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdditionalAttribute> partialUpdate(AdditionalAttribute additionalAttribute);

    /**
     * Get all the additionalAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdditionalAttribute> findAll(Pageable pageable);

    /**
     * Get the "id" additionalAttribute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdditionalAttribute> findOne(Long id);

    /**
     * Delete the "id" additionalAttribute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
