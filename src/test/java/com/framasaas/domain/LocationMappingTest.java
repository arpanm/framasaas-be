package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.FieldAgentSkillRuleTestSamples.*;
import static com.framasaas.domain.FranchiseAllocationRuleTestSamples.*;
import static com.framasaas.domain.LocationMappingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class LocationMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationMapping.class);
        LocationMapping locationMapping1 = getLocationMappingSample1();
        LocationMapping locationMapping2 = new LocationMapping();
        assertThat(locationMapping1).isNotEqualTo(locationMapping2);

        locationMapping2.setId(locationMapping1.getId());
        assertThat(locationMapping1).isEqualTo(locationMapping2);

        locationMapping2 = getLocationMappingSample2();
        assertThat(locationMapping1).isNotEqualTo(locationMapping2);
    }

    @Test
    void additionalAttributeTest() {
        LocationMapping locationMapping = getLocationMappingRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        locationMapping.addAdditionalAttribute(additionalAttributeBack);
        assertThat(locationMapping.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getLocation()).isEqualTo(locationMapping);

        locationMapping.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(locationMapping.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getLocation()).isNull();

        locationMapping.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(locationMapping.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getLocation()).isEqualTo(locationMapping);

        locationMapping.setAdditionalAttributes(new HashSet<>());
        assertThat(locationMapping.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getLocation()).isNull();
    }

    @Test
    void franchiseRuleTest() {
        LocationMapping locationMapping = getLocationMappingRandomSampleGenerator();
        FranchiseAllocationRule franchiseAllocationRuleBack = getFranchiseAllocationRuleRandomSampleGenerator();

        locationMapping.setFranchiseRule(franchiseAllocationRuleBack);
        assertThat(locationMapping.getFranchiseRule()).isEqualTo(franchiseAllocationRuleBack);

        locationMapping.franchiseRule(null);
        assertThat(locationMapping.getFranchiseRule()).isNull();
    }

    @Test
    void fieldAgentSkillRuleTest() {
        LocationMapping locationMapping = getLocationMappingRandomSampleGenerator();
        FieldAgentSkillRule fieldAgentSkillRuleBack = getFieldAgentSkillRuleRandomSampleGenerator();

        locationMapping.setFieldAgentSkillRule(fieldAgentSkillRuleBack);
        assertThat(locationMapping.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRuleBack);

        locationMapping.fieldAgentSkillRule(null);
        assertThat(locationMapping.getFieldAgentSkillRule()).isNull();
    }
}
