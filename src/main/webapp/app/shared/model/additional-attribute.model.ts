import dayjs from 'dayjs';
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
}

export const defaultValue: Readonly<IAdditionalAttribute> = {};
