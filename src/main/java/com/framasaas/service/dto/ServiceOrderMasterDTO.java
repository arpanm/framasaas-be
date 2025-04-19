package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.ServiceOrderType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.ServiceOrderMaster} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderMasterDTO implements Serializable {

    private Long id;

    private ServiceOrderType serviceOrderType;

    private Long slaInHours;

    private Float charge;

    private Float tax;

    private Float franchiseCommissionWithinSla;

    private Float franchiseTaxWithinSlaTax;

    private Float franchiseCommissionOutsideSla;

    private Float franchiseTaxOutsideSlaTax;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceOrderType getServiceOrderType() {
        return serviceOrderType;
    }

    public void setServiceOrderType(ServiceOrderType serviceOrderType) {
        this.serviceOrderType = serviceOrderType;
    }

    public Long getSlaInHours() {
        return slaInHours;
    }

    public void setSlaInHours(Long slaInHours) {
        this.slaInHours = slaInHours;
    }

    public Float getCharge() {
        return charge;
    }

    public void setCharge(Float charge) {
        this.charge = charge;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getFranchiseCommissionWithinSla() {
        return franchiseCommissionWithinSla;
    }

    public void setFranchiseCommissionWithinSla(Float franchiseCommissionWithinSla) {
        this.franchiseCommissionWithinSla = franchiseCommissionWithinSla;
    }

    public Float getFranchiseTaxWithinSlaTax() {
        return franchiseTaxWithinSlaTax;
    }

    public void setFranchiseTaxWithinSlaTax(Float franchiseTaxWithinSlaTax) {
        this.franchiseTaxWithinSlaTax = franchiseTaxWithinSlaTax;
    }

    public Float getFranchiseCommissionOutsideSla() {
        return franchiseCommissionOutsideSla;
    }

    public void setFranchiseCommissionOutsideSla(Float franchiseCommissionOutsideSla) {
        this.franchiseCommissionOutsideSla = franchiseCommissionOutsideSla;
    }

    public Float getFranchiseTaxOutsideSlaTax() {
        return franchiseTaxOutsideSlaTax;
    }

    public void setFranchiseTaxOutsideSlaTax(Float franchiseTaxOutsideSlaTax) {
        this.franchiseTaxOutsideSlaTax = franchiseTaxOutsideSlaTax;
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
        if (!(o instanceof ServiceOrderMasterDTO)) {
            return false;
        }

        ServiceOrderMasterDTO serviceOrderMasterDTO = (ServiceOrderMasterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceOrderMasterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderMasterDTO{" +
            "id=" + getId() +
            ", serviceOrderType='" + getServiceOrderType() + "'" +
            ", slaInHours=" + getSlaInHours() +
            ", charge=" + getCharge() +
            ", tax=" + getTax() +
            ", franchiseCommissionWithinSla=" + getFranchiseCommissionWithinSla() +
            ", franchiseTaxWithinSlaTax=" + getFranchiseTaxWithinSlaTax() +
            ", franchiseCommissionOutsideSla=" + getFranchiseCommissionOutsideSla() +
            ", franchiseTaxOutsideSlaTax=" + getFranchiseTaxOutsideSlaTax() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", product=" + getProduct() +
            "}";
    }
}
