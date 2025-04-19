package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.DocumentFormat;
import com.framasaas.domain.enumeration.DocumentType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.SupportingDocument} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SupportingDocumentDTO implements Serializable {

    private Long id;

    @NotNull
    private String documentName;

    @NotNull
    private DocumentType documentType;

    @NotNull
    private DocumentFormat documentFormat;

    private Long documentSize;

    @NotNull
    private String documentPath;

    @NotNull
    private Boolean isValidated;

    @NotNull
    private String validatedBy;

    @NotNull
    private Instant validatedTime;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private FranchiseDTO franchise;

    private ArticleDTO article;

    private ArticleWarrantyDetailsDTO articleWarranty;

    private ServiceOrderDTO serviceOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public DocumentFormat getDocumentFormat() {
        return documentFormat;
    }

    public void setDocumentFormat(DocumentFormat documentFormat) {
        this.documentFormat = documentFormat;
    }

    public Long getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(Long documentSize) {
        this.documentSize = documentSize;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public String getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }

    public Instant getValidatedTime() {
        return validatedTime;
    }

    public void setValidatedTime(Instant validatedTime) {
        this.validatedTime = validatedTime;
    }

    public String getCreateddBy() {
        return createddBy;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public FranchiseDTO getFranchise() {
        return franchise;
    }

    public void setFranchise(FranchiseDTO franchise) {
        this.franchise = franchise;
    }

    public ArticleDTO getArticle() {
        return article;
    }

    public void setArticle(ArticleDTO article) {
        this.article = article;
    }

    public ArticleWarrantyDetailsDTO getArticleWarranty() {
        return articleWarranty;
    }

    public void setArticleWarranty(ArticleWarrantyDetailsDTO articleWarranty) {
        this.articleWarranty = articleWarranty;
    }

    public ServiceOrderDTO getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrderDTO serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupportingDocumentDTO)) {
            return false;
        }

        SupportingDocumentDTO supportingDocumentDTO = (SupportingDocumentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, supportingDocumentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupportingDocumentDTO{" +
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
            ", franchise=" + getFranchise() +
            ", article=" + getArticle() +
            ", articleWarranty=" + getArticleWarranty() +
            ", serviceOrder=" + getServiceOrder() +
            "}";
    }
}
