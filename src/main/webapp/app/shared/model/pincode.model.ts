import dayjs from 'dayjs';
import { IFranchiseAllocationRule } from 'app/shared/model/franchise-allocation-rule.model';

export interface IPincode {
  id?: number;
  pincode?: string;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchiseRule?: IFranchiseAllocationRule | null;
}

export const defaultValue: Readonly<IPincode> = {};
