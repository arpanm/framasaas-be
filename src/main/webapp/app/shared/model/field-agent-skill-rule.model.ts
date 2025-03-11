import dayjs from 'dayjs';
import { IFieldAgentSkillRuleSet } from 'app/shared/model/field-agent-skill-rule-set.model';
import { FieldAgentSkillRuleType } from 'app/shared/model/enumerations/field-agent-skill-rule-type.model';
import { FieldAgentSkillRuleJoinType } from 'app/shared/model/enumerations/field-agent-skill-rule-join-type.model';

export interface IFieldAgentSkillRule {
  id?: number;
  skillType?: keyof typeof FieldAgentSkillRuleType | null;
  joinType?: keyof typeof FieldAgentSkillRuleJoinType | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  fieldAgentSkillRuleSet?: IFieldAgentSkillRuleSet | null;
}

export const defaultValue: Readonly<IFieldAgentSkillRule> = {};
