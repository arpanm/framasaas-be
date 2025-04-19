package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ArticleWarrantyDetailsCriteriaTest {

    @Test
    void newArticleWarrantyDetailsCriteriaHasAllFiltersNullTest() {
        var articleWarrantyDetailsCriteria = new ArticleWarrantyDetailsCriteria();
        assertThat(articleWarrantyDetailsCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void articleWarrantyDetailsCriteriaFluentMethodsCreatesFiltersTest() {
        var articleWarrantyDetailsCriteria = new ArticleWarrantyDetailsCriteria();

        setAllFilters(articleWarrantyDetailsCriteria);

        assertThat(articleWarrantyDetailsCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void articleWarrantyDetailsCriteriaCopyCreatesNullFilterTest() {
        var articleWarrantyDetailsCriteria = new ArticleWarrantyDetailsCriteria();
        var copy = articleWarrantyDetailsCriteria.copy();

        assertThat(articleWarrantyDetailsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(articleWarrantyDetailsCriteria)
        );
    }

    @Test
    void articleWarrantyDetailsCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var articleWarrantyDetailsCriteria = new ArticleWarrantyDetailsCriteria();
        setAllFilters(articleWarrantyDetailsCriteria);

        var copy = articleWarrantyDetailsCriteria.copy();

        assertThat(articleWarrantyDetailsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(articleWarrantyDetailsCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var articleWarrantyDetailsCriteria = new ArticleWarrantyDetailsCriteria();

        assertThat(articleWarrantyDetailsCriteria).hasToString("ArticleWarrantyDetailsCriteria{}");
    }

    private static void setAllFilters(ArticleWarrantyDetailsCriteria articleWarrantyDetailsCriteria) {
        articleWarrantyDetailsCriteria.id();
        articleWarrantyDetailsCriteria.warrantyType();
        articleWarrantyDetailsCriteria.vendorArticleWarrantyId();
        articleWarrantyDetailsCriteria.vendorWarrantyMasterId();
        articleWarrantyDetailsCriteria.startDate();
        articleWarrantyDetailsCriteria.endDate();
        articleWarrantyDetailsCriteria.soldBy();
        articleWarrantyDetailsCriteria.soldByUser();
        articleWarrantyDetailsCriteria.soldDate();
        articleWarrantyDetailsCriteria.isValidated();
        articleWarrantyDetailsCriteria.validatedBy();
        articleWarrantyDetailsCriteria.validatedTime();
        articleWarrantyDetailsCriteria.isActive();
        articleWarrantyDetailsCriteria.createddBy();
        articleWarrantyDetailsCriteria.createdTime();
        articleWarrantyDetailsCriteria.updatedBy();
        articleWarrantyDetailsCriteria.updatedTime();
        articleWarrantyDetailsCriteria.supportingDocumentId();
        articleWarrantyDetailsCriteria.additionalAttributeId();
        articleWarrantyDetailsCriteria.articleId();
        articleWarrantyDetailsCriteria.warrantyMasterId();
        articleWarrantyDetailsCriteria.distinct();
    }

    private static Condition<ArticleWarrantyDetailsCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getWarrantyType()) &&
                condition.apply(criteria.getVendorArticleWarrantyId()) &&
                condition.apply(criteria.getVendorWarrantyMasterId()) &&
                condition.apply(criteria.getStartDate()) &&
                condition.apply(criteria.getEndDate()) &&
                condition.apply(criteria.getSoldBy()) &&
                condition.apply(criteria.getSoldByUser()) &&
                condition.apply(criteria.getSoldDate()) &&
                condition.apply(criteria.getIsValidated()) &&
                condition.apply(criteria.getValidatedBy()) &&
                condition.apply(criteria.getValidatedTime()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getSupportingDocumentId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getArticleId()) &&
                condition.apply(criteria.getWarrantyMasterId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ArticleWarrantyDetailsCriteria> copyFiltersAre(
        ArticleWarrantyDetailsCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getWarrantyType(), copy.getWarrantyType()) &&
                condition.apply(criteria.getVendorArticleWarrantyId(), copy.getVendorArticleWarrantyId()) &&
                condition.apply(criteria.getVendorWarrantyMasterId(), copy.getVendorWarrantyMasterId()) &&
                condition.apply(criteria.getStartDate(), copy.getStartDate()) &&
                condition.apply(criteria.getEndDate(), copy.getEndDate()) &&
                condition.apply(criteria.getSoldBy(), copy.getSoldBy()) &&
                condition.apply(criteria.getSoldByUser(), copy.getSoldByUser()) &&
                condition.apply(criteria.getSoldDate(), copy.getSoldDate()) &&
                condition.apply(criteria.getIsValidated(), copy.getIsValidated()) &&
                condition.apply(criteria.getValidatedBy(), copy.getValidatedBy()) &&
                condition.apply(criteria.getValidatedTime(), copy.getValidatedTime()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getSupportingDocumentId(), copy.getSupportingDocumentId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getArticleId(), copy.getArticleId()) &&
                condition.apply(criteria.getWarrantyMasterId(), copy.getWarrantyMasterId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
