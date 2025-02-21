import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Franchise from './franchise';
import FranchiseDetail from './franchise-detail';
import FranchiseUpdate from './franchise-update';
import FranchiseDeleteDialog from './franchise-delete-dialog';

const FranchiseRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Franchise />} />
    <Route path="new" element={<FranchiseUpdate />} />
    <Route path=":id">
      <Route index element={<FranchiseDetail />} />
      <Route path="edit" element={<FranchiseUpdate />} />
      <Route path="delete" element={<FranchiseDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FranchiseRoutes;
