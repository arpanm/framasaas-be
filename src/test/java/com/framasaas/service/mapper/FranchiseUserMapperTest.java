package com.framasaas.service.mapper;

import static com.framasaas.domain.FranchiseUserAsserts.*;
import static com.framasaas.domain.FranchiseUserTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FranchiseUserMapperTest {

    private FranchiseUserMapper franchiseUserMapper;

    @BeforeEach
    void setUp() {
        franchiseUserMapper = new FranchiseUserMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFranchiseUserSample1();
        var actual = franchiseUserMapper.toEntity(franchiseUserMapper.toDto(expected));
        assertFranchiseUserAllPropertiesEquals(expected, actual);
    }
}
