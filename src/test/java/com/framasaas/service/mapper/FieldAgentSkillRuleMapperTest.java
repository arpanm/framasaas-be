package com.framasaas.service.mapper;

import static com.framasaas.domain.FieldAgentSkillRuleAsserts.*;
import static com.framasaas.domain.FieldAgentSkillRuleTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldAgentSkillRuleMapperTest {

    private FieldAgentSkillRuleMapper fieldAgentSkillRuleMapper;

    @BeforeEach
    void setUp() {
        fieldAgentSkillRuleMapper = new FieldAgentSkillRuleMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFieldAgentSkillRuleSample1();
        var actual = fieldAgentSkillRuleMapper.toEntity(fieldAgentSkillRuleMapper.toDto(expected));
        assertFieldAgentSkillRuleAllPropertiesEquals(expected, actual);
    }
}
