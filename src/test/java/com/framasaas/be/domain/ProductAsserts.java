package com.framasaas.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProductAllPropertiesEquals(Product expected, Product actual) {
        assertProductAutoGeneratedPropertiesEquals(expected, actual);
        assertProductAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProductAllUpdatablePropertiesEquals(Product expected, Product actual) {
        assertProductUpdatableFieldsEquals(expected, actual);
        assertProductUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProductAutoGeneratedPropertiesEquals(Product expected, Product actual) {
        assertThat(actual)
            .as("Verify Product auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProductUpdatableFieldsEquals(Product expected, Product actual) {
        assertThat(actual)
            .as("Verify Product relevant properties")
            .satisfies(a -> assertThat(a.getProductName()).as("check productName").isEqualTo(expected.getProductName()))
            .satisfies(a -> assertThat(a.getVendorProductId()).as("check vendorProductId").isEqualTo(expected.getVendorProductId()))
            .satisfies(a -> assertThat(a.getDescription()).as("check description").isEqualTo(expected.getDescription()))
            .satisfies(a -> assertThat(a.getPrice()).as("check price").isEqualTo(expected.getPrice()))
            .satisfies(a -> assertThat(a.getTax()).as("check tax").isEqualTo(expected.getTax()))
            .satisfies(a ->
                assertThat(a.getFranchiseCommission()).as("check franchiseCommission").isEqualTo(expected.getFranchiseCommission())
            )
            .satisfies(a -> assertThat(a.getFranchiseTax()).as("check franchiseTax").isEqualTo(expected.getFranchiseTax()))
            .satisfies(a -> assertThat(a.getProductType()).as("check productType").isEqualTo(expected.getProductType()))
            .satisfies(a -> assertThat(a.getIsActive()).as("check isActive").isEqualTo(expected.getIsActive()))
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
    public static void assertProductUpdatableRelationshipsEquals(Product expected, Product actual) {
        assertThat(actual)
            .as("Verify Product relationships")
            .satisfies(a -> assertThat(a.getCategory()).as("check category").isEqualTo(expected.getCategory()))
            .satisfies(a -> assertThat(a.getBrand()).as("check brand").isEqualTo(expected.getBrand()))
            .satisfies(a -> assertThat(a.getHsn()).as("check hsn").isEqualTo(expected.getHsn()));
    }
}
