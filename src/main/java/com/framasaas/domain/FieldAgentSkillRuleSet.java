package com.framasaas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.domain.enumeration.FieldAgentSkillRuleSetSortType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldAgentSkillRuleSet.
 */
@Entity
@Table(name = "field_agent_skill_rule_set")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldAgentSkillRuleSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "sort_type")
    private FieldAgentSkillRuleSetSortType sortType;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldAgentSkillRuleSet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "brands", "categories", "pincodes", "locationMappings", "languageMappings", "additionalAttributes", "fieldAgentSkillRuleSet",
        },
        allowSetters = true
    )
    private Set<FieldAgentSkillRule> fieldAgentSkillRules = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "skillRuleSet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "franchiseUserStatusHistories", "additionalAttributes", "franchise", "skillRuleSet" },
        allowSetters = true
    )
    private Set<FranchiseUser> franchiseUsers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldAgentSkillRuleSet")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldAgentSkillRuleSet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FieldAgentSkillRuleSetSortType getSortType() {
        return this.sortType;
    }

    public FieldAgentSkillRuleSet sortType(FieldAgentSkillRuleSetSortType sortType) {
        this.setSortType(sortType);
        return this;
    }

    public void setSortType(FieldAgentSkillRuleSetSortType sortType) {
        this.sortType = sortType;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public FieldAgentSkillRuleSet createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public FieldAgentSkillRuleSet createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FieldAgentSkillRuleSet updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public FieldAgentSkillRuleSet updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<FieldAgentSkillRule> getFieldAgentSkillRules() {
        return this.fieldAgentSkillRules;
    }

    public void setFieldAgentSkillRules(Set<FieldAgentSkillRule> fieldAgentSkillRules) {
        if (this.fieldAgentSkillRules != null) {
            this.fieldAgentSkillRules.forEach(i -> i.setFieldAgentSkillRuleSet(null));
        }
        if (fieldAgentSkillRules != null) {
            fieldAgentSkillRules.forEach(i -> i.setFieldAgentSkillRuleSet(this));
        }
        this.fieldAgentSkillRules = fieldAgentSkillRules;
    }

    public FieldAgentSkillRuleSet fieldAgentSkillRules(Set<FieldAgentSkillRule> fieldAgentSkillRules) {
        this.setFieldAgentSkillRules(fieldAgentSkillRules);
        return this;
    }

    public FieldAgentSkillRuleSet addFieldAgentSkillRule(FieldAgentSkillRule fieldAgentSkillRule) {
        this.fieldAgentSkillRules.add(fieldAgentSkillRule);
        fieldAgentSkillRule.setFieldAgentSkillRuleSet(this);
        return this;
    }

    public FieldAgentSkillRuleSet removeFieldAgentSkillRule(FieldAgentSkillRule fieldAgentSkillRule) {
        this.fieldAgentSkillRules.remove(fieldAgentSkillRule);
        fieldAgentSkillRule.setFieldAgentSkillRuleSet(null);
        return this;
    }

    public Set<FranchiseUser> getFranchiseUsers() {
        return this.franchiseUsers;
    }

    public void setFranchiseUsers(Set<FranchiseUser> franchiseUsers) {
        if (this.franchiseUsers != null) {
            this.franchiseUsers.forEach(i -> i.setSkillRuleSet(null));
        }
        if (franchiseUsers != null) {
            franchiseUsers.forEach(i -> i.setSkillRuleSet(this));
        }
        this.franchiseUsers = franchiseUsers;
    }

    public FieldAgentSkillRuleSet franchiseUsers(Set<FranchiseUser> franchiseUsers) {
        this.setFranchiseUsers(franchiseUsers);
        return this;
    }

    public FieldAgentSkillRuleSet addFranchiseUser(FranchiseUser franchiseUser) {
        this.franchiseUsers.add(franchiseUser);
        franchiseUser.setSkillRuleSet(this);
        return this;
    }

    public FieldAgentSkillRuleSet removeFranchiseUser(FranchiseUser franchiseUser) {
        this.franchiseUsers.remove(franchiseUser);
        franchiseUser.setSkillRuleSet(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setFieldAgentSkillRuleSet(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setFieldAgentSkillRuleSet(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public FieldAgentSkillRuleSet additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public FieldAgentSkillRuleSet addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setFieldAgentSkillRuleSet(this);
        return this;
    }

    public FieldAgentSkillRuleSet removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setFieldAgentSkillRuleSet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldAgentSkillRuleSet)) {
            return false;
        }
        return getId() != null && getId().equals(((FieldAgentSkillRuleSet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldAgentSkillRuleSet{" +
            "id=" + getId() +
            ", sortType='" + getSortType() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
