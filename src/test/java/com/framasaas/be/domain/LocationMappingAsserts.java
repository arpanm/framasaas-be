package com.framasaas.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationMappingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationMappingAllPropertiesEquals(LocationMapping expected, LocationMapping actual) {
        assertLocationMappingAutoGeneratedPropertiesEquals(expected, actual);
        assertLocationMappingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationMappingAllUpdatablePropertiesEquals(LocationMapping expected, LocationMapping actual) {
        assertLocationMappingUpdatableFieldsEquals(expected, actual);
        assertLocationMappingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationMappingAutoGeneratedPropertiesEquals(LocationMapping expected, LocationMapping actual) {
        assertThat(actual)
            .as("Verify LocationMapping auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationMappingUpdatableFieldsEquals(LocationMapping expected, LocationMapping actual) {
        assertThat(actual)
            .as("Verify LocationMapping relevant properties")
            .satisfies(a -> assertThat(a.getLocationName()).as("check locationName").isEqualTo(expected.getLocationName()))
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
    public static void assertLocationMappingUpdatableRelationshipsEquals(LocationMapping expected, LocationMapping actual) {
        assertThat(actual)
            .as("Verify LocationMapping relationships")
            .satisfies(a -> assertThat(a.getFranchiseRule()).as("check franchiseRule").isEqualTo(expected.getFranchiseRule()));
    }
}
