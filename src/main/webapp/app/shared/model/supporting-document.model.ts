import dayjs from 'dayjs';
import { IFranchise } from 'app/shared/model/franchise.model';
import { IArticleWarrantyDetails } from 'app/shared/model/article-warranty-details.model';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';
import { DocumentFormat } from 'app/shared/model/enumerations/document-format.model';

export interface ISupportingDocument {
  id?: number;
  documentName?: string;
  documentType?: keyof typeof DocumentType;
  documentFormat?: keyof typeof DocumentFormat;
  documentSize?: number | null;
  documentPath?: string;
  isValidated?: boolean;
  validatedBy?: string;
  validatedTime?: dayjs.Dayjs;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchise?: IFranchise | null;
  articleWarranty?: IArticleWarrantyDetails | null;
}

export const defaultValue: Readonly<ISupportingDocument> = {
  isValidated: false,
};
