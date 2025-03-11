import dayjs from 'dayjs';

export interface ICategory {
  id?: number;
  categoryName?: string;
  imagePath?: string | null;
  vendorCategoryId?: string;
  description?: string | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<ICategory> = {};
