package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.FieldAgentSkillRuleJoinType;
import com.framasaas.domain.enumeration.FieldAgentSkillRuleType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.FieldAgentSkillRule} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldAgentSkillRuleDTO implements Serializable {

    private Long id;

    private FieldAgentSkillRuleType skillType;

    private FieldAgentSkillRuleJoinType joinType;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FieldAgentSkillRuleType getSkillType() {
        return skillType;
    }

    public void setSkillType(FieldAgentSkillRuleType skillType) {
        this.skillType = skillType;
    }

    public FieldAgentSkillRuleJoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(FieldAgentSkillRuleJoinType joinType) {
        this.joinType = joinType;
    }

    public String getCreateddBy() {
        return createddBy;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public FieldAgentSkillRuleSetDTO getFieldAgentSkillRuleSet() {
        return fieldAgentSkillRuleSet;
    }

    public void setFieldAgentSkillRuleSet(FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSet) {
        this.fieldAgentSkillRuleSet = fieldAgentSkillRuleSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldAgentSkillRuleDTO)) {
            return false;
        }

        FieldAgentSkillRuleDTO fieldAgentSkillRuleDTO = (FieldAgentSkillRuleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fieldAgentSkillRuleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldAgentSkillRuleDTO{" +
            "id=" + getId() +
            ", skillType='" + getSkillType() + "'" +
            ", joinType='" + getJoinType() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", fieldAgentSkillRuleSet=" + getFieldAgentSkillRuleSet() +
            "}";
    }
}
