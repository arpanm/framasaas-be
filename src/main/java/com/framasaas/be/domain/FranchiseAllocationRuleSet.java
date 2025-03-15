package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.FranchiseAllocationRuleSetSortType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FranchiseAllocationRuleSet.
 */
@Entity
@Table(name = "franchise_allocation_rule_set")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseAllocationRuleSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sort_type", nullable = false)
    private FranchiseAllocationRuleSetSortType sortType;

    @Column(name = "priority")
    private Long priority;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ruleset")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Franchise> franchises = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchiseAllocationRuleSet")
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

    public FranchiseAllocationRuleSet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public FranchiseAllocationRuleSet name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FranchiseAllocationRuleSetSortType getSortType() {
        return this.sortType;
    }

    public FranchiseAllocationRuleSet sortType(FranchiseAllocationRuleSetSortType sortType) {
        this.setSortType(sortType);
        return this;
    }

    public void setSortType(FranchiseAllocationRuleSetSortType sortType) {
        this.sortType = sortType;
    }

    public Long getPriority() {
        return this.priority;
    }

    public FranchiseAllocationRuleSet priority(Long priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public FranchiseAllocationRuleSet isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public FranchiseAllocationRuleSet createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public FranchiseAllocationRuleSet createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FranchiseAllocationRuleSet updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public FranchiseAllocationRuleSet updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<Franchise> getFranchises() {
        return this.franchises;
    }

    public void setFranchises(Set<Franchise> franchises) {
        if (this.franchises != null) {
            this.franchises.forEach(i -> i.setRuleset(null));
        }
        if (franchises != null) {
            franchises.forEach(i -> i.setRuleset(this));
        }
        this.franchises = franchises;
    }

    public FranchiseAllocationRuleSet franchises(Set<Franchise> franchises) {
        this.setFranchises(franchises);
        return this;
    }

    public FranchiseAllocationRuleSet addFranchise(Franchise franchise) {
        this.franchises.add(franchise);
        franchise.setRuleset(this);
        return this;
    }

    public FranchiseAllocationRuleSet removeFranchise(Franchise franchise) {
        this.franchises.remove(franchise);
        franchise.setRuleset(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setFranchiseAllocationRuleSet(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setFranchiseAllocationRuleSet(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public FranchiseAllocationRuleSet additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public FranchiseAllocationRuleSet addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setFranchiseAllocationRuleSet(this);
        return this;
    }

    public FranchiseAllocationRuleSet removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setFranchiseAllocationRuleSet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchiseAllocationRuleSet)) {
            return false;
        }
        return getId() != null && getId().equals(((FranchiseAllocationRuleSet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseAllocationRuleSet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sortType='" + getSortType() + "'" +
            ", priority=" + getPriority() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
