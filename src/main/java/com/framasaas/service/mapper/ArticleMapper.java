package com.framasaas.service.mapper;

import com.framasaas.domain.Article;
import com.framasaas.domain.Customer;
import com.framasaas.domain.Product;
import com.framasaas.service.dto.ArticleDTO;
import com.framasaas.service.dto.CustomerDTO;
import com.framasaas.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Article} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    ArticleDTO toDto(Article s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
