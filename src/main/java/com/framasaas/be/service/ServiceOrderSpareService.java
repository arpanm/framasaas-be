package com.framasaas.be.service;

import com.framasaas.be.domain.ServiceOrderSpare;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.ServiceOrderSpare}.
 */
public interface ServiceOrderSpareService {
    /**
     * Save a serviceOrderSpare.
     *
     * @param serviceOrderSpare the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderSpare save(ServiceOrderSpare serviceOrderSpare);

    /**
     * Updates a serviceOrderSpare.
     *
     * @param serviceOrderSpare the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderSpare update(ServiceOrderSpare serviceOrderSpare);

    /**
     * Partially updates a serviceOrderSpare.
     *
     * @param serviceOrderSpare the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderSpare> partialUpdate(ServiceOrderSpare serviceOrderSpare);

    /**
     * Get all the serviceOrderSpares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOrderSpare> findAll(Pageable pageable);

    /**
     * Get the "id" serviceOrderSpare.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderSpare> findOne(Long id);

    /**
     * Delete the "id" serviceOrderSpare.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
