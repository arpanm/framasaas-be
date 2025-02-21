package com.framasaas.be.service.impl;

import com.framasaas.be.domain.FranchiseStatusHistory;
import com.framasaas.be.repository.FranchiseStatusHistoryRepository;
import com.framasaas.be.service.FranchiseStatusHistoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.FranchiseStatusHistory}.
 */
@Service
@Transactional
public class FranchiseStatusHistoryServiceImpl implements FranchiseStatusHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseStatusHistoryServiceImpl.class);

    private final FranchiseStatusHistoryRepository franchiseStatusHistoryRepository;

    public FranchiseStatusHistoryServiceImpl(FranchiseStatusHistoryRepository franchiseStatusHistoryRepository) {
        this.franchiseStatusHistoryRepository = franchiseStatusHistoryRepository;
    }

    @Override
    public FranchiseStatusHistory save(FranchiseStatusHistory franchiseStatusHistory) {
        LOG.debug("Request to save FranchiseStatusHistory : {}", franchiseStatusHistory);
        return franchiseStatusHistoryRepository.save(franchiseStatusHistory);
    }

    @Override
    public FranchiseStatusHistory update(FranchiseStatusHistory franchiseStatusHistory) {
        LOG.debug("Request to update FranchiseStatusHistory : {}", franchiseStatusHistory);
        return franchiseStatusHistoryRepository.save(franchiseStatusHistory);
    }

    @Override
    public Optional<FranchiseStatusHistory> partialUpdate(FranchiseStatusHistory franchiseStatusHistory) {
        LOG.debug("Request to partially update FranchiseStatusHistory : {}", franchiseStatusHistory);

        return franchiseStatusHistoryRepository
            .findById(franchiseStatusHistory.getId())
            .map(existingFranchiseStatusHistory -> {
                if (franchiseStatusHistory.getFranchiseSatus() != null) {
                    existingFranchiseStatusHistory.setFranchiseSatus(franchiseStatusHistory.getFranchiseSatus());
                }
                if (franchiseStatusHistory.getUpdatedBy() != null) {
                    existingFranchiseStatusHistory.setUpdatedBy(franchiseStatusHistory.getUpdatedBy());
                }
                if (franchiseStatusHistory.getUpdatedTime() != null) {
                    existingFranchiseStatusHistory.setUpdatedTime(franchiseStatusHistory.getUpdatedTime());
                }

                return existingFranchiseStatusHistory;
            })
            .map(franchiseStatusHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FranchiseStatusHistory> findAll(Pageable pageable) {
        LOG.debug("Request to get all FranchiseStatusHistories");
        return franchiseStatusHistoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseStatusHistory> findOne(Long id) {
        LOG.debug("Request to get FranchiseStatusHistory : {}", id);
        return franchiseStatusHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseStatusHistory : {}", id);
        franchiseStatusHistoryRepository.deleteById(id);
    }
}
