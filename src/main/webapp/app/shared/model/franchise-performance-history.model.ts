import dayjs from 'dayjs';
import { IFranchise } from 'app/shared/model/franchise.model';
import { PerformanceTag } from 'app/shared/model/enumerations/performance-tag.model';

export interface IFranchisePerformanceHistory {
  id?: number;
  performanceScore?: number | null;
  performanceTag?: keyof typeof PerformanceTag | null;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  franchise?: IFranchise | null;
}

export const defaultValue: Readonly<IFranchisePerformanceHistory> = {};
