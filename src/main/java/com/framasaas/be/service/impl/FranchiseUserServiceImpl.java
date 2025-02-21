package com.framasaas.be.service.impl;

import com.framasaas.be.domain.FranchiseUser;
import com.framasaas.be.repository.FranchiseUserRepository;
import com.framasaas.be.service.FranchiseUserService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.FranchiseUser}.
 */
@Service
@Transactional
public class FranchiseUserServiceImpl implements FranchiseUserService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseUserServiceImpl.class);

    private final FranchiseUserRepository franchiseUserRepository;

    public FranchiseUserServiceImpl(FranchiseUserRepository franchiseUserRepository) {
        this.franchiseUserRepository = franchiseUserRepository;
    }

    @Override
    public FranchiseUser save(FranchiseUser franchiseUser) {
        LOG.debug("Request to save FranchiseUser : {}", franchiseUser);
        return franchiseUserRepository.save(franchiseUser);
    }

    @Override
    public FranchiseUser update(FranchiseUser franchiseUser) {
        LOG.debug("Request to update FranchiseUser : {}", franchiseUser);
        return franchiseUserRepository.save(franchiseUser);
    }

    @Override
    public Optional<FranchiseUser> partialUpdate(FranchiseUser franchiseUser) {
        LOG.debug("Request to partially update FranchiseUser : {}", franchiseUser);

        return franchiseUserRepository
            .findById(franchiseUser.getId())
            .map(existingFranchiseUser -> {
                if (franchiseUser.getUserName() != null) {
                    existingFranchiseUser.setUserName(franchiseUser.getUserName());
                }
                if (franchiseUser.getEmail() != null) {
                    existingFranchiseUser.setEmail(franchiseUser.getEmail());
                }
                if (franchiseUser.getContact() != null) {
                    existingFranchiseUser.setContact(franchiseUser.getContact());
                }
                if (franchiseUser.getUserStatus() != null) {
                    existingFranchiseUser.setUserStatus(franchiseUser.getUserStatus());
                }
                if (franchiseUser.getCreateddBy() != null) {
                    existingFranchiseUser.setCreateddBy(franchiseUser.getCreateddBy());
                }
                if (franchiseUser.getCreatedTime() != null) {
                    existingFranchiseUser.setCreatedTime(franchiseUser.getCreatedTime());
                }
                if (franchiseUser.getUpdatedBy() != null) {
                    existingFranchiseUser.setUpdatedBy(franchiseUser.getUpdatedBy());
                }
                if (franchiseUser.getUpdatedTime() != null) {
                    existingFranchiseUser.setUpdatedTime(franchiseUser.getUpdatedTime());
                }

                return existingFranchiseUser;
            })
            .map(franchiseUserRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FranchiseUser> findAll(Pageable pageable) {
        LOG.debug("Request to get all FranchiseUsers");
        return franchiseUserRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseUser> findOne(Long id) {
        LOG.debug("Request to get FranchiseUser : {}", id);
        return franchiseUserRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseUser : {}", id);
        franchiseUserRepository.deleteById(id);
    }
}
