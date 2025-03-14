import dayjs from 'dayjs';
import { IFranchise } from 'app/shared/model/franchise.model';
import { FranchiseStatus } from 'app/shared/model/enumerations/franchise-status.model';

export interface IFranchiseStatusHistory {
  id?: number;
  franchiseSatus?: keyof typeof FranchiseStatus;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchise?: IFranchise | null;
}

export const defaultValue: Readonly<IFranchiseStatusHistory> = {};
