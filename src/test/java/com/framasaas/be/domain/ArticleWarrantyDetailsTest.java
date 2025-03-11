package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.ArticleTestSamples.*;
import static com.framasaas.be.domain.ArticleWarrantyDetailsDocumentTestSamples.*;
import static com.framasaas.be.domain.ArticleWarrantyDetailsTestSamples.*;
import static com.framasaas.be.domain.WarrantyMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ArticleWarrantyDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleWarrantyDetails.class);
        ArticleWarrantyDetails articleWarrantyDetails1 = getArticleWarrantyDetailsSample1();
        ArticleWarrantyDetails articleWarrantyDetails2 = new ArticleWarrantyDetails();
        assertThat(articleWarrantyDetails1).isNotEqualTo(articleWarrantyDetails2);

        articleWarrantyDetails2.setId(articleWarrantyDetails1.getId());
        assertThat(articleWarrantyDetails1).isEqualTo(articleWarrantyDetails2);

        articleWarrantyDetails2 = getArticleWarrantyDetailsSample2();
        assertThat(articleWarrantyDetails1).isNotEqualTo(articleWarrantyDetails2);
    }

    @Test
    void articleWarrantyDetailsDocumentTest() {
        ArticleWarrantyDetails articleWarrantyDetails = getArticleWarrantyDetailsRandomSampleGenerator();
        ArticleWarrantyDetailsDocument articleWarrantyDetailsDocumentBack = getArticleWarrantyDetailsDocumentRandomSampleGenerator();

        articleWarrantyDetails.addArticleWarrantyDetailsDocument(articleWarrantyDetailsDocumentBack);
        assertThat(articleWarrantyDetails.getArticleWarrantyDetailsDocuments()).containsOnly(articleWarrantyDetailsDocumentBack);
        assertThat(articleWarrantyDetailsDocumentBack.getArticleWarranty()).isEqualTo(articleWarrantyDetails);

        articleWarrantyDetails.removeArticleWarrantyDetailsDocument(articleWarrantyDetailsDocumentBack);
        assertThat(articleWarrantyDetails.getArticleWarrantyDetailsDocuments()).doesNotContain(articleWarrantyDetailsDocumentBack);
        assertThat(articleWarrantyDetailsDocumentBack.getArticleWarranty()).isNull();

        articleWarrantyDetails.articleWarrantyDetailsDocuments(new HashSet<>(Set.of(articleWarrantyDetailsDocumentBack)));
        assertThat(articleWarrantyDetails.getArticleWarrantyDetailsDocuments()).containsOnly(articleWarrantyDetailsDocumentBack);
        assertThat(articleWarrantyDetailsDocumentBack.getArticleWarranty()).isEqualTo(articleWarrantyDetails);

        articleWarrantyDetails.setArticleWarrantyDetailsDocuments(new HashSet<>());
        assertThat(articleWarrantyDetails.getArticleWarrantyDetailsDocuments()).doesNotContain(articleWarrantyDetailsDocumentBack);
        assertThat(articleWarrantyDetailsDocumentBack.getArticleWarranty()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        ArticleWarrantyDetails articleWarrantyDetails = getArticleWarrantyDetailsRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        articleWarrantyDetails.addAdditionalAttribute(additionalAttributeBack);
        assertThat(articleWarrantyDetails.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticleWarranty()).isEqualTo(articleWarrantyDetails);

        articleWarrantyDetails.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(articleWarrantyDetails.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticleWarranty()).isNull();

        articleWarrantyDetails.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(articleWarrantyDetails.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticleWarranty()).isEqualTo(articleWarrantyDetails);

        articleWarrantyDetails.setAdditionalAttributes(new HashSet<>());
        assertThat(articleWarrantyDetails.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticleWarranty()).isNull();
    }

    @Test
    void articleTest() {
        ArticleWarrantyDetails articleWarrantyDetails = getArticleWarrantyDetailsRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        articleWarrantyDetails.setArticle(articleBack);
        assertThat(articleWarrantyDetails.getArticle()).isEqualTo(articleBack);

        articleWarrantyDetails.article(null);
        assertThat(articleWarrantyDetails.getArticle()).isNull();
    }

    @Test
    void warrantyMasterTest() {
        ArticleWarrantyDetails articleWarrantyDetails = getArticleWarrantyDetailsRandomSampleGenerator();
        WarrantyMaster warrantyMasterBack = getWarrantyMasterRandomSampleGenerator();

        articleWarrantyDetails.setWarrantyMaster(warrantyMasterBack);
        assertThat(articleWarrantyDetails.getWarrantyMaster()).isEqualTo(warrantyMasterBack);

        articleWarrantyDetails.warrantyMaster(null);
        assertThat(articleWarrantyDetails.getWarrantyMaster()).isNull();
    }
}
