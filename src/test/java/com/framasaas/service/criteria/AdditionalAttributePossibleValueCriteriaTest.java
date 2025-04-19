package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AdditionalAttributePossibleValueCriteriaTest {

    @Test
    void newAdditionalAttributePossibleValueCriteriaHasAllFiltersNullTest() {
        var additionalAttributePossibleValueCriteria = new AdditionalAttributePossibleValueCriteria();
        assertThat(additionalAttributePossibleValueCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void additionalAttributePossibleValueCriteriaFluentMethodsCreatesFiltersTest() {
        var additionalAttributePossibleValueCriteria = new AdditionalAttributePossibleValueCriteria();

        setAllFilters(additionalAttributePossibleValueCriteria);

        assertThat(additionalAttributePossibleValueCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void additionalAttributePossibleValueCriteriaCopyCreatesNullFilterTest() {
        var additionalAttributePossibleValueCriteria = new AdditionalAttributePossibleValueCriteria();
        var copy = additionalAttributePossibleValueCriteria.copy();

        assertThat(additionalAttributePossibleValueCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(additionalAttributePossibleValueCriteria)
        );
    }

    @Test
    void additionalAttributePossibleValueCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var additionalAttributePossibleValueCriteria = new AdditionalAttributePossibleValueCriteria();
        setAllFilters(additionalAttributePossibleValueCriteria);

        var copy = additionalAttributePossibleValueCriteria.copy();

        assertThat(additionalAttributePossibleValueCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(additionalAttributePossibleValueCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var additionalAttributePossibleValueCriteria = new AdditionalAttributePossibleValueCriteria();

        assertThat(additionalAttributePossibleValueCriteria).hasToString("AdditionalAttributePossibleValueCriteria{}");
    }

    private static void setAllFilters(AdditionalAttributePossibleValueCriteria additionalAttributePossibleValueCriteria) {
        additionalAttributePossibleValueCriteria.id();
        additionalAttributePossibleValueCriteria.possibleValue();
        additionalAttributePossibleValueCriteria.createddBy();
        additionalAttributePossibleValueCriteria.createdTime();
        additionalAttributePossibleValueCriteria.updatedBy();
        additionalAttributePossibleValueCriteria.updatedTime();
        additionalAttributePossibleValueCriteria.attributeId();
        additionalAttributePossibleValueCriteria.distinct();
    }

    private static Condition<AdditionalAttributePossibleValueCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPossibleValue()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAttributeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AdditionalAttributePossibleValueCriteria> copyFiltersAre(
        AdditionalAttributePossibleValueCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPossibleValue(), copy.getPossibleValue()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAttributeId(), copy.getAttributeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
