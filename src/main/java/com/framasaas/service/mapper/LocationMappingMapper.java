package com.framasaas.service.mapper;

import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.domain.LocationMapping;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import com.framasaas.service.dto.LocationMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationMapping} and its DTO {@link LocationMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMappingMapper extends EntityMapper<LocationMappingDTO, LocationMapping> {
    @Mapping(target = "franchiseRule", source = "franchiseRule", qualifiedByName = "franchiseAllocationRuleId")
    @Mapping(target = "fieldAgentSkillRule", source = "fieldAgentSkillRule", qualifiedByName = "fieldAgentSkillRuleId")
    LocationMappingDTO toDto(LocationMapping s);

    @Named("franchiseAllocationRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseAllocationRuleDTO toDtoFranchiseAllocationRuleId(FranchiseAllocationRule franchiseAllocationRule);

    @Named("fieldAgentSkillRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldAgentSkillRuleDTO toDtoFieldAgentSkillRuleId(FieldAgentSkillRule fieldAgentSkillRule);
}
