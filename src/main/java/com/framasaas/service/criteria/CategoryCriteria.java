package com.framasaas.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.Category} entity. This class is used
 * in {@link com.framasaas.web.rest.CategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter categoryName;

    private StringFilter imagePath;

    private StringFilter vendorCategoryId;

    private StringFilter description;

    private BooleanFilter isActive;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter productId;

    private LongFilter additionalAttributeId;

    private LongFilter franchiseRuleId;

    private LongFilter fieldAgentSkillRuleId;

    private Boolean distinct;

    public CategoryCriteria() {}

    public CategoryCriteria(CategoryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.categoryName = other.optionalCategoryName().map(StringFilter::copy).orElse(null);
        this.imagePath = other.optionalImagePath().map(StringFilter::copy).orElse(null);
        this.vendorCategoryId = other.optionalVendorCategoryId().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.productId = other.optionalProductId().map(LongFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.franchiseRuleId = other.optionalFranchiseRuleId().map(LongFilter::copy).orElse(null);
        this.fieldAgentSkillRuleId = other.optionalFieldAgentSkillRuleId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CategoryCriteria copy() {
        return new CategoryCriteria(this);
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

    public StringFilter getCategoryName() {
        return categoryName;
    }

    public Optional<StringFilter> optionalCategoryName() {
        return Optional.ofNullable(categoryName);
    }

    public StringFilter categoryName() {
        if (categoryName == null) {
            setCategoryName(new StringFilter());
        }
        return categoryName;
    }

    public void setCategoryName(StringFilter categoryName) {
        this.categoryName = categoryName;
    }

    public StringFilter getImagePath() {
        return imagePath;
    }

    public Optional<StringFilter> optionalImagePath() {
        return Optional.ofNullable(imagePath);
    }

    public StringFilter imagePath() {
        if (imagePath == null) {
            setImagePath(new StringFilter());
        }
        return imagePath;
    }

    public void setImagePath(StringFilter imagePath) {
        this.imagePath = imagePath;
    }

    public StringFilter getVendorCategoryId() {
        return vendorCategoryId;
    }

    public Optional<StringFilter> optionalVendorCategoryId() {
        return Optional.ofNullable(vendorCategoryId);
    }

    public StringFilter vendorCategoryId() {
        if (vendorCategoryId == null) {
            setVendorCategoryId(new StringFilter());
        }
        return vendorCategoryId;
    }

    public void setVendorCategoryId(StringFilter vendorCategoryId) {
        this.vendorCategoryId = vendorCategoryId;
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

    public LongFilter getFranchiseRuleId() {
        return franchiseRuleId;
    }

    public Optional<LongFilter> optionalFranchiseRuleId() {
        return Optional.ofNullable(franchiseRuleId);
    }

    public LongFilter franchiseRuleId() {
        if (franchiseRuleId == null) {
            setFranchiseRuleId(new LongFilter());
        }
        return franchiseRuleId;
    }

    public void setFranchiseRuleId(LongFilter franchiseRuleId) {
        this.franchiseRuleId = franchiseRuleId;
    }

    public LongFilter getFieldAgentSkillRuleId() {
        return fieldAgentSkillRuleId;
    }

    public Optional<LongFilter> optionalFieldAgentSkillRuleId() {
        return Optional.ofNullable(fieldAgentSkillRuleId);
    }

    public LongFilter fieldAgentSkillRuleId() {
        if (fieldAgentSkillRuleId == null) {
            setFieldAgentSkillRuleId(new LongFilter());
        }
        return fieldAgentSkillRuleId;
    }

    public void setFieldAgentSkillRuleId(LongFilter fieldAgentSkillRuleId) {
        this.fieldAgentSkillRuleId = fieldAgentSkillRuleId;
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
        final CategoryCriteria that = (CategoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(categoryName, that.categoryName) &&
            Objects.equals(imagePath, that.imagePath) &&
            Objects.equals(vendorCategoryId, that.vendorCategoryId) &&
            Objects.equals(description, that.description) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(franchiseRuleId, that.franchiseRuleId) &&
            Objects.equals(fieldAgentSkillRuleId, that.fieldAgentSkillRuleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            categoryName,
            imagePath,
            vendorCategoryId,
            description,
            isActive,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            productId,
            additionalAttributeId,
            franchiseRuleId,
            fieldAgentSkillRuleId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCategoryName().map(f -> "categoryName=" + f + ", ").orElse("") +
            optionalImagePath().map(f -> "imagePath=" + f + ", ").orElse("") +
            optionalVendorCategoryId().map(f -> "vendorCategoryId=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalProductId().map(f -> "productId=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalFranchiseRuleId().map(f -> "franchiseRuleId=" + f + ", ").orElse("") +
            optionalFieldAgentSkillRuleId().map(f -> "fieldAgentSkillRuleId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
