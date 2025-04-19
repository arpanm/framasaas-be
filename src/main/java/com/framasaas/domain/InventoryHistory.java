package com.framasaas.domain;

import com.framasaas.domain.enumeration.InventoryChangeReason;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InventoryHistory.
 */
@Entity
@Table(name = "inventory_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InventoryHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "inventory_value", nullable = false)
    private Long inventoryValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    private InventoryChangeReason reason;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @NotNull
    @Column(name = "updated_time", nullable = false)
    private Instant updatedTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InventoryHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInventoryValue() {
        return this.inventoryValue;
    }

    public InventoryHistory inventoryValue(Long inventoryValue) {
        this.setInventoryValue(inventoryValue);
        return this;
    }

    public void setInventoryValue(Long inventoryValue) {
        this.inventoryValue = inventoryValue;
    }

    public InventoryChangeReason getReason() {
        return this.reason;
    }

    public InventoryHistory reason(InventoryChangeReason reason) {
        this.setReason(reason);
        return this;
    }

    public void setReason(InventoryChangeReason reason) {
        this.reason = reason;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public InventoryHistory updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public InventoryHistory updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((InventoryHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryHistory{" +
            "id=" + getId() +
            ", inventoryValue=" + getInventoryValue() +
            ", reason='" + getReason() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
