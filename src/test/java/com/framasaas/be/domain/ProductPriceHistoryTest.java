package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.ProductPriceHistoryTestSamples.*;
import static com.framasaas.be.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductPriceHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPriceHistory.class);
        ProductPriceHistory productPriceHistory1 = getProductPriceHistorySample1();
        ProductPriceHistory productPriceHistory2 = new ProductPriceHistory();
        assertThat(productPriceHistory1).isNotEqualTo(productPriceHistory2);

        productPriceHistory2.setId(productPriceHistory1.getId());
        assertThat(productPriceHistory1).isEqualTo(productPriceHistory2);

        productPriceHistory2 = getProductPriceHistorySample2();
        assertThat(productPriceHistory1).isNotEqualTo(productPriceHistory2);
    }

    @Test
    void additionalAttributeTest() {
        ProductPriceHistory productPriceHistory = getProductPriceHistoryRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        productPriceHistory.addAdditionalAttribute(additionalAttributeBack);
        assertThat(productPriceHistory.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getPriceHistory()).isEqualTo(productPriceHistory);

        productPriceHistory.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(productPriceHistory.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getPriceHistory()).isNull();

        productPriceHistory.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(productPriceHistory.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getPriceHistory()).isEqualTo(productPriceHistory);

        productPriceHistory.setAdditionalAttributes(new HashSet<>());
        assertThat(productPriceHistory.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getPriceHistory()).isNull();
    }

    @Test
    void productTest() {
        ProductPriceHistory productPriceHistory = getProductPriceHistoryRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        productPriceHistory.setProduct(productBack);
        assertThat(productPriceHistory.getProduct()).isEqualTo(productBack);

        productPriceHistory.product(null);
        assertThat(productPriceHistory.getProduct()).isNull();
    }
}
