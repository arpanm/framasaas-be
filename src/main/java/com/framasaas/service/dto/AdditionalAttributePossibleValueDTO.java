package com.framasaas.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.AdditionalAttributePossibleValue} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdditionalAttributePossibleValueDTO implements Serializable {

    private Long id;

    private String possibleValue;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private AdditionalAttributeDTO attribute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPossibleValue() {
        return possibleValue;
    }

    public void setPossibleValue(String possibleValue) {
        this.possibleValue = possibleValue;
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

    public AdditionalAttributeDTO getAttribute() {
        return attribute;
    }

    public void setAttribute(AdditionalAttributeDTO attribute) {
        this.attribute = attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalAttributePossibleValueDTO)) {
            return false;
        }

        AdditionalAttributePossibleValueDTO additionalAttributePossibleValueDTO = (AdditionalAttributePossibleValueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, additionalAttributePossibleValueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalAttributePossibleValueDTO{" +
            "id=" + getId() +
            ", possibleValue='" + getPossibleValue() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", attribute=" + getAttribute() +
            "}";
    }
}
