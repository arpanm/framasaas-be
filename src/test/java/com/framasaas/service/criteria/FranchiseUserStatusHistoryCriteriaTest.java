package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FranchiseUserStatusHistoryCriteriaTest {

    @Test
    void newFranchiseUserStatusHistoryCriteriaHasAllFiltersNullTest() {
        var franchiseUserStatusHistoryCriteria = new FranchiseUserStatusHistoryCriteria();
        assertThat(franchiseUserStatusHistoryCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void franchiseUserStatusHistoryCriteriaFluentMethodsCreatesFiltersTest() {
        var franchiseUserStatusHistoryCriteria = new FranchiseUserStatusHistoryCriteria();

        setAllFilters(franchiseUserStatusHistoryCriteria);

        assertThat(franchiseUserStatusHistoryCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void franchiseUserStatusHistoryCriteriaCopyCreatesNullFilterTest() {
        var franchiseUserStatusHistoryCriteria = new FranchiseUserStatusHistoryCriteria();
        var copy = franchiseUserStatusHistoryCriteria.copy();

        assertThat(franchiseUserStatusHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseUserStatusHistoryCriteria)
        );
    }

    @Test
    void franchiseUserStatusHistoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var franchiseUserStatusHistoryCriteria = new FranchiseUserStatusHistoryCriteria();
        setAllFilters(franchiseUserStatusHistoryCriteria);

        var copy = franchiseUserStatusHistoryCriteria.copy();

        assertThat(franchiseUserStatusHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseUserStatusHistoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var franchiseUserStatusHistoryCriteria = new FranchiseUserStatusHistoryCriteria();

        assertThat(franchiseUserStatusHistoryCriteria).hasToString("FranchiseUserStatusHistoryCriteria{}");
    }

    private static void setAllFilters(FranchiseUserStatusHistoryCriteria franchiseUserStatusHistoryCriteria) {
        franchiseUserStatusHistoryCriteria.id();
        franchiseUserStatusHistoryCriteria.userSatus();
        franchiseUserStatusHistoryCriteria.updatedBy();
        franchiseUserStatusHistoryCriteria.updatedTime();
        franchiseUserStatusHistoryCriteria.franchiseUserId();
        franchiseUserStatusHistoryCriteria.distinct();
    }

    private static Condition<FranchiseUserStatusHistoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getUserSatus()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getFranchiseUserId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FranchiseUserStatusHistoryCriteria> copyFiltersAre(
        FranchiseUserStatusHistoryCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getUserSatus(), copy.getUserSatus()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getFranchiseUserId(), copy.getFranchiseUserId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
