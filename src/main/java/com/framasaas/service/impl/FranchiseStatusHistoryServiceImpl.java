package com.framasaas.service.impl;

import com.framasaas.domain.FranchiseStatusHistory;
import com.framasaas.repository.FranchiseStatusHistoryRepository;
import com.framasaas.service.FranchiseStatusHistoryService;
import com.framasaas.service.dto.FranchiseStatusHistoryDTO;
import com.framasaas.service.mapper.FranchiseStatusHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.FranchiseStatusHistory}.
 */
@Service
@Transactional
public class FranchiseStatusHistoryServiceImpl implements FranchiseStatusHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseStatusHistoryServiceImpl.class);

    private final FranchiseStatusHistoryRepository franchiseStatusHistoryRepository;

    private final FranchiseStatusHistoryMapper franchiseStatusHistoryMapper;

    public FranchiseStatusHistoryServiceImpl(
        FranchiseStatusHistoryRepository franchiseStatusHistoryRepository,
        FranchiseStatusHistoryMapper franchiseStatusHistoryMapper
    ) {
        this.franchiseStatusHistoryRepository = franchiseStatusHistoryRepository;
        this.franchiseStatusHistoryMapper = franchiseStatusHistoryMapper;
    }

    @Override
    public FranchiseStatusHistoryDTO save(FranchiseStatusHistoryDTO franchiseStatusHistoryDTO) {
        LOG.debug("Request to save FranchiseStatusHistory : {}", franchiseStatusHistoryDTO);
        FranchiseStatusHistory franchiseStatusHistory = franchiseStatusHistoryMapper.toEntity(franchiseStatusHistoryDTO);
        franchiseStatusHistory = franchiseStatusHistoryRepository.save(franchiseStatusHistory);
        return franchiseStatusHistoryMapper.toDto(franchiseStatusHistory);
    }

    @Override
    public FranchiseStatusHistoryDTO update(FranchiseStatusHistoryDTO franchiseStatusHistoryDTO) {
        LOG.debug("Request to update FranchiseStatusHistory : {}", franchiseStatusHistoryDTO);
        FranchiseStatusHistory franchiseStatusHistory = franchiseStatusHistoryMapper.toEntity(franchiseStatusHistoryDTO);
        franchiseStatusHistory = franchiseStatusHistoryRepository.save(franchiseStatusHistory);
        return franchiseStatusHistoryMapper.toDto(franchiseStatusHistory);
    }

    @Override
    public Optional<FranchiseStatusHistoryDTO> partialUpdate(FranchiseStatusHistoryDTO franchiseStatusHistoryDTO) {
        LOG.debug("Request to partially update FranchiseStatusHistory : {}", franchiseStatusHistoryDTO);

        return franchiseStatusHistoryRepository
            .findById(franchiseStatusHistoryDTO.getId())
            .map(existingFranchiseStatusHistory -> {
                franchiseStatusHistoryMapper.partialUpdate(existingFranchiseStatusHistory, franchiseStatusHistoryDTO);

                return existingFranchiseStatusHistory;
            })
            .map(franchiseStatusHistoryRepository::save)
            .map(franchiseStatusHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseStatusHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get FranchiseStatusHistory : {}", id);
        return franchiseStatusHistoryRepository.findById(id).map(franchiseStatusHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseStatusHistory : {}", id);
        franchiseStatusHistoryRepository.deleteById(id);
    }
}
