package com.framasaas.service.mapper;

import com.framasaas.domain.FieldAgentSkillRuleSet;
import com.framasaas.domain.Franchise;
import com.framasaas.domain.FranchiseUser;
import com.framasaas.service.dto.FieldAgentSkillRuleSetDTO;
import com.framasaas.service.dto.FranchiseDTO;
import com.framasaas.service.dto.FranchiseUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FranchiseUser} and its DTO {@link FranchiseUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface FranchiseUserMapper extends EntityMapper<FranchiseUserDTO, FranchiseUser> {
    @Mapping(target = "franchise", source = "franchise", qualifiedByName = "franchiseId")
    @Mapping(target = "skillRuleSet", source = "skillRuleSet", qualifiedByName = "fieldAgentSkillRuleSetId")
    FranchiseUserDTO toDto(FranchiseUser s);

    @Named("franchiseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseDTO toDtoFranchiseId(Franchise franchise);

    @Named("fieldAgentSkillRuleSetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldAgentSkillRuleSetDTO toDtoFieldAgentSkillRuleSetId(FieldAgentSkillRuleSet fieldAgentSkillRuleSet);
}
