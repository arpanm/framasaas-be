package com.framasaas.service.impl;

import com.framasaas.domain.LanguageMapping;
import com.framasaas.repository.LanguageMappingRepository;
import com.framasaas.service.LanguageMappingService;
import com.framasaas.service.dto.LanguageMappingDTO;
import com.framasaas.service.mapper.LanguageMappingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.LanguageMapping}.
 */
@Service
@Transactional
public class LanguageMappingServiceImpl implements LanguageMappingService {

    private static final Logger LOG = LoggerFactory.getLogger(LanguageMappingServiceImpl.class);

    private final LanguageMappingRepository languageMappingRepository;

    private final LanguageMappingMapper languageMappingMapper;

    public LanguageMappingServiceImpl(LanguageMappingRepository languageMappingRepository, LanguageMappingMapper languageMappingMapper) {
        this.languageMappingRepository = languageMappingRepository;
        this.languageMappingMapper = languageMappingMapper;
    }

    @Override
    public LanguageMappingDTO save(LanguageMappingDTO languageMappingDTO) {
        LOG.debug("Request to save LanguageMapping : {}", languageMappingDTO);
        LanguageMapping languageMapping = languageMappingMapper.toEntity(languageMappingDTO);
        languageMapping = languageMappingRepository.save(languageMapping);
        return languageMappingMapper.toDto(languageMapping);
    }

    @Override
    public LanguageMappingDTO update(LanguageMappingDTO languageMappingDTO) {
        LOG.debug("Request to update LanguageMapping : {}", languageMappingDTO);
        LanguageMapping languageMapping = languageMappingMapper.toEntity(languageMappingDTO);
        languageMapping = languageMappingRepository.save(languageMapping);
        return languageMappingMapper.toDto(languageMapping);
    }

    @Override
    public Optional<LanguageMappingDTO> partialUpdate(LanguageMappingDTO languageMappingDTO) {
        LOG.debug("Request to partially update LanguageMapping : {}", languageMappingDTO);

        return languageMappingRepository
            .findById(languageMappingDTO.getId())
            .map(existingLanguageMapping -> {
                languageMappingMapper.partialUpdate(existingLanguageMapping, languageMappingDTO);

                return existingLanguageMapping;
            })
            .map(languageMappingRepository::save)
            .map(languageMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LanguageMappingDTO> findOne(Long id) {
        LOG.debug("Request to get LanguageMapping : {}", id);
        return languageMappingRepository.findById(id).map(languageMappingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete LanguageMapping : {}", id);
        languageMappingRepository.deleteById(id);
    }
}
