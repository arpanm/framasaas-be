import React from 'react';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Franchise from './franchise';
import FranchiseStatusHistory from './franchise-status-history';
import FranchiseBrandMapping from './franchise-brand-mapping';
import FranchiseCategoryMapping from './franchise-category-mapping';
import Address from './address';
import LocationMapping from './location-mapping';
import FranchiseDocument from './franchise-document';
import FranchisePerformanceHistory from './franchise-performance-history';
import FranchiseUser from './franchise-user';
import FranchiseUserStatusHistory from './franchise-user-status-history';
import Customer from './customer';
import Product from './product';
import Hsn from './hsn';
import ProductPriceHistory from './product-price-history';
import AdditionalAttribute from './additional-attribute';
import AdditionalAttributePossibleValue from './additional-attribute-possible-value';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="franchise/*" element={<Franchise />} />
        <Route path="franchise-status-history/*" element={<FranchiseStatusHistory />} />
        <Route path="franchise-brand-mapping/*" element={<FranchiseBrandMapping />} />
        <Route path="franchise-category-mapping/*" element={<FranchiseCategoryMapping />} />
        <Route path="address/*" element={<Address />} />
        <Route path="location-mapping/*" element={<LocationMapping />} />
        <Route path="franchise-document/*" element={<FranchiseDocument />} />
        <Route path="franchise-performance-history/*" element={<FranchisePerformanceHistory />} />
        <Route path="franchise-user/*" element={<FranchiseUser />} />
        <Route path="franchise-user-status-history/*" element={<FranchiseUserStatusHistory />} />
        <Route path="customer/*" element={<Customer />} />
        <Route path="product/*" element={<Product />} />
        <Route path="hsn/*" element={<Hsn />} />
        <Route path="product-price-history/*" element={<ProductPriceHistory />} />
        <Route path="additional-attribute/*" element={<AdditionalAttribute />} />
        <Route path="additional-attribute-possible-value/*" element={<AdditionalAttributePossibleValue />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
