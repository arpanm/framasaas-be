import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServiceOrderFieldAgentAssignment from './service-order-field-agent-assignment';
import ServiceOrderFieldAgentAssignmentDetail from './service-order-field-agent-assignment-detail';
import ServiceOrderFieldAgentAssignmentUpdate from './service-order-field-agent-assignment-update';
import ServiceOrderFieldAgentAssignmentDeleteDialog from './service-order-field-agent-assignment-delete-dialog';

const ServiceOrderFieldAgentAssignmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServiceOrderFieldAgentAssignment />} />
    <Route path="new" element={<ServiceOrderFieldAgentAssignmentUpdate />} />
    <Route path=":id">
      <Route index element={<ServiceOrderFieldAgentAssignmentDetail />} />
      <Route path="edit" element={<ServiceOrderFieldAgentAssignmentUpdate />} />
      <Route path="delete" element={<ServiceOrderFieldAgentAssignmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServiceOrderFieldAgentAssignmentRoutes;
