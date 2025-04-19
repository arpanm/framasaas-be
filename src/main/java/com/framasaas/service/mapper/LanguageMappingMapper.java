package com.framasaas.service.mapper;

import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.domain.LanguageMapping;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import com.framasaas.service.dto.LanguageMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LanguageMapping} and its DTO {@link LanguageMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface LanguageMappingMapper extends EntityMapper<LanguageMappingDTO, LanguageMapping> {
    @Mapping(target = "franchiseRule", source = "franchiseRule", qualifiedByName = "franchiseAllocationRuleId")
    @Mapping(target = "fieldAgentSkillRule", source = "fieldAgentSkillRule", qualifiedByName = "fieldAgentSkillRuleId")
    LanguageMappingDTO toDto(LanguageMapping s);

    @Named("franchiseAllocationRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseAllocationRuleDTO toDtoFranchiseAllocationRuleId(FranchiseAllocationRule franchiseAllocationRule);

    @Named("fieldAgentSkillRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldAgentSkillRuleDTO toDtoFieldAgentSkillRuleId(FieldAgentSkillRule fieldAgentSkillRule);
}
