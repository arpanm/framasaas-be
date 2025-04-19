package com.framasaas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.domain.enumeration.FranchiseAllocationRuleJoinType;
import com.framasaas.domain.enumeration.FranchiseAllocationRuleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FranchiseAllocationRule.
 */
@Entity
@Table(name = "franchise_allocation_rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseAllocationRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false)
    private FranchiseAllocationRuleType ruleType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "join_type", nullable = false)
    private FranchiseAllocationRuleJoinType joinType;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchiseRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Set<Brand> brands = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchiseRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchiseRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Set<Pincode> pincodes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchiseRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Set<LocationMapping> locationMappings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchiseRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Set<LanguageMapping> languageMappings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchiseAllocationRule")
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

    public FranchiseAllocationRule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FranchiseAllocationRuleType getRuleType() {
        return this.ruleType;
    }

    public FranchiseAllocationRule ruleType(FranchiseAllocationRuleType ruleType) {
        this.setRuleType(ruleType);
        return this;
    }

    public void setRuleType(FranchiseAllocationRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public FranchiseAllocationRuleJoinType getJoinType() {
        return this.joinType;
    }

    public FranchiseAllocationRule joinType(FranchiseAllocationRuleJoinType joinType) {
        this.setJoinType(joinType);
        return this;
    }

    public void setJoinType(FranchiseAllocationRuleJoinType joinType) {
        this.joinType = joinType;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public FranchiseAllocationRule createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public FranchiseAllocationRule createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FranchiseAllocationRule updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public FranchiseAllocationRule updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<Brand> getBrands() {
        return this.brands;
    }

    public void setBrands(Set<Brand> brands) {
        if (this.brands != null) {
            this.brands.forEach(i -> i.setFranchiseRule(null));
        }
        if (brands != null) {
            brands.forEach(i -> i.setFranchiseRule(this));
        }
        this.brands = brands;
    }

    public FranchiseAllocationRule brands(Set<Brand> brands) {
        this.setBrands(brands);
        return this;
    }

    public FranchiseAllocationRule addBrand(Brand brand) {
        this.brands.add(brand);
        brand.setFranchiseRule(this);
        return this;
    }

    public FranchiseAllocationRule removeBrand(Brand brand) {
        this.brands.remove(brand);
        brand.setFranchiseRule(null);
        return this;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        if (this.categories != null) {
            this.categories.forEach(i -> i.setFranchiseRule(null));
        }
        if (categories != null) {
            categories.forEach(i -> i.setFranchiseRule(this));
        }
        this.categories = categories;
    }

    public FranchiseAllocationRule categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public FranchiseAllocationRule addCategory(Category category) {
        this.categories.add(category);
        category.setFranchiseRule(this);
        return this;
    }

    public FranchiseAllocationRule removeCategory(Category category) {
        this.categories.remove(category);
        category.setFranchiseRule(null);
        return this;
    }

    public Set<Pincode> getPincodes() {
        return this.pincodes;
    }

    public void setPincodes(Set<Pincode> pincodes) {
        if (this.pincodes != null) {
            this.pincodes.forEach(i -> i.setFranchiseRule(null));
        }
        if (pincodes != null) {
            pincodes.forEach(i -> i.setFranchiseRule(this));
        }
        this.pincodes = pincodes;
    }

    public FranchiseAllocationRule pincodes(Set<Pincode> pincodes) {
        this.setPincodes(pincodes);
        return this;
    }

    public FranchiseAllocationRule addPincode(Pincode pincode) {
        this.pincodes.add(pincode);
        pincode.setFranchiseRule(this);
        return this;
    }

    public FranchiseAllocationRule removePincode(Pincode pincode) {
        this.pincodes.remove(pincode);
        pincode.setFranchiseRule(null);
        return this;
    }

    public Set<LocationMapping> getLocationMappings() {
        return this.locationMappings;
    }

    public void setLocationMappings(Set<LocationMapping> locationMappings) {
        if (this.locationMappings != null) {
            this.locationMappings.forEach(i -> i.setFranchiseRule(null));
        }
        if (locationMappings != null) {
            locationMappings.forEach(i -> i.setFranchiseRule(this));
        }
        this.locationMappings = locationMappings;
    }

    public FranchiseAllocationRule locationMappings(Set<LocationMapping> locationMappings) {
        this.setLocationMappings(locationMappings);
        return this;
    }

    public FranchiseAllocationRule addLocationMapping(LocationMapping locationMapping) {
        this.locationMappings.add(locationMapping);
        locationMapping.setFranchiseRule(this);
        return this;
    }

    public FranchiseAllocationRule removeLocationMapping(LocationMapping locationMapping) {
        this.locationMappings.remove(locationMapping);
        locationMapping.setFranchiseRule(null);
        return this;
    }

    public Set<LanguageMapping> getLanguageMappings() {
        return this.languageMappings;
    }

    public void setLanguageMappings(Set<LanguageMapping> languageMappings) {
        if (this.languageMappings != null) {
            this.languageMappings.forEach(i -> i.setFranchiseRule(null));
        }
        if (languageMappings != null) {
            languageMappings.forEach(i -> i.setFranchiseRule(this));
        }
        this.languageMappings = languageMappings;
    }

    public FranchiseAllocationRule languageMappings(Set<LanguageMapping> languageMappings) {
        this.setLanguageMappings(languageMappings);
        return this;
    }

    public FranchiseAllocationRule addLanguageMapping(LanguageMapping languageMapping) {
        this.languageMappings.add(languageMapping);
        languageMapping.setFranchiseRule(this);
        return this;
    }

    public FranchiseAllocationRule removeLanguageMapping(LanguageMapping languageMapping) {
        this.languageMappings.remove(languageMapping);
        languageMapping.setFranchiseRule(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setFranchiseAllocationRule(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setFranchiseAllocationRule(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public FranchiseAllocationRule additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public FranchiseAllocationRule addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setFranchiseAllocationRule(this);
        return this;
    }

    public FranchiseAllocationRule removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setFranchiseAllocationRule(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchiseAllocationRule)) {
            return false;
        }
        return getId() != null && getId().equals(((FranchiseAllocationRule) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseAllocationRule{" +
            "id=" + getId() +
            ", ruleType='" + getRuleType() + "'" +
            ", joinType='" + getJoinType() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
