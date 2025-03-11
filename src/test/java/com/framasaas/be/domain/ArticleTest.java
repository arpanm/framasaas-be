package com.framasaas.be.domain;

import static com.framasaas.be.domain.AdditionalAttributeTestSamples.*;
import static com.framasaas.be.domain.ArticleTestSamples.*;
import static com.framasaas.be.domain.ArticleWarrantyDetailsTestSamples.*;
import static com.framasaas.be.domain.CustomerTestSamples.*;
import static com.framasaas.be.domain.ProductTestSamples.*;
import static com.framasaas.be.domain.ServiceOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.framasaas.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ArticleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
        Article article1 = getArticleSample1();
        Article article2 = new Article();
        assertThat(article1).isNotEqualTo(article2);

        article2.setId(article1.getId());
        assertThat(article1).isEqualTo(article2);

        article2 = getArticleSample2();
        assertThat(article1).isNotEqualTo(article2);
    }

    @Test
    void articleWarrantyDetailsTest() {
        Article article = getArticleRandomSampleGenerator();
        ArticleWarrantyDetails articleWarrantyDetailsBack = getArticleWarrantyDetailsRandomSampleGenerator();

        article.addArticleWarrantyDetails(articleWarrantyDetailsBack);
        assertThat(article.getArticleWarrantyDetails()).containsOnly(articleWarrantyDetailsBack);
        assertThat(articleWarrantyDetailsBack.getArticle()).isEqualTo(article);

        article.removeArticleWarrantyDetails(articleWarrantyDetailsBack);
        assertThat(article.getArticleWarrantyDetails()).doesNotContain(articleWarrantyDetailsBack);
        assertThat(articleWarrantyDetailsBack.getArticle()).isNull();

        article.articleWarrantyDetails(new HashSet<>(Set.of(articleWarrantyDetailsBack)));
        assertThat(article.getArticleWarrantyDetails()).containsOnly(articleWarrantyDetailsBack);
        assertThat(articleWarrantyDetailsBack.getArticle()).isEqualTo(article);

        article.setArticleWarrantyDetails(new HashSet<>());
        assertThat(article.getArticleWarrantyDetails()).doesNotContain(articleWarrantyDetailsBack);
        assertThat(articleWarrantyDetailsBack.getArticle()).isNull();
    }

    @Test
    void serviceOrderTest() {
        Article article = getArticleRandomSampleGenerator();
        ServiceOrder serviceOrderBack = getServiceOrderRandomSampleGenerator();

        article.addServiceOrder(serviceOrderBack);
        assertThat(article.getServiceOrders()).containsOnly(serviceOrderBack);
        assertThat(serviceOrderBack.getArticle()).isEqualTo(article);

        article.removeServiceOrder(serviceOrderBack);
        assertThat(article.getServiceOrders()).doesNotContain(serviceOrderBack);
        assertThat(serviceOrderBack.getArticle()).isNull();

        article.serviceOrders(new HashSet<>(Set.of(serviceOrderBack)));
        assertThat(article.getServiceOrders()).containsOnly(serviceOrderBack);
        assertThat(serviceOrderBack.getArticle()).isEqualTo(article);

        article.setServiceOrders(new HashSet<>());
        assertThat(article.getServiceOrders()).doesNotContain(serviceOrderBack);
        assertThat(serviceOrderBack.getArticle()).isNull();
    }

    @Test
    void additionalAttributeTest() {
        Article article = getArticleRandomSampleGenerator();
        AdditionalAttribute additionalAttributeBack = getAdditionalAttributeRandomSampleGenerator();

        article.addAdditionalAttribute(additionalAttributeBack);
        assertThat(article.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticle()).isEqualTo(article);

        article.removeAdditionalAttribute(additionalAttributeBack);
        assertThat(article.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticle()).isNull();

        article.additionalAttributes(new HashSet<>(Set.of(additionalAttributeBack)));
        assertThat(article.getAdditionalAttributes()).containsOnly(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticle()).isEqualTo(article);

        article.setAdditionalAttributes(new HashSet<>());
        assertThat(article.getAdditionalAttributes()).doesNotContain(additionalAttributeBack);
        assertThat(additionalAttributeBack.getArticle()).isNull();
    }

    @Test
    void productTest() {
        Article article = getArticleRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        article.setProduct(productBack);
        assertThat(article.getProduct()).isEqualTo(productBack);

        article.product(null);
        assertThat(article.getProduct()).isNull();
    }

    @Test
    void customerTest() {
        Article article = getArticleRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        article.setCustomer(customerBack);
        assertThat(article.getCustomer()).isEqualTo(customerBack);

        article.customer(null);
        assertThat(article.getCustomer()).isNull();
    }
}
