import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Pincode from './pincode';
import PincodeDetail from './pincode-detail';
import PincodeUpdate from './pincode-update';
import PincodeDeleteDialog from './pincode-delete-dialog';

const PincodeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Pincode />} />
    <Route path="new" element={<PincodeUpdate />} />
    <Route path=":id">
      <Route index element={<PincodeDetail />} />
      <Route path="edit" element={<PincodeUpdate />} />
      <Route path="delete" element={<PincodeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PincodeRoutes;
