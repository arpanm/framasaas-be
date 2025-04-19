package com.framasaas.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AdditionalAttributeCriteriaTest {

    @Test
    void newAdditionalAttributeCriteriaHasAllFiltersNullTest() {
        var additionalAttributeCriteria = new AdditionalAttributeCriteria();
        assertThat(additionalAttributeCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void additionalAttributeCriteriaFluentMethodsCreatesFiltersTest() {
        var additionalAttributeCriteria = new AdditionalAttributeCriteria();

        setAllFilters(additionalAttributeCriteria);

        assertThat(additionalAttributeCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void additionalAttributeCriteriaCopyCreatesNullFilterTest() {
        var additionalAttributeCriteria = new AdditionalAttributeCriteria();
        var copy = additionalAttributeCriteria.copy();

        assertThat(additionalAttributeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(additionalAttributeCriteria)
        );
    }

    @Test
    void additionalAttributeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var additionalAttributeCriteria = new AdditionalAttributeCriteria();
        setAllFilters(additionalAttributeCriteria);

        var copy = additionalAttributeCriteria.copy();

        assertThat(additionalAttributeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(additionalAttributeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var additionalAttributeCriteria = new AdditionalAttributeCriteria();

        assertThat(additionalAttributeCriteria).hasToString("AdditionalAttributeCriteria{}");
    }

    private static void setAllFilters(AdditionalAttributeCriteria additionalAttributeCriteria) {
        additionalAttributeCriteria.id();
        additionalAttributeCriteria.attributeName();
        additionalAttributeCriteria.attributeValue();
        additionalAttributeCriteria.attributeType();
        additionalAttributeCriteria.createddBy();
        additionalAttributeCriteria.createdTime();
        additionalAttributeCriteria.updatedBy();
        additionalAttributeCriteria.updatedTime();
        additionalAttributeCriteria.additionalAttributePossibleValueId();
        additionalAttributeCriteria.franchiseId();
        additionalAttributeCriteria.franchiseStatusId();
        additionalAttributeCriteria.franchisePerformanceId();
        additionalAttributeCriteria.brandId();
        additionalAttributeCriteria.categoryId();
        additionalAttributeCriteria.addressId();
        additionalAttributeCriteria.locationId();
        additionalAttributeCriteria.franchiseUserId();
        additionalAttributeCriteria.customerId();
        additionalAttributeCriteria.supportDocumentId();
        additionalAttributeCriteria.productId();
        additionalAttributeCriteria.hsnId();
        additionalAttributeCriteria.priceHistoryId();
        additionalAttributeCriteria.warrantyMasterId();
        additionalAttributeCriteria.warrantyMasterPriceHistoryId();
        additionalAttributeCriteria.articleId();
        additionalAttributeCriteria.articleWarrantyId();
        additionalAttributeCriteria.serviceOrderId();
        additionalAttributeCriteria.serviceOrderPaymentId();
        additionalAttributeCriteria.serviceOrderFranchiseAssignmentId();
        additionalAttributeCriteria.serviceOrderFieldAgentAssignmentId();
        additionalAttributeCriteria.franchiseAllocationRuleSetId();
        additionalAttributeCriteria.franchiseAllocationRuleId();
        additionalAttributeCriteria.fieldAgentSkillRuleSetId();
        additionalAttributeCriteria.fieldAgentSkillRuleId();
        additionalAttributeCriteria.inventoryLocationId();
        additionalAttributeCriteria.inventoryId();
        additionalAttributeCriteria.documentId();
        additionalAttributeCriteria.articleWarrantyDocumentId();
        additionalAttributeCriteria.serviceOrderAssignmentId();
        additionalAttributeCriteria.distinct();
    }

    private static Condition<AdditionalAttributeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getAttributeName()) &&
                condition.apply(criteria.getAttributeValue()) &&
                condition.apply(criteria.getAttributeType()) &&
                condition.apply(criteria.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributePossibleValueId()) &&
                condition.apply(criteria.getFranchiseId()) &&
                condition.apply(criteria.getFranchiseStatusId()) &&
                condition.apply(criteria.getFranchisePerformanceId()) &&
                condition.apply(criteria.getBrandId()) &&
                condition.apply(criteria.getCategoryId()) &&
                condition.apply(criteria.getAddressId()) &&
                condition.apply(criteria.getLocationId()) &&
                condition.apply(criteria.getFranchiseUserId()) &&
                condition.apply(criteria.getCustomerId()) &&
                condition.apply(criteria.getSupportDocumentId()) &&
                condition.apply(criteria.getProductId()) &&
                condition.apply(criteria.getHsnId()) &&
                condition.apply(criteria.getPriceHistoryId()) &&
                condition.apply(criteria.getWarrantyMasterId()) &&
                condition.apply(criteria.getWarrantyMasterPriceHistoryId()) &&
                condition.apply(criteria.getArticleId()) &&
                condition.apply(criteria.getArticleWarrantyId()) &&
                condition.apply(criteria.getServiceOrderId()) &&
                condition.apply(criteria.getServiceOrderPaymentId()) &&
                condition.apply(criteria.getServiceOrderFranchiseAssignmentId()) &&
                condition.apply(criteria.getServiceOrderFieldAgentAssignmentId()) &&
                condition.apply(criteria.getFranchiseAllocationRuleSetId()) &&
                condition.apply(criteria.getFranchiseAllocationRuleId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleSetId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleId()) &&
                condition.apply(criteria.getInventoryLocationId()) &&
                condition.apply(criteria.getInventoryId()) &&
                condition.apply(criteria.getDocumentId()) &&
                condition.apply(criteria.getArticleWarrantyDocumentId()) &&
                condition.apply(criteria.getServiceOrderAssignmentId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AdditionalAttributeCriteria> copyFiltersAre(
        AdditionalAttributeCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getAttributeName(), copy.getAttributeName()) &&
                condition.apply(criteria.getAttributeValue(), copy.getAttributeValue()) &&
                condition.apply(criteria.getAttributeType(), copy.getAttributeType()) &&
                condition.apply(criteria.getCreateddBy(), copy.getCreateddBy()) &&
                condition.apply(criteria.getCreatedTime(), copy.getCreatedTime()) &&
                condition.apply(criteria.getUpdatedBy(), copy.getUpdatedBy()) &&
                condition.apply(criteria.getUpdatedTime(), copy.getUpdatedTime()) &&
                condition.apply(criteria.getAdditionalAttributePossibleValueId(), copy.getAdditionalAttributePossibleValueId()) &&
                condition.apply(criteria.getFranchiseId(), copy.getFranchiseId()) &&
                condition.apply(criteria.getFranchiseStatusId(), copy.getFranchiseStatusId()) &&
                condition.apply(criteria.getFranchisePerformanceId(), copy.getFranchisePerformanceId()) &&
                condition.apply(criteria.getBrandId(), copy.getBrandId()) &&
                condition.apply(criteria.getCategoryId(), copy.getCategoryId()) &&
                condition.apply(criteria.getAddressId(), copy.getAddressId()) &&
                condition.apply(criteria.getLocationId(), copy.getLocationId()) &&
                condition.apply(criteria.getFranchiseUserId(), copy.getFranchiseUserId()) &&
                condition.apply(criteria.getCustomerId(), copy.getCustomerId()) &&
                condition.apply(criteria.getSupportDocumentId(), copy.getSupportDocumentId()) &&
                condition.apply(criteria.getProductId(), copy.getProductId()) &&
                condition.apply(criteria.getHsnId(), copy.getHsnId()) &&
                condition.apply(criteria.getPriceHistoryId(), copy.getPriceHistoryId()) &&
                condition.apply(criteria.getWarrantyMasterId(), copy.getWarrantyMasterId()) &&
                condition.apply(criteria.getWarrantyMasterPriceHistoryId(), copy.getWarrantyMasterPriceHistoryId()) &&
                condition.apply(criteria.getArticleId(), copy.getArticleId()) &&
                condition.apply(criteria.getArticleWarrantyId(), copy.getArticleWarrantyId()) &&
                condition.apply(criteria.getServiceOrderId(), copy.getServiceOrderId()) &&
                condition.apply(criteria.getServiceOrderPaymentId(), copy.getServiceOrderPaymentId()) &&
                condition.apply(criteria.getServiceOrderFranchiseAssignmentId(), copy.getServiceOrderFranchiseAssignmentId()) &&
                condition.apply(criteria.getServiceOrderFieldAgentAssignmentId(), copy.getServiceOrderFieldAgentAssignmentId()) &&
                condition.apply(criteria.getFranchiseAllocationRuleSetId(), copy.getFranchiseAllocationRuleSetId()) &&
                condition.apply(criteria.getFranchiseAllocationRuleId(), copy.getFranchiseAllocationRuleId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleSetId(), copy.getFieldAgentSkillRuleSetId()) &&
                condition.apply(criteria.getFieldAgentSkillRuleId(), copy.getFieldAgentSkillRuleId()) &&
                condition.apply(criteria.getInventoryLocationId(), copy.getInventoryLocationId()) &&
                condition.apply(criteria.getInventoryId(), copy.getInventoryId()) &&
                condition.apply(criteria.getDocumentId(), copy.getDocumentId()) &&
                condition.apply(criteria.getArticleWarrantyDocumentId(), copy.getArticleWarrantyDocumentId()) &&
                condition.apply(criteria.getServiceOrderAssignmentId(), copy.getServiceOrderAssignmentId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
