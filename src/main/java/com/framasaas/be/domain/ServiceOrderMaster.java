package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.ServiceOrderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServiceOrderMaster.
 */
@Entity
@Table(name = "service_order_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_order_type")
    private ServiceOrderType serviceOrderType;

    @Column(name = "sla_in_hours")
    private Long slaInHours;

    @Column(name = "charge")
    private Float charge;

    @Column(name = "tax")
    private Float tax;

    @Column(name = "franchise_commission_within_sla")
    private Float franchiseCommissionWithinSla;

    @Column(name = "franchise_charge_within_sla_tax")
    private Float franchiseChargeWithinSlaTax;

    @Column(name = "franchise_commission_outside_sla")
    private Float franchiseCommissionOutsideSla;

    @Column(name = "franchise_charge_outside_sla_tax")
    private Float franchiseChargeOutsideSlaTax;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "productPriceHistories",
            "warrantyMasters",
            "articles",
            "serviceOrderMasters",
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

    public ServiceOrderMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceOrderType getServiceOrderType() {
        return this.serviceOrderType;
    }

    public ServiceOrderMaster serviceOrderType(ServiceOrderType serviceOrderType) {
        this.setServiceOrderType(serviceOrderType);
        return this;
    }

    public void setServiceOrderType(ServiceOrderType serviceOrderType) {
        this.serviceOrderType = serviceOrderType;
    }

    public Long getSlaInHours() {
        return this.slaInHours;
    }

    public ServiceOrderMaster slaInHours(Long slaInHours) {
        this.setSlaInHours(slaInHours);
        return this;
    }

    public void setSlaInHours(Long slaInHours) {
        this.slaInHours = slaInHours;
    }

    public Float getCharge() {
        return this.charge;
    }

    public ServiceOrderMaster charge(Float charge) {
        this.setCharge(charge);
        return this;
    }

    public void setCharge(Float charge) {
        this.charge = charge;
    }

    public Float getTax() {
        return this.tax;
    }

    public ServiceOrderMaster tax(Float tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getFranchiseCommissionWithinSla() {
        return this.franchiseCommissionWithinSla;
    }

    public ServiceOrderMaster franchiseCommissionWithinSla(Float franchiseCommissionWithinSla) {
        this.setFranchiseCommissionWithinSla(franchiseCommissionWithinSla);
        return this;
    }

    public void setFranchiseCommissionWithinSla(Float franchiseCommissionWithinSla) {
        this.franchiseCommissionWithinSla = franchiseCommissionWithinSla;
    }

    public Float getFranchiseChargeWithinSlaTax() {
        return this.franchiseChargeWithinSlaTax;
    }

    public ServiceOrderMaster franchiseChargeWithinSlaTax(Float franchiseChargeWithinSlaTax) {
        this.setFranchiseChargeWithinSlaTax(franchiseChargeWithinSlaTax);
        return this;
    }

    public void setFranchiseChargeWithinSlaTax(Float franchiseChargeWithinSlaTax) {
        this.franchiseChargeWithinSlaTax = franchiseChargeWithinSlaTax;
    }

    public Float getFranchiseCommissionOutsideSla() {
        return this.franchiseCommissionOutsideSla;
    }

    public ServiceOrderMaster franchiseCommissionOutsideSla(Float franchiseCommissionOutsideSla) {
        this.setFranchiseCommissionOutsideSla(franchiseCommissionOutsideSla);
        return this;
    }

    public void setFranchiseCommissionOutsideSla(Float franchiseCommissionOutsideSla) {
        this.franchiseCommissionOutsideSla = franchiseCommissionOutsideSla;
    }

    public Float getFranchiseChargeOutsideSlaTax() {
        return this.franchiseChargeOutsideSlaTax;
    }

    public ServiceOrderMaster franchiseChargeOutsideSlaTax(Float franchiseChargeOutsideSlaTax) {
        this.setFranchiseChargeOutsideSlaTax(franchiseChargeOutsideSlaTax);
        return this;
    }

    public void setFranchiseChargeOutsideSlaTax(Float franchiseChargeOutsideSlaTax) {
        this.franchiseChargeOutsideSlaTax = franchiseChargeOutsideSlaTax;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ServiceOrderMaster isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public ServiceOrderMaster createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ServiceOrderMaster createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ServiceOrderMaster updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public ServiceOrderMaster updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ServiceOrderMaster product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOrderMaster)) {
            return false;
        }
        return getId() != null && getId().equals(((ServiceOrderMaster) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderMaster{" +
            "id=" + getId() +
            ", serviceOrderType='" + getServiceOrderType() + "'" +
            ", slaInHours=" + getSlaInHours() +
            ", charge=" + getCharge() +
            ", tax=" + getTax() +
            ", franchiseCommissionWithinSla=" + getFranchiseCommissionWithinSla() +
            ", franchiseChargeWithinSlaTax=" + getFranchiseChargeWithinSlaTax() +
            ", franchiseCommissionOutsideSla=" + getFranchiseCommissionOutsideSla() +
            ", franchiseChargeOutsideSlaTax=" + getFranchiseChargeOutsideSlaTax() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
