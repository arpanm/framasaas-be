package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.FranchiseStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Franchise.
 */
@Entity
@Table(name = "franchise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Franchise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "franchise_name", nullable = false)
    private String franchiseName;

    @Column(name = "owner")
    private String owner;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @Column(name = "contact", nullable = false)
    private Long contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "franchise_status")
    private FranchiseStatus franchiseStatus;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "performance_score")
    private Float performanceScore;

    @JsonIgnoreProperties(value = { "location", "franchise" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchise" }, allowSetters = true)
    private Set<FranchiseStatusHistory> franchiseStatusHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchise" }, allowSetters = true)
    private Set<FranchiseBrandMapping> franchiseBrandMappings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchise" }, allowSetters = true)
    private Set<FranchiseCategoryMapping> franchiseCategoryMappings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchise" }, allowSetters = true)
    private Set<FranchiseDocument> franchiseDocuments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Franchise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFranchiseName() {
        return this.franchiseName;
    }

    public Franchise franchiseName(String franchiseName) {
        this.setFranchiseName(franchiseName);
        return this;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public String getOwner() {
        return this.owner;
    }

    public Franchise owner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEmail() {
        return this.email;
    }

    public Franchise email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContact() {
        return this.contact;
    }

    public Franchise contact(Long contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public FranchiseStatus getFranchiseStatus() {
        return this.franchiseStatus;
    }

    public Franchise franchiseStatus(FranchiseStatus franchiseStatus) {
        this.setFranchiseStatus(franchiseStatus);
        return this;
    }

    public void setFranchiseStatus(FranchiseStatus franchiseStatus) {
        this.franchiseStatus = franchiseStatus;
    }

    public String getGstNumber() {
        return this.gstNumber;
    }

    public Franchise gstNumber(String gstNumber) {
        this.setGstNumber(gstNumber);
        return this;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public Franchise registrationNumber(String registrationNumber) {
        this.setRegistrationNumber(registrationNumber);
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Float getPerformanceScore() {
        return this.performanceScore;
    }

    public Franchise performanceScore(Float performanceScore) {
        this.setPerformanceScore(performanceScore);
        return this;
    }

    public void setPerformanceScore(Float performanceScore) {
        this.performanceScore = performanceScore;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Franchise address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Set<FranchiseStatusHistory> getFranchiseStatusHistories() {
        return this.franchiseStatusHistories;
    }

    public void setFranchiseStatusHistories(Set<FranchiseStatusHistory> franchiseStatusHistories) {
        if (this.franchiseStatusHistories != null) {
            this.franchiseStatusHistories.forEach(i -> i.setFranchise(null));
        }
        if (franchiseStatusHistories != null) {
            franchiseStatusHistories.forEach(i -> i.setFranchise(this));
        }
        this.franchiseStatusHistories = franchiseStatusHistories;
    }

    public Franchise franchiseStatusHistories(Set<FranchiseStatusHistory> franchiseStatusHistories) {
        this.setFranchiseStatusHistories(franchiseStatusHistories);
        return this;
    }

    public Franchise addFranchiseStatusHistory(FranchiseStatusHistory franchiseStatusHistory) {
        this.franchiseStatusHistories.add(franchiseStatusHistory);
        franchiseStatusHistory.setFranchise(this);
        return this;
    }

    public Franchise removeFranchiseStatusHistory(FranchiseStatusHistory franchiseStatusHistory) {
        this.franchiseStatusHistories.remove(franchiseStatusHistory);
        franchiseStatusHistory.setFranchise(null);
        return this;
    }

    public Set<FranchiseBrandMapping> getFranchiseBrandMappings() {
        return this.franchiseBrandMappings;
    }

    public void setFranchiseBrandMappings(Set<FranchiseBrandMapping> franchiseBrandMappings) {
        if (this.franchiseBrandMappings != null) {
            this.franchiseBrandMappings.forEach(i -> i.setFranchise(null));
        }
        if (franchiseBrandMappings != null) {
            franchiseBrandMappings.forEach(i -> i.setFranchise(this));
        }
        this.franchiseBrandMappings = franchiseBrandMappings;
    }

    public Franchise franchiseBrandMappings(Set<FranchiseBrandMapping> franchiseBrandMappings) {
        this.setFranchiseBrandMappings(franchiseBrandMappings);
        return this;
    }

    public Franchise addFranchiseBrandMapping(FranchiseBrandMapping franchiseBrandMapping) {
        this.franchiseBrandMappings.add(franchiseBrandMapping);
        franchiseBrandMapping.setFranchise(this);
        return this;
    }

    public Franchise removeFranchiseBrandMapping(FranchiseBrandMapping franchiseBrandMapping) {
        this.franchiseBrandMappings.remove(franchiseBrandMapping);
        franchiseBrandMapping.setFranchise(null);
        return this;
    }

    public Set<FranchiseCategoryMapping> getFranchiseCategoryMappings() {
        return this.franchiseCategoryMappings;
    }

    public void setFranchiseCategoryMappings(Set<FranchiseCategoryMapping> franchiseCategoryMappings) {
        if (this.franchiseCategoryMappings != null) {
            this.franchiseCategoryMappings.forEach(i -> i.setFranchise(null));
        }
        if (franchiseCategoryMappings != null) {
            franchiseCategoryMappings.forEach(i -> i.setFranchise(this));
        }
        this.franchiseCategoryMappings = franchiseCategoryMappings;
    }

    public Franchise franchiseCategoryMappings(Set<FranchiseCategoryMapping> franchiseCategoryMappings) {
        this.setFranchiseCategoryMappings(franchiseCategoryMappings);
        return this;
    }

    public Franchise addFranchiseCategoryMapping(FranchiseCategoryMapping franchiseCategoryMapping) {
        this.franchiseCategoryMappings.add(franchiseCategoryMapping);
        franchiseCategoryMapping.setFranchise(this);
        return this;
    }

    public Franchise removeFranchiseCategoryMapping(FranchiseCategoryMapping franchiseCategoryMapping) {
        this.franchiseCategoryMappings.remove(franchiseCategoryMapping);
        franchiseCategoryMapping.setFranchise(null);
        return this;
    }

    public Set<FranchiseDocument> getFranchiseDocuments() {
        return this.franchiseDocuments;
    }

    public void setFranchiseDocuments(Set<FranchiseDocument> franchiseDocuments) {
        if (this.franchiseDocuments != null) {
            this.franchiseDocuments.forEach(i -> i.setFranchise(null));
        }
        if (franchiseDocuments != null) {
            franchiseDocuments.forEach(i -> i.setFranchise(this));
        }
        this.franchiseDocuments = franchiseDocuments;
    }

    public Franchise franchiseDocuments(Set<FranchiseDocument> franchiseDocuments) {
        this.setFranchiseDocuments(franchiseDocuments);
        return this;
    }

    public Franchise addFranchiseDocument(FranchiseDocument franchiseDocument) {
        this.franchiseDocuments.add(franchiseDocument);
        franchiseDocument.setFranchise(this);
        return this;
    }

    public Franchise removeFranchiseDocument(FranchiseDocument franchiseDocument) {
        this.franchiseDocuments.remove(franchiseDocument);
        franchiseDocument.setFranchise(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Franchise)) {
            return false;
        }
        return getId() != null && getId().equals(((Franchise) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Franchise{" +
            "id=" + getId() +
            ", franchiseName='" + getFranchiseName() + "'" +
            ", owner='" + getOwner() + "'" +
            ", email='" + getEmail() + "'" +
            ", contact=" + getContact() +
            ", franchiseStatus='" + getFranchiseStatus() + "'" +
            ", gstNumber='" + getGstNumber() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", performanceScore=" + getPerformanceScore() +
            "}";
    }
}
