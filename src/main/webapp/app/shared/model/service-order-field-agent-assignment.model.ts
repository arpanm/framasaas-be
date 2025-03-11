import dayjs from 'dayjs';
import { ServiceOrderAssignmentStatus } from 'app/shared/model/enumerations/service-order-assignment-status.model';

export interface IServiceOrderFieldAgentAssignment {
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
  completionOTP?: number | null;
  cancellationOTP?: number | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IServiceOrderFieldAgentAssignment> = {
  isActive: false,
};
