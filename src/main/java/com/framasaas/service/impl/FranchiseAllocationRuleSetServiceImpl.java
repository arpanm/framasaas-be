package com.framasaas.service.impl;

import com.framasaas.domain.FranchiseAllocationRuleSet;
import com.framasaas.repository.FranchiseAllocationRuleSetRepository;
import com.framasaas.service.FranchiseAllocationRuleSetService;
import com.framasaas.service.dto.FranchiseAllocationRuleSetDTO;
import com.framasaas.service.mapper.FranchiseAllocationRuleSetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.FranchiseAllocationRuleSet}.
 */
@Service
@Transactional
public class FranchiseAllocationRuleSetServiceImpl implements FranchiseAllocationRuleSetService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseAllocationRuleSetServiceImpl.class);

    private final FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository;

    private final FranchiseAllocationRuleSetMapper franchiseAllocationRuleSetMapper;

    public FranchiseAllocationRuleSetServiceImpl(
        FranchiseAllocationRuleSetRepository franchiseAllocationRuleSetRepository,
        FranchiseAllocationRuleSetMapper franchiseAllocationRuleSetMapper
    ) {
        this.franchiseAllocationRuleSetRepository = franchiseAllocationRuleSetRepository;
        this.franchiseAllocationRuleSetMapper = franchiseAllocationRuleSetMapper;
    }

    @Override
    public FranchiseAllocationRuleSetDTO save(FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO) {
        LOG.debug("Request to save FranchiseAllocationRuleSet : {}", franchiseAllocationRuleSetDTO);
        FranchiseAllocationRuleSet franchiseAllocationRuleSet = franchiseAllocationRuleSetMapper.toEntity(franchiseAllocationRuleSetDTO);
        franchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.save(franchiseAllocationRuleSet);
        return franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);
    }

    @Override
    public FranchiseAllocationRuleSetDTO update(FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO) {
        LOG.debug("Request to update FranchiseAllocationRuleSet : {}", franchiseAllocationRuleSetDTO);
        FranchiseAllocationRuleSet franchiseAllocationRuleSet = franchiseAllocationRuleSetMapper.toEntity(franchiseAllocationRuleSetDTO);
        franchiseAllocationRuleSet = franchiseAllocationRuleSetRepository.save(franchiseAllocationRuleSet);
        return franchiseAllocationRuleSetMapper.toDto(franchiseAllocationRuleSet);
    }

    @Override
    public Optional<FranchiseAllocationRuleSetDTO> partialUpdate(FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO) {
        LOG.debug("Request to partially update FranchiseAllocationRuleSet : {}", franchiseAllocationRuleSetDTO);

        return franchiseAllocationRuleSetRepository
            .findById(franchiseAllocationRuleSetDTO.getId())
            .map(existingFranchiseAllocationRuleSet -> {
                franchiseAllocationRuleSetMapper.partialUpdate(existingFranchiseAllocationRuleSet, franchiseAllocationRuleSetDTO);

                return existingFranchiseAllocationRuleSet;
            })
            .map(franchiseAllocationRuleSetRepository::save)
            .map(franchiseAllocationRuleSetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseAllocationRuleSetDTO> findOne(Long id) {
        LOG.debug("Request to get FranchiseAllocationRuleSet : {}", id);
        return franchiseAllocationRuleSetRepository.findById(id).map(franchiseAllocationRuleSetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseAllocationRuleSet : {}", id);
        franchiseAllocationRuleSetRepository.deleteById(id);
    }
}
