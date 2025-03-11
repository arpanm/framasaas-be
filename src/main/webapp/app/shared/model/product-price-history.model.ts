import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';

export interface IProductPriceHistory {
  id?: number;
  price?: number | null;
  tax?: number | null;
  franchiseCommission?: number | null;
  franchiseTax?: number | null;
  updateDescription?: string | null;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IProductPriceHistory> = {};
