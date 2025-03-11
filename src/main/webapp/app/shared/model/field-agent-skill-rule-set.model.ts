import dayjs from 'dayjs';
import { FieldAgentSkillRuleSetSortType } from 'app/shared/model/enumerations/field-agent-skill-rule-set-sort-type.model';

export interface IFieldAgentSkillRuleSet {
  id?: number;
  sortType?: keyof typeof FieldAgentSkillRuleSetSortType | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IFieldAgentSkillRuleSet> = {};
