package com.framasaas.service.mapper;

import static com.framasaas.domain.FranchiseAllocationRuleAsserts.*;
import static com.framasaas.domain.FranchiseAllocationRuleTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FranchiseAllocationRuleMapperTest {

    private FranchiseAllocationRuleMapper franchiseAllocationRuleMapper;

    @BeforeEach
    void setUp() {
        franchiseAllocationRuleMapper = new FranchiseAllocationRuleMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFranchiseAllocationRuleSample1();
        var actual = franchiseAllocationRuleMapper.toEntity(franchiseAllocationRuleMapper.toDto(expected));
        assertFranchiseAllocationRuleAllPropertiesEquals(expected, actual);
    }
}
