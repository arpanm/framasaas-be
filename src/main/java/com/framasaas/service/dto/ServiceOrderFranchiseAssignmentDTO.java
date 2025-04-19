package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.ServiceOrderAssignmentStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.ServiceOrderFranchiseAssignment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderFranchiseAssignmentDTO implements Serializable {

    private Long id;

    @NotNull
    private ServiceOrderAssignmentStatus serviceOrderAssignmentStatus;

    private Long nps;

    private Boolean isActive;

    private Instant assignedTime;

    private Instant movedBackTime;

    private Instant visitTime;

    private Instant spareOrderTime;

    private Instant spareDeliveryTime;

    private Instant completedTime;

    private Float franchiseCommision;

    private Float franchiseCommisionTax;

    private String franchiseInvoicePath;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private ServiceOrderDTO serviceOrder;

    private FranchiseDTO franchise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceOrderAssignmentStatus getServiceOrderAssignmentStatus() {
        return serviceOrderAssignmentStatus;
    }

    public void setServiceOrderAssignmentStatus(ServiceOrderAssignmentStatus serviceOrderAssignmentStatus) {
        this.serviceOrderAssignmentStatus = serviceOrderAssignmentStatus;
    }

    public Long getNps() {
        return nps;
    }

    public void setNps(Long nps) {
        this.nps = nps;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(Instant assignedTime) {
        this.assignedTime = assignedTime;
    }

    public Instant getMovedBackTime() {
        return movedBackTime;
    }

    public void setMovedBackTime(Instant movedBackTime) {
        this.movedBackTime = movedBackTime;
    }

    public Instant getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Instant visitTime) {
        this.visitTime = visitTime;
    }

    public Instant getSpareOrderTime() {
        return spareOrderTime;
    }

    public void setSpareOrderTime(Instant spareOrderTime) {
        this.spareOrderTime = spareOrderTime;
    }

    public Instant getSpareDeliveryTime() {
        return spareDeliveryTime;
    }

    public void setSpareDeliveryTime(Instant spareDeliveryTime) {
        this.spareDeliveryTime = spareDeliveryTime;
    }

    public Instant getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Instant completedTime) {
        this.completedTime = completedTime;
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

    public String getFranchiseInvoicePath() {
        return franchiseInvoicePath;
    }

    public void setFranchiseInvoicePath(String franchiseInvoicePath) {
        this.franchiseInvoicePath = franchiseInvoicePath;
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
        if (!(o instanceof ServiceOrderFranchiseAssignmentDTO)) {
            return false;
        }

        ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignmentDTO = (ServiceOrderFranchiseAssignmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceOrderFranchiseAssignmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderFranchiseAssignmentDTO{" +
            "id=" + getId() +
            ", serviceOrderAssignmentStatus='" + getServiceOrderAssignmentStatus() + "'" +
            ", nps=" + getNps() +
            ", isActive='" + getIsActive() + "'" +
            ", assignedTime='" + getAssignedTime() + "'" +
            ", movedBackTime='" + getMovedBackTime() + "'" +
            ", visitTime='" + getVisitTime() + "'" +
            ", spareOrderTime='" + getSpareOrderTime() + "'" +
            ", spareDeliveryTime='" + getSpareDeliveryTime() + "'" +
            ", completedTime='" + getCompletedTime() + "'" +
            ", franchiseCommision=" + getFranchiseCommision() +
            ", franchiseCommisionTax=" + getFranchiseCommisionTax() +
            ", franchiseInvoicePath='" + getFranchiseInvoicePath() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", serviceOrder=" + getServiceOrder() +
            ", franchise=" + getFranchise() +
            "}";
    }
}
