package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.ServiceOrderSpareStatus;
import com.framasaas.be.domain.enumeration.SpareOrderedFrom;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServiceOrderSpare.
 */
@Entity
@Table(name = "service_order_spare")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderSpare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private Float price;

    @Column(name = "tax")
    private Float tax;

    @Column(name = "total_charge")
    private Float totalCharge;

    @Enumerated(EnumType.STRING)
    @Column(name = "ordered_from")
    private SpareOrderedFrom orderedFrom;

    @Enumerated(EnumType.STRING)
    @Column(name = "spare_status")
    private ServiceOrderSpareStatus spareStatus;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceOrderSpare")
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
            "serviceOrderSpare",
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
            "productPriceHistories",
            "warrantyMasters",
            "articles",
            "serviceOrderMasters",
            "serviceOrderSpares",
            "additionalAttributes",
            "category",
            "brand",
            "hsn",
        },
        allowSetters = true
    )
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServiceOrderSpare id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return this.price;
    }

    public ServiceOrderSpare price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTax() {
        return this.tax;
    }

    public ServiceOrderSpare tax(Float tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getTotalCharge() {
        return this.totalCharge;
    }

    public ServiceOrderSpare totalCharge(Float totalCharge) {
        this.setTotalCharge(totalCharge);
        return this;
    }

    public void setTotalCharge(Float totalCharge) {
        this.totalCharge = totalCharge;
    }

    public SpareOrderedFrom getOrderedFrom() {
        return this.orderedFrom;
    }

    public ServiceOrderSpare orderedFrom(SpareOrderedFrom orderedFrom) {
        this.setOrderedFrom(orderedFrom);
        return this;
    }

    public void setOrderedFrom(SpareOrderedFrom orderedFrom) {
        this.orderedFrom = orderedFrom;
    }

    public ServiceOrderSpareStatus getSpareStatus() {
        return this.spareStatus;
    }

    public ServiceOrderSpare spareStatus(ServiceOrderSpareStatus spareStatus) {
        this.setSpareStatus(spareStatus);
        return this;
    }

    public void setSpareStatus(ServiceOrderSpareStatus spareStatus) {
        this.spareStatus = spareStatus;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public ServiceOrderSpare createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ServiceOrderSpare createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ServiceOrderSpare updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public ServiceOrderSpare updatedTime(Instant updatedTime) {
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
            this.additionalAttributes.forEach(i -> i.setServiceOrderSpare(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setServiceOrderSpare(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public ServiceOrderSpare additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public ServiceOrderSpare addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setServiceOrderSpare(this);
        return this;
    }

    public ServiceOrderSpare removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setServiceOrderSpare(null);
        return this;
    }

    public ServiceOrder getServiceOrder() {
        return this.serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public ServiceOrderSpare serviceOrder(ServiceOrder serviceOrder) {
        this.setServiceOrder(serviceOrder);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ServiceOrderSpare product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrderSpare)) {
            return false;
        }
        return getId() != null && getId().equals(((ServiceOrderSpare) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderSpare{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", tax=" + getTax() +
            ", totalCharge=" + getTotalCharge() +
            ", orderedFrom='" + getOrderedFrom() + "'" +
            ", spareStatus='" + getSpareStatus() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
