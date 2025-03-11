import dayjs from 'dayjs';
import { IFranchiseAllocationRule } from 'app/shared/model/franchise-allocation-rule.model';

export interface ICategory {
  id?: number;
  categoryName?: string;
  imagePath?: string | null;
  vendorCategoryId?: string;
  description?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchiseRule?: IFranchiseAllocationRule | null;
}

export const defaultValue: Readonly<ICategory> = {
  isActive: false,
};
