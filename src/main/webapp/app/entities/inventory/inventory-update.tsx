import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { getEntities as getInventoryLocations } from 'app/entities/inventory-location/inventory-location.reducer';
import { createEntity, getEntity, updateEntity } from './inventory.reducer';

export const InventoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const inventoryLocations = useAppSelector(state => state.inventoryLocation.entities);
  const inventoryEntity = useAppSelector(state => state.inventory.entity);
  const loading = useAppSelector(state => state.inventory.loading);
  const updating = useAppSelector(state => state.inventory.updating);
  const updateSuccess = useAppSelector(state => state.inventory.updateSuccess);

  const handleClose = () => {
    navigate('/inventory');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getProducts({}));
    dispatch(getInventoryLocations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.inventoryValue !== undefined && typeof values.inventoryValue !== 'number') {
      values.inventoryValue = Number(values.inventoryValue);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...inventoryEntity,
      ...values,
      product: products.find(it => it.id.toString() === values.product?.toString()),
      location: inventoryLocations.find(it => it.id.toString() === values.location?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          ...inventoryEntity,
          createdTime: convertDateTimeFromServer(inventoryEntity.createdTime),
          updatedTime: convertDateTimeFromServer(inventoryEntity.updatedTime),
          product: inventoryEntity?.product?.id,
          location: inventoryEntity?.location?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.inventory.home.createOrEditLabel" data-cy="InventoryCreateUpdateHeading">
            <Translate contentKey="framasaasApp.inventory.home.createOrEditLabel">Create or edit a Inventory</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="inventory-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.inventory.inventoryValue')}
                id="inventory-inventoryValue"
                name="inventoryValue"
                data-cy="inventoryValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.inventory.isSellable')}
                id="inventory-isSellable"
                name="isSellable"
                data-cy="isSellable"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.inventory.createddBy')}
                id="inventory-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.inventory.createdTime')}
                id="inventory-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.inventory.updatedBy')}
                id="inventory-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.inventory.updatedTime')}
                id="inventory-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="inventory-product"
                name="product"
                data-cy="product"
                label={translate('framasaasApp.inventory.product')}
                type="select"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inventory-location"
                name="location"
                data-cy="location"
                label={translate('framasaasApp.inventory.location')}
                type="select"
              >
                <option value="" key="0" />
                {inventoryLocations
                  ? inventoryLocations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inventory" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default InventoryUpdate;
