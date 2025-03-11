import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getServiceOrders } from 'app/entities/service-order/service-order.reducer';
import { getEntities as getFranchises } from 'app/entities/franchise/franchise.reducer';
import { ServiceOrderAssignmentStatus } from 'app/shared/model/enumerations/service-order-assignment-status.model';
import { createEntity, getEntity, updateEntity } from './service-order-franchise-assignment.reducer';

export const ServiceOrderFranchiseAssignmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const serviceOrders = useAppSelector(state => state.serviceOrder.entities);
  const franchises = useAppSelector(state => state.franchise.entities);
  const serviceOrderFranchiseAssignmentEntity = useAppSelector(state => state.serviceOrderFranchiseAssignment.entity);
  const loading = useAppSelector(state => state.serviceOrderFranchiseAssignment.loading);
  const updating = useAppSelector(state => state.serviceOrderFranchiseAssignment.updating);
  const updateSuccess = useAppSelector(state => state.serviceOrderFranchiseAssignment.updateSuccess);
  const serviceOrderAssignmentStatusValues = Object.keys(ServiceOrderAssignmentStatus);

  const handleClose = () => {
    navigate('/service-order-franchise-assignment');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getServiceOrders({}));
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
    if (values.franchiseCommision !== undefined && typeof values.franchiseCommision !== 'number') {
      values.franchiseCommision = Number(values.franchiseCommision);
    }
    if (values.franchiseCommisionTax !== undefined && typeof values.franchiseCommisionTax !== 'number') {
      values.franchiseCommisionTax = Number(values.franchiseCommisionTax);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...serviceOrderFranchiseAssignmentEntity,
      ...values,
      serviceOrder: serviceOrders.find(it => it.id.toString() === values.serviceOrder?.toString()),
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
          ...serviceOrderFranchiseAssignmentEntity,
          assignedTime: convertDateTimeFromServer(serviceOrderFranchiseAssignmentEntity.assignedTime),
          movedBackTime: convertDateTimeFromServer(serviceOrderFranchiseAssignmentEntity.movedBackTime),
          visitTime: convertDateTimeFromServer(serviceOrderFranchiseAssignmentEntity.visitTime),
          spareOrderTime: convertDateTimeFromServer(serviceOrderFranchiseAssignmentEntity.spareOrderTime),
          spareDeliveryTime: convertDateTimeFromServer(serviceOrderFranchiseAssignmentEntity.spareDeliveryTime),
          completedTime: convertDateTimeFromServer(serviceOrderFranchiseAssignmentEntity.completedTime),
          createdTime: convertDateTimeFromServer(serviceOrderFranchiseAssignmentEntity.createdTime),
          updatedTime: convertDateTimeFromServer(serviceOrderFranchiseAssignmentEntity.updatedTime),
          serviceOrder: serviceOrderFranchiseAssignmentEntity?.serviceOrder?.id,
          franchise: serviceOrderFranchiseAssignmentEntity?.franchise?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="framasaasApp.serviceOrderFranchiseAssignment.home.createOrEditLabel"
            data-cy="ServiceOrderFranchiseAssignmentCreateUpdateHeading"
          >
            <Translate contentKey="framasaasApp.serviceOrderFranchiseAssignment.home.createOrEditLabel">
              Create or edit a ServiceOrderFranchiseAssignment
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
                  id="service-order-franchise-assignment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.serviceOrderAssignmentStatus')}
                id="service-order-franchise-assignment-serviceOrderAssignmentStatus"
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
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.nps')}
                id="service-order-franchise-assignment-nps"
                name="nps"
                data-cy="nps"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.isActive')}
                id="service-order-franchise-assignment-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.assignedTime')}
                id="service-order-franchise-assignment-assignedTime"
                name="assignedTime"
                data-cy="assignedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.movedBackTime')}
                id="service-order-franchise-assignment-movedBackTime"
                name="movedBackTime"
                data-cy="movedBackTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.visitTime')}
                id="service-order-franchise-assignment-visitTime"
                name="visitTime"
                data-cy="visitTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.spareOrderTime')}
                id="service-order-franchise-assignment-spareOrderTime"
                name="spareOrderTime"
                data-cy="spareOrderTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.spareDeliveryTime')}
                id="service-order-franchise-assignment-spareDeliveryTime"
                name="spareDeliveryTime"
                data-cy="spareDeliveryTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.completedTime')}
                id="service-order-franchise-assignment-completedTime"
                name="completedTime"
                data-cy="completedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.completionOTP')}
                id="service-order-franchise-assignment-completionOTP"
                name="completionOTP"
                data-cy="completionOTP"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.cancellationOTP')}
                id="service-order-franchise-assignment-cancellationOTP"
                name="cancellationOTP"
                data-cy="cancellationOTP"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.franchiseCommision')}
                id="service-order-franchise-assignment-franchiseCommision"
                name="franchiseCommision"
                data-cy="franchiseCommision"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.franchiseCommisionTax')}
                id="service-order-franchise-assignment-franchiseCommisionTax"
                name="franchiseCommisionTax"
                data-cy="franchiseCommisionTax"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.franchiseInvoicePath')}
                id="service-order-franchise-assignment-franchiseInvoicePath"
                name="franchiseInvoicePath"
                data-cy="franchiseInvoicePath"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.createddBy')}
                id="service-order-franchise-assignment-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.createdTime')}
                id="service-order-franchise-assignment-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.updatedBy')}
                id="service-order-franchise-assignment-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.updatedTime')}
                id="service-order-franchise-assignment-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="service-order-franchise-assignment-serviceOrder"
                name="serviceOrder"
                data-cy="serviceOrder"
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.serviceOrder')}
                type="select"
              >
                <option value="" key="0" />
                {serviceOrders
                  ? serviceOrders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="service-order-franchise-assignment-franchise"
                name="franchise"
                data-cy="franchise"
                label={translate('framasaasApp.serviceOrderFranchiseAssignment.franchise')}
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
                to="/service-order-franchise-assignment"
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

export default ServiceOrderFranchiseAssignmentUpdate;
