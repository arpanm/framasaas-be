package com.framasaas.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldAgentSkillRuleAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFieldAgentSkillRuleAllPropertiesEquals(FieldAgentSkillRule expected, FieldAgentSkillRule actual) {
        assertFieldAgentSkillRuleAutoGeneratedPropertiesEquals(expected, actual);
        assertFieldAgentSkillRuleAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFieldAgentSkillRuleAllUpdatablePropertiesEquals(FieldAgentSkillRule expected, FieldAgentSkillRule actual) {
        assertFieldAgentSkillRuleUpdatableFieldsEquals(expected, actual);
        assertFieldAgentSkillRuleUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFieldAgentSkillRuleAutoGeneratedPropertiesEquals(FieldAgentSkillRule expected, FieldAgentSkillRule actual) {
        assertThat(actual)
            .as("Verify FieldAgentSkillRule auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFieldAgentSkillRuleUpdatableFieldsEquals(FieldAgentSkillRule expected, FieldAgentSkillRule actual) {
        assertThat(actual)
            .as("Verify FieldAgentSkillRule relevant properties")
            .satisfies(a -> assertThat(a.getSkillType()).as("check skillType").isEqualTo(expected.getSkillType()))
            .satisfies(a -> assertThat(a.getJoinType()).as("check joinType").isEqualTo(expected.getJoinType()))
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
    public static void assertFieldAgentSkillRuleUpdatableRelationshipsEquals(FieldAgentSkillRule expected, FieldAgentSkillRule actual) {
        assertThat(actual)
            .as("Verify FieldAgentSkillRule relationships")
            .satisfies(a ->
                assertThat(a.getFieldAgentSkillRuleSet()).as("check fieldAgentSkillRuleSet").isEqualTo(expected.getFieldAgentSkillRuleSet())
            );
    }
}
