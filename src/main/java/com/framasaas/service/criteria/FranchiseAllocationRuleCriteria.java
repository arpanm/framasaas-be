package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.FranchiseAllocationRuleJoinType;
import com.framasaas.domain.enumeration.FranchiseAllocationRuleType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.FranchiseAllocationRule} entity. This class is used
 * in {@link com.framasaas.web.rest.FranchiseAllocationRuleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /franchise-allocation-rules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseAllocationRuleCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FranchiseAllocationRuleType
     */
    public static class FranchiseAllocationRuleTypeFilter extends Filter<FranchiseAllocationRuleType> {

        public FranchiseAllocationRuleTypeFilter() {}

        public FranchiseAllocationRuleTypeFilter(FranchiseAllocationRuleTypeFilter filter) {
            super(filter);
        }

        @Override
        public FranchiseAllocationRuleTypeFilter copy() {
            return new FranchiseAllocationRuleTypeFilter(this);
        }
    }

    /**
     * Class for filtering FranchiseAllocationRuleJoinType
     */
    public static class FranchiseAllocationRuleJoinTypeFilter extends Filter<FranchiseAllocationRuleJoinType> {

        public FranchiseAllocationRuleJoinTypeFilter() {}

        public FranchiseAllocationRuleJoinTypeFilter(FranchiseAllocationRuleJoinTypeFilter filter) {
            super(filter);
        }

        @Override
        public FranchiseAllocationRuleJoinTypeFilter copy() {
            return new FranchiseAllocationRuleJoinTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FranchiseAllocationRuleTypeFilter ruleType;

    private FranchiseAllocationRuleJoinTypeFilter joinType;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter brandId;

    private LongFilter categoryId;

    private LongFilter pincodeId;

    private LongFilter locationMappingId;

    private LongFilter languageMappingId;

    private LongFilter additionalAttributeId;

    private Boolean distinct;

    public FranchiseAllocationRuleCriteria() {}

    public FranchiseAllocationRuleCriteria(FranchiseAllocationRuleCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.ruleType = other.optionalRuleType().map(FranchiseAllocationRuleTypeFilter::copy).orElse(null);
        this.joinType = other.optionalJoinType().map(FranchiseAllocationRuleJoinTypeFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.brandId = other.optionalBrandId().map(LongFilter::copy).orElse(null);
        this.categoryId = other.optionalCategoryId().map(LongFilter::copy).orElse(null);
        this.pincodeId = other.optionalPincodeId().map(LongFilter::copy).orElse(null);
        this.locationMappingId = other.optionalLocationMappingId().map(LongFilter::copy).orElse(null);
        this.languageMappingId = other.optionalLanguageMappingId().map(LongFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FranchiseAllocationRuleCriteria copy() {
        return new FranchiseAllocationRuleCriteria(this);
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

    public FranchiseAllocationRuleTypeFilter getRuleType() {
        return ruleType;
    }

    public Optional<FranchiseAllocationRuleTypeFilter> optionalRuleType() {
        return Optional.ofNullable(ruleType);
    }

    public FranchiseAllocationRuleTypeFilter ruleType() {
        if (ruleType == null) {
            setRuleType(new FranchiseAllocationRuleTypeFilter());
        }
        return ruleType;
    }

    public void setRuleType(FranchiseAllocationRuleTypeFilter ruleType) {
        this.ruleType = ruleType;
    }

    public FranchiseAllocationRuleJoinTypeFilter getJoinType() {
        return joinType;
    }

    public Optional<FranchiseAllocationRuleJoinTypeFilter> optionalJoinType() {
        return Optional.ofNullable(joinType);
    }

    public FranchiseAllocationRuleJoinTypeFilter joinType() {
        if (joinType == null) {
            setJoinType(new FranchiseAllocationRuleJoinTypeFilter());
        }
        return joinType;
    }

    public void setJoinType(FranchiseAllocationRuleJoinTypeFilter joinType) {
        this.joinType = joinType;
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

    public LongFilter getPincodeId() {
        return pincodeId;
    }

    public Optional<LongFilter> optionalPincodeId() {
        return Optional.ofNullable(pincodeId);
    }

    public LongFilter pincodeId() {
        if (pincodeId == null) {
            setPincodeId(new LongFilter());
        }
        return pincodeId;
    }

    public void setPincodeId(LongFilter pincodeId) {
        this.pincodeId = pincodeId;
    }

    public LongFilter getLocationMappingId() {
        return locationMappingId;
    }

    public Optional<LongFilter> optionalLocationMappingId() {
        return Optional.ofNullable(locationMappingId);
    }

    public LongFilter locationMappingId() {
        if (locationMappingId == null) {
            setLocationMappingId(new LongFilter());
        }
        return locationMappingId;
    }

    public void setLocationMappingId(LongFilter locationMappingId) {
        this.locationMappingId = locationMappingId;
    }

    public LongFilter getLanguageMappingId() {
        return languageMappingId;
    }

    public Optional<LongFilter> optionalLanguageMappingId() {
        return Optional.ofNullable(languageMappingId);
    }

    public LongFilter languageMappingId() {
        if (languageMappingId == null) {
            setLanguageMappingId(new LongFilter());
        }
        return languageMappingId;
    }

    public void setLanguageMappingId(LongFilter languageMappingId) {
        this.languageMappingId = languageMappingId;
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
        final FranchiseAllocationRuleCriteria that = (FranchiseAllocationRuleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(ruleType, that.ruleType) &&
            Objects.equals(joinType, that.joinType) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(brandId, that.brandId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(pincodeId, that.pincodeId) &&
            Objects.equals(locationMappingId, that.locationMappingId) &&
            Objects.equals(languageMappingId, that.languageMappingId) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            ruleType,
            joinType,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            brandId,
            categoryId,
            pincodeId,
            locationMappingId,
            languageMappingId,
            additionalAttributeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseAllocationRuleCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRuleType().map(f -> "ruleType=" + f + ", ").orElse("") +
            optionalJoinType().map(f -> "joinType=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalBrandId().map(f -> "brandId=" + f + ", ").orElse("") +
            optionalCategoryId().map(f -> "categoryId=" + f + ", ").orElse("") +
            optionalPincodeId().map(f -> "pincodeId=" + f + ", ").orElse("") +
            optionalLocationMappingId().map(f -> "locationMappingId=" + f + ", ").orElse("") +
            optionalLanguageMappingId().map(f -> "languageMappingId=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
