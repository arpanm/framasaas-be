package com.framasaas.service.mapper;

import static com.framasaas.domain.FranchiseAllocationRuleSetAsserts.*;
import static com.framasaas.domain.FranchiseAllocationRuleSetTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FranchiseAllocationRuleSetMapperTest {

    private FranchiseAllocationRuleSetMapper franchiseAllocationRuleSetMapper;

    @BeforeEach
    void setUp() {
        franchiseAllocationRuleSetMapper = new FranchiseAllocationRuleSetMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFranchiseAllocationRuleSetSample1();
        var actual = franchiseAllocationRuleSetMapper.toEntity(franchiseAllocationRuleSetMapper.toDto(expected));
        assertFranchiseAllocationRuleSetAllPropertiesEquals(expected, actual);
    }
}
