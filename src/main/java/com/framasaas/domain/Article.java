package com.framasaas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "vendor_article_id")
    private String vendorArticleId;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "puchase_price")
    private Float puchasePrice;

    @Column(name = "purchase_store")
    private String purchaseStore;

    @Column(name = "invoice_path")
    private String invoicePath;

    @Column(name = "is_validated")
    private Boolean isValidated;

    @Column(name = "validated_by")
    private String validatedBy;

    @Column(name = "validated_time")
    private Instant validatedTime;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "additionalAttributes", "franchise", "article", "articleWarranty", "serviceOrder" },
        allowSetters = true
    )
    private Set<SupportingDocument> supportingDocuments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supportingDocuments", "additionalAttributes", "article", "warrantyMaster" }, allowSetters = true)
    private Set<ArticleWarrantyDetails> articleWarrantyDetails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "supportingDocuments",
            "serviceOrderFranchiseAssignments",
            "serviceOrderSpares",
            "additionalAttributes",
            "customer",
            "serviceMaster",
            "article",
            "address",
        },
        allowSetters = true
    )
    private Set<ServiceOrder> serviceOrders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
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
            "supportDocument",
            "product",
            "hsn",
            "priceHistory",
            "warrantyMaster",
            "warrantyMasterPriceHistory",
            "article",
            "articleWarranty",
            "serviceOrder",
            "serviceOrderPayment",
            "serviceOrderFranchiseAssignment",
            "serviceOrderFieldAgentAssignment",
            "franchiseAllocationRuleSet",
            "franchiseAllocationRule",
            "fieldAgentSkillRuleSet",
            "fieldAgentSkillRule",
            "inventoryLocation",
            "inventory",
            "document",
            "articleWarrantyDocument",
            "serviceOrderAssignment",
        },
        allowSetters = true
    )
    private Set<AdditionalAttribute> additionalAttributes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "productPriceHistories",
            "warrantyMasters",
            "articles",
            "serviceOrderMasters",
            "serviceOrderSpares",
            "inventories",
            "additionalAttributes",
            "category",
            "brand",
            "hsn",
            "coveredUnderWarranties",
        },
        allowSetters = true
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses", "articles", "serviceOrders", "additionalAttributes" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Article id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public Article serialNo(String serialNo) {
        this.setSerialNo(serialNo);
        return this;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getVendorArticleId() {
        return this.vendorArticleId;
    }

    public Article vendorArticleId(String vendorArticleId) {
        this.setVendorArticleId(vendorArticleId);
        return this;
    }

    public void setVendorArticleId(String vendorArticleId) {
        this.vendorArticleId = vendorArticleId;
    }

    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    public Article purchaseDate(LocalDate purchaseDate) {
        this.setPurchaseDate(purchaseDate);
        return this;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Float getPuchasePrice() {
        return this.puchasePrice;
    }

    public Article puchasePrice(Float puchasePrice) {
        this.setPuchasePrice(puchasePrice);
        return this;
    }

    public void setPuchasePrice(Float puchasePrice) {
        this.puchasePrice = puchasePrice;
    }

    public String getPurchaseStore() {
        return this.purchaseStore;
    }

    public Article purchaseStore(String purchaseStore) {
        this.setPurchaseStore(purchaseStore);
        return this;
    }

    public void setPurchaseStore(String purchaseStore) {
        this.purchaseStore = purchaseStore;
    }

    public String getInvoicePath() {
        return this.invoicePath;
    }

    public Article invoicePath(String invoicePath) {
        this.setInvoicePath(invoicePath);
        return this;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }

    public Boolean getIsValidated() {
        return this.isValidated;
    }

    public Article isValidated(Boolean isValidated) {
        this.setIsValidated(isValidated);
        return this;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public String getValidatedBy() {
        return this.validatedBy;
    }

    public Article validatedBy(String validatedBy) {
        this.setValidatedBy(validatedBy);
        return this;
    }

    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }

    public Instant getValidatedTime() {
        return this.validatedTime;
    }

    public Article validatedTime(Instant validatedTime) {
        this.setValidatedTime(validatedTime);
        return this;
    }

    public void setValidatedTime(Instant validatedTime) {
        this.validatedTime = validatedTime;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Article createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Article createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Article updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Article updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<SupportingDocument> getSupportingDocuments() {
        return this.supportingDocuments;
    }

    public void setSupportingDocuments(Set<SupportingDocument> supportingDocuments) {
        if (this.supportingDocuments != null) {
            this.supportingDocuments.forEach(i -> i.setArticle(null));
        }
        if (supportingDocuments != null) {
            supportingDocuments.forEach(i -> i.setArticle(this));
        }
        this.supportingDocuments = supportingDocuments;
    }

    public Article supportingDocuments(Set<SupportingDocument> supportingDocuments) {
        this.setSupportingDocuments(supportingDocuments);
        return this;
    }

    public Article addSupportingDocument(SupportingDocument supportingDocument) {
        this.supportingDocuments.add(supportingDocument);
        supportingDocument.setArticle(this);
        return this;
    }

    public Article removeSupportingDocument(SupportingDocument supportingDocument) {
        this.supportingDocuments.remove(supportingDocument);
        supportingDocument.setArticle(null);
        return this;
    }

    public Set<ArticleWarrantyDetails> getArticleWarrantyDetails() {
        return this.articleWarrantyDetails;
    }

    public void setArticleWarrantyDetails(Set<ArticleWarrantyDetails> articleWarrantyDetails) {
        if (this.articleWarrantyDetails != null) {
            this.articleWarrantyDetails.forEach(i -> i.setArticle(null));
        }
        if (articleWarrantyDetails != null) {
            articleWarrantyDetails.forEach(i -> i.setArticle(this));
        }
        this.articleWarrantyDetails = articleWarrantyDetails;
    }

    public Article articleWarrantyDetails(Set<ArticleWarrantyDetails> articleWarrantyDetails) {
        this.setArticleWarrantyDetails(articleWarrantyDetails);
        return this;
    }

    public Article addArticleWarrantyDetails(ArticleWarrantyDetails articleWarrantyDetails) {
        this.articleWarrantyDetails.add(articleWarrantyDetails);
        articleWarrantyDetails.setArticle(this);
        return this;
    }

    public Article removeArticleWarrantyDetails(ArticleWarrantyDetails articleWarrantyDetails) {
        this.articleWarrantyDetails.remove(articleWarrantyDetails);
        articleWarrantyDetails.setArticle(null);
        return this;
    }

    public Set<ServiceOrder> getServiceOrders() {
        return this.serviceOrders;
    }

    public void setServiceOrders(Set<ServiceOrder> serviceOrders) {
        if (this.serviceOrders != null) {
            this.serviceOrders.forEach(i -> i.setArticle(null));
        }
        if (serviceOrders != null) {
            serviceOrders.forEach(i -> i.setArticle(this));
        }
        this.serviceOrders = serviceOrders;
    }

    public Article serviceOrders(Set<ServiceOrder> serviceOrders) {
        this.setServiceOrders(serviceOrders);
        return this;
    }

    public Article addServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrders.add(serviceOrder);
        serviceOrder.setArticle(this);
        return this;
    }

    public Article removeServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrders.remove(serviceOrder);
        serviceOrder.setArticle(null);
        return this;
    }

    public Set<AdditionalAttribute> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void setAdditionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        if (this.additionalAttributes != null) {
            this.additionalAttributes.forEach(i -> i.setArticle(null));
        }
        if (additionalAttributes != null) {
            additionalAttributes.forEach(i -> i.setArticle(this));
        }
        this.additionalAttributes = additionalAttributes;
    }

    public Article additionalAttributes(Set<AdditionalAttribute> additionalAttributes) {
        this.setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public Article addAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.add(additionalAttribute);
        additionalAttribute.setArticle(this);
        return this;
    }

    public Article removeAdditionalAttribute(AdditionalAttribute additionalAttribute) {
        this.additionalAttributes.remove(additionalAttribute);
        additionalAttribute.setArticle(null);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Article product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Article customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return getId() != null && getId().equals(((Article) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", serialNo='" + getSerialNo() + "'" +
            ", vendorArticleId='" + getVendorArticleId() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", puchasePrice=" + getPuchasePrice() +
            ", purchaseStore='" + getPurchaseStore() + "'" +
            ", invoicePath='" + getInvoicePath() + "'" +
            ", isValidated='" + getIsValidated() + "'" +
            ", validatedBy='" + getValidatedBy() + "'" +
            ", validatedTime='" + getValidatedTime() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
