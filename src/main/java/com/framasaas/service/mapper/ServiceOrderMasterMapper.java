package com.framasaas.service.mapper;

import com.framasaas.domain.Product;
import com.framasaas.domain.ServiceOrderMaster;
import com.framasaas.service.dto.ProductDTO;
import com.framasaas.service.dto.ServiceOrderMasterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceOrderMaster} and its DTO {@link ServiceOrderMasterDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceOrderMasterMapper extends EntityMapper<ServiceOrderMasterDTO, ServiceOrderMaster> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    ServiceOrderMasterDTO toDto(ServiceOrderMaster s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
