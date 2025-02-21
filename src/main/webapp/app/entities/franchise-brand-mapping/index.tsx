import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FranchiseBrandMapping from './franchise-brand-mapping';
import FranchiseBrandMappingDetail from './franchise-brand-mapping-detail';
import FranchiseBrandMappingUpdate from './franchise-brand-mapping-update';
import FranchiseBrandMappingDeleteDialog from './franchise-brand-mapping-delete-dialog';

const FranchiseBrandMappingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FranchiseBrandMapping />} />
    <Route path="new" element={<FranchiseBrandMappingUpdate />} />
    <Route path=":id">
      <Route index element={<FranchiseBrandMappingDetail />} />
      <Route path="edit" element={<FranchiseBrandMappingUpdate />} />
      <Route path="delete" element={<FranchiseBrandMappingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FranchiseBrandMappingRoutes;
