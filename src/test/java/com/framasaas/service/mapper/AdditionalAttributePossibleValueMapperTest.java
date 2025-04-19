package com.framasaas.service.mapper;

import static com.framasaas.domain.AdditionalAttributePossibleValueAsserts.*;
import static com.framasaas.domain.AdditionalAttributePossibleValueTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdditionalAttributePossibleValueMapperTest {

    private AdditionalAttributePossibleValueMapper additionalAttributePossibleValueMapper;

    @BeforeEach
    void setUp() {
        additionalAttributePossibleValueMapper = new AdditionalAttributePossibleValueMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdditionalAttributePossibleValueSample1();
        var actual = additionalAttributePossibleValueMapper.toEntity(additionalAttributePossibleValueMapper.toDto(expected));
        assertAdditionalAttributePossibleValueAllPropertiesEquals(expected, actual);
    }
}
