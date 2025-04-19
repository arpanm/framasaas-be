package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.FieldAgentSkillRuleSetSortType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.FieldAgentSkillRuleSet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldAgentSkillRuleSetDTO implements Serializable {

    private Long id;

    private FieldAgentSkillRuleSetSortType sortType;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FieldAgentSkillRuleSetSortType getSortType() {
        return sortType;
    }

    public void setSortType(FieldAgentSkillRuleSetSortType sortType) {
        this.sortType = sortType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldAgentSkillRuleSetDTO)) {
            return false;
        }

        FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSetDTO = (FieldAgentSkillRuleSetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fieldAgentSkillRuleSetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldAgentSkillRuleSetDTO{" +
            "id=" + getId() +
            ", sortType='" + getSortType() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
