package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.HsnTestSamples.*;
import static com.framasaas.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HsnTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hsn.class);
        Hsn hsn1 = getHsnSample1();
        Hsn hsn2 = new Hsn();
        assertThat(hsn1).isNotEqualTo(hsn2);

        hsn2.setId(hsn1.getId());
        assertThat(hsn1).isEqualTo(hsn2);

        hsn2 = getHsnSample2();
        assertThat(hsn1).isNotEqualTo(hsn2);
    }

    @Test
    void productTest() {
        Hsn hsn = getHsnRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        hsn.addProduct(productBack);
        assertThat(hsn.getProducts()).containsOnly(productBack);
        assertThat(productBack.getHsn()).isEqualTo(hsn);

        hsn.removeProduct(productBack);
        assertThat(hsn.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getHsn()).isNull();

        hsn.products(new HashSet<>(Set.of(productBack)));
        assertThat(hsn.getProducts()).containsOnly(productBack);
        assertThat(productBack.getHsn()).isEqualTo(hsn);

        hsn.setProducts(new HashSet<>());
        assertThat(hsn.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getHsn()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        Hsn hsn = getHsnRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        hsn.addAdditionalAttribute(additionalAttributeBack);
        assertThat(hsn.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getHsn()).isEqualTo(hsn);

        hsn.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(hsn.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getHsn()).isNull();

        hsn.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(hsn.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getHsn()).isEqualTo(hsn);

        hsn.setAdditionalAttributes(new HashSet<>());
        assertThat(hsn.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getHsn()).isNull();
    }
}
