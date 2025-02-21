package com.framasaas.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class FranchiseAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFranchiseAllPropertiesEquals(Franchise expected, Franchise actual) {
        assertFranchiseAutoGeneratedPropertiesEquals(expected, actual);
        assertFranchiseAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFranchiseAllUpdatablePropertiesEquals(Franchise expected, Franchise actual) {
        assertFranchiseUpdatableFieldsEquals(expected, actual);
        assertFranchiseUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFranchiseAutoGeneratedPropertiesEquals(Franchise expected, Franchise actual) {
        assertThat(actual)
            .as("Verify Franchise auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFranchiseUpdatableFieldsEquals(Franchise expected, Franchise actual) {
        assertThat(actual)
            .as("Verify Franchise relevant properties")
            .satisfies(a -> assertThat(a.getFranchiseName()).as("check franchiseName").isEqualTo(expected.getFranchiseName()))
            .satisfies(a -> assertThat(a.getOwner()).as("check owner").isEqualTo(expected.getOwner()))
            .satisfies(a -> assertThat(a.getEmail()).as("check email").isEqualTo(expected.getEmail()))
            .satisfies(a -> assertThat(a.getContact()).as("check contact").isEqualTo(expected.getContact()))
            .satisfies(a -> assertThat(a.getFranchiseStatus()).as("check franchiseStatus").isEqualTo(expected.getFranchiseStatus()))
            .satisfies(a -> assertThat(a.getGstNumber()).as("check gstNumber").isEqualTo(expected.getGstNumber()))
            .satisfies(a -> assertThat(a.getRegistrationNumber()).as("check registrationNumber").isEqualTo(expected.getRegistrationNumber())
            )
            .satisfies(a -> assertThat(a.getPerformanceScore()).as("check performanceScore").isEqualTo(expected.getPerformanceScore()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFranchiseUpdatableRelationshipsEquals(Franchise expected, Franchise actual) {
        assertThat(actual)
            .as("Verify Franchise relationships")
            .satisfies(a -> assertThat(a.getAddress()).as("check address").isEqualTo(expected.getAddress()));
    }
}
