import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FranchiseAllocationRuleSet from './franchise-allocation-rule-set';
import FranchiseAllocationRuleSetDetail from './franchise-allocation-rule-set-detail';
import FranchiseAllocationRuleSetUpdate from './franchise-allocation-rule-set-update';
import FranchiseAllocationRuleSetDeleteDialog from './franchise-allocation-rule-set-delete-dialog';

const FranchiseAllocationRuleSetRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FranchiseAllocationRuleSet />} />
    <Route path="new" element={<FranchiseAllocationRuleSetUpdate />} />
    <Route path=":id">
      <Route index element={<FranchiseAllocationRuleSetDetail />} />
      <Route path="edit" element={<FranchiseAllocationRuleSetUpdate />} />
      <Route path="delete" element={<FranchiseAllocationRuleSetDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FranchiseAllocationRuleSetRoutes;
