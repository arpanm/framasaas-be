package com.framasaas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.domain.enumeration.FranchiseUserRole;
import com.framasaas.domain.enumeration.FranchiseUserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FranchiseUser.
 */
@Entity
@Table(name = "franchise_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @Column(name = "contact", nullable = false)
    private Long contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private FranchiseUserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private FranchiseUserRole userRole;

    @NotNull
    @Column(name = "createdd_by", nullable = false)
    private String createddBy;

    @NotNull
    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @NotNull
    @Column(name = "updated_time", nullable = false)
    private Instant updatedTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchiseUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchiseUser" }, allowSetters = true)
    private Set<FranchiseUserStatusHistory> franchiseUserStatusHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchiseUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "additionalAttributePossibleValues",
            "franchise",
            "franchiseStatus",
            "franchisePerformance",
            "brand",
            "category",
            "address",
            "location",
            "franchiseUser",
            "customer",
            "supportDocument",
            "product",
            "hsn",
            "priceHistory",
            "warrantyMaster",
            "warrantyMasterPriceHistory",
            "article",
            "articleWarranty",
            "serviceOrder",
            "serviceOrderPayment",
            "serviceOrderFranchiseAssignment",
            "serviceOrderFieldAgentAssignment",
            "franchiseAllocationRuleSet",
            "franchiseAllocationRule",
            "fieldAgentSkillRuleSet",
            "fieldAgentSkillRule",
            "inventoryLocation",
            "inventory",
            "document",
            "articleWarrantyDocument",
            "serviceOrderAssignment",
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "address",
            "franchiseStatusHistories",
            "franchisePerformanceHistories",
            "supportingDocuments",
            "franchiseUsers",
            "serviceOrderFranchiseAssignments",
            "additionalAttributes",
            "ruleset",
            "brands",
            "categories",
        },
        allowSetters = true
    )
    private Franchise franchise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "fieldAgentSkillRules", "franchiseUsers", "additionalAttributes" }, allowSetters = true)
    private FieldAgentSkillRuleSet skillRuleSet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FranchiseUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public FranchiseUser userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public FranchiseUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContact() {
        return this.contact;
    }

    public FranchiseUser contact(Long contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public FranchiseUserStatus getUserStatus() {
        return this.userStatus;
    }

    public FranchiseUser userStatus(FranchiseUserStatus userStatus) {
        this.setUserStatus(userStatus);
        return this;
    }

    public void setUserStatus(FranchiseUserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public FranchiseUserRole getUserRole() {
        return this.userRole;
    }

    public FranchiseUser userRole(FranchiseUserRole userRole) {
        this.setUserRole(userRole);
        return this;
    }

    public void setUserRole(FranchiseUserRole userRole) {
        this.userRole = userRole;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public FranchiseUser createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public FranchiseUser createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FranchiseUser updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public FranchiseUser updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<FranchiseUserStatusHistory> getFranchiseUserStatusHistories() {
        return this.franchiseUserStatusHistories;
    }

    public void setFranchiseUserStatusHistories(Set<FranchiseUserStatusHistory> franchiseUserStatusHistories) {
        if (this.franchiseUserStatusHistories != null) {
            this.franchiseUserStatusHistories.forEach(i -> i.setFranchiseUser(null));
        }
        if (franchiseUserStatusHistories != null) {
            franchiseUserStatusHistories.forEach(i -> i.setFranchiseUser(this));
        }
        this.franchiseUserStatusHistories = franchiseUserStatusHistories;
    }

    public FranchiseUser franchiseUserStatusHistories(Set<FranchiseUserStatusHistory> franchiseUserStatusHistories) {
        this.setFranchiseUserStatusHistories(franchiseUserStatusHistories);
        return this;
    }

    public FranchiseUser addFranchiseUserStatusHistory(FranchiseUserStatusHistory franchiseUserStatusHistory) {
        this.franchiseUserStatusHistories.add(franchiseUserStatusHistory);
        franchiseUserStatusHistory.setFranchiseUser(this);
        return this;
    }

    public FranchiseUser removeFranchiseUserStatusHistory(FranchiseUserStatusHistory franchiseUserStatusHistory) {
        this.franchiseUserStatusHistories.remove(franchiseUserStatusHistory);
        franchiseUserStatusHistory.setFranchiseUser(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setFranchiseUser(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setFranchiseUser(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public FranchiseUser additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public FranchiseUser addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setFranchiseUser(this);
        return this;
    }

    public FranchiseUser removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setFranchiseUser(null);
        return this;
    }

    public Franchise getFranchise() {
        return this.franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public FranchiseUser franchise(Franchise franchise) {
        this.setFranchise(franchise);
        return this;
    }

    public FieldAgentSkillRuleSet getSkillRuleSet() {
        return this.skillRuleSet;
    }

    public void setSkillRuleSet(FieldAgentSkillRuleSet fieldAgentSkillRuleSet) {
        this.skillRuleSet = fieldAgentSkillRuleSet;
    }

    public FranchiseUser skillRuleSet(FieldAgentSkillRuleSet fieldAgentSkillRuleSet) {
        this.setSkillRuleSet(fieldAgentSkillRuleSet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchiseUser)) {
            return false;
        }
        return getId() != null && getId().equals(((FranchiseUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseUser{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", email='" + getEmail() + "'" +
            ", contact=" + getContact() +
            ", userStatus='" + getUserStatus() + "'" +
            ", userRole='" + getUserRole() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
