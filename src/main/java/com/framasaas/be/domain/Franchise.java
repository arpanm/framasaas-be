package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.FranchiseStatus;
import com.framasaas.be.domain.enumeration.PerformanceTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "performance_tag")
    private PerformanceTag performanceTag;

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

    @JsonIgnoreProperties(value = { "location", "franchise", "customer" }, allowSetters = true)
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
    private Set<FranchisePerformanceHistory> franchisePerformanceHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchise" }, allowSetters = true)
    private Set<FranchiseBrandMapping> brands = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchise" }, allowSetters = true)
    private Set<FranchiseCategoryMapping> categories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchise" }, allowSetters = true)
    private Set<FranchiseDocument> documents = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "franchise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "franchiseUserStatusHistories", "franchise" }, allowSetters = true)
    private Set<FranchiseUser> franchiseUsers = new HashSet<>();

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

    public PerformanceTag getPerformanceTag() {
        return this.performanceTag;
    }

    public Franchise performanceTag(PerformanceTag performanceTag) {
        this.setPerformanceTag(performanceTag);
        return this;
    }

    public void setPerformanceTag(PerformanceTag performanceTag) {
        this.performanceTag = performanceTag;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Franchise createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Franchise createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Franchise updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Franchise updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
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

    public Set<FranchisePerformanceHistory> getFranchisePerformanceHistories() {
        return this.franchisePerformanceHistories;
    }

    public void setFranchisePerformanceHistories(Set<FranchisePerformanceHistory> franchisePerformanceHistories) {
        if (this.franchisePerformanceHistories != null) {
            this.franchisePerformanceHistories.forEach(i -> i.setFranchise(null));
        }
        if (franchisePerformanceHistories != null) {
            franchisePerformanceHistories.forEach(i -> i.setFranchise(this));
        }
        this.franchisePerformanceHistories = franchisePerformanceHistories;
    }

    public Franchise franchisePerformanceHistories(Set<FranchisePerformanceHistory> franchisePerformanceHistories) {
        this.setFranchisePerformanceHistories(franchisePerformanceHistories);
        return this;
    }

    public Franchise addFranchisePerformanceHistory(FranchisePerformanceHistory franchisePerformanceHistory) {
        this.franchisePerformanceHistories.add(franchisePerformanceHistory);
        franchisePerformanceHistory.setFranchise(this);
        return this;
    }

    public Franchise removeFranchisePerformanceHistory(FranchisePerformanceHistory franchisePerformanceHistory) {
        this.franchisePerformanceHistories.remove(franchisePerformanceHistory);
        franchisePerformanceHistory.setFranchise(null);
        return this;
    }

    public Set<FranchiseBrandMapping> getBrands() {
        return this.brands;
    }

    public void setBrands(Set<FranchiseBrandMapping> franchiseBrandMappings) {
        if (this.brands != null) {
            this.brands.forEach(i -> i.setFranchise(null));
        }
        if (franchiseBrandMappings != null) {
            franchiseBrandMappings.forEach(i -> i.setFranchise(this));
        }
        this.brands = franchiseBrandMappings;
    }

    public Franchise brands(Set<FranchiseBrandMapping> franchiseBrandMappings) {
        this.setBrands(franchiseBrandMappings);
        return this;
    }

    public Franchise addBrands(FranchiseBrandMapping franchiseBrandMapping) {
        this.brands.add(franchiseBrandMapping);
        franchiseBrandMapping.setFranchise(this);
        return this;
    }

    public Franchise removeBrands(FranchiseBrandMapping franchiseBrandMapping) {
        this.brands.remove(franchiseBrandMapping);
        franchiseBrandMapping.setFranchise(null);
        return this;
    }

    public Set<FranchiseCategoryMapping> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<FranchiseCategoryMapping> franchiseCategoryMappings) {
        if (this.categories != null) {
            this.categories.forEach(i -> i.setFranchise(null));
        }
        if (franchiseCategoryMappings != null) {
            franchiseCategoryMappings.forEach(i -> i.setFranchise(this));
        }
        this.categories = franchiseCategoryMappings;
    }

    public Franchise categories(Set<FranchiseCategoryMapping> franchiseCategoryMappings) {
        this.setCategories(franchiseCategoryMappings);
        return this;
    }

    public Franchise addCategories(FranchiseCategoryMapping franchiseCategoryMapping) {
        this.categories.add(franchiseCategoryMapping);
        franchiseCategoryMapping.setFranchise(this);
        return this;
    }

    public Franchise removeCategories(FranchiseCategoryMapping franchiseCategoryMapping) {
        this.categories.remove(franchiseCategoryMapping);
        franchiseCategoryMapping.setFranchise(null);
        return this;
    }

    public Set<FranchiseDocument> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set<FranchiseDocument> franchiseDocuments) {
        if (this.documents != null) {
            this.documents.forEach(i -> i.setFranchise(null));
        }
        if (franchiseDocuments != null) {
            franchiseDocuments.forEach(i -> i.setFranchise(this));
        }
        this.documents = franchiseDocuments;
    }

    public Franchise documents(Set<FranchiseDocument> franchiseDocuments) {
        this.setDocuments(franchiseDocuments);
        return this;
    }

    public Franchise addDocuments(FranchiseDocument franchiseDocument) {
        this.documents.add(franchiseDocument);
        franchiseDocument.setFranchise(this);
        return this;
    }

    public Franchise removeDocuments(FranchiseDocument franchiseDocument) {
        this.documents.remove(franchiseDocument);
        franchiseDocument.setFranchise(null);
        return this;
    }

    public Set<FranchiseUser> getFranchiseUsers() {
        return this.franchiseUsers;
    }

    public void setFranchiseUsers(Set<FranchiseUser> franchiseUsers) {
        if (this.franchiseUsers != null) {
            this.franchiseUsers.forEach(i -> i.setFranchise(null));
        }
        if (franchiseUsers != null) {
            franchiseUsers.forEach(i -> i.setFranchise(this));
        }
        this.franchiseUsers = franchiseUsers;
    }

    public Franchise franchiseUsers(Set<FranchiseUser> franchiseUsers) {
        this.setFranchiseUsers(franchiseUsers);
        return this;
    }

    public Franchise addFranchiseUser(FranchiseUser franchiseUser) {
        this.franchiseUsers.add(franchiseUser);
        franchiseUser.setFranchise(this);
        return this;
    }

    public Franchise removeFranchiseUser(FranchiseUser franchiseUser) {
        this.franchiseUsers.remove(franchiseUser);
        franchiseUser.setFranchise(null);
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
            ", performanceTag='" + getPerformanceTag() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
