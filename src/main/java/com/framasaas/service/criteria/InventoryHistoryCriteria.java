package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.InventoryChangeReason;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.InventoryHistory} entity. This class is used
 * in {@link com.framasaas.web.rest.InventoryHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventory-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InventoryHistoryCriteria implements Serializable, Criteria {

    /**
     * Class for filtering InventoryChangeReason
     */
    public static class InventoryChangeReasonFilter extends Filter<InventoryChangeReason> {

        public InventoryChangeReasonFilter() {}

        public InventoryChangeReasonFilter(InventoryChangeReasonFilter filter) {
            super(filter);
        }

        @Override
        public InventoryChangeReasonFilter copy() {
            return new InventoryChangeReasonFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter inventoryValue;

    private InventoryChangeReasonFilter reason;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private Boolean distinct;

    public InventoryHistoryCriteria() {}

    public InventoryHistoryCriteria(InventoryHistoryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.inventoryValue = other.optionalInventoryValue().map(LongFilter::copy).orElse(null);
        this.reason = other.optionalReason().map(InventoryChangeReasonFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public InventoryHistoryCriteria copy() {
        return new InventoryHistoryCriteria(this);
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

    public LongFilter getInventoryValue() {
        return inventoryValue;
    }

    public Optional<LongFilter> optionalInventoryValue() {
        return Optional.ofNullable(inventoryValue);
    }

    public LongFilter inventoryValue() {
        if (inventoryValue == null) {
            setInventoryValue(new LongFilter());
        }
        return inventoryValue;
    }

    public void setInventoryValue(LongFilter inventoryValue) {
        this.inventoryValue = inventoryValue;
    }

    public InventoryChangeReasonFilter getReason() {
        return reason;
    }

    public Optional<InventoryChangeReasonFilter> optionalReason() {
        return Optional.ofNullable(reason);
    }

    public InventoryChangeReasonFilter reason() {
        if (reason == null) {
            setReason(new InventoryChangeReasonFilter());
        }
        return reason;
    }

    public void setReason(InventoryChangeReasonFilter reason) {
        this.reason = reason;
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
        final InventoryHistoryCriteria that = (InventoryHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(inventoryValue, that.inventoryValue) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inventoryValue, reason, updatedBy, updatedTime, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryHistoryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInventoryValue().map(f -> "inventoryValue=" + f + ", ").orElse("") +
            optionalReason().map(f -> "reason=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
