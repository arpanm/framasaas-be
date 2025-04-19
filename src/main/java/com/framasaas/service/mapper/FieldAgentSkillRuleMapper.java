package com.framasaas.service.mapper;

import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FieldAgentSkillRuleSet;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import com.framasaas.service.dto.FieldAgentSkillRuleSetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FieldAgentSkillRule} and its DTO {@link FieldAgentSkillRuleDTO}.
 */
@Mapper(componentModel = "spring")
public interface FieldAgentSkillRuleMapper extends EntityMapper<FieldAgentSkillRuleDTO, FieldAgentSkillRule> {
    @Mapping(target = "fieldAgentSkillRuleSet", source = "fieldAgentSkillRuleSet", qualifiedByName = "fieldAgentSkillRuleSetId")
    FieldAgentSkillRuleDTO toDto(FieldAgentSkillRule s);

    @Named("fieldAgentSkillRuleSetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldAgentSkillRuleSetDTO toDtoFieldAgentSkillRuleSetId(FieldAgentSkillRuleSet fieldAgentSkillRuleSet);
}
