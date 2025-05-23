package com.framasaas.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInventoryAllPropertiesEquals(Inventory expected, Inventory actual) {
        assertInventoryAutoGeneratedPropertiesEquals(expected, actual);
        assertInventoryAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInventoryAllUpdatablePropertiesEquals(Inventory expected, Inventory actual) {
        assertInventoryUpdatableFieldsEquals(expected, actual);
        assertInventoryUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInventoryAutoGeneratedPropertiesEquals(Inventory expected, Inventory actual) {
        assertThat(actual)
            .as("Verify Inventory auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertInventoryUpdatableFieldsEquals(Inventory expected, Inventory actual) {
        assertThat(actual)
            .as("Verify Inventory relevant properties")
            .satisfies(a -> assertThat(a.getInventoryValue()).as("check inventoryValue").isEqualTo(expected.getInventoryValue()))
            .satisfies(a -> assertThat(a.getIsSellable()).as("check isSellable").isEqualTo(expected.getIsSellable()))
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
    public static void assertInventoryUpdatableRelationshipsEquals(Inventory expected, Inventory actual) {
        assertThat(actual)
            .as("Verify Inventory relationships")
            .satisfies(a -> assertThat(a.getProduct()).as("check product").isEqualTo(expected.getProduct()))
            .satisfies(a -> assertThat(a.getLocation()).as("check location").isEqualTo(expected.getLocation()));
    }
}
