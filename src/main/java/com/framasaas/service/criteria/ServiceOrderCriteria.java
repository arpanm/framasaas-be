package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.ServiceOrderStatus;
import com.framasaas.domain.enumeration.ServiceOrderType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.ServiceOrder} entity. This class is used
 * in {@link com.framasaas.web.rest.ServiceOrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ServiceOrderType
     */
    public static class ServiceOrderTypeFilter extends Filter<ServiceOrderType> {

        public ServiceOrderTypeFilter() {}

        public ServiceOrderTypeFilter(ServiceOrderTypeFilter filter) {
            super(filter);
        }

        @Override
        public ServiceOrderTypeFilter copy() {
            return new ServiceOrderTypeFilter(this);
        }
    }

    /**
     * Class for filtering ServiceOrderStatus
     */
    public static class ServiceOrderStatusFilter extends Filter<ServiceOrderStatus> {

        public ServiceOrderStatusFilter() {}

        public ServiceOrderStatusFilter(ServiceOrderStatusFilter filter) {
            super(filter);
        }

        @Override
        public ServiceOrderStatusFilter copy() {
            return new ServiceOrderStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private ServiceOrderTypeFilter orderType;

    private ServiceOrderStatusFilter orderStatus;

    private BooleanFilter inWarranty;

    private BooleanFilter isFree;

    private BooleanFilter isSpareNeeded;

    private InstantFilter confirmedTime;

    private InstantFilter closedTime;

    private FloatFilter serviceCharge;

    private FloatFilter tax;

    private FloatFilter totalSpareCharges;

    private FloatFilter totalSpareTax;

    private FloatFilter totalCharges;

    private FloatFilter totalPaymentDone;

    private StringFilter customerInvoicePath;

    private FloatFilter nps;

    private LongFilter priority;

    private BooleanFilter isActive;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter supportingDocumentId;

    private LongFilter serviceOrderFranchiseAssignmentId;

    private LongFilter serviceOrderSpareId;

    private LongFilter additionalAttributeId;

    private LongFilter customerId;

    private LongFilter serviceMasterId;

    private LongFilter articleId;

    private LongFilter addressId;

    private Boolean distinct;

    public ServiceOrderCriteria() {}

    public ServiceOrderCriteria(ServiceOrderCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.orderType = other.optionalOrderType().map(ServiceOrderTypeFilter::copy).orElse(null);
        this.orderStatus = other.optionalOrderStatus().map(ServiceOrderStatusFilter::copy).orElse(null);
        this.inWarranty = other.optionalInWarranty().map(BooleanFilter::copy).orElse(null);
        this.isFree = other.optionalIsFree().map(BooleanFilter::copy).orElse(null);
        this.isSpareNeeded = other.optionalIsSpareNeeded().map(BooleanFilter::copy).orElse(null);
        this.confirmedTime = other.optionalConfirmedTime().map(InstantFilter::copy).orElse(null);
        this.closedTime = other.optionalClosedTime().map(InstantFilter::copy).orElse(null);
        this.serviceCharge = other.optionalServiceCharge().map(FloatFilter::copy).orElse(null);
        this.tax = other.optionalTax().map(FloatFilter::copy).orElse(null);
        this.totalSpareCharges = other.optionalTotalSpareCharges().map(FloatFilter::copy).orElse(null);
        this.totalSpareTax = other.optionalTotalSpareTax().map(FloatFilter::copy).orElse(null);
        this.totalCharges = other.optionalTotalCharges().map(FloatFilter::copy).orElse(null);
        this.totalPaymentDone = other.optionalTotalPaymentDone().map(FloatFilter::copy).orElse(null);
        this.customerInvoicePath = other.optionalCustomerInvoicePath().map(StringFilter::copy).orElse(null);
        this.nps = other.optionalNps().map(FloatFilter::copy).orElse(null);
        this.priority = other.optionalPriority().map(LongFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.supportingDocumentId = other.optionalSupportingDocumentId().map(LongFilter::copy).orElse(null);
        this.serviceOrderFranchiseAssignmentId = other.optionalServiceOrderFranchiseAssignmentId().map(LongFilter::copy).orElse(null);
        this.serviceOrderSpareId = other.optionalServiceOrderSpareId().map(LongFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.customerId = other.optionalCustomerId().map(LongFilter::copy).orElse(null);
        this.serviceMasterId = other.optionalServiceMasterId().map(LongFilter::copy).orElse(null);
        this.articleId = other.optionalArticleId().map(LongFilter::copy).orElse(null);
        this.addressId = other.optionalAddressId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ServiceOrderCriteria copy() {
        return new ServiceOrderCriteria(this);
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

    public ServiceOrderTypeFilter getOrderType() {
        return orderType;
    }

    public Optional<ServiceOrderTypeFilter> optionalOrderType() {
        return Optional.ofNullable(orderType);
    }

    public ServiceOrderTypeFilter orderType() {
        if (orderType == null) {
            setOrderType(new ServiceOrderTypeFilter());
        }
        return orderType;
    }

    public void setOrderType(ServiceOrderTypeFilter orderType) {
        this.orderType = orderType;
    }

    public ServiceOrderStatusFilter getOrderStatus() {
        return orderStatus;
    }

    public Optional<ServiceOrderStatusFilter> optionalOrderStatus() {
        return Optional.ofNullable(orderStatus);
    }

    public ServiceOrderStatusFilter orderStatus() {
        if (orderStatus == null) {
            setOrderStatus(new ServiceOrderStatusFilter());
        }
        return orderStatus;
    }

    public void setOrderStatus(ServiceOrderStatusFilter orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BooleanFilter getInWarranty() {
        return inWarranty;
    }

    public Optional<BooleanFilter> optionalInWarranty() {
        return Optional.ofNullable(inWarranty);
    }

    public BooleanFilter inWarranty() {
        if (inWarranty == null) {
            setInWarranty(new BooleanFilter());
        }
        return inWarranty;
    }

    public void setInWarranty(BooleanFilter inWarranty) {
        this.inWarranty = inWarranty;
    }

    public BooleanFilter getIsFree() {
        return isFree;
    }

    public Optional<BooleanFilter> optionalIsFree() {
        return Optional.ofNullable(isFree);
    }

    public BooleanFilter isFree() {
        if (isFree == null) {
            setIsFree(new BooleanFilter());
        }
        return isFree;
    }

    public void setIsFree(BooleanFilter isFree) {
        this.isFree = isFree;
    }

    public BooleanFilter getIsSpareNeeded() {
        return isSpareNeeded;
    }

    public Optional<BooleanFilter> optionalIsSpareNeeded() {
        return Optional.ofNullable(isSpareNeeded);
    }

    public BooleanFilter isSpareNeeded() {
        if (isSpareNeeded == null) {
            setIsSpareNeeded(new BooleanFilter());
        }
        return isSpareNeeded;
    }

    public void setIsSpareNeeded(BooleanFilter isSpareNeeded) {
        this.isSpareNeeded = isSpareNeeded;
    }

    public InstantFilter getConfirmedTime() {
        return confirmedTime;
    }

    public Optional<InstantFilter> optionalConfirmedTime() {
        return Optional.ofNullable(confirmedTime);
    }

    public InstantFilter confirmedTime() {
        if (confirmedTime == null) {
            setConfirmedTime(new InstantFilter());
        }
        return confirmedTime;
    }

    public void setConfirmedTime(InstantFilter confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    public InstantFilter getClosedTime() {
        return closedTime;
    }

    public Optional<InstantFilter> optionalClosedTime() {
        return Optional.ofNullable(closedTime);
    }

    public InstantFilter closedTime() {
        if (closedTime == null) {
            setClosedTime(new InstantFilter());
        }
        return closedTime;
    }

    public void setClosedTime(InstantFilter closedTime) {
        this.closedTime = closedTime;
    }

    public FloatFilter getServiceCharge() {
        return serviceCharge;
    }

    public Optional<FloatFilter> optionalServiceCharge() {
        return Optional.ofNullable(serviceCharge);
    }

    public FloatFilter serviceCharge() {
        if (serviceCharge == null) {
            setServiceCharge(new FloatFilter());
        }
        return serviceCharge;
    }

    public void setServiceCharge(FloatFilter serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public FloatFilter getTax() {
        return tax;
    }

    public Optional<FloatFilter> optionalTax() {
        return Optional.ofNullable(tax);
    }

    public FloatFilter tax() {
        if (tax == null) {
            setTax(new FloatFilter());
        }
        return tax;
    }

    public void setTax(FloatFilter tax) {
        this.tax = tax;
    }

    public FloatFilter getTotalSpareCharges() {
        return totalSpareCharges;
    }

    public Optional<FloatFilter> optionalTotalSpareCharges() {
        return Optional.ofNullable(totalSpareCharges);
    }

    public FloatFilter totalSpareCharges() {
        if (totalSpareCharges == null) {
            setTotalSpareCharges(new FloatFilter());
        }
        return totalSpareCharges;
    }

    public void setTotalSpareCharges(FloatFilter totalSpareCharges) {
        this.totalSpareCharges = totalSpareCharges;
    }

    public FloatFilter getTotalSpareTax() {
        return totalSpareTax;
    }

    public Optional<FloatFilter> optionalTotalSpareTax() {
        return Optional.ofNullable(totalSpareTax);
    }

    public FloatFilter totalSpareTax() {
        if (totalSpareTax == null) {
            setTotalSpareTax(new FloatFilter());
        }
        return totalSpareTax;
    }

    public void setTotalSpareTax(FloatFilter totalSpareTax) {
        this.totalSpareTax = totalSpareTax;
    }

    public FloatFilter getTotalCharges() {
        return totalCharges;
    }

    public Optional<FloatFilter> optionalTotalCharges() {
        return Optional.ofNullable(totalCharges);
    }

    public FloatFilter totalCharges() {
        if (totalCharges == null) {
            setTotalCharges(new FloatFilter());
        }
        return totalCharges;
    }

    public void setTotalCharges(FloatFilter totalCharges) {
        this.totalCharges = totalCharges;
    }

    public FloatFilter getTotalPaymentDone() {
        return totalPaymentDone;
    }

    public Optional<FloatFilter> optionalTotalPaymentDone() {
        return Optional.ofNullable(totalPaymentDone);
    }

    public FloatFilter totalPaymentDone() {
        if (totalPaymentDone == null) {
            setTotalPaymentDone(new FloatFilter());
        }
        return totalPaymentDone;
    }

    public void setTotalPaymentDone(FloatFilter totalPaymentDone) {
        this.totalPaymentDone = totalPaymentDone;
    }

    public StringFilter getCustomerInvoicePath() {
        return customerInvoicePath;
    }

    public Optional<StringFilter> optionalCustomerInvoicePath() {
        return Optional.ofNullable(customerInvoicePath);
    }

    public StringFilter customerInvoicePath() {
        if (customerInvoicePath == null) {
            setCustomerInvoicePath(new StringFilter());
        }
        return customerInvoicePath;
    }

    public void setCustomerInvoicePath(StringFilter customerInvoicePath) {
        this.customerInvoicePath = customerInvoicePath;
    }

    public FloatFilter getNps() {
        return nps;
    }

    public Optional<FloatFilter> optionalNps() {
        return Optional.ofNullable(nps);
    }

    public FloatFilter nps() {
        if (nps == null) {
            setNps(new FloatFilter());
        }
        return nps;
    }

    public void setNps(FloatFilter nps) {
        this.nps = nps;
    }

    public LongFilter getPriority() {
        return priority;
    }

    public Optional<LongFilter> optionalPriority() {
        return Optional.ofNullable(priority);
    }

    public LongFilter priority() {
        if (priority == null) {
            setPriority(new LongFilter());
        }
        return priority;
    }

    public void setPriority(LongFilter priority) {
        this.priority = priority;
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

    public LongFilter getSupportingDocumentId() {
        return supportingDocumentId;
    }

    public Optional<LongFilter> optionalSupportingDocumentId() {
        return Optional.ofNullable(supportingDocumentId);
    }

    public LongFilter supportingDocumentId() {
        if (supportingDocumentId == null) {
            setSupportingDocumentId(new LongFilter());
        }
        return supportingDocumentId;
    }

    public void setSupportingDocumentId(LongFilter supportingDocumentId) {
        this.supportingDocumentId = supportingDocumentId;
    }

    public LongFilter getServiceOrderFranchiseAssignmentId() {
        return serviceOrderFranchiseAssignmentId;
    }

    public Optional<LongFilter> optionalServiceOrderFranchiseAssignmentId() {
        return Optional.ofNullable(serviceOrderFranchiseAssignmentId);
    }

    public LongFilter serviceOrderFranchiseAssignmentId() {
        if (serviceOrderFranchiseAssignmentId == null) {
            setServiceOrderFranchiseAssignmentId(new LongFilter());
        }
        return serviceOrderFranchiseAssignmentId;
    }

    public void setServiceOrderFranchiseAssignmentId(LongFilter serviceOrderFranchiseAssignmentId) {
        this.serviceOrderFranchiseAssignmentId = serviceOrderFranchiseAssignmentId;
    }

    public LongFilter getServiceOrderSpareId() {
        return serviceOrderSpareId;
    }

    public Optional<LongFilter> optionalServiceOrderSpareId() {
        return Optional.ofNullable(serviceOrderSpareId);
    }

    public LongFilter serviceOrderSpareId() {
        if (serviceOrderSpareId == null) {
            setServiceOrderSpareId(new LongFilter());
        }
        return serviceOrderSpareId;
    }

    public void setServiceOrderSpareId(LongFilter serviceOrderSpareId) {
        this.serviceOrderSpareId = serviceOrderSpareId;
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

    public LongFilter getCustomerId() {
        return customerId;
    }

    public Optional<LongFilter> optionalCustomerId() {
        return Optional.ofNullable(customerId);
    }

    public LongFilter customerId() {
        if (customerId == null) {
            setCustomerId(new LongFilter());
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getServiceMasterId() {
        return serviceMasterId;
    }

    public Optional<LongFilter> optionalServiceMasterId() {
        return Optional.ofNullable(serviceMasterId);
    }

    public LongFilter serviceMasterId() {
        if (serviceMasterId == null) {
            setServiceMasterId(new LongFilter());
        }
        return serviceMasterId;
    }

    public void setServiceMasterId(LongFilter serviceMasterId) {
        this.serviceMasterId = serviceMasterId;
    }

    public LongFilter getArticleId() {
        return articleId;
    }

    public Optional<LongFilter> optionalArticleId() {
        return Optional.ofNullable(articleId);
    }

    public LongFilter articleId() {
        if (articleId == null) {
            setArticleId(new LongFilter());
        }
        return articleId;
    }

    public void setArticleId(LongFilter articleId) {
        this.articleId = articleId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public Optional<LongFilter> optionalAddressId() {
        return Optional.ofNullable(addressId);
    }

    public LongFilter addressId() {
        if (addressId == null) {
            setAddressId(new LongFilter());
        }
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
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
        final ServiceOrderCriteria that = (ServiceOrderCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(orderType, that.orderType) &&
            Objects.equals(orderStatus, that.orderStatus) &&
            Objects.equals(inWarranty, that.inWarranty) &&
            Objects.equals(isFree, that.isFree) &&
            Objects.equals(isSpareNeeded, that.isSpareNeeded) &&
            Objects.equals(confirmedTime, that.confirmedTime) &&
            Objects.equals(closedTime, that.closedTime) &&
            Objects.equals(serviceCharge, that.serviceCharge) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(totalSpareCharges, that.totalSpareCharges) &&
            Objects.equals(totalSpareTax, that.totalSpareTax) &&
            Objects.equals(totalCharges, that.totalCharges) &&
            Objects.equals(totalPaymentDone, that.totalPaymentDone) &&
            Objects.equals(customerInvoicePath, that.customerInvoicePath) &&
            Objects.equals(nps, that.nps) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(supportingDocumentId, that.supportingDocumentId) &&
            Objects.equals(serviceOrderFranchiseAssignmentId, that.serviceOrderFranchiseAssignmentId) &&
            Objects.equals(serviceOrderSpareId, that.serviceOrderSpareId) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(serviceMasterId, that.serviceMasterId) &&
            Objects.equals(articleId, that.articleId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            description,
            orderType,
            orderStatus,
            inWarranty,
            isFree,
            isSpareNeeded,
            confirmedTime,
            closedTime,
            serviceCharge,
            tax,
            totalSpareCharges,
            totalSpareTax,
            totalCharges,
            totalPaymentDone,
            customerInvoicePath,
            nps,
            priority,
            isActive,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            supportingDocumentId,
            serviceOrderFranchiseAssignmentId,
            serviceOrderSpareId,
            additionalAttributeId,
            customerId,
            serviceMasterId,
            articleId,
            addressId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalOrderType().map(f -> "orderType=" + f + ", ").orElse("") +
            optionalOrderStatus().map(f -> "orderStatus=" + f + ", ").orElse("") +
            optionalInWarranty().map(f -> "inWarranty=" + f + ", ").orElse("") +
            optionalIsFree().map(f -> "isFree=" + f + ", ").orElse("") +
            optionalIsSpareNeeded().map(f -> "isSpareNeeded=" + f + ", ").orElse("") +
            optionalConfirmedTime().map(f -> "confirmedTime=" + f + ", ").orElse("") +
            optionalClosedTime().map(f -> "closedTime=" + f + ", ").orElse("") +
            optionalServiceCharge().map(f -> "serviceCharge=" + f + ", ").orElse("") +
            optionalTax().map(f -> "tax=" + f + ", ").orElse("") +
            optionalTotalSpareCharges().map(f -> "totalSpareCharges=" + f + ", ").orElse("") +
            optionalTotalSpareTax().map(f -> "totalSpareTax=" + f + ", ").orElse("") +
            optionalTotalCharges().map(f -> "totalCharges=" + f + ", ").orElse("") +
            optionalTotalPaymentDone().map(f -> "totalPaymentDone=" + f + ", ").orElse("") +
            optionalCustomerInvoicePath().map(f -> "customerInvoicePath=" + f + ", ").orElse("") +
            optionalNps().map(f -> "nps=" + f + ", ").orElse("") +
            optionalPriority().map(f -> "priority=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalSupportingDocumentId().map(f -> "supportingDocumentId=" + f + ", ").orElse("") +
            optionalServiceOrderFranchiseAssignmentId().map(f -> "serviceOrderFranchiseAssignmentId=" + f + ", ").orElse("") +
            optionalServiceOrderSpareId().map(f -> "serviceOrderSpareId=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalCustomerId().map(f -> "customerId=" + f + ", ").orElse("") +
            optionalServiceMasterId().map(f -> "serviceMasterId=" + f + ", ").orElse("") +
            optionalArticleId().map(f -> "articleId=" + f + ", ").orElse("") +
            optionalAddressId().map(f -> "addressId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
