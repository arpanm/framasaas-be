package com.framasaas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.domain.enumeration.FranchiseUserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FranchiseUserStatusHistory.
 */
@Entity
@Table(name = "franchise_user_status_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchiseUserStatusHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_satus", nullable = false)
    private FranchiseUserStatus userSatus;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @NotNull
    @Column(name = "updated_time", nullable = false)
    private Instant updatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "franchiseUserStatusHistories", "additionalAttributes", "franchise", "skillRuleSet" },
        allowSetters = true
    )
    private FranchiseUser franchiseUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FranchiseUserStatusHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FranchiseUserStatus getUserSatus() {
        return this.userSatus;
    }

    public FranchiseUserStatusHistory userSatus(FranchiseUserStatus userSatus) {
        this.setUserSatus(userSatus);
        return this;
    }

    public void setUserSatus(FranchiseUserStatus userSatus) {
        this.userSatus = userSatus;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FranchiseUserStatusHistory updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public FranchiseUserStatusHistory updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public FranchiseUser getFranchiseUser() {
        return this.franchiseUser;
    }

    public void setFranchiseUser(FranchiseUser franchiseUser) {
        this.franchiseUser = franchiseUser;
    }

    public FranchiseUserStatusHistory franchiseUser(FranchiseUser franchiseUser) {
        this.setFranchiseUser(franchiseUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchiseUserStatusHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((FranchiseUserStatusHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchiseUserStatusHistory{" +
            "id=" + getId() +
            ", userSatus='" + getUserSatus() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
