package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FieldAgentSkillRuleCriteriaTest {

    @Test
    void newFieldAgentSkillRuleCriteriaHasAllFiltersNullTest() {
        var fieldAgentSkillRuleCriteria = new FieldAgentSkillRuleCriteria();
        assertThat(fieldAgentSkillRuleCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void fieldAgentSkillRuleCriteriaFluentMethodsCreatesFiltersTest() {
        var fieldAgentSkillRuleCriteria = new FieldAgentSkillRuleCriteria();

        setAllFilters(fieldAgentSkillRuleCriteria);

        assertThat(fieldAgentSkillRuleCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void fieldAgentSkillRuleCriteriaCopyCreatesNullFilterTest() {
        var fieldAgentSkillRuleCriteria = new FieldAgentSkillRuleCriteria();
        var copy = fieldAgentSkillRuleCriteria.copy();

        assertThat(fieldAgentSkillRuleCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(fieldAgentSkillRuleCriteria)
        );
    }

    @Test
    void fieldAgentSkillRuleCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var fieldAgentSkillRuleCriteria = new FieldAgentSkillRuleCriteria();
        setAllFilters(fieldAgentSkillRuleCriteria);

        var copy = fieldAgentSkillRuleCriteria.copy();

        assertThat(fieldAgentSkillRuleCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(fieldAgentSkillRuleCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var fieldAgentSkillRuleCriteria = new FieldAgentSkillRuleCriteria();

        assertThat(fieldAgentSkillRuleCriteria).hasToString("FieldAgentSkillRuleCriteria{}");
    }

    private static void setAllFilters(FieldAgentSkillRuleCriteria fieldAgentSkillRuleCriteria) {
        fieldAgentSkillRuleCriteria.id();
        fieldAgentSkillRuleCriteria.skillType();
        fieldAgentSkillRuleCriteria.joinType();
        fieldAgentSkillRuleCriteria.createddBy();
        fieldAgentSkillRuleCriteria.createdTime();
        fieldAgentSkillRuleCriteria.updatedBy();
        fieldAgentSkillRuleCriteria.updatedTime();
        fieldAgentSkillRuleCriteria.brandId();
        fieldAgentSkillRuleCriteria.categoryId();
        fieldAgentSkillRuleCriteria.pincodeId();
        fieldAgentSkillRuleCriteria.locationMappingId();
        fieldAgentSkillRuleCriteria.languageMappingId();
        fieldAgentSkillRuleCriteria.additionalAttributeId();
        fieldAgentSkillRuleCriteria.fieldAgentSkillRuleSetId();
        fieldAgentSkillRuleCriteria.distinct();
    }

    private static Condition<FieldAgentSkillRuleCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getSkillType()) &&
                condition.apply(criteria.getJoinType()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getBrandId()) &&
                condition.apply(criteria.getCategoryId()) &&
                condition.apply(criteria.getPincodeId()) &&
                condition.apply(criteria.getLocationMappingId()) &&
                condition.apply(criteria.getLanguageMappingId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleSetId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FieldAgentSkillRuleCriteria> copyFiltersAre(
        FieldAgentSkillRuleCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSkillType(), copy.getSkillType()) &&
                condition.apply(criteria.getJoinType(), copy.getJoinType()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getBrandId(), copy.getBrandId()) &&
                condition.apply(criteria.getCategoryId(), copy.getCategoryId()) &&
                condition.apply(criteria.getPincodeId(), copy.getPincodeId()) &&
                condition.apply(criteria.getLocationMappingId(), copy.getLocationMappingId()) &&
                condition.apply(criteria.getLanguageMappingId(), copy.getLanguageMappingId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleSetId(), copy.getFieldAgentSkillRuleSetId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
