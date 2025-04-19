package com.framasaas.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.Brand} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BrandDTO implements Serializable {

    private Long id;

    @NotNull
    private String brandName;

    private String logoPath;

    @NotNull
    private String vendorBrandId;

    private String description;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private FranchiseAllocationRuleDTO franchiseRule;

    private FieldAgentSkillRuleDTO fieldAgentSkillRule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getVendorBrandId() {
        return vendorBrandId;
    }

    public void setVendorBrandId(String vendorBrandId) {
        this.vendorBrandId = vendorBrandId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public FranchiseAllocationRuleDTO getFranchiseRule() {
        return franchiseRule;
    }

    public void setFranchiseRule(FranchiseAllocationRuleDTO franchiseRule) {
        this.franchiseRule = franchiseRule;
    }

    public FieldAgentSkillRuleDTO getFieldAgentSkillRule() {
        return fieldAgentSkillRule;
    }

    public void setFieldAgentSkillRule(FieldAgentSkillRuleDTO fieldAgentSkillRule) {
        this.fieldAgentSkillRule = fieldAgentSkillRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrandDTO)) {
            return false;
        }

        BrandDTO brandDTO = (BrandDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, brandDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BrandDTO{" +
            "id=" + getId() +
            ", brandName='" + getBrandName() + "'" +
            ", logoPath='" + getLogoPath() + "'" +
            ", vendorBrandId='" + getVendorBrandId() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", franchiseRule=" + getFranchiseRule() +
            ", fieldAgentSkillRule=" + getFieldAgentSkillRule() +
            "}";
    }
}
