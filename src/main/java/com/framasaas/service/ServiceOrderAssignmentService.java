package com.framasaas.service;

import com.framasaas.domain.ServiceOrderAssignment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.domain.ServiceOrderAssignment}.
 */
public interface ServiceOrderAssignmentService {
    /**
     * Save a serviceOrderAssignment.
     *
     * @param serviceOrderAssignment the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderAssignment save(ServiceOrderAssignment serviceOrderAssignment);

    /**
     * Updates a serviceOrderAssignment.
     *
     * @param serviceOrderAssignment the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderAssignment update(ServiceOrderAssignment serviceOrderAssignment);

    /**
     * Partially updates a serviceOrderAssignment.
     *
     * @param serviceOrderAssignment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderAssignment> partialUpdate(ServiceOrderAssignment serviceOrderAssignment);

    /**
     * Get all the serviceOrderAssignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOrderAssignment> findAll(Pageable pageable);

    /**
     * Get the "id" serviceOrderAssignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderAssignment> findOne(Long id);

    /**
     * Delete the "id" serviceOrderAssignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
