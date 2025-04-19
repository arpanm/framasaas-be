package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ServiceOrderFranchiseAssignmentCriteriaTest {

    @Test
    void newServiceOrderFranchiseAssignmentCriteriaHasAllFiltersNullTest() {
        var serviceOrderFranchiseAssignmentCriteria = new ServiceOrderFranchiseAssignmentCriteria();
        assertThat(serviceOrderFranchiseAssignmentCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void serviceOrderFranchiseAssignmentCriteriaFluentMethodsCreatesFiltersTest() {
        var serviceOrderFranchiseAssignmentCriteria = new ServiceOrderFranchiseAssignmentCriteria();

        setAllFilters(serviceOrderFranchiseAssignmentCriteria);

        assertThat(serviceOrderFranchiseAssignmentCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void serviceOrderFranchiseAssignmentCriteriaCopyCreatesNullFilterTest() {
        var serviceOrderFranchiseAssignmentCriteria = new ServiceOrderFranchiseAssignmentCriteria();
        var copy = serviceOrderFranchiseAssignmentCriteria.copy();

        assertThat(serviceOrderFranchiseAssignmentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderFranchiseAssignmentCriteria)
        );
    }

    @Test
    void serviceOrderFranchiseAssignmentCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var serviceOrderFranchiseAssignmentCriteria = new ServiceOrderFranchiseAssignmentCriteria();
        setAllFilters(serviceOrderFranchiseAssignmentCriteria);

        var copy = serviceOrderFranchiseAssignmentCriteria.copy();

        assertThat(serviceOrderFranchiseAssignmentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderFranchiseAssignmentCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var serviceOrderFranchiseAssignmentCriteria = new ServiceOrderFranchiseAssignmentCriteria();

        assertThat(serviceOrderFranchiseAssignmentCriteria).hasToString("ServiceOrderFranchiseAssignmentCriteria{}");
    }

    private static void setAllFilters(ServiceOrderFranchiseAssignmentCriteria serviceOrderFranchiseAssignmentCriteria) {
        serviceOrderFranchiseAssignmentCriteria.id();
        serviceOrderFranchiseAssignmentCriteria.serviceOrderAssignmentStatus();
        serviceOrderFranchiseAssignmentCriteria.nps();
        serviceOrderFranchiseAssignmentCriteria.isActive();
        serviceOrderFranchiseAssignmentCriteria.assignedTime();
        serviceOrderFranchiseAssignmentCriteria.movedBackTime();
        serviceOrderFranchiseAssignmentCriteria.visitTime();
        serviceOrderFranchiseAssignmentCriteria.spareOrderTime();
        serviceOrderFranchiseAssignmentCriteria.spareDeliveryTime();
        serviceOrderFranchiseAssignmentCriteria.completedTime();
        serviceOrderFranchiseAssignmentCriteria.franchiseCommision();
        serviceOrderFranchiseAssignmentCriteria.franchiseCommisionTax();
        serviceOrderFranchiseAssignmentCriteria.franchiseInvoicePath();
        serviceOrderFranchiseAssignmentCriteria.createddBy();
        serviceOrderFranchiseAssignmentCriteria.createdTime();
        serviceOrderFranchiseAssignmentCriteria.updatedBy();
        serviceOrderFranchiseAssignmentCriteria.updatedTime();
        serviceOrderFranchiseAssignmentCriteria.additionalAttributeId();
        serviceOrderFranchiseAssignmentCriteria.serviceOrderId();
        serviceOrderFranchiseAssignmentCriteria.franchiseId();
        serviceOrderFranchiseAssignmentCriteria.distinct();
    }

    private static Condition<ServiceOrderFranchiseAssignmentCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
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
                condition.apply(criteria.getFranchiseCommision()) &&
                condition.apply(criteria.getFranchiseCommisionTax()) &&
                condition.apply(criteria.getFranchiseInvoicePath()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getServiceOrderId()) &&
                condition.apply(criteria.getFranchiseId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ServiceOrderFranchiseAssignmentCriteria> copyFiltersAre(
        ServiceOrderFranchiseAssignmentCriteria copy,
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
                condition.apply(criteria.getFranchiseCommision(), copy.getFranchiseCommision()) &&
                condition.apply(criteria.getFranchiseCommisionTax(), copy.getFranchiseCommisionTax()) &&
                condition.apply(criteria.getFranchiseInvoicePath(), copy.getFranchiseInvoicePath()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getServiceOrderId(), copy.getServiceOrderId()) &&
                condition.apply(criteria.getFranchiseId(), copy.getFranchiseId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
