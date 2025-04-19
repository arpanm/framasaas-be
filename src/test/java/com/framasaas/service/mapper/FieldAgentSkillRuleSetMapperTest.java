package com.framasaas.service.mapper;

import static com.framasaas.domain.FieldAgentSkillRuleSetAsserts.*;
import static com.framasaas.domain.FieldAgentSkillRuleSetTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldAgentSkillRuleSetMapperTest {

    private FieldAgentSkillRuleSetMapper fieldAgentSkillRuleSetMapper;

    @BeforeEach
    void setUp() {
        fieldAgentSkillRuleSetMapper = new FieldAgentSkillRuleSetMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFieldAgentSkillRuleSetSample1();
        var actual = fieldAgentSkillRuleSetMapper.toEntity(fieldAgentSkillRuleSetMapper.toDto(expected));
        assertFieldAgentSkillRuleSetAllPropertiesEquals(expected, actual);
    }
}
