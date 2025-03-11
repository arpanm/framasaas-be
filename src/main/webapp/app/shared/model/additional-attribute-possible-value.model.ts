import dayjs from 'dayjs';
import { IAdditionalAttribute } from 'app/shared/model/additional-attribute.model';

export interface IAdditionalAttributePossibleValue {
  id?: number;
  possibleValue?: string | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  attribute?: IAdditionalAttribute | null;
}

export const defaultValue: Readonly<IAdditionalAttributePossibleValue> = {};
