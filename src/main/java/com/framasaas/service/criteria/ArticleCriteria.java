package com.framasaas.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.Article} entity. This class is used
 * in {@link com.framasaas.web.rest.ArticleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /articles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter serialNo;

    private StringFilter vendorArticleId;

    private LocalDateFilter purchaseDate;

    private FloatFilter puchasePrice;

    private StringFilter purchaseStore;

    private StringFilter invoicePath;

    private BooleanFilter isValidated;

    private StringFilter validatedBy;

    private InstantFilter validatedTime;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter supportingDocumentId;

    private LongFilter articleWarrantyDetailsId;

    private LongFilter serviceOrderId;

    private LongFilter additionalAttributeId;

    private LongFilter productId;

    private LongFilter customerId;

    private Boolean distinct;

    public ArticleCriteria() {}

    public ArticleCriteria(ArticleCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.serialNo = other.optionalSerialNo().map(StringFilter::copy).orElse(null);
        this.vendorArticleId = other.optionalVendorArticleId().map(StringFilter::copy).orElse(null);
        this.purchaseDate = other.optionalPurchaseDate().map(LocalDateFilter::copy).orElse(null);
        this.puchasePrice = other.optionalPuchasePrice().map(FloatFilter::copy).orElse(null);
        this.purchaseStore = other.optionalPurchaseStore().map(StringFilter::copy).orElse(null);
        this.invoicePath = other.optionalInvoicePath().map(StringFilter::copy).orElse(null);
        this.isValidated = other.optionalIsValidated().map(BooleanFilter::copy).orElse(null);
        this.validatedBy = other.optionalValidatedBy().map(StringFilter::copy).orElse(null);
        this.validatedTime = other.optionalValidatedTime().map(InstantFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.supportingDocumentId = other.optionalSupportingDocumentId().map(LongFilter::copy).orElse(null);
        this.articleWarrantyDetailsId = other.optionalArticleWarrantyDetailsId().map(LongFilter::copy).orElse(null);
        this.serviceOrderId = other.optionalServiceOrderId().map(LongFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.productId = other.optionalProductId().map(LongFilter::copy).orElse(null);
        this.customerId = other.optionalCustomerId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ArticleCriteria copy() {
        return new ArticleCriteria(this);
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

    public StringFilter getSerialNo() {
        return serialNo;
    }

    public Optional<StringFilter> optionalSerialNo() {
        return Optional.ofNullable(serialNo);
    }

    public StringFilter serialNo() {
        if (serialNo == null) {
            setSerialNo(new StringFilter());
        }
        return serialNo;
    }

    public void setSerialNo(StringFilter serialNo) {
        this.serialNo = serialNo;
    }

    public StringFilter getVendorArticleId() {
        return vendorArticleId;
    }

    public Optional<StringFilter> optionalVendorArticleId() {
        return Optional.ofNullable(vendorArticleId);
    }

    public StringFilter vendorArticleId() {
        if (vendorArticleId == null) {
            setVendorArticleId(new StringFilter());
        }
        return vendorArticleId;
    }

    public void setVendorArticleId(StringFilter vendorArticleId) {
        this.vendorArticleId = vendorArticleId;
    }

    public LocalDateFilter getPurchaseDate() {
        return purchaseDate;
    }

    public Optional<LocalDateFilter> optionalPurchaseDate() {
        return Optional.ofNullable(purchaseDate);
    }

    public LocalDateFilter purchaseDate() {
        if (purchaseDate == null) {
            setPurchaseDate(new LocalDateFilter());
        }
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateFilter purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public FloatFilter getPuchasePrice() {
        return puchasePrice;
    }

    public Optional<FloatFilter> optionalPuchasePrice() {
        return Optional.ofNullable(puchasePrice);
    }

    public FloatFilter puchasePrice() {
        if (puchasePrice == null) {
            setPuchasePrice(new FloatFilter());
        }
        return puchasePrice;
    }

    public void setPuchasePrice(FloatFilter puchasePrice) {
        this.puchasePrice = puchasePrice;
    }

    public StringFilter getPurchaseStore() {
        return purchaseStore;
    }

    public Optional<StringFilter> optionalPurchaseStore() {
        return Optional.ofNullable(purchaseStore);
    }

    public StringFilter purchaseStore() {
        if (purchaseStore == null) {
            setPurchaseStore(new StringFilter());
        }
        return purchaseStore;
    }

    public void setPurchaseStore(StringFilter purchaseStore) {
        this.purchaseStore = purchaseStore;
    }

    public StringFilter getInvoicePath() {
        return invoicePath;
    }

    public Optional<StringFilter> optionalInvoicePath() {
        return Optional.ofNullable(invoicePath);
    }

    public StringFilter invoicePath() {
        if (invoicePath == null) {
            setInvoicePath(new StringFilter());
        }
        return invoicePath;
    }

    public void setInvoicePath(StringFilter invoicePath) {
        this.invoicePath = invoicePath;
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

    public LongFilter getArticleWarrantyDetailsId() {
        return articleWarrantyDetailsId;
    }

    public Optional<LongFilter> optionalArticleWarrantyDetailsId() {
        return Optional.ofNullable(articleWarrantyDetailsId);
    }

    public LongFilter articleWarrantyDetailsId() {
        if (articleWarrantyDetailsId == null) {
            setArticleWarrantyDetailsId(new LongFilter());
        }
        return articleWarrantyDetailsId;
    }

    public void setArticleWarrantyDetailsId(LongFilter articleWarrantyDetailsId) {
        this.articleWarrantyDetailsId = articleWarrantyDetailsId;
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

    public LongFilter getProductId() {
        return productId;
    }

    public Optional<LongFilter> optionalProductId() {
        return Optional.ofNullable(productId);
    }

    public LongFilter productId() {
        if (productId == null) {
            setProductId(new LongFilter());
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public Optional<LongFilter> optionalCustomerId() {
        return Optional.ofNullable(customerId);
    }

    public LongFilter customerId() {
        if (customerId == null) {
            setCustomerId(new LongFilter());
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
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
        final ArticleCriteria that = (ArticleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(serialNo, that.serialNo) &&
            Objects.equals(vendorArticleId, that.vendorArticleId) &&
            Objects.equals(purchaseDate, that.purchaseDate) &&
            Objects.equals(puchasePrice, that.puchasePrice) &&
            Objects.equals(purchaseStore, that.purchaseStore) &&
            Objects.equals(invoicePath, that.invoicePath) &&
            Objects.equals(isValidated, that.isValidated) &&
            Objects.equals(validatedBy, that.validatedBy) &&
            Objects.equals(validatedTime, that.validatedTime) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(supportingDocumentId, that.supportingDocumentId) &&
            Objects.equals(articleWarrantyDetailsId, that.articleWarrantyDetailsId) &&
            Objects.equals(serviceOrderId, that.serviceOrderId) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            serialNo,
            vendorArticleId,
            purchaseDate,
            puchasePrice,
            purchaseStore,
            invoicePath,
            isValidated,
            validatedBy,
            validatedTime,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            supportingDocumentId,
            articleWarrantyDetailsId,
            serviceOrderId,
            additionalAttributeId,
            productId,
            customerId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSerialNo().map(f -> "serialNo=" + f + ", ").orElse("") +
            optionalVendorArticleId().map(f -> "vendorArticleId=" + f + ", ").orElse("") +
            optionalPurchaseDate().map(f -> "purchaseDate=" + f + ", ").orElse("") +
            optionalPuchasePrice().map(f -> "puchasePrice=" + f + ", ").orElse("") +
            optionalPurchaseStore().map(f -> "purchaseStore=" + f + ", ").orElse("") +
            optionalInvoicePath().map(f -> "invoicePath=" + f + ", ").orElse("") +
            optionalIsValidated().map(f -> "isValidated=" + f + ", ").orElse("") +
            optionalValidatedBy().map(f -> "validatedBy=" + f + ", ").orElse("") +
            optionalValidatedTime().map(f -> "validatedTime=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalSupportingDocumentId().map(f -> "supportingDocumentId=" + f + ", ").orElse("") +
            optionalArticleWarrantyDetailsId().map(f -> "articleWarrantyDetailsId=" + f + ", ").orElse("") +
            optionalServiceOrderId().map(f -> "serviceOrderId=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalProductId().map(f -> "productId=" + f + ", ").orElse("") +
            optionalCustomerId().map(f -> "customerId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
