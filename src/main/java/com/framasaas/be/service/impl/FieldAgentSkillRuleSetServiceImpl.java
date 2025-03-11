package com.framasaas.be.service.impl;

import com.framasaas.be.domain.FieldAgentSkillRuleSet;
import com.framasaas.be.repository.FieldAgentSkillRuleSetRepository;
import com.framasaas.be.service.FieldAgentSkillRuleSetService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.FieldAgentSkillRuleSet}.
 */
@Service
@Transactional
public class FieldAgentSkillRuleSetServiceImpl implements FieldAgentSkillRuleSetService {

    private static final Logger LOG = LoggerFactory.getLogger(FieldAgentSkillRuleSetServiceImpl.class);

    private final FieldAgentSkillRuleSetRepository fieldAgentSkillRuleSetRepository;

    public FieldAgentSkillRuleSetServiceImpl(FieldAgentSkillRuleSetRepository fieldAgentSkillRuleSetRepository) {
        this.fieldAgentSkillRuleSetRepository = fieldAgentSkillRuleSetRepository;
    }

    @Override
    public FieldAgentSkillRuleSet save(FieldAgentSkillRuleSet fieldAgentSkillRuleSet) {
        LOG.debug("Request to save FieldAgentSkillRuleSet : {}", fieldAgentSkillRuleSet);
        return fieldAgentSkillRuleSetRepository.save(fieldAgentSkillRuleSet);
    }

    @Override
    public FieldAgentSkillRuleSet update(FieldAgentSkillRuleSet fieldAgentSkillRuleSet) {
        LOG.debug("Request to update FieldAgentSkillRuleSet : {}", fieldAgentSkillRuleSet);
        return fieldAgentSkillRuleSetRepository.save(fieldAgentSkillRuleSet);
    }

    @Override
    public Optional<FieldAgentSkillRuleSet> partialUpdate(FieldAgentSkillRuleSet fieldAgentSkillRuleSet) {
        LOG.debug("Request to partially update FieldAgentSkillRuleSet : {}", fieldAgentSkillRuleSet);

        return fieldAgentSkillRuleSetRepository
            .findById(fieldAgentSkillRuleSet.getId())
            .map(existingFieldAgentSkillRuleSet -> {
                if (fieldAgentSkillRuleSet.getSortType() != null) {
                    existingFieldAgentSkillRuleSet.setSortType(fieldAgentSkillRuleSet.getSortType());
                }
                if (fieldAgentSkillRuleSet.getCreateddBy() != null) {
                    existingFieldAgentSkillRuleSet.setCreateddBy(fieldAgentSkillRuleSet.getCreateddBy());
                }
                if (fieldAgentSkillRuleSet.getCreatedTime() != null) {
                    existingFieldAgentSkillRuleSet.setCreatedTime(fieldAgentSkillRuleSet.getCreatedTime());
                }
                if (fieldAgentSkillRuleSet.getUpdatedBy() != null) {
                    existingFieldAgentSkillRuleSet.setUpdatedBy(fieldAgentSkillRuleSet.getUpdatedBy());
                }
                if (fieldAgentSkillRuleSet.getUpdatedTime() != null) {
                    existingFieldAgentSkillRuleSet.setUpdatedTime(fieldAgentSkillRuleSet.getUpdatedTime());
                }

                return existingFieldAgentSkillRuleSet;
            })
            .map(fieldAgentSkillRuleSetRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldAgentSkillRuleSet> findAll(Pageable pageable) {
        LOG.debug("Request to get all FieldAgentSkillRuleSets");
        return fieldAgentSkillRuleSetRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldAgentSkillRuleSet> findOne(Long id) {
        LOG.debug("Request to get FieldAgentSkillRuleSet : {}", id);
        return fieldAgentSkillRuleSetRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FieldAgentSkillRuleSet : {}", id);
        fieldAgentSkillRuleSetRepository.deleteById(id);
    }
}
