package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.FieldAgentSkillRuleSetTestSamples.*;
import static com.framasaas.domain.FranchiseTestSamples.*;
import static com.framasaas.domain.FranchiseUserStatusHistoryTestSamples.*;
import static com.framasaas.domain.FranchiseUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FranchiseUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseUser.class);
        FranchiseUser franchiseUser1 = getFranchiseUserSample1();
        FranchiseUser franchiseUser2 = new FranchiseUser();
        assertThat(franchiseUser1).isNotEqualTo(franchiseUser2);

        franchiseUser2.setId(franchiseUser1.getId());
        assertThat(franchiseUser1).isEqualTo(franchiseUser2);

        franchiseUser2 = getFranchiseUserSample2();
        assertThat(franchiseUser1).isNotEqualTo(franchiseUser2);
    }

    @Test
    void franchiseUserStatusHistoryTest() {
        FranchiseUser franchiseUser = getFranchiseUserRandomSampleGenerator();
        FranchiseUserStatusHistory franchiseUserStatusHistoryBack = getFranchiseUserStatusHistoryRandomSampleGenerator();

        franchiseUser.addFranchiseUserStatusHistory(franchiseUserStatusHistoryBack);
        assertThat(franchiseUser.getFranchiseUserStatusHistories()).containsOnly(franchiseUserStatusHistoryBack);
        assertThat(franchiseUserStatusHistoryBack.getFranchiseUser()).isEqualTo(franchiseUser);

        franchiseUser.removeFranchiseUserStatusHistory(franchiseUserStatusHistoryBack);
        assertThat(franchiseUser.getFranchiseUserStatusHistories()).doesNotContain(franchiseUserStatusHistoryBack);
        assertThat(franchiseUserStatusHistoryBack.getFranchiseUser()).isNull();

        franchiseUser.franchiseUserStatusHistories(new HashSet<>(Set.of(franchiseUserStatusHistoryBack)));
        assertThat(franchiseUser.getFranchiseUserStatusHistories()).containsOnly(franchiseUserStatusHistoryBack);
        assertThat(franchiseUserStatusHistoryBack.getFranchiseUser()).isEqualTo(franchiseUser);

        franchiseUser.setFranchiseUserStatusHistories(new HashSet<>());
        assertThat(franchiseUser.getFranchiseUserStatusHistories()).doesNotContain(franchiseUserStatusHistoryBack);
        assertThat(franchiseUserStatusHistoryBack.getFranchiseUser()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        FranchiseUser franchiseUser = getFranchiseUserRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        franchiseUser.addAdditionalAttribute(additionalAttributeBack);
        assertThat(franchiseUser.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchiseUser()).isEqualTo(franchiseUser);

        franchiseUser.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(franchiseUser.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchiseUser()).isNull();

        franchiseUser.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(franchiseUser.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchiseUser()).isEqualTo(franchiseUser);

        franchiseUser.setAdditionalAttributes(new HashSet<>());
        assertThat(franchiseUser.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchiseUser()).isNull();
    }

    @Test
    void franchiseTest() {
        FranchiseUser franchiseUser = getFranchiseUserRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        franchiseUser.setFranchise(franchiseBack);
        assertThat(franchiseUser.getFranchise()).isEqualTo(franchiseBack);

        franchiseUser.franchise(null);
        assertThat(franchiseUser.getFranchise()).isNull();
    }

    @Test
    void skillRuleSetTest() {
        FranchiseUser franchiseUser = getFranchiseUserRandomSampleGenerator();
        FieldAgentSkillRuleSet fieldAgentSkillRuleSetBack = getFieldAgentSkillRuleSetRandomSampleGenerator();

        franchiseUser.setSkillRuleSet(fieldAgentSkillRuleSetBack);
        assertThat(franchiseUser.getSkillRuleSet()).isEqualTo(fieldAgentSkillRuleSetBack);

        franchiseUser.skillRuleSet(null);
        assertThat(franchiseUser.getSkillRuleSet()).isNull();
    }
}
