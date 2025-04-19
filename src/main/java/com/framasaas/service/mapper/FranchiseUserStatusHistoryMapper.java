package com.framasaas.service.mapper;

import com.framasaas.domain.FranchiseUser;
import com.framasaas.domain.FranchiseUserStatusHistory;
import com.framasaas.service.dto.FranchiseUserDTO;
import com.framasaas.service.dto.FranchiseUserStatusHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FranchiseUserStatusHistory} and its DTO {@link FranchiseUserStatusHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface FranchiseUserStatusHistoryMapper extends EntityMapper<FranchiseUserStatusHistoryDTO, FranchiseUserStatusHistory> {
    @Mapping(target = "franchiseUser", source = "franchiseUser", qualifiedByName = "franchiseUserId")
    FranchiseUserStatusHistoryDTO toDto(FranchiseUserStatusHistory s);

    @Named("franchiseUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseUserDTO toDtoFranchiseUserId(FranchiseUser franchiseUser);
}
