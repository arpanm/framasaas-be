package com.framasaas.service.mapper;

import static com.framasaas.domain.InventoryLocationAsserts.*;
import static com.framasaas.domain.InventoryLocationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryLocationMapperTest {

    private InventoryLocationMapper inventoryLocationMapper;

    @BeforeEach
    void setUp() {
        inventoryLocationMapper = new InventoryLocationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInventoryLocationSample1();
        var actual = inventoryLocationMapper.toEntity(inventoryLocationMapper.toDto(expected));
        assertInventoryLocationAllPropertiesEquals(expected, actual);
    }
}
