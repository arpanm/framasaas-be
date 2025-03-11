import dayjs from 'dayjs';
import { IFranchiseAllocationRule } from 'app/shared/model/franchise-allocation-rule.model';
import { IFieldAgentSkillRule } from 'app/shared/model/field-agent-skill-rule.model';

export interface ILocationMapping {
  id?: number;
  locationName?: string;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchiseRule?: IFranchiseAllocationRule | null;
  fieldAgentSkillRule?: IFieldAgentSkillRule | null;
}

export const defaultValue: Readonly<ILocationMapping> = {};
