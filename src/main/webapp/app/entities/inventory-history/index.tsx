import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InventoryHistory from './inventory-history';
import InventoryHistoryDetail from './inventory-history-detail';
import InventoryHistoryUpdate from './inventory-history-update';
import InventoryHistoryDeleteDialog from './inventory-history-delete-dialog';

const InventoryHistoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InventoryHistory />} />
    <Route path="new" element={<InventoryHistoryUpdate />} />
    <Route path=":id">
      <Route index element={<InventoryHistoryDetail />} />
      <Route path="edit" element={<InventoryHistoryUpdate />} />
      <Route path="delete" element={<InventoryHistoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InventoryHistoryRoutes;
