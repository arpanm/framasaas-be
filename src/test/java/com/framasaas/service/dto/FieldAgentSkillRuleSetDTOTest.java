package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldAgentSkillRuleSetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldAgentSkillRuleSetDTO.class);
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO1 = new FieldAgentSkillRuleSetDTO();
        fieldAgentSkillRuleSetDTO1.setId(1L);
        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO2 = new FieldAgentSkillRuleSetDTO();
        assertThat(fieldAgentSkillRuleSetDTO1).isNotEqualTo(fieldAgentSkillRuleSetDTO2);
        fieldAgentSkillRuleSetDTO2.setId(fieldAgentSkillRuleSetDTO1.getId());
        assertThat(fieldAgentSkillRuleSetDTO1).isEqualTo(fieldAgentSkillRuleSetDTO2);
        fieldAgentSkillRuleSetDTO2.setId(2L);
        assertThat(fieldAgentSkillRuleSetDTO1).isNotEqualTo(fieldAgentSkillRuleSetDTO2);
        fieldAgentSkillRuleSetDTO1.setId(null);
        assertThat(fieldAgentSkillRuleSetDTO1).isNotEqualTo(fieldAgentSkillRuleSetDTO2);
    }
}
