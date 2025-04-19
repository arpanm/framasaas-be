package com.framasaas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.domain.enumeration.ServiceOrderAssignmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServiceOrderAssignment.
 */
@Entity
@Table(name = "service_order_assignment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderAssignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "franchise_commision")
    private Float franchiseCommision;

    @Column(name = "franchise_commision_tax")
    private Float franchiseCommisionTax;

    @Column(name = "franchise_invoice_path")
    private String franchiseInvoicePath;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceOrderAssignment")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "supportingDocuments",
            "serviceOrderFranchiseAssignments",
            "serviceOrderSpares",
            "additionalAttributes",
            "customer",
            "serviceMaster",
            "article",
            "address",
        },
        allowSetters = true
    )
    private ServiceOrder serviceOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "address",
            "franchiseStatusHistories",
            "franchisePerformanceHistories",
            "supportingDocuments",
            "franchiseUsers",
            "serviceOrderFranchiseAssignments",
            "additionalAttributes",
            "ruleset",
            "brands",
            "categories",
        },
        allowSetters = true
    )
    private Franchise franchise;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServiceOrderAssignment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceOrderAssignmentStatus getServiceOrderAssignmentStatus() {
        return this.serviceOrderAssignmentStatus;
    }

    public ServiceOrderAssignment serviceOrderAssignmentStatus(ServiceOrderAssignmentStatus serviceOrderAssignmentStatus) {
        this.setServiceOrderAssignmentStatus(serviceOrderAssignmentStatus);
        return this;
    }

    public void setServiceOrderAssignmentStatus(ServiceOrderAssignmentStatus serviceOrderAssignmentStatus) {
        this.serviceOrderAssignmentStatus = serviceOrderAssignmentStatus;
    }

    public Long getNps() {
        return this.nps;
    }

    public ServiceOrderAssignment nps(Long nps) {
        this.setNps(nps);
        return this;
    }

    public void setNps(Long nps) {
        this.nps = nps;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ServiceOrderAssignment isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getAssignedTime() {
        return this.assignedTime;
    }

    public ServiceOrderAssignment assignedTime(Instant assignedTime) {
        this.setAssignedTime(assignedTime);
        return this;
    }

    public void setAssignedTime(Instant assignedTime) {
        this.assignedTime = assignedTime;
    }

    public Instant getMovedBackTime() {
        return this.movedBackTime;
    }

    public ServiceOrderAssignment movedBackTime(Instant movedBackTime) {
        this.setMovedBackTime(movedBackTime);
        return this;
    }

    public void setMovedBackTime(Instant movedBackTime) {
        this.movedBackTime = movedBackTime;
    }

    public Instant getVisitTime() {
        return this.visitTime;
    }

    public ServiceOrderAssignment visitTime(Instant visitTime) {
        this.setVisitTime(visitTime);
        return this;
    }

    public void setVisitTime(Instant visitTime) {
        this.visitTime = visitTime;
    }

    public Instant getSpareOrderTime() {
        return this.spareOrderTime;
    }

    public ServiceOrderAssignment spareOrderTime(Instant spareOrderTime) {
        this.setSpareOrderTime(spareOrderTime);
        return this;
    }

    public void setSpareOrderTime(Instant spareOrderTime) {
        this.spareOrderTime = spareOrderTime;
    }

    public Instant getSpareDeliveryTime() {
        return this.spareDeliveryTime;
    }

    public ServiceOrderAssignment spareDeliveryTime(Instant spareDeliveryTime) {
        this.setSpareDeliveryTime(spareDeliveryTime);
        return this;
    }

    public void setSpareDeliveryTime(Instant spareDeliveryTime) {
        this.spareDeliveryTime = spareDeliveryTime;
    }

    public Instant getCompletedTime() {
        return this.completedTime;
    }

    public ServiceOrderAssignment completedTime(Instant completedTime) {
        this.setCompletedTime(completedTime);
        return this;
    }

    public void setCompletedTime(Instant completedTime) {
        this.completedTime = completedTime;
    }

    public Long getCompletionOTP() {
        return this.completionOTP;
    }

    public ServiceOrderAssignment completionOTP(Long completionOTP) {
        this.setCompletionOTP(completionOTP);
        return this;
    }

    public void setCompletionOTP(Long completionOTP) {
        this.completionOTP = completionOTP;
    }

    public Long getCancellationOTP() {
        return this.cancellationOTP;
    }

    public ServiceOrderAssignment cancellationOTP(Long cancellationOTP) {
        this.setCancellationOTP(cancellationOTP);
        return this;
    }

    public void setCancellationOTP(Long cancellationOTP) {
        this.cancellationOTP = cancellationOTP;
    }

    public Float getFranchiseCommision() {
        return this.franchiseCommision;
    }

    public ServiceOrderAssignment franchiseCommision(Float franchiseCommision) {
        this.setFranchiseCommision(franchiseCommision);
        return this;
    }

    public void setFranchiseCommision(Float franchiseCommision) {
        this.franchiseCommision = franchiseCommision;
    }

    public Float getFranchiseCommisionTax() {
        return this.franchiseCommisionTax;
    }

    public ServiceOrderAssignment franchiseCommisionTax(Float franchiseCommisionTax) {
        this.setFranchiseCommisionTax(franchiseCommisionTax);
        return this;
    }

    public void setFranchiseCommisionTax(Float franchiseCommisionTax) {
        this.franchiseCommisionTax = franchiseCommisionTax;
    }

    public String getFranchiseInvoicePath() {
        return this.franchiseInvoicePath;
    }

    public ServiceOrderAssignment franchiseInvoicePath(String franchiseInvoicePath) {
        this.setFranchiseInvoicePath(franchiseInvoicePath);
        return this;
    }

    public void setFranchiseInvoicePath(String franchiseInvoicePath) {
        this.franchiseInvoicePath = franchiseInvoicePath;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public ServiceOrderAssignment createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ServiceOrderAssignment createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ServiceOrderAssignment updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public ServiceOrderAssignment updatedTime(Instant updatedTime) {
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
            this.additionalAttributes.forEach(i -> i.setServiceOrderAssignment(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setServiceOrderAssignment(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public ServiceOrderAssignment additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public ServiceOrderAssignment addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setServiceOrderAssignment(this);
        return this;
    }

    public ServiceOrderAssignment removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setServiceOrderAssignment(null);
        return this;
    }

    public ServiceOrder getServiceOrder() {
        return this.serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public ServiceOrderAssignment serviceOrder(ServiceOrder serviceOrder) {
        this.setServiceOrder(serviceOrder);
        return this;
    }

    public Franchise getFranchise() {
        return this.franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public ServiceOrderAssignment franchise(Franchise franchise) {
        this.setFranchise(franchise);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrderAssignment)) {
            return false;
        }
        return getId() != null && getId().equals(((ServiceOrderAssignment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderAssignment{" +
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
            ", franchiseCommision=" + getFranchiseCommision() +
            ", franchiseCommisionTax=" + getFranchiseCommisionTax() +
            ", franchiseInvoicePath='" + getFranchiseInvoicePath() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
