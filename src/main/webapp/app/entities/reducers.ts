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
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
