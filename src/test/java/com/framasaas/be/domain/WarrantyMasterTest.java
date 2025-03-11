package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.ArticleWarrantyDetailsTestSamples.*;
import static com.framasaas.be.domain.ProductTestSamples.*;
import static com.framasaas.be.domain.WarrantyMasterPriceHistoryTestSamples.*;
import static com.framasaas.be.domain.WarrantyMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WarrantyMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyMaster.class);
        WarrantyMaster warrantyMaster1 = getWarrantyMasterSample1();
        WarrantyMaster warrantyMaster2 = new WarrantyMaster();
        assertThat(warrantyMaster1).isNotEqualTo(warrantyMaster2);

        warrantyMaster2.setId(warrantyMaster1.getId());
        assertThat(warrantyMaster1).isEqualTo(warrantyMaster2);

        warrantyMaster2 = getWarrantyMasterSample2();
        assertThat(warrantyMaster1).isNotEqualTo(warrantyMaster2);
    }

    @Test
    void warrantyMasterPriceHistoryTest() {
        WarrantyMaster warrantyMaster = getWarrantyMasterRandomSampleGenerator();
        WarrantyMasterPriceHistory warrantyMasterPriceHistoryBack = getWarrantyMasterPriceHistoryRandomSampleGenerator();

        warrantyMaster.addWarrantyMasterPriceHistory(warrantyMasterPriceHistoryBack);
        assertThat(warrantyMaster.getWarrantyMasterPriceHistories()).containsOnly(warrantyMasterPriceHistoryBack);
        assertThat(warrantyMasterPriceHistoryBack.getWarrantyMaster()).isEqualTo(warrantyMaster);

        warrantyMaster.removeWarrantyMasterPriceHistory(warrantyMasterPriceHistoryBack);
        assertThat(warrantyMaster.getWarrantyMasterPriceHistories()).doesNotContain(warrantyMasterPriceHistoryBack);
        assertThat(warrantyMasterPriceHistoryBack.getWarrantyMaster()).isNull();

        warrantyMaster.warrantyMasterPriceHistories(new HashSet<>(Set.of(warrantyMasterPriceHistoryBack)));
        assertThat(warrantyMaster.getWarrantyMasterPriceHistories()).containsOnly(warrantyMasterPriceHistoryBack);
        assertThat(warrantyMasterPriceHistoryBack.getWarrantyMaster()).isEqualTo(warrantyMaster);

        warrantyMaster.setWarrantyMasterPriceHistories(new HashSet<>());
        assertThat(warrantyMaster.getWarrantyMasterPriceHistories()).doesNotContain(warrantyMasterPriceHistoryBack);
        assertThat(warrantyMasterPriceHistoryBack.getWarrantyMaster()).isNull();
    }

    @Test
    void articleWarrantyDetailsTest() {
        WarrantyMaster warrantyMaster = getWarrantyMasterRandomSampleGenerator();
        ArticleWarrantyDetails articleWarrantyDetailsBack = getArticleWarrantyDetailsRandomSampleGenerator();

        warrantyMaster.addArticleWarrantyDetails(articleWarrantyDetailsBack);
        assertThat(warrantyMaster.getArticleWarrantyDetails()).containsOnly(articleWarrantyDetailsBack);
        assertThat(articleWarrantyDetailsBack.getWarrantyMaster()).isEqualTo(warrantyMaster);

        warrantyMaster.removeArticleWarrantyDetails(articleWarrantyDetailsBack);
        assertThat(warrantyMaster.getArticleWarrantyDetails()).doesNotContain(articleWarrantyDetailsBack);
        assertThat(articleWarrantyDetailsBack.getWarrantyMaster()).isNull();

        warrantyMaster.articleWarrantyDetails(new HashSet<>(Set.of(articleWarrantyDetailsBack)));
        assertThat(warrantyMaster.getArticleWarrantyDetails()).containsOnly(articleWarrantyDetailsBack);
        assertThat(articleWarrantyDetailsBack.getWarrantyMaster()).isEqualTo(warrantyMaster);

        warrantyMaster.setArticleWarrantyDetails(new HashSet<>());
        assertThat(warrantyMaster.getArticleWarrantyDetails()).doesNotContain(articleWarrantyDetailsBack);
        assertThat(articleWarrantyDetailsBack.getWarrantyMaster()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        WarrantyMaster warrantyMaster = getWarrantyMasterRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        warrantyMaster.addAdditionalAttribute(additionalAttributeBack);
        assertThat(warrantyMaster.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getWarrantyMaster()).isEqualTo(warrantyMaster);

        warrantyMaster.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(warrantyMaster.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getWarrantyMaster()).isNull();

        warrantyMaster.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(warrantyMaster.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getWarrantyMaster()).isEqualTo(warrantyMaster);

        warrantyMaster.setAdditionalAttributes(new HashSet<>());
        assertThat(warrantyMaster.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getWarrantyMaster()).isNull();
    }

    @Test
    void productTest() {
        WarrantyMaster warrantyMaster = getWarrantyMasterRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        warrantyMaster.setProduct(productBack);
        assertThat(warrantyMaster.getProduct()).isEqualTo(productBack);

        warrantyMaster.product(null);
        assertThat(warrantyMaster.getProduct()).isNull();
    }
}
