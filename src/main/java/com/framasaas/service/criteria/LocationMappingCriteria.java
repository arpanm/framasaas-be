package com.framasaas.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.LocationMapping} entity. This class is used
 * in {@link com.framasaas.web.rest.LocationMappingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /location-mappings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationMappingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter locationName;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter additionalAttributeId;

    private LongFilter franchiseRuleId;

    private LongFilter fieldAgentSkillRuleId;

    private Boolean distinct;

    public LocationMappingCriteria() {}

    public LocationMappingCriteria(LocationMappingCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.locationName = other.optionalLocationName().map(StringFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.franchiseRuleId = other.optionalFranchiseRuleId().map(LongFilter::copy).orElse(null);
        this.fieldAgentSkillRuleId = other.optionalFieldAgentSkillRuleId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public LocationMappingCriteria copy() {
        return new LocationMappingCriteria(this);
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

    public StringFilter getLocationName() {
        return locationName;
    }

    public Optional<StringFilter> optionalLocationName() {
        return Optional.ofNullable(locationName);
    }

    public StringFilter locationName() {
        if (locationName == null) {
            setLocationName(new StringFilter());
        }
        return locationName;
    }

    public void setLocationName(StringFilter locationName) {
        this.locationName = locationName;
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
        final LocationMappingCriteria that = (LocationMappingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(locationName, that.locationName) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
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
            locationName,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            additionalAttributeId,
            franchiseRuleId,
            fieldAgentSkillRuleId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationMappingCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLocationName().map(f -> "locationName=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalFranchiseRuleId().map(f -> "franchiseRuleId=" + f + ", ").orElse("") +
            optionalFieldAgentSkillRuleId().map(f -> "fieldAgentSkillRuleId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
