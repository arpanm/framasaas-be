package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.BrandTestSamples.*;
import static com.framasaas.domain.CategoryTestSamples.*;
import static com.framasaas.domain.FieldAgentSkillRuleSetTestSamples.*;
import static com.framasaas.domain.FieldAgentSkillRuleTestSamples.*;
import static com.framasaas.domain.LanguageMappingTestSamples.*;
import static com.framasaas.domain.LocationMappingTestSamples.*;
import static com.framasaas.domain.PincodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FieldAgentSkillRuleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldAgentSkillRule.class);
        FieldAgentSkillRule fieldAgentSkillRule1 = getFieldAgentSkillRuleSample1();
        FieldAgentSkillRule fieldAgentSkillRule2 = new FieldAgentSkillRule();
        assertThat(fieldAgentSkillRule1).isNotEqualTo(fieldAgentSkillRule2);

        fieldAgentSkillRule2.setId(fieldAgentSkillRule1.getId());
        assertThat(fieldAgentSkillRule1).isEqualTo(fieldAgentSkillRule2);

        fieldAgentSkillRule2 = getFieldAgentSkillRuleSample2();
        assertThat(fieldAgentSkillRule1).isNotEqualTo(fieldAgentSkillRule2);
    }

    @Test
    void brandTest() {
        FieldAgentSkillRule fieldAgentSkillRule = getFieldAgentSkillRuleRandomSampleGenerator();
        Brand brandBack = getBrandRandomSampleGenerator();

        fieldAgentSkillRule.addBrand(brandBack);
        assertThat(fieldAgentSkillRule.getBrands()).containsOnly(brandBack);
        assertThat(brandBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.removeBrand(brandBack);
        assertThat(fieldAgentSkillRule.getBrands()).doesNotContain(brandBack);
        assertThat(brandBack.getFieldAgentSkillRule()).isNull();

        fieldAgentSkillRule.brands(new HashSet<>(Set.of(brandBack)));
        assertThat(fieldAgentSkillRule.getBrands()).containsOnly(brandBack);
        assertThat(brandBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.setBrands(new HashSet<>());
        assertThat(fieldAgentSkillRule.getBrands()).doesNotContain(brandBack);
        assertThat(brandBack.getFieldAgentSkillRule()).isNull();
    }

    @Test
    void categoryTest() {
        FieldAgentSkillRule fieldAgentSkillRule = getFieldAgentSkillRuleRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        fieldAgentSkillRule.addCategory(categoryBack);
        assertThat(fieldAgentSkillRule.getCategories()).containsOnly(categoryBack);
        assertThat(categoryBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.removeCategory(categoryBack);
        assertThat(fieldAgentSkillRule.getCategories()).doesNotContain(categoryBack);
        assertThat(categoryBack.getFieldAgentSkillRule()).isNull();

        fieldAgentSkillRule.categories(new HashSet<>(Set.of(categoryBack)));
        assertThat(fieldAgentSkillRule.getCategories()).containsOnly(categoryBack);
        assertThat(categoryBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.setCategories(new HashSet<>());
        assertThat(fieldAgentSkillRule.getCategories()).doesNotContain(categoryBack);
        assertThat(categoryBack.getFieldAgentSkillRule()).isNull();
    }

    @Test
    void pincodeTest() {
        FieldAgentSkillRule fieldAgentSkillRule = getFieldAgentSkillRuleRandomSampleGenerator();
        Pincode pincodeBack = getPincodeRandomSampleGenerator();

        fieldAgentSkillRule.addPincode(pincodeBack);
        assertThat(fieldAgentSkillRule.getPincodes()).containsOnly(pincodeBack);
        assertThat(pincodeBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.removePincode(pincodeBack);
        assertThat(fieldAgentSkillRule.getPincodes()).doesNotContain(pincodeBack);
        assertThat(pincodeBack.getFieldAgentSkillRule()).isNull();

        fieldAgentSkillRule.pincodes(new HashSet<>(Set.of(pincodeBack)));
        assertThat(fieldAgentSkillRule.getPincodes()).containsOnly(pincodeBack);
        assertThat(pincodeBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.setPincodes(new HashSet<>());
        assertThat(fieldAgentSkillRule.getPincodes()).doesNotContain(pincodeBack);
        assertThat(pincodeBack.getFieldAgentSkillRule()).isNull();
    }

    @Test
    void locationMappingTest() {
        FieldAgentSkillRule fieldAgentSkillRule = getFieldAgentSkillRuleRandomSampleGenerator();
        LocationMapping locationMappingBack = getLocationMappingRandomSampleGenerator();

        fieldAgentSkillRule.addLocationMapping(locationMappingBack);
        assertThat(fieldAgentSkillRule.getLocationMappings()).containsOnly(locationMappingBack);
        assertThat(locationMappingBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.removeLocationMapping(locationMappingBack);
        assertThat(fieldAgentSkillRule.getLocationMappings()).doesNotContain(locationMappingBack);
        assertThat(locationMappingBack.getFieldAgentSkillRule()).isNull();

        fieldAgentSkillRule.locationMappings(new HashSet<>(Set.of(locationMappingBack)));
        assertThat(fieldAgentSkillRule.getLocationMappings()).containsOnly(locationMappingBack);
        assertThat(locationMappingBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.setLocationMappings(new HashSet<>());
        assertThat(fieldAgentSkillRule.getLocationMappings()).doesNotContain(locationMappingBack);
        assertThat(locationMappingBack.getFieldAgentSkillRule()).isNull();
    }

    @Test
    void languageMappingTest() {
        FieldAgentSkillRule fieldAgentSkillRule = getFieldAgentSkillRuleRandomSampleGenerator();
        LanguageMapping languageMappingBack = getLanguageMappingRandomSampleGenerator();

        fieldAgentSkillRule.addLanguageMapping(languageMappingBack);
        assertThat(fieldAgentSkillRule.getLanguageMappings()).containsOnly(languageMappingBack);
        assertThat(languageMappingBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.removeLanguageMapping(languageMappingBack);
        assertThat(fieldAgentSkillRule.getLanguageMappings()).doesNotContain(languageMappingBack);
        assertThat(languageMappingBack.getFieldAgentSkillRule()).isNull();

        fieldAgentSkillRule.languageMappings(new HashSet<>(Set.of(languageMappingBack)));
        assertThat(fieldAgentSkillRule.getLanguageMappings()).containsOnly(languageMappingBack);
        assertThat(languageMappingBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.setLanguageMappings(new HashSet<>());
        assertThat(fieldAgentSkillRule.getLanguageMappings()).doesNotContain(languageMappingBack);
        assertThat(languageMappingBack.getFieldAgentSkillRule()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        FieldAgentSkillRule fieldAgentSkillRule = getFieldAgentSkillRuleRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        fieldAgentSkillRule.addAdditionalAttribute(additionalAttributeBack);
        assertThat(fieldAgentSkillRule.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(fieldAgentSkillRule.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFieldAgentSkillRule()).isNull();

        fieldAgentSkillRule.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(fieldAgentSkillRule.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRule);

        fieldAgentSkillRule.setAdditionalAttributes(new HashSet<>());
        assertThat(fieldAgentSkillRule.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFieldAgentSkillRule()).isNull();
    }

    @Test
    void fieldAgentSkillRuleSetTest() {
        FieldAgentSkillRule fieldAgentSkillRule = getFieldAgentSkillRuleRandomSampleGenerator();
        FieldAgentSkillRuleSet fieldAgentSkillRuleSetBack = getFieldAgentSkillRuleSetRandomSampleGenerator();

        fieldAgentSkillRule.setFieldAgentSkillRuleSet(fieldAgentSkillRuleSetBack);
        assertThat(fieldAgentSkillRule.getFieldAgentSkillRuleSet()).isEqualTo(fieldAgentSkillRuleSetBack);

        fieldAgentSkillRule.fieldAgentSkillRuleSet(null);
        assertThat(fieldAgentSkillRule.getFieldAgentSkillRuleSet()).isNull();
    }
}
