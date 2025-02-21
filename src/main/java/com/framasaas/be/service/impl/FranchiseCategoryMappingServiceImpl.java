package com.framasaas.be.service.impl;

import com.framasaas.be.domain.FranchiseCategoryMapping;
import com.framasaas.be.repository.FranchiseCategoryMappingRepository;
import com.framasaas.be.service.FranchiseCategoryMappingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.FranchiseCategoryMapping}.
 */
@Service
@Transactional
public class FranchiseCategoryMappingServiceImpl implements FranchiseCategoryMappingService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseCategoryMappingServiceImpl.class);

    private final FranchiseCategoryMappingRepository franchiseCategoryMappingRepository;

    public FranchiseCategoryMappingServiceImpl(FranchiseCategoryMappingRepository franchiseCategoryMappingRepository) {
        this.franchiseCategoryMappingRepository = franchiseCategoryMappingRepository;
    }

    @Override
    public FranchiseCategoryMapping save(FranchiseCategoryMapping franchiseCategoryMapping) {
        LOG.debug("Request to save FranchiseCategoryMapping : {}", franchiseCategoryMapping);
        return franchiseCategoryMappingRepository.save(franchiseCategoryMapping);
    }

    @Override
    public FranchiseCategoryMapping update(FranchiseCategoryMapping franchiseCategoryMapping) {
        LOG.debug("Request to update FranchiseCategoryMapping : {}", franchiseCategoryMapping);
        return franchiseCategoryMappingRepository.save(franchiseCategoryMapping);
    }

    @Override
    public Optional<FranchiseCategoryMapping> partialUpdate(FranchiseCategoryMapping franchiseCategoryMapping) {
        LOG.debug("Request to partially update FranchiseCategoryMapping : {}", franchiseCategoryMapping);

        return franchiseCategoryMappingRepository
            .findById(franchiseCategoryMapping.getId())
            .map(existingFranchiseCategoryMapping -> {
                if (franchiseCategoryMapping.getServiceCategory() != null) {
                    existingFranchiseCategoryMapping.setServiceCategory(franchiseCategoryMapping.getServiceCategory());
                }
                if (franchiseCategoryMapping.getCreateddBy() != null) {
                    existingFranchiseCategoryMapping.setCreateddBy(franchiseCategoryMapping.getCreateddBy());
                }
                if (franchiseCategoryMapping.getCreatedTime() != null) {
                    existingFranchiseCategoryMapping.setCreatedTime(franchiseCategoryMapping.getCreatedTime());
                }
                if (franchiseCategoryMapping.getUpdatedBy() != null) {
                    existingFranchiseCategoryMapping.setUpdatedBy(franchiseCategoryMapping.getUpdatedBy());
                }
                if (franchiseCategoryMapping.getUpdatedTime() != null) {
                    existingFranchiseCategoryMapping.setUpdatedTime(franchiseCategoryMapping.getUpdatedTime());
                }

                return existingFranchiseCategoryMapping;
            })
            .map(franchiseCategoryMappingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FranchiseCategoryMapping> findAll(Pageable pageable) {
        LOG.debug("Request to get all FranchiseCategoryMappings");
        return franchiseCategoryMappingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseCategoryMapping> findOne(Long id) {
        LOG.debug("Request to get FranchiseCategoryMapping : {}", id);
        return franchiseCategoryMappingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseCategoryMapping : {}", id);
        franchiseCategoryMappingRepository.deleteById(id);
    }
}
