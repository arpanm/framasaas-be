package com.framasaas.be.service.impl;

import com.framasaas.be.domain.AdditionalAttributePossibleValue;
import com.framasaas.be.repository.AdditionalAttributePossibleValueRepository;
import com.framasaas.be.service.AdditionalAttributePossibleValueService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.AdditionalAttributePossibleValue}.
 */
@Service
@Transactional
public class AdditionalAttributePossibleValueServiceImpl implements AdditionalAttributePossibleValueService {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalAttributePossibleValueServiceImpl.class);

    private final AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository;

    public AdditionalAttributePossibleValueServiceImpl(
        AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository
    ) {
        this.additionalAttributePossibleValueRepository = additionalAttributePossibleValueRepository;
    }

    @Override
    public AdditionalAttributePossibleValue save(AdditionalAttributePossibleValue additionalAttributePossibleValue) {
        LOG.debug("Request to save AdditionalAttributePossibleValue : {}", additionalAttributePossibleValue);
        return additionalAttributePossibleValueRepository.save(additionalAttributePossibleValue);
    }

    @Override
    public AdditionalAttributePossibleValue update(AdditionalAttributePossibleValue additionalAttributePossibleValue) {
        LOG.debug("Request to update AdditionalAttributePossibleValue : {}", additionalAttributePossibleValue);
        return additionalAttributePossibleValueRepository.save(additionalAttributePossibleValue);
    }

    @Override
    public Optional<AdditionalAttributePossibleValue> partialUpdate(AdditionalAttributePossibleValue additionalAttributePossibleValue) {
        LOG.debug("Request to partially update AdditionalAttributePossibleValue : {}", additionalAttributePossibleValue);

        return additionalAttributePossibleValueRepository
            .findById(additionalAttributePossibleValue.getId())
            .map(existingAdditionalAttributePossibleValue -> {
                if (additionalAttributePossibleValue.getPossibleValue() != null) {
                    existingAdditionalAttributePossibleValue.setPossibleValue(additionalAttributePossibleValue.getPossibleValue());
                }
                if (additionalAttributePossibleValue.getCreateddBy() != null) {
                    existingAdditionalAttributePossibleValue.setCreateddBy(additionalAttributePossibleValue.getCreateddBy());
                }
                if (additionalAttributePossibleValue.getCreatedTime() != null) {
                    existingAdditionalAttributePossibleValue.setCreatedTime(additionalAttributePossibleValue.getCreatedTime());
                }
                if (additionalAttributePossibleValue.getUpdatedBy() != null) {
                    existingAdditionalAttributePossibleValue.setUpdatedBy(additionalAttributePossibleValue.getUpdatedBy());
                }
                if (additionalAttributePossibleValue.getUpdatedTime() != null) {
                    existingAdditionalAttributePossibleValue.setUpdatedTime(additionalAttributePossibleValue.getUpdatedTime());
                }

                return existingAdditionalAttributePossibleValue;
            })
            .map(additionalAttributePossibleValueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdditionalAttributePossibleValue> findAll(Pageable pageable) {
        LOG.debug("Request to get all AdditionalAttributePossibleValues");
        return additionalAttributePossibleValueRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdditionalAttributePossibleValue> findOne(Long id) {
        LOG.debug("Request to get AdditionalAttributePossibleValue : {}", id);
        return additionalAttributePossibleValueRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete AdditionalAttributePossibleValue : {}", id);
        additionalAttributePossibleValueRepository.deleteById(id);
    }
}
