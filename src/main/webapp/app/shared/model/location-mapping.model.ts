import dayjs from 'dayjs';
import { IFranchise } from 'app/shared/model/franchise.model';

export interface ILocationMapping {
  id?: number;
  locationName?: string;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  franchise?: IFranchise | null;
}

export const defaultValue: Readonly<ILocationMapping> = {};
