import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';

export interface IProductPriceHistory {
  id?: number;
  price?: number;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IProductPriceHistory> = {};
