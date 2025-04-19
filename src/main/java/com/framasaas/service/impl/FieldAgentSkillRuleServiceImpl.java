package com.framasaas.service.impl;

import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.repository.FieldAgentSkillRuleRepository;
import com.framasaas.service.FieldAgentSkillRuleService;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import com.framasaas.service.mapper.FieldAgentSkillRuleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.FieldAgentSkillRule}.
 */
@Service
@Transactional
public class FieldAgentSkillRuleServiceImpl implements FieldAgentSkillRuleService {

    private static final Logger LOG = LoggerFactory.getLogger(FieldAgentSkillRuleServiceImpl.class);

    private final FieldAgentSkillRuleRepository fieldAgentSkillRuleRepository;

    private final FieldAgentSkillRuleMapper fieldAgentSkillRuleMapper;

    public FieldAgentSkillRuleServiceImpl(
        FieldAgentSkillRuleRepository fieldAgentSkillRuleRepository,
        FieldAgentSkillRuleMapper fieldAgentSkillRuleMapper
    ) {
        this.fieldAgentSkillRuleRepository = fieldAgentSkillRuleRepository;
        this.fieldAgentSkillRuleMapper = fieldAgentSkillRuleMapper;
    }

    @Override
    public FieldAgentSkillRuleDTO save(FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO) {
        LOG.debug("Request to save FieldAgentSkillRule : {}", fieldAgentSkillRuleDTO);
        FieldAgentSkillRule fieldAgentSkillRule = fieldAgentSkillRuleMapper.toEntity(fieldAgentSkillRuleDTO);
        fieldAgentSkillRule = fieldAgentSkillRuleRepository.save(fieldAgentSkillRule);
        return fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);
    }

    @Override
    public FieldAgentSkillRuleDTO update(FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO) {
        LOG.debug("Request to update FieldAgentSkillRule : {}", fieldAgentSkillRuleDTO);
        FieldAgentSkillRule fieldAgentSkillRule = fieldAgentSkillRuleMapper.toEntity(fieldAgentSkillRuleDTO);
        fieldAgentSkillRule = fieldAgentSkillRuleRepository.save(fieldAgentSkillRule);
        return fieldAgentSkillRuleMapper.toDto(fieldAgentSkillRule);
    }

    @Override
    public Optional<FieldAgentSkillRuleDTO> partialUpdate(FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO) {
        LOG.debug("Request to partially update FieldAgentSkillRule : {}", fieldAgentSkillRuleDTO);

        return fieldAgentSkillRuleRepository
            .findById(fieldAgentSkillRuleDTO.getId())
            .map(existingFieldAgentSkillRule -> {
                fieldAgentSkillRuleMapper.partialUpdate(existingFieldAgentSkillRule, fieldAgentSkillRuleDTO);

                return existingFieldAgentSkillRule;
            })
            .map(fieldAgentSkillRuleRepository::save)
            .map(fieldAgentSkillRuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldAgentSkillRuleDTO> findOne(Long id) {
        LOG.debug("Request to get FieldAgentSkillRule : {}", id);
        return fieldAgentSkillRuleRepository.findById(id).map(fieldAgentSkillRuleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FieldAgentSkillRule : {}", id);
        fieldAgentSkillRuleRepository.deleteById(id);
    }
}
