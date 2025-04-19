package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FieldAgentSkillRuleSetCriteriaTest {

    @Test
    void newFieldAgentSkillRuleSetCriteriaHasAllFiltersNullTest() {
        var fieldAgentSkillRuleSetCriteria = new FieldAgentSkillRuleSetCriteria();
        assertThat(fieldAgentSkillRuleSetCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void fieldAgentSkillRuleSetCriteriaFluentMethodsCreatesFiltersTest() {
        var fieldAgentSkillRuleSetCriteria = new FieldAgentSkillRuleSetCriteria();

        setAllFilters(fieldAgentSkillRuleSetCriteria);

        assertThat(fieldAgentSkillRuleSetCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void fieldAgentSkillRuleSetCriteriaCopyCreatesNullFilterTest() {
        var fieldAgentSkillRuleSetCriteria = new FieldAgentSkillRuleSetCriteria();
        var copy = fieldAgentSkillRuleSetCriteria.copy();

        assertThat(fieldAgentSkillRuleSetCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(fieldAgentSkillRuleSetCriteria)
        );
    }

    @Test
    void fieldAgentSkillRuleSetCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var fieldAgentSkillRuleSetCriteria = new FieldAgentSkillRuleSetCriteria();
        setAllFilters(fieldAgentSkillRuleSetCriteria);

        var copy = fieldAgentSkillRuleSetCriteria.copy();

        assertThat(fieldAgentSkillRuleSetCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(fieldAgentSkillRuleSetCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var fieldAgentSkillRuleSetCriteria = new FieldAgentSkillRuleSetCriteria();

        assertThat(fieldAgentSkillRuleSetCriteria).hasToString("FieldAgentSkillRuleSetCriteria{}");
    }

    private static void setAllFilters(FieldAgentSkillRuleSetCriteria fieldAgentSkillRuleSetCriteria) {
        fieldAgentSkillRuleSetCriteria.id();
        fieldAgentSkillRuleSetCriteria.sortType();
        fieldAgentSkillRuleSetCriteria.createddBy();
        fieldAgentSkillRuleSetCriteria.createdTime();
        fieldAgentSkillRuleSetCriteria.updatedBy();
        fieldAgentSkillRuleSetCriteria.updatedTime();
        fieldAgentSkillRuleSetCriteria.fieldAgentSkillRuleId();
        fieldAgentSkillRuleSetCriteria.franchiseUserId();
        fieldAgentSkillRuleSetCriteria.additionalAttributeId();
        fieldAgentSkillRuleSetCriteria.distinct();
    }

    private static Condition<FieldAgentSkillRuleSetCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSortType()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getFieldAgentSkillRuleId()) &&
                condition.apply(criteria.getFranchiseUserId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FieldAgentSkillRuleSetCriteria> copyFiltersAre(
        FieldAgentSkillRuleSetCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSortType(), copy.getSortType()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getFieldAgentSkillRuleId(), copy.getFieldAgentSkillRuleId()) &&
                condition.apply(criteria.getFranchiseUserId(), copy.getFranchiseUserId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
