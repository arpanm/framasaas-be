package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProductPriceHistoryCriteriaTest {

    @Test
    void newProductPriceHistoryCriteriaHasAllFiltersNullTest() {
        var productPriceHistoryCriteria = new ProductPriceHistoryCriteria();
        assertThat(productPriceHistoryCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void productPriceHistoryCriteriaFluentMethodsCreatesFiltersTest() {
        var productPriceHistoryCriteria = new ProductPriceHistoryCriteria();

        setAllFilters(productPriceHistoryCriteria);

        assertThat(productPriceHistoryCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void productPriceHistoryCriteriaCopyCreatesNullFilterTest() {
        var productPriceHistoryCriteria = new ProductPriceHistoryCriteria();
        var copy = productPriceHistoryCriteria.copy();

        assertThat(productPriceHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(productPriceHistoryCriteria)
        );
    }

    @Test
    void productPriceHistoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var productPriceHistoryCriteria = new ProductPriceHistoryCriteria();
        setAllFilters(productPriceHistoryCriteria);

        var copy = productPriceHistoryCriteria.copy();

        assertThat(productPriceHistoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(productPriceHistoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var productPriceHistoryCriteria = new ProductPriceHistoryCriteria();

        assertThat(productPriceHistoryCriteria).hasToString("ProductPriceHistoryCriteria{}");
    }

    private static void setAllFilters(ProductPriceHistoryCriteria productPriceHistoryCriteria) {
        productPriceHistoryCriteria.id();
        productPriceHistoryCriteria.price();
        productPriceHistoryCriteria.tax();
        productPriceHistoryCriteria.franchiseCommission();
        productPriceHistoryCriteria.franchiseTax();
        productPriceHistoryCriteria.updateDescription();
        productPriceHistoryCriteria.updatedBy();
        productPriceHistoryCriteria.updatedTime();
        productPriceHistoryCriteria.additionalAttributeId();
        productPriceHistoryCriteria.productId();
        productPriceHistoryCriteria.distinct();
    }

    private static Condition<ProductPriceHistoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getTax()) &&
                condition.apply(criteria.getFranchiseCommission()) &&
                condition.apply(criteria.getFranchiseTax()) &&
                condition.apply(criteria.getUpdateDescription()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getProductId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProductPriceHistoryCriteria> copyFiltersAre(
        ProductPriceHistoryCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getTax(), copy.getTax()) &&
                condition.apply(criteria.getFranchiseCommission(), copy.getFranchiseCommission()) &&
                condition.apply(criteria.getFranchiseTax(), copy.getFranchiseTax()) &&
                condition.apply(criteria.getUpdateDescription(), copy.getUpdateDescription()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getProductId(), copy.getProductId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
