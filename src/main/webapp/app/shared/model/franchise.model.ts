import dayjs from 'dayjs';
import { IAddress } from 'app/shared/model/address.model';
import { FranchiseStatus } from 'app/shared/model/enumerations/franchise-status.model';
import { PerformanceTag } from 'app/shared/model/enumerations/performance-tag.model';

export interface IFranchise {
  id?: number;
  franchiseName?: string;
  owner?: string | null;
  email?: string;
  contact?: number;
  franchiseStatus?: keyof typeof FranchiseStatus | null;
  gstNumber?: string | null;
  registrationNumber?: string | null;
  performanceScore?: number | null;
  performanceTag?: keyof typeof PerformanceTag | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  address?: IAddress | null;
}

export const defaultValue: Readonly<IFranchise> = {};
