package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class LocationMappingCriteriaTest {

    @Test
    void newLocationMappingCriteriaHasAllFiltersNullTest() {
        var locationMappingCriteria = new LocationMappingCriteria();
        assertThat(locationMappingCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void locationMappingCriteriaFluentMethodsCreatesFiltersTest() {
        var locationMappingCriteria = new LocationMappingCriteria();

        setAllFilters(locationMappingCriteria);

        assertThat(locationMappingCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void locationMappingCriteriaCopyCreatesNullFilterTest() {
        var locationMappingCriteria = new LocationMappingCriteria();
        var copy = locationMappingCriteria.copy();

        assertThat(locationMappingCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(locationMappingCriteria)
        );
    }

    @Test
    void locationMappingCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var locationMappingCriteria = new LocationMappingCriteria();
        setAllFilters(locationMappingCriteria);

        var copy = locationMappingCriteria.copy();

        assertThat(locationMappingCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(locationMappingCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var locationMappingCriteria = new LocationMappingCriteria();

        assertThat(locationMappingCriteria).hasToString("LocationMappingCriteria{}");
    }

    private static void setAllFilters(LocationMappingCriteria locationMappingCriteria) {
        locationMappingCriteria.id();
        locationMappingCriteria.locationName();
        locationMappingCriteria.createddBy();
        locationMappingCriteria.createdTime();
        locationMappingCriteria.updatedBy();
        locationMappingCriteria.updatedTime();
        locationMappingCriteria.additionalAttributeId();
        locationMappingCriteria.franchiseRuleId();
        locationMappingCriteria.fieldAgentSkillRuleId();
        locationMappingCriteria.distinct();
    }

    private static Condition<LocationMappingCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLocationName()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseRuleId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<LocationMappingCriteria> copyFiltersAre(
        LocationMappingCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLocationName(), copy.getLocationName()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseRuleId(), copy.getFranchiseRuleId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleId(), copy.getFieldAgentSkillRuleId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
