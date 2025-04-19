package com.framasaas.service.mapper;

import static com.framasaas.domain.PincodeAsserts.*;
import static com.framasaas.domain.PincodeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PincodeMapperTest {

    private PincodeMapper pincodeMapper;

    @BeforeEach
    void setUp() {
        pincodeMapper = new PincodeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPincodeSample1();
        var actual = pincodeMapper.toEntity(pincodeMapper.toDto(expected));
        assertPincodeAllPropertiesEquals(expected, actual);
    }
}
