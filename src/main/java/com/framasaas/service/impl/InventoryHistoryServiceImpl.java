package com.framasaas.service.impl;

import com.framasaas.domain.InventoryHistory;
import com.framasaas.repository.InventoryHistoryRepository;
import com.framasaas.service.InventoryHistoryService;
import com.framasaas.service.dto.InventoryHistoryDTO;
import com.framasaas.service.mapper.InventoryHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.InventoryHistory}.
 */
@Service
@Transactional
public class InventoryHistoryServiceImpl implements InventoryHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryHistoryServiceImpl.class);

    private final InventoryHistoryRepository inventoryHistoryRepository;

    private final InventoryHistoryMapper inventoryHistoryMapper;

    public InventoryHistoryServiceImpl(
        InventoryHistoryRepository inventoryHistoryRepository,
        InventoryHistoryMapper inventoryHistoryMapper
    ) {
        this.inventoryHistoryRepository = inventoryHistoryRepository;
        this.inventoryHistoryMapper = inventoryHistoryMapper;
    }

    @Override
    public InventoryHistoryDTO save(InventoryHistoryDTO inventoryHistoryDTO) {
        LOG.debug("Request to save InventoryHistory : {}", inventoryHistoryDTO);
        InventoryHistory inventoryHistory = inventoryHistoryMapper.toEntity(inventoryHistoryDTO);
        inventoryHistory = inventoryHistoryRepository.save(inventoryHistory);
        return inventoryHistoryMapper.toDto(inventoryHistory);
    }

    @Override
    public InventoryHistoryDTO update(InventoryHistoryDTO inventoryHistoryDTO) {
        LOG.debug("Request to update InventoryHistory : {}", inventoryHistoryDTO);
        InventoryHistory inventoryHistory = inventoryHistoryMapper.toEntity(inventoryHistoryDTO);
        inventoryHistory = inventoryHistoryRepository.save(inventoryHistory);
        return inventoryHistoryMapper.toDto(inventoryHistory);
    }

    @Override
    public Optional<InventoryHistoryDTO> partialUpdate(InventoryHistoryDTO inventoryHistoryDTO) {
        LOG.debug("Request to partially update InventoryHistory : {}", inventoryHistoryDTO);

        return inventoryHistoryRepository
            .findById(inventoryHistoryDTO.getId())
            .map(existingInventoryHistory -> {
                inventoryHistoryMapper.partialUpdate(existingInventoryHistory, inventoryHistoryDTO);

                return existingInventoryHistory;
            })
            .map(inventoryHistoryRepository::save)
            .map(inventoryHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get InventoryHistory : {}", id);
        return inventoryHistoryRepository.findById(id).map(inventoryHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete InventoryHistory : {}", id);
        inventoryHistoryRepository.deleteById(id);
    }
}
