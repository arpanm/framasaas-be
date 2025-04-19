package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class LanguageMappingCriteriaTest {

    @Test
    void newLanguageMappingCriteriaHasAllFiltersNullTest() {
        var languageMappingCriteria = new LanguageMappingCriteria();
        assertThat(languageMappingCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void languageMappingCriteriaFluentMethodsCreatesFiltersTest() {
        var languageMappingCriteria = new LanguageMappingCriteria();

        setAllFilters(languageMappingCriteria);

        assertThat(languageMappingCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void languageMappingCriteriaCopyCreatesNullFilterTest() {
        var languageMappingCriteria = new LanguageMappingCriteria();
        var copy = languageMappingCriteria.copy();

        assertThat(languageMappingCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(languageMappingCriteria)
        );
    }

    @Test
    void languageMappingCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var languageMappingCriteria = new LanguageMappingCriteria();
        setAllFilters(languageMappingCriteria);

        var copy = languageMappingCriteria.copy();

        assertThat(languageMappingCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(languageMappingCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var languageMappingCriteria = new LanguageMappingCriteria();

        assertThat(languageMappingCriteria).hasToString("LanguageMappingCriteria{}");
    }

    private static void setAllFilters(LanguageMappingCriteria languageMappingCriteria) {
        languageMappingCriteria.id();
        languageMappingCriteria.lang();
        languageMappingCriteria.createddBy();
        languageMappingCriteria.createdTime();
        languageMappingCriteria.updatedBy();
        languageMappingCriteria.updatedTime();
        languageMappingCriteria.franchiseRuleId();
        languageMappingCriteria.fieldAgentSkillRuleId();
        languageMappingCriteria.distinct();
    }

    private static Condition<LanguageMappingCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLang()) &&
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

    private static Condition<LanguageMappingCriteria> copyFiltersAre(
        LanguageMappingCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLang(), copy.getLang()) &&
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
