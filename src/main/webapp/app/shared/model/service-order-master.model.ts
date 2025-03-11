import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';
import { ServiceOrderType } from 'app/shared/model/enumerations/service-order-type.model';

export interface IServiceOrderMaster {
  id?: number;
  serviceOrderType?: keyof typeof ServiceOrderType | null;
  slaInHours?: number | null;
  charge?: number | null;
  tax?: number | null;
  franchiseCommissionWithinSla?: number | null;
  franchiseChargeWithinSlaTax?: number | null;
  franchiseCommissionOutsideSla?: number | null;
  franchiseChargeOutsideSlaTax?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IServiceOrderMaster> = {
  isActive: false,
};
