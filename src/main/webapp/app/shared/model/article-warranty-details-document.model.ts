import dayjs from 'dayjs';
import { IArticleWarrantyDetails } from 'app/shared/model/article-warranty-details.model';

export interface IArticleWarrantyDetailsDocument {
  id?: number;
  documentPath?: string;
  isValid?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  articleWarranty?: IArticleWarrantyDetails | null;
}

export const defaultValue: Readonly<IArticleWarrantyDetailsDocument> = {
  isValid: false,
};
