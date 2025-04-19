package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.FranchiseUserStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.FranchiseUserStatusHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseUserStatusHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private FranchiseUserStatus userSatus;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private FranchiseUserDTO franchiseUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FranchiseUserStatus getUserSatus() {
        return userSatus;
    }

    public void setUserSatus(FranchiseUserStatus userSatus) {
        this.userSatus = userSatus;
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

    public FranchiseUserDTO getFranchiseUser() {
        return franchiseUser;
    }

    public void setFranchiseUser(FranchiseUserDTO franchiseUser) {
        this.franchiseUser = franchiseUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchiseUserStatusHistoryDTO)) {
            return false;
        }

        FranchiseUserStatusHistoryDTO franchiseUserStatusHistoryDTO = (FranchiseUserStatusHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, franchiseUserStatusHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseUserStatusHistoryDTO{" +
            "id=" + getId() +
            ", userSatus='" + getUserSatus() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", franchiseUser=" + getFranchiseUser() +
            "}";
    }
}
