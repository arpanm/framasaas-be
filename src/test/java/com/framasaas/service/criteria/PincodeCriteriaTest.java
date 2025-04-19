package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PincodeCriteriaTest {

    @Test
    void newPincodeCriteriaHasAllFiltersNullTest() {
        var pincodeCriteria = new PincodeCriteria();
        assertThat(pincodeCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void pincodeCriteriaFluentMethodsCreatesFiltersTest() {
        var pincodeCriteria = new PincodeCriteria();

        setAllFilters(pincodeCriteria);

        assertThat(pincodeCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void pincodeCriteriaCopyCreatesNullFilterTest() {
        var pincodeCriteria = new PincodeCriteria();
        var copy = pincodeCriteria.copy();

        assertThat(pincodeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(pincodeCriteria)
        );
    }

    @Test
    void pincodeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var pincodeCriteria = new PincodeCriteria();
        setAllFilters(pincodeCriteria);

        var copy = pincodeCriteria.copy();

        assertThat(pincodeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(pincodeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var pincodeCriteria = new PincodeCriteria();

        assertThat(pincodeCriteria).hasToString("PincodeCriteria{}");
    }

    private static void setAllFilters(PincodeCriteria pincodeCriteria) {
        pincodeCriteria.id();
        pincodeCriteria.pincode();
        pincodeCriteria.createddBy();
        pincodeCriteria.createdTime();
        pincodeCriteria.updatedBy();
        pincodeCriteria.updatedTime();
        pincodeCriteria.franchiseRuleId();
        pincodeCriteria.fieldAgentSkillRuleId();
        pincodeCriteria.distinct();
    }

    private static Condition<PincodeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPincode()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getFranchiseRuleId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PincodeCriteria> copyFiltersAre(PincodeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPincode(), copy.getPincode()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getFranchiseRuleId(), copy.getFranchiseRuleId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleId(), copy.getFieldAgentSkillRuleId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
