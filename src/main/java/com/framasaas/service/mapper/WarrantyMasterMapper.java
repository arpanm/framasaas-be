package com.framasaas.service.mapper;

import com.framasaas.domain.Product;
import com.framasaas.domain.WarrantyMaster;
import com.framasaas.service.dto.ProductDTO;
import com.framasaas.service.dto.WarrantyMasterDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WarrantyMaster} and its DTO {@link WarrantyMasterDTO}.
 */
@Mapper(componentModel = "spring")
public interface WarrantyMasterMapper extends EntityMapper<WarrantyMasterDTO, WarrantyMaster> {
    @Mapping(target = "coveredSpares", source = "coveredSpares", qualifiedByName = "productIdSet")
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    WarrantyMasterDTO toDto(WarrantyMaster s);

    @Mapping(target = "removeCoveredSpare", ignore = true)
    WarrantyMaster toEntity(WarrantyMasterDTO warrantyMasterDTO);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("productIdSet")
    default Set<ProductDTO> toDtoProductIdSet(Set<Product> product) {
        return product.stream().map(this::toDtoProductId).collect(Collectors.toSet());
    }
}
