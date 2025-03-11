import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArticleWarrantyDetailsDocument from './article-warranty-details-document';
import ArticleWarrantyDetailsDocumentDetail from './article-warranty-details-document-detail';
import ArticleWarrantyDetailsDocumentUpdate from './article-warranty-details-document-update';
import ArticleWarrantyDetailsDocumentDeleteDialog from './article-warranty-details-document-delete-dialog';

const ArticleWarrantyDetailsDocumentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArticleWarrantyDetailsDocument />} />
    <Route path="new" element={<ArticleWarrantyDetailsDocumentUpdate />} />
    <Route path=":id">
      <Route index element={<ArticleWarrantyDetailsDocumentDetail />} />
      <Route path="edit" element={<ArticleWarrantyDetailsDocumentUpdate />} />
      <Route path="delete" element={<ArticleWarrantyDetailsDocumentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArticleWarrantyDetailsDocumentRoutes;
