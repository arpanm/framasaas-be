package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.ServiceOrderAssignmentStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.ServiceOrderFieldAgentAssignment} entity. This class is used
 * in {@link com.framasaas.web.rest.ServiceOrderFieldAgentAssignmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-order-field-agent-assignments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderFieldAgentAssignmentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ServiceOrderAssignmentStatus
     */
    public static class ServiceOrderAssignmentStatusFilter extends Filter<ServiceOrderAssignmentStatus> {

        public ServiceOrderAssignmentStatusFilter() {}

        public ServiceOrderAssignmentStatusFilter(ServiceOrderAssignmentStatusFilter filter) {
            super(filter);
        }

        @Override
        public ServiceOrderAssignmentStatusFilter copy() {
            return new ServiceOrderAssignmentStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ServiceOrderAssignmentStatusFilter serviceOrderAssignmentStatus;

    private LongFilter nps;

    private BooleanFilter isActive;

    private InstantFilter assignedTime;

    private InstantFilter movedBackTime;

    private InstantFilter visitTime;

    private InstantFilter spareOrderTime;

    private InstantFilter spareDeliveryTime;

    private InstantFilter completedTime;

    private LongFilter completionOTP;

    private LongFilter cancellationOTP;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter additionalAttributeId;

    private Boolean distinct;

    public ServiceOrderFieldAgentAssignmentCriteria() {}

    public ServiceOrderFieldAgentAssignmentCriteria(ServiceOrderFieldAgentAssignmentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.serviceOrderAssignmentStatus = other
            .optionalServiceOrderAssignmentStatus()
            .map(ServiceOrderAssignmentStatusFilter::copy)
            .orElse(null);
        this.nps = other.optionalNps().map(LongFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.assignedTime = other.optionalAssignedTime().map(InstantFilter::copy).orElse(null);
        this.movedBackTime = other.optionalMovedBackTime().map(InstantFilter::copy).orElse(null);
        this.visitTime = other.optionalVisitTime().map(InstantFilter::copy).orElse(null);
        this.spareOrderTime = other.optionalSpareOrderTime().map(InstantFilter::copy).orElse(null);
        this.spareDeliveryTime = other.optionalSpareDeliveryTime().map(InstantFilter::copy).orElse(null);
        this.completedTime = other.optionalCompletedTime().map(InstantFilter::copy).orElse(null);
        this.completionOTP = other.optionalCompletionOTP().map(LongFilter::copy).orElse(null);
        this.cancellationOTP = other.optionalCancellationOTP().map(LongFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ServiceOrderFieldAgentAssignmentCriteria copy() {
        return new ServiceOrderFieldAgentAssignmentCriteria(this);
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

    public ServiceOrderAssignmentStatusFilter getServiceOrderAssignmentStatus() {
        return serviceOrderAssignmentStatus;
    }

    public Optional<ServiceOrderAssignmentStatusFilter> optionalServiceOrderAssignmentStatus() {
        return Optional.ofNullable(serviceOrderAssignmentStatus);
    }

    public ServiceOrderAssignmentStatusFilter serviceOrderAssignmentStatus() {
        if (serviceOrderAssignmentStatus == null) {
            setServiceOrderAssignmentStatus(new ServiceOrderAssignmentStatusFilter());
        }
        return serviceOrderAssignmentStatus;
    }

    public void setServiceOrderAssignmentStatus(ServiceOrderAssignmentStatusFilter serviceOrderAssignmentStatus) {
        this.serviceOrderAssignmentStatus = serviceOrderAssignmentStatus;
    }

    public LongFilter getNps() {
        return nps;
    }

    public Optional<LongFilter> optionalNps() {
        return Optional.ofNullable(nps);
    }

    public LongFilter nps() {
        if (nps == null) {
            setNps(new LongFilter());
        }
        return nps;
    }

    public void setNps(LongFilter nps) {
        this.nps = nps;
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

    public InstantFilter getAssignedTime() {
        return assignedTime;
    }

    public Optional<InstantFilter> optionalAssignedTime() {
        return Optional.ofNullable(assignedTime);
    }

    public InstantFilter assignedTime() {
        if (assignedTime == null) {
            setAssignedTime(new InstantFilter());
        }
        return assignedTime;
    }

    public void setAssignedTime(InstantFilter assignedTime) {
        this.assignedTime = assignedTime;
    }

    public InstantFilter getMovedBackTime() {
        return movedBackTime;
    }

    public Optional<InstantFilter> optionalMovedBackTime() {
        return Optional.ofNullable(movedBackTime);
    }

    public InstantFilter movedBackTime() {
        if (movedBackTime == null) {
            setMovedBackTime(new InstantFilter());
        }
        return movedBackTime;
    }

    public void setMovedBackTime(InstantFilter movedBackTime) {
        this.movedBackTime = movedBackTime;
    }

    public InstantFilter getVisitTime() {
        return visitTime;
    }

    public Optional<InstantFilter> optionalVisitTime() {
        return Optional.ofNullable(visitTime);
    }

    public InstantFilter visitTime() {
        if (visitTime == null) {
            setVisitTime(new InstantFilter());
        }
        return visitTime;
    }

    public void setVisitTime(InstantFilter visitTime) {
        this.visitTime = visitTime;
    }

    public InstantFilter getSpareOrderTime() {
        return spareOrderTime;
    }

    public Optional<InstantFilter> optionalSpareOrderTime() {
        return Optional.ofNullable(spareOrderTime);
    }

    public InstantFilter spareOrderTime() {
        if (spareOrderTime == null) {
            setSpareOrderTime(new InstantFilter());
        }
        return spareOrderTime;
    }

    public void setSpareOrderTime(InstantFilter spareOrderTime) {
        this.spareOrderTime = spareOrderTime;
    }

    public InstantFilter getSpareDeliveryTime() {
        return spareDeliveryTime;
    }

    public Optional<InstantFilter> optionalSpareDeliveryTime() {
        return Optional.ofNullable(spareDeliveryTime);
    }

    public InstantFilter spareDeliveryTime() {
        if (spareDeliveryTime == null) {
            setSpareDeliveryTime(new InstantFilter());
        }
        return spareDeliveryTime;
    }

    public void setSpareDeliveryTime(InstantFilter spareDeliveryTime) {
        this.spareDeliveryTime = spareDeliveryTime;
    }

    public InstantFilter getCompletedTime() {
        return completedTime;
    }

    public Optional<InstantFilter> optionalCompletedTime() {
        return Optional.ofNullable(completedTime);
    }

    public InstantFilter completedTime() {
        if (completedTime == null) {
            setCompletedTime(new InstantFilter());
        }
        return completedTime;
    }

    public void setCompletedTime(InstantFilter completedTime) {
        this.completedTime = completedTime;
    }

    public LongFilter getCompletionOTP() {
        return completionOTP;
    }

    public Optional<LongFilter> optionalCompletionOTP() {
        return Optional.ofNullable(completionOTP);
    }

    public LongFilter completionOTP() {
        if (completionOTP == null) {
            setCompletionOTP(new LongFilter());
        }
        return completionOTP;
    }

    public void setCompletionOTP(LongFilter completionOTP) {
        this.completionOTP = completionOTP;
    }

    public LongFilter getCancellationOTP() {
        return cancellationOTP;
    }

    public Optional<LongFilter> optionalCancellationOTP() {
        return Optional.ofNullable(cancellationOTP);
    }

    public LongFilter cancellationOTP() {
        if (cancellationOTP == null) {
            setCancellationOTP(new LongFilter());
        }
        return cancellationOTP;
    }

    public void setCancellationOTP(LongFilter cancellationOTP) {
        this.cancellationOTP = cancellationOTP;
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
        final ServiceOrderFieldAgentAssignmentCriteria that = (ServiceOrderFieldAgentAssignmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(serviceOrderAssignmentStatus, that.serviceOrderAssignmentStatus) &&
            Objects.equals(nps, that.nps) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(assignedTime, that.assignedTime) &&
            Objects.equals(movedBackTime, that.movedBackTime) &&
            Objects.equals(visitTime, that.visitTime) &&
            Objects.equals(spareOrderTime, that.spareOrderTime) &&
            Objects.equals(spareDeliveryTime, that.spareDeliveryTime) &&
            Objects.equals(completedTime, that.completedTime) &&
            Objects.equals(completionOTP, that.completionOTP) &&
            Objects.equals(cancellationOTP, that.cancellationOTP) &&
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
            serviceOrderAssignmentStatus,
            nps,
            isActive,
            assignedTime,
            movedBackTime,
            visitTime,
            spareOrderTime,
            spareDeliveryTime,
            completedTime,
            completionOTP,
            cancellationOTP,
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
        return "ServiceOrderFieldAgentAssignmentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalServiceOrderAssignmentStatus().map(f -> "serviceOrderAssignmentStatus=" + f + ", ").orElse("") +
            optionalNps().map(f -> "nps=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalAssignedTime().map(f -> "assignedTime=" + f + ", ").orElse("") +
            optionalMovedBackTime().map(f -> "movedBackTime=" + f + ", ").orElse("") +
            optionalVisitTime().map(f -> "visitTime=" + f + ", ").orElse("") +
            optionalSpareOrderTime().map(f -> "spareOrderTime=" + f + ", ").orElse("") +
            optionalSpareDeliveryTime().map(f -> "spareDeliveryTime=" + f + ", ").orElse("") +
            optionalCompletedTime().map(f -> "completedTime=" + f + ", ").orElse("") +
            optionalCompletionOTP().map(f -> "completionOTP=" + f + ", ").orElse("") +
            optionalCancellationOTP().map(f -> "cancellationOTP=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
