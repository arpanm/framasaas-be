package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.ArticleWarrantyDetailsTestSamples.*;
import static com.framasaas.be.domain.FranchiseTestSamples.*;
import static com.framasaas.be.domain.SupportingDocumentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
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
    void articleWarrantyTest() {
        SupportingDocument supportingDocument = getSupportingDocumentRandomSampleGenerator();
        ArticleWarrantyDetails articleWarrantyDetailsBack = getArticleWarrantyDetailsRandomSampleGenerator();

        supportingDocument.setArticleWarranty(articleWarrantyDetailsBack);
        assertThat(supportingDocument.getArticleWarranty()).isEqualTo(articleWarrantyDetailsBack);

        supportingDocument.articleWarranty(null);
        assertThat(supportingDocument.getArticleWarranty()).isNull();
    }
}
