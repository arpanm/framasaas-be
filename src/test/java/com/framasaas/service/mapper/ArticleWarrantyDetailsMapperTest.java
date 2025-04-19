package com.framasaas.service.mapper;

import static com.framasaas.domain.ArticleWarrantyDetailsAsserts.*;
import static com.framasaas.domain.ArticleWarrantyDetailsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticleWarrantyDetailsMapperTest {

    private ArticleWarrantyDetailsMapper articleWarrantyDetailsMapper;

    @BeforeEach
    void setUp() {
        articleWarrantyDetailsMapper = new ArticleWarrantyDetailsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getArticleWarrantyDetailsSample1();
        var actual = articleWarrantyDetailsMapper.toEntity(articleWarrantyDetailsMapper.toDto(expected));
        assertArticleWarrantyDetailsAllPropertiesEquals(expected, actual);
    }
}
