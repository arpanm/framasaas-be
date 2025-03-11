package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.WarrantyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ArticleWarrantyDetails.
 */
@Entity
@Table(name = "article_warranty_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleWarrantyDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "warranty_type")
    private WarrantyType warrantyType;

    @Column(name = "vendor_article_warranty_id")
    private String vendorArticleWarrantyId;

    @Column(name = "vendor_warranty_master_id")
    private String vendorWarrantyMasterId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "articleWarranty")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "additionalAttributes", "articleWarranty" }, allowSetters = true)
    private Set<ArticleWarrantyDetailsDocument> articleWarrantyDetailsDocuments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "articleWarranty")
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
    @JsonIgnoreProperties(value = { "articleWarrantyDetails", "additionalAttributes", "product", "customer" }, allowSetters = true)
    private Article article;

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

    public ArticleWarrantyDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WarrantyType getWarrantyType() {
        return this.warrantyType;
    }

    public ArticleWarrantyDetails warrantyType(WarrantyType warrantyType) {
        this.setWarrantyType(warrantyType);
        return this;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public String getVendorArticleWarrantyId() {
        return this.vendorArticleWarrantyId;
    }

    public ArticleWarrantyDetails vendorArticleWarrantyId(String vendorArticleWarrantyId) {
        this.setVendorArticleWarrantyId(vendorArticleWarrantyId);
        return this;
    }

    public void setVendorArticleWarrantyId(String vendorArticleWarrantyId) {
        this.vendorArticleWarrantyId = vendorArticleWarrantyId;
    }

    public String getVendorWarrantyMasterId() {
        return this.vendorWarrantyMasterId;
    }

    public ArticleWarrantyDetails vendorWarrantyMasterId(String vendorWarrantyMasterId) {
        this.setVendorWarrantyMasterId(vendorWarrantyMasterId);
        return this;
    }

    public void setVendorWarrantyMasterId(String vendorWarrantyMasterId) {
        this.vendorWarrantyMasterId = vendorWarrantyMasterId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public ArticleWarrantyDetails startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public ArticleWarrantyDetails endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ArticleWarrantyDetails isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public ArticleWarrantyDetails createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ArticleWarrantyDetails createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ArticleWarrantyDetails updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public ArticleWarrantyDetails updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<ArticleWarrantyDetailsDocument> getArticleWarrantyDetailsDocuments() {
        return this.articleWarrantyDetailsDocuments;
    }

    public void setArticleWarrantyDetailsDocuments(Set<ArticleWarrantyDetailsDocument> articleWarrantyDetailsDocuments) {
        if (this.articleWarrantyDetailsDocuments != null) {
            this.articleWarrantyDetailsDocuments.forEach(i -> i.setArticleWarranty(null));
        }
        if (articleWarrantyDetailsDocuments != null) {
            articleWarrantyDetailsDocuments.forEach(i -> i.setArticleWarranty(this));
        }
        this.articleWarrantyDetailsDocuments = articleWarrantyDetailsDocuments;
    }

    public ArticleWarrantyDetails articleWarrantyDetailsDocuments(Set<ArticleWarrantyDetailsDocument> articleWarrantyDetailsDocuments) {
        this.setArticleWarrantyDetailsDocuments(articleWarrantyDetailsDocuments);
        return this;
    }

    public ArticleWarrantyDetails addArticleWarrantyDetailsDocument(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument) {
        this.articleWarrantyDetailsDocuments.add(articleWarrantyDetailsDocument);
        articleWarrantyDetailsDocument.setArticleWarranty(this);
        return this;
    }

    public ArticleWarrantyDetails removeArticleWarrantyDetailsDocument(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument) {
        this.articleWarrantyDetailsDocuments.remove(articleWarrantyDetailsDocument);
        articleWarrantyDetailsDocument.setArticleWarranty(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setArticleWarranty(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setArticleWarranty(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public ArticleWarrantyDetails additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public ArticleWarrantyDetails addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setArticleWarranty(this);
        return this;
    }

    public ArticleWarrantyDetails removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setArticleWarranty(null);
        return this;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public ArticleWarrantyDetails article(Article article) {
        this.setArticle(article);
        return this;
    }

    public WarrantyMaster getWarrantyMaster() {
        return this.warrantyMaster;
    }

    public void setWarrantyMaster(WarrantyMaster warrantyMaster) {
        this.warrantyMaster = warrantyMaster;
    }

    public ArticleWarrantyDetails warrantyMaster(WarrantyMaster warrantyMaster) {
        this.setWarrantyMaster(warrantyMaster);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleWarrantyDetails)) {
            return false;
        }
        return getId() != null && getId().equals(((ArticleWarrantyDetails) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleWarrantyDetails{" +
            "id=" + getId() +
            ", warrantyType='" + getWarrantyType() + "'" +
            ", vendorArticleWarrantyId='" + getVendorArticleWarrantyId() + "'" +
            ", vendorWarrantyMasterId='" + getVendorWarrantyMasterId() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
