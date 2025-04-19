package com.framasaas.service.mapper;

import com.framasaas.domain.Franchise;
import com.framasaas.domain.FranchiseStatusHistory;
import com.framasaas.service.dto.FranchiseDTO;
import com.framasaas.service.dto.FranchiseStatusHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FranchiseStatusHistory} and its DTO {@link FranchiseStatusHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface FranchiseStatusHistoryMapper extends EntityMapper<FranchiseStatusHistoryDTO, FranchiseStatusHistory> {
    @Mapping(target = "franchise", source = "franchise", qualifiedByName = "franchiseId")
    FranchiseStatusHistoryDTO toDto(FranchiseStatusHistory s);

    @Named("franchiseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseDTO toDtoFranchiseId(Franchise franchise);
}
