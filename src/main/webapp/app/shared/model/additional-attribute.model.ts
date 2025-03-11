import dayjs from 'dayjs';
import { IFranchise } from 'app/shared/model/franchise.model';
import { IFranchiseStatusHistory } from 'app/shared/model/franchise-status-history.model';
import { IFranchisePerformanceHistory } from 'app/shared/model/franchise-performance-history.model';
import { IBrand } from 'app/shared/model/brand.model';
import { ICategory } from 'app/shared/model/category.model';
import { IAddress } from 'app/shared/model/address.model';
import { ILocationMapping } from 'app/shared/model/location-mapping.model';
import { IFranchiseUser } from 'app/shared/model/franchise-user.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IFranchiseDocument } from 'app/shared/model/franchise-document.model';
import { IProduct } from 'app/shared/model/product.model';
import { IHsn } from 'app/shared/model/hsn.model';
import { IProductPriceHistory } from 'app/shared/model/product-price-history.model';
import { AttributeType } from 'app/shared/model/enumerations/attribute-type.model';

export interface IAdditionalAttribute {
  id?: number;
  attributeName?: string;
  attributeValue?: string;
  attributeType?: keyof typeof AttributeType | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchise?: IFranchise | null;
  franchiseStatus?: IFranchiseStatusHistory | null;
  franchisePerformance?: IFranchisePerformanceHistory | null;
  brand?: IBrand | null;
  category?: ICategory | null;
  address?: IAddress | null;
  location?: ILocationMapping | null;
  franchiseUser?: IFranchiseUser | null;
  customer?: ICustomer | null;
  document?: IFranchiseDocument | null;
  product?: IProduct | null;
  hsn?: IHsn | null;
  priceHistory?: IProductPriceHistory | null;
}

export const defaultValue: Readonly<IAdditionalAttribute> = {};
