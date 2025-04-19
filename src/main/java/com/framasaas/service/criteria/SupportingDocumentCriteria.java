package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.DocumentFormat;
import com.framasaas.domain.enumeration.DocumentType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.SupportingDocument} entity. This class is used
 * in {@link com.framasaas.web.rest.SupportingDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /supporting-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SupportingDocumentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DocumentType
     */
    public static class DocumentTypeFilter extends Filter<DocumentType> {

        public DocumentTypeFilter() {}

        public DocumentTypeFilter(DocumentTypeFilter filter) {
            super(filter);
        }

        @Override
        public DocumentTypeFilter copy() {
            return new DocumentTypeFilter(this);
        }
    }

    /**
     * Class for filtering DocumentFormat
     */
    public static class DocumentFormatFilter extends Filter<DocumentFormat> {

        public DocumentFormatFilter() {}

        public DocumentFormatFilter(DocumentFormatFilter filter) {
            super(filter);
        }

        @Override
        public DocumentFormatFilter copy() {
            return new DocumentFormatFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentName;

    private DocumentTypeFilter documentType;

    private DocumentFormatFilter documentFormat;

    private LongFilter documentSize;

    private StringFilter documentPath;

    private BooleanFilter isValidated;

    private StringFilter validatedBy;

    private InstantFilter validatedTime;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter additionalAttributeId;

    private LongFilter franchiseId;

    private LongFilter articleId;

    private LongFilter articleWarrantyId;

    private LongFilter serviceOrderId;

    private Boolean distinct;

    public SupportingDocumentCriteria() {}

    public SupportingDocumentCriteria(SupportingDocumentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.documentName = other.optionalDocumentName().map(StringFilter::copy).orElse(null);
        this.documentType = other.optionalDocumentType().map(DocumentTypeFilter::copy).orElse(null);
        this.documentFormat = other.optionalDocumentFormat().map(DocumentFormatFilter::copy).orElse(null);
        this.documentSize = other.optionalDocumentSize().map(LongFilter::copy).orElse(null);
        this.documentPath = other.optionalDocumentPath().map(StringFilter::copy).orElse(null);
        this.isValidated = other.optionalIsValidated().map(BooleanFilter::copy).orElse(null);
        this.validatedBy = other.optionalValidatedBy().map(StringFilter::copy).orElse(null);
        this.validatedTime = other.optionalValidatedTime().map(InstantFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.franchiseId = other.optionalFranchiseId().map(LongFilter::copy).orElse(null);
        this.articleId = other.optionalArticleId().map(LongFilter::copy).orElse(null);
        this.articleWarrantyId = other.optionalArticleWarrantyId().map(LongFilter::copy).orElse(null);
        this.serviceOrderId = other.optionalServiceOrderId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SupportingDocumentCriteria copy() {
        return new SupportingDocumentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDocumentName() {
        return documentName;
    }

    public Optional<StringFilter> optionalDocumentName() {
        return Optional.ofNullable(documentName);
    }

    public StringFilter documentName() {
        if (documentName == null) {
            setDocumentName(new StringFilter());
        }
        return documentName;
    }

    public void setDocumentName(StringFilter documentName) {
        this.documentName = documentName;
    }

    public DocumentTypeFilter getDocumentType() {
        return documentType;
    }

    public Optional<DocumentTypeFilter> optionalDocumentType() {
        return Optional.ofNullable(documentType);
    }

    public DocumentTypeFilter documentType() {
        if (documentType == null) {
            setDocumentType(new DocumentTypeFilter());
        }
        return documentType;
    }

    public void setDocumentType(DocumentTypeFilter documentType) {
        this.documentType = documentType;
    }

    public DocumentFormatFilter getDocumentFormat() {
        return documentFormat;
    }

    public Optional<DocumentFormatFilter> optionalDocumentFormat() {
        return Optional.ofNullable(documentFormat);
    }

    public DocumentFormatFilter documentFormat() {
        if (documentFormat == null) {
            setDocumentFormat(new DocumentFormatFilter());
        }
        return documentFormat;
    }

    public void setDocumentFormat(DocumentFormatFilter documentFormat) {
        this.documentFormat = documentFormat;
    }

    public LongFilter getDocumentSize() {
        return documentSize;
    }

    public Optional<LongFilter> optionalDocumentSize() {
        return Optional.ofNullable(documentSize);
    }

    public LongFilter documentSize() {
        if (documentSize == null) {
            setDocumentSize(new LongFilter());
        }
        return documentSize;
    }

    public void setDocumentSize(LongFilter documentSize) {
        this.documentSize = documentSize;
    }

    public StringFilter getDocumentPath() {
        return documentPath;
    }

    public Optional<StringFilter> optionalDocumentPath() {
        return Optional.ofNullable(documentPath);
    }

    public StringFilter documentPath() {
        if (documentPath == null) {
            setDocumentPath(new StringFilter());
        }
        return documentPath;
    }

    public void setDocumentPath(StringFilter documentPath) {
        this.documentPath = documentPath;
    }

    public BooleanFilter getIsValidated() {
        return isValidated;
    }

    public Optional<BooleanFilter> optionalIsValidated() {
        return Optional.ofNullable(isValidated);
    }

    public BooleanFilter isValidated() {
        if (isValidated == null) {
            setIsValidated(new BooleanFilter());
        }
        return isValidated;
    }

    public void setIsValidated(BooleanFilter isValidated) {
        this.isValidated = isValidated;
    }

    public StringFilter getValidatedBy() {
        return validatedBy;
    }

    public Optional<StringFilter> optionalValidatedBy() {
        return Optional.ofNullable(validatedBy);
    }

    public StringFilter validatedBy() {
        if (validatedBy == null) {
            setValidatedBy(new StringFilter());
        }
        return validatedBy;
    }

    public void setValidatedBy(StringFilter validatedBy) {
        this.validatedBy = validatedBy;
    }

    public InstantFilter getValidatedTime() {
        return validatedTime;
    }

    public Optional<InstantFilter> optionalValidatedTime() {
        return Optional.ofNullable(validatedTime);
    }

    public InstantFilter validatedTime() {
        if (validatedTime == null) {
            setValidatedTime(new InstantFilter());
        }
        return validatedTime;
    }

    public void setValidatedTime(InstantFilter validatedTime) {
        this.validatedTime = validatedTime;
    }

    public StringFilter getCreateddBy() {
        return createddBy;
    }

    public Optional<StringFilter> optionalCreateddBy() {
        return Optional.ofNullable(createddBy);
    }

    public StringFilter createddBy() {
        if (createddBy == null) {
            setCreateddBy(new StringFilter());
        }
        return createddBy;
    }

    public void setCreateddBy(StringFilter createddBy) {
        this.createddBy = createddBy;
    }

    public InstantFilter getCreatedTime() {
        return createdTime;
    }

    public Optional<InstantFilter> optionalCreatedTime() {
        return Optional.ofNullable(createdTime);
    }

    public InstantFilter createdTime() {
        if (createdTime == null) {
            setCreatedTime(new InstantFilter());
        }
        return createdTime;
    }

    public void setCreatedTime(InstantFilter createdTime) {
        this.createdTime = createdTime;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public Optional<StringFilter> optionalUpdatedBy() {
        return Optional.ofNullable(updatedBy);
    }

    public StringFilter updatedBy() {
        if (updatedBy == null) {
            setUpdatedBy(new StringFilter());
        }
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedTime() {
        return updatedTime;
    }

    public Optional<InstantFilter> optionalUpdatedTime() {
        return Optional.ofNullable(updatedTime);
    }

    public InstantFilter updatedTime() {
        if (updatedTime == null) {
            setUpdatedTime(new InstantFilter());
        }
        return updatedTime;
    }

    public void setUpdatedTime(InstantFilter updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LongFilter getAdditionalAttributeId() {
        return additionalAttributeId;
    }

    public Optional<LongFilter> optionalAdditionalAttributeId() {
        return Optional.ofNullable(additionalAttributeId);
    }

    public LongFilter additionalAttributeId() {
        if (additionalAttributeId == null) {
            setAdditionalAttributeId(new LongFilter());
        }
        return additionalAttributeId;
    }

    public void setAdditionalAttributeId(LongFilter additionalAttributeId) {
        this.additionalAttributeId = additionalAttributeId;
    }

    public LongFilter getFranchiseId() {
        return franchiseId;
    }

    public Optional<LongFilter> optionalFranchiseId() {
        return Optional.ofNullable(franchiseId);
    }

    public LongFilter franchiseId() {
        if (franchiseId == null) {
            setFranchiseId(new LongFilter());
        }
        return franchiseId;
    }

    public void setFranchiseId(LongFilter franchiseId) {
        this.franchiseId = franchiseId;
    }

    public LongFilter getArticleId() {
        return articleId;
    }

    public Optional<LongFilter> optionalArticleId() {
        return Optional.ofNullable(articleId);
    }

    public LongFilter articleId() {
        if (articleId == null) {
            setArticleId(new LongFilter());
        }
        return articleId;
    }

    public void setArticleId(LongFilter articleId) {
        this.articleId = articleId;
    }

    public LongFilter getArticleWarrantyId() {
        return articleWarrantyId;
    }

    public Optional<LongFilter> optionalArticleWarrantyId() {
        return Optional.ofNullable(articleWarrantyId);
    }

    public LongFilter articleWarrantyId() {
        if (articleWarrantyId == null) {
            setArticleWarrantyId(new LongFilter());
        }
        return articleWarrantyId;
    }

    public void setArticleWarrantyId(LongFilter articleWarrantyId) {
        this.articleWarrantyId = articleWarrantyId;
    }

    public LongFilter getServiceOrderId() {
        return serviceOrderId;
    }

    public Optional<LongFilter> optionalServiceOrderId() {
        return Optional.ofNullable(serviceOrderId);
    }

    public LongFilter serviceOrderId() {
        if (serviceOrderId == null) {
            setServiceOrderId(new LongFilter());
        }
        return serviceOrderId;
    }

    public void setServiceOrderId(LongFilter serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupportingDocumentCriteria that = (SupportingDocumentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(documentName, that.documentName) &&
            Objects.equals(documentType, that.documentType) &&
            Objects.equals(documentFormat, that.documentFormat) &&
            Objects.equals(documentSize, that.documentSize) &&
            Objects.equals(documentPath, that.documentPath) &&
            Objects.equals(isValidated, that.isValidated) &&
            Objects.equals(validatedBy, that.validatedBy) &&
            Objects.equals(validatedTime, that.validatedTime) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(franchiseId, that.franchiseId) &&
            Objects.equals(articleId, that.articleId) &&
            Objects.equals(articleWarrantyId, that.articleWarrantyId) &&
            Objects.equals(serviceOrderId, that.serviceOrderId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            documentName,
            documentType,
            documentFormat,
            documentSize,
            documentPath,
            isValidated,
            validatedBy,
            validatedTime,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            additionalAttributeId,
            franchiseId,
            articleId,
            articleWarrantyId,
            serviceOrderId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupportingDocumentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDocumentName().map(f -> "documentName=" + f + ", ").orElse("") +
            optionalDocumentType().map(f -> "documentType=" + f + ", ").orElse("") +
            optionalDocumentFormat().map(f -> "documentFormat=" + f + ", ").orElse("") +
            optionalDocumentSize().map(f -> "documentSize=" + f + ", ").orElse("") +
            optionalDocumentPath().map(f -> "documentPath=" + f + ", ").orElse("") +
            optionalIsValidated().map(f -> "isValidated=" + f + ", ").orElse("") +
            optionalValidatedBy().map(f -> "validatedBy=" + f + ", ").orElse("") +
            optionalValidatedTime().map(f -> "validatedTime=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalFranchiseId().map(f -> "franchiseId=" + f + ", ").orElse("") +
            optionalArticleId().map(f -> "articleId=" + f + ", ").orElse("") +
            optionalArticleWarrantyId().map(f -> "articleWarrantyId=" + f + ", ").orElse("") +
            optionalServiceOrderId().map(f -> "serviceOrderId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
