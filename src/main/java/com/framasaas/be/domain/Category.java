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
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "image_path")
    private String imagePath;

    @NotNull
    @Column(name = "vendor_category_id", nullable = false)
    private String vendorCategoryId;

    @Column(name = "description")
    private String description;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
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
    private Set<Product> products = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
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
            "inventoryLocation",
            "inventory",
            "serviceOrderAssignment",
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "brands", "categories", "pincodes", "locationMappings", "languageMappings", "additionalAttributes" },
        allowSetters = true
    )
    private FranchiseAllocationRule franchiseRule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "brands", "categories", "pincodes", "locationMappings", "languageMappings", "additionalAttributes", "fieldAgentSkillRuleSet",
        },
        allowSetters = true
    )
    private FieldAgentSkillRule fieldAgentSkillRule;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Category id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public Category categoryName(String categoryName) {
        this.setCategoryName(categoryName);
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public Category imagePath(String imagePath) {
        this.setImagePath(imagePath);
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVendorCategoryId() {
        return this.vendorCategoryId;
    }

    public Category vendorCategoryId(String vendorCategoryId) {
        this.setVendorCategoryId(vendorCategoryId);
        return this;
    }

    public void setVendorCategoryId(String vendorCategoryId) {
        this.vendorCategoryId = vendorCategoryId;
    }

    public String getDescription() {
        return this.description;
    }

    public Category description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Category isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Category createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Category createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Category updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Category updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setCategory(null));
        }
        if (products != null) {
            products.forEach(i -> i.setCategory(this));
        }
        this.products = products;
    }

    public Category products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Category addProduct(Product product) {
        this.products.add(product);
        product.setCategory(this);
        return this;
    }

    public Category removeProduct(Product product) {
        this.products.remove(product);
        product.setCategory(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setCategory(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setCategory(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public Category additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public Category addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setCategory(this);
        return this;
    }

    public Category removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setCategory(null);
        return this;
    }

    public FranchiseAllocationRule getFranchiseRule() {
        return this.franchiseRule;
    }

    public void setFranchiseRule(FranchiseAllocationRule franchiseAllocationRule) {
        this.franchiseRule = franchiseAllocationRule;
    }

    public Category franchiseRule(FranchiseAllocationRule franchiseAllocationRule) {
        this.setFranchiseRule(franchiseAllocationRule);
        return this;
    }

    public FieldAgentSkillRule getFieldAgentSkillRule() {
        return this.fieldAgentSkillRule;
    }

    public void setFieldAgentSkillRule(FieldAgentSkillRule fieldAgentSkillRule) {
        this.fieldAgentSkillRule = fieldAgentSkillRule;
    }

    public Category fieldAgentSkillRule(FieldAgentSkillRule fieldAgentSkillRule) {
        this.setFieldAgentSkillRule(fieldAgentSkillRule);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return getId() != null && getId().equals(((Category) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", vendorCategoryId='" + getVendorCategoryId() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
