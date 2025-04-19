package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.FranchiseAllocationRuleSetSortType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.FranchiseAllocationRuleSet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseAllocationRuleSetDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private FranchiseAllocationRuleSetSortType sortType;

    private Long priority;

    @NotNull
    private Boolean isActive;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FranchiseAllocationRuleSetSortType getSortType() {
        return sortType;
    }

    public void setSortType(FranchiseAllocationRuleSetSortType sortType) {
        this.sortType = sortType;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        if (!(o instanceof FranchiseAllocationRuleSetDTO)) {
            return false;
        }

        FranchiseAllocationRuleSetDTO franchiseAllocationRuleSetDTO = (FranchiseAllocationRuleSetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, franchiseAllocationRuleSetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseAllocationRuleSetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sortType='" + getSortType() + "'" +
            ", priority=" + getPriority() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
