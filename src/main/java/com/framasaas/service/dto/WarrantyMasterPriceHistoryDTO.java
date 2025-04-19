package com.framasaas.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.WarrantyMasterPriceHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarrantyMasterPriceHistoryDTO implements Serializable {

    private Long id;

    private Float price;

    private Float tax;

    private Float franchiseCommission;

    private Float franchiseTax;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private WarrantyMasterDTO warrantyMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof WarrantyMasterPriceHistoryDTO)) {
            return false;
        }

        WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistoryDTO = (WarrantyMasterPriceHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, warrantyMasterPriceHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarrantyMasterPriceHistoryDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", tax=" + getTax() +
            ", franchiseCommission=" + getFranchiseCommission() +
            ", franchiseTax=" + getFranchiseTax() +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", warrantyMaster=" + getWarrantyMaster() +
            "}";
    }
}
