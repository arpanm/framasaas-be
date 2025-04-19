package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class InventoryHistoryCriteriaTest {

    @Test
    void newInventoryHistoryCriteriaHasAllFiltersNullTest() {
        var inventoryHistoryCriteria = new InventoryHistoryCriteria();
        assertThat(inventoryHistoryCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void inventoryHistoryCriteriaFluentMethodsCreatesFiltersTest() {
        var inventoryHistoryCriteria = new InventoryHistoryCriteria();

        setAllFilters(inventoryHistoryCriteria);

        assertThat(inventoryHistoryCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void inventoryHistoryCriteriaCopyCreatesNullFilterTest() {
        var inventoryHistoryCriteria = new InventoryHistoryCriteria();
        var copy = inventoryHistoryCriteria.copy();

        assertThat(inventoryHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(inventoryHistoryCriteria)
        );
    }

    @Test
    void inventoryHistoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var inventoryHistoryCriteria = new InventoryHistoryCriteria();
        setAllFilters(inventoryHistoryCriteria);

        var copy = inventoryHistoryCriteria.copy();

        assertThat(inventoryHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(inventoryHistoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var inventoryHistoryCriteria = new InventoryHistoryCriteria();

        assertThat(inventoryHistoryCriteria).hasToString("InventoryHistoryCriteria{}");
    }

    private static void setAllFilters(InventoryHistoryCriteria inventoryHistoryCriteria) {
        inventoryHistoryCriteria.id();
        inventoryHistoryCriteria.inventoryValue();
        inventoryHistoryCriteria.reason();
        inventoryHistoryCriteria.updatedBy();
        inventoryHistoryCriteria.updatedTime();
        inventoryHistoryCriteria.distinct();
    }

    private static Condition<InventoryHistoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInventoryValue()) &&
                condition.apply(criteria.getReason()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<InventoryHistoryCriteria> copyFiltersAre(
        InventoryHistoryCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInventoryValue(), copy.getInventoryValue()) &&
                condition.apply(criteria.getReason(), copy.getReason()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
