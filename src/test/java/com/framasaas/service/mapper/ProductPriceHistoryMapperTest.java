package com.framasaas.service.mapper;

import static com.framasaas.domain.ProductPriceHistoryAsserts.*;
import static com.framasaas.domain.ProductPriceHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductPriceHistoryMapperTest {

    private ProductPriceHistoryMapper productPriceHistoryMapper;

    @BeforeEach
    void setUp() {
        productPriceHistoryMapper = new ProductPriceHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProductPriceHistorySample1();
        var actual = productPriceHistoryMapper.toEntity(productPriceHistoryMapper.toDto(expected));
        assertProductPriceHistoryAllPropertiesEquals(expected, actual);
    }
}
