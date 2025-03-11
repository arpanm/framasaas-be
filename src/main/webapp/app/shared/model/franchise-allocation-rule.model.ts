import dayjs from 'dayjs';
import { FranchiseAllocationRuleType } from 'app/shared/model/enumerations/franchise-allocation-rule-type.model';
import { FranchiseAllocationRuleJoinType } from 'app/shared/model/enumerations/franchise-allocation-rule-join-type.model';

export interface IFranchiseAllocationRule {
  id?: number;
  ruleType?: keyof typeof FranchiseAllocationRuleType;
  joinType?: keyof typeof FranchiseAllocationRuleJoinType;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IFranchiseAllocationRule> = {};
