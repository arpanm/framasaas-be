package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.CategoryTestSamples.*;
import static com.framasaas.be.domain.FranchiseAllocationRuleTestSamples.*;
import static com.framasaas.be.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = getCategorySample1();
        Category category2 = new Category();
        assertThat(category1).isNotEqualTo(category2);

        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);

        category2 = getCategorySample2();
        assertThat(category1).isNotEqualTo(category2);
    }

    @Test
    void productTest() {
        Category category = getCategoryRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        category.addProduct(productBack);
        assertThat(category.getProducts()).containsOnly(productBack);
        assertThat(productBack.getCategory()).isEqualTo(category);

        category.removeProduct(productBack);
        assertThat(category.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getCategory()).isNull();

        category.products(new HashSet<>(Set.of(productBack)));
        assertThat(category.getProducts()).containsOnly(productBack);
        assertThat(productBack.getCategory()).isEqualTo(category);

        category.setProducts(new HashSet<>());
        assertThat(category.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getCategory()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        Category category = getCategoryRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        category.addAdditionalAttribute(additionalAttributeBack);
        assertThat(category.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getCategory()).isEqualTo(category);

        category.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(category.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getCategory()).isNull();

        category.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(category.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getCategory()).isEqualTo(category);

        category.setAdditionalAttributes(new HashSet<>());
        assertThat(category.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getCategory()).isNull();
    }

    @Test
    void franchiseRuleTest() {
        Category category = getCategoryRandomSampleGenerator();
        FranchiseAllocationRule franchiseAllocationRuleBack = getFranchiseAllocationRuleRandomSampleGenerator();

        category.setFranchiseRule(franchiseAllocationRuleBack);
        assertThat(category.getFranchiseRule()).isEqualTo(franchiseAllocationRuleBack);

        category.franchiseRule(null);
        assertThat(category.getFranchiseRule()).isNull();
    }
}
