package com.framasaas.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.WarrantyMasterPriceHistory} entity. This class is used
 * in {@link com.framasaas.web.rest.WarrantyMasterPriceHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /warranty-master-price-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WarrantyMasterPriceHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter price;

    private FloatFilter tax;

    private FloatFilter franchiseCommission;

    private FloatFilter franchiseTax;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter additionalAttributeId;

    private LongFilter warrantyMasterId;

    private Boolean distinct;

    public WarrantyMasterPriceHistoryCriteria() {}

    public WarrantyMasterPriceHistoryCriteria(WarrantyMasterPriceHistoryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(FloatFilter::copy).orElse(null);
        this.tax = other.optionalTax().map(FloatFilter::copy).orElse(null);
        this.franchiseCommission = other.optionalFranchiseCommission().map(FloatFilter::copy).orElse(null);
        this.franchiseTax = other.optionalFranchiseTax().map(FloatFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.warrantyMasterId = other.optionalWarrantyMasterId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public WarrantyMasterPriceHistoryCriteria copy() {
        return new WarrantyMasterPriceHistoryCriteria(this);
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
        final WarrantyMasterPriceHistoryCriteria that = (WarrantyMasterPriceHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(franchiseCommission, that.franchiseCommission) &&
            Objects.equals(franchiseTax, that.franchiseTax) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(warrantyMasterId, that.warrantyMasterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            price,
            tax,
            franchiseCommission,
            franchiseTax,
            updatedBy,
            updatedTime,
            additionalAttributeId,
            warrantyMasterId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WarrantyMasterPriceHistoryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalTax().map(f -> "tax=" + f + ", ").orElse("") +
            optionalFranchiseCommission().map(f -> "franchiseCommission=" + f + ", ").orElse("") +
            optionalFranchiseTax().map(f -> "franchiseTax=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalWarrantyMasterId().map(f -> "warrantyMasterId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
