package com.framasaas.service.mapper;

import com.framasaas.domain.FieldAgentSkillRuleSet;
import com.framasaas.service.dto.FieldAgentSkillRuleSetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FieldAgentSkillRuleSet} and its DTO {@link FieldAgentSkillRuleSetDTO}.
 */
@Mapper(componentModel = "spring")
public interface FieldAgentSkillRuleSetMapper extends EntityMapper<FieldAgentSkillRuleSetDTO, FieldAgentSkillRuleSet> {}
