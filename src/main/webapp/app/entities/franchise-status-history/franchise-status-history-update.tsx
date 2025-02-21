import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFranchises } from 'app/entities/franchise/franchise.reducer';
import { FranchiseStatus } from 'app/shared/model/enumerations/franchise-status.model';
import { createEntity, getEntity, updateEntity } from './franchise-status-history.reducer';

export const FranchiseStatusHistoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchises = useAppSelector(state => state.franchise.entities);
  const franchiseStatusHistoryEntity = useAppSelector(state => state.franchiseStatusHistory.entity);
  const loading = useAppSelector(state => state.franchiseStatusHistory.loading);
  const updating = useAppSelector(state => state.franchiseStatusHistory.updating);
  const updateSuccess = useAppSelector(state => state.franchiseStatusHistory.updateSuccess);
  const franchiseStatusValues = Object.keys(FranchiseStatus);

  const handleClose = () => {
    navigate('/franchise-status-history');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFranchises({}));
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

    const entity = {
      ...franchiseStatusHistoryEntity,
      ...values,
      franchise: franchises.find(it => it.id.toString() === values.franchise?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          franchiseSatus: 'PendingApproval',
          ...franchiseStatusHistoryEntity,
          franchise: franchiseStatusHistoryEntity?.franchise?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.franchiseStatusHistory.home.createOrEditLabel" data-cy="FranchiseStatusHistoryCreateUpdateHeading">
            <Translate contentKey="framasaasApp.franchiseStatusHistory.home.createOrEditLabel">
              Create or edit a FranchiseStatusHistory
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
                  id="franchise-status-history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.franchiseStatusHistory.franchiseSatus')}
                id="franchise-status-history-franchiseSatus"
                name="franchiseSatus"
                data-cy="franchiseSatus"
                type="select"
              >
                {franchiseStatusValues.map(franchiseStatus => (
                  <option value={franchiseStatus} key={franchiseStatus}>
                    {translate(`framasaasApp.FranchiseStatus.${franchiseStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.franchiseStatusHistory.updatedBy')}
                id="franchise-status-history-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseStatusHistory.updatedTime')}
                id="franchise-status-history-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="time"
                placeholder="HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="franchise-status-history-franchise"
                name="franchise"
                data-cy="franchise"
                label={translate('framasaasApp.franchiseStatusHistory.franchise')}
                type="select"
              >
                <option value="" key="0" />
                {franchises
                  ? franchises.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/franchise-status-history" replace color="info">
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

export default FranchiseStatusHistoryUpdate;
