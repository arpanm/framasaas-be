package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.Language;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.LanguageMapping} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LanguageMappingDTO implements Serializable {

    private Long id;

    @NotNull
    private Language lang;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private FranchiseAllocationRuleDTO franchiseRule;

    private FieldAgentSkillRuleDTO fieldAgentSkillRule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public String getCreateddBy() {
        return createddBy;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public FranchiseAllocationRuleDTO getFranchiseRule() {
        return franchiseRule;
    }

    public void setFranchiseRule(FranchiseAllocationRuleDTO franchiseRule) {
        this.franchiseRule = franchiseRule;
    }

    public FieldAgentSkillRuleDTO getFieldAgentSkillRule() {
        return fieldAgentSkillRule;
    }

    public void setFieldAgentSkillRule(FieldAgentSkillRuleDTO fieldAgentSkillRule) {
        this.fieldAgentSkillRule = fieldAgentSkillRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LanguageMappingDTO)) {
            return false;
        }

        LanguageMappingDTO languageMappingDTO = (LanguageMappingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, languageMappingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LanguageMappingDTO{" +
            "id=" + getId() +
            ", lang='" + getLang() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", franchiseRule=" + getFranchiseRule() +
            ", fieldAgentSkillRule=" + getFieldAgentSkillRule() +
            "}";
    }
}
