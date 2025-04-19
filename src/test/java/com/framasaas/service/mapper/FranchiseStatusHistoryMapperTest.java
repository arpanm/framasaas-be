package com.framasaas.service.mapper;

import static com.framasaas.domain.FranchiseStatusHistoryAsserts.*;
import static com.framasaas.domain.FranchiseStatusHistoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FranchiseStatusHistoryMapperTest {

    private FranchiseStatusHistoryMapper franchiseStatusHistoryMapper;

    @BeforeEach
    void setUp() {
        franchiseStatusHistoryMapper = new FranchiseStatusHistoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFranchiseStatusHistorySample1();
        var actual = franchiseStatusHistoryMapper.toEntity(franchiseStatusHistoryMapper.toDto(expected));
        assertFranchiseStatusHistoryAllPropertiesEquals(expected, actual);
    }
}
