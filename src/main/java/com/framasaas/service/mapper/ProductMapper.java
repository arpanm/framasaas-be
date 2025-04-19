package com.framasaas.service.mapper;

import com.framasaas.domain.Brand;
import com.framasaas.domain.Category;
import com.framasaas.domain.Hsn;
import com.framasaas.domain.Product;
import com.framasaas.domain.WarrantyMaster;
import com.framasaas.service.dto.BrandDTO;
import com.framasaas.service.dto.CategoryDTO;
import com.framasaas.service.dto.HsnDTO;
import com.framasaas.service.dto.ProductDTO;
import com.framasaas.service.dto.WarrantyMasterDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "brand", source = "brand", qualifiedByName = "brandId")
    @Mapping(target = "hsn", source = "hsn", qualifiedByName = "hsnId")
    @Mapping(target = "coveredUnderWarranties", source = "coveredUnderWarranties", qualifiedByName = "warrantyMasterIdSet")
    ProductDTO toDto(Product s);

    @Mapping(target = "coveredUnderWarranties", ignore = true)
    @Mapping(target = "removeCoveredUnderWarranty", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @Named("warrantyMasterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WarrantyMasterDTO toDtoWarrantyMasterId(WarrantyMaster warrantyMaster);

    @Named("warrantyMasterIdSet")
    default Set<WarrantyMasterDTO> toDtoWarrantyMasterIdSet(Set<WarrantyMaster> warrantyMaster) {
        return warrantyMaster.stream().map(this::toDtoWarrantyMasterId).collect(Collectors.toSet());
    }

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("brandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BrandDTO toDtoBrandId(Brand brand);

    @Named("hsnId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HsnDTO toDtoHsnId(Hsn hsn);
}
