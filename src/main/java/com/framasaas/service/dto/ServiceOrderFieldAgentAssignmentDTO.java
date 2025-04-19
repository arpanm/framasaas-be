package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.ServiceOrderAssignmentStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.ServiceOrderFieldAgentAssignment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderFieldAgentAssignmentDTO implements Serializable {

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

    private Long completionOTP;

    private Long cancellationOTP;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

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

    public Long getCompletionOTP() {
        return completionOTP;
    }

    public void setCompletionOTP(Long completionOTP) {
        this.completionOTP = completionOTP;
    }

    public Long getCancellationOTP() {
        return cancellationOTP;
    }

    public void setCancellationOTP(Long cancellationOTP) {
        this.cancellationOTP = cancellationOTP;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrderFieldAgentAssignmentDTO)) {
            return false;
        }

        ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignmentDTO = (ServiceOrderFieldAgentAssignmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceOrderFieldAgentAssignmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderFieldAgentAssignmentDTO{" +
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
            ", completionOTP=" + getCompletionOTP() +
            ", cancellationOTP=" + getCancellationOTP() +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
