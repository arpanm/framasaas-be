package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.FieldAgentSkillRuleJoinType;
import com.framasaas.domain.enumeration.FieldAgentSkillRuleType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.FieldAgentSkillRule} entity. This class is used
 * in {@link com.framasaas.web.rest.FieldAgentSkillRuleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /field-agent-skill-rules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldAgentSkillRuleCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FieldAgentSkillRuleType
     */
    public static class FieldAgentSkillRuleTypeFilter extends Filter<FieldAgentSkillRuleType> {

        public FieldAgentSkillRuleTypeFilter() {}

        public FieldAgentSkillRuleTypeFilter(FieldAgentSkillRuleTypeFilter filter) {
            super(filter);
        }

        @Override
        public FieldAgentSkillRuleTypeFilter copy() {
            return new FieldAgentSkillRuleTypeFilter(this);
        }
    }

    /**
     * Class for filtering FieldAgentSkillRuleJoinType
     */
    public static class FieldAgentSkillRuleJoinTypeFilter extends Filter<FieldAgentSkillRuleJoinType> {

        public FieldAgentSkillRuleJoinTypeFilter() {}

        public FieldAgentSkillRuleJoinTypeFilter(FieldAgentSkillRuleJoinTypeFilter filter) {
            super(filter);
        }

        @Override
        public FieldAgentSkillRuleJoinTypeFilter copy() {
            return new FieldAgentSkillRuleJoinTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FieldAgentSkillRuleTypeFilter skillType;

    private FieldAgentSkillRuleJoinTypeFilter joinType;

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

    private LongFilter fieldAgentSkillRuleSetId;

    private Boolean distinct;

    public FieldAgentSkillRuleCriteria() {}

    public FieldAgentSkillRuleCriteria(FieldAgentSkillRuleCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.skillType = other.optionalSkillType().map(FieldAgentSkillRuleTypeFilter::copy).orElse(null);
        this.joinType = other.optionalJoinType().map(FieldAgentSkillRuleJoinTypeFilter::copy).orElse(null);
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
        this.fieldAgentSkillRuleSetId = other.optionalFieldAgentSkillRuleSetId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FieldAgentSkillRuleCriteria copy() {
        return new FieldAgentSkillRuleCriteria(this);
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

    public FieldAgentSkillRuleTypeFilter getSkillType() {
        return skillType;
    }

    public Optional<FieldAgentSkillRuleTypeFilter> optionalSkillType() {
        return Optional.ofNullable(skillType);
    }

    public FieldAgentSkillRuleTypeFilter skillType() {
        if (skillType == null) {
            setSkillType(new FieldAgentSkillRuleTypeFilter());
        }
        return skillType;
    }

    public void setSkillType(FieldAgentSkillRuleTypeFilter skillType) {
        this.skillType = skillType;
    }

    public FieldAgentSkillRuleJoinTypeFilter getJoinType() {
        return joinType;
    }

    public Optional<FieldAgentSkillRuleJoinTypeFilter> optionalJoinType() {
        return Optional.ofNullable(joinType);
    }

    public FieldAgentSkillRuleJoinTypeFilter joinType() {
        if (joinType == null) {
            setJoinType(new FieldAgentSkillRuleJoinTypeFilter());
        }
        return joinType;
    }

    public void setJoinType(FieldAgentSkillRuleJoinTypeFilter joinType) {
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

    public LongFilter getFieldAgentSkillRuleSetId() {
        return fieldAgentSkillRuleSetId;
    }

    public Optional<LongFilter> optionalFieldAgentSkillRuleSetId() {
        return Optional.ofNullable(fieldAgentSkillRuleSetId);
    }

    public LongFilter fieldAgentSkillRuleSetId() {
        if (fieldAgentSkillRuleSetId == null) {
            setFieldAgentSkillRuleSetId(new LongFilter());
        }
        return fieldAgentSkillRuleSetId;
    }

    public void setFieldAgentSkillRuleSetId(LongFilter fieldAgentSkillRuleSetId) {
        this.fieldAgentSkillRuleSetId = fieldAgentSkillRuleSetId;
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
        final FieldAgentSkillRuleCriteria that = (FieldAgentSkillRuleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(skillType, that.skillType) &&
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
            Objects.equals(fieldAgentSkillRuleSetId, that.fieldAgentSkillRuleSetId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            skillType,
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
            fieldAgentSkillRuleSetId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldAgentSkillRuleCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSkillType().map(f -> "skillType=" + f + ", ").orElse("") +
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
            optionalFieldAgentSkillRuleSetId().map(f -> "fieldAgentSkillRuleSetId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
