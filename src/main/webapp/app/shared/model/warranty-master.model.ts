import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';
import { WarrantyType } from 'app/shared/model/enumerations/warranty-type.model';

export interface IWarrantyMaster {
  id?: number;
  name?: string;
  vendorWarrantyMasterId?: string;
  warrantyType?: keyof typeof WarrantyType;
  description?: string | null;
  price?: number;
  tax?: number;
  franchiseCommission?: number;
  franchiseTax?: number;
  periodInMonths?: number;
  taxRate?: number;
  coverage?: string | null;
  exclusion?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  coveredSpares?: IProduct[] | null;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IWarrantyMaster> = {
  isActive: false,
};
