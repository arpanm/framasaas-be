import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { InventoryLocationType } from 'app/shared/model/enumerations/inventory-location-type.model';
import { createEntity, getEntity, updateEntity } from './inventory-location.reducer';

export const InventoryLocationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inventoryLocationEntity = useAppSelector(state => state.inventoryLocation.entity);
  const loading = useAppSelector(state => state.inventoryLocation.loading);
  const updating = useAppSelector(state => state.inventoryLocation.updating);
  const updateSuccess = useAppSelector(state => state.inventoryLocation.updateSuccess);
  const inventoryLocationTypeValues = Object.keys(InventoryLocationType);

  const handleClose = () => {
    navigate('/inventory-location');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
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
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...inventoryLocationEntity,
      ...values,
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
          locationType: 'ENGINEER',
          ...inventoryLocationEntity,
          createdTime: convertDateTimeFromServer(inventoryLocationEntity.createdTime),
          updatedTime: convertDateTimeFromServer(inventoryLocationEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.inventoryLocation.home.createOrEditLabel" data-cy="InventoryLocationCreateUpdateHeading">
            <Translate contentKey="framasaasApp.inventoryLocation.home.createOrEditLabel">Create or edit a InventoryLocation</Translate>
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
                  id="inventory-location-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.inventoryLocation.name')}
                id="inventory-location-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.inventoryLocation.locationType')}
                id="inventory-location-locationType"
                name="locationType"
                data-cy="locationType"
                type="select"
              >
                {inventoryLocationTypeValues.map(inventoryLocationType => (
                  <option value={inventoryLocationType} key={inventoryLocationType}>
                    {translate(`framasaasApp.InventoryLocationType.${inventoryLocationType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.inventoryLocation.isActive')}
                id="inventory-location-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.inventoryLocation.createddBy')}
                id="inventory-location-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.inventoryLocation.createdTime')}
                id="inventory-location-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.inventoryLocation.updatedBy')}
                id="inventory-location-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.inventoryLocation.updatedTime')}
                id="inventory-location-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inventory-location" replace color="info">
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

export default InventoryLocationUpdate;
