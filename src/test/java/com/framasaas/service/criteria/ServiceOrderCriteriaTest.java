package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ServiceOrderCriteriaTest {

    @Test
    void newServiceOrderCriteriaHasAllFiltersNullTest() {
        var serviceOrderCriteria = new ServiceOrderCriteria();
        assertThat(serviceOrderCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void serviceOrderCriteriaFluentMethodsCreatesFiltersTest() {
        var serviceOrderCriteria = new ServiceOrderCriteria();

        setAllFilters(serviceOrderCriteria);

        assertThat(serviceOrderCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void serviceOrderCriteriaCopyCreatesNullFilterTest() {
        var serviceOrderCriteria = new ServiceOrderCriteria();
        var copy = serviceOrderCriteria.copy();

        assertThat(serviceOrderCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderCriteria)
        );
    }

    @Test
    void serviceOrderCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var serviceOrderCriteria = new ServiceOrderCriteria();
        setAllFilters(serviceOrderCriteria);

        var copy = serviceOrderCriteria.copy();

        assertThat(serviceOrderCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(serviceOrderCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var serviceOrderCriteria = new ServiceOrderCriteria();

        assertThat(serviceOrderCriteria).hasToString("ServiceOrderCriteria{}");
    }

    private static void setAllFilters(ServiceOrderCriteria serviceOrderCriteria) {
        serviceOrderCriteria.id();
        serviceOrderCriteria.description();
        serviceOrderCriteria.orderType();
        serviceOrderCriteria.orderStatus();
        serviceOrderCriteria.inWarranty();
        serviceOrderCriteria.isFree();
        serviceOrderCriteria.isSpareNeeded();
        serviceOrderCriteria.confirmedTime();
        serviceOrderCriteria.closedTime();
        serviceOrderCriteria.serviceCharge();
        serviceOrderCriteria.tax();
        serviceOrderCriteria.totalSpareCharges();
        serviceOrderCriteria.totalSpareTax();
        serviceOrderCriteria.totalCharges();
        serviceOrderCriteria.totalPaymentDone();
        serviceOrderCriteria.customerInvoicePath();
        serviceOrderCriteria.nps();
        serviceOrderCriteria.priority();
        serviceOrderCriteria.isActive();
        serviceOrderCriteria.createddBy();
        serviceOrderCriteria.createdTime();
        serviceOrderCriteria.updatedBy();
        serviceOrderCriteria.updatedTime();
        serviceOrderCriteria.supportingDocumentId();
        serviceOrderCriteria.serviceOrderFranchiseAssignmentId();
        serviceOrderCriteria.serviceOrderSpareId();
        serviceOrderCriteria.additionalAttributeId();
        serviceOrderCriteria.customerId();
        serviceOrderCriteria.serviceMasterId();
        serviceOrderCriteria.articleId();
        serviceOrderCriteria.addressId();
        serviceOrderCriteria.distinct();
    }

    private static Condition<ServiceOrderCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getOrderType()) &&
                condition.apply(criteria.getOrderStatus()) &&
                condition.apply(criteria.getInWarranty()) &&
                condition.apply(criteria.getIsFree()) &&
                condition.apply(criteria.getIsSpareNeeded()) &&
                condition.apply(criteria.getConfirmedTime()) &&
                condition.apply(criteria.getClosedTime()) &&
                condition.apply(criteria.getServiceCharge()) &&
                condition.apply(criteria.getTax()) &&
                condition.apply(criteria.getTotalSpareCharges()) &&
                condition.apply(criteria.getTotalSpareTax()) &&
                condition.apply(criteria.getTotalCharges()) &&
                condition.apply(criteria.getTotalPaymentDone()) &&
                condition.apply(criteria.getCustomerInvoicePath()) &&
                condition.apply(criteria.getNps()) &&
                condition.apply(criteria.getPriority()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getSupportingDocumentId()) &&
                condition.apply(criteria.getServiceOrderFranchiseAssignmentId()) &&
                condition.apply(criteria.getServiceOrderSpareId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getCustomerId()) &&
                condition.apply(criteria.getServiceMasterId()) &&
                condition.apply(criteria.getArticleId()) &&
                condition.apply(criteria.getAddressId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ServiceOrderCriteria> copyFiltersAre(
        ServiceOrderCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getOrderType(), copy.getOrderType()) &&
                condition.apply(criteria.getOrderStatus(), copy.getOrderStatus()) &&
                condition.apply(criteria.getInWarranty(), copy.getInWarranty()) &&
                condition.apply(criteria.getIsFree(), copy.getIsFree()) &&
                condition.apply(criteria.getIsSpareNeeded(), copy.getIsSpareNeeded()) &&
                condition.apply(criteria.getConfirmedTime(), copy.getConfirmedTime()) &&
                condition.apply(criteria.getClosedTime(), copy.getClosedTime()) &&
                condition.apply(criteria.getServiceCharge(), copy.getServiceCharge()) &&
                condition.apply(criteria.getTax(), copy.getTax()) &&
                condition.apply(criteria.getTotalSpareCharges(), copy.getTotalSpareCharges()) &&
                condition.apply(criteria.getTotalSpareTax(), copy.getTotalSpareTax()) &&
                condition.apply(criteria.getTotalCharges(), copy.getTotalCharges()) &&
                condition.apply(criteria.getTotalPaymentDone(), copy.getTotalPaymentDone()) &&
                condition.apply(criteria.getCustomerInvoicePath(), copy.getCustomerInvoicePath()) &&
                condition.apply(criteria.getNps(), copy.getNps()) &&
                condition.apply(criteria.getPriority(), copy.getPriority()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getSupportingDocumentId(), copy.getSupportingDocumentId()) &&
                condition.apply(criteria.getServiceOrderFranchiseAssignmentId(), copy.getServiceOrderFranchiseAssignmentId()) &&
                condition.apply(criteria.getServiceOrderSpareId(), copy.getServiceOrderSpareId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getCustomerId(), copy.getCustomerId()) &&
                condition.apply(criteria.getServiceMasterId(), copy.getServiceMasterId()) &&
                condition.apply(criteria.getArticleId(), copy.getArticleId()) &&
                condition.apply(criteria.getAddressId(), copy.getAddressId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
