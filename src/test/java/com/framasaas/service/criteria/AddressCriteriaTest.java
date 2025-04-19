package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AddressCriteriaTest {

    @Test
    void newAddressCriteriaHasAllFiltersNullTest() {
        var addressCriteria = new AddressCriteria();
        assertThat(addressCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void addressCriteriaFluentMethodsCreatesFiltersTest() {
        var addressCriteria = new AddressCriteria();

        setAllFilters(addressCriteria);

        assertThat(addressCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void addressCriteriaCopyCreatesNullFilterTest() {
        var addressCriteria = new AddressCriteria();
        var copy = addressCriteria.copy();

        assertThat(addressCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(addressCriteria)
        );
    }

    @Test
    void addressCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var addressCriteria = new AddressCriteria();
        setAllFilters(addressCriteria);

        var copy = addressCriteria.copy();

        assertThat(addressCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(addressCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var addressCriteria = new AddressCriteria();

        assertThat(addressCriteria).hasToString("AddressCriteria{}");
    }

    private static void setAllFilters(AddressCriteria addressCriteria) {
        addressCriteria.id();
        addressCriteria.address1();
        addressCriteria.address2();
        addressCriteria.city();
        addressCriteria.area();
        addressCriteria.district();
        addressCriteria.pincode();
        addressCriteria.state();
        addressCriteria.country();
        addressCriteria.createddBy();
        addressCriteria.createdTime();
        addressCriteria.updatedBy();
        addressCriteria.updatedTime();
        addressCriteria.serviceOrderId();
        addressCriteria.additionalAttributeId();
        addressCriteria.locationId();
        addressCriteria.franchiseId();
        addressCriteria.customerId();
        addressCriteria.distinct();
    }

    private static Condition<AddressCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getAddress1()) &&
                condition.apply(criteria.getAddress2()) &&
                condition.apply(criteria.getCity()) &&
                condition.apply(criteria.getArea()) &&
                condition.apply(criteria.getDistrict()) &&
                condition.apply(criteria.getPincode()) &&
                condition.apply(criteria.getState()) &&
                condition.apply(criteria.getCountry()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getServiceOrderId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getLocationId()) &&
                condition.apply(criteria.getFranchiseId()) &&
                condition.apply(criteria.getCustomerId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AddressCriteria> copyFiltersAre(AddressCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getAddress1(), copy.getAddress1()) &&
                condition.apply(criteria.getAddress2(), copy.getAddress2()) &&
                condition.apply(criteria.getCity(), copy.getCity()) &&
                condition.apply(criteria.getArea(), copy.getArea()) &&
                condition.apply(criteria.getDistrict(), copy.getDistrict()) &&
                condition.apply(criteria.getPincode(), copy.getPincode()) &&
                condition.apply(criteria.getState(), copy.getState()) &&
                condition.apply(criteria.getCountry(), copy.getCountry()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getServiceOrderId(), copy.getServiceOrderId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getLocationId(), copy.getLocationId()) &&
                condition.apply(criteria.getFranchiseId(), copy.getFranchiseId()) &&
                condition.apply(criteria.getCustomerId(), copy.getCustomerId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
