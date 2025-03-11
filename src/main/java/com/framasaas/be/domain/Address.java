package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "address_1", nullable = false)
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "area")
    private String area;

    @Column(name = "district")
    private String district;

    @NotNull
    @Column(name = "pincode", nullable = false)
    private Long pincode;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "serviceOrderFranchiseAssignments", "serviceOrderSpares", "additionalAttributes", "customer", "article", "address" },
        allowSetters = true
    )
    private Set<ServiceOrder> serviceOrders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "additionalAttributePossibleValues",
            "franchise",
            "franchiseStatus",
            "franchisePerformance",
            "brand",
            "category",
            "address",
            "location",
            "franchiseUser",
            "customer",
            "document",
            "product",
            "hsn",
            "priceHistory",
            "warrantyMaster",
            "warrantyMasterPriceHistory",
            "article",
            "articleWarranty",
            "articleWarrantyDocument",
            "serviceOrder",
            "serviceOrderPayment",
            "serviceOrderFranchiseAssignment",
            "serviceOrderFieldAgentAssignment",
            "franchiseAllocationRuleSet",
            "franchiseAllocationRule",
            "fieldAgentSkillRuleSet",
            "fieldAgentSkillRule",
            "serviceOrderAssignment",
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private LocationMapping location;

    @JsonIgnoreProperties(
        value = {
            "address",
            "franchiseStatusHistories",
            "franchisePerformanceHistories",
            "franchiseDocuments",
            "franchiseUsers",
            "serviceOrderFranchiseAssignments",
            "additionalAttributes",
            "ruleset",
            "brands",
            "categories",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private Franchise franchise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses", "articles", "serviceOrders", "additionalAttributes" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress1() {
        return this.address1;
    }

    public Address address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public Address address2(String address2) {
        this.setAddress2(address2);
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return this.city;
    }

    public Address city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return this.area;
    }

    public Address area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDistrict() {
        return this.district;
    }

    public Address district(String district) {
        this.setDistrict(district);
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getPincode() {
        return this.pincode;
    }

    public Address pincode(Long pincode) {
        this.setPincode(pincode);
        return this;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return this.state;
    }

    public Address state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public Address country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Address createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Address createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Address updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Address updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<ServiceOrder> getServiceOrders() {
        return this.serviceOrders;
    }

    public void setServiceOrders(Set<ServiceOrder> serviceOrders) {
        if (this.serviceOrders != null) {
            this.serviceOrders.forEach(i -> i.setAddress(null));
        }
        if (serviceOrders != null) {
            serviceOrders.forEach(i -> i.setAddress(this));
        }
        this.serviceOrders = serviceOrders;
    }

    public Address serviceOrders(Set<ServiceOrder> serviceOrders) {
        this.setServiceOrders(serviceOrders);
        return this;
    }

    public Address addServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrders.add(serviceOrder);
        serviceOrder.setAddress(this);
        return this;
    }

    public Address removeServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrders.remove(serviceOrder);
        serviceOrder.setAddress(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setAddress(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setAddress(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public Address additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public Address addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setAddress(this);
        return this;
    }

    public Address removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setAddress(null);
        return this;
    }

    public LocationMapping getLocation() {
        return this.location;
    }

    public void setLocation(LocationMapping locationMapping) {
        this.location = locationMapping;
    }

    public Address location(LocationMapping locationMapping) {
        this.setLocation(locationMapping);
        return this;
    }

    public Franchise getFranchise() {
        return this.franchise;
    }

    public void setFranchise(Franchise franchise) {
        if (this.franchise != null) {
            this.franchise.setAddress(null);
        }
        if (franchise != null) {
            franchise.setAddress(this);
        }
        this.franchise = franchise;
    }

    public Address franchise(Franchise franchise) {
        this.setFranchise(franchise);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return getId() != null && getId().equals(((Address) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", city='" + getCity() + "'" +
            ", area='" + getArea() + "'" +
            ", district='" + getDistrict() + "'" +
            ", pincode=" + getPincode() +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
