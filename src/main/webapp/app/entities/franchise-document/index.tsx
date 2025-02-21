import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FranchiseDocument from './franchise-document';
import FranchiseDocumentDetail from './franchise-document-detail';
import FranchiseDocumentUpdate from './franchise-document-update';
import FranchiseDocumentDeleteDialog from './franchise-document-delete-dialog';

const FranchiseDocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FranchiseDocument />} />
    <Route path="new" element={<FranchiseDocumentUpdate />} />
    <Route path=":id">
      <Route index element={<FranchiseDocumentDetail />} />
      <Route path="edit" element={<FranchiseDocumentUpdate />} />
      <Route path="delete" element={<FranchiseDocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FranchiseDocumentRoutes;
