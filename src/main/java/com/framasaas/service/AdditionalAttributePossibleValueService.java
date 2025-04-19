package com.framasaas.service;

import com.framasaas.service.dto.AdditionalAttributePossibleValueDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.AdditionalAttributePossibleValue}.
 */
public interface AdditionalAttributePossibleValueService {
    /**
     * Save a additionalAttributePossibleValue.
     *
     * @param additionalAttributePossibleValueDTO the entity to save.
     * @return the persisted entity.
     */
    AdditionalAttributePossibleValueDTO save(AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO);

    /**
     * Updates a additionalAttributePossibleValue.
     *
     * @param additionalAttributePossibleValueDTO the entity to update.
     * @return the persisted entity.
     */
    AdditionalAttributePossibleValueDTO update(AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO);

    /**
     * Partially updates a additionalAttributePossibleValue.
     *
     * @param additionalAttributePossibleValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdditionalAttributePossibleValueDTO> partialUpdate(AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO);

    /**
     * Get the "id" additionalAttributePossibleValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdditionalAttributePossibleValueDTO> findOne(Long id);

    /**
     * Delete the "id" additionalAttributePossibleValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
