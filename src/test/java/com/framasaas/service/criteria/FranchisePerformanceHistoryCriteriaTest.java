package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FranchisePerformanceHistoryCriteriaTest {

    @Test
    void newFranchisePerformanceHistoryCriteriaHasAllFiltersNullTest() {
        var franchisePerformanceHistoryCriteria = new FranchisePerformanceHistoryCriteria();
        assertThat(franchisePerformanceHistoryCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void franchisePerformanceHistoryCriteriaFluentMethodsCreatesFiltersTest() {
        var franchisePerformanceHistoryCriteria = new FranchisePerformanceHistoryCriteria();

        setAllFilters(franchisePerformanceHistoryCriteria);

        assertThat(franchisePerformanceHistoryCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void franchisePerformanceHistoryCriteriaCopyCreatesNullFilterTest() {
        var franchisePerformanceHistoryCriteria = new FranchisePerformanceHistoryCriteria();
        var copy = franchisePerformanceHistoryCriteria.copy();

        assertThat(franchisePerformanceHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(franchisePerformanceHistoryCriteria)
        );
    }

    @Test
    void franchisePerformanceHistoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var franchisePerformanceHistoryCriteria = new FranchisePerformanceHistoryCriteria();
        setAllFilters(franchisePerformanceHistoryCriteria);

        var copy = franchisePerformanceHistoryCriteria.copy();

        assertThat(franchisePerformanceHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(franchisePerformanceHistoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var franchisePerformanceHistoryCriteria = new FranchisePerformanceHistoryCriteria();

        assertThat(franchisePerformanceHistoryCriteria).hasToString("FranchisePerformanceHistoryCriteria{}");
    }

    private static void setAllFilters(FranchisePerformanceHistoryCriteria franchisePerformanceHistoryCriteria) {
        franchisePerformanceHistoryCriteria.id();
        franchisePerformanceHistoryCriteria.performanceScore();
        franchisePerformanceHistoryCriteria.performanceTag();
        franchisePerformanceHistoryCriteria.updatedBy();
        franchisePerformanceHistoryCriteria.updatedTime();
        franchisePerformanceHistoryCriteria.createddBy();
        franchisePerformanceHistoryCriteria.createdTime();
        franchisePerformanceHistoryCriteria.additionalAttributeId();
        franchisePerformanceHistoryCriteria.franchiseId();
        franchisePerformanceHistoryCriteria.distinct();
    }

    private static Condition<FranchisePerformanceHistoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPerformanceScore()) &&
                condition.apply(criteria.getPerformanceTag()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FranchisePerformanceHistoryCriteria> copyFiltersAre(
        FranchisePerformanceHistoryCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPerformanceScore(), copy.getPerformanceScore()) &&
                condition.apply(criteria.getPerformanceTag(), copy.getPerformanceTag()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseId(), copy.getFranchiseId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
