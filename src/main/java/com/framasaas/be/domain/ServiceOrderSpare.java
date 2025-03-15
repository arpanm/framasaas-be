package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.InventoryLocationType;
import com.framasaas.be.domain.enumeration.ServiceOrderSpareStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
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

    @Column(name = "franchise_commision")
    private Float franchiseCommision;

    @Column(name = "franchise_commision_tax")
    private Float franchiseCommisionTax;

    @Enumerated(EnumType.STRING)
    @Column(name = "ordered_from")
    private InventoryLocationType orderedFrom;

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
            "inventories",
            "additionalAttributes",
            "category",
            "brand",
            "hsn",
            "coveredUnderWarranties",
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

    public Float getFranchiseCommision() {
        return this.franchiseCommision;
    }

    public ServiceOrderSpare franchiseCommision(Float franchiseCommision) {
        this.setFranchiseCommision(franchiseCommision);
        return this;
    }

    public void setFranchiseCommision(Float franchiseCommision) {
        this.franchiseCommision = franchiseCommision;
    }

    public Float getFranchiseCommisionTax() {
        return this.franchiseCommisionTax;
    }

    public ServiceOrderSpare franchiseCommisionTax(Float franchiseCommisionTax) {
        this.setFranchiseCommisionTax(franchiseCommisionTax);
        return this;
    }

    public void setFranchiseCommisionTax(Float franchiseCommisionTax) {
        this.franchiseCommisionTax = franchiseCommisionTax;
    }

    public InventoryLocationType getOrderedFrom() {
        return this.orderedFrom;
    }

    public ServiceOrderSpare orderedFrom(InventoryLocationType orderedFrom) {
        this.setOrderedFrom(orderedFrom);
        return this;
    }

    public void setOrderedFrom(InventoryLocationType orderedFrom) {
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
            ", franchiseCommision=" + getFranchiseCommision() +
            ", franchiseCommisionTax=" + getFranchiseCommisionTax() +
            ", orderedFrom='" + getOrderedFrom() + "'" +
            ", spareStatus='" + getSpareStatus() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
