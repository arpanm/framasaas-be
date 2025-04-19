package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProductCriteriaTest {

    @Test
    void newProductCriteriaHasAllFiltersNullTest() {
        var productCriteria = new ProductCriteria();
        assertThat(productCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void productCriteriaFluentMethodsCreatesFiltersTest() {
        var productCriteria = new ProductCriteria();

        setAllFilters(productCriteria);

        assertThat(productCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void productCriteriaCopyCreatesNullFilterTest() {
        var productCriteria = new ProductCriteria();
        var copy = productCriteria.copy();

        assertThat(productCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(productCriteria)
        );
    }

    @Test
    void productCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var productCriteria = new ProductCriteria();
        setAllFilters(productCriteria);

        var copy = productCriteria.copy();

        assertThat(productCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(productCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var productCriteria = new ProductCriteria();

        assertThat(productCriteria).hasToString("ProductCriteria{}");
    }

    private static void setAllFilters(ProductCriteria productCriteria) {
        productCriteria.id();
        productCriteria.productName();
        productCriteria.vendorProductId();
        productCriteria.description();
        productCriteria.price();
        productCriteria.tax();
        productCriteria.franchiseCommission();
        productCriteria.franchiseTax();
        productCriteria.productType();
        productCriteria.isActive();
        productCriteria.createddBy();
        productCriteria.createdTime();
        productCriteria.updatedBy();
        productCriteria.updatedTime();
        productCriteria.productPriceHistoryId();
        productCriteria.warrantyMasterId();
        productCriteria.articleId();
        productCriteria.serviceOrderMasterId();
        productCriteria.serviceOrderSpareId();
        productCriteria.inventoryId();
        productCriteria.additionalAttributeId();
        productCriteria.categoryId();
        productCriteria.brandId();
        productCriteria.hsnId();
        productCriteria.coveredUnderWarrantyId();
        productCriteria.distinct();
    }

    private static Condition<ProductCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getProductName()) &&
                condition.apply(criteria.getVendorProductId()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getTax()) &&
                condition.apply(criteria.getFranchiseCommission()) &&
                condition.apply(criteria.getFranchiseTax()) &&
                condition.apply(criteria.getProductType()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getProductPriceHistoryId()) &&
                condition.apply(criteria.getWarrantyMasterId()) &&
                condition.apply(criteria.getArticleId()) &&
                condition.apply(criteria.getServiceOrderMasterId()) &&
                condition.apply(criteria.getServiceOrderSpareId()) &&
                condition.apply(criteria.getInventoryId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getCategoryId()) &&
                condition.apply(criteria.getBrandId()) &&
                condition.apply(criteria.getHsnId()) &&
                condition.apply(criteria.getCoveredUnderWarrantyId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProductCriteria> copyFiltersAre(ProductCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getProductName(), copy.getProductName()) &&
                condition.apply(criteria.getVendorProductId(), copy.getVendorProductId()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getTax(), copy.getTax()) &&
                condition.apply(criteria.getFranchiseCommission(), copy.getFranchiseCommission()) &&
                condition.apply(criteria.getFranchiseTax(), copy.getFranchiseTax()) &&
                condition.apply(criteria.getProductType(), copy.getProductType()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getProductPriceHistoryId(), copy.getProductPriceHistoryId()) &&
                condition.apply(criteria.getWarrantyMasterId(), copy.getWarrantyMasterId()) &&
                condition.apply(criteria.getArticleId(), copy.getArticleId()) &&
                condition.apply(criteria.getServiceOrderMasterId(), copy.getServiceOrderMasterId()) &&
                condition.apply(criteria.getServiceOrderSpareId(), copy.getServiceOrderSpareId()) &&
                condition.apply(criteria.getInventoryId(), copy.getInventoryId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getCategoryId(), copy.getCategoryId()) &&
                condition.apply(criteria.getBrandId(), copy.getBrandId()) &&
                condition.apply(criteria.getHsnId(), copy.getHsnId()) &&
                condition.apply(criteria.getCoveredUnderWarrantyId(), copy.getCoveredUnderWarrantyId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
