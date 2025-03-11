import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServiceOrder from './service-order';
import ServiceOrderDetail from './service-order-detail';
import ServiceOrderUpdate from './service-order-update';
import ServiceOrderDeleteDialog from './service-order-delete-dialog';

const ServiceOrderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServiceOrder />} />
    <Route path="new" element={<ServiceOrderUpdate />} />
    <Route path=":id">
      <Route index element={<ServiceOrderDetail />} />
      <Route path="edit" element={<ServiceOrderUpdate />} />
      <Route path="delete" element={<ServiceOrderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServiceOrderRoutes;
