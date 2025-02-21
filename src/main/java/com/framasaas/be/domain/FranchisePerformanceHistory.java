package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.PerformanceTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FranchisePerformanceHistory.
 */
@Entity
@Table(name = "franchise_performance_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FranchisePerformanceHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "performance_score")
    private Float performanceScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "performance_tag")
    private PerformanceTag performanceTag;

    @Column(name = "updated_by")
    private String updatedBy;

    @NotNull
    @Column(name = "updated_time", nullable = false)
    private LocalTime updatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "address", "franchiseStatusHistories", "franchisePerformanceHistories", "brands", "categories", "documents" },
        allowSetters = true
    )
    private Franchise franchise;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FranchisePerformanceHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPerformanceScore() {
        return this.performanceScore;
    }

    public FranchisePerformanceHistory performanceScore(Float performanceScore) {
        this.setPerformanceScore(performanceScore);
        return this;
    }

    public void setPerformanceScore(Float performanceScore) {
        this.performanceScore = performanceScore;
    }

    public PerformanceTag getPerformanceTag() {
        return this.performanceTag;
    }

    public FranchisePerformanceHistory performanceTag(PerformanceTag performanceTag) {
        this.setPerformanceTag(performanceTag);
        return this;
    }

    public void setPerformanceTag(PerformanceTag performanceTag) {
        this.performanceTag = performanceTag;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FranchisePerformanceHistory updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalTime getUpdatedTime() {
        return this.updatedTime;
    }

    public FranchisePerformanceHistory updatedTime(LocalTime updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(LocalTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Franchise getFranchise() {
        return this.franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public FranchisePerformanceHistory franchise(Franchise franchise) {
        this.setFranchise(franchise);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FranchisePerformanceHistory)) {
            return false;
        }
        return getId() != null && getId().equals(((FranchisePerformanceHistory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FranchisePerformanceHistory{" +
            "id=" + getId() +
            ", performanceScore=" + getPerformanceScore() +
            ", performanceTag='" + getPerformanceTag() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
