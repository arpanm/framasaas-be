import dayjs from 'dayjs';
import { ICategory } from 'app/shared/model/category.model';
import { IBrand } from 'app/shared/model/brand.model';
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
  category?: ICategory | null;
  brand?: IBrand | null;
  hsn?: IHsn | null;
}

export const defaultValue: Readonly<IProduct> = {};
