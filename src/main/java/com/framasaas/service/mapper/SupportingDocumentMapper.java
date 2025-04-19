package com.framasaas.service.mapper;

import com.framasaas.domain.Article;
import com.framasaas.domain.ArticleWarrantyDetails;
import com.framasaas.domain.Franchise;
import com.framasaas.domain.ServiceOrder;
import com.framasaas.domain.SupportingDocument;
import com.framasaas.service.dto.ArticleDTO;
import com.framasaas.service.dto.ArticleWarrantyDetailsDTO;
import com.framasaas.service.dto.FranchiseDTO;
import com.framasaas.service.dto.ServiceOrderDTO;
import com.framasaas.service.dto.SupportingDocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupportingDocument} and its DTO {@link SupportingDocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface SupportingDocumentMapper extends EntityMapper<SupportingDocumentDTO, SupportingDocument> {
    @Mapping(target = "franchise", source = "franchise", qualifiedByName = "franchiseId")
    @Mapping(target = "article", source = "article", qualifiedByName = "articleId")
    @Mapping(target = "articleWarranty", source = "articleWarranty", qualifiedByName = "articleWarrantyDetailsId")
    @Mapping(target = "serviceOrder", source = "serviceOrder", qualifiedByName = "serviceOrderId")
    SupportingDocumentDTO toDto(SupportingDocument s);

    @Named("franchiseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseDTO toDtoFranchiseId(Franchise franchise);

    @Named("articleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArticleDTO toDtoArticleId(Article article);

    @Named("articleWarrantyDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArticleWarrantyDetailsDTO toDtoArticleWarrantyDetailsId(ArticleWarrantyDetails articleWarrantyDetails);

    @Named("serviceOrderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceOrderDTO toDtoServiceOrderId(ServiceOrder serviceOrder);
}
