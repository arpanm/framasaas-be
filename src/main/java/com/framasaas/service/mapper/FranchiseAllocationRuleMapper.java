package com.framasaas.service.mapper;

import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FranchiseAllocationRule} and its DTO {@link FranchiseAllocationRuleDTO}.
 */
@Mapper(componentModel = "spring")
public interface FranchiseAllocationRuleMapper extends EntityMapper<FranchiseAllocationRuleDTO, FranchiseAllocationRule> {}
