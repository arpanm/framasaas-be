package com.framasaas.service.mapper;

import com.framasaas.domain.Franchise;
import com.framasaas.domain.FranchisePerformanceHistory;
import com.framasaas.service.dto.FranchiseDTO;
import com.framasaas.service.dto.FranchisePerformanceHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FranchisePerformanceHistory} and its DTO {@link FranchisePerformanceHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface FranchisePerformanceHistoryMapper extends EntityMapper<FranchisePerformanceHistoryDTO, FranchisePerformanceHistory> {
    @Mapping(target = "franchise", source = "franchise", qualifiedByName = "franchiseId")
    FranchisePerformanceHistoryDTO toDto(FranchisePerformanceHistory s);

    @Named("franchiseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseDTO toDtoFranchiseId(Franchise franchise);
}
