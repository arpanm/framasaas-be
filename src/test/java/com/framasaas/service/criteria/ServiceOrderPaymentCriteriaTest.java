package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ServiceOrderPaymentCriteriaTest {

    @Test
    void newServiceOrderPaymentCriteriaHasAllFiltersNullTest() {
        var serviceOrderPaymentCriteria = new ServiceOrderPaymentCriteria();
        assertThat(serviceOrderPaymentCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void serviceOrderPaymentCriteriaFluentMethodsCreatesFiltersTest() {
        var serviceOrderPaymentCriteria = new ServiceOrderPaymentCriteria();

        setAllFilters(serviceOrderPaymentCriteria);

        assertThat(serviceOrderPaymentCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void serviceOrderPaymentCriteriaCopyCreatesNullFilterTest() {
        var serviceOrderPaymentCriteria = new ServiceOrderPaymentCriteria();
        var copy = serviceOrderPaymentCriteria.copy();

        assertThat(serviceOrderPaymentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderPaymentCriteria)
        );
    }

    @Test
    void serviceOrderPaymentCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var serviceOrderPaymentCriteria = new ServiceOrderPaymentCriteria();
        setAllFilters(serviceOrderPaymentCriteria);

        var copy = serviceOrderPaymentCriteria.copy();

        assertThat(serviceOrderPaymentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderPaymentCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var serviceOrderPaymentCriteria = new ServiceOrderPaymentCriteria();

        assertThat(serviceOrderPaymentCriteria).hasToString("ServiceOrderPaymentCriteria{}");
    }

    private static void setAllFilters(ServiceOrderPaymentCriteria serviceOrderPaymentCriteria) {
        serviceOrderPaymentCriteria.id();
        serviceOrderPaymentCriteria.paymentLink();
        serviceOrderPaymentCriteria.paymentStatus();
        serviceOrderPaymentCriteria.mop();
        serviceOrderPaymentCriteria.pgTxnId();
        serviceOrderPaymentCriteria.pgTxnResponse();
        serviceOrderPaymentCriteria.pgTxnResponseTime();
        serviceOrderPaymentCriteria.paymentInitiatedBy();
        serviceOrderPaymentCriteria.isActive();
        serviceOrderPaymentCriteria.createddBy();
        serviceOrderPaymentCriteria.createdTime();
        serviceOrderPaymentCriteria.updatedBy();
        serviceOrderPaymentCriteria.updatedTime();
        serviceOrderPaymentCriteria.additionalAttributeId();
        serviceOrderPaymentCriteria.distinct();
    }

    private static Condition<ServiceOrderPaymentCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPaymentLink()) &&
                condition.apply(criteria.getPaymentStatus()) &&
                condition.apply(criteria.getMop()) &&
                condition.apply(criteria.getPgTxnId()) &&
                condition.apply(criteria.getPgTxnResponse()) &&
                condition.apply(criteria.getPgTxnResponseTime()) &&
                condition.apply(criteria.getPaymentInitiatedBy()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ServiceOrderPaymentCriteria> copyFiltersAre(
        ServiceOrderPaymentCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPaymentLink(), copy.getPaymentLink()) &&
                condition.apply(criteria.getPaymentStatus(), copy.getPaymentStatus()) &&
                condition.apply(criteria.getMop(), copy.getMop()) &&
                condition.apply(criteria.getPgTxnId(), copy.getPgTxnId()) &&
                condition.apply(criteria.getPgTxnResponse(), copy.getPgTxnResponse()) &&
                condition.apply(criteria.getPgTxnResponseTime(), copy.getPgTxnResponseTime()) &&
                condition.apply(criteria.getPaymentInitiatedBy(), copy.getPaymentInitiatedBy()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
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
