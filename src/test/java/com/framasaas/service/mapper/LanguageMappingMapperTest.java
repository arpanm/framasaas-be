package com.framasaas.service.mapper;

import static com.framasaas.domain.LanguageMappingAsserts.*;
import static com.framasaas.domain.LanguageMappingTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LanguageMappingMapperTest {

    private LanguageMappingMapper languageMappingMapper;

    @BeforeEach
    void setUp() {
        languageMappingMapper = new LanguageMappingMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getLanguageMappingSample1();
        var actual = languageMappingMapper.toEntity(languageMappingMapper.toDto(expected));
        assertLanguageMappingAllPropertiesEquals(expected, actual);
    }
}
