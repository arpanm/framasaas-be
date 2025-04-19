package com.framasaas.service.impl;

import com.framasaas.domain.FranchiseBrandMapping;
import com.framasaas.repository.FranchiseBrandMappingRepository;
import com.framasaas.service.FranchiseBrandMappingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.FranchiseBrandMapping}.
 */
@Service
@Transactional
public class FranchiseBrandMappingServiceImpl implements FranchiseBrandMappingService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseBrandMappingServiceImpl.class);

    private final FranchiseBrandMappingRepository franchiseBrandMappingRepository;

    public FranchiseBrandMappingServiceImpl(FranchiseBrandMappingRepository franchiseBrandMappingRepository) {
        this.franchiseBrandMappingRepository = franchiseBrandMappingRepository;
    }

    @Override
    public FranchiseBrandMapping save(FranchiseBrandMapping franchiseBrandMapping) {
        LOG.debug("Request to save FranchiseBrandMapping : {}", franchiseBrandMapping);
        return franchiseBrandMappingRepository.save(franchiseBrandMapping);
    }

    @Override
    public FranchiseBrandMapping update(FranchiseBrandMapping franchiseBrandMapping) {
        LOG.debug("Request to update FranchiseBrandMapping : {}", franchiseBrandMapping);
        return franchiseBrandMappingRepository.save(franchiseBrandMapping);
    }

    @Override
    public Optional<FranchiseBrandMapping> partialUpdate(FranchiseBrandMapping franchiseBrandMapping) {
        LOG.debug("Request to partially update FranchiseBrandMapping : {}", franchiseBrandMapping);

        return franchiseBrandMappingRepository
            .findById(franchiseBrandMapping.getId())
            .map(existingFranchiseBrandMapping -> {
                if (franchiseBrandMapping.getBrand() != null) {
                    existingFranchiseBrandMapping.setBrand(franchiseBrandMapping.getBrand());
                }
                if (franchiseBrandMapping.getCreateddBy() != null) {
                    existingFranchiseBrandMapping.setCreateddBy(franchiseBrandMapping.getCreateddBy());
                }
                if (franchiseBrandMapping.getCreatedTime() != null) {
                    existingFranchiseBrandMapping.setCreatedTime(franchiseBrandMapping.getCreatedTime());
                }
                if (franchiseBrandMapping.getUpdatedBy() != null) {
                    existingFranchiseBrandMapping.setUpdatedBy(franchiseBrandMapping.getUpdatedBy());
                }
                if (franchiseBrandMapping.getUpdatedTime() != null) {
                    existingFranchiseBrandMapping.setUpdatedTime(franchiseBrandMapping.getUpdatedTime());
                }

                return existingFranchiseBrandMapping;
            })
            .map(franchiseBrandMappingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FranchiseBrandMapping> findAll(Pageable pageable) {
        LOG.debug("Request to get all FranchiseBrandMappings");
        return franchiseBrandMappingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseBrandMapping> findOne(Long id) {
        LOG.debug("Request to get FranchiseBrandMapping : {}", id);
        return franchiseBrandMappingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseBrandMapping : {}", id);
        franchiseBrandMappingRepository.deleteById(id);
    }
}
