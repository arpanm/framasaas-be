package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.SoldBy;
import com.framasaas.domain.enumeration.WarrantyType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.ArticleWarrantyDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleWarrantyDetailsDTO implements Serializable {

    private Long id;

    private WarrantyType warrantyType;

    private String vendorArticleWarrantyId;

    private String vendorWarrantyMasterId;

    private LocalDate startDate;

    private LocalDate endDate;

    private SoldBy soldBy;

    private String soldByUser;

    private LocalDate soldDate;

    private Boolean isValidated;

    private String validatedBy;

    private Instant validatedTime;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private ArticleDTO article;

    private WarrantyMasterDTO warrantyMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public String getVendorArticleWarrantyId() {
        return vendorArticleWarrantyId;
    }

    public void setVendorArticleWarrantyId(String vendorArticleWarrantyId) {
        this.vendorArticleWarrantyId = vendorArticleWarrantyId;
    }

    public String getVendorWarrantyMasterId() {
        return vendorWarrantyMasterId;
    }

    public void setVendorWarrantyMasterId(String vendorWarrantyMasterId) {
        this.vendorWarrantyMasterId = vendorWarrantyMasterId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SoldBy getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(SoldBy soldBy) {
        this.soldBy = soldBy;
    }

    public String getSoldByUser() {
        return soldByUser;
    }

    public void setSoldByUser(String soldByUser) {
        this.soldByUser = soldByUser;
    }

    public LocalDate getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(LocalDate soldDate) {
        this.soldDate = soldDate;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public String getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }

    public Instant getValidatedTime() {
        return validatedTime;
    }

    public void setValidatedTime(Instant validatedTime) {
        this.validatedTime = validatedTime;
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

    public ArticleDTO getArticle() {
        return article;
    }

    public void setArticle(ArticleDTO article) {
        this.article = article;
    }

    public WarrantyMasterDTO getWarrantyMaster() {
        return warrantyMaster;
    }

    public void setWarrantyMaster(WarrantyMasterDTO warrantyMaster) {
        this.warrantyMaster = warrantyMaster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleWarrantyDetailsDTO)) {
            return false;
        }

        ArticleWarrantyDetailsDTO articleWarrantyDetailsDTO = (ArticleWarrantyDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, articleWarrantyDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleWarrantyDetailsDTO{" +
            "id=" + getId() +
            ", warrantyType='" + getWarrantyType() + "'" +
            ", vendorArticleWarrantyId='" + getVendorArticleWarrantyId() + "'" +
            ", vendorWarrantyMasterId='" + getVendorWarrantyMasterId() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", soldBy='" + getSoldBy() + "'" +
            ", soldByUser='" + getSoldByUser() + "'" +
            ", soldDate='" + getSoldDate() + "'" +
            ", isValidated='" + getIsValidated() + "'" +
            ", validatedBy='" + getValidatedBy() + "'" +
            ", validatedTime='" + getValidatedTime() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", article=" + getArticle() +
            ", warrantyMaster=" + getWarrantyMaster() +
            "}";
    }
}
