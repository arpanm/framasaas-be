package com.framasaas.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.framasaas.be.domain.enumeration.AttributeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdditionalAttribute.
 */
@Entity
@Table(name = "additional_attribute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdditionalAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "attribute_name", nullable = false)
    private String attributeName;

    @NotNull
    @Column(name = "attribute_value", nullable = false)
    private String attributeValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "attribute_type")
    private AttributeType attributeType;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attribute")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "attribute" }, allowSetters = true)
    private Set<AdditionalAttributePossibleValue> additionalAttributePossibleValues = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "address",
            "franchiseStatusHistories",
            "franchisePerformanceHistories",
            "supportingDocuments",
            "franchiseUsers",
            "serviceOrderFranchiseAssignments",
            "additionalAttributes",
            "ruleset",
            "brands",
            "categories",
        },
        allowSetters = true
    )
    private Franchise franchise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "franchise" }, allowSetters = true)
    private FranchiseStatusHistory franchiseStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "franchise" }, allowSetters = true)
    private FranchisePerformanceHistory franchisePerformance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "products", "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "products", "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "serviceOrders", "additionalAttributes", "location", "franchise", "customer" }, allowSetters = true)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "franchiseRule", "fieldAgentSkillRule" }, allowSetters = true)
    private LocationMapping location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "franchiseUserStatusHistories", "additionalAttributes", "franchise", "skillRuleSet" },
        allowSetters = true
    )
    private FranchiseUser franchiseUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses", "articles", "serviceOrders", "additionalAttributes" }, allowSetters = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "franchise", "articleWarranty" }, allowSetters = true)
    private SupportingDocument supportDocument;

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
    @JsonIgnoreProperties(value = { "products", "additionalAttributes" }, allowSetters = true)
    private Hsn hsn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "product" }, allowSetters = true)
    private ProductPriceHistory priceHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "warrantyMasterPriceHistories", "articleWarrantyDetails", "additionalAttributes", "coveredSpares", "product" },
        allowSetters = true
    )
    private WarrantyMaster warrantyMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "warrantyMaster" }, allowSetters = true)
    private WarrantyMasterPriceHistory warrantyMasterPriceHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "articleWarrantyDetails", "serviceOrders", "additionalAttributes", "product", "customer" },
        allowSetters = true
    )
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "supportingDocuments", "additionalAttributes", "article", "warrantyMaster" }, allowSetters = true)
    private ArticleWarrantyDetails articleWarranty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "serviceOrderFranchiseAssignments", "serviceOrderSpares", "additionalAttributes", "customer", "article", "address" },
        allowSetters = true
    )
    private ServiceOrder serviceOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes" }, allowSetters = true)
    private ServiceOrderPayment serviceOrderPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "serviceOrder", "franchise" }, allowSetters = true)
    private ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes" }, allowSetters = true)
    private ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "franchises", "additionalAttributes" }, allowSetters = true)
    private FranchiseAllocationRuleSet franchiseAllocationRuleSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "brands", "categories", "pincodes", "locationMappings", "languageMappings", "additionalAttributes" },
        allowSetters = true
    )
    private FranchiseAllocationRule franchiseAllocationRule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "fieldAgentSkillRules", "franchiseUsers", "additionalAttributes" }, allowSetters = true)
    private FieldAgentSkillRuleSet fieldAgentSkillRuleSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "brands", "categories", "pincodes", "locationMappings", "languageMappings", "additionalAttributes", "fieldAgentSkillRuleSet",
        },
        allowSetters = true
    )
    private FieldAgentSkillRule fieldAgentSkillRule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "inventories", "additionalAttributes" }, allowSetters = true)
    private InventoryLocation inventoryLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "product", "location" }, allowSetters = true)
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "franchise" }, allowSetters = true)
    private FranchiseDocument document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "articleWarranty" }, allowSetters = true)
    private ArticleWarrantyDetailsDocument articleWarrantyDocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "additionalAttributes", "serviceOrder", "franchise" }, allowSetters = true)
    private ServiceOrderAssignment serviceOrderAssignment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdditionalAttribute id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public AdditionalAttribute attributeName(String attributeName) {
        this.setAttributeName(attributeName);
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public AdditionalAttribute attributeValue(String attributeValue) {
        this.setAttributeValue(attributeValue);
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public AttributeType getAttributeType() {
        return this.attributeType;
    }

    public AdditionalAttribute attributeType(AttributeType attributeType) {
        this.setAttributeType(attributeType);
        return this;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public AdditionalAttribute createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public AdditionalAttribute createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public AdditionalAttribute updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public AdditionalAttribute updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<AdditionalAttributePossibleValue> getAdditionalAttributePossibleValues() {
        return this.additionalAttributePossibleValues;
    }

    public void setAdditionalAttributePossibleValues(Set<AdditionalAttributePossibleValue> additionalAttributePossibleValues) {
        if (this.additionalAttributePossibleValues != null) {
            this.additionalAttributePossibleValues.forEach(i -> i.setAttribute(null));
        }
        if (additionalAttributePossibleValues != null) {
            additionalAttributePossibleValues.forEach(i -> i.setAttribute(this));
        }
        this.additionalAttributePossibleValues = additionalAttributePossibleValues;
    }

    public AdditionalAttribute additionalAttributePossibleValues(Set<AdditionalAttributePossibleValue> additionalAttributePossibleValues) {
        this.setAdditionalAttributePossibleValues(additionalAttributePossibleValues);
        return this;
    }

    public AdditionalAttribute addAdditionalAttributePossibleValue(AdditionalAttributePossibleValue additionalAttributePossibleValue) {
        this.additionalAttributePossibleValues.add(additionalAttributePossibleValue);
        additionalAttributePossibleValue.setAttribute(this);
        return this;
    }

    public AdditionalAttribute removeAdditionalAttributePossibleValue(AdditionalAttributePossibleValue additionalAttributePossibleValue) {
        this.additionalAttributePossibleValues.remove(additionalAttributePossibleValue);
        additionalAttributePossibleValue.setAttribute(null);
        return this;
    }

    public Franchise getFranchise() {
        return this.franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public AdditionalAttribute franchise(Franchise franchise) {
        this.setFranchise(franchise);
        return this;
    }

    public FranchiseStatusHistory getFranchiseStatus() {
        return this.franchiseStatus;
    }

    public void setFranchiseStatus(FranchiseStatusHistory franchiseStatusHistory) {
        this.franchiseStatus = franchiseStatusHistory;
    }

    public AdditionalAttribute franchiseStatus(FranchiseStatusHistory franchiseStatusHistory) {
        this.setFranchiseStatus(franchiseStatusHistory);
        return this;
    }

    public FranchisePerformanceHistory getFranchisePerformance() {
        return this.franchisePerformance;
    }

    public void setFranchisePerformance(FranchisePerformanceHistory franchisePerformanceHistory) {
        this.franchisePerformance = franchisePerformanceHistory;
    }

    public AdditionalAttribute franchisePerformance(FranchisePerformanceHistory franchisePerformanceHistory) {
        this.setFranchisePerformance(franchisePerformanceHistory);
        return this;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public AdditionalAttribute brand(Brand brand) {
        this.setBrand(brand);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public AdditionalAttribute category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public AdditionalAttribute address(Address address) {
        this.setAddress(address);
        return this;
    }

    public LocationMapping getLocation() {
        return this.location;
    }

    public void setLocation(LocationMapping locationMapping) {
        this.location = locationMapping;
    }

    public AdditionalAttribute location(LocationMapping locationMapping) {
        this.setLocation(locationMapping);
        return this;
    }

    public FranchiseUser getFranchiseUser() {
        return this.franchiseUser;
    }

    public void setFranchiseUser(FranchiseUser franchiseUser) {
        this.franchiseUser = franchiseUser;
    }

    public AdditionalAttribute franchiseUser(FranchiseUser franchiseUser) {
        this.setFranchiseUser(franchiseUser);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AdditionalAttribute customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public SupportingDocument getSupportDocument() {
        return this.supportDocument;
    }

    public void setSupportDocument(SupportingDocument supportingDocument) {
        this.supportDocument = supportingDocument;
    }

    public AdditionalAttribute supportDocument(SupportingDocument supportingDocument) {
        this.setSupportDocument(supportingDocument);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public AdditionalAttribute product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Hsn getHsn() {
        return this.hsn;
    }

    public void setHsn(Hsn hsn) {
        this.hsn = hsn;
    }

    public AdditionalAttribute hsn(Hsn hsn) {
        this.setHsn(hsn);
        return this;
    }

    public ProductPriceHistory getPriceHistory() {
        return this.priceHistory;
    }

    public void setPriceHistory(ProductPriceHistory productPriceHistory) {
        this.priceHistory = productPriceHistory;
    }

    public AdditionalAttribute priceHistory(ProductPriceHistory productPriceHistory) {
        this.setPriceHistory(productPriceHistory);
        return this;
    }

    public WarrantyMaster getWarrantyMaster() {
        return this.warrantyMaster;
    }

    public void setWarrantyMaster(WarrantyMaster warrantyMaster) {
        this.warrantyMaster = warrantyMaster;
    }

    public AdditionalAttribute warrantyMaster(WarrantyMaster warrantyMaster) {
        this.setWarrantyMaster(warrantyMaster);
        return this;
    }

    public WarrantyMasterPriceHistory getWarrantyMasterPriceHistory() {
        return this.warrantyMasterPriceHistory;
    }

    public void setWarrantyMasterPriceHistory(WarrantyMasterPriceHistory warrantyMasterPriceHistory) {
        this.warrantyMasterPriceHistory = warrantyMasterPriceHistory;
    }

    public AdditionalAttribute warrantyMasterPriceHistory(WarrantyMasterPriceHistory warrantyMasterPriceHistory) {
        this.setWarrantyMasterPriceHistory(warrantyMasterPriceHistory);
        return this;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public AdditionalAttribute article(Article article) {
        this.setArticle(article);
        return this;
    }

    public ArticleWarrantyDetails getArticleWarranty() {
        return this.articleWarranty;
    }

    public void setArticleWarranty(ArticleWarrantyDetails articleWarrantyDetails) {
        this.articleWarranty = articleWarrantyDetails;
    }

    public AdditionalAttribute articleWarranty(ArticleWarrantyDetails articleWarrantyDetails) {
        this.setArticleWarranty(articleWarrantyDetails);
        return this;
    }

    public ServiceOrder getServiceOrder() {
        return this.serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public AdditionalAttribute serviceOrder(ServiceOrder serviceOrder) {
        this.setServiceOrder(serviceOrder);
        return this;
    }

    public ServiceOrderPayment getServiceOrderPayment() {
        return this.serviceOrderPayment;
    }

    public void setServiceOrderPayment(ServiceOrderPayment serviceOrderPayment) {
        this.serviceOrderPayment = serviceOrderPayment;
    }

    public AdditionalAttribute serviceOrderPayment(ServiceOrderPayment serviceOrderPayment) {
        this.setServiceOrderPayment(serviceOrderPayment);
        return this;
    }

    public ServiceOrderFranchiseAssignment getServiceOrderFranchiseAssignment() {
        return this.serviceOrderFranchiseAssignment;
    }

    public void setServiceOrderFranchiseAssignment(ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment) {
        this.serviceOrderFranchiseAssignment = serviceOrderFranchiseAssignment;
    }

    public AdditionalAttribute serviceOrderFranchiseAssignment(ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment) {
        this.setServiceOrderFranchiseAssignment(serviceOrderFranchiseAssignment);
        return this;
    }

    public ServiceOrderFieldAgentAssignment getServiceOrderFieldAgentAssignment() {
        return this.serviceOrderFieldAgentAssignment;
    }

    public void setServiceOrderFieldAgentAssignment(ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment) {
        this.serviceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignment;
    }

    public AdditionalAttribute serviceOrderFieldAgentAssignment(ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment) {
        this.setServiceOrderFieldAgentAssignment(serviceOrderFieldAgentAssignment);
        return this;
    }

    public FranchiseAllocationRuleSet getFranchiseAllocationRuleSet() {
        return this.franchiseAllocationRuleSet;
    }

    public void setFranchiseAllocationRuleSet(FranchiseAllocationRuleSet franchiseAllocationRuleSet) {
        this.franchiseAllocationRuleSet = franchiseAllocationRuleSet;
    }

    public AdditionalAttribute franchiseAllocationRuleSet(FranchiseAllocationRuleSet franchiseAllocationRuleSet) {
        this.setFranchiseAllocationRuleSet(franchiseAllocationRuleSet);
        return this;
    }

    public FranchiseAllocationRule getFranchiseAllocationRule() {
        return this.franchiseAllocationRule;
    }

    public void setFranchiseAllocationRule(FranchiseAllocationRule franchiseAllocationRule) {
        this.franchiseAllocationRule = franchiseAllocationRule;
    }

    public AdditionalAttribute franchiseAllocationRule(FranchiseAllocationRule franchiseAllocationRule) {
        this.setFranchiseAllocationRule(franchiseAllocationRule);
        return this;
    }

    public FieldAgentSkillRuleSet getFieldAgentSkillRuleSet() {
        return this.fieldAgentSkillRuleSet;
    }

    public void setFieldAgentSkillRuleSet(FieldAgentSkillRuleSet fieldAgentSkillRuleSet) {
        this.fieldAgentSkillRuleSet = fieldAgentSkillRuleSet;
    }

    public AdditionalAttribute fieldAgentSkillRuleSet(FieldAgentSkillRuleSet fieldAgentSkillRuleSet) {
        this.setFieldAgentSkillRuleSet(fieldAgentSkillRuleSet);
        return this;
    }

    public FieldAgentSkillRule getFieldAgentSkillRule() {
        return this.fieldAgentSkillRule;
    }

    public void setFieldAgentSkillRule(FieldAgentSkillRule fieldAgentSkillRule) {
        this.fieldAgentSkillRule = fieldAgentSkillRule;
    }

    public AdditionalAttribute fieldAgentSkillRule(FieldAgentSkillRule fieldAgentSkillRule) {
        this.setFieldAgentSkillRule(fieldAgentSkillRule);
        return this;
    }

    public InventoryLocation getInventoryLocation() {
        return this.inventoryLocation;
    }

    public void setInventoryLocation(InventoryLocation inventoryLocation) {
        this.inventoryLocation = inventoryLocation;
    }

    public AdditionalAttribute inventoryLocation(InventoryLocation inventoryLocation) {
        this.setInventoryLocation(inventoryLocation);
        return this;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public AdditionalAttribute inventory(Inventory inventory) {
        this.setInventory(inventory);
        return this;
    }

    public FranchiseDocument getDocument() {
        return this.document;
    }

    public void setDocument(FranchiseDocument franchiseDocument) {
        this.document = franchiseDocument;
    }

    public AdditionalAttribute document(FranchiseDocument franchiseDocument) {
        this.setDocument(franchiseDocument);
        return this;
    }

    public ArticleWarrantyDetailsDocument getArticleWarrantyDocument() {
        return this.articleWarrantyDocument;
    }

    public void setArticleWarrantyDocument(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument) {
        this.articleWarrantyDocument = articleWarrantyDetailsDocument;
    }

    public AdditionalAttribute articleWarrantyDocument(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument) {
        this.setArticleWarrantyDocument(articleWarrantyDetailsDocument);
        return this;
    }

    public ServiceOrderAssignment getServiceOrderAssignment() {
        return this.serviceOrderAssignment;
    }

    public void setServiceOrderAssignment(ServiceOrderAssignment serviceOrderAssignment) {
        this.serviceOrderAssignment = serviceOrderAssignment;
    }

    public AdditionalAttribute serviceOrderAssignment(ServiceOrderAssignment serviceOrderAssignment) {
        this.setServiceOrderAssignment(serviceOrderAssignment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalAttribute)) {
            return false;
        }
        return getId() != null && getId().equals(((AdditionalAttribute) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalAttribute{" +
            "id=" + getId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            ", attributeType='" + getAttributeType() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
