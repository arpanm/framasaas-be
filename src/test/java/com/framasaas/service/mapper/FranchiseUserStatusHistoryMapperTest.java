package com.framasaas.service.mapper;

import static com.framasaas.domain.FranchiseUserStatusHistoryAsserts.*;
import static com.framasaas.domain.FranchiseUserStatusHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FranchiseUserStatusHistoryMapperTest {

    private FranchiseUserStatusHistoryMapper franchiseUserStatusHistoryMapper;

    @BeforeEach
    void setUp() {
        franchiseUserStatusHistoryMapper = new FranchiseUserStatusHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFranchiseUserStatusHistorySample1();
        var actual = franchiseUserStatusHistoryMapper.toEntity(franchiseUserStatusHistoryMapper.toDto(expected));
        assertFranchiseUserStatusHistoryAllPropertiesEquals(expected, actual);
    }
}
