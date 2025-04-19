package com.framasaas.service.impl;

import com.framasaas.domain.AdditionalAttributePossibleValue;
import com.framasaas.repository.AdditionalAttributePossibleValueRepository;
import com.framasaas.service.AdditionalAttributePossibleValueService;
import com.framasaas.service.dto.AdditionalAttributePossibleValueDTO;
import com.framasaas.service.mapper.AdditionalAttributePossibleValueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.AdditionalAttributePossibleValue}.
 */
@Service
@Transactional
public class AdditionalAttributePossibleValueServiceImpl implements AdditionalAttributePossibleValueService {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalAttributePossibleValueServiceImpl.class);

    private final AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository;

    private final AdditionalAttributePossibleValueMapper additionalAttributePossibleValueMapper;

    public AdditionalAttributePossibleValueServiceImpl(
        AdditionalAttributePossibleValueRepository additionalAttributePossibleValueRepository,
        AdditionalAttributePossibleValueMapper additionalAttributePossibleValueMapper
    ) {
        this.additionalAttributePossibleValueRepository = additionalAttributePossibleValueRepository;
        this.additionalAttributePossibleValueMapper = additionalAttributePossibleValueMapper;
    }

    @Override
    public AdditionalAttributePossibleValueDTO save(AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO) {
        LOG.debug("Request to save AdditionalAttributePossibleValue : {}", additionalAttributePossibleValueDTO);
        AdditionalAttributePossibleValue additionalAttributePossibleValue = additionalAttributePossibleValueMapper.toEntity(
            additionalAttributePossibleValueDTO
        );
        additionalAttributePossibleValue = additionalAttributePossibleValueRepository.save(additionalAttributePossibleValue);
        return additionalAttributePossibleValueMapper.toDto(additionalAttributePossibleValue);
    }

    @Override
    public AdditionalAttributePossibleValueDTO update(AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO) {
        LOG.debug("Request to update AdditionalAttributePossibleValue : {}", additionalAttributePossibleValueDTO);
        AdditionalAttributePossibleValue additionalAttributePossibleValue = additionalAttributePossibleValueMapper.toEntity(
            additionalAttributePossibleValueDTO
        );
        additionalAttributePossibleValue = additionalAttributePossibleValueRepository.save(additionalAttributePossibleValue);
        return additionalAttributePossibleValueMapper.toDto(additionalAttributePossibleValue);
    }

    @Override
    public Optional<AdditionalAttributePossibleValueDTO> partialUpdate(
        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO
    ) {
        LOG.debug("Request to partially update AdditionalAttributePossibleValue : {}", additionalAttributePossibleValueDTO);

        return additionalAttributePossibleValueRepository
            .findById(additionalAttributePossibleValueDTO.getId())
            .map(existingAdditionalAttributePossibleValue -> {
                additionalAttributePossibleValueMapper.partialUpdate(
                    existingAdditionalAttributePossibleValue,
                    additionalAttributePossibleValueDTO
                );

                return existingAdditionalAttributePossibleValue;
            })
            .map(additionalAttributePossibleValueRepository::save)
            .map(additionalAttributePossibleValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdditionalAttributePossibleValueDTO> findOne(Long id) {
        LOG.debug("Request to get AdditionalAttributePossibleValue : {}", id);
        return additionalAttributePossibleValueRepository.findById(id).map(additionalAttributePossibleValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete AdditionalAttributePossibleValue : {}", id);
        additionalAttributePossibleValueRepository.deleteById(id);
    }
}
