import { IFranchise } from 'app/shared/model/franchise.model';
import { PerformanceTag } from 'app/shared/model/enumerations/performance-tag.model';

export interface IFranchisePerformanceHistory {
  id?: number;
  performanceScore?: number | null;
  performanceTag?: keyof typeof PerformanceTag | null;
  updatedBy?: string | null;
  updatedTime?: string;
  franchise?: IFranchise | null;
}

export const defaultValue: Readonly<IFranchisePerformanceHistory> = {};
