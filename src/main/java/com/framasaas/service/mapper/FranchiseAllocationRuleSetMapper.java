package com.framasaas.service.mapper;

import com.framasaas.domain.FranchiseAllocationRuleSet;
import com.framasaas.service.dto.FranchiseAllocationRuleSetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FranchiseAllocationRuleSet} and its DTO {@link FranchiseAllocationRuleSetDTO}.
 */
@Mapper(componentModel = "spring")
public interface FranchiseAllocationRuleSetMapper extends EntityMapper<FranchiseAllocationRuleSetDTO, FranchiseAllocationRuleSet> {}
