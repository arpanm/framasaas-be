package com.framasaas.service.mapper;

import static com.framasaas.domain.WarrantyMasterPriceHistoryAsserts.*;
import static com.framasaas.domain.WarrantyMasterPriceHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarrantyMasterPriceHistoryMapperTest {

    private WarrantyMasterPriceHistoryMapper warrantyMasterPriceHistoryMapper;

    @BeforeEach
    void setUp() {
        warrantyMasterPriceHistoryMapper = new WarrantyMasterPriceHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWarrantyMasterPriceHistorySample1();
        var actual = warrantyMasterPriceHistoryMapper.toEntity(warrantyMasterPriceHistoryMapper.toDto(expected));
        assertWarrantyMasterPriceHistoryAllPropertiesEquals(expected, actual);
    }
}
