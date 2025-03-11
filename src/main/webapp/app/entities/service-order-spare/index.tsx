import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServiceOrderSpare from './service-order-spare';
import ServiceOrderSpareDetail from './service-order-spare-detail';
import ServiceOrderSpareUpdate from './service-order-spare-update';
import ServiceOrderSpareDeleteDialog from './service-order-spare-delete-dialog';

const ServiceOrderSpareRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServiceOrderSpare />} />
    <Route path="new" element={<ServiceOrderSpareUpdate />} />
    <Route path=":id">
      <Route index element={<ServiceOrderSpareDetail />} />
      <Route path="edit" element={<ServiceOrderSpareUpdate />} />
      <Route path="delete" element={<ServiceOrderSpareDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServiceOrderSpareRoutes;
