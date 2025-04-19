package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.InventoryLocationType;
import com.framasaas.domain.enumeration.ServiceOrderSpareStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.ServiceOrderSpare} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderSpareDTO implements Serializable {

    private Long id;

    private Float price;

    private Float tax;

    private Float totalCharge;

    private Float franchiseCommision;

    private Float franchiseCommisionTax;

    private InventoryLocationType orderedFrom;

    private ServiceOrderSpareStatus spareStatus;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private ServiceOrderDTO serviceOrder;

    private ProductDTO product;

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

    public Float getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Float totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Float getFranchiseCommision() {
        return franchiseCommision;
    }

    public void setFranchiseCommision(Float franchiseCommision) {
        this.franchiseCommision = franchiseCommision;
    }

    public Float getFranchiseCommisionTax() {
        return franchiseCommisionTax;
    }

    public void setFranchiseCommisionTax(Float franchiseCommisionTax) {
        this.franchiseCommisionTax = franchiseCommisionTax;
    }

    public InventoryLocationType getOrderedFrom() {
        return orderedFrom;
    }

    public void setOrderedFrom(InventoryLocationType orderedFrom) {
        this.orderedFrom = orderedFrom;
    }

    public ServiceOrderSpareStatus getSpareStatus() {
        return spareStatus;
    }

    public void setSpareStatus(ServiceOrderSpareStatus spareStatus) {
        this.spareStatus = spareStatus;
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

    public ServiceOrderDTO getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrderDTO serviceOrder) {
        this.serviceOrder = serviceOrder;
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
        if (!(o instanceof ServiceOrderSpareDTO)) {
            return false;
        }

        ServiceOrderSpareDTO serviceOrderSpareDTO = (ServiceOrderSpareDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceOrderSpareDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderSpareDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", tax=" + getTax() +
            ", totalCharge=" + getTotalCharge() +
            ", franchiseCommision=" + getFranchiseCommision() +
            ", franchiseCommisionTax=" + getFranchiseCommisionTax() +
            ", orderedFrom='" + getOrderedFrom() + "'" +
            ", spareStatus='" + getSpareStatus() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", serviceOrder=" + getServiceOrder() +
            ", product=" + getProduct() +
            "}";
    }
}
