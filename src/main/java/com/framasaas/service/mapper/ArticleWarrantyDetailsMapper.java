package com.framasaas.service.mapper;

import com.framasaas.domain.Article;
import com.framasaas.domain.ArticleWarrantyDetails;
import com.framasaas.domain.WarrantyMaster;
import com.framasaas.service.dto.ArticleDTO;
import com.framasaas.service.dto.ArticleWarrantyDetailsDTO;
import com.framasaas.service.dto.WarrantyMasterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArticleWarrantyDetails} and its DTO {@link ArticleWarrantyDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArticleWarrantyDetailsMapper extends EntityMapper<ArticleWarrantyDetailsDTO, ArticleWarrantyDetails> {
    @Mapping(target = "article", source = "article", qualifiedByName = "articleId")
    @Mapping(target = "warrantyMaster", source = "warrantyMaster", qualifiedByName = "warrantyMasterId")
    ArticleWarrantyDetailsDTO toDto(ArticleWarrantyDetails s);

    @Named("articleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArticleDTO toDtoArticleId(Article article);

    @Named("warrantyMasterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WarrantyMasterDTO toDtoWarrantyMasterId(WarrantyMaster warrantyMaster);
}
