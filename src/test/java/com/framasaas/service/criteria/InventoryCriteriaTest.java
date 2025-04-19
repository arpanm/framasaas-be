package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class InventoryCriteriaTest {

    @Test
    void newInventoryCriteriaHasAllFiltersNullTest() {
        var inventoryCriteria = new InventoryCriteria();
        assertThat(inventoryCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void inventoryCriteriaFluentMethodsCreatesFiltersTest() {
        var inventoryCriteria = new InventoryCriteria();

        setAllFilters(inventoryCriteria);

        assertThat(inventoryCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void inventoryCriteriaCopyCreatesNullFilterTest() {
        var inventoryCriteria = new InventoryCriteria();
        var copy = inventoryCriteria.copy();

        assertThat(inventoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(inventoryCriteria)
        );
    }

    @Test
    void inventoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var inventoryCriteria = new InventoryCriteria();
        setAllFilters(inventoryCriteria);

        var copy = inventoryCriteria.copy();

        assertThat(inventoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(inventoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var inventoryCriteria = new InventoryCriteria();

        assertThat(inventoryCriteria).hasToString("InventoryCriteria{}");
    }

    private static void setAllFilters(InventoryCriteria inventoryCriteria) {
        inventoryCriteria.id();
        inventoryCriteria.inventoryValue();
        inventoryCriteria.isSellable();
        inventoryCriteria.createddBy();
        inventoryCriteria.createdTime();
        inventoryCriteria.updatedBy();
        inventoryCriteria.updatedTime();
        inventoryCriteria.additionalAttributeId();
        inventoryCriteria.productId();
        inventoryCriteria.locationId();
        inventoryCriteria.distinct();
    }

    private static Condition<InventoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInventoryValue()) &&
                condition.apply(criteria.getIsSellable()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getProductId()) &&
                condition.apply(criteria.getLocationId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<InventoryCriteria> copyFiltersAre(InventoryCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInventoryValue(), copy.getInventoryValue()) &&
                condition.apply(criteria.getIsSellable(), copy.getIsSellable()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getProductId(), copy.getProductId()) &&
                condition.apply(criteria.getLocationId(), copy.getLocationId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
