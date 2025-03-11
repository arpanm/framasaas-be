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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "additionalAttributes", "franchise" }, allowSetters = true)
    private Set<ProductPriceHistory> productPriceHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "articleWarrantyDetails", "additionalAttributes", "product", "customer" }, allowSetters = true)
    private Set<Article> articles = new HashSet<>();

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
            "article",
            "articleWarrantyDetails",
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "products", "additionalAttributes", "franchiseRule" }, allowSetters = true)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "products", "additionalAttributes", "franchiseRule" }, allowSetters = true)
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
            this.productPriceHistories.forEach(i -> i.setFranchise(null));
        }
        if (productPriceHistories != null) {
            productPriceHistories.forEach(i -> i.setFranchise(this));
        }
        this.productPriceHistories = productPriceHistories;
    }

    public Product productPriceHistories(Set<ProductPriceHistory> productPriceHistories) {
        this.setProductPriceHistories(productPriceHistories);
        return this;
    }

    public Product addProductPriceHistory(ProductPriceHistory productPriceHistory) {
        this.productPriceHistories.add(productPriceHistory);
        productPriceHistory.setFranchise(this);
        return this;
    }

    public Product removeProductPriceHistory(ProductPriceHistory productPriceHistory) {
        this.productPriceHistories.remove(productPriceHistory);
        productPriceHistory.setFranchise(null);
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
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
