import dayjs from 'dayjs';
import { FranchiseAllocationRuleSetSortType } from 'app/shared/model/enumerations/franchise-allocation-rule-set-sort-type.model';

export interface IFranchiseAllocationRuleSet {
  id?: number;
  name?: string;
  sortType?: keyof typeof FranchiseAllocationRuleSetSortType;
  priority?: number | null;
  isActive?: boolean;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IFranchiseAllocationRuleSet> = {
  isActive: false,
};
