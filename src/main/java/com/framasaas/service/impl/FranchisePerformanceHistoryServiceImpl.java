package com.framasaas.service.impl;

import com.framasaas.domain.FranchisePerformanceHistory;
import com.framasaas.repository.FranchisePerformanceHistoryRepository;
import com.framasaas.service.FranchisePerformanceHistoryService;
import com.framasaas.service.dto.FranchisePerformanceHistoryDTO;
import com.framasaas.service.mapper.FranchisePerformanceHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.FranchisePerformanceHistory}.
 */
@Service
@Transactional
public class FranchisePerformanceHistoryServiceImpl implements FranchisePerformanceHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchisePerformanceHistoryServiceImpl.class);

    private final FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository;

    private final FranchisePerformanceHistoryMapper franchisePerformanceHistoryMapper;

    public FranchisePerformanceHistoryServiceImpl(
        FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository,
        FranchisePerformanceHistoryMapper franchisePerformanceHistoryMapper
    ) {
        this.franchisePerformanceHistoryRepository = franchisePerformanceHistoryRepository;
        this.franchisePerformanceHistoryMapper = franchisePerformanceHistoryMapper;
    }

    @Override
    public FranchisePerformanceHistoryDTO save(FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO) {
        LOG.debug("Request to save FranchisePerformanceHistory : {}", franchisePerformanceHistoryDTO);
        FranchisePerformanceHistory franchisePerformanceHistory = franchisePerformanceHistoryMapper.toEntity(
            franchisePerformanceHistoryDTO
        );
        franchisePerformanceHistory = franchisePerformanceHistoryRepository.save(franchisePerformanceHistory);
        return franchisePerformanceHistoryMapper.toDto(franchisePerformanceHistory);
    }

    @Override
    public FranchisePerformanceHistoryDTO update(FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO) {
        LOG.debug("Request to update FranchisePerformanceHistory : {}", franchisePerformanceHistoryDTO);
        FranchisePerformanceHistory franchisePerformanceHistory = franchisePerformanceHistoryMapper.toEntity(
            franchisePerformanceHistoryDTO
        );
        franchisePerformanceHistory = franchisePerformanceHistoryRepository.save(franchisePerformanceHistory);
        return franchisePerformanceHistoryMapper.toDto(franchisePerformanceHistory);
    }

    @Override
    public Optional<FranchisePerformanceHistoryDTO> partialUpdate(FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO) {
        LOG.debug("Request to partially update FranchisePerformanceHistory : {}", franchisePerformanceHistoryDTO);

        return franchisePerformanceHistoryRepository
            .findById(franchisePerformanceHistoryDTO.getId())
            .map(existingFranchisePerformanceHistory -> {
                franchisePerformanceHistoryMapper.partialUpdate(existingFranchisePerformanceHistory, franchisePerformanceHistoryDTO);

                return existingFranchisePerformanceHistory;
            })
            .map(franchisePerformanceHistoryRepository::save)
            .map(franchisePerformanceHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchisePerformanceHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get FranchisePerformanceHistory : {}", id);
        return franchisePerformanceHistoryRepository.findById(id).map(franchisePerformanceHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchisePerformanceHistory : {}", id);
        franchisePerformanceHistoryRepository.deleteById(id);
    }
}
