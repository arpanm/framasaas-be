package com.framasaas.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AdditionalAttributeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdditionalAttributeAllPropertiesEquals(AdditionalAttribute expected, AdditionalAttribute actual) {
        assertAdditionalAttributeAutoGeneratedPropertiesEquals(expected, actual);
        assertAdditionalAttributeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdditionalAttributeAllUpdatablePropertiesEquals(AdditionalAttribute expected, AdditionalAttribute actual) {
        assertAdditionalAttributeUpdatableFieldsEquals(expected, actual);
        assertAdditionalAttributeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdditionalAttributeAutoGeneratedPropertiesEquals(AdditionalAttribute expected, AdditionalAttribute actual) {
        assertThat(actual)
            .as("Verify AdditionalAttribute auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdditionalAttributeUpdatableFieldsEquals(AdditionalAttribute expected, AdditionalAttribute actual) {
        assertThat(actual)
            .as("Verify AdditionalAttribute relevant properties")
            .satisfies(a -> assertThat(a.getAttributeName()).as("check attributeName").isEqualTo(expected.getAttributeName()))
            .satisfies(a -> assertThat(a.getAttributeValue()).as("check attributeValue").isEqualTo(expected.getAttributeValue()))
            .satisfies(a -> assertThat(a.getAttributeType()).as("check attributeType").isEqualTo(expected.getAttributeType()))
            .satisfies(a -> assertThat(a.getCreateddBy()).as("check createddBy").isEqualTo(expected.getCreateddBy()))
            .satisfies(a -> assertThat(a.getCreatedTime()).as("check createdTime").isEqualTo(expected.getCreatedTime()))
            .satisfies(a -> assertThat(a.getUpdatedBy()).as("check updatedBy").isEqualTo(expected.getUpdatedBy()))
            .satisfies(a -> assertThat(a.getUpdatedTime()).as("check updatedTime").isEqualTo(expected.getUpdatedTime()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAdditionalAttributeUpdatableRelationshipsEquals(AdditionalAttribute expected, AdditionalAttribute actual) {
        assertThat(actual)
            .as("Verify AdditionalAttribute relationships")
            .satisfies(a -> assertThat(a.getFranchise()).as("check franchise").isEqualTo(expected.getFranchise()))
            .satisfies(a -> assertThat(a.getFranchiseStatus()).as("check franchiseStatus").isEqualTo(expected.getFranchiseStatus()))
            .satisfies(a ->
                assertThat(a.getFranchisePerformance()).as("check franchisePerformance").isEqualTo(expected.getFranchisePerformance())
            )
            .satisfies(a -> assertThat(a.getBrand()).as("check brand").isEqualTo(expected.getBrand()))
            .satisfies(a -> assertThat(a.getCategory()).as("check category").isEqualTo(expected.getCategory()))
            .satisfies(a -> assertThat(a.getAddress()).as("check address").isEqualTo(expected.getAddress()))
            .satisfies(a -> assertThat(a.getLocation()).as("check location").isEqualTo(expected.getLocation()))
            .satisfies(a -> assertThat(a.getFranchiseUser()).as("check franchiseUser").isEqualTo(expected.getFranchiseUser()))
            .satisfies(a -> assertThat(a.getCustomer()).as("check customer").isEqualTo(expected.getCustomer()))
            .satisfies(a -> assertThat(a.getDocument()).as("check document").isEqualTo(expected.getDocument()))
            .satisfies(a -> assertThat(a.getProduct()).as("check product").isEqualTo(expected.getProduct()))
            .satisfies(a -> assertThat(a.getHsn()).as("check hsn").isEqualTo(expected.getHsn()))
            .satisfies(a -> assertThat(a.getPriceHistory()).as("check priceHistory").isEqualTo(expected.getPriceHistory()))
            .satisfies(a -> assertThat(a.getWarrantyMaster()).as("check warrantyMaster").isEqualTo(expected.getWarrantyMaster()))
            .satisfies(a ->
                assertThat(a.getWarrantyMasterPriceHistory())
                    .as("check warrantyMasterPriceHistory")
                    .isEqualTo(expected.getWarrantyMasterPriceHistory())
            )
            .satisfies(a -> assertThat(a.getArticle()).as("check article").isEqualTo(expected.getArticle()))
            .satisfies(a -> assertThat(a.getArticleWarranty()).as("check articleWarranty").isEqualTo(expected.getArticleWarranty()))
            .satisfies(a ->
                assertThat(a.getArticleWarrantyDocument())
                    .as("check articleWarrantyDocument")
                    .isEqualTo(expected.getArticleWarrantyDocument())
            )
            .satisfies(a -> assertThat(a.getServiceOrder()).as("check serviceOrder").isEqualTo(expected.getServiceOrder()))
            .satisfies(a ->
                assertThat(a.getServiceOrderPayment()).as("check serviceOrderPayment").isEqualTo(expected.getServiceOrderPayment())
            )
            .satisfies(a ->
                assertThat(a.getServiceOrderFranchiseAssignment())
                    .as("check serviceOrderFranchiseAssignment")
                    .isEqualTo(expected.getServiceOrderFranchiseAssignment())
            )
            .satisfies(a ->
                assertThat(a.getServiceOrderFieldAgentAssignment())
                    .as("check serviceOrderFieldAgentAssignment")
                    .isEqualTo(expected.getServiceOrderFieldAgentAssignment())
            )
            .satisfies(a ->
                assertThat(a.getFranchiseAllocationRuleSet())
                    .as("check franchiseAllocationRuleSet")
                    .isEqualTo(expected.getFranchiseAllocationRuleSet())
            )
            .satisfies(a ->
                assertThat(a.getFranchiseAllocationRule())
                    .as("check franchiseAllocationRule")
                    .isEqualTo(expected.getFranchiseAllocationRule())
            )
            .satisfies(a ->
                assertThat(a.getFieldAgentSkillRuleSet()).as("check fieldAgentSkillRuleSet").isEqualTo(expected.getFieldAgentSkillRuleSet())
            )
            .satisfies(a ->
                assertThat(a.getFieldAgentSkillRule()).as("check fieldAgentSkillRule").isEqualTo(expected.getFieldAgentSkillRule())
            )
            .satisfies(a ->
                assertThat(a.getServiceOrderAssignment()).as("check serviceOrderAssignment").isEqualTo(expected.getServiceOrderAssignment())
            );
    }
}
