package com.framasaas.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceOrderMasterAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiceOrderMasterAllPropertiesEquals(ServiceOrderMaster expected, ServiceOrderMaster actual) {
        assertServiceOrderMasterAutoGeneratedPropertiesEquals(expected, actual);
        assertServiceOrderMasterAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiceOrderMasterAllUpdatablePropertiesEquals(ServiceOrderMaster expected, ServiceOrderMaster actual) {
        assertServiceOrderMasterUpdatableFieldsEquals(expected, actual);
        assertServiceOrderMasterUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiceOrderMasterAutoGeneratedPropertiesEquals(ServiceOrderMaster expected, ServiceOrderMaster actual) {
        assertThat(actual)
            .as("Verify ServiceOrderMaster auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiceOrderMasterUpdatableFieldsEquals(ServiceOrderMaster expected, ServiceOrderMaster actual) {
        assertThat(actual)
            .as("Verify ServiceOrderMaster relevant properties")
            .satisfies(a -> assertThat(a.getServiceOrderType()).as("check serviceOrderType").isEqualTo(expected.getServiceOrderType()))
            .satisfies(a -> assertThat(a.getSlaInHours()).as("check slaInHours").isEqualTo(expected.getSlaInHours()))
            .satisfies(a -> assertThat(a.getCharge()).as("check charge").isEqualTo(expected.getCharge()))
            .satisfies(a -> assertThat(a.getTax()).as("check tax").isEqualTo(expected.getTax()))
            .satisfies(a ->
                assertThat(a.getFranchiseCommissionWithinSla())
                    .as("check franchiseCommissionWithinSla")
                    .isEqualTo(expected.getFranchiseCommissionWithinSla())
            )
            .satisfies(a ->
                assertThat(a.getFranchiseTaxWithinSlaTax())
                    .as("check franchiseTaxWithinSlaTax")
                    .isEqualTo(expected.getFranchiseTaxWithinSlaTax())
            )
            .satisfies(a ->
                assertThat(a.getFranchiseCommissionOutsideSla())
                    .as("check franchiseCommissionOutsideSla")
                    .isEqualTo(expected.getFranchiseCommissionOutsideSla())
            )
            .satisfies(a ->
                assertThat(a.getFranchiseTaxOutsideSlaTax())
                    .as("check franchiseTaxOutsideSlaTax")
                    .isEqualTo(expected.getFranchiseTaxOutsideSlaTax())
            )
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
    public static void assertServiceOrderMasterUpdatableRelationshipsEquals(ServiceOrderMaster expected, ServiceOrderMaster actual) {
        assertThat(actual)
            .as("Verify ServiceOrderMaster relationships")
            .satisfies(a -> assertThat(a.getProduct()).as("check product").isEqualTo(expected.getProduct()));
    }
}
