package com.framasaas.be.service;

import com.framasaas.be.domain.ServiceOrderFieldAgentAssignment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.ServiceOrderFieldAgentAssignment}.
 */
public interface ServiceOrderFieldAgentAssignmentService {
    /**
     * Save a serviceOrderFieldAgentAssignment.
     *
     * @param serviceOrderFieldAgentAssignment the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderFieldAgentAssignment save(ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment);

    /**
     * Updates a serviceOrderFieldAgentAssignment.
     *
     * @param serviceOrderFieldAgentAssignment the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderFieldAgentAssignment update(ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment);

    /**
     * Partially updates a serviceOrderFieldAgentAssignment.
     *
     * @param serviceOrderFieldAgentAssignment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderFieldAgentAssignment> partialUpdate(ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment);

    /**
     * Get all the serviceOrderFieldAgentAssignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOrderFieldAgentAssignment> findAll(Pageable pageable);

    /**
     * Get the "id" serviceOrderFieldAgentAssignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderFieldAgentAssignment> findOne(Long id);

    /**
     * Delete the "id" serviceOrderFieldAgentAssignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
