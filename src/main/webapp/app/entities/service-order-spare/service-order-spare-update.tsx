import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getServiceOrders } from 'app/entities/service-order/service-order.reducer';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { InventoryLocationType } from 'app/shared/model/enumerations/inventory-location-type.model';
import { ServiceOrderSpareStatus } from 'app/shared/model/enumerations/service-order-spare-status.model';
import { createEntity, getEntity, updateEntity } from './service-order-spare.reducer';

export const ServiceOrderSpareUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const serviceOrders = useAppSelector(state => state.serviceOrder.entities);
  const products = useAppSelector(state => state.product.entities);
  const serviceOrderSpareEntity = useAppSelector(state => state.serviceOrderSpare.entity);
  const loading = useAppSelector(state => state.serviceOrderSpare.loading);
  const updating = useAppSelector(state => state.serviceOrderSpare.updating);
  const updateSuccess = useAppSelector(state => state.serviceOrderSpare.updateSuccess);
  const inventoryLocationTypeValues = Object.keys(InventoryLocationType);
  const serviceOrderSpareStatusValues = Object.keys(ServiceOrderSpareStatus);

  const handleClose = () => {
    navigate('/service-order-spare');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getServiceOrders({}));
    dispatch(getProducts({}));
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
    if (values.tax !== undefined && typeof values.tax !== 'number') {
      values.tax = Number(values.tax);
    }
    if (values.totalCharge !== undefined && typeof values.totalCharge !== 'number') {
      values.totalCharge = Number(values.totalCharge);
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
      ...serviceOrderSpareEntity,
      ...values,
      serviceOrder: serviceOrders.find(it => it.id.toString() === values.serviceOrder?.toString()),
      product: products.find(it => it.id.toString() === values.product?.toString()),
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
          orderedFrom: 'ENGINEER',
          spareStatus: 'ADDED',
          ...serviceOrderSpareEntity,
          createdTime: convertDateTimeFromServer(serviceOrderSpareEntity.createdTime),
          updatedTime: convertDateTimeFromServer(serviceOrderSpareEntity.updatedTime),
          serviceOrder: serviceOrderSpareEntity?.serviceOrder?.id,
          product: serviceOrderSpareEntity?.product?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.serviceOrderSpare.home.createOrEditLabel" data-cy="ServiceOrderSpareCreateUpdateHeading">
            <Translate contentKey="framasaasApp.serviceOrderSpare.home.createOrEditLabel">Create or edit a ServiceOrderSpare</Translate>
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
                  id="service-order-spare-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.price')}
                id="service-order-spare-price"
                name="price"
                data-cy="price"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.tax')}
                id="service-order-spare-tax"
                name="tax"
                data-cy="tax"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.totalCharge')}
                id="service-order-spare-totalCharge"
                name="totalCharge"
                data-cy="totalCharge"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.franchiseCommision')}
                id="service-order-spare-franchiseCommision"
                name="franchiseCommision"
                data-cy="franchiseCommision"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.franchiseCommisionTax')}
                id="service-order-spare-franchiseCommisionTax"
                name="franchiseCommisionTax"
                data-cy="franchiseCommisionTax"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.orderedFrom')}
                id="service-order-spare-orderedFrom"
                name="orderedFrom"
                data-cy="orderedFrom"
                type="select"
              >
                {inventoryLocationTypeValues.map(inventoryLocationType => (
                  <option value={inventoryLocationType} key={inventoryLocationType}>
                    {translate(`framasaasApp.InventoryLocationType.${inventoryLocationType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.spareStatus')}
                id="service-order-spare-spareStatus"
                name="spareStatus"
                data-cy="spareStatus"
                type="select"
              >
                {serviceOrderSpareStatusValues.map(serviceOrderSpareStatus => (
                  <option value={serviceOrderSpareStatus} key={serviceOrderSpareStatus}>
                    {translate(`framasaasApp.ServiceOrderSpareStatus.${serviceOrderSpareStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.createddBy')}
                id="service-order-spare-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.createdTime')}
                id="service-order-spare-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.updatedBy')}
                id="service-order-spare-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderSpare.updatedTime')}
                id="service-order-spare-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="service-order-spare-serviceOrder"
                name="serviceOrder"
                data-cy="serviceOrder"
                label={translate('framasaasApp.serviceOrderSpare.serviceOrder')}
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
                id="service-order-spare-product"
                name="product"
                data-cy="product"
                label={translate('framasaasApp.serviceOrderSpare.product')}
                type="select"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/service-order-spare" replace color="info">
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

export default ServiceOrderSpareUpdate;
