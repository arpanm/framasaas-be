package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.FranchiseStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.FranchiseStatusHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseStatusHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private FranchiseStatus franchiseSatus;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private FranchiseDTO franchise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FranchiseStatus getFranchiseSatus() {
        return franchiseSatus;
    }

    public void setFranchiseSatus(FranchiseStatus franchiseSatus) {
        this.franchiseSatus = franchiseSatus;
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
        if (!(o instanceof FranchiseStatusHistoryDTO)) {
            return false;
        }

        FranchiseStatusHistoryDTO franchiseStatusHistoryDTO = (FranchiseStatusHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, franchiseStatusHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseStatusHistoryDTO{" +
            "id=" + getId() +
            ", franchiseSatus='" + getFranchiseSatus() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", franchise=" + getFranchise() +
            "}";
    }
}
