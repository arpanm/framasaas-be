import dayjs from 'dayjs';
import { IFranchise } from 'app/shared/model/franchise.model';
import { IFranchiseStatusHistory } from 'app/shared/model/franchise-status-history.model';
import { IFranchisePerformanceHistory } from 'app/shared/model/franchise-performance-history.model';
import { IAddress } from 'app/shared/model/address.model';
import { ILocationMapping } from 'app/shared/model/location-mapping.model';
import { IFranchiseUser } from 'app/shared/model/franchise-user.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IFranchiseDocument } from 'app/shared/model/franchise-document.model';
import { AttributeType } from 'app/shared/model/enumerations/attribute-type.model';

export interface IAdditionalAttribute {
  id?: number;
  attributeName?: string;
  attributeValue?: string;
  attributeType?: keyof typeof AttributeType | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchise?: IFranchise | null;
  franchiseStatus?: IFranchiseStatusHistory | null;
  franchisePerformance?: IFranchisePerformanceHistory | null;
  address?: IAddress | null;
  location?: ILocationMapping | null;
  franchiseUser?: IFranchiseUser | null;
  customer?: ICustomer | null;
  document?: IFranchiseDocument | null;
}

export const defaultValue: Readonly<IAdditionalAttribute> = {};
