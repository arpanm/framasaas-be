package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.FranchiseStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.FranchiseStatusHistory} entity. This class is used
 * in {@link com.framasaas.web.rest.FranchiseStatusHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /franchise-status-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseStatusHistoryCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FranchiseStatus
     */
    public static class FranchiseStatusFilter extends Filter<FranchiseStatus> {

        public FranchiseStatusFilter() {}

        public FranchiseStatusFilter(FranchiseStatusFilter filter) {
            super(filter);
        }

        @Override
        public FranchiseStatusFilter copy() {
            return new FranchiseStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FranchiseStatusFilter franchiseSatus;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter additionalAttributeId;

    private LongFilter franchiseId;

    private Boolean distinct;

    public FranchiseStatusHistoryCriteria() {}

    public FranchiseStatusHistoryCriteria(FranchiseStatusHistoryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.franchiseSatus = other.optionalFranchiseSatus().map(FranchiseStatusFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.franchiseId = other.optionalFranchiseId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FranchiseStatusHistoryCriteria copy() {
        return new FranchiseStatusHistoryCriteria(this);
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

    public FranchiseStatusFilter getFranchiseSatus() {
        return franchiseSatus;
    }

    public Optional<FranchiseStatusFilter> optionalFranchiseSatus() {
        return Optional.ofNullable(franchiseSatus);
    }

    public FranchiseStatusFilter franchiseSatus() {
        if (franchiseSatus == null) {
            setFranchiseSatus(new FranchiseStatusFilter());
        }
        return franchiseSatus;
    }

    public void setFranchiseSatus(FranchiseStatusFilter franchiseSatus) {
        this.franchiseSatus = franchiseSatus;
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
        final FranchiseStatusHistoryCriteria that = (FranchiseStatusHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(franchiseSatus, that.franchiseSatus) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(franchiseId, that.franchiseId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, franchiseSatus, updatedBy, updatedTime, additionalAttributeId, franchiseId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseStatusHistoryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFranchiseSatus().map(f -> "franchiseSatus=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalFranchiseId().map(f -> "franchiseId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
