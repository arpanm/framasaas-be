import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Hsn from './hsn';
import HsnDetail from './hsn-detail';
import HsnUpdate from './hsn-update';
import HsnDeleteDialog from './hsn-delete-dialog';

const HsnRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Hsn />} />
    <Route path="new" element={<HsnUpdate />} />
    <Route path=":id">
      <Route index element={<HsnDetail />} />
      <Route path="edit" element={<HsnUpdate />} />
      <Route path="delete" element={<HsnDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HsnRoutes;
