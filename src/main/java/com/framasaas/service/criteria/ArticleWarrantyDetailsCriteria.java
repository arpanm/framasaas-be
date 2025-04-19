package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.SoldBy;
import com.framasaas.domain.enumeration.WarrantyType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.ArticleWarrantyDetails} entity. This class is used
 * in {@link com.framasaas.web.rest.ArticleWarrantyDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /article-warranty-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleWarrantyDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering WarrantyType
     */
    public static class WarrantyTypeFilter extends Filter<WarrantyType> {

        public WarrantyTypeFilter() {}

        public WarrantyTypeFilter(WarrantyTypeFilter filter) {
            super(filter);
        }

        @Override
        public WarrantyTypeFilter copy() {
            return new WarrantyTypeFilter(this);
        }
    }

    /**
     * Class for filtering SoldBy
     */
    public static class SoldByFilter extends Filter<SoldBy> {

        public SoldByFilter() {}

        public SoldByFilter(SoldByFilter filter) {
            super(filter);
        }

        @Override
        public SoldByFilter copy() {
            return new SoldByFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private WarrantyTypeFilter warrantyType;

    private StringFilter vendorArticleWarrantyId;

    private StringFilter vendorWarrantyMasterId;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private SoldByFilter soldBy;

    private StringFilter soldByUser;

    private LocalDateFilter soldDate;

    private BooleanFilter isValidated;

    private StringFilter validatedBy;

    private InstantFilter validatedTime;

    private BooleanFilter isActive;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter supportingDocumentId;

    private LongFilter additionalAttributeId;

    private LongFilter articleId;

    private LongFilter warrantyMasterId;

    private Boolean distinct;

    public ArticleWarrantyDetailsCriteria() {}

    public ArticleWarrantyDetailsCriteria(ArticleWarrantyDetailsCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.warrantyType = other.optionalWarrantyType().map(WarrantyTypeFilter::copy).orElse(null);
        this.vendorArticleWarrantyId = other.optionalVendorArticleWarrantyId().map(StringFilter::copy).orElse(null);
        this.vendorWarrantyMasterId = other.optionalVendorWarrantyMasterId().map(StringFilter::copy).orElse(null);
        this.startDate = other.optionalStartDate().map(LocalDateFilter::copy).orElse(null);
        this.endDate = other.optionalEndDate().map(LocalDateFilter::copy).orElse(null);
        this.soldBy = other.optionalSoldBy().map(SoldByFilter::copy).orElse(null);
        this.soldByUser = other.optionalSoldByUser().map(StringFilter::copy).orElse(null);
        this.soldDate = other.optionalSoldDate().map(LocalDateFilter::copy).orElse(null);
        this.isValidated = other.optionalIsValidated().map(BooleanFilter::copy).orElse(null);
        this.validatedBy = other.optionalValidatedBy().map(StringFilter::copy).orElse(null);
        this.validatedTime = other.optionalValidatedTime().map(InstantFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.supportingDocumentId = other.optionalSupportingDocumentId().map(LongFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.articleId = other.optionalArticleId().map(LongFilter::copy).orElse(null);
        this.warrantyMasterId = other.optionalWarrantyMasterId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ArticleWarrantyDetailsCriteria copy() {
        return new ArticleWarrantyDetailsCriteria(this);
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

    public WarrantyTypeFilter getWarrantyType() {
        return warrantyType;
    }

    public Optional<WarrantyTypeFilter> optionalWarrantyType() {
        return Optional.ofNullable(warrantyType);
    }

    public WarrantyTypeFilter warrantyType() {
        if (warrantyType == null) {
            setWarrantyType(new WarrantyTypeFilter());
        }
        return warrantyType;
    }

    public void setWarrantyType(WarrantyTypeFilter warrantyType) {
        this.warrantyType = warrantyType;
    }

    public StringFilter getVendorArticleWarrantyId() {
        return vendorArticleWarrantyId;
    }

    public Optional<StringFilter> optionalVendorArticleWarrantyId() {
        return Optional.ofNullable(vendorArticleWarrantyId);
    }

    public StringFilter vendorArticleWarrantyId() {
        if (vendorArticleWarrantyId == null) {
            setVendorArticleWarrantyId(new StringFilter());
        }
        return vendorArticleWarrantyId;
    }

    public void setVendorArticleWarrantyId(StringFilter vendorArticleWarrantyId) {
        this.vendorArticleWarrantyId = vendorArticleWarrantyId;
    }

    public StringFilter getVendorWarrantyMasterId() {
        return vendorWarrantyMasterId;
    }

    public Optional<StringFilter> optionalVendorWarrantyMasterId() {
        return Optional.ofNullable(vendorWarrantyMasterId);
    }

    public StringFilter vendorWarrantyMasterId() {
        if (vendorWarrantyMasterId == null) {
            setVendorWarrantyMasterId(new StringFilter());
        }
        return vendorWarrantyMasterId;
    }

    public void setVendorWarrantyMasterId(StringFilter vendorWarrantyMasterId) {
        this.vendorWarrantyMasterId = vendorWarrantyMasterId;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public Optional<LocalDateFilter> optionalStartDate() {
        return Optional.ofNullable(startDate);
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            setStartDate(new LocalDateFilter());
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public Optional<LocalDateFilter> optionalEndDate() {
        return Optional.ofNullable(endDate);
    }

    public LocalDateFilter endDate() {
        if (endDate == null) {
            setEndDate(new LocalDateFilter());
        }
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public SoldByFilter getSoldBy() {
        return soldBy;
    }

    public Optional<SoldByFilter> optionalSoldBy() {
        return Optional.ofNullable(soldBy);
    }

    public SoldByFilter soldBy() {
        if (soldBy == null) {
            setSoldBy(new SoldByFilter());
        }
        return soldBy;
    }

    public void setSoldBy(SoldByFilter soldBy) {
        this.soldBy = soldBy;
    }

    public StringFilter getSoldByUser() {
        return soldByUser;
    }

    public Optional<StringFilter> optionalSoldByUser() {
        return Optional.ofNullable(soldByUser);
    }

    public StringFilter soldByUser() {
        if (soldByUser == null) {
            setSoldByUser(new StringFilter());
        }
        return soldByUser;
    }

    public void setSoldByUser(StringFilter soldByUser) {
        this.soldByUser = soldByUser;
    }

    public LocalDateFilter getSoldDate() {
        return soldDate;
    }

    public Optional<LocalDateFilter> optionalSoldDate() {
        return Optional.ofNullable(soldDate);
    }

    public LocalDateFilter soldDate() {
        if (soldDate == null) {
            setSoldDate(new LocalDateFilter());
        }
        return soldDate;
    }

    public void setSoldDate(LocalDateFilter soldDate) {
        this.soldDate = soldDate;
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

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public Optional<BooleanFilter> optionalIsActive() {
        return Optional.ofNullable(isActive);
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            setIsActive(new BooleanFilter());
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
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

    public LongFilter getSupportingDocumentId() {
        return supportingDocumentId;
    }

    public Optional<LongFilter> optionalSupportingDocumentId() {
        return Optional.ofNullable(supportingDocumentId);
    }

    public LongFilter supportingDocumentId() {
        if (supportingDocumentId == null) {
            setSupportingDocumentId(new LongFilter());
        }
        return supportingDocumentId;
    }

    public void setSupportingDocumentId(LongFilter supportingDocumentId) {
        this.supportingDocumentId = supportingDocumentId;
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

    public LongFilter getWarrantyMasterId() {
        return warrantyMasterId;
    }

    public Optional<LongFilter> optionalWarrantyMasterId() {
        return Optional.ofNullable(warrantyMasterId);
    }

    public LongFilter warrantyMasterId() {
        if (warrantyMasterId == null) {
            setWarrantyMasterId(new LongFilter());
        }
        return warrantyMasterId;
    }

    public void setWarrantyMasterId(LongFilter warrantyMasterId) {
        this.warrantyMasterId = warrantyMasterId;
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
        final ArticleWarrantyDetailsCriteria that = (ArticleWarrantyDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(warrantyType, that.warrantyType) &&
            Objects.equals(vendorArticleWarrantyId, that.vendorArticleWarrantyId) &&
            Objects.equals(vendorWarrantyMasterId, that.vendorWarrantyMasterId) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(soldBy, that.soldBy) &&
            Objects.equals(soldByUser, that.soldByUser) &&
            Objects.equals(soldDate, that.soldDate) &&
            Objects.equals(isValidated, that.isValidated) &&
            Objects.equals(validatedBy, that.validatedBy) &&
            Objects.equals(validatedTime, that.validatedTime) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(supportingDocumentId, that.supportingDocumentId) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(articleId, that.articleId) &&
            Objects.equals(warrantyMasterId, that.warrantyMasterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            warrantyType,
            vendorArticleWarrantyId,
            vendorWarrantyMasterId,
            startDate,
            endDate,
            soldBy,
            soldByUser,
            soldDate,
            isValidated,
            validatedBy,
            validatedTime,
            isActive,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            supportingDocumentId,
            additionalAttributeId,
            articleId,
            warrantyMasterId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleWarrantyDetailsCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalWarrantyType().map(f -> "warrantyType=" + f + ", ").orElse("") +
            optionalVendorArticleWarrantyId().map(f -> "vendorArticleWarrantyId=" + f + ", ").orElse("") +
            optionalVendorWarrantyMasterId().map(f -> "vendorWarrantyMasterId=" + f + ", ").orElse("") +
            optionalStartDate().map(f -> "startDate=" + f + ", ").orElse("") +
            optionalEndDate().map(f -> "endDate=" + f + ", ").orElse("") +
            optionalSoldBy().map(f -> "soldBy=" + f + ", ").orElse("") +
            optionalSoldByUser().map(f -> "soldByUser=" + f + ", ").orElse("") +
            optionalSoldDate().map(f -> "soldDate=" + f + ", ").orElse("") +
            optionalIsValidated().map(f -> "isValidated=" + f + ", ").orElse("") +
            optionalValidatedBy().map(f -> "validatedBy=" + f + ", ").orElse("") +
            optionalValidatedTime().map(f -> "validatedTime=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalSupportingDocumentId().map(f -> "supportingDocumentId=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalArticleId().map(f -> "articleId=" + f + ", ").orElse("") +
            optionalWarrantyMasterId().map(f -> "warrantyMasterId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
