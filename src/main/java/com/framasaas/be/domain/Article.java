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
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "vendor_article_id")
    private String vendorArticleId;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "articleWarrantyDetailsDocuments", "additionalAttributes", "article", "warrantyMaster" },
        allowSetters = true
    )
    private Set<ArticleWarrantyDetails> articleWarrantyDetails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
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
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "productPriceHistories", "warrantyMasters", "articles", "additionalAttributes", "category", "brand", "hsn" },
        allowSetters = true
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses", "articles", "additionalAttributes" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Article id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public Article serialNo(String serialNo) {
        this.setSerialNo(serialNo);
        return this;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getVendorArticleId() {
        return this.vendorArticleId;
    }

    public Article vendorArticleId(String vendorArticleId) {
        this.setVendorArticleId(vendorArticleId);
        return this;
    }

    public void setVendorArticleId(String vendorArticleId) {
        this.vendorArticleId = vendorArticleId;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Article createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Article createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Article updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Article updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<ArticleWarrantyDetails> getArticleWarrantyDetails() {
        return this.articleWarrantyDetails;
    }

    public void setArticleWarrantyDetails(Set<ArticleWarrantyDetails> articleWarrantyDetails) {
        if (this.articleWarrantyDetails != null) {
            this.articleWarrantyDetails.forEach(i -> i.setArticle(null));
        }
        if (articleWarrantyDetails != null) {
            articleWarrantyDetails.forEach(i -> i.setArticle(this));
        }
        this.articleWarrantyDetails = articleWarrantyDetails;
    }

    public Article articleWarrantyDetails(Set<ArticleWarrantyDetails> articleWarrantyDetails) {
        this.setArticleWarrantyDetails(articleWarrantyDetails);
        return this;
    }

    public Article addArticleWarrantyDetails(ArticleWarrantyDetails articleWarrantyDetails) {
        this.articleWarrantyDetails.add(articleWarrantyDetails);
        articleWarrantyDetails.setArticle(this);
        return this;
    }

    public Article removeArticleWarrantyDetails(ArticleWarrantyDetails articleWarrantyDetails) {
        this.articleWarrantyDetails.remove(articleWarrantyDetails);
        articleWarrantyDetails.setArticle(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setArticle(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setArticle(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public Article additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public Article addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setArticle(this);
        return this;
    }

    public Article removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setArticle(null);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Article product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Article customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return getId() != null && getId().equals(((Article) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", serialNo='" + getSerialNo() + "'" +
            ", vendorArticleId='" + getVendorArticleId() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
