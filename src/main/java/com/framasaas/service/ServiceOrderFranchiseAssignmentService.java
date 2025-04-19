package com.framasaas.service;

import com.framasaas.service.dto.ServiceOrderFranchiseAssignmentDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.ServiceOrderFranchiseAssignment}.
 */
public interface ServiceOrderFranchiseAssignmentService {
    /**
     * Save a serviceOrderFranchiseAssignment.
     *
     * @param serviceOrderFranchiseAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderFranchiseAssignmentDTO save(ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO);

    /**
     * Updates a serviceOrderFranchiseAssignment.
     *
     * @param serviceOrderFranchiseAssignmentDTO the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderFranchiseAssignmentDTO update(ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO);

    /**
     * Partially updates a serviceOrderFranchiseAssignment.
     *
     * @param serviceOrderFranchiseAssignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderFranchiseAssignmentDTO> partialUpdate(ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO);

    /**
     * Get the "id" serviceOrderFranchiseAssignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderFranchiseAssignmentDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOrderFranchiseAssignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
