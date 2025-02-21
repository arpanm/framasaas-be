import dayjs from 'dayjs';
import { IFranchiseUser } from 'app/shared/model/franchise-user.model';
import { FranchiseUserStatus } from 'app/shared/model/enumerations/franchise-user-status.model';

export interface IFranchiseUserStatusHistory {
  id?: number;
  userSatus?: keyof typeof FranchiseUserStatus;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchiseUser?: IFranchiseUser | null;
}

export const defaultValue: Readonly<IFranchiseUserStatusHistory> = {};
