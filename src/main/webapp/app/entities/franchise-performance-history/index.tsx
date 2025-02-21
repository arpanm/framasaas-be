import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FranchisePerformanceHistory from './franchise-performance-history';
import FranchisePerformanceHistoryDetail from './franchise-performance-history-detail';
import FranchisePerformanceHistoryUpdate from './franchise-performance-history-update';
import FranchisePerformanceHistoryDeleteDialog from './franchise-performance-history-delete-dialog';

const FranchisePerformanceHistoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FranchisePerformanceHistory />} />
    <Route path="new" element={<FranchisePerformanceHistoryUpdate />} />
    <Route path=":id">
      <Route index element={<FranchisePerformanceHistoryDetail />} />
      <Route path="edit" element={<FranchisePerformanceHistoryUpdate />} />
      <Route path="delete" element={<FranchisePerformanceHistoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FranchisePerformanceHistoryRoutes;
