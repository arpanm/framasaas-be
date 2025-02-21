import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFranchiseUsers } from 'app/entities/franchise-user/franchise-user.reducer';
import { FranchiseUserStatus } from 'app/shared/model/enumerations/franchise-user-status.model';
import { createEntity, getEntity, updateEntity } from './franchise-user-status-history.reducer';

export const FranchiseUserStatusHistoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchiseUsers = useAppSelector(state => state.franchiseUser.entities);
  const franchiseUserStatusHistoryEntity = useAppSelector(state => state.franchiseUserStatusHistory.entity);
  const loading = useAppSelector(state => state.franchiseUserStatusHistory.loading);
  const updating = useAppSelector(state => state.franchiseUserStatusHistory.updating);
  const updateSuccess = useAppSelector(state => state.franchiseUserStatusHistory.updateSuccess);
  const franchiseUserStatusValues = Object.keys(FranchiseUserStatus);

  const handleClose = () => {
    navigate('/franchise-user-status-history');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFranchiseUsers({}));
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
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...franchiseUserStatusHistoryEntity,
      ...values,
      franchiseUser: franchiseUsers.find(it => it.id.toString() === values.franchiseUser?.toString()),
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
          userSatus: 'PendingApproval',
          ...franchiseUserStatusHistoryEntity,
          updatedTime: convertDateTimeFromServer(franchiseUserStatusHistoryEntity.updatedTime),
          franchiseUser: franchiseUserStatusHistoryEntity?.franchiseUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.franchiseUserStatusHistory.home.createOrEditLabel" data-cy="FranchiseUserStatusHistoryCreateUpdateHeading">
            <Translate contentKey="framasaasApp.franchiseUserStatusHistory.home.createOrEditLabel">
              Create or edit a FranchiseUserStatusHistory
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
                  id="franchise-user-status-history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.franchiseUserStatusHistory.userSatus')}
                id="franchise-user-status-history-userSatus"
                name="userSatus"
                data-cy="userSatus"
                type="select"
              >
                {franchiseUserStatusValues.map(franchiseUserStatus => (
                  <option value={franchiseUserStatus} key={franchiseUserStatus}>
                    {translate(`framasaasApp.FranchiseUserStatus.${franchiseUserStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.franchiseUserStatusHistory.updatedBy')}
                id="franchise-user-status-history-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseUserStatusHistory.updatedTime')}
                id="franchise-user-status-history-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="franchise-user-status-history-franchiseUser"
                name="franchiseUser"
                data-cy="franchiseUser"
                label={translate('framasaasApp.franchiseUserStatusHistory.franchiseUser')}
                type="select"
              >
                <option value="" key="0" />
                {franchiseUsers
                  ? franchiseUsers.map(otherEntity => (
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
                to="/franchise-user-status-history"
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

export default FranchiseUserStatusHistoryUpdate;
