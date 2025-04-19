package com.framasaas.service.mapper;

import com.framasaas.domain.Address;
import com.framasaas.domain.Article;
import com.framasaas.domain.Customer;
import com.framasaas.domain.ServiceOrder;
import com.framasaas.domain.ServiceOrderMaster;
import com.framasaas.service.dto.AddressDTO;
import com.framasaas.service.dto.ArticleDTO;
import com.framasaas.service.dto.CustomerDTO;
import com.framasaas.service.dto.ServiceOrderDTO;
import com.framasaas.service.dto.ServiceOrderMasterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceOrder} and its DTO {@link ServiceOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceOrderMapper extends EntityMapper<ServiceOrderDTO, ServiceOrder> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "serviceMaster", source = "serviceMaster", qualifiedByName = "serviceOrderMasterId")
    @Mapping(target = "article", source = "article", qualifiedByName = "articleId")
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    ServiceOrderDTO toDto(ServiceOrder s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("serviceOrderMasterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceOrderMasterDTO toDtoServiceOrderMasterId(ServiceOrderMaster serviceOrderMaster);

    @Named("articleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArticleDTO toDtoArticleId(Article article);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);
}
