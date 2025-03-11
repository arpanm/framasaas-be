package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.HsnTestSamples.*;
import static com.framasaas.be.domain.ProductPriceHistoryTestSamples.*;
import static com.framasaas.be.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void productPriceHistoryTest() {
        Product product = getProductRandomSampleGenerator();
        ProductPriceHistory productPriceHistoryBack = getProductPriceHistoryRandomSampleGenerator();

        product.addProductPriceHistory(productPriceHistoryBack);
        assertThat(product.getProductPriceHistories()).containsOnly(productPriceHistoryBack);
        assertThat(productPriceHistoryBack.getFranchise()).isEqualTo(product);

        product.removeProductPriceHistory(productPriceHistoryBack);
        assertThat(product.getProductPriceHistories()).doesNotContain(productPriceHistoryBack);
        assertThat(productPriceHistoryBack.getFranchise()).isNull();

        product.productPriceHistories(new HashSet<>(Set.of(productPriceHistoryBack)));
        assertThat(product.getProductPriceHistories()).containsOnly(productPriceHistoryBack);
        assertThat(productPriceHistoryBack.getFranchise()).isEqualTo(product);

        product.setProductPriceHistories(new HashSet<>());
        assertThat(product.getProductPriceHistories()).doesNotContain(productPriceHistoryBack);
        assertThat(productPriceHistoryBack.getFranchise()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        Product product = getProductRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        product.addAdditionalAttribute(additionalAttributeBack);
        assertThat(product.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getProduct()).isEqualTo(product);

        product.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(product.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getProduct()).isNull();

        product.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(product.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getProduct()).isEqualTo(product);

        product.setAdditionalAttributes(new HashSet<>());
        assertThat(product.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getProduct()).isNull();
    }

    @Test
    void hsnTest() {
        Product product = getProductRandomSampleGenerator();
        Hsn hsnBack = getHsnRandomSampleGenerator();

        product.setHsn(hsnBack);
        assertThat(product.getHsn()).isEqualTo(hsnBack);

        product.hsn(null);
        assertThat(product.getHsn()).isNull();
    }
}
