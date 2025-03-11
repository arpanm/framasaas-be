package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.AttributeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdditionalAttribute.
 */
@Entity
@Table(name = "additional_attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdditionalAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "attribute_name", nullable = false)
    private String attributeName;

    @NotNull
    @Column(name = "attribute_value", nullable = false)
    private String attributeValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "attribute_type")
    private AttributeType attributeType;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attribute")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "attribute" }, allowSetters = true)
    private Set<AdditionalAttributePossibleValue> additionalAttributePossibleValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdditionalAttribute id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public AdditionalAttribute attributeName(String attributeName) {
        this.setAttributeName(attributeName);
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public AdditionalAttribute attributeValue(String attributeValue) {
        this.setAttributeValue(attributeValue);
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public AttributeType getAttributeType() {
        return this.attributeType;
    }

    public AdditionalAttribute attributeType(AttributeType attributeType) {
        this.setAttributeType(attributeType);
        return this;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public AdditionalAttribute createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public AdditionalAttribute createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public AdditionalAttribute updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public AdditionalAttribute updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<AdditionalAttributePossibleValue> getAdditionalAttributePossibleValues() {
        return this.additionalAttributePossibleValues;
    }

    public void setAdditionalAttributePossibleValues(Set<AdditionalAttributePossibleValue> additionalAttributePossibleValues) {
        if (this.additionalAttributePossibleValues != null) {
            this.additionalAttributePossibleValues.forEach(i -> i.setAttribute(null));
        }
        if (additionalAttributePossibleValues != null) {
            additionalAttributePossibleValues.forEach(i -> i.setAttribute(this));
        }
        this.additionalAttributePossibleValues = additionalAttributePossibleValues;
    }

    public AdditionalAttribute additionalAttributePossibleValues(Set<AdditionalAttributePossibleValue> additionalAttributePossibleValues) {
        this.setAdditionalAttributePossibleValues(additionalAttributePossibleValues);
        return this;
    }

    public AdditionalAttribute addAdditionalAttributePossibleValue(AdditionalAttributePossibleValue additionalAttributePossibleValue) {
        this.additionalAttributePossibleValues.add(additionalAttributePossibleValue);
        additionalAttributePossibleValue.setAttribute(this);
        return this;
    }

    public AdditionalAttribute removeAdditionalAttributePossibleValue(AdditionalAttributePossibleValue additionalAttributePossibleValue) {
        this.additionalAttributePossibleValues.remove(additionalAttributePossibleValue);
        additionalAttributePossibleValue.setAttribute(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalAttribute)) {
            return false;
        }
        return getId() != null && getId().equals(((AdditionalAttribute) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalAttribute{" +
            "id=" + getId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            ", attributeType='" + getAttributeType() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
