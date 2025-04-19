package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FranchiseCriteriaTest {

    @Test
    void newFranchiseCriteriaHasAllFiltersNullTest() {
        var franchiseCriteria = new FranchiseCriteria();
        assertThat(franchiseCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void franchiseCriteriaFluentMethodsCreatesFiltersTest() {
        var franchiseCriteria = new FranchiseCriteria();

        setAllFilters(franchiseCriteria);

        assertThat(franchiseCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void franchiseCriteriaCopyCreatesNullFilterTest() {
        var franchiseCriteria = new FranchiseCriteria();
        var copy = franchiseCriteria.copy();

        assertThat(franchiseCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseCriteria)
        );
    }

    @Test
    void franchiseCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var franchiseCriteria = new FranchiseCriteria();
        setAllFilters(franchiseCriteria);

        var copy = franchiseCriteria.copy();

        assertThat(franchiseCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(franchiseCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var franchiseCriteria = new FranchiseCriteria();

        assertThat(franchiseCriteria).hasToString("FranchiseCriteria{}");
    }

    private static void setAllFilters(FranchiseCriteria franchiseCriteria) {
        franchiseCriteria.id();
        franchiseCriteria.franchiseName();
        franchiseCriteria.owner();
        franchiseCriteria.email();
        franchiseCriteria.contact();
        franchiseCriteria.franchiseStatus();
        franchiseCriteria.gstNumber();
        franchiseCriteria.registrationNumber();
        franchiseCriteria.performanceScore();
        franchiseCriteria.performanceTag();
        franchiseCriteria.dailyMaxServiceLimit();
        franchiseCriteria.createddBy();
        franchiseCriteria.createdTime();
        franchiseCriteria.updatedBy();
        franchiseCriteria.updatedTime();
        franchiseCriteria.addressId();
        franchiseCriteria.franchiseStatusHistoryId();
        franchiseCriteria.franchisePerformanceHistoryId();
        franchiseCriteria.supportingDocumentId();
        franchiseCriteria.franchiseUserId();
        franchiseCriteria.serviceOrderFranchiseAssignmentId();
        franchiseCriteria.additionalAttributeId();
        franchiseCriteria.rulesetId();
        franchiseCriteria.brandsId();
        franchiseCriteria.categoriesId();
        franchiseCriteria.distinct();
    }

    private static Condition<FranchiseCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getFranchiseName()) &&
                condition.apply(criteria.getOwner()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getContact()) &&
                condition.apply(criteria.getFranchiseStatus()) &&
                condition.apply(criteria.getGstNumber()) &&
                condition.apply(criteria.getRegistrationNumber()) &&
                condition.apply(criteria.getPerformanceScore()) &&
                condition.apply(criteria.getPerformanceTag()) &&
                condition.apply(criteria.getDailyMaxServiceLimit()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAddressId()) &&
                condition.apply(criteria.getFranchiseStatusHistoryId()) &&
                condition.apply(criteria.getFranchisePerformanceHistoryId()) &&
                condition.apply(criteria.getSupportingDocumentId()) &&
                condition.apply(criteria.getFranchiseUserId()) &&
                condition.apply(criteria.getServiceOrderFranchiseAssignmentId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getRulesetId()) &&
                condition.apply(criteria.getBrandsId()) &&
                condition.apply(criteria.getCategoriesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FranchiseCriteria> copyFiltersAre(FranchiseCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getFranchiseName(), copy.getFranchiseName()) &&
                condition.apply(criteria.getOwner(), copy.getOwner()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getContact(), copy.getContact()) &&
                condition.apply(criteria.getFranchiseStatus(), copy.getFranchiseStatus()) &&
                condition.apply(criteria.getGstNumber(), copy.getGstNumber()) &&
                condition.apply(criteria.getRegistrationNumber(), copy.getRegistrationNumber()) &&
                condition.apply(criteria.getPerformanceScore(), copy.getPerformanceScore()) &&
                condition.apply(criteria.getPerformanceTag(), copy.getPerformanceTag()) &&
                condition.apply(criteria.getDailyMaxServiceLimit(), copy.getDailyMaxServiceLimit()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAddressId(), copy.getAddressId()) &&
                condition.apply(criteria.getFranchiseStatusHistoryId(), copy.getFranchiseStatusHistoryId()) &&
                condition.apply(criteria.getFranchisePerformanceHistoryId(), copy.getFranchisePerformanceHistoryId()) &&
                condition.apply(criteria.getSupportingDocumentId(), copy.getSupportingDocumentId()) &&
                condition.apply(criteria.getFranchiseUserId(), copy.getFranchiseUserId()) &&
                condition.apply(criteria.getServiceOrderFranchiseAssignmentId(), copy.getServiceOrderFranchiseAssignmentId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getRulesetId(), copy.getRulesetId()) &&
                condition.apply(criteria.getBrandsId(), copy.getBrandsId()) &&
                condition.apply(criteria.getCategoriesId(), copy.getCategoriesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
