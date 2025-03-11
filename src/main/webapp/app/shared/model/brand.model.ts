import dayjs from 'dayjs';

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
}

export const defaultValue: Readonly<IBrand> = {};
