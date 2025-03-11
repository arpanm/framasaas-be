import dayjs from 'dayjs';
import { Language } from 'app/shared/model/enumerations/language.model';
import { UserStatus } from 'app/shared/model/enumerations/user-status.model';

export interface ICustomer {
  id?: number;
  email?: string | null;
  contact?: number;
  alternameContact?: number | null;
  language?: keyof typeof Language | null;
  userStatus?: keyof typeof UserStatus | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<ICustomer> = {};
