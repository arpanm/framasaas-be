package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.ProductType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.Product} entity. This class is used
 * in {@link com.framasaas.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ProductType
     */
    public static class ProductTypeFilter extends Filter<ProductType> {

        public ProductTypeFilter() {}

        public ProductTypeFilter(ProductTypeFilter filter) {
            super(filter);
        }

        @Override
        public ProductTypeFilter copy() {
            return new ProductTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productName;

    private StringFilter vendorProductId;

    private StringFilter description;

    private FloatFilter price;

    private FloatFilter tax;

    private FloatFilter franchiseCommission;

    private FloatFilter franchiseTax;

    private ProductTypeFilter productType;

    private BooleanFilter isActive;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter productPriceHistoryId;

    private LongFilter warrantyMasterId;

    private LongFilter articleId;

    private LongFilter serviceOrderMasterId;

    private LongFilter serviceOrderSpareId;

    private LongFilter inventoryId;

    private LongFilter additionalAttributeId;

    private LongFilter categoryId;

    private LongFilter brandId;

    private LongFilter hsnId;

    private LongFilter coveredUnderWarrantyId;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.productName = other.optionalProductName().map(StringFilter::copy).orElse(null);
        this.vendorProductId = other.optionalVendorProductId().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(FloatFilter::copy).orElse(null);
        this.tax = other.optionalTax().map(FloatFilter::copy).orElse(null);
        this.franchiseCommission = other.optionalFranchiseCommission().map(FloatFilter::copy).orElse(null);
        this.franchiseTax = other.optionalFranchiseTax().map(FloatFilter::copy).orElse(null);
        this.productType = other.optionalProductType().map(ProductTypeFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.productPriceHistoryId = other.optionalProductPriceHistoryId().map(LongFilter::copy).orElse(null);
        this.warrantyMasterId = other.optionalWarrantyMasterId().map(LongFilter::copy).orElse(null);
        this.articleId = other.optionalArticleId().map(LongFilter::copy).orElse(null);
        this.serviceOrderMasterId = other.optionalServiceOrderMasterId().map(LongFilter::copy).orElse(null);
        this.serviceOrderSpareId = other.optionalServiceOrderSpareId().map(LongFilter::copy).orElse(null);
        this.inventoryId = other.optionalInventoryId().map(LongFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.categoryId = other.optionalCategoryId().map(LongFilter::copy).orElse(null);
        this.brandId = other.optionalBrandId().map(LongFilter::copy).orElse(null);
        this.hsnId = other.optionalHsnId().map(LongFilter::copy).orElse(null);
        this.coveredUnderWarrantyId = other.optionalCoveredUnderWarrantyId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public StringFilter getProductName() {
        return productName;
    }

    public Optional<StringFilter> optionalProductName() {
        return Optional.ofNullable(productName);
    }

    public StringFilter productName() {
        if (productName == null) {
            setProductName(new StringFilter());
        }
        return productName;
    }

    public void setProductName(StringFilter productName) {
        this.productName = productName;
    }

    public StringFilter getVendorProductId() {
        return vendorProductId;
    }

    public Optional<StringFilter> optionalVendorProductId() {
        return Optional.ofNullable(vendorProductId);
    }

    public StringFilter vendorProductId() {
        if (vendorProductId == null) {
            setVendorProductId(new StringFilter());
        }
        return vendorProductId;
    }

    public void setVendorProductId(StringFilter vendorProductId) {
        this.vendorProductId = vendorProductId;
    }

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public Optional<FloatFilter> optionalPrice() {
        return Optional.ofNullable(price);
    }

    public FloatFilter price() {
        if (price == null) {
            setPrice(new FloatFilter());
        }
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public FloatFilter getTax() {
        return tax;
    }

    public Optional<FloatFilter> optionalTax() {
        return Optional.ofNullable(tax);
    }

    public FloatFilter tax() {
        if (tax == null) {
            setTax(new FloatFilter());
        }
        return tax;
    }

    public void setTax(FloatFilter tax) {
        this.tax = tax;
    }

    public FloatFilter getFranchiseCommission() {
        return franchiseCommission;
    }

    public Optional<FloatFilter> optionalFranchiseCommission() {
        return Optional.ofNullable(franchiseCommission);
    }

    public FloatFilter franchiseCommission() {
        if (franchiseCommission == null) {
            setFranchiseCommission(new FloatFilter());
        }
        return franchiseCommission;
    }

    public void setFranchiseCommission(FloatFilter franchiseCommission) {
        this.franchiseCommission = franchiseCommission;
    }

    public FloatFilter getFranchiseTax() {
        return franchiseTax;
    }

    public Optional<FloatFilter> optionalFranchiseTax() {
        return Optional.ofNullable(franchiseTax);
    }

    public FloatFilter franchiseTax() {
        if (franchiseTax == null) {
            setFranchiseTax(new FloatFilter());
        }
        return franchiseTax;
    }

    public void setFranchiseTax(FloatFilter franchiseTax) {
        this.franchiseTax = franchiseTax;
    }

    public ProductTypeFilter getProductType() {
        return productType;
    }

    public Optional<ProductTypeFilter> optionalProductType() {
        return Optional.ofNullable(productType);
    }

    public ProductTypeFilter productType() {
        if (productType == null) {
            setProductType(new ProductTypeFilter());
        }
        return productType;
    }

    public void setProductType(ProductTypeFilter productType) {
        this.productType = productType;
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

    public LongFilter getProductPriceHistoryId() {
        return productPriceHistoryId;
    }

    public Optional<LongFilter> optionalProductPriceHistoryId() {
        return Optional.ofNullable(productPriceHistoryId);
    }

    public LongFilter productPriceHistoryId() {
        if (productPriceHistoryId == null) {
            setProductPriceHistoryId(new LongFilter());
        }
        return productPriceHistoryId;
    }

    public void setProductPriceHistoryId(LongFilter productPriceHistoryId) {
        this.productPriceHistoryId = productPriceHistoryId;
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

    public LongFilter getServiceOrderMasterId() {
        return serviceOrderMasterId;
    }

    public Optional<LongFilter> optionalServiceOrderMasterId() {
        return Optional.ofNullable(serviceOrderMasterId);
    }

    public LongFilter serviceOrderMasterId() {
        if (serviceOrderMasterId == null) {
            setServiceOrderMasterId(new LongFilter());
        }
        return serviceOrderMasterId;
    }

    public void setServiceOrderMasterId(LongFilter serviceOrderMasterId) {
        this.serviceOrderMasterId = serviceOrderMasterId;
    }

    public LongFilter getServiceOrderSpareId() {
        return serviceOrderSpareId;
    }

    public Optional<LongFilter> optionalServiceOrderSpareId() {
        return Optional.ofNullable(serviceOrderSpareId);
    }

    public LongFilter serviceOrderSpareId() {
        if (serviceOrderSpareId == null) {
            setServiceOrderSpareId(new LongFilter());
        }
        return serviceOrderSpareId;
    }

    public void setServiceOrderSpareId(LongFilter serviceOrderSpareId) {
        this.serviceOrderSpareId = serviceOrderSpareId;
    }

    public LongFilter getInventoryId() {
        return inventoryId;
    }

    public Optional<LongFilter> optionalInventoryId() {
        return Optional.ofNullable(inventoryId);
    }

    public LongFilter inventoryId() {
        if (inventoryId == null) {
            setInventoryId(new LongFilter());
        }
        return inventoryId;
    }

    public void setInventoryId(LongFilter inventoryId) {
        this.inventoryId = inventoryId;
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

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public Optional<LongFilter> optionalCategoryId() {
        return Optional.ofNullable(categoryId);
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            setCategoryId(new LongFilter());
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getBrandId() {
        return brandId;
    }

    public Optional<LongFilter> optionalBrandId() {
        return Optional.ofNullable(brandId);
    }

    public LongFilter brandId() {
        if (brandId == null) {
            setBrandId(new LongFilter());
        }
        return brandId;
    }

    public void setBrandId(LongFilter brandId) {
        this.brandId = brandId;
    }

    public LongFilter getHsnId() {
        return hsnId;
    }

    public Optional<LongFilter> optionalHsnId() {
        return Optional.ofNullable(hsnId);
    }

    public LongFilter hsnId() {
        if (hsnId == null) {
            setHsnId(new LongFilter());
        }
        return hsnId;
    }

    public void setHsnId(LongFilter hsnId) {
        this.hsnId = hsnId;
    }

    public LongFilter getCoveredUnderWarrantyId() {
        return coveredUnderWarrantyId;
    }

    public Optional<LongFilter> optionalCoveredUnderWarrantyId() {
        return Optional.ofNullable(coveredUnderWarrantyId);
    }

    public LongFilter coveredUnderWarrantyId() {
        if (coveredUnderWarrantyId == null) {
            setCoveredUnderWarrantyId(new LongFilter());
        }
        return coveredUnderWarrantyId;
    }

    public void setCoveredUnderWarrantyId(LongFilter coveredUnderWarrantyId) {
        this.coveredUnderWarrantyId = coveredUnderWarrantyId;
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
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(productName, that.productName) &&
            Objects.equals(vendorProductId, that.vendorProductId) &&
            Objects.equals(description, that.description) &&
            Objects.equals(price, that.price) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(franchiseCommission, that.franchiseCommission) &&
            Objects.equals(franchiseTax, that.franchiseTax) &&
            Objects.equals(productType, that.productType) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(productPriceHistoryId, that.productPriceHistoryId) &&
            Objects.equals(warrantyMasterId, that.warrantyMasterId) &&
            Objects.equals(articleId, that.articleId) &&
            Objects.equals(serviceOrderMasterId, that.serviceOrderMasterId) &&
            Objects.equals(serviceOrderSpareId, that.serviceOrderSpareId) &&
            Objects.equals(inventoryId, that.inventoryId) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(brandId, that.brandId) &&
            Objects.equals(hsnId, that.hsnId) &&
            Objects.equals(coveredUnderWarrantyId, that.coveredUnderWarrantyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            productName,
            vendorProductId,
            description,
            price,
            tax,
            franchiseCommission,
            franchiseTax,
            productType,
            isActive,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            productPriceHistoryId,
            warrantyMasterId,
            articleId,
            serviceOrderMasterId,
            serviceOrderSpareId,
            inventoryId,
            additionalAttributeId,
            categoryId,
            brandId,
            hsnId,
            coveredUnderWarrantyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalProductName().map(f -> "productName=" + f + ", ").orElse("") +
            optionalVendorProductId().map(f -> "vendorProductId=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalTax().map(f -> "tax=" + f + ", ").orElse("") +
            optionalFranchiseCommission().map(f -> "franchiseCommission=" + f + ", ").orElse("") +
            optionalFranchiseTax().map(f -> "franchiseTax=" + f + ", ").orElse("") +
            optionalProductType().map(f -> "productType=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalProductPriceHistoryId().map(f -> "productPriceHistoryId=" + f + ", ").orElse("") +
            optionalWarrantyMasterId().map(f -> "warrantyMasterId=" + f + ", ").orElse("") +
            optionalArticleId().map(f -> "articleId=" + f + ", ").orElse("") +
            optionalServiceOrderMasterId().map(f -> "serviceOrderMasterId=" + f + ", ").orElse("") +
            optionalServiceOrderSpareId().map(f -> "serviceOrderSpareId=" + f + ", ").orElse("") +
            optionalInventoryId().map(f -> "inventoryId=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalCategoryId().map(f -> "categoryId=" + f + ", ").orElse("") +
            optionalBrandId().map(f -> "brandId=" + f + ", ").orElse("") +
            optionalHsnId().map(f -> "hsnId=" + f + ", ").orElse("") +
            optionalCoveredUnderWarrantyId().map(f -> "coveredUnderWarrantyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
