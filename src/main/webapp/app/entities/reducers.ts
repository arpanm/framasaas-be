import franchise from 'app/entities/franchise/franchise.reducer';
import franchiseStatusHistory from 'app/entities/franchise-status-history/franchise-status-history.reducer';
import franchiseBrandMapping from 'app/entities/franchise-brand-mapping/franchise-brand-mapping.reducer';
import franchiseCategoryMapping from 'app/entities/franchise-category-mapping/franchise-category-mapping.reducer';
import address from 'app/entities/address/address.reducer';
import locationMapping from 'app/entities/location-mapping/location-mapping.reducer';
import franchiseDocument from 'app/entities/franchise-document/franchise-document.reducer';
import franchisePerformanceHistory from 'app/entities/franchise-performance-history/franchise-performance-history.reducer';
import franchiseUser from 'app/entities/franchise-user/franchise-user.reducer';
import franchiseUserStatusHistory from 'app/entities/franchise-user-status-history/franchise-user-status-history.reducer';
import customer from 'app/entities/customer/customer.reducer';
import product from 'app/entities/product/product.reducer';
import hsn from 'app/entities/hsn/hsn.reducer';
import productPriceHistory from 'app/entities/product-price-history/product-price-history.reducer';
import additionalAttribute from 'app/entities/additional-attribute/additional-attribute.reducer';
import additionalAttributePossibleValue from 'app/entities/additional-attribute-possible-value/additional-attribute-possible-value.reducer';
import brand from 'app/entities/brand/brand.reducer';
import category from 'app/entities/category/category.reducer';
import pincode from 'app/entities/pincode/pincode.reducer';
import franchiseAllocationRuleSet from 'app/entities/franchise-allocation-rule-set/franchise-allocation-rule-set.reducer';
import franchiseAllocationRule from 'app/entities/franchise-allocation-rule/franchise-allocation-rule.reducer';
import article from 'app/entities/article/article.reducer';
import articleWarrantyDetails from 'app/entities/article-warranty-details/article-warranty-details.reducer';
import warrantyMaster from 'app/entities/warranty-master/warranty-master.reducer';
import warrantyMasterPriceHistory from 'app/entities/warranty-master-price-history/warranty-master-price-history.reducer';
import articleWarrantyDetailsDocument from 'app/entities/article-warranty-details-document/article-warranty-details-document.reducer';
import serviceOrderMaster from 'app/entities/service-order-master/service-order-master.reducer';
import serviceOrder from 'app/entities/service-order/service-order.reducer';
import serviceOrderPayment from 'app/entities/service-order-payment/service-order-payment.reducer';
import serviceOrderAssignment from 'app/entities/service-order-assignment/service-order-assignment.reducer';
import serviceOrderFranchiseAssignment from 'app/entities/service-order-franchise-assignment/service-order-franchise-assignment.reducer';
import serviceOrderSpare from 'app/entities/service-order-spare/service-order-spare.reducer';
import languageMapping from 'app/entities/language-mapping/language-mapping.reducer';
import fieldAgentSkillRuleSet from 'app/entities/field-agent-skill-rule-set/field-agent-skill-rule-set.reducer';
import fieldAgentSkillRule from 'app/entities/field-agent-skill-rule/field-agent-skill-rule.reducer';
import serviceOrderFieldAgentAssignment from 'app/entities/service-order-field-agent-assignment/service-order-field-agent-assignment.reducer';
import inventoryLocation from 'app/entities/inventory-location/inventory-location.reducer';
import inventory from 'app/entities/inventory/inventory.reducer';
import inventoryHistory from 'app/entities/inventory-history/inventory-history.reducer';
import supportingDocument from 'app/entities/supporting-document/supporting-document.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  franchise,
  franchiseStatusHistory,
  franchiseBrandMapping,
  franchiseCategoryMapping,
  address,
  locationMapping,
  franchiseDocument,
  franchisePerformanceHistory,
  franchiseUser,
  franchiseUserStatusHistory,
  customer,
  product,
  hsn,
  productPriceHistory,
  additionalAttribute,
  additionalAttributePossibleValue,
  brand,
  category,
  pincode,
  franchiseAllocationRuleSet,
  franchiseAllocationRule,
  article,
  articleWarrantyDetails,
  warrantyMaster,
  warrantyMasterPriceHistory,
  articleWarrantyDetailsDocument,
  serviceOrderMaster,
  serviceOrder,
  serviceOrderPayment,
  serviceOrderAssignment,
  serviceOrderFranchiseAssignment,
  serviceOrderSpare,
  languageMapping,
  fieldAgentSkillRuleSet,
  fieldAgentSkillRule,
  serviceOrderFieldAgentAssignment,
  inventoryLocation,
  inventory,
  inventoryHistory,
  supportingDocument,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
