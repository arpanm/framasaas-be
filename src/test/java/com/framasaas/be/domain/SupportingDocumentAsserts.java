package com.framasaas.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportingDocumentAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSupportingDocumentAllPropertiesEquals(SupportingDocument expected, SupportingDocument actual) {
        assertSupportingDocumentAutoGeneratedPropertiesEquals(expected, actual);
        assertSupportingDocumentAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSupportingDocumentAllUpdatablePropertiesEquals(SupportingDocument expected, SupportingDocument actual) {
        assertSupportingDocumentUpdatableFieldsEquals(expected, actual);
        assertSupportingDocumentUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSupportingDocumentAutoGeneratedPropertiesEquals(SupportingDocument expected, SupportingDocument actual) {
        assertThat(actual)
            .as("Verify SupportingDocument auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSupportingDocumentUpdatableFieldsEquals(SupportingDocument expected, SupportingDocument actual) {
        assertThat(actual)
            .as("Verify SupportingDocument relevant properties")
            .satisfies(a -> assertThat(a.getDocumentName()).as("check documentName").isEqualTo(expected.getDocumentName()))
            .satisfies(a -> assertThat(a.getDocumentType()).as("check documentType").isEqualTo(expected.getDocumentType()))
            .satisfies(a -> assertThat(a.getDocumentFormat()).as("check documentFormat").isEqualTo(expected.getDocumentFormat()))
            .satisfies(a -> assertThat(a.getDocumentSize()).as("check documentSize").isEqualTo(expected.getDocumentSize()))
            .satisfies(a -> assertThat(a.getDocumentPath()).as("check documentPath").isEqualTo(expected.getDocumentPath()))
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
    public static void assertSupportingDocumentUpdatableRelationshipsEquals(SupportingDocument expected, SupportingDocument actual) {
        assertThat(actual)
            .as("Verify SupportingDocument relationships")
            .satisfies(a -> assertThat(a.getFranchise()).as("check franchise").isEqualTo(expected.getFranchise()))
            .satisfies(a -> assertThat(a.getArticle()).as("check article").isEqualTo(expected.getArticle()))
            .satisfies(a -> assertThat(a.getArticleWarranty()).as("check articleWarranty").isEqualTo(expected.getArticleWarranty()))
            .satisfies(a -> assertThat(a.getServiceOrder()).as("check serviceOrder").isEqualTo(expected.getServiceOrder()));
    }
}
