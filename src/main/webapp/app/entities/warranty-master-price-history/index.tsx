import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WarrantyMasterPriceHistory from './warranty-master-price-history';
import WarrantyMasterPriceHistoryDetail from './warranty-master-price-history-detail';
import WarrantyMasterPriceHistoryUpdate from './warranty-master-price-history-update';
import WarrantyMasterPriceHistoryDeleteDialog from './warranty-master-price-history-delete-dialog';

const WarrantyMasterPriceHistoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WarrantyMasterPriceHistory />} />
    <Route path="new" element={<WarrantyMasterPriceHistoryUpdate />} />
    <Route path=":id">
      <Route index element={<WarrantyMasterPriceHistoryDetail />} />
      <Route path="edit" element={<WarrantyMasterPriceHistoryUpdate />} />
      <Route path="delete" element={<WarrantyMasterPriceHistoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WarrantyMasterPriceHistoryRoutes;
