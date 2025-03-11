package com.framasaas.be.service.impl;

import com.framasaas.be.domain.Inventory;
import com.framasaas.be.repository.InventoryRepository;
import com.framasaas.be.service.InventoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.Inventory}.
 */
@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory save(Inventory inventory) {
        LOG.debug("Request to save Inventory : {}", inventory);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory update(Inventory inventory) {
        LOG.debug("Request to update Inventory : {}", inventory);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Optional<Inventory> partialUpdate(Inventory inventory) {
        LOG.debug("Request to partially update Inventory : {}", inventory);

        return inventoryRepository
            .findById(inventory.getId())
            .map(existingInventory -> {
                if (inventory.getInventoryValue() != null) {
                    existingInventory.setInventoryValue(inventory.getInventoryValue());
                }
                if (inventory.getIsSellable() != null) {
                    existingInventory.setIsSellable(inventory.getIsSellable());
                }
                if (inventory.getCreateddBy() != null) {
                    existingInventory.setCreateddBy(inventory.getCreateddBy());
                }
                if (inventory.getCreatedTime() != null) {
                    existingInventory.setCreatedTime(inventory.getCreatedTime());
                }
                if (inventory.getUpdatedBy() != null) {
                    existingInventory.setUpdatedBy(inventory.getUpdatedBy());
                }
                if (inventory.getUpdatedTime() != null) {
                    existingInventory.setUpdatedTime(inventory.getUpdatedTime());
                }

                return existingInventory;
            })
            .map(inventoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Inventory> findAll(Pageable pageable) {
        LOG.debug("Request to get all Inventories");
        return inventoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Inventory> findOne(Long id) {
        LOG.debug("Request to get Inventory : {}", id);
        return inventoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Inventory : {}", id);
        inventoryRepository.deleteById(id);
    }
}
