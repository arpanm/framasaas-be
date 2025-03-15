package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.ServiceOrderAssignmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServiceOrderFieldAgentAssignment.
 */
@Entity
@Table(name = "service_order_field_agent_assignment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderFieldAgentAssignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "service_order_assignment_status", nullable = false)
    private ServiceOrderAssignmentStatus serviceOrderAssignmentStatus;

    @Column(name = "nps")
    private Long nps;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "assigned_time")
    private Instant assignedTime;

    @Column(name = "moved_back_time")
    private Instant movedBackTime;

    @Column(name = "visit_time")
    private Instant visitTime;

    @Column(name = "spare_order_time")
    private Instant spareOrderTime;

    @Column(name = "spare_delivery_time")
    private Instant spareDeliveryTime;

    @Column(name = "completed_time")
    private Instant completedTime;

    @Column(name = "completion_otp")
    private Long completionOTP;

    @Column(name = "cancellation_otp")
    private Long cancellationOTP;

    @NotNull
    @Column(name = "createdd_by", nullable = false)
    private String createddBy;

    @NotNull
    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @NotNull
    @Column(name = "updated_time", nullable = false)
    private Instant updatedTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceOrderFieldAgentAssignment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "additionalAttributePossibleValues",
            "franchise",
            "franchiseStatus",
            "franchisePerformance",
            "brand",
            "category",
            "address",
            "location",
            "franchiseUser",
            "customer",
            "supportDocument",
            "product",
            "hsn",
            "priceHistory",
            "warrantyMaster",
            "warrantyMasterPriceHistory",
            "article",
            "articleWarranty",
            "serviceOrder",
            "serviceOrderPayment",
            "serviceOrderFranchiseAssignment",
            "serviceOrderFieldAgentAssignment",
            "franchiseAllocationRuleSet",
            "franchiseAllocationRule",
            "fieldAgentSkillRuleSet",
            "fieldAgentSkillRule",
            "inventoryLocation",
            "inventory",
            "document",
            "articleWarrantyDocument",
            "serviceOrderAssignment",
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServiceOrderFieldAgentAssignment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceOrderAssignmentStatus getServiceOrderAssignmentStatus() {
        return this.serviceOrderAssignmentStatus;
    }

    public ServiceOrderFieldAgentAssignment serviceOrderAssignmentStatus(ServiceOrderAssignmentStatus serviceOrderAssignmentStatus) {
        this.setServiceOrderAssignmentStatus(serviceOrderAssignmentStatus);
        return this;
    }

    public void setServiceOrderAssignmentStatus(ServiceOrderAssignmentStatus serviceOrderAssignmentStatus) {
        this.serviceOrderAssignmentStatus = serviceOrderAssignmentStatus;
    }

    public Long getNps() {
        return this.nps;
    }

    public ServiceOrderFieldAgentAssignment nps(Long nps) {
        this.setNps(nps);
        return this;
    }

    public void setNps(Long nps) {
        this.nps = nps;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ServiceOrderFieldAgentAssignment isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getAssignedTime() {
        return this.assignedTime;
    }

    public ServiceOrderFieldAgentAssignment assignedTime(Instant assignedTime) {
        this.setAssignedTime(assignedTime);
        return this;
    }

    public void setAssignedTime(Instant assignedTime) {
        this.assignedTime = assignedTime;
    }

    public Instant getMovedBackTime() {
        return this.movedBackTime;
    }

    public ServiceOrderFieldAgentAssignment movedBackTime(Instant movedBackTime) {
        this.setMovedBackTime(movedBackTime);
        return this;
    }

    public void setMovedBackTime(Instant movedBackTime) {
        this.movedBackTime = movedBackTime;
    }

    public Instant getVisitTime() {
        return this.visitTime;
    }

    public ServiceOrderFieldAgentAssignment visitTime(Instant visitTime) {
        this.setVisitTime(visitTime);
        return this;
    }

    public void setVisitTime(Instant visitTime) {
        this.visitTime = visitTime;
    }

    public Instant getSpareOrderTime() {
        return this.spareOrderTime;
    }

    public ServiceOrderFieldAgentAssignment spareOrderTime(Instant spareOrderTime) {
        this.setSpareOrderTime(spareOrderTime);
        return this;
    }

    public void setSpareOrderTime(Instant spareOrderTime) {
        this.spareOrderTime = spareOrderTime;
    }

    public Instant getSpareDeliveryTime() {
        return this.spareDeliveryTime;
    }

    public ServiceOrderFieldAgentAssignment spareDeliveryTime(Instant spareDeliveryTime) {
        this.setSpareDeliveryTime(spareDeliveryTime);
        return this;
    }

    public void setSpareDeliveryTime(Instant spareDeliveryTime) {
        this.spareDeliveryTime = spareDeliveryTime;
    }

    public Instant getCompletedTime() {
        return this.completedTime;
    }

    public ServiceOrderFieldAgentAssignment completedTime(Instant completedTime) {
        this.setCompletedTime(completedTime);
        return this;
    }

    public void setCompletedTime(Instant completedTime) {
        this.completedTime = completedTime;
    }

    public Long getCompletionOTP() {
        return this.completionOTP;
    }

    public ServiceOrderFieldAgentAssignment completionOTP(Long completionOTP) {
        this.setCompletionOTP(completionOTP);
        return this;
    }

    public void setCompletionOTP(Long completionOTP) {
        this.completionOTP = completionOTP;
    }

    public Long getCancellationOTP() {
        return this.cancellationOTP;
    }

    public ServiceOrderFieldAgentAssignment cancellationOTP(Long cancellationOTP) {
        this.setCancellationOTP(cancellationOTP);
        return this;
    }

    public void setCancellationOTP(Long cancellationOTP) {
        this.cancellationOTP = cancellationOTP;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public ServiceOrderFieldAgentAssignment createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ServiceOrderFieldAgentAssignment createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ServiceOrderFieldAgentAssignment updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public ServiceOrderFieldAgentAssignment updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setServiceOrderFieldAgentAssignment(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setServiceOrderFieldAgentAssignment(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public ServiceOrderFieldAgentAssignment additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public ServiceOrderFieldAgentAssignment addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setServiceOrderFieldAgentAssignment(this);
        return this;
    }

    public ServiceOrderFieldAgentAssignment removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setServiceOrderFieldAgentAssignment(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrderFieldAgentAssignment)) {
            return false;
        }
        return getId() != null && getId().equals(((ServiceOrderFieldAgentAssignment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderFieldAgentAssignment{" +
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
