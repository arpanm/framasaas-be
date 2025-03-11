package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.ArticleWarrantyDetailsDocumentTestSamples.*;
import static com.framasaas.be.domain.ArticleWarrantyDetailsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ArticleWarrantyDetailsDocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleWarrantyDetailsDocument.class);
        ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument1 = getArticleWarrantyDetailsDocumentSample1();
        ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument2 = new ArticleWarrantyDetailsDocument();
        assertThat(articleWarrantyDetailsDocument1).isNotEqualTo(articleWarrantyDetailsDocument2);

        articleWarrantyDetailsDocument2.setId(articleWarrantyDetailsDocument1.getId());
        assertThat(articleWarrantyDetailsDocument1).isEqualTo(articleWarrantyDetailsDocument2);

        articleWarrantyDetailsDocument2 = getArticleWarrantyDetailsDocumentSample2();
        assertThat(articleWarrantyDetailsDocument1).isNotEqualTo(articleWarrantyDetailsDocument2);
    }

    @Test
    void additionalAttributeTest() {
        ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument = getArticleWarrantyDetailsDocumentRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        articleWarrantyDetailsDocument.addAdditionalAttribute(additionalAttributeBack);
        assertThat(articleWarrantyDetailsDocument.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticleWarrantyDocument()).isEqualTo(articleWarrantyDetailsDocument);

        articleWarrantyDetailsDocument.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(articleWarrantyDetailsDocument.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticleWarrantyDocument()).isNull();

        articleWarrantyDetailsDocument.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(articleWarrantyDetailsDocument.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticleWarrantyDocument()).isEqualTo(articleWarrantyDetailsDocument);

        articleWarrantyDetailsDocument.setAdditionalAttributes(new HashSet<>());
        assertThat(articleWarrantyDetailsDocument.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticleWarrantyDocument()).isNull();
    }

    @Test
    void articleWarrantyTest() {
        ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument = getArticleWarrantyDetailsDocumentRandomSampleGenerator();
        ArticleWarrantyDetails articleWarrantyDetailsBack = getArticleWarrantyDetailsRandomSampleGenerator();

        articleWarrantyDetailsDocument.setArticleWarranty(articleWarrantyDetailsBack);
        assertThat(articleWarrantyDetailsDocument.getArticleWarranty()).isEqualTo(articleWarrantyDetailsBack);

        articleWarrantyDetailsDocument.articleWarranty(null);
        assertThat(articleWarrantyDetailsDocument.getArticleWarranty()).isNull();
    }
}
