import dayjs from 'dayjs';
import { IServiceOrder } from 'app/shared/model/service-order.model';
import { IFranchise } from 'app/shared/model/franchise.model';
import { ServiceOrderAssignmentStatus } from 'app/shared/model/enumerations/service-order-assignment-status.model';

export interface IServiceOrderFranchiseAssignment {
  id?: number;
  serviceOrderAssignmentStatus?: keyof typeof ServiceOrderAssignmentStatus;
  nps?: number | null;
  isActive?: boolean | null;
  assignedTime?: dayjs.Dayjs | null;
  movedBackTime?: dayjs.Dayjs | null;
  visitTime?: dayjs.Dayjs | null;
  spareOrderTime?: dayjs.Dayjs | null;
  spareDeliveryTime?: dayjs.Dayjs | null;
  completedTime?: dayjs.Dayjs | null;
  franchiseCommision?: number | null;
  franchiseCommisionTax?: number | null;
  franchiseInvoicePath?: string | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  serviceOrder?: IServiceOrder | null;
  franchise?: IFranchise | null;
}

export const defaultValue: Readonly<IServiceOrderFranchiseAssignment> = {
  isActive: false,
};
