package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.BrandTestSamples.*;
import static com.framasaas.domain.CategoryTestSamples.*;
import static com.framasaas.domain.FranchiseAllocationRuleTestSamples.*;
import static com.framasaas.domain.LanguageMappingTestSamples.*;
import static com.framasaas.domain.LocationMappingTestSamples.*;
import static com.framasaas.domain.PincodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FranchiseAllocationRuleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FranchiseAllocationRule.class);
        FranchiseAllocationRule franchiseAllocationRule1 = getFranchiseAllocationRuleSample1();
        FranchiseAllocationRule franchiseAllocationRule2 = new FranchiseAllocationRule();
        assertThat(franchiseAllocationRule1).isNotEqualTo(franchiseAllocationRule2);

        franchiseAllocationRule2.setId(franchiseAllocationRule1.getId());
        assertThat(franchiseAllocationRule1).isEqualTo(franchiseAllocationRule2);

        franchiseAllocationRule2 = getFranchiseAllocationRuleSample2();
        assertThat(franchiseAllocationRule1).isNotEqualTo(franchiseAllocationRule2);
    }

    @Test
    void brandTest() {
        FranchiseAllocationRule franchiseAllocationRule = getFranchiseAllocationRuleRandomSampleGenerator();
        Brand brandBack = getBrandRandomSampleGenerator();

        franchiseAllocationRule.addBrand(brandBack);
        assertThat(franchiseAllocationRule.getBrands()).containsOnly(brandBack);
        assertThat(brandBack.getFranchiseRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.removeBrand(brandBack);
        assertThat(franchiseAllocationRule.getBrands()).doesNotContain(brandBack);
        assertThat(brandBack.getFranchiseRule()).isNull();

        franchiseAllocationRule.brands(new HashSet<>(Set.of(brandBack)));
        assertThat(franchiseAllocationRule.getBrands()).containsOnly(brandBack);
        assertThat(brandBack.getFranchiseRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.setBrands(new HashSet<>());
        assertThat(franchiseAllocationRule.getBrands()).doesNotContain(brandBack);
        assertThat(brandBack.getFranchiseRule()).isNull();
    }

    @Test
    void categoryTest() {
        FranchiseAllocationRule franchiseAllocationRule = getFranchiseAllocationRuleRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        franchiseAllocationRule.addCategory(categoryBack);
        assertThat(franchiseAllocationRule.getCategories()).containsOnly(categoryBack);
        assertThat(categoryBack.getFranchiseRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.removeCategory(categoryBack);
        assertThat(franchiseAllocationRule.getCategories()).doesNotContain(categoryBack);
        assertThat(categoryBack.getFranchiseRule()).isNull();

        franchiseAllocationRule.categories(new HashSet<>(Set.of(categoryBack)));
        assertThat(franchiseAllocationRule.getCategories()).containsOnly(categoryBack);
        assertThat(categoryBack.getFranchiseRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.setCategories(new HashSet<>());
        assertThat(franchiseAllocationRule.getCategories()).doesNotContain(categoryBack);
        assertThat(categoryBack.getFranchiseRule()).isNull();
    }

    @Test
    void pincodeTest() {
        FranchiseAllocationRule franchiseAllocationRule = getFranchiseAllocationRuleRandomSampleGenerator();
        Pincode pincodeBack = getPincodeRandomSampleGenerator();

        franchiseAllocationRule.addPincode(pincodeBack);
        assertThat(franchiseAllocationRule.getPincodes()).containsOnly(pincodeBack);
        assertThat(pincodeBack.getFranchiseRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.removePincode(pincodeBack);
        assertThat(franchiseAllocationRule.getPincodes()).doesNotContain(pincodeBack);
        assertThat(pincodeBack.getFranchiseRule()).isNull();

        franchiseAllocationRule.pincodes(new HashSet<>(Set.of(pincodeBack)));
        assertThat(franchiseAllocationRule.getPincodes()).containsOnly(pincodeBack);
        assertThat(pincodeBack.getFranchiseRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.setPincodes(new HashSet<>());
        assertThat(franchiseAllocationRule.getPincodes()).doesNotContain(pincodeBack);
        assertThat(pincodeBack.getFranchiseRule()).isNull();
    }

    @Test
    void locationMappingTest() {
        FranchiseAllocationRule franchiseAllocationRule = getFranchiseAllocationRuleRandomSampleGenerator();
        LocationMapping locationMappingBack = getLocationMappingRandomSampleGenerator();

        franchiseAllocationRule.addLocationMapping(locationMappingBack);
        assertThat(franchiseAllocationRule.getLocationMappings()).containsOnly(locationMappingBack);
        assertThat(locationMappingBack.getFranchiseRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.removeLocationMapping(locationMappingBack);
        assertThat(franchiseAllocationRule.getLocationMappings()).doesNotContain(locationMappingBack);
        assertThat(locationMappingBack.getFranchiseRule()).isNull();

        franchiseAllocationRule.locationMappings(new HashSet<>(Set.of(locationMappingBack)));
        assertThat(franchiseAllocationRule.getLocationMappings()).containsOnly(locationMappingBack);
        assertThat(locationMappingBack.getFranchiseRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.setLocationMappings(new HashSet<>());
        assertThat(franchiseAllocationRule.getLocationMappings()).doesNotContain(locationMappingBack);
        assertThat(locationMappingBack.getFranchiseRule()).isNull();
    }

    @Test
    void languageMappingTest() {
        FranchiseAllocationRule franchiseAllocationRule = getFranchiseAllocationRuleRandomSampleGenerator();
        LanguageMapping languageMappingBack = getLanguageMappingRandomSampleGenerator();

        franchiseAllocationRule.addLanguageMapping(languageMappingBack);
        assertThat(franchiseAllocationRule.getLanguageMappings()).containsOnly(languageMappingBack);
        assertThat(languageMappingBack.getFranchiseRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.removeLanguageMapping(languageMappingBack);
        assertThat(franchiseAllocationRule.getLanguageMappings()).doesNotContain(languageMappingBack);
        assertThat(languageMappingBack.getFranchiseRule()).isNull();

        franchiseAllocationRule.languageMappings(new HashSet<>(Set.of(languageMappingBack)));
        assertThat(franchiseAllocationRule.getLanguageMappings()).containsOnly(languageMappingBack);
        assertThat(languageMappingBack.getFranchiseRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.setLanguageMappings(new HashSet<>());
        assertThat(franchiseAllocationRule.getLanguageMappings()).doesNotContain(languageMappingBack);
        assertThat(languageMappingBack.getFranchiseRule()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        FranchiseAllocationRule franchiseAllocationRule = getFranchiseAllocationRuleRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        franchiseAllocationRule.addAdditionalAttribute(additionalAttributeBack);
        assertThat(franchiseAllocationRule.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchiseAllocationRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(franchiseAllocationRule.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchiseAllocationRule()).isNull();

        franchiseAllocationRule.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(franchiseAllocationRule.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchiseAllocationRule()).isEqualTo(franchiseAllocationRule);

        franchiseAllocationRule.setAdditionalAttributes(new HashSet<>());
        assertThat(franchiseAllocationRule.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getFranchiseAllocationRule()).isNull();
    }
}
