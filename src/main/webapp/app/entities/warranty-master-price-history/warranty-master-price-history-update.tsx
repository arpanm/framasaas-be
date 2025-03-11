import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getWarrantyMasters } from 'app/entities/warranty-master/warranty-master.reducer';
import { createEntity, getEntity, updateEntity } from './warranty-master-price-history.reducer';

export const WarrantyMasterPriceHistoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const warrantyMasters = useAppSelector(state => state.warrantyMaster.entities);
  const warrantyMasterPriceHistoryEntity = useAppSelector(state => state.warrantyMasterPriceHistory.entity);
  const loading = useAppSelector(state => state.warrantyMasterPriceHistory.loading);
  const updating = useAppSelector(state => state.warrantyMasterPriceHistory.updating);
  const updateSuccess = useAppSelector(state => state.warrantyMasterPriceHistory.updateSuccess);

  const handleClose = () => {
    navigate('/warranty-master-price-history');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getWarrantyMasters({}));
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
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...warrantyMasterPriceHistoryEntity,
      ...values,
      warrantyMaster: warrantyMasters.find(it => it.id.toString() === values.warrantyMaster?.toString()),
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
          ...warrantyMasterPriceHistoryEntity,
          updatedTime: convertDateTimeFromServer(warrantyMasterPriceHistoryEntity.updatedTime),
          warrantyMaster: warrantyMasterPriceHistoryEntity?.warrantyMaster?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.warrantyMasterPriceHistory.home.createOrEditLabel" data-cy="WarrantyMasterPriceHistoryCreateUpdateHeading">
            <Translate contentKey="framasaasApp.warrantyMasterPriceHistory.home.createOrEditLabel">
              Create or edit a WarrantyMasterPriceHistory
            </Translate>
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
                  id="warranty-master-price-history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.warrantyMasterPriceHistory.price')}
                id="warranty-master-price-history-price"
                name="price"
                data-cy="price"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMasterPriceHistory.updatedBy')}
                id="warranty-master-price-history-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMasterPriceHistory.updatedTime')}
                id="warranty-master-price-history-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="warranty-master-price-history-warrantyMaster"
                name="warrantyMaster"
                data-cy="warrantyMaster"
                label={translate('framasaasApp.warrantyMasterPriceHistory.warrantyMaster')}
                type="select"
              >
                <option value="" key="0" />
                {warrantyMasters
                  ? warrantyMasters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/warranty-master-price-history"
                replace
                color="info"
              >
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

export default WarrantyMasterPriceHistoryUpdate;
