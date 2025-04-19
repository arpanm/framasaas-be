package com.framasaas.service.mapper;

import com.framasaas.domain.Product;
import com.framasaas.domain.ServiceOrder;
import com.framasaas.domain.ServiceOrderSpare;
import com.framasaas.service.dto.ProductDTO;
import com.framasaas.service.dto.ServiceOrderDTO;
import com.framasaas.service.dto.ServiceOrderSpareDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceOrderSpare} and its DTO {@link ServiceOrderSpareDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceOrderSpareMapper extends EntityMapper<ServiceOrderSpareDTO, ServiceOrderSpare> {
    @Mapping(target = "serviceOrder", source = "serviceOrder", qualifiedByName = "serviceOrderId")
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    ServiceOrderSpareDTO toDto(ServiceOrderSpare s);

    @Named("serviceOrderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceOrderDTO toDtoServiceOrderId(ServiceOrder serviceOrder);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
