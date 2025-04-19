package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class InventoryLocationCriteriaTest {

    @Test
    void newInventoryLocationCriteriaHasAllFiltersNullTest() {
        var inventoryLocationCriteria = new InventoryLocationCriteria();
        assertThat(inventoryLocationCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void inventoryLocationCriteriaFluentMethodsCreatesFiltersTest() {
        var inventoryLocationCriteria = new InventoryLocationCriteria();

        setAllFilters(inventoryLocationCriteria);

        assertThat(inventoryLocationCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void inventoryLocationCriteriaCopyCreatesNullFilterTest() {
        var inventoryLocationCriteria = new InventoryLocationCriteria();
        var copy = inventoryLocationCriteria.copy();

        assertThat(inventoryLocationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(inventoryLocationCriteria)
        );
    }

    @Test
    void inventoryLocationCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var inventoryLocationCriteria = new InventoryLocationCriteria();
        setAllFilters(inventoryLocationCriteria);

        var copy = inventoryLocationCriteria.copy();

        assertThat(inventoryLocationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(inventoryLocationCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var inventoryLocationCriteria = new InventoryLocationCriteria();

        assertThat(inventoryLocationCriteria).hasToString("InventoryLocationCriteria{}");
    }

    private static void setAllFilters(InventoryLocationCriteria inventoryLocationCriteria) {
        inventoryLocationCriteria.id();
        inventoryLocationCriteria.name();
        inventoryLocationCriteria.locationType();
        inventoryLocationCriteria.isActive();
        inventoryLocationCriteria.createddBy();
        inventoryLocationCriteria.createdTime();
        inventoryLocationCriteria.updatedBy();
        inventoryLocationCriteria.updatedTime();
        inventoryLocationCriteria.inventoryId();
        inventoryLocationCriteria.additionalAttributeId();
        inventoryLocationCriteria.distinct();
    }

    private static Condition<InventoryLocationCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getLocationType()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getInventoryId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<InventoryLocationCriteria> copyFiltersAre(
        InventoryLocationCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getLocationType(), copy.getLocationType()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getInventoryId(), copy.getInventoryId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
