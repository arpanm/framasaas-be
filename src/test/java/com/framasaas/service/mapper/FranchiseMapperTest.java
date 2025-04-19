package com.framasaas.service.mapper;

import static com.framasaas.domain.FranchiseAsserts.*;
import static com.framasaas.domain.FranchiseTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FranchiseMapperTest {

    private FranchiseMapper franchiseMapper;

    @BeforeEach
    void setUp() {
        franchiseMapper = new FranchiseMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFranchiseSample1();
        var actual = franchiseMapper.toEntity(franchiseMapper.toDto(expected));
        assertFranchiseAllPropertiesEquals(expected, actual);
    }
}
