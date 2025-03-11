import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AdditionalAttributePossibleValue from './additional-attribute-possible-value';
import AdditionalAttributePossibleValueDetail from './additional-attribute-possible-value-detail';
import AdditionalAttributePossibleValueUpdate from './additional-attribute-possible-value-update';
import AdditionalAttributePossibleValueDeleteDialog from './additional-attribute-possible-value-delete-dialog';

const AdditionalAttributePossibleValueRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AdditionalAttributePossibleValue />} />
    <Route path="new" element={<AdditionalAttributePossibleValueUpdate />} />
    <Route path=":id">
      <Route index element={<AdditionalAttributePossibleValueDetail />} />
      <Route path="edit" element={<AdditionalAttributePossibleValueUpdate />} />
      <Route path="delete" element={<AdditionalAttributePossibleValueDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdditionalAttributePossibleValueRoutes;
