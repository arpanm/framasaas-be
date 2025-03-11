import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServiceOrderMaster from './service-order-master';
import ServiceOrderMasterDetail from './service-order-master-detail';
import ServiceOrderMasterUpdate from './service-order-master-update';
import ServiceOrderMasterDeleteDialog from './service-order-master-delete-dialog';

const ServiceOrderMasterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServiceOrderMaster />} />
    <Route path="new" element={<ServiceOrderMasterUpdate />} />
    <Route path=":id">
      <Route index element={<ServiceOrderMasterDetail />} />
      <Route path="edit" element={<ServiceOrderMasterUpdate />} />
      <Route path="delete" element={<ServiceOrderMasterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServiceOrderMasterRoutes;
