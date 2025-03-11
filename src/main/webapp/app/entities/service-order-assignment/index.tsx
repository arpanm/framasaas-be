import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServiceOrderAssignment from './service-order-assignment';
import ServiceOrderAssignmentDetail from './service-order-assignment-detail';
import ServiceOrderAssignmentUpdate from './service-order-assignment-update';
import ServiceOrderAssignmentDeleteDialog from './service-order-assignment-delete-dialog';

const ServiceOrderAssignmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServiceOrderAssignment />} />
    <Route path="new" element={<ServiceOrderAssignmentUpdate />} />
    <Route path=":id">
      <Route index element={<ServiceOrderAssignmentDetail />} />
      <Route path="edit" element={<ServiceOrderAssignmentUpdate />} />
      <Route path="delete" element={<ServiceOrderAssignmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServiceOrderAssignmentRoutes;
