package com.framasaas.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldAgentSkillRuleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldAgentSkillRuleDTO.class);
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO1 = new FieldAgentSkillRuleDTO();
        fieldAgentSkillRuleDTO1.setId(1L);
        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO2 = new FieldAgentSkillRuleDTO();
        assertThat(fieldAgentSkillRuleDTO1).isNotEqualTo(fieldAgentSkillRuleDTO2);
        fieldAgentSkillRuleDTO2.setId(fieldAgentSkillRuleDTO1.getId());
        assertThat(fieldAgentSkillRuleDTO1).isEqualTo(fieldAgentSkillRuleDTO2);
        fieldAgentSkillRuleDTO2.setId(2L);
        assertThat(fieldAgentSkillRuleDTO1).isNotEqualTo(fieldAgentSkillRuleDTO2);
        fieldAgentSkillRuleDTO1.setId(null);
        assertThat(fieldAgentSkillRuleDTO1).isNotEqualTo(fieldAgentSkillRuleDTO2);
    }
}
