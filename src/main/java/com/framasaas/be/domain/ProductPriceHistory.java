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
 * A ProductPriceHistory.
 */
@Entity
@Table(name = "product_price_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductPriceHistory implements Serializable {

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

    @Column(name = "franchise_commission")
    private Float franchiseCommission;

    @Column(name = "franchise_tax")
    private Float franchiseTax;

    @Column(name = "update_description")
    private String updateDescription;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @NotNull
    @Column(name = "updated_time", nullable = false)
    private Instant updatedTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "priceHistory")
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

    public ProductPriceHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return this.price;
    }

    public ProductPriceHistory price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTax() {
        return this.tax;
    }

    public ProductPriceHistory tax(Float tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getFranchiseCommission() {
        return this.franchiseCommission;
    }

    public ProductPriceHistory franchiseCommission(Float franchiseCommission) {
        this.setFranchiseCommission(franchiseCommission);
        return this;
    }

    public void setFranchiseCommission(Float franchiseCommission) {
        this.franchiseCommission = franchiseCommission;
    }

    public Float getFranchiseTax() {
        return this.franchiseTax;
    }

    public ProductPriceHistory franchiseTax(Float franchiseTax) {
        this.setFranchiseTax(franchiseTax);
        return this;
    }

    public void setFranchiseTax(Float franchiseTax) {
        this.franchiseTax = franchiseTax;
    }

    public String getUpdateDescription() {
        return this.updateDescription;
    }

    public ProductPriceHistory updateDescription(String updateDescription) {
        this.setUpdateDescription(updateDescription);
        return this;
    }

    public void setUpdateDescription(String updateDescription) {
        this.updateDescription = updateDescription;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ProductPriceHistory updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public ProductPriceHistory updatedTime(Instant updatedTime) {
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
            this.additionalAttributes.forEach(i -> i.setPriceHistory(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setPriceHistory(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public ProductPriceHistory additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public ProductPriceHistory addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setPriceHistory(this);
        return this;
    }

    public ProductPriceHistory removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setPriceHistory(null);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductPriceHistory product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductPriceHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((ProductPriceHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductPriceHistory{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", tax=" + getTax() +
            ", franchiseCommission=" + getFranchiseCommission() +
            ", franchiseTax=" + getFranchiseTax() +
            ", updateDescription='" + getUpdateDescription() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
