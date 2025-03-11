package com.framasaas.be.service.impl;

import com.framasaas.be.domain.FranchiseAllocationRule;
import com.framasaas.be.repository.FranchiseAllocationRuleRepository;
import com.framasaas.be.service.FranchiseAllocationRuleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.FranchiseAllocationRule}.
 */
@Service
@Transactional
public class FranchiseAllocationRuleServiceImpl implements FranchiseAllocationRuleService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseAllocationRuleServiceImpl.class);

    private final FranchiseAllocationRuleRepository franchiseAllocationRuleRepository;

    public FranchiseAllocationRuleServiceImpl(FranchiseAllocationRuleRepository franchiseAllocationRuleRepository) {
        this.franchiseAllocationRuleRepository = franchiseAllocationRuleRepository;
    }

    @Override
    public FranchiseAllocationRule save(FranchiseAllocationRule franchiseAllocationRule) {
        LOG.debug("Request to save FranchiseAllocationRule : {}", franchiseAllocationRule);
        return franchiseAllocationRuleRepository.save(franchiseAllocationRule);
    }

    @Override
    public FranchiseAllocationRule update(FranchiseAllocationRule franchiseAllocationRule) {
        LOG.debug("Request to update FranchiseAllocationRule : {}", franchiseAllocationRule);
        return franchiseAllocationRuleRepository.save(franchiseAllocationRule);
    }

    @Override
    public Optional<FranchiseAllocationRule> partialUpdate(FranchiseAllocationRule franchiseAllocationRule) {
        LOG.debug("Request to partially update FranchiseAllocationRule : {}", franchiseAllocationRule);

        return franchiseAllocationRuleRepository
            .findById(franchiseAllocationRule.getId())
            .map(existingFranchiseAllocationRule -> {
                if (franchiseAllocationRule.getRuleType() != null) {
                    existingFranchiseAllocationRule.setRuleType(franchiseAllocationRule.getRuleType());
                }
                if (franchiseAllocationRule.getJoinType() != null) {
                    existingFranchiseAllocationRule.setJoinType(franchiseAllocationRule.getJoinType());
                }
                if (franchiseAllocationRule.getCreateddBy() != null) {
                    existingFranchiseAllocationRule.setCreateddBy(franchiseAllocationRule.getCreateddBy());
                }
                if (franchiseAllocationRule.getCreatedTime() != null) {
                    existingFranchiseAllocationRule.setCreatedTime(franchiseAllocationRule.getCreatedTime());
                }
                if (franchiseAllocationRule.getUpdatedBy() != null) {
                    existingFranchiseAllocationRule.setUpdatedBy(franchiseAllocationRule.getUpdatedBy());
                }
                if (franchiseAllocationRule.getUpdatedTime() != null) {
                    existingFranchiseAllocationRule.setUpdatedTime(franchiseAllocationRule.getUpdatedTime());
                }

                return existingFranchiseAllocationRule;
            })
            .map(franchiseAllocationRuleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FranchiseAllocationRule> findAll(Pageable pageable) {
        LOG.debug("Request to get all FranchiseAllocationRules");
        return franchiseAllocationRuleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseAllocationRule> findOne(Long id) {
        LOG.debug("Request to get FranchiseAllocationRule : {}", id);
        return franchiseAllocationRuleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseAllocationRule : {}", id);
        franchiseAllocationRuleRepository.deleteById(id);
    }
}
