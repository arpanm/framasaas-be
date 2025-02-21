import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FranchiseUserStatusHistory from './franchise-user-status-history';
import FranchiseUserStatusHistoryDetail from './franchise-user-status-history-detail';
import FranchiseUserStatusHistoryUpdate from './franchise-user-status-history-update';
import FranchiseUserStatusHistoryDeleteDialog from './franchise-user-status-history-delete-dialog';

const FranchiseUserStatusHistoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FranchiseUserStatusHistory />} />
    <Route path="new" element={<FranchiseUserStatusHistoryUpdate />} />
    <Route path=":id">
      <Route index element={<FranchiseUserStatusHistoryDetail />} />
      <Route path="edit" element={<FranchiseUserStatusHistoryUpdate />} />
      <Route path="delete" element={<FranchiseUserStatusHistoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FranchiseUserStatusHistoryRoutes;
