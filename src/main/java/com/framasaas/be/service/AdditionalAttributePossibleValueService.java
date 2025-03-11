package com.framasaas.be.service;

import com.framasaas.be.domain.AdditionalAttributePossibleValue;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.AdditionalAttributePossibleValue}.
 */
public interface AdditionalAttributePossibleValueService {
    /**
     * Save a additionalAttributePossibleValue.
     *
     * @param additionalAttributePossibleValue the entity to save.
     * @return the persisted entity.
     */
    AdditionalAttributePossibleValue save(AdditionalAttributePossibleValue additionalAttributePossibleValue);

    /**
     * Updates a additionalAttributePossibleValue.
     *
     * @param additionalAttributePossibleValue the entity to update.
     * @return the persisted entity.
     */
    AdditionalAttributePossibleValue update(AdditionalAttributePossibleValue additionalAttributePossibleValue);

    /**
     * Partially updates a additionalAttributePossibleValue.
     *
     * @param additionalAttributePossibleValue the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdditionalAttributePossibleValue> partialUpdate(AdditionalAttributePossibleValue additionalAttributePossibleValue);

    /**
     * Get all the additionalAttributePossibleValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdditionalAttributePossibleValue> findAll(Pageable pageable);

    /**
     * Get the "id" additionalAttributePossibleValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdditionalAttributePossibleValue> findOne(Long id);

    /**
     * Delete the "id" additionalAttributePossibleValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
