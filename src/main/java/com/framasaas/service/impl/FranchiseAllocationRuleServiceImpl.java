package com.framasaas.service.impl;

import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.repository.FranchiseAllocationRuleRepository;
import com.framasaas.service.FranchiseAllocationRuleService;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import com.framasaas.service.mapper.FranchiseAllocationRuleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.FranchiseAllocationRule}.
 */
@Service
@Transactional
public class FranchiseAllocationRuleServiceImpl implements FranchiseAllocationRuleService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseAllocationRuleServiceImpl.class);

    private final FranchiseAllocationRuleRepository franchiseAllocationRuleRepository;

    private final FranchiseAllocationRuleMapper franchiseAllocationRuleMapper;

    public FranchiseAllocationRuleServiceImpl(
        FranchiseAllocationRuleRepository franchiseAllocationRuleRepository,
        FranchiseAllocationRuleMapper franchiseAllocationRuleMapper
    ) {
        this.franchiseAllocationRuleRepository = franchiseAllocationRuleRepository;
        this.franchiseAllocationRuleMapper = franchiseAllocationRuleMapper;
    }

    @Override
    public FranchiseAllocationRuleDTO save(FranchiseAllocationRuleDTO franchiseAllocationRuleDTO) {
        LOG.debug("Request to save FranchiseAllocationRule : {}", franchiseAllocationRuleDTO);
        FranchiseAllocationRule franchiseAllocationRule = franchiseAllocationRuleMapper.toEntity(franchiseAllocationRuleDTO);
        franchiseAllocationRule = franchiseAllocationRuleRepository.save(franchiseAllocationRule);
        return franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);
    }

    @Override
    public FranchiseAllocationRuleDTO update(FranchiseAllocationRuleDTO franchiseAllocationRuleDTO) {
        LOG.debug("Request to update FranchiseAllocationRule : {}", franchiseAllocationRuleDTO);
        FranchiseAllocationRule franchiseAllocationRule = franchiseAllocationRuleMapper.toEntity(franchiseAllocationRuleDTO);
        franchiseAllocationRule = franchiseAllocationRuleRepository.save(franchiseAllocationRule);
        return franchiseAllocationRuleMapper.toDto(franchiseAllocationRule);
    }

    @Override
    public Optional<FranchiseAllocationRuleDTO> partialUpdate(FranchiseAllocationRuleDTO franchiseAllocationRuleDTO) {
        LOG.debug("Request to partially update FranchiseAllocationRule : {}", franchiseAllocationRuleDTO);

        return franchiseAllocationRuleRepository
            .findById(franchiseAllocationRuleDTO.getId())
            .map(existingFranchiseAllocationRule -> {
                franchiseAllocationRuleMapper.partialUpdate(existingFranchiseAllocationRule, franchiseAllocationRuleDTO);

                return existingFranchiseAllocationRule;
            })
            .map(franchiseAllocationRuleRepository::save)
            .map(franchiseAllocationRuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseAllocationRuleDTO> findOne(Long id) {
        LOG.debug("Request to get FranchiseAllocationRule : {}", id);
        return franchiseAllocationRuleRepository.findById(id).map(franchiseAllocationRuleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseAllocationRule : {}", id);
        franchiseAllocationRuleRepository.deleteById(id);
    }
}
