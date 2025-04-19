package com.framasaas.service.mapper;

import com.framasaas.domain.AdditionalAttribute;
import com.framasaas.domain.Address;
import com.framasaas.domain.Article;
import com.framasaas.domain.ArticleWarrantyDetails;
import com.framasaas.domain.ArticleWarrantyDetailsDocument;
import com.framasaas.domain.Brand;
import com.framasaas.domain.Category;
import com.framasaas.domain.Customer;
import com.framasaas.domain.FieldAgentSkillRule;
import com.framasaas.domain.FieldAgentSkillRuleSet;
import com.framasaas.domain.Franchise;
import com.framasaas.domain.FranchiseAllocationRule;
import com.framasaas.domain.FranchiseAllocationRuleSet;
import com.framasaas.domain.FranchiseDocument;
import com.framasaas.domain.FranchisePerformanceHistory;
import com.framasaas.domain.FranchiseStatusHistory;
import com.framasaas.domain.FranchiseUser;
import com.framasaas.domain.Hsn;
import com.framasaas.domain.Inventory;
import com.framasaas.domain.InventoryLocation;
import com.framasaas.domain.LocationMapping;
import com.framasaas.domain.Product;
import com.framasaas.domain.ProductPriceHistory;
import com.framasaas.domain.ServiceOrder;
import com.framasaas.domain.ServiceOrderAssignment;
import com.framasaas.domain.ServiceOrderFieldAgentAssignment;
import com.framasaas.domain.ServiceOrderFranchiseAssignment;
import com.framasaas.domain.ServiceOrderPayment;
import com.framasaas.domain.SupportingDocument;
import com.framasaas.domain.WarrantyMaster;
import com.framasaas.domain.WarrantyMasterPriceHistory;
import com.framasaas.service.dto.AdditionalAttributeDTO;
import com.framasaas.service.dto.AddressDTO;
import com.framasaas.service.dto.ArticleDTO;
import com.framasaas.service.dto.ArticleWarrantyDetailsDTO;
import com.framasaas.service.dto.ArticleWarrantyDetailsDocumentDTO;
import com.framasaas.service.dto.BrandDTO;
import com.framasaas.service.dto.CategoryDTO;
import com.framasaas.service.dto.CustomerDTO;
import com.framasaas.service.dto.FieldAgentSkillRuleDTO;
import com.framasaas.service.dto.FieldAgentSkillRuleSetDTO;
import com.framasaas.service.dto.FranchiseAllocationRuleDTO;
import com.framasaas.service.dto.FranchiseAllocationRuleSetDTO;
import com.framasaas.service.dto.FranchiseDTO;
import com.framasaas.service.dto.FranchiseDocumentDTO;
import com.framasaas.service.dto.FranchisePerformanceHistoryDTO;
import com.framasaas.service.dto.FranchiseStatusHistoryDTO;
import com.framasaas.service.dto.FranchiseUserDTO;
import com.framasaas.service.dto.HsnDTO;
import com.framasaas.service.dto.InventoryDTO;
import com.framasaas.service.dto.InventoryLocationDTO;
import com.framasaas.service.dto.LocationMappingDTO;
import com.framasaas.service.dto.ProductDTO;
import com.framasaas.service.dto.ProductPriceHistoryDTO;
import com.framasaas.service.dto.ServiceOrderAssignmentDTO;
import com.framasaas.service.dto.ServiceOrderDTO;
import com.framasaas.service.dto.ServiceOrderFieldAgentAssignmentDTO;
import com.framasaas.service.dto.ServiceOrderFranchiseAssignmentDTO;
import com.framasaas.service.dto.ServiceOrderPaymentDTO;
import com.framasaas.service.dto.SupportingDocumentDTO;
import com.framasaas.service.dto.WarrantyMasterDTO;
import com.framasaas.service.dto.WarrantyMasterPriceHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdditionalAttribute} and its DTO {@link AdditionalAttributeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdditionalAttributeMapper extends EntityMapper<AdditionalAttributeDTO, AdditionalAttribute> {
    @Mapping(target = "franchise", source = "franchise", qualifiedByName = "franchiseId")
    @Mapping(target = "franchiseStatus", source = "franchiseStatus", qualifiedByName = "franchiseStatusHistoryId")
    @Mapping(target = "franchisePerformance", source = "franchisePerformance", qualifiedByName = "franchisePerformanceHistoryId")
    @Mapping(target = "brand", source = "brand", qualifiedByName = "brandId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "location", source = "location", qualifiedByName = "locationMappingId")
    @Mapping(target = "franchiseUser", source = "franchiseUser", qualifiedByName = "franchiseUserId")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "supportDocument", source = "supportDocument", qualifiedByName = "supportingDocumentId")
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    @Mapping(target = "hsn", source = "hsn", qualifiedByName = "hsnId")
    @Mapping(target = "priceHistory", source = "priceHistory", qualifiedByName = "productPriceHistoryId")
    @Mapping(target = "warrantyMaster", source = "warrantyMaster", qualifiedByName = "warrantyMasterId")
    @Mapping(target = "warrantyMasterPriceHistory", source = "warrantyMasterPriceHistory", qualifiedByName = "warrantyMasterPriceHistoryId")
    @Mapping(target = "article", source = "article", qualifiedByName = "articleId")
    @Mapping(target = "articleWarranty", source = "articleWarranty", qualifiedByName = "articleWarrantyDetailsId")
    @Mapping(target = "serviceOrder", source = "serviceOrder", qualifiedByName = "serviceOrderId")
    @Mapping(target = "serviceOrderPayment", source = "serviceOrderPayment", qualifiedByName = "serviceOrderPaymentId")
    @Mapping(
        target = "serviceOrderFranchiseAssignment",
        source = "serviceOrderFranchiseAssignment",
        qualifiedByName = "serviceOrderFranchiseAssignmentId"
    )
    @Mapping(
        target = "serviceOrderFieldAgentAssignment",
        source = "serviceOrderFieldAgentAssignment",
        qualifiedByName = "serviceOrderFieldAgentAssignmentId"
    )
    @Mapping(target = "franchiseAllocationRuleSet", source = "franchiseAllocationRuleSet", qualifiedByName = "franchiseAllocationRuleSetId")
    @Mapping(target = "franchiseAllocationRule", source = "franchiseAllocationRule", qualifiedByName = "franchiseAllocationRuleId")
    @Mapping(target = "fieldAgentSkillRuleSet", source = "fieldAgentSkillRuleSet", qualifiedByName = "fieldAgentSkillRuleSetId")
    @Mapping(target = "fieldAgentSkillRule", source = "fieldAgentSkillRule", qualifiedByName = "fieldAgentSkillRuleId")
    @Mapping(target = "inventoryLocation", source = "inventoryLocation", qualifiedByName = "inventoryLocationId")
    @Mapping(target = "inventory", source = "inventory", qualifiedByName = "inventoryId")
    @Mapping(target = "document", source = "document", qualifiedByName = "franchiseDocumentId")
    @Mapping(target = "articleWarrantyDocument", source = "articleWarrantyDocument", qualifiedByName = "articleWarrantyDetailsDocumentId")
    @Mapping(target = "serviceOrderAssignment", source = "serviceOrderAssignment", qualifiedByName = "serviceOrderAssignmentId")
    AdditionalAttributeDTO toDto(AdditionalAttribute s);

    @Named("franchiseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseDTO toDtoFranchiseId(Franchise franchise);

    @Named("franchiseStatusHistoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseStatusHistoryDTO toDtoFranchiseStatusHistoryId(FranchiseStatusHistory franchiseStatusHistory);

    @Named("franchisePerformanceHistoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchisePerformanceHistoryDTO toDtoFranchisePerformanceHistoryId(FranchisePerformanceHistory franchisePerformanceHistory);

    @Named("brandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BrandDTO toDtoBrandId(Brand brand);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("locationMappingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationMappingDTO toDtoLocationMappingId(LocationMapping locationMapping);

    @Named("franchiseUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseUserDTO toDtoFranchiseUserId(FranchiseUser franchiseUser);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("supportingDocumentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SupportingDocumentDTO toDtoSupportingDocumentId(SupportingDocument supportingDocument);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("hsnId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HsnDTO toDtoHsnId(Hsn hsn);

    @Named("productPriceHistoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductPriceHistoryDTO toDtoProductPriceHistoryId(ProductPriceHistory productPriceHistory);

    @Named("warrantyMasterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WarrantyMasterDTO toDtoWarrantyMasterId(WarrantyMaster warrantyMaster);

    @Named("warrantyMasterPriceHistoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WarrantyMasterPriceHistoryDTO toDtoWarrantyMasterPriceHistoryId(WarrantyMasterPriceHistory warrantyMasterPriceHistory);

    @Named("articleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArticleDTO toDtoArticleId(Article article);

    @Named("articleWarrantyDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArticleWarrantyDetailsDTO toDtoArticleWarrantyDetailsId(ArticleWarrantyDetails articleWarrantyDetails);

    @Named("serviceOrderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceOrderDTO toDtoServiceOrderId(ServiceOrder serviceOrder);

    @Named("serviceOrderPaymentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceOrderPaymentDTO toDtoServiceOrderPaymentId(ServiceOrderPayment serviceOrderPayment);

    @Named("serviceOrderFranchiseAssignmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceOrderFranchiseAssignmentDTO toDtoServiceOrderFranchiseAssignmentId(
        ServiceOrderFranchiseAssignment serviceOrderFranchiseAssignment
    );

    @Named("serviceOrderFieldAgentAssignmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceOrderFieldAgentAssignmentDTO toDtoServiceOrderFieldAgentAssignmentId(
        ServiceOrderFieldAgentAssignment serviceOrderFieldAgentAssignment
    );

    @Named("franchiseAllocationRuleSetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseAllocationRuleSetDTO toDtoFranchiseAllocationRuleSetId(FranchiseAllocationRuleSet franchiseAllocationRuleSet);

    @Named("franchiseAllocationRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseAllocationRuleDTO toDtoFranchiseAllocationRuleId(FranchiseAllocationRule franchiseAllocationRule);

    @Named("fieldAgentSkillRuleSetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldAgentSkillRuleSetDTO toDtoFieldAgentSkillRuleSetId(FieldAgentSkillRuleSet fieldAgentSkillRuleSet);

    @Named("fieldAgentSkillRuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldAgentSkillRuleDTO toDtoFieldAgentSkillRuleId(FieldAgentSkillRule fieldAgentSkillRule);

    @Named("inventoryLocationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InventoryLocationDTO toDtoInventoryLocationId(InventoryLocation inventoryLocation);

    @Named("inventoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InventoryDTO toDtoInventoryId(Inventory inventory);

    @Named("franchiseDocumentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FranchiseDocumentDTO toDtoFranchiseDocumentId(FranchiseDocument franchiseDocument);

    @Named("articleWarrantyDetailsDocumentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArticleWarrantyDetailsDocumentDTO toDtoArticleWarrantyDetailsDocumentId(ArticleWarrantyDetailsDocument articleWarrantyDetailsDocument);

    @Named("serviceOrderAssignmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceOrderAssignmentDTO toDtoServiceOrderAssignmentId(ServiceOrderAssignment serviceOrderAssignment);
}
