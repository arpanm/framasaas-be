import dayjs from 'dayjs';
import { InventoryChangeReason } from 'app/shared/model/enumerations/inventory-change-reason.model';

export interface IInventoryHistory {
  id?: number;
  inventoryValue?: number;
  reason?: keyof typeof InventoryChangeReason;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IInventoryHistory> = {};
