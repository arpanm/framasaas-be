package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ServiceOrderMasterCriteriaTest {

    @Test
    void newServiceOrderMasterCriteriaHasAllFiltersNullTest() {
        var serviceOrderMasterCriteria = new ServiceOrderMasterCriteria();
        assertThat(serviceOrderMasterCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void serviceOrderMasterCriteriaFluentMethodsCreatesFiltersTest() {
        var serviceOrderMasterCriteria = new ServiceOrderMasterCriteria();

        setAllFilters(serviceOrderMasterCriteria);

        assertThat(serviceOrderMasterCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void serviceOrderMasterCriteriaCopyCreatesNullFilterTest() {
        var serviceOrderMasterCriteria = new ServiceOrderMasterCriteria();
        var copy = serviceOrderMasterCriteria.copy();

        assertThat(serviceOrderMasterCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderMasterCriteria)
        );
    }

    @Test
    void serviceOrderMasterCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var serviceOrderMasterCriteria = new ServiceOrderMasterCriteria();
        setAllFilters(serviceOrderMasterCriteria);

        var copy = serviceOrderMasterCriteria.copy();

        assertThat(serviceOrderMasterCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderMasterCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var serviceOrderMasterCriteria = new ServiceOrderMasterCriteria();

        assertThat(serviceOrderMasterCriteria).hasToString("ServiceOrderMasterCriteria{}");
    }

    private static void setAllFilters(ServiceOrderMasterCriteria serviceOrderMasterCriteria) {
        serviceOrderMasterCriteria.id();
        serviceOrderMasterCriteria.serviceOrderType();
        serviceOrderMasterCriteria.slaInHours();
        serviceOrderMasterCriteria.charge();
        serviceOrderMasterCriteria.tax();
        serviceOrderMasterCriteria.franchiseCommissionWithinSla();
        serviceOrderMasterCriteria.franchiseTaxWithinSlaTax();
        serviceOrderMasterCriteria.franchiseCommissionOutsideSla();
        serviceOrderMasterCriteria.franchiseTaxOutsideSlaTax();
        serviceOrderMasterCriteria.isActive();
        serviceOrderMasterCriteria.createddBy();
        serviceOrderMasterCriteria.createdTime();
        serviceOrderMasterCriteria.updatedBy();
        serviceOrderMasterCriteria.updatedTime();
        serviceOrderMasterCriteria.serviceOrderId();
        serviceOrderMasterCriteria.productId();
        serviceOrderMasterCriteria.distinct();
    }

    private static Condition<ServiceOrderMasterCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getServiceOrderType()) &&
                condition.apply(criteria.getSlaInHours()) &&
                condition.apply(criteria.getCharge()) &&
                condition.apply(criteria.getTax()) &&
                condition.apply(criteria.getFranchiseCommissionWithinSla()) &&
                condition.apply(criteria.getFranchiseTaxWithinSlaTax()) &&
                condition.apply(criteria.getFranchiseCommissionOutsideSla()) &&
                condition.apply(criteria.getFranchiseTaxOutsideSlaTax()) &&
                condition.apply(criteria.getIsActive()) &&
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

    private static Condition<ServiceOrderMasterCriteria> copyFiltersAre(
        ServiceOrderMasterCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getServiceOrderType(), copy.getServiceOrderType()) &&
                condition.apply(criteria.getSlaInHours(), copy.getSlaInHours()) &&
                condition.apply(criteria.getCharge(), copy.getCharge()) &&
                condition.apply(criteria.getTax(), copy.getTax()) &&
                condition.apply(criteria.getFranchiseCommissionWithinSla(), copy.getFranchiseCommissionWithinSla()) &&
                condition.apply(criteria.getFranchiseTaxWithinSlaTax(), copy.getFranchiseTaxWithinSlaTax()) &&
                condition.apply(criteria.getFranchiseCommissionOutsideSla(), copy.getFranchiseCommissionOutsideSla()) &&
                condition.apply(criteria.getFranchiseTaxOutsideSlaTax(), copy.getFranchiseTaxOutsideSlaTax()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
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
