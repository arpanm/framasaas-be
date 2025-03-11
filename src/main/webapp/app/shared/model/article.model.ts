import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IArticle {
  id?: number;
  serialNo?: string | null;
  vendorArticleId?: string | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  product?: IProduct | null;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<IArticle> = {};
