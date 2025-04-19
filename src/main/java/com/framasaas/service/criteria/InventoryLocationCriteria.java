package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.InventoryLocationType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.InventoryLocation} entity. This class is used
 * in {@link com.framasaas.web.rest.InventoryLocationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventory-locations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InventoryLocationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering InventoryLocationType
     */
    public static class InventoryLocationTypeFilter extends Filter<InventoryLocationType> {

        public InventoryLocationTypeFilter() {}

        public InventoryLocationTypeFilter(InventoryLocationTypeFilter filter) {
            super(filter);
        }

        @Override
        public InventoryLocationTypeFilter copy() {
            return new InventoryLocationTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private InventoryLocationTypeFilter locationType;

    private BooleanFilter isActive;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter inventoryId;

    private LongFilter additionalAttributeId;

    private Boolean distinct;

    public InventoryLocationCriteria() {}

    public InventoryLocationCriteria(InventoryLocationCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.locationType = other.optionalLocationType().map(InventoryLocationTypeFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.inventoryId = other.optionalInventoryId().map(LongFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public InventoryLocationCriteria copy() {
        return new InventoryLocationCriteria(this);
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

    public InventoryLocationTypeFilter getLocationType() {
        return locationType;
    }

    public Optional<InventoryLocationTypeFilter> optionalLocationType() {
        return Optional.ofNullable(locationType);
    }

    public InventoryLocationTypeFilter locationType() {
        if (locationType == null) {
            setLocationType(new InventoryLocationTypeFilter());
        }
        return locationType;
    }

    public void setLocationType(InventoryLocationTypeFilter locationType) {
        this.locationType = locationType;
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
        final InventoryLocationCriteria that = (InventoryLocationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(locationType, that.locationType) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(inventoryId, that.inventoryId) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            locationType,
            isActive,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            inventoryId,
            additionalAttributeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryLocationCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalLocationType().map(f -> "locationType=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalInventoryId().map(f -> "inventoryId=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
