package com.framasaas.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCategoryAllPropertiesEquals(Category expected, Category actual) {
        assertCategoryAutoGeneratedPropertiesEquals(expected, actual);
        assertCategoryAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCategoryAllUpdatablePropertiesEquals(Category expected, Category actual) {
        assertCategoryUpdatableFieldsEquals(expected, actual);
        assertCategoryUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCategoryAutoGeneratedPropertiesEquals(Category expected, Category actual) {
        assertThat(actual)
            .as("Verify Category auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCategoryUpdatableFieldsEquals(Category expected, Category actual) {
        assertThat(actual)
            .as("Verify Category relevant properties")
            .satisfies(a -> assertThat(a.getCategoryName()).as("check categoryName").isEqualTo(expected.getCategoryName()))
            .satisfies(a -> assertThat(a.getImagePath()).as("check imagePath").isEqualTo(expected.getImagePath()))
            .satisfies(a -> assertThat(a.getVendorCategoryId()).as("check vendorCategoryId").isEqualTo(expected.getVendorCategoryId()))
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
    public static void assertCategoryUpdatableRelationshipsEquals(Category expected, Category actual) {
        assertThat(actual)
            .as("Verify Category relationships")
            .satisfies(a -> assertThat(a.getFranchiseRule()).as("check franchiseRule").isEqualTo(expected.getFranchiseRule()))
            .satisfies(a ->
                assertThat(a.getFieldAgentSkillRule()).as("check fieldAgentSkillRule").isEqualTo(expected.getFieldAgentSkillRule())
            );
    }
}
