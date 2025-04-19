package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.FranchiseStatus;
import com.framasaas.domain.enumeration.PerformanceTag;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.Franchise} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseDTO implements Serializable {

    private Long id;

    @NotNull
    private String franchiseName;

    private String owner;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    @NotNull
    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    private Long contact;

    private FranchiseStatus franchiseStatus;

    private String gstNumber;

    private String registrationNumber;

    private Float performanceScore;

    private PerformanceTag performanceTag;

    private Long dailyMaxServiceLimit;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private AddressDTO address;

    private FranchiseAllocationRuleSetDTO ruleset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFranchiseName() {
        return franchiseName;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public FranchiseStatus getFranchiseStatus() {
        return franchiseStatus;
    }

    public void setFranchiseStatus(FranchiseStatus franchiseStatus) {
        this.franchiseStatus = franchiseStatus;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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

    public Long getDailyMaxServiceLimit() {
        return dailyMaxServiceLimit;
    }

    public void setDailyMaxServiceLimit(Long dailyMaxServiceLimit) {
        this.dailyMaxServiceLimit = dailyMaxServiceLimit;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public FranchiseAllocationRuleSetDTO getRuleset() {
        return ruleset;
    }

    public void setRuleset(FranchiseAllocationRuleSetDTO ruleset) {
        this.ruleset = ruleset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchiseDTO)) {
            return false;
        }

        FranchiseDTO franchiseDTO = (FranchiseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, franchiseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseDTO{" +
            "id=" + getId() +
            ", franchiseName='" + getFranchiseName() + "'" +
            ", owner='" + getOwner() + "'" +
            ", email='" + getEmail() + "'" +
            ", contact=" + getContact() +
            ", franchiseStatus='" + getFranchiseStatus() + "'" +
            ", gstNumber='" + getGstNumber() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", performanceScore=" + getPerformanceScore() +
            ", performanceTag='" + getPerformanceTag() + "'" +
            ", dailyMaxServiceLimit=" + getDailyMaxServiceLimit() +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", address=" + getAddress() +
            ", ruleset=" + getRuleset() +
            "}";
    }
}
