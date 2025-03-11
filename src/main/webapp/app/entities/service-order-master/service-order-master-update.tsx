import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { ServiceOrderType } from 'app/shared/model/enumerations/service-order-type.model';
import { createEntity, getEntity, updateEntity } from './service-order-master.reducer';

export const ServiceOrderMasterUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const serviceOrderMasterEntity = useAppSelector(state => state.serviceOrderMaster.entity);
  const loading = useAppSelector(state => state.serviceOrderMaster.loading);
  const updating = useAppSelector(state => state.serviceOrderMaster.updating);
  const updateSuccess = useAppSelector(state => state.serviceOrderMaster.updateSuccess);
  const serviceOrderTypeValues = Object.keys(ServiceOrderType);

  const handleClose = () => {
    navigate('/service-order-master');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

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
    if (values.slaInHours !== undefined && typeof values.slaInHours !== 'number') {
      values.slaInHours = Number(values.slaInHours);
    }
    if (values.charge !== undefined && typeof values.charge !== 'number') {
      values.charge = Number(values.charge);
    }
    if (values.tax !== undefined && typeof values.tax !== 'number') {
      values.tax = Number(values.tax);
    }
    if (values.franchiseCommissionWithinSla !== undefined && typeof values.franchiseCommissionWithinSla !== 'number') {
      values.franchiseCommissionWithinSla = Number(values.franchiseCommissionWithinSla);
    }
    if (values.franchiseChargeWithinSlaTax !== undefined && typeof values.franchiseChargeWithinSlaTax !== 'number') {
      values.franchiseChargeWithinSlaTax = Number(values.franchiseChargeWithinSlaTax);
    }
    if (values.franchiseCommissionOutsideSla !== undefined && typeof values.franchiseCommissionOutsideSla !== 'number') {
      values.franchiseCommissionOutsideSla = Number(values.franchiseCommissionOutsideSla);
    }
    if (values.franchiseChargeOutsideSlaTax !== undefined && typeof values.franchiseChargeOutsideSlaTax !== 'number') {
      values.franchiseChargeOutsideSlaTax = Number(values.franchiseChargeOutsideSlaTax);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...serviceOrderMasterEntity,
      ...values,
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
          serviceOrderType: 'REPAIR',
          ...serviceOrderMasterEntity,
          createdTime: convertDateTimeFromServer(serviceOrderMasterEntity.createdTime),
          updatedTime: convertDateTimeFromServer(serviceOrderMasterEntity.updatedTime),
          product: serviceOrderMasterEntity?.product?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.serviceOrderMaster.home.createOrEditLabel" data-cy="ServiceOrderMasterCreateUpdateHeading">
            <Translate contentKey="framasaasApp.serviceOrderMaster.home.createOrEditLabel">Create or edit a ServiceOrderMaster</Translate>
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
                  id="service-order-master-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.serviceOrderType')}
                id="service-order-master-serviceOrderType"
                name="serviceOrderType"
                data-cy="serviceOrderType"
                type="select"
              >
                {serviceOrderTypeValues.map(serviceOrderType => (
                  <option value={serviceOrderType} key={serviceOrderType}>
                    {translate(`framasaasApp.ServiceOrderType.${serviceOrderType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.slaInHours')}
                id="service-order-master-slaInHours"
                name="slaInHours"
                data-cy="slaInHours"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.charge')}
                id="service-order-master-charge"
                name="charge"
                data-cy="charge"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.tax')}
                id="service-order-master-tax"
                name="tax"
                data-cy="tax"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.franchiseCommissionWithinSla')}
                id="service-order-master-franchiseCommissionWithinSla"
                name="franchiseCommissionWithinSla"
                data-cy="franchiseCommissionWithinSla"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.franchiseChargeWithinSlaTax')}
                id="service-order-master-franchiseChargeWithinSlaTax"
                name="franchiseChargeWithinSlaTax"
                data-cy="franchiseChargeWithinSlaTax"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.franchiseCommissionOutsideSla')}
                id="service-order-master-franchiseCommissionOutsideSla"
                name="franchiseCommissionOutsideSla"
                data-cy="franchiseCommissionOutsideSla"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.franchiseChargeOutsideSlaTax')}
                id="service-order-master-franchiseChargeOutsideSlaTax"
                name="franchiseChargeOutsideSlaTax"
                data-cy="franchiseChargeOutsideSlaTax"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.isActive')}
                id="service-order-master-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.createddBy')}
                id="service-order-master-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.createdTime')}
                id="service-order-master-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.updatedBy')}
                id="service-order-master-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.serviceOrderMaster.updatedTime')}
                id="service-order-master-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="service-order-master-product"
                name="product"
                data-cy="product"
                label={translate('framasaasApp.serviceOrderMaster.product')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/service-order-master" replace color="info">
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

export default ServiceOrderMasterUpdate;
