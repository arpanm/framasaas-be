package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SupportingDocumentCriteriaTest {

    @Test
    void newSupportingDocumentCriteriaHasAllFiltersNullTest() {
        var supportingDocumentCriteria = new SupportingDocumentCriteria();
        assertThat(supportingDocumentCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void supportingDocumentCriteriaFluentMethodsCreatesFiltersTest() {
        var supportingDocumentCriteria = new SupportingDocumentCriteria();

        setAllFilters(supportingDocumentCriteria);

        assertThat(supportingDocumentCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void supportingDocumentCriteriaCopyCreatesNullFilterTest() {
        var supportingDocumentCriteria = new SupportingDocumentCriteria();
        var copy = supportingDocumentCriteria.copy();

        assertThat(supportingDocumentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(supportingDocumentCriteria)
        );
    }

    @Test
    void supportingDocumentCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var supportingDocumentCriteria = new SupportingDocumentCriteria();
        setAllFilters(supportingDocumentCriteria);

        var copy = supportingDocumentCriteria.copy();

        assertThat(supportingDocumentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(supportingDocumentCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var supportingDocumentCriteria = new SupportingDocumentCriteria();

        assertThat(supportingDocumentCriteria).hasToString("SupportingDocumentCriteria{}");
    }

    private static void setAllFilters(SupportingDocumentCriteria supportingDocumentCriteria) {
        supportingDocumentCriteria.id();
        supportingDocumentCriteria.documentName();
        supportingDocumentCriteria.documentType();
        supportingDocumentCriteria.documentFormat();
        supportingDocumentCriteria.documentSize();
        supportingDocumentCriteria.documentPath();
        supportingDocumentCriteria.isValidated();
        supportingDocumentCriteria.validatedBy();
        supportingDocumentCriteria.validatedTime();
        supportingDocumentCriteria.createddBy();
        supportingDocumentCriteria.createdTime();
        supportingDocumentCriteria.updatedBy();
        supportingDocumentCriteria.updatedTime();
        supportingDocumentCriteria.additionalAttributeId();
        supportingDocumentCriteria.franchiseId();
        supportingDocumentCriteria.articleId();
        supportingDocumentCriteria.articleWarrantyId();
        supportingDocumentCriteria.serviceOrderId();
        supportingDocumentCriteria.distinct();
    }

    private static Condition<SupportingDocumentCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDocumentName()) &&
                condition.apply(criteria.getDocumentType()) &&
                condition.apply(criteria.getDocumentFormat()) &&
                condition.apply(criteria.getDocumentSize()) &&
                condition.apply(criteria.getDocumentPath()) &&
                condition.apply(criteria.getIsValidated()) &&
                condition.apply(criteria.getValidatedBy()) &&
                condition.apply(criteria.getValidatedTime()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseId()) &&
                condition.apply(criteria.getArticleId()) &&
                condition.apply(criteria.getArticleWarrantyId()) &&
                condition.apply(criteria.getServiceOrderId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SupportingDocumentCriteria> copyFiltersAre(
        SupportingDocumentCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDocumentName(), copy.getDocumentName()) &&
                condition.apply(criteria.getDocumentType(), copy.getDocumentType()) &&
                condition.apply(criteria.getDocumentFormat(), copy.getDocumentFormat()) &&
                condition.apply(criteria.getDocumentSize(), copy.getDocumentSize()) &&
                condition.apply(criteria.getDocumentPath(), copy.getDocumentPath()) &&
                condition.apply(criteria.getIsValidated(), copy.getIsValidated()) &&
                condition.apply(criteria.getValidatedBy(), copy.getValidatedBy()) &&
                condition.apply(criteria.getValidatedTime(), copy.getValidatedTime()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseId(), copy.getFranchiseId()) &&
                condition.apply(criteria.getArticleId(), copy.getArticleId()) &&
                condition.apply(criteria.getArticleWarrantyId(), copy.getArticleWarrantyId()) &&
                condition.apply(criteria.getServiceOrderId(), copy.getServiceOrderId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
