package com.framasaas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.domain.enumeration.SoldBy;
import com.framasaas.domain.enumeration.WarrantyType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "sold_by")
    private SoldBy soldBy;

    @Column(name = "sold_by_user")
    private String soldByUser;

    @Column(name = "sold_date")
    private LocalDate soldDate;

    @Column(name = "is_validated")
    private Boolean isValidated;

    @Column(name = "validated_by")
    private String validatedBy;

    @Column(name = "validated_time")
    private Instant validatedTime;

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
    @JsonIgnoreProperties(
        value = { "additionalAttributes", "franchise", "article", "articleWarranty", "serviceOrder" },
        allowSetters = true
    )
    private Set<SupportingDocument> supportingDocuments = new HashSet<>();

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
        value = { "supportingDocuments", "articleWarrantyDetails", "serviceOrders", "additionalAttributes", "product", "customer" },
        allowSetters = true
    )
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "warrantyMasterPriceHistories", "articleWarrantyDetails", "additionalAttributes", "coveredSpares", "product" },
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

    public SoldBy getSoldBy() {
        return this.soldBy;
    }

    public ArticleWarrantyDetails soldBy(SoldBy soldBy) {
        this.setSoldBy(soldBy);
        return this;
    }

    public void setSoldBy(SoldBy soldBy) {
        this.soldBy = soldBy;
    }

    public String getSoldByUser() {
        return this.soldByUser;
    }

    public ArticleWarrantyDetails soldByUser(String soldByUser) {
        this.setSoldByUser(soldByUser);
        return this;
    }

    public void setSoldByUser(String soldByUser) {
        this.soldByUser = soldByUser;
    }

    public LocalDate getSoldDate() {
        return this.soldDate;
    }

    public ArticleWarrantyDetails soldDate(LocalDate soldDate) {
        this.setSoldDate(soldDate);
        return this;
    }

    public void setSoldDate(LocalDate soldDate) {
        this.soldDate = soldDate;
    }

    public Boolean getIsValidated() {
        return this.isValidated;
    }

    public ArticleWarrantyDetails isValidated(Boolean isValidated) {
        this.setIsValidated(isValidated);
        return this;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public String getValidatedBy() {
        return this.validatedBy;
    }

    public ArticleWarrantyDetails validatedBy(String validatedBy) {
        this.setValidatedBy(validatedBy);
        return this;
    }

    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }

    public Instant getValidatedTime() {
        return this.validatedTime;
    }

    public ArticleWarrantyDetails validatedTime(Instant validatedTime) {
        this.setValidatedTime(validatedTime);
        return this;
    }

    public void setValidatedTime(Instant validatedTime) {
        this.validatedTime = validatedTime;
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

    public Set<SupportingDocument> getSupportingDocuments() {
        return this.supportingDocuments;
    }

    public void setSupportingDocuments(Set<SupportingDocument> supportingDocuments) {
        if (this.supportingDocuments != null) {
            this.supportingDocuments.forEach(i -> i.setArticleWarranty(null));
        }
        if (supportingDocuments != null) {
            supportingDocuments.forEach(i -> i.setArticleWarranty(this));
        }
        this.supportingDocuments = supportingDocuments;
    }

    public ArticleWarrantyDetails supportingDocuments(Set<SupportingDocument> supportingDocuments) {
        this.setSupportingDocuments(supportingDocuments);
        return this;
    }

    public ArticleWarrantyDetails addSupportingDocument(SupportingDocument supportingDocument) {
        this.supportingDocuments.add(supportingDocument);
        supportingDocument.setArticleWarranty(this);
        return this;
    }

    public ArticleWarrantyDetails removeSupportingDocument(SupportingDocument supportingDocument) {
        this.supportingDocuments.remove(supportingDocument);
        supportingDocument.setArticleWarranty(null);
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
            ", soldBy='" + getSoldBy() + "'" +
            ", soldByUser='" + getSoldByUser() + "'" +
            ", soldDate='" + getSoldDate() + "'" +
            ", isValidated='" + getIsValidated() + "'" +
            ", validatedBy='" + getValidatedBy() + "'" +
            ", validatedTime='" + getValidatedTime() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
