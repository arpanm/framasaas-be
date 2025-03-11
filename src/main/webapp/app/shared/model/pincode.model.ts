import dayjs from 'dayjs';

export interface IPincode {
  id?: number;
  pincode?: string;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IPincode> = {};
