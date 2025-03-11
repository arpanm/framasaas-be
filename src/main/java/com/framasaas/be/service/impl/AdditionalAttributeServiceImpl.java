package com.framasaas.be.service.impl;

import com.framasaas.be.domain.AdditionalAttribute;
import com.framasaas.be.repository.AdditionalAttributeRepository;
import com.framasaas.be.service.AdditionalAttributeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.AdditionalAttribute}.
 */
@Service
@Transactional
public class AdditionalAttributeServiceImpl implements AdditionalAttributeService {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalAttributeServiceImpl.class);

    private final AdditionalAttributeRepository additionalAttributeRepository;

    public AdditionalAttributeServiceImpl(AdditionalAttributeRepository additionalAttributeRepository) {
        this.additionalAttributeRepository = additionalAttributeRepository;
    }

    @Override
    public AdditionalAttribute save(AdditionalAttribute additionalAttribute) {
        LOG.debug("Request to save AdditionalAttribute : {}", additionalAttribute);
        return additionalAttributeRepository.save(additionalAttribute);
    }

    @Override
    public AdditionalAttribute update(AdditionalAttribute additionalAttribute) {
        LOG.debug("Request to update AdditionalAttribute : {}", additionalAttribute);
        return additionalAttributeRepository.save(additionalAttribute);
    }

    @Override
    public Optional<AdditionalAttribute> partialUpdate(AdditionalAttribute additionalAttribute) {
        LOG.debug("Request to partially update AdditionalAttribute : {}", additionalAttribute);

        return additionalAttributeRepository
            .findById(additionalAttribute.getId())
            .map(existingAdditionalAttribute -> {
                if (additionalAttribute.getAttributeName() != null) {
                    existingAdditionalAttribute.setAttributeName(additionalAttribute.getAttributeName());
                }
                if (additionalAttribute.getAttributeValue() != null) {
                    existingAdditionalAttribute.setAttributeValue(additionalAttribute.getAttributeValue());
                }
                if (additionalAttribute.getAttributeType() != null) {
                    existingAdditionalAttribute.setAttributeType(additionalAttribute.getAttributeType());
                }
                if (additionalAttribute.getCreateddBy() != null) {
                    existingAdditionalAttribute.setCreateddBy(additionalAttribute.getCreateddBy());
                }
                if (additionalAttribute.getCreatedTime() != null) {
                    existingAdditionalAttribute.setCreatedTime(additionalAttribute.getCreatedTime());
                }
                if (additionalAttribute.getUpdatedBy() != null) {
                    existingAdditionalAttribute.setUpdatedBy(additionalAttribute.getUpdatedBy());
                }
                if (additionalAttribute.getUpdatedTime() != null) {
                    existingAdditionalAttribute.setUpdatedTime(additionalAttribute.getUpdatedTime());
                }

                return existingAdditionalAttribute;
            })
            .map(additionalAttributeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdditionalAttribute> findAll(Pageable pageable) {
        LOG.debug("Request to get all AdditionalAttributes");
        return additionalAttributeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdditionalAttribute> findOne(Long id) {
        LOG.debug("Request to get AdditionalAttribute : {}", id);
        return additionalAttributeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete AdditionalAttribute : {}", id);
        additionalAttributeRepository.deleteById(id);
    }
}
