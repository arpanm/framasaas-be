import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FranchiseStatusHistory from './franchise-status-history';
import FranchiseStatusHistoryDetail from './franchise-status-history-detail';
import FranchiseStatusHistoryUpdate from './franchise-status-history-update';
import FranchiseStatusHistoryDeleteDialog from './franchise-status-history-delete-dialog';

const FranchiseStatusHistoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FranchiseStatusHistory />} />
    <Route path="new" element={<FranchiseStatusHistoryUpdate />} />
    <Route path=":id">
      <Route index element={<FranchiseStatusHistoryDetail />} />
      <Route path="edit" element={<FranchiseStatusHistoryUpdate />} />
      <Route path="delete" element={<FranchiseStatusHistoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FranchiseStatusHistoryRoutes;
