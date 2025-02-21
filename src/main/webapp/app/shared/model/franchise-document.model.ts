import { IFranchise } from 'app/shared/model/franchise.model';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';
import { DocumentFormat } from 'app/shared/model/enumerations/document-format.model';

export interface IFranchiseDocument {
  id?: number;
  documentName?: string;
  documentType?: keyof typeof DocumentType;
  documentFormat?: keyof typeof DocumentFormat;
  documentSize?: number | null;
  documentPath?: string;
  franchise?: IFranchise | null;
}

export const defaultValue: Readonly<IFranchiseDocument> = {};
