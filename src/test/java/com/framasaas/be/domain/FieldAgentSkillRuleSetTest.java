package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.FieldAgentSkillRuleSetTestSamples.*;
import static com.framasaas.be.domain.FieldAgentSkillRuleTestSamples.*;
import static com.framasaas.be.domain.FranchiseUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FieldAgentSkillRuleSetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldAgentSkillRuleSet.class);
        FieldAgentSkillRuleSet fieldAgentSkillRuleSet1 = getFieldAgentSkillRuleSetSample1();
        FieldAgentSkillRuleSet fieldAgentSkillRuleSet2 = new FieldAgentSkillRuleSet();
        assertThat(fieldAgentSkillRuleSet1).isNotEqualTo(fieldAgentSkillRuleSet2);

        fieldAgentSkillRuleSet2.setId(fieldAgentSkillRuleSet1.getId());
        assertThat(fieldAgentSkillRuleSet1).isEqualTo(fieldAgentSkillRuleSet2);

        fieldAgentSkillRuleSet2 = getFieldAgentSkillRuleSetSample2();
        assertThat(fieldAgentSkillRuleSet1).isNotEqualTo(fieldAgentSkillRuleSet2);
    }

    @Test
    void fieldAgentSkillRuleTest() {
        FieldAgentSkillRuleSet fieldAgentSkillRuleSet = getFieldAgentSkillRuleSetRandomSampleGenerator();
        FieldAgentSkillRule fieldAgentSkillRuleBack = getFieldAgentSkillRuleRandomSampleGenerator();

        fieldAgentSkillRuleSet.addFieldAgentSkillRule(fieldAgentSkillRuleBack);
        assertThat(fieldAgentSkillRuleSet.getFieldAgentSkillRules()).containsOnly(fieldAgentSkillRuleBack);
        assertThat(fieldAgentSkillRuleBack.getFieldAgentSkillRuleSet()).isEqualTo(fieldAgentSkillRuleSet);

        fieldAgentSkillRuleSet.removeFieldAgentSkillRule(fieldAgentSkillRuleBack);
        assertThat(fieldAgentSkillRuleSet.getFieldAgentSkillRules()).doesNotContain(fieldAgentSkillRuleBack);
        assertThat(fieldAgentSkillRuleBack.getFieldAgentSkillRuleSet()).isNull();

        fieldAgentSkillRuleSet.fieldAgentSkillRules(new HashSet<>(Set.of(fieldAgentSkillRuleBack)));
        assertThat(fieldAgentSkillRuleSet.getFieldAgentSkillRules()).containsOnly(fieldAgentSkillRuleBack);
        assertThat(fieldAgentSkillRuleBack.getFieldAgentSkillRuleSet()).isEqualTo(fieldAgentSkillRuleSet);

        fieldAgentSkillRuleSet.setFieldAgentSkillRules(new HashSet<>());
        assertThat(fieldAgentSkillRuleSet.getFieldAgentSkillRules()).doesNotContain(fieldAgentSkillRuleBack);
        assertThat(fieldAgentSkillRuleBack.getFieldAgentSkillRuleSet()).isNull();
    }

    @Test
    void franchiseUserTest() {
        FieldAgentSkillRuleSet fieldAgentSkillRuleSet = getFieldAgentSkillRuleSetRandomSampleGenerator();
        FranchiseUser franchiseUserBack = getFranchiseUserRandomSampleGenerator();

        fieldAgentSkillRuleSet.addFranchiseUser(franchiseUserBack);
        assertThat(fieldAgentSkillRuleSet.getFranchiseUsers()).containsOnly(franchiseUserBack);
        assertThat(franchiseUserBack.getSkillRuleSet()).isEqualTo(fieldAgentSkillRuleSet);

        fieldAgentSkillRuleSet.removeFranchiseUser(franchiseUserBack);
        assertThat(fieldAgentSkillRuleSet.getFranchiseUsers()).doesNotContain(franchiseUserBack);
        assertThat(franchiseUserBack.getSkillRuleSet()).isNull();

        fieldAgentSkillRuleSet.franchiseUsers(new HashSet<>(Set.of(franchiseUserBack)));
        assertThat(fieldAgentSkillRuleSet.getFranchiseUsers()).containsOnly(franchiseUserBack);
        assertThat(franchiseUserBack.getSkillRuleSet()).isEqualTo(fieldAgentSkillRuleSet);

        fieldAgentSkillRuleSet.setFranchiseUsers(new HashSet<>());
        assertThat(fieldAgentSkillRuleSet.getFranchiseUsers()).doesNotContain(franchiseUserBack);
        assertThat(franchiseUserBack.getSkillRuleSet()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        FieldAgentSkillRuleSet fieldAgentSkillRuleSet = getFieldAgentSkillRuleSetRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        fieldAgentSkillRuleSet.addAdditionalAttribute(additionalAttributeBack);
        assertThat(fieldAgentSkillRuleSet.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFieldAgentSkillRuleSet()).isEqualTo(fieldAgentSkillRuleSet);

        fieldAgentSkillRuleSet.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(fieldAgentSkillRuleSet.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFieldAgentSkillRuleSet()).isNull();

        fieldAgentSkillRuleSet.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(fieldAgentSkillRuleSet.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFieldAgentSkillRuleSet()).isEqualTo(fieldAgentSkillRuleSet);

        fieldAgentSkillRuleSet.setAdditionalAttributes(new HashSet<>());
        assertThat(fieldAgentSkillRuleSet.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFieldAgentSkillRuleSet()).isNull();
    }
}
