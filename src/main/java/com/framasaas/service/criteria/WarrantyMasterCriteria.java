package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.WarrantyType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.WarrantyMaster} entity. This class is used
 * in {@link com.framasaas.web.rest.WarrantyMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /warranty-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarrantyMasterCriteria implements Serializable, Criteria {

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter vendorWarrantyMasterId;

    private WarrantyTypeFilter warrantyType;

    private StringFilter description;

    private FloatFilter price;

    private FloatFilter tax;

    private FloatFilter franchiseCommission;

    private FloatFilter franchiseTax;

    private LongFilter periodInMonths;

    private FloatFilter taxRate;

    private StringFilter coverage;

    private StringFilter exclusion;

    private BooleanFilter isActive;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter warrantyMasterPriceHistoryId;

    private LongFilter articleWarrantyDetailsId;

    private LongFilter additionalAttributeId;

    private LongFilter coveredSpareId;

    private LongFilter productId;

    private Boolean distinct;

    public WarrantyMasterCriteria() {}

    public WarrantyMasterCriteria(WarrantyMasterCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.vendorWarrantyMasterId = other.optionalVendorWarrantyMasterId().map(StringFilter::copy).orElse(null);
        this.warrantyType = other.optionalWarrantyType().map(WarrantyTypeFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(FloatFilter::copy).orElse(null);
        this.tax = other.optionalTax().map(FloatFilter::copy).orElse(null);
        this.franchiseCommission = other.optionalFranchiseCommission().map(FloatFilter::copy).orElse(null);
        this.franchiseTax = other.optionalFranchiseTax().map(FloatFilter::copy).orElse(null);
        this.periodInMonths = other.optionalPeriodInMonths().map(LongFilter::copy).orElse(null);
        this.taxRate = other.optionalTaxRate().map(FloatFilter::copy).orElse(null);
        this.coverage = other.optionalCoverage().map(StringFilter::copy).orElse(null);
        this.exclusion = other.optionalExclusion().map(StringFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.warrantyMasterPriceHistoryId = other.optionalWarrantyMasterPriceHistoryId().map(LongFilter::copy).orElse(null);
        this.articleWarrantyDetailsId = other.optionalArticleWarrantyDetailsId().map(LongFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.coveredSpareId = other.optionalCoveredSpareId().map(LongFilter::copy).orElse(null);
        this.productId = other.optionalProductId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public WarrantyMasterCriteria copy() {
        return new WarrantyMasterCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public LongFilter getPeriodInMonths() {
        return periodInMonths;
    }

    public Optional<LongFilter> optionalPeriodInMonths() {
        return Optional.ofNullable(periodInMonths);
    }

    public LongFilter periodInMonths() {
        if (periodInMonths == null) {
            setPeriodInMonths(new LongFilter());
        }
        return periodInMonths;
    }

    public void setPeriodInMonths(LongFilter periodInMonths) {
        this.periodInMonths = periodInMonths;
    }

    public FloatFilter getTaxRate() {
        return taxRate;
    }

    public Optional<FloatFilter> optionalTaxRate() {
        return Optional.ofNullable(taxRate);
    }

    public FloatFilter taxRate() {
        if (taxRate == null) {
            setTaxRate(new FloatFilter());
        }
        return taxRate;
    }

    public void setTaxRate(FloatFilter taxRate) {
        this.taxRate = taxRate;
    }

    public StringFilter getCoverage() {
        return coverage;
    }

    public Optional<StringFilter> optionalCoverage() {
        return Optional.ofNullable(coverage);
    }

    public StringFilter coverage() {
        if (coverage == null) {
            setCoverage(new StringFilter());
        }
        return coverage;
    }

    public void setCoverage(StringFilter coverage) {
        this.coverage = coverage;
    }

    public StringFilter getExclusion() {
        return exclusion;
    }

    public Optional<StringFilter> optionalExclusion() {
        return Optional.ofNullable(exclusion);
    }

    public StringFilter exclusion() {
        if (exclusion == null) {
            setExclusion(new StringFilter());
        }
        return exclusion;
    }

    public void setExclusion(StringFilter exclusion) {
        this.exclusion = exclusion;
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

    public LongFilter getWarrantyMasterPriceHistoryId() {
        return warrantyMasterPriceHistoryId;
    }

    public Optional<LongFilter> optionalWarrantyMasterPriceHistoryId() {
        return Optional.ofNullable(warrantyMasterPriceHistoryId);
    }

    public LongFilter warrantyMasterPriceHistoryId() {
        if (warrantyMasterPriceHistoryId == null) {
            setWarrantyMasterPriceHistoryId(new LongFilter());
        }
        return warrantyMasterPriceHistoryId;
    }

    public void setWarrantyMasterPriceHistoryId(LongFilter warrantyMasterPriceHistoryId) {
        this.warrantyMasterPriceHistoryId = warrantyMasterPriceHistoryId;
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

    public LongFilter getCoveredSpareId() {
        return coveredSpareId;
    }

    public Optional<LongFilter> optionalCoveredSpareId() {
        return Optional.ofNullable(coveredSpareId);
    }

    public LongFilter coveredSpareId() {
        if (coveredSpareId == null) {
            setCoveredSpareId(new LongFilter());
        }
        return coveredSpareId;
    }

    public void setCoveredSpareId(LongFilter coveredSpareId) {
        this.coveredSpareId = coveredSpareId;
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
        final WarrantyMasterCriteria that = (WarrantyMasterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(vendorWarrantyMasterId, that.vendorWarrantyMasterId) &&
            Objects.equals(warrantyType, that.warrantyType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(price, that.price) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(franchiseCommission, that.franchiseCommission) &&
            Objects.equals(franchiseTax, that.franchiseTax) &&
            Objects.equals(periodInMonths, that.periodInMonths) &&
            Objects.equals(taxRate, that.taxRate) &&
            Objects.equals(coverage, that.coverage) &&
            Objects.equals(exclusion, that.exclusion) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(warrantyMasterPriceHistoryId, that.warrantyMasterPriceHistoryId) &&
            Objects.equals(articleWarrantyDetailsId, that.articleWarrantyDetailsId) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(coveredSpareId, that.coveredSpareId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            vendorWarrantyMasterId,
            warrantyType,
            description,
            price,
            tax,
            franchiseCommission,
            franchiseTax,
            periodInMonths,
            taxRate,
            coverage,
            exclusion,
            isActive,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            warrantyMasterPriceHistoryId,
            articleWarrantyDetailsId,
            additionalAttributeId,
            coveredSpareId,
            productId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarrantyMasterCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalVendorWarrantyMasterId().map(f -> "vendorWarrantyMasterId=" + f + ", ").orElse("") +
            optionalWarrantyType().map(f -> "warrantyType=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalTax().map(f -> "tax=" + f + ", ").orElse("") +
            optionalFranchiseCommission().map(f -> "franchiseCommission=" + f + ", ").orElse("") +
            optionalFranchiseTax().map(f -> "franchiseTax=" + f + ", ").orElse("") +
            optionalPeriodInMonths().map(f -> "periodInMonths=" + f + ", ").orElse("") +
            optionalTaxRate().map(f -> "taxRate=" + f + ", ").orElse("") +
            optionalCoverage().map(f -> "coverage=" + f + ", ").orElse("") +
            optionalExclusion().map(f -> "exclusion=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalWarrantyMasterPriceHistoryId().map(f -> "warrantyMasterPriceHistoryId=" + f + ", ").orElse("") +
            optionalArticleWarrantyDetailsId().map(f -> "articleWarrantyDetailsId=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalCoveredSpareId().map(f -> "coveredSpareId=" + f + ", ").orElse("") +
            optionalProductId().map(f -> "productId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
