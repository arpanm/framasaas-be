import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LanguageMapping from './language-mapping';
import LanguageMappingDetail from './language-mapping-detail';
import LanguageMappingUpdate from './language-mapping-update';
import LanguageMappingDeleteDialog from './language-mapping-delete-dialog';

const LanguageMappingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LanguageMapping />} />
    <Route path="new" element={<LanguageMappingUpdate />} />
    <Route path=":id">
      <Route index element={<LanguageMappingDetail />} />
      <Route path="edit" element={<LanguageMappingUpdate />} />
      <Route path="delete" element={<LanguageMappingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LanguageMappingRoutes;
