package com.framasaas.service.mapper;

import com.framasaas.domain.WarrantyMaster;
import com.framasaas.domain.WarrantyMasterPriceHistory;
import com.framasaas.service.dto.WarrantyMasterDTO;
import com.framasaas.service.dto.WarrantyMasterPriceHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WarrantyMasterPriceHistory} and its DTO {@link WarrantyMasterPriceHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface WarrantyMasterPriceHistoryMapper extends EntityMapper<WarrantyMasterPriceHistoryDTO, WarrantyMasterPriceHistory> {
    @Mapping(target = "warrantyMaster", source = "warrantyMaster", qualifiedByName = "warrantyMasterId")
    WarrantyMasterPriceHistoryDTO toDto(WarrantyMasterPriceHistory s);

    @Named("warrantyMasterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WarrantyMasterDTO toDtoWarrantyMasterId(WarrantyMaster warrantyMaster);
}
