import dayjs from 'dayjs';

export interface ILocationMapping {
  id?: number;
  locationName?: string;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<ILocationMapping> = {};
