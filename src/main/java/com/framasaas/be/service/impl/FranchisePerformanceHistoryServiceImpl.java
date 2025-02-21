package com.framasaas.be.service.impl;

import com.framasaas.be.domain.FranchisePerformanceHistory;
import com.framasaas.be.repository.FranchisePerformanceHistoryRepository;
import com.framasaas.be.service.FranchisePerformanceHistoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.FranchisePerformanceHistory}.
 */
@Service
@Transactional
public class FranchisePerformanceHistoryServiceImpl implements FranchisePerformanceHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchisePerformanceHistoryServiceImpl.class);

    private final FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository;

    public FranchisePerformanceHistoryServiceImpl(FranchisePerformanceHistoryRepository franchisePerformanceHistoryRepository) {
        this.franchisePerformanceHistoryRepository = franchisePerformanceHistoryRepository;
    }

    @Override
    public FranchisePerformanceHistory save(FranchisePerformanceHistory franchisePerformanceHistory) {
        LOG.debug("Request to save FranchisePerformanceHistory : {}", franchisePerformanceHistory);
        return franchisePerformanceHistoryRepository.save(franchisePerformanceHistory);
    }

    @Override
    public FranchisePerformanceHistory update(FranchisePerformanceHistory franchisePerformanceHistory) {
        LOG.debug("Request to update FranchisePerformanceHistory : {}", franchisePerformanceHistory);
        return franchisePerformanceHistoryRepository.save(franchisePerformanceHistory);
    }

    @Override
    public Optional<FranchisePerformanceHistory> partialUpdate(FranchisePerformanceHistory franchisePerformanceHistory) {
        LOG.debug("Request to partially update FranchisePerformanceHistory : {}", franchisePerformanceHistory);

        return franchisePerformanceHistoryRepository
            .findById(franchisePerformanceHistory.getId())
            .map(existingFranchisePerformanceHistory -> {
                if (franchisePerformanceHistory.getPerformanceScore() != null) {
                    existingFranchisePerformanceHistory.setPerformanceScore(franchisePerformanceHistory.getPerformanceScore());
                }
                if (franchisePerformanceHistory.getPerformanceTag() != null) {
                    existingFranchisePerformanceHistory.setPerformanceTag(franchisePerformanceHistory.getPerformanceTag());
                }
                if (franchisePerformanceHistory.getUpdatedBy() != null) {
                    existingFranchisePerformanceHistory.setUpdatedBy(franchisePerformanceHistory.getUpdatedBy());
                }
                if (franchisePerformanceHistory.getUpdatedTime() != null) {
                    existingFranchisePerformanceHistory.setUpdatedTime(franchisePerformanceHistory.getUpdatedTime());
                }

                return existingFranchisePerformanceHistory;
            })
            .map(franchisePerformanceHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FranchisePerformanceHistory> findAll(Pageable pageable) {
        LOG.debug("Request to get all FranchisePerformanceHistories");
        return franchisePerformanceHistoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchisePerformanceHistory> findOne(Long id) {
        LOG.debug("Request to get FranchisePerformanceHistory : {}", id);
        return franchisePerformanceHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchisePerformanceHistory : {}", id);
        franchisePerformanceHistoryRepository.deleteById(id);
    }
}
