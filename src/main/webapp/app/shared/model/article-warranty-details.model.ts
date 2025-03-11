import dayjs from 'dayjs';
import { IArticle } from 'app/shared/model/article.model';
import { WarrantyType } from 'app/shared/model/enumerations/warranty-type.model';

export interface IArticleWarrantyDetails {
  id?: number;
  warrantyType?: keyof typeof WarrantyType | null;
  vendorArticleWarrantyId?: string | null;
  vendorWarrantyMasterId?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  article?: IArticle | null;
}

export const defaultValue: Readonly<IArticleWarrantyDetails> = {
  isActive: false,
};
