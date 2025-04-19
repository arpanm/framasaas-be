package com.framasaas.service.mapper;

import static com.framasaas.domain.FranchisePerformanceHistoryAsserts.*;
import static com.framasaas.domain.FranchisePerformanceHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FranchisePerformanceHistoryMapperTest {

    private FranchisePerformanceHistoryMapper franchisePerformanceHistoryMapper;

    @BeforeEach
    void setUp() {
        franchisePerformanceHistoryMapper = new FranchisePerformanceHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFranchisePerformanceHistorySample1();
        var actual = franchisePerformanceHistoryMapper.toEntity(franchisePerformanceHistoryMapper.toDto(expected));
        assertFranchisePerformanceHistoryAllPropertiesEquals(expected, actual);
    }
}
