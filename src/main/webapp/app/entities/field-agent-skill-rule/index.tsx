import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldAgentSkillRule from './field-agent-skill-rule';
import FieldAgentSkillRuleDetail from './field-agent-skill-rule-detail';
import FieldAgentSkillRuleUpdate from './field-agent-skill-rule-update';
import FieldAgentSkillRuleDeleteDialog from './field-agent-skill-rule-delete-dialog';

const FieldAgentSkillRuleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldAgentSkillRule />} />
    <Route path="new" element={<FieldAgentSkillRuleUpdate />} />
    <Route path=":id">
      <Route index element={<FieldAgentSkillRuleDetail />} />
      <Route path="edit" element={<FieldAgentSkillRuleUpdate />} />
      <Route path="delete" element={<FieldAgentSkillRuleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldAgentSkillRuleRoutes;
