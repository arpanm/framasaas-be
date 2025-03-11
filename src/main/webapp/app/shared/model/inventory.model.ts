import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';

export interface IInventory {
  id?: number;
  inventoryValue?: number;
  isSellable?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  product?: IProduct | null;
  location?: IInventoryLocation | null;
}

export const defaultValue: Readonly<IInventory> = {
  isSellable: false,
};
