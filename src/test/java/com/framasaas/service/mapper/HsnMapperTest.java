package com.framasaas.service.mapper;

import static com.framasaas.domain.HsnAsserts.*;
import static com.framasaas.domain.HsnTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HsnMapperTest {

    private HsnMapper hsnMapper;

    @BeforeEach
    void setUp() {
        hsnMapper = new HsnMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHsnSample1();
        var actual = hsnMapper.toEntity(hsnMapper.toDto(expected));
        assertHsnAllPropertiesEquals(expected, actual);
    }
}
