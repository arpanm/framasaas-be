import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFranchises } from 'app/entities/franchise/franchise.reducer';
import { PerformanceTag } from 'app/shared/model/enumerations/performance-tag.model';
import { createEntity, getEntity, updateEntity } from './franchise-performance-history.reducer';

export const FranchisePerformanceHistoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchises = useAppSelector(state => state.franchise.entities);
  const franchisePerformanceHistoryEntity = useAppSelector(state => state.franchisePerformanceHistory.entity);
  const loading = useAppSelector(state => state.franchisePerformanceHistory.loading);
  const updating = useAppSelector(state => state.franchisePerformanceHistory.updating);
  const updateSuccess = useAppSelector(state => state.franchisePerformanceHistory.updateSuccess);
  const performanceTagValues = Object.keys(PerformanceTag);

  const handleClose = () => {
    navigate('/franchise-performance-history');
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
    if (values.performanceScore !== undefined && typeof values.performanceScore !== 'number') {
      values.performanceScore = Number(values.performanceScore);
    }
    values.updatedTime = convertDateTimeToServer(values.updatedTime);
    values.createdTime = convertDateTimeToServer(values.createdTime);

    const entity = {
      ...franchisePerformanceHistoryEntity,
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
      ? {
          updatedTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
        }
      : {
          performanceTag: 'High',
          ...franchisePerformanceHistoryEntity,
          updatedTime: convertDateTimeFromServer(franchisePerformanceHistoryEntity.updatedTime),
          createdTime: convertDateTimeFromServer(franchisePerformanceHistoryEntity.createdTime),
          franchise: franchisePerformanceHistoryEntity?.franchise?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.franchisePerformanceHistory.home.createOrEditLabel" data-cy="FranchisePerformanceHistoryCreateUpdateHeading">
            <Translate contentKey="framasaasApp.franchisePerformanceHistory.home.createOrEditLabel">
              Create or edit a FranchisePerformanceHistory
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
                  id="franchise-performance-history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.franchisePerformanceHistory.performanceScore')}
                id="franchise-performance-history-performanceScore"
                name="performanceScore"
                data-cy="performanceScore"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.franchisePerformanceHistory.performanceTag')}
                id="franchise-performance-history-performanceTag"
                name="performanceTag"
                data-cy="performanceTag"
                type="select"
              >
                {performanceTagValues.map(performanceTag => (
                  <option value={performanceTag} key={performanceTag}>
                    {translate(`framasaasApp.PerformanceTag.${performanceTag}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.franchisePerformanceHistory.updatedBy')}
                id="franchise-performance-history-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchisePerformanceHistory.updatedTime')}
                id="franchise-performance-history-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchisePerformanceHistory.createddBy')}
                id="franchise-performance-history-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchisePerformanceHistory.createdTime')}
                id="franchise-performance-history-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="franchise-performance-history-franchise"
                name="franchise"
                data-cy="franchise"
                label={translate('framasaasApp.franchisePerformanceHistory.franchise')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/franchise-performance-history"
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

export default FranchisePerformanceHistoryUpdate;
