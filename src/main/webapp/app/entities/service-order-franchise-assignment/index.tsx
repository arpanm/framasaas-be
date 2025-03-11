import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServiceOrderFranchiseAssignment from './service-order-franchise-assignment';
import ServiceOrderFranchiseAssignmentDetail from './service-order-franchise-assignment-detail';
import ServiceOrderFranchiseAssignmentUpdate from './service-order-franchise-assignment-update';
import ServiceOrderFranchiseAssignmentDeleteDialog from './service-order-franchise-assignment-delete-dialog';

const ServiceOrderFranchiseAssignmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServiceOrderFranchiseAssignment />} />
    <Route path="new" element={<ServiceOrderFranchiseAssignmentUpdate />} />
    <Route path=":id">
      <Route index element={<ServiceOrderFranchiseAssignmentDetail />} />
      <Route path="edit" element={<ServiceOrderFranchiseAssignmentUpdate />} />
      <Route path="delete" element={<ServiceOrderFranchiseAssignmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServiceOrderFranchiseAssignmentRoutes;
