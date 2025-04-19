package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ServiceOrderFieldAgentAssignmentCriteriaTest {

    @Test
    void newServiceOrderFieldAgentAssignmentCriteriaHasAllFiltersNullTest() {
        var serviceOrderFieldAgentAssignmentCriteria = new ServiceOrderFieldAgentAssignmentCriteria();
        assertThat(serviceOrderFieldAgentAssignmentCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void serviceOrderFieldAgentAssignmentCriteriaFluentMethodsCreatesFiltersTest() {
        var serviceOrderFieldAgentAssignmentCriteria = new ServiceOrderFieldAgentAssignmentCriteria();

        setAllFilters(serviceOrderFieldAgentAssignmentCriteria);

        assertThat(serviceOrderFieldAgentAssignmentCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void serviceOrderFieldAgentAssignmentCriteriaCopyCreatesNullFilterTest() {
        var serviceOrderFieldAgentAssignmentCriteria = new ServiceOrderFieldAgentAssignmentCriteria();
        var copy = serviceOrderFieldAgentAssignmentCriteria.copy();

        assertThat(serviceOrderFieldAgentAssignmentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderFieldAgentAssignmentCriteria)
        );
    }

    @Test
    void serviceOrderFieldAgentAssignmentCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var serviceOrderFieldAgentAssignmentCriteria = new ServiceOrderFieldAgentAssignmentCriteria();
        setAllFilters(serviceOrderFieldAgentAssignmentCriteria);

        var copy = serviceOrderFieldAgentAssignmentCriteria.copy();

        assertThat(serviceOrderFieldAgentAssignmentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderFieldAgentAssignmentCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var serviceOrderFieldAgentAssignmentCriteria = new ServiceOrderFieldAgentAssignmentCriteria();

        assertThat(serviceOrderFieldAgentAssignmentCriteria).hasToString("ServiceOrderFieldAgentAssignmentCriteria{}");
    }

    private static void setAllFilters(ServiceOrderFieldAgentAssignmentCriteria serviceOrderFieldAgentAssignmentCriteria) {
        serviceOrderFieldAgentAssignmentCriteria.id();
        serviceOrderFieldAgentAssignmentCriteria.serviceOrderAssignmentStatus();
        serviceOrderFieldAgentAssignmentCriteria.nps();
        serviceOrderFieldAgentAssignmentCriteria.isActive();
        serviceOrderFieldAgentAssignmentCriteria.assignedTime();
        serviceOrderFieldAgentAssignmentCriteria.movedBackTime();
        serviceOrderFieldAgentAssignmentCriteria.visitTime();
        serviceOrderFieldAgentAssignmentCriteria.spareOrderTime();
        serviceOrderFieldAgentAssignmentCriteria.spareDeliveryTime();
        serviceOrderFieldAgentAssignmentCriteria.completedTime();
        serviceOrderFieldAgentAssignmentCriteria.completionOTP();
        serviceOrderFieldAgentAssignmentCriteria.cancellationOTP();
        serviceOrderFieldAgentAssignmentCriteria.createddBy();
        serviceOrderFieldAgentAssignmentCriteria.createdTime();
        serviceOrderFieldAgentAssignmentCriteria.updatedBy();
        serviceOrderFieldAgentAssignmentCriteria.updatedTime();
        serviceOrderFieldAgentAssignmentCriteria.additionalAttributeId();
        serviceOrderFieldAgentAssignmentCriteria.distinct();
    }

    private static Condition<ServiceOrderFieldAgentAssignmentCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getServiceOrderAssignmentStatus()) &&
                condition.apply(criteria.getNps()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getAssignedTime()) &&
                condition.apply(criteria.getMovedBackTime()) &&
                condition.apply(criteria.getVisitTime()) &&
                condition.apply(criteria.getSpareOrderTime()) &&
                condition.apply(criteria.getSpareDeliveryTime()) &&
                condition.apply(criteria.getCompletedTime()) &&
                condition.apply(criteria.getCompletionOTP()) &&
                condition.apply(criteria.getCancellationOTP()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ServiceOrderFieldAgentAssignmentCriteria> copyFiltersAre(
        ServiceOrderFieldAgentAssignmentCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getServiceOrderAssignmentStatus(), copy.getServiceOrderAssignmentStatus()) &&
                condition.apply(criteria.getNps(), copy.getNps()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getAssignedTime(), copy.getAssignedTime()) &&
                condition.apply(criteria.getMovedBackTime(), copy.getMovedBackTime()) &&
                condition.apply(criteria.getVisitTime(), copy.getVisitTime()) &&
                condition.apply(criteria.getSpareOrderTime(), copy.getSpareOrderTime()) &&
                condition.apply(criteria.getSpareDeliveryTime(), copy.getSpareDeliveryTime()) &&
                condition.apply(criteria.getCompletedTime(), copy.getCompletedTime()) &&
                condition.apply(criteria.getCompletionOTP(), copy.getCompletionOTP()) &&
                condition.apply(criteria.getCancellationOTP(), copy.getCancellationOTP()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
