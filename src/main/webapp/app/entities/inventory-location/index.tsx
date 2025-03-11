import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InventoryLocation from './inventory-location';
import InventoryLocationDetail from './inventory-location-detail';
import InventoryLocationUpdate from './inventory-location-update';
import InventoryLocationDeleteDialog from './inventory-location-delete-dialog';

const InventoryLocationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InventoryLocation />} />
    <Route path="new" element={<InventoryLocationUpdate />} />
    <Route path=":id">
      <Route index element={<InventoryLocationDetail />} />
      <Route path="edit" element={<InventoryLocationUpdate />} />
      <Route path="delete" element={<InventoryLocationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InventoryLocationRoutes;
