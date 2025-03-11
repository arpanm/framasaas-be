import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ServiceOrderAssignmentStatus } from 'app/shared/model/enumerations/service-order-assignment-status.model';
import { createEntity, getEntity, updateEntity } from './service-order-field-agent-assignment.reducer';

export const ServiceOrderFieldAgentAssignmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const serviceOrderFieldAgentAssignmentEntity = useAppSelector(state => state.serviceOrderFieldAgentAssignment.entity);
  const loading = useAppSelector(state => state.serviceOrderFieldAgentAssignment.loading);
  const updating = useAppSelector(state => state.serviceOrderFieldAgentAssignment.updating);
  const updateSuccess = useAppSelector(state => state.serviceOrderFieldAgentAssignment.updateSuccess);
  const serviceOrderAssignmentStatusValues = Object.keys(ServiceOrderAssignmentStatus);

  const handleClose = () => {
    navigate('/service-order-field-agent-assignment');
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
    if (values.nps !== undefined && typeof values.nps !== 'number') {
      values.nps = Number(values.nps);
    }
    values.assignedTime = convertDateTimeToServer(values.assignedTime);
    values.movedBackTime = convertDateTimeToServer(values.movedBackTime);
    values.visitTime = convertDateTimeToServer(values.visitTime);
    values.spareOrderTime = convertDateTimeToServer(values.spareOrderTime);
    values.spareDeliveryTime = convertDateTimeToServer(values.spareDeliveryTime);
    values.completedTime = convertDateTimeToServer(values.completedTime);
    if (values.completionOTP !== undefined && typeof values.completionOTP !== 'number') {
      values.completionOTP = Number(values.completionOTP);
    }
    if (values.cancellationOTP !== undefined && typeof values.cancellationOTP !== 'number') {
      values.cancellationOTP = Number(values.cancellationOTP);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...serviceOrderFieldAgentAssignmentEntity,
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
          assignedTime: displayDefaultDateTime(),
          movedBackTime: displayDefaultDateTime(),
          visitTime: displayDefaultDateTime(),
          spareOrderTime: displayDefaultDateTime(),
          spareDeliveryTime: displayDefaultDateTime(),
          completedTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          serviceOrderAssignmentStatus: 'ASSIGNED',
          ...serviceOrderFieldAgentAssignmentEntity,
          assignedTime: convertDateTimeFromServer(serviceOrderFieldAgentAssignmentEntity.assignedTime),
          movedBackTime: convertDateTimeFromServer(serviceOrderFieldAgentAssignmentEntity.movedBackTime),
          visitTime: convertDateTimeFromServer(serviceOrderFieldAgentAssignmentEntity.visitTime),
          spareOrderTime: convertDateTimeFromServer(serviceOrderFieldAgentAssignmentEntity.spareOrderTime),
          spareDeliveryTime: convertDateTimeFromServer(serviceOrderFieldAgentAssignmentEntity.spareDeliveryTime),
          completedTime: convertDateTimeFromServer(serviceOrderFieldAgentAssignmentEntity.completedTime),
          createdTime: convertDateTimeFromServer(serviceOrderFieldAgentAssignmentEntity.createdTime),
          updatedTime: convertDateTimeFromServer(serviceOrderFieldAgentAssignmentEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="framasaasApp.serviceOrderFieldAgentAssignment.home.createOrEditLabel"
            data-cy="ServiceOrderFieldAgentAssignmentCreateUpdateHeading"
          >
            <Translate contentKey="framasaasApp.serviceOrderFieldAgentAssignment.home.createOrEditLabel">
              Create or edit a ServiceOrderFieldAgentAssignment
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
                  id="service-order-field-agent-assignment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.serviceOrderAssignmentStatus')}
                id="service-order-field-agent-assignment-serviceOrderAssignmentStatus"
                name="serviceOrderAssignmentStatus"
                data-cy="serviceOrderAssignmentStatus"
                type="select"
              >
                {serviceOrderAssignmentStatusValues.map(serviceOrderAssignmentStatus => (
                  <option value={serviceOrderAssignmentStatus} key={serviceOrderAssignmentStatus}>
                    {translate(`framasaasApp.ServiceOrderAssignmentStatus.${serviceOrderAssignmentStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.nps')}
                id="service-order-field-agent-assignment-nps"
                name="nps"
                data-cy="nps"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.isActive')}
                id="service-order-field-agent-assignment-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.assignedTime')}
                id="service-order-field-agent-assignment-assignedTime"
                name="assignedTime"
                data-cy="assignedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.movedBackTime')}
                id="service-order-field-agent-assignment-movedBackTime"
                name="movedBackTime"
                data-cy="movedBackTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.visitTime')}
                id="service-order-field-agent-assignment-visitTime"
                name="visitTime"
                data-cy="visitTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.spareOrderTime')}
                id="service-order-field-agent-assignment-spareOrderTime"
                name="spareOrderTime"
                data-cy="spareOrderTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.spareDeliveryTime')}
                id="service-order-field-agent-assignment-spareDeliveryTime"
                name="spareDeliveryTime"
                data-cy="spareDeliveryTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.completedTime')}
                id="service-order-field-agent-assignment-completedTime"
                name="completedTime"
                data-cy="completedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.completionOTP')}
                id="service-order-field-agent-assignment-completionOTP"
                name="completionOTP"
                data-cy="completionOTP"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.cancellationOTP')}
                id="service-order-field-agent-assignment-cancellationOTP"
                name="cancellationOTP"
                data-cy="cancellationOTP"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.createddBy')}
                id="service-order-field-agent-assignment-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.createdTime')}
                id="service-order-field-agent-assignment-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.updatedBy')}
                id="service-order-field-agent-assignment-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFieldAgentAssignment.updatedTime')}
                id="service-order-field-agent-assignment-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/service-order-field-agent-assignment"
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

export default ServiceOrderFieldAgentAssignmentUpdate;
