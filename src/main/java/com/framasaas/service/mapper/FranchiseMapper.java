package com.framasaas.service.mapper;

import com.framasaas.domain.Address;
import com.framasaas.domain.Franchise;
import com.framasaas.domain.FranchiseAllocationRuleSet;
import com.framasaas.service.dto.AddressDTO;
import com.framasaas.service.dto.FranchiseAllocationRuleSetDTO;
import com.framasaas.service.dto.FranchiseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Franchise} and its DTO {@link FranchiseDTO}.
 */
@Mapper(componentModel = "spring")
public interface FranchiseMapper extends EntityMapper<FranchiseDTO, Franchise> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "ruleset", source = "ruleset", qualifiedByName = "franchiseAllocationRuleSetId")
    FranchiseDTO toDto(Franchise s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("franchiseAllocationRuleSetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseAllocationRuleSetDTO toDtoFranchiseAllocationRuleSetId(FranchiseAllocationRuleSet franchiseAllocationRuleSet);
}
