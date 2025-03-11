import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';

export interface IProductPriceHistory {
  id?: number;
  price?: number;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchise?: IProduct | null;
}

export const defaultValue: Readonly<IProductPriceHistory> = {};
