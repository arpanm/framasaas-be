import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FranchiseUser from './franchise-user';
import FranchiseUserDetail from './franchise-user-detail';
import FranchiseUserUpdate from './franchise-user-update';
import FranchiseUserDeleteDialog from './franchise-user-delete-dialog';

const FranchiseUserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FranchiseUser />} />
    <Route path="new" element={<FranchiseUserUpdate />} />
    <Route path=":id">
      <Route index element={<FranchiseUserDetail />} />
      <Route path="edit" element={<FranchiseUserUpdate />} />
      <Route path="delete" element={<FranchiseUserDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FranchiseUserRoutes;
