package com.framasaas.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArticleAllPropertiesEquals(Article expected, Article actual) {
        assertArticleAutoGeneratedPropertiesEquals(expected, actual);
        assertArticleAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArticleAllUpdatablePropertiesEquals(Article expected, Article actual) {
        assertArticleUpdatableFieldsEquals(expected, actual);
        assertArticleUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArticleAutoGeneratedPropertiesEquals(Article expected, Article actual) {
        assertThat(actual)
            .as("Verify Article auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertArticleUpdatableFieldsEquals(Article expected, Article actual) {
        assertThat(actual)
            .as("Verify Article relevant properties")
            .satisfies(a -> assertThat(a.getSerialNo()).as("check serialNo").isEqualTo(expected.getSerialNo()))
            .satisfies(a -> assertThat(a.getVendorArticleId()).as("check vendorArticleId").isEqualTo(expected.getVendorArticleId()))
            .satisfies(a -> assertThat(a.getPurchaseDate()).as("check purchaseDate").isEqualTo(expected.getPurchaseDate()))
            .satisfies(a -> assertThat(a.getPuchasePrice()).as("check puchasePrice").isEqualTo(expected.getPuchasePrice()))
            .satisfies(a -> assertThat(a.getPurchaseStore()).as("check purchaseStore").isEqualTo(expected.getPurchaseStore()))
            .satisfies(a -> assertThat(a.getInvoicePath()).as("check invoicePath").isEqualTo(expected.getInvoicePath()))
            .satisfies(a -> assertThat(a.getIsValidated()).as("check isValidated").isEqualTo(expected.getIsValidated()))
            .satisfies(a -> assertThat(a.getValidatedBy()).as("check validatedBy").isEqualTo(expected.getValidatedBy()))
            .satisfies(a -> assertThat(a.getValidatedTime()).as("check validatedTime").isEqualTo(expected.getValidatedTime()))
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
    public static void assertArticleUpdatableRelationshipsEquals(Article expected, Article actual) {
        assertThat(actual)
            .as("Verify Article relationships")
            .satisfies(a -> assertThat(a.getProduct()).as("check product").isEqualTo(expected.getProduct()))
            .satisfies(a -> assertThat(a.getCustomer()).as("check customer").isEqualTo(expected.getCustomer()));
    }
}
