import dayjs from 'dayjs';
import { IWarrantyMaster } from 'app/shared/model/warranty-master.model';

export interface IWarrantyMasterPriceHistory {
  id?: number;
  price?: number;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  warrantyMaster?: IWarrantyMaster | null;
}

export const defaultValue: Readonly<IWarrantyMasterPriceHistory> = {};
