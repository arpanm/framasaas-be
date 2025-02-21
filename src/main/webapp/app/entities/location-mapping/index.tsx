import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LocationMapping from './location-mapping';
import LocationMappingDetail from './location-mapping-detail';
import LocationMappingUpdate from './location-mapping-update';
import LocationMappingDeleteDialog from './location-mapping-delete-dialog';

const LocationMappingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LocationMapping />} />
    <Route path="new" element={<LocationMappingUpdate />} />
    <Route path=":id">
      <Route index element={<LocationMappingDetail />} />
      <Route path="edit" element={<LocationMappingUpdate />} />
      <Route path="delete" element={<LocationMappingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocationMappingRoutes;
