package com.framasaas.domain;

import static com.framasaas.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.domain.ArticleTestSamples.*;
import static com.framasaas.domain.ArticleWarrantyDetailsTestSamples.*;
import static com.framasaas.domain.FranchiseTestSamples.*;
import static com.framasaas.domain.ServiceOrderTestSamples.*;
import static com.framasaas.domain.SupportingDocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SupportingDocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportingDocument.class);
        SupportingDocument supportingDocument1 = getSupportingDocumentSample1();
        SupportingDocument supportingDocument2 = new SupportingDocument();
        assertThat(supportingDocument1).isNotEqualTo(supportingDocument2);

        supportingDocument2.setId(supportingDocument1.getId());
        assertThat(supportingDocument1).isEqualTo(supportingDocument2);

        supportingDocument2 = getSupportingDocumentSample2();
        assertThat(supportingDocument1).isNotEqualTo(supportingDocument2);
    }

    @Test
    void additionalAttributeTest() {
        SupportingDocument supportingDocument = getSupportingDocumentRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        supportingDocument.addAdditionalAttribute(additionalAttributeBack);
        assertThat(supportingDocument.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getSupportDocument()).isEqualTo(supportingDocument);

        supportingDocument.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(supportingDocument.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getSupportDocument()).isNull();

        supportingDocument.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(supportingDocument.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getSupportDocument()).isEqualTo(supportingDocument);

        supportingDocument.setAdditionalAttributes(new HashSet<>());
        assertThat(supportingDocument.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getSupportDocument()).isNull();
    }

    @Test
    void franchiseTest() {
        SupportingDocument supportingDocument = getSupportingDocumentRandomSampleGenerator();
        Franchise franchiseBack = getFranchiseRandomSampleGenerator();

        supportingDocument.setFranchise(franchiseBack);
        assertThat(supportingDocument.getFranchise()).isEqualTo(franchiseBack);

        supportingDocument.franchise(null);
        assertThat(supportingDocument.getFranchise()).isNull();
    }

    @Test
    void articleTest() {
        SupportingDocument supportingDocument = getSupportingDocumentRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        supportingDocument.setArticle(articleBack);
        assertThat(supportingDocument.getArticle()).isEqualTo(articleBack);

        supportingDocument.article(null);
        assertThat(supportingDocument.getArticle()).isNull();
    }

    @Test
    void articleWarrantyTest() {
        SupportingDocument supportingDocument = getSupportingDocumentRandomSampleGenerator();
        ArticleWarrantyDetails articleWarrantyDetailsBack = getArticleWarrantyDetailsRandomSampleGenerator();

        supportingDocument.setArticleWarranty(articleWarrantyDetailsBack);
        assertThat(supportingDocument.getArticleWarranty()).isEqualTo(articleWarrantyDetailsBack);

        supportingDocument.articleWarranty(null);
        assertThat(supportingDocument.getArticleWarranty()).isNull();
    }

    @Test
    void serviceOrderTest() {
        SupportingDocument supportingDocument = getSupportingDocumentRandomSampleGenerator();
        ServiceOrder serviceOrderBack = getServiceOrderRandomSampleGenerator();

        supportingDocument.setServiceOrder(serviceOrderBack);
        assertThat(supportingDocument.getServiceOrder()).isEqualTo(serviceOrderBack);

        supportingDocument.serviceOrder(null);
        assertThat(supportingDocument.getServiceOrder()).isNull();
    }
}
