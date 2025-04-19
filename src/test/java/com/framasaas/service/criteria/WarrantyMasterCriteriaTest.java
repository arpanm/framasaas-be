package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class WarrantyMasterCriteriaTest {

    @Test
    void newWarrantyMasterCriteriaHasAllFiltersNullTest() {
        var warrantyMasterCriteria = new WarrantyMasterCriteria();
        assertThat(warrantyMasterCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void warrantyMasterCriteriaFluentMethodsCreatesFiltersTest() {
        var warrantyMasterCriteria = new WarrantyMasterCriteria();

        setAllFilters(warrantyMasterCriteria);

        assertThat(warrantyMasterCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void warrantyMasterCriteriaCopyCreatesNullFilterTest() {
        var warrantyMasterCriteria = new WarrantyMasterCriteria();
        var copy = warrantyMasterCriteria.copy();

        assertThat(warrantyMasterCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(warrantyMasterCriteria)
        );
    }

    @Test
    void warrantyMasterCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var warrantyMasterCriteria = new WarrantyMasterCriteria();
        setAllFilters(warrantyMasterCriteria);

        var copy = warrantyMasterCriteria.copy();

        assertThat(warrantyMasterCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(warrantyMasterCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var warrantyMasterCriteria = new WarrantyMasterCriteria();

        assertThat(warrantyMasterCriteria).hasToString("WarrantyMasterCriteria{}");
    }

    private static void setAllFilters(WarrantyMasterCriteria warrantyMasterCriteria) {
        warrantyMasterCriteria.id();
        warrantyMasterCriteria.name();
        warrantyMasterCriteria.vendorWarrantyMasterId();
        warrantyMasterCriteria.warrantyType();
        warrantyMasterCriteria.description();
        warrantyMasterCriteria.price();
        warrantyMasterCriteria.tax();
        warrantyMasterCriteria.franchiseCommission();
        warrantyMasterCriteria.franchiseTax();
        warrantyMasterCriteria.periodInMonths();
        warrantyMasterCriteria.taxRate();
        warrantyMasterCriteria.coverage();
        warrantyMasterCriteria.exclusion();
        warrantyMasterCriteria.isActive();
        warrantyMasterCriteria.createddBy();
        warrantyMasterCriteria.createdTime();
        warrantyMasterCriteria.updatedBy();
        warrantyMasterCriteria.updatedTime();
        warrantyMasterCriteria.warrantyMasterPriceHistoryId();
        warrantyMasterCriteria.articleWarrantyDetailsId();
        warrantyMasterCriteria.additionalAttributeId();
        warrantyMasterCriteria.coveredSpareId();
        warrantyMasterCriteria.productId();
        warrantyMasterCriteria.distinct();
    }

    private static Condition<WarrantyMasterCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getVendorWarrantyMasterId()) &&
                condition.apply(criteria.getWarrantyType()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getTax()) &&
                condition.apply(criteria.getFranchiseCommission()) &&
                condition.apply(criteria.getFranchiseTax()) &&
                condition.apply(criteria.getPeriodInMonths()) &&
                condition.apply(criteria.getTaxRate()) &&
                condition.apply(criteria.getCoverage()) &&
                condition.apply(criteria.getExclusion()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getWarrantyMasterPriceHistoryId()) &&
                condition.apply(criteria.getArticleWarrantyDetailsId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getCoveredSpareId()) &&
                condition.apply(criteria.getProductId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<WarrantyMasterCriteria> copyFiltersAre(
        WarrantyMasterCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getVendorWarrantyMasterId(), copy.getVendorWarrantyMasterId()) &&
                condition.apply(criteria.getWarrantyType(), copy.getWarrantyType()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getTax(), copy.getTax()) &&
                condition.apply(criteria.getFranchiseCommission(), copy.getFranchiseCommission()) &&
                condition.apply(criteria.getFranchiseTax(), copy.getFranchiseTax()) &&
                condition.apply(criteria.getPeriodInMonths(), copy.getPeriodInMonths()) &&
                condition.apply(criteria.getTaxRate(), copy.getTaxRate()) &&
                condition.apply(criteria.getCoverage(), copy.getCoverage()) &&
                condition.apply(criteria.getExclusion(), copy.getExclusion()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getWarrantyMasterPriceHistoryId(), copy.getWarrantyMasterPriceHistoryId()) &&
                condition.apply(criteria.getArticleWarrantyDetailsId(), copy.getArticleWarrantyDetailsId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getCoveredSpareId(), copy.getCoveredSpareId()) &&
                condition.apply(criteria.getProductId(), copy.getProductId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
