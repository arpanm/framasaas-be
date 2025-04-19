package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.FranchiseStatus;
import com.framasaas.domain.enumeration.PerformanceTag;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.Franchise} entity. This class is used
 * in {@link com.framasaas.web.rest.FranchiseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /franchises?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseCriteria implements Serializable, Criteria {

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

    private StringFilter franchiseName;

    private StringFilter owner;

    private StringFilter email;

    private LongFilter contact;

    private FranchiseStatusFilter franchiseStatus;

    private StringFilter gstNumber;

    private StringFilter registrationNumber;

    private FloatFilter performanceScore;

    private PerformanceTagFilter performanceTag;

    private LongFilter dailyMaxServiceLimit;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter addressId;

    private LongFilter franchiseStatusHistoryId;

    private LongFilter franchisePerformanceHistoryId;

    private LongFilter supportingDocumentId;

    private LongFilter franchiseUserId;

    private LongFilter serviceOrderFranchiseAssignmentId;

    private LongFilter additionalAttributeId;

    private LongFilter rulesetId;

    private LongFilter brandsId;

    private LongFilter categoriesId;

    private Boolean distinct;

    public FranchiseCriteria() {}

    public FranchiseCriteria(FranchiseCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.franchiseName = other.optionalFranchiseName().map(StringFilter::copy).orElse(null);
        this.owner = other.optionalOwner().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.contact = other.optionalContact().map(LongFilter::copy).orElse(null);
        this.franchiseStatus = other.optionalFranchiseStatus().map(FranchiseStatusFilter::copy).orElse(null);
        this.gstNumber = other.optionalGstNumber().map(StringFilter::copy).orElse(null);
        this.registrationNumber = other.optionalRegistrationNumber().map(StringFilter::copy).orElse(null);
        this.performanceScore = other.optionalPerformanceScore().map(FloatFilter::copy).orElse(null);
        this.performanceTag = other.optionalPerformanceTag().map(PerformanceTagFilter::copy).orElse(null);
        this.dailyMaxServiceLimit = other.optionalDailyMaxServiceLimit().map(LongFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.addressId = other.optionalAddressId().map(LongFilter::copy).orElse(null);
        this.franchiseStatusHistoryId = other.optionalFranchiseStatusHistoryId().map(LongFilter::copy).orElse(null);
        this.franchisePerformanceHistoryId = other.optionalFranchisePerformanceHistoryId().map(LongFilter::copy).orElse(null);
        this.supportingDocumentId = other.optionalSupportingDocumentId().map(LongFilter::copy).orElse(null);
        this.franchiseUserId = other.optionalFranchiseUserId().map(LongFilter::copy).orElse(null);
        this.serviceOrderFranchiseAssignmentId = other.optionalServiceOrderFranchiseAssignmentId().map(LongFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.rulesetId = other.optionalRulesetId().map(LongFilter::copy).orElse(null);
        this.brandsId = other.optionalBrandsId().map(LongFilter::copy).orElse(null);
        this.categoriesId = other.optionalCategoriesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FranchiseCriteria copy() {
        return new FranchiseCriteria(this);
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

    public StringFilter getFranchiseName() {
        return franchiseName;
    }

    public Optional<StringFilter> optionalFranchiseName() {
        return Optional.ofNullable(franchiseName);
    }

    public StringFilter franchiseName() {
        if (franchiseName == null) {
            setFranchiseName(new StringFilter());
        }
        return franchiseName;
    }

    public void setFranchiseName(StringFilter franchiseName) {
        this.franchiseName = franchiseName;
    }

    public StringFilter getOwner() {
        return owner;
    }

    public Optional<StringFilter> optionalOwner() {
        return Optional.ofNullable(owner);
    }

    public StringFilter owner() {
        if (owner == null) {
            setOwner(new StringFilter());
        }
        return owner;
    }

    public void setOwner(StringFilter owner) {
        this.owner = owner;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getContact() {
        return contact;
    }

    public Optional<LongFilter> optionalContact() {
        return Optional.ofNullable(contact);
    }

    public LongFilter contact() {
        if (contact == null) {
            setContact(new LongFilter());
        }
        return contact;
    }

    public void setContact(LongFilter contact) {
        this.contact = contact;
    }

    public FranchiseStatusFilter getFranchiseStatus() {
        return franchiseStatus;
    }

    public Optional<FranchiseStatusFilter> optionalFranchiseStatus() {
        return Optional.ofNullable(franchiseStatus);
    }

    public FranchiseStatusFilter franchiseStatus() {
        if (franchiseStatus == null) {
            setFranchiseStatus(new FranchiseStatusFilter());
        }
        return franchiseStatus;
    }

    public void setFranchiseStatus(FranchiseStatusFilter franchiseStatus) {
        this.franchiseStatus = franchiseStatus;
    }

    public StringFilter getGstNumber() {
        return gstNumber;
    }

    public Optional<StringFilter> optionalGstNumber() {
        return Optional.ofNullable(gstNumber);
    }

    public StringFilter gstNumber() {
        if (gstNumber == null) {
            setGstNumber(new StringFilter());
        }
        return gstNumber;
    }

    public void setGstNumber(StringFilter gstNumber) {
        this.gstNumber = gstNumber;
    }

    public StringFilter getRegistrationNumber() {
        return registrationNumber;
    }

    public Optional<StringFilter> optionalRegistrationNumber() {
        return Optional.ofNullable(registrationNumber);
    }

    public StringFilter registrationNumber() {
        if (registrationNumber == null) {
            setRegistrationNumber(new StringFilter());
        }
        return registrationNumber;
    }

    public void setRegistrationNumber(StringFilter registrationNumber) {
        this.registrationNumber = registrationNumber;
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

    public LongFilter getDailyMaxServiceLimit() {
        return dailyMaxServiceLimit;
    }

    public Optional<LongFilter> optionalDailyMaxServiceLimit() {
        return Optional.ofNullable(dailyMaxServiceLimit);
    }

    public LongFilter dailyMaxServiceLimit() {
        if (dailyMaxServiceLimit == null) {
            setDailyMaxServiceLimit(new LongFilter());
        }
        return dailyMaxServiceLimit;
    }

    public void setDailyMaxServiceLimit(LongFilter dailyMaxServiceLimit) {
        this.dailyMaxServiceLimit = dailyMaxServiceLimit;
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

    public LongFilter getFranchiseStatusHistoryId() {
        return franchiseStatusHistoryId;
    }

    public Optional<LongFilter> optionalFranchiseStatusHistoryId() {
        return Optional.ofNullable(franchiseStatusHistoryId);
    }

    public LongFilter franchiseStatusHistoryId() {
        if (franchiseStatusHistoryId == null) {
            setFranchiseStatusHistoryId(new LongFilter());
        }
        return franchiseStatusHistoryId;
    }

    public void setFranchiseStatusHistoryId(LongFilter franchiseStatusHistoryId) {
        this.franchiseStatusHistoryId = franchiseStatusHistoryId;
    }

    public LongFilter getFranchisePerformanceHistoryId() {
        return franchisePerformanceHistoryId;
    }

    public Optional<LongFilter> optionalFranchisePerformanceHistoryId() {
        return Optional.ofNullable(franchisePerformanceHistoryId);
    }

    public LongFilter franchisePerformanceHistoryId() {
        if (franchisePerformanceHistoryId == null) {
            setFranchisePerformanceHistoryId(new LongFilter());
        }
        return franchisePerformanceHistoryId;
    }

    public void setFranchisePerformanceHistoryId(LongFilter franchisePerformanceHistoryId) {
        this.franchisePerformanceHistoryId = franchisePerformanceHistoryId;
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

    public LongFilter getRulesetId() {
        return rulesetId;
    }

    public Optional<LongFilter> optionalRulesetId() {
        return Optional.ofNullable(rulesetId);
    }

    public LongFilter rulesetId() {
        if (rulesetId == null) {
            setRulesetId(new LongFilter());
        }
        return rulesetId;
    }

    public void setRulesetId(LongFilter rulesetId) {
        this.rulesetId = rulesetId;
    }

    public LongFilter getBrandsId() {
        return brandsId;
    }

    public Optional<LongFilter> optionalBrandsId() {
        return Optional.ofNullable(brandsId);
    }

    public LongFilter brandsId() {
        if (brandsId == null) {
            setBrandsId(new LongFilter());
        }
        return brandsId;
    }

    public void setBrandsId(LongFilter brandsId) {
        this.brandsId = brandsId;
    }

    public LongFilter getCategoriesId() {
        return categoriesId;
    }

    public Optional<LongFilter> optionalCategoriesId() {
        return Optional.ofNullable(categoriesId);
    }

    public LongFilter categoriesId() {
        if (categoriesId == null) {
            setCategoriesId(new LongFilter());
        }
        return categoriesId;
    }

    public void setCategoriesId(LongFilter categoriesId) {
        this.categoriesId = categoriesId;
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
        final FranchiseCriteria that = (FranchiseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(franchiseName, that.franchiseName) &&
            Objects.equals(owner, that.owner) &&
            Objects.equals(email, that.email) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(franchiseStatus, that.franchiseStatus) &&
            Objects.equals(gstNumber, that.gstNumber) &&
            Objects.equals(registrationNumber, that.registrationNumber) &&
            Objects.equals(performanceScore, that.performanceScore) &&
            Objects.equals(performanceTag, that.performanceTag) &&
            Objects.equals(dailyMaxServiceLimit, that.dailyMaxServiceLimit) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(franchiseStatusHistoryId, that.franchiseStatusHistoryId) &&
            Objects.equals(franchisePerformanceHistoryId, that.franchisePerformanceHistoryId) &&
            Objects.equals(supportingDocumentId, that.supportingDocumentId) &&
            Objects.equals(franchiseUserId, that.franchiseUserId) &&
            Objects.equals(serviceOrderFranchiseAssignmentId, that.serviceOrderFranchiseAssignmentId) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(rulesetId, that.rulesetId) &&
            Objects.equals(brandsId, that.brandsId) &&
            Objects.equals(categoriesId, that.categoriesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            franchiseName,
            owner,
            email,
            contact,
            franchiseStatus,
            gstNumber,
            registrationNumber,
            performanceScore,
            performanceTag,
            dailyMaxServiceLimit,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            addressId,
            franchiseStatusHistoryId,
            franchisePerformanceHistoryId,
            supportingDocumentId,
            franchiseUserId,
            serviceOrderFranchiseAssignmentId,
            additionalAttributeId,
            rulesetId,
            brandsId,
            categoriesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFranchiseName().map(f -> "franchiseName=" + f + ", ").orElse("") +
            optionalOwner().map(f -> "owner=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalContact().map(f -> "contact=" + f + ", ").orElse("") +
            optionalFranchiseStatus().map(f -> "franchiseStatus=" + f + ", ").orElse("") +
            optionalGstNumber().map(f -> "gstNumber=" + f + ", ").orElse("") +
            optionalRegistrationNumber().map(f -> "registrationNumber=" + f + ", ").orElse("") +
            optionalPerformanceScore().map(f -> "performanceScore=" + f + ", ").orElse("") +
            optionalPerformanceTag().map(f -> "performanceTag=" + f + ", ").orElse("") +
            optionalDailyMaxServiceLimit().map(f -> "dailyMaxServiceLimit=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalAddressId().map(f -> "addressId=" + f + ", ").orElse("") +
            optionalFranchiseStatusHistoryId().map(f -> "franchiseStatusHistoryId=" + f + ", ").orElse("") +
            optionalFranchisePerformanceHistoryId().map(f -> "franchisePerformanceHistoryId=" + f + ", ").orElse("") +
            optionalSupportingDocumentId().map(f -> "supportingDocumentId=" + f + ", ").orElse("") +
            optionalFranchiseUserId().map(f -> "franchiseUserId=" + f + ", ").orElse("") +
            optionalServiceOrderFranchiseAssignmentId().map(f -> "serviceOrderFranchiseAssignmentId=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalRulesetId().map(f -> "rulesetId=" + f + ", ").orElse("") +
            optionalBrandsId().map(f -> "brandsId=" + f + ", ").orElse("") +
            optionalCategoriesId().map(f -> "categoriesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
