package com.framasaas.be.service.impl;

import com.framasaas.be.domain.Franchise;
import com.framasaas.be.repository.FranchiseRepository;
import com.framasaas.be.service.FranchiseService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.Franchise}.
 */
@Service
@Transactional
public class FranchiseServiceImpl implements FranchiseService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseServiceImpl.class);

    private final FranchiseRepository franchiseRepository;

    public FranchiseServiceImpl(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public Franchise save(Franchise franchise) {
        LOG.debug("Request to save Franchise : {}", franchise);
        return franchiseRepository.save(franchise);
    }

    @Override
    public Franchise update(Franchise franchise) {
        LOG.debug("Request to update Franchise : {}", franchise);
        return franchiseRepository.save(franchise);
    }

    @Override
    public Optional<Franchise> partialUpdate(Franchise franchise) {
        LOG.debug("Request to partially update Franchise : {}", franchise);

        return franchiseRepository
            .findById(franchise.getId())
            .map(existingFranchise -> {
                if (franchise.getFranchiseName() != null) {
                    existingFranchise.setFranchiseName(franchise.getFranchiseName());
                }
                if (franchise.getOwner() != null) {
                    existingFranchise.setOwner(franchise.getOwner());
                }
                if (franchise.getEmail() != null) {
                    existingFranchise.setEmail(franchise.getEmail());
                }
                if (franchise.getContact() != null) {
                    existingFranchise.setContact(franchise.getContact());
                }
                if (franchise.getFranchiseStatus() != null) {
                    existingFranchise.setFranchiseStatus(franchise.getFranchiseStatus());
                }
                if (franchise.getGstNumber() != null) {
                    existingFranchise.setGstNumber(franchise.getGstNumber());
                }
                if (franchise.getRegistrationNumber() != null) {
                    existingFranchise.setRegistrationNumber(franchise.getRegistrationNumber());
                }
                if (franchise.getPerformanceScore() != null) {
                    existingFranchise.setPerformanceScore(franchise.getPerformanceScore());
                }

                return existingFranchise;
            })
            .map(franchiseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Franchise> findAll(Pageable pageable) {
        LOG.debug("Request to get all Franchises");
        return franchiseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Franchise> findOne(Long id) {
        LOG.debug("Request to get Franchise : {}", id);
        return franchiseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Franchise : {}", id);
        franchiseRepository.deleteById(id);
    }
}
