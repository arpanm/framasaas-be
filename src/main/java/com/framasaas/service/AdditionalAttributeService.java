package com.framasaas.service;

import com.framasaas.service.dto.AdditionalAttributeDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.AdditionalAttribute}.
 */
public interface AdditionalAttributeService {
    /**
     * Save a additionalAttribute.
     *
     * @param additionalAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    AdditionalAttributeDTO save(AdditionalAttributeDTO additionalAttributeDTO);

    /**
     * Updates a additionalAttribute.
     *
     * @param additionalAttributeDTO the entity to update.
     * @return the persisted entity.
     */
    AdditionalAttributeDTO update(AdditionalAttributeDTO additionalAttributeDTO);

    /**
     * Partially updates a additionalAttribute.
     *
     * @param additionalAttributeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdditionalAttributeDTO> partialUpdate(AdditionalAttributeDTO additionalAttributeDTO);

    /**
     * Get the "id" additionalAttribute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdditionalAttributeDTO> findOne(Long id);

    /**
     * Delete the "id" additionalAttribute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
