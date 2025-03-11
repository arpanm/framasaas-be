package com.framasaas.be.service.impl;

import com.framasaas.be.domain.Brand;
import com.framasaas.be.repository.BrandRepository;
import com.framasaas.be.service.BrandService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.Brand}.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private static final Logger LOG = LoggerFactory.getLogger(BrandServiceImpl.class);

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand save(Brand brand) {
        LOG.debug("Request to save Brand : {}", brand);
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Brand brand) {
        LOG.debug("Request to update Brand : {}", brand);
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> partialUpdate(Brand brand) {
        LOG.debug("Request to partially update Brand : {}", brand);

        return brandRepository
            .findById(brand.getId())
            .map(existingBrand -> {
                if (brand.getBrandName() != null) {
                    existingBrand.setBrandName(brand.getBrandName());
                }
                if (brand.getLogoPath() != null) {
                    existingBrand.setLogoPath(brand.getLogoPath());
                }
                if (brand.getVendorBrandId() != null) {
                    existingBrand.setVendorBrandId(brand.getVendorBrandId());
                }
                if (brand.getDescription() != null) {
                    existingBrand.setDescription(brand.getDescription());
                }
                if (brand.getCreateddBy() != null) {
                    existingBrand.setCreateddBy(brand.getCreateddBy());
                }
                if (brand.getCreatedTime() != null) {
                    existingBrand.setCreatedTime(brand.getCreatedTime());
                }
                if (brand.getUpdatedBy() != null) {
                    existingBrand.setUpdatedBy(brand.getUpdatedBy());
                }
                if (brand.getUpdatedTime() != null) {
                    existingBrand.setUpdatedTime(brand.getUpdatedTime());
                }

                return existingBrand;
            })
            .map(brandRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Brand> findAll(Pageable pageable) {
        LOG.debug("Request to get all Brands");
        return brandRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Brand> findOne(Long id) {
        LOG.debug("Request to get Brand : {}", id);
        return brandRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Brand : {}", id);
        brandRepository.deleteById(id);
    }
}
