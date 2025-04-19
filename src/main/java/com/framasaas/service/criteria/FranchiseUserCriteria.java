package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.FranchiseUserRole;
import com.framasaas.domain.enumeration.FranchiseUserStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.FranchiseUser} entity. This class is used
 * in {@link com.framasaas.web.rest.FranchiseUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /franchise-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseUserCriteria implements Serializable, Criteria {

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

    /**
     * Class for filtering FranchiseUserRole
     */
    public static class FranchiseUserRoleFilter extends Filter<FranchiseUserRole> {

        public FranchiseUserRoleFilter() {}

        public FranchiseUserRoleFilter(FranchiseUserRoleFilter filter) {
            super(filter);
        }

        @Override
        public FranchiseUserRoleFilter copy() {
            return new FranchiseUserRoleFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userName;

    private StringFilter email;

    private LongFilter contact;

    private FranchiseUserStatusFilter userStatus;

    private FranchiseUserRoleFilter userRole;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter franchiseUserStatusHistoryId;

    private LongFilter additionalAttributeId;

    private LongFilter franchiseId;

    private LongFilter skillRuleSetId;

    private Boolean distinct;

    public FranchiseUserCriteria() {}

    public FranchiseUserCriteria(FranchiseUserCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.userName = other.optionalUserName().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.contact = other.optionalContact().map(LongFilter::copy).orElse(null);
        this.userStatus = other.optionalUserStatus().map(FranchiseUserStatusFilter::copy).orElse(null);
        this.userRole = other.optionalUserRole().map(FranchiseUserRoleFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.franchiseUserStatusHistoryId = other.optionalFranchiseUserStatusHistoryId().map(LongFilter::copy).orElse(null);
        this.additionalAttributeId = other.optionalAdditionalAttributeId().map(LongFilter::copy).orElse(null);
        this.franchiseId = other.optionalFranchiseId().map(LongFilter::copy).orElse(null);
        this.skillRuleSetId = other.optionalSkillRuleSetId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FranchiseUserCriteria copy() {
        return new FranchiseUserCriteria(this);
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

    public StringFilter getUserName() {
        return userName;
    }

    public Optional<StringFilter> optionalUserName() {
        return Optional.ofNullable(userName);
    }

    public StringFilter userName() {
        if (userName == null) {
            setUserName(new StringFilter());
        }
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
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

    public FranchiseUserStatusFilter getUserStatus() {
        return userStatus;
    }

    public Optional<FranchiseUserStatusFilter> optionalUserStatus() {
        return Optional.ofNullable(userStatus);
    }

    public FranchiseUserStatusFilter userStatus() {
        if (userStatus == null) {
            setUserStatus(new FranchiseUserStatusFilter());
        }
        return userStatus;
    }

    public void setUserStatus(FranchiseUserStatusFilter userStatus) {
        this.userStatus = userStatus;
    }

    public FranchiseUserRoleFilter getUserRole() {
        return userRole;
    }

    public Optional<FranchiseUserRoleFilter> optionalUserRole() {
        return Optional.ofNullable(userRole);
    }

    public FranchiseUserRoleFilter userRole() {
        if (userRole == null) {
            setUserRole(new FranchiseUserRoleFilter());
        }
        return userRole;
    }

    public void setUserRole(FranchiseUserRoleFilter userRole) {
        this.userRole = userRole;
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

    public LongFilter getFranchiseUserStatusHistoryId() {
        return franchiseUserStatusHistoryId;
    }

    public Optional<LongFilter> optionalFranchiseUserStatusHistoryId() {
        return Optional.ofNullable(franchiseUserStatusHistoryId);
    }

    public LongFilter franchiseUserStatusHistoryId() {
        if (franchiseUserStatusHistoryId == null) {
            setFranchiseUserStatusHistoryId(new LongFilter());
        }
        return franchiseUserStatusHistoryId;
    }

    public void setFranchiseUserStatusHistoryId(LongFilter franchiseUserStatusHistoryId) {
        this.franchiseUserStatusHistoryId = franchiseUserStatusHistoryId;
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

    public LongFilter getSkillRuleSetId() {
        return skillRuleSetId;
    }

    public Optional<LongFilter> optionalSkillRuleSetId() {
        return Optional.ofNullable(skillRuleSetId);
    }

    public LongFilter skillRuleSetId() {
        if (skillRuleSetId == null) {
            setSkillRuleSetId(new LongFilter());
        }
        return skillRuleSetId;
    }

    public void setSkillRuleSetId(LongFilter skillRuleSetId) {
        this.skillRuleSetId = skillRuleSetId;
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
        final FranchiseUserCriteria that = (FranchiseUserCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(userStatus, that.userStatus) &&
            Objects.equals(userRole, that.userRole) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(franchiseUserStatusHistoryId, that.franchiseUserStatusHistoryId) &&
            Objects.equals(additionalAttributeId, that.additionalAttributeId) &&
            Objects.equals(franchiseId, that.franchiseId) &&
            Objects.equals(skillRuleSetId, that.skillRuleSetId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            userName,
            email,
            contact,
            userStatus,
            userRole,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            franchiseUserStatusHistoryId,
            additionalAttributeId,
            franchiseId,
            skillRuleSetId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseUserCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalUserName().map(f -> "userName=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalContact().map(f -> "contact=" + f + ", ").orElse("") +
            optionalUserStatus().map(f -> "userStatus=" + f + ", ").orElse("") +
            optionalUserRole().map(f -> "userRole=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalFranchiseUserStatusHistoryId().map(f -> "franchiseUserStatusHistoryId=" + f + ", ").orElse("") +
            optionalAdditionalAttributeId().map(f -> "additionalAttributeId=" + f + ", ").orElse("") +
            optionalFranchiseId().map(f -> "franchiseId=" + f + ", ").orElse("") +
            optionalSkillRuleSetId().map(f -> "skillRuleSetId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
