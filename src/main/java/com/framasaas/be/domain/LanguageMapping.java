package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.Language;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LanguageMapping.
 */
@Entity
@Table(name = "language_mapping")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LanguageMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "lang", nullable = false)
    private Language lang;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "brands", "categories", "pincodes", "locationMappings", "languageMappings" }, allowSetters = true)
    private FranchiseAllocationRule franchiseRule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "brands", "categories", "pincodes", "locationMappings", "languageMappings", "fieldAgentSkillRuleSet" },
        allowSetters = true
    )
    private FieldAgentSkillRule fieldAgentSkillRule;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LanguageMapping id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLang() {
        return this.lang;
    }

    public LanguageMapping lang(Language lang) {
        this.setLang(lang);
        return this;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public LanguageMapping createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public LanguageMapping createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public LanguageMapping updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public LanguageMapping updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public FranchiseAllocationRule getFranchiseRule() {
        return this.franchiseRule;
    }

    public void setFranchiseRule(FranchiseAllocationRule franchiseAllocationRule) {
        this.franchiseRule = franchiseAllocationRule;
    }

    public LanguageMapping franchiseRule(FranchiseAllocationRule franchiseAllocationRule) {
        this.setFranchiseRule(franchiseAllocationRule);
        return this;
    }

    public FieldAgentSkillRule getFieldAgentSkillRule() {
        return this.fieldAgentSkillRule;
    }

    public void setFieldAgentSkillRule(FieldAgentSkillRule fieldAgentSkillRule) {
        this.fieldAgentSkillRule = fieldAgentSkillRule;
    }

    public LanguageMapping fieldAgentSkillRule(FieldAgentSkillRule fieldAgentSkillRule) {
        this.setFieldAgentSkillRule(fieldAgentSkillRule);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LanguageMapping)) {
            return false;
        }
        return getId() != null && getId().equals(((LanguageMapping) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LanguageMapping{" +
            "id=" + getId() +
            ", lang='" + getLang() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
