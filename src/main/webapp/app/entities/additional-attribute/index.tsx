import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AdditionalAttribute from './additional-attribute';
import AdditionalAttributeDetail from './additional-attribute-detail';
import AdditionalAttributeUpdate from './additional-attribute-update';
import AdditionalAttributeDeleteDialog from './additional-attribute-delete-dialog';

const AdditionalAttributeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AdditionalAttribute />} />
    <Route path="new" element={<AdditionalAttributeUpdate />} />
    <Route path=":id">
      <Route index element={<AdditionalAttributeDetail />} />
      <Route path="edit" element={<AdditionalAttributeUpdate />} />
      <Route path="delete" element={<AdditionalAttributeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdditionalAttributeRoutes;
