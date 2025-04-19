package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.WarrantyType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.framasaas.domain.WarrantyMaster} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarrantyMasterDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String vendorWarrantyMasterId;

    @NotNull
    private WarrantyType warrantyType;

    private String description;

    @NotNull
    private Float price;

    @NotNull
    private Float tax;

    @NotNull
    private Float franchiseCommission;

    @NotNull
    private Float franchiseTax;

    @NotNull
    private Long periodInMonths;

    @NotNull
    private Float taxRate;

    private String coverage;

    private String exclusion;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private Set<ProductDTO> coveredSpares = new HashSet<>();

    private ProductDTO product;

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

    public String getVendorWarrantyMasterId() {
        return vendorWarrantyMasterId;
    }

    public void setVendorWarrantyMasterId(String vendorWarrantyMasterId) {
        this.vendorWarrantyMasterId = vendorWarrantyMasterId;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getFranchiseCommission() {
        return franchiseCommission;
    }

    public void setFranchiseCommission(Float franchiseCommission) {
        this.franchiseCommission = franchiseCommission;
    }

    public Float getFranchiseTax() {
        return franchiseTax;
    }

    public void setFranchiseTax(Float franchiseTax) {
        this.franchiseTax = franchiseTax;
    }

    public Long getPeriodInMonths() {
        return periodInMonths;
    }

    public void setPeriodInMonths(Long periodInMonths) {
        this.periodInMonths = periodInMonths;
    }

    public Float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getExclusion() {
        return exclusion;
    }

    public void setExclusion(String exclusion) {
        this.exclusion = exclusion;
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

    public Set<ProductDTO> getCoveredSpares() {
        return coveredSpares;
    }

    public void setCoveredSpares(Set<ProductDTO> coveredSpares) {
        this.coveredSpares = coveredSpares;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarrantyMasterDTO)) {
            return false;
        }

        WarrantyMasterDTO warrantyMasterDTO = (WarrantyMasterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, warrantyMasterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarrantyMasterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vendorWarrantyMasterId='" + getVendorWarrantyMasterId() + "'" +
            ", warrantyType='" + getWarrantyType() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", tax=" + getTax() +
            ", franchiseCommission=" + getFranchiseCommission() +
            ", franchiseTax=" + getFranchiseTax() +
            ", periodInMonths=" + getPeriodInMonths() +
            ", taxRate=" + getTaxRate() +
            ", coverage='" + getCoverage() + "'" +
            ", exclusion='" + getExclusion() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", coveredSpares=" + getCoveredSpares() +
            ", product=" + getProduct() +
            "}";
    }
}
