import { IAddress } from 'app/shared/model/address.model';
import { FranchiseStatus } from 'app/shared/model/enumerations/franchise-status.model';

export interface IFranchise {
  id?: number;
  franchiseName?: string;
  owner?: string | null;
  email?: string;
  contact?: number;
  franchiseStatus?: keyof typeof FranchiseStatus | null;
  gstNumber?: string | null;
  registrationNumber?: string | null;
  performanceScore?: number | null;
  address?: IAddress | null;
}

export const defaultValue: Readonly<IFranchise> = {};
