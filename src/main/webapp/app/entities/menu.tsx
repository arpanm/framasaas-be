import React from 'react';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/franchise">
        <Translate contentKey="global.menu.entities.franchise" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/franchise-status-history">
        <Translate contentKey="global.menu.entities.franchiseStatusHistory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/franchise-brand-mapping">
        <Translate contentKey="global.menu.entities.franchiseBrandMapping" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/franchise-category-mapping">
        <Translate contentKey="global.menu.entities.franchiseCategoryMapping" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/address">
        <Translate contentKey="global.menu.entities.address" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/location-mapping">
        <Translate contentKey="global.menu.entities.locationMapping" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/franchise-document">
        <Translate contentKey="global.menu.entities.franchiseDocument" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
