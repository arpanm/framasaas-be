package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ServiceOrderSpareCriteriaTest {

    @Test
    void newServiceOrderSpareCriteriaHasAllFiltersNullTest() {
        var serviceOrderSpareCriteria = new ServiceOrderSpareCriteria();
        assertThat(serviceOrderSpareCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void serviceOrderSpareCriteriaFluentMethodsCreatesFiltersTest() {
        var serviceOrderSpareCriteria = new ServiceOrderSpareCriteria();

        setAllFilters(serviceOrderSpareCriteria);

        assertThat(serviceOrderSpareCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void serviceOrderSpareCriteriaCopyCreatesNullFilterTest() {
        var serviceOrderSpareCriteria = new ServiceOrderSpareCriteria();
        var copy = serviceOrderSpareCriteria.copy();

        assertThat(serviceOrderSpareCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderSpareCriteria)
        );
    }

    @Test
    void serviceOrderSpareCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var serviceOrderSpareCriteria = new ServiceOrderSpareCriteria();
        setAllFilters(serviceOrderSpareCriteria);

        var copy = serviceOrderSpareCriteria.copy();

        assertThat(serviceOrderSpareCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderSpareCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var serviceOrderSpareCriteria = new ServiceOrderSpareCriteria();

        assertThat(serviceOrderSpareCriteria).hasToString("ServiceOrderSpareCriteria{}");
    }

    private static void setAllFilters(ServiceOrderSpareCriteria serviceOrderSpareCriteria) {
        serviceOrderSpareCriteria.id();
        serviceOrderSpareCriteria.price();
        serviceOrderSpareCriteria.tax();
        serviceOrderSpareCriteria.totalCharge();
        serviceOrderSpareCriteria.franchiseCommision();
        serviceOrderSpareCriteria.franchiseCommisionTax();
        serviceOrderSpareCriteria.orderedFrom();
        serviceOrderSpareCriteria.spareStatus();
        serviceOrderSpareCriteria.createddBy();
        serviceOrderSpareCriteria.createdTime();
        serviceOrderSpareCriteria.updatedBy();
        serviceOrderSpareCriteria.updatedTime();
        serviceOrderSpareCriteria.serviceOrderId();
        serviceOrderSpareCriteria.productId();
        serviceOrderSpareCriteria.distinct();
    }

    private static Condition<ServiceOrderSpareCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getTax()) &&
                condition.apply(criteria.getTotalCharge()) &&
                condition.apply(criteria.getFranchiseCommision()) &&
                condition.apply(criteria.getFranchiseCommisionTax()) &&
                condition.apply(criteria.getOrderedFrom()) &&
                condition.apply(criteria.getSpareStatus()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getServiceOrderId()) &&
                condition.apply(criteria.getProductId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ServiceOrderSpareCriteria> copyFiltersAre(
        ServiceOrderSpareCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getTax(), copy.getTax()) &&
                condition.apply(criteria.getTotalCharge(), copy.getTotalCharge()) &&
                condition.apply(criteria.getFranchiseCommision(), copy.getFranchiseCommision()) &&
                condition.apply(criteria.getFranchiseCommisionTax(), copy.getFranchiseCommisionTax()) &&
                condition.apply(criteria.getOrderedFrom(), copy.getOrderedFrom()) &&
                condition.apply(criteria.getSpareStatus(), copy.getSpareStatus()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getServiceOrderId(), copy.getServiceOrderId()) &&
                condition.apply(criteria.getProductId(), copy.getProductId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
