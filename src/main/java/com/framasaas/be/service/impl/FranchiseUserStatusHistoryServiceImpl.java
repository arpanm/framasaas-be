package com.framasaas.be.service.impl;

import com.framasaas.be.domain.FranchiseUserStatusHistory;
import com.framasaas.be.repository.FranchiseUserStatusHistoryRepository;
import com.framasaas.be.service.FranchiseUserStatusHistoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.FranchiseUserStatusHistory}.
 */
@Service
@Transactional
public class FranchiseUserStatusHistoryServiceImpl implements FranchiseUserStatusHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseUserStatusHistoryServiceImpl.class);

    private final FranchiseUserStatusHistoryRepository franchiseUserStatusHistoryRepository;

    public FranchiseUserStatusHistoryServiceImpl(FranchiseUserStatusHistoryRepository franchiseUserStatusHistoryRepository) {
        this.franchiseUserStatusHistoryRepository = franchiseUserStatusHistoryRepository;
    }

    @Override
    public FranchiseUserStatusHistory save(FranchiseUserStatusHistory franchiseUserStatusHistory) {
        LOG.debug("Request to save FranchiseUserStatusHistory : {}", franchiseUserStatusHistory);
        return franchiseUserStatusHistoryRepository.save(franchiseUserStatusHistory);
    }

    @Override
    public FranchiseUserStatusHistory update(FranchiseUserStatusHistory franchiseUserStatusHistory) {
        LOG.debug("Request to update FranchiseUserStatusHistory : {}", franchiseUserStatusHistory);
        return franchiseUserStatusHistoryRepository.save(franchiseUserStatusHistory);
    }

    @Override
    public Optional<FranchiseUserStatusHistory> partialUpdate(FranchiseUserStatusHistory franchiseUserStatusHistory) {
        LOG.debug("Request to partially update FranchiseUserStatusHistory : {}", franchiseUserStatusHistory);

        return franchiseUserStatusHistoryRepository
            .findById(franchiseUserStatusHistory.getId())
            .map(existingFranchiseUserStatusHistory -> {
                if (franchiseUserStatusHistory.getUserSatus() != null) {
                    existingFranchiseUserStatusHistory.setUserSatus(franchiseUserStatusHistory.getUserSatus());
                }
                if (franchiseUserStatusHistory.getUpdatedBy() != null) {
                    existingFranchiseUserStatusHistory.setUpdatedBy(franchiseUserStatusHistory.getUpdatedBy());
                }
                if (franchiseUserStatusHistory.getUpdatedTime() != null) {
                    existingFranchiseUserStatusHistory.setUpdatedTime(franchiseUserStatusHistory.getUpdatedTime());
                }

                return existingFranchiseUserStatusHistory;
            })
            .map(franchiseUserStatusHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FranchiseUserStatusHistory> findAll(Pageable pageable) {
        LOG.debug("Request to get all FranchiseUserStatusHistories");
        return franchiseUserStatusHistoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseUserStatusHistory> findOne(Long id) {
        LOG.debug("Request to get FranchiseUserStatusHistory : {}", id);
        return franchiseUserStatusHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseUserStatusHistory : {}", id);
        franchiseUserStatusHistoryRepository.deleteById(id);
    }
}
