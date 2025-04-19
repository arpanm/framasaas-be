package com.framasaas.service.impl;

import com.framasaas.domain.WarrantyMasterPriceHistory;
import com.framasaas.repository.WarrantyMasterPriceHistoryRepository;
import com.framasaas.service.WarrantyMasterPriceHistoryService;
import com.framasaas.service.dto.WarrantyMasterPriceHistoryDTO;
import com.framasaas.service.mapper.WarrantyMasterPriceHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.WarrantyMasterPriceHistory}.
 */
@Service
@Transactional
public class WarrantyMasterPriceHistoryServiceImpl implements WarrantyMasterPriceHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(WarrantyMasterPriceHistoryServiceImpl.class);

    private final WarrantyMasterPriceHistoryRepository warrantyMasterPriceHistoryRepository;

    private final WarrantyMasterPriceHistoryMapper warrantyMasterPriceHistoryMapper;

    public WarrantyMasterPriceHistoryServiceImpl(
        WarrantyMasterPriceHistoryRepository warrantyMasterPriceHistoryRepository,
        WarrantyMasterPriceHistoryMapper warrantyMasterPriceHistoryMapper
    ) {
        this.warrantyMasterPriceHistoryRepository = warrantyMasterPriceHistoryRepository;
        this.warrantyMasterPriceHistoryMapper = warrantyMasterPriceHistoryMapper;
    }

    @Override
    public WarrantyMasterPriceHistoryDTO save(WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO) {
        LOG.debug("Request to save WarrantyMasterPriceHistory : {}", warrantyMasterPriceHistoryDTO);
        WarrantyMasterPriceHistory warrantyMasterPriceHistory = warrantyMasterPriceHistoryMapper.toEntity(warrantyMasterPriceHistoryDTO);
        warrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.save(warrantyMasterPriceHistory);
        return warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);
    }

    @Override
    public WarrantyMasterPriceHistoryDTO update(WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO) {
        LOG.debug("Request to update WarrantyMasterPriceHistory : {}", warrantyMasterPriceHistoryDTO);
        WarrantyMasterPriceHistory warrantyMasterPriceHistory = warrantyMasterPriceHistoryMapper.toEntity(warrantyMasterPriceHistoryDTO);
        warrantyMasterPriceHistory = warrantyMasterPriceHistoryRepository.save(warrantyMasterPriceHistory);
        return warrantyMasterPriceHistoryMapper.toDto(warrantyMasterPriceHistory);
    }

    @Override
    public Optional<WarrantyMasterPriceHistoryDTO> partialUpdate(WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO) {
        LOG.debug("Request to partially update WarrantyMasterPriceHistory : {}", warrantyMasterPriceHistoryDTO);

        return warrantyMasterPriceHistoryRepository
            .findById(warrantyMasterPriceHistoryDTO.getId())
            .map(existingWarrantyMasterPriceHistory -> {
                warrantyMasterPriceHistoryMapper.partialUpdate(existingWarrantyMasterPriceHistory, warrantyMasterPriceHistoryDTO);

                return existingWarrantyMasterPriceHistory;
            })
            .map(warrantyMasterPriceHistoryRepository::save)
            .map(warrantyMasterPriceHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WarrantyMasterPriceHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get WarrantyMasterPriceHistory : {}", id);
        return warrantyMasterPriceHistoryRepository.findById(id).map(warrantyMasterPriceHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete WarrantyMasterPriceHistory : {}", id);
        warrantyMasterPriceHistoryRepository.deleteById(id);
    }
}
