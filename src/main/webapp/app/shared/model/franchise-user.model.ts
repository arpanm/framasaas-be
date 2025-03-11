import dayjs from 'dayjs';
import { IFranchise } from 'app/shared/model/franchise.model';
import { FranchiseUserStatus } from 'app/shared/model/enumerations/franchise-user-status.model';
import { FranchiseUserRole } from 'app/shared/model/enumerations/franchise-user-role.model';

export interface IFranchiseUser {
  id?: number;
  userName?: string;
  email?: string;
  contact?: number;
  userStatus?: keyof typeof FranchiseUserStatus | null;
  userRole?: keyof typeof FranchiseUserRole | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchise?: IFranchise | null;
}

export const defaultValue: Readonly<IFranchiseUser> = {};
