import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArticleWarrantyDetails from './article-warranty-details';
import ArticleWarrantyDetailsDetail from './article-warranty-details-detail';
import ArticleWarrantyDetailsUpdate from './article-warranty-details-update';
import ArticleWarrantyDetailsDeleteDialog from './article-warranty-details-delete-dialog';

const ArticleWarrantyDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArticleWarrantyDetails />} />
    <Route path="new" element={<ArticleWarrantyDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<ArticleWarrantyDetailsDetail />} />
      <Route path="edit" element={<ArticleWarrantyDetailsUpdate />} />
      <Route path="delete" element={<ArticleWarrantyDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArticleWarrantyDetailsRoutes;
