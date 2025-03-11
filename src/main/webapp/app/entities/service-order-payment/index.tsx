import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServiceOrderPayment from './service-order-payment';
import ServiceOrderPaymentDetail from './service-order-payment-detail';
import ServiceOrderPaymentUpdate from './service-order-payment-update';
import ServiceOrderPaymentDeleteDialog from './service-order-payment-delete-dialog';

const ServiceOrderPaymentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServiceOrderPayment />} />
    <Route path="new" element={<ServiceOrderPaymentUpdate />} />
    <Route path=":id">
      <Route index element={<ServiceOrderPaymentDetail />} />
      <Route path="edit" element={<ServiceOrderPaymentUpdate />} />
      <Route path="delete" element={<ServiceOrderPaymentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServiceOrderPaymentRoutes;
