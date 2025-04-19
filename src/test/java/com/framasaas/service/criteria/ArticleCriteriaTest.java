package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ArticleCriteriaTest {

    @Test
    void newArticleCriteriaHasAllFiltersNullTest() {
        var articleCriteria = new ArticleCriteria();
        assertThat(articleCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void articleCriteriaFluentMethodsCreatesFiltersTest() {
        var articleCriteria = new ArticleCriteria();

        setAllFilters(articleCriteria);

        assertThat(articleCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void articleCriteriaCopyCreatesNullFilterTest() {
        var articleCriteria = new ArticleCriteria();
        var copy = articleCriteria.copy();

        assertThat(articleCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(articleCriteria)
        );
    }

    @Test
    void articleCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var articleCriteria = new ArticleCriteria();
        setAllFilters(articleCriteria);

        var copy = articleCriteria.copy();

        assertThat(articleCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(articleCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var articleCriteria = new ArticleCriteria();

        assertThat(articleCriteria).hasToString("ArticleCriteria{}");
    }

    private static void setAllFilters(ArticleCriteria articleCriteria) {
        articleCriteria.id();
        articleCriteria.serialNo();
        articleCriteria.vendorArticleId();
        articleCriteria.purchaseDate();
        articleCriteria.puchasePrice();
        articleCriteria.purchaseStore();
        articleCriteria.invoicePath();
        articleCriteria.isValidated();
        articleCriteria.validatedBy();
        articleCriteria.validatedTime();
        articleCriteria.createddBy();
        articleCriteria.createdTime();
        articleCriteria.updatedBy();
        articleCriteria.updatedTime();
        articleCriteria.supportingDocumentId();
        articleCriteria.articleWarrantyDetailsId();
        articleCriteria.serviceOrderId();
        articleCriteria.additionalAttributeId();
        articleCriteria.productId();
        articleCriteria.customerId();
        articleCriteria.distinct();
    }

    private static Condition<ArticleCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSerialNo()) &&
                condition.apply(criteria.getVendorArticleId()) &&
                condition.apply(criteria.getPurchaseDate()) &&
                condition.apply(criteria.getPuchasePrice()) &&
                condition.apply(criteria.getPurchaseStore()) &&
                condition.apply(criteria.getInvoicePath()) &&
                condition.apply(criteria.getIsValidated()) &&
                condition.apply(criteria.getValidatedBy()) &&
                condition.apply(criteria.getValidatedTime()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getSupportingDocumentId()) &&
                condition.apply(criteria.getArticleWarrantyDetailsId()) &&
                condition.apply(criteria.getServiceOrderId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getProductId()) &&
                condition.apply(criteria.getCustomerId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ArticleCriteria> copyFiltersAre(ArticleCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSerialNo(), copy.getSerialNo()) &&
                condition.apply(criteria.getVendorArticleId(), copy.getVendorArticleId()) &&
                condition.apply(criteria.getPurchaseDate(), copy.getPurchaseDate()) &&
                condition.apply(criteria.getPuchasePrice(), copy.getPuchasePrice()) &&
                condition.apply(criteria.getPurchaseStore(), copy.getPurchaseStore()) &&
                condition.apply(criteria.getInvoicePath(), copy.getInvoicePath()) &&
                condition.apply(criteria.getIsValidated(), copy.getIsValidated()) &&
                condition.apply(criteria.getValidatedBy(), copy.getValidatedBy()) &&
                condition.apply(criteria.getValidatedTime(), copy.getValidatedTime()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getSupportingDocumentId(), copy.getSupportingDocumentId()) &&
                condition.apply(criteria.getArticleWarrantyDetailsId(), copy.getArticleWarrantyDetailsId()) &&
                condition.apply(criteria.getServiceOrderId(), copy.getServiceOrderId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getProductId(), copy.getProductId()) &&
                condition.apply(criteria.getCustomerId(), copy.getCustomerId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
