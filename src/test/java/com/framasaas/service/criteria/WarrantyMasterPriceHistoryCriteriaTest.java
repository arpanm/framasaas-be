package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class WarrantyMasterPriceHistoryCriteriaTest {

    @Test
    void newWarrantyMasterPriceHistoryCriteriaHasAllFiltersNullTest() {
        var warrantyMasterPriceHistoryCriteria = new WarrantyMasterPriceHistoryCriteria();
        assertThat(warrantyMasterPriceHistoryCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void warrantyMasterPriceHistoryCriteriaFluentMethodsCreatesFiltersTest() {
        var warrantyMasterPriceHistoryCriteria = new WarrantyMasterPriceHistoryCriteria();

        setAllFilters(warrantyMasterPriceHistoryCriteria);

        assertThat(warrantyMasterPriceHistoryCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void warrantyMasterPriceHistoryCriteriaCopyCreatesNullFilterTest() {
        var warrantyMasterPriceHistoryCriteria = new WarrantyMasterPriceHistoryCriteria();
        var copy = warrantyMasterPriceHistoryCriteria.copy();

        assertThat(warrantyMasterPriceHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(warrantyMasterPriceHistoryCriteria)
        );
    }

    @Test
    void warrantyMasterPriceHistoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var warrantyMasterPriceHistoryCriteria = new WarrantyMasterPriceHistoryCriteria();
        setAllFilters(warrantyMasterPriceHistoryCriteria);

        var copy = warrantyMasterPriceHistoryCriteria.copy();

        assertThat(warrantyMasterPriceHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(warrantyMasterPriceHistoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var warrantyMasterPriceHistoryCriteria = new WarrantyMasterPriceHistoryCriteria();

        assertThat(warrantyMasterPriceHistoryCriteria).hasToString("WarrantyMasterPriceHistoryCriteria{}");
    }

    private static void setAllFilters(WarrantyMasterPriceHistoryCriteria warrantyMasterPriceHistoryCriteria) {
        warrantyMasterPriceHistoryCriteria.id();
        warrantyMasterPriceHistoryCriteria.price();
        warrantyMasterPriceHistoryCriteria.tax();
        warrantyMasterPriceHistoryCriteria.franchiseCommission();
        warrantyMasterPriceHistoryCriteria.franchiseTax();
        warrantyMasterPriceHistoryCriteria.updatedBy();
        warrantyMasterPriceHistoryCriteria.updatedTime();
        warrantyMasterPriceHistoryCriteria.additionalAttributeId();
        warrantyMasterPriceHistoryCriteria.warrantyMasterId();
        warrantyMasterPriceHistoryCriteria.distinct();
    }

    private static Condition<WarrantyMasterPriceHistoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getTax()) &&
                condition.apply(criteria.getFranchiseCommission()) &&
                condition.apply(criteria.getFranchiseTax()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getWarrantyMasterId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<WarrantyMasterPriceHistoryCriteria> copyFiltersAre(
        WarrantyMasterPriceHistoryCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getTax(), copy.getTax()) &&
                condition.apply(criteria.getFranchiseCommission(), copy.getFranchiseCommission()) &&
                condition.apply(criteria.getFranchiseTax(), copy.getFranchiseTax()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getWarrantyMasterId(), copy.getWarrantyMasterId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
