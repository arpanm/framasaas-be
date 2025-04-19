package com.framasaas.service.mapper;

import com.framasaas.domain.Brand;
import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.service.dto.BrandDTO;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Brand} and its DTO {@link BrandDTO}.
 */
@Mapper(componentModel = "spring")
public interface BrandMapper extends EntityMapper<BrandDTO, Brand> {
    @Mapping(target = "franchiseRule", source = "franchiseRule", qualifiedByName = "franchiseAllocationRuleId")
    @Mapping(target = "fieldAgentSkillRule", source = "fieldAgentSkillRule", qualifiedByName = "fieldAgentSkillRuleId")
    BrandDTO toDto(Brand s);

    @Named("franchiseAllocationRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseAllocationRuleDTO toDtoFranchiseAllocationRuleId(FranchiseAllocationRule franchiseAllocationRule);

    @Named("fieldAgentSkillRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldAgentSkillRuleDTO toDtoFieldAgentSkillRuleId(FieldAgentSkillRule fieldAgentSkillRule);
}
