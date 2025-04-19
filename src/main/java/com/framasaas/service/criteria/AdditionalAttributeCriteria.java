package com.framasaas.service.criteria;

import com.framasaas.domain.enumeration.AttributeType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.framasaas.domain.AdditionalAttribute} entity. This class is used
 * in {@link com.framasaas.web.rest.AdditionalAttributeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /additional-attributes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdditionalAttributeCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AttributeType
     */
    public static class AttributeTypeFilter extends Filter<AttributeType> {

        public AttributeTypeFilter() {}

        public AttributeTypeFilter(AttributeTypeFilter filter) {
            super(filter);
        }

        @Override
        public AttributeTypeFilter copy() {
            return new AttributeTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter attributeName;

    private StringFilter attributeValue;

    private AttributeTypeFilter attributeType;

    private StringFilter createddBy;

    private InstantFilter createdTime;

    private StringFilter updatedBy;

    private InstantFilter updatedTime;

    private LongFilter additionalAttributePossibleValueId;

    private LongFilter franchiseId;

    private LongFilter franchiseStatusId;

    private LongFilter franchisePerformanceId;

    private LongFilter brandId;

    private LongFilter categoryId;

    private LongFilter addressId;

    private LongFilter locationId;

    private LongFilter franchiseUserId;

    private LongFilter customerId;

    private LongFilter supportDocumentId;

    private LongFilter productId;

    private LongFilter hsnId;

    private LongFilter priceHistoryId;

    private LongFilter warrantyMasterId;

    private LongFilter warrantyMasterPriceHistoryId;

    private LongFilter articleId;

    private LongFilter articleWarrantyId;

    private LongFilter serviceOrderId;

    private LongFilter serviceOrderPaymentId;

    private LongFilter serviceOrderFranchiseAssignmentId;

    private LongFilter serviceOrderFieldAgentAssignmentId;

    private LongFilter franchiseAllocationRuleSetId;

    private LongFilter franchiseAllocationRuleId;

    private LongFilter fieldAgentSkillRuleSetId;

    private LongFilter fieldAgentSkillRuleId;

    private LongFilter inventoryLocationId;

    private LongFilter inventoryId;

    private LongFilter documentId;

    private LongFilter articleWarrantyDocumentId;

    private LongFilter serviceOrderAssignmentId;

    private Boolean distinct;

    public AdditionalAttributeCriteria() {}

    public AdditionalAttributeCriteria(AdditionalAttributeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.attributeName = other.optionalAttributeName().map(StringFilter::copy).orElse(null);
        this.attributeValue = other.optionalAttributeValue().map(StringFilter::copy).orElse(null);
        this.attributeType = other.optionalAttributeType().map(AttributeTypeFilter::copy).orElse(null);
        this.createddBy = other.optionalCreateddBy().map(StringFilter::copy).orElse(null);
        this.createdTime = other.optionalCreatedTime().map(InstantFilter::copy).orElse(null);
        this.updatedBy = other.optionalUpdatedBy().map(StringFilter::copy).orElse(null);
        this.updatedTime = other.optionalUpdatedTime().map(InstantFilter::copy).orElse(null);
        this.additionalAttributePossibleValueId = other.optionalAdditionalAttributePossibleValueId().map(LongFilter::copy).orElse(null);
        this.franchiseId = other.optionalFranchiseId().map(LongFilter::copy).orElse(null);
        this.franchiseStatusId = other.optionalFranchiseStatusId().map(LongFilter::copy).orElse(null);
        this.franchisePerformanceId = other.optionalFranchisePerformanceId().map(LongFilter::copy).orElse(null);
        this.brandId = other.optionalBrandId().map(LongFilter::copy).orElse(null);
        this.categoryId = other.optionalCategoryId().map(LongFilter::copy).orElse(null);
        this.addressId = other.optionalAddressId().map(LongFilter::copy).orElse(null);
        this.locationId = other.optionalLocationId().map(LongFilter::copy).orElse(null);
        this.franchiseUserId = other.optionalFranchiseUserId().map(LongFilter::copy).orElse(null);
        this.customerId = other.optionalCustomerId().map(LongFilter::copy).orElse(null);
        this.supportDocumentId = other.optionalSupportDocumentId().map(LongFilter::copy).orElse(null);
        this.productId = other.optionalProductId().map(LongFilter::copy).orElse(null);
        this.hsnId = other.optionalHsnId().map(LongFilter::copy).orElse(null);
        this.priceHistoryId = other.optionalPriceHistoryId().map(LongFilter::copy).orElse(null);
        this.warrantyMasterId = other.optionalWarrantyMasterId().map(LongFilter::copy).orElse(null);
        this.warrantyMasterPriceHistoryId = other.optionalWarrantyMasterPriceHistoryId().map(LongFilter::copy).orElse(null);
        this.articleId = other.optionalArticleId().map(LongFilter::copy).orElse(null);
        this.articleWarrantyId = other.optionalArticleWarrantyId().map(LongFilter::copy).orElse(null);
        this.serviceOrderId = other.optionalServiceOrderId().map(LongFilter::copy).orElse(null);
        this.serviceOrderPaymentId = other.optionalServiceOrderPaymentId().map(LongFilter::copy).orElse(null);
        this.serviceOrderFranchiseAssignmentId = other.optionalServiceOrderFranchiseAssignmentId().map(LongFilter::copy).orElse(null);
        this.serviceOrderFieldAgentAssignmentId = other.optionalServiceOrderFieldAgentAssignmentId().map(LongFilter::copy).orElse(null);
        this.franchiseAllocationRuleSetId = other.optionalFranchiseAllocationRuleSetId().map(LongFilter::copy).orElse(null);
        this.franchiseAllocationRuleId = other.optionalFranchiseAllocationRuleId().map(LongFilter::copy).orElse(null);
        this.fieldAgentSkillRuleSetId = other.optionalFieldAgentSkillRuleSetId().map(LongFilter::copy).orElse(null);
        this.fieldAgentSkillRuleId = other.optionalFieldAgentSkillRuleId().map(LongFilter::copy).orElse(null);
        this.inventoryLocationId = other.optionalInventoryLocationId().map(LongFilter::copy).orElse(null);
        this.inventoryId = other.optionalInventoryId().map(LongFilter::copy).orElse(null);
        this.documentId = other.optionalDocumentId().map(LongFilter::copy).orElse(null);
        this.articleWarrantyDocumentId = other.optionalArticleWarrantyDocumentId().map(LongFilter::copy).orElse(null);
        this.serviceOrderAssignmentId = other.optionalServiceOrderAssignmentId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AdditionalAttributeCriteria copy() {
        return new AdditionalAttributeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAttributeName() {
        return attributeName;
    }

    public Optional<StringFilter> optionalAttributeName() {
        return Optional.ofNullable(attributeName);
    }

    public StringFilter attributeName() {
        if (attributeName == null) {
            setAttributeName(new StringFilter());
        }
        return attributeName;
    }

    public void setAttributeName(StringFilter attributeName) {
        this.attributeName = attributeName;
    }

    public StringFilter getAttributeValue() {
        return attributeValue;
    }

    public Optional<StringFilter> optionalAttributeValue() {
        return Optional.ofNullable(attributeValue);
    }

    public StringFilter attributeValue() {
        if (attributeValue == null) {
            setAttributeValue(new StringFilter());
        }
        return attributeValue;
    }

    public void setAttributeValue(StringFilter attributeValue) {
        this.attributeValue = attributeValue;
    }

    public AttributeTypeFilter getAttributeType() {
        return attributeType;
    }

    public Optional<AttributeTypeFilter> optionalAttributeType() {
        return Optional.ofNullable(attributeType);
    }

    public AttributeTypeFilter attributeType() {
        if (attributeType == null) {
            setAttributeType(new AttributeTypeFilter());
        }
        return attributeType;
    }

    public void setAttributeType(AttributeTypeFilter attributeType) {
        this.attributeType = attributeType;
    }

    public StringFilter getCreateddBy() {
        return createddBy;
    }

    public Optional<StringFilter> optionalCreateddBy() {
        return Optional.ofNullable(createddBy);
    }

    public StringFilter createddBy() {
        if (createddBy == null) {
            setCreateddBy(new StringFilter());
        }
        return createddBy;
    }

    public void setCreateddBy(StringFilter createddBy) {
        this.createddBy = createddBy;
    }

    public InstantFilter getCreatedTime() {
        return createdTime;
    }

    public Optional<InstantFilter> optionalCreatedTime() {
        return Optional.ofNullable(createdTime);
    }

    public InstantFilter createdTime() {
        if (createdTime == null) {
            setCreatedTime(new InstantFilter());
        }
        return createdTime;
    }

    public void setCreatedTime(InstantFilter createdTime) {
        this.createdTime = createdTime;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public Optional<StringFilter> optionalUpdatedBy() {
        return Optional.ofNullable(updatedBy);
    }

    public StringFilter updatedBy() {
        if (updatedBy == null) {
            setUpdatedBy(new StringFilter());
        }
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedTime() {
        return updatedTime;
    }

    public Optional<InstantFilter> optionalUpdatedTime() {
        return Optional.ofNullable(updatedTime);
    }

    public InstantFilter updatedTime() {
        if (updatedTime == null) {
            setUpdatedTime(new InstantFilter());
        }
        return updatedTime;
    }

    public void setUpdatedTime(InstantFilter updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LongFilter getAdditionalAttributePossibleValueId() {
        return additionalAttributePossibleValueId;
    }

    public Optional<LongFilter> optionalAdditionalAttributePossibleValueId() {
        return Optional.ofNullable(additionalAttributePossibleValueId);
    }

    public LongFilter additionalAttributePossibleValueId() {
        if (additionalAttributePossibleValueId == null) {
            setAdditionalAttributePossibleValueId(new LongFilter());
        }
        return additionalAttributePossibleValueId;
    }

    public void setAdditionalAttributePossibleValueId(LongFilter additionalAttributePossibleValueId) {
        this.additionalAttributePossibleValueId = additionalAttributePossibleValueId;
    }

    public LongFilter getFranchiseId() {
        return franchiseId;
    }

    public Optional<LongFilter> optionalFranchiseId() {
        return Optional.ofNullable(franchiseId);
    }

    public LongFilter franchiseId() {
        if (franchiseId == null) {
            setFranchiseId(new LongFilter());
        }
        return franchiseId;
    }

    public void setFranchiseId(LongFilter franchiseId) {
        this.franchiseId = franchiseId;
    }

    public LongFilter getFranchiseStatusId() {
        return franchiseStatusId;
    }

    public Optional<LongFilter> optionalFranchiseStatusId() {
        return Optional.ofNullable(franchiseStatusId);
    }

    public LongFilter franchiseStatusId() {
        if (franchiseStatusId == null) {
            setFranchiseStatusId(new LongFilter());
        }
        return franchiseStatusId;
    }

    public void setFranchiseStatusId(LongFilter franchiseStatusId) {
        this.franchiseStatusId = franchiseStatusId;
    }

    public LongFilter getFranchisePerformanceId() {
        return franchisePerformanceId;
    }

    public Optional<LongFilter> optionalFranchisePerformanceId() {
        return Optional.ofNullable(franchisePerformanceId);
    }

    public LongFilter franchisePerformanceId() {
        if (franchisePerformanceId == null) {
            setFranchisePerformanceId(new LongFilter());
        }
        return franchisePerformanceId;
    }

    public void setFranchisePerformanceId(LongFilter franchisePerformanceId) {
        this.franchisePerformanceId = franchisePerformanceId;
    }

    public LongFilter getBrandId() {
        return brandId;
    }

    public Optional<LongFilter> optionalBrandId() {
        return Optional.ofNullable(brandId);
    }

    public LongFilter brandId() {
        if (brandId == null) {
            setBrandId(new LongFilter());
        }
        return brandId;
    }

    public void setBrandId(LongFilter brandId) {
        this.brandId = brandId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public Optional<LongFilter> optionalCategoryId() {
        return Optional.ofNullable(categoryId);
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            setCategoryId(new LongFilter());
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public Optional<LongFilter> optionalAddressId() {
        return Optional.ofNullable(addressId);
    }

    public LongFilter addressId() {
        if (addressId == null) {
            setAddressId(new LongFilter());
        }
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public Optional<LongFilter> optionalLocationId() {
        return Optional.ofNullable(locationId);
    }

    public LongFilter locationId() {
        if (locationId == null) {
            setLocationId(new LongFilter());
        }
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getFranchiseUserId() {
        return franchiseUserId;
    }

    public Optional<LongFilter> optionalFranchiseUserId() {
        return Optional.ofNullable(franchiseUserId);
    }

    public LongFilter franchiseUserId() {
        if (franchiseUserId == null) {
            setFranchiseUserId(new LongFilter());
        }
        return franchiseUserId;
    }

    public void setFranchiseUserId(LongFilter franchiseUserId) {
        this.franchiseUserId = franchiseUserId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public Optional<LongFilter> optionalCustomerId() {
        return Optional.ofNullable(customerId);
    }

    public LongFilter customerId() {
        if (customerId == null) {
            setCustomerId(new LongFilter());
        }
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    public LongFilter getSupportDocumentId() {
        return supportDocumentId;
    }

    public Optional<LongFilter> optionalSupportDocumentId() {
        return Optional.ofNullable(supportDocumentId);
    }

    public LongFilter supportDocumentId() {
        if (supportDocumentId == null) {
            setSupportDocumentId(new LongFilter());
        }
        return supportDocumentId;
    }

    public void setSupportDocumentId(LongFilter supportDocumentId) {
        this.supportDocumentId = supportDocumentId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public Optional<LongFilter> optionalProductId() {
        return Optional.ofNullable(productId);
    }

    public LongFilter productId() {
        if (productId == null) {
            setProductId(new LongFilter());
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getHsnId() {
        return hsnId;
    }

    public Optional<LongFilter> optionalHsnId() {
        return Optional.ofNullable(hsnId);
    }

    public LongFilter hsnId() {
        if (hsnId == null) {
            setHsnId(new LongFilter());
        }
        return hsnId;
    }

    public void setHsnId(LongFilter hsnId) {
        this.hsnId = hsnId;
    }

    public LongFilter getPriceHistoryId() {
        return priceHistoryId;
    }

    public Optional<LongFilter> optionalPriceHistoryId() {
        return Optional.ofNullable(priceHistoryId);
    }

    public LongFilter priceHistoryId() {
        if (priceHistoryId == null) {
            setPriceHistoryId(new LongFilter());
        }
        return priceHistoryId;
    }

    public void setPriceHistoryId(LongFilter priceHistoryId) {
        this.priceHistoryId = priceHistoryId;
    }

    public LongFilter getWarrantyMasterId() {
        return warrantyMasterId;
    }

    public Optional<LongFilter> optionalWarrantyMasterId() {
        return Optional.ofNullable(warrantyMasterId);
    }

    public LongFilter warrantyMasterId() {
        if (warrantyMasterId == null) {
            setWarrantyMasterId(new LongFilter());
        }
        return warrantyMasterId;
    }

    public void setWarrantyMasterId(LongFilter warrantyMasterId) {
        this.warrantyMasterId = warrantyMasterId;
    }

    public LongFilter getWarrantyMasterPriceHistoryId() {
        return warrantyMasterPriceHistoryId;
    }

    public Optional<LongFilter> optionalWarrantyMasterPriceHistoryId() {
        return Optional.ofNullable(warrantyMasterPriceHistoryId);
    }

    public LongFilter warrantyMasterPriceHistoryId() {
        if (warrantyMasterPriceHistoryId == null) {
            setWarrantyMasterPriceHistoryId(new LongFilter());
        }
        return warrantyMasterPriceHistoryId;
    }

    public void setWarrantyMasterPriceHistoryId(LongFilter warrantyMasterPriceHistoryId) {
        this.warrantyMasterPriceHistoryId = warrantyMasterPriceHistoryId;
    }

    public LongFilter getArticleId() {
        return articleId;
    }

    public Optional<LongFilter> optionalArticleId() {
        return Optional.ofNullable(articleId);
    }

    public LongFilter articleId() {
        if (articleId == null) {
            setArticleId(new LongFilter());
        }
        return articleId;
    }

    public void setArticleId(LongFilter articleId) {
        this.articleId = articleId;
    }

    public LongFilter getArticleWarrantyId() {
        return articleWarrantyId;
    }

    public Optional<LongFilter> optionalArticleWarrantyId() {
        return Optional.ofNullable(articleWarrantyId);
    }

    public LongFilter articleWarrantyId() {
        if (articleWarrantyId == null) {
            setArticleWarrantyId(new LongFilter());
        }
        return articleWarrantyId;
    }

    public void setArticleWarrantyId(LongFilter articleWarrantyId) {
        this.articleWarrantyId = articleWarrantyId;
    }

    public LongFilter getServiceOrderId() {
        return serviceOrderId;
    }

    public Optional<LongFilter> optionalServiceOrderId() {
        return Optional.ofNullable(serviceOrderId);
    }

    public LongFilter serviceOrderId() {
        if (serviceOrderId == null) {
            setServiceOrderId(new LongFilter());
        }
        return serviceOrderId;
    }

    public void setServiceOrderId(LongFilter serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public LongFilter getServiceOrderPaymentId() {
        return serviceOrderPaymentId;
    }

    public Optional<LongFilter> optionalServiceOrderPaymentId() {
        return Optional.ofNullable(serviceOrderPaymentId);
    }

    public LongFilter serviceOrderPaymentId() {
        if (serviceOrderPaymentId == null) {
            setServiceOrderPaymentId(new LongFilter());
        }
        return serviceOrderPaymentId;
    }

    public void setServiceOrderPaymentId(LongFilter serviceOrderPaymentId) {
        this.serviceOrderPaymentId = serviceOrderPaymentId;
    }

    public LongFilter getServiceOrderFranchiseAssignmentId() {
        return serviceOrderFranchiseAssignmentId;
    }

    public Optional<LongFilter> optionalServiceOrderFranchiseAssignmentId() {
        return Optional.ofNullable(serviceOrderFranchiseAssignmentId);
    }

    public LongFilter serviceOrderFranchiseAssignmentId() {
        if (serviceOrderFranchiseAssignmentId == null) {
            setServiceOrderFranchiseAssignmentId(new LongFilter());
        }
        return serviceOrderFranchiseAssignmentId;
    }

    public void setServiceOrderFranchiseAssignmentId(LongFilter serviceOrderFranchiseAssignmentId) {
        this.serviceOrderFranchiseAssignmentId = serviceOrderFranchiseAssignmentId;
    }

    public LongFilter getServiceOrderFieldAgentAssignmentId() {
        return serviceOrderFieldAgentAssignmentId;
    }

    public Optional<LongFilter> optionalServiceOrderFieldAgentAssignmentId() {
        return Optional.ofNullable(serviceOrderFieldAgentAssignmentId);
    }

    public LongFilter serviceOrderFieldAgentAssignmentId() {
        if (serviceOrderFieldAgentAssignmentId == null) {
            setServiceOrderFieldAgentAssignmentId(new LongFilter());
        }
        return serviceOrderFieldAgentAssignmentId;
    }

    public void setServiceOrderFieldAgentAssignmentId(LongFilter serviceOrderFieldAgentAssignmentId) {
        this.serviceOrderFieldAgentAssignmentId = serviceOrderFieldAgentAssignmentId;
    }

    public LongFilter getFranchiseAllocationRuleSetId() {
        return franchiseAllocationRuleSetId;
    }

    public Optional<LongFilter> optionalFranchiseAllocationRuleSetId() {
        return Optional.ofNullable(franchiseAllocationRuleSetId);
    }

    public LongFilter franchiseAllocationRuleSetId() {
        if (franchiseAllocationRuleSetId == null) {
            setFranchiseAllocationRuleSetId(new LongFilter());
        }
        return franchiseAllocationRuleSetId;
    }

    public void setFranchiseAllocationRuleSetId(LongFilter franchiseAllocationRuleSetId) {
        this.franchiseAllocationRuleSetId = franchiseAllocationRuleSetId;
    }

    public LongFilter getFranchiseAllocationRuleId() {
        return franchiseAllocationRuleId;
    }

    public Optional<LongFilter> optionalFranchiseAllocationRuleId() {
        return Optional.ofNullable(franchiseAllocationRuleId);
    }

    public LongFilter franchiseAllocationRuleId() {
        if (franchiseAllocationRuleId == null) {
            setFranchiseAllocationRuleId(new LongFilter());
        }
        return franchiseAllocationRuleId;
    }

    public void setFranchiseAllocationRuleId(LongFilter franchiseAllocationRuleId) {
        this.franchiseAllocationRuleId = franchiseAllocationRuleId;
    }

    public LongFilter getFieldAgentSkillRuleSetId() {
        return fieldAgentSkillRuleSetId;
    }

    public Optional<LongFilter> optionalFieldAgentSkillRuleSetId() {
        return Optional.ofNullable(fieldAgentSkillRuleSetId);
    }

    public LongFilter fieldAgentSkillRuleSetId() {
        if (fieldAgentSkillRuleSetId == null) {
            setFieldAgentSkillRuleSetId(new LongFilter());
        }
        return fieldAgentSkillRuleSetId;
    }

    public void setFieldAgentSkillRuleSetId(LongFilter fieldAgentSkillRuleSetId) {
        this.fieldAgentSkillRuleSetId = fieldAgentSkillRuleSetId;
    }

    public LongFilter getFieldAgentSkillRuleId() {
        return fieldAgentSkillRuleId;
    }

    public Optional<LongFilter> optionalFieldAgentSkillRuleId() {
        return Optional.ofNullable(fieldAgentSkillRuleId);
    }

    public LongFilter fieldAgentSkillRuleId() {
        if (fieldAgentSkillRuleId == null) {
            setFieldAgentSkillRuleId(new LongFilter());
        }
        return fieldAgentSkillRuleId;
    }

    public void setFieldAgentSkillRuleId(LongFilter fieldAgentSkillRuleId) {
        this.fieldAgentSkillRuleId = fieldAgentSkillRuleId;
    }

    public LongFilter getInventoryLocationId() {
        return inventoryLocationId;
    }

    public Optional<LongFilter> optionalInventoryLocationId() {
        return Optional.ofNullable(inventoryLocationId);
    }

    public LongFilter inventoryLocationId() {
        if (inventoryLocationId == null) {
            setInventoryLocationId(new LongFilter());
        }
        return inventoryLocationId;
    }

    public void setInventoryLocationId(LongFilter inventoryLocationId) {
        this.inventoryLocationId = inventoryLocationId;
    }

    public LongFilter getInventoryId() {
        return inventoryId;
    }

    public Optional<LongFilter> optionalInventoryId() {
        return Optional.ofNullable(inventoryId);
    }

    public LongFilter inventoryId() {
        if (inventoryId == null) {
            setInventoryId(new LongFilter());
        }
        return inventoryId;
    }

    public void setInventoryId(LongFilter inventoryId) {
        this.inventoryId = inventoryId;
    }

    public LongFilter getDocumentId() {
        return documentId;
    }

    public Optional<LongFilter> optionalDocumentId() {
        return Optional.ofNullable(documentId);
    }

    public LongFilter documentId() {
        if (documentId == null) {
            setDocumentId(new LongFilter());
        }
        return documentId;
    }

    public void setDocumentId(LongFilter documentId) {
        this.documentId = documentId;
    }

    public LongFilter getArticleWarrantyDocumentId() {
        return articleWarrantyDocumentId;
    }

    public Optional<LongFilter> optionalArticleWarrantyDocumentId() {
        return Optional.ofNullable(articleWarrantyDocumentId);
    }

    public LongFilter articleWarrantyDocumentId() {
        if (articleWarrantyDocumentId == null) {
            setArticleWarrantyDocumentId(new LongFilter());
        }
        return articleWarrantyDocumentId;
    }

    public void setArticleWarrantyDocumentId(LongFilter articleWarrantyDocumentId) {
        this.articleWarrantyDocumentId = articleWarrantyDocumentId;
    }

    public LongFilter getServiceOrderAssignmentId() {
        return serviceOrderAssignmentId;
    }

    public Optional<LongFilter> optionalServiceOrderAssignmentId() {
        return Optional.ofNullable(serviceOrderAssignmentId);
    }

    public LongFilter serviceOrderAssignmentId() {
        if (serviceOrderAssignmentId == null) {
            setServiceOrderAssignmentId(new LongFilter());
        }
        return serviceOrderAssignmentId;
    }

    public void setServiceOrderAssignmentId(LongFilter serviceOrderAssignmentId) {
        this.serviceOrderAssignmentId = serviceOrderAssignmentId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdditionalAttributeCriteria that = (AdditionalAttributeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(attributeName, that.attributeName) &&
            Objects.equals(attributeValue, that.attributeValue) &&
            Objects.equals(attributeType, that.attributeType) &&
            Objects.equals(createddBy, that.createddBy) &&
            Objects.equals(createdTime, that.createdTime) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedTime, that.updatedTime) &&
            Objects.equals(additionalAttributePossibleValueId, that.additionalAttributePossibleValueId) &&
            Objects.equals(franchiseId, that.franchiseId) &&
            Objects.equals(franchiseStatusId, that.franchiseStatusId) &&
            Objects.equals(franchisePerformanceId, that.franchisePerformanceId) &&
            Objects.equals(brandId, that.brandId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(franchiseUserId, that.franchiseUserId) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(supportDocumentId, that.supportDocumentId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(hsnId, that.hsnId) &&
            Objects.equals(priceHistoryId, that.priceHistoryId) &&
            Objects.equals(warrantyMasterId, that.warrantyMasterId) &&
            Objects.equals(warrantyMasterPriceHistoryId, that.warrantyMasterPriceHistoryId) &&
            Objects.equals(articleId, that.articleId) &&
            Objects.equals(articleWarrantyId, that.articleWarrantyId) &&
            Objects.equals(serviceOrderId, that.serviceOrderId) &&
            Objects.equals(serviceOrderPaymentId, that.serviceOrderPaymentId) &&
            Objects.equals(serviceOrderFranchiseAssignmentId, that.serviceOrderFranchiseAssignmentId) &&
            Objects.equals(serviceOrderFieldAgentAssignmentId, that.serviceOrderFieldAgentAssignmentId) &&
            Objects.equals(franchiseAllocationRuleSetId, that.franchiseAllocationRuleSetId) &&
            Objects.equals(franchiseAllocationRuleId, that.franchiseAllocationRuleId) &&
            Objects.equals(fieldAgentSkillRuleSetId, that.fieldAgentSkillRuleSetId) &&
            Objects.equals(fieldAgentSkillRuleId, that.fieldAgentSkillRuleId) &&
            Objects.equals(inventoryLocationId, that.inventoryLocationId) &&
            Objects.equals(inventoryId, that.inventoryId) &&
            Objects.equals(documentId, that.documentId) &&
            Objects.equals(articleWarrantyDocumentId, that.articleWarrantyDocumentId) &&
            Objects.equals(serviceOrderAssignmentId, that.serviceOrderAssignmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            attributeName,
            attributeValue,
            attributeType,
            createddBy,
            createdTime,
            updatedBy,
            updatedTime,
            additionalAttributePossibleValueId,
            franchiseId,
            franchiseStatusId,
            franchisePerformanceId,
            brandId,
            categoryId,
            addressId,
            locationId,
            franchiseUserId,
            customerId,
            supportDocumentId,
            productId,
            hsnId,
            priceHistoryId,
            warrantyMasterId,
            warrantyMasterPriceHistoryId,
            articleId,
            articleWarrantyId,
            serviceOrderId,
            serviceOrderPaymentId,
            serviceOrderFranchiseAssignmentId,
            serviceOrderFieldAgentAssignmentId,
            franchiseAllocationRuleSetId,
            franchiseAllocationRuleId,
            fieldAgentSkillRuleSetId,
            fieldAgentSkillRuleId,
            inventoryLocationId,
            inventoryId,
            documentId,
            articleWarrantyDocumentId,
            serviceOrderAssignmentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalAttributeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalAttributeName().map(f -> "attributeName=" + f + ", ").orElse("") +
            optionalAttributeValue().map(f -> "attributeValue=" + f + ", ").orElse("") +
            optionalAttributeType().map(f -> "attributeType=" + f + ", ").orElse("") +
            optionalCreateddBy().map(f -> "createddBy=" + f + ", ").orElse("") +
            optionalCreatedTime().map(f -> "createdTime=" + f + ", ").orElse("") +
            optionalUpdatedBy().map(f -> "updatedBy=" + f + ", ").orElse("") +
            optionalUpdatedTime().map(f -> "updatedTime=" + f + ", ").orElse("") +
            optionalAdditionalAttributePossibleValueId().map(f -> "additionalAttributePossibleValueId=" + f + ", ").orElse("") +
            optionalFranchiseId().map(f -> "franchiseId=" + f + ", ").orElse("") +
            optionalFranchiseStatusId().map(f -> "franchiseStatusId=" + f + ", ").orElse("") +
            optionalFranchisePerformanceId().map(f -> "franchisePerformanceId=" + f + ", ").orElse("") +
            optionalBrandId().map(f -> "brandId=" + f + ", ").orElse("") +
            optionalCategoryId().map(f -> "categoryId=" + f + ", ").orElse("") +
            optionalAddressId().map(f -> "addressId=" + f + ", ").orElse("") +
            optionalLocationId().map(f -> "locationId=" + f + ", ").orElse("") +
            optionalFranchiseUserId().map(f -> "franchiseUserId=" + f + ", ").orElse("") +
            optionalCustomerId().map(f -> "customerId=" + f + ", ").orElse("") +
            optionalSupportDocumentId().map(f -> "supportDocumentId=" + f + ", ").orElse("") +
            optionalProductId().map(f -> "productId=" + f + ", ").orElse("") +
            optionalHsnId().map(f -> "hsnId=" + f + ", ").orElse("") +
            optionalPriceHistoryId().map(f -> "priceHistoryId=" + f + ", ").orElse("") +
            optionalWarrantyMasterId().map(f -> "warrantyMasterId=" + f + ", ").orElse("") +
            optionalWarrantyMasterPriceHistoryId().map(f -> "warrantyMasterPriceHistoryId=" + f + ", ").orElse("") +
            optionalArticleId().map(f -> "articleId=" + f + ", ").orElse("") +
            optionalArticleWarrantyId().map(f -> "articleWarrantyId=" + f + ", ").orElse("") +
            optionalServiceOrderId().map(f -> "serviceOrderId=" + f + ", ").orElse("") +
            optionalServiceOrderPaymentId().map(f -> "serviceOrderPaymentId=" + f + ", ").orElse("") +
            optionalServiceOrderFranchiseAssignmentId().map(f -> "serviceOrderFranchiseAssignmentId=" + f + ", ").orElse("") +
            optionalServiceOrderFieldAgentAssignmentId().map(f -> "serviceOrderFieldAgentAssignmentId=" + f + ", ").orElse("") +
            optionalFranchiseAllocationRuleSetId().map(f -> "franchiseAllocationRuleSetId=" + f + ", ").orElse("") +
            optionalFranchiseAllocationRuleId().map(f -> "franchiseAllocationRuleId=" + f + ", ").orElse("") +
            optionalFieldAgentSkillRuleSetId().map(f -> "fieldAgentSkillRuleSetId=" + f + ", ").orElse("") +
            optionalFieldAgentSkillRuleId().map(f -> "fieldAgentSkillRuleId=" + f + ", ").orElse("") +
            optionalInventoryLocationId().map(f -> "inventoryLocationId=" + f + ", ").orElse("") +
            optionalInventoryId().map(f -> "inventoryId=" + f + ", ").orElse("") +
            optionalDocumentId().map(f -> "documentId=" + f + ", ").orElse("") +
            optionalArticleWarrantyDocumentId().map(f -> "articleWarrantyDocumentId=" + f + ", ").orElse("") +
            optionalServiceOrderAssignmentId().map(f -> "serviceOrderAssignmentId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
