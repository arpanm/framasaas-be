package com.framasaas.service.impl;

import com.framasaas.domain.FranchiseUser;
import com.framasaas.repository.FranchiseUserRepository;
import com.framasaas.service.FranchiseUserService;
import com.framasaas.service.dto.FranchiseUserDTO;
import com.framasaas.service.mapper.FranchiseUserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.FranchiseUser}.
 */
@Service
@Transactional
public class FranchiseUserServiceImpl implements FranchiseUserService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseUserServiceImpl.class);

    private final FranchiseUserRepository franchiseUserRepository;

    private final FranchiseUserMapper franchiseUserMapper;

    public FranchiseUserServiceImpl(FranchiseUserRepository franchiseUserRepository, FranchiseUserMapper franchiseUserMapper) {
        this.franchiseUserRepository = franchiseUserRepository;
        this.franchiseUserMapper = franchiseUserMapper;
    }

    @Override
    public FranchiseUserDTO save(FranchiseUserDTO franchiseUserDTO) {
        LOG.debug("Request to save FranchiseUser : {}", franchiseUserDTO);
        FranchiseUser franchiseUser = franchiseUserMapper.toEntity(franchiseUserDTO);
        franchiseUser = franchiseUserRepository.save(franchiseUser);
        return franchiseUserMapper.toDto(franchiseUser);
    }

    @Override
    public FranchiseUserDTO update(FranchiseUserDTO franchiseUserDTO) {
        LOG.debug("Request to update FranchiseUser : {}", franchiseUserDTO);
        FranchiseUser franchiseUser = franchiseUserMapper.toEntity(franchiseUserDTO);
        franchiseUser = franchiseUserRepository.save(franchiseUser);
        return franchiseUserMapper.toDto(franchiseUser);
    }

    @Override
    public Optional<FranchiseUserDTO> partialUpdate(FranchiseUserDTO franchiseUserDTO) {
        LOG.debug("Request to partially update FranchiseUser : {}", franchiseUserDTO);

        return franchiseUserRepository
            .findById(franchiseUserDTO.getId())
            .map(existingFranchiseUser -> {
                franchiseUserMapper.partialUpdate(existingFranchiseUser, franchiseUserDTO);

                return existingFranchiseUser;
            })
            .map(franchiseUserRepository::save)
            .map(franchiseUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseUserDTO> findOne(Long id) {
        LOG.debug("Request to get FranchiseUser : {}", id);
        return franchiseUserRepository.findById(id).map(franchiseUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseUser : {}", id);
        franchiseUserRepository.deleteById(id);
    }
}
