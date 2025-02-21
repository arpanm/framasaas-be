import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FranchiseCategoryMapping from './franchise-category-mapping';
import FranchiseCategoryMappingDetail from './franchise-category-mapping-detail';
import FranchiseCategoryMappingUpdate from './franchise-category-mapping-update';
import FranchiseCategoryMappingDeleteDialog from './franchise-category-mapping-delete-dialog';

const FranchiseCategoryMappingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FranchiseCategoryMapping />} />
    <Route path="new" element={<FranchiseCategoryMappingUpdate />} />
    <Route path=":id">
      <Route index element={<FranchiseCategoryMappingDetail />} />
      <Route path="edit" element={<FranchiseCategoryMappingUpdate />} />
      <Route path="delete" element={<FranchiseCategoryMappingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FranchiseCategoryMappingRoutes;
