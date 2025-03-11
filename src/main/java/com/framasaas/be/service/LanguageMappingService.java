package com.framasaas.be.service;

import com.framasaas.be.domain.LanguageMapping;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.LanguageMapping}.
 */
public interface LanguageMappingService {
    /**
     * Save a languageMapping.
     *
     * @param languageMapping the entity to save.
     * @return the persisted entity.
     */
    LanguageMapping save(LanguageMapping languageMapping);

    /**
     * Updates a languageMapping.
     *
     * @param languageMapping the entity to update.
     * @return the persisted entity.
     */
    LanguageMapping update(LanguageMapping languageMapping);

    /**
     * Partially updates a languageMapping.
     *
     * @param languageMapping the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LanguageMapping> partialUpdate(LanguageMapping languageMapping);

    /**
     * Get all the languageMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LanguageMapping> findAll(Pageable pageable);

    /**
     * Get the "id" languageMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LanguageMapping> findOne(Long id);

    /**
     * Delete the "id" languageMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
