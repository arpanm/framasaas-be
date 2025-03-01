import franchise from 'app/entities/franchise/franchise.reducer';
import franchiseStatusHistory from 'app/entities/franchise-status-history/franchise-status-history.reducer';
import franchiseBrandMapping from 'app/entities/franchise-brand-mapping/franchise-brand-mapping.reducer';
import franchiseCategoryMapping from 'app/entities/franchise-category-mapping/franchise-category-mapping.reducer';
import address from 'app/entities/address/address.reducer';
import locationMapping from 'app/entities/location-mapping/location-mapping.reducer';
import franchiseDocument from 'app/entities/franchise-document/franchise-document.reducer';
import franchisePerformanceHistory from 'app/entities/franchise-performance-history/franchise-performance-history.reducer';
import franchiseUser from 'app/entities/franchise-user/franchise-user.reducer';
import franchiseUserStatusHistory from 'app/entities/franchise-user-status-history/franchise-user-status-history.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  franchise,
  franchiseStatusHistory,
  franchiseBrandMapping,
  franchiseCategoryMapping,
  address,
  locationMapping,
  franchiseDocument,
  franchisePerformanceHistory,
  franchiseUser,
  franchiseUserStatusHistory,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
