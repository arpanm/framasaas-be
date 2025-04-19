package com.framasaas.service;

import com.framasaas.service.dto.ServiceOrderFieldAgentAssignmentDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.framasaas.domain.ServiceOrderFieldAgentAssignment}.
 */
public interface ServiceOrderFieldAgentAssignmentService {
    /**
     * Save a serviceOrderFieldAgentAssignment.
     *
     * @param serviceOrderFieldAgentAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderFieldAgentAssignmentDTO save(ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO);

    /**
     * Updates a serviceOrderFieldAgentAssignment.
     *
     * @param serviceOrderFieldAgentAssignmentDTO the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderFieldAgentAssignmentDTO update(ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO);

    /**
     * Partially updates a serviceOrderFieldAgentAssignment.
     *
     * @param serviceOrderFieldAgentAssignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderFieldAgentAssignmentDTO> partialUpdate(ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO);

    /**
     * Get the "id" serviceOrderFieldAgentAssignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderFieldAgentAssignmentDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOrderFieldAgentAssignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
