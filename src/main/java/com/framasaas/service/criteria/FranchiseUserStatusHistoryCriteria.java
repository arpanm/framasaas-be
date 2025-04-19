package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.FranchiseUserStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.FranchiseUserStatusHistory} entity. This class is used
 * in {@link com.framasaas.web.rest.FranchiseUserStatusHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /franchise-user-status-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseUserStatusHistoryCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FranchiseUserStatus
     */
    public static class FranchiseUserStatusFilter extends Filter<FranchiseUserStatus> {

        public FranchiseUserStatusFilter() {}

        public FranchiseUserStatusFilter(FranchiseUserStatusFilter filter) {
            super(filter);
        }

        @Override
        public FranchiseUserStatusFilter copy() {
            return new FranchiseUserStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FranchiseUserStatusFilter userSatus;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter franchiseUserId;

    private Boolean distinct;

    public FranchiseUserStatusHistoryCriteria() {}

    public FranchiseUserStatusHistoryCriteria(FranchiseUserStatusHistoryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.userSatus = other.optionalUserSatus().map(FranchiseUserStatusFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.franchiseUserId = other.optionalFranchiseUserId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FranchiseUserStatusHistoryCriteria copy() {
        return new FranchiseUserStatusHistoryCriteria(this);
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

    public FranchiseUserStatusFilter getUserSatus() {
        return userSatus;
    }

    public Optional<FranchiseUserStatusFilter> optionalUserSatus() {
        return Optional.ofNullable(userSatus);
    }

    public FranchiseUserStatusFilter userSatus() {
        if (userSatus == null) {
            setUserSatus(new FranchiseUserStatusFilter());
        }
        return userSatus;
    }

    public void setUserSatus(FranchiseUserStatusFilter userSatus) {
        this.userSatus = userSatus;
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

    public LongFilter getFranchiseUserId() {
        return franchiseUserId;
    }

    public Optional<LongFilter> optionalFranchiseUserId() {
        return Optional.ofNullable(franchiseUserId);
    }

    public LongFilter franchiseUserId() {
        if (franchiseUserId == null) {
            setFranchiseUserId(new LongFilter());
        }
        return franchiseUserId;
    }

    public void setFranchiseUserId(LongFilter franchiseUserId) {
        this.franchiseUserId = franchiseUserId;
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
        final FranchiseUserStatusHistoryCriteria that = (FranchiseUserStatusHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userSatus, that.userSatus) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(franchiseUserId, that.franchiseUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userSatus, updatedBy, updatedTime, franchiseUserId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseUserStatusHistoryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalUserSatus().map(f -> "userSatus=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalFranchiseUserId().map(f -> "franchiseUserId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
