package com.framasaas.service.mapper;

import static com.framasaas.domain.AdditionalAttributeAsserts.*;
import static com.framasaas.domain.AdditionalAttributeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdditionalAttributeMapperTest {

    private AdditionalAttributeMapper additionalAttributeMapper;

    @BeforeEach
    void setUp() {
        additionalAttributeMapper = new AdditionalAttributeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdditionalAttributeSample1();
        var actual = additionalAttributeMapper.toEntity(additionalAttributeMapper.toDto(expected));
        assertAdditionalAttributeAllPropertiesEquals(expected, actual);
    }
}
