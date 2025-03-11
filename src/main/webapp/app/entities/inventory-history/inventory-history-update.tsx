import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { InventoryChangeReason } from 'app/shared/model/enumerations/inventory-change-reason.model';
import { createEntity, getEntity, updateEntity } from './inventory-history.reducer';

export const InventoryHistoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inventoryHistoryEntity = useAppSelector(state => state.inventoryHistory.entity);
  const loading = useAppSelector(state => state.inventoryHistory.loading);
  const updating = useAppSelector(state => state.inventoryHistory.updating);
  const updateSuccess = useAppSelector(state => state.inventoryHistory.updateSuccess);
  const inventoryChangeReasonValues = Object.keys(InventoryChangeReason);

  const handleClose = () => {
    navigate('/inventory-history');
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
    if (values.inventoryValue !== undefined && typeof values.inventoryValue !== 'number') {
      values.inventoryValue = Number(values.inventoryValue);
    }
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...inventoryHistoryEntity,
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
          updatedTime: displayDefaultDateTime(),
        }
      : {
          reason: 'GRIN',
          ...inventoryHistoryEntity,
          updatedTime: convertDateTimeFromServer(inventoryHistoryEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.inventoryHistory.home.createOrEditLabel" data-cy="InventoryHistoryCreateUpdateHeading">
            <Translate contentKey="framasaasApp.inventoryHistory.home.createOrEditLabel">Create or edit a InventoryHistory</Translate>
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
                  id="inventory-history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.inventoryHistory.inventoryValue')}
                id="inventory-history-inventoryValue"
                name="inventoryValue"
                data-cy="inventoryValue"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.inventoryHistory.reason')}
                id="inventory-history-reason"
                name="reason"
                data-cy="reason"
                type="select"
              >
                {inventoryChangeReasonValues.map(inventoryChangeReason => (
                  <option value={inventoryChangeReason} key={inventoryChangeReason}>
                    {translate(`framasaasApp.InventoryChangeReason.${inventoryChangeReason}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.inventoryHistory.updatedBy')}
                id="inventory-history-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.inventoryHistory.updatedTime')}
                id="inventory-history-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inventory-history" replace color="info">
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

export default InventoryHistoryUpdate;
