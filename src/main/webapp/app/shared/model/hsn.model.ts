import dayjs from 'dayjs';

export interface IHsn {
  id?: number;
  hsnCD?: string;
  description?: string | null;
  taxRate?: number;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IHsn> = {};
