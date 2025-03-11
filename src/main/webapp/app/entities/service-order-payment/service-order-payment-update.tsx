import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { ModeOfPayment } from 'app/shared/model/enumerations/mode-of-payment.model';
import { createEntity, getEntity, updateEntity } from './service-order-payment.reducer';

export const ServiceOrderPaymentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const serviceOrderPaymentEntity = useAppSelector(state => state.serviceOrderPayment.entity);
  const loading = useAppSelector(state => state.serviceOrderPayment.loading);
  const updating = useAppSelector(state => state.serviceOrderPayment.updating);
  const updateSuccess = useAppSelector(state => state.serviceOrderPayment.updateSuccess);
  const paymentStatusValues = Object.keys(PaymentStatus);
  const modeOfPaymentValues = Object.keys(ModeOfPayment);

  const handleClose = () => {
    navigate('/service-order-payment');
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
    values.pgTxnResponseTime = convertDateTimeToServer(values.pgTxnResponseTime);
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...serviceOrderPaymentEntity,
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
          pgTxnResponseTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          paymentStatus: 'INITIATED',
          mop: 'CASH',
          ...serviceOrderPaymentEntity,
          pgTxnResponseTime: convertDateTimeFromServer(serviceOrderPaymentEntity.pgTxnResponseTime),
          createdTime: convertDateTimeFromServer(serviceOrderPaymentEntity.createdTime),
          updatedTime: convertDateTimeFromServer(serviceOrderPaymentEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.serviceOrderPayment.home.createOrEditLabel" data-cy="ServiceOrderPaymentCreateUpdateHeading">
            <Translate contentKey="framasaasApp.serviceOrderPayment.home.createOrEditLabel">Create or edit a ServiceOrderPayment</Translate>
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
                  id="service-order-payment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.paymentLink')}
                id="service-order-payment-paymentLink"
                name="paymentLink"
                data-cy="paymentLink"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.paymentStatus')}
                id="service-order-payment-paymentStatus"
                name="paymentStatus"
                data-cy="paymentStatus"
                type="select"
              >
                {paymentStatusValues.map(paymentStatus => (
                  <option value={paymentStatus} key={paymentStatus}>
                    {translate(`framasaasApp.PaymentStatus.${paymentStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.mop')}
                id="service-order-payment-mop"
                name="mop"
                data-cy="mop"
                type="select"
              >
                {modeOfPaymentValues.map(modeOfPayment => (
                  <option value={modeOfPayment} key={modeOfPayment}>
                    {translate(`framasaasApp.ModeOfPayment.${modeOfPayment}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.pgTxnId')}
                id="service-order-payment-pgTxnId"
                name="pgTxnId"
                data-cy="pgTxnId"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.pgTxnResponse')}
                id="service-order-payment-pgTxnResponse"
                name="pgTxnResponse"
                data-cy="pgTxnResponse"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.pgTxnResponseTime')}
                id="service-order-payment-pgTxnResponseTime"
                name="pgTxnResponseTime"
                data-cy="pgTxnResponseTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.paymentInitiatedBy')}
                id="service-order-payment-paymentInitiatedBy"
                name="paymentInitiatedBy"
                data-cy="paymentInitiatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.isActive')}
                id="service-order-payment-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.createddBy')}
                id="service-order-payment-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.createdTime')}
                id="service-order-payment-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.updatedBy')}
                id="service-order-payment-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderPayment.updatedTime')}
                id="service-order-payment-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/service-order-payment" replace color="info">
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

export default ServiceOrderPaymentUpdate;
