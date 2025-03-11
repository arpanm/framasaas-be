import dayjs from 'dayjs';
import { IServiceOrder } from 'app/shared/model/service-order.model';
import { IProduct } from 'app/shared/model/product.model';
import { InventoryLocationType } from 'app/shared/model/enumerations/inventory-location-type.model';
import { ServiceOrderSpareStatus } from 'app/shared/model/enumerations/service-order-spare-status.model';

export interface IServiceOrderSpare {
  id?: number;
  price?: number | null;
  tax?: number | null;
  totalCharge?: number | null;
  franchiseCommision?: number | null;
  franchiseCommisionTax?: number | null;
  orderedFrom?: keyof typeof InventoryLocationType | null;
  spareStatus?: keyof typeof ServiceOrderSpareStatus | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  serviceOrder?: IServiceOrder | null;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IServiceOrderSpare> = {};
