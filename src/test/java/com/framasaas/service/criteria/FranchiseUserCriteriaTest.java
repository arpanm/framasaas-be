package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FranchiseUserCriteriaTest {

    @Test
    void newFranchiseUserCriteriaHasAllFiltersNullTest() {
        var franchiseUserCriteria = new FranchiseUserCriteria();
        assertThat(franchiseUserCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void franchiseUserCriteriaFluentMethodsCreatesFiltersTest() {
        var franchiseUserCriteria = new FranchiseUserCriteria();

        setAllFilters(franchiseUserCriteria);

        assertThat(franchiseUserCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void franchiseUserCriteriaCopyCreatesNullFilterTest() {
        var franchiseUserCriteria = new FranchiseUserCriteria();
        var copy = franchiseUserCriteria.copy();

        assertThat(franchiseUserCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseUserCriteria)
        );
    }

    @Test
    void franchiseUserCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var franchiseUserCriteria = new FranchiseUserCriteria();
        setAllFilters(franchiseUserCriteria);

        var copy = franchiseUserCriteria.copy();

        assertThat(franchiseUserCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseUserCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var franchiseUserCriteria = new FranchiseUserCriteria();

        assertThat(franchiseUserCriteria).hasToString("FranchiseUserCriteria{}");
    }

    private static void setAllFilters(FranchiseUserCriteria franchiseUserCriteria) {
        franchiseUserCriteria.id();
        franchiseUserCriteria.userName();
        franchiseUserCriteria.email();
        franchiseUserCriteria.contact();
        franchiseUserCriteria.userStatus();
        franchiseUserCriteria.userRole();
        franchiseUserCriteria.createddBy();
        franchiseUserCriteria.createdTime();
        franchiseUserCriteria.updatedBy();
        franchiseUserCriteria.updatedTime();
        franchiseUserCriteria.franchiseUserStatusHistoryId();
        franchiseUserCriteria.additionalAttributeId();
        franchiseUserCriteria.franchiseId();
        franchiseUserCriteria.skillRuleSetId();
        franchiseUserCriteria.distinct();
    }

    private static Condition<FranchiseUserCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getUserName()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getContact()) &&
                condition.apply(criteria.getUserStatus()) &&
                condition.apply(criteria.getUserRole()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getFranchiseUserStatusHistoryId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseId()) &&
                condition.apply(criteria.getSkillRuleSetId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FranchiseUserCriteria> copyFiltersAre(
        FranchiseUserCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getUserName(), copy.getUserName()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getContact(), copy.getContact()) &&
                condition.apply(criteria.getUserStatus(), copy.getUserStatus()) &&
                condition.apply(criteria.getUserRole(), copy.getUserRole()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getFranchiseUserStatusHistoryId(), copy.getFranchiseUserStatusHistoryId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseId(), copy.getFranchiseId()) &&
                condition.apply(criteria.getSkillRuleSetId(), copy.getSkillRuleSetId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
