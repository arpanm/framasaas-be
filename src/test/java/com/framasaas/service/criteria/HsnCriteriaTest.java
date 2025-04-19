package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class HsnCriteriaTest {

    @Test
    void newHsnCriteriaHasAllFiltersNullTest() {
        var hsnCriteria = new HsnCriteria();
        assertThat(hsnCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void hsnCriteriaFluentMethodsCreatesFiltersTest() {
        var hsnCriteria = new HsnCriteria();

        setAllFilters(hsnCriteria);

        assertThat(hsnCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void hsnCriteriaCopyCreatesNullFilterTest() {
        var hsnCriteria = new HsnCriteria();
        var copy = hsnCriteria.copy();

        assertThat(hsnCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(hsnCriteria)
        );
    }

    @Test
    void hsnCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var hsnCriteria = new HsnCriteria();
        setAllFilters(hsnCriteria);

        var copy = hsnCriteria.copy();

        assertThat(hsnCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(hsnCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var hsnCriteria = new HsnCriteria();

        assertThat(hsnCriteria).hasToString("HsnCriteria{}");
    }

    private static void setAllFilters(HsnCriteria hsnCriteria) {
        hsnCriteria.id();
        hsnCriteria.hsnCD();
        hsnCriteria.description();
        hsnCriteria.taxRate();
        hsnCriteria.isActive();
        hsnCriteria.createddBy();
        hsnCriteria.createdTime();
        hsnCriteria.updatedBy();
        hsnCriteria.updatedTime();
        hsnCriteria.productId();
        hsnCriteria.additionalAttributeId();
        hsnCriteria.distinct();
    }

    private static Condition<HsnCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getHsnCD()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getTaxRate()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getProductId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<HsnCriteria> copyFiltersAre(HsnCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getHsnCD(), copy.getHsnCD()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getTaxRate(), copy.getTaxRate()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getProductId(), copy.getProductId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
