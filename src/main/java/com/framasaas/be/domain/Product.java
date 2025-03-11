package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "vendor_product_id", nullable = false)
    private String vendorProductId;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "additionalAttributes", "product" }, allowSetters = true)
    private Set<ProductPriceHistory> productPriceHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "warrantyMasterPriceHistories", "articleWarrantyDetails", "additionalAttributes", "product" },
        allowSetters = true
    )
    private Set<WarrantyMaster> warrantyMasters = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "articleWarrantyDetails", "serviceOrders", "additionalAttributes", "product", "customer" },
        allowSetters = true
    )
    private Set<Article> articles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<ServiceOrderMaster> serviceOrderMasters = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "serviceOrder", "product" }, allowSetters = true)
    private Set<ServiceOrderSpare> serviceOrderSpares = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
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
    @JsonIgnoreProperties(value = { "products", "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "products", "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "products", "additionalAttributes" }, allowSetters = true)
    private Hsn hsn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public Product productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVendorProductId() {
        return this.vendorProductId;
    }

    public Product vendorProductId(String vendorProductId) {
        this.setVendorProductId(vendorProductId);
        return this;
    }

    public void setVendorProductId(String vendorProductId) {
        this.vendorProductId = vendorProductId;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return this.price;
    }

    public Product price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public Product productType(ProductType productType) {
        this.setProductType(productType);
        return this;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Product isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Product createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Product createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Product updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Product updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<ProductPriceHistory> getProductPriceHistories() {
        return this.productPriceHistories;
    }

    public void setProductPriceHistories(Set<ProductPriceHistory> productPriceHistories) {
        if (this.productPriceHistories != null) {
            this.productPriceHistories.forEach(i -> i.setProduct(null));
        }
        if (productPriceHistories != null) {
            productPriceHistories.forEach(i -> i.setProduct(this));
        }
        this.productPriceHistories = productPriceHistories;
    }

    public Product productPriceHistories(Set<ProductPriceHistory> productPriceHistories) {
        this.setProductPriceHistories(productPriceHistories);
        return this;
    }

    public Product addProductPriceHistory(ProductPriceHistory productPriceHistory) {
        this.productPriceHistories.add(productPriceHistory);
        productPriceHistory.setProduct(this);
        return this;
    }

    public Product removeProductPriceHistory(ProductPriceHistory productPriceHistory) {
        this.productPriceHistories.remove(productPriceHistory);
        productPriceHistory.setProduct(null);
        return this;
    }

    public Set<WarrantyMaster> getWarrantyMasters() {
        return this.warrantyMasters;
    }

    public void setWarrantyMasters(Set<WarrantyMaster> warrantyMasters) {
        if (this.warrantyMasters != null) {
            this.warrantyMasters.forEach(i -> i.setProduct(null));
        }
        if (warrantyMasters != null) {
            warrantyMasters.forEach(i -> i.setProduct(this));
        }
        this.warrantyMasters = warrantyMasters;
    }

    public Product warrantyMasters(Set<WarrantyMaster> warrantyMasters) {
        this.setWarrantyMasters(warrantyMasters);
        return this;
    }

    public Product addWarrantyMaster(WarrantyMaster warrantyMaster) {
        this.warrantyMasters.add(warrantyMaster);
        warrantyMaster.setProduct(this);
        return this;
    }

    public Product removeWarrantyMaster(WarrantyMaster warrantyMaster) {
        this.warrantyMasters.remove(warrantyMaster);
        warrantyMaster.setProduct(null);
        return this;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setProduct(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setProduct(this));
        }
        this.articles = articles;
    }

    public Product articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public Product addArticle(Article article) {
        this.articles.add(article);
        article.setProduct(this);
        return this;
    }

    public Product removeArticle(Article article) {
        this.articles.remove(article);
        article.setProduct(null);
        return this;
    }

    public Set<ServiceOrderMaster> getServiceOrderMasters() {
        return this.serviceOrderMasters;
    }

    public void setServiceOrderMasters(Set<ServiceOrderMaster> serviceOrderMasters) {
        if (this.serviceOrderMasters != null) {
            this.serviceOrderMasters.forEach(i -> i.setProduct(null));
        }
        if (serviceOrderMasters != null) {
            serviceOrderMasters.forEach(i -> i.setProduct(this));
        }
        this.serviceOrderMasters = serviceOrderMasters;
    }

    public Product serviceOrderMasters(Set<ServiceOrderMaster> serviceOrderMasters) {
        this.setServiceOrderMasters(serviceOrderMasters);
        return this;
    }

    public Product addServiceOrderMaster(ServiceOrderMaster serviceOrderMaster) {
        this.serviceOrderMasters.add(serviceOrderMaster);
        serviceOrderMaster.setProduct(this);
        return this;
    }

    public Product removeServiceOrderMaster(ServiceOrderMaster serviceOrderMaster) {
        this.serviceOrderMasters.remove(serviceOrderMaster);
        serviceOrderMaster.setProduct(null);
        return this;
    }

    public Set<ServiceOrderSpare> getServiceOrderSpares() {
        return this.serviceOrderSpares;
    }

    public void setServiceOrderSpares(Set<ServiceOrderSpare> serviceOrderSpares) {
        if (this.serviceOrderSpares != null) {
            this.serviceOrderSpares.forEach(i -> i.setProduct(null));
        }
        if (serviceOrderSpares != null) {
            serviceOrderSpares.forEach(i -> i.setProduct(this));
        }
        this.serviceOrderSpares = serviceOrderSpares;
    }

    public Product serviceOrderSpares(Set<ServiceOrderSpare> serviceOrderSpares) {
        this.setServiceOrderSpares(serviceOrderSpares);
        return this;
    }

    public Product addServiceOrderSpare(ServiceOrderSpare serviceOrderSpare) {
        this.serviceOrderSpares.add(serviceOrderSpare);
        serviceOrderSpare.setProduct(this);
        return this;
    }

    public Product removeServiceOrderSpare(ServiceOrderSpare serviceOrderSpare) {
        this.serviceOrderSpares.remove(serviceOrderSpare);
        serviceOrderSpare.setProduct(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setProduct(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setProduct(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public Product additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public Product addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setProduct(this);
        return this;
    }

    public Product removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setProduct(null);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Product brand(Brand brand) {
        this.setBrand(brand);
        return this;
    }

    public Hsn getHsn() {
        return this.hsn;
    }

    public void setHsn(Hsn hsn) {
        this.hsn = hsn;
    }

    public Product hsn(Hsn hsn) {
        this.setHsn(hsn);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", vendorProductId='" + getVendorProductId() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", productType='" + getProductType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
