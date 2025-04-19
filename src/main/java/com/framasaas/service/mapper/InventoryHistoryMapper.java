package com.framasaas.service.mapper;

import com.framasaas.domain.InventoryHistory;
import com.framasaas.service.dto.InventoryHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InventoryHistory} and its DTO {@link InventoryHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface InventoryHistoryMapper extends EntityMapper<InventoryHistoryDTO, InventoryHistory> {}
