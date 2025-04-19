package com.framasaas.service;

import com.framasaas.service.dto.LanguageMappingDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.LanguageMapping}.
 */
public interface LanguageMappingService {
    /**
     * Save a languageMapping.
     *
     * @param languageMappingDTO the entity to save.
     * @return the persisted entity.
     */
    LanguageMappingDTO save(LanguageMappingDTO languageMappingDTO);

    /**
     * Updates a languageMapping.
     *
     * @param languageMappingDTO the entity to update.
     * @return the persisted entity.
     */
    LanguageMappingDTO update(LanguageMappingDTO languageMappingDTO);

    /**
     * Partially updates a languageMapping.
     *
     * @param languageMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LanguageMappingDTO> partialUpdate(LanguageMappingDTO languageMappingDTO);

    /**
     * Get the "id" languageMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LanguageMappingDTO> findOne(Long id);

    /**
     * Delete the "id" languageMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
