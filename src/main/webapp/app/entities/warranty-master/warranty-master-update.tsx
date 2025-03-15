import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { WarrantyType } from 'app/shared/model/enumerations/warranty-type.model';
import { createEntity, getEntity, updateEntity } from './warranty-master.reducer';

export const WarrantyMasterUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const warrantyMasterEntity = useAppSelector(state => state.warrantyMaster.entity);
  const loading = useAppSelector(state => state.warrantyMaster.loading);
  const updating = useAppSelector(state => state.warrantyMaster.updating);
  const updateSuccess = useAppSelector(state => state.warrantyMaster.updateSuccess);
  const warrantyTypeValues = Object.keys(WarrantyType);

  const handleClose = () => {
    navigate('/warranty-master');
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
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }
    if (values.tax !== undefined && typeof values.tax !== 'number') {
      values.tax = Number(values.tax);
    }
    if (values.franchiseCommission !== undefined && typeof values.franchiseCommission !== 'number') {
      values.franchiseCommission = Number(values.franchiseCommission);
    }
    if (values.franchiseTax !== undefined && typeof values.franchiseTax !== 'number') {
      values.franchiseTax = Number(values.franchiseTax);
    }
    if (values.periodInMonths !== undefined && typeof values.periodInMonths !== 'number') {
      values.periodInMonths = Number(values.periodInMonths);
    }
    if (values.taxRate !== undefined && typeof values.taxRate !== 'number') {
      values.taxRate = Number(values.taxRate);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...warrantyMasterEntity,
      ...values,
      coveredSpares: mapIdList(values.coveredSpares),
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
          warrantyType: 'BRANDFREEWARRANTY',
          ...warrantyMasterEntity,
          createdTime: convertDateTimeFromServer(warrantyMasterEntity.createdTime),
          updatedTime: convertDateTimeFromServer(warrantyMasterEntity.updatedTime),
          coveredSpares: warrantyMasterEntity?.coveredSpares?.map(e => e.id.toString()),
          product: warrantyMasterEntity?.product?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.warrantyMaster.home.createOrEditLabel" data-cy="WarrantyMasterCreateUpdateHeading">
            <Translate contentKey="framasaasApp.warrantyMaster.home.createOrEditLabel">Create or edit a WarrantyMaster</Translate>
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
                  id="warranty-master-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.name')}
                id="warranty-master-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.vendorWarrantyMasterId')}
                id="warranty-master-vendorWarrantyMasterId"
                name="vendorWarrantyMasterId"
                data-cy="vendorWarrantyMasterId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.warrantyType')}
                id="warranty-master-warrantyType"
                name="warrantyType"
                data-cy="warrantyType"
                type="select"
              >
                {warrantyTypeValues.map(warrantyType => (
                  <option value={warrantyType} key={warrantyType}>
                    {translate(`framasaasApp.WarrantyType.${warrantyType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.description')}
                id="warranty-master-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.price')}
                id="warranty-master-price"
                name="price"
                data-cy="price"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.tax')}
                id="warranty-master-tax"
                name="tax"
                data-cy="tax"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.franchiseCommission')}
                id="warranty-master-franchiseCommission"
                name="franchiseCommission"
                data-cy="franchiseCommission"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.franchiseTax')}
                id="warranty-master-franchiseTax"
                name="franchiseTax"
                data-cy="franchiseTax"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.periodInMonths')}
                id="warranty-master-periodInMonths"
                name="periodInMonths"
                data-cy="periodInMonths"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.taxRate')}
                id="warranty-master-taxRate"
                name="taxRate"
                data-cy="taxRate"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.isActive')}
                id="warranty-master-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.createddBy')}
                id="warranty-master-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.createdTime')}
                id="warranty-master-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.updatedBy')}
                id="warranty-master-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.updatedTime')}
                id="warranty-master-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.warrantyMaster.coveredSpare')}
                id="warranty-master-coveredSpare"
                data-cy="coveredSpare"
                type="select"
                multiple
                name="coveredSpares"
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
              <ValidatedField
                id="warranty-master-product"
                name="product"
                data-cy="product"
                label={translate('framasaasApp.warrantyMaster.product')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/warranty-master" replace color="info">
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

export default WarrantyMasterUpdate;
