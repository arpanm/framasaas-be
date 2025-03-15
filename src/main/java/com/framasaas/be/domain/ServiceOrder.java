package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.ServiceOrderStatus;
import com.framasaas.be.domain.enumeration.ServiceOrderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServiceOrder.
 */
@Entity
@Table(name = "service_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private ServiceOrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private ServiceOrderStatus orderStatus;

    @Column(name = "in_warranty")
    private Boolean inWarranty;

    @Column(name = "is_free")
    private Boolean isFree;

    @Column(name = "is_spare_needed")
    private Boolean isSpareNeeded;

    @Column(name = "confirmed_time")
    private Instant confirmedTime;

    @Column(name = "closed_time")
    private Instant closedTime;

    @Column(name = "service_charge")
    private Float serviceCharge;

    @Column(name = "tax")
    private Float tax;

    @Column(name = "total_spare_charges")
    private Float totalSpareCharges;

    @Column(name = "total_spare_tax")
    private Float totalSpareTax;

    @Column(name = "total_charges")
    private Float totalCharges;

    @Column(name = "total_payment_done")
    private Float totalPaymentDone;

    @Column(name = "customer_invoice_path")
    private String customerInvoicePath;

    @Column(name = "nps")
    private Float nps;

    @Column(name = "priority")
    private Long priority;

    @Column(name = "is_active")
    private Boolean isActive;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceOrder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "additionalAttributes", "serviceOrder", "franchise" }, allowSetters = true)
    private Set<ServiceOrderFranchiseAssignment> serviceOrderFranchiseAssignments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceOrder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "serviceOrder", "product" }, allowSetters = true)
    private Set<ServiceOrderSpare> serviceOrderSpares = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceOrder")
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
    @JsonIgnoreProperties(value = { "addresses", "articles", "serviceOrders", "additionalAttributes" }, allowSetters = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "articleWarrantyDetails", "serviceOrders", "additionalAttributes", "product", "customer" },
        allowSetters = true
    )
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "serviceOrders", "additionalAttributes", "location", "franchise", "customer" }, allowSetters = true)
    private Address address;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServiceOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public ServiceOrder description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceOrderType getOrderType() {
        return this.orderType;
    }

    public ServiceOrder orderType(ServiceOrderType orderType) {
        this.setOrderType(orderType);
        return this;
    }

    public void setOrderType(ServiceOrderType orderType) {
        this.orderType = orderType;
    }

    public ServiceOrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public ServiceOrder orderStatus(ServiceOrderStatus orderStatus) {
        this.setOrderStatus(orderStatus);
        return this;
    }

    public void setOrderStatus(ServiceOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getInWarranty() {
        return this.inWarranty;
    }

    public ServiceOrder inWarranty(Boolean inWarranty) {
        this.setInWarranty(inWarranty);
        return this;
    }

    public void setInWarranty(Boolean inWarranty) {
        this.inWarranty = inWarranty;
    }

    public Boolean getIsFree() {
        return this.isFree;
    }

    public ServiceOrder isFree(Boolean isFree) {
        this.setIsFree(isFree);
        return this;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Boolean getIsSpareNeeded() {
        return this.isSpareNeeded;
    }

    public ServiceOrder isSpareNeeded(Boolean isSpareNeeded) {
        this.setIsSpareNeeded(isSpareNeeded);
        return this;
    }

    public void setIsSpareNeeded(Boolean isSpareNeeded) {
        this.isSpareNeeded = isSpareNeeded;
    }

    public Instant getConfirmedTime() {
        return this.confirmedTime;
    }

    public ServiceOrder confirmedTime(Instant confirmedTime) {
        this.setConfirmedTime(confirmedTime);
        return this;
    }

    public void setConfirmedTime(Instant confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    public Instant getClosedTime() {
        return this.closedTime;
    }

    public ServiceOrder closedTime(Instant closedTime) {
        this.setClosedTime(closedTime);
        return this;
    }

    public void setClosedTime(Instant closedTime) {
        this.closedTime = closedTime;
    }

    public Float getServiceCharge() {
        return this.serviceCharge;
    }

    public ServiceOrder serviceCharge(Float serviceCharge) {
        this.setServiceCharge(serviceCharge);
        return this;
    }

    public void setServiceCharge(Float serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Float getTax() {
        return this.tax;
    }

    public ServiceOrder tax(Float tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getTotalSpareCharges() {
        return this.totalSpareCharges;
    }

    public ServiceOrder totalSpareCharges(Float totalSpareCharges) {
        this.setTotalSpareCharges(totalSpareCharges);
        return this;
    }

    public void setTotalSpareCharges(Float totalSpareCharges) {
        this.totalSpareCharges = totalSpareCharges;
    }

    public Float getTotalSpareTax() {
        return this.totalSpareTax;
    }

    public ServiceOrder totalSpareTax(Float totalSpareTax) {
        this.setTotalSpareTax(totalSpareTax);
        return this;
    }

    public void setTotalSpareTax(Float totalSpareTax) {
        this.totalSpareTax = totalSpareTax;
    }

    public Float getTotalCharges() {
        return this.totalCharges;
    }

    public ServiceOrder totalCharges(Float totalCharges) {
        this.setTotalCharges(totalCharges);
        return this;
    }

    public void setTotalCharges(Float totalCharges) {
        this.totalCharges = totalCharges;
    }

    public Float getTotalPaymentDone() {
        return this.totalPaymentDone;
    }

    public ServiceOrder totalPaymentDone(Float totalPaymentDone) {
        this.setTotalPaymentDone(totalPaymentDone);
        return this;
    }

    public void setTotalPaymentDone(Float totalPaymentDone) {
        this.totalPaymentDone = totalPaymentDone;
    }

    public String getCustomerInvoicePath() {
        return this.customerInvoicePath;
    }

    public ServiceOrder customerInvoicePath(String customerInvoicePath) {
        this.setCustomerInvoicePath(customerInvoicePath);
        return this;
    }

    public void setCustomerInvoicePath(String customerInvoicePath) {
        this.customerInvoicePath = customerInvoicePath;
    }

    public Float getNps() {
        return this.nps;
    }

    public ServiceOrder nps(Float nps) {
        this.setNps(nps);
        return this;
    }

    public void setNps(Float nps) {
        this.nps = nps;
    }

    public Long getPriority() {
        return this.priority;
    }

    public ServiceOrder priority(Long priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ServiceOrder isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public ServiceOrder createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ServiceOrder createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ServiceOrder updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public ServiceOrder updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<ServiceOrderFranchiseAssignment> getServiceOrderFranchiseAssignments() {
        return this.serviceOrderFranchiseAssignments;
    }

    public void setServiceOrderFranchiseAssignments(Set<ServiceOrderFranchiseAssignment> serviceOrderFranchiseAssignments) {
        if (this.serviceOrderFranchiseAssignments != null) {
            this.serviceOrderFranchiseAssignments.forEach(i -> i.setServiceOrder(null));
        }
        if (serviceOrderFranchiseAssignments != null) {
            serviceOrderFranchiseAssignments.forEach(i -> i.setServiceOrder(this));
        }
        this.serviceOrderFranchiseAssignments = serviceOrderFranchiseAssignments;
    }

    public ServiceOrder serviceOrderFranchiseAssignments(Set<ServiceOrderFranchiseAssignment> serviceOrderFranchiseAssignments) {
        this.setServiceOrderFranchiseAssignments(serviceOrderFranchiseAssignments);
        return this;
    }

    public ServiceOrder addServiceOrderFranchiseAssignment(ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment) {
        this.serviceOrderFranchiseAssignments.add(serviceOrderFranchiseAssignment);
        serviceOrderFranchiseAssignment.setServiceOrder(this);
        return this;
    }

    public ServiceOrder removeServiceOrderFranchiseAssignment(ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment) {
        this.serviceOrderFranchiseAssignments.remove(serviceOrderFranchiseAssignment);
        serviceOrderFranchiseAssignment.setServiceOrder(null);
        return this;
    }

    public Set<ServiceOrderSpare> getServiceOrderSpares() {
        return this.serviceOrderSpares;
    }

    public void setServiceOrderSpares(Set<ServiceOrderSpare> serviceOrderSpares) {
        if (this.serviceOrderSpares != null) {
            this.serviceOrderSpares.forEach(i -> i.setServiceOrder(null));
        }
        if (serviceOrderSpares != null) {
            serviceOrderSpares.forEach(i -> i.setServiceOrder(this));
        }
        this.serviceOrderSpares = serviceOrderSpares;
    }

    public ServiceOrder serviceOrderSpares(Set<ServiceOrderSpare> serviceOrderSpares) {
        this.setServiceOrderSpares(serviceOrderSpares);
        return this;
    }

    public ServiceOrder addServiceOrderSpare(ServiceOrderSpare serviceOrderSpare) {
        this.serviceOrderSpares.add(serviceOrderSpare);
        serviceOrderSpare.setServiceOrder(this);
        return this;
    }

    public ServiceOrder removeServiceOrderSpare(ServiceOrderSpare serviceOrderSpare) {
        this.serviceOrderSpares.remove(serviceOrderSpare);
        serviceOrderSpare.setServiceOrder(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setServiceOrder(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setServiceOrder(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public ServiceOrder additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public ServiceOrder addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setServiceOrder(this);
        return this;
    }

    public ServiceOrder removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setServiceOrder(null);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ServiceOrder customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public ServiceOrder article(Article article) {
        this.setArticle(article);
        return this;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ServiceOrder address(Address address) {
        this.setAddress(address);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrder)) {
            return false;
        }
        return getId() != null && getId().equals(((ServiceOrder) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrder{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", orderType='" + getOrderType() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", inWarranty='" + getInWarranty() + "'" +
            ", isFree='" + getIsFree() + "'" +
            ", isSpareNeeded='" + getIsSpareNeeded() + "'" +
            ", confirmedTime='" + getConfirmedTime() + "'" +
            ", closedTime='" + getClosedTime() + "'" +
            ", serviceCharge=" + getServiceCharge() +
            ", tax=" + getTax() +
            ", totalSpareCharges=" + getTotalSpareCharges() +
            ", totalSpareTax=" + getTotalSpareTax() +
            ", totalCharges=" + getTotalCharges() +
            ", totalPaymentDone=" + getTotalPaymentDone() +
            ", customerInvoicePath='" + getCustomerInvoicePath() + "'" +
            ", nps=" + getNps() +
            ", priority=" + getPriority() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
