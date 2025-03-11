import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { getEntities as getArticles } from 'app/entities/article/article.reducer';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { ServiceOrderType } from 'app/shared/model/enumerations/service-order-type.model';
import { ServiceOrderStatus } from 'app/shared/model/enumerations/service-order-status.model';
import { createEntity, getEntity, updateEntity } from './service-order.reducer';

export const ServiceOrderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.customer.entities);
  const articles = useAppSelector(state => state.article.entities);
  const addresses = useAppSelector(state => state.address.entities);
  const serviceOrderEntity = useAppSelector(state => state.serviceOrder.entity);
  const loading = useAppSelector(state => state.serviceOrder.loading);
  const updating = useAppSelector(state => state.serviceOrder.updating);
  const updateSuccess = useAppSelector(state => state.serviceOrder.updateSuccess);
  const serviceOrderTypeValues = Object.keys(ServiceOrderType);
  const serviceOrderStatusValues = Object.keys(ServiceOrderStatus);

  const handleClose = () => {
    navigate('/service-order');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCustomers({}));
    dispatch(getArticles({}));
    dispatch(getAddresses({}));
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
    values.confirmedTime = convertDateTimeToServer(values.confirmedTime);
    values.closedTime = convertDateTimeToServer(values.closedTime);
    if (values.serviceCharge !== undefined && typeof values.serviceCharge !== 'number') {
      values.serviceCharge = Number(values.serviceCharge);
    }
    if (values.tax !== undefined && typeof values.tax !== 'number') {
      values.tax = Number(values.tax);
    }
    if (values.totalSpareCharges !== undefined && typeof values.totalSpareCharges !== 'number') {
      values.totalSpareCharges = Number(values.totalSpareCharges);
    }
    if (values.totalSpareTax !== undefined && typeof values.totalSpareTax !== 'number') {
      values.totalSpareTax = Number(values.totalSpareTax);
    }
    if (values.totalCharges !== undefined && typeof values.totalCharges !== 'number') {
      values.totalCharges = Number(values.totalCharges);
    }
    if (values.totalPaymentDone !== undefined && typeof values.totalPaymentDone !== 'number') {
      values.totalPaymentDone = Number(values.totalPaymentDone);
    }
    if (values.nps !== undefined && typeof values.nps !== 'number') {
      values.nps = Number(values.nps);
    }
    if (values.priority !== undefined && typeof values.priority !== 'number') {
      values.priority = Number(values.priority);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...serviceOrderEntity,
      ...values,
      customer: customers.find(it => it.id.toString() === values.customer?.toString()),
      article: articles.find(it => it.id.toString() === values.article?.toString()),
      address: addresses.find(it => it.id.toString() === values.address?.toString()),
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
          confirmedTime: displayDefaultDateTime(),
          closedTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          orderType: 'REPAIR',
          orderStatus: 'CREATED',
          ...serviceOrderEntity,
          confirmedTime: convertDateTimeFromServer(serviceOrderEntity.confirmedTime),
          closedTime: convertDateTimeFromServer(serviceOrderEntity.closedTime),
          createdTime: convertDateTimeFromServer(serviceOrderEntity.createdTime),
          updatedTime: convertDateTimeFromServer(serviceOrderEntity.updatedTime),
          customer: serviceOrderEntity?.customer?.id,
          article: serviceOrderEntity?.article?.id,
          address: serviceOrderEntity?.address?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.serviceOrder.home.createOrEditLabel" data-cy="ServiceOrderCreateUpdateHeading">
            <Translate contentKey="framasaasApp.serviceOrder.home.createOrEditLabel">Create or edit a ServiceOrder</Translate>
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
                  id="service-order-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.description')}
                id="service-order-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.orderType')}
                id="service-order-orderType"
                name="orderType"
                data-cy="orderType"
                type="select"
              >
                {serviceOrderTypeValues.map(serviceOrderType => (
                  <option value={serviceOrderType} key={serviceOrderType}>
                    {translate(`framasaasApp.ServiceOrderType.${serviceOrderType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.orderStatus')}
                id="service-order-orderStatus"
                name="orderStatus"
                data-cy="orderStatus"
                type="select"
              >
                {serviceOrderStatusValues.map(serviceOrderStatus => (
                  <option value={serviceOrderStatus} key={serviceOrderStatus}>
                    {translate(`framasaasApp.ServiceOrderStatus.${serviceOrderStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.inWarranty')}
                id="service-order-inWarranty"
                name="inWarranty"
                data-cy="inWarranty"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.isFree')}
                id="service-order-isFree"
                name="isFree"
                data-cy="isFree"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.isSpareNeeded')}
                id="service-order-isSpareNeeded"
                name="isSpareNeeded"
                data-cy="isSpareNeeded"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.confirmedTime')}
                id="service-order-confirmedTime"
                name="confirmedTime"
                data-cy="confirmedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.closedTime')}
                id="service-order-closedTime"
                name="closedTime"
                data-cy="closedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.serviceCharge')}
                id="service-order-serviceCharge"
                name="serviceCharge"
                data-cy="serviceCharge"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.tax')}
                id="service-order-tax"
                name="tax"
                data-cy="tax"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.totalSpareCharges')}
                id="service-order-totalSpareCharges"
                name="totalSpareCharges"
                data-cy="totalSpareCharges"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.totalSpareTax')}
                id="service-order-totalSpareTax"
                name="totalSpareTax"
                data-cy="totalSpareTax"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.totalCharges')}
                id="service-order-totalCharges"
                name="totalCharges"
                data-cy="totalCharges"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.totalPaymentDone')}
                id="service-order-totalPaymentDone"
                name="totalPaymentDone"
                data-cy="totalPaymentDone"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.customerInvoicePath')}
                id="service-order-customerInvoicePath"
                name="customerInvoicePath"
                data-cy="customerInvoicePath"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.nps')}
                id="service-order-nps"
                name="nps"
                data-cy="nps"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.priority')}
                id="service-order-priority"
                name="priority"
                data-cy="priority"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.isActive')}
                id="service-order-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.createddBy')}
                id="service-order-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.createdTime')}
                id="service-order-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.updatedBy')}
                id="service-order-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrder.updatedTime')}
                id="service-order-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="service-order-customer"
                name="customer"
                data-cy="customer"
                label={translate('framasaasApp.serviceOrder.customer')}
                type="select"
              >
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="service-order-article"
                name="article"
                data-cy="article"
                label={translate('framasaasApp.serviceOrder.article')}
                type="select"
              >
                <option value="" key="0" />
                {articles
                  ? articles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="service-order-address"
                name="address"
                data-cy="address"
                label={translate('framasaasApp.serviceOrder.address')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/service-order" replace color="info">
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

export default ServiceOrderUpdate;
