package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.FranchiseUserRole;
import com.framasaas.domain.enumeration.FranchiseUserStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.FranchiseUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseUserDTO implements Serializable {

    private Long id;

    @NotNull
    private String userName;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    @NotNull
    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    private Long contact;

    private FranchiseUserStatus userStatus;

    private FranchiseUserRole userRole;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private FranchiseDTO franchise;

    private FieldAgentSkillRuleSetDTO skillRuleSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public FranchiseUserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(FranchiseUserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public FranchiseUserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(FranchiseUserRole userRole) {
        this.userRole = userRole;
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

    public FranchiseDTO getFranchise() {
        return franchise;
    }

    public void setFranchise(FranchiseDTO franchise) {
        this.franchise = franchise;
    }

    public FieldAgentSkillRuleSetDTO getSkillRuleSet() {
        return skillRuleSet;
    }

    public void setSkillRuleSet(FieldAgentSkillRuleSetDTO skillRuleSet) {
        this.skillRuleSet = skillRuleSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchiseUserDTO)) {
            return false;
        }

        FranchiseUserDTO franchiseUserDTO = (FranchiseUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, franchiseUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseUserDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", email='" + getEmail() + "'" +
            ", contact=" + getContact() +
            ", userStatus='" + getUserStatus() + "'" +
            ", userRole='" + getUserRole() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", franchise=" + getFranchise() +
            ", skillRuleSet=" + getSkillRuleSet() +
            "}";
    }
}
