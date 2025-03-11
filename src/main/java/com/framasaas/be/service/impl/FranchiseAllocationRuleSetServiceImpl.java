package com.framasaas.be.service.impl;

import com.framasaas.be.domain.FranchiseAllocationRuleSet;
import com.framasaas.be.repository.FranchiseAllocationRuleSetRepository;
import com.framasaas.be.service.FranchiseAllocationRuleSetService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.FranchiseAllocationRuleSet}.
 */
@Service
@Transactional
public class FranchiseAllocationRuleSetServiceImpl implements FranchiseAllocationRuleSetService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseAllocationRuleSetServiceImpl.class);

    private final FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository;

    public FranchiseAllocationRuleSetServiceImpl(FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository) {
        this.franchiseAllocationRuleSetRepository = franchiseAllocationRuleSetRepository;
    }

    @Override
    public FranchiseAllocationRuleSet save(FranchiseAllocationRuleSet franchiseAllocationRuleSet) {
        LOG.debug("Request to save FranchiseAllocationRuleSet : {}", franchiseAllocationRuleSet);
        return franchiseAllocationRuleSetRepository.save(franchiseAllocationRuleSet);
    }

    @Override
    public FranchiseAllocationRuleSet update(FranchiseAllocationRuleSet franchiseAllocationRuleSet) {
        LOG.debug("Request to update FranchiseAllocationRuleSet : {}", franchiseAllocationRuleSet);
        return franchiseAllocationRuleSetRepository.save(franchiseAllocationRuleSet);
    }

    @Override
    public Optional<FranchiseAllocationRuleSet> partialUpdate(FranchiseAllocationRuleSet franchiseAllocationRuleSet) {
        LOG.debug("Request to partially update FranchiseAllocationRuleSet : {}", franchiseAllocationRuleSet);

        return franchiseAllocationRuleSetRepository
            .findById(franchiseAllocationRuleSet.getId())
            .map(existingFranchiseAllocationRuleSet -> {
                if (franchiseAllocationRuleSet.getName() != null) {
                    existingFranchiseAllocationRuleSet.setName(franchiseAllocationRuleSet.getName());
                }
                if (franchiseAllocationRuleSet.getSortType() != null) {
                    existingFranchiseAllocationRuleSet.setSortType(franchiseAllocationRuleSet.getSortType());
                }
                if (franchiseAllocationRuleSet.getPriority() != null) {
                    existingFranchiseAllocationRuleSet.setPriority(franchiseAllocationRuleSet.getPriority());
                }
                if (franchiseAllocationRuleSet.getIsActive() != null) {
                    existingFranchiseAllocationRuleSet.setIsActive(franchiseAllocationRuleSet.getIsActive());
                }
                if (franchiseAllocationRuleSet.getCreateddBy() != null) {
                    existingFranchiseAllocationRuleSet.setCreateddBy(franchiseAllocationRuleSet.getCreateddBy());
                }
                if (franchiseAllocationRuleSet.getCreatedTime() != null) {
                    existingFranchiseAllocationRuleSet.setCreatedTime(franchiseAllocationRuleSet.getCreatedTime());
                }
                if (franchiseAllocationRuleSet.getUpdatedBy() != null) {
                    existingFranchiseAllocationRuleSet.setUpdatedBy(franchiseAllocationRuleSet.getUpdatedBy());
                }
                if (franchiseAllocationRuleSet.getUpdatedTime() != null) {
                    existingFranchiseAllocationRuleSet.setUpdatedTime(franchiseAllocationRuleSet.getUpdatedTime());
                }

                return existingFranchiseAllocationRuleSet;
            })
            .map(franchiseAllocationRuleSetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FranchiseAllocationRuleSet> findAll(Pageable pageable) {
        LOG.debug("Request to get all FranchiseAllocationRuleSets");
        return franchiseAllocationRuleSetRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseAllocationRuleSet> findOne(Long id) {
        LOG.debug("Request to get FranchiseAllocationRuleSet : {}", id);
        return franchiseAllocationRuleSetRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseAllocationRuleSet : {}", id);
        franchiseAllocationRuleSetRepository.deleteById(id);
    }
}
