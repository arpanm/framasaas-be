package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.PerformanceTag;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.FranchisePerformanceHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchisePerformanceHistoryDTO implements Serializable {

    private Long id;

    private Float performanceScore;

    private PerformanceTag performanceTag;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    private FranchiseDTO franchise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(Float performanceScore) {
        this.performanceScore = performanceScore;
    }

    public PerformanceTag getPerformanceTag() {
        return performanceTag;
    }

    public void setPerformanceTag(PerformanceTag performanceTag) {
        this.performanceTag = performanceTag;
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

    public FranchiseDTO getFranchise() {
        return franchise;
    }

    public void setFranchise(FranchiseDTO franchise) {
        this.franchise = franchise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchisePerformanceHistoryDTO)) {
            return false;
        }

        FranchisePerformanceHistoryDTO franchisePerformanceHistoryDTO = (FranchisePerformanceHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, franchisePerformanceHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchisePerformanceHistoryDTO{" +
            "id=" + getId() +
            ", performanceScore=" + getPerformanceScore() +
            ", performanceTag='" + getPerformanceTag() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", franchise=" + getFranchise() +
            "}";
    }
}
