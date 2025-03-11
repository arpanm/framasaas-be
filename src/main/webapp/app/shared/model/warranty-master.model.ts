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
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IWarrantyMaster> = {
  isActive: false,
};
