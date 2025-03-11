package com.framasaas.be.service.impl;

import com.framasaas.be.domain.InventoryHistory;
import com.framasaas.be.repository.InventoryHistoryRepository;
import com.framasaas.be.service.InventoryHistoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.InventoryHistory}.
 */
@Service
@Transactional
public class InventoryHistoryServiceImpl implements InventoryHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryHistoryServiceImpl.class);

    private final InventoryHistoryRepository inventoryHistoryRepository;

    public InventoryHistoryServiceImpl(InventoryHistoryRepository inventoryHistoryRepository) {
        this.inventoryHistoryRepository = inventoryHistoryRepository;
    }

    @Override
    public InventoryHistory save(InventoryHistory inventoryHistory) {
        LOG.debug("Request to save InventoryHistory : {}", inventoryHistory);
        return inventoryHistoryRepository.save(inventoryHistory);
    }

    @Override
    public InventoryHistory update(InventoryHistory inventoryHistory) {
        LOG.debug("Request to update InventoryHistory : {}", inventoryHistory);
        return inventoryHistoryRepository.save(inventoryHistory);
    }

    @Override
    public Optional<InventoryHistory> partialUpdate(InventoryHistory inventoryHistory) {
        LOG.debug("Request to partially update InventoryHistory : {}", inventoryHistory);

        return inventoryHistoryRepository
            .findById(inventoryHistory.getId())
            .map(existingInventoryHistory -> {
                if (inventoryHistory.getInventoryValue() != null) {
                    existingInventoryHistory.setInventoryValue(inventoryHistory.getInventoryValue());
                }
                if (inventoryHistory.getReason() != null) {
                    existingInventoryHistory.setReason(inventoryHistory.getReason());
                }
                if (inventoryHistory.getUpdatedBy() != null) {
                    existingInventoryHistory.setUpdatedBy(inventoryHistory.getUpdatedBy());
                }
                if (inventoryHistory.getUpdatedTime() != null) {
                    existingInventoryHistory.setUpdatedTime(inventoryHistory.getUpdatedTime());
                }

                return existingInventoryHistory;
            })
            .map(inventoryHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InventoryHistory> findAll(Pageable pageable) {
        LOG.debug("Request to get all InventoryHistories");
        return inventoryHistoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryHistory> findOne(Long id) {
        LOG.debug("Request to get InventoryHistory : {}", id);
        return inventoryHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete InventoryHistory : {}", id);
        inventoryHistoryRepository.deleteById(id);
    }
}
