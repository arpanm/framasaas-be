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
 * A ServiceOrderFranchiseAssignment.
 */
@Entity
@Table(name = "service_order_franchise_assignment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderFranchiseAssignment implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceOrderFranchiseAssignment")
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
            "document",
            "product",
            "hsn",
            "priceHistory",
            "warrantyMaster",
            "warrantyMasterPriceHistory",
            "article",
            "articleWarranty",
            "articleWarrantyDocument",
            "serviceOrder",
            "serviceOrderPayment",
            "serviceOrderFranchiseAssignment",
            "serviceOrderFieldAgentAssignment",
            "franchiseAllocationRuleSet",
            "franchiseAllocationRule",
            "fieldAgentSkillRuleSet",
            "fieldAgentSkillRule",
            "serviceOrderAssignment",
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "serviceOrderFranchiseAssignments", "serviceOrderSpares", "additionalAttributes", "customer", "article", "address" },
        allowSetters = true
    )
    private ServiceOrder serviceOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "address",
            "franchiseStatusHistories",
            "franchisePerformanceHistories",
            "franchiseDocuments",
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

    public ServiceOrderFranchiseAssignment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceOrderAssignmentStatus getServiceOrderAssignmentStatus() {
        return this.serviceOrderAssignmentStatus;
    }

    public ServiceOrderFranchiseAssignment serviceOrderAssignmentStatus(ServiceOrderAssignmentStatus serviceOrderAssignmentStatus) {
        this.setServiceOrderAssignmentStatus(serviceOrderAssignmentStatus);
        return this;
    }

    public void setServiceOrderAssignmentStatus(ServiceOrderAssignmentStatus serviceOrderAssignmentStatus) {
        this.serviceOrderAssignmentStatus = serviceOrderAssignmentStatus;
    }

    public Long getNps() {
        return this.nps;
    }

    public ServiceOrderFranchiseAssignment nps(Long nps) {
        this.setNps(nps);
        return this;
    }

    public void setNps(Long nps) {
        this.nps = nps;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ServiceOrderFranchiseAssignment isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getAssignedTime() {
        return this.assignedTime;
    }

    public ServiceOrderFranchiseAssignment assignedTime(Instant assignedTime) {
        this.setAssignedTime(assignedTime);
        return this;
    }

    public void setAssignedTime(Instant assignedTime) {
        this.assignedTime = assignedTime;
    }

    public Instant getMovedBackTime() {
        return this.movedBackTime;
    }

    public ServiceOrderFranchiseAssignment movedBackTime(Instant movedBackTime) {
        this.setMovedBackTime(movedBackTime);
        return this;
    }

    public void setMovedBackTime(Instant movedBackTime) {
        this.movedBackTime = movedBackTime;
    }

    public Instant getVisitTime() {
        return this.visitTime;
    }

    public ServiceOrderFranchiseAssignment visitTime(Instant visitTime) {
        this.setVisitTime(visitTime);
        return this;
    }

    public void setVisitTime(Instant visitTime) {
        this.visitTime = visitTime;
    }

    public Instant getSpareOrderTime() {
        return this.spareOrderTime;
    }

    public ServiceOrderFranchiseAssignment spareOrderTime(Instant spareOrderTime) {
        this.setSpareOrderTime(spareOrderTime);
        return this;
    }

    public void setSpareOrderTime(Instant spareOrderTime) {
        this.spareOrderTime = spareOrderTime;
    }

    public Instant getSpareDeliveryTime() {
        return this.spareDeliveryTime;
    }

    public ServiceOrderFranchiseAssignment spareDeliveryTime(Instant spareDeliveryTime) {
        this.setSpareDeliveryTime(spareDeliveryTime);
        return this;
    }

    public void setSpareDeliveryTime(Instant spareDeliveryTime) {
        this.spareDeliveryTime = spareDeliveryTime;
    }

    public Instant getCompletedTime() {
        return this.completedTime;
    }

    public ServiceOrderFranchiseAssignment completedTime(Instant completedTime) {
        this.setCompletedTime(completedTime);
        return this;
    }

    public void setCompletedTime(Instant completedTime) {
        this.completedTime = completedTime;
    }

    public Float getFranchiseCommision() {
        return this.franchiseCommision;
    }

    public ServiceOrderFranchiseAssignment franchiseCommision(Float franchiseCommision) {
        this.setFranchiseCommision(franchiseCommision);
        return this;
    }

    public void setFranchiseCommision(Float franchiseCommision) {
        this.franchiseCommision = franchiseCommision;
    }

    public Float getFranchiseCommisionTax() {
        return this.franchiseCommisionTax;
    }

    public ServiceOrderFranchiseAssignment franchiseCommisionTax(Float franchiseCommisionTax) {
        this.setFranchiseCommisionTax(franchiseCommisionTax);
        return this;
    }

    public void setFranchiseCommisionTax(Float franchiseCommisionTax) {
        this.franchiseCommisionTax = franchiseCommisionTax;
    }

    public String getFranchiseInvoicePath() {
        return this.franchiseInvoicePath;
    }

    public ServiceOrderFranchiseAssignment franchiseInvoicePath(String franchiseInvoicePath) {
        this.setFranchiseInvoicePath(franchiseInvoicePath);
        return this;
    }

    public void setFranchiseInvoicePath(String franchiseInvoicePath) {
        this.franchiseInvoicePath = franchiseInvoicePath;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public ServiceOrderFranchiseAssignment createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ServiceOrderFranchiseAssignment createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ServiceOrderFranchiseAssignment updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public ServiceOrderFranchiseAssignment updatedTime(Instant updatedTime) {
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
            this.additionalAttributes.forEach(i -> i.setServiceOrderFranchiseAssignment(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setServiceOrderFranchiseAssignment(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public ServiceOrderFranchiseAssignment additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public ServiceOrderFranchiseAssignment addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setServiceOrderFranchiseAssignment(this);
        return this;
    }

    public ServiceOrderFranchiseAssignment removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setServiceOrderFranchiseAssignment(null);
        return this;
    }

    public ServiceOrder getServiceOrder() {
        return this.serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public ServiceOrderFranchiseAssignment serviceOrder(ServiceOrder serviceOrder) {
        this.setServiceOrder(serviceOrder);
        return this;
    }

    public Franchise getFranchise() {
        return this.franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public ServiceOrderFranchiseAssignment franchise(Franchise franchise) {
        this.setFranchise(franchise);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrderFranchiseAssignment)) {
            return false;
        }
        return getId() != null && getId().equals(((ServiceOrderFranchiseAssignment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderFranchiseAssignment{" +
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
            "}";
    }
}
