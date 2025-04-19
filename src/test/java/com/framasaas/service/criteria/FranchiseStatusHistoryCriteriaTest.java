package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FranchiseStatusHistoryCriteriaTest {

    @Test
    void newFranchiseStatusHistoryCriteriaHasAllFiltersNullTest() {
        var franchiseStatusHistoryCriteria = new FranchiseStatusHistoryCriteria();
        assertThat(franchiseStatusHistoryCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void franchiseStatusHistoryCriteriaFluentMethodsCreatesFiltersTest() {
        var franchiseStatusHistoryCriteria = new FranchiseStatusHistoryCriteria();

        setAllFilters(franchiseStatusHistoryCriteria);

        assertThat(franchiseStatusHistoryCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void franchiseStatusHistoryCriteriaCopyCreatesNullFilterTest() {
        var franchiseStatusHistoryCriteria = new FranchiseStatusHistoryCriteria();
        var copy = franchiseStatusHistoryCriteria.copy();

        assertThat(franchiseStatusHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseStatusHistoryCriteria)
        );
    }

    @Test
    void franchiseStatusHistoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var franchiseStatusHistoryCriteria = new FranchiseStatusHistoryCriteria();
        setAllFilters(franchiseStatusHistoryCriteria);

        var copy = franchiseStatusHistoryCriteria.copy();

        assertThat(franchiseStatusHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseStatusHistoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var franchiseStatusHistoryCriteria = new FranchiseStatusHistoryCriteria();

        assertThat(franchiseStatusHistoryCriteria).hasToString("FranchiseStatusHistoryCriteria{}");
    }

    private static void setAllFilters(FranchiseStatusHistoryCriteria franchiseStatusHistoryCriteria) {
        franchiseStatusHistoryCriteria.id();
        franchiseStatusHistoryCriteria.franchiseSatus();
        franchiseStatusHistoryCriteria.updatedBy();
        franchiseStatusHistoryCriteria.updatedTime();
        franchiseStatusHistoryCriteria.additionalAttributeId();
        franchiseStatusHistoryCriteria.franchiseId();
        franchiseStatusHistoryCriteria.distinct();
    }

    private static Condition<FranchiseStatusHistoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getFranchiseSatus()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FranchiseStatusHistoryCriteria> copyFiltersAre(
        FranchiseStatusHistoryCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getFranchiseSatus(), copy.getFranchiseSatus()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseId(), copy.getFranchiseId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
