import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/customer.model';
import { IServiceOrderMaster } from 'app/shared/model/service-order-master.model';
import { IArticle } from 'app/shared/model/article.model';
import { IAddress } from 'app/shared/model/address.model';
import { ServiceOrderType } from 'app/shared/model/enumerations/service-order-type.model';
import { ServiceOrderStatus } from 'app/shared/model/enumerations/service-order-status.model';

export interface IServiceOrder {
  id?: number;
  description?: string | null;
  orderType?: keyof typeof ServiceOrderType | null;
  orderStatus?: keyof typeof ServiceOrderStatus | null;
  inWarranty?: boolean | null;
  isFree?: boolean | null;
  isSpareNeeded?: boolean | null;
  confirmedTime?: dayjs.Dayjs | null;
  closedTime?: dayjs.Dayjs | null;
  serviceCharge?: number | null;
  tax?: number | null;
  totalSpareCharges?: number | null;
  totalSpareTax?: number | null;
  totalCharges?: number | null;
  totalPaymentDone?: number | null;
  customerInvoicePath?: string | null;
  nps?: number | null;
  priority?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  customer?: ICustomer | null;
  serviceMaster?: IServiceOrderMaster | null;
  article?: IArticle | null;
  address?: IAddress | null;
}

export const defaultValue: Readonly<IServiceOrder> = {
  inWarranty: false,
  isFree: false,
  isSpareNeeded: false,
  isActive: false,
};
