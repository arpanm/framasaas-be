package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.ModeOfPayment;
import com.framasaas.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.ServiceOrderPayment} entity. This class is used
 * in {@link com.framasaas.web.rest.ServiceOrderPaymentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-order-payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderPaymentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PaymentStatus
     */
    public static class PaymentStatusFilter extends Filter<PaymentStatus> {

        public PaymentStatusFilter() {}

        public PaymentStatusFilter(PaymentStatusFilter filter) {
            super(filter);
        }

        @Override
        public PaymentStatusFilter copy() {
            return new PaymentStatusFilter(this);
        }
    }

    /**
     * Class for filtering ModeOfPayment
     */
    public static class ModeOfPaymentFilter extends Filter<ModeOfPayment> {

        public ModeOfPaymentFilter() {}

        public ModeOfPaymentFilter(ModeOfPaymentFilter filter) {
            super(filter);
        }

        @Override
        public ModeOfPaymentFilter copy() {
            return new ModeOfPaymentFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter paymentLink;

    private PaymentStatusFilter paymentStatus;

    private ModeOfPaymentFilter mop;

    private StringFilter pgTxnId;

    private StringFilter pgTxnResponse;

    private InstantFilter pgTxnResponseTime;

    private StringFilter paymentInitiatedBy;

    private BooleanFilter isActive;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter additionalAttributeId;

    private Boolean distinct;

    public ServiceOrderPaymentCriteria() {}

    public ServiceOrderPaymentCriteria(ServiceOrderPaymentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.paymentLink = other.optionalPaymentLink().map(StringFilter::copy).orElse(null);
        this.paymentStatus = other.optionalPaymentStatus().map(PaymentStatusFilter::copy).orElse(null);
        this.mop = other.optionalMop().map(ModeOfPaymentFilter::copy).orElse(null);
        this.pgTxnId = other.optionalPgTxnId().map(StringFilter::copy).orElse(null);
        this.pgTxnResponse = other.optionalPgTxnResponse().map(StringFilter::copy).orElse(null);
        this.pgTxnResponseTime = other.optionalPgTxnResponseTime().map(InstantFilter::copy).orElse(null);
        this.paymentInitiatedBy = other.optionalPaymentInitiatedBy().map(StringFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ServiceOrderPaymentCriteria copy() {
        return new ServiceOrderPaymentCriteria(this);
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

    public StringFilter getPaymentLink() {
        return paymentLink;
    }

    public Optional<StringFilter> optionalPaymentLink() {
        return Optional.ofNullable(paymentLink);
    }

    public StringFilter paymentLink() {
        if (paymentLink == null) {
            setPaymentLink(new StringFilter());
        }
        return paymentLink;
    }

    public void setPaymentLink(StringFilter paymentLink) {
        this.paymentLink = paymentLink;
    }

    public PaymentStatusFilter getPaymentStatus() {
        return paymentStatus;
    }

    public Optional<PaymentStatusFilter> optionalPaymentStatus() {
        return Optional.ofNullable(paymentStatus);
    }

    public PaymentStatusFilter paymentStatus() {
        if (paymentStatus == null) {
            setPaymentStatus(new PaymentStatusFilter());
        }
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ModeOfPaymentFilter getMop() {
        return mop;
    }

    public Optional<ModeOfPaymentFilter> optionalMop() {
        return Optional.ofNullable(mop);
    }

    public ModeOfPaymentFilter mop() {
        if (mop == null) {
            setMop(new ModeOfPaymentFilter());
        }
        return mop;
    }

    public void setMop(ModeOfPaymentFilter mop) {
        this.mop = mop;
    }

    public StringFilter getPgTxnId() {
        return pgTxnId;
    }

    public Optional<StringFilter> optionalPgTxnId() {
        return Optional.ofNullable(pgTxnId);
    }

    public StringFilter pgTxnId() {
        if (pgTxnId == null) {
            setPgTxnId(new StringFilter());
        }
        return pgTxnId;
    }

    public void setPgTxnId(StringFilter pgTxnId) {
        this.pgTxnId = pgTxnId;
    }

    public StringFilter getPgTxnResponse() {
        return pgTxnResponse;
    }

    public Optional<StringFilter> optionalPgTxnResponse() {
        return Optional.ofNullable(pgTxnResponse);
    }

    public StringFilter pgTxnResponse() {
        if (pgTxnResponse == null) {
            setPgTxnResponse(new StringFilter());
        }
        return pgTxnResponse;
    }

    public void setPgTxnResponse(StringFilter pgTxnResponse) {
        this.pgTxnResponse = pgTxnResponse;
    }

    public InstantFilter getPgTxnResponseTime() {
        return pgTxnResponseTime;
    }

    public Optional<InstantFilter> optionalPgTxnResponseTime() {
        return Optional.ofNullable(pgTxnResponseTime);
    }

    public InstantFilter pgTxnResponseTime() {
        if (pgTxnResponseTime == null) {
            setPgTxnResponseTime(new InstantFilter());
        }
        return pgTxnResponseTime;
    }

    public void setPgTxnResponseTime(InstantFilter pgTxnResponseTime) {
        this.pgTxnResponseTime = pgTxnResponseTime;
    }

    public StringFilter getPaymentInitiatedBy() {
        return paymentInitiatedBy;
    }

    public Optional<StringFilter> optionalPaymentInitiatedBy() {
        return Optional.ofNullable(paymentInitiatedBy);
    }

    public StringFilter paymentInitiatedBy() {
        if (paymentInitiatedBy == null) {
            setPaymentInitiatedBy(new StringFilter());
        }
        return paymentInitiatedBy;
    }

    public void setPaymentInitiatedBy(StringFilter paymentInitiatedBy) {
        this.paymentInitiatedBy = paymentInitiatedBy;
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
        final ServiceOrderPaymentCriteria that = (ServiceOrderPaymentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(paymentLink, that.paymentLink) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(mop, that.mop) &&
            Objects.equals(pgTxnId, that.pgTxnId) &&
            Objects.equals(pgTxnResponse, that.pgTxnResponse) &&
            Objects.equals(pgTxnResponseTime, that.pgTxnResponseTime) &&
            Objects.equals(paymentInitiatedBy, that.paymentInitiatedBy) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            paymentLink,
            paymentStatus,
            mop,
            pgTxnId,
            pgTxnResponse,
            pgTxnResponseTime,
            paymentInitiatedBy,
            isActive,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            additionalAttributeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderPaymentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPaymentLink().map(f -> "paymentLink=" + f + ", ").orElse("") +
            optionalPaymentStatus().map(f -> "paymentStatus=" + f + ", ").orElse("") +
            optionalMop().map(f -> "mop=" + f + ", ").orElse("") +
            optionalPgTxnId().map(f -> "pgTxnId=" + f + ", ").orElse("") +
            optionalPgTxnResponse().map(f -> "pgTxnResponse=" + f + ", ").orElse("") +
            optionalPgTxnResponseTime().map(f -> "pgTxnResponseTime=" + f + ", ").orElse("") +
            optionalPaymentInitiatedBy().map(f -> "paymentInitiatedBy=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
