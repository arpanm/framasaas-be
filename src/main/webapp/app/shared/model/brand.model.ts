import dayjs from 'dayjs';
import { IFranchiseAllocationRule } from 'app/shared/model/franchise-allocation-rule.model';

export interface IBrand {
  id?: number;
  brandName?: string;
  logoPath?: string | null;
  vendorBrandId?: string;
  description?: string | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchiseRule?: IFranchiseAllocationRule | null;
}

export const defaultValue: Readonly<IBrand> = {};
