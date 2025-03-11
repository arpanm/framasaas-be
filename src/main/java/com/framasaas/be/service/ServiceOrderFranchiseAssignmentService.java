package com.framasaas.be.service;

import com.framasaas.be.domain.ServiceOrderFranchiseAssignment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.ServiceOrderFranchiseAssignment}.
 */
public interface ServiceOrderFranchiseAssignmentService {
    /**
     * Save a serviceOrderFranchiseAssignment.
     *
     * @param serviceOrderFranchiseAssignment the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderFranchiseAssignment save(ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment);

    /**
     * Updates a serviceOrderFranchiseAssignment.
     *
     * @param serviceOrderFranchiseAssignment the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderFranchiseAssignment update(ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment);

    /**
     * Partially updates a serviceOrderFranchiseAssignment.
     *
     * @param serviceOrderFranchiseAssignment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderFranchiseAssignment> partialUpdate(ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment);

    /**
     * Get all the serviceOrderFranchiseAssignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOrderFranchiseAssignment> findAll(Pageable pageable);

    /**
     * Get the "id" serviceOrderFranchiseAssignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderFranchiseAssignment> findOne(Long id);

    /**
     * Delete the "id" serviceOrderFranchiseAssignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
