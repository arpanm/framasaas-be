package com.framasaas.service.impl;

import com.framasaas.domain.FieldAgentSkillRuleSet;
import com.framasaas.repository.FieldAgentSkillRuleSetRepository;
import com.framasaas.service.FieldAgentSkillRuleSetService;
import com.framasaas.service.dto.FieldAgentSkillRuleSetDTO;
import com.framasaas.service.mapper.FieldAgentSkillRuleSetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.FieldAgentSkillRuleSet}.
 */
@Service
@Transactional
public class FieldAgentSkillRuleSetServiceImpl implements FieldAgentSkillRuleSetService {

    private static final Logger LOG = LoggerFactory.getLogger(FieldAgentSkillRuleSetServiceImpl.class);

    private final FieldAgentSkillRuleSetRepository fieldAgentSkillRuleSetRepository;

    private final FieldAgentSkillRuleSetMapper fieldAgentSkillRuleSetMapper;

    public FieldAgentSkillRuleSetServiceImpl(
        FieldAgentSkillRuleSetRepository fieldAgentSkillRuleSetRepository,
        FieldAgentSkillRuleSetMapper fieldAgentSkillRuleSetMapper
    ) {
        this.fieldAgentSkillRuleSetRepository = fieldAgentSkillRuleSetRepository;
        this.fieldAgentSkillRuleSetMapper = fieldAgentSkillRuleSetMapper;
    }

    @Override
    public FieldAgentSkillRuleSetDTO save(FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO) {
        LOG.debug("Request to save FieldAgentSkillRuleSet : {}", fieldAgentSkillRuleSetDTO);
        FieldAgentSkillRuleSet fieldAgentSkillRuleSet = fieldAgentSkillRuleSetMapper.toEntity(fieldAgentSkillRuleSetDTO);
        fieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.save(fieldAgentSkillRuleSet);
        return fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);
    }

    @Override
    public FieldAgentSkillRuleSetDTO update(FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO) {
        LOG.debug("Request to update FieldAgentSkillRuleSet : {}", fieldAgentSkillRuleSetDTO);
        FieldAgentSkillRuleSet fieldAgentSkillRuleSet = fieldAgentSkillRuleSetMapper.toEntity(fieldAgentSkillRuleSetDTO);
        fieldAgentSkillRuleSet = fieldAgentSkillRuleSetRepository.save(fieldAgentSkillRuleSet);
        return fieldAgentSkillRuleSetMapper.toDto(fieldAgentSkillRuleSet);
    }

    @Override
    public Optional<FieldAgentSkillRuleSetDTO> partialUpdate(FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO) {
        LOG.debug("Request to partially update FieldAgentSkillRuleSet : {}", fieldAgentSkillRuleSetDTO);

        return fieldAgentSkillRuleSetRepository
            .findById(fieldAgentSkillRuleSetDTO.getId())
            .map(existingFieldAgentSkillRuleSet -> {
                fieldAgentSkillRuleSetMapper.partialUpdate(existingFieldAgentSkillRuleSet, fieldAgentSkillRuleSetDTO);

                return existingFieldAgentSkillRuleSet;
            })
            .map(fieldAgentSkillRuleSetRepository::save)
            .map(fieldAgentSkillRuleSetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldAgentSkillRuleSetDTO> findOne(Long id) {
        LOG.debug("Request to get FieldAgentSkillRuleSet : {}", id);
        return fieldAgentSkillRuleSetRepository.findById(id).map(fieldAgentSkillRuleSetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FieldAgentSkillRuleSet : {}", id);
        fieldAgentSkillRuleSetRepository.deleteById(id);
    }
}
