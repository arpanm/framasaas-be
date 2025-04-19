package com.framasaas.service.mapper;

import static com.framasaas.domain.InventoryHistoryAsserts.*;
import static com.framasaas.domain.InventoryHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryHistoryMapperTest {

    private InventoryHistoryMapper inventoryHistoryMapper;

    @BeforeEach
    void setUp() {
        inventoryHistoryMapper = new InventoryHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInventoryHistorySample1();
        var actual = inventoryHistoryMapper.toEntity(inventoryHistoryMapper.toDto(expected));
        assertInventoryHistoryAllPropertiesEquals(expected, actual);
    }
}
