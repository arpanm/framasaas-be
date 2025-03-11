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
      <MenuItem icon="asterisk" to="/franchise-performance-history">
        <Translate contentKey="global.menu.entities.franchisePerformanceHistory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/franchise-user">
        <Translate contentKey="global.menu.entities.franchiseUser" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/franchise-user-status-history">
        <Translate contentKey="global.menu.entities.franchiseUserStatusHistory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer">
        <Translate contentKey="global.menu.entities.customer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        <Translate contentKey="global.menu.entities.product" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/hsn">
        <Translate contentKey="global.menu.entities.hsn" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/product-price-history">
        <Translate contentKey="global.menu.entities.productPriceHistory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/additional-attribute">
        <Translate contentKey="global.menu.entities.additionalAttribute" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/additional-attribute-possible-value">
        <Translate contentKey="global.menu.entities.additionalAttributePossibleValue" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/brand">
        <Translate contentKey="global.menu.entities.brand" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/category">
        <Translate contentKey="global.menu.entities.category" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pincode">
        <Translate contentKey="global.menu.entities.pincode" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/franchise-allocation-rule-set">
        <Translate contentKey="global.menu.entities.franchiseAllocationRuleSet" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/franchise-allocation-rule">
        <Translate contentKey="global.menu.entities.franchiseAllocationRule" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
