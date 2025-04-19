package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.PerformanceTag;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.FranchisePerformanceHistory} entity. This class is used
 * in {@link com.framasaas.web.rest.FranchisePerformanceHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /franchise-performance-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchisePerformanceHistoryCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PerformanceTag
     */
    public static class PerformanceTagFilter extends Filter<PerformanceTag> {

        public PerformanceTagFilter() {}

        public PerformanceTagFilter(PerformanceTagFilter filter) {
            super(filter);
        }

        @Override
        public PerformanceTagFilter copy() {
            return new PerformanceTagFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter performanceScore;

    private PerformanceTagFilter performanceTag;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private LongFilter additionalAttributeId;

    private LongFilter franchiseId;

    private Boolean distinct;

    public FranchisePerformanceHistoryCriteria() {}

    public FranchisePerformanceHistoryCriteria(FranchisePerformanceHistoryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.performanceScore = other.optionalPerformanceScore().map(FloatFilter::copy).orElse(null);
        this.performanceTag = other.optionalPerformanceTag().map(PerformanceTagFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.franchiseId = other.optionalFranchiseId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FranchisePerformanceHistoryCriteria copy() {
        return new FranchisePerformanceHistoryCriteria(this);
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

    public FloatFilter getPerformanceScore() {
        return performanceScore;
    }

    public Optional<FloatFilter> optionalPerformanceScore() {
        return Optional.ofNullable(performanceScore);
    }

    public FloatFilter performanceScore() {
        if (performanceScore == null) {
            setPerformanceScore(new FloatFilter());
        }
        return performanceScore;
    }

    public void setPerformanceScore(FloatFilter performanceScore) {
        this.performanceScore = performanceScore;
    }

    public PerformanceTagFilter getPerformanceTag() {
        return performanceTag;
    }

    public Optional<PerformanceTagFilter> optionalPerformanceTag() {
        return Optional.ofNullable(performanceTag);
    }

    public PerformanceTagFilter performanceTag() {
        if (performanceTag == null) {
            setPerformanceTag(new PerformanceTagFilter());
        }
        return performanceTag;
    }

    public void setPerformanceTag(PerformanceTagFilter performanceTag) {
        this.performanceTag = performanceTag;
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

    public LongFilter getFranchiseId() {
        return franchiseId;
    }

    public Optional<LongFilter> optionalFranchiseId() {
        return Optional.ofNullable(franchiseId);
    }

    public LongFilter franchiseId() {
        if (franchiseId == null) {
            setFranchiseId(new LongFilter());
        }
        return franchiseId;
    }

    public void setFranchiseId(LongFilter franchiseId) {
        this.franchiseId = franchiseId;
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
        final FranchisePerformanceHistoryCriteria that = (FranchisePerformanceHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(performanceScore, that.performanceScore) &&
            Objects.equals(performanceTag, that.performanceTag) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(franchiseId, that.franchiseId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            performanceScore,
            performanceTag,
            updatedBy,
            updatedTime,
            createddBy,
            createdTime,
            additionalAttributeId,
            franchiseId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchisePerformanceHistoryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPerformanceScore().map(f -> "performanceScore=" + f + ", ").orElse("") +
            optionalPerformanceTag().map(f -> "performanceTag=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalFranchiseId().map(f -> "franchiseId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
