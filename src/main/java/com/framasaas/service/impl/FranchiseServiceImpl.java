package com.framasaas.service.impl;

import com.framasaas.domain.Franchise;
import com.framasaas.repository.FranchiseRepository;
import com.framasaas.service.FranchiseService;
import com.framasaas.service.dto.FranchiseDTO;
import com.framasaas.service.mapper.FranchiseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.Franchise}.
 */
@Service
@Transactional
public class FranchiseServiceImpl implements FranchiseService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseServiceImpl.class);

    private final FranchiseRepository franchiseRepository;

    private final FranchiseMapper franchiseMapper;

    public FranchiseServiceImpl(FranchiseRepository franchiseRepository, FranchiseMapper franchiseMapper) {
        this.franchiseRepository = franchiseRepository;
        this.franchiseMapper = franchiseMapper;
    }

    @Override
    public FranchiseDTO save(FranchiseDTO franchiseDTO) {
        LOG.debug("Request to save Franchise : {}", franchiseDTO);
        Franchise franchise = franchiseMapper.toEntity(franchiseDTO);
        franchise = franchiseRepository.save(franchise);
        return franchiseMapper.toDto(franchise);
    }

    @Override
    public FranchiseDTO update(FranchiseDTO franchiseDTO) {
        LOG.debug("Request to update Franchise : {}", franchiseDTO);
        Franchise franchise = franchiseMapper.toEntity(franchiseDTO);
        franchise = franchiseRepository.save(franchise);
        return franchiseMapper.toDto(franchise);
    }

    @Override
    public Optional<FranchiseDTO> partialUpdate(FranchiseDTO franchiseDTO) {
        LOG.debug("Request to partially update Franchise : {}", franchiseDTO);

        return franchiseRepository
            .findById(franchiseDTO.getId())
            .map(existingFranchise -> {
                franchiseMapper.partialUpdate(existingFranchise, franchiseDTO);

                return existingFranchise;
            })
            .map(franchiseRepository::save)
            .map(franchiseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseDTO> findOne(Long id) {
        LOG.debug("Request to get Franchise : {}", id);
        return franchiseRepository.findById(id).map(franchiseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Franchise : {}", id);
        franchiseRepository.deleteById(id);
    }
}
