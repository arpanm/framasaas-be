import dayjs from 'dayjs';
import { IFranchiseAllocationRule } from 'app/shared/model/franchise-allocation-rule.model';
import { IFieldAgentSkillRule } from 'app/shared/model/field-agent-skill-rule.model';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface ILanguageMapping {
  id?: number;
  lang?: keyof typeof Language;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchiseRule?: IFranchiseAllocationRule | null;
  fieldAgentSkillRule?: IFieldAgentSkillRule | null;
}

export const defaultValue: Readonly<ILanguageMapping> = {};
