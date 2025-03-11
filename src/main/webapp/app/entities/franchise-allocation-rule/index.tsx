import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FranchiseAllocationRule from './franchise-allocation-rule';
import FranchiseAllocationRuleDetail from './franchise-allocation-rule-detail';
import FranchiseAllocationRuleUpdate from './franchise-allocation-rule-update';
import FranchiseAllocationRuleDeleteDialog from './franchise-allocation-rule-delete-dialog';

const FranchiseAllocationRuleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FranchiseAllocationRule />} />
    <Route path="new" element={<FranchiseAllocationRuleUpdate />} />
    <Route path=":id">
      <Route index element={<FranchiseAllocationRuleDetail />} />
      <Route path="edit" element={<FranchiseAllocationRuleUpdate />} />
      <Route path="delete" element={<FranchiseAllocationRuleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FranchiseAllocationRuleRoutes;
