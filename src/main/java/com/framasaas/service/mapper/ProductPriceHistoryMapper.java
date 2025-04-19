package com.framasaas.service.mapper;

import com.framasaas.domain.Product;
import com.framasaas.domain.ProductPriceHistory;
import com.framasaas.service.dto.ProductDTO;
import com.framasaas.service.dto.ProductPriceHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductPriceHistory} and its DTO {@link ProductPriceHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductPriceHistoryMapper extends EntityMapper<ProductPriceHistoryDTO, ProductPriceHistory> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    ProductPriceHistoryDTO toDto(ProductPriceHistory s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
