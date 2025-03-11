import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProductPriceHistory from './product-price-history';
import ProductPriceHistoryDetail from './product-price-history-detail';
import ProductPriceHistoryUpdate from './product-price-history-update';
import ProductPriceHistoryDeleteDialog from './product-price-history-delete-dialog';

const ProductPriceHistoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProductPriceHistory />} />
    <Route path="new" element={<ProductPriceHistoryUpdate />} />
    <Route path=":id">
      <Route index element={<ProductPriceHistoryDetail />} />
      <Route path="edit" element={<ProductPriceHistoryUpdate />} />
      <Route path="delete" element={<ProductPriceHistoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProductPriceHistoryRoutes;
