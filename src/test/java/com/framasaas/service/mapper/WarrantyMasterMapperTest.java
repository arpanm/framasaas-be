package com.framasaas.service.mapper;

import static com.framasaas.domain.WarrantyMasterAsserts.*;
import static com.framasaas.domain.WarrantyMasterTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarrantyMasterMapperTest {

    private WarrantyMasterMapper warrantyMasterMapper;

    @BeforeEach
    void setUp() {
        warrantyMasterMapper = new WarrantyMasterMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWarrantyMasterSample1();
        var actual = warrantyMasterMapper.toEntity(warrantyMasterMapper.toDto(expected));
        assertWarrantyMasterAllPropertiesEquals(expected, actual);
    }
}
