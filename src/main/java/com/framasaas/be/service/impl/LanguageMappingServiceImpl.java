package com.framasaas.be.service.impl;

import com.framasaas.be.domain.LanguageMapping;
import com.framasaas.be.repository.LanguageMappingRepository;
import com.framasaas.be.service.LanguageMappingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.LanguageMapping}.
 */
@Service
@Transactional
public class LanguageMappingServiceImpl implements LanguageMappingService {

    private static final Logger LOG = LoggerFactory.getLogger(LanguageMappingServiceImpl.class);

    private final LanguageMappingRepository languageMappingRepository;

    public LanguageMappingServiceImpl(LanguageMappingRepository languageMappingRepository) {
        this.languageMappingRepository = languageMappingRepository;
    }

    @Override
    public LanguageMapping save(LanguageMapping languageMapping) {
        LOG.debug("Request to save LanguageMapping : {}", languageMapping);
        return languageMappingRepository.save(languageMapping);
    }

    @Override
    public LanguageMapping update(LanguageMapping languageMapping) {
        LOG.debug("Request to update LanguageMapping : {}", languageMapping);
        return languageMappingRepository.save(languageMapping);
    }

    @Override
    public Optional<LanguageMapping> partialUpdate(LanguageMapping languageMapping) {
        LOG.debug("Request to partially update LanguageMapping : {}", languageMapping);

        return languageMappingRepository
            .findById(languageMapping.getId())
            .map(existingLanguageMapping -> {
                if (languageMapping.getLang() != null) {
                    existingLanguageMapping.setLang(languageMapping.getLang());
                }
                if (languageMapping.getCreateddBy() != null) {
                    existingLanguageMapping.setCreateddBy(languageMapping.getCreateddBy());
                }
                if (languageMapping.getCreatedTime() != null) {
                    existingLanguageMapping.setCreatedTime(languageMapping.getCreatedTime());
                }
                if (languageMapping.getUpdatedBy() != null) {
                    existingLanguageMapping.setUpdatedBy(languageMapping.getUpdatedBy());
                }
                if (languageMapping.getUpdatedTime() != null) {
                    existingLanguageMapping.setUpdatedTime(languageMapping.getUpdatedTime());
                }

                return existingLanguageMapping;
            })
            .map(languageMappingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LanguageMapping> findAll(Pageable pageable) {
        LOG.debug("Request to get all LanguageMappings");
        return languageMappingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LanguageMapping> findOne(Long id) {
        LOG.debug("Request to get LanguageMapping : {}", id);
        return languageMappingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete LanguageMapping : {}", id);
        languageMappingRepository.deleteById(id);
    }
}
