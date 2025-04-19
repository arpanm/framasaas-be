package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.ServiceOrderAssignmentStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.ServiceOrderFranchiseAssignment} entity. This class is used
 * in {@link com.framasaas.web.rest.ServiceOrderFranchiseAssignmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-order-franchise-assignments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderFranchiseAssignmentCriteria implements Serializable, Criteria {

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

    private FloatFilter franchiseCommision;

    private FloatFilter franchiseCommisionTax;

    private StringFilter franchiseInvoicePath;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter additionalAttributeId;

    private LongFilter serviceOrderId;

    private LongFilter franchiseId;

    private Boolean distinct;

    public ServiceOrderFranchiseAssignmentCriteria() {}

    public ServiceOrderFranchiseAssignmentCriteria(ServiceOrderFranchiseAssignmentCriteria other) {
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
        this.franchiseCommision = other.optionalFranchiseCommision().map(FloatFilter::copy).orElse(null);
        this.franchiseCommisionTax = other.optionalFranchiseCommisionTax().map(FloatFilter::copy).orElse(null);
        this.franchiseInvoicePath = other.optionalFranchiseInvoicePath().map(StringFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.serviceOrderId = other.optionalServiceOrderId().map(LongFilter::copy).orElse(null);
        this.franchiseId = other.optionalFranchiseId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ServiceOrderFranchiseAssignmentCriteria copy() {
        return new ServiceOrderFranchiseAssignmentCriteria(this);
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

    public FloatFilter getFranchiseCommision() {
        return franchiseCommision;
    }

    public Optional<FloatFilter> optionalFranchiseCommision() {
        return Optional.ofNullable(franchiseCommision);
    }

    public FloatFilter franchiseCommision() {
        if (franchiseCommision == null) {
            setFranchiseCommision(new FloatFilter());
        }
        return franchiseCommision;
    }

    public void setFranchiseCommision(FloatFilter franchiseCommision) {
        this.franchiseCommision = franchiseCommision;
    }

    public FloatFilter getFranchiseCommisionTax() {
        return franchiseCommisionTax;
    }

    public Optional<FloatFilter> optionalFranchiseCommisionTax() {
        return Optional.ofNullable(franchiseCommisionTax);
    }

    public FloatFilter franchiseCommisionTax() {
        if (franchiseCommisionTax == null) {
            setFranchiseCommisionTax(new FloatFilter());
        }
        return franchiseCommisionTax;
    }

    public void setFranchiseCommisionTax(FloatFilter franchiseCommisionTax) {
        this.franchiseCommisionTax = franchiseCommisionTax;
    }

    public StringFilter getFranchiseInvoicePath() {
        return franchiseInvoicePath;
    }

    public Optional<StringFilter> optionalFranchiseInvoicePath() {
        return Optional.ofNullable(franchiseInvoicePath);
    }

    public StringFilter franchiseInvoicePath() {
        if (franchiseInvoicePath == null) {
            setFranchiseInvoicePath(new StringFilter());
        }
        return franchiseInvoicePath;
    }

    public void setFranchiseInvoicePath(StringFilter franchiseInvoicePath) {
        this.franchiseInvoicePath = franchiseInvoicePath;
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

    public LongFilter getServiceOrderId() {
        return serviceOrderId;
    }

    public Optional<LongFilter> optionalServiceOrderId() {
        return Optional.ofNullable(serviceOrderId);
    }

    public LongFilter serviceOrderId() {
        if (serviceOrderId == null) {
            setServiceOrderId(new LongFilter());
        }
        return serviceOrderId;
    }

    public void setServiceOrderId(LongFilter serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
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
        final ServiceOrderFranchiseAssignmentCriteria that = (ServiceOrderFranchiseAssignmentCriteria) o;
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
            Objects.equals(franchiseCommision, that.franchiseCommision) &&
            Objects.equals(franchiseCommisionTax, that.franchiseCommisionTax) &&
            Objects.equals(franchiseInvoicePath, that.franchiseInvoicePath) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(serviceOrderId, that.serviceOrderId) &&
            Objects.equals(franchiseId, that.franchiseId) &&
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
            franchiseCommision,
            franchiseCommisionTax,
            franchiseInvoicePath,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            additionalAttributeId,
            serviceOrderId,
            franchiseId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderFranchiseAssignmentCriteria{" +
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
            optionalFranchiseCommision().map(f -> "franchiseCommision=" + f + ", ").orElse("") +
            optionalFranchiseCommisionTax().map(f -> "franchiseCommisionTax=" + f + ", ").orElse("") +
            optionalFranchiseInvoicePath().map(f -> "franchiseInvoicePath=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalServiceOrderId().map(f -> "serviceOrderId=" + f + ", ").orElse("") +
            optionalFranchiseId().map(f -> "franchiseId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
