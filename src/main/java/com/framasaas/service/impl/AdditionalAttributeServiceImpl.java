package com.framasaas.service.impl;

import com.framasaas.domain.AdditionalAttribute;
import com.framasaas.repository.AdditionalAttributeRepository;
import com.framasaas.service.AdditionalAttributeService;
import com.framasaas.service.dto.AdditionalAttributeDTO;
import com.framasaas.service.mapper.AdditionalAttributeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.AdditionalAttribute}.
 */
@Service
@Transactional
public class AdditionalAttributeServiceImpl implements AdditionalAttributeService {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalAttributeServiceImpl.class);

    private final AdditionalAttributeRepository additionalAttributeRepository;

    private final AdditionalAttributeMapper additionalAttributeMapper;

    public AdditionalAttributeServiceImpl(
        AdditionalAttributeRepository additionalAttributeRepository,
        AdditionalAttributeMapper additionalAttributeMapper
    ) {
        this.additionalAttributeRepository = additionalAttributeRepository;
        this.additionalAttributeMapper = additionalAttributeMapper;
    }

    @Override
    public AdditionalAttributeDTO save(AdditionalAttributeDTO additionalAttributeDTO) {
        LOG.debug("Request to save AdditionalAttribute : {}", additionalAttributeDTO);
        AdditionalAttribute additionalAttribute = additionalAttributeMapper.toEntity(additionalAttributeDTO);
        additionalAttribute = additionalAttributeRepository.save(additionalAttribute);
        return additionalAttributeMapper.toDto(additionalAttribute);
    }

    @Override
    public AdditionalAttributeDTO update(AdditionalAttributeDTO additionalAttributeDTO) {
        LOG.debug("Request to update AdditionalAttribute : {}", additionalAttributeDTO);
        AdditionalAttribute additionalAttribute = additionalAttributeMapper.toEntity(additionalAttributeDTO);
        additionalAttribute = additionalAttributeRepository.save(additionalAttribute);
        return additionalAttributeMapper.toDto(additionalAttribute);
    }

    @Override
    public Optional<AdditionalAttributeDTO> partialUpdate(AdditionalAttributeDTO additionalAttributeDTO) {
        LOG.debug("Request to partially update AdditionalAttribute : {}", additionalAttributeDTO);

        return additionalAttributeRepository
            .findById(additionalAttributeDTO.getId())
            .map(existingAdditionalAttribute -> {
                additionalAttributeMapper.partialUpdate(existingAdditionalAttribute, additionalAttributeDTO);

                return existingAdditionalAttribute;
            })
            .map(additionalAttributeRepository::save)
            .map(additionalAttributeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdditionalAttributeDTO> findOne(Long id) {
        LOG.debug("Request to get AdditionalAttribute : {}", id);
        return additionalAttributeRepository.findById(id).map(additionalAttributeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete AdditionalAttribute : {}", id);
        additionalAttributeRepository.deleteById(id);
    }
}
