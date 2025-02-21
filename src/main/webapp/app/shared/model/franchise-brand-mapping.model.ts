import { IFranchise } from 'app/shared/model/franchise.model';
import { Brand } from 'app/shared/model/enumerations/brand.model';

export interface IFranchiseBrandMapping {
  id?: number;
  brand?: keyof typeof Brand;
  franchise?: IFranchise | null;
}

export const defaultValue: Readonly<IFranchiseBrandMapping> = {};
