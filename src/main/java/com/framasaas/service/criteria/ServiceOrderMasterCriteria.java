package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.ServiceOrderType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.ServiceOrderMaster} entity. This class is used
 * in {@link com.framasaas.web.rest.ServiceOrderMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-order-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceOrderMasterCriteria implements Serializable, Criteria {

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ServiceOrderTypeFilter serviceOrderType;

    private LongFilter slaInHours;

    private FloatFilter charge;

    private FloatFilter tax;

    private FloatFilter franchiseCommissionWithinSla;

    private FloatFilter franchiseTaxWithinSlaTax;

    private FloatFilter franchiseCommissionOutsideSla;

    private FloatFilter franchiseTaxOutsideSlaTax;

    private BooleanFilter isActive;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter serviceOrderId;

    private LongFilter productId;

    private Boolean distinct;

    public ServiceOrderMasterCriteria() {}

    public ServiceOrderMasterCriteria(ServiceOrderMasterCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.serviceOrderType = other.optionalServiceOrderType().map(ServiceOrderTypeFilter::copy).orElse(null);
        this.slaInHours = other.optionalSlaInHours().map(LongFilter::copy).orElse(null);
        this.charge = other.optionalCharge().map(FloatFilter::copy).orElse(null);
        this.tax = other.optionalTax().map(FloatFilter::copy).orElse(null);
        this.franchiseCommissionWithinSla = other.optionalFranchiseCommissionWithinSla().map(FloatFilter::copy).orElse(null);
        this.franchiseTaxWithinSlaTax = other.optionalFranchiseTaxWithinSlaTax().map(FloatFilter::copy).orElse(null);
        this.franchiseCommissionOutsideSla = other.optionalFranchiseCommissionOutsideSla().map(FloatFilter::copy).orElse(null);
        this.franchiseTaxOutsideSlaTax = other.optionalFranchiseTaxOutsideSlaTax().map(FloatFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.serviceOrderId = other.optionalServiceOrderId().map(LongFilter::copy).orElse(null);
        this.productId = other.optionalProductId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ServiceOrderMasterCriteria copy() {
        return new ServiceOrderMasterCriteria(this);
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

    public ServiceOrderTypeFilter getServiceOrderType() {
        return serviceOrderType;
    }

    public Optional<ServiceOrderTypeFilter> optionalServiceOrderType() {
        return Optional.ofNullable(serviceOrderType);
    }

    public ServiceOrderTypeFilter serviceOrderType() {
        if (serviceOrderType == null) {
            setServiceOrderType(new ServiceOrderTypeFilter());
        }
        return serviceOrderType;
    }

    public void setServiceOrderType(ServiceOrderTypeFilter serviceOrderType) {
        this.serviceOrderType = serviceOrderType;
    }

    public LongFilter getSlaInHours() {
        return slaInHours;
    }

    public Optional<LongFilter> optionalSlaInHours() {
        return Optional.ofNullable(slaInHours);
    }

    public LongFilter slaInHours() {
        if (slaInHours == null) {
            setSlaInHours(new LongFilter());
        }
        return slaInHours;
    }

    public void setSlaInHours(LongFilter slaInHours) {
        this.slaInHours = slaInHours;
    }

    public FloatFilter getCharge() {
        return charge;
    }

    public Optional<FloatFilter> optionalCharge() {
        return Optional.ofNullable(charge);
    }

    public FloatFilter charge() {
        if (charge == null) {
            setCharge(new FloatFilter());
        }
        return charge;
    }

    public void setCharge(FloatFilter charge) {
        this.charge = charge;
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

    public FloatFilter getFranchiseCommissionWithinSla() {
        return franchiseCommissionWithinSla;
    }

    public Optional<FloatFilter> optionalFranchiseCommissionWithinSla() {
        return Optional.ofNullable(franchiseCommissionWithinSla);
    }

    public FloatFilter franchiseCommissionWithinSla() {
        if (franchiseCommissionWithinSla == null) {
            setFranchiseCommissionWithinSla(new FloatFilter());
        }
        return franchiseCommissionWithinSla;
    }

    public void setFranchiseCommissionWithinSla(FloatFilter franchiseCommissionWithinSla) {
        this.franchiseCommissionWithinSla = franchiseCommissionWithinSla;
    }

    public FloatFilter getFranchiseTaxWithinSlaTax() {
        return franchiseTaxWithinSlaTax;
    }

    public Optional<FloatFilter> optionalFranchiseTaxWithinSlaTax() {
        return Optional.ofNullable(franchiseTaxWithinSlaTax);
    }

    public FloatFilter franchiseTaxWithinSlaTax() {
        if (franchiseTaxWithinSlaTax == null) {
            setFranchiseTaxWithinSlaTax(new FloatFilter());
        }
        return franchiseTaxWithinSlaTax;
    }

    public void setFranchiseTaxWithinSlaTax(FloatFilter franchiseTaxWithinSlaTax) {
        this.franchiseTaxWithinSlaTax = franchiseTaxWithinSlaTax;
    }

    public FloatFilter getFranchiseCommissionOutsideSla() {
        return franchiseCommissionOutsideSla;
    }

    public Optional<FloatFilter> optionalFranchiseCommissionOutsideSla() {
        return Optional.ofNullable(franchiseCommissionOutsideSla);
    }

    public FloatFilter franchiseCommissionOutsideSla() {
        if (franchiseCommissionOutsideSla == null) {
            setFranchiseCommissionOutsideSla(new FloatFilter());
        }
        return franchiseCommissionOutsideSla;
    }

    public void setFranchiseCommissionOutsideSla(FloatFilter franchiseCommissionOutsideSla) {
        this.franchiseCommissionOutsideSla = franchiseCommissionOutsideSla;
    }

    public FloatFilter getFranchiseTaxOutsideSlaTax() {
        return franchiseTaxOutsideSlaTax;
    }

    public Optional<FloatFilter> optionalFranchiseTaxOutsideSlaTax() {
        return Optional.ofNullable(franchiseTaxOutsideSlaTax);
    }

    public FloatFilter franchiseTaxOutsideSlaTax() {
        if (franchiseTaxOutsideSlaTax == null) {
            setFranchiseTaxOutsideSlaTax(new FloatFilter());
        }
        return franchiseTaxOutsideSlaTax;
    }

    public void setFranchiseTaxOutsideSlaTax(FloatFilter franchiseTaxOutsideSlaTax) {
        this.franchiseTaxOutsideSlaTax = franchiseTaxOutsideSlaTax;
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
        final ServiceOrderMasterCriteria that = (ServiceOrderMasterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(serviceOrderType, that.serviceOrderType) &&
            Objects.equals(slaInHours, that.slaInHours) &&
            Objects.equals(charge, that.charge) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(franchiseCommissionWithinSla, that.franchiseCommissionWithinSla) &&
            Objects.equals(franchiseTaxWithinSlaTax, that.franchiseTaxWithinSlaTax) &&
            Objects.equals(franchiseCommissionOutsideSla, that.franchiseCommissionOutsideSla) &&
            Objects.equals(franchiseTaxOutsideSlaTax, that.franchiseTaxOutsideSlaTax) &&
            Objects.equals(isActive, that.isActive) &&
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
            serviceOrderType,
            slaInHours,
            charge,
            tax,
            franchiseCommissionWithinSla,
            franchiseTaxWithinSlaTax,
            franchiseCommissionOutsideSla,
            franchiseTaxOutsideSlaTax,
            isActive,
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
        return "ServiceOrderMasterCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalServiceOrderType().map(f -> "serviceOrderType=" + f + ", ").orElse("") +
            optionalSlaInHours().map(f -> "slaInHours=" + f + ", ").orElse("") +
            optionalCharge().map(f -> "charge=" + f + ", ").orElse("") +
            optionalTax().map(f -> "tax=" + f + ", ").orElse("") +
            optionalFranchiseCommissionWithinSla().map(f -> "franchiseCommissionWithinSla=" + f + ", ").orElse("") +
            optionalFranchiseTaxWithinSlaTax().map(f -> "franchiseTaxWithinSlaTax=" + f + ", ").orElse("") +
            optionalFranchiseCommissionOutsideSla().map(f -> "franchiseCommissionOutsideSla=" + f + ", ").orElse("") +
            optionalFranchiseTaxOutsideSlaTax().map(f -> "franchiseTaxOutsideSlaTax=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
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
