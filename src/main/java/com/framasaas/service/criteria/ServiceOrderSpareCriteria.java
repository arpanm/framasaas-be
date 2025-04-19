package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.InventoryLocationType;
import com.framasaas.domain.enumeration.ServiceOrderSpareStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.ServiceOrderSpare} entity. This class is used
 * in {@link com.framasaas.web.rest.ServiceOrderSpareResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-order-spares?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderSpareCriteria implements Serializable, Criteria {

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

    /**
     * Class for filtering ServiceOrderSpareStatus
     */
    public static class ServiceOrderSpareStatusFilter extends Filter<ServiceOrderSpareStatus> {

        public ServiceOrderSpareStatusFilter() {}

        public ServiceOrderSpareStatusFilter(ServiceOrderSpareStatusFilter filter) {
            super(filter);
        }

        @Override
        public ServiceOrderSpareStatusFilter copy() {
            return new ServiceOrderSpareStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter price;

    private FloatFilter tax;

    private FloatFilter totalCharge;

    private FloatFilter franchiseCommision;

    private FloatFilter franchiseCommisionTax;

    private InventoryLocationTypeFilter orderedFrom;

    private ServiceOrderSpareStatusFilter spareStatus;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter serviceOrderId;

    private LongFilter productId;

    private Boolean distinct;

    public ServiceOrderSpareCriteria() {}

    public ServiceOrderSpareCriteria(ServiceOrderSpareCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(FloatFilter::copy).orElse(null);
        this.tax = other.optionalTax().map(FloatFilter::copy).orElse(null);
        this.totalCharge = other.optionalTotalCharge().map(FloatFilter::copy).orElse(null);
        this.franchiseCommision = other.optionalFranchiseCommision().map(FloatFilter::copy).orElse(null);
        this.franchiseCommisionTax = other.optionalFranchiseCommisionTax().map(FloatFilter::copy).orElse(null);
        this.orderedFrom = other.optionalOrderedFrom().map(InventoryLocationTypeFilter::copy).orElse(null);
        this.spareStatus = other.optionalSpareStatus().map(ServiceOrderSpareStatusFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.serviceOrderId = other.optionalServiceOrderId().map(LongFilter::copy).orElse(null);
        this.productId = other.optionalProductId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ServiceOrderSpareCriteria copy() {
        return new ServiceOrderSpareCriteria(this);
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

    public FloatFilter getPrice() {
        return price;
    }

    public Optional<FloatFilter> optionalPrice() {
        return Optional.ofNullable(price);
    }

    public FloatFilter price() {
        if (price == null) {
            setPrice(new FloatFilter());
        }
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
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

    public FloatFilter getTotalCharge() {
        return totalCharge;
    }

    public Optional<FloatFilter> optionalTotalCharge() {
        return Optional.ofNullable(totalCharge);
    }

    public FloatFilter totalCharge() {
        if (totalCharge == null) {
            setTotalCharge(new FloatFilter());
        }
        return totalCharge;
    }

    public void setTotalCharge(FloatFilter totalCharge) {
        this.totalCharge = totalCharge;
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

    public InventoryLocationTypeFilter getOrderedFrom() {
        return orderedFrom;
    }

    public Optional<InventoryLocationTypeFilter> optionalOrderedFrom() {
        return Optional.ofNullable(orderedFrom);
    }

    public InventoryLocationTypeFilter orderedFrom() {
        if (orderedFrom == null) {
            setOrderedFrom(new InventoryLocationTypeFilter());
        }
        return orderedFrom;
    }

    public void setOrderedFrom(InventoryLocationTypeFilter orderedFrom) {
        this.orderedFrom = orderedFrom;
    }

    public ServiceOrderSpareStatusFilter getSpareStatus() {
        return spareStatus;
    }

    public Optional<ServiceOrderSpareStatusFilter> optionalSpareStatus() {
        return Optional.ofNullable(spareStatus);
    }

    public ServiceOrderSpareStatusFilter spareStatus() {
        if (spareStatus == null) {
            setSpareStatus(new ServiceOrderSpareStatusFilter());
        }
        return spareStatus;
    }

    public void setSpareStatus(ServiceOrderSpareStatusFilter spareStatus) {
        this.spareStatus = spareStatus;
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

    public LongFilter getProductId() {
        return productId;
    }

    public Optional<LongFilter> optionalProductId() {
        return Optional.ofNullable(productId);
    }

    public LongFilter productId() {
        if (productId == null) {
            setProductId(new LongFilter());
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
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
        final ServiceOrderSpareCriteria that = (ServiceOrderSpareCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(totalCharge, that.totalCharge) &&
            Objects.equals(franchiseCommision, that.franchiseCommision) &&
            Objects.equals(franchiseCommisionTax, that.franchiseCommisionTax) &&
            Objects.equals(orderedFrom, that.orderedFrom) &&
            Objects.equals(spareStatus, that.spareStatus) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(serviceOrderId, that.serviceOrderId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            price,
            tax,
            totalCharge,
            franchiseCommision,
            franchiseCommisionTax,
            orderedFrom,
            spareStatus,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            serviceOrderId,
            productId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOrderSpareCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalTax().map(f -> "tax=" + f + ", ").orElse("") +
            optionalTotalCharge().map(f -> "totalCharge=" + f + ", ").orElse("") +
            optionalFranchiseCommision().map(f -> "franchiseCommision=" + f + ", ").orElse("") +
            optionalFranchiseCommisionTax().map(f -> "franchiseCommisionTax=" + f + ", ").orElse("") +
            optionalOrderedFrom().map(f -> "orderedFrom=" + f + ", ").orElse("") +
            optionalSpareStatus().map(f -> "spareStatus=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalServiceOrderId().map(f -> "serviceOrderId=" + f + ", ").orElse("") +
            optionalProductId().map(f -> "productId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
