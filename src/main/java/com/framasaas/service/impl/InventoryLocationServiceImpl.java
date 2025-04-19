package com.framasaas.service.impl;

import com.framasaas.domain.InventoryLocation;
import com.framasaas.repository.InventoryLocationRepository;
import com.framasaas.service.InventoryLocationService;
import com.framasaas.service.dto.InventoryLocationDTO;
import com.framasaas.service.mapper.InventoryLocationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.InventoryLocation}.
 */
@Service
@Transactional
public class InventoryLocationServiceImpl implements InventoryLocationService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryLocationServiceImpl.class);

    private final InventoryLocationRepository inventoryLocationRepository;

    private final InventoryLocationMapper inventoryLocationMapper;

    public InventoryLocationServiceImpl(
        InventoryLocationRepository inventoryLocationRepository,
        InventoryLocationMapper inventoryLocationMapper
    ) {
        this.inventoryLocationRepository = inventoryLocationRepository;
        this.inventoryLocationMapper = inventoryLocationMapper;
    }

    @Override
    public InventoryLocationDTO save(InventoryLocationDTO inventoryLocationDTO) {
        LOG.debug("Request to save InventoryLocation : {}", inventoryLocationDTO);
        InventoryLocation inventoryLocation = inventoryLocationMapper.toEntity(inventoryLocationDTO);
        inventoryLocation = inventoryLocationRepository.save(inventoryLocation);
        return inventoryLocationMapper.toDto(inventoryLocation);
    }

    @Override
    public InventoryLocationDTO update(InventoryLocationDTO inventoryLocationDTO) {
        LOG.debug("Request to update InventoryLocation : {}", inventoryLocationDTO);
        InventoryLocation inventoryLocation = inventoryLocationMapper.toEntity(inventoryLocationDTO);
        inventoryLocation = inventoryLocationRepository.save(inventoryLocation);
        return inventoryLocationMapper.toDto(inventoryLocation);
    }

    @Override
    public Optional<InventoryLocationDTO> partialUpdate(InventoryLocationDTO inventoryLocationDTO) {
        LOG.debug("Request to partially update InventoryLocation : {}", inventoryLocationDTO);

        return inventoryLocationRepository
            .findById(inventoryLocationDTO.getId())
            .map(existingInventoryLocation -> {
                inventoryLocationMapper.partialUpdate(existingInventoryLocation, inventoryLocationDTO);

                return existingInventoryLocation;
            })
            .map(inventoryLocationRepository::save)
            .map(inventoryLocationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryLocationDTO> findOne(Long id) {
        LOG.debug("Request to get InventoryLocation : {}", id);
        return inventoryLocationRepository.findById(id).map(inventoryLocationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete InventoryLocation : {}", id);
        inventoryLocationRepository.deleteById(id);
    }
}
