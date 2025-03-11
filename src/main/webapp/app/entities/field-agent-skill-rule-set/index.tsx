import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldAgentSkillRuleSet from './field-agent-skill-rule-set';
import FieldAgentSkillRuleSetDetail from './field-agent-skill-rule-set-detail';
import FieldAgentSkillRuleSetUpdate from './field-agent-skill-rule-set-update';
import FieldAgentSkillRuleSetDeleteDialog from './field-agent-skill-rule-set-delete-dialog';

const FieldAgentSkillRuleSetRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldAgentSkillRuleSet />} />
    <Route path="new" element={<FieldAgentSkillRuleSetUpdate />} />
    <Route path=":id">
      <Route index element={<FieldAgentSkillRuleSetDetail />} />
      <Route path="edit" element={<FieldAgentSkillRuleSetUpdate />} />
      <Route path="delete" element={<FieldAgentSkillRuleSetDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldAgentSkillRuleSetRoutes;
