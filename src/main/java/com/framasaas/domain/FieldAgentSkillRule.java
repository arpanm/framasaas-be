package com.framasaas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.domain.enumeration.FieldAgentSkillRuleJoinType;
import com.framasaas.domain.enumeration.FieldAgentSkillRuleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldAgentSkillRule.
 */
@Entity
@Table(name = "field_agent_skill_rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldAgentSkillRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_type")
    private FieldAgentSkillRuleType skillType;

    @Enumerated(EnumType.STRING)
    @Column(name = "join_type")
    private FieldAgentSkillRuleJoinType joinType;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldAgentSkillRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Set<Brand> brands = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldAgentSkillRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldAgentSkillRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Set<Pincode> pincodes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldAgentSkillRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Set<LocationMapping> locationMappings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldAgentSkillRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Set<LanguageMapping> languageMappings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldAgentSkillRule")
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
    @JsonIgnoreProperties(value = { "fieldAgentSkillRules", "franchiseUsers", "additionalAttributes" }, allowSetters = true)
    private FieldAgentSkillRuleSet fieldAgentSkillRuleSet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldAgentSkillRule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FieldAgentSkillRuleType getSkillType() {
        return this.skillType;
    }

    public FieldAgentSkillRule skillType(FieldAgentSkillRuleType skillType) {
        this.setSkillType(skillType);
        return this;
    }

    public void setSkillType(FieldAgentSkillRuleType skillType) {
        this.skillType = skillType;
    }

    public FieldAgentSkillRuleJoinType getJoinType() {
        return this.joinType;
    }

    public FieldAgentSkillRule joinType(FieldAgentSkillRuleJoinType joinType) {
        this.setJoinType(joinType);
        return this;
    }

    public void setJoinType(FieldAgentSkillRuleJoinType joinType) {
        this.joinType = joinType;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public FieldAgentSkillRule createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public FieldAgentSkillRule createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FieldAgentSkillRule updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public FieldAgentSkillRule updatedTime(Instant updatedTime) {
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
            this.brands.forEach(i -> i.setFieldAgentSkillRule(null));
        }
        if (brands != null) {
            brands.forEach(i -> i.setFieldAgentSkillRule(this));
        }
        this.brands = brands;
    }

    public FieldAgentSkillRule brands(Set<Brand> brands) {
        this.setBrands(brands);
        return this;
    }

    public FieldAgentSkillRule addBrand(Brand brand) {
        this.brands.add(brand);
        brand.setFieldAgentSkillRule(this);
        return this;
    }

    public FieldAgentSkillRule removeBrand(Brand brand) {
        this.brands.remove(brand);
        brand.setFieldAgentSkillRule(null);
        return this;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        if (this.categories != null) {
            this.categories.forEach(i -> i.setFieldAgentSkillRule(null));
        }
        if (categories != null) {
            categories.forEach(i -> i.setFieldAgentSkillRule(this));
        }
        this.categories = categories;
    }

    public FieldAgentSkillRule categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public FieldAgentSkillRule addCategory(Category category) {
        this.categories.add(category);
        category.setFieldAgentSkillRule(this);
        return this;
    }

    public FieldAgentSkillRule removeCategory(Category category) {
        this.categories.remove(category);
        category.setFieldAgentSkillRule(null);
        return this;
    }

    public Set<Pincode> getPincodes() {
        return this.pincodes;
    }

    public void setPincodes(Set<Pincode> pincodes) {
        if (this.pincodes != null) {
            this.pincodes.forEach(i -> i.setFieldAgentSkillRule(null));
        }
        if (pincodes != null) {
            pincodes.forEach(i -> i.setFieldAgentSkillRule(this));
        }
        this.pincodes = pincodes;
    }

    public FieldAgentSkillRule pincodes(Set<Pincode> pincodes) {
        this.setPincodes(pincodes);
        return this;
    }

    public FieldAgentSkillRule addPincode(Pincode pincode) {
        this.pincodes.add(pincode);
        pincode.setFieldAgentSkillRule(this);
        return this;
    }

    public FieldAgentSkillRule removePincode(Pincode pincode) {
        this.pincodes.remove(pincode);
        pincode.setFieldAgentSkillRule(null);
        return this;
    }

    public Set<LocationMapping> getLocationMappings() {
        return this.locationMappings;
    }

    public void setLocationMappings(Set<LocationMapping> locationMappings) {
        if (this.locationMappings != null) {
            this.locationMappings.forEach(i -> i.setFieldAgentSkillRule(null));
        }
        if (locationMappings != null) {
            locationMappings.forEach(i -> i.setFieldAgentSkillRule(this));
        }
        this.locationMappings = locationMappings;
    }

    public FieldAgentSkillRule locationMappings(Set<LocationMapping> locationMappings) {
        this.setLocationMappings(locationMappings);
        return this;
    }

    public FieldAgentSkillRule addLocationMapping(LocationMapping locationMapping) {
        this.locationMappings.add(locationMapping);
        locationMapping.setFieldAgentSkillRule(this);
        return this;
    }

    public FieldAgentSkillRule removeLocationMapping(LocationMapping locationMapping) {
        this.locationMappings.remove(locationMapping);
        locationMapping.setFieldAgentSkillRule(null);
        return this;
    }

    public Set<LanguageMapping> getLanguageMappings() {
        return this.languageMappings;
    }

    public void setLanguageMappings(Set<LanguageMapping> languageMappings) {
        if (this.languageMappings != null) {
            this.languageMappings.forEach(i -> i.setFieldAgentSkillRule(null));
        }
        if (languageMappings != null) {
            languageMappings.forEach(i -> i.setFieldAgentSkillRule(this));
        }
        this.languageMappings = languageMappings;
    }

    public FieldAgentSkillRule languageMappings(Set<LanguageMapping> languageMappings) {
        this.setLanguageMappings(languageMappings);
        return this;
    }

    public FieldAgentSkillRule addLanguageMapping(LanguageMapping languageMapping) {
        this.languageMappings.add(languageMapping);
        languageMapping.setFieldAgentSkillRule(this);
        return this;
    }

    public FieldAgentSkillRule removeLanguageMapping(LanguageMapping languageMapping) {
        this.languageMappings.remove(languageMapping);
        languageMapping.setFieldAgentSkillRule(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setFieldAgentSkillRule(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setFieldAgentSkillRule(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public FieldAgentSkillRule additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public FieldAgentSkillRule addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setFieldAgentSkillRule(this);
        return this;
    }

    public FieldAgentSkillRule removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setFieldAgentSkillRule(null);
        return this;
    }

    public FieldAgentSkillRuleSet getFieldAgentSkillRuleSet() {
        return this.fieldAgentSkillRuleSet;
    }

    public void setFieldAgentSkillRuleSet(FieldAgentSkillRuleSet fieldAgentSkillRuleSet) {
        this.fieldAgentSkillRuleSet = fieldAgentSkillRuleSet;
    }

    public FieldAgentSkillRule fieldAgentSkillRuleSet(FieldAgentSkillRuleSet fieldAgentSkillRuleSet) {
        this.setFieldAgentSkillRuleSet(fieldAgentSkillRuleSet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldAgentSkillRule)) {
            return false;
        }
        return getId() != null && getId().equals(((FieldAgentSkillRule) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldAgentSkillRule{" +
            "id=" + getId() +
            ", skillType='" + getSkillType() + "'" +
            ", joinType='" + getJoinType() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
