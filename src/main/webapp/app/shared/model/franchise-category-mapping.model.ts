import dayjs from 'dayjs';
import { IFranchise } from 'app/shared/model/franchise.model';
import { ServiceCategory } from 'app/shared/model/enumerations/service-category.model';

export interface IFranchiseCategoryMapping {
  id?: number;
  serviceCategory?: keyof typeof ServiceCategory;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchise?: IFranchise | null;
}

export const defaultValue: Readonly<IFranchiseCategoryMapping> = {};
