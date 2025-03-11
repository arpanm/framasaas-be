package com.framasaas.be.domain;

import static com.framasaas.be.domain.FieldAgentSkillRuleTestSamples.*;
import static com.framasaas.be.domain.FranchiseAllocationRuleTestSamples.*;
import static com.framasaas.be.domain.LanguageMappingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LanguageMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LanguageMapping.class);
        LanguageMapping languageMapping1 = getLanguageMappingSample1();
        LanguageMapping languageMapping2 = new LanguageMapping();
        assertThat(languageMapping1).isNotEqualTo(languageMapping2);

        languageMapping2.setId(languageMapping1.getId());
        assertThat(languageMapping1).isEqualTo(languageMapping2);

        languageMapping2 = getLanguageMappingSample2();
        assertThat(languageMapping1).isNotEqualTo(languageMapping2);
    }

    @Test
    void franchiseRuleTest() {
        LanguageMapping languageMapping = getLanguageMappingRandomSampleGenerator();
        FranchiseAllocationRule franchiseAllocationRuleBack = getFranchiseAllocationRuleRandomSampleGenerator();

        languageMapping.setFranchiseRule(franchiseAllocationRuleBack);
        assertThat(languageMapping.getFranchiseRule()).isEqualTo(franchiseAllocationRuleBack);

        languageMapping.franchiseRule(null);
        assertThat(languageMapping.getFranchiseRule()).isNull();
    }

    @Test
    void fieldAgentSkillRuleTest() {
        LanguageMapping languageMapping = getLanguageMappingRandomSampleGenerator();
        FieldAgentSkillRule fieldAgentSkillRuleBack = getFieldAgentSkillRuleRandomSampleGenerator();

        languageMapping.setFieldAgentSkillRule(fieldAgentSkillRuleBack);
        assertThat(languageMapping.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRuleBack);

        languageMapping.fieldAgentSkillRule(null);
        assertThat(languageMapping.getFieldAgentSkillRule()).isNull();
    }
}
