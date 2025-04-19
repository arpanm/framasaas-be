package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BrandCriteriaTest {

    @Test
    void newBrandCriteriaHasAllFiltersNullTest() {
        var brandCriteria = new BrandCriteria();
        assertThat(brandCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void brandCriteriaFluentMethodsCreatesFiltersTest() {
        var brandCriteria = new BrandCriteria();

        setAllFilters(brandCriteria);

        assertThat(brandCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void brandCriteriaCopyCreatesNullFilterTest() {
        var brandCriteria = new BrandCriteria();
        var copy = brandCriteria.copy();

        assertThat(brandCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(brandCriteria)
        );
    }

    @Test
    void brandCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var brandCriteria = new BrandCriteria();
        setAllFilters(brandCriteria);

        var copy = brandCriteria.copy();

        assertThat(brandCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(brandCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var brandCriteria = new BrandCriteria();

        assertThat(brandCriteria).hasToString("BrandCriteria{}");
    }

    private static void setAllFilters(BrandCriteria brandCriteria) {
        brandCriteria.id();
        brandCriteria.brandName();
        brandCriteria.logoPath();
        brandCriteria.vendorBrandId();
        brandCriteria.description();
        brandCriteria.isActive();
        brandCriteria.createddBy();
        brandCriteria.createdTime();
        brandCriteria.updatedBy();
        brandCriteria.updatedTime();
        brandCriteria.productId();
        brandCriteria.additionalAttributeId();
        brandCriteria.franchiseRuleId();
        brandCriteria.fieldAgentSkillRuleId();
        brandCriteria.distinct();
    }

    private static Condition<BrandCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getBrandName()) &&
                condition.apply(criteria.getLogoPath()) &&
                condition.apply(criteria.getVendorBrandId()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getProductId()) &&
                condition.apply(criteria.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseRuleId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BrandCriteria> copyFiltersAre(BrandCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getBrandName(), copy.getBrandName()) &&
                condition.apply(criteria.getLogoPath(), copy.getLogoPath()) &&
                condition.apply(criteria.getVendorBrandId(), copy.getVendorBrandId()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getProductId(), copy.getProductId()) &&
                condition.apply(criteria.getAdditionalAttributeId(), copy.getAdditionalAttributeId()) &&
                condition.apply(criteria.getFranchiseRuleId(), copy.getFranchiseRuleId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleId(), copy.getFieldAgentSkillRuleId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
