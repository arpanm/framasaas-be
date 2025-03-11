package com.framasaas.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BrandAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBrandAllPropertiesEquals(Brand expected, Brand actual) {
        assertBrandAutoGeneratedPropertiesEquals(expected, actual);
        assertBrandAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBrandAllUpdatablePropertiesEquals(Brand expected, Brand actual) {
        assertBrandUpdatableFieldsEquals(expected, actual);
        assertBrandUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBrandAutoGeneratedPropertiesEquals(Brand expected, Brand actual) {
        assertThat(actual)
            .as("Verify Brand auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBrandUpdatableFieldsEquals(Brand expected, Brand actual) {
        assertThat(actual)
            .as("Verify Brand relevant properties")
            .satisfies(a -> assertThat(a.getBrandName()).as("check brandName").isEqualTo(expected.getBrandName()))
            .satisfies(a -> assertThat(a.getLogoPath()).as("check logoPath").isEqualTo(expected.getLogoPath()))
            .satisfies(a -> assertThat(a.getVendorBrandId()).as("check vendorBrandId").isEqualTo(expected.getVendorBrandId()))
            .satisfies(a -> assertThat(a.getDescription()).as("check description").isEqualTo(expected.getDescription()))
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
    public static void assertBrandUpdatableRelationshipsEquals(Brand expected, Brand actual) {
        assertThat(actual)
            .as("Verify Brand relationships")
            .satisfies(a -> assertThat(a.getFranchiseRule()).as("check franchiseRule").isEqualTo(expected.getFranchiseRule()));
    }
}
