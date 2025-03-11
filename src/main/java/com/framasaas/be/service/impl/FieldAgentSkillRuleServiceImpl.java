package com.framasaas.be.service.impl;

import com.framasaas.be.domain.FieldAgentSkillRule;
import com.framasaas.be.repository.FieldAgentSkillRuleRepository;
import com.framasaas.be.service.FieldAgentSkillRuleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.FieldAgentSkillRule}.
 */
@Service
@Transactional
public class FieldAgentSkillRuleServiceImpl implements FieldAgentSkillRuleService {

    private static final Logger LOG = LoggerFactory.getLogger(FieldAgentSkillRuleServiceImpl.class);

    private final FieldAgentSkillRuleRepository fieldAgentSkillRuleRepository;

    public FieldAgentSkillRuleServiceImpl(FieldAgentSkillRuleRepository fieldAgentSkillRuleRepository) {
        this.fieldAgentSkillRuleRepository = fieldAgentSkillRuleRepository;
    }

    @Override
    public FieldAgentSkillRule save(FieldAgentSkillRule fieldAgentSkillRule) {
        LOG.debug("Request to save FieldAgentSkillRule : {}", fieldAgentSkillRule);
        return fieldAgentSkillRuleRepository.save(fieldAgentSkillRule);
    }

    @Override
    public FieldAgentSkillRule update(FieldAgentSkillRule fieldAgentSkillRule) {
        LOG.debug("Request to update FieldAgentSkillRule : {}", fieldAgentSkillRule);
        return fieldAgentSkillRuleRepository.save(fieldAgentSkillRule);
    }

    @Override
    public Optional<FieldAgentSkillRule> partialUpdate(FieldAgentSkillRule fieldAgentSkillRule) {
        LOG.debug("Request to partially update FieldAgentSkillRule : {}", fieldAgentSkillRule);

        return fieldAgentSkillRuleRepository
            .findById(fieldAgentSkillRule.getId())
            .map(existingFieldAgentSkillRule -> {
                if (fieldAgentSkillRule.getSkillType() != null) {
                    existingFieldAgentSkillRule.setSkillType(fieldAgentSkillRule.getSkillType());
                }
                if (fieldAgentSkillRule.getJoinType() != null) {
                    existingFieldAgentSkillRule.setJoinType(fieldAgentSkillRule.getJoinType());
                }
                if (fieldAgentSkillRule.getCreateddBy() != null) {
                    existingFieldAgentSkillRule.setCreateddBy(fieldAgentSkillRule.getCreateddBy());
                }
                if (fieldAgentSkillRule.getCreatedTime() != null) {
                    existingFieldAgentSkillRule.setCreatedTime(fieldAgentSkillRule.getCreatedTime());
                }
                if (fieldAgentSkillRule.getUpdatedBy() != null) {
                    existingFieldAgentSkillRule.setUpdatedBy(fieldAgentSkillRule.getUpdatedBy());
                }
                if (fieldAgentSkillRule.getUpdatedTime() != null) {
                    existingFieldAgentSkillRule.setUpdatedTime(fieldAgentSkillRule.getUpdatedTime());
                }

                return existingFieldAgentSkillRule;
            })
            .map(fieldAgentSkillRuleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldAgentSkillRule> findAll(Pageable pageable) {
        LOG.debug("Request to get all FieldAgentSkillRules");
        return fieldAgentSkillRuleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldAgentSkillRule> findOne(Long id) {
        LOG.debug("Request to get FieldAgentSkillRule : {}", id);
        return fieldAgentSkillRuleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FieldAgentSkillRule : {}", id);
        fieldAgentSkillRuleRepository.deleteById(id);
    }
}
