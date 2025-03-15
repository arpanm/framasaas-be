package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.WarrantyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WarrantyMaster.
 */
@Entity
@Table(name = "warranty_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarrantyMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "vendor_warranty_master_id", nullable = false)
    private String vendorWarrantyMasterId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "warranty_type", nullable = false)
    private WarrantyType warrantyType;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @NotNull
    @Column(name = "tax", nullable = false)
    private Float tax;

    @NotNull
    @Column(name = "franchise_commission", nullable = false)
    private Float franchiseCommission;

    @NotNull
    @Column(name = "franchise_tax", nullable = false)
    private Float franchiseTax;

    @NotNull
    @Column(name = "period_in_months", nullable = false)
    private Long periodInMonths;

    @NotNull
    @Column(name = "tax_rate", nullable = false)
    private Float taxRate;

    @Column(name = "coverage")
    private String coverage;

    @Column(name = "exclusion")
    private String exclusion;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warrantyMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "additionalAttributes", "warrantyMaster" }, allowSetters = true)
    private Set<WarrantyMasterPriceHistory> warrantyMasterPriceHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warrantyMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supportingDocuments", "additionalAttributes", "article", "warrantyMaster" }, allowSetters = true)
    private Set<ArticleWarrantyDetails> articleWarrantyDetails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warrantyMaster")
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_warranty_master__covered_spare",
        joinColumns = @JoinColumn(name = "warranty_master_id"),
        inverseJoinColumns = @JoinColumn(name = "covered_spare_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Product> coveredSpares = new HashSet<>();

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

    public WarrantyMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public WarrantyMaster name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorWarrantyMasterId() {
        return this.vendorWarrantyMasterId;
    }

    public WarrantyMaster vendorWarrantyMasterId(String vendorWarrantyMasterId) {
        this.setVendorWarrantyMasterId(vendorWarrantyMasterId);
        return this;
    }

    public void setVendorWarrantyMasterId(String vendorWarrantyMasterId) {
        this.vendorWarrantyMasterId = vendorWarrantyMasterId;
    }

    public WarrantyType getWarrantyType() {
        return this.warrantyType;
    }

    public WarrantyMaster warrantyType(WarrantyType warrantyType) {
        this.setWarrantyType(warrantyType);
        return this;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public String getDescription() {
        return this.description;
    }

    public WarrantyMaster description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return this.price;
    }

    public WarrantyMaster price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTax() {
        return this.tax;
    }

    public WarrantyMaster tax(Float tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getFranchiseCommission() {
        return this.franchiseCommission;
    }

    public WarrantyMaster franchiseCommission(Float franchiseCommission) {
        this.setFranchiseCommission(franchiseCommission);
        return this;
    }

    public void setFranchiseCommission(Float franchiseCommission) {
        this.franchiseCommission = franchiseCommission;
    }

    public Float getFranchiseTax() {
        return this.franchiseTax;
    }

    public WarrantyMaster franchiseTax(Float franchiseTax) {
        this.setFranchiseTax(franchiseTax);
        return this;
    }

    public void setFranchiseTax(Float franchiseTax) {
        this.franchiseTax = franchiseTax;
    }

    public Long getPeriodInMonths() {
        return this.periodInMonths;
    }

    public WarrantyMaster periodInMonths(Long periodInMonths) {
        this.setPeriodInMonths(periodInMonths);
        return this;
    }

    public void setPeriodInMonths(Long periodInMonths) {
        this.periodInMonths = periodInMonths;
    }

    public Float getTaxRate() {
        return this.taxRate;
    }

    public WarrantyMaster taxRate(Float taxRate) {
        this.setTaxRate(taxRate);
        return this;
    }

    public void setTaxRate(Float taxRate) {
        this.taxRate = taxRate;
    }

    public String getCoverage() {
        return this.coverage;
    }

    public WarrantyMaster coverage(String coverage) {
        this.setCoverage(coverage);
        return this;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getExclusion() {
        return this.exclusion;
    }

    public WarrantyMaster exclusion(String exclusion) {
        this.setExclusion(exclusion);
        return this;
    }

    public void setExclusion(String exclusion) {
        this.exclusion = exclusion;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public WarrantyMaster isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public WarrantyMaster createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public WarrantyMaster createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public WarrantyMaster updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public WarrantyMaster updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<WarrantyMasterPriceHistory> getWarrantyMasterPriceHistories() {
        return this.warrantyMasterPriceHistories;
    }

    public void setWarrantyMasterPriceHistories(Set<WarrantyMasterPriceHistory> warrantyMasterPriceHistories) {
        if (this.warrantyMasterPriceHistories != null) {
            this.warrantyMasterPriceHistories.forEach(i -> i.setWarrantyMaster(null));
        }
        if (warrantyMasterPriceHistories != null) {
            warrantyMasterPriceHistories.forEach(i -> i.setWarrantyMaster(this));
        }
        this.warrantyMasterPriceHistories = warrantyMasterPriceHistories;
    }

    public WarrantyMaster warrantyMasterPriceHistories(Set<WarrantyMasterPriceHistory> warrantyMasterPriceHistories) {
        this.setWarrantyMasterPriceHistories(warrantyMasterPriceHistories);
        return this;
    }

    public WarrantyMaster addWarrantyMasterPriceHistory(WarrantyMasterPriceHistory warrantyMasterPriceHistory) {
        this.warrantyMasterPriceHistories.add(warrantyMasterPriceHistory);
        warrantyMasterPriceHistory.setWarrantyMaster(this);
        return this;
    }

    public WarrantyMaster removeWarrantyMasterPriceHistory(WarrantyMasterPriceHistory warrantyMasterPriceHistory) {
        this.warrantyMasterPriceHistories.remove(warrantyMasterPriceHistory);
        warrantyMasterPriceHistory.setWarrantyMaster(null);
        return this;
    }

    public Set<ArticleWarrantyDetails> getArticleWarrantyDetails() {
        return this.articleWarrantyDetails;
    }

    public void setArticleWarrantyDetails(Set<ArticleWarrantyDetails> articleWarrantyDetails) {
        if (this.articleWarrantyDetails != null) {
            this.articleWarrantyDetails.forEach(i -> i.setWarrantyMaster(null));
        }
        if (articleWarrantyDetails != null) {
            articleWarrantyDetails.forEach(i -> i.setWarrantyMaster(this));
        }
        this.articleWarrantyDetails = articleWarrantyDetails;
    }

    public WarrantyMaster articleWarrantyDetails(Set<ArticleWarrantyDetails> articleWarrantyDetails) {
        this.setArticleWarrantyDetails(articleWarrantyDetails);
        return this;
    }

    public WarrantyMaster addArticleWarrantyDetails(ArticleWarrantyDetails articleWarrantyDetails) {
        this.articleWarrantyDetails.add(articleWarrantyDetails);
        articleWarrantyDetails.setWarrantyMaster(this);
        return this;
    }

    public WarrantyMaster removeArticleWarrantyDetails(ArticleWarrantyDetails articleWarrantyDetails) {
        this.articleWarrantyDetails.remove(articleWarrantyDetails);
        articleWarrantyDetails.setWarrantyMaster(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setWarrantyMaster(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setWarrantyMaster(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public WarrantyMaster additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public WarrantyMaster addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setWarrantyMaster(this);
        return this;
    }

    public WarrantyMaster removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setWarrantyMaster(null);
        return this;
    }

    public Set<Product> getCoveredSpares() {
        return this.coveredSpares;
    }

    public void setCoveredSpares(Set<Product> products) {
        this.coveredSpares = products;
    }

    public WarrantyMaster coveredSpares(Set<Product> products) {
        this.setCoveredSpares(products);
        return this;
    }

    public WarrantyMaster addCoveredSpare(Product product) {
        this.coveredSpares.add(product);
        return this;
    }

    public WarrantyMaster removeCoveredSpare(Product product) {
        this.coveredSpares.remove(product);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public WarrantyMaster product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WarrantyMaster)) {
            return false;
        }
        return getId() != null && getId().equals(((WarrantyMaster) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarrantyMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vendorWarrantyMasterId='" + getVendorWarrantyMasterId() + "'" +
            ", warrantyType='" + getWarrantyType() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", tax=" + getTax() +
            ", franchiseCommission=" + getFranchiseCommission() +
            ", franchiseTax=" + getFranchiseTax() +
            ", periodInMonths=" + getPeriodInMonths() +
            ", taxRate=" + getTaxRate() +
            ", coverage='" + getCoverage() + "'" +
            ", exclusion='" + getExclusion() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
