package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.FranchiseAllocationRuleJoinType;
import com.framasaas.domain.enumeration.FranchiseAllocationRuleType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.FranchiseAllocationRule} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseAllocationRuleDTO implements Serializable {

    private Long id;

    @NotNull
    private FranchiseAllocationRuleType ruleType;

    @NotNull
    private FranchiseAllocationRuleJoinType joinType;

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

    public FranchiseAllocationRuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(FranchiseAllocationRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public FranchiseAllocationRuleJoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(FranchiseAllocationRuleJoinType joinType) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchiseAllocationRuleDTO)) {
            return false;
        }

        FranchiseAllocationRuleDTO franchiseAllocationRuleDTO = (FranchiseAllocationRuleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, franchiseAllocationRuleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseAllocationRuleDTO{" +
            "id=" + getId() +
            ", ruleType='" + getRuleType() + "'" +
            ", joinType='" + getJoinType() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
