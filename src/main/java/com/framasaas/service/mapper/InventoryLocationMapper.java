package com.framasaas.service.mapper;

import com.framasaas.domain.InventoryLocation;
import com.framasaas.service.dto.InventoryLocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InventoryLocation} and its DTO {@link InventoryLocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface InventoryLocationMapper extends EntityMapper<InventoryLocationDTO, InventoryLocation> {}
