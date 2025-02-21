import dayjs from 'dayjs';
import { IFranchise } from 'app/shared/model/franchise.model';
import { Brand } from 'app/shared/model/enumerations/brand.model';

export interface IFranchiseBrandMapping {
  id?: number;
  brand?: keyof typeof Brand;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchise?: IFranchise | null;
}

export const defaultValue: Readonly<IFranchiseBrandMapping> = {};
