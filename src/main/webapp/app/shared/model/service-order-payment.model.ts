import dayjs from 'dayjs';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { ModeOfPayment } from 'app/shared/model/enumerations/mode-of-payment.model';

export interface IServiceOrderPayment {
  id?: number;
  paymentLink?: string | null;
  paymentStatus?: keyof typeof PaymentStatus | null;
  mop?: keyof typeof ModeOfPayment | null;
  pgTxnId?: string | null;
  pgTxnResponse?: string | null;
  pgTxnResponseTime?: dayjs.Dayjs | null;
  paymentInitiatedBy?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IServiceOrderPayment> = {
  isActive: false,
};
