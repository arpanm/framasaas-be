package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.Language;
import com.framasaas.be.domain.enumeration.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @NotNull
    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @Column(name = "contact", nullable = false)
    private Long contact;

    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @Column(name = "altername_contact")
    private Long alternameContact;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "serviceOrders", "additionalAttributes", "location", "franchise", "customer" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "articleWarrantyDetails", "serviceOrders", "additionalAttributes", "product", "customer" },
        allowSetters = true
    )
    private Set<Article> articles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "serviceOrderAssignments", "additionalAttributes", "customer", "article", "address" },
        allowSetters = true
    )
    private Set<ServiceOrder> serviceOrders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
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
            "serviceOrderAssignment",
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public Customer email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContact() {
        return this.contact;
    }

    public Customer contact(Long contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public Long getAlternameContact() {
        return this.alternameContact;
    }

    public Customer alternameContact(Long alternameContact) {
        this.setAlternameContact(alternameContact);
        return this;
    }

    public void setAlternameContact(Long alternameContact) {
        this.alternameContact = alternameContact;
    }

    public Language getLanguage() {
        return this.language;
    }

    public Customer language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public UserStatus getUserStatus() {
        return this.userStatus;
    }

    public Customer userStatus(UserStatus userStatus) {
        this.setUserStatus(userStatus);
        return this;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Customer createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Customer createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Customer updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Customer updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setCustomer(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setCustomer(this));
        }
        this.addresses = addresses;
    }

    public Customer addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Customer addAddress(Address address) {
        this.addresses.add(address);
        address.setCustomer(this);
        return this;
    }

    public Customer removeAddress(Address address) {
        this.addresses.remove(address);
        address.setCustomer(null);
        return this;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setCustomer(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setCustomer(this));
        }
        this.articles = articles;
    }

    public Customer articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public Customer addArticle(Article article) {
        this.articles.add(article);
        article.setCustomer(this);
        return this;
    }

    public Customer removeArticle(Article article) {
        this.articles.remove(article);
        article.setCustomer(null);
        return this;
    }

    public Set<ServiceOrder> getServiceOrders() {
        return this.serviceOrders;
    }

    public void setServiceOrders(Set<ServiceOrder> serviceOrders) {
        if (this.serviceOrders != null) {
            this.serviceOrders.forEach(i -> i.setCustomer(null));
        }
        if (serviceOrders != null) {
            serviceOrders.forEach(i -> i.setCustomer(this));
        }
        this.serviceOrders = serviceOrders;
    }

    public Customer serviceOrders(Set<ServiceOrder> serviceOrders) {
        this.setServiceOrders(serviceOrders);
        return this;
    }

    public Customer addServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrders.add(serviceOrder);
        serviceOrder.setCustomer(this);
        return this;
    }

    public Customer removeServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrders.remove(serviceOrder);
        serviceOrder.setCustomer(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setCustomer(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setCustomer(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public Customer additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public Customer addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setCustomer(this);
        return this;
    }

    public Customer removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setCustomer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return getId() != null && getId().equals(((Customer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", contact=" + getContact() +
            ", alternameContact=" + getAlternameContact() +
            ", language='" + getLanguage() + "'" +
            ", userStatus='" + getUserStatus() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
