import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import WarrantyMaster from './warranty-master';
import WarrantyMasterDetail from './warranty-master-detail';
import WarrantyMasterUpdate from './warranty-master-update';
import WarrantyMasterDeleteDialog from './warranty-master-delete-dialog';

const WarrantyMasterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<WarrantyMaster />} />
    <Route path="new" element={<WarrantyMasterUpdate />} />
    <Route path=":id">
      <Route index element={<WarrantyMasterDetail />} />
      <Route path="edit" element={<WarrantyMasterUpdate />} />
      <Route path="delete" element={<WarrantyMasterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WarrantyMasterRoutes;
