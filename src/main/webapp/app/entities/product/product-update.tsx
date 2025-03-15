import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { getEntities as getBrands } from 'app/entities/brand/brand.reducer';
import { getEntities as getHsns } from 'app/entities/hsn/hsn.reducer';
import { getEntities as getWarrantyMasters } from 'app/entities/warranty-master/warranty-master.reducer';
import { ProductType } from 'app/shared/model/enumerations/product-type.model';
import { createEntity, getEntity, updateEntity } from './product.reducer';

export const ProductUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const categories = useAppSelector(state => state.category.entities);
  const brands = useAppSelector(state => state.brand.entities);
  const hsns = useAppSelector(state => state.hsn.entities);
  const warrantyMasters = useAppSelector(state => state.warrantyMaster.entities);
  const productEntity = useAppSelector(state => state.product.entity);
  const loading = useAppSelector(state => state.product.loading);
  const updating = useAppSelector(state => state.product.updating);
  const updateSuccess = useAppSelector(state => state.product.updateSuccess);
  const productTypeValues = Object.keys(ProductType);

  const handleClose = () => {
    navigate('/product');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCategories({}));
    dispatch(getBrands({}));
    dispatch(getHsns({}));
    dispatch(getWarrantyMasters({}));
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
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...productEntity,
      ...values,
      category: categories.find(it => it.id.toString() === values.category?.toString()),
      brand: brands.find(it => it.id.toString() === values.brand?.toString()),
      hsn: hsns.find(it => it.id.toString() === values.hsn?.toString()),
      coveredUnderWarranties: mapIdList(values.coveredUnderWarranties),
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
          productType: 'COMMERCE',
          ...productEntity,
          createdTime: convertDateTimeFromServer(productEntity.createdTime),
          updatedTime: convertDateTimeFromServer(productEntity.updatedTime),
          category: productEntity?.category?.id,
          brand: productEntity?.brand?.id,
          hsn: productEntity?.hsn?.id,
          coveredUnderWarranties: productEntity?.coveredUnderWarranties?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.product.home.createOrEditLabel" data-cy="ProductCreateUpdateHeading">
            <Translate contentKey="framasaasApp.product.home.createOrEditLabel">Create or edit a Product</Translate>
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
                  id="product-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.product.productName')}
                id="product-productName"
                name="productName"
                data-cy="productName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.product.vendorProductId')}
                id="product-vendorProductId"
                name="vendorProductId"
                data-cy="vendorProductId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.product.description')}
                id="product-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.product.price')}
                id="product-price"
                name="price"
                data-cy="price"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.product.tax')}
                id="product-tax"
                name="tax"
                data-cy="tax"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.product.franchiseCommission')}
                id="product-franchiseCommission"
                name="franchiseCommission"
                data-cy="franchiseCommission"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.product.franchiseTax')}
                id="product-franchiseTax"
                name="franchiseTax"
                data-cy="franchiseTax"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.product.productType')}
                id="product-productType"
                name="productType"
                data-cy="productType"
                type="select"
              >
                {productTypeValues.map(productType => (
                  <option value={productType} key={productType}>
                    {translate(`framasaasApp.ProductType.${productType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.product.isActive')}
                id="product-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.product.createddBy')}
                id="product-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.product.createdTime')}
                id="product-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.product.updatedBy')}
                id="product-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.product.updatedTime')}
                id="product-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="product-category"
                name="category"
                data-cy="category"
                label={translate('framasaasApp.product.category')}
                type="select"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="product-brand" name="brand" data-cy="brand" label={translate('framasaasApp.product.brand')} type="select">
                <option value="" key="0" />
                {brands
                  ? brands.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="product-hsn" name="hsn" data-cy="hsn" label={translate('framasaasApp.product.hsn')} type="select">
                <option value="" key="0" />
                {hsns
                  ? hsns.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.product.coveredUnderWarranty')}
                id="product-coveredUnderWarranty"
                data-cy="coveredUnderWarranty"
                type="select"
                multiple
                name="coveredUnderWarranties"
              >
                <option value="" key="0" />
                {warrantyMasters
                  ? warrantyMasters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product" replace color="info">
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

export default ProductUpdate;
