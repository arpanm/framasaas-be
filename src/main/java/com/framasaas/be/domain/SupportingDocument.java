package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.DocumentFormat;
import com.framasaas.be.domain.enumeration.DocumentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SupportingDocument.
 */
@Entity
@Table(name = "supporting_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SupportingDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "document_name", nullable = false)
    private String documentName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_format", nullable = false)
    private DocumentFormat documentFormat;

    @Column(name = "document_size")
    private Long documentSize;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supportDocument")
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
            "address",
            "franchiseStatusHistories",
            "franchisePerformanceHistories",
            "supportingDocuments",
            "franchiseUsers",
            "serviceOrderFranchiseAssignments",
            "additionalAttributes",
            "ruleset",
            "brands",
            "categories",
        },
        allowSetters = true
    )
    private Franchise franchise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "supportingDocuments", "additionalAttributes", "article", "warrantyMaster" }, allowSetters = true)
    private ArticleWarrantyDetails articleWarranty;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SupportingDocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public SupportingDocument documentName(String documentName) {
        this.setDocumentName(documentName);
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public SupportingDocument documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public DocumentFormat getDocumentFormat() {
        return this.documentFormat;
    }

    public SupportingDocument documentFormat(DocumentFormat documentFormat) {
        this.setDocumentFormat(documentFormat);
        return this;
    }

    public void setDocumentFormat(DocumentFormat documentFormat) {
        this.documentFormat = documentFormat;
    }

    public Long getDocumentSize() {
        return this.documentSize;
    }

    public SupportingDocument documentSize(Long documentSize) {
        this.setDocumentSize(documentSize);
        return this;
    }

    public void setDocumentSize(Long documentSize) {
        this.documentSize = documentSize;
    }

    public String getDocumentPath() {
        return this.documentPath;
    }

    public SupportingDocument documentPath(String documentPath) {
        this.setDocumentPath(documentPath);
        return this;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Boolean getIsValidated() {
        return this.isValidated;
    }

    public SupportingDocument isValidated(Boolean isValidated) {
        this.setIsValidated(isValidated);
        return this;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public String getValidatedBy() {
        return this.validatedBy;
    }

    public SupportingDocument validatedBy(String validatedBy) {
        this.setValidatedBy(validatedBy);
        return this;
    }

    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }

    public Instant getValidatedTime() {
        return this.validatedTime;
    }

    public SupportingDocument validatedTime(Instant validatedTime) {
        this.setValidatedTime(validatedTime);
        return this;
    }

    public void setValidatedTime(Instant validatedTime) {
        this.validatedTime = validatedTime;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public SupportingDocument createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public SupportingDocument createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public SupportingDocument updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public SupportingDocument updatedTime(Instant updatedTime) {
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
            this.additionalAttributes.forEach(i -> i.setSupportDocument(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setSupportDocument(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public SupportingDocument additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public SupportingDocument addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setSupportDocument(this);
        return this;
    }

    public SupportingDocument removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setSupportDocument(null);
        return this;
    }

    public Franchise getFranchise() {
        return this.franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public SupportingDocument franchise(Franchise franchise) {
        this.setFranchise(franchise);
        return this;
    }

    public ArticleWarrantyDetails getArticleWarranty() {
        return this.articleWarranty;
    }

    public void setArticleWarranty(ArticleWarrantyDetails articleWarrantyDetails) {
        this.articleWarranty = articleWarrantyDetails;
    }

    public SupportingDocument articleWarranty(ArticleWarrantyDetails articleWarrantyDetails) {
        this.setArticleWarranty(articleWarrantyDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupportingDocument)) {
            return false;
        }
        return getId() != null && getId().equals(((SupportingDocument) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupportingDocument{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", documentFormat='" + getDocumentFormat() + "'" +
            ", documentSize=" + getDocumentSize() +
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
