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
 * A ArticleWarrantyDetailsDocument.
 */
@Entity
@Table(name = "article_warranty_details_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleWarrantyDetailsDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "document_path", nullable = false)
    private String documentPath;

    @NotNull
    @Column(name = "is_validated", nullable = false)
    private Boolean isValidated;

    @NotNull
    @Column(name = "validated_by", nullable = false)
    private String validatedBy;

    @NotNull
    @Column(name = "validated_time", nullable = false)
    private Instant validatedTime;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "articleWarrantyDocument")
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
            "serviceOrderAssignment",
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "articleWarrantyDetailsDocuments", "additionalAttributes", "article", "warrantyMaster" },
        allowSetters = true
    )
    private ArticleWarrantyDetails articleWarranty;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArticleWarrantyDetailsDocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentPath() {
        return this.documentPath;
    }

    public ArticleWarrantyDetailsDocument documentPath(String documentPath) {
        this.setDocumentPath(documentPath);
        return this;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Boolean getIsValidated() {
        return this.isValidated;
    }

    public ArticleWarrantyDetailsDocument isValidated(Boolean isValidated) {
        this.setIsValidated(isValidated);
        return this;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public String getValidatedBy() {
        return this.validatedBy;
    }

    public ArticleWarrantyDetailsDocument validatedBy(String validatedBy) {
        this.setValidatedBy(validatedBy);
        return this;
    }

    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }

    public Instant getValidatedTime() {
        return this.validatedTime;
    }

    public ArticleWarrantyDetailsDocument validatedTime(Instant validatedTime) {
        this.setValidatedTime(validatedTime);
        return this;
    }

    public void setValidatedTime(Instant validatedTime) {
        this.validatedTime = validatedTime;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public ArticleWarrantyDetailsDocument createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ArticleWarrantyDetailsDocument createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public ArticleWarrantyDetailsDocument updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public ArticleWarrantyDetailsDocument updatedTime(Instant updatedTime) {
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
            this.additionalAttributes.forEach(i -> i.setArticleWarrantyDocument(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setArticleWarrantyDocument(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public ArticleWarrantyDetailsDocument additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public ArticleWarrantyDetailsDocument addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setArticleWarrantyDocument(this);
        return this;
    }

    public ArticleWarrantyDetailsDocument removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setArticleWarrantyDocument(null);
        return this;
    }

    public ArticleWarrantyDetails getArticleWarranty() {
        return this.articleWarranty;
    }

    public void setArticleWarranty(ArticleWarrantyDetails articleWarrantyDetails) {
        this.articleWarranty = articleWarrantyDetails;
    }

    public ArticleWarrantyDetailsDocument articleWarranty(ArticleWarrantyDetails articleWarrantyDetails) {
        this.setArticleWarranty(articleWarrantyDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleWarrantyDetailsDocument)) {
            return false;
        }
        return getId() != null && getId().equals(((ArticleWarrantyDetailsDocument) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleWarrantyDetailsDocument{" +
            "id=" + getId() +
            ", documentPath='" + getDocumentPath() + "'" +
            ", isValidated='" + getIsValidated() + "'" +
            ", validatedBy='" + getValidatedBy() + "'" +
            ", validatedTime='" + getValidatedTime() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
