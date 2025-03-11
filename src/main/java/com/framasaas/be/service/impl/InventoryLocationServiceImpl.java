package com.framasaas.be.service.impl;

import com.framasaas.be.domain.InventoryLocation;
import com.framasaas.be.repository.InventoryLocationRepository;
import com.framasaas.be.service.InventoryLocationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.InventoryLocation}.
 */
@Service
@Transactional
public class InventoryLocationServiceImpl implements InventoryLocationService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryLocationServiceImpl.class);

    private final InventoryLocationRepository inventoryLocationRepository;

    public InventoryLocationServiceImpl(InventoryLocationRepository inventoryLocationRepository) {
        this.inventoryLocationRepository = inventoryLocationRepository;
    }

    @Override
    public InventoryLocation save(InventoryLocation inventoryLocation) {
        LOG.debug("Request to save InventoryLocation : {}", inventoryLocation);
        return inventoryLocationRepository.save(inventoryLocation);
    }

    @Override
    public InventoryLocation update(InventoryLocation inventoryLocation) {
        LOG.debug("Request to update InventoryLocation : {}", inventoryLocation);
        return inventoryLocationRepository.save(inventoryLocation);
    }

    @Override
    public Optional<InventoryLocation> partialUpdate(InventoryLocation inventoryLocation) {
        LOG.debug("Request to partially update InventoryLocation : {}", inventoryLocation);

        return inventoryLocationRepository
            .findById(inventoryLocation.getId())
            .map(existingInventoryLocation -> {
                if (inventoryLocation.getName() != null) {
                    existingInventoryLocation.setName(inventoryLocation.getName());
                }
                if (inventoryLocation.getLocationType() != null) {
                    existingInventoryLocation.setLocationType(inventoryLocation.getLocationType());
                }
                if (inventoryLocation.getIsActive() != null) {
                    existingInventoryLocation.setIsActive(inventoryLocation.getIsActive());
                }
                if (inventoryLocation.getCreateddBy() != null) {
                    existingInventoryLocation.setCreateddBy(inventoryLocation.getCreateddBy());
                }
                if (inventoryLocation.getCreatedTime() != null) {
                    existingInventoryLocation.setCreatedTime(inventoryLocation.getCreatedTime());
                }
                if (inventoryLocation.getUpdatedBy() != null) {
                    existingInventoryLocation.setUpdatedBy(inventoryLocation.getUpdatedBy());
                }
                if (inventoryLocation.getUpdatedTime() != null) {
                    existingInventoryLocation.setUpdatedTime(inventoryLocation.getUpdatedTime());
                }

                return existingInventoryLocation;
            })
            .map(inventoryLocationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InventoryLocation> findAll(Pageable pageable) {
        LOG.debug("Request to get all InventoryLocations");
        return inventoryLocationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryLocation> findOne(Long id) {
        LOG.debug("Request to get InventoryLocation : {}", id);
        return inventoryLocationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete InventoryLocation : {}", id);
        inventoryLocationRepository.deleteById(id);
    }
}
