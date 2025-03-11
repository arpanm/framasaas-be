import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { createEntity, getEntity, updateEntity } from './product-price-history.reducer';

export const ProductPriceHistoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const productPriceHistoryEntity = useAppSelector(state => state.productPriceHistory.entity);
  const loading = useAppSelector(state => state.productPriceHistory.loading);
  const updating = useAppSelector(state => state.productPriceHistory.updating);
  const updateSuccess = useAppSelector(state => state.productPriceHistory.updateSuccess);

  const handleClose = () => {
    navigate('/product-price-history');
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
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...productPriceHistoryEntity,
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
          updatedTime: displayDefaultDateTime(),
        }
      : {
          ...productPriceHistoryEntity,
          updatedTime: convertDateTimeFromServer(productPriceHistoryEntity.updatedTime),
          product: productPriceHistoryEntity?.product?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.productPriceHistory.home.createOrEditLabel" data-cy="ProductPriceHistoryCreateUpdateHeading">
            <Translate contentKey="framasaasApp.productPriceHistory.home.createOrEditLabel">Create or edit a ProductPriceHistory</Translate>
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
                  id="product-price-history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.productPriceHistory.price')}
                id="product-price-history-price"
                name="price"
                data-cy="price"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.productPriceHistory.tax')}
                id="product-price-history-tax"
                name="tax"
                data-cy="tax"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.productPriceHistory.franchiseCommission')}
                id="product-price-history-franchiseCommission"
                name="franchiseCommission"
                data-cy="franchiseCommission"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.productPriceHistory.franchiseTax')}
                id="product-price-history-franchiseTax"
                name="franchiseTax"
                data-cy="franchiseTax"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.productPriceHistory.updateDescription')}
                id="product-price-history-updateDescription"
                name="updateDescription"
                data-cy="updateDescription"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.productPriceHistory.updatedBy')}
                id="product-price-history-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.productPriceHistory.updatedTime')}
                id="product-price-history-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="product-price-history-product"
                name="product"
                data-cy="product"
                label={translate('framasaasApp.productPriceHistory.product')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product-price-history" replace color="info">
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

export default ProductPriceHistoryUpdate;
