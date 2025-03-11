import dayjs from 'dayjs';
import { ILocationMapping } from 'app/shared/model/location-mapping.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IAddress {
  id?: number;
  address1?: string;
  address2?: string | null;
  city?: string;
  area?: string | null;
  district?: string | null;
  pincode?: number;
  state?: string | null;
  country?: string | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  location?: ILocationMapping | null;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<IAddress> = {};
