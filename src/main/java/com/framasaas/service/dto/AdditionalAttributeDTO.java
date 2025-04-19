package com.framasaas.service.dto;

import com.framasaas.domain.enumeration.AttributeType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.framasaas.domain.AdditionalAttribute} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdditionalAttributeDTO implements Serializable {

    private Long id;

    @NotNull
    private String attributeName;

    @NotNull
    private String attributeValue;

    private AttributeType attributeType;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private FranchiseDTO franchise;

    private FranchiseStatusHistoryDTO franchiseStatus;

    private FranchisePerformanceHistoryDTO franchisePerformance;

    private BrandDTO brand;

    private CategoryDTO category;

    private AddressDTO address;

    private LocationMappingDTO location;

    private FranchiseUserDTO franchiseUser;

    private CustomerDTO customer;

    private SupportingDocumentDTO supportDocument;

    private ProductDTO product;

    private HsnDTO hsn;

    private ProductPriceHistoryDTO priceHistory;

    private WarrantyMasterDTO warrantyMaster;

    private WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistory;

    private ArticleDTO article;

    private ArticleWarrantyDetailsDTO articleWarranty;

    private ServiceOrderDTO serviceOrder;

    private ServiceOrderPaymentDTO serviceOrderPayment;

    private ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignment;

    private ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignment;

    private FranchiseAllocationRuleSetDTO franchiseAllocationRuleSet;

    private FranchiseAllocationRuleDTO franchiseAllocationRule;

    private FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSet;

    private FieldAgentSkillRuleDTO fieldAgentSkillRule;

    private InventoryLocationDTO inventoryLocation;

    private InventoryDTO inventory;

    private FranchiseDocumentDTO document;

    private ArticleWarrantyDetailsDocumentDTO articleWarrantyDocument;

    private ServiceOrderAssignmentDTO serviceOrderAssignment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String getCreateddBy() {
        return createddBy;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
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

    public FranchiseDTO getFranchise() {
        return franchise;
    }

    public void setFranchise(FranchiseDTO franchise) {
        this.franchise = franchise;
    }

    public FranchiseStatusHistoryDTO getFranchiseStatus() {
        return franchiseStatus;
    }

    public void setFranchiseStatus(FranchiseStatusHistoryDTO franchiseStatus) {
        this.franchiseStatus = franchiseStatus;
    }

    public FranchisePerformanceHistoryDTO getFranchisePerformance() {
        return franchisePerformance;
    }

    public void setFranchisePerformance(FranchisePerformanceHistoryDTO franchisePerformance) {
        this.franchisePerformance = franchisePerformance;
    }

    public BrandDTO getBrand() {
        return brand;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public LocationMappingDTO getLocation() {
        return location;
    }

    public void setLocation(LocationMappingDTO location) {
        this.location = location;
    }

    public FranchiseUserDTO getFranchiseUser() {
        return franchiseUser;
    }

    public void setFranchiseUser(FranchiseUserDTO franchiseUser) {
        this.franchiseUser = franchiseUser;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public SupportingDocumentDTO getSupportDocument() {
        return supportDocument;
    }

    public void setSupportDocument(SupportingDocumentDTO supportDocument) {
        this.supportDocument = supportDocument;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public HsnDTO getHsn() {
        return hsn;
    }

    public void setHsn(HsnDTO hsn) {
        this.hsn = hsn;
    }

    public ProductPriceHistoryDTO getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(ProductPriceHistoryDTO priceHistory) {
        this.priceHistory = priceHistory;
    }

    public WarrantyMasterDTO getWarrantyMaster() {
        return warrantyMaster;
    }

    public void setWarrantyMaster(WarrantyMasterDTO warrantyMaster) {
        this.warrantyMaster = warrantyMaster;
    }

    public WarrantyMasterPriceHistoryDTO getWarrantyMasterPriceHistory() {
        return warrantyMasterPriceHistory;
    }

    public void setWarrantyMasterPriceHistory(WarrantyMasterPriceHistoryDTO warrantyMasterPriceHistory) {
        this.warrantyMasterPriceHistory = warrantyMasterPriceHistory;
    }

    public ArticleDTO getArticle() {
        return article;
    }

    public void setArticle(ArticleDTO article) {
        this.article = article;
    }

    public ArticleWarrantyDetailsDTO getArticleWarranty() {
        return articleWarranty;
    }

    public void setArticleWarranty(ArticleWarrantyDetailsDTO articleWarranty) {
        this.articleWarranty = articleWarranty;
    }

    public ServiceOrderDTO getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrderDTO serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public ServiceOrderPaymentDTO getServiceOrderPayment() {
        return serviceOrderPayment;
    }

    public void setServiceOrderPayment(ServiceOrderPaymentDTO serviceOrderPayment) {
        this.serviceOrderPayment = serviceOrderPayment;
    }

    public ServiceOrderFranchiseAssignmentDTO getServiceOrderFranchiseAssignment() {
        return serviceOrderFranchiseAssignment;
    }

    public void setServiceOrderFranchiseAssignment(ServiceOrderFranchiseAssignmentDTO serviceOrderFranchiseAssignment) {
        this.serviceOrderFranchiseAssignment = serviceOrderFranchiseAssignment;
    }

    public ServiceOrderFieldAgentAssignmentDTO getServiceOrderFieldAgentAssignment() {
        return serviceOrderFieldAgentAssignment;
    }

    public void setServiceOrderFieldAgentAssignment(ServiceOrderFieldAgentAssignmentDTO serviceOrderFieldAgentAssignment) {
        this.serviceOrderFieldAgentAssignment = serviceOrderFieldAgentAssignment;
    }

    public FranchiseAllocationRuleSetDTO getFranchiseAllocationRuleSet() {
        return franchiseAllocationRuleSet;
    }

    public void setFranchiseAllocationRuleSet(FranchiseAllocationRuleSetDTO franchiseAllocationRuleSet) {
        this.franchiseAllocationRuleSet = franchiseAllocationRuleSet;
    }

    public FranchiseAllocationRuleDTO getFranchiseAllocationRule() {
        return franchiseAllocationRule;
    }

    public void setFranchiseAllocationRule(FranchiseAllocationRuleDTO franchiseAllocationRule) {
        this.franchiseAllocationRule = franchiseAllocationRule;
    }

    public FieldAgentSkillRuleSetDTO getFieldAgentSkillRuleSet() {
        return fieldAgentSkillRuleSet;
    }

    public void setFieldAgentSkillRuleSet(FieldAgentSkillRuleSetDTO fieldAgentSkillRuleSet) {
        this.fieldAgentSkillRuleSet = fieldAgentSkillRuleSet;
    }

    public FieldAgentSkillRuleDTO getFieldAgentSkillRule() {
        return fieldAgentSkillRule;
    }

    public void setFieldAgentSkillRule(FieldAgentSkillRuleDTO fieldAgentSkillRule) {
        this.fieldAgentSkillRule = fieldAgentSkillRule;
    }

    public InventoryLocationDTO getInventoryLocation() {
        return inventoryLocation;
    }

    public void setInventoryLocation(InventoryLocationDTO inventoryLocation) {
        this.inventoryLocation = inventoryLocation;
    }

    public InventoryDTO getInventory() {
        return inventory;
    }

    public void setInventory(InventoryDTO inventory) {
        this.inventory = inventory;
    }

    public FranchiseDocumentDTO getDocument() {
        return document;
    }

    public void setDocument(FranchiseDocumentDTO document) {
        this.document = document;
    }

    public ArticleWarrantyDetailsDocumentDTO getArticleWarrantyDocument() {
        return articleWarrantyDocument;
    }

    public void setArticleWarrantyDocument(ArticleWarrantyDetailsDocumentDTO articleWarrantyDocument) {
        this.articleWarrantyDocument = articleWarrantyDocument;
    }

    public ServiceOrderAssignmentDTO getServiceOrderAssignment() {
        return serviceOrderAssignment;
    }

    public void setServiceOrderAssignment(ServiceOrderAssignmentDTO serviceOrderAssignment) {
        this.serviceOrderAssignment = serviceOrderAssignment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalAttributeDTO)) {
            return false;
        }

        AdditionalAttributeDTO additionalAttributeDTO = (AdditionalAttributeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, additionalAttributeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalAttributeDTO{" +
            "id=" + getId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            ", attributeType='" + getAttributeType() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", franchise=" + getFranchise() +
            ", franchiseStatus=" + getFranchiseStatus() +
            ", franchisePerformance=" + getFranchisePerformance() +
            ", brand=" + getBrand() +
            ", category=" + getCategory() +
            ", address=" + getAddress() +
            ", location=" + getLocation() +
            ", franchiseUser=" + getFranchiseUser() +
            ", customer=" + getCustomer() +
            ", supportDocument=" + getSupportDocument() +
            ", product=" + getProduct() +
            ", hsn=" + getHsn() +
            ", priceHistory=" + getPriceHistory() +
            ", warrantyMaster=" + getWarrantyMaster() +
            ", warrantyMasterPriceHistory=" + getWarrantyMasterPriceHistory() +
            ", article=" + getArticle() +
            ", articleWarranty=" + getArticleWarranty() +
            ", serviceOrder=" + getServiceOrder() +
            ", serviceOrderPayment=" + getServiceOrderPayment() +
            ", serviceOrderFranchiseAssignment=" + getServiceOrderFranchiseAssignment() +
            ", serviceOrderFieldAgentAssignment=" + getServiceOrderFieldAgentAssignment() +
            ", franchiseAllocationRuleSet=" + getFranchiseAllocationRuleSet() +
            ", franchiseAllocationRule=" + getFranchiseAllocationRule() +
            ", fieldAgentSkillRuleSet=" + getFieldAgentSkillRuleSet() +
            ", fieldAgentSkillRule=" + getFieldAgentSkillRule() +
            ", inventoryLocation=" + getInventoryLocation() +
            ", inventory=" + getInventory() +
            ", document=" + getDocument() +
            ", articleWarrantyDocument=" + getArticleWarrantyDocument() +
            ", serviceOrderAssignment=" + getServiceOrderAssignment() +
            "}";
    }
}
