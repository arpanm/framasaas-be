package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FranchiseAllocationRuleCriteriaTest {

    @Test
    void newFranchiseAllocationRuleCriteriaHasAllFiltersNullTest() {
        var franchiseAllocationRuleCriteria = new FranchiseAllocationRuleCriteria();
        assertThat(franchiseAllocationRuleCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void franchiseAllocationRuleCriteriaFluentMethodsCreatesFiltersTest() {
        var franchiseAllocationRuleCriteria = new FranchiseAllocationRuleCriteria();

        setAllFilters(franchiseAllocationRuleCriteria);

        assertThat(franchiseAllocationRuleCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void franchiseAllocationRuleCriteriaCopyCreatesNullFilterTest() {
        var franchiseAllocationRuleCriteria = new FranchiseAllocationRuleCriteria();
        var copy = franchiseAllocationRuleCriteria.copy();

        assertThat(franchiseAllocationRuleCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseAllocationRuleCriteria)
        );
    }

    @Test
    void franchiseAllocationRuleCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var franchiseAllocationRuleCriteria = new FranchiseAllocationRuleCriteria();
        setAllFilters(franchiseAllocationRuleCriteria);

        var copy = franchiseAllocationRuleCriteria.copy();

        assertThat(franchiseAllocationRuleCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseAllocationRuleCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var franchiseAllocationRuleCriteria = new FranchiseAllocationRuleCriteria();

        assertThat(franchiseAllocationRuleCriteria).hasToString("FranchiseAllocationRuleCriteria{}");
    }

    private static void setAllFilters(FranchiseAllocationRuleCriteria franchiseAllocationRuleCriteria) {
        franchiseAllocationRuleCriteria.id();
        franchiseAllocationRuleCriteria.ruleType();
        franchiseAllocationRuleCriteria.joinType();
        franchiseAllocationRuleCriteria.createddBy();
        franchiseAllocationRuleCriteria.createdTime();
        franchiseAllocationRuleCriteria.updatedBy();
        franchiseAllocationRuleCriteria.updatedTime();
        franchiseAllocationRuleCriteria.brandId();
        franchiseAllocationRuleCriteria.categoryId();
        franchiseAllocationRuleCriteria.pincodeId();
        franchiseAllocationRuleCriteria.locationMappingId();
        franchiseAllocationRuleCriteria.languageMappingId();
        franchiseAllocationRuleCriteria.additionalAttributeId();
        franchiseAllocationRuleCriteria.distinct();
    }

    private static Condition<FranchiseAllocationRuleCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRuleType()) &&
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
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FranchiseAllocationRuleCriteria> copyFiltersAre(
        FranchiseAllocationRuleCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRuleType(), copy.getRuleType()) &&
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
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
