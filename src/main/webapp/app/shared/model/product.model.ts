import dayjs from 'dayjs';
import { IHsn } from 'app/shared/model/hsn.model';
import { ProductType } from 'app/shared/model/enumerations/product-type.model';

export interface IProduct {
  id?: number;
  productName?: string;
  vendorProductId?: string;
  description?: string | null;
  price?: number;
  productType?: keyof typeof ProductType | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  hsn?: IHsn | null;
}

export const defaultValue: Readonly<IProduct> = {};
