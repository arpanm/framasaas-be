package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FranchiseAllocationRuleSetCriteriaTest {

    @Test
    void newFranchiseAllocationRuleSetCriteriaHasAllFiltersNullTest() {
        var franchiseAllocationRuleSetCriteria = new FranchiseAllocationRuleSetCriteria();
        assertThat(franchiseAllocationRuleSetCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void franchiseAllocationRuleSetCriteriaFluentMethodsCreatesFiltersTest() {
        var franchiseAllocationRuleSetCriteria = new FranchiseAllocationRuleSetCriteria();

        setAllFilters(franchiseAllocationRuleSetCriteria);

        assertThat(franchiseAllocationRuleSetCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void franchiseAllocationRuleSetCriteriaCopyCreatesNullFilterTest() {
        var franchiseAllocationRuleSetCriteria = new FranchiseAllocationRuleSetCriteria();
        var copy = franchiseAllocationRuleSetCriteria.copy();

        assertThat(franchiseAllocationRuleSetCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseAllocationRuleSetCriteria)
        );
    }

    @Test
    void franchiseAllocationRuleSetCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var franchiseAllocationRuleSetCriteria = new FranchiseAllocationRuleSetCriteria();
        setAllFilters(franchiseAllocationRuleSetCriteria);

        var copy = franchiseAllocationRuleSetCriteria.copy();

        assertThat(franchiseAllocationRuleSetCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseAllocationRuleSetCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var franchiseAllocationRuleSetCriteria = new FranchiseAllocationRuleSetCriteria();

        assertThat(franchiseAllocationRuleSetCriteria).hasToString("FranchiseAllocationRuleSetCriteria{}");
    }

    private static void setAllFilters(FranchiseAllocationRuleSetCriteria franchiseAllocationRuleSetCriteria) {
        franchiseAllocationRuleSetCriteria.id();
        franchiseAllocationRuleSetCriteria.name();
        franchiseAllocationRuleSetCriteria.sortType();
        franchiseAllocationRuleSetCriteria.priority();
        franchiseAllocationRuleSetCriteria.isActive();
        franchiseAllocationRuleSetCriteria.createddBy();
        franchiseAllocationRuleSetCriteria.createdTime();
        franchiseAllocationRuleSetCriteria.updatedBy();
        franchiseAllocationRuleSetCriteria.updatedTime();
        franchiseAllocationRuleSetCriteria.franchiseId();
        franchiseAllocationRuleSetCriteria.additionalAttributeId();
        franchiseAllocationRuleSetCriteria.distinct();
    }

    private static Condition<FranchiseAllocationRuleSetCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getSortType()) &&
                condition.apply(criteria.getPriority()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getFranchiseId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FranchiseAllocationRuleSetCriteria> copyFiltersAre(
        FranchiseAllocationRuleSetCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getSortType(), copy.getSortType()) &&
                condition.apply(criteria.getPriority(), copy.getPriority()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getFranchiseId(), copy.getFranchiseId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
