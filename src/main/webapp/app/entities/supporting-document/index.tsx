import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SupportingDocument from './supporting-document';
import SupportingDocumentDetail from './supporting-document-detail';
import SupportingDocumentUpdate from './supporting-document-update';
import SupportingDocumentDeleteDialog from './supporting-document-delete-dialog';

const SupportingDocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SupportingDocument />} />
    <Route path="new" element={<SupportingDocumentUpdate />} />
    <Route path=":id">
      <Route index element={<SupportingDocumentDetail />} />
      <Route path="edit" element={<SupportingDocumentUpdate />} />
      <Route path="delete" element={<SupportingDocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SupportingDocumentRoutes;
