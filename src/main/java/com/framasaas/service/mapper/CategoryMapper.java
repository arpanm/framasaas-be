package com.framasaas.service.mapper;

import com.framasaas.domain.Category;
import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.service.dto.CategoryDTO;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    @Mapping(target = "franchiseRule", source = "franchiseRule", qualifiedByName = "franchiseAllocationRuleId")
    @Mapping(target = "fieldAgentSkillRule", source = "fieldAgentSkillRule", qualifiedByName = "fieldAgentSkillRuleId")
    CategoryDTO toDto(Category s);

    @Named("franchiseAllocationRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseAllocationRuleDTO toDtoFranchiseAllocationRuleId(FranchiseAllocationRule franchiseAllocationRule);

    @Named("fieldAgentSkillRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldAgentSkillRuleDTO toDtoFieldAgentSkillRuleId(FieldAgentSkillRule fieldAgentSkillRule);
}
