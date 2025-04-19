import dayjs from 'dayjs';
import { IArticle } from 'app/shared/model/article.model';
import { IWarrantyMaster } from 'app/shared/model/warranty-master.model';
import { WarrantyType } from 'app/shared/model/enumerations/warranty-type.model';
import { SoldBy } from 'app/shared/model/enumerations/sold-by.model';

export interface IArticleWarrantyDetails {
  id?: number;
  warrantyType?: keyof typeof WarrantyType | null;
  vendorArticleWarrantyId?: string | null;
  vendorWarrantyMasterId?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  soldBy?: keyof typeof SoldBy | null;
  soldByUser?: string | null;
  soldDate?: dayjs.Dayjs | null;
  isValidated?: boolean | null;
  validatedBy?: string | null;
  validatedTime?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  article?: IArticle | null;
  warrantyMaster?: IWarrantyMaster | null;
}

export const defaultValue: Readonly<IArticleWarrantyDetails> = {
  isValidated: false,
  isActive: false,
};
