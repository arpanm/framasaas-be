package com.framasaas.service.mapper;

import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.domain.Pincode;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import com.framasaas.service.dto.PincodeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pincode} and its DTO {@link PincodeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PincodeMapper extends EntityMapper<PincodeDTO, Pincode> {
    @Mapping(target = "franchiseRule", source = "franchiseRule", qualifiedByName = "franchiseAllocationRuleId")
    @Mapping(target = "fieldAgentSkillRule", source = "fieldAgentSkillRule", qualifiedByName = "fieldAgentSkillRuleId")
    PincodeDTO toDto(Pincode s);

    @Named("franchiseAllocationRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseAllocationRuleDTO toDtoFranchiseAllocationRuleId(FranchiseAllocationRule franchiseAllocationRule);

    @Named("fieldAgentSkillRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldAgentSkillRuleDTO toDtoFieldAgentSkillRuleId(FieldAgentSkillRule fieldAgentSkillRule);
}
