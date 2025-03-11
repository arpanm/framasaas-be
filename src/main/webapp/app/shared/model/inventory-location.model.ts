import dayjs from 'dayjs';
import { InventoryLocationType } from 'app/shared/model/enumerations/inventory-location-type.model';

export interface IInventoryLocation {
  id?: number;
  name?: string | null;
  locationType?: keyof typeof InventoryLocationType | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IInventoryLocation> = {
  isActive: false,
};
