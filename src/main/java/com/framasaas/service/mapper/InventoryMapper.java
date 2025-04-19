package com.framasaas.service.mapper;

import com.framasaas.domain.Inventory;
import com.framasaas.domain.InventoryLocation;
import com.framasaas.domain.Product;
import com.framasaas.service.dto.InventoryDTO;
import com.framasaas.service.dto.InventoryLocationDTO;
import com.framasaas.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inventory} and its DTO {@link InventoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    @Mapping(target = "location", source = "location", qualifiedByName = "inventoryLocationId")
    InventoryDTO toDto(Inventory s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("inventoryLocationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InventoryLocationDTO toDtoInventoryLocationId(InventoryLocation inventoryLocation);
}
