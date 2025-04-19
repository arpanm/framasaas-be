package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.InventoryChangeReason;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.InventoryHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InventoryHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private Long inventoryValue;

    @NotNull
    private InventoryChangeReason reason;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInventoryValue() {
        return inventoryValue;
    }

    public void setInventoryValue(Long inventoryValue) {
        this.inventoryValue = inventoryValue;
    }

    public InventoryChangeReason getReason() {
        return reason;
    }

    public void setReason(InventoryChangeReason reason) {
        this.reason = reason;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryHistoryDTO)) {
            return false;
        }

        InventoryHistoryDTO inventoryHistoryDTO = (InventoryHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inventoryHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryHistoryDTO{" +
            "id=" + getId() +
            ", inventoryValue=" + getInventoryValue() +
            ", reason='" + getReason() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
