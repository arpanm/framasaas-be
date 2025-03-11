package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WarrantyMasterPriceHistory.
 */
@Entity
@Table(name = "warranty_master_price_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarrantyMasterPriceHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @NotNull
    @Column(name = "updated_time", nullable = false)
    private Instant updatedTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warrantyMasterPriceHistory")
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
        value = { "warrantyMasterPriceHistories", "articleWarrantyDetails", "additionalAttributes", "product" },
        allowSetters = true
    )
    private WarrantyMaster warrantyMaster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WarrantyMasterPriceHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return this.price;
    }

    public WarrantyMasterPriceHistory price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public WarrantyMasterPriceHistory updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public WarrantyMasterPriceHistory updatedTime(Instant updatedTime) {
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
            this.additionalAttributes.forEach(i -> i.setWarrantyMasterPriceHistory(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setWarrantyMasterPriceHistory(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public WarrantyMasterPriceHistory additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public WarrantyMasterPriceHistory addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setWarrantyMasterPriceHistory(this);
        return this;
    }

    public WarrantyMasterPriceHistory removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setWarrantyMasterPriceHistory(null);
        return this;
    }

    public WarrantyMaster getWarrantyMaster() {
        return this.warrantyMaster;
    }

    public void setWarrantyMaster(WarrantyMaster warrantyMaster) {
        this.warrantyMaster = warrantyMaster;
    }

    public WarrantyMasterPriceHistory warrantyMaster(WarrantyMaster warrantyMaster) {
        this.setWarrantyMaster(warrantyMaster);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarrantyMasterPriceHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((WarrantyMasterPriceHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarrantyMasterPriceHistory{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
