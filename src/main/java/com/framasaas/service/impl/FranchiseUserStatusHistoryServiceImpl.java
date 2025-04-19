package com.framasaas.service.impl;

import com.framasaas.domain.FranchiseUserStatusHistory;
import com.framasaas.repository.FranchiseUserStatusHistoryRepository;
import com.framasaas.service.FranchiseUserStatusHistoryService;
import com.framasaas.service.dto.FranchiseUserStatusHistoryDTO;
import com.framasaas.service.mapper.FranchiseUserStatusHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.FranchiseUserStatusHistory}.
 */
@Service
@Transactional
public class FranchiseUserStatusHistoryServiceImpl implements FranchiseUserStatusHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseUserStatusHistoryServiceImpl.class);

    private final FranchiseUserStatusHistoryRepository franchiseUserStatusHistoryRepository;

    private final FranchiseUserStatusHistoryMapper franchiseUserStatusHistoryMapper;

    public FranchiseUserStatusHistoryServiceImpl(
        FranchiseUserStatusHistoryRepository franchiseUserStatusHistoryRepository,
        FranchiseUserStatusHistoryMapper franchiseUserStatusHistoryMapper
    ) {
        this.franchiseUserStatusHistoryRepository = franchiseUserStatusHistoryRepository;
        this.franchiseUserStatusHistoryMapper = franchiseUserStatusHistoryMapper;
    }

    @Override
    public FranchiseUserStatusHistoryDTO save(FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO) {
        LOG.debug("Request to save FranchiseUserStatusHistory : {}", franchiseUserStatusHistoryDTO);
        FranchiseUserStatusHistory franchiseUserStatusHistory = franchiseUserStatusHistoryMapper.toEntity(franchiseUserStatusHistoryDTO);
        franchiseUserStatusHistory = franchiseUserStatusHistoryRepository.save(franchiseUserStatusHistory);
        return franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);
    }

    @Override
    public FranchiseUserStatusHistoryDTO update(FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO) {
        LOG.debug("Request to update FranchiseUserStatusHistory : {}", franchiseUserStatusHistoryDTO);
        FranchiseUserStatusHistory franchiseUserStatusHistory = franchiseUserStatusHistoryMapper.toEntity(franchiseUserStatusHistoryDTO);
        franchiseUserStatusHistory = franchiseUserStatusHistoryRepository.save(franchiseUserStatusHistory);
        return franchiseUserStatusHistoryMapper.toDto(franchiseUserStatusHistory);
    }

    @Override
    public Optional<FranchiseUserStatusHistoryDTO> partialUpdate(FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO) {
        LOG.debug("Request to partially update FranchiseUserStatusHistory : {}", franchiseUserStatusHistoryDTO);

        return franchiseUserStatusHistoryRepository
            .findById(franchiseUserStatusHistoryDTO.getId())
            .map(existingFranchiseUserStatusHistory -> {
                franchiseUserStatusHistoryMapper.partialUpdate(existingFranchiseUserStatusHistory, franchiseUserStatusHistoryDTO);

                return existingFranchiseUserStatusHistory;
            })
            .map(franchiseUserStatusHistoryRepository::save)
            .map(franchiseUserStatusHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseUserStatusHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get FranchiseUserStatusHistory : {}", id);
        return franchiseUserStatusHistoryRepository.findById(id).map(franchiseUserStatusHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseUserStatusHistory : {}", id);
        franchiseUserStatusHistoryRepository.deleteById(id);
    }
}
