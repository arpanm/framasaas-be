package com.framasaas.be.service;

import com.framasaas.be.domain.ServiceOrderMaster;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.framasaas.be.domain.ServiceOrderMaster}.
 */
public interface ServiceOrderMasterService {
    /**
     * Save a serviceOrderMaster.
     *
     * @param serviceOrderMaster the entity to save.
     * @return the persisted entity.
     */
    ServiceOrderMaster save(ServiceOrderMaster serviceOrderMaster);

    /**
     * Updates a serviceOrderMaster.
     *
     * @param serviceOrderMaster the entity to update.
     * @return the persisted entity.
     */
    ServiceOrderMaster update(ServiceOrderMaster serviceOrderMaster);

    /**
     * Partially updates a serviceOrderMaster.
     *
     * @param serviceOrderMaster the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiceOrderMaster> partialUpdate(ServiceOrderMaster serviceOrderMaster);

    /**
     * Get all the serviceOrderMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOrderMaster> findAll(Pageable pageable);

    /**
     * Get the "id" serviceOrderMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOrderMaster> findOne(Long id);

    /**
     * Delete the "id" serviceOrderMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
