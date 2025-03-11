package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.BrandTestSamples.*;
import static com.framasaas.be.domain.FieldAgentSkillRuleTestSamples.*;
import static com.framasaas.be.domain.FranchiseAllocationRuleTestSamples.*;
import static com.framasaas.be.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BrandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Brand.class);
        Brand brand1 = getBrandSample1();
        Brand brand2 = new Brand();
        assertThat(brand1).isNotEqualTo(brand2);

        brand2.setId(brand1.getId());
        assertThat(brand1).isEqualTo(brand2);

        brand2 = getBrandSample2();
        assertThat(brand1).isNotEqualTo(brand2);
    }

    @Test
    void productTest() {
        Brand brand = getBrandRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        brand.addProduct(productBack);
        assertThat(brand.getProducts()).containsOnly(productBack);
        assertThat(productBack.getBrand()).isEqualTo(brand);

        brand.removeProduct(productBack);
        assertThat(brand.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getBrand()).isNull();

        brand.products(new HashSet<>(Set.of(productBack)));
        assertThat(brand.getProducts()).containsOnly(productBack);
        assertThat(productBack.getBrand()).isEqualTo(brand);

        brand.setProducts(new HashSet<>());
        assertThat(brand.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getBrand()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        Brand brand = getBrandRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        brand.addAdditionalAttribute(additionalAttributeBack);
        assertThat(brand.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getBrand()).isEqualTo(brand);

        brand.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(brand.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getBrand()).isNull();

        brand.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(brand.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getBrand()).isEqualTo(brand);

        brand.setAdditionalAttributes(new HashSet<>());
        assertThat(brand.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getBrand()).isNull();
    }

    @Test
    void franchiseRuleTest() {
        Brand brand = getBrandRandomSampleGenerator();
        FranchiseAllocationRule franchiseAllocationRuleBack = getFranchiseAllocationRuleRandomSampleGenerator();

        brand.setFranchiseRule(franchiseAllocationRuleBack);
        assertThat(brand.getFranchiseRule()).isEqualTo(franchiseAllocationRuleBack);

        brand.franchiseRule(null);
        assertThat(brand.getFranchiseRule()).isNull();
    }

    @Test
    void fieldAgentSkillRuleTest() {
        Brand brand = getBrandRandomSampleGenerator();
        FieldAgentSkillRule fieldAgentSkillRuleBack = getFieldAgentSkillRuleRandomSampleGenerator();

        brand.setFieldAgentSkillRule(fieldAgentSkillRuleBack);
        assertThat(brand.getFieldAgentSkillRule()).isEqualTo(fieldAgentSkillRuleBack);

        brand.fieldAgentSkillRule(null);
        assertThat(brand.getFieldAgentSkillRule()).isNull();
    }
}
